package com.ryx.credit.activity.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ActRuTask implements Serializable{

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActRuTask)) return false;

        ActRuTask actRuTask = (ActRuTask) o;

        if (getId() != null ? !getId().equals(actRuTask.getId()) : actRuTask.getId() != null) return false;
        if (getRev() != null ? !getRev().equals(actRuTask.getRev()) : actRuTask.getRev() != null) return false;
        if (getExecutionId() != null ? !getExecutionId().equals(actRuTask.getExecutionId()) : actRuTask.getExecutionId() != null)
            return false;
        if (getProcInstId() != null ? !getProcInstId().equals(actRuTask.getProcInstId()) : actRuTask.getProcInstId() != null)
            return false;
        if (getProcDefId() != null ? !getProcDefId().equals(actRuTask.getProcDefId()) : actRuTask.getProcDefId() != null)
            return false;
        if (getName() != null ? !getName().equals(actRuTask.getName()) : actRuTask.getName() != null) return false;
        if (getParentTaskId() != null ? !getParentTaskId().equals(actRuTask.getParentTaskId()) : actRuTask.getParentTaskId() != null)
            return false;
        if (getDescription() != null ? !getDescription().equals(actRuTask.getDescription()) : actRuTask.getDescription() != null)
            return false;
        if (getTaskDefKey() != null ? !getTaskDefKey().equals(actRuTask.getTaskDefKey()) : actRuTask.getTaskDefKey() != null)
            return false;
        if (getOwner() != null ? !getOwner().equals(actRuTask.getOwner()) : actRuTask.getOwner() != null) return false;
        if (getAssignee() != null ? !getAssignee().equals(actRuTask.getAssignee()) : actRuTask.getAssignee() != null)
            return false;
        if (getDelegation() != null ? !getDelegation().equals(actRuTask.getDelegation()) : actRuTask.getDelegation() != null)
            return false;
        if (getPriority() != null ? !getPriority().equals(actRuTask.getPriority()) : actRuTask.getPriority() != null)
            return false;
        if (getCreateTime() != null ? !getCreateTime().equals(actRuTask.getCreateTime()) : actRuTask.getCreateTime() != null)
            return false;
        if (getDueDate() != null ? !getDueDate().equals(actRuTask.getDueDate()) : actRuTask.getDueDate() != null)
            return false;
        if (getCategory() != null ? !getCategory().equals(actRuTask.getCategory()) : actRuTask.getCategory() != null)
            return false;
        if (getSuspensionState() != null ? !getSuspensionState().equals(actRuTask.getSuspensionState()) : actRuTask.getSuspensionState() != null)
            return false;
        if (getTenantId() != null ? !getTenantId().equals(actRuTask.getTenantId()) : actRuTask.getTenantId() != null)
            return false;
        if (getFormKey() != null ? !getFormKey().equals(actRuTask.getFormKey()) : actRuTask.getFormKey() != null)
            return false;
        return !(getClaimTime() != null ? !getClaimTime().equals(actRuTask.getClaimTime()) : actRuTask.getClaimTime() != null);

    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getRev() != null ? getRev().hashCode() : 0);
        result = 31 * result + (getExecutionId() != null ? getExecutionId().hashCode() : 0);
        result = 31 * result + (getProcInstId() != null ? getProcInstId().hashCode() : 0);
        result = 31 * result + (getProcDefId() != null ? getProcDefId().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getParentTaskId() != null ? getParentTaskId().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getTaskDefKey() != null ? getTaskDefKey().hashCode() : 0);
        result = 31 * result + (getOwner() != null ? getOwner().hashCode() : 0);
        result = 31 * result + (getAssignee() != null ? getAssignee().hashCode() : 0);
        result = 31 * result + (getDelegation() != null ? getDelegation().hashCode() : 0);
        result = 31 * result + (getPriority() != null ? getPriority().hashCode() : 0);
        result = 31 * result + (getCreateTime() != null ? getCreateTime().hashCode() : 0);
        result = 31 * result + (getDueDate() != null ? getDueDate().hashCode() : 0);
        result = 31 * result + (getCategory() != null ? getCategory().hashCode() : 0);
        result = 31 * result + (getSuspensionState() != null ? getSuspensionState().hashCode() : 0);
        result = 31 * result + (getTenantId() != null ? getTenantId().hashCode() : 0);
        result = 31 * result + (getFormKey() != null ? getFormKey().hashCode() : 0);
        result = 31 * result + (getClaimTime() != null ? getClaimTime().hashCode() : 0);
        return result;
    }

    private Object id;

    private BigDecimal rev;

    private Object executionId;

    private Object procInstId;

    private Object procDefId;

    private Object name;

    private Object parentTaskId;

    private Object description;

    private Object taskDefKey;

    private Object owner;

    private Object assignee;

    private Object delegation;

    private BigDecimal priority;

    private Date createTime;

    private Date dueDate;

    private Object category;

    private BigDecimal suspensionState;

    private Object tenantId;

    private Object formKey;

    private Date claimTime;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public BigDecimal getRev() {
        return rev;
    }

    public void setRev(BigDecimal rev) {
        this.rev = rev;
    }

    public Object getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Object executionId) {
        this.executionId = executionId;
    }

    public Object getProcInstId() {
        return procInstId;
    }

    public void setProcInstId(Object procInstId) {
        this.procInstId = procInstId;
    }

    public Object getProcDefId() {
        return procDefId;
    }

    public void setProcDefId(Object procDefId) {
        this.procDefId = procDefId;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getParentTaskId() {
        return parentTaskId;
    }

    public void setParentTaskId(Object parentTaskId) {
        this.parentTaskId = parentTaskId;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getTaskDefKey() {
        return taskDefKey;
    }

    public void setTaskDefKey(Object taskDefKey) {
        this.taskDefKey = taskDefKey;
    }

    public Object getOwner() {
        return owner;
    }

    public void setOwner(Object owner) {
        this.owner = owner;
    }

    public Object getAssignee() {
        return assignee;
    }

    public void setAssignee(Object assignee) {
        this.assignee = assignee;
    }

    public Object getDelegation() {
        return delegation;
    }

    public void setDelegation(Object delegation) {
        this.delegation = delegation;
    }

    public BigDecimal getPriority() {
        return priority;
    }

    public void setPriority(BigDecimal priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Object getCategory() {
        return category;
    }

    public void setCategory(Object category) {
        this.category = category;
    }

    public BigDecimal getSuspensionState() {
        return suspensionState;
    }

    public void setSuspensionState(BigDecimal suspensionState) {
        this.suspensionState = suspensionState;
    }

    public Object getTenantId() {
        return tenantId;
    }

    public void setTenantId(Object tenantId) {
        this.tenantId = tenantId;
    }

    public Object getFormKey() {
        return formKey;
    }

    public void setFormKey(Object formKey) {
        this.formKey = formKey;
    }

    public Date getClaimTime() {
        return claimTime;
    }

    public void setClaimTime(Date claimTime) {
        this.claimTime = claimTime;
    }
}