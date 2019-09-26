package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by lhl on 2019/8/15.
 * 退货服务：退转发明细数据导出实体
 */
public class ReturnOrderVo implements Serializable {

    private String returnOrderId;

    private String busNum;

    private String agUniqNum;

    private String agName;

    private String vender;

    private String proModel;

    private BigDecimal returnCount;

    private BigDecimal orderPrice;

    private String activityName;

    private BigDecimal receiveSendNum;

    private String receiveOrderId;

    private String receiveAgentId;

    private String receiveAgentName;

    private String receiveActivityName;

    private BigDecimal cutAmo;

    private String addrProvince;

    private String addrCity;

    private String addrDistrict;

    private String addrDetail;

    private String wnumber;

    private String agDocDistrict;

    private String agDocPro;

    public String getReturnOrderId() {
        return returnOrderId;
    }

    public void setReturnOrderId(String returnOrderId) {
        this.returnOrderId = returnOrderId;
    }

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
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

    public String getVender() {
        return vender;
    }

    public void setVender(String vender) {
        this.vender = vender;
    }

    public String getProModel() {
        return proModel;
    }

    public void setProModel(String proModel) {
        this.proModel = proModel;
    }

    public BigDecimal getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(BigDecimal returnCount) {
        this.returnCount = returnCount;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public BigDecimal getReceiveSendNum() {
        return receiveSendNum;
    }

    public void setReceiveSendNum(BigDecimal receiveSendNum) {
        this.receiveSendNum = receiveSendNum;
    }

    public String getReceiveOrderId() {
        return receiveOrderId;
    }

    public void setReceiveOrderId(String receiveOrderId) {
        this.receiveOrderId = receiveOrderId;
    }

    public String getReceiveAgentId() {
        return receiveAgentId;
    }

    public void setReceiveAgentId(String receiveAgentId) {
        this.receiveAgentId = receiveAgentId;
    }

    public String getReceiveAgentName() {
        return receiveAgentName;
    }

    public void setReceiveAgentName(String receiveAgentName) {
        this.receiveAgentName = receiveAgentName;
    }

    public String getReceiveActivityName() {
        return receiveActivityName;
    }

    public void setReceiveActivityName(String receiveActivityName) {
        this.receiveActivityName = receiveActivityName;
    }

    public BigDecimal getCutAmo() {
        return cutAmo;
    }

    public void setCutAmo(BigDecimal cutAmo) {
        this.cutAmo = cutAmo;
    }

    public String getAddrProvince() {
        return addrProvince;
    }

    public void setAddrProvince(String addrProvince) {
        this.addrProvince = addrProvince;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getAddrDistrict() {
        return addrDistrict;
    }

    public void setAddrDistrict(String addrDistrict) {
        this.addrDistrict = addrDistrict;
    }

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getWnumber() {
        return wnumber;
    }

    public void setWnumber(String wnumber) {
        this.wnumber = wnumber;
    }

    public String getAgDocDistrict() {
        return agDocDistrict;
    }

    public void setAgDocDistrict(String agDocDistrict) {
        this.agDocDistrict = agDocDistrict;
    }

    public String getAgDocPro() {
        return agDocPro;
    }

    public void setAgDocPro(String agDocPro) {
        this.agDocPro = agDocPro;
    }
}
