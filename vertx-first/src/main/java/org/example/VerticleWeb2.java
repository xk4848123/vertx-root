package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

/**
 * @Author: Administrator
 * @Description: web2
 * @Date: 2020/6/5 9:49
 * @Version: 1.0
 */
public class VerticleWeb2 extends AbstractVerticle
{
    public void start() {
        // 创建HttpServer
        HttpServer server = vertx.createHttpServer();

        // 创建路由对象
        Router router = Router.router(vertx);

        // 监听/index地址
        router.route("/index").handler(request -> {
            System.out.println("haha" + Thread.currentThread().getName());
            request.response().end("INDEX SUCCESS");
        });

        // 把请求交给路由处理--------------------(1)
        server.requestHandler(router);
        server.listen(7777);


    }

}