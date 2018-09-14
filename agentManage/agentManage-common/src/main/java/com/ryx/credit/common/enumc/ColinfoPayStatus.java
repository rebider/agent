package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 收款账户状态
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/4/17 18:11
 */
public enum ColinfoPayStatus {

    A(1, "未处理"),
    B(2, "待验证"),
    C(3, "验证成功"),
    D(4, "验证失败");

    public BigDecimal code;

    public String msg;

    ColinfoPayStatus(int c, String m){
        this.code=new BigDecimal(c);
        this.msg =m;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public BigDecimal getValue() {
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
    public static String getContentByValue(BigDecimal value){
        ColinfoPayStatus[] payStatus = ColinfoPayStatus.values();
        for(ColinfoPayStatus cc : payStatus){
            if(cc.code.compareTo(value)==0){
                return cc.msg;
            }
        }
        return "";
    }

    /**
     * 获取key、Value map
     * @return
     */
    public static Map<BigDecimal,String> getKeyValueMap(){
        Map<BigDecimal,String> resultMap = new HashMap<>();
        ColinfoPayStatus[] payStatus = ColinfoPayStatus.values();
        for(ColinfoPayStatus cc : payStatus){
            resultMap.put(cc.code,cc.msg);
        }
        return resultMap;
    }

}
