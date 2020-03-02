package com.ryx.jobOrder.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JobOrderAuthMapper {
    List<Map<String,Object>> getReqJobOrderAuth(@Param("userId") Long userId);
    Map<String,Object> getAcceptCode(@Param("jobOrderType") String jobOrderType);
    Map<String,Object> getAcceptGroup(@Param("userId") String userId);
}
