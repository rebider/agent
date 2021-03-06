package com.ryx.jobOrder.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JobOrderAuthMapper {
    List<Map<String,Object>> getReqJobOrderAuth(@Param("userId") Long userId);
    Map<String,Object> getAcceptCode(@Param("jobOrderType") String jobOrderType);
    Map<String,Object> getAcceptGroup(@Param("userId") String userId);
    Map<String,Object> getAcceptGroupAgent();
    List<Map<String,Object>> getAllAcceptGroup();
    Map<String,Object> getAcceptByAcceptCode(@Param("acceptCode") String acceptCode);
    Map<String,Object> getAcceptByuserid(@Param("userId") String userID);
    List<Map<String,Object>> getViewJobKeyManageNodesByUserId(@Param("userId") String userId);
    List<Map<String,Object>> getViewJobKeyManageModesByAgent();
}
