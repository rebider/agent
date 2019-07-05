package com.ryx.credit.common.enumc;


import com.ryx.credit.common.util.JsonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口通知类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum NotifyType {

    /**
     * 数据库存Key
     */
    NetInAdd("netInAdd","新增入网"),
    NetInAddBus("NetInAddBus","入网新增业务"),
    NetInEdit("netInEdit","入网修改"),
    NetInUpgrade("netInUpgrade","入网升级"),
    IdentityAuth("identityAuth","身份认证"),
    ThreeElements("ThreeElements","三要素认证"),
    AgentMerge("agentMerge","代理商合并"),
    AgentQuit("agentQuit","代理商退出");

    public String code;

    public String msg;

    NotifyType(String c, String m){
        this.code=c;
        this.msg =m;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public String getValue() {
        return this.code;
    }
    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }

    /**
     * 根据值获取内容
     * @param value
     * @return
     */
    public static String getContentByValue(String value){
        NotifyType[] notifyType = NotifyType.values();
        for(NotifyType cc : notifyType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    /**
     * 获取key、Value map
     * @return
     */
    public static Map<String,Object> getKeyValueMap(){
        Map<String,Object> resultMap = new HashMap<>();
        NotifyType[] notifyType = NotifyType.values();
        for(NotifyType cc : notifyType){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

    /**
     * 获取key、Value json
     * @return
     */
    public static String getKeyValueJson(){
        List<Map<String,Object>> resultList = new ArrayList<>();
        NotifyType[] notifyType = NotifyType.values();
        for(NotifyType cc : notifyType){
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("key",cc.code);
            resultMap.put("value",cc.msg);
            resultList.add(resultMap);
        }
        String resultJson = JsonUtil.objectToJson(resultList);
        return resultJson;
    }
}
