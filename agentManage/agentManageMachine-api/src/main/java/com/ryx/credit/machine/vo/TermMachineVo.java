package com.ryx.credit.machine.vo;

import java.io.Serializable;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述：POS极具vo 手刷活动vo
 */
public class TermMachineVo implements Serializable{

    public String id;

    public String mechineName;

    public String standAmt;

    public String backType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMechineName() {
        return mechineName;
    }

    public void setMechineName(String mechineName) {
        this.mechineName = mechineName;
    }

    public String getStandAmt() {
        return standAmt;
    }

    public void setStandAmt(String standAmt) {
        this.standAmt = standAmt;
    }

    public String getBackType() {
        return backType;
    }

    public void setBackType(String backType) {
        this.backType = backType;
    }
}
