package org.example;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.types.HttpEndpoint;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: Administrator
 * @Description:
 * @Date: 2020/7/16 10:42
 * @Version: 1.0
 */
public class AppCluster {

    public static void main(String[] args) throws  UnknownHostException {
        final VertxOptions vertxOptions = new VertxOptions();
        EventBusOptions eventBusOptions = new EventBusOptions();
        // 本机局域网Ip
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        vertxOptions.setEventBusOptions(eventBusOptions).getEventBusOptions().setHost(hostAddress);

        HazelcastClusterManager clusterManager = new HazelcastClusterManager();

        vertxOptions.setClusterManager(clusterManager);

        Vertx.clusteredVertx(vertxOptions, res -> {
            Vertx vertx = res.result();
            if (res.succeeded()) {
                vertx.deployVerticle(new MyFirstVerticle());
                vertx.deployVerticle(new MySecondVerticle());
                ServiceDiscovery serviceDiscovery = ServiceDiscovery.create(vertx);
                Record record1 = HttpEndpoint.createRecord("first-service", "localhost", 8089, "/");
                Record record2 = HttpEndpoint.createRecord("second-service", "localhost", 8090, "/");
                serviceDiscovery.publish(record1,ar -> { System.out.println("cluser03 - first-service publish success!");});
                serviceDiscovery.publish(record2,ar -> { System.out.println("cluser03 - first-service publish success!");});

            } else {
                res.cause().printStackTrace();
            }
        });
    }
}