package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.example.MessageCode.ProtoMessageCodec;
import org.example.proto.ProtoCommonMsg;

public class MainJsonClusterVerticle2 extends AbstractVerticle {
    public void start() {
        ProtoMessageCodec protoMessageCodec = new ProtoMessageCodec();
        EventBus eventBus = vertx.eventBus();
        eventBus.registerCodec(protoMessageCodec);
        DeliveryOptions options = new DeliveryOptions().setCodecName(protoMessageCodec.name());
        System.out.println("start thread" + Thread.currentThread().getName());
        //发布web服务

        // 创建HttpServer
        HttpServer server = vertx.createHttpServer();
        // 创建路由对象
        Router router = Router.router(vertx);
        // 监听/index地址
        router.route("/index").handler(request -> {
            JsonObject jsonObject = new JsonObject();
            for (int i=0; i<500; i ++){
                jsonObject.put("name_" + i,"wan_ke");
            }
            //通过eventbus发送请求
            eventBus.request("com.xiaoniu.bus", jsonObject, msg -> {
                        if (msg.succeeded()) {
                            if (msg.result() != null){
                                String data = ((JsonObject) msg.result().body()).encodePrettily();
                            }
                        } else {
                            System.err.println(msg.cause().getMessage());
                            msg.cause().printStackTrace();
                        }
                    }
            );
            request.response().end("INDEX SUCCESS");
        });

        // 把请求交给路由处理--------------------(1)
        server.requestHandler(router);
        server.listen(7777);


    }
}
