package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.order.OSupplement;

import java.io.Serializable;
import java.util.List;

/**

/**
 * @Auther: RYX
 * @Date: 2018/7/21 16:01
 * @Description:
 */
public class OsupplementVo implements Serializable{
    private OSupplement supplement;
    private List<String> agentTableFile;
    private List<OCashReceivablesVo> oCashReceivablesVos;

    public OSupplement getSupplement() {
        return supplement;
    }

    public void setSupplement(OSupplement supplement) {
        this.supplement = supplement;
    }

    public List<String> getAgentTableFile() {
        return agentTableFile;
    }

    public void setAgentTableFile(List<String> agentTableFile) {
        this.agentTableFile = agentTableFile;
    }

    public List<OCashReceivablesVo> getoCashReceivablesVos() {
        return oCashReceivablesVos;
    }

    public void setoCashReceivablesVos(List<OCashReceivablesVo> oCashReceivablesVos) {
        this.oCashReceivablesVos = oCashReceivablesVos;
    }
}