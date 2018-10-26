package com.ryx.credit.common.enumc;


import java.util.HashMap;
import java.util.Map;

/**
 * 分润出款状态
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/9/17 9:11
 */
public enum TransFlag {

    YSP("1","已生批"),
    WCL("0","未处理"),
    DKSB("2","打款失败-已重试"),
    FHTG("3","复合通过"),
    FXLJ("4","风险拦截"),
    YCX("5","已撤销"),
    FHZ("7","复合中"),
    FHBTG("8","复合不通过"),
    YHCLCG("9","银行处理成功"),
    CG("11","打款成功"),
    SB("12","打款失败"),
    YHCLSB("20","银行处理失败");


    public String code;

    public String msg;

    TransFlag(String c, String m){
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
        TransFlag[] transFlag = TransFlag.values();
        for(TransFlag cc : transFlag){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

    /**
     * 返回map
     * @return
     */
    public static Map<String,Object> getItemMap(){
        TransFlag[] valus = TransFlag.values();
        Map<String,Object> resultMap = new HashMap<>();
        for (TransFlag transFlag : valus) {
            resultMap.put(transFlag.getValue(),transFlag.getContent());
        }
        return resultMap;
    }

}
