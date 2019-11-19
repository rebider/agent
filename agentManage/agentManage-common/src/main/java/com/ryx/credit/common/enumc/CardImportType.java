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
    E("5","流量卡状态"),
    F("6","物联网卡续费信息"),
    G("7","批量延期"),

    AV("0.0.1","流量卡卡号版本"),
    BV("0.0.1","供应商发卡汇总版本"),
    CV("0.0.1","历史北京总部发卡版本"),
    DV("0.0.1","退货转发数据版本"),
    EV("0.0.1","流量卡状态版本"),
    FV("0.0.1","物联网卡续费信息版本"),
    GV("0.0.2","批量延期版本");


    public String code;

    public String msg;

    CardImportType(String c, String m){
        this.code=c;
        this.msg =m;
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
            if(!cc.code.equals(CardImportType.F.getValue()) && !cc.code.contains("0.0."))
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

    public static void main(String[] args){
        System.out.println(getSelectMap())    ;
    }


    public static String getContentCodeByValue(String value){
        if(CardImportType.A.code.equals(value)){
            return "InternetCardModel3_"+AV.getValue();
        }else if(CardImportType.B.code.equals(value)){
            return "InternetCardModel1_"+BV.getValue();
        }else if(CardImportType.C.code.equals(value)){
            return "InternetCardModel2_"+CV.getValue();
        }else if(CardImportType.D.code.equals(value)){
            return "InternetCardModel5_"+DV.getValue();
        }else if(CardImportType.E.code.equals(value)){
            return "InternetCardModel4_"+EV.getValue();
        }else if(CardImportType.G.code.equals(value)){
            return "InternetCardModel6_"+GV.getValue();
        }
        return "";
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
}
