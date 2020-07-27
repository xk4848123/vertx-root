package org.example;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * @Author: Administrator
 * @Description:
 * @Date: 2020/7/20 17:47
 * @Version: 1.0
 */
public class AppClient {

    public static void main(String[] args){
//        单机模式
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(1));
        DeploymentOptions optionsExcutorWorker = new DeploymentOptions().setWorkerPoolSize(2).setInstances(1);
        DeploymentOptions optionsPeriodic = new DeploymentOptions().setWorkerPoolSize(1).setInstances(1);
        DeploymentOptions optionsWeb = new DeploymentOptions().setWorkerPoolSize(1).setInstances(1);
        vertx.deployVerticle(GrpcClient.class.getName(),optionsWeb, res -> {
            if (res.succeeded()) {
                System.out.println("tcp Deployment id is: " + res.result());
            } else {
                System.out.println("tcp Deployment failed!");
            }
        });

    }
}