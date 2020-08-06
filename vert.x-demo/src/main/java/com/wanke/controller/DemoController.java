package com.wanke.controller;

import com.wanke.common.annotion.Controller;
import com.wanke.common.annotion.RequestMapping;
import com.wanke.common.msg.msghandle.WrapMsg;
import com.wanke.pojo.User;

import java.util.Map;

@Controller
@RequestMapping("device")
public class DemoController {

    @RequestMapping("getdevice")
    public User get(WrapMsg msg){
        //获取传参
        Map body = msg.body();
        System.out.println(body);
        //返回结果
        User user = new User();
        user.setA("adf32");
        user.setB(2);
        return user;
    }


//    @RequestMapping("postdevice")
//    public ResultDTO result(WrapMsg msg){
//        //获取传参
//        Map body = msg.body();
//        //返回结果
//        return ResultUtil.getSuccess("1",null);
//    }

}
