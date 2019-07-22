package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * 订单结算-分润形式
 * Created by lhl on 2019/7/18.
 */
public enum ProfitForm {

    RFR(new BigDecimal("1"), "日分润"),
    RFX(new BigDecimal("2"), "日返现"),
    XJDK(new BigDecimal("3"), "下级代扣");


    public BigDecimal key;
    public String msg;

    ProfitForm(BigDecimal k, String s){
        key = k;
        msg = s;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public BigDecimal getValue() {
        return this.key;
    }
    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }


    public static String getContentByValue(BigDecimal value){
        ProfitForm[] busType = ProfitForm.values();
        for(ProfitForm bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static BigDecimal getValueByContent(String value){
        ProfitForm[] busType = ProfitForm.values();
        for(ProfitForm bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

}
