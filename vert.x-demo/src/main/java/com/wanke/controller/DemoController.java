package com.wanke.controller;

import com.wanke.common.annotion.Controller;
import com.wanke.common.annotion.RequestMapping;
import com.wanke.common.msg.msghandle.WrapMsg;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("device")
public class DemoController {

    @RequestMapping("getdevice")
    public Map get(WrapMsg msg){
        //获取传参
        Map body = msg.body();
        System.out.println(body);
        //返回结果
        HashMap data = new HashMap();
        data.put("a","1");
        return data;
    }


//    @RequestMapping("postdevice")
//    public ResultDTO result(WrapMsg msg){
//        //获取传参
//        Map body = msg.body();
//        //返回结果
//        return ResultUtil.getSuccess("1",null);
//    }

}
