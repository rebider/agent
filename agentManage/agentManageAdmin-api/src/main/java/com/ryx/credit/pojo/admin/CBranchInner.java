package com.ryx.credit.pojo.admin;

import java.math.BigDecimal;
import java.util.Date;

public class CBranchInner {
    private String id;

    private String branchLogin;

    private String innerLogin;

    private String cUserId;

    private String branchName;

    private Date cTime;

    private BigDecimal status;

    private String cUserName;

    private String innerName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBranchLogin() {
        return branchLogin;
    }

    public void setBranchLogin(String branchLogin) {
        this.branchLogin = branchLogin == null ? null : branchLogin.trim();
    }

    public String getInnerLogin() {
        return innerLogin;
    }

    public void setInnerLogin(String innerLogin) {
        this.innerLogin = innerLogin == null ? null : innerLogin.trim();
    }

    public String getcUserId() {
        return cUserId;
    }

    public void setcUserId(String cUserId) {
        this.cUserId = cUserId == null ? null : cUserId.trim();
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
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

    public String getcUserName() {
        return cUserName;
    }

    public void setcUserName(String cUserName) {
        this.cUserName = cUserName == null ? null : cUserName.trim();
    }

    public String getInnerName() {
        return innerName;
    }

    public void setInnerName(String innerName) {
        this.innerName = innerName == null ? null : innerName.trim();
    }
}