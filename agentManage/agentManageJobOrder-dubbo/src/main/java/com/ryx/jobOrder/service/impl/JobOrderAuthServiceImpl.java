package com.ryx.jobOrder.service.impl;

import com.ryx.credit.pojo.admin.CResource;
import com.ryx.jobOrder.dao.JobOrderAuthMapper;
import com.ryx.jobOrder.service.JobOrderAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("jobOrderAuthService")
public class JobOrderAuthServiceImpl implements JobOrderAuthService {

    @Autowired
    private JobOrderAuthMapper jobOrderAuthMapper;

    @Override
    public CResource getReqJobOrderAuth(Long userId) {
        return null;
    }

    @Override
    public Map<String, Object> getJobOrderType(String JobOrderKey) {
        Map<String, Object> acceptCode = jobOrderAuthMapper.getAcceptCode(JobOrderKey);
        return acceptCode;
    }
}
