package org.giwi.geotracker.injection;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.auth.mongo.MongoAuth;
import io.vertx.ext.mongo.MongoClient;
import org.giwi.geotracker.beans.AuthUtils;
import org.giwi.geotracker.beans.ResponseUtils;
import org.giwi.geotracker.beans.Utils;
import org.giwi.geotracker.beans.impl.AuthUtilsImpl;
import org.giwi.geotracker.beans.impl.ResponseUtilsImpl;
import org.giwi.geotracker.beans.impl.UtilsImpl;
import org.giwi.geotracker.services.ParamService;
import org.giwi.geotracker.services.UserService;

/**
 * The type Guice module.
 */
public class GuiceModule extends AbstractModule {
    private static final Logger LOG = LoggerFactory.getLogger(GuiceModule.class.getName());
    private JsonObject config;
    private Vertx vertx;

    /**
     * Instantiates a new Guice module.
     *
     * @param config the config
     * @param vertx  the vertx
     */
    public GuiceModule(JsonObject config, Vertx vertx) {
        this.config = config;
        this.vertx = vertx;
    }

    @Override
    protected void configure() {
        JsonObject mongoConfig = config.getJsonObject("mongo.db");
        bind(JsonObject.class).annotatedWith(Names.named("mongo.db")).toInstance(mongoConfig);
        bind(Vertx.class).toInstance(vertx);
        bind(MongoClient.class).toProvider(MongoClientProvider.class).asEagerSingleton();
        bind(MongoAuth.class).toProvider(MongoAuthProvider.class).asEagerSingleton();
        bind(AuthUtils.class).to(AuthUtilsImpl.class).asEagerSingleton();
        bind(Utils.class).to(UtilsImpl.class).asEagerSingleton();
        bind(ResponseUtils.class).to(ResponseUtilsImpl.class).asEagerSingleton();
        // Services
        bind(UserService.class).toInstance(UserService.createProxy(vertx, UserService.ADDRESS));
        bind(ParamService.class).toInstance(ParamService.createProxy(vertx, ParamService.ADDRESS));
    }
}
