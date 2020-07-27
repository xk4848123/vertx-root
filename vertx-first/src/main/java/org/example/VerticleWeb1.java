package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;

/**
 * @Author: Administrator
 * @Description: web
 * @Date: 2020/6/5 9:49
 * @Version: 1.0
 */
public class VerticleWeb1 extends AbstractVerticle
{
    public void start() {
        System.out.println("web start thread " + Thread.currentThread().getName());
        // 在这里可以通过this.vertx获取到当前的Vertx
        Vertx vertx = this.vertx;

        // 创建一个HttpServer
        HttpServer server = vertx.createHttpServer();

        server.requestHandler(request -> {
            //处理方法在事件循环线程中，所以不要阻塞
            //建议自定义work线程处理
            System.out.println("web read " + Thread.currentThread().getName());

            // 获取到response对象
            HttpServerResponse response = request.response();

            // 设置响应头
            response.putHeader("Content-type", "text/html;charset=utf-8");

            // 响应数据
            response.end("SUCCESS");

        });

        server.listen(80);
    }

}