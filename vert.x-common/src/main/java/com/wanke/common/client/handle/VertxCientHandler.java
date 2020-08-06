package com.wanke.common.client.handle;

import com.wanke.common.annotion.RequestMapping;
import com.wanke.common.annotion.VertxClient;
import com.wanke.common.config.VertxConfig;
import com.wanke.common.log.LogUtil;
import com.wanke.common.msg.msghandle.WrapMsg;
import com.wanke.common.msg.proto.ProtoCommonMsg;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

import javax.security.auth.Subject;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: chendi
 * @Description: 代理请求客户端代理处理
 * @Date: 2020/8/5 15:43
 * @Version: 1.0
 */
public class VertxCientHandler implements InvocationHandler {

    private Vertx vertx;

    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    public <T> T getProxy(Class clazz) {
        return (T) Proxy.newProxyInstance(VertxCientHandler.class.getClassLoader(), new Class[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RequestMapping classRM = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        String headMapping = classRM.value();
        RequestMapping methodRM = method.getAnnotation(RequestMapping.class);
        String methodMapping = methodRM.value();
        EventBus eventBus = vertx.eventBus();
        eventBus.request(headMapping + "." + methodMapping, new WrapMsg().request((Map) args[0]), VertxConfig.getOptions(), msg -> {
            if (msg.succeeded()) {
                if (msg.result() != null) {
                    ProtoCommonMsg proto = (ProtoCommonMsg) msg.result().body();
                    System.out.println(proto);
                }
            } else {
                LogUtil.error(msg.cause().getMessage());
            }
        });
        return 1;
    }
}