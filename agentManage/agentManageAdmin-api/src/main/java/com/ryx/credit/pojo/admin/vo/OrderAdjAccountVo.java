package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.order.OrderAdjAccount;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderAdjAccountVo extends OrderAdjAccount implements Serializable {
    private List<String> accountFile;

    public List<String> getAccountFile() {
        return accountFile;
    }

    public void setAccountFile(List<String> accountFile) {
        this.accountFile = accountFile;
    }
}