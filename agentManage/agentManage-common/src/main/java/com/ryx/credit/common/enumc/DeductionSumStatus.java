package com.ryx.credit.common.enumc;


/**
 * @Auther: chenliang
 * @Date: 2019/3/20
 * @Description:扣款状态
 */
public enum DeductionSumStatus {
    Deduction_for("1","已汇总待抵扣"),
    Deduction_lok("2","抵扣锁定中"),
    Deduction_Finish("3","抵扣完成");
    public String code;

    public String msg;

    DeductionSumStatus(String c, String m){
        this.code=c;
        this.msg =m;
    }
}
