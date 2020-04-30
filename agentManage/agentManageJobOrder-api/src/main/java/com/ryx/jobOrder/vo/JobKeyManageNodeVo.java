package com.ryx.jobOrder.vo;

import com.ryx.jobOrder.pojo.JoKeyManage;

import java.util.List;

public class JobKeyManageNodeVo extends JoKeyManage {
    private List<JobKeyManageNodeVo> childNodes;

    public List<JobKeyManageNodeVo> getChildNodes() {
        return childNodes;
    }

    public void setChildNodes(List<JobKeyManageNodeVo> childNodes) {
        this.childNodes = childNodes;
    }
}
