package com.wanke.client;

import com.wanke.common.annotion.BusMapping;
import com.wanke.common.annotion.VertxClient;
import com.wanke.pojo.User;

/**
 * @Author: chendi
 * @Description:
 * @Date: 2020/8/5 15:50
 * @Version: 1.0
 */
@VertxClient
@BusMapping("device")
public interface myclient {

    @BusMapping("getdevice")
    public User test(User msg);

    @BusMapping("world.get")
    public User test0(User msg);
}