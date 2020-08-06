package com.wanke.common.msg.messagecodec.util;

import com.google.flatbuffers.FlatBufferBuilder;
import com.wanke.common.msg.flat.FlatBuffersCommonMsg;
import com.wanke.common.msg.flat.Flist;
import com.wanke.common.msg.flat.Fmap;

import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @Author: Administrator
 * @Description: 序列化和反序列化FlatBuffersCommonMsg类型对象
 * @Date: 2020/7/22 15:47
 * @Version: 1.0
 */
public class FlatBuffersUtil {

    public static byte[] serialize(FlatBuffersCommonMsg msg) {
        FlatBufferBuilder builder = new FlatBufferBuilder(1024);
        Map<String, String> map = msg.getMap();
        int[] list = new int[map.size()];
        int count = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            list[count] = Fmap.createFmap(builder, builder.createString(entry.getKey()), builder.createString(entry.getValue()));
            count ++;
        }
        int data = Flist.createDatasVector(builder, list);
        Flist.startFlist(builder);
        Flist.addDatas(builder, data);
        int orc = Flist.endFlist(builder);
        builder.finish(orc);
        byte[] buf = builder.sizedByteArray();
        return buf;

    }

    public static FlatBuffersCommonMsg deserialize(byte[] buf) {
        //模拟从获取到二进制数据 进行反序列化对象
        ByteBuffer buffer = ByteBuffer.wrap(buf);
        //根据该二进制数据列生成Flist对象
        Flist flist = Flist.getRootAsFlist(buffer);
        int length = flist.datasLength();
        FlatBuffersCommonMsg flatBuffersCommonMsg = new FlatBuffersCommonMsg();
        for (int i = 0; i < length; i++) {
            flatBuffersCommonMsg.put(flist.datas(i).key(), flist.datas(i).value());
        }
        return flatBuffersCommonMsg;
    }
}