package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.order.ORefundPriceDiff;

import java.io.Serializable;

public class ORefundPriceDiffVo extends ORefundPriceDiff implements Serializable{

    private String gatherTimeStr;

    private String applyBeginTime;

    private String applyEndTime;

    public String getGatherTimeStr() {
        return gatherTimeStr;
    }

    public void setGatherTimeStr(String gatherTimeStr) {
        this.gatherTimeStr = gatherTimeStr;
    }

    public String getApplyBeginTime() {
        return applyBeginTime;
    }

    public void setApplyBeginTime(String applyBeginTime) {
        this.applyBeginTime = applyBeginTime;
    }

    public String getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
    }
}