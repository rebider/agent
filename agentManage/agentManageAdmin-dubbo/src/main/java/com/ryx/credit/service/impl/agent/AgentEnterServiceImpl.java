package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.*;
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

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by cx on 2018/5/28.
 */
@Service("agentEnterService")
public class AgentEnterServiceImpl implements AgentEnterService {

    private static Logger logger = LoggerFactory.getLogger(AgentEnterServiceImpl.class);
    private final String JURIS_DICTION = AppConfig.getProperty("region_jurisdiction");

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
    @Autowired
    private AgentAssProtocolService agentAssProtocolService;
    @Autowired
    private AimportService aimportService;
    @Autowired
    private AgentNotifyService agentNotifyService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private AssProtoColMapper assProtoColMapper;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private CapitalFlowService capitalFlowService;


    /**
     * 商户入网
     *
     * @param agentVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO agentEnterIn(AgentVo agentVo) throws ProcessException {
        try {
            verifyOrgAndBZYD(agentVo.getBusInfoVoList());
//            verifyOther(agentVo.getBusInfoVoList());
            Agent agent = agentService.insertAgent(agentVo.getAgent(), agentVo.getAgentTableFile(),agentVo.getAgent().getcUser());
            agentVo.setAgent(agent);
            for (AgentContractVo item : agentVo.getContractVoList()) {
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                item.setCloReviewStatus(AgStatus.Create.status);
                agentContractService.insertAgentContract(item, item.getContractTableFile(),agent.getcUser());

            }
            for (CapitalVo item : agentVo.getCapitalVoList()) {
                if(item.getcPayType().equals(PayType.YHHK.getValue())){
                    if(item.getCapitalTableFile()==null){
                        throw new ProcessException("银行汇款方式必须上传打款凭据");
                    }
                    if(item.getCapitalTableFile().size()==0){
                        throw new ProcessException("银行汇款方式必须上传打款凭据");
                    }
                }
                item.setcAgentId(agent.getId());
                item.setcUser(agent.getcUser());
                AgentResult res = accountPaidItemService.insertAccountPaid(item, item.getCapitalTableFile(), agentVo.getAgent().getcUser(),false);
                if (!res.isOK()) {
                    throw new ProcessException("添加交款项异常");
                }

            }
            for (AgentColinfoVo item : agentVo.getColinfoVoList()) {
                item.setAgentId(agent.getId());
                item.setcUser(agent.getcUser());
                item.setCloReviewStatus(AgStatus.Create.status);
                agentColinfoService.agentColinfoInsert(item, item.getColinfoTableFile());
            }
            //判断平台是否重复
            List hav = new ArrayList();
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                if(item.getBusType().equals(BusType.ZQZF.key) || item.getBusType().equals(BusType.ZQBZF.key) || item.getBusType().equals(BusType.ZQ.key) ){
                    if(StringUtils.isBlank(item.getBusParent()))
                    throw new ProcessException("直签上级不能为空");
                }

                if (hav.contains(item.getBusPlatform())) {
                    throw new ProcessException("开通(" + item.getBusPlatform() + ")业务平台重复");
                } else {
                    hav.add(item.getBusPlatform());
                }
                if (null!=item.getBusPlatform()){
                    PlatformType platformType = platFormService.byPlatformCode(item.getBusPlatform());
                    if (null!=platformType){
                        if(platformType.code.equals(PlatformType.POS.code) || platformType.code.equals(PlatformType.ZPOS.code)){
                            if (StringUtils.isNotBlank(item.getBusNum())){
                                if (StringUtils.isBlank(item.getBusLoginNum())){
                                    logger.info("请填写平台登录账号");
                                    throw new ProcessException("请填写平台登录账号");
                                }
                            }
                        }
                    }
                }
            }
            Set<String> resultSet = new HashSet<>();
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                PlatForm platForm = platFormMapper.selectByPlatFormNum(item.getBusPlatform());
                resultSet.add(platForm.getPlatformType());
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                item.setCloReviewStatus(AgStatus.Create.status);
                AgentBusInfo db_AgentBusInfo = agentBusinfoService.agentBusInfoInsert(item);
                if (StringUtils.isNotBlank(item.getAgentAssProtocol())) {
                    AssProtoColRel rel = new AssProtoColRel();
                    rel.setAgentBusinfoId(db_AgentBusInfo.getId());
                    rel.setAssProtocolId(item.getAgentAssProtocol());
                    AssProtoCol assProtoCol = assProtoColMapper.selectByPrimaryKey(item.getAgentAssProtocol());
                    if(org.apache.commons.lang.StringUtils.isNotBlank(item.getProtocolRuleValue())){
                        String ruleReplace = assProtoCol.getProtocolRule().replace("{}", item.getProtocolRuleValue());
                        rel.setProtocolRule(ruleReplace);
                    }else{
                        rel.setProtocolRule(assProtoCol.getProtocolRule());
                    }
                    rel.setProtocolRuleValue(item.getProtocolRuleValue());
                    if (1 != agentAssProtocolService.addProtocolRel(rel, agent.getcUser())) {
                        throw new ProcessException("业务分管协议添加失败");
                    }
                }
            }
            if(resultSet.size()>1){
                throw new ProcessException("不能同时提交pos和手刷平台");
            }
            return ResultVO.success(agentVo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProcessException(e.getMessage());
        }
    }

    /**
     * 代理商入网开通的业务平台类型为机构与标准一代时，新增业务平台业务类型也必须为机构或标准一代。
     * @param busInfoVoList
     * @throws Exception
     */
    @Override
    public void verifyOrgAndBZYD(List<AgentBusInfoVo> busInfoVoList)throws Exception {
        Set<String> BusTypeSet = new HashSet<>();
        for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
            BusTypeSet.add(agentBusInfoVo.getBusType());
        }
        for (String busType : BusTypeSet) {
            if(busType.equals(BusType.JG.key) || busType.equals(BusType.BZYD.key)){
                for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
                    if(!agentBusInfoVo.getBusType().equals(BusType.JG.key) && !agentBusInfoVo.getBusType().equals(BusType.BZYD.key)){
                        throw new ProcessException("业务平台类型为机构与标准一代时不能选择其他");
                    }
                }
            }
        }
    }

    /**
     * （业务市场财务去掉必须是同一个上级限制，此方法废弃 20190313）
     * 代理商新签入网业务平台类型为机构一代、二代直签直发、直签不直发、一代X时，所有业务平台上级必须为同一个。
     * @param busInfoVoList
     * @throws Exception
     */
