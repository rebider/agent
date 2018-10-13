package com.ryx.credit.machine.vo;

import java.io.Serializable;

/**
 * 作者：cx
 * 时间：2018/10/13
 * 描述：首刷终端下发vo
 */
public class LowerHairMachineVo  implements Serializable {
    //    业务编号，
    private String busNum;
    //    终端批次（动态获取从保昌那获取，保存到活动信息），
    private String busBatchNum;
    //    SN码或者sn区间，
    private String snStart;
    private String snEnd;
    //    秘钥（？张晨），
    private String mykey;
    //    活动信息，
    private String actCode;
    //    终端类型（商品表添加终端类型和终端类型code，?小蓝牙）,
    private String termType;
    //    下发人。
    private String optUser;


    public String getBusNum() {
        return busNum;
    }

    public void setBusNum(String busNum) {
        this.busNum = busNum;
    }

    public String getBusBatchNum() {
        return busBatchNum;
    }

    public void setBusBatchNum(String busBatchNum) {
        this.busBatchNum = busBatchNum;
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

    public String getMykey() {
        return mykey;
    }

    public void setMykey(String mykey) {
        this.mykey = mykey;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }

    public String getTermType() {
        return termType;
    }

    public void setTermType(String termType) {
        this.termType = termType;
    }

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }
}
