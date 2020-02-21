package com.ryx.jobOrder.dao;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface JobOrderAuthMapper {
    Map<String,Object> getReqJobOrderAuth(Long userId);
    Map<String,Object> getAcceptCode(@Param("jobOrderType") String jobOrderType);
}
