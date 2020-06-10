package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentFreezeMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.FreezeRequestMapper;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.pojo.admin.vo.AgentFreezePort;
import com.ryx.credit.pojo.admin.vo.AgentFreezeVo;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentFreezeService;
import com.ryx.credit.service.agent.PlatFormService;
import com.ryx.credit.service.dict.IdService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
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
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private FreezeRequestMapper freezeRequestMapper;


    @Override
    public PageInfo agentFreezeList(AgentFreeze agentFreeze, Page page){

        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(agentFreeze.getId())){
            reqMap.put("id",agentFreeze.getId());
        }
        if(StringUtils.isNotBlank(agentFreeze.getAgentId())){
            String agentIdS = String.valueOf(agentFreeze.getAgentId());
            String[] split = agentIdS.split(",");
            reqMap.put("agentId",split);
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
            resultMap.put("UNFREEZE_CAUSE",UnfreeCause.getContentByValue(resultMap.get("UNFREEZE_CAUSE")));
            if(StringUtils.isNotBlank(resultMap.get("FREEZE_PERSON"))){
                if(resultMap.get("FREEZE_PERSON").equals(String.valueOf(FreePerson.XTDJ.getValue()))){
                    resultMap.put("FREEZE_PERSON_MSG",FreePerson.getContentByValue(new BigDecimal(resultMap.get("FREEZE_PERSON"))));
                }else{
                    CUser cUser = userService.selectById(Long.valueOf(resultMap.get("FREEZE_PERSON")));
                    if(null!=cUser){
                        resultMap.put("FREEZE_PERSON_MSG",cUser.getName());
                    }
                }
            }
            if(StringUtils.isNotBlank(resultMap.get("UNFREEZE_PERSON"))){
                if(resultMap.get("UNFREEZE_PERSON").equals(String.valueOf(UnfreePerson.XTJD.getValue()))){
                    resultMap.put("UNFREEZE_PERSON_MSG",UnfreePerson.getContentByValue(new BigDecimal(resultMap.get("UNFREEZE_PERSON"))));
                }else{
                    CUser cUser1 = userService.selectById(Long.valueOf(resultMap.get("UNFREEZE_PERSON")));
                    if(null!=cUser1){
                        resultMap.put("UNFREEZE_PERSON_MSG",cUser1.getName());
                    }
                }
            }
            if (StringUtils.isNotBlank(resultMap.get("BUS_PLATFORM"))){
                PlatForm busPlatform = platFormService.selectByPlatformNum(String.valueOf(resultMap.get("BUS_PLATFORM")));
                resultMap.put("BUS_PLATFORM",busPlatform.getPlatformName());
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
            AgentResult verify = verify(agentFreezePort,FreeStatus.DJ.getValue(),BigDecimal.ZERO);
            if(!verify.isOK()){
                return verify;
            }
            for(String busPlatform:agentFreezePort.getBusPlatform()){
                for (BigDecimal freeType:agentFreezePort.getFreeType()){
                    log.info("冻结类型为[{}]",FreeType.getmsg(freeType));
                    AgentFreezeExample agentFreezeExample = new AgentFreezeExample();
                    if (freeType.compareTo(FreeType.AGNET.code) == 0){
                        AgentFreezeExample.Criteria criteria = agentFreezeExample.createCriteria();
                        criteria.andFreezeTypeIsNull();
                        criteria.andStatusEqualTo(Status.STATUS_1.status);
                        criteria.andAgentIdEqualTo(agentFreezePort.getAgentId());
                        criteria.andFreezeCauseEqualTo(agentFreezePort.getFreezeCause());
                        criteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                    }
                    agentFreezeExample.or()
                            .andFreezeTypeEqualTo(freeType)
                            .andStatusEqualTo(Status.STATUS_1.status)
                            .andAgentIdEqualTo(agentFreezePort.getAgentId())
                            .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                            .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                    List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(agentFreezeExample);
                    if(agentFreezes.size()!=0){
                        throw new MessageException("代理商此原因已被冻结:"+FreeType.getmsg(freeType));
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
                    /** 保存新增字段 **/
                    agentFreeze.setBusPlatform("");
                    agentFreeze.setBusId(busPlatform);
                    agentFreeze.setBusNum("");
                    if (freeType.compareTo(FreeType.AGNET.code)==0){
                        agentFreeze.setBusFreeze(agentFreezePort.getCurLevel().getBusFreeze());
                        agentFreeze.setProfitFreeze(agentFreezePort.getCurLevel().getProfitFreeze());
                        agentFreeze.setReflowFreeze(agentFreezePort.getCurLevel().getReflowFreeze());
                        agentFreeze.setMonthlyFreeze(agentFreezePort.getCurLevel().getMonthlyFreeze());
                        agentFreeze.setDailyFreeze(agentFreezePort.getCurLevel().getDailyFreeze());
                        agentFreeze.setStopProfitFreeze(agentFreezePort.getCurLevel().getStopProfitFreeze());
                        agentFreeze.setCashFreeze(agentFreezePort.getCurLevel().getCashFreeze());
                        agentFreeze.setStopCount(agentFreezePort.getCurLevel().getStopCount());
                    }else if (freeType.compareTo(FreeType.SUB_AGENT.code)==0){
                        agentFreeze.setBusFreeze(agentFreezePort.getSubLevel().getBusFreeze());
                        agentFreeze.setProfitFreeze(agentFreezePort.getSubLevel().getProfitFreeze());
                        agentFreeze.setReflowFreeze(agentFreezePort.getSubLevel().getReflowFreeze());
                        agentFreeze.setMonthlyFreeze(agentFreezePort.getSubLevel().getMonthlyFreeze());
                        agentFreeze.setDailyFreeze(agentFreezePort.getSubLevel().getDailyFreeze());
                        agentFreeze.setStopProfitFreeze(agentFreezePort.getSubLevel().getStopProfitFreeze());
                        agentFreeze.setCashFreeze(agentFreezePort.getSubLevel().getCashFreeze());
                        agentFreeze.setStopCount(agentFreezePort.getSubLevel().getStopCount());
                    }
                    if(StringUtils.isNotBlank(agentFreezePort.getRemark())){//备注
                        agentFreeze.setRemark(agentFreezePort.getRemark());
                    }
                    agentFreezeMapper.insert(agentFreeze);
                    if (freeType.compareTo(FreeType.AGNET.code) == 0){
                        Map<String,Object> dataMap = (Map<String,Object>)verify.getData();
                        Agent agent = (Agent)dataMap.get("agent");
                        if(agent.getFreestatus().compareTo(FreeStatus.DJ.getValue())!=0){
                            agent.setFreestatus(FreeStatus.DJ.getValue());
                            int i = agentMapper.updateByPrimaryKeySelective(agent);
                            if(i!=1){
                                throw new MessageException("代理商冻结更新代理商信息失败");
                            }
                        }
                    }
                }
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

            AgentResult verify = verify(agentFreezePort,FreeStatus.JD.getValue(),BigDecimal.ZERO);
            if(!verify.isOK()){
                return verify;
            }
            if(StringUtils.isBlank(agentFreezePort.getUnfreezeCause())){
                return AgentResult.fail("解冻原因必填");
            }
            if (null == agentFreezePort.getFreeType()){
                agentFreezePort.setFreeType(Arrays.asList(FreeType.AGNET.code));
            }
            for (BigDecimal freeType:agentFreezePort.getFreeType()){
                AgentFreezeExample freezeExample = new AgentFreezeExample();
                if (freeType.compareTo(FreeType.AGNET.code) == 0){
                    AgentFreezeExample.Criteria freezeCriteria = freezeExample.createCriteria();
                    freezeCriteria.andFreezeTypeIsNull();
                    freezeCriteria.andStatusEqualTo(Status.STATUS_1.status);
                    freezeCriteria.andAgentIdEqualTo(agentFreezePort.getAgentId());
                    freezeCriteria.andFreezeCauseEqualTo(agentFreezePort.getFreezeCause());
                    freezeCriteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                }
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
                if(StringUtils.isNotBlank(agentFreezePort.getRemark())){//备注
                    agentFreeze.setRemark(agentFreezePort.getRemark());
                }
                int j = agentFreezeMapper.updateByPrimaryKeySelective(agentFreeze);
                if(j!=1){
                    throw new MessageException("更新解冻失败");
                }
                if (freeType.compareTo(FreeType.AGNET.code) == 0){

                    AgentFreezeExample qfreezeExample = new AgentFreezeExample();
                    AgentFreezeExample.Criteria qfreezeCriteria = qfreezeExample.createCriteria();
                    qfreezeCriteria.andFreezeTypeIsNull();
                    qfreezeCriteria.andStatusEqualTo(Status.STATUS_1.status);
                    qfreezeCriteria.andAgentIdEqualTo(agentFreezePort.getAgentId());
                    qfreezeCriteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());

                    qfreezeExample.or().andStatusEqualTo(Status.STATUS_1.status)
                            .andFreezeTypeEqualTo(freeType)
                            .andAgentIdEqualTo(agentFreezePort.getAgentId())
                            .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());

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
     * @param verifyType 0:冻结/解冻 1:修改
     * @return
     */
    private AgentResult verify(AgentFreezePort agentFreezePort,BigDecimal freeStatus,BigDecimal verifyType){
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
            if(StringUtils.isBlank(agentFreezePort.getFreezeNum())&& verifyType.compareTo(BigDecimal.ZERO) == 0){
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
                log.info("[{}]未传入冻结层级，默认本级",agentFreezePort.getAgentId());
                agentFreezePort.setFreeType(Arrays.asList(FreeType.AGNET.code));
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
        CUser cUser = null;
        if(!agentFreezePort.getOperationPerson().equals(String.valueOf(FreePerson.XTDJ.getValue()))){
            cUser = userService.selectById(Long.valueOf(agentFreezePort.getOperationPerson()));
            if(null==cUser){
                agentResult.setMsg("操作人不存在");
                return agentResult;
            }
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
        if(agentFreezePort.getOperationPerson().equals(String.valueOf(FreePerson.XTDJ.getValue()))){
            resultMap.put("cUser",FreePerson.XTDJ.getValue());
        }
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
            freezeCriteria.andFreezeTypeIsNull();
            freezeCriteria.andStatusEqualTo(Status.STATUS_1.status);
            freezeCriteria.andAgentIdEqualTo(agentId);
            freezeCriteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());

            freezeExample.or().andFreezeTypeEqualTo(FreeType.AGNET.code)
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andAgentIdEqualTo(agentId)
                    .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
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

    /**
     * 查询代理商的冻结状态，下级冻结状态
     * @param busNum 业务编号
     * @param platformType 平台类型
     * @param agBd 品牌编号
     * @return
     */
    @Override
    public FastMap checkAgentIsFreeze(String busNum, String platformType, String agBd) {
        Map<String,Object> par = new HashedMap();
        par.put("agBd",agBd);
        par.put("platformType",platformType);
        par.put("busNum",busNum);
        par.put("busStatus",BusStatus.getAvbList());
        List<Map<String,Object>> data = agentMapper.queryAgentFreezeInfo(par);
        if(data.size()!=1){
            return FastMap.fastFailMap("未找到代理商信息");
        }
        Map<String,Object> agentInfo = data.get(0);
        Object ag = agentInfo.get("AG");
        //代理商本级有没有冻结
        AgentFreezeExample bj = new AgentFreezeExample();
        bj.or().andAgentIdEqualTo(ag.toString())
                .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString())
                .andFreezeTypeEqualTo(FreeType.AGNET.code)
                .andStatusEqualTo(Status.STATUS_1.status);
        long bj_list = agentFreezeMapper.countByExample(bj);
        log.info("代理商 {} 冻结记录为 {}",ag,bj_list);
        //非直签下级代理商有没有冻结
        AgentFreezeExample fzqxj = new AgentFreezeExample();
        fzqxj.or().andAgentIdEqualTo(ag.toString())
                .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString())
                .andFreezeTypeEqualTo(FreeType.SUB_AGENT.code)
                .andStatusEqualTo(Status.STATUS_1.status);
        long xj_count = agentFreezeMapper.countByExample(fzqxj);
        log.info("代理商 {} 冻结非直签下级记录为 {}",ag,bj_list);
        return FastMap.fastSuccessMap("代理商正常").putKeyV("agFcount",bj_list+"").putKeyV("noSignSubAgentFcount",xj_count+"");
    }

    /**
     * 查询基本信息缺失的代理商，进行批量冻结
     * @param userId
     * @return
     */
    @Override
    public AgentResult queryAgentBasicLack(String userId) {
        List<AgentFreezeVo> agentFreezeVoList = agentFreezeMapper.queryAgentBasicLackData();
        // 校验字段是否为空
        List<Map> stringList = verifyFieldEmpty(agentFreezeVoList);
        try {
            if (stringList.size()>0 && stringList!=null) { // 基本信息缺失的代理商id
                for (Map map : stringList) {
                    String agent_id = String.valueOf(map.get("agent_id"));
                    String str_remark = String.valueOf(map.get("str_remark"));
                    if (StringUtils.isNotBlank(str_remark)) {
                        String freeze_cause = FreeCause.XXQS.getValue();
                        BigDecimal freeze_type = FreeType.AGNET.code;
                        // 调用冻结接口前，检查冻结表是否存在同一AG码同一冻结类型的冻结数据，存在则无需调接口
                        AgentResult resultCheck = checkAgentFreezeExists(agent_id, freeze_cause, freeze_type);
                        if (!resultCheck.isOK()) {
                            // 调用冻结接口
                            AgentFreezePort agentFreezePort = new AgentFreezePort();
                            agentFreezePort.setAgentId(agent_id);
                            agentFreezePort.setFreezeCause(FreeCause.XXQS.getValue());
                            agentFreezePort.setOperationPerson(userId);
                            agentFreezePort.setFreezeNum(agent_id);
                            agentFreezePort.setFreeType(Arrays.asList(FreeType.AGNET.code));
                            agentFreezePort.setRemark(str_remark);
                            AgentResult agentResult = agentFreeze(agentFreezePort);
                            if (!agentResult.isOK()) {
                                throw new ProcessException(agentResult.getMsg());
                            }
                        }
                    }
                }
            }
        } catch (MessageException e) {
            e.printStackTrace();
            throw new ProcessException(e.getMsg());
        }
        return AgentResult.ok();
    }

    /**
     * 校验字段是否为空
     * @param agentFreezeVoList
     * @return
     */
    @Override
    public List<Map> verifyFieldEmpty(List<AgentFreezeVo> agentFreezeVoList) {
        List<Map> stringList = new ArrayList<Map>();
        if (agentFreezeVoList.size()>0 && agentFreezeVoList!=null) {
            for (AgentFreezeVo agentFreezeVo : agentFreezeVoList) {
                Map<String, String> listMap = new HashMap();
                String agent_id = agentFreezeVo.getAgId(); // agentId
                String str_empty = ""; // 备注-为空字段
                if (StringUtils.isNotBlank(agentFreezeVo.getCloType())) {
                    str_empty += agentFreezeVo.getAgName() == null ? "代理商名称," : "";
                    str_empty += agentFreezeVo.getAgNature() == null ? "公司性质," : "";
                    str_empty += agentFreezeVo.getAgCapital() == null ? "注册资本(元)," : "";
                    str_empty += agentFreezeVo.getAgBusLic() == null ? "营业执照," : "";
                    str_empty += agentFreezeVo.getAgBusLicb() == null ? "营业执照起始时间," : "";
                    str_empty += agentFreezeVo.getAgBusLice() == null ? "营业执照结束时间," : "";
                    str_empty += agentFreezeVo.getAgLegal() == null ? "法人姓名," : "";
                    str_empty += agentFreezeVo.getAgLegalCertype() == null ? "法人证件类型," : "";
                    str_empty += agentFreezeVo.getAgLegalCernum() == null ? "法人证件号码," : "";
                    str_empty += agentFreezeVo.getAgLegalMobile() == null ? "法人联系电话," : "";
                    str_empty += agentFreezeVo.getAgHead() == null ? "法人联系电话," : "";
                    str_empty += agentFreezeVo.getAgHeadMobile() == null ? "法人联系电话," : "";
                    str_empty += agentFreezeVo.getAgRegArea() == null ? "注册区域," : "";
                    str_empty += agentFreezeVo.getAgRegAdd() == null ? "注册地址," : "";
                    str_empty += agentFreezeVo.getAgBusScope() == null ? "营业范围," : "";
                    str_empty += agentFreezeVo.getBusRiskEmail() == null ? "投诉及风险风控对接邮箱," : "";
                    str_empty += agentFreezeVo.getBusContactEmail() == null ? "分润对接邮箱," : "";
                    str_empty += agentFreezeVo.getCloType() == null ? "收款账户类型," : "";
                    str_empty += agentFreezeVo.getCloRealname() == null ? "收款账户名," : "";
                    str_empty += agentFreezeVo.getCloBankAccount() == null ? "收款账号," : "";
                    str_empty += agentFreezeVo.getCloBank() == null ? "收款开户总行," : "";
                    str_empty += agentFreezeVo.getBankRegion() == null ? "开户行地区," : "";
                    str_empty += agentFreezeVo.getCloBankBranch() == null ? "收款开户行支行," : "";
                    str_empty += agentFreezeVo.getAllLineNum() == null ? "总行联行号," : "";
                    str_empty += agentFreezeVo.getBranchLineNum() == null ? "支行联行号," : "";
                    str_empty += agentFreezeVo.getCloTaxPoint() == null ? "税点," : "";
                    str_empty += agentFreezeVo.getCloInvoice() == null ? "是否开具分润发票," : "";
                    if (agentFreezeVo.getCloType().equals("2")) { // 对私
                        str_empty += agentFreezeVo.getAcAgLegalCernum() == null ? "结算卡法人证件号" : "";
                    }
                    listMap.put("agent_id", agent_id);
                    listMap.put("str_remark", str_empty);
                    stringList.add(listMap);
                } else {
                    str_empty = "代理商缺失结算卡信息";
                    listMap.put("agent_id", agent_id);
                    listMap.put("str_remark", str_empty);
                    stringList.add(listMap);
                }
            }
            log.info("基础信息缺失代理商:{}", JsonUtil.objectToJson(stringList));
        }
        return stringList;
    }

    /**
     * 检查同一AG码同一类型的冻结数据是否存在
     * 存在返回ok，不存在返回false
     * @param agentId
     * @param freeCause
     * @return
     */
    @Override
    public AgentResult checkAgentFreezeExists(String agentId, String freeCause, BigDecimal freeType) {
        Map<String, Object> resultMap = new HashMap<>();
        AgentFreezeExample agentFreezeExample = new AgentFreezeExample();
        if (freeType.compareTo(FreeType.AGNET.code) == 0) {
            AgentFreezeExample.Criteria criteria = agentFreezeExample.createCriteria();
            criteria.andFreezeTypeIsNull();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andAgentIdEqualTo(agentId);
            criteria.andFreezeCauseEqualTo(freeCause);
            criteria.andFreezeStatusEqualTo(String.valueOf(FreeStatus.DJ.getValue()));
        }
        agentFreezeExample.or()
                .andFreezeTypeEqualTo(freeType)
                .andStatusEqualTo(Status.STATUS_1.status)
                .andAgentIdEqualTo(agentId)
                .andFreezeCauseEqualTo(freeCause)
                .andFreezeStatusEqualTo(String.valueOf(FreeStatus.DJ.getValue()));
        List<AgentFreeze> freezeList = agentFreezeMapper.selectByExample(agentFreezeExample);
        if (freezeList.size() != 0) {
            resultMap.put("id", freezeList.get(0).getId());
            resultMap.put("agentId", freezeList.get(0).getAgentId());
            resultMap.put("freezeCause", freezeList.get(0).getFreezeCause());
            resultMap.put("freezeStatus", freezeList.get(0).getFreezeStatus());
            log.info("查询已存在冻结数据信息:{}", JsonUtil.objectToJson(resultMap));
            return AgentResult.okMap(resultMap);
        }
        return AgentResult.fail();
    }

    /**
     * 校验审批通过代理商
     * @param agentId
     * @param freeCause
     * @param freeType
     * @return
     */
    @Override
    public AgentResult checkAgentUnFreezeExists(String agentId, String freeCause, BigDecimal freeType) {
        AgentExample agentExample = new AgentExample();
        agentExample.or()
                .andStatusEqualTo(Status.STATUS_1.status)
                .andIdEqualTo(agentId)
                .andAgStatusEqualTo(AgStatus.Approved.name());
        List<Agent> agentList = agentMapper.selectByExample(agentExample);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("agent", agentList.get(0));
        return AgentResult.ok(resultMap);
    }

    /**
     * 查询冻结数据是否是结算卡冻结变更申请
     * @param agentId
     * @param freeNum
     * @return
     */
    @Override
    public AgentResult queryAgentIdByFreezeNum(String agentId, String freeNum) {
        Map<String, Object> resultMap = new HashMap<>();
        AgentFreezeExample agentFreezeExample = new AgentFreezeExample();
        AgentFreezeExample.Criteria criteria = agentFreezeExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andAgentIdEqualTo(agentId);
        criteria.andFreezeNumEqualTo(freeNum);
        criteria.andFreezeStatusEqualTo(String.valueOf(FreeStatus.DJ.getValue()));
        List<AgentFreeze> freezeList = agentFreezeMapper.selectByExample(agentFreezeExample);
        if (freezeList.size() != 0) {
            resultMap.put("agentFreeze", freezeList.get(0));
            log.info("查询冻结数据信息:{}", JsonUtil.objectToJson(resultMap));
            return AgentResult.okMap(resultMap);
        }
        return AgentResult.fail();
    }

    /**
     * 结算卡审批通过校验信息缺失字段是否补充完整
     * @param agent
     * @param agentColinfoVo
     * @return
     */
    @Override
    public List<Map> approvedVerify(Agent agent, AgentColinfoVo agentColinfoVo) {
        List<AgentFreezeVo> agentFreezeVoList = new ArrayList<>();
        AgentFreezeVo agentFreezeVo = new AgentFreezeVo();
        agentFreezeVo.setAgId(agent.getId());
        agentFreezeVo.setAgName(agent.getAgName());
        agentFreezeVo.setAgNature(String.valueOf(agent.getAgNature()));
        agentFreezeVo.setAgCapital(String.valueOf(agent.getAgCapital()));
        agentFreezeVo.setAgBusLic(agent.getAgBusLic());
        agentFreezeVo.setAgBusLicb(String.valueOf(agent.getAgBusLicb()));
        agentFreezeVo.setAgBusLice(String.valueOf(agent.getAgBusLice()));
        agentFreezeVo.setAgLegal(agent.getAgLegal());
        agentFreezeVo.setAgLegalCertype(String.valueOf(agent.getAgLegalCertype()));
        agentFreezeVo.setAgLegalCernum(agent.getAgLegalCernum());
        agentFreezeVo.setAgLegalMobile(agent.getAgLegalMobile());
        agentFreezeVo.setAgHead(agent.getAgHead());
        agentFreezeVo.setAgHeadMobile(agent.getAgHeadMobile());
        agentFreezeVo.setAgRegArea(agent.getAgRegArea());
        agentFreezeVo.setAgRegAdd(agent.getAgRegAdd());
        agentFreezeVo.setAgBusScope(agent.getAgBusScope());
        agentFreezeVo.setBusContactEmail(agent.getBusContactEmail());
        agentFreezeVo.setBusRiskEmail(agent.getBusRiskEmail());
        agentFreezeVo.setAcId(agentColinfoVo.getId());
        agentFreezeVo.setCloType(String.valueOf(agentColinfoVo.getCloType()));
        agentFreezeVo.setCloRealname(agentColinfoVo.getCloRealname());
        agentFreezeVo.setCloBankAccount(agentColinfoVo.getCloBankAccount());
        agentFreezeVo.setCloBank(agentColinfoVo.getCloBank());
        agentFreezeVo.setBankRegion(agentColinfoVo.getBankRegion());
        agentFreezeVo.setCloBankBranch(agentColinfoVo.getCloBankBranch());
        agentFreezeVo.setAllLineNum(agentColinfoVo.getAllLineNum());
        agentFreezeVo.setBranchLineNum(agentColinfoVo.getBranchLineNum());
        agentFreezeVo.setCloTaxPoint(String.valueOf(agentColinfoVo.getCloTaxPoint()));
        agentFreezeVo.setCloInvoice(String.valueOf(agentColinfoVo.getCloInvoice()));
        agentFreezeVo.setAcAgLegalCernum(agentColinfoVo.getAgLegalCernum());
        agentFreezeVoList.add(agentFreezeVo);
        List<Map> mapList = verifyFieldEmpty(agentFreezeVoList);
        return mapList;
    }

    @Override
    public Map<String, String> selectAgentFreeze(HashMap map) throws MessageException {
        return null;
    }

    @Override
    public AgentResult agentFreezeModify(AgentFreezePort agentFreezePort) throws MessageException {
        log.info("代理商申请你变更请求参数：{}",JsonUtil.objectToJson(agentFreezePort));
        try {
            AgentResult verify = verify(agentFreezePort,FreeStatus.DJ.getValue(),BigDecimal.ONE);
            if(!verify.isOK()){
                return verify;
            }
            for (BigDecimal freeType:agentFreezePort.getFreeType()){
                log.info("冻结类型为[{}]",FreeType.getmsg(freeType));
                AgentFreezeExample agentFreezeExample = new AgentFreezeExample();
                if (freeType.compareTo(FreeType.AGNET.code) == 0){
                    AgentFreezeExample.Criteria criteria = agentFreezeExample.createCriteria();
                    criteria.andFreezeTypeIsNull();
                    criteria.andStatusEqualTo(Status.STATUS_1.status);
                    criteria.andAgentIdEqualTo(agentFreezePort.getAgentId());
                    criteria.andFreezeCauseEqualTo(agentFreezePort.getFreezeCause());
                    criteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                }
                agentFreezeExample.or()
                        .andFreezeTypeEqualTo(freeType)
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andAgentIdEqualTo(agentFreezePort.getAgentId())
                        .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                        .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(agentFreezeExample);
                if(agentFreezes.size()==0){
                    throw new MessageException("代理商此原因冻结记录不存在:"+FreeType.getmsg(freeType));
                }
                /** 保存新增字段 **/
                agentFreezes.get(0).setBusFreeze(agentFreezePort.getCurLevel().getBusFreeze());
                agentFreezes.get(0).setProfitFreeze(agentFreezePort.getCurLevel().getProfitFreeze());
                agentFreezes.get(0).setReflowFreeze(agentFreezePort.getCurLevel().getReflowFreeze());
                agentFreezes.get(0).setMonthlyFreeze(agentFreezePort.getCurLevel().getMonthlyFreeze());
                agentFreezes.get(0).setDailyFreeze(agentFreezePort.getCurLevel().getDailyFreeze());
                agentFreezes.get(0).setStopProfitFreeze(agentFreezePort.getCurLevel().getStopProfitFreeze());
                agentFreezes.get(0).setCashFreeze(agentFreezePort.getCurLevel().getCashFreeze());
                agentFreezes.get(0).setStopCount(agentFreezePort.getCurLevel().getStopCount());
                if(StringUtils.isNotBlank(agentFreezePort.getRemark())){//备注
                    agentFreezes.get(0).setRemark(agentFreezePort.getRemark());
                }
                agentFreezeMapper.updateByPrimaryKey(agentFreezes.get(0));
                if (agentFreezeMapper.updateByPrimaryKey(agentFreezes.get(0)) != 1){
                    throw new MessageException("冻结申请变更异常!");
                }
            }
            return AgentResult.ok("冻结申请变更成功!");
        }catch (Exception e){
            return AgentResult.fail("冻结申请变更失败!");
        }

    }
}
