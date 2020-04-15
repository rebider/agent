package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.order.OrderAdjAccount;

import java.math.BigDecimal;
import java.util.Date;

public class OrderAdjAccountVo extends OrderAdjAccount {
    private String accountFile;

    public String getAccountFile() {
        return accountFile;
    }

    public void setAccountFile(String accountFile) {
        this.accountFile = accountFile;
    }
}