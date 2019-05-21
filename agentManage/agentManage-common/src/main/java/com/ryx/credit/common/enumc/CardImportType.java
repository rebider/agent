package com.ryx.credit.common.enumc;

import java.util.HashMap;
import java.util.Map;

/**
 * 导入类型
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/12/4 18:11
 */
public enum CardImportType {

    A("1","流量卡卡号"),
    B("2","供应商发卡汇总"),
    C("3","历史北京总部发卡"),
    D("4","退货转发数据"),
    E("5","流量卡状态");

    public String code;

    public String msg;

    CardImportType(String c, String m){
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
        CardImportType[] status = CardImportType.values();
        for(CardImportType cc : status){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    public static Map<String, Object> getSelectMap(){
        Map<String, Object> resultMap = new HashMap<>();
        CardImportType[] status = CardImportType.values();
        for(CardImportType cc : status){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

    public static String getContentCodeByValue(String value){
        if(CardImportType.A.code.equals(value)){
            return "InternetCardModel3";
        }
        if(CardImportType.B.code.equals(value)){
            return "InternetCardModel1";
        }
        if(CardImportType.C.code.equals(value)){
            return "InternetCardModel2";
        }
        if(CardImportType.D.code.equals(value)){
            return "InternetCardModel5";
        }
        if(CardImportType.E.code.equals(value)){
            return "InternetCardModel4";
        }
        return "";
    }
}
