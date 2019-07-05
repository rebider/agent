package com.ryx.credit.machine.vo;

import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.machine.entity.ImsTermWarehouseDetail;

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

    private String platFormNum;

    private String  PlatformType;

    private String actCode;


    /**
     * 物流id
     */
    private String oLogisticsId;


    ImsTermWarehouseDetail imsTermWarehouseDetail;

    List<String> snList;


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

    public String getoLogisticsId() {
        return oLogisticsId;
    }

    public void setoLogisticsId(String oLogisticsId) {
        this.oLogisticsId = oLogisticsId;
    }

    public String getPlatFormNum() {
        return platFormNum;
    }

    public void setPlatFormNum(String platFormNum) {
        this.platFormNum = platFormNum;
    }

    public String getActCode() {
        return actCode;
    }

    public void setActCode(String actCode) {
        this.actCode = actCode;
    }


    public String getPlatformType() {
        return PlatformType;
    }

    public void setPlatformType(String platformType) {
        PlatformType = platformType;
    }

    public ImsTermWarehouseDetail getImsTermWarehouseDetail() {
        return imsTermWarehouseDetail;
    }

    public void setImsTermWarehouseDetail(ImsTermWarehouseDetail imsTermWarehouseDetail) {
        this.imsTermWarehouseDetail = imsTermWarehouseDetail;
    }

    public List<String> getSnList() {
        return snList;
    }

    public void setSnList(List<String> snList) {
        this.snList = snList;
    }
}
