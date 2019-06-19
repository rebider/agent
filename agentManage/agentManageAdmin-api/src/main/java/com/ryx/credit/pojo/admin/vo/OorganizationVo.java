package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.order.Organization;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: lrr
 * @Date: 2019/6/14 13:55
 * @Description:
 */
public class OorganizationVo extends Organization {
    private List<String> organizationbleFile;

    public List<String> getOrganizationbleFile() {
        return organizationbleFile;
    }

    public void setOrganizationbleFile(List<String> organizationbleFile) {
        this.organizationbleFile = organizationbleFile;
    }
}