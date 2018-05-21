package com.ryx.credit.pojo.admin.agent;

import java.math.BigDecimal;
import java.util.Date;

public class AssProtoCol {
    private String id;

    private String platform;

    private String protocolDes;

    private String protocolRule;

    private Date cTime;

    private Date cStart;

    private Date cEnd;

    private BigDecimal cSort;

    private BigDecimal protocolStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getProtocolDes() {
        return protocolDes;
    }

    public void setProtocolDes(String protocolDes) {
        this.protocolDes = protocolDes == null ? null : protocolDes.trim();
    }

    public String getProtocolRule() {
        return protocolRule;
    }

    public void setProtocolRule(String protocolRule) {
        this.protocolRule = protocolRule == null ? null : protocolRule.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getcStart() {
        return cStart;
    }

    public void setcStart(Date cStart) {
        this.cStart = cStart;
    }

    public Date getcEnd() {
        return cEnd;
    }

    public void setcEnd(Date cEnd) {
        this.cEnd = cEnd;
    }

    public BigDecimal getcSort() {
        return cSort;
    }

    public void setcSort(BigDecimal cSort) {
        this.cSort = cSort;
    }

    public BigDecimal getProtocolStatus() {
        return protocolStatus;
    }

    public void setProtocolStatus(BigDecimal protocolStatus) {
        this.protocolStatus = protocolStatus;
    }
}