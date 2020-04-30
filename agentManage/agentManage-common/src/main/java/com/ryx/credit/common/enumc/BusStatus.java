package com.ryx.credit.common.enumc;

import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * @Auther: lrr
 * @Date: 2019/8/16 10:58
 * @Description:
 */
public enum BusStatus {
    WQY(0,"未启用"),QY(1,"启用"),WJH(2,"未激活"),SD(3,"锁定"),YWTC(4,"业务已退出"),TZQ(5,"退出直签"),YWQY(6,"业务已迁移");

    public BigDecimal status;

    public String  msg;
    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public BigDecimal getValue() {
        return this.status;
    }
    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }
    BusStatus(int status,String s){
        this.status = new BigDecimal(status);
        msg = s;
    }


    public static String getAgStatusString(BigDecimal s){
        if(s==null)return null;
        for (BusStatus busStatus : BusStatus.values()) {
            if(busStatus.status.compareTo(s)==0){
                return busStatus.name();
            }
        }
        return "";
    }

    public static BigDecimal getAgStatusString(String s){
        if(StringUtils.isEmpty(s))return null;
        for (BusStatus busStatus : BusStatus.values()) {
            if(busStatus.name().equals(s)){
                return busStatus.status;
            }
        }
        return new BigDecimal(-1);
    }

    public static String getAgStatusByValue(String s){
        if(StringUtils.isEmpty(s))return null;
        for (BusStatus busStatus : BusStatus.values()) {
            if(busStatus.name().equals(s)){
                return busStatus.msg;
            }
        }
        return "";
    }
    public static String getMsg(BigDecimal s){
        if(s==null)return null;
        for (BusStatus busStatus : BusStatus.values()) {
            if(busStatus.status.compareTo(s)==0){
                return busStatus.msg;
            }
        }
        return "";
    }

    public static List<BigDecimal> getAvbList(){
        return Arrays.asList(BusStatus.QY.status,BusStatus.WJH.status);
    }
}
