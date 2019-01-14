package com.ryx.credit.profit.service;

import java.util.Map;

/**
 * @Author: Zhang Lei
 * @Description: 代理商信息查询通用服务
 * @Date: 13:50 2019/1/14
 */
public interface IProfitAgentService {

    Map<String, Object> getAgentWithParentInfo(Map<String, Object> params);

}
