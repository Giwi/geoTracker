package org.giwi.geotracker.routes.priv;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.giwi.geotracker.annotation.VertxRoute;
import org.giwi.geotracker.beans.AuthUtils;
import org.giwi.geotracker.beans.ResponseUtils;
import org.giwi.geotracker.exception.BusinessException;
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
    private ResponseUtils responseUtils;
    @Inject
    private UserService userService;

    @Override
    public Router init(Vertx vertx) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.get("/logout").handler(this::logout);
        router.get("/").handler(this::getCurrentUser);
        router.get("/all").handler(this::getUserList);
        return router;
    }

    /**
     * @api {get} /api/1/private/user/all Get User list
     * @apiName getUserList
     * @apiGroup Users
     * @apiDescription Get User list
     * @apiHeader {String} secureToken User secureToken
     * @apiSuccess {Array} user User[]
     */
    private void getUserList(RoutingContext context) {
        userService.getUserList(new JsonObject(), res -> {
            if (res.succeeded()) {
                JsonArray jar = new JsonArray();
                res.result().forEach(u -> {
                    u.remove("password");
                    u.remove("salt");
                    u.remove("secureToken");
                    jar.add(u);
                });
                context.response().end(jar.encode());
            } else {
                context.fail(new BusinessException(res.cause()));
            }
        });
    }

    /**
     * @api {get} /api/1/private/user Get Current User
     * @apiName getCurrentUser
     * @apiGroup Users
     * @apiDescription Get the current logged user
     * @apiHeader {String} secureToken User secureToken
     * @apiSuccess {Object} user User
     */
    private void getCurrentUser(RoutingContext context) {
        authUtils.getClientFromToken(context.request(), res -> {
            if (res.succeeded()) {
                res.result().remove("secureToken");
                res.result().remove("salt");
                res.result().remove("password");
                context.response().end(res.result().encode());
            } else {
                context.fail(new BusinessException(res.cause()));
            }
        });
    }

    /**
     * @api {get} /api/1/private/user/logout Logout
     * @apiName logout
     * @apiGroup Users
     * @apiDescription Logout the user
     * @apiHeader {String} secureToken User secureToken
     * @apiSuccess {Boolean} status Status
     */
    private void logout(RoutingContext context) {
        authUtils.getClientFromToken(context.request(), res -> {
            res.result().getJsonObject("account").remove("secureToken");
            userService.updateUser(res.result(), upd -> {
                if (upd.succeeded()) {
                    responseUtils.sendStatus(context.response(), true);
                } else {
                    context.fail(new BusinessException(res.cause()));
                }
            });
        });
    }
}
