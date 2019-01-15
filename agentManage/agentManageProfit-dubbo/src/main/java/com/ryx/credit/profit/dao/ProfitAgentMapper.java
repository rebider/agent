package com.ryx.credit.profit.dao;

import java.util.Map;

/**
 * @Author: Zhang Lei
 * @Description:
 * @Date: 14:01 2019/1/14
 */
public interface ProfitAgentMapper {

    Map<String,Object> getAgentWithParentInfo(Map<String,Object> params);
}
