package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.order.OrgPlatform;
import com.ryx.credit.pojo.admin.order.Organization;

import java.util.List;

/**
 * @Auther: lrr
 * @Date: 2019/6/14 13:55
 * @Description:
 */
public class OorganizationVo extends Organization {
    private List<String> organizationbleFile;

    private List<OrgPlatform> orgPlatform;


    public List<String> getOrganizationbleFile() {
        return organizationbleFile;
    }

    public void setOrganizationbleFile(List<String> organizationbleFile) {
        this.organizationbleFile = organizationbleFile;
    }

    public List<OrgPlatform> getOrgPlatform() {
        return orgPlatform;
    }

    public void setOrgPlatform(List<OrgPlatform> orgPlatform) {
        this.orgPlatform = orgPlatform;
    }
}