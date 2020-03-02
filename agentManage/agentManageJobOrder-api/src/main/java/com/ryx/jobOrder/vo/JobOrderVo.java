package com.ryx.jobOrder.vo;

import com.ryx.credit.pojo.admin.vo.OorganizationVo;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: lrr
 * @Date: 2020/2/28 15:08
 * @Description:
 */
public class JobOrderVo implements Serializable {
    private List<JobKeyManageVo> jobKeyManageVoList;
    private String firstId;
    private String secondId;
    private String threeId;

    public List<JobKeyManageVo> getJobKeyManageVoList() {
        return jobKeyManageVoList;
    }

    public void setJobKeyManageVoList(List<JobKeyManageVo> jobKeyManageVoList) {
        this.jobKeyManageVoList = jobKeyManageVoList;
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public String getThreeId() {
        return threeId;
    }

    public void setThreeId(String threeId) {
        this.threeId = threeId;
    }
}