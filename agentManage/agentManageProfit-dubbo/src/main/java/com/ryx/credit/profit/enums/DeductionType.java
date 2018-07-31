package com.ryx.credit.profit.enums;

/**
 * @author zhaodw
 * @Title: StagingDetailStatus
 * @ProjectName agentManage
 * @Description: 分期状态
 * @date 2018/7/2516:08
 */
public enum DeductionType {
    //退单，未审核，审核中，通过，不通过
    SETTLE_ERR("01");

    private String type;
    DeductionType(String type) {
        this.type = type;
    }
    public String getType() {
        return this.type;
    }
}
