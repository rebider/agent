package com.ryx.jobOrder.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class JoKeyManage implements Serializable{
    private String id;

    private String joKeyType;

    private String joKey;

    private String joKeyName;

    private String joKeyValueType;

    private String joStatus;

    private String joKeyBackNum;

    private BigDecimal joKeySort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJoKeyType() {
        return joKeyType;
    }

    public void setJoKeyType(String joKeyType) {
        this.joKeyType = joKeyType == null ? null : joKeyType.trim();
    }

    public String getJoKey() {
        return joKey;
    }

    public void setJoKey(String joKey) {
        this.joKey = joKey == null ? null : joKey.trim();
    }

    public String getJoKeyName() {
        return joKeyName;
    }

    public void setJoKeyName(String joKeyName) {
        this.joKeyName = joKeyName == null ? null : joKeyName.trim();
    }

    public String getJoKeyValueType() {
        return joKeyValueType;
    }

    public void setJoKeyValueType(String joKeyValueType) {
        this.joKeyValueType = joKeyValueType == null ? null : joKeyValueType.trim();
    }

    public String getJoStatus() {
        return joStatus;
    }

    public void setJoStatus(String joStatus) {
        this.joStatus = joStatus == null ? null : joStatus.trim();
    }

    public String getJoKeyBackNum() {
        return joKeyBackNum;
    }

    public void setJoKeyBackNum(String joKeyBackNum) {
        this.joKeyBackNum = joKeyBackNum == null ? null : joKeyBackNum.trim();
    }

    public BigDecimal getJoKeySort() {
        return joKeySort;
    }

    public void setJoKeySort(BigDecimal joKeySort) {
        this.joKeySort = joKeySort;
    }
}