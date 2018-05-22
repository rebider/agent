package com.ryx.credit.activity.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ActHiVarinst {
    private Object id;

    private Object procInstId;

    private Object executionId;

    private Object taskId;

    private Object name;

    private Object varType;

    private BigDecimal rev;

    private Object bytearrayId;

    private BigDecimal double;

    private BigDecimal long;

    private Object text;

    private Object text2;

    private Date createTime;

    private Date lastUpdatedTime;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(Object procInstId) {
        this.procInstId = procInstId;
    }

    public Object getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Object executionId) {
        this.executionId = executionId;
    }

    public Object getTaskId() {
        return taskId;
    }

    public void setTaskId(Object taskId) {
        this.taskId = taskId;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getVarType() {
        return varType;
    }

    public void setVarType(Object varType) {
        this.varType = varType;
    }

    public BigDecimal getRev() {
        return rev;
    }

    public void setRev(BigDecimal rev) {
        this.rev = rev;
    }

    public Object getBytearrayId() {
        return bytearrayId;
    }

    public void setBytearrayId(Object bytearrayId) {
        this.bytearrayId = bytearrayId;
    }

    public BigDecimal getDouble() {
        return double;
    }

    public void setDouble(BigDecimal double) {
        this.double = double;
    }

    public BigDecimal getLong() {
        return long;
    }

    public void setLong(BigDecimal long) {
        this.long = long;
    }

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }

    public Object getText2() {
        return text2;
    }

    public void setText2(Object text2) {
        this.text2 = text2;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
}