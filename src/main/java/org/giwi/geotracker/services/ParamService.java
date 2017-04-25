package org.giwi.geotracker.services;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.serviceproxy.ProxyHelper;
import org.giwi.geotracker.services.impl.ParamServiceImpl;

/**
 * The interface Param service.
 */
@ProxyGen
@VertxGen
public interface ParamService {
    /**
     * The constant ADDRESS.
     */
    String ADDRESS = "vertx.param.service";
    /**
     * The constant COLLECTION.
     */
    String COLLECTION = "param";

    /**
     * Create param service.
     *
     * @param vertx the vertx
     * @return the param service
     */
    static ParamService create(Vertx vertx) {
        return new ParamServiceImpl(vertx);
    }

    /**
     * Create proxy param service.
     *
     * @param vertx   the vertx
     * @param address the address
     * @return the param service
     */
    static ParamService createProxy(Vertx vertx, String address) {
        return ProxyHelper.createProxy(ParamService.class, vertx, address);
    }

    /**
     * Gets roles.
     *
     * @param resultHandler the result handler
     */
    void getRoles(Handler<AsyncResult<JsonArray>> resultHandler);
}
