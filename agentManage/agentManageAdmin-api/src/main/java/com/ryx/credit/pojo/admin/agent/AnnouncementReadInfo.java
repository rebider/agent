package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AnnouncementReadInfo implements Serializable {
    private String id;

    private String annId;

    private String annTitle;

    private BigDecimal annType;

    private Date readTm;

    private String readUserId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAnnId() {
        return annId;
    }

    public void setAnnId(String annId) {
        this.annId = annId == null ? null : annId.trim();
    }

    public String getAnnTitle() {
        return annTitle;
    }

    public void setAnnTitle(String annTitle) {
        this.annTitle = annTitle == null ? null : annTitle.trim();
    }

    public BigDecimal getAnnType() {
        return annType;
    }

    public void setAnnType(BigDecimal annType) {
        this.annType = annType;
    }

    public Date getReadTm() {
        return readTm;
    }

    public void setReadTm(Date readTm) {
        this.readTm = readTm;
    }

    public String getReadUserId() {
        return readUserId;
    }

    public void setReadUserId(String readUserId) {
        this.readUserId = readUserId == null ? null : readUserId.trim();
    }
}