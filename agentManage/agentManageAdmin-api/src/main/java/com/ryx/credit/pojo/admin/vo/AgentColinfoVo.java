package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.AgentColinfo;

import java.util.List;

/**
 * Created by cx on 2018/5/28.
 */
public class AgentColinfoVo extends AgentColinfo {
    private List<String> colinfoTableFile;

    public List<String> getColinfoTableFile() {
        return colinfoTableFile;
    }

    public void setColinfoTableFile(List<String> colinfoTableFile) {
        this.colinfoTableFile = colinfoTableFile;
    }
}
