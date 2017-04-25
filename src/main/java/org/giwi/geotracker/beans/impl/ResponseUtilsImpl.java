package org.giwi.geotracker.beans.impl;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;
import org.giwi.geotracker.beans.ResponseUtils;
import org.giwi.geotracker.exception.BusinessException;

/**
 * The type Response utils.
 */
public class ResponseUtilsImpl implements ResponseUtils {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseUtilsImpl.class.getName());

    @Override
    public void sendStatus(HttpServerResponse response, boolean status) {
        sendStatus(response, status, "");
    }

    @Override
    public void sendStatus(HttpServerResponse response, boolean status, String message) {
        sendStatus(response, status, 200, message);
    }

    @Override
    public void sendStatus(HttpServerResponse response, boolean status, int statusCode, String message) {
        JsonObject resp = new JsonObject()
                .put("status", status)
                .put("message", message);
        response.setStatusCode(statusCode).end(resp.encode());
    }

    @Override
    public void failureHandler(RoutingContext context) {
        LOG.error(context.failure());
        if (context.failure() != null) {
            context.failure().printStackTrace();
        }
        BusinessException exception = (BusinessException) context.failure();
        final JsonObject error = new JsonObject()
                .put("timestamp", System.nanoTime())
                .put("status", exception.getStatusCode())
                .put("error", HttpResponseStatus.valueOf(exception.getStatusCode()).reasonPhrase())
                .put("path", context.normalisedPath())
                .put("exception", exception.getClass().getName());

        if (exception.getCause().getMessage() != null) {
            error.put("message", exception.getCause().getMessage());
            context.response().setStatusMessage(exception.getCause().getMessage());
        }
        context.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8");
        context.response().setStatusCode(exception.getStatusCode());
        context.response().end(error.encode());
    }
}
