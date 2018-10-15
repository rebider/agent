package com.ryx.credit.machine.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2018/10/13
 * 描述：
 */
public class MposSnVo implements Serializable {


    public MposSnVo(String busBatchNum, String sn, String mykey, String actCode, String termType) {
        this.busBatchNum = busBatchNum;
        this.sn = sn;
        this.mykey = mykey;
        this.actCode = actCode;
        this.termType = termType;
    }

    //    终端批次（动态获取从保昌那获取，保存到活动信息），
    private String busBatchNum;
    //    SN码
    private String sn;
    //    秘钥（？张晨），
    private String mykey;
    //    活动信息，
    private String actCode;
    //    终端类型（商品表添加终端类型和终端类型code，?小蓝牙）,
    private String termType;


    public String getBusBatchNum() {
        return busBatchNum;
    }

    public void setBusBatchNum(String busBatchNum) {
        this.busBatchNum = busBatchNum;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
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

    ;
}
