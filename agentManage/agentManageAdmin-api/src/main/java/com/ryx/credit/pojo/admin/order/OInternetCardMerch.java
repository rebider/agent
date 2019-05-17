package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;

public class OInternetCardMerch implements Serializable{
    private String iccid;

    private String merchId;

    private String merchName;

    private String internetCardNum;

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId == null ? null : merchId.trim();
    }

    public String getMerchName() {
        return merchName;
    }

    public void setMerchName(String merchName) {
        this.merchName = merchName == null ? null : merchName.trim();
    }

    public String getInternetCardNum() {
        return internetCardNum;
    }

    public void setInternetCardNum(String internetCardNum) {
        this.internetCardNum = internetCardNum == null ? null : internetCardNum.trim();
    }
}