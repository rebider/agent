package com.ryx.credit.pojo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CStrategyRule implements Serializable {
    private BigDecimal id;

    private BigDecimal ruleId;

    private BigDecimal strategyId;

    private Date updateTime;

    private String ruleName;

    private String strategyName;

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
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

    public BigDecimal getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(BigDecimal strategyId) {
        this.strategyId = strategyId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}