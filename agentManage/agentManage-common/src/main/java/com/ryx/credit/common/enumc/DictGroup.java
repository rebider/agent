package com.ryx.credit.common.enumc;


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
    BUS_STATUS("业务状态"),
    DATA_CHANGE_TYPE("数据修改申请"),
    DATA_CACTIVITY_TYPE("数据修改对应的启动流程"),
    BUS_ACT_REL_BUSTYPE("审批关系类型"),
    APPROVAL_PASS_TYPE("审批通过"),
    ACTIVITY_RESPAR("南北大区部门code正则匹配"),//AGENT模块
    ACTIVITY_TASK_REX("任务code正则匹配"),//AGENT模块
    USE_SCOPE("使用范围"),
    BUS_SCOPE("业务范围");


    public String  msg;

    DictGroup(String s){
        msg = s;
    }


}
