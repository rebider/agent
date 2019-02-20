package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;
import com.ryx.credit.pojo.admin.vo.AgentColinfoVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.dict.DictOptionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by cx on 2018/6/6.
 */
@Service("dataChangeActivityService")
public class DataChangeActivityServiceImpl implements DataChangeActivityService {

    private static Logger logger = LoggerFactory.getLogger(AgentEnterServiceImpl.class);

    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private DateChangeRequestMapper dateChangeRequestMapper;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private AimportService aimportService;
    @Autowired
    private AgentNotifyService agentNotifyService;
    @Autowired
    private AColinfoPaymentService colinfoPaymentService;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private CapitalService capitalService;
    @Autowired
    private CapitalMapper capitalMapper;



    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public ResultVO startDataChangeActivity(String dataChangeId,String userId) throws Exception{
        logger.info("========用户{}启动数据修改申请{}",userId,dataChangeId);
        DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(dataChangeId);

        DateChangeRequestExample dateChangeRequestExample = new DateChangeRequestExample();
        DateChangeRequestExample.Criteria criteria = dateChangeRequestExample.createCriteria();
        criteria.andDataIdEqualTo(dateChangeRequest.getDataId());
        criteria.andDataTypeEqualTo(BusActRelBusType.DC_Colinfo.name());
        criteria.andAppyStatusEqualTo(AgStatus.Approving.getValue());
        List<DateChangeRequest> dateChangeRequests = dateChangeRequestMapper.selectByExample(dateChangeRequestExample);
        if(null==dateChangeRequests){
            logger.info("数据修改申请:{}", "查询审批状态失败");
            throw new MessageException("查询审批状态失败！");
        }
        if(dateChangeRequests.size()>0){
            logger.info("数据修改申请:{}", "审批中请勿重复提交");
            throw new MessageException("审批中请勿重复提交！");
        }
        AgentVo agentVo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
        if(null==agentVo){
            logger.info("数据修改申请:{}", "转换异常");
            throw new MessageException("修改申请提交审批失败！");
        }
        List<AgentColinfoVo> colinfoVoList = agentVo.getColinfoVoList();
        if(colinfoVoList!=null) {
            for (AgentColinfoVo agentColinfoVo : colinfoVoList) {
                List<AColinfoPayment> aColinfoPayments = colinfoPaymentService.queryConlifoPaymentList(dateChangeRequest.getDataId(), agentColinfoVo.getCloBankAccount());
                if (null == aColinfoPayments) {
                    logger.info("数据修改申请:{}", "收款账号查询失败");
                    throw new MessageException("收款账号查询失败");
                }
                if (aColinfoPayments.size() > 2) {
                    logger.info("数据修改申请:{}", "收款账号已验证超过2次请更换银行卡");
                    throw new MessageException("收款账号已验证超过2次请更换银行卡！");
                }
            }
        }
        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(dateChangeRequest.getId()).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if(list.size()>0){
            logger.info("========用户{}启动数据修改申请{}{}",dataChangeId,userId,"申请进行中，禁止重复提交");
            return ResultVO.fail("申请进行中，禁止重复提交");
        }

        //不同的业务类型找到不同的启动流程
        List<Dict> actlist = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.DATA_CACTIVITY_TYPE.name());
        String workId = null;
        for (Dict dict : actlist) {
            if(dict.getdItemvalue().equals(dateChangeRequest.getDataType())){
                workId = dict.getdItemname();
            }
        }

