package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;

public class CashSummaryMouthKey implements Serializable{
    private String busid;

    private String dayStr;

    private String payType;

    private String isCloInvoice;

    public String getBusid() {
        return busid;
    }

    public void setBusid(String busid) {
        this.busid = busid == null ? null : busid.trim();
    }

    public String getDayStr() {
        return dayStr;
    }

    public void setDayStr(String dayStr) {
        this.dayStr = dayStr == null ? null : dayStr.trim();
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getIsCloInvoice() {
        return isCloInvoice;
    }

    public void setIsCloInvoice(String isCloInvoice) {
        this.isCloInvoice = isCloInvoice == null ? null : isCloInvoice.trim();
    }
}