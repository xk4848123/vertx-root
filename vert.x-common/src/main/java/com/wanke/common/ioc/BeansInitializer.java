package com.wanke.common.ioc;

import com.wanke.common.annotion.*;
import com.wanke.common.client.handle.VertxCientHandler;
import com.wanke.common.client.handle.VertxConfigurationHandler;
import com.wanke.common.log.LogUtil;
import io.vertx.core.Vertx;
import com.wanke.common.msg.msghandle.WrapMsg;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

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
    protected static Map<Field, Object> pendingFields = new HashMap();

    //初始化需要启动的component
    protected static List<Object> pendingInitComponent = new ArrayList();

    //VertxConfiguration集合
    protected  static List<Class<?>> pendingInitConfiguration = new ArrayList<>();

    public void initController(List<String> classList, Vertx vertx) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (String theClass : classList) {
            Class<?> clazz = Class.forName(theClass);
            if (clazz.isAnnotationPresent(BusController.class)) {
                Method[] methods = clazz.getMethods();
                String firstRMString = clazz.getAnnotation(BusMapping.class).value();
                Object o = clazz.newInstance();
                Field[] declaredFields = clazz.getDeclaredFields();
                for (Field field : declaredFields) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        field.setAccessible(true);
                        field.set(o, registerMap.get(field.getClass()));
                    }
                }
                for (Method method : methods) {
                    BusMapping curMethodRM = method.getAnnotation(BusMapping.class);
                    if (curMethodRM == null) {
                        continue;
                    }
                    Type t = method.getAnnotatedReturnType().getType();
                    String secondRMString = curMethodRM.value();
                    vertx.eventBus().consumer(firstRMString + "." + secondRMString, msg -> {
                        WrapMsg wrapMsgReq = new WrapMsg();
                        wrapMsgReq.setInnerMsg(msg);
                        if (t.getTypeName().equals("void")) {
                            vertx.executeBlocking(future -> {
                                try {
                                    method.invoke(o, wrapMsgReq);
                                } catch (Exception e) {
                                    LogUtil.error(e.getMessage());
                                    e.printStackTrace();
                                    wrapMsgReq.reply(e);
                                }
                            },false,null);
                        } else {
                            vertx.executeBlocking(future -> {
                                try {
                                    Object reply = method.invoke(o, (Object) wrapMsgReq.body());
                                    future.complete(reply);
                                } catch (Exception e) {
                                    LogUtil.error(e.getMessage());
                                    e.printStackTrace();
                                    future.complete(e);
                                }
                            }, false,res -> {
                                    Map resultMap = new HashMap<>();
                                    wrapMsgReq.reply(res.result());
                            });
                        }
                    });
                }
            }
        }
    }

    public void initComponent(List<String> classList, Vertx vertx) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        VertxCientHandler vertxCientHandler = new VertxCientHandler();
        vertxCientHandler.setVertx(vertx);
        for (String theClass : classList) {
            Class<?> clazz = Class.forName(theClass);
            if (clazz.isAnnotationPresent(VertxClient.class) || clazz.isAnnotationPresent(Publish.class)) {
                registerMap.put(clazz, vertxCientHandler.getProxy(clazz));
            } else if (clazz.isAnnotationPresent(VertxComponent.class)) {
                //组件如果已经加载直接取出，否则重新实例化
                Object o = registerMap.get(clazz);
                if (o == null) {
                    o = clazz.newInstance();
                    registerMap.put(clazz, o);
                    Class<?>[] interfaces = clazz.getInterfaces();
                    if (interfaces.length != 0) {
                        for (Class zz : interfaces) {
                            if (zz.equals(VertxInitializer.class)){
                                continue;
                            }
                            registerMap.put(zz, o);
                        }
                    }
                }
                Field[] declaredFields = clazz.getDeclaredFields();
                autowiredField(declaredFields,o);
                Class<?>[] componentInterfaces = clazz.getInterfaces();
                if (componentInterfaces.length != 0) {
                    for (Class zz : componentInterfaces) {
                        if (zz.equals(VertxInitializer.class)) {
                            pendingInitComponent.add(o);
                            break;
                        }
                    }
                }
            }else if(clazz.isAnnotationPresent(VertxConfiguration.class)){
                pendingInitConfiguration.add(clazz);

            }
        }
        //第一次修复VertxComponent字段中没注入的属性
        repairField();
        initConfiguration();
        //第二次修复VertxComponent字段中没注入的属性
        repairField();
        componentInit(vertx);
    }

    private void repairField() throws IllegalAccessException {
        for (Iterator<Map.Entry<Field, Object>> it = pendingFields.entrySet().iterator(); it.hasNext();){
            Map.Entry<Field, Object> item = it.next();
            Field field = item.getKey();
            Object fieldObject = registerMap.get(field.getType());
            if (fieldObject != null){
                field.set(item.getValue(), fieldObject);
                it.remove();
            }
        }
    }

    private void componentInit(Vertx vertx) {
        for (Object component : pendingInitComponent) {
            vertx.executeBlocking(future -> {
                ((VertxInitializer) component).init();
            }, false,null);
        }
    }

    private void initConfiguration() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        for (Class<?> clazz : pendingInitConfiguration) {
            Object configurationProxy = VertxConfigurationHandler.getConfigurationProxy(clazz.newInstance());
            Field[] declaredFields = clazz.getDeclaredFields();
            autowiredField(declaredFields,configurationProxy);
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                VertxBean vertxBeanAnnotation = method.getAnnotation(VertxBean.class);
                if (vertxBeanAnnotation == null) {
                    continue;
                }
                method.invoke(configurationProxy, null);
            }
        }
    }

    private void autowiredField(Field[] declaredFields,Object o) throws IllegalAccessException {
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Autowired.class)) {
                field.setAccessible(true);
                System.out.println(field.getType());
                Object fieldObject = registerMap.get(field.getType());
                if (fieldObject == null) {
                    pendingFields.put(field, o);
                } else {
                    field.set(o, fieldObject);
                }
            }
        }
    }

    public static boolean classIsInitialize(Class<?> clazz){
         if (registerMap.get(clazz) != null){
             return true;
         }
         return false;
    }

    public static void register(Class<?> clazz,Object o){
       registerMap.put(clazz,o);
    }

    public static Object getRegisterMap(Class<?> clazz){
        return registerMap.get(clazz);
    }


}