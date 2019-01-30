package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.AgentQuitRefundService;
import com.ryx.credit.service.dict.IdService;
import oracle.jdbc.driver.DatabaseError;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Lihl
 * @Date 2019/1/28
 * @Desc 代理商退出--申请退款
 */
@Service("agentQuitRefundService")
public class AgentQuitRefundServiceImpl implements AgentQuitRefundService {

    private static Logger logger = LoggerFactory.getLogger(AgentQuitRefundServiceImpl.class);
    @Autowired
    private AgentQuitRefundMapper agentQuitRefundMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentQuitMapper agentQuitMapper;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;


    /**
     * 申请退款列表
     * @param agentQuitRefund
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    @Override
    public PageInfo queryQuitRefundList(AgentQuitRefund agentQuitRefund, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(agentQuitRefund.getQuitId())) {
            reqMap.put("quitId", agentQuitRefund.getQuitId());
        }
        if (StringUtils.isNotBlank(agentQuitRefund.getAgentId())) {
            reqMap.put("agentId", agentQuitRefund.getAgentId());
        }
        if (StringUtils.isNotBlank(agentQuitRefund.getAgentName())) {
            reqMap.put("agentName", agentQuitRefund.getAgentName());
        }
        if (null != agentQuitRefund.getCloReviewStatus()) {
            reqMap.put("cloReviewStatus", agentQuitRefund.getCloReviewStatus());
        }
//        if(StringUtils.isBlank(dataRole)){
//            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
//            if(orgCodeRes == null && orgCodeRes.size() != 1){
//                return null;
//            }
//            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
//            reqMap.put("orgId", String.valueOf(stringObjectMap.get("ORGID")));
//        }
        List<Map<String, Object>> agentMergeList = agentQuitRefundMapper.queryQuitRefundList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentMergeList);
        pageInfo.setTotal(agentQuitRefundMapper.queryQuitRefundCount(reqMap));
        return pageInfo;
    }

    @Test
    public void testDays() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        Date nowDate = sdf.parse(date);//当前时间
        Date approveTime = sdf.parse(String.valueOf("2019-01-01"));//审批通过时间
        BigDecimal refundAmtDeadline = BigDecimal.valueOf(180);//申请退款期限
        long days = (nowDate.getTime() - approveTime.getTime()) / (24 * 60 * 60 * 1000);//天数=当前时间-审批通过时间
        if (new BigDecimal(days).compareTo(refundAmtDeadline) < 0) {
            logger.info("不满足180天！");
        }
    }

    /**
     * 判断申请退款的条件是否满足
     * @param agentQuitRefund
     * @throws Exception
     */
    public void whetherSatisfyRefund(AgentQuitRefund agentQuitRefund) throws Exception {
        AgentQuitExample agentQuitExample = new AgentQuitExample();
        AgentQuitExample.Criteria quitCriteria = agentQuitExample.createCriteria();
        quitCriteria.andAgentIdEqualTo(agentQuitRefund.getAgentId());
        quitCriteria.andCloReviewStatusEqualTo(AgStatus.Approved.status);
        quitCriteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentQuit> agentQuitList = agentQuitMapper.selectByExample(agentQuitExample);
        if(agentQuitList.size() == 0){
            throw new MessageException("代理商信息有误!");
        }
        for (AgentQuit agentQuit : agentQuitList) {
            if (!agentQuit.getSuppType().equals(SuppType.G.getValue())) {
                throw new MessageException("代理商补缴类型不是打款公司，不支持此操作！");
            }
            if (agentQuit.getRealitySuppDept().compareTo(BigDecimal.ZERO) < 1) {
                throw new MessageException("实际补缴欠款小于等于0，不支持此操作！");
            }
            if (!agentQuit.getAppRefund().equals(Status.STATUS_1.status)) {
                throw new MessageException("申请退款为否，不支持此操作！");
            }
            if (!agentQuit.getPlatformStatus().equals(PlatformStatus.FAIL.getValue())) {
                throw new MessageException("业务平台状态为失败，不支持此操作！");
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            String time = sdf.format(agentQuit.getApproveTime());
            Date nowDate = sdf.parse(date);//当前时间
            Date approveTime = sdf.parse(time);//审批通过时间
            BigDecimal refundAmtDeadline = agentQuit.getRefundAmtDeadline();//申请退款期限
            long days = (nowDate.getTime() - approveTime.getTime()) / (24 * 60 * 60 * 1000);//天数=当前时间-审批通过时间
            if (new BigDecimal(days).compareTo(refundAmtDeadline) < 0) {
                throw new MessageException("此代理商申请退款期限不满足180天，不支持此操作！");
            }
        }
    }

    /**
     * 申请退款-审批
     * @param agentQuitRefund
     * @param cUser
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startQuitRefundActivity(AgentQuitRefund agentQuitRefund, String cUser) throws Exception {
        //判断申请退款的条件是否满足
        whetherSatisfyRefund(agentQuitRefund);

        if (StringUtils.isBlank(cUser)) {
            logger.info("代理商退出-申请退款提交，操作用户为空:{}", cUser);
            return AgentResult.fail("代理商退出-申请退款提交，操作用户为空！");
        }
        try {
            AgentColinfo agentColinfo = agentColinfoService.selectByAgentId(agentQuitRefund.getAgentId());//获取收款账户信息
            agentQuitRefund.setId(idService.genId(TabId.A_AGENT_QUIT_REFUND));
            agentQuitRefund.setcTime(Calendar.getInstance().getTime());
            agentQuitRefund.setuTime(Calendar.getInstance().getTime());
            agentQuitRefund.setcUser(cUser);
            agentQuitRefund.setuUser(cUser);
            agentQuitRefund.setStatus(Status.STATUS_1.status);
            agentQuitRefund.setVersion(Status.STATUS_1.status);
            agentQuitRefund.setCloType(agentColinfo.getCloType());
            agentQuitRefund.setCloRealname(agentColinfo.getCloRealname());
            agentQuitRefund.setCloBank(agentColinfo.getCloBank());
            agentQuitRefund.setCloBankBranch(agentColinfo.getCloBankBranch());
            agentQuitRefund.setCloBankAccount(agentColinfo.getCloBankAccount());
            agentQuitRefund.setBrachLineNum(agentColinfo.getBranchLineNum());
            agentQuitRefund.setAllLineNum(agentColinfo.getAllLineNum());
            agentQuitRefund.setCloInvoice(agentColinfo.getCloInvoice());
            agentQuitRefund.setCloTaxPoint(agentColinfo.getCloTaxPoint());
            agentQuitRefund.setBankRegion(agentColinfo.getBankRegion());
            agentQuitRefund.setCloBankCode(agentColinfo.getCloBankCode());
            if (1 != agentQuitRefundMapper.insertSelective(agentQuitRefund)) {
                logger.info("代理商退出-申请退款提交审批，新增数据失败:{}", cUser);
                throw new MessageException("代理商退出-申请退款提交审批，新增数据失败！");
            }

            //启动审批流
            Map startPar = agentEnterService.startPar(cUser);
            if (null == startPar) {
                throw new ProcessException("启动部门参数为空！");
            }
            String proceId = activityService.createDeloyFlow(null, "quitCityRefund1.0", null, null, startPar);
            if (proceId == null) {
                logger.info("代理商退出-申请退款提交审批，审批流启动失败{}:{}", agentQuitRefund.getId(), cUser);
                throw new MessageException("审批流启动失败！");
            }
            //代理商业务&工作流关系
            BusActRel record = new BusActRel();
            record.setBusId(agentQuitRefund.getId());
            record.setActivId(proceId);
            record.setcTime(Calendar.getInstance().getTime());
            record.setcUser(cUser);
            record.setStatus(Status.STATUS_1.status);
            record.setBusType(BusActRelBusType.agentQuitRefund.name());
            record.setActivStatus(AgStatus.Approving.name());
            record.setAgentId(agentQuitRefund.getAgentId());
            record.setAgentName(agentQuitRefund.getAgentName());
            if (1 != busActRelMapper.insertSelective(record)) {
                logger.info("代理商退出-申请退款提交审批，启动审批异常，添加审批关系失败{}:{}", agentQuitRefund.getId(), proceId);
                throw new MessageException("审批流启动失败：添加审批关系失败！");
            }
            agentQuitRefund.setCloReviewStatus(AgStatus.Approving.status);//审批中
            agentQuitRefundMapper.updateByPrimaryKeySelective(agentQuitRefund);
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
     * 处理任务
     * @param agentVo
     * @param userId
     * @param busId
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult approvalQuitRefundTask(AgentVo agentVo, String userId, String busId) throws Exception {
        try {
            if (agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())) {
                List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(userId));
                if (null == orgCodeRes) {
                    throw new ProcessException("部门参数为空！");
                }
                Map<String, Object> map = orgCodeRes.get(0);
                Object orgCode = map.get("ORGANIZATIONCODE");
                AgentQuitRefund agentQuitRefund = agentQuitRefundMapper.selectByPrimaryKey(busId);
                //财务审批
                if (String.valueOf(orgCode).equals("finance")) {
                    //实际打款金额
                    BigDecimal realitySuppDept = agentVo.getRealitySuppDept();
                    if (realitySuppDept != null || realitySuppDept.compareTo(BigDecimal.ONE) == 0) {
                        agentQuitRefund.setRealitySuppDept(realitySuppDept);
                        agentQuitRefund.setuUser(userId);
                        agentQuitRefund.setuTime(Calendar.getInstance().getTime());
                        if (1 != agentQuitRefundMapper.updateByPrimaryKeySelective(agentQuitRefund)) {
                            throw new MessageException("实际打款金额更新失败！");
                        }
                    } else {
                        logger.info("申请退款添加:{}", "请填写实际打款金额");
                        throw new MessageException("请填写实际打款金额！");
                    }
                    //上传打款凭证
                    List<String> quitRefundFile = agentVo.getQuitRefundFile();
                    if (quitRefundFile != null && quitRefundFile.size() != 0) {
                        for (int i = 0; i < quitRefundFile.size(); i++) {
                            AttachmentRel record = new AttachmentRel();
                            record.setAttId(quitRefundFile.get(i));
                            record.setSrcId(busId);
                            record.setcUser(userId);
                            record.setcTime(Calendar.getInstance().getTime());
                            record.setStatus(Status.STATUS_1.status);
                            record.setBusType(AttachmentRelType.agentQuitRefund.name());
                            record.setId(idService.genId(TabId.a_attachment_rel));
                            if (1 != attachmentRelMapper.insertSelective(record)) {
                                logger.info("代理商退出申请退款-保存附件关系失败");
                                throw new ProcessException("保存附件失败！");
                            }
                        }
                    } else {
                        logger.info("申请退款添加:{}", "请上传打款凭证");
                        throw new MessageException("请上传打款凭证！");
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
            throw new MessageException("catch工作流处理任务异常！" );
        }
        return AgentResult.ok();
    }

    /**
     * 审批结果监听
     * @param proIns
     * @param agStatus
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult compressQuitRefundActivity(String proIns, BigDecimal agStatus) throws Exception {
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
            logger.info("审批任务结束{}{}，代理商退出申请退款更新失败-busActRel", proIns, agStatus);
            throw new MessageException("代理商退出申请退款更新失败！");
        }
        AgentQuitRefund agentQuitRefund = agentQuitRefundMapper.selectByPrimaryKey(busActRel.getBusId());
        agentQuitRefund.setCloReviewStatus(agStatus);
        agentQuitRefund.setApproveTime(Calendar.getInstance().getTime());
        if (1 != agentQuitRefundMapper.updateByPrimaryKeySelective(agentQuitRefund)) {
            logger.info("审批任务结束{}{}，代理商退出申请退款更新失败-agentQuitRefund", proIns, agStatus);
            throw new MessageException("代理商退出申请退款更新失败！");
        }
        if (agStatus.compareTo(AgStatus.Approved.getValue()) == 0) {
            AgentQuit agentQuit = agentQuitMapper.selectByPrimaryKey(agentQuitRefund.getQuitId());
            agentQuit.setRefundAmtStatus(Status.STATUS_1.status);
            if (1 != agentQuitMapper.updateByPrimaryKeySelective(agentQuit)) {
                logger.info("审批任务结束{}{}，代理商退出申请退款更新失败-agentQuit", proIns, agStatus);
                throw new MessageException("代理商退出申请退款更新失败！");
            }
        }
        return AgentResult.ok();
    }

    /**
     * 根据ID查询合并数据
     * @param refundId
     * @return
     */
    @Override
    public AgentQuitRefund queryQuitRefundById(String refundId) {
        if (StringUtils.isBlank(refundId)) {
            return null;
        }
        AgentQuitRefund agentQuitRefund = agentQuitRefundMapper.selectByPrimaryKey(refundId);
        if (null == agentQuitRefund) {
            return null;
        }
        //查询关联附件
        List<Attachment> attachments = attachmentMapper.accessoryQuery(refundId, AttachmentRelType.agentQuitRefund.name());
        agentQuitRefund.setAttachments(attachments);
        return agentQuitRefund;
    }

}
