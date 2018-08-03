package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.order.ORefundPriceDiff;

import java.io.Serializable;

public class ORefundPriceDiffVo extends ORefundPriceDiff implements Serializable{

    private String gatherTimeStr;

    public String getGatherTimeStr() {
        return gatherTimeStr;
    }

    public void setGatherTimeStr(String gatherTimeStr) {
        this.gatherTimeStr = gatherTimeStr;
    }
}