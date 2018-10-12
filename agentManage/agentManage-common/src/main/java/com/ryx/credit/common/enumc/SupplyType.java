package com.ryx.credit.common.enumc;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/9/26
 * @Desc 代理商退出申请表的补款方式
 */
public enum SupplyType {

    FRDK("FRDK", "分润抵扣"),
    XXDK("XXDK", "线下打款"),
    FRDK_XXDK("FRDK_XXDK", "分润抵扣+线下打款");

    public String code;

    public String msg;

    SupplyType(String c, String m) {
        this.code = c;
        this.msg = m;
    }

    public static String getSupplyType(String value) {
        SupplyType[] types = SupplyType.values();
        for (SupplyType st : types) {
            if (st.code.equals(value)) {
                return st.msg;
            }
        }
        return "";
    }

    public static Map<String, Object> getResultMap() {
        SupplyType[] valus = values();
        Map<String, Object> resultMap = new HashMap();
        SupplyType[] var2 = valus;
        int var3 = valus.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            SupplyType supplyType = var2[var4];
            resultMap.put(supplyType.name(), supplyType.msg);
        }

        return resultMap;
    }

}
