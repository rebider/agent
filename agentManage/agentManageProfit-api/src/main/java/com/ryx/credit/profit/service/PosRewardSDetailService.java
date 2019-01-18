package com.ryx.credit.profit.service;


import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.PosRewardDetail;

import java.util.List;
import java.util.Map;


public interface PosRewardSDetailService {
    PageInfo getRewardDetailList(Map<String, Object> param, PageInfo pageInfo);

    void insert(PosRewardDetail posrewardDetail);

    PosRewardDetail getPosRewardDetail(PosRewardDetail posRewardDetail);

    void updatePosRewardDetail(PosRewardDetail posRewardDetail);

    void clearPosRewardDetail(String currentDate);

    void deleteCurrentDate(String currentDate);

    /**
     * 一次性查询出代理商的所有下级
     * 目前只有3级，后续存在4、5级，在修改SQL
     * @param agentId
     * @return
     */
    List<String> queryChildLevelByAgentId(String agentId);

    /**
     * 获取上级代理商agentId
     * @param agentId
     * @return
     */
    String getSuperAgentId(String agentId);

    List<PosRewardDetail> getPosRewardDetailList(PosRewardDetail posRewardDetail, List<String> type, List<String> childAgentList);

    Map<String,Object> profitCount(Map<String,Object> param);
}
