package com.ryx.credit.machine.vo;

import java.io.Serializable;

/**
 * 作者：cx
 * 时间：2018/10/13
 * 描述：活动变更 业务接口调用对象
 */
public class ChangeActMachineVo implements Serializable{
//    业务编号，
    private String busNum;
//    sn区间，
    private String snStart,snEnd;
//    老活动信息
    private String oldAct;
//    新活动信息，
    private String newAct;
//    下发人。
    private String optUser;

    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getSnStart() {
        return snStart;
    }

    public void setSnStart(String snStart) {
        this.snStart = snStart;
    }

    public String getSnEnd() {
        return snEnd;
    }

    public void setSnEnd(String snEnd) {
        this.snEnd = snEnd;
    }

    public String getOldAct() {
        return oldAct;
    }

    public void setOldAct(String oldAct) {
        this.oldAct = oldAct;
    }

    public String getNewAct() {
        return newAct;
    }

    public void setNewAct(String newAct) {
        this.newAct = newAct;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }
}
