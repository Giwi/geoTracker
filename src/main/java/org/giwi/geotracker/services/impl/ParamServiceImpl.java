package org.giwi.geotracker.services.impl;

import com.google.inject.Inject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.auth.mongo.MongoAuth;
import io.vertx.ext.mongo.MongoClient;
import org.giwi.geotracker.annotation.ProxyService;
import org.giwi.geotracker.beans.Utils;
import org.giwi.geotracker.constants.Roles;
import org.giwi.geotracker.services.ParamService;

import java.util.Arrays;

/**
 * The type Param service.
 */
@ProxyService(address = ParamService.ADDRESS, iface = ParamService.class)
public class ParamServiceImpl implements ParamService {

    @Inject
    private MongoClient mongoClient;
    @Inject
    private MongoAuth mongoAuth;
    @Inject
    private Utils utils;

    /**
     * Instantiates a new Param service.
     *
     * @param vertx the vertx
     */
    public ParamServiceImpl(Vertx vertx) {
        super();
    }

    @Override
    public void getRoles(Handler<AsyncResult<JsonArray>> resultHandler) {
        JsonArray jar = new JsonArray();
        Arrays.asList(Roles.values()).forEach(r -> jar.add(r.toString()));
        resultHandler.handle(Future.succeededFuture(jar));
    }
}
