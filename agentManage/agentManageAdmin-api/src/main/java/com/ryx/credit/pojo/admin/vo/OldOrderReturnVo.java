package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：cx
 * 时间：2019/3/7
 * 描述：
 */
public class OldOrderReturnVo implements Serializable {

    private String userId,agentId,remark;

    private List<OldOrderReturnSubmitProVo> oldOrderReturnSubmitProVoList ;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<OldOrderReturnSubmitProVo> getOldOrderReturnSubmitProVoList() {
        return oldOrderReturnSubmitProVoList;
    }

    public void setOldOrderReturnSubmitProVoList(List<OldOrderReturnSubmitProVo> oldOrderReturnSubmitProVoList) {
        this.oldOrderReturnSubmitProVoList = oldOrderReturnSubmitProVoList;
    }
}
