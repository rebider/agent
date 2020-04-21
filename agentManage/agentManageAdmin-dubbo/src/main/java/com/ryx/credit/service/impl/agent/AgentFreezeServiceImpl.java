package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/***
 * 代理商冻结
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/10/10 9:14
 * @Param
 * @return
 **/
@Service("agentFreezeService")
public class AgentFreezeServiceImpl implements AgentFreezeService {

    private static Logger log = LoggerFactory.getLogger(AgentEnterServiceImpl.class);
    @Autowired
    private AgentFreezeMapper agentFreezeMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisService redisService;


    @Override
    public PageInfo agentFreezeList(AgentFreeze agentFreeze, Page page){

        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(agentFreeze.getId())){
            reqMap.put("id",agentFreeze.getId());
        }
        if(StringUtils.isNotBlank(agentFreeze.getAgentId())){
            reqMap.put("agentId",agentFreeze.getAgentId());
        }
        if(StringUtils.isNotBlank(agentFreeze.getAgentName())){
            reqMap.put("agentName",agentFreeze.getAgentName());
        }
        if(StringUtils.isNotBlank(agentFreeze.getIncomTimeBegin())){
            reqMap.put("incomTimeBegin",agentFreeze.getIncomTimeBegin());
        }
        if(StringUtils.isNotBlank(agentFreeze.getIncomTimeEnd())){
            reqMap.put("incomTimeEnd",agentFreeze.getIncomTimeEnd());
        }
        if(StringUtils.isNotBlank(agentFreeze.getFreezeDateBegin())){
            reqMap.put("freezeDateBegin",agentFreeze.getFreezeDateBegin());
        }
        if(StringUtils.isNotBlank(agentFreeze.getFreezeDateEnd())){
            reqMap.put("freezeDateEnd",agentFreeze.getFreezeDateEnd());
        }
        if(StringUtils.isNotBlank(agentFreeze.getUnFreezeDateBegin())){
            reqMap.put("unFreezeDateBegin",agentFreeze.getUnFreezeDateBegin());
        }
        if(StringUtils.isNotBlank(agentFreeze.getUnFreezeDateEnd())){
            reqMap.put("unFreezeDateEnd",agentFreeze.getUnFreezeDateEnd());
        }
        if(StringUtils.isNotBlank(agentFreeze.getFreeStatus())){
            reqMap.put("freeStatus",agentFreeze.getFreeStatus());
        }
        if(StringUtils.isNotBlank(agentFreeze.getFreezeCause())){
            reqMap.put("freezeCause",agentFreeze.getFreezeCause());
        }
        if(StringUtils.isNotBlank(agentFreeze.getFreezeStatus())){
            reqMap.put("freezeStatus",agentFreeze.getFreezeStatus());
        }

        List<Map<String, String>> resultMaps = agentFreezeMapper.queryAgentFreezeList(reqMap,page);
        for (Map<String, String> resultMap : resultMaps) {
            resultMap.put("FREESTATUS_MSG",FreeStatus.getContentByValue(new BigDecimal(resultMap.get("FREESTATUS"))));
            resultMap.put("FREEZE_CAUSE_MSG",FreeCause.getContentByValue(resultMap.get("FREEZE_CAUSE")));
            resultMap.put("FREEZE_STATUS_MSG",FreeStatus.getContentByValue(new BigDecimal(resultMap.get("FREEZE_STATUS"))));
            resultMap.put("FREEZE_TYPE",FreeType.getmsg(new BigDecimal(String.valueOf(resultMap.get("FREEZE_TYPE")))));
            CUser cUser = userService.selectById(Long.valueOf(resultMap.get("FREEZE_PERSON")));
            if(null!=cUser){
                resultMap.put("FREEZE_PERSON_MSG",cUser.getName());
            }
            if(StringUtils.isNotBlank(resultMap.get("UNFREEZE_PERSON"))){
                CUser cUser1 = userService.selectById(Long.valueOf(resultMap.get("UNFREEZE_PERSON")));
                if(null!=cUser1){
                    resultMap.put("UNFREEZE_PERSON_MSG",cUser1.getName());
                }
            }
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(resultMaps);
        pageInfo.setTotal(agentFreezeMapper.queryAgentFreezeCount(reqMap));
        return pageInfo;
    }


