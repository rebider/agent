package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.RegExpression;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentFreezeMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentFreeze;
import com.ryx.credit.pojo.admin.agent.AgentFreezeExample;
import com.ryx.credit.pojo.admin.vo.AgentFreezePort;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentFreezeService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 *
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/10/10 9:14
 * @Param
 * @return
 **/
@Service("agentFreezeService")
public class AgentFreezeServiceImpl implements AgentFreezeService {

    @Autowired
    private AgentFreezeMapper agentFreezeMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private IUserService userService;

    @Override
    public PageInfo agentFreezeList(AgentFreeze agentFreeze, Page page){
        Map<String, Object> reqMap = JsonUtil.objectToMap(agentFreeze);
        List<Map<String, String>> resuleMaps = agentFreezeMapper.queryAgentFreezeList(reqMap,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(resuleMaps);
        pageInfo.setTotal(agentFreezeMapper.queryAgentFreezeCount(reqMap));
        return pageInfo;
    }


    /**
     * 冻结
     * @param agentFreezePort
     * @return
     * @throws MessageException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult agentFreeze(AgentFreezePort agentFreezePort)throws MessageException{
        AgentResult verify = verify(agentFreezePort,FreeStatus.DJ.getValue());
        if(!verify.isOK()){
            return verify;
        }
        AgentFreezeExample agentFreezeExample = new AgentFreezeExample();
        AgentFreezeExample.Criteria criteria = agentFreezeExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andAgentIdEqualTo(agentFreezePort.getAgentId());
        criteria.andFreezeCauseEqualTo(agentFreezePort.getFreezeCause());
        criteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
        List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(agentFreezeExample);
        if(agentFreezes.size()!=0){
            return AgentResult.fail("代理商此原因已被冻结");
        }
        Map<String,Object> dataMap = (Map<String,Object>)verify.getData();
        Agent agent = (Agent)dataMap.get("agent");
        if(agent.getFreestatus().compareTo(FreeStatus.DJ.getValue())!=0){
            agent.setFreestatus(FreeStatus.DJ.getValue());
            int i = agentMapper.updateByPrimaryKeySelective(agent);
            if(i!=1){
                throw new MessageException("代理商冻结更新代理商信息失败");
            }
        }
        AgentFreeze agentFreeze = new AgentFreeze();
        agentFreeze.setId(idService.genId(TabId.a_agent_freeze));
        agentFreeze.setAgentId(agentFreezePort.getAgentId());
        agentFreeze.setFreezeStatus(FreeStatus.DJ.getValue().toString());
        agentFreeze.setFreezeCause(agentFreezePort.getFreezeCause());
        agentFreeze.setFreezeDate(new Date());
        agentFreeze.setFreezePerson(agentFreezePort.getOperationPerson());
        agentFreeze.setRemark(agentFreezePort.getRemark());
        agentFreeze.setStatus(Status.STATUS_1.status);
        agentFreeze.setVersion(BigDecimal.ONE);
        agentFreezeMapper.insert(agentFreeze);
        return AgentResult.ok();
    }


    /**
     * 解冻
     * @param agentFreezePort
     * @return
     * @throws MessageException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult agentUnFreeze(AgentFreezePort agentFreezePort)throws MessageException{

        AgentResult verify = verify(agentFreezePort,FreeStatus.JD.getValue());
        if(!verify.isOK()){
            return verify;
        }
        if(StringUtils.isBlank(agentFreezePort.getUnfreezeCause())){
            return AgentResult.fail("解冻原因必填");
        }

        AgentFreezeExample freezeExample = new AgentFreezeExample();
        AgentFreezeExample.Criteria freezeCriteria = freezeExample.createCriteria();
        freezeCriteria.andStatusEqualTo(Status.STATUS_1.status);
        freezeCriteria.andAgentIdEqualTo(agentFreezePort.getAgentId());
        freezeCriteria.andFreezeCauseEqualTo(agentFreezePort.getFreezeCause());
        freezeCriteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
        List<AgentFreeze> agentFreezeList = agentFreezeMapper.selectByExample(freezeExample);
        if(agentFreezeList.size()==0){
            return AgentResult.fail("解冻信息不存在");
        }
        if(agentFreezeList.size()!=1){
            return AgentResult.fail("解冻信息不唯一,请联系管理员");
        }
        AgentFreeze agentFreeze = agentFreezeList.get(0);
        agentFreeze.setUnfreezePerson(agentFreezePort.getOperationPerson());
        agentFreeze.setUnfreezeDate(new Date());
        agentFreeze.setUnfreezeCause(agentFreezePort.getUnfreezeCause());
        int j = agentFreezeMapper.updateByPrimaryKeySelective(agentFreeze);
        if(j!=1){
            throw new MessageException("更新解冻失败");
        }

        List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(freezeExample);
        //没有冻结的 更新代理商状态为解冻
        if(agentFreezes.size()==0){
            Map<String,Object> dataMap = (Map<String,Object>)verify.getData();
            Agent agent = (Agent)dataMap.get("agent");
            agent.setFreestatus(FreeStatus.JD.getValue());
            int i = agentMapper.updateByPrimaryKeySelective(agent);
            if(i!=1){
                throw new MessageException("更新代理商信息解冻失败");
            }
        }
        return AgentResult.ok();
    }

    /**
     * 验证
     * @param agentFreezePort
     * @param freeStatus
     * @return
     */
    private AgentResult verify(AgentFreezePort agentFreezePort,BigDecimal freeStatus){
        AgentResult agentResult = AgentResult.fail();
        if(StringUtils.isBlank(agentFreezePort.getAgentId())){
            agentResult.setMsg("请填写代理商编码");
            return agentResult;
        }
        if(StringUtils.isBlank(FreeCause.getContentByValue(agentFreezePort.getFreezeCause()))){
            agentResult.setMsg("未知的冻结原因");
            return agentResult;
        }
        if(freeStatus.compareTo(FreeStatus.DJ.getValue())==0 && agentFreezePort.getFreezeCause().equals(FreeCause.QTDJ.getValue())){
            if(StringUtils.isBlank(agentFreezePort.getRemark())){
                agentResult.setMsg("冻结原因是其他原因,备注必填");
                return agentResult;
            }
        }
        if(StringUtils.isBlank(agentFreezePort.getFreezeNum())){
            agentResult.setMsg("请填写请求数据编号");
            return agentResult;
        }
        if(StringUtils.isBlank(agentFreezePort.getOperationPerson())){
            agentResult.setMsg("请填写操作人");
            return agentResult;
        }
        if(!RegExpression.regNumber(agentFreezePort.getOperationPerson())){
            agentResult.setMsg("非法操作人");
            return agentResult;
        }
        CUser cUser = userService.selectById(Long.valueOf(agentFreezePort.getOperationPerson()));
        if(null==cUser){
            agentResult.setMsg("操作人不存在");
            return agentResult;
        }
        Agent agent = agentMapper.selectByPrimaryKey(agentFreezePort.getAgentId());
        if(agent==null){
            agentResult.setMsg("代理商信息不存在");
            return agentResult;
        }
        if(!agent.getAgStatus().equals(AgStatus.Approved.name())){
            agentResult.setMsg("代理商未通过审批");
            return agentResult;
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("agent",agent);
        resultMap.put("cUser",cUser);
        return AgentResult.ok(resultMap);
    }
}
