package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;


/**
 * Created by chengen on 26/04/2017.
 */
public class MainVerticle extends AbstractVerticle {
    public void start() {
        DeploymentOptions optionsWeb = new DeploymentOptions().setWorkerPoolSize(1).setInstances(1);
        vertx.deployVerticle(VerticleTcp.class.getName(),optionsWeb, res -> {
            if (res.succeeded()) {
                System.out.println("Tcp Deployment id is: " + res.result());
            } else {
                System.out.println("Tcp Deployment failed!");
            }
        });
    }
}