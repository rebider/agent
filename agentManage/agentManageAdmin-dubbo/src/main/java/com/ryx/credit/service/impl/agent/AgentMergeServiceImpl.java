package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.COrganizationMapper;
import com.ryx.credit.dao.agent.AgentMergeMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.AgentMerge;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.profit.service.IOwnInvoiceService;
import com.ryx.credit.profit.service.IProfitMergeDeductionService;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.CuserAgentMapper;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OCashReceivablesService;
import com.ryx.credit.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/***
 * 代理商合并
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/1/7 16:24
 * @Param
 * @return
 **/
@Service("agentMergeService")
public class AgentMergeServiceImpl implements AgentMergeService {

    private static Logger logger = LoggerFactory.getLogger(AgentMergeServiceImpl.class);
    @Autowired
    private AgentMergeBusInfoMapper agentMergeBusInfoMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private CUserMapper cUserMapper;
    @Autowired
    private CuserAgentMapper cUserAgentMapper;
    @Autowired
    private AgentMergeMapper agentMergeMapper;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AgentNotifyService agentNotifyService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentPlatFormSynMapper agentPlatFormSynMapper;
    @Autowired
    private OOrderMapper orderMapper;
    @Autowired
    private OSupplementMapper supplementMapper;
    @Autowired
    private ORefundPriceDiffMapper refundPriceDiffMapper;
    @Autowired
    private OReturnOrderMapper returnOrderMapper;
    @Autowired
    private OReceiptOrderMapper receiptOrderMapper;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private OCashReceivablesService cashReceivablesService;
    @Autowired
    private COrganizationMapper organizationMapper;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private IOwnInvoiceService ownInvoiceService;
    @Autowired
    private IProfitMergeDeductionService profitMergeDeductionServiceImpl;
    @Autowired
    private ProfitDeductionService profitDeductionServiceImpl;

    /**
     * 合并列表
     * @param agentMerge
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    @Override
    public PageInfo selectAgentMergeList(AgentMerge agentMerge, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(agentMerge.getId())) {
            reqMap.put("id", agentMerge.getId());
        }
        if (StringUtils.isNotBlank(agentMerge.getMainAgentId())) {
            reqMap.put("mainAgentId", agentMerge.getMainAgentId());
        }
        if (StringUtils.isNotBlank(agentMerge.getMainAgentName())) {
            reqMap.put("mainAgentName", agentMerge.getMainAgentName());
        }
        if (null != agentMerge.getCloReviewStatus()) {
            reqMap.put("cloReviewStatus", agentMerge.getCloReviewStatus());
        }
        if(StringUtils.isBlank(dataRole)){
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if(orgCodeRes==null && orgCodeRes.size()!=1){
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            reqMap.put("orgId",String.valueOf(stringObjectMap.get("ORGID")));
        }
        List<Map<String,Object>> agentMergeList = agentMergeMapper.selectAgentMergeList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentMergeList);
        pageInfo.setTotal(agentMergeMapper.selectAgentMergeCount(reqMap));
        return pageInfo;
    }

    /**
     * 保存数据
     * @param agentMerge
     * @param cUser
     * @param saveFlag
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult saveAgentMerge(AgentMerge agentMerge, String[] busType, String cUser, String saveFlag,
                                      List<OCashReceivablesVo> oCashReceivables, String[] agentMergeFiles) throws Exception {
        if (StringUtils.isBlank(cUser)) {
            logger.info("代理商合并提交，操作用户为空:{}", cUser);
            return AgentResult.fail("代理商合并提交，操作用户为空！");
        }
        try {
            String mergeId = idService.genId(TabId.A_AGENT_MERGE);
            agentMerge.setId(mergeId);
            agentMerge.setcTime(new Date());
            agentMerge.setuTime(new Date());
            agentMerge.setcUser(cUser);
            agentMerge.setuUser(cUser);
            agentMerge.setStatus(Status.STATUS_1.status);
            agentMerge.setVersion(Status.STATUS_1.status);

            //主代理商和副代理商必须是标准一代或机构，副代理商且不能有下级
            mainAndSubMustHaveLower(agentMerge);
            //合并中不能重复发起,判断是否有欠票欠款情况
            verifyMergeing(agentMerge,busType,oCashReceivables);
            //补款、退货、补差价、下订单流程中、有未排单的、未发货的,不可以合并
            verifypprovaling(agentMerge.getSubAgentId());

            if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
                agentMerge.setCloReviewStatus(AgStatus.Approving.status);
            } else {
                agentMerge.setCloReviewStatus(AgStatus.Create.status);
            }
            String strBusType = "";
            for(int i=0;i<busType.length;i++){
                strBusType+=busType[i];
                if(i!=busType.length-1){
                    strBusType+=",";
                }
            }
            agentMerge.setMergeBusIds(strBusType);
            if (1 != agentMergeMapper.insertSelective(agentMerge)) {
                logger.info("代理商合并提交审批，新增数据失败:{}", cUser);
                throw new MessageException("代理商合并提交审批，新增数据失败！");
            }
            //打款记录
            AgentResult agentResult = cashReceivablesService.addOCashReceivables(oCashReceivables,cUser,agentMerge.getSubAgentId(),CashPayType.AGENTMERGE,mergeId);
            if(!agentResult.isOK()){
                logger.info("代理商合并保存打款记录失败1");
                throw new ProcessException("保存打款记录失败");
            }
            //添加新的附件
            if (agentMergeFiles != null && agentMergeFiles.length!=0) {
                for(int i=0;i<agentMergeFiles.length;i++){
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(agentMergeFiles[i]);
                    record.setSrcId(mergeId);
                    record.setcUser(cUser);
                    record.setcTime(Calendar.getInstance().getTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.agentMerge.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    int f = attachmentRelMapper.insertSelective(record);
                    if (1 != f) {
                        logger.info("代理商合并保存附件关系失败");
                        throw new ProcessException("保存附件失败");
                    }
                }
            }
            //查看被合并代理商有没有合并过
            verifyAgentMerge(agentMerge,mergeId,cUser);
            insert(busType,mergeId,agentMerge,cUser);

            if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
                startAgentMergeActivity(mergeId, cUser,true);
            }
            return AgentResult.ok();
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 新增
     * @param busType
     * @param mergeId
     * @param agentMerge
     * @param cUser
     * @throws Exception
     */
    private void insert(String[] busType,String mergeId,AgentMerge agentMerge,String cUser)throws Exception{

        for(int i=0;i<busType.length;i++){
            String busId = busType[i];
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
            AgentMergeBusInfo agentMergeBusInfo = new AgentMergeBusInfo();
            String mergeBusInfoId = idService.genId(TabId.A_AGENT_MERGE_BUSINFO);
            agentMergeBusInfo.setId(mergeBusInfoId);
            agentMergeBusInfo.setMergeStatus(MergeStatus.WXS.getValue());
            agentMergeBusInfo.setAgentMargeId(mergeId);
            agentMergeBusInfo.setBusId(agentBusInfo.getId());
            agentMergeBusInfo.setMainAgentId(agentMerge.getMainAgentId());
            agentMergeBusInfo.setSubAgentId(agentMerge.getSubAgentId());
            agentMergeBusInfo.setcTime(new Date());
            agentMergeBusInfo.setcUtime(new Date());
            agentMergeBusInfo.setcUser(cUser);
            agentMergeBusInfo.setStatus(Status.STATUS_1.status);
            agentMergeBusInfo.setVersion(Status.STATUS_1.status);
            //以下是复制信息
            agentMergeBusInfo.setBusNum(agentBusInfo.getBusNum());
            agentMergeBusInfo.setBusPlatform(agentBusInfo.getBusPlatform());
            agentMergeBusInfo.setBusType(agentBusInfo.getBusType());
            agentMergeBusInfo.setBusParent(agentBusInfo.getBusParent());
            agentMergeBusInfo.setBusRiskParent(agentBusInfo.getBusRiskParent());
            agentMergeBusInfo.setBusActivationParent(agentBusInfo.getBusActivationParent());
            agentMergeBusInfo.setBusRegion(agentBusInfo.getBusRegion());
            agentMergeBusInfo.setBusSentDirectly(agentBusInfo.getBusSentDirectly());
            agentMergeBusInfo.setBusDirectCashback(agentBusInfo.getBusDirectCashback());
            agentMergeBusInfo.setBusIndeAss(agentBusInfo.getBusIndeAss());
            agentMergeBusInfo.setBusContact(agentBusInfo.getBusContact());
            agentMergeBusInfo.setBusContactEmail(agentBusInfo.getBusContactEmail());
            agentMergeBusInfo.setBusContactMobile(agentBusInfo.getBusContactMobile());
            agentMergeBusInfo.setBusContactPerson(agentBusInfo.getBusContactPerson());
            agentMergeBusInfo.setBusRiskEmail(agentBusInfo.getBusRiskEmail());
            agentMergeBusInfo.setCloTaxPoint(agentBusInfo.getCloTaxPoint());
            agentMergeBusInfo.setCloInvoice(agentBusInfo.getCloInvoice());
            agentMergeBusInfo.setCloReceipt(agentBusInfo.getCloReceipt());
            agentMergeBusInfo.setCloPayCompany(agentBusInfo.getCloPayCompany());
            agentMergeBusInfo.setCloPayCompany(agentBusInfo.getCloPayCompany());
            agentMergeBusInfo.setAgZbh(agentBusInfo.getAgZbh());
            agentMergeBusInfo.setBusStatus(agentBusInfo.getBusStatus());
            agentMergeBusInfo.setBusUseOrgan(agentBusInfo.getBusUseOrgan());
            agentMergeBusInfo.setCloReviewStatus(agentBusInfo.getCloReviewStatus());
            agentMergeBusInfo.setBusScope(agentBusInfo.getBusScope());
            agentMergeBusInfo.setDredgeS0(agentBusInfo.getDredgeS0());
            agentMergeBusInfo.setBusLoginNum(agentBusInfo.getBusLoginNum());
            agentMergeBusInfoMapper.insertSelective(agentMergeBusInfo);
        }

    }
    /**
     * 查看被合并代理商有没有合并过
     * @param agentMerge
     * @param mergeId
     * @param cUser
     * @throws Exception
     */
    public void verifyAgentMerge(AgentMerge agentMerge,String mergeId,String cUser)throws Exception{

        AgentMergeBusInfoExample agentMergeBusInfoExample = new AgentMergeBusInfoExample();
        AgentMergeBusInfoExample.Criteria criteria = agentMergeBusInfoExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andMergeStatusEqualTo(MergeStatus.SX.getValue());
        criteria.andMainAgentIdEqualTo(agentMerge.getSubAgentId());
        List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeBusInfoMapper.selectByExample(agentMergeBusInfoExample);
        if(agentMergeBusInfos.size()!=0){
            for (AgentMergeBusInfo agentMergeBusInfo : agentMergeBusInfos) {
                agentMergeBusInfo.setMergeStatus(MergeStatus.BHB.getValue());
                int i = agentMergeBusInfoMapper.updateByPrimaryKey(agentMergeBusInfo);
                if(i!=1){
                    throw new MessageException("代理商合并，处理失败！");
                }
                String mergeBusInfoId = idService.genId(TabId.A_AGENT_MERGE_BUSINFO);
                agentMergeBusInfo.setId(mergeBusInfoId);
                agentMergeBusInfo.setAgentMargeId(mergeId);
                agentMergeBusInfo.setMergeStatus(MergeStatus.WXS.getValue());
                agentMergeBusInfo.setStatus(Status.STATUS_1.status);
                agentMergeBusInfo.setMainAgentId(agentMerge.getMainAgentId());
                agentMergeBusInfo.setSubAgentId(agentMerge.getSubAgentId());
                agentMergeBusInfo.setcTime(new Date());
                agentMergeBusInfo.setcUtime(new Date());
                agentMergeBusInfo.setcUser(cUser);
                agentMergeBusInfoMapper.insertSelective(agentMergeBusInfo);
            }
        }

    }