        dateChangeRequest.setAppyStatus(AgStatus.Approving.status);
        if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dateChangeRequest)){
            logger.info("代理商审批，启动审批异常，更新记录状态{}:{}",dateChangeRequest.getId(),userId);
            throw  new MessageException("更新记录状态异常");
        }
        if(StringUtils.isEmpty(workId)) {
            logger.info("========用户{}启动数据修改申请{}{}",dataChangeId,userId,"审批流启动失败字典中未配置部署流程");
            throw new MessageException("审批流启动失败字典中未配置部署流程!");
        }
        Map startPar = agentEnterService.startPar(userId);
        if(null==startPar){
            logger.info("========用户{}启动数据修改申请{}{}启动部门参数为空",dataChangeId,userId,"审批流启动失败字典中未配置部署流程");
            throw new MessageException("启动部门参数为空!");
        }

        String proce = activityService.createDeloyFlow(null,workId,null,null,startPar);
        if(proce==null){
            logger.info("========用户{}启动数据修改申请{}{}",dataChangeId,userId,"数据修改审批，审批流启动失败");
            logger.info("数据修改审批，审批流启动失败{}:{}",dataChangeId,userId);
            throw new MessageException("审批流启动失败!");
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(dateChangeRequest.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(dateChangeRequest.getDataType());//流程关系类型是数据申请类型
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(dateChangeRequest.getDataId());
        Agent agent = agentMapper.selectByPrimaryKey(dateChangeRequest.getDataId());
        if(agent!=null)
        record.setAgentName(agent.getAgName());
//        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByAgenId(agent.getId());
//        AgentBusInfo agentBusInfo = agentBusInfos.get(0);
//        PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
//        record.setNetInBusType("ACTIVITY_"+platForm.getPlatformType());
        if(1!=busActRelMapper.insertSelective(record)){
            logger.info("代理商审批，启动审批异常，添加审批关系失败{}:{}",dateChangeRequest.getId(),proce);
            throw  new MessageException("添加审批关系失败");
        }
        return ResultVO.success(null);
    }


    /**
     * 收款账户修改 审批完成处理
     * @param proIns
     * @param agStatus
     * @return
     */
    @Override
    public ResultVO compressColInfoDataChangeActivity(String proIns, String agStatus)throws Exception {
        try {
            BusActRelExample example = new BusActRelExample();
            example.or().andStatusEqualTo(Status.STATUS_1.status).andActivIdEqualTo(proIns).andActivStatusEqualTo(AgStatus.Approving.name());
            List<BusActRel> resl =  busActRelMapper.selectByExample(example);
            if(resl.size()==0){
                logger.info("========审批流完成{}状态{}未找到数据与审批流关系",proIns,agStatus);
                return ResultVO.fail("未找到数据与审批流关系");
            }
            BusActRel rel = resl.get(0);
            logger.info("========审批流完成{}业务{}状态{}",proIns,rel.getBusType(),agStatus);
            DateChangeRequest dr = dateChangeRequestMapper.selectByPrimaryKey(rel.getBusId());
            if(dr==null){
                logger.info("========审批流完成{}业务{}状态{} 未找到数据",proIns,rel.getBusType(),agStatus);
                throw new ProcessException("更新数据申请失败");
            }
            try {
                if(AgStatus.Approved.name().equals(agStatus)){
                    //收款账户修改
                    if(DataChangeApyType.DC_Colinfo.name().equals(dr.getDataType())) {
                        //更新入库
                        AgentVo vo = JSONObject.parseObject(dr.getDataContent(), AgentVo.class);
                        ResultVO res = agentColinfoService.updateAgentColinfoVo(vo.getColinfoVoList(), vo.getAgent(),rel.getcUser());
                        logger.info("========审批流完成{}业务{}状态{},结果{}", proIns, rel.getBusType(), agStatus, res.getResInfo());
                        //更新数据状态为审批成功
                        if(res.isSuccess()){
                            dr.setAppyStatus(AgStatus.Approved.status);
                            dr.setcUpdate(Calendar.getInstance().getTime());
                            logger.info("========审批流完成{}业务{}状态{},结果{}",proIns,rel.getBusType(),agStatus,"更新数据申请成功");
                            if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dr)){
                                throw new ProcessException("更新数据申请失败");
                            }

                            //首刷平台
                            PlatFormExample platFormExample = new PlatFormExample();
                            platFormExample.or().andStatusEqualTo(Status.STATUS_1.status).andPlatformTypeEqualTo(PlatformType.MPOS.code);
                            List<PlatForm>  platForms = platFormMapper.selectByExample(platFormExample);
                            List<String> pltcode = new ArrayList<>();
                            pltcode.add("aaaa");
                            for (PlatForm platForm : platForms) {
                                pltcode.add(platForm.getPlatformNum());
                            }
                            //查询首刷业务
                            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                            agentBusInfoExample.or()
                                    .andStatusEqualTo(Status.STATUS_1.status)
                                    .andBusPlatformIn(pltcode)
                                    .andAgentIdEqualTo(vo.getAgent().getId());
                            List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(agentBusInfoExample);

                            //入网程序调用
                            try {
                                for (AgentBusInfo agentBusInfo : agentBusInfoList) {

                                    ImportAgent importAgent = new ImportAgent();
                                    importAgent.setDataid(agentBusInfo.getId());
                                    importAgent.setDatatype(AgImportType.DATACHANGEAPP.name());
                                    importAgent.setBatchcode(proIns);
                                    importAgent.setcUser(rel.getcUser());
                                    if (1 != aimportService.insertAgentImportData(importAgent)) {
                                        logger.info("代理商账户修改审批通过-添加修改任务失败");
                                    } else {
                                        logger.info("代理商账户修改审批通过-添加修改任务成功!{},{}", AgImportType.DATACHANGEAPP.getValue(), vo.getAgent().getId());
                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                throw new ProcessException("更新賬戶数据申请失败");
                            } finally {
                                agentNotifyService.asynNotifyPlatform();
                            }

                        }
                    //代理商新修改
                    }else if(DataChangeApyType.DC_Agent.name().equals(dr.getDataType())){

                        //更新入库
                        AgentVo vo = JSONObject.parseObject(dr.getDataContent(), AgentVo.class);
                        ResultVO res = agentEnterService.updateAgentVo(vo,rel.getcUser());
                        logger.info("========审批流完成{}业务{}状态{},结果{}", proIns, rel.getBusType(), agStatus, res.getResInfo());
                        //更新数据状态为审批成功
                        if(res.isSuccess()){
                            dr.setAppyStatus(AgStatus.Approved.status);
                            dr.setcUpdate(Calendar.getInstance().getTime());
                            logger.info("========审批流完成{}业务{}状态{},结果{}",proIns,rel.getBusType(),agStatus,"更新数据申请成功");
                            if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dr)){
                                throw new ProcessException("更新数据申请失败");
                            }
                            Agent agent = vo.getAgent();
                            String agentId = agent.getId();
                            List<Capital> capitals = capitalService.queryCapital(agentId);
                            for (Capital capital : capitals) {
                                capital.setCloReviewStatus(AgStatus.Approved.getValue());
                                int i = capitalMapper.updateByPrimaryKeySelective(capital);
                                if(1!=i){
                                    throw new ProcessException("更新缴纳款审批通过失败");
                                }
                            }
                        }

                        //入网程序调用
                        try {
                            if(vo.getBusInfoVoList()!=null){

                                for (AgentBusInfoVo agentBusInfoVo : vo.getBusInfoVoList()) {
                                    if(StringUtils.isNotBlank(agentBusInfoVo.getId())) {
                                        ImportAgent importAgent = new ImportAgent();
                                        importAgent.setDataid(agentBusInfoVo.getId());
                                        importAgent.setDatatype(AgImportType.DATACHANGEAPP.name());
                                        importAgent.setBatchcode(proIns);
                                        importAgent.setcUser(rel.getcUser());
                                        if (1 != aimportService.insertAgentImportData(importAgent)) {
                                            logger.info("代理商修改审批通过-添加开户任务失败");
                                        } else {
                                            logger.info("代理商修改审批通过-添加开户任务成功!{},{}", AgImportType.DATACHANGEAPP.getValue(), vo.getAgent().getId());
                                        }
                                    }

                                }

                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new ProcessException("更新数据申请失败");
                        } finally {
                            agentNotifyService.asynNotifyPlatform();
                        }

                    }
                //拒绝更新数据状态
                }else if(AgStatus.Refuse.name().equals(agStatus)){
                    dr.setAppyStatus(AgStatus.Refuse.status);
                    dr.setcUpdate(Calendar.getInstance().getTime());
                    logger.info("========审批流完成{}业务{}状态{},结果{}",proIns,rel.getBusType(),agStatus,"更新数据申请失败");
                    if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dr)){
                        throw new ProcessException("更新数据申请失败");
                    }
                }
                rel.setActivStatus(agStatus);
                if(1!=busActRelMapper.updateByPrimaryKeySelective(rel)){
                    throw new ProcessException("更新数据申请失败");
                }
            } catch (ProcessException e) {
                e.printStackTrace();
               throw e;
            }

            try {
                agentQueryService.loadCach();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return ResultVO.success(dr);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 处理任务
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception{

        try {
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(userId));
            if(orgCodeRes==null && orgCodeRes.size()!=1){
                throw new ProcessException("部门参数为空");
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String orgId = String.valueOf(stringObjectMap.get("ORGID"));
            //财务审批
            if(orgId.equals("222")){
                DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(agentVo.getAgentBusId());
                if(null==dateChangeRequest){
                    throw new ProcessException("数据错误");
                }
                //数据修改
                if(dateChangeRequest.getDataType().equals(DataChangeApyType.DC_Agent.name())){
                    AgentVo vo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
                    if(StringUtils.isBlank(agentVo.getDebt()) || StringUtils.isBlank(agentVo.getOweTicket())){
                        throw new ProcessException("请填写欠票欠款信息,没有请填0");
                    }
                    vo.setDebt(agentVo.getDebt());
                    vo.setOweTicket(agentVo.getOweTicket());
                    String voJson = JSONObject.toJSONString(vo);
                    dateChangeRequest.setDataContent(voJson);
                    int i = dateChangeRequestMapper.updateByPrimaryKeySelective(dateChangeRequest);
                    if(i!=1){
                        throw new ProcessException("处理任务：更新失败");
                    }
                }
            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                logger.error(result.getMsg());
                throw new ProcessException("工作流处理任务异常");
            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new MessageException("catch工作流处理任务异常:",e.getMsg());
        }
        return AgentResult.ok();
    }

}
