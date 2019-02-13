package com.ryx.credit.common.enumc;


/**
 * Created by cx on 2018/5/22.
 */
public enum DictGroup {

    ALL("所有模块"),
    YESORNO("yesorno"),
    YESORNOISYES("yesornoisyes"),
    AGENT("代理商模块"),
    ORDER("订单模块"),
    RELATION("关系模块"),
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
    BUS_SCOPE("业务范围"),
    //订单
    MODEL_TYPE("机具类型"),
    MANUFACTURER("厂商"),
    PROMODE("机具型号"),
    PAYMENTSTATUS("分期计划状态"),
    PAYMENTTYPE("付款明细类型"),
    SETTLEMENT_TYPE("结算类型"),
    ACT_RETURN_FINANCE("补款审批流名称"),
    ACT_ORDER("订单审批对应的启动流程"),
    ORDER_APR_MARKET("订单审批市场部参数"),
    ORDER_APR_BUSNISS("订单审批业务部参数"),
    ACT_COMPENSATE("退补差价审批流名称"),
    TEMP_AGREE("协议模板"),
    ORDER_STATUS("订单状态"),
    AGREE_TYPE("协议类型"),
    ACTIVITY_DIS_TYPE("活动优惠方式"),
    ACTIVITY_CONDITION("活动参与条件"),
    C_TYPE("扣款款项资金类型"),
    ACT_ORDER_RETURN("退货审批流名称"),
    TOOLS("机具押金扣款调整"),
    TOOLS_APR_BUSNISS("扣款调整审批业务部参数"),
    TOOLS_APR_MARKET("扣款调整审批市场部参数"),
    TOOLS_APR_BOSS("扣款调整审批总经办参数"),
    TOOLS_APR_EXPAND("扣款调整审批客户拓展部门参数"),
    PAYMENT_SRC_TYPE("付款明细表源类型"),
    SETTLEMENT_PRICESTR("结算价类型"),
    PAY_TYPE("打款方式"),
    MERGE("代理商合并"),
    MERGE_MARKET("代理商合并审批市场部参数"),
    MERGE_YUHUA("代理商合并审批于华参数"),
    NETIN_MARKET("代理商入网审批市场部参数"),
    AGENTQUIT("代理商退出审批流名称"),
    QUIT_MARKET("代理商退出审批市场部参数"),
    QUIT_MARKET_DEADLINE("代理商退出审批选择期限"),
    QUIT_MIGR_PLATFORM("代理商退出手刷迁移平台"),
    CAPITAL_MIARKET("保证金变更市场部审批参数"),
    CAPITAL_BUSINESS("保证金变更业务部审批参数");

    public String  msg;

    DictGroup(String s){
        msg = s;
    }


}
