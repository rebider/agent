package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentContract;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.agent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by cx on 2018/5/28.
 */
@Service("agentEnterService")
public class AgentEnterServiceImpl implements AgentEnterService {

    private static Logger logger = LoggerFactory.getLogger(AgentEnterServiceImpl.class);

    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentContractService agentContractService;
    @Autowired
    private AccountPaidItemService accountPaidItemService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentColinfoService agentColinfoService;


    /**
     * 商户入网
     * @param agentVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO agentEnterIn(AgentVo agentVo) throws ProcessException {
        try {
            Agent agent = agentService.insertAgent(agentVo.getAgent(), agentVo.getAgentTableFile());
            agentVo.setAgent(agent);
            for (AgentContractVo item : agentVo.getContractVoList()) {
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                agentContractService.insertAgentContract(item, item.getContractTableFile());
            }
            for (CapitalVo item : agentVo.getCapitalVoList()) {
                item.setcAgentId(agent.getId());
                accountPaidItemService.insertAccountPaid(item, item.getCapitalTableFile(), agentVo.getAgent().getcUser());
            }
            for (AgentColinfoVo item : agentVo.getColinfoVoList()) {
                item.setAgentId(agent.getId());
                item.setcUser(agent.getcUser());
                agentColinfoService.agentColinfoInsert(item,item.getColinfoTableFile());
            }
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                agentBusinfoService.agentBusInfoInsert(item);
            }
            return ResultVO.success(agentVo);
        }catch (Exception e){
            e.printStackTrace();
            throw new ProcessException("代理商信息录入失败");
        }
    }
}
