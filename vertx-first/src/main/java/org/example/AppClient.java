package org.example;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

/**
 * Hello world!
 *
 */
public class AppClient
{
    public static void main(String[] args){
        //单机模式
        Vertx vertx = Vertx.vertx(new VertxOptions().setWorkerPoolSize(20));
        DeploymentOptions optionsExcutorWorker = new DeploymentOptions().setWorkerPoolSize(2).setInstances(1);
        DeploymentOptions optionsPeriodic = new DeploymentOptions().setWorkerPoolSize(1).setInstances(1);
        DeploymentOptions optionsWeb = new DeploymentOptions().setWorkerPoolSize(2).setInstances(1);
        System.out.println("main thread " + Thread.currentThread().getName());
        vertx.deployVerticle(VerticleTcpClient.class.getName(),optionsWeb, res -> {
            if (res.succeeded()) {
                System.out.println("tcp Deployment thread:" + Thread.currentThread().getName());
//                System.out.println("tcp Deployment id is: " + res.result());
            } else {
                System.out.println("tcp Deployment failed!");
            }
        });

        vertx.deployVerticle(VerticleWeb1.class.getName(),optionsWeb, res -> {
            if (res.succeeded()) {
                System.out.println("web Deployment thread:" + Thread.currentThread().getName());
//                System.out.println("web Deployment id is: " + res.result());
            } else {
                System.out.println("web Deployment failed!");
            }
        });

        vertx.deployVerticle(VerticleWorker.class.getName(),optionsWeb, res -> {
            if (res.succeeded()) {
                System.out.println("work Deployment thread:" + Thread.currentThread().getName());
//                System.out.println("web Deployment id is: " + res.result());
            } else {
                System.out.println("work Deployment failed!");
            }
        });

        vertx.deployVerticle(VerticlePeriodic.class.getName(),optionsWeb, res -> {
            if (res.succeeded()) {
                System.out.println("periodic Deployment thread:" + Thread.currentThread().getName());
//                System.out.println("web Deployment id is: " + res.result());

            } else {
                System.out.println("periodic Deployment failed!");
            }
        });


//        vertx.deployVerticle(VerticleWorker.class.getName(),optionsWeb, res -> {
//            if (res.succeeded()) {
//                System.out.println("work2 Deployment thread:" + Thread.currentThread().getName());
////                System.out.println("web Deployment id is: " + res.result());
//            } else {
//                System.out.println("work2 Deployment failed!");
//            }
//        });
        //集群模式
//        ClusterManager mgr = new HazelcastClusterManager();//创建ClusterManger对象
//        VertxOptions options = new VertxOptions().setWorkerPoolSize(4).setClusterManager(mgr).setClusterHost("192.168.2.121");
//        System.out.println("1111" + Thread.currentThread().getName());
//        Vertx.clusteredVertx(options, res -> {
//            System.out.println("2222" + Thread.currentThread().getName());
//            if (res.succeeded()) {
//                System.out.println("3333" + Thread.currentThread().getName());
//                Vertx vertx = res.result();
//                DeploymentOptions optionsWeb = new DeploymentOptions().setInstances(1);
//                DeploymentOptions optionsExcutorWorker = new DeploymentOptions().setInstances(1);
//                vertx.deployVerticle(VerticleEvent.class.getName());
//                vertx.deployVerticle(VerticleWeb1.class.getName(),optionsWeb,result->{
//                    if (result.succeeded()){
//                        System.out.println("5555" + Thread.currentThread().getName());
//                    }
//                });
//                vertx.deployVerticle(VerticleWorker.class.getName(),optionsExcutorWorker,result->{
//                    if (result.succeeded()){
//                        System.out.println("101010" + Thread.currentThread().getName());
//                    }
//                });
//
//            } else {
//                // failed!
//            }
//        });

    }
}
