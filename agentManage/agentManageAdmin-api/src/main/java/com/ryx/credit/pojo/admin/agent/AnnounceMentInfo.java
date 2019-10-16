package com.ryx.credit.pojo.admin.agent;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ryx.credit.common.util.DateJsonDeserializer;
import com.ryx.credit.common.util.DateJsonSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class AnnounceMentInfo implements Serializable {
    private String annId;

    private String annTitle;

    private BigDecimal annType;

    @JSONField(format="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date effectTm;

    @JSONField(format="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date expireTm;

    private BigDecimal annoStat;

    private Date pubTm;

    private String pubOrg;

    private String publisher;

    private String upStatUser;

    @JSONField(format="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date upStatTm;

    @JSONField(format="yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @JsonDeserialize(using = DateJsonDeserializer.class)
    @JsonSerialize(using = DateJsonSerializer.class)
    private Date createTm;

    private String createUser;

    private String annInfo;

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

    public Date getEffectTm() {
        return effectTm;
    }

    public void setEffectTm(Date effectTm) {
        this.effectTm = effectTm;
    }

    public Date getExpireTm() {
        return expireTm;
    }

    public void setExpireTm(Date expireTm) {
        this.expireTm = expireTm;
    }

    public BigDecimal getAnnoStat() {
        return annoStat;
    }

    public void setAnnoStat(BigDecimal annoStat) {
        this.annoStat = annoStat;
    }

    public Date getPubTm() {
        return pubTm;
    }

    public void setPubTm(Date pubTm) {
        this.pubTm = pubTm;
    }

    public String getPubOrg() {
        return pubOrg;
    }

    public void setPubOrg(String pubOrg) {
        this.pubOrg = pubOrg == null ? null : pubOrg.trim();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    public String getUpStatUser() {
        return upStatUser;
    }

    public void setUpStatUser(String upStatUser) {
        this.upStatUser = upStatUser == null ? null : upStatUser.trim();
    }

    public Date getUpStatTm() {
        return upStatTm;
    }

    public void setUpStatTm(Date upStatTm) {
        this.upStatTm = upStatTm;
    }

    public Date getCreateTm() {
        return createTm;
    }

    public void setCreateTm(Date createTm) {
        this.createTm = createTm;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getAnnInfo() {
        return annInfo;
    }

    public void setAnnInfo(String annInfo) {
        this.annInfo = annInfo == null ? null : annInfo.trim();
    }
}