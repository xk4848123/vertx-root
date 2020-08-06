package com.wanke.common.msg.messagecodec;

import com.wanke.common.msg.messagecodec.util.ProtostuffUtils;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;
import com.wanke.common.msg.proto.ProtoCommonMsg;

/**
 * 自定义对象编解码器,两个类型可用于消息转换,即发送对象转换为接受需要的对象
 */
public class ProtoMessageCodec implements MessageCodec<ProtoCommonMsg, ProtoCommonMsg> {
    /**
     * 将消息实体封装到Buffer用于传输
     * 实现方式：使用对象流从对象中获取Byte数组然后追加到Buffer
     */
    @Override
    public void encodeToWire(Buffer buffer, ProtoCommonMsg protoCommonMsg) {
        buffer.appendBytes(ProtostuffUtils.serialize(protoCommonMsg));


    }
    //从Buffer中获取消息对象
    @Override
    public ProtoCommonMsg decodeFromWire(int pos, Buffer buffer) {
       return ProtostuffUtils.deserialize(buffer.getBytes(pos, buffer.length()), ProtoCommonMsg.class);
    }

    @Override
    public ProtoCommonMsg transform(ProtoCommonMsg protoMessage) {
        return null;
    }
    //消息转换

    @Override
    public String name() { return "myProtoCodec"; }
    //识别是否是用户自定义编解码器,通常为-1
    @Override
    public byte systemCodecID() { return -1; }

}