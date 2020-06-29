package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.RegexUtil;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.OrganizationMapper;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.AgentKafkaService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import com.ryx.credit.service.dict.DepartmentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
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
    //    @Autowired
//    private AgentNotifyService agentNotifyService;
    @Autowired
    private AColinfoPaymentService colinfoPaymentService;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentColinfoMapper agentColinfoMapper;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private CapitalService capitalService;
    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private CapitalFlowService capitalFlowService;
    @Autowired
    private DataChangeActivityService dataChangeActivityService;
    @Autowired
    private AgentNetInNotityService agentNetInNotityService;
    @Autowired
    private AgentDataHistoryService agentDataHistoryService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private AgentContractService agentContractService;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private AgentFreezeService agentFreezeService;
    @Autowired
    private AgentFreezeMapper agentFreezeMapper;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private AgentKafkaService agentKafkaService;


    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public ResultVO startDataChangeActivity(String dataChangeId,String userId) throws Exception{
        logger.info("========用户{}启动数据修改申请{}", userId, dataChangeId);
        DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(dataChangeId);

        // 查询省区是否发起数据，状态为审批中的，不能重复提交
        DateChangeRequestExample dateChangeRequestExample = new DateChangeRequestExample();
        DateChangeRequestExample.Criteria criteria = dateChangeRequestExample.createCriteria();
        criteria.andDataIdEqualTo(dateChangeRequest.getDataId());
        criteria.andDataTypeEqualTo(BusActRelBusType.DC_Colinfo.name());
        criteria.andAppyStatusEqualTo(AgStatus.Approving.getValue());
        // 查询代理商是否发起数据，状态为审批中的，不能重复提交
        dateChangeRequestExample.or()
                .andDataIdEqualTo(dateChangeRequest.getDataId())
                .andDataTypeEqualTo(BusActRelBusType.DC_AG_Colinfo.name())
                .andAppyStatusEqualTo(AgStatus.Approving.getValue());
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
               /* AgentColinfoExample agentColinfoExample = new AgentColinfoExample();
                AgentColinfoExample.Criteria criteria1 = agentColinfoExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status)
                        .andCloReviewStatusEqualTo(AgStatus.Approved.status)
                        .andAgentIdEqualTo(agentVo.getAgent().getId());
                List<AgentColinfo> agentColinfoList = agentColinfoMapper.selectByExample(agentColinfoExample);
                if(null!=agentColinfoList || agentColinfoList.size()>0){
                    AgentColinfo agentColinfo = agentColinfoList.get(0);
                    agentColinfo.setAmendStatus(AmendStatus.XGZ.status);
                    if(1!=agentColinfoMapper.updateByPrimaryKeySelective(agentColinfo)){
                        logger.info("数据修改申请:{}", "修改状态修改失败");
                        throw new MessageException("数据修改申请,修改状态修改失败！");
                    }
                }*/
            }
        }
        // 省区-结算卡申请，提交审批时，校验结算卡字段是否变更，变更则需冻结代理商状态且生成冻结记录
        if (dateChangeRequest.getDataType().equals(BusActRelBusType.DC_Colinfo.name())) {
            List<AgentBusInfo> agentBusInfos = agentBusinfoService.queryAgentBusInfoFreeze(agentVo.getAgent().getId());
            List<String> businfoList = new LinkedList<>();
            for (AgentBusInfo busInfo : agentBusInfos) {
                businfoList.add(busInfo.getId());
            }
            AgentVo preAgentVo = JSONObject.parseObject(dateChangeRequest.getDataPreContent(), AgentVo.class);
            List<AgentColinfoVo> preColinfoVoList = preAgentVo.getColinfoVoList();
            if (preColinfoVoList.size() != 0 && preColinfoVoList != null) {
                AgentColinfoVo preAgentColinfoVo = preColinfoVoList.get(0); // 变更前
                AgentColinfoVo agentColinfoVo = colinfoVoList.get(0); // 变更后
                AgentResult agentVerify = verifyColinfoIsChange(agentColinfoVo, preAgentColinfoVo);
                if (agentVerify.isOK()) {
                    // 调用冻结接口 (传参：AG, 冻结原因, 冻结人, 数据ID, 冻结业务)
                    agentFreezeResult(agentVo.getAgent().getId(),
                            FreeCause.JSKBG.getValue(),
                            String.valueOf(FreePerson.XTDJ.getValue()),
                            dateChangeRequest.getId(),
                            businfoList);
                }
            } else {
                // 调用冻结接口(preColinfoVoList为空，代理商变更前没有结算卡信息，也需调用冻结接口)
                agentFreezeResult(agentVo.getAgent().getId(),
                        FreeCause.JSKBG.getValue(),
                        String.valueOf(FreePerson.XTDJ.getValue()),
                        dateChangeRequest.getId(),
                        businfoList);
            }
        }

        if(null!=agentVo.getBusInfoVoList() && agentVo.getBusInfoVoList().size()>0){
            for (AgentBusInfoVo abus : agentVo.getBusInfoVoList()) {
                //检查业务平台数据
                PlatForm platForm = platFormMapper.selectByPlatFormNum(abus.getBusPlatform());
                if(platForm==null){
                    throw new MessageException("业务平台不存在");
                }

                PlatformType platformType = platFormService.byPlatformCode(abus.getBusPlatform());
                if(PlatformType.RDBPOS.code.equals(platformType.getValue())){
                    //检查手机号是否填写
                    if(StringUtils.isBlank(abus.getBusLoginNum())){
                        throw new MessageException("瑞大宝平台登录账号不能为空");
                    }
                    if(!RegexUtil.checkInt(abus.getBusLoginNum())){
                        throw new MessageException("瑞大宝平台登录账号必须为数字");
                    }
                    if(abus.getBusLoginNum().length()!=11){
                        throw new MessageException("手机位数不正确");
                    }
                }
                if(PlatformType.RHPOS.code.equals(platformType.getValue())){
                    //检查手机号是否填写
                    if(StringUtils.isBlank(abus.getBusLoginNum())){
                        throw new MessageException("瑞花宝平台登录账号不能为空");
                    }
                    if(!RegexUtil.checkInt(abus.getBusLoginNum())){
                        throw new MessageException("瑞花宝平台登录账号必须是数字");
                    }
                    if(abus.getBusLoginNum().length()!=11){
                        throw new MessageException("手机位数不正确");
                    }
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
        String workId;
        if(dateChangeRequest.getDataType().equals(BusActRelBusType.DC_Agent.name())){
            workId = dictOptionsService.getApproveVersion("business");
        }else if(dateChangeRequest.getDataType().equals(BusActRelBusType.DC_Colinfo.name())){
            workId = dictOptionsService.getApproveVersion("finance");
        }else{
            throw new MessageException("请求类型错误");
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
        if(startPar.get("party").toString().equals("beijing")){
            startPar.put("rs",ApprovalType.PASS.getValue());
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
        if(dateChangeRequest.getDataType().equals(BusActRelBusType.DC_Agent.name())){
            record.setDataShiro(BusActRelBusType.DC_Agent.key);
        }else if(dateChangeRequest.getDataType().equals(BusActRelBusType.DC_Colinfo.name())){
            record.setDataShiro(BusActRelBusType.DC_Colinfo.key);
        }else{
            record.setDataShiro(BusActRelBusType.DC_Agent.key);
        }
        Agent agent = agentMapper.selectByPrimaryKey(dateChangeRequest.getDataId());
        if(agent!=null)
            record.setAgentName(agent.getAgName());
        if(dateChangeRequest.getDataType().equals(BusActRelBusType.DC_Agent.name())){
            AgentVo agVo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
            if(agVo.getBusInfoVoList().size()==0){
                throw  new MessageException("缺少业务信息");
            }
            AgentBusInfoVo agentBusInfoVo = agVo.getBusInfoVoList().get(0);
            PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfoVo.getBusPlatform());
            record.setNetInBusType("ACTIVITY_"+platForm.getPlatformNum());
            record.setAgDocDistrict(agentBusInfoVo.getAgDocDistrict());
            record.setAgDocPro(agentBusInfoVo.getAgDocPro());
            if (StringUtils.isNotBlank(agentBusInfoVo.getBusNum())){
                record.setExplain(agentBusInfoVo.getBusNum());
            }
        }else{
            List<Map<String, Object>> maps = iUserService.orgCode(Long.valueOf(userId));
            if(maps!=null && maps.size()>0){
                Map<String, Object> stringObjectMap = maps.get(0);
                record.setAgDocPro(stringObjectMap.get("ORGID")+"");
                if(null!=stringObjectMap.get("isRegion") && (Boolean)stringObjectMap.get("isRegion")) {
                    record.setAgDocDistrict(stringObjectMap.get("ORGPID") + "");
                }else if(null!=stringObjectMap.get("ppidorgcodeisRegion") && (Boolean)stringObjectMap.get("ppidorgcodeisRegion")) {
                    record.setAgDocDistrict(stringObjectMap.get("ORGPPID") + "");
                }
            }else{
                throw new MessageException("未获取到部门编号!");
            }
        }
        if(1!=busActRelMapper.insertSelective(record)){
            logger.info("代理商审批，启动审批异常，添加审批关系失败{}:{}",dateChangeRequest.getId(),proce);
            throw  new MessageException("添加审批关系失败");
        }
        return ResultVO.success(null);
    }

    // 省区/代理商修改申请-调用冻结接口
    void agentFreezeResult(String agentId, String freezeCause, String freezePerson, String freezeNum, List<String> busPlatform) {
        try {
            AgentFreezePort agentFreezePort = new AgentFreezePort();
            agentFreezePort.setAgentId(agentId);
            agentFreezePort.setFreezeCause(freezeCause);
            agentFreezePort.setOperationPerson(freezePerson);
            agentFreezePort.setFreezeNum(freezeNum);
            agentFreezePort.setFreeType(Arrays.asList(FreeType.AGNET.code));
            agentFreezePort.setBusPlatform(busPlatform);
            agentFreezePort.setNewBusFreeze(String.valueOf(BigDecimal.ZERO));
            AgentResult agentResult = agentFreezeService.agentFreeze(agentFreezePort);
            if (!agentResult.isOK()) {
                logger.info("代理商{},冻结失败:{}", agentId, agentResult.getMsg());
                throw new MessageException(agentResult.getMsg());
            }
            logger.info("代理商{},冻结参数:{}", JSONObject.toJSON(agentFreezePort));
        } catch (MessageException e) {
            logger.info("代理商{},调用冻结接口失败:{}", agentId, e.getMsg());
            e.printStackTrace();
        }
    }

    // 省区/代理商修改申请-调用解冻接口
    void agentUnFreezeResult(String agentId, String UnfreezeCause, String freezeCause, String freezePerson) {
        try {
            AgentFreezePort agentFreezePort = new AgentFreezePort();
            agentFreezePort.setAgentId(agentId);
            agentFreezePort.setUnfreezeCause(UnfreezeCause);
            agentFreezePort.setFreezeCause(freezeCause);
            agentFreezePort.setOperationPerson(freezePerson);
            agentFreezePort.setFreeType(Arrays.asList(FreeType.AGNET.code));
            AgentResult agentResult = agentFreezeService.agentUnFreeze(agentFreezePort);
            if (!agentResult.isOK()) {
                logger.info("代理商{},解冻失败:{}", agentId, agentResult.getMsg());
                throw new ProcessException(agentResult.getMsg());
            }
            logger.info("代理商{},解冻参数:{}", JSONObject.toJSON(agentFreezePort));
        } catch (MessageException e) {
            logger.info("代理商{},调用解冻接口失败:{}", agentId, e.getMsg());
            e.printStackTrace();
        }
    }


    /**
     * 代理商启动结算卡变更审批服务
     * @param dataChangeId
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultVO agentStartDataChangeActivity(String dataChangeId, String userId) throws Exception {
        logger.info("========用户{}启动数据修改申请{}", userId, dataChangeId);
        DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(dataChangeId);

        // 查询代理商是否重复发起，状态为审批中的，不能重复提交
        DateChangeRequestExample dateChangeRequestExample = new DateChangeRequestExample();
        DateChangeRequestExample.Criteria criteria = dateChangeRequestExample.createCriteria();
        criteria.andDataIdEqualTo(dateChangeRequest.getDataId());
        criteria.andDataTypeEqualTo(BusActRelBusType.DC_AG_Colinfo.name());
        criteria.andAppyStatusEqualTo(AgStatus.Approving.getValue());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        // 查询省区是否发起数据，状态为审批中的，不能重复提交
        dateChangeRequestExample.or()
                .andDataIdEqualTo(dateChangeRequest.getDataId())
                .andDataTypeEqualTo(BusActRelBusType.DC_Colinfo.name())
                .andAppyStatusEqualTo(AgStatus.Approving.getValue());
        List<DateChangeRequest> dateChangeRequests = dateChangeRequestMapper.selectByExample(dateChangeRequestExample);
        if (null == dateChangeRequests) {
            logger.info("数据修改申请:{}", "查询审批状态失败");
            throw new MessageException("查询审批状态失败！");
        }
        if (dateChangeRequests.size() > 0) {
            logger.info("数据修改申请:{}", "数据已申请，审批中请勿重复提交");
            throw new MessageException("数据已申请，审批中请勿重复提交！");
        }
        AgentVo agentVo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
        if (null == agentVo) {
            logger.info("数据修改申请:{}", "转换异常");
            throw new MessageException("修改申请提交审批失败！");
        }
        List<AgentColinfoVo> colinfoVoList = agentVo.getColinfoVoList();
        if (colinfoVoList != null) {
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

        // 代理商-基础信息变更申请，提交审批时，需校验结算卡字段是否变更，变更则需冻结代理商状态且生成冻结记录
        List<AgentBusInfo> agentBusInfos = agentBusinfoService.queryAgentBusInfoFreeze(agentVo.getAgent().getId());
        List<String> businfoList = new LinkedList<>();
        for (AgentBusInfo busInfo : agentBusInfos) {
            businfoList.add(busInfo.getId());
        }
        AgentVo preAgentVo = JSONObject.parseObject(dateChangeRequest.getDataPreContent(), AgentVo.class);
        List<AgentColinfoVo> preColinfoVoList = preAgentVo.getColinfoVoList();
        if (preColinfoVoList.size() != 0 && preColinfoVoList != null) {
            AgentColinfoVo preAgentColinfoVo = preColinfoVoList.get(0); // 变更前
            AgentColinfoVo agentColinfoVo = colinfoVoList.get(0); // 变更后
            AgentResult agentVerify = verifyColinfoIsChange(agentColinfoVo, preAgentColinfoVo);
            if (agentVerify.isOK()) {
                // 调用冻结接口 (传参：AG, 冻结原因, 冻结人, 数据ID, 冻结业务)
                agentFreezeResult(agentVo.getAgent().getId(),
                        FreeCause.JSKBG.getValue(),
                        String.valueOf(FreePerson.XTDJ.getValue()),
                        dateChangeRequest.getId(),
                        businfoList);
            }
        } else {
            // 调用冻结接口(preColinfoVoList为空，代理商变更前没有结算卡信息，也需调用冻结接口)
            agentFreezeResult(agentVo.getAgent().getId(),
                    FreeCause.JSKBG.getValue(),
                    String.valueOf(FreePerson.XTDJ.getValue()),
                    dateChangeRequest.getId(),
                    businfoList);
        }

        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(dateChangeRequest.getId()).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() > 0) {
            logger.info("========用户{}启动数据修改申请{}{}", dataChangeId, userId, "申请进行中，禁止重复提交");
            return ResultVO.fail("申请进行中，禁止重复提交");
        }

        //基础信息变更申请-启动流程
        List<String> stringType = new ArrayList<String>();
        List<String> stringPro = new ArrayList<String>();
        List<String> stringDis = new ArrayList<String>();
        String stringJump = null;
        List<Dict> dictList_one = dictOptionsService.dictList(DictGroup.DATA_CHANGE.name(), DictGroup.MARKET_ONE.name());
        List<Dict> dictList_two = dictOptionsService.dictList(DictGroup.DATA_CHANGE.name(), DictGroup.MARKET_TWO.name());
        List<AgentBusInfo> agentBusInfoList = agentBusinfoService.queryAgentBusInfo(dateChangeRequest.getDataId());
        for (AgentBusInfo agentBusInfo : agentBusInfoList) {
            PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
            if (platForm.getPlatformType().equals("RJPOS") || platForm.getPlatformType().equals("RJQZ")) {
                stringType.add(dictList_two.get(0).getdItemvalue());
            } else {
                stringType.add(dictList_one.get(0).getdItemvalue());
            }
            COrganization org_pro = departmentService.getById(agentBusInfo.getAgDocPro());
            stringPro.add(org_pro.getCode());
            COrganization org_dis = departmentService.getById(agentBusInfo.getAgDocDistrict());
            stringDis.add(org_dis.getCode());
        }
        ArrayList<String> arrayType = new ArrayList<>(new HashSet<>(stringType));
        logger.info("市场部参数:{}" + JSONObject.toJSONString(arrayType));
        if (arrayType.size()==0 || arrayType==null) {
            logger.info("代理商启动审批异常，未获取到市场部审批参数{}", JSONObject.toJSON(agentBusInfoList));
            throw new MessageException("代理商启动审批异常，未获取到市场部审批参数");
        }
        ArrayList<String> arrayPro = new ArrayList<>(new HashSet<>(stringPro));
        logger.info("省区参数:{}" + JSONObject.toJSONString(arrayPro));
        if (arrayPro.size()==0 || arrayPro==null) {
            logger.info("代理商启动审批异常，未获取到省区审批参数{}", JSONObject.toJSON(agentBusInfoList));
            throw new MessageException("代理商启动审批异常，未获取到省区审批参数");
        }
        ArrayList<String> arrayDis = new ArrayList<>(new HashSet<>(stringDis));
        logger.info("去除'beijing'前的大区参数:{}" + JSONObject.toJSONString(arrayDis));
        if (arrayDis.size()==0 || arrayDis==null) {
            logger.info("代理商启动审批异常，未获取到大区审批参数{}", JSONObject.toJSON(agentBusInfoList));
            throw new MessageException("代理商启动审批异常，未获取到大区审批参数");
        } else {
            // arrayDis!=0 || arrayDis!=null, 去除"beijing"参数, 再次判断arrayDis是否有值, 有则传参"no"不跳过大区, 无则传参"yes"跳过大区
            arrayDis.remove("beijing");
        }
        logger.info("去除'beijing'后的大区参数:{}" + JSONObject.toJSONString(arrayDis));
        if (arrayDis.size()==0 || arrayDis==null) {
            stringJump = "yes";
        } else {
            stringJump = "no";
        }

        FastMap fastMap_dept = FastMap.fastMap("makUserList", arrayType) // 市场部审批人参数
                .putKeyV("proList", arrayPro) // 省区审批人部门代码
                .putKeyV("docList", arrayDis) // 大区审批人部门代码
                .putKeyV("jumpDoc", stringJump) // 大区是否跳过
                .putKeyV("rejectCount","0"); // 拒绝数量，初始值0，有拒绝就置为1
        String workId = dictOptionsService.getApproveVersion("financeAgent");
        dateChangeRequest.setAppyStatus(AgStatus.Approving.status);
        int updateDateChange = dateChangeRequestMapper.updateByPrimaryKeySelective(dateChangeRequest);
        if (1 != updateDateChange) {
            logger.info("代理商审批，启动审批异常，更新记录状态{}:{}", dateChangeRequest.getId(), userId);
            throw new MessageException("更新记录状态异常");
        }
        if (StringUtils.isEmpty(workId)) {
            logger.info("========用户{}启动数据修改申请{}{}", dataChangeId, userId, "审批流启动失败字典中未配置部署流程");
            throw new MessageException("审批流启动失败字典中未配置部署流程!");
        }

        String proce = activityService.createDeloyFlow(null, workId,null,null, fastMap_dept);
        if (proce == null) {
            logger.info("========用户{}启动数据修改申请{}{}", dataChangeId, userId, "数据修改审批，审批流启动失败");
            logger.info("数据修改审批，审批流启动失败{}:{}", dataChangeId, userId);
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
        record.setDataShiro(BusActRelBusType.DC_AG_Colinfo.key);
        Agent agent = agentMapper.selectByPrimaryKey(dateChangeRequest.getDataId());
        if (agent != null) {
            record.setAgentName(agent.getAgName());
        }
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("代理商审批，启动审批异常，添加审批关系失败{}:{}", dateChangeRequest.getId(), proce);
            throw  new MessageException("添加审批关系失败");
        }
        return ResultVO.success(null);
    }

    /**
     * 基础信息申请修改，校验结算卡是否变更
     * @param agentColinfoVo
     * @param agentColinfoPre
     * @return
     */
    public AgentResult verifyColinfoIsChange(AgentColinfoVo agentColinfoVo, AgentColinfoVo agentColinfoPre) {
        if (!agentColinfoVo.getCloType().equals(agentColinfoPre.getCloType())
                || !agentColinfoVo.getCloRealname().equals(agentColinfoPre.getCloRealname())
                || !agentColinfoVo.getCloBankAccount().equals(agentColinfoPre.getCloBankAccount())
                || !agentColinfoVo.getCloBank().equals(agentColinfoPre.getCloBank())
                || !agentColinfoVo.getBankRegion().equals(agentColinfoPre.getBankRegion())
                || !agentColinfoVo.getCloBankBranch().equals(agentColinfoPre.getCloBankBranch())
                || !agentColinfoVo.getAllLineNum().equals(agentColinfoPre.getAllLineNum())
                || !agentColinfoVo.getBranchLineNum().equals(agentColinfoPre.getBranchLineNum())
                || !agentColinfoVo.getCloTaxPoint().equals(agentColinfoPre.getCloTaxPoint())
                || !agentColinfoVo.getCloInvoice().equals(agentColinfoPre.getCloInvoice())
                || !agentColinfoVo.getAgLegalCernum().equals(agentColinfoPre.getAgLegalCernum())) {
            logger.info("结算卡信息变更AG码："+agentColinfoVo.getAgentId());
            return AgentResult.ok();
        }
        return AgentResult.fail();
    }

    /**
     * 收款账户修改 审批完成处理
     * @param proIns
     * @param agStatus
     * @return
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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
                    if(DataChangeApyType.DC_Colinfo.name().equals(dr.getDataType()) || DataChangeApyType.DC_AG_Colinfo.name().equals(dr.getDataType())) {
                        //更新入库
                        AgentVo vo = JSONObject.parseObject(dr.getDataContent(), AgentVo.class);
                        vo.getAgent().setcUser(rel.getcUser());     //直接新增收款账户时 此字段不可为空
                        List<AgentColinfoVo> colinfoVoList = vo.getColinfoVoList();
                        for (AgentColinfoVo agentColinfoVo : colinfoVoList) {
                            agentColinfoVo.setCloReviewStatus(AgStatus.Approved.status);
                        }
                        //代理商编号判断
                        if(StringUtils.isBlank(vo.getAgent().getId()))throw new ProcessException("代理商编号为空");
                        //将结算卡更新到数据库
                        ResultVO res = agentColinfoService.updateAgentColinfoVo(vo.getColinfoVoList(), vo.getAgent(),rel.getcUser(),null);
                        logger.info("========审批流完成{}业务{}状态{},结果{}", proIns, rel.getBusType(), agStatus, res.getResInfo());
                        //更新数据状态为审批成功
                        if(res.isSuccess()){
                            //结算卡审批状态更新为审批通过
                            List<AgentColinfo> query_colinfo = agentColinfoMapper.queryByAgentId(vo.getAgent().getId());
                            if (query_colinfo.size()!=1) {
                                logger.info("收款账户不唯一：{}{}", vo.getAgent().getId(),JSONObject.toJSON(query_colinfo));
                                throw new ProcessException("收款账户不唯一");
                            } else {
                                AgentColinfo agentColinfo = query_colinfo.get(0);
                                agentColinfo.setCloReviewStatus(AgStatus.Approved.status);
                                if (1 != agentColinfoMapper.updateByPrimaryKeySelective(agentColinfo)) {
                                    throw new ProcessException("更新收款账户审批状态失败");
                                }
                            }

                            //调整业务出款机构
                            ResultVO adjustFinOrg =  dataChangeActivityService.adjustFinanceOrgByAccount(vo.getAgent().getId());
                            if(!adjustFinOrg.isSuccess()){
                                throw new ProcessException("出款机构调整失败");
                            }
                            dr.setAppyStatus(AgStatus.Approved.status);
                            dr.setcUpdate(Calendar.getInstance().getTime());
                            logger.info("========审批流完成{}业务{}状态{},结果{}",proIns,rel.getBusType(),agStatus,"更新数据申请成功");
                            if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dr)){
                                throw new ProcessException("更新数据申请失败");
                            }

                            //首刷平台
                            PlatFormExample platFormExample = new PlatFormExample();
//                            List<String> list = new ArrayList<>();
//                            list.add(PlatformType.MPOS.code);
//                            list.add(PlatformType.RHPOS.code);
                            platFormExample.or().andStatusEqualTo(Status.STATUS_1.status);
//                            .andPlatformTypeIn(list);
                            List<PlatForm>  platForms = platFormMapper.selectByExample(platFormExample);
                            List<String> pltcode = new ArrayList<>();
                            pltcode.add("aaaa");
                            for (PlatForm platForm : platForms) {
                                pltcode.add(platForm.getPlatformNum());
                            }
                            //查询业务
                            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
                            agentBusInfoExample.or()
                                    .andStatusEqualTo(Status.STATUS_1.status)
                                    .andBusPlatformIn(pltcode)
                                    .andCloReviewStatusEqualTo(AgStatus.Approved.status)
                                    .andBusStatusIn(Arrays.asList(BusinessStatus.Enabled.status,BusinessStatus.inactive.status,BusinessStatus.lock.status,BusinessStatus.pause.status))
                                    .andAgentIdEqualTo(vo.getAgent().getId());
                            List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(agentBusInfoExample);

                            //是否需要同步至业务系统、进行一分钱验证
                            AgentVo preVo = JSONObject.parseObject(dr.getDataPreContent(), AgentVo.class);
                            //收款账户信息
                            List<AgentColinfoVo> voColinfoVoList = vo.getColinfoVoList();
                            List<AgentColinfoVo> preVoColinfoVoList = preVo.getColinfoVoList();
                            Agent voAgent = vo.getAgent();
                            logger.info("===============================更新代理商基础信息开始");
                            Agent db_agent = agentMapper.selectByPrimaryKey(voAgent.getId());
                            db_agent.setAgName(voAgent.getAgName());
                            db_agent.setAgNature(voAgent.getAgNature());
                            db_agent.setAgCapital(voAgent.getAgCapital());
                            db_agent.setAgBusLic(voAgent.getAgBusLic());
                            db_agent.setAgBusLicb(voAgent.getAgBusLicb());
                            db_agent.setAgBusLice(voAgent.getAgBusLice());
                            db_agent.setAgLegal(voAgent.getAgLegal());
                            db_agent.setAgLegalCertype(voAgent.getAgLegalCertype());
                            db_agent.setAgLegalCernum(voAgent.getAgLegalCernum());
                            db_agent.setAgLegalMobile(voAgent.getAgLegalMobile());
                            db_agent.setAgHead(voAgent.getAgHead());
                            db_agent.setAgHeadMobile(voAgent.getAgHeadMobile());
                            db_agent.setAgRegAdd(voAgent.getAgRegAdd());
                            db_agent.setAgBusScope(voAgent.getAgBusScope());
                            db_agent.setCloTaxPoint(voAgent.getCloTaxPoint());
                            db_agent.setAgDocPro(voAgent.getAgDocPro());
                            db_agent.setAgDocDistrict(voAgent.getAgDocDistrict());
                            db_agent.setAgRemark(voAgent.getAgRemark());
                            db_agent.setStatus(voAgent.getStatus());
                            db_agent.setAgRegArea(voAgent.getAgRegArea());
                            db_agent.setBusRiskEmail(voAgent.getBusRiskEmail());
                            db_agent.setBusContactEmail(voAgent.getBusContactEmail());
                            db_agent.setAgRunAdd(voAgent.getAgRunAdd());
                            db_agent.setAgLegalCerdate(voAgent.getAgLegalCerdate());
                            if (1 != agentMapper.updateByPrimaryKeySelective(db_agent)) {
                                throw new ProcessException("代理商信息更新失败");
                            }else{
                                //保存数据历史
                                if(!agentDataHistoryService.saveDataHistory(db_agent,db_agent.getId(), DataHistoryType.BASICS.code,rel.getcUser(),voAgent.getVersion()).isOK()){
                                    throw new ProcessException("代理商信息更新失败！请重试");
                                }
                            }
                            boolean isHaveYYZZ = false;
                            boolean isHaveFRSFZ = false;
                            //添加营业执照等附件
                            List<String> attrs = vo.getAgentTableFile();
                            if (attrs != null && attrs.size()>0) {
                                AttachmentRelExample att_example = new AttachmentRelExample();
                                att_example.or().andBusTypeEqualTo(AttachmentRelType.Agent.name()).andSrcIdEqualTo(db_agent.getId()).andStatusEqualTo(Status.STATUS_1.status);
                                List<AttachmentRel> list = attachmentRelMapper.selectByExample(att_example);
                                for (AttachmentRel attachmentRel : list) {
                                    attachmentRel.setStatus(Status.STATUS_0.status);
                                    int i = attachmentRelMapper.updateByPrimaryKeySelective(attachmentRel);
                                    if (1 != i) {
                                        logger.info("修改代理商附件关系失败");
                                        throw new ProcessException("更新修改代理商失败");
                                    }
                                }
                                for (String fileId : attrs) {
                                    Attachment attachment = attachmentMapper.selectByPrimaryKey(fileId);
                                    if(attachment!=null){
                                        if(AttDataTypeStatic.YYZZ.code.equals(attachment.getAttDataType()+"")){
                                            isHaveYYZZ = true;
                                        }
                                        if(AttDataTypeStatic.SFZZM.code.equals(attachment.getAttDataType()+"")){
                                            isHaveFRSFZ = true;
                                        }
                                    }
                                    AttachmentRel record = new AttachmentRel();
                                    record.setAttId(fileId);
                                    record.setSrcId(db_agent.getId());
                                    record.setcUser(voAgent.getcUser());
                                    record.setcTime(Calendar.getInstance().getTime());
                                    record.setStatus(Status.STATUS_1.status);
                                    record.setBusType(AttachmentRelType.Agent.name());
                                    record.setId(idService.genId(TabId.a_attachment_rel));
                                    int i = attachmentRelMapper.insertSelective(record);
                                    if (1 != i) {
                                        logger.info("修改代理商附件关系失败");
                                        throw new ProcessException("更新修改代理商失败");
                                    }
                                }
                            }
                            logger.info("===============================更新代理商基础信息成功");
                            logger.info("===============================更新合同信息开始");
                            ResultVO updateAgentContractVoRes = agentContractService.updateAgentContractVo(vo.getContractVoList(), vo.getAgent(),vo.getAgent().getcUser());
                            logger.info("===============================更新合同信息结束");

                            Agent preVoAgent = preVo.getAgent();
                            if (voColinfoVoList.size()>0){
                                if (voColinfoVoList.size() != preVoColinfoVoList.size()){       //新增收款账户
                                    //一分钱验证、同步至业务系统
                                    logger.info("========================一分钱验证状态修改开始");
                                    for (AgentColinfoVo agentColinfo:voColinfoVoList){
                                        agentColinfo.setPayStatus(ColinfoPayStatus.A.getValue());
                                    }
                                    agentColinfoService.updateAgentColinfoVo(voColinfoVoList, vo.getAgent(),rel.getcUser(),null);
                                    logger.info("========================一分钱验证状态修改完成");
                                    //建立收款账户和平台码的关系
                                    AgentColinfo agentColinfoVo=voColinfoVoList.get(0);
                                    for (AgentBusInfo agentBusInfo : agentBusInfoList) {        //为业务平台建立结算卡关系
                                        AgentColinfoRel agentColinfoRel = new AgentColinfoRel();
                                        agentColinfoRel.setcUse(rel.getcUser());
                                        agentColinfoRel.setAgentid(voAgent.getId());
                                        agentColinfoRel.setAgentColinfoid(agentColinfoVo.getId());
                                        agentColinfoRel.setBusPlatform(agentBusInfo.getBusPlatform());
                                        agentColinfoRel.setAgentbusid(agentBusInfo.getId());

                                        agentColinfoService.saveAgentColinfoRel(agentColinfoRel, rel.getcUser());
                                    }
                                    logger.info("========================同步至业务系统开始");
                                    for (AgentBusInfo agentBusInfo : agentBusInfoList) {
                                        agentNetInNotityService.asynNotifyPlatform(agentBusInfo.getId(),NotifyType.NetInEdit.getValue());
                                    }
                                    logger.info("========================同步至业务系统完成");
                                }else{
                                    boolean synTemp=true;
                                    boolean checkTemp=true;
                                    for (AgentColinfoVo newColinfo:voColinfoVoList){
                                        for (AgentColinfoVo oldColinfo:preVoColinfoVoList){
                                            if (newColinfo.getId().equals(oldColinfo.getId())){
                                                checkTemp = checkNewAccount(newColinfo,oldColinfo);
                                                synTemp=isMustSyn(newColinfo,oldColinfo,voAgent,preVoAgent);
                                            }
                                        }
                                        if (!checkTemp){
                                            //一分钱验证、同步至业务系统
                                            logger.info("========================一分钱验证状态修改开始");
                                            for (AgentColinfoVo agentColinfo:voColinfoVoList){
                                                agentColinfo.setPayStatus(ColinfoPayStatus.A.getValue());
                                            }
                                            agentColinfoService.updateAgentColinfoVo(voColinfoVoList, vo.getAgent(),rel.getcUser(),null);
                                            logger.info("========================一分钱验证状态修改完成");

                                            logger.info("========================修复业务结算卡关系");
                                            AgentColinfo agentColinfo_db = agentColinfoService.selectByAgentId(vo.getAgent().getId());
                                            if(agentColinfo_db!=null && StringUtils.isNotBlank(agentColinfo_db.getId())){
                                                for (AgentBusInfo agentBusInfo : agentBusInfoList) {
                                                    AgentColinfoRel agentColinfoRel = new AgentColinfoRel();
                                                    agentColinfoRel.setcUse(rel.getcUser());
                                                    agentColinfoRel.setAgentid(voAgent.getId());
                                                    agentColinfoRel.setAgentColinfoid(agentColinfo_db.getId());
                                                    agentColinfoRel.setBusPlatform(agentBusInfo.getBusPlatform());
                                                    agentColinfoRel.setAgentbusid(agentBusInfo.getId());
                                                    agentColinfoService.saveAgentColinfoRel(agentColinfoRel, rel.getcUser());
                                                }
                                            }
                                            logger.info("========================修复业务结算卡关系");

                                            logger.info("========================同步至业务系统开始");
                                            for (AgentBusInfo agentBusInfo : agentBusInfoList) {
                                                agentNetInNotityService.asynNotifyPlatform(agentBusInfo.getId(),NotifyType.NetInEdit.getValue());
                                            }
                                            logger.info("========================同步至业务系统完成");
                                            break;
                                        }else if (!synTemp){//同步至业务系统
                                            logger.info("========================修复业务结算卡关系");
                                            AgentColinfo agentColinfo_db = agentColinfoService.selectByAgentId(vo.getAgent().getId());
                                            if(agentColinfo_db!=null && StringUtils.isNotBlank(agentColinfo_db.getId())){
                                                for (AgentBusInfo agentBusInfo : agentBusInfoList) {
                                                    AgentColinfoRel agentColinfoRel = new AgentColinfoRel();
                                                    agentColinfoRel.setcUse(rel.getcUser());
                                                    agentColinfoRel.setAgentid(voAgent.getId());
                                                    agentColinfoRel.setAgentColinfoid(agentColinfo_db.getId());
                                                    agentColinfoRel.setBusPlatform(agentBusInfo.getBusPlatform());
                                                    agentColinfoRel.setAgentbusid(agentBusInfo.getId());
                                                    agentColinfoService.saveAgentColinfoRel(agentColinfoRel, rel.getcUser());
                                                }
                                            }
                                            logger.info("========================修复业务结算卡关系");
                                            for (AgentBusInfo agentBusInfo : agentBusInfoList) {
                                                agentNetInNotityService.asynNotifyPlatform(agentBusInfo.getId(),NotifyType.NetInEdit.getValue());
                                            }
                                            break;
                                        }
                                    }
                                }
                            }

                            AgentColinfo agentColinfo_afterChange = agentColinfoService.selectByAgentId(vo.getAgent().getId());
                            try {
                                logger.info("===================开始执行kafka消息分发");
                                agentKafkaService.sendPayMentMessage(vo.getAgent().getId(),
                                        vo.getAgent().getAgName(),
                                        "", "",
                                        KafkaMessageType.CARD,
                                        KafkaMessageTopic.CardChange.code,
                                        JSONObject.toJSONString(agentColinfo_afterChange)
                                );
                                logger.info("===================结束kafka消息分发");
                            } catch (Exception e) {
                                logger.info("kafka接口调用失败,AG码", vo.getAgent().getId());
                                e.printStackTrace();
                            }
                        }

                        // 省区/代理商-结算卡申请，审批通过后，自动解冻(冻结原因为"基本信息缺失","结算卡变更冻结","认证冻结"的数据，如代理商没有其他冻结数据，则需要更新解冻代理商数据的冻结状态)
                        // 调用解冻接口 (传参：AG, 解冻原因, 冻结原因, 解冻人, 冻结业务)
                        String data_id = dr.getId();
                        String agent_id = vo.getAgent().getId();
                        String freeze_cause_XXQS = FreeCause.XXQS.getValue();
                        String freeze_cause_RZDJ = FreeCause.RZDJ.getValue();
                        BigDecimal freeze_type = FreeType.AGNET.code;
                        if (StringUtils.isNotBlank(agent_id)) {
                            // 查询冻结数据是否是结算卡冻结变更申请(是:JSKBG,否:XXQS)(mapData为空:JSKBG)
                            AgentResult resultQuery = agentFreezeService.queryAgentIdByFreezeNum(agent_id, data_id);
                            Map<String, Object> mapData = resultQuery.getMapData();
                            if (mapData != null) {
                                AgentFreeze freeze_query = (AgentFreeze) mapData.get("agentFreeze");
                                Agent agent = vo.getAgent();
                                AgentColinfoVo agentColinfoVo = vo.getColinfoVoList().get(0);
                                if (agent!=null && agentColinfoVo!=null) {
                                    Map map = agentFreezeService.approvedVerify(agent, agentColinfoVo).get(0);
                                    if (StringUtils.isBlank(String.valueOf(map.get("str_remark")))) {
                                        AgentResult resultCheck_XXQS = agentFreezeService.checkAgentFreezeExists(agent_id, freeze_cause_XXQS, freeze_type);
                                        if (resultCheck_XXQS.isOK()) {
                                            Map<String, Object> checkMapData_XXQS = resultCheck_XXQS.getMapData();
                                            String ag_id = (String) checkMapData_XXQS.get("agentId");
                                            agentUnFreezeResult(ag_id,
                                                    UnfreeCause.XTJD.getValue(),
                                                    FreeCause.XXQS.getValue(),
                                                    String.valueOf(UnfreePerson.XTJD.getValue()));
                                        }
                                    }
                                }
                                AgentResult resultCheck_JSKBG = agentFreezeService.checkAgentFreezeExists(agent_id, freeze_query.getFreezeCause(), freeze_type);
                                if (resultCheck_JSKBG.isOK()) {
                                    Map<String, Object> checkMapData = resultCheck_JSKBG.getMapData();
                                    String ag_id = (String) checkMapData.get("agentId");
                                    agentUnFreezeResult(ag_id,
                                            UnfreeCause.XTJD.getValue(),
                                            FreeCause.JSKBG.getValue(),
                                            String.valueOf(UnfreePerson.XTJD.getValue()));
                                }
                                AgentResult resultCheck_RZDJ = agentFreezeService.checkAgentFreezeExists(agent_id, freeze_cause_RZDJ, freeze_type);
                                if (resultCheck_RZDJ.isOK()) {
                                    Map<String, Object> checkMapData = resultCheck_RZDJ.getMapData();
                                    String ag_id = (String) checkMapData.get("agentId");
                                    agentUnFreezeResult(ag_id,
                                            UnfreeCause.XTJD.getValue(),
                                            FreeCause.RZDJ.getValue(),
                                            String.valueOf(UnfreePerson.XTJD.getValue()));
                                }
                            } else {
                                AgentResult resultCheck_XXQS_TWO = agentFreezeService.checkAgentFreezeExists(agent_id, freeze_cause_XXQS, freeze_type);
                                if (resultCheck_XXQS_TWO.isOK()) {
                                    Map<String, Object> checkMapData = resultCheck_XXQS_TWO.getMapData();
                                    String ag_id = (String) checkMapData.get("agentId");
                                    agentUnFreezeResult(ag_id,
                                            UnfreeCause.XTJD.getValue(),
                                            FreeCause.XXQS.getValue(),
                                            String.valueOf(UnfreePerson.XTJD.getValue()));
                                }
                                AgentResult resultCheck_RZDJ_TWO = agentFreezeService.checkAgentFreezeExists(agent_id, freeze_cause_RZDJ, freeze_type);
                                if (resultCheck_RZDJ_TWO.isOK()) {
                                    Map<String, Object> checkMapData = resultCheck_RZDJ_TWO.getMapData();
                                    String ag_id = (String) checkMapData.get("agentId");
                                    agentUnFreezeResult(ag_id,
                                            UnfreeCause.XTJD.getValue(),
                                            FreeCause.RZDJ.getValue(),
                                            String.valueOf(UnfreePerson.XTJD.getValue()));
                                }
                            }
                        }
                        //代理商新修改
                    }else if(DataChangeApyType.DC_Agent.name().equals(dr.getDataType())){
                        //更新入库
                        AgentVo vo = JSONObject.parseObject(dr.getDataContent(), AgentVo.class);
                        AgentVo preVo = JSONObject.parseObject(dr.getDataPreContent(), AgentVo.class);
                        List<CapitalVo> capitalVoList = vo.getCapitalVoList();
                        if(null!=capitalVoList && capitalVoList.size()>0){
                            for (CapitalVo capitalVo : capitalVoList) {
                                capitalVo.setcAgentId(vo.getAgent().getId());
                                capitalVo.setcUser(rel.getcUser());
                                capitalVo.setSrcId(dr.getId());
                                capitalVo.setSrcRemark("代理商信息修改");
                            }
                        }
                        //更新财务出款机构
                        List<AgentBusInfoVo> orgTypeList = vo.getOrgTypeList();
                        for (AgentBusInfoVo agentBusInfoVo : orgTypeList) {
                            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                            agentBusInfo.setFinaceRemitOrgan(agentBusInfoVo.getFinaceRemitOrgan());
                            int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                            if ( i != 1) {
                                throw new ProcessException("更新财务出款机构失败");
                            }
                        }

                        //======================================更新业务信息
                        ResultVO res =  agentBusinfoService.updateBussiness(vo.getBusInfoVoList(),rel.getcUser());
                        //======================================更新费率信息
                        for (AgentBusInfoVo agentBusInfoVo : vo.getEditDebitList()) {
                            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                            agentBusInfoVo.setId(agentBusInfoVo.getId());
                            agentBusInfoVo.setVersion(agentBusInfo.getVersion());
                            agentBusInfoVo.setcUtime(new Date());
                            int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfoVo);
                            if(i!=1){
                                throw new ProcessException("更新借记费率等信息失败");
                            }
                        }

                        logger.info("========审批流完成{}业务{}状态{},结果{}", proIns, rel.getBusType(), agStatus, res.getResInfo());
                        //更新数据状态为审批成功
                        if(res.isSuccess()){
                            dr.setAppyStatus(AgStatus.Approved.status);
                            dr.setcUpdate(Calendar.getInstance().getTime());
                            logger.info("========审批流完成{}业务{}状态{},结果{}",proIns,rel.getBusType(),agStatus,"更新数据申请成功");
                            if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dr)){
                                throw new ProcessException("更新数据申请失败");
                            }

                        }

                        //入网程序调用
//                        try {
                        if(vo.getBusInfoVoList()!=null){
                            for (AgentBusInfoVo agentBusInfoVo : vo.getBusInfoVoList()) {
//                                    if(StringUtils.isNotBlank(agentBusInfoVo.getId())) {
//                                        ImportAgent importAgent = new ImportAgent();
//                                        importAgent.setDataid(agentBusInfoVo.getId());
//                                        importAgent.setDatatype(AgImportType.DATACHANGEAPP.name());
//                                        importAgent.setBatchcode(proIns);
//                                        importAgent.setcUser(rel.getcUser());
//                                        if (1 != aimportService.insertAgentImportData(importAgent)) {
//                                            logger.info("代理商修改审批通过-添加开户任务失败");
//                                        } else {
//                                            logger.info("代理商修改审批通过-添加开户任务成功!{},{}", AgImportType.DATACHANGEAPP.getValue(), vo.getAgent().getId());
//                                        }
//                                    }
                                agentNetInNotityService.asynNotifyPlatform(agentBusInfoVo.getId(),NotifyType.NetInEdit.getValue());
                            }
                        }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            throw new ProcessException("更新数据申请失败");
//                        } finally {
//                            agentNotifyService.asynNotifyPlatform();
//                        }
                    }
                    //拒绝更新数据状态
                }else if(AgStatus.Refuse.name().equals(agStatus)){
                    dr.setAppyStatus(AgStatus.Refuse.status);
                    dr.setcUpdate(Calendar.getInstance().getTime());
                    logger.info("========审批流完成{}业务{}状态{},结果{}",proIns,rel.getBusType(),agStatus,"更新数据申请失败");
                    if(1!=dateChangeRequestMapper.updateByPrimaryKeySelective(dr)){
                        throw new ProcessException("更新数据申请失败");
                    }
                    if (DataChangeApyType.DC_Colinfo.name().equals(dr.getDataType()) || DataChangeApyType.DC_AG_Colinfo.name().equals(dr.getDataType())) {
                        // 结算卡申请，审批拒绝后，自动解冻(冻结原因为"结算卡变更冻结"的数据，如代理商没有其他冻结数据，则需要更新解冻代理商数据的冻结状态)
                        // 调用解冻接口 (传参：AG, 解冻原因, 冻结原因, 解冻人, 冻结业务)
                        AgentVo vo = JSONObject.parseObject(dr.getDataContent(), AgentVo.class);
                        String agent_id = vo.getAgent().getId();
                        String freeze_cause_JSKBG = FreeCause.JSKBG.getValue();
                        BigDecimal freeze_type = FreeType.AGNET.code;
                        if (StringUtils.isNotBlank(agent_id)) {
                            AgentResult resultCheck = agentFreezeService.checkAgentFreezeExists(agent_id, freeze_cause_JSKBG, freeze_type);
                            if (resultCheck.isOK()) {
                                Map<String, Object> checkMapData = resultCheck.getMapData();
                                String ag_id = (String) checkMapData.get("agentId");
                                agentUnFreezeResult(ag_id,
                                        UnfreeCause.XTJD.getValue(),
                                        FreeCause.JSKBG.getValue(),
                                        String.valueOf(UnfreePerson.XTJD.getValue()));
                            }
                          /*  //更改修改状态
                            AgentColinfoExample agentColinfoExample = new AgentColinfoExample();
                            AgentColinfoExample.Criteria criteria1 = agentColinfoExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status)
                                    .andCloReviewStatusEqualTo(AgStatus.Approved.status)
                                    .andAgentIdEqualTo(agent_id);
                            List<AgentColinfo> agentColinfoList = agentColinfoMapper.selectByExample(agentColinfoExample);
                            if(null!=agentColinfoList || agentColinfoList.size()>0) {
                                AgentColinfo agentColinfo = agentColinfoList.get(0);
                                agentColinfo.setAmendStatus(AmendStatus.DXG.status);
                                if (1 != agentColinfoMapper.updateByPrimaryKeySelective(agentColinfo)) {
                                    logger.info("数据修改申请:{}", "修改状态修改失败");
                                    throw new ProcessException("数据修改申请,修改状态修改失败！");
                                }
                            }*/
                        }
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
     * 一分钱验证判断
     */
    public boolean checkNewAccount(AgentColinfoVo newColinfo,AgentColinfoVo oldColinfo){
        return (newColinfo.getCloRealname().equals(oldColinfo.getCloRealname())) &&    //收款账户名
                (newColinfo.getCloBankAccount().equals(oldColinfo.getCloBankAccount())) &&  //收款账号
                (newColinfo.getCloBankCode().equals(oldColinfo.getCloBankCode())) &&    //收款开户总行
                (newColinfo.getBankRegion().equals(oldColinfo.getBankRegion())) &&  //开户行地区
                (newColinfo.getCloBankBranch().equals(oldColinfo.getCloBankBranch())) &&    //收款开户支行
                (newColinfo.getAllLineNum().equals(oldColinfo.getAllLineNum())) &&  //总行联行号
                (newColinfo.getBranchLineNum().equals(oldColinfo.getBranchLineNum())) &&    //支行联行号
                (newColinfo.getAgLegalCernum().equals(oldColinfo.getAgLegalCernum()));  //户主证件号
    }

    /**
     * 通知业务系统判断
     *
     *
     */
    public boolean isMustSyn(AgentColinfoVo newColinfo,AgentColinfoVo oldColinfo,Agent agent,Agent preagent){
        return checkNewAccount(newColinfo,oldColinfo) && (newColinfo.getCloType().equals(oldColinfo.getCloType())) &&  //收款账户类型
                (newColinfo.getCloTaxPoint().equals(oldColinfo.getCloTaxPoint())) &&    //税点
                (newColinfo.getCloInvoice().equals(oldColinfo.getCloInvoice())) &&  //是否开具分润发票
                (newColinfo.getStatus().equals(oldColinfo.getStatus())) &&  //是否有
                agent.getAgName().equals(preagent.getAgName()) && //代理商名称
                agent.getAgBusLic().equals(preagent.getAgBusLic()) &&   //代理商营业执照
                agent.getAgLegalCernum().equals(preagent.getAgLegalCernum()) && //法人证件号
                agent.getAgLegal().equals(preagent.getAgLegal()) && //法人姓名
                agent.getAgRegArea().equals(preagent.getAgRegArea()) && // 注册地区
                agent.getAgRegAdd().equals(preagent.getAgRegAdd()); //注册地址
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTaskBusi(AgentVo agentVo, String userId) throws Exception {
        try {
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(userId));
            if(orgCodeRes==null && orgCodeRes.size()!=1){
                throw new ProcessException("部门参数为空");
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String orgCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
            DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(agentVo.getAgentBusId());
            if(null==dateChangeRequest){
                throw new ProcessException("数据错误");
            }
            //财务审批
            if(orgCode.equals("finance")){
    /*//                财务填写实际到账金额
                    for (CapitalVo  capitalVo:agentVo.getCapitalVoList()){
                        if (capitalVo.getcPayType().equals(PayType.YHHK.code)){
                            if (null==capitalVo.getcInAmount() || capitalVo.getcInAmount().equals("")){
                                logger.info("请填写实际到账金额");
                                throw new ProcessException("请填写实际到账金额");
                            }
                            if (StringUtils.isNotBlank(capitalVo.getTime())){
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date cPaytime = sdf.parse(capitalVo.getTime());
                                capitalVo.setcPaytime(cPaytime);
                            }
                            if(null!=capitalVo.getcInAmount()){
                                capitalVo.setcFqInAmount(capitalVo.getcInAmount());
                            }
                        }
                    }*/
                //数据修改
                if(dateChangeRequest.getDataType().equals(DataChangeApyType.DC_Agent.name())){
                    AgentVo vo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
                    if(StringUtils.isBlank(agentVo.getDebt()) || StringUtils.isBlank(agentVo.getOweTicket())){
                        throw new ProcessException("请填写欠票欠款信息,没有请填0");
                    }
                    vo.setDebt(agentVo.getDebt());
                    vo.setOweTicket(agentVo.getOweTicket());
                    if(orgCode.equals("finance")){
                        vo.setCapitalVoList(agentVo.getCapitalVoList());
                        //处理财务审批（财务出款机构）
                        vo.setOrgTypeList(agentVo.getOrgTypeList());
                        for (AgentBusInfoVo orgTypeList : agentVo.getOrgTypeList()) {
                            if (StringUtils.isBlank(orgTypeList.getFinaceRemitOrgan())) {
                                throw new ProcessException("请选择财务出款机构");
                            }
                            vo.setFinaceRemitOrgan(orgTypeList.getFinaceRemitOrgan());
                        }
                    }
                    String voJson = JSONObject.toJSONString(vo);
                    dateChangeRequest.setDataContent(voJson);
                    int i = dataChangeActivityService.updateByPrimaryKeySelective(dateChangeRequest);
                    if(i!=1){
                        throw new ProcessException("处理任务：更新失败");
                    }
                }
            }else if(orgCode.equals("business")){
                //数据修改
                if(dateChangeRequest.getDataType().equals(DataChangeApyType.DC_Agent.name())){
                    AgentVo vo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
                    logger.info("处理任务：更新失败 更新DataContent {} {}",orgCode,dateChangeRequest.getDataContent());
                    vo.setEditDebitList(agentVo.getEditDebitList());
                    String voJson = JSONObject.toJSONString(vo);
                    logger.info("处理任务：更新失败 更新AFTER DataContent {} {}",orgCode,voJson);
                    dateChangeRequest.setDataContent(voJson);
                    int i = dataChangeActivityService.updateByPrimaryKeySelective(dateChangeRequest);
                    if(i!=1){
                        throw new ProcessException("处理任务：更新失败");
                    }
                }
            }else if(orgCode.equals("market")){
                //业务平台除pro类型 都赋值空
                String ryx_pro = AppConfig.getProperty("ryx_pro");
                String ryx_pro1 = AppConfig.getProperty("ryx_pro1");
                if(null!=agentVo.getMarketToporgTableIdForm()&&agentVo.getMarketToporgTableIdForm().size()>0){
                    for (AgentBusInfoVo agentBusInfoVo : agentVo.getMarketToporgTableIdForm()) {
                        //上级机构和本级机构判断
                        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                        if(StringUtils.isNotBlank(agentBusInfoVo.getBusPlatform())){
                            if (!agentBusInfoVo.getBusPlatform().equals(ryx_pro) && !agentBusInfoVo.getBusPlatform().equals(ryx_pro1)){
                                agentBusInfoVo.setBusPlatform(" ");
                            }else{
                                //说明是pro类型的数据
                                if(!agentBusInfoVo.getBusPlatform().equals(agentBusInfo.getBusPlatform())){
                                    agentBusInfoVo.setAgentId(agentBusInfo.getAgentId());
                                    Boolean busPlatExist = findBusPlatExist(agentBusInfoVo);
                                    if (busPlatExist){
                                        throw new ProcessException("业务平台重复,请检查后再修改");
                                    }
                                }

                            }

                        }
                        //必须选择业务顶级机构
                        if(StringUtils.isBlank(agentBusInfoVo.getOrganNum())){
                            throw new ProcessException("请选择业务顶级机构");
                        }

                        //上级机构判断
                        if(agentBusInfo!=null){
                            //上级存在
                            if (StringUtils.isNotBlank(agentBusInfoVo.getBusParent())){
                                //获取上级代理商类型
                                AgentBusInfo busInfo = agentBusinfoService.getById(agentBusInfoVo.getBusParent());
                                if (agentBusInfoVo.getBusType().equals(BusType.ZQ.key) || agentBusInfoVo.getBusType().equals(BusType.ZQBZF.key) || agentBusInfoVo.getBusType().equals(BusType.ZQZF.key)) {
                                    if (busInfo.getBusType().equals(BusType.ZQ.key) || busInfo.getBusType().equals(BusType.ZQZF.key) || busInfo.getBusType().equals(BusType.ZQBZF.key)) {
                                        throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                                    }
                                }
                                if (agentBusInfoVo.getBusType().equals(BusType.YDX.key)) {
                                    if (busInfo.getBusType().equals(BusType.ZQ.key) || busInfo.getBusType().equals(BusType.YDX.key)
                                            || busInfo.getBusType().equals(BusType.ZQZF.key) || busInfo.getBusType().equals(BusType.ZQBZF.key)) {
                                        throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                                    }
                                }
                                if (agentBusInfoVo.getBusType().equals(BusType.JGYD.key)) {
                                    if (!busInfo.getBusType().equals(BusType.JG.key)) {
                                        throw new ProcessException("不能选择同级别的代理商为上级，请重新选择");
                                    }
                                }

                                //上级不为空  说明选择了上级---校验业务平台
                                AgentBusInfo parent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getBusParent());
                                PlatForm platForm = platFormService.selectByPlatformNum(parent.getBusPlatform());
                                if (null!=parent){
                                    if (StringUtils.isNotBlank(agentBusInfoVo.getBusPlatform())){
                                        if (!agentBusInfoVo.getBusPlatform().equals(parent.getBusPlatform())){
                                            throw new ProcessException("审批失败:业务平台类型和上级代理商业务平台类型不同，上级代理商业务平台类型为:"+platForm.getPlatformName());
                                        }
                                    }else{
                                        //业务平台为空  说明不是pro类型的(前端没有传值进来) 需查询业务平台类型
                                        if (!agentBusInfo.getBusPlatform().equals(parent.getBusPlatform())){
                                            throw new ProcessException("审批失败:业务平台类型和上级代理商业务平台类型不同，上级代理商业务平台类型为:"+platForm.getPlatformName());
                                        }
                                    }
                                    //校验顶级机构
                                    if(StringUtils.isNotBlank(parent.getOrganNum())){
                                        //上级机构不为空判断与本级是否一致
                                        if(!parent.getOrganNum().equals(agentBusInfoVo.getOrganNum())){
                                            //提示上级机构是什么
                                            Organization organization = organizationMapper.selectByPrimaryKey(parent.getOrganNum());
                                            if(organization==null){
                                                throw new ProcessException("审批失败:顶级机构和上级的顶级机构不同，上级顶级机构未找到");
                                            }else{
                                                throw new ProcessException("审批失败:顶级机构和上级的顶级机构不同，上级顶级机构为:"+organization.getOrgName());
                                            }
                                        }
                                    }else{
                                        throw new ProcessException("审批失败:上级的顶级机构为空，请联系业务进行补全");
                                    }

                                }

                            }
                            if(StringUtils.isNotBlank(agentBusInfoVo.getBusParent())){
                                AgentBusInfo parent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getBusParent());
                                //上级必须有机构，如果没有机构需要提示补全
                                if(parent!=null){
                                    //上级机构为空，提示必须填写，上级机构不为空判断与本级是否一致
                                    if(StringUtils.isNotBlank(parent.getOrganNum())){
                                        //上级机构不为空判断与本级是否一致
                                        if(!parent.getOrganNum().equals(agentBusInfoVo.getOrganNum())){
                                            //提示上级机构是什么
                                            Organization organization = organizationMapper.selectByPrimaryKey(parent.getOrganNum());
                                            if(organization==null){
                                                throw new ProcessException("审批失败:顶级机构和上级的顶级机构不同，上级顶级机构未找到");
                                            }else{
                                                throw new ProcessException("审批失败:顶级机构和上级的顶级机构不同，上级顶级机构为:"+organization.getOrgName());
                                            }
                                        }
                                    }else{
                                        throw new ProcessException("审批失败:上级的顶级机构为空，请联系业务进行补全");
                                    }
                                }
                            }
    //
                            AgentVo vo = JSONObject.parseObject(dateChangeRequest.getDataContent(), AgentVo.class);
                            List<AgentBusInfoVo> busInfoVoList = vo.getBusInfoVoList();
                            for (AgentBusInfoVo busInfoVo : busInfoVoList) {
                                if(busInfoVo.getId().equals(agentBusInfo.getId())){
                                    busInfoVo.setOrganNum(agentBusInfoVo.getOrganNum());
                                    if (StringUtils.isNotBlank(agentBusInfoVo.getBusPlatform())){
                                        busInfoVo.setBusPlatform(agentBusInfoVo.getBusPlatform());
                                    }
                                    if (StringUtils.isNotBlank(agentBusInfoVo.getBusParent())){
                                        busInfoVo.setBusParent(agentBusInfoVo.getBusParent());
                                    }
                                }
                            }
                            String voJson = JSONObject.toJSONString(vo);
                            dateChangeRequest.setDataContent(voJson);
                            int i = dataChangeActivityService.updateByPrimaryKeySelective(dateChangeRequest);
                            if(i!=1){
                                throw new ProcessException("处理任务：更新失败");
                            }

                            //修改业务审批关系表
                            if (StringUtils.isNotBlank(agentVo.getSid()) && StringUtils.isNotBlank(agentBusInfoVo.getBusPlatform())){
                                BusActRel byActivId = busActRelMapper.findByActivId(agentVo.getSid());
                                byActivId.setNetInBusType("ACTIVITY_"+agentBusInfoVo.getBusPlatform());
                                if(busActRelMapper.updateByPrimaryKeySelective(byActivId)!=1){
                                    throw new ProcessException("业务审批关系表更新异常");
                                }
                            }
                        }
                    }
                }

            }
            return AgentResult.ok();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw e;
        } catch (ProcessException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 处理任务
     * @return
     */
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception{
        logger.info("信息变更，任务处理，传递参数{}", JSONObject.toJSONString(agentVo));
        try {
            AgentResult resultBus = dataChangeActivityService.approvalTaskBusi(agentVo,userId);
            if(!resultBus.isOK()){
                throw new ProcessException(resultBus.getMsg());
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

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKeySelective(DateChangeRequest dateChangeRequest){
        int i = dateChangeRequestMapper.updateByPrimaryKeySelective(dateChangeRequest);
        return i;
    }


    @Override
    public ResultVO deleteDataChange(String dataChangeId, String userId) throws Exception {
        logger.info("========用户{}删除数据修改申请{}", userId, dataChangeId);

        DateChangeRequest dateChangeRequest = dateChangeRequestMapper.selectByPrimaryKey(dataChangeId);
        dateChangeRequest.setStatus(Status.STATUS_0.status);
        dateChangeRequest.setcUpdate(new Date());
        int updateDateChange = dateChangeRequestMapper.updateByPrimaryKeySelective(dateChangeRequest);
        if (updateDateChange != 1) {
            logger.info("删除数据修改申请:{}", "数据删除失败");
            throw new MessageException("数据删除失败！");
        }

        return ResultVO.success(null);
    }
    private Boolean findBusPlatExist(AgentBusInfo agentBusInfo) {
        AgentBusInfoExample example = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria criteria = example.createCriteria();
        criteria.andAgentIdEqualTo(agentBusInfo.getAgentId());
        criteria.andBusPlatformEqualTo(agentBusInfo.getBusPlatform());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andCloReviewStatusIn(Arrays.asList(AgStatus.Approved.status,AgStatus.Approving.status));
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(example);
        if (null == agentBusInfos) {
            return true;
        }
        if (agentBusInfos.size() == 0) {
            return false;
        }
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO adjustFinanceOrgByAccount(String agentId) throws Exception {
        try {
            //查询结算卡
            AgentColinfo agentColinfo = agentColinfoMapper.selectByAgentId(agentId);
            //查询代理商业务
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            agentBusInfoExample.or()
                    .andAgentIdEqualTo(agentId)
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andOrganNumIsNotNull();
            List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            //线上出款机构调整逻辑
            //对公 且 开票 出款机构瑞银信 ALL:1:1:orgryx
            //顶级机构为瑞银信的   对私 不开票 出款机构为 烟台澜韵信息技术有限公司 orgryx:2:0:org烟台澜韵信息技术有限公司
            //顶级机构为非瑞银信的 对私 不开票 出款机构为 出款机构顶级机构        !orgryx:2:0:TOPORG
            //顶级机构为瑞银信的   对公 不开票 出款机构为 烟台澜韵信息技术有限公司 orgryx:1:0:org烟台澜韵信息技术有限公司
            //顶级机构为非瑞银信的 对公 不开票 出款机构为 烟台澜韵信息技术有限公司 !orgryx:1:0:org烟台澜韵信息技术有限公司
            List<Dict> list = dictOptionsService.dictList(DictGroup.FINACEORG_CONFIG.name(),DictGroup.FINACEORG_PAR.name());
            //遍历所有有效业务进行出款公司的变更
            for (AgentBusInfo agentBusInfo : agentBusInfoList) {

                    //表达式循环匹配
                    for (Dict dict : list) {
                        String part = dict.getdItemname();
                        String[] partItems = part.split(":");
                        if(partItems.length==4){

                            String part_djjg = partItems[0];//顶级机构
                            String part_dgds = partItems[1];//对公对私
                            String part_isv = partItems[2];//是否开票
                            String part_ckjg = partItems[3];//出款机构
                            //顶级机构等于或者不等于
                            if(!"ALL".equalsIgnoreCase(part_djjg)) {
                                //顶级机构取非
                                if (part_djjg.startsWith("!")) {
                                    //不等于匹配
                                    part_djjg = part_djjg.replace("!", "");
                                    if(!part_djjg.equals(agentBusInfo.getOrganNum())){
                                        logger.info("表达式匹配 顶级机构:{} {}",agentBusInfo.getOrganNum(),part);
                                    }else{
                                        logger.info("表达式不匹配 顶级机构:{} {}",agentBusInfo.getOrganNum(),part);
                                        continue;
                                    }
                                }else{
                                    //等于匹配
                                    if(part_djjg.equals(agentBusInfo.getOrganNum())){
                                        logger.info("表达式匹配 顶级机构:{} {}",agentBusInfo.getOrganNum(),part);
                                    }else{
                                        logger.info("表达式不匹配 顶级机构:{} {}",agentBusInfo.getOrganNum(),part);
                                        continue;
                                    }
                                }
                            }

                            //银行卡类型匹配
                            if(agentColinfo.getCloType()!=null && agentColinfo.getCloType().toPlainString().equals(part_dgds)){
                                logger.info("表达式匹配 银行卡类型:{} {}",agentColinfo.getCloType(),part);
                            }else{
                                logger.info("表达式不匹配 银行卡类型:{} {}",agentColinfo.getCloType(),part);
                                continue;
                            }

                            //开票类型类型匹配
                            if(agentColinfo.getCloInvoice()!=null && agentColinfo.getCloInvoice().toPlainString().equals(part_isv)){
                                logger.info("表达式匹配 开票类型:{} {}",agentColinfo.getCloType(),part);
                            }else{
                                logger.info("表达式不匹配 开票类型:{} {}",agentColinfo.getCloType(),part);
                                continue;
                            }
                            logger.info("结算卡变更调整出款公司 {} {} 变更前出款公司 {}",agentBusInfo.getId(),agentBusInfo.getBusNum(),agentBusInfo.getFinaceRemitOrgan());
                            if("TOPORG".equalsIgnoreCase(part_ckjg)){
                                agentBusInfo.setFinaceRemitOrgan(agentBusInfo.getOrganNum());
                            }else{
                                agentBusInfo.setFinaceRemitOrgan(part_ckjg);
                            }
                            if(agentBusInfo.getFinaceRemitOrgan() == null || null == organizationMapper.selectByPrimaryKey(agentBusInfo.getFinaceRemitOrgan())){
                                throw new MessageException("顶级机构未找到");
                            }
                            logger.info("结算卡变更调整出款公司 {} {} 变更后出款公司 {}",agentBusInfo.getId(),agentBusInfo.getBusNum(),agentBusInfo.getFinaceRemitOrgan());
                            if(1!=agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo)){
                                logger.info("结算卡变更调整出款公司 变更数据库失败 抛出异常 {} {} 变更后出款公司 {}",agentBusInfo.getId(),agentBusInfo.getBusNum(),agentBusInfo.getFinaceRemitOrgan());
                                throw new MessageException("出款机构更新失败");
                            }
                            break;
                        }

                    }
            }
            return ResultVO.success(null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("调整出款机构失败 {} {}",agentId,e.getMessage());
            throw new MessageException("调整出款机构失败");
        }
    }
}
