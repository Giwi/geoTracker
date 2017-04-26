package org.giwi.geotracker.routes;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import org.giwi.geotracker.annotation.VertxRoute;
import org.giwi.geotracker.beans.ResponseUtils;

import javax.inject.Inject;

/**
 * The type Api route.
 */
@VertxRoute(rootPath = "/api/1", order = -1)
public class APIRoute implements VertxRoute.Route {
    @Inject
    private ResponseUtils responseUtils;

    @Override
    public Router init(Vertx vertx) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().path("/*")
                .handler(CorsHandler.create("*")
                        .allowedMethod(HttpMethod.GET)
                        .allowedMethod(HttpMethod.POST)
                        .allowedMethod(HttpMethod.PUT)
                        .allowedMethod(HttpMethod.DELETE)
                        .allowedHeader("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept, Authorization")
                );
        router.route().path("/*").produces("application/json")
                .handler(this::jsonHandler)
                .failureHandler(responseUtils::failureHandler);
        return router;
    }

    private void jsonHandler(RoutingContext context) {
        context.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .putHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0, must-revalidate")
                .putHeader("Pragma", "no-cache")
                .putHeader(HttpHeaders.EXPIRES, "0");
        context.next();
    }
}
