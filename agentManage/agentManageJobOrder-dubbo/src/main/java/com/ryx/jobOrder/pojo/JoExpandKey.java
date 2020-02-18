package com.ryx.jobOrder.pojo;

import java.math.BigDecimal;

public class JoExpandKey {
    private String jid;

    private String joKeyId;

    private String joKey;

    private String joKeyValueType;

    private String joKeyName;

    private String joKeyValue;

    private BigDecimal joExpandSort;

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid == null ? null : jid.trim();
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

    public String getJoKeyName() {
        return joKeyName;
    }

    public void setJoKeyName(String joKeyName) {
        this.joKeyName = joKeyName == null ? null : joKeyName.trim();
    }

    public String getJoKeyValue() {
        return joKeyValue;
    }

    public void setJoKeyValue(String joKeyValue) {
        this.joKeyValue = joKeyValue == null ? null : joKeyValue.trim();
    }

    public BigDecimal getJoExpandSort() {
        return joExpandSort;
    }

    public void setJoExpandSort(BigDecimal joExpandSort) {
        this.joExpandSort = joExpandSort;
    }
}