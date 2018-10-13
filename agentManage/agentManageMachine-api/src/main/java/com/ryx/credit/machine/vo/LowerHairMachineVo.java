package com.ryx.credit.machine.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2018/10/13
 * 描述：首刷终端下发vo
 */
public class LowerHairMachineVo  implements Serializable {
    //    业务编号，
    private String busNum;
    //    SN码或者sn区间，
    private String snStart;
    private String snEnd;
    //    下发人。
    private String optUser;
    //sn集合
    private List<MposSnVo> listSn;


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

    public String getOptUser() {
        return optUser;
    }

    public void setOptUser(String optUser) {
        this.optUser = optUser;
    }

    public List<MposSnVo> getListSn() {
        return listSn;
    }

    public void setListSn(List<MposSnVo> listSn) {
        this.listSn = listSn;
    }
}
