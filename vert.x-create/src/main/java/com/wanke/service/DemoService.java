package com.wanke.service;

import com.wanke.client.myclient;
import com.wanke.common.annotion.Autowired;
import com.wanke.common.annotion.VertxComponent;
import com.wanke.common.ioc.VertxInitializer;
import com.wanke.pojo.Name;
import com.wanke.pojo.User;

/**
 * @Author: chendi
 * @Description:
 * @Date: 2020/8/6 11:42
 * @Version: 1.0
 */
@VertxComponent
public class DemoService implements VertxInitializer {

    @Autowired
    private myclient a;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private User myUser;

    @Autowired
    private Name name;

    @Override
    public void init() {
        if (a != null){
            System.out.println("-----");
            System.out.println(myUser.getName());
            System.out.println(name);
            System.out.println("-----");
            cacheService.test();
        }else {
            System.out.println("null null");
        }

    }

}