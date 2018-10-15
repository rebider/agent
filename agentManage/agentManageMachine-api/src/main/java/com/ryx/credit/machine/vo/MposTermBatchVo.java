package com.ryx.credit.machine.vo;

import java.io.Serializable;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述：手刷终端批次对象
 */
public class MposTermBatchVo implements Serializable {

    private String batchId;

    private String batchName;

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }
}