    /**
     * 冻结
     * @param agentFreezePort
     * @return
     * @throws MessageException
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult agentFreeze(AgentFreezePort agentFreezePort)throws MessageException{

        log.info("代理商冻结请求参数：{}",JsonUtil.objectToJson(agentFreezePort));
        String indentifier = "";
        try {
            indentifier = redisService.lockWithTimeout(RedisCachKey.AGENT_FREEZE_LOCK + agentFreezePort.getOperationPerson()+agentFreezePort.getFreezeNum(), RedisService.ACQUIRE_TIME_OUT, RedisService.TIME_OUT);
            if (StringUtils.isBlank(indentifier)) {
                return AgentResult.fail("系统处理中,请勿重复提交！");
            }
            AgentResult verify = verify(agentFreezePort,FreeStatus.DJ.getValue());
            if(!verify.isOK()){
                return verify;
            }
            for (BigDecimal freeType:agentFreezePort.getFreeType()){
                log.info("冻结类型为[{}]",FreeType.getmsg(freeType));
                AgentFreezeExample agentFreezeExample = new AgentFreezeExample();
                AgentFreezeExample.Criteria criteria = agentFreezeExample.createCriteria();
                criteria.andFreezeTypeIsNull();
                criteria.andStatusEqualTo(Status.STATUS_1.status);
                criteria.andAgentIdEqualTo(agentFreezePort.getAgentId());
                criteria.andFreezeCauseEqualTo(agentFreezePort.getFreezeCause());
                criteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                agentFreezeExample.or().andFreezeTypeEqualTo(freeType)
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andAgentIdEqualTo(agentFreezePort.getAgentId())
                    .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                    .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
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
                agentFreeze.setFreezeNum(agentFreezePort.getFreezeNum());
                agentFreeze.setRemark(agentFreezePort.getRemark());
                agentFreeze.setStatus(Status.STATUS_1.status);
                agentFreeze.setVersion(BigDecimal.ONE);
                agentFreeze.setFreezeType(freeType);
                agentFreezeMapper.insert(agentFreeze);
            }
            return AgentResult.ok("冻结成功");
        }finally {
            if(StringUtils.isNotBlank(indentifier)){
                redisService.releaseLock(RedisCachKey.AGENT_FREEZE_LOCK + agentFreezePort.getOperationPerson()+agentFreezePort.getFreezeNum(), indentifier);
            }
        }
    }


    /**
     * 解冻
     * @param agentFreezePort
     * @return
     * @throws MessageException
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult agentUnFreeze(AgentFreezePort agentFreezePort)throws MessageException{

        log.info("代理商解冻请求参数：{}",JsonUtil.objectToJson(agentFreezePort));
        String indentifier = "";
        try {
            indentifier = redisService.lockWithTimeout(RedisCachKey.AGENT_UN_FREEZE_LOCK + agentFreezePort.getOperationPerson(), RedisService.ACQUIRE_TIME_OUT, RedisService.TIME_OUT);
            if (StringUtils.isBlank(indentifier)) {
                return AgentResult.fail("系统处理中,请勿重复提交！");
            }

            AgentResult verify = verify(agentFreezePort,FreeStatus.JD.getValue());
            if(!verify.isOK()){
                return verify;
            }
            if(StringUtils.isBlank(agentFreezePort.getUnfreezeCause())){
                return AgentResult.fail("解冻原因必填");
            }
            for (BigDecimal freeType:agentFreezePort.getFreeType()){
                AgentFreezeExample freezeExample = new AgentFreezeExample();
                AgentFreezeExample.Criteria freezeCriteria = freezeExample.createCriteria();
                freezeCriteria.andFreezeTypeIsNull();
                freezeCriteria.andStatusEqualTo(Status.STATUS_1.status);
                freezeCriteria.andAgentIdEqualTo(agentFreezePort.getAgentId());
                freezeCriteria.andFreezeCauseEqualTo(agentFreezePort.getFreezeCause());
                freezeCriteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                freezeExample.or().andFreezeTypeEqualTo(freeType)
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andAgentIdEqualTo(agentFreezePort.getAgentId())
                    .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                    .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
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
                agentFreeze.setFreezeStatus(FreeStatus.JD.getValue().toString());
                int j = agentFreezeMapper.updateByPrimaryKeySelective(agentFreeze);
                if(j!=1){
                    throw new MessageException("更新解冻失败");
                }
                AgentFreezeExample qfreezeExample = new AgentFreezeExample();
                AgentFreezeExample.Criteria qfreezeCriteria = qfreezeExample.createCriteria();
                qfreezeCriteria.andStatusEqualTo(Status.STATUS_1.status);
                qfreezeCriteria.andAgentIdEqualTo(agentFreezePort.getAgentId());
                qfreezeCriteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(qfreezeExample);

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
            }
            return AgentResult.ok("解冻成功");
        }finally {
            if(StringUtils.isNotBlank(indentifier)){
                redisService.releaseLock(RedisCachKey.AGENT_UN_FREEZE_LOCK + agentFreezePort.getOperationPerson(), indentifier);
            }
        }
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
        if(freeStatus.compareTo(FreeStatus.DJ.getValue())==0 ){
            if(agentFreezePort.getFreezeCause().equals(FreeCause.QTDJ.getValue()) && StringUtils.isBlank(agentFreezePort.getRemark())){
                agentResult.setMsg("冻结原因是其他原因,备注必填");
                return agentResult;
            }
            if(StringUtils.isBlank(agentFreezePort.getFreezeNum())){
                agentResult.setMsg("请填写请求数据编号");
                return agentResult;
            }
            List<BigDecimal> freeType = agentFreezePort.getFreeType();
            if (null != freeType && freeType.size()>0){
                for (BigDecimal type:freeType){
                    if (null == FreeType.getmsg(type)){
                        agentResult.setMsg("请正确选择冻结层级");
                        return agentResult;
                    }
                }
            }else {
                agentResult.setMsg("请选择冻结层级");
                return agentResult;
            }
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
        if(!FreeCause.HTDJ.getValue().equals(agentFreezePort.getFreezeCause())){
            if(!agent.getAgStatus().equals(AgStatus.Approved.name())){
                agentResult.setMsg("代理商未通过审批");
                return agentResult;
            }
        }
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("agent",agent);
        resultMap.put("cUser",cUser);
        return AgentResult.ok(resultMap);
    }

    /**
     * 查询代理商是否冻结,冻结返回明细
     * @param agentId
     * @return
     */
    @Override
    public AgentResult queryAgentFreeze(String agentId){

        log.info("代理商冻结查询请求参数：{}",agentId);
        AgentResult agentResult = AgentResult.fail();
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isBlank(agentId)){
            agentResult.setMsg("请填写代理商编码");
            return agentResult;
        }
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        if(agent==null){
            agentResult.setMsg("代理商信息不存在");
            return agentResult;
        }
        if(!agent.getAgStatus().equals(AgStatus.Approved.name())){
            agentResult.setMsg("代理商未通过审批");
            return agentResult;
        }
        String freeStatus = FreeStatus.getContentByValue(agent.getFreestatus());
        resultMap.put("freeStatus",agent.getFreestatus());
        resultMap.put("freeMsg",freeStatus);

