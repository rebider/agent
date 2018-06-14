package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ImportAgent implements Serializable{
    private String id;

    private String dataid;

    private String datatype;

    private String batchcode;

    private String cUser;

    private Date cTime;

    private Date dealTime;

    private String dealUser;

    private BigDecimal dealstatus;

    private String dealmsg;

    private BigDecimal status;

    private BigDecimal version;

    private String datacontent;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid == null ? null : dataid.trim();
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype == null ? null : datatype.trim();
    }

    public String getBatchcode() {
        return batchcode;
    }

    public void setBatchcode(String batchcode) {
        this.batchcode = batchcode == null ? null : batchcode.trim();
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser == null ? null : cUser.trim();
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealUser() {
        return dealUser;
    }

    public void setDealUser(String dealUser) {
        this.dealUser = dealUser == null ? null : dealUser.trim();
    }

    public BigDecimal getDealstatus() {
        return dealstatus;
    }

    public void setDealstatus(BigDecimal dealstatus) {
        this.dealstatus = dealstatus;
    }

    public String getDealmsg() {
        return dealmsg;
    }

    public void setDealmsg(String dealmsg) {
        this.dealmsg = dealmsg == null ? null : dealmsg.trim();
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

    public String getDatacontent() {
        return datacontent;
    }

    public void setDatacontent(String datacontent) {
        this.datacontent = datacontent == null ? null : datacontent.trim();
    }
}