    /**
     * 主代理商和副代理商必须是标准一代或机构，副代理商且不能有下级
     * @param agentMerge
     * @throws Exception
     */
    public void mainAndSubMustHaveLower(AgentMerge agentMerge)throws Exception{

        String mainAgentId = agentMerge.getMainAgentId();
        String subAgentId = agentMerge.getSubAgentId();

        AgentBusInfoExample mainAgentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria mainCriteria = mainAgentBusInfoExample.createCriteria();
        mainCriteria.andAgentIdEqualTo(mainAgentId);
        mainCriteria.andStatusEqualTo(Status.STATUS_1.status);
        List<BigDecimal> mainBusStatusList = new ArrayList<>();
        mainBusStatusList.add(BusinessStatus.Enabled.status);
        mainBusStatusList.add(BusinessStatus.inactive.status);
        mainCriteria.andBusStatusIn(mainBusStatusList);
        List<AgentBusInfo> mainAgentBusInfos = agentBusInfoMapper.selectByExample(mainAgentBusInfoExample);
        if(mainAgentBusInfos.size()==0){
            throw new MessageException("主代理商业务信息有误");
        }
        for (AgentBusInfo mainAgentBusInfo : mainAgentBusInfos) {
            if(!mainAgentBusInfo.getBusType().equals(BusType.BZYD.key) && !mainAgentBusInfo.getBusType().equals(BusType.JG.key)){
                throw new MessageException("主代理商不是标准一代或机构");
            }
        }
        AgentBusInfoExample subAgentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria subCriteria = subAgentBusInfoExample.createCriteria();
        subCriteria.andAgentIdEqualTo(subAgentId);
        subCriteria.andStatusEqualTo(Status.STATUS_1.status);
        List<BigDecimal> subBusStatusList = new ArrayList<>();
        subBusStatusList.add(BusinessStatus.Enabled.status);
        subBusStatusList.add(BusinessStatus.inactive.status);
        AgentMerge queryAgentMerge = agentMergeMapper.selectByPrimaryKey(agentMerge.getId());
        if(queryAgentMerge!=null){
            if(queryAgentMerge.getCloReviewStatus().compareTo(AgStatus.Approving.getValue())==0){
                subBusStatusList.add(BusinessStatus.lock.status);
            }
        }
        subCriteria.andBusStatusIn(subBusStatusList);
        List<AgentBusInfo> subAgentBusInfos = agentBusInfoMapper.selectByExample(subAgentBusInfoExample);
        if(subAgentBusInfos.size()==0){
            throw new MessageException("副代理商业务信息有误");
        }
        for (AgentBusInfo subAgentBusInfo : subAgentBusInfos) {
            if(!subAgentBusInfo.getBusType().equals(BusType.BZYD.key) && !subAgentBusInfo.getBusType().equals(BusType.JG.key)){
                throw new MessageException("副代理商不是标准一代或机构");
            }
            if(StringUtils.isBlank(subAgentBusInfo.getBusNum())){
                throw new MessageException("副代理商业务平台未入网成功");
            }
            List<AgentBusInfo> childLevelBusInfos = agentBusinfoService.queryChildLevelByBusNum(null, subAgentBusInfo.getBusPlatform(), subAgentBusInfo.getBusNum());
            if(childLevelBusInfos.size()!=0){
                throw new MessageException("副代理商不能有下级");
            }
        }
    }

