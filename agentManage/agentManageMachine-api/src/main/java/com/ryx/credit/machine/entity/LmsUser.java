package com.ryx.credit.machine.entity;

import java.io.Serializable;
import java.util.Date;

public class LmsUser implements Serializable {
    private String id;

    private String loginname;

    private String name;

    private String password;

    private String status;

    private String roleId;

    private String empId;

    private String deptId;

    private String organId;

    private String userType;

    private String phone;

    private String post;

    private Date createTm;

    private Date updateTm;

    private Date pwdUpdateTm;

    private String createOrgId;

    private String ueflag;

    private String userScope;

    private String smsAuthSwitch;

    private String sysType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId == null ? null : roleId.trim();
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId == null ? null : empId.trim();
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getOrganId() {
        return organId;
    }

    public void setOrganId(String organId) {
        this.organId = organId == null ? null : organId.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public Date getCreateTm() {
        return createTm;
    }

    public void setCreateTm(Date createTm) {
        this.createTm = createTm;
    }

    public Date getUpdateTm() {
        return updateTm;
    }

    public void setUpdateTm(Date updateTm) {
        this.updateTm = updateTm;
    }

    public Date getPwdUpdateTm() {
        return pwdUpdateTm;
    }

    public void setPwdUpdateTm(Date pwdUpdateTm) {
        this.pwdUpdateTm = pwdUpdateTm;
    }

    public String getCreateOrgId() {
        return createOrgId;
    }

    public void setCreateOrgId(String createOrgId) {
        this.createOrgId = createOrgId == null ? null : createOrgId.trim();
    }

    public String getUeflag() {
        return ueflag;
    }

    public void setUeflag(String ueflag) {
        this.ueflag = ueflag == null ? null : ueflag.trim();
    }

    public String getUserScope() {
        return userScope;
    }

    public void setUserScope(String userScope) {
        this.userScope = userScope == null ? null : userScope.trim();
    }

    public String getSmsAuthSwitch() {
        return smsAuthSwitch;
    }

    public void setSmsAuthSwitch(String smsAuthSwitch) {
        this.smsAuthSwitch = smsAuthSwitch == null ? null : smsAuthSwitch.trim();
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType == null ? null : sysType.trim();
    }
}