package com.ryx.credit.profit.enums;

/**
 * @author zhaodw
 * @Title: StagingDetailStatus
 * @ProjectName agentManage
 * @Description: 分期状态
 * @date 2018/7/2516:08
 */
public enum DeductionType {
    //退单，机具,其他
    SETTLE_ERR("01"),MACHINE("02"),OTHER("03"),POS_REWARD_DEDUCT("04");

    private String type;
    DeductionType(String type) {
        this.type = type;
    }
    public String getType() {
        return this.type;
    }
}
