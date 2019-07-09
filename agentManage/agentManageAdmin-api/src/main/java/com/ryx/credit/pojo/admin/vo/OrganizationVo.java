package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.order.OrgPlatform;
import com.ryx.credit.pojo.admin.order.Organization;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: lrr
 * @Date: 2019/6/18 20:21
 * @Description:
 */
public class OrganizationVo implements Serializable{

    private Organization organization;
    private List<String> organizatioTableFile;
    private List<OrgPlatform> orgPlatform;
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<String> getOrganizatioTableFile() {
        return organizatioTableFile;
    }

    public void setOrganizatioTableFile(List<String> organizatioTableFile) {
        this.organizatioTableFile = organizatioTableFile;
    }

    public List<OrgPlatform> getOrgPlatform() {
        return orgPlatform;
    }

    public void setOrgPlatform(List<OrgPlatform> orgPlatform) {
        this.orgPlatform = orgPlatform;
    }
}