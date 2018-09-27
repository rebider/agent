package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AssProtoColRel extends AssProtoColRelKey implements Serializable {
    private String remark;

    private String cUser;

    private Date cTime;

    private BigDecimal status;

    private String protocolRule;

    private String protocolRuleValue;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public String getProtocolRule() {
        return protocolRule;
    }

    public void setProtocolRule(String protocolRule) {
        this.protocolRule = protocolRule == null ? null : protocolRule.trim();
    }

    public String getProtocolRuleValue() {
        return protocolRuleValue;
    }

    public void setProtocolRuleValue(String protocolRuleValue) {
        this.protocolRuleValue = protocolRuleValue == null ? null : protocolRuleValue.trim();
    }
}