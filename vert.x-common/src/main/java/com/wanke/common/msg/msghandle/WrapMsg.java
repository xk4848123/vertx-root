package com.wanke.common.msg.msghandle;

import com.wanke.common.config.VertxConfig;
import com.wanke.common.msg.flat.FlatBuffersCommonMsg;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import com.wanke.common.msg.proto.ProtoCommonMsg;

import java.util.Map;

public class WrapMsg {

    private Message innerMsg;

    private Map req_dataMap;

    public void  setInnerMsg(Message innerMsg) {
        this.innerMsg = innerMsg;

    }

    public Object request(Map request_dataMap){
        if (VertxConfig.getFormat().equals("json")){
            return new JsonObject(request_dataMap);
        }else if(VertxConfig.getFormat().equals("proto")){
            return new ProtoCommonMsg(request_dataMap);
        }else {
            return new FlatBuffersCommonMsg(request_dataMap);
        }
    }

    public void reply(Map reply_dataMap){
        Object reply_data = null;
        if (VertxConfig.getFormat().equals("json")){
            reply_data = new JsonObject(reply_dataMap);
        }else if(VertxConfig.getFormat().equals("proto")){
            reply_data = new ProtoCommonMsg(reply_dataMap);
        }else {
            reply_data = new FlatBuffersCommonMsg(reply_dataMap);
        }
        innerMsg.reply(reply_data,VertxConfig.getOptions());
    }

    public Map body() {
        if (this.innerMsg != null){
            if (VertxConfig.getFormat().equals("json")){
                req_dataMap = ((JsonObject) this.innerMsg.body()).getMap();
            }else if(VertxConfig.getFormat().equals("proto")){
                req_dataMap = ((ProtoCommonMsg) this.innerMsg.body()).getMap();
            }else {
                req_dataMap = ((FlatBuffersCommonMsg) this.innerMsg.body()).getMap();
            }
        }
        return req_dataMap;
    }

    @Override
    public String toString() {
        return "WrapMsg{" +
                "req_dataMap=" + req_dataMap +
                '}';
    }
}
