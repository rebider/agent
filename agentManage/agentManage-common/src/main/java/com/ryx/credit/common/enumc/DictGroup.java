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
    CARD("流量卡模块"),
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
    APPROVAL_CANCEL_TYPE("审批撤销"),
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
    ACT_COMPENSATE("活动调整审批流名称"),
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
    OTHER_DEDUCT("其他扣款/补款审批参数"),
    INVOICEAPPLY("发票审核审批参数"),
    MERGE_MARKET("代理商合并审批市场部参数"),
    MERGE_YUHUA("代理商合并审批于华参数"),
    NETIN_MARKET("代理商入网审批市场部参数"),
    AGENTQUIT("代理商退出审批流名称"),
    QUIT_MARKET("代理商退出审批市场部参数"),
    QUIT_MARKET_DEADLINE("代理商退出审批选择期限"),
    QUIT_MIGR_PLATFORM("代理商退出手刷迁移平台"),
    CAPITAL_MIARKET("保证金变更市场部审批参数"),
    PROFIT_TEMPLATE_APPLY("分润模板线上审批审批流名称"),
    PROFIT_TEMPLATE_APPLY3("分润模板线上审批审批流名称3"),
    POS("POS审批"),
    POS_APR_BUSNISS("POS审批参数"),
    ORDER_RETURN("订单退货业务选择下一级审批人参数"),
    APPROVE_MODE("审批流程图当前节点"),
    PRE_APPROVE_MODE("准生产环境审批流程图当前节点"),
    ORG_TYPE("机构类型"),
    REPORT_STATUS("报备状态"),
    INTERNET_RENEW("流量卡续费配置"),
    CARD_AMT("流量卡每张金额"),
    OFFSET_AMT("轧差每张金额"),
    COMPENSATE_MODEL_TYPE("补差价可扩展机具类型"),
    COMPENSATE_PLATFORM_TYPE("补差价可调整关系平台"),
    RDBPOS("瑞大宝模块"),
    RDB_POS_LOWER("瑞大宝直签终端下限"),
    AGENT_CODE_FILTER("代理商过滤代码"),
    EMAIL("邮件模块"),
    LOGISTICS_FAIL_EMAIL("物流失败邮件"),
    CERTIFICATION_STATUS("代理商工商认证"),
    DATA_SHIRO("流量卡内部续费数据权限"),
    ANN_ORG("公告运维机构"),

    ANN_TYPE("公告类型"),
    REMOVEACCOUNT_FAIL_EMAIL("销账失败邮件"),
    RATYPE("销账方式"),
    RSTATUS("销账状态"),
    RELATION_PLATFORM_NUM("平台关系"),
    ADJ_STAT("订单机具调整审批状态"),
    REFUND_TYPE("退款类型"),
    REFUND_STAT("退款状态"),
    ORDER_ADJ_APR_BUSNISS("业务部订单调整审批参数"),
    FINACEORG_CONFIG("出款机构调整逻辑"),
    FINACEORG_PAR("出款机构调整逻辑匹配模式配置"),
    AUTHKEY("授权秘钥"),AUTHPUBKEY("公钥"),AUTHPRIKEY("私钥"),
    ;



    public String  msg;

    DictGroup(String s){
        msg = s;
    }


}
