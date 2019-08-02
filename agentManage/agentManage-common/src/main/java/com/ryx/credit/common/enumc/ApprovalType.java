package com.ryx.credit.common.enumc;

import java.util.HashMap;
import java.util.Map;

/**
 * 工作流审批结果
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum ApprovalType {

    PASS("pass","通过"),
    REJECT("reject","拒绝"),
    CANCEL("cancel","撤销"),
    BACK("back","退回");

    public String code;

    public String msg;

    ApprovalType(String c, String m){
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
        ApprovalType[] fundType = ApprovalType.values();
        for(ApprovalType cc : fundType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static Map<String,Object> getContentMap(){
        Map<String,Object> resultMap = new HashMap<>();
        ApprovalType[] fundType = ApprovalType.values();
        for(ApprovalType cc : fundType){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

}
