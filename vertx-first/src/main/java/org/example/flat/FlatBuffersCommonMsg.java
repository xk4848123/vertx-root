package org.example.flat;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Administrator
 * @Description:
 * @Date: 2020/7/22 15:40
 * @Version: 1.0
 */
public class FlatBuffersCommonMsg {

    private Map<String,String> data = new HashMap();

    public Map<String,String> getMap(){
        return this.data;
    }
    public void put(String key,String value){
        data.put(key,value);
    }
    public String get(String key){
        return  data.get(key);
    }
    public String toString(){
        return data.toString();
    }
}