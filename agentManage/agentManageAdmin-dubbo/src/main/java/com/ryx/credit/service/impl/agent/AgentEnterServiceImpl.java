package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;

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
    @Autowired
    private ActivityService activityService;
    @Autowired
    private BusActRelMapper busActRelMapper;


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
                AgentResult res = accountPaidItemService.insertAccountPaid(item, item.getCapitalTableFile(), agentVo.getAgent().getcUser());
                if(!res.isOK()){
                    throw new ProcessException("添加交款项异常");
                }
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


    /**
     * 启动代理商审批
     * @param agentId
     * @param cuser
     * @return
     * @throws ProcessException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO startAgentEnterActivity(String agentId,String cuser)throws ProcessException{

        //检查是否有审批中的代理商新
        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(agentId).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        if(busActRelMapper.selectByExample(example).size()>0){
            return ResultVO.fail("代理商审批中，禁止重复提交审批");
        }

        Agent agent = agentService.getAgentById(agentId);
        if(agent.getAgStatus().equals(AgStatus.Approving.name())){
            return ResultVO.fail("代理商审批中，禁止重复提交审批");
        }
        if(!agent.getStatus().equals(Status.STATUS_1.status)){
            return ResultVO.fail("代理商信息已失效");
        }

        //启动审批
        String proce = activityService.createDeloyFlow(null, AppConfig.getProperty("agent_net_in_activity"),null,null);
        if(proce==null){
            throw new ProcessException("审批流启动失败!");
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(agentId);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.Agent.name());
        record.setActivStatus(AgStatus.Approving.name());
        if(1!=busActRelMapper.insertSelective(record)){
            throw new ProcessException("启动审批异常，添加审批关系失败");
        }
        //更新代理商审批中
        agent.setAgStatus(AgStatus.Approving.name());
        if(1!=agentService.updateAgent(agent)){
            throw new ProcessException("启动审批异常，更新代理商基本信息失败");
        }

        //获取代理商有效的业务
        List<AgentBusInfo> aginfo = agentBusinfoService.agentBusInfoList(agent.getId());
        for (AgentBusInfo agentBusInfo : aginfo) {
            agentBusInfo.setcUtime(record.getcTime());
            agentBusInfo.setCloReviewStatus(AgStatus.Approving.status);
            if(agentBusinfoService.updateAgentBusInfo(agentBusInfo)!=1){
                throw new ProcessException("启动审批异常，更新业务本信息失败");
            }
        }

        return ResultVO.success(null);
    }


    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO startAgentBusiEnterActivity(String busid,String cuser)throws ProcessException{
        AgentBusInfo abus = agentBusinfoService.getById(busid);
        if(abus==null){
            return ResultVO.fail("业务信息未找到");
        }
        //检查是否有审批中的代理商新
        Agent agent = agentService.getAgentById(abus.getAgentId());
        if(agent.getAgStatus().equals(AgStatus.Approving.name())){
            return ResultVO.fail("代理商信息审批中禁止启动业务审批");
        }
        if(!agent.getAgStatus().equals(AgStatus.Approved.name())){
            return ResultVO.fail("代理商信息未审批完成启动业务审批");
        }
        if(!agent.getStatus().equals(Status.STATUS_1.status)){
            return ResultVO.fail("代理商信息已失效");
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, AppConfig.getProperty("agent_net_in_activity"),null,null);
        if(proce==null){
            throw new ProcessException("审批流启动失败!");
        }

        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(abus.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.Business.name());
        record.setActivStatus(AgStatus.Approving.name());
        if(1!=busActRelMapper.insertSelective(record)){
            throw new ProcessException("启动审批异常，添加审批关系失败");
        }
        //获取代理商有效的业务
        abus.setcUtime(record.getcTime());
        abus.setCloReviewStatus(AgStatus.Approving.status);
        if(agentBusinfoService.updateAgentBusInfo(abus)!=1){
            throw new ProcessException("启动审批异常，更新业务本信息失败");
        }
        return ResultVO.success(null);
    }




}