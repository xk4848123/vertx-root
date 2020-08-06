package com.wanke.common.ioc;

import com.wanke.common.annotion.*;
import com.wanke.common.client.handle.VertxCientHandler;
import com.wanke.common.log.LogUtil;
import io.vertx.core.Vertx;
import com.wanke.common.msg.msghandle.WrapMsg;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: chendi
 * @Description:
 * @Date: 2020/8/5 16:00
 * @Version: 1.0
 */
public class BeansInitializer {

    //单例容器
    protected static Map<Class, Object> registerMap = new HashMap();

    //注入补缺
    protected static Map<Field,Object> pendingFields = new HashMap();

    //初始化需要启动的component
    protected static List<Object> pendingInitComponent = new ArrayList();

    public void initController(List<String> classList, Vertx vertx) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (String theClass : classList) {
            Class<?> clazz = Class.forName(theClass);
            if (clazz.isAnnotationPresent(Controller.class)) {
                Method[] methods = clazz.getMethods();
                String firstRMString = clazz.getAnnotation(RequestMapping.class).value();
                Object o = clazz.newInstance();
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        field.setAccessible(true);
                        field.set(o, registerMap.get(field.getClass()));
                    }
                }
                for (Method method : methods) {
                    RequestMapping curMethodRM = method.getAnnotation(RequestMapping.class);
                    if (curMethodRM == null) {
                        continue;
                    }
                    Type t = method.getAnnotatedReturnType().getType();
                    String typeName = t.getTypeName();
//                    System.out.println(typeName);
                    if (!typeName.equals("java.util.Map") && !typeName.equals("java.util.HashMap") && !typeName.equals("void") && !typeName.equals("wanke.com.common.dto.ResultDTO")) {
                        LogUtil.errorDirect("must method with Map or HashMap or void");
                        System.exit(-1);
                    }
                    String secondRMString = curMethodRM.value();
                    vertx.eventBus().consumer(firstRMString + "." + secondRMString, msg -> {
                        WrapMsg wrapMsgReq = new WrapMsg();
                        wrapMsgReq.setInnerMsg(msg);
                        if (t.getTypeName().equals("void")) {
                            try {
                                method.invoke(o, wrapMsgReq);
                            } catch (Exception e) {
                                LogUtil.error(e.getMessage());
                            }
                        } else {
                            vertx.executeBlocking(future -> {
                                try {
                                    Object reply = method.invoke(o, wrapMsgReq);
                                    future.complete(reply);
                                } catch (Exception e) {
                                    LogUtil.error(e.getMessage());
                                }
                            }, res -> {
                                if (typeName.equals("java.util.HashMap") || typeName.equals("java.util.Map")) {
                                    wrapMsgReq.reply((Map) res.result());
                                } else {
                                    Map resultMap = new HashMap<>();
                                    resultMap.put("1", res.result());
                                    wrapMsgReq.reply(resultMap);
                                }
                            });
                        }
                    });
                }
            }
        }
    }

    public void initComponent(List<String> classList,Vertx vertx) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        VertxCientHandler vertxCientHandler = new VertxCientHandler();
        vertxCientHandler.setVertx(vertx);
        for (String theClass : classList) {
            Class<?> clazz = Class.forName(theClass);
            if (clazz.isAnnotationPresent(VertxClient.class)) {
                registerMap.put(clazz, vertxCientHandler.getProxy(clazz));
            } else if (clazz.isAnnotationPresent(Component.class)) {
                //组件如果已经加载直接取出，否则重新实例化
                Object o = registerMap.get(clazz);
                if (o == null) {
                    o = clazz.newInstance();
                    registerMap.put(clazz, o);
                    Class<?>[] interfaces = clazz.getInterfaces();
                    if (interfaces.length != 0) {
                        for (Class zz : interfaces) {
                            registerMap.put(zz, o);
                        }
                    }
                }
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        field.setAccessible(true);
                        Object fieldObject = registerMap.get(field.getClass());
                        if (fieldObject == null) {
                            pendingFields.put(field, o);
                        } else {
                            field.set(o, fieldObject);
                        }
                    }
                }
                Class<?>[] componentInterfaces = clazz.getInterfaces();
                if (componentInterfaces.length != 0) {
                    for (Class zz : componentInterfaces) {
                        if (zz.equals(VertxInitializer.class)){
                            pendingInitComponent.add(o);
                            break;
                        }
                    }
                }
            }
        }
        repairField();
        componentInit();
    }
    private void repairField() throws ClassNotFoundException, IllegalAccessException {
      for (Map.Entry<Field, Object> entry : pendingFields.entrySet()) {
          Field field = entry.getKey();
          Object fieldObject = registerMap.get(field.getType());
          System.out.println(field.getType());
          field.set(entry.getValue(),fieldObject);
      }
    }
    private void componentInit()  {
     for (Object component : pendingInitComponent){
         ((VertxInitializer) component).init();
     }
    }
}