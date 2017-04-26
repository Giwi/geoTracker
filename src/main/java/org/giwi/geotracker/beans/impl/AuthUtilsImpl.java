package org.giwi.geotracker.beans.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.mongo.MongoAuth;
import io.vertx.ext.mongo.MongoClient;
import org.giwi.geotracker.beans.AuthUtils;
import org.giwi.geotracker.exception.BusinessException;
import org.giwi.geotracker.services.UserService;

import javax.inject.Inject;

/**
 * The type Auth utils.
 */
public class AuthUtilsImpl implements AuthUtils {
    private static final Logger LOG = LoggerFactory.getLogger(AuthUtils.class.getName());
    @Inject
    private MongoClient mongoClient;
    @Inject
    private Vertx vertx;

    @Override
    public void getClientFromToken(String uid, Handler<AsyncResult<JsonObject>> resultHandler) {
        mongoClient.findOne(UserService.COLLECTION, new JsonObject().put("_id", uid), new JsonObject(), mongoRes -> {
            if (mongoRes.succeeded()) {
                resultHandler.handle(Future.succeededFuture(mongoRes.result()));
            } else {
                LOG.error(mongoRes.cause());
                resultHandler.handle(Future.failedFuture(mongoRes.cause()));
            }
        });
    }

    /**
     * Gets auth config.
     *
     * @return the auth config
     */
    @Override
    public JWTAuth getAuthProvider() {
        return JWTAuth.create(vertx, new JsonObject().put("keyStore", new JsonObject()
                .put("type", "jceks")
                .put("path", "keystore.jceks")
                .put("password", "secret")));
    }
}
