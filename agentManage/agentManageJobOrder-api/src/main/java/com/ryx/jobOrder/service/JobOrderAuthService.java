package com.ryx.jobOrder.service;

import java.util.List;
import java.util.Map;

public interface JobOrderAuthService {
    /**
     * 根据用户ID查找用户的工单类型
     * @param userId
     * @return
     */
    Map<String,Object> getReqJobOrderAuth(Long userId);

    /**
     * 根据工单类型查找对应工单组
     * @param JobOrderKey
     * @return
     */
    Map<String,Object> getJobOrderType(String JobOrderKey);

}
