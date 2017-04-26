package org.giwi.geotracker.beans;

import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;

/**
 * The interface Auth utils.
 */
public interface AuthUtils {
    /**
     * The constant DEFAULT_SESSION_TIMEOUT.
     */
    long DEFAULT_SESSION_TIMEOUT = 1000L * 60 * 30;

    /**
     * Gets client from token.
     *  @param uid           the req
     * @param resultHandler the result handler
     */
    void getClientFromToken(String uid, Handler<AsyncResult<JsonObject>> resultHandler);

    /**
     * Gets auth provider.
     *
     * @return the auth provider
     */
    JWTAuth getAuthProvider();

}
