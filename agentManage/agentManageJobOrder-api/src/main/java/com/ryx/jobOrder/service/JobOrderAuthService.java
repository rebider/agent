package com.ryx.jobOrder.service;

import com.ryx.jobOrder.vo.JobKeyManageNodeVo;

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

    /**
     * 获取全部受理组
     * @return
     */
    List<Map<String,Object>> getAllAcceptGroup();

    /**
     *
     * @param code 查询条件code
     * @param queryType  查询条件类型:0-用户id,1-受理组 enumc:QueryAcceptType
     * @return
     */
    Map<String,Object> getAcceptInfo(String code,String queryType);

    /**
     * 获取用户可以申请的工单类型
     * @param userId
     * @return
     */
    List<JobKeyManageNodeVo> getViewJobKeyManageNodes(String userId);

    /**
     *
     * @param code 查询是否是内部部门 区分省区与内部部门
     * @return
     */
    Boolean isInnerDept(String code);
}
