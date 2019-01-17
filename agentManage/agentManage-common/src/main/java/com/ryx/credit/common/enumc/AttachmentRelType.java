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
    agentMerge("代理商合并");

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
