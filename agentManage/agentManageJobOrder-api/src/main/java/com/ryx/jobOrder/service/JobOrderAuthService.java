package com.ryx.jobOrder.service;

import java.util.List;
import java.util.Map;

public interface JobOrderAuthService {
    /**
     * 根据用户ID查找用户的可以处理的工单类型
     * @param userId
     * @return
     */
    List<Map<String,Object>> getReqJobOrderAuth(Long userId);

    /**
     * 根据工单类型查找可以受理该工单的对应工单组
     * @param JobOrderKey
     * @return
     */
    Map<String,Object> getJobOrderType(String JobOrderKey);

    /**
     * 根据用户id获取用户的工单组
     * @param userId
     * @return
     */
    Map<String,Object> getAcceptGroup(String userId);

    List<Map<String,Object>> getAllAcceptGroup();
}
