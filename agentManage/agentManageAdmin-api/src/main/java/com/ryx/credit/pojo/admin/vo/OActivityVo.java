package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.order.OActivity;

import java.io.Serializable;

/**
 * Created by RYX on 2018/7/19.
 */
public class OActivityVo extends OActivity implements Serializable {

    private String beginTimeStr;

    private String endTimeStr;

    public String getBeginTimeStr() {
        return beginTimeStr;
    }

    public void setBeginTimeStr(String beginTimeStr) {
        this.beginTimeStr = beginTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }
}
