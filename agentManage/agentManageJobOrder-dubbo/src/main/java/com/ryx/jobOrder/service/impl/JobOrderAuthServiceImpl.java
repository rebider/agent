package com.ryx.jobOrder.service.impl;

import com.ryx.credit.common.enumc.QueryAcceptType;
import com.ryx.credit.pojo.admin.CResource;
import com.ryx.jobOrder.dao.JobOrderAuthMapper;
import com.ryx.jobOrder.service.JobOrderAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("jobOrderAuthService")
public class JobOrderAuthServiceImpl implements JobOrderAuthService {

    @Autowired
    private JobOrderAuthMapper jobOrderAuthMapper;

    @Override
    public List<Map<String,Object>> getReqJobOrderAuth(Long userId) {
        List<Map<String, Object>> reqJobOrderAuth = jobOrderAuthMapper.getReqJobOrderAuth(userId);
        return reqJobOrderAuth;
    }

    @Override
    public Map<String, Object> getJobOrderType(String JobOrderKey) {
        Map<String, Object> acceptCode = jobOrderAuthMapper.getAcceptCode(JobOrderKey);
        return acceptCode;
    }

    @Override
    public Map<String, Object> getAcceptGroup(String userId) {
        Map<String, Object> acceptCode = jobOrderAuthMapper.getAcceptGroup(userId);
        return acceptCode;
    }

    @Override
    public List<Map<String, Object>> getAllAcceptGroup() {
        List<Map<String, Object>> allAcceptGroup = jobOrderAuthMapper.getAllAcceptGroup();
        return allAcceptGroup;
    }

    @Override
    public Map<String, Object> getAcceptInfo(String code,String quertTYpe) {
        Map<String,Object> acceptInfo = null;
        if (quertTYpe.equals(QueryAcceptType.userId.code)){
            acceptInfo = jobOrderAuthMapper.getAcceptByuserid(code);
        }else if (quertTYpe.equals(QueryAcceptType.acceCode.code)){
            acceptInfo = jobOrderAuthMapper.getAcceptByAcceptCode(code);
        }
        return acceptInfo;
    }

}
