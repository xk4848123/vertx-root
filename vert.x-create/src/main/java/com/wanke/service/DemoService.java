package com.wanke.service;

import com.wanke.client.myclient;
import com.wanke.common.annotion.Autowired;
import com.wanke.common.annotion.Component;
import com.wanke.common.client.handle.VertxCientHandler;
import com.wanke.common.ioc.VertxInitializer;

import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * @Author: chendi
 * @Description:
 * @Date: 2020/8/6 11:42
 * @Version: 1.0
 */
@Component
public class DemoService implements VertxInitializer {

    @Autowired
    private myclient a;

    @Override
    public void init() {
        if (a != null){
            HashMap<Object, Object> map = new HashMap<>();
            map.put("1","c");
            System.out.println(Thread.currentThread().getName());
            System.out.println("----" + a.test(map).getA());
        }else {
            System.out.println("null null");
        }

    }

}