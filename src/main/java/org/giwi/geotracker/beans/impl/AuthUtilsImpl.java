package org.giwi.geotracker.beans.impl;

import com.google.inject.Inject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.auth.mongo.MongoAuth;
import io.vertx.ext.mongo.MongoClient;
import org.apache.commons.lang3.StringUtils;
import org.giwi.geotracker.beans.AuthUtils;
import org.giwi.geotracker.exception.BusinessException;
import org.giwi.geotracker.services.UserService;

/**
 * The type Auth utils.
 */
public class AuthUtilsImpl implements AuthUtils {
    private static final Logger LOG = LoggerFactory.getLogger(AuthUtils.class.getName());
    /**
     * The Mongo client.
     */
    @Inject
    MongoClient mongoClient;
    /**
     * The Mongo auth.
     */
    @Inject
    MongoAuth mongoAuth;

    @Override
    public void getClientFromToken(HttpServerRequest req, Handler<AsyncResult<JsonObject>> resultHandler) {
        String token = req.getHeader(TOKEN);
        if (StringUtils.isBlank(token)) {
            resultHandler.handle(Future.failedFuture(new BusinessException("Token must be set.", 401)));
            return;
        }
        mongoClient.findOne(UserService.COLLECTION, new JsonObject().put("secureToken.token", token), new JsonObject(), mongoRes -> {
            if (mongoRes.succeeded()) {
                resultHandler.handle(Future.succeededFuture(mongoRes.result()));
            } else {
                LOG.error(mongoRes.cause());
                resultHandler.handle(Future.failedFuture(mongoRes.cause()));
            }
        });
    }

    @Override
    public void testValidity(JsonObject client, Handler<AsyncResult<JsonObject>> resultHandler) {
        if(client == null || !client.containsKey("secureToken")) {
            resultHandler.handle(Future.failedFuture(new BusinessException("No data", 401)));
        } else if (DEFAULT_SESSION_TIMEOUT < System.currentTimeMillis() - client.getJsonObject("secureToken").getLong("timestamp", 0L)) {
            client.remove("secureToken");
            resultHandler.handle(Future.failedFuture("Expired session"));
        } else {
            client.getJsonObject("secureToken").put("timestamp", System.currentTimeMillis());
            mongoClient.save(UserService.COLLECTION, client, mongoRes -> {
                if (mongoRes.succeeded()) {
                    resultHandler.handle(Future.succeededFuture(client));
                } else {
                    resultHandler.handle(Future.failedFuture(mongoRes.cause()));
                }
            });
        }
    }

    @Override
    public void getUserFromClient(JsonObject user, Handler<AsyncResult<JsonObject>> resultHandler) {
        mongoClient.findOne(mongoAuth.getCollectionName(),
                new JsonObject().put("_id", user.getString("_id")),
                new JsonObject(), resultHandler);
    }
}
