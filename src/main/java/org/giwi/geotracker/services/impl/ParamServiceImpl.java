package org.giwi.geotracker.services.impl;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import org.giwi.geotracker.annotation.ProxyService;
import org.giwi.geotracker.constants.Roles;
import org.giwi.geotracker.services.ParamService;

import java.util.Arrays;

/**
 * The type Param service.
 */
@ProxyService(address = ParamService.ADDRESS, iface = ParamService.class)
public class ParamServiceImpl implements ParamService {

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
