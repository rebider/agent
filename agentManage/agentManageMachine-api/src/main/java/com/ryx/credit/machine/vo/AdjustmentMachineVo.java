package com.ryx.credit.machine.vo;

import com.ryx.credit.machine.entity.ImsTermAdjustDetail;
import com.ryx.credit.pojo.admin.order.OLogisticsDetail;

import java.io.Serializable;
import java.util.List;

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
     * 老活动编号
     */
    public String oldAct;
    /**
     * 新活动编号
     */
    public String newAct;
    /**
     * sn数量
     */
    public String  snNum;

    /**
     * 业务平台号
     */
    public String platformNum;

    /**
     *下发人。
     */
    public String optUser;

    private String  PlatformType;

    List<OLogisticsDetail> logisticsDetailList;
    ImsTermAdjustDetail imsTermAdjustDetail;


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

    public String getSnNum() {
        return snNum;
    }

    public void setSnNum(String snNum) {
        this.snNum = snNum;
    }

    public String getPlatformNum() {
        return platformNum;
    }

    public void setPlatformNum(String platformNum) {
        this.platformNum = platformNum;
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

    public String getPlatformType() {
        return PlatformType;
    }

    public void setPlatformType(String platformType) {
        PlatformType = platformType;
    }


    public List<OLogisticsDetail> getLogisticsDetailList() {
        return logisticsDetailList;
    }

    public void setLogisticsDetailList(List<OLogisticsDetail> logisticsDetailList) {
        this.logisticsDetailList = logisticsDetailList;
    }

    public ImsTermAdjustDetail getImsTermAdjustDetail() {
        return imsTermAdjustDetail;
    }

    public void setImsTermAdjustDetail(ImsTermAdjustDetail imsTermAdjustDetail) {
        this.imsTermAdjustDetail = imsTermAdjustDetail;
    }
}
