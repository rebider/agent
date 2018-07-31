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
    ActivityEdit("活动变更附件"),
    Clear("结算附件"),
    Return("退货打款附件");;

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
