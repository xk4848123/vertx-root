package wanke.com.common.proto;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Administrator
 * @Description:
 * @Date: 2020/7/22 8:32
 * @Version: 1.0
 */
public class ProtoCommonMsg {

  private Map data;

  public ProtoCommonMsg(){
      data = new HashMap();
  }

  public ProtoCommonMsg(Map map){
      data = map;
  }
  public void put(Object key,Object value){
      data.put(key,value);
  }
  public Object get(Object key){
      return  data.get(key);
  }
  public <T> T get(Object key, Class<T> clazz){
        return  (T) data.get(key);
  }
  public String toString(){
      return data.toString();
  }
  public Map getMap(){ return data; }
}