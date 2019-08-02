package com.ryx.credit.common.enumc;

/**
 * 附件类型
 * Created by cx on 2018/5/22.
 */
public enum AttachmentRelType {

    Agent("代理商附件"),
    Contract("合同附件"),
    Capital("缴款项"),
    Business("业务"),
    Proceeds("收款"),
    Order("订单"),
    ActivityEdit("退补差价代理商打款附件"),
    ActivityFinanceEdit("退补差价财务打款附件"),
    Clear("结算附件"),
    Return("退货打款附件"),
    ExitApplyfor("代理商退出申请"),
    agentMerge("代理商合并"),
    agentQuit("代理商退出申请"),
    agentQuitRefund("代理商退出申请退款"),
    agentQuitUpload("代理商退出上传解除合同"),
    capitalFinance("保证金变更申请财务上传附件"),
    capitalManage("保证金变更申请上传附件"),
    returnOrderInvoice("退货发票信息上传附件"),
    terminalTransfer("终端划拨上传附件"),
    Organization("机构管理上传附件"),
    internetRenew("物联网卡续费上传附件");

    public String  msg;

    AttachmentRelType(String s){
        msg = s;
    }

    public static String getItemsString(String key){
        AttachmentRelType[] valus = AttachmentRelType.values();
        for (AttachmentRelType busActRelBusType : valus) {
            if(busActRelBusType.name().equals(key)){
                return busActRelBusType.msg;
            }
        }
        return null;
    }

}