    /**
     * 合并中不能重复发起,检查合并的业务是否有效
     * 判断是否有欠票欠款情况
     * @param agentMerge
     */
    public void verifyMergeing(AgentMerge agentMerge,String[] busTypes,List<OCashReceivablesVo> oCashReceivables)throws Exception{
        String mainAgentId = agentMerge.getMainAgentId();
        String subAgentId = agentMerge.getSubAgentId();
        AgentMergeExample agentMergeExample = new AgentMergeExample();
        AgentMergeExample.Criteria criteria = agentMergeExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andMainAgentIdEqualTo(mainAgentId);
        criteria.andSubAgentIdEqualTo(subAgentId);
        List<BigDecimal> cloReviewStatusList = new ArrayList<>();
        cloReviewStatusList.add(AgStatus.Create.getValue());
        cloReviewStatusList.add(AgStatus.Approving.getValue());
        criteria.andCloReviewStatusIn(cloReviewStatusList);
        List<AgentMerge> agentMerges = agentMergeMapper.selectByExample(agentMergeExample);
        AgentMerge queryAgentMerge = agentMergeMapper.selectByPrimaryKey(agentMerge.getId());
        if(queryAgentMerge!=null){
            if(queryAgentMerge.getCloReviewStatus().compareTo(AgStatus.Approving.getValue())!=0){
                if(agentMerges.size()!=0){
                    throw new MessageException("合并中不能重复发起");
                }
            }
        }
        if(subAgentId.equals(mainAgentId)){
            throw new MessageException("主副代理商不能是同一个人");
        }
        //判断是否是当前省区下的代理商
        List<Map<String, Object>>  org = iUserService.orgCode(Long.valueOf(agentMerge.getuUser()));
        if(org.size()==0){throw new ProcessException("部门信息未找到");}
        String orgId = String.valueOf(org.get(0).get("ORGID"));
        if(StringUtils.isBlank(orgId)){
            throw new ProcessException("省区部门参数为空");
        }
        Agent mainAgent = agentMapper.selectByPrimaryKey(mainAgentId);
        if(null==mainAgent){
            throw new ProcessException("代理商登录用户不存在");
        }
        if(!orgId.equals(mainAgent.getAgDocPro())){
            throw new ProcessException("只能提交自己省区的代理商合并");
        }
        BigDecimal subAgentOweTicket = getSubAgentOweTicket(agentMerge.getSubAgentId());
        if(subAgentOweTicket==null){
            throw new ProcessException("副代理商查询欠票失败");
        }
        if(subAgentOweTicket.compareTo(new BigDecimal(0))!=0){
            throw new ProcessException("副代理商欠票不可以提交");
        }

        //判断是否有欠票欠款情况
        if(agentMerge.getSubAgentDebt().compareTo(new BigDecimal(0))==1 || agentMerge.getSubAgentOweTicket().compareTo(new BigDecimal(0))==1){
            if(null==agentMerge.getSuppType()){
                throw new MessageException("请选择补缴类型");
            }
            if(agentMerge.getSuppType().compareTo(mergeSuppType.DLSDK.getValue())==0){
                if(StringUtils.isBlank(agentMerge.getSuppAgentId())){
                    throw new MessageException("补缴类型为代理商代扣,代理商ID必填");
                }
                if(StringUtils.isBlank(agentMerge.getSuppAgentName())){
                    throw new MessageException("补缴类型为代理商代扣,代理商名称必填");
                }
                AgentExample agentExample = new AgentExample();
                AgentExample.Criteria agentCriteria = agentExample.createCriteria();
                agentCriteria.andStatusEqualTo(Status.STATUS_1.status);
                agentCriteria.andAgStatusEqualTo(AgStatus.Approved.name());
                agentCriteria.andIdEqualTo(agentMerge.getSuppAgentId());
                agentCriteria.andAgNameEqualTo(agentMerge.getSuppAgentName());
                List<Agent> agents = agentMapper.selectByExample(agentExample);
                if(agents==null){
                    throw new MessageException("补缴类型为代理商代扣,代理商必须存在,且审批通过");
                }
                if(agents.size()!=1){
                    throw new MessageException("补缴类型为代理商代扣,代理商必须存在,且审批通过");
                }
            }else if(agentMerge.getSuppType().compareTo(mergeSuppType.XXDK.getValue())==0){
                if(oCashReceivables==null){
                    throw new MessageException("补缴类型为线下补款,请填写打款记录");
                }
                if(oCashReceivables.size()==0){
                    throw new MessageException("补缴类型为线下补款,请填写打款记录");
                }
            }else{
                throw new MessageException("请选择补缴类型");
            }
        }

        for(int i=0;i<busTypes.length;i++){
            String busId = busTypes[i];
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria agentBusInfoCriteria = agentBusInfoExample.createCriteria();
            agentBusInfoCriteria.andIdEqualTo(busId);
            agentBusInfoCriteria.andStatusEqualTo(Status.STATUS_1.status);
            List<BigDecimal> busStatusList = new ArrayList<>();
            busStatusList.add(BusinessStatus.Enabled.status);
            busStatusList.add(BusinessStatus.inactive.status);
            if(queryAgentMerge!=null){
                if(queryAgentMerge.getCloReviewStatus().compareTo(AgStatus.Approving.getValue())==0){
                    busStatusList.add(BusinessStatus.lock.status);
                }
            }
            agentBusInfoCriteria.andBusStatusIn(busStatusList);
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            if(agentBusInfos.size()==0){
                throw new MessageException("合并业务不存在");
            }
        }
    }

    /**
     *补款、退货、补差价、下订单流程中、有未排单的、未发货的,不可以合并
     * @param subAgentId
     */
    public void verifypprovaling(String subAgentId)throws Exception{

        //订单
        OOrderExample oOrderExample = new OOrderExample();
        OOrderExample.Criteria criteria = oOrderExample.createCriteria();
        criteria.andAgentIdEqualTo(subAgentId);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andOrderStatusEqualTo(OrderStatus.ENABLE.status);
        criteria.andReviewStatusEqualTo(AgStatus.Approving.getValue());
        List<OOrder> oOrders = orderMapper.selectByExample(oOrderExample);
        if(oOrders.size()!=0){
            throw new MessageException("有审批中的订单,不能发起合并");
        }

        //补款
        OSupplementExample supplementExample = new OSupplementExample();
        OSupplementExample.Criteria supplementCriteria = supplementExample.createCriteria();
        supplementCriteria.andAgentIdEqualTo(subAgentId);
        supplementCriteria.andStatusEqualTo(Status.STATUS_1.status);
        supplementCriteria.andReviewStatusEqualTo(AgStatus.Approving.getValue());
        List<OSupplement> oSupplements = supplementMapper.selectByExample(supplementExample);
        if(oSupplements.size()!=0){
            throw new MessageException("有审批中的补款,不能发起合并");
        }

        //补差价
        ORefundPriceDiffExample refundPriceDiffExample = new ORefundPriceDiffExample();
        ORefundPriceDiffExample.Criteria refundPriceDiffCriteria = refundPriceDiffExample.createCriteria();
        refundPriceDiffCriteria.andAgentIdEqualTo(subAgentId);
        refundPriceDiffCriteria.andStatusEqualTo(Status.STATUS_1.status);
        refundPriceDiffCriteria.andReviewStatusEqualTo(AgStatus.Approving.getValue());
        List<ORefundPriceDiff> oRefundPriceDiffs = refundPriceDiffMapper.selectByExample(refundPriceDiffExample);
        if(oRefundPriceDiffs.size()!=0){
            throw new MessageException("有审批中的补差价,不能发起合并");
        }

        //退货
        OReturnOrderExample returnOrderExample = new OReturnOrderExample();
        OReturnOrderExample.Criteria returnOrderCriteria = returnOrderExample.createCriteria();
        returnOrderCriteria.andAgentIdEqualTo(subAgentId);
        returnOrderCriteria.andStatusEqualTo(Status.STATUS_1.status);
        returnOrderCriteria.andRetScheduleEqualTo(new BigDecimal(RetSchedule.SPZ.code));
        List<OReturnOrder> oReturnOrders = returnOrderMapper.selectByExample(returnOrderExample);
        if(oReturnOrders.size()!=0){
            throw new MessageException("有审批中的退货,不能发起合并");
        }

        //未排单
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("agStatus", AgStatus.Approved.name());
        reqMap.put("cIncomStatus", AgentInStatus.NO.status);
        reqMap.put("cloReviewStatus", AgStatus.Approved.status);
        reqMap.put("agentId", subAgentId);
        int i = receiptOrderMapper.queryPlannerCount(reqMap);
        if(i!=0){
            throw new MessageException("有审批中的排单,不能发起合并");
        }

        //未发货
        Map<String, Object> param = new HashMap<>();
        param.put("agentId",subAgentId);
        param.put("planOrderStatus", PlannerStatus.YesPlanner.getValue());
        Long count = receiptPlanMapper.getReceipPlanCount(param);
        if(count!=0){
            throw new MessageException("有审批中的已排单（未发货）,不能发起合并");
        }

    }

