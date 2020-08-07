package com.wanke.common.client.handle;

import com.wanke.common.annotion.Publish;
import com.wanke.common.annotion.BusMapping;
import com.wanke.common.annotion.VertxClient;
import com.wanke.common.config.VertxConfig;
import com.wanke.common.log.LogUtil;
import com.wanke.common.msg.msghandle.WrapMsg;
import com.wanke.common.msg.proto.ProtoCommonMsg;
import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author: chendi
 * @Description: 代理请求客户端代理处理
 * @Date: 2020/8/5 15:43
 * @Version: 1.0
 */
public class VertxCientHandler implements InvocationHandler {

    private static AtomicInteger count = new AtomicInteger(0);
    
    private static volatile Map resMap = new HashMap();

    private static  Map emptyMap = new HashMap();
    
    private Vertx vertx;

    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    public <T> T getProxy(Class clazz) {
        return (T) Proxy.newProxyInstance(VertxCientHandler.class.getClassLoader(), new Class[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> declaringClass = method.getDeclaringClass();
        Publish publishAnnotation = declaringClass.getAnnotation(Publish.class);
        VertxClient vertxClientAnnotation = declaringClass.getAnnotation(VertxClient.class);
        Class<?> returnType = method.getReturnType();
        BusMapping classRM = declaringClass.getAnnotation(BusMapping.class);
        String headMapping = classRM.value();
        BusMapping methodRM = method.getAnnotation(BusMapping.class);
        String methodMapping = methodRM.value();
        EventBus eventBus = vertx.eventBus();
        //wrap args
        Map requestMap = null;
        if (args == null || args.length == 0){
            requestMap = emptyMap;

        }else {
            requestMap = WrapMsg.newMap(args[0]);
        }
        //publish data
        if (publishAnnotation != null){
            System.out.println(headMapping + "." + methodMapping);
            publish(headMapping,methodMapping,requestMap,eventBus);
            return null;
        }

        //send data without return
        if (vertxClientAnnotation != null && returnType.getTypeName().equals("void")){
            send(headMapping,methodMapping,requestMap,eventBus);
            return  null;
        }

       //request for response
        int serialNumber = count.getAndIncrement();
        eventBus.request(headMapping + "." + methodMapping, new WrapMsg().request(requestMap), VertxConfig.getOptions(), msg -> {
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
        Timestamp cur_timestamp= Timestamp.valueOf(LocalDateTime.now());
        long curTime = cur_timestamp.getTime();
        for(;;){
            //超时机制
            if (Timestamp.valueOf(LocalDateTime.now()).getTime() - curTime > Long.valueOf(VertxConfig.getTimeout()) ){
                break;
            }
           if (resMap.get(serialNumber) != null){
               break;
           }
        }
        Object res = resMap.get(serialNumber);
        if (res instanceof Exception){
            throw (Exception) res;
        }else {
            return resMap.get(serialNumber);
        }
    }


    void publish(String headMapping,String methodMapping,Map data,EventBus bus){
        bus.publish(headMapping + "." + methodMapping,new WrapMsg().request(data),VertxConfig.getOptions());
    }

    void send(String headMapping,String methodMapping,Map data,EventBus bus){
        bus.send(headMapping + "." + methodMapping,new WrapMsg().request(data),VertxConfig.getOptions());
    }

}