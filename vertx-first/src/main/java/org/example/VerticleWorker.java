package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;

/**
 * @Author: Administrator
 * @Description:
 * @Date: 2020/6/3 13:19
 * @Version: 1.0
 */
public class VerticleWorker extends AbstractVerticle {
    public void start() {
//        final WorkerExecutor executor = vertx.createSharedWorkerExecutor("executor");
        System.out.println("work2 start thread:" + Thread.currentThread().getName());
        vertx.executeBlocking(future -> {
            try {
                Thread.sleep(5000);
            }catch (Exception e){
                System.out.println(1);
            }
            System.out.println("long work2 one:" + Thread.currentThread().getName());
            // 调用一些需要耗费显著执行时间返回结果的阻塞式API
            String result = "executor1";
            future.complete(result);
        },false,res -> {
            System.out.println("The work2 one result is: " + res.result());
            System.out.println("long work2 one result:" + Thread.currentThread().getName());
//            executor.close();
        });


        vertx.executeBlocking(future -> {
            System.out.println("long work2 two:" + Thread.currentThread().getName());
            // 调用一些需要耗费显著执行时间返回结果的阻塞式API
            String result = "executor2";
            future.complete(result);
        },false,res -> {
            System.out.println("The long work2 two result is: " + res.result());
            System.out.println("long work2 two result:" + Thread.currentThread().getName());
        });
    }
}