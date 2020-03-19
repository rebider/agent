package com.ryx.account.pojo;

import java.io.Serializable;
import java.util.Date;

public class AuthLoginToken implements Serializable{
    private String id;

    private String authCode;

    private String logName;

    private String passWord;

    private String platformType;

    private String token;

    private Date tokenBeginTime;

    private Date tokenEndTime;

    private String requestId;

    private String status;

    private String busInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName == null ? null : logName.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType == null ? null : platformType.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getTokenBeginTime() {
        return tokenBeginTime;
    }

    public void setTokenBeginTime(Date tokenBeginTime) {
        this.tokenBeginTime = tokenBeginTime;
    }

    public Date getTokenEndTime() {
        return tokenEndTime;
    }

    public void setTokenEndTime(Date tokenEndTime) {
        this.tokenEndTime = tokenEndTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId == null ? null : requestId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getBusInfo() {
        return busInfo;
    }

    public void setBusInfo(String busInfo) {
        this.busInfo = busInfo == null ? null : busInfo.trim();
    }
}