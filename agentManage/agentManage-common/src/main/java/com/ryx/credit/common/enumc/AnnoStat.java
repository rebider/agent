package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

public enum AnnoStat {
    //0-待发布，1-发布中,2-取消发布,3-删除
    WAIT(new BigDecimal("0"),"待发布"),
    PUB(new BigDecimal("0"),"待发布"),
    UNPB(new BigDecimal("0"),"待发布"),
    DEL(new BigDecimal("0"),"待发布");

    public BigDecimal code;

    public String msg;

    AnnoStat(java.math.BigDecimal c, String m){
        this.code = c;
        this.msg = m;
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
    public static String getContentByValue(BigDecimal value) {
        AnnoStat[] annoStats = AnnoStat.values();
        for (AnnoStat cc : annoStats) {
            if(cc.code.compareTo(value)==0){
                return cc.msg;
            }
        }
        return "";
    }
}