        if(agent.getFreestatus().compareTo(FreeStatus.DJ.getValue())==0){
            AgentFreezeExample freezeExample = new AgentFreezeExample();
            AgentFreezeExample.Criteria freezeCriteria = freezeExample.createCriteria();
            freezeCriteria.andStatusEqualTo(Status.STATUS_1.status);
            freezeCriteria.andAgentIdEqualTo(agentId);
            freezeCriteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
            List<AgentFreeze> agentFreezeList = agentFreezeMapper.selectByExample(freezeExample);

            List<Map<String,Object>> resultList = new ArrayList<>();
            for (AgentFreeze agentFreeze : agentFreezeList) {
                Map<String, Object> map = new HashMap<>();
                map.put("freezeDate",DateUtil.format(agentFreeze.getFreezeDate(),DateUtil.DATE_FORMAT_2));
                CUser cUser = userService.selectById(Long.valueOf(agentFreeze.getFreezePerson()));
                if(null==cUser){
                    map.put("freezePerson","");
                }else{
                    map.put("freezePerson",cUser.getName());
                }
                map.put("freezeCause",agentFreeze.getFreezeCause());
                map.put("freezeCauseMsg",FreeCause.getContentByValue(agentFreeze.getFreezeCause()));
                map.put("remark",agentFreeze.getRemark());
                resultList.add(map);
            }
            resultMap.put("freezeInfo",resultList);
        }else{
            resultMap.put("freezeInfo","");
        }
        return AgentResult.ok(resultMap);
    }

    @Override
    public AgentFreeze selectByPrimaryKey(String id){
        AgentFreeze agentFreeze = agentFreezeMapper.selectByPrimaryKey(id);
        return agentFreeze;
    }
}
