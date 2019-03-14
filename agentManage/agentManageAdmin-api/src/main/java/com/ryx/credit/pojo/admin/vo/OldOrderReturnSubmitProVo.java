package com.ryx.credit.pojo.admin.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.BiConsumer;

/**
 * 作者：cx
 * 时间：2019/3/6
 * 描述：
 */
public class OldOrderReturnSubmitProVo implements Serializable {

    private String proId,proName,proType,snStart,snEnd;
    BigDecimal returnCount;

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getSnStart() {
        return snStart;
    }

    public void setSnStart(String snStart) {
        this.snStart = snStart;
    }

    public String getSnEnd() {
        return snEnd;
    }

    public void setSnEnd(String snEnd) {
        this.snEnd = snEnd;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public BigDecimal getReturnCount() {
        return returnCount;
    }

    public void setReturnCount(BigDecimal returnCount) {
        this.returnCount = returnCount;
    }
}
