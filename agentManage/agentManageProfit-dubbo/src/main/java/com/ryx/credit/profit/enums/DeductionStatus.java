package com.ryx.credit.profit.enums;

/**
 * @author zhaodw
 * @Title: StagingDetailStatus
 * @ProjectName agentManage
 * @Description: 分期状态
 * @date 2018/7/2516:08
 */
public enum DeductionStatus {
    //未申请，未审核，审核中，通过，不通过
    NOT_APPLIED("0"),UNREVIEWED("1"),REVIEWING("2"),PASS("3"),UN_PASS("4");

    private String status;
    DeductionStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }
}
