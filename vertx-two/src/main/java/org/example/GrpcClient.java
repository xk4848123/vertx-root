package org.example;

import io.grpc.ManagedChannel;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.grpc.VertxChannelBuilder;
import proto.helloworld.GreeterGrpc;
import proto.helloworld.HelloRequest;
import proto.test.TestGrpc;
import proto.test.TestRequest;

import java.net.InetAddress;

/**
 * @Author: Administrator
 * @Description:
 * @Date: 2020/7/20 17:45
 * @Version: 1.0
 */
public class GrpcClient extends AbstractVerticle {
    @Override
    public void start() throws Exception {
        ManagedChannel channel = VertxChannelBuilder
                .forAddress(vertx, InetAddress.getLocalHost().getHostAddress(), 8080)
                .usePlaintext(true)
                .build();
        TestGrpc.TestVertxStub stub1 = TestGrpc.newVertxStub(channel);
        //发布web服务
        // 创建HttpServer
        HttpServer server = vertx.createHttpServer();
        // 创建路由对象
        Router router = Router.router(vertx);
        // 监听/index1地址
        router.route("/index").handler(req -> {
            TestRequest.Builder builder = TestRequest.newBuilder();
            for (int i =0; i< 100 ; i++){
                builder.setName(i,"wan_ke");
            }
            TestRequest testRequest = builder.build();
            stub1.sayTest(testRequest, asyncResponse -> {
                if (asyncResponse.succeeded()) {

                } else {
                    asyncResponse.cause().printStackTrace();
                }
            });
            req.response().end("INDEX SUCCESS");
        });
        // 把请求交给路由处理--------------------(1)
        server.requestHandler(router);
        server.listen(7777);
    }
}