    /**
     * 提交数据并审批
     * @param id
     * @param cUser
     * @param isSave 是否已保存
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startAgentMergeActivity(String id, String cUser, Boolean isSave) throws Exception {
        AgentMerge agentMerge = agentMergeMapper.selectByPrimaryKey(id);
        if (agentMerge == null) {
            throw new MessageException("提交审批信息有误！");
        }
        if (!isSave) {
            agentMerge.setuUser(cUser);
            agentMerge.setuTime(new Date());
            agentMerge.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != agentMergeMapper.updateByPrimaryKeySelective(agentMerge)) {
                throw new MessageException("提交审批处理失败！");
            }
        }
        //提交审批进行锁定
        String strMergeBusIds = agentMerge.getMergeBusIds();
        String[] mergeBusIds = strMergeBusIds.split(",");
        for(int i=0;i<mergeBusIds.length;i++){
            String mergeBusId = mergeBusIds[i];
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(mergeBusId);
            agentBusInfo.setBusStatus(BusinessStatus.lock.status);
            int j = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
            if (j!=1) {
                throw new MessageException("更新业务锁定失败");
            }
        }

        AgentResult agentResult = cashReceivablesService.startProcing(CashPayType.AGENTMERGE,id,cUser);
        if(!agentResult.isOK()){
            logger.info("代理商合并更新打款信息失败");
            throw new MessageException("代理商合并更新打款信息失败");
        }

        Map reqMap = new HashMap();
        Agent mainAgent = agentMapper.selectByPrimaryKey(agentMerge.getMainAgentId());
        Agent subAgent = agentMapper.selectByPrimaryKey(agentMerge.getSubAgentId());
        //判断省区是否相同
        if(mainAgent.getAgDocPro().equals(subAgent.getAgDocPro())){
            reqMap.put("city","1");
            COrganization mainOrganization = organizationMapper.selectByPrimaryKey(mainAgent.getAgDocDistrict());
            if(null==mainOrganization){
                throw new MessageException("主代理商部门信息错误");
            }
            COrganization subOrganization = organizationMapper.selectByPrimaryKey(subAgent.getAgDocDistrict());
            if(null==subOrganization){
                throw new MessageException("副代理商部门信息错误");
            }
            reqMap.put(mainOrganization.getCode(),mainOrganization.getCode());
            reqMap.put(subOrganization.getCode(),subOrganization.getCode());
            if(!reqMap.containsValue("beijing")){
                reqMap.put("beijing","");
            }
            if(!reqMap.containsValue("south")){
                reqMap.put("south","");
            }
            if(!reqMap.containsValue("north")){
                reqMap.put("north","");
            }
        }else{
            reqMap.put("city","2");
        }

        //启动审批流
        String proceId = activityService.createDeloyFlow(null, "mergeCity1.0", null, null, reqMap);
        if (proceId == null) {
            logger.info("代理商合并提交审批，审批流启动失败{}:{}", id, cUser);
            throw new MessageException("审批流启动失败！");
        }
        //代理商业务&工作流关系
        BusActRel record = new BusActRel();
        record.setBusId(id);
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cUser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.MERGE.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(mainAgent.getId());
        record.setAgentName(mainAgent.getAgName());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("代理商合并提交审批，启动审批异常，添加审批关系失败{}:{}", id, proceId);
            throw new MessageException("审批流启动失败：添加审批关系失败！");
        }
        return AgentResult.ok();
    }

    /**
     * 处理任务
     * @param agentVo
     * @param userId
     * @param busId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalAgentMergeTask(AgentVo agentVo, String userId, String busId) throws Exception {
        try {
            if (agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())) {
                List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(userId));
                if (null == orgCodeRes) {
                    throw new ProcessException("部门参数为空！");
                }
                Map<String, Object> map = orgCodeRes.get(0);
                Object orgCode = map.get("ORGANIZATIONCODE");
                AgentMerge agentMerge = agentMergeMapper.selectByPrimaryKey(busId);
                //于华审批
                if (String.valueOf(orgCode).equals("manage")) {
                    agentMerge.setMergeType(agentVo.getMergeType());
                    if (1 != agentMergeMapper.updateByPrimaryKeySelective(agentMerge)) {
                        throw new MessageException("合并类型更新失败！");
                    }
                }
                //财务审批
                if (String.valueOf(orgCode).equals("finance")) {
                    AgentResult cashAgentResult = cashReceivablesService.approveTashBusiness(CashPayType.AGENTMERGE,busId,userId,new Date(),agentVo.getoCashReceivablesVoList());
                    if(!cashAgentResult.isOK()){
                        throw new ProcessException("更新收款信息失败");
                    }
                }
                //退回修改节点和第二个省区
                if(agentVo.getSid().equals("sid-8BFF457F-18DB-4AF9-A1F9-90C19812BE67") || agentVo.getSid().equals("sid-5C0AE792-7539-46D2-86BA-243B6A42D9FE")){
                    Agent mainAgent = agentMapper.selectByPrimaryKey(agentMerge.getMainAgentId());
                    Agent subAgent = agentMapper.selectByPrimaryKey(agentMerge.getSubAgentId());
                    COrganization mainOrganization = organizationMapper.selectByPrimaryKey(mainAgent.getAgDocDistrict());
                    if(null==mainOrganization){
                        throw new MessageException("主代理商部门信息错误");
                    }
                    COrganization subOrganization = organizationMapper.selectByPrimaryKey(subAgent.getAgDocDistrict());
                    if(null==subOrganization){
                        throw new MessageException("副代理商部门信息错误");
                    }
                    agentVo.setMainDocDistrict(mainOrganization.getCode());
                    agentVo.setSubDocDistrict(subOrganization.getCode());
                }
            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo, userId);
            if (!result.isOK()) {
                logger.error(result.getMsg());
                throw new MessageException("工作流处理任务异常");
            }
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("catch工作流处理任务异常！" );
        }
        return AgentResult.ok();
    }

    /**
     * 根据ID查询合并数据
     * @param mergeId
     * @return
     */
    @Override
    public AgentMerge queryAgentMerge(String mergeId) {
        if (StringUtils.isBlank(mergeId)) {
            return null;
        }
        AgentMerge agentMerge = agentMergeMapper.selectByPrimaryKey(mergeId);
        if (null == agentMerge) {
            return null;
        }
        AgentMergeBusInfoExample agentMergeBusInfoExample = new AgentMergeBusInfoExample();
        AgentMergeBusInfoExample.Criteria criteria = agentMergeBusInfoExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andMergeStatusEqualTo(MergeStatus.WXS.getValue());
        criteria.andAgentMargeIdEqualTo(agentMerge.getId());
        List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeBusInfoMapper.selectByExample(agentMergeBusInfoExample);
        for (AgentMergeBusInfo agentMergeBusInfo : agentMergeBusInfos) {
            agentMergeBusInfo.setBusPlatformName(Platform.getContentByValue(agentMergeBusInfo.getBusPlatform()));
            agentMergeBusInfo.setBusTypeName(BusType.getContentByValue(agentMergeBusInfo.getBusType()));
        }
        agentMerge.setAgentMergeBusInfosList(agentMergeBusInfos);
        List<Map<String,Object>> data = agentBusInfoMapper.queryEditAgentMerge(FastMap.fastMap("agentId",agentMerge.getSubAgentId()));
        for (Map datum : data) {
            datum.put("BUS_TYPE_NAME",BusType.getContentByValue(String.valueOf(datum.get("BUS_TYPE"))));
        }
        agentMerge.setSubAgentBusInfoList(data);
        //查询关联附件
        List<Attachment> attachments = attachmentMapper.accessoryQuery(mergeId, AttachmentRelType.agentMerge.name());
        agentMerge.setAttachments(attachments);

        return agentMerge;
    }

