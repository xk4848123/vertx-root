package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.example.MessageCode.FlatBuffersMessageCodec;
import org.example.MessageCode.ProtoMessageCodec;
import org.example.flat.FlatBuffersCommonMsg;

public class MainFlatClusterVerticle extends AbstractVerticle {

    public void start() {
        try {
            Thread.sleep(30000);
        }catch (Exception e){

        }

        System.out.println("start thread" + Thread.currentThread().getName());
        //发布eventbus服务
        ProtoMessageCodec protoMessageCodec = new ProtoMessageCodec();
        FlatBuffersMessageCodec flatBuffersMessageCodec = new FlatBuffersMessageCodec();
        EventBus eventBus = vertx.eventBus();
        eventBus.registerCodec(protoMessageCodec);
        eventBus.registerCodec(flatBuffersMessageCodec);
        DeliveryOptions options = new DeliveryOptions().setCodecName(flatBuffersMessageCodec.name());
        eventBus.consumer("device.getdevice", msg -> {
            System.out.println("收到消息");
            if (msg != null && msg.body() instanceof FlatBuffersCommonMsg){
                FlatBuffersCommonMsg receive_data = (FlatBuffersCommonMsg) msg.body();
            }
            FlatBuffersCommonMsg protoCommonMsg = new FlatBuffersCommonMsg();
            for (int i =0; i<100 ; i ++){
                protoCommonMsg.put("name_" + i,"wan_ke_receive");
            }

            msg.reply(protoCommonMsg,options);
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