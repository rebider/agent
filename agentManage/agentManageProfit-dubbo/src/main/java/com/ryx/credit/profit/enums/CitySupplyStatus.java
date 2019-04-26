package com.ryx.credit.profit.enums;

import com.ryx.credit.common.enumc.PaymentType;

/**
 * @author chenliang
 * @Title: StagingDetailStatus
 * @ProjectName agentManage
 * @Description: 省区补款状态
 * @date 2019/4/12
 */
public enum CitySupplyStatus {

    STATUS_00("00","省区补扣/上级代扣申请中"),
    STATUS_01("01","省区补扣/上级代扣申请失败"),
    STATUS_02("02","省区补扣/上级代扣申请成功");


    public String code;

    public String msg;

    CitySupplyStatus(String c, String m){
        this.code=c;
        this.msg =m;
    }
    public static String getValue(String value){
        PaymentType[] paymentType = PaymentType.values();
        for(PaymentType cc : paymentType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }

}
