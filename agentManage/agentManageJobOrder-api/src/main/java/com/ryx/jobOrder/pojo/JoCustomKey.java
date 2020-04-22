package com.ryx.jobOrder.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

public class JoCustomKey implements Serializable{
    private String id;

    private String joFirstKeyNum;

    private String joSecondKeyNum;

    private String joKeyId;

    private String joKey;

    private String joKeyValueType;

    private String joKeyNull;

    private BigDecimal joKeySort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJoFirstKeyNum() {
        return joFirstKeyNum;
    }

    public void setJoFirstKeyNum(String joFirstKeyNum) {
        this.joFirstKeyNum = joFirstKeyNum == null ? null : joFirstKeyNum.trim();
    }

    public String getJoSecondKeyNum() {
        return joSecondKeyNum;
    }

    public void setJoSecondKeyNum(String joSecondKeyNum) {
        this.joSecondKeyNum = joSecondKeyNum == null ? null : joSecondKeyNum.trim();
    }

    public String getJoKeyId() {
        return joKeyId;
    }

    public void setJoKeyId(String joKeyId) {
        this.joKeyId = joKeyId == null ? null : joKeyId.trim();
    }

    public String getJoKey() {
        return joKey;
    }

    public void setJoKey(String joKey) {
        this.joKey = joKey == null ? null : joKey.trim();
    }

    public String getJoKeyValueType() {
        return joKeyValueType;
    }

    public void setJoKeyValueType(String joKeyValueType) {
        this.joKeyValueType = joKeyValueType == null ? null : joKeyValueType.trim();
    }

    public String getJoKeyNull() {
        return joKeyNull;
    }

    public void setJoKeyNull(String joKeyNull) {
        this.joKeyNull = joKeyNull == null ? null : joKeyNull.trim();
    }

    public BigDecimal getJoKeySort() {
        return joKeySort;
    }

    public void setJoKeySort(BigDecimal joKeySort) {
        this.joKeySort = joKeySort;
    }
}