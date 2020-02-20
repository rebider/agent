package com.ryx.jobOrder.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class JoOrder {
    private String id;

    private String agId;

    private String agName;

    private String busId;

    private String busNum;

    private String paltformNum;

    private String platformName;

    private String joFirstKeyNum;

    private String joSecondKeyNum;

    private String priorityLevel;

    private Date launchTime;

    private String launchPersonId;

    private String launchPersonName;

    private String launchPersonEmail;

    private String launchBranchId;

    private String launchBranchName;

    private String acceptGroupCode;

    private String acceptGroup;

    private String acceptNowGroup;

    private Date dealTimeStart;

    private Date dealTimeEnd;

    private BigDecimal dealTimeLength;

    private String joProgress;

    private String joTitle;

    private String joContent;

    private BigDecimal joAssessLevel;

    private String joAssessorName;

    private String joAssessorType;

    private String joAssessorId;

    private String joAssessContent;

    private Date joAssessTime;

    private BigDecimal version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAgId() {
        return agId;
    }

    public void setAgId(String agId) {
        this.agId = agId == null ? null : agId.trim();
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName == null ? null : agName.trim();
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId == null ? null : busId.trim();
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum == null ? null : busNum.trim();
    }

    public String getPaltformNum() {
        return paltformNum;
    }

    public void setPaltformNum(String paltformNum) {
        this.paltformNum = paltformNum == null ? null : paltformNum.trim();
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName == null ? null : platformName.trim();
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

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel == null ? null : priorityLevel.trim();
    }

    public Date getLaunchTime() {
        return launchTime;
    }

    public void setLaunchTime(Date launchTime) {
        this.launchTime = launchTime;
    }

    public String getLaunchPersonId() {
        return launchPersonId;
    }

    public void setLaunchPersonId(String launchPersonId) {
        this.launchPersonId = launchPersonId == null ? null : launchPersonId.trim();
    }

    public String getLaunchPersonName() {
        return launchPersonName;
    }

    public void setLaunchPersonName(String launchPersonName) {
        this.launchPersonName = launchPersonName == null ? null : launchPersonName.trim();
    }

    public String getLaunchPersonEmail() {
        return launchPersonEmail;
    }

    public void setLaunchPersonEmail(String launchPersonEmail) {
        this.launchPersonEmail = launchPersonEmail == null ? null : launchPersonEmail.trim();
    }

    public String getLaunchBranchId() {
        return launchBranchId;
    }

    public void setLaunchBranchId(String launchBranchId) {
        this.launchBranchId = launchBranchId == null ? null : launchBranchId.trim();
    }

    public String getLaunchBranchName() {
        return launchBranchName;
    }

    public void setLaunchBranchName(String launchBranchName) {
        this.launchBranchName = launchBranchName == null ? null : launchBranchName.trim();
    }

    public String getAcceptGroupCode() {
        return acceptGroupCode;
    }

    public void setAcceptGroupCode(String acceptGroupCode) {
        this.acceptGroupCode = acceptGroupCode == null ? null : acceptGroupCode.trim();
    }

    public String getAcceptGroup() {
        return acceptGroup;
    }

    public void setAcceptGroup(String acceptGroup) {
        this.acceptGroup = acceptGroup == null ? null : acceptGroup.trim();
    }

    public String getAcceptNowGroup() {
        return acceptNowGroup;
    }

    public void setAcceptNowGroup(String acceptNowGroup) {
        this.acceptNowGroup = acceptNowGroup == null ? null : acceptNowGroup.trim();
    }

    public Date getDealTimeStart() {
        return dealTimeStart;
    }

    public void setDealTimeStart(Date dealTimeStart) {
        this.dealTimeStart = dealTimeStart;
    }

    public Date getDealTimeEnd() {
        return dealTimeEnd;
    }

    public void setDealTimeEnd(Date dealTimeEnd) {
        this.dealTimeEnd = dealTimeEnd;
    }

    public BigDecimal getDealTimeLength() {
        return dealTimeLength;
    }

    public void setDealTimeLength(BigDecimal dealTimeLength) {
        this.dealTimeLength = dealTimeLength;
    }

    public String getJoProgress() {
        return joProgress;
    }

    public void setJoProgress(String joProgress) {
        this.joProgress = joProgress == null ? null : joProgress.trim();
    }

    public String getJoTitle() {
        return joTitle;
    }

    public void setJoTitle(String joTitle) {
        this.joTitle = joTitle == null ? null : joTitle.trim();
    }

    public String getJoContent() {
        return joContent;
    }

    public void setJoContent(String joContent) {
        this.joContent = joContent == null ? null : joContent.trim();
    }

    public BigDecimal getJoAssessLevel() {
        return joAssessLevel;
    }

    public void setJoAssessLevel(BigDecimal joAssessLevel) {
        this.joAssessLevel = joAssessLevel;
    }

    public String getJoAssessorName() {
        return joAssessorName;
    }

    public void setJoAssessorName(String joAssessorName) {
        this.joAssessorName = joAssessorName == null ? null : joAssessorName.trim();
    }

    public String getJoAssessorType() {
        return joAssessorType;
    }

    public void setJoAssessorType(String joAssessorType) {
        this.joAssessorType = joAssessorType == null ? null : joAssessorType.trim();
    }

    public String getJoAssessorId() {
        return joAssessorId;
    }

    public void setJoAssessorId(String joAssessorId) {
        this.joAssessorId = joAssessorId == null ? null : joAssessorId.trim();
    }

    public String getJoAssessContent() {
        return joAssessContent;
    }

    public void setJoAssessContent(String joAssessContent) {
        this.joAssessContent = joAssessContent == null ? null : joAssessContent.trim();
    }

    public Date getJoAssessTime() {
        return joAssessTime;
    }

    public void setJoAssessTime(Date joAssessTime) {
        this.joAssessTime = joAssessTime;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }
}