    /**
     * 根据合并ID查询合并业务信息
     * @param mergeId
     * @return
     */
    @Override
    public List<AgentMergeBusInfo> queryAgentMergeBusInfo(String mergeId) {
        if (StringUtils.isBlank(mergeId)) {
            return null;
        }
        AgentMergeBusInfoExample agentMergeBusInfoExample = new AgentMergeBusInfoExample();
        AgentMergeBusInfoExample.Criteria criteria = agentMergeBusInfoExample.createCriteria();
        criteria.andAgentMargeIdEqualTo(mergeId);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeBusInfoMapper.selectByExample(agentMergeBusInfoExample);
        if (agentMergeBusInfos.size() == 0) {
            return null;
        }
        for (AgentMergeBusInfo agentMergeBusInfo : agentMergeBusInfos) {
            PlatForm platForm = platFormService.selectByPlatformNum(agentMergeBusInfo.getBusPlatform());
            if (null != platForm) {
                agentMergeBusInfo.setBusPlatformType(platForm.getPlatformType());
            }
            Agent agent = agentService.getAgentById(agentMergeBusInfo.getSubAgentId());
            if (null != agent) {
                agentMergeBusInfo.setSubAgentName(agent.getAgName());
            }
            agentMergeBusInfo.setMergeStatusName(MergeStatus.getContentByValue(agentMergeBusInfo.getMergeStatus()));
        }
        return agentMergeBusInfos;
    }

    /**
     * 根据主代理商查询被合并代理商业务
     * @param mainAgentId
     * @return
     */
    @Override
    public List<AgentMergeBusInfo> queryMainAgentMergeBus(String mainAgentId) {
        if (StringUtils.isBlank(mainAgentId)) {
            return null;
        }
        AgentMergeBusInfoExample agentMergeBusInfoExample = new AgentMergeBusInfoExample();
        AgentMergeBusInfoExample.Criteria criteria = agentMergeBusInfoExample.createCriteria();
        criteria.andMainAgentIdEqualTo(mainAgentId);
        criteria.andMergeStatusEqualTo(MergeStatus.SX.getValue());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeBusInfoMapper.selectByExample(agentMergeBusInfoExample);
        for (AgentMergeBusInfo agentMergeBusInfo : agentMergeBusInfos) {
            Agent agent = agentService.getAgentById(agentMergeBusInfo.getSubAgentId());
            if (null != agent) {
                agentMergeBusInfo.setSubAgentName(agent.getAgName());
            }
            agentMergeBusInfo.setMergeStatusName(MergeStatus.getContentByValue(agentMergeBusInfo.getMergeStatus()));
        }
        return agentMergeBusInfos;
    }

