package com.wanke.common.vertx;

import com.wanke.common.config.VertxConfig;
import com.wanke.common.ioc.BeansInitializer;
import com.wanke.common.log.LogUtil;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

import com.wanke.common.msg.messagecodec.FlatBuffersMessageCodec;
import com.wanke.common.msg.messagecodec.ProtoMessageCodec;
import com.wanke.common.util.ScanPackageClassUtil;

import java.util.List;

public class ClusterVerticle extends AbstractVerticle {

    public void start() {
        //注册编码解码器,当前支持proto、flat、json
        ProtoMessageCodec protoMessageCodec = new ProtoMessageCodec();
        FlatBuffersMessageCodec flatBuffersMessageCodec = new FlatBuffersMessageCodec();
        EventBus eventBus = vertx.eventBus();
        eventBus.registerCodec(protoMessageCodec);
        eventBus.registerCodec(flatBuffersMessageCodec);
        BeansInitializer beans = new BeansInitializer();
        try {
            List<String> clientList = ScanPackageClassUtil.getClassNameFromPackage(VertxConfig.getcPkg());
            beans.initComponent(clientList,vertx);
            List<String> controllerList = ScanPackageClassUtil.getClassNameFromPackage(VertxConfig.getPkg());
            LogUtil.info(controllerList.toString());
            beans.initController(controllerList, vertx);

        } catch (Exception e) {
            LogUtil.errorDirect(e.getMessage());
            System.exit(-1);
        }
    }
}
