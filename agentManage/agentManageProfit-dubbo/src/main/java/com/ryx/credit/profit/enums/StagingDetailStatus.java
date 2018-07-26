package com.ryx.credit.profit.enums;

/**
 * @author zhaodw
 * @Title: StagingDetailStatus
 * @ProjectName agentManage
 * @Description: TODO
 * @date 2018/7/2516:08
 */
public enum StagingDetailStatus {
    N("0"),Y("1");

    private String status;
    StagingDetailStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }
}
