package com.ryx.credit.common.enumc;


/**
 * @Auther: chenliang
 * @Date: 2019/3/20
 * @Description:扣款状态
 */
public enum DeductionSumStatus {
    Deduction_for("Deduction_for","已汇总待抵扣"),
    Deduction_lok("Deduction_lok","抵扣锁定中"),
    Deduction_Finish("Deduction_Finish","抵扣完成");
    public String code;

    public String msg;

    DeductionSumStatus(String c, String m){
        this.code=c;
        this.msg =m;
    }
}
