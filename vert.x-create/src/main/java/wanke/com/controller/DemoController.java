package wanke.com.controller;

import wanke.com.common.annotion.Controller;
import wanke.com.common.annotion.RequestMapping;
import wanke.com.common.msghandle.WrapMsg;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("device")
public class DemoController {


    @RequestMapping("getdevice")
    public Map get(WrapMsg msg){

        System.out.println(msg);
        HashMap data = new HashMap();
        data.put("a","1");
        return data;
    }
}
