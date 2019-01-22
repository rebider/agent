package com.ryx.credit.pojo.admin.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class TerminalTransferDetail implements Serializable{
    private String id;

    private String terminalTransferId;

    private String snBeginNum;

    private String snEndNum;

    private String proCom;

    private String proModel;

    private String originalOrgId;

    private String originalOrgName;

    private String originalBusId;

    private String goalOrgId;

    private String goalOrgName;

    private String goalBusId;

    private Date adjustTime;

    private BigDecimal snCount;

    private String buttJointPerson;

    private BigDecimal adjustStatus;

    private String remark;

    private String batchNum;

    private String agentId;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private BigDecimal platformType;

    private String adjustMsg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTerminalTransferId() {
        return terminalTransferId;
    }

    public void setTerminalTransferId(String terminalTransferId) {
        this.terminalTransferId = terminalTransferId == null ? null : terminalTransferId.trim();
    }

    public String getSnBeginNum() {
        return snBeginNum;
    }

    public void setSnBeginNum(String snBeginNum) {
        this.snBeginNum = snBeginNum == null ? null : snBeginNum.trim();
    }

    public String getSnEndNum() {
        return snEndNum;
    }

    public void setSnEndNum(String snEndNum) {
        this.snEndNum = snEndNum == null ? null : snEndNum.trim();
    }

    public String getProCom() {
        return proCom;
    }

    public void setProCom(String proCom) {
        this.proCom = proCom == null ? null : proCom.trim();
    }

    public String getProModel() {
        return proModel;
    }

    public void setProModel(String proModel) {
        this.proModel = proModel == null ? null : proModel.trim();
    }

    public String getOriginalOrgId() {
        return originalOrgId;
    }

    public void setOriginalOrgId(String originalOrgId) {
        this.originalOrgId = originalOrgId == null ? null : originalOrgId.trim();
    }

    public String getOriginalOrgName() {
        return originalOrgName;
    }

    public void setOriginalOrgName(String originalOrgName) {
        this.originalOrgName = originalOrgName == null ? null : originalOrgName.trim();
    }

    public String getOriginalBusId() {
        return originalBusId;
    }

    public void setOriginalBusId(String originalBusId) {
        this.originalBusId = originalBusId == null ? null : originalBusId.trim();
    }

    public String getGoalOrgId() {
        return goalOrgId;
    }

    public void setGoalOrgId(String goalOrgId) {
        this.goalOrgId = goalOrgId == null ? null : goalOrgId.trim();
    }

    public String getGoalOrgName() {
        return goalOrgName;
    }

    public void setGoalOrgName(String goalOrgName) {
        this.goalOrgName = goalOrgName == null ? null : goalOrgName.trim();
    }

    public String getGoalBusId() {
        return goalBusId;
    }

    public void setGoalBusId(String goalBusId) {
        this.goalBusId = goalBusId == null ? null : goalBusId.trim();
    }

    public Date getAdjustTime() {
        return adjustTime;
    }

    public void setAdjustTime(Date adjustTime) {
        this.adjustTime = adjustTime;
    }

    public BigDecimal getSnCount() {
        return snCount;
    }

    public void setSnCount(BigDecimal snCount) {
        this.snCount = snCount;
    }

    public String getButtJointPerson() {
        return buttJointPerson;
    }

    public void setButtJointPerson(String buttJointPerson) {
        this.buttJointPerson = buttJointPerson == null ? null : buttJointPerson.trim();
    }

    public BigDecimal getAdjustStatus() {
        return adjustStatus;
    }

    public void setAdjustStatus(BigDecimal adjustStatus) {
        this.adjustStatus = adjustStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum == null ? null : batchNum.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getuTime() {
        return uTime;
    }

    public void setuTime(Date uTime) {
        this.uTime = uTime;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public String getuUser() {
        return uUser;
    }

    public void setuUser(String uUser) {
        this.uUser = uUser == null ? null : uUser.trim();
    }

    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    public BigDecimal getVersion() {
        return version;
    }

    public void setVersion(BigDecimal version) {
        this.version = version;
    }

    public String getAdjustMsg() {
        return adjustMsg;
    }

    public void setAdjustMsg(String adjustMsg) {
        this.adjustMsg = adjustMsg;
    }

    public BigDecimal getPlatformType() {
        return platformType;
    }

    public void setPlatformType(BigDecimal platformType) {
        this.platformType = platformType;
    }
}