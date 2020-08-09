package com.wanke.controller;

import com.wanke.common.annotion.BusController;
import com.wanke.common.annotion.BusMapping;
import com.wanke.common.msg.msghandle.WrapMsg;
import com.wanke.pojo.User;

@BusController
@BusMapping("device")
public class DemoController {

    //推荐方式
    @BusMapping("world.get")
    public User get(User msg){
        //获取传参
        System.out.println(msg.getA());
        //返回结果
        User user = new User();
        user.setA("adf32");
        user.setB(2);
        return user;
    }


    @BusMapping("postdevice.get")
    public void result(WrapMsg msg){
        //获取传参
        User user = msg.body();
        System.out.println(user.getA());
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //返回结果
        User user1 = new User();
        user1.setA("response");
        user1.setB(2);
        msg.reply(user1);
    }

}
