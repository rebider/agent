package com.ryx.jobOrder.vo;

import com.ryx.jobOrder.pojo.JoOrder;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class JoTaskVo extends JoOrder {

    private Long userId;

    private String joDealPersonId;

    private String joDealPersonName;

    private String orderType;

    private String secondDealGroup;

    private String joDealGroupId;

    private String joTaskId;

    private String joTaskStatus;

    private String joTopKeyNum;

    private Date joTaskTime;

    private Date joTaskAcceptTime;

    private String joStartTimeBeginStr;

    private String joStartTimeEndStr;

    private String joAcceptTimeBeginStr;

    private String joAcceptTimeEndStr;

    private Date joStartTimeBegin;

    private Date joStartTimeEnd;

    private Date joAcceptTimeBegin;

    private Date joAcceptTimeEnd;

    private List<Map>  platfromPerm;

    private String dealGroup;

    private String joTaskContent;

    private String joTaskNextTime;

    private String attId;
    private String attName;

    public String getAttId() {
        return attId;
    }

    public void setAttId(String attId) {
        this.attId = attId;
    }

    public String getAttName() {
        return attName;
    }

    public void setAttName(String attName) {
        this.attName = attName;
    }

    public String getDealGroup() {
        return dealGroup;
    }

    public void setDealGroup(String dealGroup) {
        this.dealGroup = dealGroup;
    }

    public String getJoTaskContent() {
        return joTaskContent;
    }

    public void setJoTaskContent(String joTaskContent) {
        this.joTaskContent = joTaskContent;
    }

    public String getJoTaskNextTime() {
        return joTaskNextTime;
    }

    public void setJoTaskNextTime(String joTaskNextTime) {
        this.joTaskNextTime = joTaskNextTime;
    }

    public List<Map> getPlatfromPerm() {
        return platfromPerm;
    }

    public void setPlatfromPerm(List<Map> platfromPerm) {
        this.platfromPerm = platfromPerm;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSecondDealGroup() {
        return secondDealGroup;
    }

    public void setSecondDealGroup(String secondDealGroup) {
        this.secondDealGroup = secondDealGroup;
    }

    public String getJoDealPersonName() {
        return joDealPersonName;
    }

    public void setJoDealPersonName(String joDealPersonName) {
        this.joDealPersonName = joDealPersonName;
    }

    public String getJoTopKeyNum() {
        return joTopKeyNum;
    }

    public void setJoTopKeyNum(String joTopKeyNum) {
        this.joTopKeyNum = joTopKeyNum;
    }

    public String getJoDealPersonId() {
        return joDealPersonId;
    }

    public void setJoDealPersonId(String joDealPersonId) {
        this.joDealPersonId = joDealPersonId;
    }

    public String getJoDealGroupId() {
        return joDealGroupId;
    }

    public void setJoDealGroupId(String joDealGroupId) {
        this.joDealGroupId = joDealGroupId;
    }

    public Date getJoStartTimeBegin() {
        return joStartTimeBegin;
    }

    public void setJoStartTimeBegin(Date joStartTimeBegin) {
        this.joStartTimeBegin = joStartTimeBegin;
    }

    public Date getJoStartTimeEnd() {
        return joStartTimeEnd;
    }

    public void setJoStartTimeEnd(Date joStartTimeEnd) {
        this.joStartTimeEnd = joStartTimeEnd;
    }

    public Date getJoAcceptTimeBegin() {
        return joAcceptTimeBegin;
    }

    public void setJoAcceptTimeBegin(Date joAcceptTimeBegin) {
        this.joAcceptTimeBegin = joAcceptTimeBegin;
    }

    public Date getJoAcceptTimeEnd() {
        return joAcceptTimeEnd;
    }

    public void setJoAcceptTimeEnd(Date joAcceptTimeEnd) {
        this.joAcceptTimeEnd = joAcceptTimeEnd;
    }

    public String getJoStartTimeEndStr() {
        return joStartTimeEndStr;
    }

    public void setJoStartTimeEndStr(String joStartTimeEndStr) {
        this.joStartTimeEndStr = joStartTimeEndStr;
    }

    public String getJoAcceptTimeBeginStr() {
        return joAcceptTimeBeginStr;
    }

    public void setJoAcceptTimeBeginStr(String joAcceptTimeBeginStr) {
        this.joAcceptTimeBeginStr = joAcceptTimeBeginStr;
    }

    public String getJoAcceptTimeEndStr() {
        return joAcceptTimeEndStr;
    }

    public void setJoAcceptTimeEndStr(String joAcceptTimeEndStr) {
        this.joAcceptTimeEndStr = joAcceptTimeEndStr;
    }

    public String getJoStartTimeBeginStr() {
        return joStartTimeBeginStr;
    }

    public void setJoStartTimeBeginStr(String joStartTimeBeginStr) {
        this.joStartTimeBeginStr = joStartTimeBeginStr;
    }

    public String getJoTaskId() {
        return joTaskId;
    }

    public void setJoTaskId(String joTaskId) {
        this.joTaskId = joTaskId;
    }

    public String getJoTaskStatus() {
        return joTaskStatus;
    }

    public void setJoTaskStatus(String joTaskStatus) {
        this.joTaskStatus = joTaskStatus;
    }

    public Date getJoTaskTime() {
        return joTaskTime;
    }

    public void setJoTaskTime(Date joTaskTime) {
        this.joTaskTime = joTaskTime;
    }

    public Date getJoTaskAcceptTime() {
        return joTaskAcceptTime;
    }

    public void setJoTaskAcceptTime(Date joTaskAcceptTime) {
        this.joTaskAcceptTime = joTaskAcceptTime;
    }

    @Override
    public String toString() {
        return "JoTaskVo{" +
                "userId=" + userId +
                ", joDealPersonId='" + joDealPersonId + '\'' +
                ", joDealPersonName='" + joDealPersonName + '\'' +
                ", orderType='" + orderType + '\'' +
                ", secondDealGroup='" + secondDealGroup + '\'' +
                ", joDealGroupId='" + joDealGroupId + '\'' +
                ", joTaskId='" + joTaskId + '\'' +
                ", joTaskStatus='" + joTaskStatus + '\'' +
                ", joTopKeyNum='" + joTopKeyNum + '\'' +
                ", joTaskTime=" + joTaskTime +
                ", joTaskAcceptTime=" + joTaskAcceptTime +
                ", joStartTimeBeginStr='" + joStartTimeBeginStr + '\'' +
                ", joStartTimeEndStr='" + joStartTimeEndStr + '\'' +
                ", joAcceptTimeBeginStr='" + joAcceptTimeBeginStr + '\'' +
                ", joAcceptTimeEndStr='" + joAcceptTimeEndStr + '\'' +
                ", joStartTimeBegin=" + joStartTimeBegin +
                ", joStartTimeEnd=" + joStartTimeEnd +
                ", joAcceptTimeBegin=" + joAcceptTimeBegin +
                ", joAcceptTimeEnd=" + joAcceptTimeEnd +
                ", platfromPerm=" + platfromPerm +
                '}' +super.toString();
    }
}
