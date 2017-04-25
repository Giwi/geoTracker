package org.giwi.geotracker.services;

import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.serviceproxy.ProxyHelper;
import org.giwi.geotracker.services.impl.UserServiceImpl;

import java.util.List;

/**
 * The interface Client service.
 */
@ProxyGen
@VertxGen
public interface UserService {
    /**
     * The constant ADDRESS.
     */
    String ADDRESS = "vertx.user.service";
    /**
     * The constant COLLECTION.
     */
    String COLLECTION = "user";

    /**
     * Create user service.
     *
     * @param vertx the vertx
     * @return the user service
     */
    static UserService create(Vertx vertx) {
        return new UserServiceImpl(vertx);
    }

    /**
     * Create proxy user service.
     *
     * @param vertx   the vertx
     * @param address the address
     * @return the user service
     */
    static UserService createProxy(Vertx vertx, String address) {
        return ProxyHelper.createProxy(UserService.class, vertx, address);
    }

    /**
     * Insert user.
     *
     * @param document      the document
     * @param resultHandler the result handler
     */
    void insertUser(JsonObject document, Handler<AsyncResult<String>> resultHandler);

    /**
     * Update user.
     *
     * @param user          the user
     * @param resultHandler the result handler
     */
    void updateUser(JsonObject user, Handler<AsyncResult<String>> resultHandler);

    /**
     * Gets user.
     *
     * @param query         the query
     * @param resultHandler the result handler
     */
    void getUser(JsonObject query, Handler<AsyncResult<JsonObject>> resultHandler);

    /**
     * Gets user list.
     *
     * @param query         the query
     * @param resultHandler the result handler
     */
    void getUserList(JsonObject query, Handler<AsyncResult<List<JsonObject>>> resultHandler);

    /**
     * Register.
     *
     * @param user          the user
     * @param resultHandler the result handler
     */
    void register(JsonObject user, Handler<AsyncResult<JsonObject>> resultHandler);

    /**
     * Login.
     *
     * @param query         the query
     * @param resultHandler the result handler
     */
    void login(JsonObject query, Handler<AsyncResult<JsonObject>> resultHandler);
}
