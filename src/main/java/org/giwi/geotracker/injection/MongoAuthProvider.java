package org.giwi.geotracker.injection;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.mongo.MongoAuth;
import io.vertx.ext.mongo.MongoClient;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * The type Mongo auth provider.
 */
class MongoAuthProvider implements Provider<MongoAuth> {
    @Inject
    private MongoClient mongo;

    @Override
    public MongoAuth get() {
        JsonObject authProperties = new JsonObject();
        return MongoAuth.create(mongo, authProperties);
    }
}