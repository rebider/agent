package com.ryx.credit.machine.vo;

import java.io.Serializable;

/**
 * 作者：cx
 * 时间：2018/10/13
 * 描述：退货机具调整，业务系统接口调用参数
 */
public class AdjustmentMachineVo implements Serializable{

    /**
     * 业务编号，
     */
    public String oldBusNum;
    /**
     * 新业务编号
      */
    public String newBusNum;
    /**
     * SN码或者sn区间，
     */
    public String snStart;
    public String snEnd;
    /**
     *下发人。
     */
    public String optUser;


    public String getOldBusNum() {
        return oldBusNum;
    }

    public void setOldBusNum(String oldBusNum) {
        this.oldBusNum = oldBusNum;
    }

    public String getNewBusNum() {
        return newBusNum;
    }

    public void setNewBusNum(String newBusNum) {
        this.newBusNum = newBusNum;
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

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }
}
