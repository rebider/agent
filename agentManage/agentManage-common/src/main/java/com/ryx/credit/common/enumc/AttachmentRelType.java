package com.ryx.credit.common.enumc;

/**
 * 附件类型
 * Created by cx on 2018/5/22.
 */
public enum AttachmentRelType {
    Agent("代理商附件"),Contract("合同附件"),Capital("缴款项"),Business("业务"),Proceeds("收款");

    public String  msg;

    AttachmentRelType(String s){
        msg = s;
    }
}
