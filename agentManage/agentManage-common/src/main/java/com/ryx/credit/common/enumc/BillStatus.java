package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * @Author chenliang
 * @Date 2020/4/22 9:48
 * @Version 1.0
 * 出款状态
 */
public enum BillStatus {

    WCK("01","未出款"),
    CKCG("02","出款成功"),
    CKSB("03","出款失败"),
    SPZ("04","审批中"),
    SPSB("05","审批失败"),
    CKZ("06","出款中"),
    FRTF("07","分润停发"),
    FRDF("08","分润代发");


    public  String key;

    public String msg;

    BillStatus(  String key, String m){
        this.key = key;
        this.msg = m;
    }

    /**
     * 根据值获取内容
     * @param key
     * @return
     */
    public static String getContentByValue(String  key) {
        BillStatus[] billStatuses = BillStatus.values();
        for (BillStatus cc : billStatuses) {
            if(cc.key.equals(key)){
                return cc.msg;
            }
        }
        return "";
    }
}
