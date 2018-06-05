package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.AgentContract;

import java.util.List;

/**
 * Created by cx on 2018/5/28.
 */
public class AgentContractVo extends AgentContract {

    private List<String> contractTableFile;

    public List<String> getContractTableFile() {
        return contractTableFile;
    }

    public void setContractTableFile(List<String> contractTableFile) {
        this.contractTableFile = contractTableFile;
    }
}
