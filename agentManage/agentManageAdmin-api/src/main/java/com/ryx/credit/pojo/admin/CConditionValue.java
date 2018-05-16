package com.ryx.credit.pojo.admin;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CConditionValue implements Serializable {
    private BigDecimal id;

    private BigDecimal conditionId;

    private Object operate;

    private Object value;

    private BigDecimal operator;

    private Date createTime;

    private Date updateTime;

    private Object name;

    private String conditionName;

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public BigDecimal getConditionId() {
        return conditionId;
    }

    public void setConditionId(BigDecimal conditionId) {
        this.conditionId = conditionId;
    }

    public Object getOperate() {
        return operate;
    }

    public void setOperate(Object operate) {
        this.operate = operate;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public BigDecimal getOperator() {
        return operator;
    }

    public void setOperator(BigDecimal operator) {
        this.operator = operator;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public enum Operate {
        EQ, LT, GT, LE, GE, NEQ;
    }

    public static Map<Operate, String> getOperatorMap() {
        Map<Operate, String> hashMap = new HashMap<Operate, String>();
        hashMap.put(Operate.EQ, "等于");
        hashMap.put(Operate.LT, "小于");
        hashMap.put(Operate.GT, "大于");
        hashMap.put(Operate.LE, "小于等于");
        hashMap.put(Operate.GE, "大于等于");
        hashMap.put(Operate.NEQ, "不等于");
        return hashMap;
    }
}