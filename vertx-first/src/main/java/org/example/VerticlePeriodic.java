package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

/**
 * @Author: Administrator
 * @Description: Periodic
 * @Date: 2020/6/4 17:30
 * @Version: 1.0
 */
public class VerticlePeriodic extends AbstractVerticle {
    public void start() {
        System.out.println("periodic start Thread:" + Thread.currentThread().getName());
        vertx.setPeriodic(2000, id -> {
            System.out.println("periodic Thread:" + Thread.currentThread().getName());
        });
    }

}