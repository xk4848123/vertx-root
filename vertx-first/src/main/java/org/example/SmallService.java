//package org.example;
//
//import io.vertx.core.AbstractVerticle;
//import io.vertx.core.Vertx;
//import io.vertx.core.VertxOptions;
//import io.vertx.core.spi.cluster.ClusterManager;
//import io.vertx.servicediscovery.ServiceDiscovery;
//import io.vertx.servicediscovery.types.HttpEndpoint;
//import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
//
///**
// * @Author: Administrator
// * @Description:
// * @Date: 2020/7/15 16:38
// * @Version: 1.0
// */
//public class SmallService extends AbstractVerticle {
//
//    public void start(){
//        ClusterManager mgr = new HazelcastClusterManager();
//        VertxOptions options = new VertxOptions().setClusterManager(mgr);
//        Vertx.clusteredVertx(options, res -> {
//            if (res.succeeded()) {
//                vertx = res.result();
//                vertx.deployVerticle(new VerticleWeb1());
//                vertx.deployVerticle(new VerticleWeb2());
//                ServiceDiscovery.create(vertx, sd -> {
//
//                    sd.publish(HttpEndpoint.createRecord("first-service", "localhost", 8081, "/"), ar -> {
//                        System.out.println("cluser01 - first-service publish success!");
//                    });
//                    sd.publish(HttpEndpoint.createRecord("second-service", "localhost", 8082, "/"), ar -> {
//                        System.out.println("cluser01 - second-service publish success!");
//                    });
//                });
//            } else {
//                res.cause().printStackTrace();
//            }
//        });
//
//    }
//
//}