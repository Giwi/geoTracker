package org.giwi.geotracker.routes;

import com.google.inject.Inject;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.mongo.MongoAuth;
import io.vertx.ext.auth.mongo.impl.MongoUser;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CorsHandler;
import org.giwi.geotracker.annotation.VertxRoute;
import org.giwi.geotracker.beans.AuthUtils;
import org.giwi.geotracker.beans.ResponseUtils;
import org.giwi.geotracker.exception.BusinessException;

/**
 * The type Api route.
 */
@VertxRoute(rootPath = "/api/1", order = -1)
public class APIRoute implements VertxRoute.Route {
    @Inject
    private AuthUtils authUtils;
    @Inject
    private MongoAuth mongoAuth;
    @Inject
    private ResponseUtils responseUtils;

    @Override
    public Router init(Vertx vertx) {
        JsonObject authConfig = new JsonObject().put("keyStore", new JsonObject()
                .put("type", "jceks")
                .put("path", "keystore.jceks")
                .put("password", "secret"));

        JWTAuth authProvider = JWTAuth.create(vertx, authConfig);
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().path("/*")
                .handler(CorsHandler.create("*")
                        .allowedMethod(HttpMethod.GET)
                        .allowedMethod(HttpMethod.POST)
                        .allowedMethod(HttpMethod.PUT)
                        .allowedMethod(HttpMethod.DELETE)
                        .allowedHeader("Access-Control-Allow-Headers: Origin, X-Requested-With, Content-Type, Accept, X-secure-Token")
                );
        router.route().path("/*").produces("application/json")
                .handler(this::jsonHandler)
                .failureHandler(responseUtils::failureHandler);
        router.route().path("/private/*").handler(this::securityHandler);
        return router;
    }

    private void securityHandler(RoutingContext context) {
        authUtils.getClientFromToken(context.request(), clientRes -> {
            if (clientRes.succeeded()) {
                authUtils.testValidity(clientRes.result(), validityRes -> {
                    if (validityRes.succeeded()) {
                        authUtils.getUserFromClient(clientRes.result(), userRes -> {
                            if (userRes.succeeded()) {
                                context.setUser(new MongoUser(userRes.result(), mongoAuth));
                                context.next();
                            } else {
                                context.fail(new BusinessException(userRes.cause(), 401));
                            }
                        });
                    } else {
                        context.fail(new BusinessException(validityRes.cause(), 401));
                    }
                });
            } else {
                context.fail(new BusinessException(clientRes.cause(), 401));
            }
        });
    }

    private void jsonHandler(RoutingContext context) {
        context.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json; charset=utf-8")
                .putHeader(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, max-age=0, must-revalidate")
                .putHeader("Pragma", "no-cache")
                .putHeader(HttpHeaders.EXPIRES, "0");
        context.next();
    }
}
