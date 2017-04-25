package org.giwi.geotracker;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import org.giwi.geotracker.verticles.MainVerticle;
import org.mvel2.util.Make;

public class Main {
    private Main() {
        // empty
    }
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(Make.String... args) {
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(40));
        vertx.deployVerticle(MainVerticle.class.getName());
    }
}
