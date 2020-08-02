package wanke.com.common.msghandle;

import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import wanke.com.common.config.VertxConfig;
import wanke.com.common.flat.FlatBuffersCommonMsg;
import wanke.com.common.proto.ProtoCommonMsg;

import java.util.Map;

public class WrapMsg {

    private Message innerMsg;

    private Map req_dataMap;

    public void setInnerMsg(Message innerMsg) {
        this.innerMsg = innerMsg;
        if (this.innerMsg != null){
           if (VertxConfig.getFormat().equals("json")){
               req_dataMap = ((JsonObject) this.innerMsg.body()).getMap();
           }else if(VertxConfig.getFormat().equals("proto")){
               req_dataMap = ((ProtoCommonMsg) this.innerMsg.body()).getMap();
           }else {
               req_dataMap = ((FlatBuffersCommonMsg) this.innerMsg.body()).getMap();
           }
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
        return req_dataMap;
    }

    @Override
    public String toString() {
        return "WrapMsg{" +
                "req_dataMap=" + req_dataMap +
                '}';
    }
}
