package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.core.net.NetServer;

/**
 * @Author: Administrator
 * @Description: EventBus
 * @Date: 2020/6/5 13:29
 * @Version: 1.0
 */
public class VerticleEvent extends AbstractVerticle {

    public void start() {

        System.out.println("4444" + Thread.currentThread().getName());
        EventBus eventBus = vertx.eventBus();
        MessageConsumer<String> consumer = eventBus.consumer("news.uk.sport");
        consumer.handler(message
                -> {
            System.out.println("5555" + Thread.currentThread().getName());
            System.out.println("I have received a message: " + message.body());
        });
        eventBus.publish("news.uk.sport",
                "Yay! Someone kicked a ball");
    }
}