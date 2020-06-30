package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.ActRuTaskService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
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
    @Autowired
    private AgentFreezeService agentFreezeService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentBusinfoFreezeService agentBusinfoFreezeService;
    @Autowired
    private IUserService iUserService;


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

        FreezeRequest freezeRequest = new FreezeRequest();
        freezeRequest.setId(idService.genId(TabId.a_freeze_request));
        freezeRequest.setReqType(FreezeRequestType.Freeze.code);
        freezeRequest.setcTm(new Date());
        freezeRequest.setcUserId(agentFreezePort.getOperationPerson());
        freezeRequest.setFreezeCause(agentFreezePort.getFreezeCause());
        freezeRequest.setReqReason(agentFreezePort.getRemark());
        freezeRequest.setReviewsStat(AgStatus.Approving.status);
        freezeRequest.setStatus(Status.STATUS_1.status);
        freezeRequest.setVersion(BigDecimal.ONE);
        agentFreezePort.setFreezeNum(freezeRequest.getId());
        AgentResult verify = verify(agentFreezePort, FreeStatus.DJ.getValue(),BigDecimal.ZERO);
        if(!verify.isOK()){
            return verify;
        }
        if (freezeRequestMapper.insert(freezeRequest)!=1){
            throw new MessageException("代理商冻结申请保存失败!");
        }
        if (agentFreezePort.getBusPlatform().size() == 0){
            throw new MessageException("获取业务平台异常!");
        }
        Map userMap = new HashMap();
        for (String busPlatform: agentFreezePort.getBusPlatform()){
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
                    criteria.andBusIdEqualTo(busPlatform);
                }
                agentFreezeExample.or()
                        .andFreezeTypeEqualTo(freeType)
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andAgentIdEqualTo(agentFreezePort.getAgentId())
                        .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                        .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString())
                        .andBusIdEqualTo(busPlatform);
                agentFreezeExample.or()
                        .andFreezeTypeEqualTo(freeType)
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andAgentIdEqualTo(agentFreezePort.getAgentId())
                        .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                        .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString())
                        .andBusIdIsNull();
                List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(agentFreezeExample);
                if(agentFreezes.size()!=0){
                    throw new MessageException("代理商此原因已被冻结:"+FreeType.getmsg(freeType));
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
                            throw new MessageException("代理商此原因已申请冻结:"+FreeType.getmsg(freeType));
                        }
                    }

                }
                FreezeRequestDetail  freezeRequestDetail  = new FreezeRequestDetail();
                freezeRequestDetail.setId(idService.genId(TabId.a_freeze_request_detail));
                freezeRequestDetail.setFreezeReqId(freezeRequest.getId());
                freezeRequestDetail.setAgentId(agentFreezePort.getAgentId());
                freezeRequestDetail.setFreezeStatus(FreeStatus.DJ.getValue().toString());
                freezeRequestDetail.setFreezeCause(agentFreezePort.getFreezeCause());
                freezeRequestDetail.setFreezeDate(new Date());
                freezeRequestDetail.setFreezePerson(agentFreezePort.getOperationPerson());
                freezeRequestDetail.setFreezeNum(freezeRequest.getId());
                freezeRequestDetail.setRemark(agentFreezePort.getRemark());
                freezeRequestDetail.setStatus(Status.STATUS_1.status);
                freezeRequestDetail.setVersion(BigDecimal.ONE);
                freezeRequestDetail.setFreezeType(freeType);
                freezeRequestDetail.setNewBusFreeze(new BigDecimal(agentFreezePort.getNewBusFreeze()));
                /** 保存新增字段 **/
                AgentBusInfo agentBusInfo = agentBusinfoService.queryAgentBusInfoById(busPlatform);
                if (agentBusInfo != null){
                    freezeRequestDetail.setBusPlatform(agentBusInfo.getBusPlatform());
                    freezeRequestDetail.setBusNum(agentBusInfo.getBusNum());
                }
                freezeRequestDetail.setBusId(busPlatform);
                if (freeType.compareTo(FreeType.AGNET.code)==0){
                    freezeRequestDetail.setBusFreeze(agentFreezePort.getCurLevel().getBusFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getBusFreeze());
                    freezeRequestDetail.setProfitFreeze(agentFreezePort.getCurLevel().getProfitFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getProfitFreeze());
                    freezeRequestDetail.setReflowFreeze(agentFreezePort.getCurLevel().getReflowFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getReflowFreeze());
                    freezeRequestDetail.setMonthlyFreeze(agentFreezePort.getCurLevel().getMonthlyFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getMonthlyFreeze());
                    freezeRequestDetail.setDailyFreeze(agentFreezePort.getCurLevel().getDailyFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getDailyFreeze());
                    freezeRequestDetail.setStopProfitFreeze(agentFreezePort.getCurLevel().getStopProfitFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getStopProfitFreeze());
                    freezeRequestDetail.setCashFreeze(agentFreezePort.getCurLevel().getCashFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getCashFreeze());
                    freezeRequestDetail.setStopCount(agentFreezePort.getCurLevel().getStopCount()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getStopCount());
                }else if (freeType.compareTo(FreeType.SUB_AGENT.code)==0){
                    freezeRequestDetail.setBusFreeze(agentFreezePort.getSubLevel().getBusFreeze()==null?BigDecimal.ZERO:agentFreezePort.getSubLevel().getBusFreeze());
                    freezeRequestDetail.setProfitFreeze(agentFreezePort.getSubLevel().getProfitFreeze()==null?BigDecimal.ZERO:agentFreezePort.getSubLevel().getProfitFreeze());
                    freezeRequestDetail.setReflowFreeze(agentFreezePort.getSubLevel().getReflowFreeze()==null?BigDecimal.ZERO:agentFreezePort.getSubLevel().getReflowFreeze());
                    freezeRequestDetail.setMonthlyFreeze(agentFreezePort.getSubLevel().getMonthlyFreeze()==null?BigDecimal.ZERO:agentFreezePort.getSubLevel().getMonthlyFreeze());
                    freezeRequestDetail.setDailyFreeze(agentFreezePort.getSubLevel().getDailyFreeze()==null?BigDecimal.ZERO:agentFreezePort.getSubLevel().getDailyFreeze());
                    freezeRequestDetail.setStopProfitFreeze(agentFreezePort.getSubLevel().getStopProfitFreeze()==null?BigDecimal.ZERO:agentFreezePort.getSubLevel().getStopProfitFreeze());
                    freezeRequestDetail.setCashFreeze(agentFreezePort.getSubLevel().getCashFreeze()==null?BigDecimal.ZERO:agentFreezePort.getSubLevel().getCashFreeze());
                    freezeRequestDetail.setStopCount(agentFreezePort.getSubLevel().getStopCount()==null?BigDecimal.ZERO:agentFreezePort.getSubLevel().getStopCount());
                }
                if(StringUtils.isNotBlank(agentFreezePort.getRemark())){//备注
                    freezeRequestDetail.setRemark(agentFreezePort.getRemark());
                }
                if (freezeRequestDetailMapper.insert(freezeRequestDetail)!=1){
                    throw new MessageException("代理商冻结申请明细保存失败!");
                }
                if (agentBusInfo!=null){
                    PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
                    if (platForm.getPlatformType().equals("RJPOS") || platForm.getPlatformType().equals("RJQZ")) {
                        userMap.put("RJ",dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),FreeApprovalUser.RJ.key).getdItemvalue());
                    }else {
                        userMap.put("NOT_RJ",dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),FreeApprovalUser.NOT_RJ.key).getdItemvalue());
                    }
                }else {
                    throw new MessageException("业务平台不存在");
                }
            }
        }

        //流程中的部门参数
        Map startPar = agentEnterService.startPar(freezeRequest.getcUserId());
        if (null == startPar) {
            logger.info("========用户{}启动{}部门参数为空", freezeRequest.getcUserId(), freezeRequest.getId());
            throw new MessageException("启动部门参数为空！");
        }
        List<String> userList = new ArrayList<>();
        if (userMap.get("RJ")!=null){
            userList.add(String.valueOf(userMap.get("RJ")));
        }
        if(userMap.get("NOT_RJ")!=null){
            userList.add(String.valueOf(userMap.get("NOT_RJ")));
        }
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(agentFreezePort.getOperationPerson()));
        if(orgCodeRes==null && orgCodeRes.size()!=1){
            throw new MessageException("部门参数为空");
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        if(orgCode.equals("finance")){
            userList.clear();
            userList.add(dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),FreeApprovalUser.FINANCE.key).getdItemvalue());
        }
        //Todo:增加判断是否为瑞+方法
        startPar.put("userList",userList);
        //启动审批
        String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("agentFreeze"), null, null, startPar);
        if (proce == null) {
            logger.info("冻结申请提交审批，审批流启动失败{}:{}", freezeRequest.getcUserId(), freezeRequest.getId());
            throw new MessageException("审批流启动失败！");
        }


        //添加审批关系
        BusActRel record = new BusActRel();
        record.setBusId(freezeRequest.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(freezeRequest.getcUserId());
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.freeze.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agentFreezePort.getAgentId());
        Agent agent = agentMapper.selectByPrimaryKey(agentFreezePort.getAgentId());
        if(agent!=null) {
            record.setAgentName(agent.getAgName());
        }
