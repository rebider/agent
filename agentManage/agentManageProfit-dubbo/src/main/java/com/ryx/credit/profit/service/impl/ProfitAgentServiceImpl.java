package com.ryx.credit.profit.service.impl;

import com.ryx.credit.profit.dao.ProfitAgentMapper;
import com.ryx.credit.profit.service.IProfitAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: Zhang Lei
 * @Description:
 * @Date: 13:51 2019/1/14
 */
@Service("profitAgentService")
public class ProfitAgentServiceImpl implements IProfitAgentService {
    @Autowired
    ProfitAgentMapper profitAgentMapper;

    /**
     * @Author: Zhang Lei
     * @Param agentId 代理商AG码
     * @Param platformNum 业务平台编号
     *
     * @Description: 查询代理商在某个平台下与其上级代理商信息
     * @Date: 13:54 2019/1/14
     */
    @Override
    public Map<String, Object> getAgentWithParentInfo(Map<String, Object> params) {
        Map<String, Object> result = profitAgentMapper.getAgentWithParentInfo(params);
        return result;
    }
}
