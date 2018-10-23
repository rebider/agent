package com.ryx.credit.profit.pojo;

import java.io.Serializable;

public class ProfitAdjustM implements Serializable{
    private String id;

    private String adjustMainId;

    private String adjustMainName;

    private String adjustSubId;

    private String adjustSubName;

    private String adjustMtype;

    private String adjustAmt;

    private String adjustDate;

    private String adjustContent;

    private String adjustPerson;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAdjustMainId() {
        return adjustMainId;
    }

    public void setAdjustMainId(String adjustMainId) {
        this.adjustMainId = adjustMainId == null ? null : adjustMainId.trim();
    }

    public String getAdjustMainName() {
        return adjustMainName;
    }

    public void setAdjustMainName(String adjustMainName) {
        this.adjustMainName = adjustMainName == null ? null : adjustMainName.trim();
    }

    public String getAdjustSubId() {
        return adjustSubId;
    }

    public void setAdjustSubId(String adjustSubId) {
        this.adjustSubId = adjustSubId == null ? null : adjustSubId.trim();
    }

    public String getAdjustSubName() {
        return adjustSubName;
    }

    public void setAdjustSubName(String adjustSubName) {
        this.adjustSubName = adjustSubName == null ? null : adjustSubName.trim();
    }

    public String getAdjustMtype() {
        return adjustMtype;
    }

    public void setAdjustMtype(String adjustMtype) {
        this.adjustMtype = adjustMtype == null ? null : adjustMtype.trim();
    }

    public String getAdjustAmt() {
        return adjustAmt;
    }

    public void setAdjustAmt(String adjustAmt) {
        this.adjustAmt = adjustAmt == null ? null : adjustAmt.trim();
    }

    public String getAdjustDate() {
        return adjustDate;
    }

    public void setAdjustDate(String adjustDate) {
        this.adjustDate = adjustDate == null ? null : adjustDate.trim();
    }

    public String getAdjustContent() {
        return adjustContent;
    }

    public void setAdjustContent(String adjustContent) {
        this.adjustContent = adjustContent == null ? null : adjustContent.trim();
    }

    public String getAdjustPerson() {
        return adjustPerson;
    }

    public void setAdjustPerson(String adjustPerson) {
        this.adjustPerson = adjustPerson == null ? null : adjustPerson.trim();
    }
}