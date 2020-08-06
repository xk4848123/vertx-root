package com.wanke.client;

import com.wanke.common.annotion.RequestMapping;
import com.wanke.common.annotion.VertxClient;
import com.wanke.pojo.User;

import java.util.Map;

/**
 * @Author: chendi
 * @Description:
 * @Date: 2020/8/5 15:50
 * @Version: 1.0
 */
@VertxClient
@RequestMapping("device")
public interface myclient {

    @RequestMapping("getdevice")
    public User test(Map map);


}