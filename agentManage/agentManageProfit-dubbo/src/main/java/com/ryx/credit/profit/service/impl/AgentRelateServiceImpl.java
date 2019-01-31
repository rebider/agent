package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.AgentRelateMapper;
import com.ryx.credit.profit.pojo.AgentRelate;
import com.ryx.credit.profit.pojo.AgentRelateExample;
import com.ryx.credit.profit.service.IAgentRelateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/***
 * @author renshenghao
 * @version 1.0
 * @
 */
@Service("agentRelateService")
public class AgentRelateServiceImpl implements IAgentRelateService {

    Logger logger = LoggerFactory.getLogger(AgentRelateServiceImpl.class);
    @Autowired
    private AgentRelateMapper agentRelateMapper;


    @Override
    public PageInfo getList(Map<String, Object> param, PageInfo pageInfo) {
        List<Map<String,Object>> list=agentRelateMapper.getList(param);
        pageInfo.setRows(list);
        AgentRelateExample example=new AgentRelateExample();
        AgentRelateExample.Criteria criteria=example.createCriteria();

        Object agentId=param.get("agentId");
        Object agentName=param.get("agentName");
        Object status=param.get("status");
        if(agentId!=null&&StringUtils.isNotBlank(agentId.toString())){
            criteria.andAgentIdEqualTo(agentId.toString());
        }
        if (agentName!=null&&StringUtils.isNotBlank(agentName.toString())) {
            criteria.andAgentIdEqualTo(agentName.toString());
        }
        if (status!=null&&StringUtils.isNotBlank(status.toString())){
            criteria.andStatusEqualTo(new BigDecimal(status.toString()));
        }
        long count=agentRelateMapper.countByExample(example);
        pageInfo.setTotal((int)count);
        return pageInfo;
    }

    @Override
    public int insertAgentRelate(AgentRelate agentRelate) {
        return agentRelateMapper.insert(agentRelate);
    }

    @Override
    public int updateAgentRelate(AgentRelate agentRelate) {
        return agentRelateMapper.updateByPrimaryKey(agentRelate);
    }

    @Override
    public List<AgentRelate> selectByExample(AgentRelateExample agentRelateExample) {
        return agentRelateMapper.selectByExample(agentRelateExample);
    }

    @Override
    public AgentRelate selectById(String id) {
        return agentRelateMapper.selectByPrimaryKey(id);
    }
}
