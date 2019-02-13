package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Capital;

import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/10/11
 * 保证金
 */
public interface CapitalService {

    List<Capital> queryCapital(String agentId);

    PageInfo getCapitalSummaryList(Map<String,Object> param, PageInfo pageInfo);
}
