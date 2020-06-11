package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentFreezeMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.FreezeRequestDetailMapper;
import com.ryx.credit.dao.agent.FreezeRequestMapper;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentFreezePort;
import com.ryx.credit.pojo.admin.vo.FreezeDetail;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.BusActRelService;
import com.ryx.credit.service.agent.FreezeRequestService;
import com.ryx.credit.service.dict.DictOptionsService;
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

@Service("freezeRequestService")
public class FreezeRequestServiceImpl implements FreezeRequestService {

    private static Logger logger = LoggerFactory.getLogger(FreezeRequestServiceImpl.class);

    @Autowired
    private FreezeRequestMapper freezeRequestMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private IUserService userService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentFreezeMapper agentFreezeMapper;
    @Autowired
    private FreezeRequestDetailMapper freezeRequestDetailMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private BusActRelService busActRelService;
    @Override
    public PageInfo agentFreezeList(Map map, Page page) {
        PageInfo pageInfo = new PageInfo();
        map.put("page", page);
        pageInfo.setTotal(freezeRequestMapper.queryAgentFreezeRequestListCount(map));
        pageInfo.setRows(freezeRequestMapper.queryAgentFreezeRequestList(map, page));
        return pageInfo;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public AgentResult agentFreeze(AgentFreezePort agentFreezePort) throws MessageException {
        AgentResult checkRuleRest =  checkFreezeRule(agentFreezePort);
        if (!checkRuleRest.isOK()){
           return  checkRuleRest;
        }
        AgentResult verify = verify(agentFreezePort, FreeStatus.DJ.getValue(),BigDecimal.ZERO);
        if(!verify.isOK()){
            return verify;
        }
        FreezeRequest freezeRequest = new FreezeRequest();
        freezeRequest.setId(idService.genId(TabId.a_freeze_request));
        freezeRequest.setReqType(FreezeRequestType.Freeze.code);
        freezeRequest.setcTm(new Date());
        freezeRequest.setcUserId(agentFreezePort.getOperationPerson());
        freezeRequest.setFreezeCause(agentFreezePort.getFreezeCause());
        freezeRequest.setReqReason(agentFreezePort.getRemark());
        freezeRequest.setStatus(Status.STATUS_1.status);
        freezeRequest.setVersion(BigDecimal.ONE);
        if (freezeRequestMapper.insert(freezeRequest)!=1){
            throw new MessageException("代理商冻结申请保存失败!");
        }
        for(String busPlatform:agentFreezePort.getBusPlatform()){
            for (BigDecimal freeType:agentFreezePort.getFreeType()){
                logger.info("冻结类型为[{}]",FreeType.getmsg(freeType));
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
                FreezeRequestDetail agentFreeze = new FreezeRequestDetail();
                agentFreeze.setId(idService.genId(TabId.a_freeze_request_detail));
                agentFreeze.setFreezeReqId(freezeRequest.getId());
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
                agentFreeze.setNewBusFreeze(new BigDecimal(agentFreezePort.getNewBusFreeze()));
                /** 保存新增字段 **/
                AgentBusInfo agentBusInfo = agentBusinfoService.getById(busPlatform);
                agentFreeze.setBusPlatform(agentBusInfo.getBusPlatform());
                agentFreeze.setBusId(busPlatform);
                agentFreeze.setBusNum(agentBusInfo.getBusNum());
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
                if (freezeRequestDetailMapper.insert(agentFreeze)!=1){
                    throw new MessageException("代理商冻结申请明细保存失败!");
                }
            }
        }

        return AgentResult.ok("申请冻结成功");
    }

    @Override
    public AgentResult agentFreezeFinish(String insid, String actname) throws Exception {
        logger.info("订单调整审批完成:{},{}", insid, actname);
        //审批流关系
        BusActRel busActRel = busActRelService.findById(insid);
        if (actname.equals("finish_end")) { //审批完成
            logger.info("审批通过{}", busActRel.getBusId());
            busActRel.setActivStatus(AgStatus.Approved.name());
            if (1 != busActRelService.updateByPrimaryKey(busActRel)) {
                throw new MessageException("请重新提交！");
            }

        } else if(actname.equals("reject_end")) { //审批拒绝
            logger.info("审批完审批拒绝{}", busActRel.getBusId());
            busActRel.setActivStatus(AgStatus.Refuse.name());
            if (1 != busActRelService.updateByPrimaryKey(busActRel)) {
                throw new MessageException("请重新提交！");
            }



        }
        logger.info("订单调整审批结束", busActRel.getBusId());
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
                logger.info("[{}]未传入冻结层级，默认本级",agentFreezePort.getAgentId());
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

    @Override
    public AgentResult agentFreezeModify(AgentFreezePort agentFreezePort) throws MessageException {
        logger.info("代理商申请变更请求参数：{}", JsonUtil.objectToJson(agentFreezePort));
        try {
            AgentResult checkFreezeRule = checkFreezeRule(agentFreezePort);
            if (!checkFreezeRule.isOK()){
                return checkFreezeRule;
            }
            AgentResult verify = verify(agentFreezePort,FreeStatus.DJ.getValue(),BigDecimal.ONE);
            if(!verify.isOK()){
                return verify;
            }
            //查询原冻结信息
            AgentFreezeExample orgFreezeExample = new AgentFreezeExample();
            orgFreezeExample.or().andAgentIdEqualTo(agentFreezePort.getAgentId())
                    .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                    .andFreezeTypeEqualTo(agentFreezePort.getFreeType().get(0))
                    .andStatusEqualTo(Status.STATUS_1.status);
            List<AgentFreeze> orgAgentFreezeList = agentFreezeMapper.selectByExample(orgFreezeExample);
            if (orgAgentFreezeList == null || orgAgentFreezeList.size() == 0){
                throw new MessageException("代理商冻结信息不存在!");
            }
            //创建新的冻结任务
            FreezeRequest freezeRequest = new FreezeRequest();
            freezeRequest.setId(idService.genId(TabId.a_freeze_request));
            freezeRequest.setReqType(FreezeRequestType.Modify.code);
            freezeRequest.setcTm(new Date());
            freezeRequest.setcUserId(agentFreezePort.getOperationPerson());
            freezeRequest.setFreezeCause(agentFreezePort.getFreezeCause());
            freezeRequest.setReqReason(agentFreezePort.getRemark());
            freezeRequest.setReviewsStat(AgStatus.Approving.status);
            freezeRequest.setStatus(Status.STATUS_1.status);
            freezeRequest.setVersion(BigDecimal.ONE);
            if (freezeRequestMapper.insert(freezeRequest)!=1){
                throw new MessageException("代理商冻结变更保存失败!");
            }
            for(String busPlatform:agentFreezePort.getBusPlatform()){
                for (BigDecimal freeType:agentFreezePort.getFreeType()){
                    logger.info("冻结类型为[{}]",FreeType.getmsg(freeType));
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
                        throw new MessageException("代理商此原因已被冻结:"+FreeType.getmsg(freeType));
                    }
                    FreezeRequestDetail agentFreeze = new FreezeRequestDetail();
                    agentFreeze.setId(idService.genId(TabId.a_freeze_request_detail));
                    agentFreeze.setFreezeReqId(freezeRequest.getId());
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
                    agentFreeze.setNewBusFreeze(new BigDecimal(agentFreezePort.getNewBusFreeze()));
                    /** 保存新增字段 **/
                    AgentBusInfo agentBusInfo = agentBusinfoService.getById(busPlatform);
                    agentFreeze.setBusPlatform(agentBusInfo.getBusPlatform());
                    agentFreeze.setBusId(busPlatform);
                    agentFreeze.setBusNum(agentBusInfo.getBusNum());
                    agentFreeze.setBusFreeze(agentFreezePort.getCurLevel().getBusFreeze());
                    agentFreeze.setProfitFreeze(agentFreezePort.getCurLevel().getProfitFreeze());
                    agentFreeze.setReflowFreeze(agentFreezePort.getCurLevel().getReflowFreeze());
                    agentFreeze.setMonthlyFreeze(agentFreezePort.getCurLevel().getMonthlyFreeze());
                    agentFreeze.setDailyFreeze(agentFreezePort.getCurLevel().getDailyFreeze());
                    agentFreeze.setStopProfitFreeze(agentFreezePort.getCurLevel().getStopProfitFreeze());
                    agentFreeze.setCashFreeze(agentFreezePort.getCurLevel().getCashFreeze());
                    agentFreeze.setStopCount(agentFreezePort.getCurLevel().getStopCount());
                    /**
                     * 保存原信息
                     */
                    agentFreeze.setBusFreezeOrg(orgAgentFreezeList.get(0).getBusFreeze());
                    agentFreeze.setProfitFreezeOrg(orgAgentFreezeList.get(0).getProfitFreeze());
                    agentFreeze.setReflowFreezeOrg(orgAgentFreezeList.get(0).getReflowFreeze());
                    agentFreeze.setMonthlyFreezeOrg(orgAgentFreezeList.get(0).getMonthlyFreeze());
                    agentFreeze.setDailyFreezeOrg(orgAgentFreezeList.get(0).getDailyFreeze());
                    agentFreeze.setStopProfitFreezeOrg(orgAgentFreezeList.get(0).getStopProfitFreeze());
                    agentFreeze.setCashFreezeOrg(orgAgentFreezeList.get(0).getCashFreeze());
                    agentFreeze.setStopCountOrg(orgAgentFreezeList.get(0).getStopCount());

                    if(StringUtils.isNotBlank(agentFreezePort.getRemark())){//备注
                        agentFreeze.setRemark(agentFreezePort.getRemark());
                    }
                    if (freezeRequestDetailMapper.insert(agentFreeze)!=1){
                        throw new MessageException("代理商冻结申请明细保存失败!");
                    }
                }
            }
            return AgentResult.ok("冻结申请变更成功!");
        }catch (Exception e){
            return AgentResult.fail("冻结申请变更失败!");
        }

    }

    @Override
    public AgentResult approvalTask(AgentFreezePort agentFreezePort, String userId) throws Exception {
        try {
            AgentResult result = new AgentResult();/*agentEnterService.completeTaskEnterActivity(agentVo, userId);*/
            if (!result.isOK()) {
                logger.error(result.getMsg());
                throw new MessageException("工作流处理任务异常");
            }
        } catch (MessageException | ProcessException e) {
            e.printStackTrace();
            throw new MessageException(e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("catch工作流处理任务异常!");
        }
        return AgentResult.ok();
    }

    @Override
    public FreezeRequest queryFreezeRequestById(String id)  {
        FreezeRequest freezeRequest = freezeRequestMapper.selectByPrimaryKey(id);
        return freezeRequest;
    }

    private AgentResult checkFreezeRule(AgentFreezePort agentFreezePort){
        List<Dict> dicts = dictOptionsService.dictList(DictGroup.FREEZE_RULE.name(), agentFreezePort.getFreezeCause());
        Map<String,Object> ruleMap = new HashMap<String, Object>();
        for (Dict dict:dicts){
            ruleMap.put(dict.getdItemvalue(),dict.getdItemname()+"-"+dict.getdItemnremark());
        }
        FreezeDetail cur = agentFreezePort.getCurLevel();
        FreezeDetail sub = agentFreezePort.getSubLevel();
        if (cur!= null){
            Map curMap = TransformUnderlineUtils.transform(cur);
            //1:对比本级代理商
            for(Map.Entry<String, Object> rulEentry:ruleMap.entrySet()){
                String ruleValue = rulEentry.getValue() == null?"":String.valueOf(rulEentry.getValue()).substring(0,String.valueOf(rulEentry.getValue()).lastIndexOf("-"));
                String curValue = curMap.get(rulEentry.getKey())==null?"":String.valueOf(curMap.get(rulEentry.getKey()));
                if (!curValue.equals("") && !ruleValue.equals(curValue) && ruleValue.equals("0")) {//若两个map中相同key对应的value不相等
                    return AgentResult.fail("存在不允许配置的选项:"+String.valueOf(rulEentry.getValue()).substring(String.valueOf(rulEentry.getValue()).lastIndexOf("-")+1));
                }
            }
        }
        if (null != sub){
            Map subMap = TransformUnderlineUtils.transform(sub);
            //2:对比非直签下级
            for(Map.Entry<String, Object> rulEentry:ruleMap.entrySet()){
                String ruleValue = rulEentry.getValue() == null?"":String.valueOf(rulEentry.getValue());
                String curValue = subMap.get(rulEentry.getKey())==null?"":String.valueOf(subMap.get(rulEentry.getKey()));
                if (!curValue.equals("") && !ruleValue.equals(curValue) && ruleValue.equals("0")) {//若两个map中相同key对应的value不相等
                    return AgentResult.fail("存在不允许配置的选项"+String.valueOf(rulEentry.getValue()).substring(String.valueOf(rulEentry.getValue()).lastIndexOf("-")+1));
                }
            }
        }
        return AgentResult.ok();
    }


}
