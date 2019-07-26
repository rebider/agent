package com.ryx.credit.common.enumc;

/**
 * Created by RYX on 2018/9/5.
 * 附件类型
 */
public enum AttDataTypeStatic {

//    "SFZZM":{key:"SFZZM",name:"身份证正面"},
//            "YYZZ":{key:"YYZZ",name:"营业执照"},
//            "YHKSMJ":{key:"YHKSMJ",name:"银行卡扫描件"},
//            "KHXUZ":{key:"KHXUZ",name:"开户许可证"}

    SFZZM("SFZZM", "身份证正面"),
    YYZZ("YYZZ", "营业执照"),
    YHKSMJ("YHKSMJ", "银行卡扫描件"),
    KHXUZ("KHXUZ", "开户许可证"),
    YBNSRZM("YBNSRZM", "一般纳税人证明"),
    ZHBGB("ZHBGB", "账户变更表"),
    JSRSFZ("JSRSFZ", "结算人身份证"),
    FFRSQS("FFRSQS", "非法人授权书");


    public String code;

    public String msg;

    AttDataTypeStatic(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
