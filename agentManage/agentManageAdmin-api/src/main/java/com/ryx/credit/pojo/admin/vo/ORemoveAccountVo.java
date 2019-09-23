package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.AgentContract;
import com.ryx.credit.pojo.admin.order.ORemoveAccount;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: lrr
 * @Date: 2019/9/20 10:47
 * @Description:销账的VO
 */
public class ORemoveAccountVo implements Serializable {
    private ORemoveAccount removeAccount;
    private List<String> removeAccountFile;

    public ORemoveAccount getRemoveAccount() {
        return removeAccount;
    }

    public void setRemoveAccount(ORemoveAccount removeAccount) {
        this.removeAccount = removeAccount;
    }

    public List<String> getRemoveAccountFile() {
        return removeAccountFile;
    }

    public void setRemoveAccountFile(List<String> removeAccountFile) {
        this.removeAccountFile = removeAccountFile;
    }
}