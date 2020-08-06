package com.wanke.common.starter;

import com.wanke.common.log.LogUtil;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.eventbus.EventBusOptions;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;
import com.wanke.common.config.VertxConfig;
import com.wanke.common.vertx.ClusterVerticle;


/**
 * @Author: Administrator
 * @Description:
 * @Date: 2020/7/16 10:42
 * @Version: 1.0
 */
public class AppCluster {

    public static void main(String[] args)  {
        try {
            Class.forName("com.wanke.common.config.VertxConfig");
        } catch (ClassNotFoundException e) {
           System.exit(-1);
        }
        final VertxOptions vertxOptions = new VertxOptions();
        EventBusOptions eventBusOptions = new EventBusOptions();
        //设置eventbus数据监听端口
        vertxOptions.setEventBusOptions(eventBusOptions).getEventBusOptions().setHost(VertxConfig.getDataIp());
        HazelcastClusterManager clusterManager = new HazelcastClusterManager();
        //集群方式启动
        vertxOptions.setClusterManager(clusterManager);
        Vertx.clusteredVertx(vertxOptions, res -> {
            Vertx result = res.result();
            result.deployVerticle(new ClusterVerticle(), r -> {
                if (r.succeeded()) {
                    LogUtil.infoDirect("vert.x is started successful !!!");
                } else {
                    r.cause().printStackTrace();
                    LogUtil.errorDirect("vert.x is started error !!!, " + r.cause().getMessage());
                }
            });
        });
    }
}