package com.ryx.credit.profit.pojo;

public class PmsProfitTempWithBLOBs extends PmsProfitTemp {
    private String sheetHead;

    private String sheetData;

    public String getSheetHead() {
        return sheetHead;
    }

    public void setSheetHead(String sheetHead) {
        this.sheetHead = sheetHead == null ? null : sheetHead.trim();
    }

    public String getSheetData() {
        return sheetData;
    }

    public void setSheetData(String sheetData) {
        this.sheetData = sheetData == null ? null : sheetData.trim();
    }
}