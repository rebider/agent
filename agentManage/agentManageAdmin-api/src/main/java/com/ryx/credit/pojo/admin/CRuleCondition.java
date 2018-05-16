package com.ryx.credit.pojo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CRuleCondition implements Serializable {
    private BigDecimal id;

    private BigDecimal ruleId;

    private BigDecimal conditionValueId;

    private Date updateTime;

    private String ruleName;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getRuleId() {
        return ruleId;
    }

    public void setRuleId(BigDecimal ruleId) {
        this.ruleId = ruleId;
    }

    public BigDecimal getConditionValueId() {
        return conditionValueId;
    }

    public void setConditionValueId(BigDecimal conditionValueId) {
        this.conditionValueId = conditionValueId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}