package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: agentManage
 *
 * @description: 工商认证VO对象
 *
 * @author: ssx
 *
 * @create: 2019-09-21 21:13
 **/
public class AgentCaVo implements Serializable {
    private String id;

    private String agUniqNum;

    private String agName;

    private BigDecimal agNature;

    private BigDecimal agCapital;

    private String agBusLic;

    private String agBusLicb;

    private String agBusLice;

    private String agLegal;

    private BigDecimal agLegalCertype;

    private String agLegalCernum;

    private String agLegalMobile;

    private String agHead;

    private String agHeadMobile;

    private String agRegAdd;

    private String agBusScope;

    private BigDecimal cloTaxPoint;

    private String agStatus;

    private String agDocPro;

    private String agDocDistrict;

    private String agRemark;

    private String cIncomTime;

    private String cTime;

    private String cUtime;

    private BigDecimal cIncomStatus;

    private String cUser;

    private String agZbh;

    private BigDecimal status;

    private BigDecimal version;

    private BigDecimal caStatus;

    private String agRegArea;

    private boolean isImport = false;

    private BigDecimal freestatus;

    private String busRiskEmail;

    private String busContactEmail;

    private BigDecimal reportStatus;

    private String reportTime;

    private String caStatusMark;

    private String cerSuccessTm;

    private String agentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgUniqNum() {
        return agUniqNum;
    }

    public void setAgUniqNum(String agUniqNum) {
        this.agUniqNum = agUniqNum;
    }

    public String getAgName() {
        return agName;
    }

    public void setAgName(String agName) {
        this.agName = agName;
    }

    public BigDecimal getAgNature() {
        return agNature;
    }

    public void setAgNature(BigDecimal agNature) {
        this.agNature = agNature;
    }

    public BigDecimal getAgCapital() {
        return agCapital;
    }

    public void setAgCapital(BigDecimal agCapital) {
        this.agCapital = agCapital;
    }

    public String getAgBusLic() {
        return agBusLic;
    }

    public void setAgBusLic(String agBusLic) {
        this.agBusLic = agBusLic;
    }

    public String getAgBusLicb() {
        return agBusLicb;
    }

    public void setAgBusLicb(String agBusLicb) {
        this.agBusLicb = agBusLicb;
    }

    public String getAgBusLice() {
        return agBusLice;
    }

    public void setAgBusLice(String agBusLice) {
        this.agBusLice = agBusLice;
    }

    public String getAgLegal() {
        return agLegal;
    }

    public void setAgLegal(String agLegal) {
        this.agLegal = agLegal;
    }

    public BigDecimal getAgLegalCertype() {
        return agLegalCertype;
    }

    public void setAgLegalCertype(BigDecimal agLegalCertype) {
        this.agLegalCertype = agLegalCertype;
    }

    public String getAgLegalCernum() {
        return agLegalCernum;
    }

    public void setAgLegalCernum(String agLegalCernum) {
        this.agLegalCernum = agLegalCernum;
    }

    public String getAgLegalMobile() {
        return agLegalMobile;
    }

    public void setAgLegalMobile(String agLegalMobile) {
        this.agLegalMobile = agLegalMobile;
    }

    public String getAgHead() {
        return agHead;
    }

    public void setAgHead(String agHead) {
        this.agHead = agHead;
    }

    public String getAgHeadMobile() {
        return agHeadMobile;
    }

    public void setAgHeadMobile(String agHeadMobile) {
        this.agHeadMobile = agHeadMobile;
    }

    public String getAgRegAdd() {
        return agRegAdd;
    }

    public void setAgRegAdd(String agRegAdd) {
        this.agRegAdd = agRegAdd;
    }

    public String getAgBusScope() {
        return agBusScope;
    }

    public void setAgBusScope(String agBusScope) {
        this.agBusScope = agBusScope;
    }

    public BigDecimal getCloTaxPoint() {
        return cloTaxPoint;
    }

    public void setCloTaxPoint(BigDecimal cloTaxPoint) {
        this.cloTaxPoint = cloTaxPoint;
    }

    public String getAgStatus() {
        return agStatus;
    }

    public void setAgStatus(String agStatus) {
        this.agStatus = agStatus;
    }

    public String getAgDocPro() {
        return agDocPro;
    }

    public void setAgDocPro(String agDocPro) {
        this.agDocPro = agDocPro;
    }

    public String getAgDocDistrict() {
        return agDocDistrict;
    }

    public void setAgDocDistrict(String agDocDistrict) {
        this.agDocDistrict = agDocDistrict;
    }

    public String getAgRemark() {
        return agRemark;
    }

    public void setAgRemark(String agRemark) {
        this.agRemark = agRemark;
    }

    public String getCIncomTime() {
        return cIncomTime;
    }

    public void setCIncomTime(String cIncomTime) {
        this.cIncomTime = cIncomTime;
    }

    public String getCTime() {
        return cTime;
    }

    public void setCTime(String cTime) {
        this.cTime = cTime;
    }

    public String getcUtime() {
        return cUtime;
    }

    public void setCUtime(String cUtime) {
        this.cUtime = cUtime;
    }

    public BigDecimal getCIncomStatus() {
        return cIncomStatus;
    }

    public void setIncomStatus(BigDecimal cIncomStatus) {
        this.cIncomStatus = cIncomStatus;
    }

    public String getCUser() {
        return cUser;
    }

    public void setCUser(String cUser) {
        this.cUser = cUser;
    }

    public String getAgZbh() {
        return agZbh;
    }

    public void setAgZbh(String agZbh) {
        this.agZbh = agZbh;
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

    public BigDecimal getCaStatus() {
        return caStatus;
    }

    public void setCaStatus(BigDecimal caStatus) {
        this.caStatus = caStatus;
    }

    public String getAgRegArea() {
        return agRegArea;
    }

    public void setAgRegArea(String agRegArea) {
        this.agRegArea = agRegArea;
    }

    public boolean isImport() {
        return isImport;
    }

    public void setImport(boolean anImport) {
        isImport = anImport;
    }

    public BigDecimal getFreestatus() {
        return freestatus;
    }

    public void setFreestatus(BigDecimal freestatus) {
        this.freestatus = freestatus;
    }

    public String getBusRiskEmail() {
        return busRiskEmail;
    }

    public void setBusRiskEmail(String busRiskEmail) {
        this.busRiskEmail = busRiskEmail;
    }

    public String getBusContactEmail() {
        return busContactEmail;
    }

    public void setBusContactEmail(String busContactEmail) {
        this.busContactEmail = busContactEmail;
    }

    public BigDecimal getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(BigDecimal reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getReportTime() {
        return reportTime;
    }

    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    public String getCaStatusMark() {
        return caStatusMark;
    }

    public void setCaStatusMark(String caStatusMark) {
        this.caStatusMark = caStatusMark;
    }

    public String getCerSuccessTm() {
        return cerSuccessTm;
    }

    public void setCerSuccessTm(String cerSuccessTm) {
        this.cerSuccessTm = cerSuccessTm;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
}
