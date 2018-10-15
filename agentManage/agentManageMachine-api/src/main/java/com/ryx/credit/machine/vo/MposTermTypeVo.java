package com.ryx.credit.machine.vo;

import java.io.Serializable;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述：
 */
public class MposTermTypeVo implements Serializable {

    private String termTypeId;

    private String termTypeName;

    public String getTermTypeId() {
        return termTypeId;
    }

    public void setTermTypeId(String termTypeId) {
        this.termTypeId = termTypeId;
    }

    public String getTermTypeName() {
        return termTypeName;
    }

    public void setTermTypeName(String termTypeName) {
        this.termTypeName = termTypeName;
    }
}