//        record.setNetInBusType("ACTIVITY_"+agentBusInfo.getBusPlatform());//数据权限
        record.setDataShiro(BusActRelBusType.freeze.key);
//        record.setAgDocPro(agentBusInfo.getAgDocPro());
//        record.setAgDocDistrict(agentBusInfo.getAgDocDistrict());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("冻结申请提交审批，启动审批异常，添加审批关系失败{}:{}", freezeRequest.getId(), proce);
            throw new MessageException("冻结申请审批流启动失败：添加审批关系失败！");
        }
        return AgentResult.ok("申请冻结成功");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public AgentResult agentUnFreeze(AgentFreezePort agentFreezePort) throws MessageException {
        AgentResult verify = verify(agentFreezePort, FreeStatus.JD.getValue(),BigDecimal.ZERO);
        if(!verify.isOK()){
            return verify;
        }
        if(StringUtils.isBlank(agentFreezePort.getUnfreezeCause())){
            return AgentResult.fail("解冻原因必填");
        }
        Map userMap = new HashMap();
        FreezeRequest freezeRequest = new FreezeRequest();
        freezeRequest.setId(idService.genId(TabId.a_freeze_request));
        freezeRequest.setReqType(FreezeRequestType.UnFreeze.code);
        freezeRequest.setcTm(new Date());
        freezeRequest.setcUserId(agentFreezePort.getOperationPerson());
        freezeRequest.setFreezeCause(agentFreezePort.getFreezeCause());
        freezeRequest.setReqReason(agentFreezePort.getUnfreezeCause());
        freezeRequest.setReviewsStat(AgStatus.Approving.status);
        freezeRequest.setStatus(Status.STATUS_1.status);
        freezeRequest.setVersion(BigDecimal.ONE);
        if (freezeRequestMapper.insert(freezeRequest)!=1){
            throw new MessageException("代理商冻结申请保存失败!");
        }
        String busId = "";
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
                if( agentFreezePort.getBusPlatform().get(0)!=null){
                    criteria.andBusIdEqualTo(agentFreezePort.getBusPlatform().get(0));
                }else {
                    criteria.andBusIdIsNull();
                }
            }
            if (agentFreezePort.getBusPlatform().get(0)!=null){
                agentFreezeExample.or()
                        .andFreezeTypeEqualTo(freeType)
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andAgentIdEqualTo(agentFreezePort.getAgentId())
                        .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                        .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString())
                        .andBusIdEqualTo(agentFreezePort.getBusPlatform().get(0));
            }else {
                agentFreezeExample.or()
                        .andFreezeTypeEqualTo(freeType)
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andAgentIdEqualTo(agentFreezePort.getAgentId())
                        .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                        .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString())
                        .andBusIdIsNull();
            }

            List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(agentFreezeExample);
            if(agentFreezes.size()==0){
                return AgentResult.fail("解冻信息不存在");
            }
            if(agentFreezes.size()!=1){
                return AgentResult.fail("解冻信息不唯一,请联系管理员");
            }
            //检查是否有在审批中的解冻申请
            FreezeRequestDetailExample freezeRequestDetailExample = new FreezeRequestDetailExample();
            if (agentFreezePort.getBusPlatform().get(0)!=null){
                freezeRequestDetailExample.or()
                        .andAgentIdEqualTo(agentFreezePort.getAgentId())
                        .andFreezeTypeEqualTo(freeType)
                        .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                        .andBusIdEqualTo(agentFreezePort.getBusPlatform().get(0))
                        .andStatusEqualTo(Status.STATUS_1.status);
            }else{
                freezeRequestDetailExample.or()
                        .andAgentIdEqualTo(agentFreezePort.getAgentId())
                        .andFreezeTypeEqualTo(freeType)
                        .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                        .andBusIdIsNull()
                        .andStatusEqualTo(Status.STATUS_1.status);
            }
            List<FreezeRequestDetail> freezeRequestDetails = freezeRequestDetailMapper.selectByExample(freezeRequestDetailExample);
            if (freezeRequestDetails!=null && freezeRequestDetails.size()>0){
                for (FreezeRequestDetail freezeRequestDetail : freezeRequestDetails) {
                    String freezeReqId = freezeRequestDetail.getFreezeReqId();
                    FreezeRequest freezeRequestApp = freezeRequestMapper.selectByPrimaryKey(freezeReqId);
                    if (freezeRequestApp !=null
                            && freezeRequestApp.getReviewsStat().compareTo(AgStatus.Approving.status)==0
                            && freezeRequestApp.getStatus().compareTo(Status.STATUS_1.status)==0
                            && freezeRequestApp.getReqType().compareTo(FreezeRequestType.UnFreeze.code)==0){
                        throw new MessageException("代理商此原因已申请解冻:"+FreeType.getmsg(freeType));
                    }
                }

            }
            AgentFreeze curAgentFreeze = agentFreezes.get(0);
            busId = curAgentFreeze.getBusId();
            FreezeRequestDetail  freezeRequestDetail  = new FreezeRequestDetail();
            freezeRequestDetail.setId(idService.genId(TabId.a_freeze_request_detail));
            freezeRequestDetail.setFreezeReqId(freezeRequest.getId());
            freezeRequestDetail.setAgentId(curAgentFreeze.getAgentId());
            freezeRequestDetail.setFreezeStatus(FreeStatus.JD.getValue().toString());
            freezeRequestDetail.setFreezeId(curAgentFreeze.getId());
            freezeRequestDetail.setFreezeCause(curAgentFreeze.getFreezeCause());
            freezeRequestDetail.setFreezeDate(curAgentFreeze.getFreezeDate());
            freezeRequestDetail.setFreezePerson(curAgentFreeze.getFreezePerson());
            freezeRequestDetail.setUnfreezePerson(agentFreezePort.getOperationPerson());
            freezeRequestDetail.setUnfreezeCause(agentFreezePort.getUnfreezeCause());
            freezeRequestDetail.setRemark(curAgentFreeze.getRemark());
            freezeRequestDetail.setFreezeNum(curAgentFreeze.getId());
            freezeRequestDetail.setStatus(Status.STATUS_1.status);
            freezeRequestDetail.setVersion(BigDecimal.ONE);
            freezeRequestDetail.setFreezeType(freeType);
            freezeRequestDetail.setNewBusFreeze(curAgentFreeze.getNewBusFreeze());
            freezeRequestDetail.setBusPlatform(curAgentFreeze.getBusPlatform());
            freezeRequestDetail.setBusId(curAgentFreeze.getBusId());
            freezeRequestDetail.setBusNum(curAgentFreeze.getBusNum());
            freezeRequestDetail.setBusFreeze(curAgentFreeze.getBusFreeze()==null?BigDecimal.ZERO:curAgentFreeze.getBusFreeze());
            freezeRequestDetail.setProfitFreeze(curAgentFreeze.getProfitFreeze()==null?BigDecimal.ZERO:curAgentFreeze.getProfitFreeze());
            freezeRequestDetail.setReflowFreeze(curAgentFreeze.getReflowFreeze()==null?BigDecimal.ZERO:curAgentFreeze.getReflowFreeze());
            freezeRequestDetail.setMonthlyFreeze(curAgentFreeze.getMonthlyFreeze()==null?BigDecimal.ZERO:curAgentFreeze.getMonthlyFreeze());
            freezeRequestDetail.setDailyFreeze(curAgentFreeze.getDailyFreeze()==null?BigDecimal.ZERO:curAgentFreeze.getDailyFreeze());
            freezeRequestDetail.setStopProfitFreeze(curAgentFreeze.getStopProfitFreeze()==null?BigDecimal.ZERO:curAgentFreeze.getStopProfitFreeze());
            freezeRequestDetail.setCashFreeze(curAgentFreeze.getCashFreeze()==null?BigDecimal.ZERO:curAgentFreeze.getCashFreeze());
            freezeRequestDetail.setStopCount(curAgentFreeze.getStopCount()==null?BigDecimal.ZERO:curAgentFreeze.getStopCount());
            if (freezeRequestDetailMapper.insert(freezeRequestDetail)!=1){
                throw new MessageException("代理商解冻冻结申请明细保存失败!");
            }
            AgentBusInfo agentBusInfo = agentBusinfoService.queryAgentBusInfoById(freezeRequestDetail.getBusId());
            if (agentBusInfo!=null){
                PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
                if (platForm.getPlatformType().equals("RJPOS") || platForm.getPlatformType().equals("RJQZ")) {
                    userMap.put("RJ",dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),FreeApprovalUser.RJ.key).getdItemvalue());
                }else {
                    userMap.put("NOT_RJ",dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),FreeApprovalUser.NOT_RJ.key).getdItemvalue());
                }
            }else {
                logger.info("冻结记录业务平台不存在{}",curAgentFreeze.getId());
                userMap.put(FreeApprovalUser.RJ.key,dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),FreeApprovalUser.RJ.key).getdItemvalue());
