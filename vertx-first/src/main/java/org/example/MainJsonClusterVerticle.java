package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.example.MessageCode.ProtoMessageCodec;
import org.example.proto.ProtoCommonMsg;

public class MainJsonClusterVerticle extends AbstractVerticle {

    public void start() {
        System.out.println("start thread" + Thread.currentThread().getName());
        //发布eventbus服务
        ProtoMessageCodec protoMessageCodec = new ProtoMessageCodec();
        EventBus eventBus = vertx.eventBus();
        eventBus.consumer("com.xiaoniu.bus", msg -> {
            System.out.println("收到消息");
            if (msg != null && msg.body() instanceof JsonObject){
                String receive_data = ((JsonObject) msg.body()).encodePrettily();
            }
            JsonObject jsonObject = new JsonObject();
            for (int i=0;i<500;i++){
                jsonObject.put("name_"+ i,"wan_ke_receive");
            }

            msg.reply(jsonObject);
        });
        //发布web服务

        // 创建HttpServer
        HttpServer server = vertx.createHttpServer();
        // 创建路由对象
        Router router = Router.router(vertx);

        // 监听/index地址
        router.route("/index").handler(request -> {
            request.response().end("INDEX SUCCESS");
        });

        // 监听/index地址
        router.route("/index1").handler(request -> {
            request.response().end("INDEX1 SUCCESS");
        });
        // 把请求交给路由处理--------------------(1)
        server.requestHandler(router);
        server.listen(8888);
    }
}