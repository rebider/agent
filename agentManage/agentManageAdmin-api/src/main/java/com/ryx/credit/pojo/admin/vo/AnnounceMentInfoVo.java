package com.ryx.credit.pojo.admin.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ryx.credit.pojo.admin.agent.AnnoPlatformRela;
import com.ryx.credit.pojo.admin.agent.AnnounceMentInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @program: AnnounceMentInfoVo
 * @description:
 * @author: ssx
 * @create: 2019-10-10 10:20
 **/
public class AnnounceMentInfoVo extends AnnounceMentInfo {
    private List<String> busTypes;
    private List<String> plats;
    private String orgs;
    private List<String> attFiles;

    public List<String> getBusTypes() {
        return busTypes;
    }

    public void setBusTypes(List<String> busTypes) {
        this.busTypes = busTypes;
    }

    public List<String> getPlats() {
        return plats;
    }

    public void setPlats(List<String> plats) {
        this.plats = plats;
    }

    public String getOrgs() {
        return orgs;
    }

    public void setOrgs(String orgs) {
        this.orgs = orgs;
    }

    public List<String> getAttFiles() {
        return attFiles;
    }

    public void setAttFiles(List<String> attFiles) {
        this.attFiles = attFiles;
    }
}
