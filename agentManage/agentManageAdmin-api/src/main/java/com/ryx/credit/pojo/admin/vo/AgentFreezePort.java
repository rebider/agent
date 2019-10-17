package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/10/10 10:22
 * @Param
 * @return
 **/
public class AgentFreezePort implements Serializable{

    private String agentId;
    private String freezeCause;
    private String operationPerson;
    private String freezeNum;
    private String remark;
    private String unfreezeCause;

    public String getUnfreezeCause() {
        return unfreezeCause;
    }

    public void setUnfreezeCause(String unfreezeCause) {
        this.unfreezeCause = unfreezeCause;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getFreezeCause() {
        return freezeCause;
    }

    public void setFreezeCause(String freezeCause) {
        this.freezeCause = freezeCause;
    }

    public String getFreezeNum() {
        return freezeNum;
    }

    public void setFreezeNum(String freezeNum) {
        this.freezeNum = freezeNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperationPerson() {
        return operationPerson;
    }

    public void setOperationPerson(String operationPerson) {
        this.operationPerson = operationPerson;
    }

}
