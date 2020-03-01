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

    public List<JobKeyManageVo> getJobKeyManageVoList() {
        return jobKeyManageVoList;
    }

    public void setJobKeyManageVoList(List<JobKeyManageVo> jobKeyManageVoList) {
        this.jobKeyManageVoList = jobKeyManageVoList;
    }
}