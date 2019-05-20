package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;

public class OInternetCardMerch implements Serializable{
    private String chnTermposi;

    private String chnMerchId;

    private String merchName;

    private String tranDate;

    private String tranTime;

    public String getChnTermposi() {
        return chnTermposi;
    }

    public void setChnTermposi(String chnTermposi) {
        this.chnTermposi = chnTermposi == null ? null : chnTermposi.trim();
    }

    public String getChnMerchId() {
        return chnMerchId;
    }

    public void setChnMerchId(String chnMerchId) {
        this.chnMerchId = chnMerchId == null ? null : chnMerchId.trim();
    }

    public String getMerchName() {
        return merchName;
    }

    public void setMerchName(String merchName) {
        this.merchName = merchName == null ? null : merchName.trim();
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate == null ? null : tranDate.trim();
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime == null ? null : tranTime.trim();
    }
}