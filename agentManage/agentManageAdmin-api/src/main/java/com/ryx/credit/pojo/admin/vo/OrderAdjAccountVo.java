package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.pojo.admin.order.OrderAdjAccount;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderAdjAccountVo extends OrderAdjAccount implements Serializable {
    private String accountFile;

    private List<Attachment> attachments;

    private List<Attachment> tkattachments;

    public String getAccountFile() {
        return accountFile;
    }

    public void setAccountFile(String accountFile) {
        this.accountFile = accountFile;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Attachment> getTkattachments() {
        return tkattachments;
    }

    public void setTkattachments(List<Attachment> tkattachments) {
        this.tkattachments = tkattachments;
    }
}