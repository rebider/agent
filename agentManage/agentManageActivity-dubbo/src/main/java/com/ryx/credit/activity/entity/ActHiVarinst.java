package com.ryx.credit.activity.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ActHiVarinst implements Serializable{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActHiVarinst that = (ActHiVarinst) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (procInstId != null ? !procInstId.equals(that.procInstId) : that.procInstId != null) return false;
        if (executionId != null ? !executionId.equals(that.executionId) : that.executionId != null) return false;
        if (taskId != null ? !taskId.equals(that.taskId) : that.taskId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (varType != null ? !varType.equals(that.varType) : that.varType != null) return false;
        if (rev != null ? !rev.equals(that.rev) : that.rev != null) return false;
        if (bytearrayId != null ? !bytearrayId.equals(that.bytearrayId) : that.bytearrayId != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (text2 != null ? !text2.equals(that.text2) : that.text2 != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        return !(lastUpdatedTime != null ? !lastUpdatedTime.equals(that.lastUpdatedTime) : that.lastUpdatedTime != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (procInstId != null ? procInstId.hashCode() : 0);
        result = 31 * result + (executionId != null ? executionId.hashCode() : 0);
        result = 31 * result + (taskId != null ? taskId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (varType != null ? varType.hashCode() : 0);
        result = 31 * result + (rev != null ? rev.hashCode() : 0);
        result = 31 * result + (bytearrayId != null ? bytearrayId.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (text2 != null ? text2.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (lastUpdatedTime != null ? lastUpdatedTime.hashCode() : 0);
        return result;
    }

    private Object id;

    private Object procInstId;

    private Object executionId;

    private Object taskId;

    private Object name;

    private Object varType;

    private BigDecimal rev;

    private Object bytearrayId;



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