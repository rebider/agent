package com.ryx.account.pojo;

import java.util.Date;

public class AuthSysCode {
    private String id;

    private String platformType;

    private Date authCodeBeginTime;

    private Date authCodeEndTime;

    private String authCode;

    private String serverIp;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType == null ? null : platformType.trim();
    }

    public Date getAuthCodeBeginTime() {
        return authCodeBeginTime;
    }

    public void setAuthCodeBeginTime(Date authCodeBeginTime) {
        this.authCodeBeginTime = authCodeBeginTime;
    }

    public Date getAuthCodeEndTime() {
        return authCodeEndTime;
    }

    public void setAuthCodeEndTime(Date authCodeEndTime) {
        this.authCodeEndTime = authCodeEndTime;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp == null ? null : serverIp.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}