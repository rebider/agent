package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.OPaymentDetailExample;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.CapitalChangeApplyService;
import com.ryx.credit.service.agent.CapitalService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.IPaymentDetailService;
import com.ryx.credit.service.order.OCashReceivablesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by RYX on 2019/2/12.
 * 保证金变更申请
 */
@Service("capitalChangeApplyService")
public class CapitalChangeApplyServiceImpl implements CapitalChangeApplyService {

    private static Logger logger = LoggerFactory.getLogger(CapitalChangeApplyServiceImpl.class);
    @Autowired
    private CapitalChangeApplyMapper capitalChangeApplyMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private OCashReceivablesService cashReceivablesService;
    @Autowired
    private IdService idService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private CapitalFlowMapper capitalFlowMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private IPaymentDetailService paymentDetailService;
    @Autowired
    private OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    private CapitalService capitalService;
    @Autowired
    private CapitalChangeApplyService capitalChangeApplyService;

    /**
     * 保证金列表
     * @param capitalChangeApply
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    @Override
    public PageInfo queryCapitalChangeList(CapitalChangeApply capitalChangeApply, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(capitalChangeApply.getId())) {
            reqMap.put("id", capitalChangeApply.getId());
        }
        if (StringUtils.isNotBlank(capitalChangeApply.getAgentId())) {
            reqMap.put("agentId", capitalChangeApply.getAgentId());
        }
        if (StringUtils.isNotBlank(capitalChangeApply.getAgentName())) {
            reqMap.put("agentName", capitalChangeApply.getAgentName());
        }
        if (null != capitalChangeApply.getCloReviewStatus()) {
            reqMap.put("cloReviewStatus", capitalChangeApply.getCloReviewStatus());
        }
//        if(StringUtils.isBlank(dataRole)){
//            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
//            if(orgCodeRes == null && orgCodeRes.size() != 1){
//                return null;
//            }
//            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
//            reqMap.put("orgId", String.valueOf(stringObjectMap.get("ORGID")));
//        }
        List<Map<String, Object>> capitalChangeList = capitalChangeApplyMapper.queryCapitalChangeList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(capitalChangeList);
        pageInfo.setTotal(capitalChangeApplyMapper.queryCapitalChangeCount(reqMap));
        return pageInfo;
    }

    /**
     * 查看申请数据
     * @param capitalId
     * @return
     */
    @Override
    public CapitalChangeApply queryCapitalChangeById(String capitalId) {
        if (StringUtils.isBlank(capitalId)) {
            return null;
        }
        CapitalChangeApply capitalChangeApply = capitalChangeApplyMapper.selectByPrimaryKey(capitalId);
        if (null == capitalChangeApply) {
            return null;
        }
        //查询关联附件
        List<Attachment> attachments = attachmentMapper.accessoryQuery(capitalChangeApply.getId(), AttachmentRelType.capitalManage.name());
        capitalChangeApply.setAttachments(attachments);
        //查询关联附件
        List<Attachment> financeAttachments = attachmentMapper.accessoryQuery(capitalChangeApply.getId(), AttachmentRelType.capitalFinance.name());
        capitalChangeApply.setFinanceAttachments(financeAttachments);
        return capitalChangeApply;
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult saveCapitalChange(CapitalChangeApply capitalChangeApply, String[] capitalChangeFiles, String cUser,
                                     String saveFlag, List<OCashReceivablesVo> oCashReceivables)throws Exception {
        try {
            if(StringUtils.isBlank(capitalChangeApply.getAgentId())){
                throw new MessageException("代理商ID为空！");
            }
            String applyId = idService.genId(TabId.A_CAPITAL_CHANGE_APPLY);
            capitalChangeApply.setId(applyId);
            Agent agent = agentMapper.selectByPrimaryKey(capitalChangeApply.getAgentId());
            if (capitalChangeApply.getOperationType().compareTo(OperationType.KQ.getValue()) == 0) {
                capitalChangeApply.setRealOperationAmt(capitalChangeApply.getOperationAmt().add(capitalChangeApply.getServiceCharge()));
            }else if(capitalChangeApply.getOperationType().compareTo(OperationType.TK.getValue()) == 0){
                //处理金额 + 手续费-机具抵扣金额 = 最终打款金额
                BigDecimal subtract = capitalChangeApply.getOperationAmt().subtract(capitalChangeApply.getServiceCharge()).subtract(capitalChangeApply.getMachinesDeptAmt());
                if(subtract.signum()==-1){
                    throw new MessageException("实际打款金额必须大于0");
                }
                capitalChangeApply.setRealOperationAmt(subtract);
            }else{
                throw new MessageException("处理类型错误！");
            }

            capitalChangeApply.setAgentName(agent.getAgName());
            if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
                capitalChangeApply.setCloReviewStatus(AgStatus.Approving.status);
            } else {
                capitalChangeApply.setCloReviewStatus(AgStatus.Create.status);
            }
            capitalChangeApply.setcUser(cUser);
            capitalChangeApply.setuUser(cUser);
            capitalChangeApply.setcTime(new Date());
            capitalChangeApply.setuTime(new Date());
            capitalChangeApply.setStatus(Status.STATUS_1.status);
            capitalChangeApply.setVersion(BigDecimal.ONE);
            //校验
            verify(capitalChangeApply);
            int i = capitalChangeApplyMapper.insertSelective(capitalChangeApply);
            if(i!=1){
                throw new MessageException("保存退出申请失败！");
            }

            //添加新的附件
            if (capitalChangeFiles != null && capitalChangeFiles.length!=0) {
                for(int j=0;j<capitalChangeFiles.length;j++){
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(capitalChangeFiles[j]);
                    record.setSrcId(applyId);
                    record.setcUser(cUser);
                    record.setcTime(Calendar.getInstance().getTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.capitalManage.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    int f = attachmentRelMapper.insertSelective(record);
                    if (1 != f) {
                        logger.info("代理商退出保存附件关系失败");
                        throw new ProcessException("保存附件失败");
                    }
                }
            }
            //打款记录
            AgentResult agentResult = cashReceivablesService.addOCashReceivables(oCashReceivables,cUser,capitalChangeApply.getAgentId(),CashPayType.CAPITALCHANGE,applyId);
            if(!agentResult.isOK()){
                logger.info("代理商退出保存打款记录失败1");
                throw new ProcessException("保存打款记录失败");
            }
            if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
                startAgentMergeActivity(applyId, cUser,true);
            }
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return AgentResult.ok();
    }


    public void verify(CapitalChangeApply capitalChangeApply)throws MessageException {

        if(null==capitalChangeApply.getOperationAmt()){
            throw new MessageException("操作金额必填！");
        }
        if(null==capitalChangeApply.getServiceCharge()){
            throw new MessageException("手续费必填！");
        }
        if(null==capitalChangeApply.getMachinesDeptAmt()){
            throw new MessageException("机具欠款金额必填！");
        }

        if(capitalChangeApply.getOperationAmt().compareTo(BigDecimal.ZERO)==-1){
            throw new MessageException("处理金额必须大于0！");
        }
        if(capitalChangeApply.getServiceCharge().compareTo(BigDecimal.ZERO)==-1){
            throw new MessageException("手续费必须大于等于0！");
        }
        if(capitalChangeApply.getOperationAmt().compareTo(capitalChangeApply.getMachinesDeptAmt())==-1){
            throw new MessageException("处理金额必须大于等于抵扣机具欠款金额！");
        }
        CapitalExample capitalExample = new CapitalExample();
        CapitalExample.Criteria criteria = capitalExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andCAgentIdEqualTo(capitalChangeApply.getAgentId());
        criteria.andCTypeEqualTo(capitalChangeApply.getCapitalType());
        List<Capital> capitals = capitalMapper.selectByExample(capitalExample);
        for (Capital capital : capitals) {
            if(capital.getFreezeAmt().compareTo(BigDecimal.ZERO)!=0){
                throw new MessageException("缴纳款冻结中请勿重复提交！");
            }
        }
        if(capitalChangeApply.getOperationType().compareTo(OperationType.KQ.getValue())==0){
            if(capitalChangeApply.getRealOperationAmt().compareTo(capitalChangeApply.getCapitalAmt())==1){
                throw new MessageException("处理金额不能大于剩余金额！");
            }
        }else if(capitalChangeApply.getOperationType().compareTo(OperationType.TK.getValue())==0){
            BigDecimal amt = capitalChangeApply.getOperationAmt().subtract(capitalChangeApply.getServiceCharge());
            if(amt.compareTo(capitalChangeApply.getCapitalAmt())==1){
                throw new MessageException("处理金额不能大于剩余金额！");
            }
        }else{
            throw new MessageException("处理类型错误！");
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
        CapitalChangeApply capitalChangeApply = capitalChangeApplyMapper.selectByPrimaryKey(id);
        if (capitalChangeApply == null) {
            throw new MessageException("提交审批信息有误！");
        }
        if (!isSave) {
            capitalChangeApply.setuUser(cUser);
            capitalChangeApply.setuTime(new Date());
            capitalChangeApply.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != capitalChangeApplyMapper.updateByPrimaryKeySelective(capitalChangeApply)) {
                throw new MessageException("提交审批处理失败！");
            }
        }
        //锁定金额
        disposeAmt(capitalChangeApply);

        AgentResult agentResult = cashReceivablesService.startProcing(CashPayType.CAPITALCHANGE,id,cUser);
        if(!agentResult.isOK()){
            logger.info("代理商退出更新打款信息失败");
            throw new MessageException("代理商退出更新打款信息失败");
        }

        Map startPar = agentEnterService.startPar(cUser);
        if (null == startPar) {
            logger.info("========用户{}{}启动部门参数为空", id, cUser);
            throw new MessageException("启动部门参数为空!");
        }
        startPar.put("rs","pass");
        //启动审批
        String proce = activityService.createDeloyFlow(null, "capitalChange1.0", null, null, startPar);
        if (proce == null) {
            logger.info("退补差价提交审批，审批流启动失败{}:{}", id, cUser);
            throw new MessageException("审批流启动失败!");
        }

        //代理商业务&工作流关系
        BusActRel record = new BusActRel();
        record.setBusId(id);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cUser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.capitalChange.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(capitalChangeApply.getAgentId());
        record.setAgentName(capitalChangeApply.getAgentName());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("代理商退出提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("审批流启动失败：添加审批关系失败！");
        }
        return AgentResult.ok();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalCapitalChangeTask(AgentVo agentVo, String userId, String busId) throws Exception {
        try {
            if (agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())) {
                List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(userId));
                if (null == orgCodeRes) {
                    throw new ProcessException("部门参数为空！");
                }
                Map<String, Object> map = orgCodeRes.get(0);
                Object orgCode = map.get("ORGANIZATIONCODE");
                CapitalChangeApply capitalChangeApply = capitalChangeApplyMapper.selectByPrimaryKey(busId);
                agentVo.setOperationType(String.valueOf(capitalChangeApply.getOperationType()));
                agentVo.setAmt(capitalChangeApply.getRealOperationAmt());
                //财务审批
                if (String.valueOf(orgCode).equals("finance")) {
                    AgentResult cashAgentResult = cashReceivablesService.approveTashBusiness(
                            CashPayType.CAPITALCHANGE, busId, userId, new Date(), agentVo.getoCashReceivablesVoList());
                    if (!cashAgentResult.isOK()) {
                        throw new ProcessException("更新收款信息失败！");
                    }
                    if (agentVo.getCapitalChangeFinaFiles() == null) {
                        throw new ProcessException("请上传打款截图");
                    }
                    if (agentVo.getCapitalChangeFinaFiles().size()==0) {
                        throw new ProcessException("请上传打款截图");
                    }
                    if (StringUtils.isBlank(agentVo.getRemitTimeStr())) {
                        throw new ProcessException("请填写打款时间");
                    }
                    if (StringUtils.isBlank(agentVo.getRemitPerson())) {
                        throw new ProcessException("请填写打款人");
                    }
                    Date format = DateUtil.format(agentVo.getRemitTimeStr());
                    capitalChangeApply.setRemitTime(format);
                    capitalChangeApply.setRemitPerson(agentVo.getRemitPerson());
                    int i = capitalChangeApplyService.updateByPrimaryKeySelective(capitalChangeApply);
                    if(i!=1){
                        throw new ProcessException("更新打款信息失败");
                    }

                    //添加新的附件
                    for (String capitalFile : agentVo.getCapitalChangeFinaFiles()) {
                        AttachmentRel record = new AttachmentRel();
                        record.setAttId(capitalFile);
                        record.setSrcId(busId);
                        record.setcUser(userId);
                        record.setcTime(Calendar.getInstance().getTime());
                        record.setStatus(Status.STATUS_1.status);
                        record.setBusType(AttachmentRelType.capitalFinance.name());
                        record.setId(idService.genId(TabId.a_attachment_rel));
                        int f = attachmentRelMapper.insertSelective(record);
                        if (1 != f) {
                            logger.info("代理商退出保存附件关系失败");
                            throw new ProcessException("保存附件失败");
                        }
                    }
                }
            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo, userId);
            if (!result.isOK()) {
                logger.error(result.getMsg());
                throw new MessageException("工作流处理任务异常！");
            }
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException(e.getMessage());
        }
        return AgentResult.ok();
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressCapitalChangeActivity(String proIns, BigDecimal agStatus) throws Exception {
        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andStatusEqualTo(Status.STATUS_1.status).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            logger.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            throw new MessageException("审批和数据关系有误！");
        }
        BusActRel busActRel = list.get(0);
        busActRel.setActivStatus(AgStatus.getAgStatusString(agStatus));
        if(1 != busActRelMapper.updateByPrimaryKey(busActRel)) {
            logger.info("审批任务结束{}{}，保证金变更申请更新失败-busActRel", proIns, agStatus);
            throw new MessageException("保证金变更申请更新失败！");
        }
        CapitalChangeApply capitalChangeApply = capitalChangeApplyMapper.selectByPrimaryKey(busActRel.getBusId());
        capitalChangeApply.setCloReviewStatus(agStatus);
        capitalChangeApply.setuTime(Calendar.getInstance().getTime());
        if (1 != capitalChangeApplyMapper.updateByPrimaryKeySelective(capitalChangeApply)) {
            logger.info("审批任务结束{}{}，保证金变更申请更新失败-capitalChangeApply", proIns, agStatus);
            throw new MessageException("保证金变更申请更新失败！");
        }
        AgentResult agentResult = AgentResult.fail();
        if(agStatus.compareTo(AgStatus.Refuse.getValue())==0){
            agentResult = cashReceivablesService.refuseProcing(CashPayType.CAPITALCHANGE,capitalChangeApply.getId(),capitalChangeApply.getcUser());
        }
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            agentResult = cashReceivablesService.finishProcing(CashPayType.CAPITALCHANGE,capitalChangeApply.getId(),capitalChangeApply.getcUser());
        }
        if(!agentResult.isOK()){
            throw new ProcessException("更新打款记录失败");
        }
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            CapitalExample capitalExample = new CapitalExample();
            CapitalExample.Criteria criteria = capitalExample.createCriteria();
            criteria.andCAgentIdEqualTo(capitalChangeApply.getAgentId());
            criteria.andCTypeEqualTo(capitalChangeApply.getCapitalType());
            List<Capital> capitals = capitalMapper.selectByExample(capitalExample);
            for (Capital capital : capitals) {
                capital.setFreezeAmt(BigDecimal.ZERO);
                int i = capitalMapper.updateByPrimaryKeySelective(capital);
                if(i!=1){
                    throw new MessageException("通过更新冻结金额失败！");
                }
                CapitalFlowExample capitalFlowExample = new CapitalFlowExample();
                CapitalFlowExample.Criteria criteria1 = capitalFlowExample.createCriteria();
                criteria1.andStatusEqualTo(Status.STATUS_1.status);
                criteria1.andCapitalIdEqualTo(capital.getId());
                List<CapitalFlow> capitalFlows = capitalFlowMapper.selectByExample(capitalFlowExample);
                for (CapitalFlow capitalFlow : capitalFlows) {
                    capitalFlow.setFlowStatus(Status.STATUS_1.status);
                    int j = capitalFlowMapper.updateByPrimaryKey(capitalFlow);
                    if(j!=1){
                        throw new MessageException("通过更新资金流水记录失败！");
                    }
                }
            }
            if (capitalChangeApply.getOperationType().compareTo(OperationType.TK.getValue()) == 0) {
                OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
                OPaymentDetailExample.Criteria detailcriteria = oPaymentDetailExample.createCriteria();
                detailcriteria.andSrcIdEqualTo(capitalChangeApply.getId());
                detailcriteria.andStatusEqualTo(Status.STATUS_1.status);
                List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
                for (OPaymentDetail oPaymentDetail : oPaymentDetails) {
                    if(oPaymentDetail.getRealPayAmount().compareTo(BigDecimal.ZERO)==0){
                        oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
                        oPaymentDetail.setSrcId("");
                        oPaymentDetail.setSrcType("");
                    }else{
                        //如果不相等更新成部分付款
                        if(oPaymentDetail.getRealPayAmount().compareTo(oPaymentDetail.getPayAmount())==0){
                            oPaymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
                        }else{
                            oPaymentDetail.setPaymentStatus(PaymentStatus.BF.code);
                        }
                    }
                    int i = oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail);
                    if(i!=1){
                        throw new MessageException("通过更新付款明细失败！");
                    }
                }
            }
        }else{
            CapitalExample capitalExample = new CapitalExample();
            CapitalExample.Criteria criteria = capitalExample.createCriteria();
            criteria.andCAgentIdEqualTo(capitalChangeApply.getAgentId());
            criteria.andCTypeEqualTo(capitalChangeApply.getCapitalType());
            List<Capital> capitals = capitalMapper.selectByExample(capitalExample);
            for (Capital capital : capitals) {
                capital.setcFqInAmount(capital.getcFqInAmount().add(capital.getFreezeAmt()));
                capital.setFreezeAmt(BigDecimal.ZERO);
                int i = capitalMapper.updateByPrimaryKeySelective(capital);
                if(i!=1){
                    throw new MessageException("拒绝更新冻结金额失败！");
                }
            }
            if (capitalChangeApply.getOperationType().compareTo(OperationType.TK.getValue()) == 0) {
                OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
                OPaymentDetailExample.Criteria detailcriteria = oPaymentDetailExample.createCriteria();
                detailcriteria.andSrcIdEqualTo(capitalChangeApply.getId());
                detailcriteria.andStatusEqualTo(Status.STATUS_1.status);
                List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
                for (OPaymentDetail oPaymentDetail : oPaymentDetails) {
                    if(oPaymentDetail.getRealPayAmount().compareTo(BigDecimal.ZERO)==0){
                        oPaymentDetail.setStatus(Status.STATUS_0.status);
                        int i = oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail);
                        if(i!=1){
                            throw new MessageException("拒绝更新付款明细失败！");
                        }
                    }else {
                        oPaymentDetail.setRealPayAmount(BigDecimal.ZERO);
                        oPaymentDetail.setPayTime(null);
                        oPaymentDetail.setSrcId("");
                        oPaymentDetail.setSrcType("");
                        oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
                        int i = oPaymentDetailMapper.updateCapitalById(oPaymentDetail);
                        if(i!=1){
                            throw new MessageException("拒绝更新付款明细失败！");
                        }
                    }
                }
            }
        }
        return AgentResult.ok();
    }

    /**
     * 锁定扣除金额
     * @param capitalChangeApply
     * @throws MessageException
     */
    public void disposeAmt(CapitalChangeApply capitalChangeApply)throws Exception {

        CapitalExample capitalExample = new CapitalExample();
        CapitalExample.Criteria criteria = capitalExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andCAgentIdEqualTo(capitalChangeApply.getAgentId());
        criteria.andCTypeEqualTo(capitalChangeApply.getCapitalType());
        capitalExample.setOrderByClause(" c_fq_in_amount asc");
        List<Capital> capitals = capitalMapper.selectByExample(capitalExample);
        if (capitalChangeApply.getOperationType().compareTo(OperationType.KQ.getValue()) == 0) {
            capitalService.disposeCapital(capitals,capitalChangeApply.getRealOperationAmt(),capitalChangeApply.getId(),
                    capitalChangeApply.getcUser(),capitalChangeApply.getAgentId(),capitalChangeApply.getAgentName(),"保证金扣款");
        } else if (capitalChangeApply.getOperationType().compareTo(OperationType.TK.getValue()) == 0) {
            //查询所有机具欠款
            BigDecimal sumDebt = paymentDetailService.getSumDebt(capitalChangeApply.getAgentId());
            capitalService.disposeCapital(capitals,capitalChangeApply.getOperationAmt(), capitalChangeApply.getId(),capitalChangeApply.getcUser(),
                                          capitalChangeApply.getAgentId(),capitalChangeApply.getAgentName(),"保证金扣款");
            if(capitalChangeApply.getMachinesDeptAmt().signum()==-1){
                throw new MessageException("抵扣金额必须是正数！");
            }
            if(capitalChangeApply.getMachinesDeptAmt().compareTo(BigDecimal.ZERO)==1){
                if(capitalChangeApply.getMachinesDeptAmt().compareTo(sumDebt)==1){
                    throw new MessageException("抵扣金额不能大于欠款！");
                }
                HashMap<String, Object> reqMap = new HashMap<>();
                reqMap.put("agentId", capitalChangeApply.getAgentId());
                List<Map<String, Object>> debtList = oPaymentDetailMapper.getOrderDebt(reqMap);
                //剩余金额
                BigDecimal residueAmt = capitalChangeApply.getMachinesDeptAmt();
                for (Map<String, Object> debtMap : debtList) {
                    String paymentDetailId = String.valueOf(debtMap.get("ID"));
                    BigDecimal payAmount = new BigDecimal(debtMap.get("PAY_AMOUNT").toString());
                    BigDecimal subtract = payAmount.subtract(residueAmt);
                    //说明未扣足
                    if(subtract.signum()==-1){
                        //更新付款明细表  实际金额等于付款金额
                        OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectById(paymentDetailId);
                        oPaymentDetail.setRealPayAmount(oPaymentDetail.getPayAmount());
                        oPaymentDetail.setPayTime(new Date());
                        oPaymentDetail.setSrcId(capitalChangeApply.getId());
                        oPaymentDetail.setSrcType(PamentSrcType.CAPITAL_DIKOU.code);
                        oPaymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
                        int i = oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail);
                        if(i!=1){
                            throw new MessageException("更新付款明细！");
                        }
                        residueAmt = subtract.abs();
                    }else{
                        //没扣完或刚好扣足
                        OPaymentDetail oPaymentDetail = oPaymentDetailMapper.selectById(paymentDetailId);
                        oPaymentDetail.setRealPayAmount(residueAmt);
                        oPaymentDetail.setPayTime(new Date());
                        oPaymentDetail.setSrcId(capitalChangeApply.getId());
                        oPaymentDetail.setSrcType(PamentSrcType.CAPITAL_DIKOU.code);
                        oPaymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
                        int i = oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail);
                        if(i!=1){
                            throw new MessageException("更新付款明细！");
                        }
                        //如果未扣完
                        if(subtract.signum()==1){
                            //从新生成付款明细
                            oPaymentDetail.setId(idService.genId(TabId.o_payment_detail));
                            oPaymentDetail.setPayAmount(subtract);
                            oPaymentDetail.setRealPayAmount(BigDecimal.ZERO);
                            oPaymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
                            oPaymentDetail.setcUser(capitalChangeApply.getcUser());
                            oPaymentDetail.setcDate(new Date());
                            oPaymentDetail.setPayTime(null);
                            oPaymentDetail.setSrcId(capitalChangeApply.getId());
                            oPaymentDetail.setSrcType(PamentSrcType.CAPITAL_DIKOU.code);
                            oPaymentDetailMapper.insertSelective(oPaymentDetail);
                        }
                        break;
                    }
                }
            }else{
                //如果机具欠款大于0必须抵扣
                if(sumDebt.compareTo(BigDecimal.ZERO)==1){
                    throw new MessageException("机具欠款大于0必须填写抵扣金额！");
                }
            }
        } else {
            throw new MessageException("处理类型错误！");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKeySelective(CapitalChangeApply capitalChangeApply){
        return capitalChangeApplyMapper.updateByPrimaryKeySelective(capitalChangeApply);
    }
}
