package com.ryx.credit.common.enumc;

/**
 * @Author Lihl
 * @Date 2018/08/06
 * POS奖励申请状态
 */
public enum RewardStatus {
    //9：新建; 0：申请中; 1：生效; 2：无效;
    NOUVEAU("9"),REVIEWING("0"),PASS("1"),UN_PASS("2");

    private String status;
    RewardStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }

}
