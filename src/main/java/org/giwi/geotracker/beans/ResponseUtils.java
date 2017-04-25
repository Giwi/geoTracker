package org.giwi.geotracker.beans;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

/**
 * The interface Response utils.
 */
public interface ResponseUtils {
    /**
     * Send status.
     *
     * @param response the response
     * @param status   the status
     */
    void sendStatus(HttpServerResponse response, boolean status);

    /**
     * Send status.
     *
     * @param response the response
     * @param status   the status
     * @param message  the message
     */
    void sendStatus(HttpServerResponse response, boolean status, String message);

    /**
     * Send status.
     *
     * @param response   the response
     * @param status     the status
     * @param statusCode the status code
     * @param message    the message
     */
    void sendStatus(HttpServerResponse response, boolean status, int statusCode, String message);

    /**
     * Failure handler.
     *
     * @param routingContext the routing context
     */
    void failureHandler(RoutingContext routingContext);
}
