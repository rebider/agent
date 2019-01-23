package com.ryx.credit.profit.enums;

/**
 * @author zhaodw
 * @Title: StagingDetailStatus
 * @ProjectName agentManage
 * @Description: 分期状态
 * @date 2018/7/2516:08
 */
public enum DeductionType {
    //01退单，02机具,03其他,04 考核扣款,05 考核扣款（系统计算不能清除）06 代理商合并扣款
    SETTLE_ERR("01"),MACHINE("02"),OTHER("03"),POS_REWARD_DEDUCT("04"),POS_REWARD_CALCU_DEDUCT("05"),MERGE("06");

    private String type;
    DeductionType(String type) {
        this.type = type;
    }
    public String getType() {
        return this.type;
    }
}