//                userMap.put("NOT_RJ",dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),"NOT_RJ").getdItemvalue());
            }
        }
        //流程中的部门参数
        Map startPar = agentEnterService.startPar(freezeRequest.getcUserId());
        if (null == startPar) {
            logger.info("========用户{}启动{}部门参数为空", freezeRequest.getcUserId(), freezeRequest.getId());
            throw new MessageException("启动部门参数为空！");
        }
        //Todo:增加判断是否为瑞+方法
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
        List<String> userList = new ArrayList<>();
        if (userMap.get("RJ")!=null){
            userList.add(String.valueOf(userMap.get("RJ")));
        }
        if(userMap.get("NOT_RJ")!=null){
            userList.add(String.valueOf(userMap.get("NOT_RJ")));
        }
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(agentFreezePort.getOperationPerson()));
        if(orgCodeRes==null && orgCodeRes.size()!=1){
            throw new MessageException("部门参数为空");
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        if(orgCode.equals("finance")){
            userList.clear();
            userList.add(dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),FreeApprovalUser.FINANCE.key).getdItemvalue());
        }
        startPar.put("userList",userList);
        //启动审批
        String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("agentFreeze"), null, null, startPar);
        if (proce == null) {
            logger.info("解冻申请提交审批，审批流启动失败{}:{}", freezeRequest.getcUserId(), freezeRequest.getId());
            throw new MessageException("审批流启动失败！");
        }

        //添加审批关系
        BusActRel record = new BusActRel();
        record.setBusId(freezeRequest.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(freezeRequest.getcUserId());
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.freeze.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agentFreezePort.getAgentId());
        Agent agent = agentMapper.selectByPrimaryKey(agentFreezePort.getAgentId());
        if(agent!=null) {
            record.setAgentName(agent.getAgName());
        }
        if (agentBusInfo != null){
            record.setNetInBusType("ACTIVITY_"+agentBusInfo.getBusPlatform());//数据权限
            record.setAgDocPro(agentBusInfo.getAgDocPro());
            record.setAgDocDistrict(agentBusInfo.getAgDocDistrict());
        }
        record.setDataShiro(BusActRelBusType.freeze.key);
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("解冻申请提交审批，启动审批异常，添加审批关系失败{}:{}", freezeRequest.getId(), proce);
            throw new MessageException("解冻申请审批流启动失败：添加审批关系失败！");
        }
        return AgentResult.ok("解冻申请成功");
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
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public AgentResult agentFreezeModify(AgentFreezePort agentFreezePort) throws MessageException {
        logger.info("代理商申请变更请求参数：{}", JsonUtil.objectToJson(agentFreezePort));
        AgentResult checkFreezeRule = checkFreezeRule(agentFreezePort);
        if (!checkFreezeRule.isOK()){
            return checkFreezeRule;
        }
        AgentResult verify = verify(agentFreezePort,FreeStatus.DJ.getValue(),BigDecimal.ONE);
        if(!verify.isOK()){
            return verify;
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
        Map userMap = new HashMap();
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
                if(agentFreezes.size()!=1 ){
                    throw new MessageException("代理商此原因冻结记录:"+FreeType.getmsg(freeType)+"不唯一");
                }

                //检查是否有在审批中的解冻申请
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
                        if (freezeRequestApp !=null
                                && freezeRequestApp.getReviewsStat().compareTo(AgStatus.Approving.status)==0
                                && freezeRequestApp.getStatus().compareTo(Status.STATUS_1.status)==0
                                && freezeRequestApp.getReqType().compareTo(FreezeRequestType.Modify.code)==0){
                            throw new MessageException("代理商此原因已申请变更:"+FreeType.getmsg(freeType));
                        }
                    }

                }

                FreezeRequestDetail agentFreeze = new FreezeRequestDetail();
                agentFreeze.setId(idService.genId(TabId.a_freeze_request_detail));
                agentFreeze.setFreezeReqId(freezeRequest.getId());
                agentFreeze.setAgentId(agentFreezePort.getAgentId());
                agentFreeze.setFreezeStatus(FreeStatus.DJ.getValue().toString());
                agentFreeze.setFreezeCause(agentFreezePort.getFreezeCause());
                agentFreeze.setFreezeDate(new Date());
                agentFreeze.setFreezePerson(agentFreezePort.getOperationPerson());
                agentFreeze.setFreezeNum(freezeRequest.getId());
                agentFreeze.setRemark(agentFreezePort.getRemark());
                agentFreeze.setStatus(Status.STATUS_1.status);
                agentFreeze.setVersion(BigDecimal.ONE);
                agentFreeze.setFreezeType(freeType);
                agentFreeze.setNewBusFreeze(new BigDecimal(agentFreezePort.getNewBusFreeze()));
                /** 保存新增字段 **/
                AgentBusInfo agentBusInfo = agentBusinfoService.queryAgentBusInfoById(busPlatform);
                agentFreeze.setBusPlatform(agentBusInfo.getBusPlatform());
                agentFreeze.setBusId(busPlatform);
                agentFreeze.setBusNum(agentBusInfo.getBusNum());
                agentFreeze.setBusFreeze(agentFreezePort.getCurLevel().getBusFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getBusFreeze());
                agentFreeze.setProfitFreeze(agentFreezePort.getCurLevel().getProfitFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getProfitFreeze());
                agentFreeze.setReflowFreeze(agentFreezePort.getCurLevel().getReflowFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getReflowFreeze());
                agentFreeze.setMonthlyFreeze(agentFreezePort.getCurLevel().getMonthlyFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getMonthlyFreeze());
                agentFreeze.setDailyFreeze(agentFreezePort.getCurLevel().getDailyFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getDailyFreeze());
                agentFreeze.setStopProfitFreeze(agentFreezePort.getCurLevel().getStopProfitFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getStopProfitFreeze());
                agentFreeze.setCashFreeze(agentFreezePort.getCurLevel().getCashFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getCashFreeze());
                agentFreeze.setStopCount(agentFreezePort.getCurLevel().getStopCount()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getStopCount());
                agentFreeze.setNewBusFreeze(agentFreezePort.getCurLevel().getNewBusFreeze()==null?BigDecimal.ZERO:agentFreezePort.getCurLevel().getNewBusFreeze());
                /**
                 * 保存原信息
                 */
                agentFreeze.setBusFreezeOrg(agentFreezes.get(0).getBusFreeze());
                agentFreeze.setProfitFreezeOrg(agentFreezes.get(0).getProfitFreeze());
                agentFreeze.setReflowFreezeOrg(agentFreezes.get(0).getReflowFreeze());
                agentFreeze.setMonthlyFreezeOrg(agentFreezes.get(0).getMonthlyFreeze());
                agentFreeze.setDailyFreezeOrg(agentFreezes.get(0).getDailyFreeze());
                agentFreeze.setStopProfitFreezeOrg(agentFreezes.get(0).getStopProfitFreeze());
                agentFreeze.setCashFreezeOrg(agentFreezes.get(0).getCashFreeze());
                agentFreeze.setStopCountOrg(agentFreezes.get(0).getStopCount());
                agentFreeze.setNewBusFreezeOrg(agentFreezes.get(0).getNewBusFreeze());
                agentFreeze.setFreezeId(agentFreezes.get(0).getId());

                if(StringUtils.isNotBlank(agentFreezePort.getRemark())){//备注
                    agentFreeze.setRemark(agentFreezePort.getRemark());
                }
                if (freezeRequestDetailMapper.insert(agentFreeze)!=1){
                    throw new MessageException("代理商冻结申请明细保存失败!");
                }
                if (agentBusInfo!=null){
                    PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
                    if (platForm.getPlatformType().equals("RJPOS") || platForm.getPlatformType().equals("RJQZ")) {
                        userMap.put(FreeApprovalUser.RJ.key,dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),FreeApprovalUser.RJ.key).getdItemvalue());
                    }else {
                        userMap.put(FreeApprovalUser.NOT_RJ.key,dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),FreeApprovalUser.NOT_RJ.key).getdItemvalue());
                    }
                }else {
                    throw new MessageException("业务平台不存在");
                }
            }
        }
        //流程中的部门参数
        Map startPar = agentEnterService.startPar(freezeRequest.getcUserId());
        if (null == startPar) {
            logger.info("========用户{}启动{}部门参数为空", freezeRequest.getcUserId(), freezeRequest.getId());
            throw new MessageException("启动部门参数为空！");
        }
        //Todo:增加判断是否为瑞+方法
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentFreezePort.getBusPlatform().get(0));
        List<String> userList = new ArrayList<>();
        if (userMap.get(FreeApprovalUser.RJ.key)!=null){
            userList.add(String.valueOf(userMap.get(FreeApprovalUser.RJ.key)));
        }
        if(userMap.get(FreeApprovalUser.NOT_RJ.key)!=null){
            userList.add(String.valueOf(userMap.get(FreeApprovalUser.NOT_RJ.key)));
        }
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(agentFreezePort.getOperationPerson()));
        if(orgCodeRes==null && orgCodeRes.size()!=1){
            throw new MessageException("部门参数为空");
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        if(orgCode.equals("finance")){
            userList.clear();
            userList.add(dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),FreeApprovalUser.FINANCE.key).getdItemvalue());
        }
        startPar.put("userList",userList);
        //启动审批
        String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("agentFreeze"), null, null, startPar);
        if (proce == null) {
            logger.info("解冻申请提交审批，审批流启动失败{}:{}", freezeRequest.getcUserId(), freezeRequest.getId());
            throw new MessageException("审批流启动失败！");
        }

        //添加审批关系
        BusActRel record = new BusActRel();
        record.setBusId(freezeRequest.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(freezeRequest.getcUserId());
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.freeze.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agentFreezePort.getAgentId());
        Agent agent = agentMapper.selectByPrimaryKey(agentFreezePort.getAgentId());
        if(agent!=null) {
            record.setAgentName(agent.getAgName());
        }
        if (agentBusInfo != null){
            record.setNetInBusType("ACTIVITY_"+agentBusInfo.getBusPlatform());//数据权限
            record.setAgDocPro(agentBusInfo.getAgDocPro());
            record.setAgDocDistrict(agentBusInfo.getAgDocDistrict());
        }
        record.setDataShiro(BusActRelBusType.freeze.key);
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("解冻申请提交审批，启动审批异常，添加审批关系失败{}:{}", freezeRequest.getId(), proce);
            throw new MessageException("解冻申请审批流启动失败：添加审批关系失败！");
        }
        return AgentResult.ok("冻结申请变更成功!");

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception {
        try {

            FreezeRequestDetailExample freezeRequestDetailExample = new FreezeRequestDetailExample();
            freezeRequestDetailExample.or()
                    .andFreezeReqIdEqualTo(agentVo.getAgentBusId())
                    .andStatusEqualTo(Status.STATUS_1.status);

            List<FreezeRequestDetail> freezeRequestDetails = freezeRequestDetailMapper.selectByExample(freezeRequestDetailExample);

            for (FreezeRequestDetail freezeRequestDetail : freezeRequestDetails) {
                freezeRequestDetail.setReviewsDate(new Date());
                freezeRequestDetail.setReviewsUser(userId);
                freezeRequestDetailMapper.updateByPrimaryKeySelective(freezeRequestDetail);
            }
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("rs", agentVo.getApprovalResult());
            reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
            reqMap.put("approvalPerson", userId);
            reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
            reqMap.put("taskId", agentVo.getTaskId());
            Map map = activityService.completeTask(agentVo.getTaskId(), reqMap);
            if (map == null) {
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
    public AgentResult agentFreezeFinish(String insid, String actname) throws Exception {
        try {
            logger.info("申请冻结审批完成:{},{}", insid, actname);
            //审批流关系
            BusActRel busActRel = busActRelService.findById(insid);
            if (actname.equals("finish_end")) { //审批完成
                logger.info("审批通过{}", busActRel.getBusId());
                busActRel.setActivStatus(AgStatus.Approved.name());
                if (1 != busActRelService.updateByPrimaryKey(busActRel)) {
                    throw new MessageException("请重新提交！");
                }
                FreezeRequest freezeRequest = freezeRequestMapper.selectByPrimaryKey(busActRel.getBusId());
                freezeRequest.setReviewsStat(AgStatus.Approved.status);
                freezeRequest.setReviewsDate(new Date());
                freezeRequestMapper.updateByPrimaryKeySelective(freezeRequest);
                AgentResult agentResult = new AgentResult();
                //保存冻结信息
                FreezeRequestDetailExample detailExample = new FreezeRequestDetailExample() ;
                detailExample.or().andFreezeReqIdEqualTo(freezeRequest.getId())
                        .andStatusEqualTo(Status.STATUS_1.status);
                List<FreezeRequestDetail> freezeRequestDetails = freezeRequestDetailMapper.selectByExample(detailExample);
                for (FreezeRequestDetail freezeRequestDetail : freezeRequestDetails) {
                    AgentFreezePort agentFreezePort = new AgentFreezePort();
                    agentFreezePort.setAgentId(freezeRequestDetail.getAgentId());
                    agentFreezePort.setFreeType(Arrays.asList(freezeRequestDetail.getFreezeType()));
                    agentFreezePort.setFreezeCause(freezeRequestDetail.getFreezeCause());
                    agentFreezePort.setBusPlatform(Arrays.asList(freezeRequestDetail.getBusId()));
                    agentFreezePort.setOperationPerson(freezeRequestDetail.getFreezePerson());
                    agentFreezePort.setRemark(freezeRequestDetail.getRemark());
                    agentFreezePort.setNewBusFreeze(String.valueOf(freezeRequestDetail.getNewBusFreeze()));
                    agentFreezePort.setUnfreezeCause(freezeRequestDetail.getUnfreezeCause());
                    FreezeDetail curDetail = new FreezeDetail();
                    curDetail.setBusFreeze(freezeRequestDetail.getBusFreeze());
                    curDetail.setProfitFreeze(freezeRequestDetail.getProfitFreeze());
                    curDetail.setReflowFreeze(freezeRequestDetail.getReflowFreeze());
                    curDetail.setMonthlyFreeze(freezeRequestDetail.getMonthlyFreeze());
                    curDetail.setDailyFreeze(freezeRequestDetail.getDailyFreeze());
                    curDetail.setStopProfitFreeze(freezeRequestDetail.getStopProfitFreeze());
                    curDetail.setStopCount(freezeRequestDetail.getStopCount());
                    curDetail.setNewBusFreeze(freezeRequestDetail.getNewBusFreeze());
                    curDetail.setCashFreeze(freezeRequestDetail.getCashFreeze());
                    agentFreezePort.setCurLevel(curDetail);
                    agentFreezePort.setFreezeNum(busActRel.getBusId());
                    try {
                        if (freezeRequest.getReqType().compareTo(FreezeRequestType.Freeze.code)== 0 ){
                            agentResult  = agentFreezeService.agentFreeze(agentFreezePort);
                        }else if (freezeRequest.getReqType().compareTo(FreezeRequestType.UnFreeze.code) == 0){
                            agentResult = agentFreezeService.agentUnFreeze(agentFreezePort);
                        }else if (freezeRequest.getReqType().compareTo(FreezeRequestType.Modify.code)== 0){
                            agentFreezePort.setUnfreezeCause("申请变更解冻");
                            agentResult = agentFreezeService.agentUnFreeze(agentFreezePort);
                            agentFreezePort.setUnfreezeCause(freezeRequestDetail.getUnfreezeCause());
                            agentResult  = agentFreezeService.agentFreeze(agentFreezePort);
                        }
                    }catch (MessageException m){
                        if (!m.getCode().equals("2000")){
                            throw m;
                        }
                    }catch (Exception m){
                       throw m;
                    }


                }

            } else if(actname.equals("reject_end")) { //审批拒绝
                logger.info("审批完审批拒绝{}", busActRel.getBusId());
                busActRel.setActivStatus(AgStatus.Refuse.name());
                if (1 != busActRelService.updateByPrimaryKey(busActRel)) {
                    throw new MessageException("请重新提交！");
                }
                FreezeRequest freezeRequest = freezeRequestMapper.selectByPrimaryKey(busActRel.getBusId());
                freezeRequest.setReviewsStat(AgStatus.Refuse.status);
                freezeRequest.setReviewsDate(new Date());
                if (1!=freezeRequestMapper.updateByPrimaryKeySelective(freezeRequest)){
                    throw new MessageException("请重新提交！");
                };
            }
            logger.info("申请冻结审批结束", busActRel.getBusId());
            return AgentResult.ok();
        }catch (MessageException messageexception){
            logger.error(messageexception.toString());
            throw messageexception;
        }catch (Exception e){
            logger.error(e.toString());
            throw new MessageException("请重新提交！");
        }

    }

    @Override
    public FreezeRequest queryFreezeRequestById(String id)  {
        FreezeRequest freezeRequest = freezeRequestMapper.selectByPrimaryKey(id);
        return freezeRequest;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult addList(List<List<Object>> list, String userid) throws Exception {
        try {
            AgentResult agentResult = AgentResult.ok();
        if (null == list || list.size() == 0) {
            logger.info("导入的数据为空");
            throw new MessageException("导入的数据为空");
        }
        int num = 0;
        String freeCause = null;
        AgentFreezePort agentFreezePort = new AgentFreezePort();
        agentFreezePort.setOperationPerson(userid);
        agentFreezePort.setRemark("批量冻结");
        List<FreezeRequestDetail> freezeRequestDetaillist = new ArrayList<FreezeRequestDetail>();
        for (List<Object> objectList : list) {
            num = num+1;
            if (StringUtils.isBlank(String.valueOf(objectList.get(0)))) {
                logger.info("请填写代理商唯一码:{}", String.valueOf(objectList.get(0)));
                throw new MessageException("第[" + num + "]行,请填写代理商唯一码");
            }
            Agent agentById = agentService.getAgentById(String.valueOf(objectList.get(0)));
            if (null == agentById) {
                logger.info("代理商编号不存在:{}", String.valueOf(objectList.get(0)));
                throw new MessageException("第[" + num + "]行,找不到的代理商" + objectList.get(0));
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(1)))) {
                logger.info("代理商名称为空:{}", String.valueOf(objectList.get(1)));
                throw new MessageException("第[" + num + "]行,请填写代理商名称");
            } else if (!agentById.getAgName().equals(String.valueOf(objectList.get(1)).replaceAll("\r|\n", ""))) {
                logger.info("代理商名称不匹配:{}", String.valueOf(objectList.get(1)).replaceAll("\r|\n", ""));
                throw new MessageException("第[" + num + "]行,代理商名称不匹配:"+String.valueOf(objectList.get(1)).replaceAll("\r|\n", ""));
            }
            if (StringUtils.isBlank(String.valueOf(objectList.get(2)))) {
                logger.info("冻结类型为空:{}", String.valueOf(objectList.get(2)));
                throw new MessageException("第[" + num + "]行,请选择正确的冻结类型");
            } else {
                if (!(String.valueOf(objectList.get(2)).equals(FreeCause.FRFX.msg) || String.valueOf(objectList.get(2)).equals(FreeCause.QTDJ.msg))) {
                    logger.info("请选择正确的冻结类型:{}", String.valueOf(objectList.get(2)));
                    throw new MessageException("第[" + num + "]行,请选择正确的冻结类型");
                } else if ((String.valueOf(objectList.get(2)).equals(FreeCause.FRFX.msg))) {
                    freeCause = FreeCause.FRFX.code;
                } else if ((String.valueOf(objectList.get(2)).equals(FreeCause.QTDJ.msg))) {
                    freeCause = FreeCause.QTDJ.code;
                }
            }

            BigDecimal freeType = BigDecimal.ZERO;
            if (StringUtils.isBlank(String.valueOf(objectList.get(5)))) {
                logger.info("冻结层级为空:{}", String.valueOf(objectList.get(5)));
                throw new MessageException("第[" + num + "]行,请选择正确的层级");
            } else {
                if (!(String.valueOf(objectList.get(5)).equals(FreeType.AGNET.msg) || String.valueOf(objectList.get(5)).equals(FreeType.SUB_AGENT.msg))) {
                    logger.info("请选择正确的冻结类型:{}", String.valueOf(objectList.get(2)));
                    throw new MessageException("第[" + num + "]行,请选择正确的冻结类型");
                } else if ((String.valueOf(objectList.get(5)).equals(FreeType.AGNET.msg))) {
                    freeType = FreeType.AGNET.code;
                } else if ((String.valueOf(objectList.get(5)).equals(FreeType.SUB_AGENT.msg))) {
                    freeType = FreeType.SUB_AGENT.code;
                }
            }


            if (StringUtils.isBlank(String.valueOf(objectList.get(3)))) {
                logger.info("业务平台编号为空:{}", String.valueOf(objectList.get(3)));
                throw new MessageException("第[" + num + "]行,请填写业务平台编号");
            }

            if (StringUtils.isBlank(String.valueOf(objectList.get(4)))) {
                logger.info("业务平台为空:{}", String.valueOf(objectList.get(4)));
                throw new MessageException("第[" + num + "]行,请填写业务平台");
            }
            PlatForm platForm = platFormService.selectByPlatformName(String.valueOf(objectList.get(4)).replaceAll("\r|\n", ""));
            if (platForm == null) {
                logger.info("业务平台不存在:{}", String.valueOf(objectList.get(4)).replaceAll("\r|\n", ""));
                throw new MessageException("第[" + num + "]行,业务平台不存在:"+String.valueOf(objectList.get(4)).replaceAll("\r|\n", ""));
            }
            AgentBusInfo agentBusInfo = new AgentBusInfo();
            agentBusInfo.setBusNum(String.valueOf(objectList.get(3)).replaceAll("\r|\n", ""));
            agentBusInfo.setBusPlatform(platForm.getPlatformNum());
            agentBusInfo.setAgentId(String.valueOf(objectList.get(0)));
            List<AgentBusInfo> agentBusInfos = agentBusinfoService.selectByAgentBusInfo(agentBusInfo);
            if (agentBusInfos == null || agentBusInfos.size() == 0) {
                logger.info("找不到的业务平台码:{}", String.valueOf(objectList.get(3)).replaceAll("\r|\n", ""));
                throw new MessageException("第[" + num + "]行,找不到的业务平台信息:业务平台编号[" + String.valueOf(objectList.get(3)).replaceAll("\r|\n", "")+"],业务平台["+String.valueOf(objectList.get(4)).replaceAll("\r|\n", "")+"]");
            }

            if (StringUtils.isBlank(String.valueOf(objectList.get(15)))) {
                logger.info("冻结原因为空:{}", String.valueOf(objectList.get(15)));
                throw new MessageException("第[" + num + "]行,请填写冻结原因");
            }
            AgentFreezeExample agentFreezeExample = new AgentFreezeExample();
            agentFreezeExample.or().andStatusEqualTo(Status.STATUS_1.status)
                    .andFreezeTypeEqualTo(freeType)
                    .andBusNumEqualTo(String.valueOf(objectList.get(4)).replaceAll("\r|\n", ""))
                    .andFreezeCauseEqualTo(FreeCause.getcodeBymsg(String.valueOf(objectList.get(2))))
                    .andAgentIdEqualTo(String.valueOf(objectList.get(0)));
            List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(agentFreezeExample);
            if (agentFreezes != null && agentFreezes.size() != 0) {
                logger.info("代理商已有相同冻结记录:{}", String.valueOf(objectList.get(2)));
                throw new MessageException("第[" + num + "]行," + String.valueOf(objectList.get(0)) + "代理商已有相同冻结记录");
            }

            //检查是否有在审批中的冻结申请
            FreezeRequestDetailExample freezeRequestDetailExample = new FreezeRequestDetailExample();
            freezeRequestDetailExample.or()
                    .andAgentIdEqualTo(String.valueOf(objectList.get(0)))
                    .andFreezeTypeEqualTo(freeType)
                    .andFreezeCauseEqualTo(freeCause)
                    .andBusIdEqualTo(agentBusInfos.get(0).getId())
                    .andStatusEqualTo(Status.STATUS_1.status);
            List<FreezeRequestDetail> freezeRequestDetails = freezeRequestDetailMapper.selectByExample(freezeRequestDetailExample);
            if (freezeRequestDetails!=null && freezeRequestDetails.size()>0){
                for (FreezeRequestDetail freezeRequestDetail : freezeRequestDetails) {
                    String freezeReqId = freezeRequestDetail.getFreezeReqId();
                    FreezeRequest freezeRequestApp = freezeRequestMapper.selectByPrimaryKey(freezeReqId);
                    if (freezeRequestApp !=null && freezeRequestApp.getReviewsStat().compareTo(AgStatus.Approving.status)==0 && freezeRequestApp.getStatus().compareTo(Status.STATUS_1.status)==0){
                        throw new MessageException("代理商此原因已申请冻结:"+String.valueOf(objectList.get(2)));
                    }
                }

            }


            FreezeRequestDetail freezeRequestDetail = new FreezeRequestDetail();
            freezeRequestDetail.setAgentId(String.valueOf(objectList.get(0)));
            freezeRequestDetail.setFreezeCause(freeCause);
            freezeRequestDetail.setFreezeType(freeType);

            freezeRequestDetail.setBusPlatform(platForm.getPlatformNum());//
            freezeRequestDetail.setBusId(agentBusInfos.get(0).getId());//AB码
            freezeRequestDetail.setBusNum(String.valueOf(objectList.get(3)).replaceAll("\r|\n", ""));//业务平台码,O码

            freezeRequestDetail.setBusFreeze(String.valueOf(objectList.get(6)).equals("是") ? BigDecimal.ONE : BigDecimal.ZERO);
            freezeRequestDetail.setProfitFreeze(String.valueOf(objectList.get(7)).equals("是") ? BigDecimal.ONE : BigDecimal.ZERO);
            freezeRequestDetail.setReflowFreeze(String.valueOf(objectList.get(8)).equals("是") ? BigDecimal.ONE : BigDecimal.ZERO);
            freezeRequestDetail.setMonthlyFreeze(String.valueOf(objectList.get(9)).equals("是") ? BigDecimal.ONE : BigDecimal.ZERO);
            freezeRequestDetail.setDailyFreeze(String.valueOf(objectList.get(10)).equals("是") ? BigDecimal.ONE : BigDecimal.ZERO);
            freezeRequestDetail.setStopProfitFreeze(String.valueOf(objectList.get(11)).equals("是") ? BigDecimal.ONE : BigDecimal.ZERO);
            freezeRequestDetail.setCashFreeze(String.valueOf(objectList.get(12)).equals("是") ? BigDecimal.ONE : BigDecimal.ZERO);
            freezeRequestDetail.setStopCount(String.valueOf(objectList.get(13)).equals("是") ? BigDecimal.ONE : BigDecimal.ZERO);
            freezeRequestDetail.setNewBusFreeze(String.valueOf(objectList.get(14)).equals("是") ? BigDecimal.ONE : BigDecimal.ZERO);
            freezeRequestDetail.setRemark(String.valueOf(objectList.get(15)));

            freezeRequestDetail.setFreezeDate(new Date());
            freezeRequestDetail.setStatus(Status.STATUS_1.status);
            freezeRequestDetail.setVersion(Status.STATUS_1.status);

            freezeRequestDetaillist.add(freezeRequestDetail);
        }

            FreezeRequest freezeRequest = new FreezeRequest();
            freezeRequest.setId(idService.genId(TabId.a_freeze_request));
            freezeRequest.setReqType(FreezeRequestType.Freeze.code);
            freezeRequest.setcTm(new Date());
            freezeRequest.setcUserId(agentFreezePort.getOperationPerson());
            freezeRequest.setFreezeCause(agentFreezePort.getFreezeCause());
            freezeRequest.setReqReason(agentFreezePort.getRemark());
            freezeRequest.setReviewsStat(AgStatus.Approving.status);
            freezeRequest.setStatus(Status.STATUS_1.status);
            freezeRequest.setVersion(BigDecimal.ONE);
            if (freezeRequestMapper.insert(freezeRequest)!=1){
                throw new MessageException("代理商冻结申请保存失败!");
            }
            Map userMap = new HashMap();
            int numFreeze = 0;
            for (FreezeRequestDetail freezeRequestDetail : freezeRequestDetaillist) {
                numFreeze = numFreeze+1;
                agentFreezePort.setFreezeCause(freezeRequestDetail.getFreezeCause());
                agentFreezePort.setFreezeNum(freezeRequest.getId());
                agentFreezePort.setAgentId(freezeRequestDetail.getAgentId());
                if (freezeRequestDetail.getFreezeType().compareTo(FreeType.AGNET.code)==0){
                    FreezeDetail freezeDetail = new FreezeDetail();
                    freezeDetail.setCashFreeze(freezeRequestDetail.getCashFreeze());
                    freezeDetail.setNewBusFreeze(freezeRequestDetail.getNewBusFreeze());
                    freezeDetail.setStopCount(freezeRequestDetail.getStopCount());
                    freezeDetail.setDailyFreeze(freezeRequestDetail.getDailyFreeze());
                    freezeDetail.setMonthlyFreeze(freezeRequestDetail.getMonthlyFreeze());
                    freezeDetail.setReflowFreeze(freezeRequestDetail.getReflowFreeze());
                    freezeDetail.setProfitFreeze(freezeRequestDetail.getProfitFreeze());
                    freezeDetail.setBusFreeze(freezeRequestDetail.getBusFreeze());
                    freezeDetail.setStopProfitFreeze(freezeRequestDetail.getStopProfitFreeze());
                    agentFreezePort.setCurLevel(freezeDetail);
                }else if (freezeRequestDetail.getFreezeType().compareTo(FreeType.SUB_AGENT.code)==0){
                    FreezeDetail freezeDetail = new FreezeDetail();
                    freezeDetail.setCashFreeze(freezeRequestDetail.getCashFreeze());
                    freezeDetail.setNewBusFreeze(freezeRequestDetail.getNewBusFreeze());
                    freezeDetail.setStopCount(freezeRequestDetail.getStopCount());
                    freezeDetail.setDailyFreeze(freezeRequestDetail.getDailyFreeze());
                    freezeDetail.setMonthlyFreeze(freezeRequestDetail.getMonthlyFreeze());
                    freezeDetail.setReflowFreeze(freezeRequestDetail.getReflowFreeze());
                    freezeDetail.setProfitFreeze(freezeRequestDetail.getProfitFreeze());
                    freezeDetail.setBusFreeze(freezeRequestDetail.getBusFreeze());
                    freezeDetail.setStopProfitFreeze(freezeRequestDetail.getStopProfitFreeze());
                    agentFreezePort.setSubLevel(freezeDetail);
                }else {
                    throw new MessageException("第[" + numFreeze + "]行,冻结层级错误");
                }
                AgentResult checkRuleRest =  checkFreezeRule(agentFreezePort);
                if (!checkRuleRest.isOK()){
                    throw new MessageException("第[" + numFreeze + "]行,"+checkRuleRest.getMsg());
                }
                AgentResult verify = verify(agentFreezePort, FreeStatus.DJ.getValue(),BigDecimal.ZERO);
                if(!verify.isOK()){
                    throw new MessageException("第[" + numFreeze + "]行,"+verify.getMsg());
                }
                BigDecimal freeType = freezeRequestDetail.getFreezeType();
                logger.info("冻结类型为[{}]",FreeType.getmsg(freeType));
                AgentFreezeExample agentFreezeExample = new AgentFreezeExample();
                if (freeType.compareTo(FreeType.AGNET.code) == 0){
                    AgentFreezeExample.Criteria criteria = agentFreezeExample.createCriteria();
                    criteria.andFreezeTypeIsNull();
                    criteria.andStatusEqualTo(Status.STATUS_1.status);
                    criteria.andAgentIdEqualTo(agentFreezePort.getAgentId());
                    criteria.andFreezeCauseEqualTo(agentFreezePort.getFreezeCause());
                    criteria.andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString());
                    criteria.andBusIdEqualTo(freezeRequestDetail.getBusId());
                }
                agentFreezeExample.or()
                        .andFreezeTypeEqualTo(freeType)
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andAgentIdEqualTo(agentFreezePort.getAgentId())
                        .andFreezeCauseEqualTo(agentFreezePort.getFreezeCause())
                        .andFreezeStatusEqualTo(FreeStatus.DJ.getValue().toString())
                        .andBusIdEqualTo(freezeRequestDetail.getBusId());
                List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(agentFreezeExample);
                if(agentFreezes.size()!=0){
                    throw new MessageException("第[" + numFreeze + "]行,代理商此原因已被冻结:"+FreeType.getmsg(freeType));
                }
                freezeRequestDetail.setId(idService.genId(TabId.a_freeze_request_detail));
                freezeRequestDetail.setFreezeReqId(freezeRequest.getId());
                freezeRequestDetail.setFreezePerson(agentFreezePort.getOperationPerson());
                if (freezeRequestDetailMapper.insert(freezeRequestDetail)!=1){
                    throw new MessageException("第[" + numFreeze + "]行,代理商冻结申请明细保存失败!");
                }
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(freezeRequestDetail.getBusId());
                if (agentBusInfo!=null){
                    PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
                    if (platForm.getPlatformType().equals("RJPOS") || platForm.getPlatformType().equals("RJQZ")) {
                        userMap.put("RJ",dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),"RJ").getdItemvalue());
                    }else {
                        userMap.put("NOT_RJ",dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),"NOT_RJ").getdItemvalue());
                    }
                }else {
                    throw new MessageException("第[" + num + "]行,业务平台不存在");
                }

            }
            //流程中的部门参数
            Map startPar = agentEnterService.startPar(agentFreezePort.getOperationPerson());
            if (null == startPar) {
                logger.info("========用户{}启动{}部门参数为空", agentFreezePort.getOperationPerson(), freezeRequest.getId());
                throw new MessageException("启动部门参数为空！");
            }
            List<String> userList = new ArrayList<>();
            if (userMap.get("RJ")!=null){
                userList.add(String.valueOf(userMap.get("RJ")));
            }
            if(userMap.get("NOT_RJ")!=null){
                userList.add(String.valueOf(userMap.get("NOT_RJ")));
            }
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(agentFreezePort.getOperationPerson()));
            if(orgCodeRes==null && orgCodeRes.size()!=1){
                throw new MessageException("部门参数为空");
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String orgCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
            if(orgCode.equals("finance")){
                userList.clear();
                userList.add(dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),FreeApprovalUser.FINANCE.key).getdItemvalue());
            }
            //Todo:增加判断是否为瑞+方法
            startPar.put("userList",userList);
            //启动审批
            String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("agentFreeze"), null, null, startPar);
            if (proce == null) {
                logger.info("批量冻结申请提交审批，审批流启动失败{}:{}", agentFreezePort.getOperationPerson(), freezeRequest.getId());
                throw new MessageException("审批流启动失败！");
            }

            //添加审批关系
            BusActRel record = new BusActRel();
            record.setBusId(freezeRequest.getId());
            record.setActivId(proce);
            record.setcTime(Calendar.getInstance().getTime());
            record.setcUser(freezeRequest.getcUserId());
            record.setStatus(Status.STATUS_1.status);
            record.setBusType(BusActRelBusType.freeze.name());
            record.setActivStatus(AgStatus.Approving.name());
            record.setAgentId(agentFreezePort.getAgentId());
            Agent agent = agentMapper.selectByPrimaryKey(agentFreezePort.getAgentId());
            if(agent!=null) {
                record.setAgentName(agent.getAgName());
            }
