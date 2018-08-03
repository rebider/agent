package com.ryx.credit.pojo.admin.order;

import com.ryx.credit.pojo.admin.agent.Attachment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ORefundPriceDiff implements Serializable{
    private String id;

    private String applyCompType;

    private String applyCompName;

    private BigDecimal applyCompAmt;

    private String relCompType;

    private String relCompName;

    private BigDecimal relCompAmt;

    private BigDecimal machOweAmt;

    private Date gatherTime;

    private BigDecimal gatherAmt;

    private BigDecimal reviewStatus;

    private Date sTime;

    private String applyRemark;

    private BigDecimal deductAmt;

    private Date cTime;

    private Date uTime;

    private String cUser;

    private String uUser;

    private BigDecimal status;

    private BigDecimal version;

    private List<ORefundPriceDiffDetail> refundPriceDiffDetailList;

    private List<Attachment> attachmentList;

    private List<ODeductCapital>  deductCapitalList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getApplyCompType() {
        return applyCompType;
    }

    public void setApplyCompType(String applyCompType) {
        this.applyCompType = applyCompType == null ? null : applyCompType.trim();
    }

    public BigDecimal getApplyCompAmt() {
        return applyCompAmt;
    }

    public void setApplyCompAmt(BigDecimal applyCompAmt) {
        this.applyCompAmt = applyCompAmt;
    }

    public String getRelCompType() {
        return relCompType;
    }

    public void setRelCompType(String relCompType) {
        this.relCompType = relCompType == null ? null : relCompType.trim();
    }

    public BigDecimal getRelCompAmt() {
        return relCompAmt;
    }

    public void setRelCompAmt(BigDecimal relCompAmt) {
        this.relCompAmt = relCompAmt;
    }

    public BigDecimal getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(BigDecimal reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public Date getsTime() {
        return sTime;
    }

    public void setsTime(Date sTime) {
        this.sTime = sTime;
    }

    public String getApplyRemark() {
        return applyRemark;
    }

    public void setApplyRemark(String applyRemark) {
        this.applyRemark = applyRemark == null ? null : applyRemark.trim();
    }

    public BigDecimal getDeductAmt() {
        return deductAmt;
    }

    public void setDeductAmt(BigDecimal deductAmt) {
        this.deductAmt = deductAmt;
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

    public List<ORefundPriceDiffDetail> getRefundPriceDiffDetailList() {
        return refundPriceDiffDetailList;
    }

    public void setRefundPriceDiffDetailList(List<ORefundPriceDiffDetail> refundPriceDiffDetailList) {
        this.refundPriceDiffDetailList = refundPriceDiffDetailList;
    }

    public List<Attachment> getAttachmentList() {
        return attachmentList;
    }

    public void setAttachmentList(List<Attachment> attachmentList) {
        this.attachmentList = attachmentList;
    }

    public List<ODeductCapital> getDeductCapitalList() {
        return deductCapitalList;
    }

    public void setDeductCapitalList(List<ODeductCapital> deductCapitalList) {
        this.deductCapitalList = deductCapitalList;
    }

    public BigDecimal getMachOweAmt() {
        return machOweAmt;
    }

    public void setMachOweAmt(BigDecimal machOweAmt) {
        this.machOweAmt = machOweAmt;
    }

    public Date getGatherTime() {
        return gatherTime;
    }

    public void setGatherTime(Date gatherTime) {
        this.gatherTime = gatherTime;
    }

    public BigDecimal getGatherAmt() {
        return gatherAmt;
    }

    public void setGatherAmt(BigDecimal gatherAmt) {
        this.gatherAmt = gatherAmt;
    }

    public String getApplyCompName() {
        return applyCompName;
    }

    public void setApplyCompName(String applyCompName) {
        this.applyCompName = applyCompName;
    }

    public String getRelCompName() {
        return relCompName;
    }

    public void setRelCompName(String relCompName) {
        this.relCompName = relCompName;
    }
}