    /**
     * 修改数据
     * @param agentMerge
     * @param busType
     * @param cUser
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult editAgentMerge(AgentMerge agentMerge, String[] busType, String cUser,
                                      List<OCashReceivablesVo> oCashReceivables, String[] agentMergeFiles,String proIns) throws Exception {
        if(StringUtils.isBlank(agentMerge.getId())){
            throw new MessageException("数据ID为空！");
        }
        try {

            agentMerge.setuTime(new Date());
            agentMerge.setuUser(cUser);
            //主代理商和副代理商必须是标准一代或机构，副代理商且不能有下级
            mainAndSubMustHaveLower(agentMerge);
            //合并中不能重复发起,判断是否有欠票欠款情况
            verifyMergeing(agentMerge,busType,oCashReceivables);
            //补款、退货、补差价、下订单流程中、有未排单的、未发货的,不可以合并
            verifypprovaling(agentMerge.getSubAgentId());

            String strBusType = "";
            for(int i=0;i<busType.length;i++){
                strBusType+=busType[i];
                if(i!=busType.length-1){
                    strBusType+=",";
                }
            }
            agentMerge.setMergeBusIds(strBusType);
            if (1 != agentMergeMapper.updateByPrimaryKeySelective(agentMerge)) {
                logger.info("代理商合并修改审批，更新数据失败:{}", cUser);
                throw new MessageException("更新合并数据失败！");
            }

            //打款记录
            AgentResult agentResult = cashReceivablesService.addOCashReceivables(oCashReceivables,cUser,agentMerge.getSubAgentId(),CashPayType.AGENTMERGE,agentMerge.getId());
            if(!agentResult.isOK()){
                logger.info("代理商合并保存打款记录失败1");
                throw new ProcessException("保存打款记录失败");
            }

            //附件修改
            if(null!=agentMergeFiles && agentMergeFiles.length!=0){
                AttachmentRelExample attachmentRelExample = new AttachmentRelExample();
                AttachmentRelExample.Criteria criteria = attachmentRelExample.createCriteria();
                criteria.andSrcIdEqualTo(agentMerge.getId());
                criteria.andBusTypeEqualTo(AttachmentRelType.agentMerge.name());
                List<AttachmentRel> attachmentRels = attachmentRelMapper.selectByExample(attachmentRelExample);
                attachmentRels.forEach(row->{
                    row.setStatus(Status.STATUS_0.status);
                    int i = attachmentRelMapper.updateByPrimaryKeySelective(row);
                    if (1 != i) {
                        logger.info("删除代理商合并附件关系失败");
                        throw new ProcessException("删除附件失败");
                    }
                });

                for(int i=0;i<agentMergeFiles.length;i++){
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(agentMergeFiles[i]);
                    record.setSrcId(agentMerge.getId());
                    record.setcUser(cUser);
                    record.setcTime(Calendar.getInstance().getTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.agentMerge.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    int f = attachmentRelMapper.insertSelective(record);
                    if (1 != f) {
                        logger.info("代理商合并附件关系失败");
                        throw new ProcessException("附件关系失败");
                    }
                }
            }

            AgentMergeBusInfoExample agentMergeBusInfoExample = new AgentMergeBusInfoExample();
            AgentMergeBusInfoExample.Criteria criteria = agentMergeBusInfoExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andAgentMargeIdEqualTo(agentMerge.getId());
            List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeBusInfoMapper.selectByExample(agentMergeBusInfoExample);
            for (AgentMergeBusInfo agentMergeBusInfo : agentMergeBusInfos) {
                agentMergeBusInfo.setStatus(Status.STATUS_0.status);
                int i = agentMergeBusInfoMapper.updateByPrimaryKeySelective(agentMergeBusInfo);
                if (i != 1) {
                    logger.info("代理商合并修改审批，更新数据失败:{}", cUser);
                    throw new MessageException("更新合并数据失败！");
                }
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentMergeBusInfo.getBusId());
                if(agentBusInfo.getBusStatus().compareTo(BusinessStatus.lock.status)==0){
                    agentBusInfo.setBusStatus(BusinessStatus.Enabled.status);
                    int j = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                    if (j != 1) {
                        logger.info("代理商合并修改审批，更新数据失败:{}", cUser);
                        throw new MessageException("更新合并数据失败！");
                    }
                }
            }
            AgentMerge queryAgentMerge = agentMergeMapper.selectByPrimaryKey(agentMerge.getId());
            if(queryAgentMerge.getCloReviewStatus().compareTo(AgStatus.Approving.getValue())==0){
                BusActRel busActRel = new BusActRel();
                busActRel.setBusId(agentMerge.getId());
                busActRel.setActivId(proIns);
                busActRel.setAgentId(agentMerge.getMainAgentId());
                busActRel.setAgentName(agentMerge.getMainAgentName());
                int i = busActRelMapper.updateByPrimaryKeySelective(busActRel);
                if (i != 1) {
                    throw new MessageException("更新合并工作流关系失败！");
                }
                String strMergeBusIds = agentMerge.getMergeBusIds();
                String[] mergeBusIds = strMergeBusIds.split(",");
                for(int j=0;j<mergeBusIds.length;j++){
                    String mergeBusId = mergeBusIds[j];
                    AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(mergeBusId);
                    agentBusInfo.setBusStatus(BusinessStatus.lock.status);
                    int p = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                    if (p!=1) {
                        throw new MessageException("更新业务锁定失败");
                    }
                }
            }
            //查看被合并代理商有没有合并过
            verifyAgentMerge(agentMerge,agentMerge.getId(),cUser);
            insert(busType,agentMerge.getId(),agentMerge,cUser);

            return AgentResult.ok();
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("更新数据失败！");
        }
    }

    /**
     * 审批结果监听
     * @param proIns
     * @param agStatus
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressAgentMergeActivity(String proIns, BigDecimal agStatus)throws Exception{

        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andStatusEqualTo(Status.STATUS_1.status).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            logger.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            throw new MessageException("审批和数据关系有误");
        }
        BusActRel busActRel = list.get(0);
        busActRel.setActivStatus(AgStatus.getAgStatusString(agStatus));
        int z = busActRelMapper.updateByPrimaryKey(busActRel);
        if(z!=1) {
            logger.info("审批任务结束{}{}，代理商合并更新失败2", proIns, agStatus);
            throw new MessageException("代理商合并更新失败");
        }
        AgentMerge agentMerge = agentMergeMapper.selectByPrimaryKey(busActRel.getBusId());
        agentMerge.setCloReviewStatus(agStatus);
        agentMerge.setuTime(new Date());
        int i = agentMergeMapper.updateByPrimaryKeySelective(agentMerge);
        if(i!=1){
            logger.info("审批任务结束{}{}，代理商合并更新失败1", proIns, agStatus);
            throw new MessageException("代理商合并更新失败");
        }
        AgentResult agentResult = AgentResult.fail();
        if(agStatus.compareTo(AgStatus.Refuse.getValue())==0){
            agentResult = cashReceivablesService.refuseProcing(CashPayType.AGENTMERGE,agentMerge.getId(),agentMerge.getcUser());
        }
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            agentResult = cashReceivablesService.finishProcing(CashPayType.AGENTMERGE,agentMerge.getId(),agentMerge.getcUser());
        }
        if(!agentResult.isOK()){
            throw new ProcessException("更新打款记录失败");
        }

        AgentMergeBusInfoExample agentMergeBusInfoExample = new AgentMergeBusInfoExample();
        AgentMergeBusInfoExample.Criteria criteria = agentMergeBusInfoExample.createCriteria();
        criteria.andAgentMargeIdEqualTo(busActRel.getBusId());
        criteria.andMergeStatusEqualTo(MergeStatus.WXS.getValue());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeBusInfoMapper.selectByExample(agentMergeBusInfoExample);
        if(agentMergeBusInfos.size()==0){
            throw new MessageException("查询合并业务失败");
        }
        for (AgentMergeBusInfo agentMergeBusInfo : agentMergeBusInfos) {
            agentMergeBusInfo.setMergeStatus(MergeStatus.SX.getValue());
            //更新合并业务表合并状态为生效
            int j = agentMergeBusInfoMapper.updateByPrimaryKeySelective(agentMergeBusInfo);
            if(j!=1){
                logger.info("审批任务结束{}{}，代理商合并更新合并状态", proIns, agStatus);
                throw new MessageException("代理商合并更新失败");
            }
            //删除副代理商被合并的平台
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentMergeBusInfo.getBusId());
            agentBusInfo.setStatus(Status.STATUS_0.status);
            agentBusInfo.setBusStatus(BusinessStatus.pause.status);
            int k = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
            if(k!=1){
                logger.info("审批任务结束{}{}，代理商合并删除副代理商被合并的平台失败", proIns, agStatus);
                throw new MessageException("代理商合并更新失败");
            }
        }
        //代理商合并之后如果一个业务都没了,基本信息等保留,禁止登陆后台
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria  agentBusInfocriteria = agentBusInfoExample.createCriteria();
        agentBusInfocriteria.andAgentIdEqualTo(agentMerge.getSubAgentId());
        agentBusInfocriteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        if(agentBusInfos.size()==0){
            CuserAgentExample cuserAgentExample = new CuserAgentExample();
            CuserAgentExample.Criteria userAgentCriteria = cuserAgentExample.createCriteria();
            userAgentCriteria.andAgentidEqualTo(agentMerge.getSubAgentId());
            List<CuserAgent> cuserAgents = cUserAgentMapper.selectByExample(cuserAgentExample);
            if(cuserAgents.size()!=1){
                throw new MessageException("代理商信息有误");
            }
            CuserAgent cuserAgent = cuserAgents.get(0);
            int l = cUserMapper.updateStatusByPrimaryKey(Long.valueOf(cuserAgent.getUserid()));
            if(l!=1){
                logger.info("审批任务结束{}{}，代理商合并禁止登陆后台失败", proIns, agStatus);
                throw new MessageException("代理商合并更新失败");
            }
        }
        //如果填写了，合并到那个被合并代理商的分期订单欠款,同步到分润其他扣款
        if(agentMerge.getSuppType().compareTo(mergeSuppType.DLSDK.getValue())==0){
            Map<String,String> reqMap = new HashMap<>();
            String subAgentName = agentMerge.getSubAgentName();
            String subAgentId = agentMerge.getSubAgentId();
            reqMap.put("AGENT_NAME",subAgentName);
            reqMap.put("AGENT_ID",subAgentId);
            //机构和标准一代不存在上级直接传本级
            reqMap.put("PARENT_AGENT_ID",subAgentId);
            reqMap.put("PARENT_AGENT_NAME",subAgentName);
            reqMap.put("RPLACE_AGENT_ID",agentMerge.getSuppAgentId());
            reqMap.put("RPLACE_AGENT_NAME",agentMerge.getSuppAgentName());
            reqMap.put("SUPPLY_AMT",String.valueOf(getSubAgentDebt(subAgentId)));
            reqMap.put("REMARK",agentMerge.getRemark());
            logger.info("代理商合并欠款代理商代扣请求参数：{}",reqMap);
            Map map = profitMergeDeductionServiceImpl.ProfitMergeDeduction(reqMap);
            String rusult_code = String.valueOf(map.get("rusult_code"));
            if(!rusult_code.equals("00")){
                throw new MessageException("欠款同步分润失败");
            }
        }

        //通知业务系统
        updateAgentName(busActRel.getBusId(),agentMergeBusInfos);

        return AgentResult.ok();
    }


    /**
     * 手动更改手刷、POS代理商名称
     * @param busId
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.NOT_SUPPORTED)
    @Override
    public AgentResult updateAgentName(String busId,List<AgentMergeBusInfo> agentMergeBusInfos) throws Exception{

        AgentMerge agentMerge = agentMergeMapper.selectByPrimaryKey(busId);
        if(agentMerge==null){
            throw new MessageException("未找到合并信息");
        }

        List<String> mPosOrgList = new ArrayList<>();
        for (AgentMergeBusInfo agentMergeBusInfo : agentMergeBusInfos) {
            PlatForm platForm = platFormMapper.selectByPlatFormNum(agentMergeBusInfo.getBusPlatform());
            String platType = platForm.getPlatformType();
            if(platType.equals(PlatformType.MPOS.getValue())){
                mPosOrgList.add(agentMergeBusInfo.getBusNum());
                continue;
            }
            //调用Pos
            if(platType.equals(PlatformType.POS.getValue()) || platType.equals(PlatformType.ZPOS.getValue())){

                String subAgentName = StringUtils.isNotBlank(platForm.getPosanameprefix()) ? platForm.getPosanameprefix()+agentMerge.getSubAgentName() : agentMerge.getSubAgentName();
                String agentName = agentMerge.getMainAgentName()+"-"+subAgentName;

                AgentPlatFormSyn record = new AgentPlatFormSyn();
                AgentNotifyVo agentNotifyVo = new AgentNotifyVo();

                agentNotifyVo.setOrgName(agentName);
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentMergeBusInfo.getBusId());
                Agent agent = agentMapper.selectByPrimaryKey(agentBusInfo.getAgentId());
                agentNotifyVo.setUniqueId(agentBusInfo.getId());
                agentNotifyVo.setAgHeadMobile(agent.getAgHeadMobile());
                agentNotifyVo.setUseOrgan(agentBusInfo.getBusUseOrgan());
                agentNotifyVo.setBusPlatform(agentBusInfo.getBusPlatform());
                agentNotifyVo.setBaseMessage(agent);
                agentNotifyVo.setBusMessage(agentBusInfo);
                agentNotifyVo.setHasS0(agentBusInfo.getDredgeS0().equals(new BigDecimal(1))?"0":"1");
                agentNotifyVo.setLoginName(agentBusInfo.getBusLoginNum());
                agentNotifyVo.setBusiType(platType.equals(PlatformType.POS.getValue())?"01":"02"); //cxinfo 新增瑞易送，瑞享送的等平台 pos结构 业务类型 变更
                Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.AGENT.name(), DictGroup.BUS_TYPE.name(), agentBusInfo.getBusType());
                agentNotifyVo.setOrgType(dictByValue.getdItemname().contains(OrgType.STR.getContent())?OrgType.STR.getValue():OrgType.ORG.getValue());
                AgentBusInfo agentParent = null;
                if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
                    //取出上级业务
                    agentParent = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
                    if(null!=agentParent){
                        agentNotifyVo.setSupDorgId(agentParent.getBusNum());
                    }
                }
                String sendJson = JsonUtil.objectToJson(agentNotifyVo);
                record.setId(idService.genId(TabId.a_agent_platformsyn));
                record.setNotifyTime(new Date());
                record.setBusId(busId);
                record.setPlatformCode(agentMergeBusInfo.getBusPlatform());
                record.setVersion(Status.STATUS_1.status);
                record.setcTime(new Date());
                record.setNotifyStatus(Status.STATUS_0.status);
                record.setNotifyCount(Status.STATUS_1.status);
                record.setcUser(agentMergeBusInfo.getcUser());
                record.setSendJson(sendJson);
                record.setNotifyType(NotifyType.AgentMerge.getValue());
                AgentResult agentResult =new AgentResult();
                try{
                    agentResult =  agentNotifyService.httpRequestForPos(agentNotifyVo);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("代理商合并申请-更改代理商名称异常，activId：{}" + busId);
                    agentResult.setData(e.getLocalizedMessage());
                }
                //接口请求成功
                if(null!=agentResult && !"".equals(agentResult) && agentResult.isOK()) {
                    //添加请求记录
                    record.setSuccesTime(new Date());
                    record.setNotifyStatus(Status.STATUS_1.status);
                }
                record.setNotifyJson(String.valueOf(agentResult.getData()));
                try {
                    agentPlatFormSynMapper.insert(record);
                } catch (Exception e) {
                    logger.info("代理商合并通知pos异常：{}"+e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        //调用手刷接口
        if(mPosOrgList.size()!=0){
            String agentName = agentMerge.getMainAgentName() + "-" + agentMerge.getSubAgentName();
            List<AgentBusInfo> agentBusInfoList = agentBusinfoService.agentBusInfoList(agentMerge.getMainAgentId());
            AgentBusInfo busInfo = agentBusInfoList.get(0);
            PayComp payComp = apaycompService.selectById(busInfo.getCloPayCompany());
            AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(agentMerge.getMainAgentId(), busInfo.getId());
            agentColinfo.setAccountId(payComp.getId());
            agentColinfo.setAccountName(payComp.getComName());
            AgentPlatFormSyn record = new AgentPlatFormSyn();
            String id = idService.genId(TabId.a_agent_platformsyn);
            record.setSendJson("agentName:"+agentName+",mPosOrgList:"+mPosOrgList.toString()+",agentColinfo:"+JsonUtil.objectToJson(agentColinfo));
            AgentResult agentResult = new AgentResult();
            try {
               agentResult = mPos_updateAgName(agentName, mPosOrgList, agentColinfo);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("代理商合并申请-更改代理商名称异常，activId：{}" + busId);
                agentResult.setData(e.getLocalizedMessage());
            }
            record.setId(id);
            record.setNotifyTime(new Date());
            record.setBusId(busId);
            record.setPlatformCode("999999");  //手刷
            record.setVersion(Status.STATUS_1.status);
            record.setcTime(new Date());
            record.setNotifyStatus(Status.STATUS_0.status);
            record.setNotifyCount(Status.STATUS_1.status);
            record.setcUser(busInfo.getcUser());
            record.setNotifyJson(String.valueOf(agentResult.getData()));
            record.setNotifyType(NotifyType.AgentMerge.getValue());
            //接口请求成功
            if(agentResult.isOK()) {
                //添加请求记录
                record.setSuccesTime(new Date());
                record.setNotifyStatus(Status.STATUS_1.status);
            }
            try {
                agentPlatFormSynMapper.insert(record);
            } catch (Exception e) {
                logger.info("代理商合并通知手刷异常：{}"+e.getMessage());
                e.printStackTrace();
            }
        }
        return AgentResult.ok();
    }

    /**
     * 通知手刷
     * @param agentName
     * @param mPosOrgList
     * @param agentColinfo
     * @return
     */
    public AgentResult mPos_updateAgName(String agentName, List<String> mPosOrgList, AgentColinfo agentColinfo) {
        Map<String,Object> map = new HashMap<>();
        map.put("companyname", agentName);//代理商名称
        map.put("batchIds", JsonUtil.objectToJson(mPosOrgList));//AG码
        Map<String,Object> colinfoMap = new HashMap<>();
        colinfoMap.put("cloType",String.valueOf(agentColinfo.getCloType()));
        colinfoMap.put("cloRealname",agentColinfo.getCloRealname());
        colinfoMap.put("cloBank",agentColinfo.getCloBank());
        colinfoMap.put("cloBankBranch",agentColinfo.getCloBankBranch());
        colinfoMap.put("cloBankAccount",agentColinfo.getCloBankAccount());
        colinfoMap.put("branchLineNum",agentColinfo.getBranchLineNum());
        colinfoMap.put("allLineNum",agentColinfo.getAllLineNum());
        colinfoMap.put("accountId",agentColinfo.getAccountId());
        colinfoMap.put("accountName",agentColinfo.getAccountName());
        map.put("colinfoMessage", colinfoMap);//收款账户
        String params = JsonUtil .objectToJson(map);
        logger.info("代理商合并通知手刷请求:{}",params);
        String res = HttpClientUtil.doPostJson(AppConfig.getProperty("busiPlat.upAgName"),params);
        logger.info("代理商合并通知手刷结果:{}",res);
        JSONObject resObj = JSONObject.parseObject(res);
        if(!resObj.get("respCode").equals("000000")){
            logger.error("请求失败！");
            AppConfig.sendEmails("代理商更名失败:"+res,"代理商更名失败");
            return AgentResult.fail(resObj.toString());
        }
        logger.info("代理商更名成功！{}",resObj.get("respMsg"));
        return AgentResult.ok(res);
    }


