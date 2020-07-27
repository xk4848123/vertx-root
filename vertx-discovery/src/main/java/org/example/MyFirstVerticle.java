package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class MyFirstVerticle extends AbstractVerticle {

    public void start() {
        Router router = Router.router(vertx);
        router.get("/").handler(rc -> {
            rc.response().end("Welcome use first1-service!");
        });
        vertx.createHttpServer().requestHandler(router).listen(8089);
    }
}