package com.ryx.credit.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by RYX on 2018/4/12.
 */
public class FastMap extends HashMap {
    public static FastMap fastMap(String key,Object val){
        return new FastMap().putKeyV(key,val);
    }

    public static FastMap fastSuccessMap(){
        return new FastMap().putKeyV("msg","成功").putKeyV("code","0000");
    }
    public static FastMap fastSuccessMap(String msg){
        return new FastMap().putKeyV("msg",msg).putKeyV("code","0000");
    }
    public static FastMap fastSuccessMap(Map<String,Object> map){
        FastMap fastMap = new FastMap().putKeyV("msg", "成功").putKeyV("code", "0000");
        for (String result : map.keySet()) {
            fastMap.putKeyV(result,map.get(result));
        }
        return fastMap;
    }
    public static FastMap fastFailMap(){
        return new FastMap().putKeyV("msg","失败").putKeyV("code","1000");
    }
    public static FastMap fastFailMap(String msg){
        return new FastMap().putKeyV("msg",msg).putKeyV("code","1000");
    }
    public static FastMap fastFailMap(Map<String,Object> map){
        FastMap fastMap = new FastMap().putKeyV("msg", "失败").putKeyV("code", "1000");
        for (String result : map.keySet()) {
            fastMap.putKeyV(result,map.get(result));
        }
        return fastMap;
    }
    public static boolean isSuc(Map map){
        if(null != map && "0000".equals(map.get("code"))){
            return true;
        }
        return false;
    }
    public  FastMap putData(Object val){
        this.put("data",val);
        return this;
    }
    public  FastMap putKeyV(String key,Object val){
        this.put(key,val);
        return this;
    }

    public  FastMap putKeyV(Map<String,Object> resultMap){
        for (String result : resultMap.keySet()) {
            this.put(result,resultMap.get(result));
        }
        return this;
    }

    public static FastMap mapToFastMap(Map<String,Object> resultMap){
        FastMap fastMap = new FastMap();
        for (String result : resultMap.keySet()) {
            fastMap.putKeyV(result,resultMap.get(result));
        }
        return fastMap;
    }
}