//    @Override
//    public void verifyOther(List<AgentBusInfoVo> busInfoVoList)throws Exception {
//        Set<String> busParentSet = new HashSet<>();
//        for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
//            String busParent = "";
//            if(StringUtils.isNotBlank(agentBusInfoVo.getBusParent())){
//                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getBusParent());
//                busParent = agentBusInfo.getAgentId();
//            }else{
//                busParent = "";
//            }
//            busParentSet.add(busParent);
//        }
//        for (AgentBusInfoVo agentBusInfoVo : busInfoVoList) {
//            if(agentBusInfoVo.getBusType().equals(BusType.ZQ.key) || agentBusInfoVo.getBusType().equals(BusType.JGYD.key)
//            || agentBusInfoVo.getBusType().equals(BusType.YDX.key) || agentBusInfoVo.getBusType().equals(BusType.ZQBZF.key)){
//                if(busParentSet.size()!=1){
//                    throw new ProcessException("上级不是同一个");
//                }
//            }
//        }
//    }

    /**
     * 启动代理商审批
     *
     * @param agentId
     * @param cuser
     * @return
     * @throws ProcessException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO startAgentEnterActivity(String agentId, String cuser) throws ProcessException {
            if (StringUtils.isBlank(agentId)) {
                logger.info("代理商审批,代理商ID为空{}:{}", agentId, cuser);
                return ResultVO.fail("代理商审批中，代理商ID为空");
            }
            if (StringUtils.isBlank(cuser)) {
                logger.info("代理商审批,操作用户为空{}:{}", agentId, cuser);
                return ResultVO.fail("代理商审批中，操作用户为空");
            }

            //检查是否有审批中的代理商新
            BusActRelExample example = new BusActRelExample();
            example.or().andBusIdEqualTo(agentId).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
            if (busActRelMapper.selectByExample(example).size() > 0) {
                logger.info("代理商审批,禁止重复提交审批{}:{}", agentId, cuser);
                return ResultVO.fail("代理商审批中，禁止重复提交审批");
            }

            Agent agent = agentService.getAgentById(agentId);
            if (agent.getAgStatus().equals(AgStatus.Approving.name())) {
                logger.info("代理商审批,禁止重复提交审批{}:{}", agentId, cuser);
                return ResultVO.fail("代理商审批中，禁止重复提交审批");
            }
            if (!agent.getStatus().equals(Status.STATUS_1.status)) {
                logger.info("代理商审批中,代理商信息已失效{}:{}", agentId, cuser);
                return ResultVO.fail("代理商信息已失效");
            }


            //更新代理商审批中
            agent.setAgStatus(AgStatus.Approving.name());
            if (1 != agentService.updateAgent(agent)) {
                logger.info("代理商审批，更新代理商基本信息失败{}:{}", agentId, cuser);
                throw new ProcessException("启动审批异常，更新代理商基本信息失败");
            }

            //获取代理商有效的业务
            List<AgentBusInfo> aginfo = agentBusinfoService.agentBusInfoList(agent.getId());
            for (AgentBusInfo agentBusInfo : aginfo) {
                agentBusInfo.setcUtime(Calendar.getInstance().getTime());
                agentBusInfo.setCloReviewStatus(AgStatus.Approving.status);
                if (agentBusinfoService.updateAgentBusInfo(agentBusInfo) != 1) {
                    logger.info("代理商审批，更新业务本信息失败{}:{}", agentId, cuser);
                    throw new ProcessException("启动审批异常，更新业务本信息失败");
                }
            }
            //代理商有效新建的合同
            List<AgentContract> ag = agentContractService.queryAgentContract(agentId, null, AgStatus.Create.status);
            for (AgentContract contract : ag) {
                contract.setCloReviewStatus(AgStatus.Approving.status);
                if (1 != agentContractService.update(contract)) {
                    logger.info("代理商审批，合同状态更新失败{}:{}", agentId, cuser);
                    throw new ProcessException("合同状态更新失败");
                }
            }

            //代理商有效的新建的收款账户
            List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(agentId, null, AgStatus.Create.status);
            for (AgentColinfo agentColinfo : clolist) {
                agentColinfo.setCloReviewStatus(AgStatus.Approving.status);
                if (1 != agentColinfoService.update(agentColinfo)) {
                    logger.info("代理商审批，合同状态更新失败{}:{}", agentId, cuser);
                    throw new ProcessException("合同状态更新失败");
                }
            }

            List<Capital> capitals = accountPaidItemService.queryCap(agentId, null, null, AgStatus.Create.status);
            for (Capital capital : capitals) {
                capital.setCloReviewStatus(AgStatus.Approving.status);
                if (1 != accountPaidItemService.update(capital)) {
                    logger.info("代理商审批，合同状态更新失败{}:{}", agentId, cuser);
                    throw new ProcessException("合同状态更新失败");
                }
            }
            Map startPar = startPar(cuser);
            if (null == startPar) {
                logger.info("========用户{}{}启动部门参数为空", agentId, cuser);
                throw new ProcessException("启动部门参数为空!");
            }
            startPar.put("rs",ApprovalType.PASS.getValue());
            //启动审批
            String proce = activityService.createDeloyFlow(null, AppConfig.getProperty("agent_net_in_activity"), null, null, startPar);
            if (proce == null) {
                logger.info("代理商审批，审批流启动失败{}:{}", agentId, cuser);
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
            record.setAgentId(agent.getId());
            record.setAgentName(agent.getAgName());
            record.setDataShiro(BusActRelBusType.Agent.key);

            AgentBusInfo agentBusInfo = aginfo.get(0);
            PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
            record.setNetInBusType("ACTIVITY_"+platForm.getPlatformType());
            if (1 != busActRelMapper.insertSelective(record)) {
                logger.info("代理商审批，启动审批异常，添加审批关系失败{}:{}", agentId, proce);
            }
            return ResultVO.success(null);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO startAgentBusiEnterActivity(String busid, String cuser) throws ProcessException {
        AgentBusInfo abus = agentBusinfoService.getById(busid);
        if (abus == null) {
            logger.info("代理商信息审批中,业务信息未找到{}:{}", busid, cuser);
            return ResultVO.fail("业务信息未找到");
        }
        //检查是否有审批中的代理商新
        Agent agent = agentService.getAgentById(abus.getAgentId());
        if (agent.getAgStatus().equals(AgStatus.Approving.name())) {
            logger.info("代理商信息审批中,禁止启动业务审批{}:{}", busid, cuser);
            return ResultVO.fail("代理商信息审批中,禁止启动业务审批");
        }
        if (!agent.getAgStatus().equals(AgStatus.Approved.name())) {
            logger.info("代理商信息未审批完成,禁止启动业务审批{}:{}", busid, cuser);
            return ResultVO.fail("代理商信息未审批完成,禁止启动业务审批");
        }
        if (!agent.getStatus().equals(Status.STATUS_1.status)) {
            logger.info("代理商信息已失效{}:{}", busid, cuser);
            return ResultVO.fail("代理商信息已失效");
        }

        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(abus.getId()).andBusTypeEqualTo(BusActRelBusType.Business.name()).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        if (busActRelMapper.selectByExample(example).size() > 0) {
            logger.info("代理商审批中，禁止重复提交审批{}:{}", busid, cuser);
            return ResultVO.fail("代理商审批中，禁止重复提交审批");
        }

        //获取代理商有效的业务
        abus.setcUtime(Calendar.getInstance().getTime());
        abus.setCloReviewStatus(AgStatus.Approving.status);
        if (agentBusinfoService.updateAgentBusInfo(abus) != 1) {
            logger.info("代理商业务启动审批异常，更新业务本信息失败{}:{}", busid, cuser);
            throw new ProcessException("代理商业务启动审批异常，更新业务本信息失败");
        }


        //代理商有效新建的合同
        List<AgentContract> ag = agentContractService.queryAgentContract(abus.getAgentId(), null, AgStatus.Create.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != agentContractService.update(contract)) {
                logger.info("代理商业务启动审批异常，合同状态更新失败{}:{}", busid, cuser);
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(abus.getAgentId(), null, AgStatus.Create.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != agentColinfoService.update(agentColinfo)) {
                logger.info("代理商业务启动审批异常，收款账户状态更新失败{}:{}", busid, cuser);
                throw new ProcessException("收款账户状态更新失败");
            }
        }

        List<Capital> capitals = accountPaidItemService.queryCap(abus.getAgentId(), null, null, AgStatus.Create.status);
        for (Capital capital : capitals) {
            capital.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != accountPaidItemService.update(capital)) {
                logger.info("代理商审批，合同状态更新失败{}:{}", abus.getAgentId(), cuser);
                throw new ProcessException("合同状态更新失败");
            }
        }

        Map startPar = startPar(cuser);
        if (null == startPar) {
            logger.info("========用户{}{}启动部门参数为空", busid, cuser);
            throw new ProcessException("启动部门参数为空!");
        }
        startPar.put("rs",ApprovalType.PASS.getValue());
        //启动审批
        String proce = activityService.createDeloyFlow(null, AppConfig.getProperty("agent_net_in_activity"), null, null, startPar);
        if (proce == null) {
            logger.info("代理商业务启动审批异常，审批流启动失败{}:{}", busid, cuser);
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
        record.setAgentId(agent.getId());
        record.setAgentName(agent.getAgName());
        record.setDataShiro(BusActRelBusType.Business.key);

        PlatForm platForm = platFormMapper.selectByPlatFormNum(abus.getBusPlatform());
        record.setNetInBusType("ACTIVITY_"+platForm.getPlatformType());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("代理商业务启动审批异常，添加审批关系失败{}:{}", record.getBusId(), proce);
        }
        return ResultVO.success(null);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult completeTaskEnterActivity(AgentVo agentVo, String userId) throws ProcessException {

        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("rs", agentVo.getApprovalResult());
        reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
        reqMap.put("approvalPerson", userId);
        reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
        reqMap.put("taskId", agentVo.getTaskId());
        reqMap.put("dept", agentVo.getDept());
        if(StringUtils.isNotBlank(agentVo.getMainDocDistrict()) && StringUtils.isNotBlank(agentVo.getSubDocDistrict())){
            reqMap.put(agentVo.getMainDocDistrict(),agentVo.getMainDocDistrict());
            reqMap.put(agentVo.getSubDocDistrict(),agentVo.getSubDocDistrict());
            if(!reqMap.containsValue("beijing")){
                reqMap.put("beijing","");
            }
            if(!reqMap.containsValue("south")){
                reqMap.put("south","");
            }
            if(!reqMap.containsValue("north")){
                reqMap.put("north","");
            }
        }

        if(StringUtils.isNotBlank(agentVo.getOperationType())){
            reqMap.put("operationType", agentVo.getOperationType());
        }

        if(agentVo.getAmt() != null){
            reqMap.put("amt", agentVo.getAmt());
        }

        //传递部门信息
        Map startPar = startPar(userId);
        if (null != startPar) {
            reqMap.put("party", startPar.get("party"));
        }

        Map resultMap = activityService.completeTask(agentVo.getTaskId(), reqMap);
        Boolean rs = (Boolean) resultMap.get("rs");
        String msg = String.valueOf(resultMap.get("msg"));
        if (resultMap == null) {
            return result;
        }
        if (!rs) {
            result.setMsg(msg);
            return result;
        }
        return AgentResult.ok(resultMap);
    }


    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultVO completeProcessing(String processingId, String processingStatus) throws ProcessException {
        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(processingId).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            logger.info("审批任务结束{}{}，未找到审批中的审批和数据关系", processingId, processingStatus);
            return ResultVO.fail("审批任务结束" + processingId + ":" + processingStatus + "未找到审批中的审批和数据关系");
        }
        BusActRel rel = list.get(0);
        if (rel.getBusType().equals(BusActRelBusType.Business.name())) {
            //审批通过
            if (AgStatus.Approved.name().equals(processingStatus)) {
                return processingBusAproveApproved(rel, processingId, rel.getBusId());
            }
            //审批拒绝
            if (AgStatus.Refuse.name().equals(processingStatus)) {
                return processingBusAproveRefuse(rel, processingId, rel.getBusId());
            }
        }
        if (rel.getBusType().equals(BusActRelBusType.Agent.name())) {
            //审批通过
            if (AgStatus.Approved.name().equals(processingStatus)) {
                return processingAgentApproved(rel, processingId, rel.getBusId());
            }
            //审批拒绝
            if (AgStatus.Refuse.name().equals(processingStatus)) {
                return processingAgentRefuse(rel, processingId, rel.getBusId());
            }
        }
        return ResultVO.success("");
    }


    /**
     * 业务审批同意
     *
     * @param processingId
     * @param busId
     * @return
     */
    private ResultVO processingBusAproveApproved(BusActRel rel, String processingId, String busId) {
        rel.setActivStatus(AgStatus.Approved.name());
        if (1 != busActRelMapper.updateByPrimaryKeySelective(rel)) {
            logger.info("代理商审批通过，更新BusActRel失败{}:{}", processingId, rel.getBusId());
        }

        AgentBusInfo bus = agentBusinfoService.getById(busId);
        bus.setcUtime(Calendar.getInstance().getTime());
        bus.setCloReviewStatus(AgStatus.Approved.status);
        if(StringUtils.isNotBlank(bus.getBusNum())){
            bus.setBusStatus(BusinessStatus.pause.status);
        }
        if (agentBusinfoService.updateAgentBusInfo(bus) != 1) {
            logger.info("代理商审批通过，更新业务本信息失败{}:{}", processingId, bus.getId());
            throw new ProcessException("代理商审批通过，更新业务本信息失败");
        }
        //代理商有效新建的合同
        List<AgentContract> ag = agentContractService.queryAgentContract(bus.getAgentId(), null, AgStatus.Approving.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Approved.status);
            if (1 != agentContractService.update(contract)) {
                logger.info("代理商审批通过，合同状态更新失败{}:{}", processingId, contract.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(bus.getAgentId(), null, AgStatus.Approving.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Approved.status);
            if (1 != agentColinfoService.update(agentColinfo)) {
                logger.info("代理商审批通过，收款状态更新失败{}:{}", processingId, agentColinfo.getId());
                throw new ProcessException("收款状态更新失败");
            }
        }
        //缴款
        List<Capital> capitals = accountPaidItemService.queryCap(bus.getAgentId(), null, null, AgStatus.Approving.status);
        for (Capital capital : capitals) {
            capital.setCloReviewStatus(AgStatus.Approved.status);
            if (1 != accountPaidItemService.update(capital)) {
                logger.info("代理商审批通过，合同状态更新失败{}:{}", processingId, bus.getId());
                throw new ProcessException("合同状态更新失败");
            }
            //插入资金流水
            try {
                capitalFlowService.insertCapitalFlow(capital,BigDecimal.ZERO,busId,"代理商新增业务");
            } catch (Exception e) {
                e.printStackTrace();
                throw new ProcessException("新增资金流水失败");
            }
        }
        //入网程序调用
        try {
            ImportAgent importAgent = new ImportAgent();
            importAgent.setDataid(busId);
            importAgent.setDatatype(AgImportType.BUSAPP.name());
            importAgent.setBatchcode(processingId);
            importAgent.setcUser(rel.getcUser());
            if (1 != aimportService.insertAgentImportData(importAgent)) {
                logger.info("代理商审批通过-添加开户任务失败");
            } else {
                logger.info("代理商审批通过-添加开户任务成功!{},{}", AgImportType.BUSAPP.getValue(), busId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            agentNotifyService.asynNotifyPlatform();
        }
        return ResultVO.success(null);
    }

    /**
     * 业务审批拒绝
     *
     * @param processingId
     * @param busId
     * @return
     */
    private ResultVO processingBusAproveRefuse(BusActRel rel, String processingId, String busId) {
        rel.setActivStatus(AgStatus.Refuse.name());
        if (1 != busActRelMapper.updateByPrimaryKeySelective(rel)) {
            logger.info("代理商审批拒绝，更新BusActRel失败{}:{}", processingId, rel.getBusId());
        }

        AgentBusInfo bus = agentBusinfoService.getById(busId);
        bus.setcUtime(Calendar.getInstance().getTime());
        bus.setCloReviewStatus(AgStatus.Refuse.status);
        if(StringUtils.isNotBlank(bus.getBusNum())){
            bus.setBusStatus(BusinessStatus.pause.status);
        }
        if (agentBusinfoService.updateAgentBusInfo(bus) != 1) {
            logger.info("代理商审批拒绝，更新业务本信息失败{}:{}", processingId, bus.getId());
            throw new ProcessException("代理商审批通过，更新业务本信息失败");
        }

        //代理商有效新建的合同
        List<AgentContract> ag = agentContractService.queryAgentContract(bus.getAgentId(), null, AgStatus.Approving.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Refuse.status);
            if (1 != agentContractService.update(contract)) {
                logger.info("代理商审批拒绝，合同状态更新失败{}:{}", processingId, contract.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(bus.getAgentId(), null, AgStatus.Approving.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Refuse.status);
            if (1 != agentColinfoService.update(agentColinfo)) {
                logger.info("代理商审批拒绝，收款状态更新失败{}:{}", processingId, agentColinfo.getId());
                throw new ProcessException("收款状态更新失败");
            }
        }

        List<Capital> capitals = accountPaidItemService.queryCap(bus.getAgentId(), null, null, AgStatus.Approving.status);
        for (Capital capital : capitals) {
            capital.setCloReviewStatus(AgStatus.Refuse.status);
            capital.setStatus(Status.STATUS_0.status);
            if (1 != accountPaidItemService.update(capital)) {
                logger.info("代理商审批，合同状态更新失败{}:{}", processingId, bus.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }


        return ResultVO.success(null);
    }

    /**
     * 代理商业务审批同意
     *
     * @param processingId
     * @param busId
     * @return
     */
    private ResultVO processingAgentApproved(BusActRel rel, String processingId, String busId) {

        rel.setActivStatus(AgStatus.Approved.name());
        if (1 != busActRelMapper.updateByPrimaryKeySelective(rel)) {
            logger.info("代理商审批通过，更新BusActRel失败{}:{}", processingId, rel.getBusId());
        }

        Agent agent = agentService.getAgentById(busId);
        agent.setAgStatus(AgStatus.Approved.name());
        agent.setAgUniqNum(agent.getId());
        if (1 != agentService.updateAgent(agent)) {
            logger.info("代理商审批通过，代理商信息失败{}:{}", processingId, agent.getId());
        }

        //获取代理商有效的业务
        List<AgentBusInfo> aginfo = agentBusinfoService.agentBusInfoList(agent.getId(), null, AgStatus.Approving.status);
        for (AgentBusInfo agentBusInfo : aginfo) {
            if(StringUtils.isNotBlank(agentBusInfo.getBusNum())){
                agentBusInfo.setBusStatus(BusinessStatus.pause.status);
            }
            agentBusInfo.setcUtime(Calendar.getInstance().getTime());
            agentBusInfo.setCloReviewStatus(AgStatus.Approved.status);
            if (agentBusinfoService.updateAgentBusInfo(agentBusInfo) != 1) {
                logger.info("代理商审批通过，更新业务本信息失败{}:{}", processingId, agentBusInfo.getId());
                throw new ProcessException("代理商审批通过，更新业务本信息失败");
            }
        }

        //代理商有效新建的合同
        List<AgentContract> ag = agentContractService.queryAgentContract(agent.getId(), null, AgStatus.Approving.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Approved.status);
            if (1 != agentContractService.update(contract)) {
                logger.info("代理商审批通过，合同状态更新失败{}:{}", processingId, contract.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(agent.getId(), null, AgStatus.Approving.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Approved.status);
            if (1 != agentColinfoService.update(agentColinfo)) {
                logger.info("代理商审批通过，收款状态更新失败{}:{}", processingId, agentColinfo.getId());
                throw new ProcessException("收款状态更新失败");
            }
        }
        List<Capital> capitals = accountPaidItemService.queryCap(agent.getId(), null, null, AgStatus.Approving.status);
        for (Capital capital : capitals) {
            capital.setCloReviewStatus(AgStatus.Approved.status);
            if (1 != accountPaidItemService.update(capital)) {
                logger.info("代理商审批，合同状态更新失败{}:{}",processingId, capital.getId());
                throw new ProcessException("合同状态更新失败");
            }
            if(PayType.FRDK.code.equals(capital.getcPayType())) {
                try {
                    //生成保证金等分期数据
                    AgentResult capitalFq = accountPaidItemService.capitalFq(capital);
                    if (!capitalFq.isOK()) {
                        logger.info("代理商审批，保证金{}:{}", processingId, capital.getId());
                        throw new ProcessException("合同状态更新失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new ProcessException(e.getMessage());
                }
            }
            try {
                capitalFlowService.insertCapitalFlow(capital,BigDecimal.ZERO,busId,"代理商入网");
            } catch (Exception e) {
                e.printStackTrace();
                throw new ProcessException("新增资金流水失败");
            }
        }
        //入网程序调用
        try {
            ImportAgent importAgent = new ImportAgent();
            importAgent.setDataid(busId);
            importAgent.setDatatype(AgImportType.NETINAPP.name());
            importAgent.setBatchcode(processingId);
            importAgent.setcUser(rel.getcUser());
            if (1 != aimportService.insertAgentImportData(importAgent)) {
                logger.info("代理商审批通过-添加开户任务失败");
            } else {
                logger.info("代理商审批通过-添加开户任务成功!{},{}", AgImportType.NETINAPP.getValue(), busId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //todo 生成后台用户
            agentService.createBackUserbyAgent(agent.getId());
            agentNotifyService.asynNotifyPlatform();
        }

        return ResultVO.success(null);
    }

    /**
     * 代理商业务审批拒绝
     *
     * @param processingId
     * @param busId
     * @return
     */
    private ResultVO processingAgentRefuse(BusActRel rel, String processingId, String busId) {
        rel.setActivStatus(AgStatus.Refuse.name());
        if (1 != busActRelMapper.updateByPrimaryKeySelective(rel)) {
            logger.info("代理商审批拒绝，更新BusActRel失败{}:{}", processingId, rel.getBusId());
        }

        Agent agent = agentService.getAgentById(busId);
        agent.setAgStatus(AgStatus.Refuse.name());
        if (1 != agentService.updateAgent(agent)) {
            logger.info("代理商审批拒绝，代理商信息失败{}:{}", processingId, agent.getId());
        }

        //获取代理商有效的业务
        List<AgentBusInfo> aginfo = agentBusinfoService.agentBusInfoList(agent.getId(), null, AgStatus.Approving.status);
        for (AgentBusInfo agentBusInfo : aginfo) {
            agentBusInfo.setcUtime(Calendar.getInstance().getTime());
            agentBusInfo.setCloReviewStatus(AgStatus.Refuse.status);
            if(StringUtils.isNotBlank(agentBusInfo.getBusNum())){
                agentBusInfo.setBusStatus(BusinessStatus.pause.status);
            }
            if (agentBusinfoService.updateAgentBusInfo(agentBusInfo) != 1) {
                logger.info("代理商审批拒绝，更新业务本信息失败{}:{}", processingId, agentBusInfo.getId());
                throw new ProcessException("代理商审批通过，更新业务本信息失败");
            }
        }

        //代理商有效新建的合同
        List<AgentContract> ag = agentContractService.queryAgentContract(agent.getId(), null, AgStatus.Approving.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Refuse.status);
            if (1 != agentContractService.update(contract)) {
                logger.info("代理商审批拒绝，合同状态更新失败{}:{}", processingId, contract.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(agent.getId(), null, AgStatus.Approving.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Refuse.status);
            if (1 != agentColinfoService.update(agentColinfo)) {
                logger.info("代理商审批拒绝，收款状态更新失败{}:{}", processingId, agentColinfo.getId());
                throw new ProcessException("收款状态更新失败");
            }
        }
        List<Capital> capitals = accountPaidItemService.queryCap(agent.getId(), null, null, AgStatus.Approving.status);
        for (Capital capital : capitals) {
            capital.setCloReviewStatus(AgStatus.Refuse.status);
            if (1 != accountPaidItemService.update(capital)) {
                logger.info("代理商审批，合同状态更新失败{}:{}",processingId, capital.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }
        return ResultVO.success(null);
    }


    /**
     * 信息修改
     * @param agent
     * @param userId
     * @param isPass  是否审批通过  审批通过传true ,未提交审批修改传 false
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO updateAgentVo(AgentVo agent, String userId,Boolean isPass) throws Exception {
        try {
            verifyOrgAndBZYD(agent.getBusInfoVoList());
//            verifyOther(agent.getBusInfoVoList());
            logger.info("用户{}{}修改代理商信息{}", userId, agent.getAgent().getId(), JSONObject.toJSONString(agent));
            Agent ag = null;
            if (StringUtils.isNotBlank(agent.getAgent().getAgName())) {
                ag = agentService.updateAgentVo(agent.getAgent(), agent.getAgentTableFile(),userId);
            }
            logger.info("用户{}{}修改代理商信息结果{}", userId, agent.getAgent().getId(), "成功");

            if (agent.getCapitalVoList() != null && agent.getCapitalVoList().size() > 0) {
                logger.info("用户{}{}修改代理商收款信息{}", userId, agent.getAgent().getId(), JSONObject.toJSONString(agent.getCapitalVoList()));
                ResultVO updateAccountPaidUpdateRes = accountPaidItemService.updateListCapitalVo(agent.getCapitalVoList(), agent.getAgent(),userId,isPass);
                logger.info("用户{}{}修改代理商收款信息结果{}", userId, agent.getAgent().getId(), updateAccountPaidUpdateRes.getResInfo());
            }
            if (agent.getContractVoList() != null && agent.getContractVoList().size() > 0) {
                logger.info("用户{}{}修改代理商合同信息{}", userId, agent.getAgent().getId(), JSONObject.toJSONString(agent.getContractVoList()));
                ResultVO updateAgentContractVoRes = agentContractService.updateAgentContractVo(agent.getContractVoList(), agent.getAgent(),userId);
                logger.info("用户{}{}修改代理商合同信息结果{}", userId, agent.getAgent().getId(), updateAgentContractVoRes.getResInfo());
            }
            if (agent.getColinfoVoList() != null && agent.getColinfoVoList().size() > 0) {
                logger.info("用户{}{}修改代理商收款信息{}", userId, agent.getAgent().getId(), JSONObject.toJSONString(agent.getColinfoVoList()));
                ResultVO updateAgentColinfoVoRes = agentColinfoService.updateAgentColinfoVo(agent.getColinfoVoList(), agent.getAgent(),userId);
                logger.info("用户{}{}修改代理商收款信息结果{}", userId, agent.getAgent().getId(), updateAgentColinfoVoRes.getResInfo());
            }
            if (agent.getBusInfoVoList() != null && agent.getBusInfoVoList().size() > 0) {
                logger.info("用户{}{}修改代理商业务信息{}", userId, agent.getAgent().getId(), JSONObject.toJSONString(agent.getBusInfoVoList()));
                ResultVO updateAgentBusInfoVoRes = agentBusinfoService.updateAgentBusInfoVo(agent.getBusInfoVoList(), agent.getAgent(),userId,isPass);
                logger.info("用户{}{}修改代理商业务信息结果{}", userId, agent.getAgent().getId(), updateAgentBusInfoVoRes.getResInfo());
            }

            return ResultVO.success(ag);
        } catch (ProcessException e) {
            logger.error("修改代理商错误", e.getMsg());
            throw new Exception(e.getMsg());
        } catch (MessageException e) {
            e.printStackTrace();
            logger.error("修改代理商错误", e);
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改代理商错误", e);
            throw new Exception("修改代理商错误");
        }
    }

    @Override
    public Map startPar(String cuserId) {
        if (StringUtils.isBlank(cuserId)) {
            logger.info("startPar用户ID为空");
            return null;
        }
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(cuserId));
        List<Dict> disc = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.ACTIVITY_RESPAR.name());
        Map res = null;
        for (Map<String, Object> orgCodeRe : orgCodeRes) {
            for (Dict dict : disc) {
                if (Pattern.matches(dict.getdItemvalue(), orgCodeRe.get("ORGANIZATIONCODE") + "")) {
                    res = FastMap.fastMap("party", dict.getdItemname());
                    return res;
                }
            }
        }
        return res;
    }

    public static BusinessPlatformService businessPlatformService;

    @Override
    public List<AgentoutVo> exportAgent(Map map,Long userId) throws ParseException {
        //加载缓存
        if (String.valueOf(map.get("flag")).equals("1")){
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if(orgCodeRes==null && orgCodeRes.size()!=1){
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String orgId = String.valueOf(stringObjectMap.get("ORGID"));
            map.put("orgId",orgId);
            map.put("userId",userId);
        }

        if (null != map) {
            String time = String.valueOf(map.get("time"));
            if (org.apache.commons.lang.StringUtils.isNotBlank(time)&&!time.equals("null")) {
                String reltime = time.substring(0, 10);
                map.put("time", reltime);
            }
        }
        String isZpos = String.valueOf(map.get("isZpos"));
        if (org.apache.commons.lang.StringUtils.isNotBlank(isZpos) && !isZpos.equals("null")) {
            map.put("isZpos", isZpos);
            map.put("platForm", Platform.ZPOS.getValue());
        }
        List<AgentoutVo> agentoutVos = agentMapper.excelAgent(map);
        if (null==agentoutVos && agentoutVos.size()<1)
            return null;
        List<Dict> BUS_TYPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name());
        List<Dict> BUS_SCOPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.BUS_SCOPE.name());
        List<Dict> COLINFO_TYPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.COLINFO_TYPE.name());


        if (null != agentoutVos && agentoutVos.size() > 0)
            for (AgentoutVo agentoutVo : agentoutVos) {
                if (StringUtils.isNotBlank(agentoutVo.getBusType()) && !agentoutVo.getBusType().equals("null")) {
                    for (Dict dict : BUS_TYPE) {
                        if (null!=dict  &&  agentoutVo.getBusType().equals(dict.getdItemvalue())){
                              agentoutVo.setBusType(dict.getdItemname());
                               break;
                        }
                    }
                }

                if (StringUtils.isNotBlank(agentoutVo.getBusScope()) && !agentoutVo.getBusScope().equals("null")) {
                    for (Dict dict : BUS_SCOPE) {
                        if (null!=dict  &&  agentoutVo.getBusScope().equals(dict.getdItemvalue())){
                            agentoutVo.setBusScope(dict.getdItemname());
                            break;
                        }
                    }
                }

                if (null != agentoutVo.getCloInvoice()) {
                    if (agentoutVo.getCloInvoice().compareTo(new BigDecimal(1)) == 0) {
                        agentoutVo.setYesOrNo("是");
                    } else if (agentoutVo.getCloInvoice().compareTo(new BigDecimal(0)) == 0)
                        agentoutVo.setYesOrNo("否");
                }

                if (null != agentoutVo.getCloType()) {
                    for (Dict dict : COLINFO_TYPE) {
                        if (null!=dict  &&  agentoutVo.getCloType().equals(dict.getdItemvalue())){
                            agentoutVo.setCloString(dict.getdItemname());
                            break;
                        }
                    }
                }

                if (null != agentoutVo.getCloTaxPoint()) {
                    NumberFormat numberFormat = NumberFormat.getPercentInstance();
                    Number parse = numberFormat.parse(agentoutVo.getCloTaxPoint().toString() + "%");
                    String point = numberFormat.format(parse);
                    agentoutVo.setPoint(point);
                }

                //业务区域
                if (StringUtils.isNotBlank(agentoutVo.getBusRegion()) && !agentoutVo.getBusRegion().equals("null")){
                    String busRegion = agentoutVo.getBusRegion();
                    if (StringUtils.isNotBlank(busRegion) &&!busRegion.equals("null")){
                        String[] arr = busRegion.split(",");
                        String name = agentQueryService.dPosRegionNameFromDposIds(arr);
                        if (StringUtils.isNotBlank(name) && !name.equals("null"))
                            agentoutVo.setBusRegion(name);
                    }
                }
                //银行地址
                if (StringUtils.isNotBlank(agentoutVo.getBankRegion()) && !agentoutVo.getBankRegion().equals("null")){
                    String bankRegion = agentoutVo.getBankRegion();
                    if (StringUtils.isNotBlank(bankRegion)){
                        String[] bank = bankRegion.split(",");
                        String bankName = agentQueryService.dRegionNameFromIds(bank);
                        if (StringUtils.isNotBlank(bankName))
                            agentoutVo.setBankRegion(bankName);
                    }
                }

            }
        return agentoutVos;
    }

    private void getBusParent(AgentoutVo agentoutVo) {
        List<AgentBusInfo> agentBusInfos = agentBusinfoService.queryParenFourLevel(new ArrayList<AgentBusInfo>(), agentoutVo.getBusPlatform(), agentoutVo.getAgentId());
        if (null != agentBusInfos && agentBusInfos.size() > 0) {
            if (agentBusInfos.size() == 1) {
                AgentBusInfo agentBusInfo = agentBusInfos.get(0);
                if (null != agentBusInfo) {
                    agentoutVo.setBusParentId(agentBusInfo.getAgentId());
                    Agent agentById = agentService.getAgentById(agentBusInfo.getAgentId());
                    if (null != agentById.getAgName())
                        agentoutVo.setBusParent(agentBusInfo.getAgentId() + agentById.getAgName());
                }
            } else if (agentBusInfos.size() == 2) {
                AgentBusInfo agentBusInfo = agentBusInfos.get(0);
                if (null != agentBusInfo) {
                    agentoutVo.setBusParentId(agentBusInfo.getAgentId());
                    Agent agentById = agentService.getAgentById(agentBusInfo.getAgentId());
                    if (null != agentById.getAgName())
                        agentoutVo.setBusParent(agentBusInfo.getAgentId() + agentById.getAgName());
                }
                AgentBusInfo twoParent = agentBusInfos.get(1);
                if (null != twoParent) {
                    agentoutVo.setBusParentId(twoParent.getAgentId());
                    Agent agentById = agentService.getAgentById(twoParent.getAgentId());
                    if (null != agentById.getAgName())
                        agentoutVo.setTwoParentId(twoParent.getAgentId() + agentById.getAgName());
                }

            } else if (agentBusInfos.size() == 3) {
                AgentBusInfo agentBusInfo = agentBusInfos.get(0);
                if (null != agentBusInfo) {
                    agentoutVo.setBusParentId(agentBusInfo.getAgentId());
                    Agent agentById = agentService.getAgentById(agentBusInfo.getAgentId());
                    if (null != agentById.getAgName())
                        agentoutVo.setBusParent(agentBusInfo.getAgentId() + agentById.getAgName());
                }
                AgentBusInfo twoParent = agentBusInfos.get(1);
                if (null != twoParent) {
                    agentoutVo.setBusParentId(twoParent.getAgentId());
                    Agent agentById = agentService.getAgentById(twoParent.getAgentId());
                    if (null != agentById.getAgName())
                        agentoutVo.setTwoParentId(twoParent.getAgentId() + agentById.getAgName());
                }
                AgentBusInfo threeParent = agentBusInfos.get(2);
                if (null != threeParent) {
                    agentoutVo.setBusParentId(threeParent.getAgentId());
                    Agent agentById = agentService.getAgentById(threeParent.getAgentId());
                    if (null != agentById.getAgName())
                        agentoutVo.setThreeParentId(threeParent.getAgentId() + agentById.getAgName());
                }
            }
        }
    }

}
