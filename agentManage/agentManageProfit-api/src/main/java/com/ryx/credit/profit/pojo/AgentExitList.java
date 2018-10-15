package com.ryx.credit.profit.pojo;

import java.io.Serializable;

/**
 * @Author Lihl
 * @Date 2018/7/27
 * @Desc 代理商退出列表
 */
public class AgentExitList implements Serializable{
    private static final long serialVersionUID = -1708596491530510464L;

    private String agUniqNum;//代理商唯一号

    private String agName;//代理商名称

    private String agHead;//负责人

    private String agHeadMobile;//负责人联系电话

    private String agRemark;//备注

    private String createDate;//创建日期

    private String agDocDistrict;//所属大区

    private String agDocPro;//所属省区

    private String flowStatus;//审批状态

    private String passDate;//退出时间

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

    public String getAgRemark() {
        return agRemark;
    }

    public void setAgRemark(String agRemark) {
        this.agRemark = agRemark;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
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

    public String getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(String flowStatus) {
        this.flowStatus = flowStatus;
    }

    public String getPassDate() {
        return passDate;
    }

    public void setPassDate(String passDate) {
        this.passDate = passDate;
    }
}
