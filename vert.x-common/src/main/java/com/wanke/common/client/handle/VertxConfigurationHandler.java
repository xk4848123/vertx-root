package com.wanke.common.client.handle;

import com.wanke.common.ioc.BeansInitializer;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author: chendi
 * @Description:
 * @Date: 2020/8/7 14:06
 * @Version: 1.0
 */
public class VertxConfigurationHandler implements MethodInterceptor {


    private Object target;
    public VertxConfigurationHandler(Object target) {
        this.target = target;
    }

    public Object createCgLibProxy(){
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    public static <T> T getConfigurationProxy(T target){
        return (T) new VertxConfigurationHandler(target).createCgLibProxy();
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (! BeansInitializer.classIsInitialize(method.getReturnType())){
            Object vertxBean = proxy.invokeSuper(obj,args);
            BeansInitializer.register(method.getReturnType(),vertxBean);
            return vertxBean;
        }
        return BeansInitializer.getRegisterMap(method.getReturnType());
    }
}