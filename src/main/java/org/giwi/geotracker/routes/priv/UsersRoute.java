package org.giwi.geotracker.routes.priv;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.AuthHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import org.giwi.geotracker.annotation.VertxRoute;
import org.giwi.geotracker.beans.AuthUtils;
import org.giwi.geotracker.services.UserService;

import javax.inject.Inject;

/**
 * The type Users route.
 */
@VertxRoute(rootPath = "/api/1/private/user")
public class UsersRoute implements VertxRoute.Route {

    @Inject
    private AuthUtils authUtils;
    @Inject
    private UserService userService;

    @Override
    public Router init(Vertx vertx) {
        AuthHandler adminHandler = JWTAuthHandler.create(authUtils.getAuthProvider());
        adminHandler.addAuthority("admin");

        Router router = Router.router(vertx);
        router.route("/*").handler(JWTAuthHandler.create(authUtils.getAuthProvider()));
        router.get("/logout").handler(this::logout);
        router.get("/").handler(this::getCurrentUser);
        router.get("/all").handler(adminHandler);
        router.get("/all").handler(this::getUserList);
        return router;
    }

    /**
     * @api {get} /api/1/private/user/all Get User list
     * @apiName getUserList
     * @apiGroup Users
     * @apiDescription Get User list
     * @apiSuccess {Array} user User[]
     */
    private void getUserList(RoutingContext context) {
        userService.getUserList(new JsonObject(), res -> {
            if (res.succeeded()) {
                JsonArray jar = new JsonArray();
                res.result().forEach(u -> {
                    u.remove("password");
                    u.remove("salt");
                    jar.add(u);
                });
                context.response().end(jar.encode());
            } else {
                context.fail(res.cause());
            }
        });
    }

    /**
     * @api {get} /api/1/private/user Get Current User
     * @apiName getCurrentUser
     * @apiGroup Users
     * @apiDescription Get the current logged user
     * @apiSuccess {Object} user User
     */
    private void getCurrentUser(RoutingContext context) {
        String uid = context.user().principal().getString("uid");
        authUtils.getClientFromToken(uid, res -> {
            if (res.succeeded()) {
                res.result().remove("salt");
                res.result().remove("password");
                context.response().end(res.result().encode());
            } else {
                context.fail(res.cause());
            }
        });
    }

    /**
     * @api {get} /api/1/private/user/logout Logout
     * @apiName logout
     * @apiGroup Users
     * @apiDescription Logout the user
     * @apiSuccess {Boolean} status Status
     */
    private void logout(RoutingContext context) {
        String uid = context.user().principal().getString("uid");
    }
}
