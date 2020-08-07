package com.wanke.common.msg.msghandle;

import com.wanke.common.config.VertxConfig;
import com.wanke.common.msg.flat.FlatBuffersCommonMsg;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import com.wanke.common.msg.proto.ProtoCommonMsg;

import java.util.HashMap;
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

    public void reply(Object reply_data){
        Map reply_wrap = new HashMap();
        reply_wrap.put(VertxConfig.getMsgKey(), reply_data);
        Object final_return = null;
        if (VertxConfig.getFormat().equals("json")){
            final_return = new JsonObject(reply_wrap);
        }else if(VertxConfig.getFormat().equals("proto")){
            final_return = new ProtoCommonMsg(reply_wrap);
        }else {
            final_return = new FlatBuffersCommonMsg(reply_wrap);
        }
        innerMsg.reply(final_return,VertxConfig.getOptions());
    }

    public <T> T body() {
        if (this.innerMsg != null){
            if (VertxConfig.getFormat().equals("json")){
                req_dataMap = ((JsonObject) this.innerMsg.body()).getMap();
            }else if(VertxConfig.getFormat().equals("proto")){
                req_dataMap = ((ProtoCommonMsg) this.innerMsg.body()).getMap();
            }else {
                req_dataMap = ((FlatBuffersCommonMsg) this.innerMsg.body()).getMap();
            }
        }
        return (T) req_dataMap.get(VertxConfig.getMsgKey());
    }

    public static Map newMap(Object o){
        Map requestMap = new HashMap();
        requestMap.put(VertxConfig.getMsgKey(),o);
        return requestMap;
    }


    @Override
    public String toString() {
        return "WrapMsg{" +
                "req_dataMap=" + req_dataMap +
                '}';
    }
}
