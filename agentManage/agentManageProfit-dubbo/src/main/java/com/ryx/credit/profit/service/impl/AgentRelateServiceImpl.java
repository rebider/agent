package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.AgentRelateMapper;
import com.ryx.credit.profit.pojo.AgentRelate;
import com.ryx.credit.profit.pojo.AgentRelateDetail;
import com.ryx.credit.profit.pojo.AgentRelateExample;
import com.ryx.credit.profit.service.IAgentRelateService;
import com.ryx.credit.service.dict.IdService;
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
    @Autowired
    private IdService idService;


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

    @Override
    public Map<String, String> queryParentAgentByAgentId(String agentId) {
        return agentRelateMapper.queryParentAgentByAgentId(agentId);
    }

    @Override
    public boolean applyAgentRelate(AgentRelate agentRelate, List<AgentRelateDetail> list, String userId, String workId) {
        agentRelate.setId(idService.genId(TabId.A_AGENT_RELATE));
        logger.info("序列ID......"+idService.genId(TabId.A_AGENT_RELATE));
        for (AgentRelateDetail agentRelateDetail:list){
            agentRelateDetail.setId(idService.genId(TabId.A_AGENT_RELATE_DETAIL));
        }
        agentRelateMapper.insert(agentRelate);


        return false;
    }
}
