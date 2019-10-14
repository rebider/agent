package com.ryx.credit.pojo.admin.vo;

import com.ryx.credit.pojo.admin.COrganization;

import java.io.Serializable;
import java.util.List;

/**
 * @program: agentManage
 * @description:
 * @author: ssx
 * @create: 2019-10-11 12:26
 **/
public class COrganizationVo implements Serializable {
    private String pid;
    private String pname;
    private List<COrganization> cOrganizations;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public List<COrganization> getcOrganizations() {
        return cOrganizations;
    }

    public void setcOrganizations(List<COrganization> cOrganizations) {
        this.cOrganizations = cOrganizations;
    }
}
