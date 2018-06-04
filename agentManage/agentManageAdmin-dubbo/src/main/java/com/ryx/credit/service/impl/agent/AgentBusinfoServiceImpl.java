package com.ryx.credit.service.impl.agent;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ryx.credit.pojo.admin.agent.AgentBusInfoExample;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusinessStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.dict.IdService;

/**
 * 业务服务类
 * Created by cx on 2018/5/23.
 */
@Service("agentBusinfoService")
public class AgentBusinfoServiceImpl implements AgentBusinfoService {

    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;

    @Autowired
    private IdService idService;
    /**
     * 代理商查询插件数据获取
     * @param par
     * @return
     */
    @Override
    public PageInfo agentBusInfoSelectViewList(Map par, PageInfo page){
        int count = agentBusInfoMapper.queryAgentBusListCount(par);
        par.put("page",page);
        List<Map<String,Object>> list = agentBusInfoMapper.queryAgentBusList(par);
        page.setTotal(count);
        page.setRows(list);
        return page;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void agentBusInfoInsert(AgentBusInfo agentBusInfo) throws Exception{
    		if(agentBusInfo == null ||
        			StringUtils.isEmpty(agentBusInfo.getAgentId()) ||
        			StringUtils.isEmpty(agentBusInfo.getBusPlatform()) ||
        			StringUtils.isEmpty(agentBusInfo.getBusType()) ||
        			StringUtils.isEmpty(agentBusInfo.getBusRegion()) ||
        			null == agentBusInfo.getBusSentDirectly() ||
        			null == agentBusInfo.getBusDirectCashback() ||
        			StringUtils.isEmpty(agentBusInfo.getBusContact()) ||
        			StringUtils.isEmpty(agentBusInfo.getBusContactMobile()) ||
        			StringUtils.isEmpty(agentBusInfo.getBusContactEmail()) ||
        			StringUtils.isEmpty(agentBusInfo.getBusContactPerson()) ||
        			StringUtils.isEmpty(agentBusInfo.getBusRiskEmail()) ||
        			null == agentBusInfo.getCloTaxPoint() ||
        			null == agentBusInfo.getCloInvoice() ||
        			null == agentBusInfo.getCloReceipt() ||
        			StringUtils.isEmpty(agentBusInfo.getcUser())
        			){
                throw new ProcessException("业务数据不完整");
        	}
        	agentBusInfo.setId(idService.genId(TabId.a_agent_businfo));
        	agentBusInfo.setcTime(new Date());
        	agentBusInfo.setcUtime(agentBusInfo.getcTime());
        	agentBusInfo.setBusStatus(BusinessStatus.Enabled.status);
        	agentBusInfo.setCloReviewStatus(AgStatus.Create.status);
        	agentBusInfo.setStatus(Status.STATUS_1.status);
			agentBusInfo.setVersion(Status.STATUS_1.status);
        	agentBusInfoMapper.insert(agentBusInfo);

    }


	@Override
	public List<AgentBusInfo> agentBusInfoList(String agentId) {
		AgentBusInfoExample example = new AgentBusInfoExample();
		example.or().andAgentIdEqualTo(agentId).andStatusEqualTo(Status.STATUS_1.status);
		return agentBusInfoMapper.selectByExample(example);
	}

	public List<AgentBusInfo> agentBusInfoList(String agentId, String id, BigDecimal appStatus) {
		AgentBusInfoExample example = new AgentBusInfoExample();
		AgentBusInfoExample.Criteria c = example.or().andStatusEqualTo(Status.STATUS_1.status);
		if(StringUtils.isNotEmpty(agentId))c.andAgentIdEqualTo(agentId);
		if(StringUtils.isNotEmpty(id))c.andIdEqualTo(id);
		if(null!=appStatus)c.andCloReviewStatusEqualTo(appStatus);
		return agentBusInfoMapper.selectByExample(example);
	}


	public int updateAgentBusInfo(AgentBusInfo agentBusInfo){
    	return agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
	}

	public AgentBusInfo getById(String id){
		return agentBusInfoMapper.selectByPrimaryKey(id);
	}
}
