package com.ryx.credit.pojo.admin.agent;

import java.io.Serializable;
import java.math.BigDecimal;

public class Dict extends DictKey implements Serializable {
    private String dItemname;

    private String dItemnremark;

    private BigDecimal dSort;

    private BigDecimal dStatus;

    public String getdItemname() {
        return dItemname;
    }

    public void setdItemname(String dItemname) {
        this.dItemname = dItemname == null ? null : dItemname.trim();
    }

    public String getdItemnremark() {
        return dItemnremark;
    }

    public void setdItemnremark(String dItemnremark) {
        this.dItemnremark = dItemnremark == null ? null : dItemnremark.trim();
    }

    public BigDecimal getdSort() {
        return dSort;
    }

    public void setdSort(BigDecimal dSort) {
        this.dSort = dSort;
    }

    public BigDecimal getdStatus() {
        return dStatus;
    }

    public void setdStatus(BigDecimal dStatus) {
        this.dStatus = dStatus;
    }
}