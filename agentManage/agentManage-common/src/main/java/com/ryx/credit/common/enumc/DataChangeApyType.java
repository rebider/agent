package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by cx on 2018/6/6.
 */
public enum DataChangeApyType {
    //数据修改申请类型需要加入到BusActRelBusType枚举中,审批关系表中包含此数据修改申请类型，需要加入到字典表 DATA_CHANGE_TYPE  DATA_CACTIVITY_TYPE BusActRelBusType 三个字段中，
    DC_Agent("代理商"),DC_Colinfo("代理商账户修改申请");

    public String  msg;

    DataChangeApyType(String s){
        msg = s;
    }
}
