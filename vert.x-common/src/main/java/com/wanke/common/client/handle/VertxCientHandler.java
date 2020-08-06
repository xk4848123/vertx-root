package com.wanke.common.client.handle;

import com.wanke.common.annotion.RequestMapping;
import com.wanke.common.config.VertxConfig;
import com.wanke.common.log.LogUtil;
import com.wanke.common.msg.msghandle.WrapMsg;
import com.wanke.common.msg.proto.ProtoCommonMsg;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: chendi
 * @Description: 代理请求客户端代理处理
 * @Date: 2020/8/5 15:43
 * @Version: 1.0
 */
public class VertxCientHandler implements InvocationHandler {

    private static AtomicInteger count = new AtomicInteger(0);
    
    private static volatile Map resMap = new HashMap();
    
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
        int serialNumber = count.getAndIncrement();
        ReentrantLock lock = new ReentrantLock();
        eventBus.request(headMapping + "." + methodMapping, new WrapMsg().request((Map) args[0]), VertxConfig.getOptions(), msg -> {
            if (msg.succeeded()) {
                if (msg.result() != null) {
                    ProtoCommonMsg proto = (ProtoCommonMsg) msg.result().body();
                    System.out.println(proto.get(VertxConfig.getMsgKey()).getClass());
                    Object res = proto.get(VertxConfig.getMsgKey());
                    resMap.put(serialNumber,res);
                }
            } else {
                LogUtil.error(msg.cause().getMessage());
            }
        });
        for(;;){
           if (resMap.get(serialNumber) != null){
               break;
           }
        }
        return resMap.get(serialNumber);
    }

}