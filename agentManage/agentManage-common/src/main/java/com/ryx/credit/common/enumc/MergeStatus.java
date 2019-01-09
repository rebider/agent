package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/1/8 18:02
 * @Param
 * @return
 **/
public enum MergeStatus {

    WXS(new BigDecimal("0"),"未生效"),
    SX(new BigDecimal("1"),"已生效");

    public BigDecimal code;

    public String msg;

    MergeStatus(BigDecimal c, String m){
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


}
