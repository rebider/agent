package com.ryx.jobOrder.service;

import com.ryx.credit.pojo.admin.CResource;

import java.util.Map;

public interface JobOrderAuthService {
    CResource getReqJobOrderAuth(Long userId);
    Map<String,Object> getJobOrderType(String JobOrderKey);
}