//        record.setNetInBusType("ACTIVITY_"+agentBusInfo.getBusPlatform());//数据权限
            record.setDataShiro(BusActRelBusType.freeze.key);
//        record.setAgDocPro(agentBusInfo.getAgDocPro());
//        record.setAgDocDistrict(agentBusInfo.getAgDocDistrict());
            if (1 != busActRelMapper.insertSelective(record)) {
                logger.info("批量冻结申请提交审批，启动审批异常，添加审批关系失败{}:{}", freezeRequest.getId(), proce);
                throw new MessageException("批量申请冻结审批流启动失败：添加审批关系失败！");
            }
            return AgentResult.ok("批量冻结申请成功");
        }catch (MessageException messageexception){
        logger.error(messageexception.toString());
        throw messageexception;
        }catch (Exception e){
            logger.error(e.toString());
            throw new MessageException("请重新提交！");
        }
    }

    private AgentResult checkFreezeRule(AgentFreezePort agentFreezePort){
        List<Dict> dicts = dictOptionsService.dictList(DictGroup.FREEZE_RULE.name(), agentFreezePort.getFreezeCause());
        Map<String,Object> ruleMap = new HashMap<String, Object>();
        for (Dict dict:dicts){
            ruleMap.put(dict.getdItemvalue(),dict.getdItemname()+"-"+dict.getdItemnremark());
        }
        FreezeDetail cur = agentFreezePort.getCurLevel();
        FreezeDetail sub = agentFreezePort.getSubLevel();
        if(cur==null && sub ==null){
            return AgentResult.fail("至少选择一个冻结层级");
        }
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


        switch (agentFreezePort.getFreezeCause()){
            case "FRFX": //分润返现
                if (cur!= null){
                    if (cur.getProfitFreeze() !=null && cur.getReflowFreeze() != null){
                        if ((cur.getProfitFreeze().compareTo(BigDecimal.ZERO)==0 &&  cur.getReflowFreeze().compareTo(BigDecimal.ZERO) ==0 )){
                            return AgentResult.fail("分润与返现最少二选一");
                        }
                    }
                    if (cur.getDailyFreeze()!=null && cur.getMonthlyFreeze()!=null){
                        if (cur.getDailyFreeze().compareTo(BigDecimal.ZERO) == 0 && cur.getMonthlyFreeze().compareTo(BigDecimal.ZERO) == 0){
                            return AgentResult.fail("日结与月结最少二选一");
                        }
                    }
                    if (cur.getStopProfitFreeze()!=null && cur.getStopCount()!=null){
                        if (cur.getStopProfitFreeze().compareTo(BigDecimal.ONE) == 0 && cur.getStopCount().compareTo(BigDecimal.ONE) == 0){
                            return AgentResult.fail("停发与停算不可同时选择");
                        }
                    }
                }
                if (sub!= null){
                    if (sub.getReflowFreeze()!=null && sub.getProfitFreeze()!=null){
                        if ( (sub.getProfitFreeze().compareTo(BigDecimal.ZERO)==0 &&  sub.getReflowFreeze().compareTo(BigDecimal.ZERO) ==0 )){
                            return AgentResult.fail("分润与返现最少二选一");
                        }
                    }
                    if (sub.getDailyFreeze()!=null && sub.getMonthlyFreeze()!= null){
                        if (sub.getDailyFreeze().compareTo(BigDecimal.ZERO) == 0 && sub.getMonthlyFreeze().compareTo(BigDecimal.ZERO) == 0){
                            return AgentResult.fail("日结与月结最少二选一");
                        }
                    }

                    if (sub.getStopProfitFreeze()!=null && sub.getStopCount()!=null){
                        if (sub.getStopProfitFreeze().compareTo(BigDecimal.ONE) == 0 && sub.getStopCount().compareTo(BigDecimal.ONE) == 0){
                            return AgentResult.fail("停发与停算不可同时选择");
                        }
                    }
                }
                break;
        }

        return AgentResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult batchUnFreeze(AgentUnFreezeBatcthVo agentUnFreezeBatcthVo, String userid) throws Exception {
        try {
            if (null == agentUnFreezeBatcthVo.getFreezeList() && agentUnFreezeBatcthVo.getFreezeList().size() == 0) {
                logger.info("批量解冻数据为空");
                throw new MessageException("批量解冻数据为空");
            }
            FreezeRequest freezeRequest = new FreezeRequest();
            freezeRequest.setId(idService.genId(TabId.a_freeze_request));
            freezeRequest.setReqType(FreezeRequestType.UnFreeze.code);
            freezeRequest.setcTm(new Date());
            freezeRequest.setcUserId(userid);
            freezeRequest.setFreezeCause("");
            freezeRequest.setReqReason(agentUnFreezeBatcthVo.getFreezeCause());
            freezeRequest.setReviewsStat(AgStatus.Approving.status);
            freezeRequest.setStatus(Status.STATUS_1.status);
            freezeRequest.setVersion(BigDecimal.ONE);
            if (freezeRequestMapper.insert(freezeRequest)!=1){
                throw new MessageException("代理商冻结申请保存失败!");
            }
            int num = 0;
            AgentFreezePort agentFreezePort = new AgentFreezePort();
            agentFreezePort.setOperationPerson(userid);
            agentFreezePort.setRemark("批量解冻");
            Map userMap = new HashMap();
            for (String agentFreezeId : agentUnFreezeBatcthVo.getFreezeList()) {
                num = +1;
                AgentFreezeExample agentFreezeExample = new AgentFreezeExample();
                agentFreezeExample.or().andStatusEqualTo(Status.STATUS_1.status)
                        .andIdEqualTo(agentFreezeId)
                        .andFreezeStatusEqualTo(String.valueOf(FreeStatus.DJ.code));
                List<AgentFreeze> agentFreezes = agentFreezeMapper.selectByExample(agentFreezeExample);


                if (agentFreezes == null || agentFreezes.size() == 0) {
                    logger.info("代理商冻结记录不存在:{}", agentFreezeId);
                    throw new MessageException("第[" + num + "]行," + agentFreezeId + "代理商冻结记录不存在");
                }

                //检查是否有在审批中的解冻申请
                FreezeRequestDetailExample freezeRequestDetailExample = new FreezeRequestDetailExample();
                if (agentFreezes.get(0).getBusId() == null){
                    freezeRequestDetailExample.or()
                            .andAgentIdEqualTo(agentFreezes.get(0).getAgentId())
                            .andFreezeTypeEqualTo(agentFreezes.get(0).getFreezeType())
                            .andFreezeCauseEqualTo(agentFreezes.get(0).getFreezeCause())
                            .andStatusEqualTo(Status.STATUS_1.status);
                }else {
                    freezeRequestDetailExample.or()
                            .andAgentIdEqualTo(agentFreezes.get(0).getAgentId())
                            .andFreezeTypeEqualTo(agentFreezes.get(0).getFreezeType())
                            .andFreezeCauseEqualTo(agentFreezes.get(0).getFreezeCause())
                            .andBusIdEqualTo(agentFreezes.get(0).getBusId())
                            .andStatusEqualTo(Status.STATUS_1.status);
                }
                List<FreezeRequestDetail> freezeRequestDetails = freezeRequestDetailMapper.selectByExample(freezeRequestDetailExample);
                if (freezeRequestDetails!=null && freezeRequestDetails.size()>0){
                    for (FreezeRequestDetail freezeRequestDetailTmp : freezeRequestDetails) {
                        String freezeReqId = freezeRequestDetailTmp.getFreezeReqId();
                        FreezeRequest freezeRequestApp = freezeRequestMapper.selectByPrimaryKey(freezeReqId);
                        if (freezeRequestApp !=null
                                && freezeRequestApp.getReviewsStat().compareTo(AgStatus.Approving.status)==0
                                && freezeRequestApp.getStatus().compareTo(Status.STATUS_1.status)==0
                                && freezeRequestApp.getReqType().compareTo(FreezeRequestType.UnFreeze.code)==0){
                            throw new MessageException("代理商此原因已申请解冻:"+freezeRequestDetailTmp.getAgentId());
                        }
                    }

                }

                FreezeRequestDetail freezeRequestDetail = new FreezeRequestDetail();
                freezeRequestDetail.setId(idService.genId(TabId.a_freeze_request_detail));
                freezeRequestDetail.setAgentId(agentFreezes.get(0).getAgentId());
                freezeRequestDetail.setFreezeId(agentFreezeId);
                freezeRequestDetail.setFreezeStatus(agentFreezes.get(0).getFreezeStatus());
                freezeRequestDetail.setFreezePerson(agentFreezes.get(0).getFreezePerson());
                freezeRequestDetail.setUnfreezePerson(userid);
                freezeRequestDetail.setFreezeCause(agentFreezes.get(0).getFreezeCause());
                freezeRequestDetail.setUnfreezeCause(agentUnFreezeBatcthVo.getUnfreezeCause());
                freezeRequestDetail.setFreezeNum(freezeRequest.getId());
                freezeRequestDetail.setFreezeType(agentFreezes.get(0).getFreezeType());
                freezeRequestDetail.setFreezeReqId(freezeRequest.getId());
                freezeRequestDetail.setBusPlatform(agentFreezes.get(0).getBusPlatform());//
                freezeRequestDetail.setBusId(agentFreezes.get(0).getBusId());//AB码
                freezeRequestDetail.setBusNum(agentFreezes.get(0).getBusNum());//业务平台码,O码

                freezeRequestDetail.setBusFreeze(agentFreezes.get(0).getBusFreeze());
                freezeRequestDetail.setProfitFreeze(agentFreezes.get(0).getProfitFreeze());
                freezeRequestDetail.setReflowFreeze(agentFreezes.get(0).getReflowFreeze());
                freezeRequestDetail.setMonthlyFreeze(agentFreezes.get(0).getMonthlyFreeze());
                freezeRequestDetail.setDailyFreeze(agentFreezes.get(0).getDailyFreeze());
                freezeRequestDetail.setStopProfitFreeze(agentFreezes.get(0).getStopProfitFreeze());
                freezeRequestDetail.setCashFreeze(agentFreezes.get(0).getCashFreeze());
                freezeRequestDetail.setStopCount(agentFreezes.get(0).getStopCount());
                freezeRequestDetail.setNewBusFreeze(agentFreezes.get(0).getNewBusFreeze());
                freezeRequestDetail.setRemark(agentFreezes.get(0).getRemark());

                freezeRequestDetail.setFreezeDate(agentFreezes.get(0).getFreezeDate());
                freezeRequestDetail.setStatus(Status.STATUS_1.status);
                freezeRequestDetail.setVersion(Status.STATUS_1.status);

                if (freezeRequestDetailMapper.insert(freezeRequestDetail)!=1){
                    throw new MessageException("代理商[" + freezeRequestDetail.getAgentId() + "],代理商批量解冻申请明细保存失败!");
                }
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(freezeRequestDetail.getBusId());
                if (agentBusInfo!=null){
                    PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
                    if (platForm.getPlatformType().equals("RJPOS") || platForm.getPlatformType().equals("RJQZ")) {
                        userMap.put("RJ",dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),"RJ").getdItemvalue());
                    }else {
                        userMap.put("NOT_RJ",dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),"NOT_RJ").getdItemvalue());
                    }
                }else {
                    throw new MessageException("代理商[" + freezeRequestDetail.getAgentId() + "],业务平台不存在");
                }
            }
            //流程中的部门参数
            Map startPar = agentEnterService.startPar(agentFreezePort.getOperationPerson());
            if (null == startPar) {
                logger.info("========用户{}启动{}部门参数为空", agentFreezePort.getOperationPerson(), freezeRequest.getId());
                throw new MessageException("启动部门参数为空！");
            }
            List<String> userList = new ArrayList<>();
            if (userMap.get("RJ")!=null){
                userList.add(String.valueOf(userMap.get("RJ")));
            }
            if(userMap.get("NOT_RJ")!=null){
                userList.add(String.valueOf(userMap.get("NOT_RJ")));
            }
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(agentFreezePort.getOperationPerson()));
            if(orgCodeRes==null && orgCodeRes.size()!=1){
                throw new MessageException("部门参数为空");
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String orgCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
            if(orgCode.equals("finance")){
                userList.clear();
                userList.add(dictOptionsService.findDictByName(DictGroup.AGENT.name(), DictGroup.FREE_APPROVAL_USER.name(),FreeApprovalUser.FINANCE.key).getdItemvalue());
            }
            //Todo:增加判断是否为瑞+方法
            startPar.put("userList",userList);
            //启动审批
            String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("agentFreeze"), null, null, startPar);
            if (proce == null) {
                logger.info("批量解冻申请提交审批，审批流启动失败{}:{}", agentFreezePort.getOperationPerson(), freezeRequest.getId());
                throw new MessageException("审批流启动失败！");
            }

            //添加审批关系
            BusActRel record = new BusActRel();
            record.setBusId(freezeRequest.getId());
            record.setActivId(proce);
            record.setcTime(Calendar.getInstance().getTime());
            record.setcUser(freezeRequest.getcUserId());
            record.setStatus(Status.STATUS_1.status);
            record.setBusType(BusActRelBusType.freeze.name());
            record.setActivStatus(AgStatus.Approving.name());
            record.setAgentId(agentFreezePort.getAgentId());
            Agent agent = agentMapper.selectByPrimaryKey(agentFreezePort.getAgentId());
            if(agent!=null) {
                record.setAgentName(agent.getAgName());
            }
//        record.setNetInBusType("ACTIVITY_"+agentBusInfo.getBusPlatform());//数据权限
            record.setDataShiro(BusActRelBusType.freeze.key);
//        record.setAgDocPro(agentBusInfo.getAgDocPro());
//        record.setAgDocDistrict(agentBusInfo.getAgDocDistrict());
            if (1 != busActRelMapper.insertSelective(record)) {
                logger.info("批量解冻申请提交审批，启动审批异常，添加审批关系失败{}:{}", freezeRequest.getId(), proce);
                throw new MessageException("批量申请解冻审批流启动失败：添加审批关系失败！");
            }
            return AgentResult.ok("批量解冻申请成功");
        }catch (MessageException e){
            throw new MessageException(e.getMessage());
        }
    }

}
