package com.ryx.credit.profit.pojo;
/**
 * 机构类型
 * */
public enum PosMechanismType {
    JG("JG", "机构"),
    JG_ONE("JG_ONE", "机构一代"),
    JG_STANDARD("JG_STANDARD", "标准一代");

    public String posMechanismType;

    public String msg;

    PosMechanismType(String posMechanismType, String msg) {
        this.posMechanismType = posMechanismType;
        this.msg = msg;
    }
}
