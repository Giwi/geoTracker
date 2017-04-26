package org.giwi.geotracker.routes.priv;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.giwi.geotracker.annotation.VertxRoute;
import org.giwi.geotracker.beans.AuthUtils;
import org.giwi.geotracker.exception.BusinessException;
import org.giwi.geotracker.services.ParamService;

import javax.inject.Inject;

/**
 * The type Param route.
 */
@VertxRoute(rootPath = "/api/1/private/param")
public class ParamRoute implements VertxRoute.Route {
    @Inject
    private ParamService paramService;
    @Inject
    private AuthUtils authUtils;

    /**
     * Init router.
     *
     * @param vertx the vertx
     * @return the router
     */
    @Override
    public Router init(Vertx vertx) {
        Router router = Router.router(vertx);
        router.get("/roles").handler(this::getRoles);
        return router;
    }
    /**
     * @api {get} /api/1/private/param/roles Get roles
     * @apiName getRoles
     * @apiGroup Params
     * @apiDescription Get roles
     * @apiHeader {String} secureToken User secureToken
     * @apiSuccess {Array} roles Role[]
     */
    private void getRoles(RoutingContext ctx) {
        paramService.getRoles(res -> {
            if (res.succeeded()) {
                ctx.response().end(res.result().encode());
            } else {
                ctx.fail(new BusinessException(res.cause()));
            }
        });
    }
}
