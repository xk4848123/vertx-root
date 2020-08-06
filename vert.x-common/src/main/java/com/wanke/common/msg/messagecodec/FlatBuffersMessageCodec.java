package com.wanke.common.msg.messagecodec;

import com.wanke.common.msg.flat.FlatBuffersCommonMsg;
import com.wanke.common.msg.messagecodec.util.FlatBuffersUtil;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

/**
 * @Author: Administrator
 * @Description:
 * @Date: 2020/7/22 15:36
 * @Version: 1.0
 */
public class FlatBuffersMessageCodec implements MessageCodec<FlatBuffersCommonMsg, FlatBuffersCommonMsg> {


    @Override
    public void encodeToWire(Buffer buffer, FlatBuffersCommonMsg flatBuffersCommonMsg) {
        buffer.appendBytes(FlatBuffersUtil.serialize(flatBuffersCommonMsg));
    }

    @Override
    public FlatBuffersCommonMsg decodeFromWire(int pos, Buffer buffer) {
        return FlatBuffersUtil.deserialize(buffer.getBytes(pos, buffer.length()));
    }

    @Override
    public FlatBuffersCommonMsg transform(FlatBuffersCommonMsg flatBuffersCommonMsg) {
        return null;
    }

    @Override
    public String name() {
        return "myFlatCodec";
    }

    @Override
    public byte systemCodecID() {
        return -1;
    }
}