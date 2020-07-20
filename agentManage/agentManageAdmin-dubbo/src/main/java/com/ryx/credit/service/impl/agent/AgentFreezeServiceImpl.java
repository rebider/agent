package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.pojo.admin.vo.AgentFreezePort;
import com.ryx.credit.pojo.admin.vo.AgentFreezeVo;
import com.ryx.credit.pojo.admin.vo.FreezeDetail;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoFreezeService;
import com.ryx.credit.service.agent.AgentBusinfoService;
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
import sun.management.resources.agent;

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
    private AgentBusinfoFreezeMapper agentBusinfoFreezeMapper;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private FreezeRequestMapper freezeRequestMapper;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IResourceService iResourceService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentFreezeService agentFreezeService;
    @Autowired
    private AgentBusinfoFreezeService agentBusinfoFreezeService;
    @Autowired
    private FreezeRequestDetailMapper freezeRequestDetailMapper;

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
        reqMap.put("userId",agentFreeze.getFreezePerson());
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.parseLong(agentFreeze.getFreezePerson()));
        if (orgCodeRes == null && orgCodeRes.size() != 1) {
            return null;
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getBusFreeze()))){
            reqMap.put("busFreeze",agentFreeze.getBusFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getProfitFreeze()))){
            reqMap.put("profitFreeze",agentFreeze.getProfitFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getReflowFreeze()))){
            reqMap.put("reflowFreeze",agentFreeze.getReflowFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getMonthlyFreeze()))){
            reqMap.put("monthlyFreeze",agentFreeze.getMonthlyFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getDailyFreeze()))){
            reqMap.put("dailyFreeze",agentFreeze.getDailyFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getStopProfitFreeze()))){
            reqMap.put("stopProfitFreeze",agentFreeze.getStopProfitFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getCashFreeze()))){
            reqMap.put("cashFreeze",agentFreeze.getCashFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getStopCount()))){
            reqMap.put("stopCount",agentFreeze.getStopCount());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getBusPlatform()))){
            reqMap.put("busPlatform",agentFreeze.getBusPlatform());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getFreezeType()))){
            reqMap.put("freezeType",agentFreeze.getFreezeType());
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgId = String.valueOf(stringObjectMap.get("ORGID"));
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        reqMap.put("orgId", orgId);
        reqMap.put("userId", agentFreeze.getFreezePerson());
        reqMap.put("organizationCode", organizationCode);
        List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(Long.parseLong(agentFreeze.getFreezePerson()));
        reqMap.put("platfromPerm",platfromPerm);
        List<Map<String, String>> resultMaps = agentFreezeMapper.queryAgentFreezeList(reqMap,page);
        for (Map<String, String> resultMap : resultMaps) {
            resultMap.put("FREESTATUS_MSG",FreeStatus.getContentByValue(new BigDecimal(resultMap.get("FREESTATUS"))));
            resultMap.put("FREEZE_CAUSE_MSG",FreeCause.getContentByValue(resultMap.get("FREEZE_CAUSE")));
            resultMap.put("FREEZE_STATUS_MSG",FreeStatus.getContentByValue(new BigDecimal(resultMap.get("FREEZE_STATUS"))));
            resultMap.put("FREEZE_TYPE",FreeType.getmsg(new BigDecimal(String.valueOf(resultMap.get("FREEZE_TYPE")))));
            resultMap.put("UNFREEZE_CAUSE",UnfreeCause.getContentByValue(resultMap.get("UNFREEZE_CAUSE")).equals("")?resultMap.get("UNFREEZE_CAUSE"):UnfreeCause.getContentByValue(resultMap.get("UNFREEZE_CAUSE")));
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
            List<Map<String, Object>> orgCodeResTmp = iUserService.orgCode(Long.parseLong(resultMap.get("FREEZE_PERSON")));
            if (orgCodeResTmp == null || orgCodeResTmp.size() != 1) {
                continue;
            }
            Map<String, Object> stringObjectMapTmp = orgCodeResTmp.get(0);
            String organizationCodeTmp = String.valueOf(stringObjectMapTmp.get("ORGANIZATIONCODE"));
            resultMap.put("ORGANIZATIONCODE_C", organizationCodeTmp);
            resultMap.put("ORGANIZATIONCODE_Q",organizationCode);
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

    @Override
    public AgentResult agentFreeze(AgentFreezePort agentFreezePort)throws MessageException{
        //本地冻结事务
        AgentResult agentResult = agentFreezeService.agentFreezeLocal(agentFreezePort);
        if (!agentResult.isOK()){
            AppConfig.sendEmails("代理商冻结失败："+ JsonUtil.objectToJson(agentFreezePort)+agentResult.getMsg(), "冻结失败报警");
            log.error("代理商冻结失败："+ JsonUtil.objectToJson(agentFreezePort)+agentResult.getMsg());
            throw new MessageException(agentResult.getMsg());
        }
        //代理商未冻结，需冻结代理商
        Agent agent = agentMapper.selectByPrimaryKey(agentFreezePort.getAgentId());
        if(agent.getFreestatus().compareTo(FreeStatus.DJ.getValue())!=0){
            agent.setFreestatus(FreeStatus.DJ.getValue());
            int i = agentMapper.updateByPrimaryKeySelective(agent);
            if(i!=1){
                throw new MessageException("代理商冻结更新代理商信息失败");
            }
        }

        AgentFreeze date = new AgentFreeze();
        date.setAgentId(agent.getId());
        AgentResult notifyResult = agentBusinfoFreezeService.agentBusinfoFreeze(date, agentFreezePort.getOperationPerson());
        if (!notifyResult.isOK()){
            AppConfig.sendEmails("代理商冻结汇总失败："+ JsonUtil.objectToJson(agentFreezePort)+agentResult.getMsg(), "冻结汇总失败报警");
            log.error("代理商冻结汇总失败："+ JsonUtil.objectToJson(agentFreezePort)+agentResult.getMsg());
            throw new MessageException("2000","通知汇总异常");
        }
        return AgentResult.ok();

    }


    /**
     * 解冻
     * @param agentFreezePort
     * @return
     * @throws MessageException
     */

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public AgentResult agentUnFreeze(AgentFreezePort agentFreezePort)throws MessageException{

        //本地冻结事务
        AgentResult agentResult = agentFreezeService.agentUnFreezeLocal(agentFreezePort);
        if (!agentResult.isOK()){
            AppConfig.sendEmails("代理商解冻失败："+ JsonUtil.objectToJson(agentFreezePort)+agentResult.getMsg(), "解冻失败报警");
            log.error("代理商解冻失败："+ JsonUtil.objectToJson(agentFreezePort)+agentResult.getMsg());
            throw new MessageException(agentResult.getMsg());
        }
        log.info("开始查询是否有冻结的记录{}开始{}",agentFreezePort.getAgentId(),new Date());
        //更新代理商冻结状态
        AgentFreezeExample qfreezeExample = new AgentFreezeExample();
        AgentFreezeExample.Criteria qfreezeCriteria = qfreezeExample.createCriteria();
        qfreezeCriteria.andFreezeTypeIsNull();
        qfreezeCriteria.andStatusEqualTo(Status.STATUS_1.status);
        qfreezeCriteria.andAgentIdEqualTo(agentFreezePort.getAgentId());
        qfreezeCriteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
        qfreezeExample.or().andStatusEqualTo(Status.STATUS_1.status)
                .andFreezeTypeEqualTo(FreeType.AGNET.code)
                .andAgentIdEqualTo(agentFreezePort.getAgentId())
                .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
        List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(qfreezeExample);
        log.info("冻结查询数据{}查询时间{}",JSONObject.toJSONString(agentFreezes),new Date());
        //没有冻结的 更新代理商状态为解冻
        if(agentFreezes.size()==0){
            Agent agent =agentMapper.selectByPrimaryKey(agentFreezePort.getAgentId());
            agent.setFreestatus(FreeStatus.JD.getValue());
            log.error("代理商解冻：无冻结记录解冻代理商 {} {}",agent.getId(),agent.getAgName());
            int i = agentMapper.updateByPrimaryKeySelective(agent);
            if(i!=1){
                throw new MessageException("更新代理商信息解冻失败");
            }
        }
        log.info("开始查询是否有冻结的记录{}结束",agentFreezePort.getAgentId(),new Date());
        //汇总逻辑
        AgentFreeze date = new AgentFreeze();
        date.setAgentId(agentFreezePort.getAgentId());
        AgentResult notifyResult = agentBusinfoFreezeService.agentBusinfoFreeze(date, agentFreezePort.getOperationPerson());
        if (!notifyResult.isOK()){
            AppConfig.sendEmails("代理商解冻汇总失败："+ JsonUtil.objectToJson(agentFreezePort)+agentResult.getMsg(), "解冻汇总失败报警");
            log.error("代理商解冻汇总失败："+ JsonUtil.objectToJson(agentFreezePort)+agentResult.getMsg());
            throw new MessageException("2000","通知汇总异常");
        }
        return AgentResult.ok();

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
                    List<AgentBusInfo> agentBusInfos = agentBusinfoService.queryAgentBusInfoFreeze(agent_id);
                    List<String> businfoList = new LinkedList<>();
                    for (AgentBusInfo busInfo : agentBusInfos) {
                        businfoList.add(busInfo.getId());
                    }
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
                            agentFreezePort.setBusPlatform(businfoList);
                            agentFreezePort.setNewBusFreeze(String.valueOf(BigDecimal.ZERO));
                            FreezeDetail freezeDetail = new FreezeDetail();
                            freezeDetail.setProfitFreeze(BigDecimal.ONE);//分润冻结
                            freezeDetail.setReflowFreeze(BigDecimal.ONE);//返现冻结
                            freezeDetail.setMonthlyFreeze(BigDecimal.ONE);//月结
                            freezeDetail.setDailyFreeze(BigDecimal.ONE);//日结
                            freezeDetail.setCashFreeze(BigDecimal.ONE);//体现结算冻结
                            agentFreezePort.setCurLevel(freezeDetail);
                            AgentResult agentResult = agentFreeze(agentFreezePort);
                            if (!agentResult.isOK()) {
                                log.info("批量冻结基本信息缺失代理商{},冻结失败:{}", agent_id, agentResult.getMsg());
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
    public Map<String, Object> selectAgentFreeze(HashMap hashMap) throws MessageException {
        log.info("代理商冻结查询请求参数：{},{}", String.valueOf(hashMap.get("busNum")),String.valueOf(hashMap.get("busType")));
        Map<String,Object> resultMap = new HashMap<>();
        if(StringUtils.isBlank(String.valueOf(hashMap.get("busNum")))){
            throw new MessageException("业务平台编码不能为空");
        }
        if(StringUtils.isBlank(String.valueOf(hashMap.get("busType")))){
            throw new MessageException("业务平台类型不能为空");
        }
        String[] busTypes = String.valueOf(hashMap.get("busType")).split(",");
        ArrayList<String> busTypeList = new ArrayList<String>();
        Collections.addAll(busTypeList, busTypes);
        AgentBusinfoFreezeExample agentBusinfoFreezeExample = new AgentBusinfoFreezeExample();
        AgentBusinfoFreezeExample.Criteria criteria = agentBusinfoFreezeExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andBusNumEqualTo(String.valueOf(hashMap.get("busNum"))).andPlatTypeIn(busTypeList);
        List<AgentBusinfoFreeze> agentBusinfoFreezeList = agentBusinfoFreezeMapper.selectByExample(agentBusinfoFreezeExample);
        log.info("冻结查询结果：{}",agentBusinfoFreezeList);
        List<Map<String,Object>> resultList = new ArrayList<>();
        if(null==agentBusinfoFreezeList || agentBusinfoFreezeList.size()==0)
            resultMap.put("freezeInfo","");
        for (AgentBusinfoFreeze agentBusinfoFreeze : agentBusinfoFreezeList) {
            Map<String, Object> map = new HashMap<>();
            //直签  非直签
            map.put("busNum",agentBusinfoFreeze.getBusNum());
            map.put("busType",agentBusinfoFreeze.getPlatType());
            if(null!=agentBusinfoFreeze.getFreezeType()){
                map.put("freezeType",agentBusinfoFreeze.getFreezeType());
            }
            //冻结内容--分润 返现冻结 PROFIT_FREEZR REFLOW_FREEZE
            if(null!=agentBusinfoFreeze.getProfitFreeze()){
                map.put("profitFreeze",agentBusinfoFreeze.getProfitFreeze());
            }
            if(null!=agentBusinfoFreeze.getReflowFreeze()){
                map.put("reflowFreeze",agentBusinfoFreeze.getReflowFreeze());
            }
            //冻结范围 月结 日结冻结  MONTHLY_FREEZE  DAILY_FREEZE
            if(null!=agentBusinfoFreeze.getMonthlyFreeze()){
                map.put("monthlyFreeze",agentBusinfoFreeze.getMonthlyFreeze());
            }
            if(null!=agentBusinfoFreeze.getDailyFreeze()){
                map.put("dailyFreeze",agentBusinfoFreeze.getDailyFreeze());
            }
            //冻结方式  停发  提现（结算）冻结  停算 STOP_PROFIT_FREEZE  CASH_FREEZE  STOP_COUNT
            if(null!=agentBusinfoFreeze.getStopProfitFreeze()){
                map.put("stopProfitFreeze",agentBusinfoFreeze.getStopProfitFreeze());
            }
            if(null!=agentBusinfoFreeze.getCashFreeze()){
                map.put("cashFreeze",agentBusinfoFreeze.getCashFreeze());
            }
            if(null!=agentBusinfoFreeze.getStopCount()){
                map.put("stopCount",agentBusinfoFreeze.getStopCount());
            }
            //是否限制登录
            if(null!=agentBusinfoFreeze.getBusFreeze()){
                map.put("busFreeze",agentBusinfoFreeze.getBusFreeze());
            }
            resultList.add(map);
        }
        log.info("查询结果：{}",resultList);
        resultMap.put("freezeInfo",resultList);
        return resultMap;
    }

    @Override
    public PageInfo agentFreezeListAll(AgentFreeze agentFreeze, Page page){

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
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getBusFreeze()))){
            reqMap.put("busFreeze",agentFreeze.getBusFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getProfitFreeze()))){
            reqMap.put("profitFreeze",agentFreeze.getProfitFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getReflowFreeze()))){
            reqMap.put("reflowFreeze",agentFreeze.getReflowFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getMonthlyFreeze()))){
            reqMap.put("monthlyFreeze",agentFreeze.getMonthlyFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getDailyFreeze()))){
            reqMap.put("dailyFreeze",agentFreeze.getDailyFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getStopProfitFreeze()))){
            reqMap.put("stopProfitFreeze",agentFreeze.getStopProfitFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getCashFreeze()))){
            reqMap.put("cashFreeze",agentFreeze.getCashFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getStopCount()))){
            reqMap.put("stopCount",agentFreeze.getStopCount());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getBusPlatform()))){
            reqMap.put("busPlatform",agentFreeze.getBusPlatform());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getFreezeType()))){
            reqMap.put("freezeType",agentFreeze.getFreezeType());
        }
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.parseLong(agentFreeze.getFreezePerson()));
        if (orgCodeRes == null && orgCodeRes.size() != 1) {
            return null;
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgId = String.valueOf(stringObjectMap.get("ORGID"));
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        List<Map<String, String>> resultMaps = agentFreezeMapper.queryAgentFreezeAll(reqMap,page);
        for (Map<String, String> resultMap : resultMaps) {
            resultMap.put("FREESTATUS_MSG",FreeStatus.getContentByValue(new BigDecimal(resultMap.get("FREESTATUS"))));
            resultMap.put("FREEZE_CAUSE_MSG",FreeCause.getContentByValue(resultMap.get("FREEZE_CAUSE")));
            resultMap.put("FREEZE_STATUS_MSG",FreeStatus.getContentByValue(new BigDecimal(resultMap.get("FREEZE_STATUS"))));
            resultMap.put("FREEZE_TYPE",FreeType.getmsg(new BigDecimal(String.valueOf(resultMap.get("FREEZE_TYPE")))));
            resultMap.put("UNFREEZE_CAUSE",UnfreeCause.getContentByValue(resultMap.get("UNFREEZE_CAUSE")).equals("")?resultMap.get("UNFREEZE_CAUSE"):UnfreeCause.getContentByValue(resultMap.get("UNFREEZE_CAUSE")));
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
            List<Map<String, Object>> orgCodeResTmp = iUserService.orgCode(Long.parseLong(resultMap.get("FREEZE_PERSON")));
            if (orgCodeResTmp == null || orgCodeResTmp.size() != 1) {
                continue;
            }
            Map<String, Object> stringObjectMapTmp = orgCodeResTmp.get(0);
            String organizationCodeTmp = String.valueOf(stringObjectMapTmp.get("ORGANIZATIONCODE"));
            resultMap.put("ORGANIZATIONCODE_C", organizationCodeTmp);
            resultMap.put("ORGANIZATIONCODE_Q",organizationCode);
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(resultMaps);
        pageInfo.setTotal(agentFreezeMapper.queryAgentFreezeCountAll(reqMap));
        return pageInfo;
    }

    @Override
    public PageInfo agentFreezeListRegion(AgentFreeze agentFreeze, Page page){
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
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.parseLong(agentFreeze.getFreezePerson()));
        if (orgCodeRes == null && orgCodeRes.size() != 1) {
            return null;
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getBusFreeze()))){
            reqMap.put("busFreeze",agentFreeze.getBusFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getProfitFreeze()))){
            reqMap.put("profitFreeze",agentFreeze.getProfitFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getReflowFreeze()))){
            reqMap.put("reflowFreeze",agentFreeze.getReflowFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getMonthlyFreeze()))){
            reqMap.put("monthlyFreeze",agentFreeze.getMonthlyFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getDailyFreeze()))){
            reqMap.put("dailyFreeze",agentFreeze.getDailyFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getStopProfitFreeze()))){
            reqMap.put("stopProfitFreeze",agentFreeze.getStopProfitFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getCashFreeze()))){
            reqMap.put("cashFreeze",agentFreeze.getCashFreeze());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getStopCount()))){
            reqMap.put("stopCount",agentFreeze.getStopCount());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getBusPlatform()))){
            reqMap.put("busPlatform",agentFreeze.getBusPlatform());
        }
        if(StringUtils.isNotBlank(String.valueOf(agentFreeze.getFreezeType()))){
            reqMap.put("freezeType",agentFreeze.getFreezeType());
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgId = String.valueOf(stringObjectMap.get("ORGID"));
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        reqMap.put("orgId", orgId);
        reqMap.put("userId", agentFreeze.getFreezePerson());
        reqMap.put("organizationCode", organizationCode);
        List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(Long.parseLong(agentFreeze.getFreezePerson()));
        reqMap.put("platfromPerm",platfromPerm);
        List<Map<String, String>> resultMaps = agentFreezeMapper.queryAgentFreezeListRegion(reqMap,page);
        for (Map<String, String> resultMap : resultMaps) {
            resultMap.put("FREESTATUS_MSG",FreeStatus.getContentByValue(new BigDecimal(resultMap.get("FREESTATUS"))));
            resultMap.put("FREEZE_CAUSE_MSG",FreeCause.getContentByValue(resultMap.get("FREEZE_CAUSE")));
            resultMap.put("FREEZE_STATUS_MSG",FreeStatus.getContentByValue(new BigDecimal(resultMap.get("FREEZE_STATUS"))));
            resultMap.put("FREEZE_TYPE",FreeType.getmsg(new BigDecimal(String.valueOf(resultMap.get("FREEZE_TYPE")))));
            resultMap.put("UNFREEZE_CAUSE",UnfreeCause.getContentByValue(resultMap.get("UNFREEZE_CAUSE")).equals("")?resultMap.get("UNFREEZE_CAUSE"):UnfreeCause.getContentByValue(resultMap.get("UNFREEZE_CAUSE")));
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
            List<Map<String, Object>> orgCodeResTmp = iUserService.orgCode(Long.parseLong(resultMap.get("FREEZE_PERSON")));
            if (orgCodeResTmp == null || orgCodeResTmp.size() != 1) {
                continue;
            }
            Map<String, Object> stringObjectMapTmp = orgCodeResTmp.get(0);
            String organizationCodeTmp = String.valueOf(stringObjectMapTmp.get("ORGANIZATIONCODE"));
            resultMap.put("ORGANIZATIONCODE_C", organizationCodeTmp);
            resultMap.put("ORGANIZATIONCODE_Q",organizationCode);

        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(resultMaps);
        pageInfo.setTotal(agentFreezeMapper.queryAgentFreezeCountRegion(reqMap));
        return pageInfo;
    }

    @Override
    public AgentResult freezeNewBus(AgentFreezePort agentFreezePort) {
        log.info("新增业务平台开始冻结{},{}",agentFreezePort.getAgentId(),agentFreezePort.getBusPlatform());
        if (agentFreezePort.getBusPlatform() != null && agentFreezePort.getBusPlatform().size() != 0){
            for (String busId:agentFreezePort.getBusPlatform()){
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
                if (agentBusInfo == null){
                    return  AgentResult.fail("业务信息不存在");
                }
                AgentFreezeExample agentFreezeExample = new AgentFreezeExample();
                agentFreezeExample.or().andStatusEqualTo(Status.STATUS_1.status)
                        .andAgentIdEqualTo(agentFreezePort.getAgentId())
                        .andNewBusFreezeEqualTo(Status.STATUS_1.status)
                        .andFreezeStatusEqualTo(String.valueOf(FreeStatus.DJ.getValue()));
                agentFreezeExample.setOrderByClause("FREEZE_DATE desc");

                List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(agentFreezeExample);
                if (agentFreezes== null || agentFreezes.size() == 0){
                    return AgentResult.ok("不需要冻结新增业务");
                }else {

                    AgentFreezeExample agentFreezeExampleQuerry = new AgentFreezeExample();
                    AgentFreezeExample.Criteria criteria = agentFreezeExampleQuerry.createCriteria();
                    criteria.andFreezeTypeIsNull();
                    criteria.andStatusEqualTo(Status.STATUS_1.status);
                    criteria.andAgentIdEqualTo(agentFreezes.get(0).getAgentId());
                    criteria.andFreezeCauseEqualTo(agentFreezes.get(0).getFreezeCause());
                    criteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                    criteria.andBusIdEqualTo(busId);
                    agentFreezeExampleQuerry.or()
                            .andFreezeTypeEqualTo(FreeType.AGNET.code)
                            .andStatusEqualTo(Status.STATUS_1.status)
                            .andAgentIdEqualTo(agentFreezes.get(0).getAgentId())
                            .andFreezeCauseEqualTo(agentFreezes.get(0).getFreezeCause())
                            .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString())
                            .andBusIdEqualTo(busId);
                    List<AgentFreeze> agentFreezeQs = agentFreezeMapper.selectByExample(agentFreezeExampleQuerry);
                    if(agentFreezeQs.size()!=0){
                        return AgentResult.ok("代理商此原因已被冻结:"+FreeCause.getContentByValue(agentFreezes.get(0).getFreezeCause()));
                    }


                    AgentFreeze agentFreeze = new AgentFreeze();
                    agentFreeze.setId(idService.genId(TabId.a_agent_freeze));
                    agentFreeze.setAgentId(agentFreezePort.getAgentId());
                    agentFreeze.setFreezeStatus(FreeStatus.DJ.getValue().toString());
                    agentFreeze.setFreezeCause(agentFreezes.get(0).getFreezeCause());
                    agentFreeze.setFreezeDate(new Date());
                    agentFreeze.setFreezePerson(agentFreezePort.getOperationPerson());
                    agentFreeze.setFreezeNum(agentFreezePort.getFreezeNum());
                    agentFreeze.setRemark(agentFreezePort.getRemark());
                    agentFreeze.setStatus(Status.STATUS_1.status);
                    agentFreeze.setVersion(BigDecimal.ONE);
                    agentFreeze.setFreezeType(FreeType.AGNET.code);
                    /** 保存新增字段 **/
                    AgentBusInfo agentBusInfoTmp = agentBusinfoService.getById(busId);
                    if (agentBusInfoTmp.getBusNum() == null || agentBusInfoTmp.getBusPlatform() == null){
                        return AgentResult.fail("业务平台信息不完整!");
                    }
                    agentFreeze.setBusPlatform(agentBusInfoTmp.getBusPlatform());
                    agentFreeze.setBusId(busId);
                    agentFreeze.setBusNum(agentBusInfoTmp.getBusNum());
                    agentFreeze.setNewBusFreeze(BigDecimal.ZERO);
                    agentFreeze.setBusFreeze(agentFreezes.get(0).getBusFreeze());
                    agentFreeze.setProfitFreeze(agentFreezes.get(0).getProfitFreeze());
                    agentFreeze.setReflowFreeze(agentFreezes.get(0).getReflowFreeze());
                    agentFreeze.setMonthlyFreeze(agentFreezes.get(0).getMonthlyFreeze());
                    agentFreeze.setDailyFreeze(agentFreezes.get(0).getDailyFreeze());
                    agentFreeze.setStopProfitFreeze(agentFreezes.get(0).getStopProfitFreeze());
                    agentFreeze.setCashFreeze(agentFreezes.get(0).getCashFreeze());
                    agentFreeze.setStopCount(agentFreezes.get(0).getStopCount());
                    if (1!=agentFreezeMapper.insert(agentFreeze)){
                        return AgentResult.fail("冻结信息保存失败!");
                    }
                }
            }
            return AgentResult.ok();
        }else {
            return AgentResult.fail("缺少业务平台码(AB码)");
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public AgentResult agentFreezeLocal(AgentFreezePort agentFreezePort) throws MessageException {

        log.info("代理商冻结请求参数：{}",JsonUtil.objectToJson(agentFreezePort));
        String indentifier = "";
        Map resMap = new HashMap();
        try {
            indentifier = redisService.lockWithTimeout(RedisCachKey.AGENT_FREEZE_LOCK + agentFreezePort.getOperationPerson()+agentFreezePort.getFreezeNum(), RedisService.ACQUIRE_TIME_OUT, RedisService.TIME_OUT);
            if (StringUtils.isBlank(indentifier)) {
                return AgentResult.fail("系统处理中,请勿重复提交！");
            }
            AgentResult verify = verify(agentFreezePort,FreeStatus.DJ.getValue(),BigDecimal.ZERO);
            if(!verify.isOK()){
                return verify;
            }
            if (agentFreezePort.getBusPlatform() == null || agentFreezePort.getBusPlatform().size()==0){
                return AgentResult.fail("代理商业务平台参数未传入");
            }else {
                for (String  BusId:agentFreezePort.getBusPlatform()){
                    AgentBusInfo agentBusInfo = agentBusinfoService.queryAgentBusInfoById(BusId);
                    if (agentBusInfo.getBusNum()==null){
                        AppConfig.sendEmails("代理商冻结失败,业务平台信息不完整："+ JsonUtil.objectToJson(agentFreezePort), "冻结失败报警");
                        return AgentResult.fail("代理商业务平台码不能为空");
                    }
                }

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
                        criteria.andBusIdEqualTo(busPlatform);
                    }
                    agentFreezeExample.or()
                            .andFreezeTypeEqualTo(freeType)
                            .andStatusEqualTo(Status.STATUS_1.status)
                            .andAgentIdEqualTo(agentFreezePort.getAgentId())
                            .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                            .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString())
                            .andBusIdEqualTo(busPlatform);
                    List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(agentFreezeExample);
                    if(agentFreezes.size()!=0){
                        log.info("存在冻结记录{},跳过",JsonUtil.objectToJson(agentFreezes));
                        continue;
                    }
                    //检查是否有在审批中的冻结申请
                    FreezeRequestDetailExample freezeRequestDetailExample = new FreezeRequestDetailExample();
                    freezeRequestDetailExample.or()
                            .andAgentIdEqualTo(agentFreezePort.getAgentId())
                            .andFreezeTypeEqualTo(freeType)
                            .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                            .andBusIdEqualTo(busPlatform)
                            .andStatusEqualTo(Status.STATUS_1.status);
                    List<FreezeRequestDetail> freezeRequestDetails = freezeRequestDetailMapper.selectByExample(freezeRequestDetailExample);
                    if (freezeRequestDetails!=null && freezeRequestDetails.size()>0){
                        for (FreezeRequestDetail freezeRequestDetail : freezeRequestDetails) {
                            String freezeReqId = freezeRequestDetail.getFreezeReqId();
                            FreezeRequest freezeRequestApp = freezeRequestMapper.selectByPrimaryKey(freezeReqId);
                            if (freezeRequestApp !=null && freezeRequestApp.getReviewsStat().compareTo(AgStatus.Approving.status)==0 && freezeRequestApp.getStatus().compareTo(Status.STATUS_1.status)==0){
                                throw new MessageException("代理商此原因已申请冻结:AG:"+agentFreezePort.getAgentId()+",冻结原因:"+FreeCause.getContentByValue(agentFreezePort.getFreezeCause()));
                            }
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
                    /** 保存新增字段 **/
                    AgentBusInfo agentBusInfo = agentBusinfoService.getById(busPlatform);
                    agentFreeze.setBusPlatform(agentBusInfo.getBusPlatform());
                    agentFreeze.setBusId(busPlatform);
                    agentFreeze.setBusNum(agentBusInfo.getBusNum());
                    agentFreeze.setNewBusFreeze(agentFreezePort.getCurLevel()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getNewBusFreeze());
                    agentFreeze.setBusFreeze(agentFreezePort.getCurLevel()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getBusFreeze());
                    agentFreeze.setProfitFreeze(agentFreezePort.getCurLevel()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getProfitFreeze());
                    agentFreeze.setReflowFreeze(agentFreezePort.getCurLevel()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getReflowFreeze());
                    agentFreeze.setMonthlyFreeze(agentFreezePort.getCurLevel()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getMonthlyFreeze());
                    agentFreeze.setDailyFreeze(agentFreezePort.getCurLevel()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getDailyFreeze());
                    agentFreeze.setStopProfitFreeze(agentFreezePort.getCurLevel()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getStopProfitFreeze());
                    agentFreeze.setCashFreeze(agentFreezePort.getCurLevel()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getCashFreeze());
                    agentFreeze.setStopCount(agentFreezePort.getCurLevel()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getStopCount());
                    if(StringUtils.isNotBlank(agentFreezePort.getRemark())){//备注
                        agentFreeze.setRemark(agentFreezePort.getRemark());
                    }
                    agentFreezeMapper.insert(agentFreeze);
                    resMap.put("data",agentFreeze);
                }
            }


            return AgentResult.ok(resMap);
        }catch (Exception exception){
            log.error("代理商冻结异常:AG:{},冻结原因:{}",agentFreezePort.getAgentId(),FreeCause.getContentByValue(agentFreezePort.getFreezeCause()));
            throw new MessageException("代理商冻结异常:AG:"+agentFreezePort.getAgentId()+",冻结原因:"+FreeCause.getContentByValue(agentFreezePort.getFreezeCause()));
        }finally {
            if(StringUtils.isNotBlank(indentifier)){
                redisService.releaseLock(RedisCachKey.AGENT_FREEZE_LOCK + agentFreezePort.getOperationPerson()+agentFreezePort.getFreezeNum(), indentifier);
            }
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public AgentResult agentUnFreezeLocal(AgentFreezePort agentFreezePort) throws MessageException {

        log.info("代理商解冻请求参数：{}",JsonUtil.objectToJson(agentFreezePort));
        String indentifier = "";
        Map resMap = new HashMap();
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
                //检查是否有在审批中的解冻申请
                FreezeRequestDetailExample freezeRequestDetailExample = new FreezeRequestDetailExample();
                if (freeType.compareTo(FreeType.AGNET.code) == 0){
                    AgentFreezeExample.Criteria freezeCriteria = freezeExample.createCriteria();
                    freezeCriteria.andFreezeTypeIsNull();
                    freezeCriteria.andStatusEqualTo(Status.STATUS_1.status);
                    freezeCriteria.andAgentIdEqualTo(agentFreezePort.getAgentId());
                    freezeCriteria.andFreezeCauseEqualTo(agentFreezePort.getFreezeCause());
                    freezeCriteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                    if (agentFreezePort.getBusPlatform()!= null && agentFreezePort.getBusPlatform().get(0)!=null){
                        freezeCriteria.andBusIdEqualTo(agentFreezePort.getBusPlatform().get(0));
                    }
                    //不传入业务平台码则查找AG+层级+冻结原因类型的冻结

                }
                if (agentFreezePort.getBusPlatform()!= null && agentFreezePort.getBusPlatform().get(0)!=null){
                    freezeExample.or().andFreezeTypeEqualTo(freeType)
                            .andStatusEqualTo(Status.STATUS_1.status)
                            .andAgentIdEqualTo(agentFreezePort.getAgentId())
                            .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                            .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString())
                            .andBusIdEqualTo(agentFreezePort.getBusPlatform().get(0));
                    freezeRequestDetailExample.or()
                            .andAgentIdEqualTo(agentFreezePort.getAgentId())
                            .andFreezeTypeEqualTo(freeType)
                            .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                            .andBusIdEqualTo(agentFreezePort.getBusPlatform().get(0))
                            .andStatusEqualTo(Status.STATUS_1.status);
                }else {
                    freezeExample.or().andFreezeTypeEqualTo(freeType)
                            .andStatusEqualTo(Status.STATUS_1.status)
                            .andAgentIdEqualTo(agentFreezePort.getAgentId())
                            .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                            .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                    freezeRequestDetailExample.or()
                            .andAgentIdEqualTo(agentFreezePort.getAgentId())
                            .andFreezeTypeEqualTo(freeType)
                            .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                            .andStatusEqualTo(Status.STATUS_1.status);
                }

                List<AgentFreeze> agentFreezeList = agentFreezeMapper.selectByExample(freezeExample);
                if(agentFreezeList.size()==0){
                    log.info("解冻信息不存在{}",JsonUtil.objectToJson(agentFreezePort));
                    return AgentResult.ok("解冻信息不存在");
                }
//                if(agentFreezeList.size()!=1){
//                    return AgentResult.fail("解冻信息不唯一,请联系管理员");
//                }
                List<FreezeRequestDetail> freezeRequestDetails = freezeRequestDetailMapper.selectByExample(freezeRequestDetailExample);
                if (freezeRequestDetails!=null && freezeRequestDetails.size()>0){
                    for (FreezeRequestDetail freezeRequestDetail : freezeRequestDetails) {
                        String freezeReqId = freezeRequestDetail.getFreezeReqId();
                        FreezeRequest freezeRequestApp = freezeRequestMapper.selectByPrimaryKey(freezeReqId);
                        if (freezeRequestApp !=null
                                && freezeRequestApp.getReviewsStat().compareTo(AgStatus.Approving.status)==0
                                && freezeRequestApp.getStatus().compareTo(Status.STATUS_1.status)==0
                                && freezeRequestApp.getReqType().compareTo(FreezeRequestType.UnFreeze.code)==0){
                            throw new MessageException("代理商["+agentFreezePort.getAgentId()+"]此原因已申请解冻:"+FreeCause.getContentByValue(agentFreezePort.getFreezeCause()));
                        }
                    }

                }

                for (AgentFreeze agentFreeze : agentFreezeList) {
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
                    resMap.put("data",agentFreeze);
                }
            }
            return AgentResult.ok(resMap);
        }finally {
            if(StringUtils.isNotBlank(indentifier)){
                redisService.releaseLock(RedisCachKey.AGENT_UN_FREEZE_LOCK + agentFreezePort.getOperationPerson(), indentifier);
            }
        }
    }


}
