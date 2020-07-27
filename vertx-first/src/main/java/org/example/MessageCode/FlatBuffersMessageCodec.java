package org.example.MessageCode;

import com.google.flatbuffers.FlatBufferBuilder;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import org.example.flat.FlatBuffersCommonMsg;
import org.example.proto.ProtoCommonMsg;
import org.example.util.FlatBuffersUtil;
import org.example.util.ProtostuffUtils;

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