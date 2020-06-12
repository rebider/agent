package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.FreezeRequest;

public class FreeRequestVo extends FreezeRequest {
    private String isApproveWhenSubmit;
    private String taskId;
    private String approvalOpinion;
    private String approvalResult;
    private String dept;
    private String sid;

    public String getIsApproveWhenSubmit() {
        return isApproveWhenSubmit;
    }

    public void setIsApproveWhenSubmit(String isApproveWhenSubmit) {
        this.isApproveWhenSubmit = isApproveWhenSubmit;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getApprovalOpinion() {
        return approvalOpinion;
    }

    public void setApprovalOpinion(String approvalOpinion) {
        this.approvalOpinion = approvalOpinion;
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }
}
