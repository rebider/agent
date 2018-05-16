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
    public static FastMap fastFailMap(){
        return new FastMap().putKeyV("msg","失败").putKeyV("code","1000");
    }
    public static FastMap fastFailMap(String msg){
        return new FastMap().putKeyV("msg",msg).putKeyV("code","1000");
    }
    public static boolean isSuc(Map map){
        if(null != map && "0000".equals(map.get("code"))){
            return true;
        }
        return false;
    }

    public  FastMap putKeyV(String key,Object val){
        this.put(key,val);
        return this;
    }
}
