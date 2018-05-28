package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.Capital;

import java.util.List;

/**
 * Created by cx on 2018/5/28.
 */
public class CapitalVo extends Capital {

    private List<String> capitalTableFile;

    public List<String> getCapitalTableFile() {
        return capitalTableFile;
    }

    public void setCapitalTableFile(List<String> capitalTableFile) {
        this.capitalTableFile = capitalTableFile;
    }
}
