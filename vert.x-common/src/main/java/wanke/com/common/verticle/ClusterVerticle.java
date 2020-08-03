package wanke.com.common.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import wanke.com.common.annotion.Controller;
import wanke.com.common.annotion.RequestMapping;
import wanke.com.common.config.VertxConfig;
import wanke.com.common.log.LogUtil;
import wanke.com.common.messagecodec.FlatBuffersMessageCodec;
import wanke.com.common.messagecodec.ProtoMessageCodec;
import wanke.com.common.msghandle.WrapMsg;
import wanke.com.common.util.ScanPackageClassUtil;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ClusterVerticle  extends AbstractVerticle {

    public void start() {
        //注册编码解码器,当前支持proto、flat、json
        ProtoMessageCodec protoMessageCodec = new ProtoMessageCodec();
        FlatBuffersMessageCodec flatBuffersMessageCodec = new FlatBuffersMessageCodec();
        EventBus eventBus = vertx.eventBus();
        eventBus.registerCodec(protoMessageCodec);
        eventBus.registerCodec(flatBuffersMessageCodec);

        //扫描
        List<String> controllerList = null;
        Method[] methods = null;
        try {
           controllerList = ScanPackageClassUtil.getClassNameFromPackage(VertxConfig.getPkg());
           LogUtil.info(controllerList.toString());
            for (String controllerClass : controllerList) {
                Class<?> controller = Class.forName(controllerClass);
                if (!controller.isAnnotationPresent(Controller.class)) {
                    continue;
                }
                methods = controller.getMethods();
                String firstRMString = controller.getAnnotation(RequestMapping.class).value();
                Object o = controller.newInstance();
                for (Method method : methods) {
                    RequestMapping curMethodRM = method.getAnnotation(RequestMapping.class);
                    if (curMethodRM == null){
                        continue;
                    }
                    Type t = method.getAnnotatedReturnType().getType();
                    if (!t.getTypeName().equals("java.util.Map") && !t.getTypeName().equals("java.util.HashMap") && !t.getTypeName().equals("void")){
                        LogUtil.errorDirect("must method with Map or HashMap or void");
                        System.exit(-1);
                    }
                    String secondRMString = curMethodRM.value();
                    eventBus.consumer(firstRMString + "." + secondRMString, msg -> {
                        WrapMsg wrapMsgReq = new WrapMsg();
                        wrapMsgReq.setInnerMsg(msg);
                        if (t.getTypeName().equals("void")){
                            try {
                                method.invoke(o, wrapMsgReq);
                            } catch (Exception e) {
                                LogUtil.error(e.getMessage());
                            }
                        }else {
                            vertx.executeBlocking(future -> {
                                try {
                                    Object reply = method.invoke(o, wrapMsgReq);
                                    future.complete(reply);
                                } catch (Exception e) {
                                    LogUtil.error(e.getMessage());
                                }
                            }, res -> {
                                wrapMsgReq.reply((Map) res.result());
                            });
                        }
                    });
                }
            }
        }catch (Exception e){
            LogUtil.errorDirect(e.getMessage());
            System.exit(-1);
        }
    }
}
