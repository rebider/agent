package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by cx on 2018/5/22.
 */
public enum DictGroup {


    AGENT("代理商模块"),CAPITAL_TYPE("交款类型"),CONTRACT_TYPE("合同类型"),CERT_TYPE("证件类型"),AGNATURE_TYPE("公司类型");


    public String  msg;

    DictGroup(String s){
        msg = s;
    }


}
