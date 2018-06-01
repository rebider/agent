package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by cx on 2018/5/22.
 */
public enum DictGroup {

    ALL("所有模块"),
    YESORNO("yesorno"),
    AGENT("代理商模块"),
    AGENT_AUDIT("代理商审核模块"),
    BUS_TYPE("业务模块类型或者级别"),
    CAPITAL_TYPE("交款类型"),
    CONTRACT_TYPE("合同类型"),
    CERT_TYPE("证件类型"),
    AGNATURE_TYPE("公司类型"),
    COLINFO_TYPE("收款账户类型"),
    AGENT_IN_STATUS("代理商入网状态"),
    AG_STATUS_S("审核状态"),
    AG_STATUS_I("审核数字状态"),
    APPROVAL_TYPE("审批结果类型"),
    APPROVAL_PASS_TYPE("审批通过");


    public String  msg;

    DictGroup(String s){
        msg = s;
    }


}
