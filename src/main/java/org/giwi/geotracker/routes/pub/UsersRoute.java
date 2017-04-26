package org.giwi.geotracker.routes.pub;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.giwi.geotracker.annotation.VertxRoute;
import org.giwi.geotracker.beans.ResponseUtils;
import org.giwi.geotracker.exception.BusinessException;
import org.giwi.geotracker.services.UserService;

import javax.inject.Inject;

/**
 * The type Users route.
 */
@VertxRoute(rootPath = "/api/1/user")
public class UsersRoute implements VertxRoute.Route {

    @Inject
    private ResponseUtils responseUtils;
    @Inject
    private UserService userService;

    @Override
    public Router init(Vertx vertx) {
        Router router = Router.router(vertx);
        router.put("/register").handler(this::register);
        router.post("/login").handler(this::login);
        return router;
    }

    /**
     * @api {post} /api/1/user/login Login
     * @apiName login
     * @apiGroup Users
     * @apiDescription user login
     * @apiParam {String} username User username
     * @apiParam {String} password User password
     * @apiSuccess {String} secureToken Token of the User
     * @apiSuccess {Boolean} status Status
     */
    private void login(RoutingContext context) {
        userService.login(context.getBodyAsJson(), res -> {
            if (res.succeeded()) {
                context.response().end(res.result().encode());
            } else {
                context.fail(new BusinessException(res.cause(), 401));
            }
        });
    }

    /**
     * @api {put} /api/1/user/register Register
     * @apiName register
     * @apiGroup Users
     * @apiDescription Register a new user
     * @apiParam {String} username User username
     * @apiParam {String} password User password
     * @apiParam {String} name User name
     * @apiParam {String} firstname User firstname
     * @apiSuccess {Boolean} status Status
     */
    private void register(RoutingContext context) {
        userService.register(context.getBodyAsJson(), res -> {
            if (res.succeeded()) {
                responseUtils.sendStatus(context.response(), true);
            } else {
                context.fail(new BusinessException(res.cause()));
            }
        });
    }
}
