package com.ryx.jobOrder.vo;

import com.ryx.jobOrder.pojo.JoOrder;

import java.util.Date;

public class JoTaskVo extends JoOrder {

    private String joTaskId;

    private String joTaskStatus;

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

}
