package com.wanke.configuration;

import com.wanke.common.annotion.Autowired;
import com.wanke.common.annotion.VertxBean;
import com.wanke.common.annotion.VertxConfiguration;
import com.wanke.pojo.Name;
import com.wanke.pojo.User;
import com.wanke.service.DemoService;

/**
 * @Author: chendi
 * @Description:
 * @Date: 2020/8/7 15:02
 * @Version: 1.0
 */
@VertxConfiguration
public class TestConfiguration {


    @Autowired
    private DemoService demoService;

    @VertxBean
    public User getUser(){
        User user = new User();
        user.setA("我的");
        user.setName(getName());
        return user;
    }


    @VertxBean
    public Name getName(){
        Name name = new Name();
        System.out.println(2);
        name.setValue("5544");
        return name;
    }


}