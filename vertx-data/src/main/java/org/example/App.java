package org.example;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.types.HttpEndpoint;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main(String[] args){
//        单机模式
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(1));
        DeploymentOptions optionsExcutorWorker = new DeploymentOptions().setWorkerPoolSize(2).setInstances(1);
        DeploymentOptions optionsPeriodic = new DeploymentOptions().setWorkerPoolSize(1).setInstances(1);
        DeploymentOptions optionsWeb = new DeploymentOptions().setWorkerPoolSize(1).setInstances(1);
        vertx.deployVerticle(MyVerticle.class.getName(),optionsWeb, res -> {
            if (res.succeeded()) {
                System.out.println("tcp Deployment id is: " + res.result());
            } else {
                System.out.println("tcp Deployment failed!");
            }
        });

    }
}