    /**
     * 手动调用
     * @param busId
     * @param platformCode
     * @throws Exception
     */
    @Override
    public void manualAgentMergeNotify(String busId,String platformCode) throws Exception{

        AgentMergeBusInfoExample agentMergeBusInfoExample = new AgentMergeBusInfoExample();
        AgentMergeBusInfoExample.Criteria criteria = agentMergeBusInfoExample.createCriteria();
        criteria.andAgentMargeIdEqualTo(busId);
        if(!platformCode.equals("999999")){//手刷
            criteria.andBusPlatformEqualTo(platformCode);
        }
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeBusInfoMapper.selectByExample(agentMergeBusInfoExample);
        if(agentMergeBusInfos.size()==0){
            throw new MessageException("查询合并业务失败");
        }
        updateAgentName(busId,agentMergeBusInfos);
    }

    /**
     * 删除合并业务数据
     * @param mergeId
     * @param cUser
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult deleteAgentMerge(String mergeId, String cUser) throws Exception {
        if (StringUtils.isBlank(mergeId)) {
            throw new MessageException("数据ID为空！");
        }
        AgentMerge agentMerge = agentMergeMapper.selectByPrimaryKey(mergeId);
        agentMerge.setStatus(Status.STATUS_0.status);
        agentMerge.setuTime(new Date());
        agentMerge.setuUser(cUser);
        if (1 != agentMergeMapper.updateByPrimaryKeySelective(agentMerge)) {
            throw new MessageException("合并数据处理失败！");
        }
        AgentMergeBusInfoExample agentMergeBusInfoExample = new AgentMergeBusInfoExample();
        AgentMergeBusInfoExample.Criteria criteria = agentMergeBusInfoExample.createCriteria();
        criteria.andAgentMargeIdEqualTo(mergeId);
        List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeBusInfoMapper.selectByExample(agentMergeBusInfoExample);
        for (AgentMergeBusInfo agentMergeBusInfo : agentMergeBusInfos) {
            agentMergeBusInfo.setStatus(Status.STATUS_0.status);
            agentMergeBusInfo.setcUtime(new Date());
            agentMergeBusInfo.setcUser(cUser);
            if (1 != agentMergeBusInfoMapper.updateByPrimaryKeySelective(agentMergeBusInfo)) {
                throw new MessageException("合并业务数据处理失败！");
            }
        }
        return AgentResult.ok();
    }

    /**
     * 合并业务明细列表
     * @param agentMergeBusInfo
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    @Override
    public PageInfo selectMergeBusinfoList(AgentMergeBusInfo agentMergeBusInfo, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(agentMergeBusInfo.getAgentMargeId())) {
            reqMap.put("agentMargeId", agentMergeBusInfo.getAgentMargeId());
        }
        if (StringUtils.isNotBlank(agentMergeBusInfo.getBusId())) {
            reqMap.put("busId", agentMergeBusInfo.getBusId());
        }
        if (StringUtils.isNotBlank(agentMergeBusInfo.getMainAgentId())) {
            reqMap.put("mainAgentId", agentMergeBusInfo.getMainAgentId());
        }
        if (StringUtils.isNotBlank(agentMergeBusInfo.getSubAgentId())) {
            reqMap.put("subAgentId", agentMergeBusInfo.getSubAgentId());
        }
        if(StringUtils.isBlank(dataRole)){
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if(orgCodeRes==null && orgCodeRes.size()!=1){
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            reqMap.put("orgId",String.valueOf(stringObjectMap.get("ORGID")));
        }
        List<Map<String,Object>> agentMergeList = agentMergeBusInfoMapper.selectMergeBusinfoList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentMergeList);
        pageInfo.setTotal(agentMergeBusInfoMapper.selectMergeBusinfoCount(reqMap));
        return pageInfo;
    }


    /**
     * 欠款
     * @param agentId
     * @return
     */
    @Override
    public BigDecimal getSubAgentDebt(String agentId){
        //订单欠款
        BigDecimal orderDebt = orderService.queryAgentDebt(agentId);
        logger.info("代理商合并查询订单欠款：代理商id:{},欠款：{}",agentId,orderDebt);
        Map<String,String> reqMap = new HashMap<>();
        reqMap.put("agentId",agentId);
        Map<String, BigDecimal> notDeduction = profitDeductionServiceImpl.getNotDeduction(reqMap);
        BigDecimal checkNotDeductionAmt = notDeduction.get("checkNotDeductionAmt");
        BigDecimal bLNotDeductionAmt = notDeduction.get("BLNotDeductionAmt");
        BigDecimal otherNotDeductionAmt = notDeduction.get("otherNotDeductionAmt");
        BigDecimal chargeBackNotDeductionAmt = notDeduction.get("chargeBackNotDeductionAmt");
        BigDecimal toolNotDeductionAmt = notDeduction.get("ToolNotDeductionAmt");

        BigDecimal sum = new BigDecimal(0);
        sum = sum.add(orderDebt).add(checkNotDeductionAmt).add(bLNotDeductionAmt).add(otherNotDeductionAmt).
                add(chargeBackNotDeductionAmt).add(toolNotDeductionAmt);
        return sum;
    }

    /**
     * 欠票
     * @param agentId
     * @return
     */
    @Override
    public BigDecimal getSubAgentOweTicket(String agentId){
        Map<String,String> reqMap = new HashMap<>();
        reqMap.put("agentId",agentId);
        Map<String, Object> resultMap = ownInvoiceService.getOwmInvoice(reqMap);
        logger.info("代理商合并查询欠票：代理商id:{},欠票：{}",resultMap);
        if(resultMap==null){
            return new BigDecimal(0);
        }
        String owminvoice = String.valueOf(resultMap.get("OWMINVOICE"));
        return new BigDecimal(owminvoice);
    }
}
