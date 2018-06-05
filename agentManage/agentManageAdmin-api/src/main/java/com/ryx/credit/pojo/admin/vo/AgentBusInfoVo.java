package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.AgentBusInfo;

import java.util.List;

/**
 * Created by cx on 2018/5/28.
 */
public class AgentBusInfoVo extends AgentBusInfo {
    private List<String> busInfoTableFile;

    public List<String> getBusInfoTableFile() {
        return busInfoTableFile;
    }

    public void setBusInfoTableFile(List<String> busInfoTableFile) {
        this.busInfoTableFile = busInfoTableFile;
    }
}
