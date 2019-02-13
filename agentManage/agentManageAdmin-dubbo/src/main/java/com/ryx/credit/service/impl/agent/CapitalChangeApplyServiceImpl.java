package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.agent.CapitalChangeApplyMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.CapitalChangeApplyService;
import com.ryx.credit.service.dict.IdService;
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
            capitalChangeApply.setAgentName(agent.getAgName());
            capitalChangeApply.setCloReviewStatus(AgStatus.Create.status);
            capitalChangeApply.setcUser(cUser);
            capitalChangeApply.setuUser(cUser);
            capitalChangeApply.setStatus(Status.STATUS_1.status);
            capitalChangeApply.setVersion(BigDecimal.ONE);
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
                    record.setBusType(AttachmentRelType.agentQuit.name());
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

    @Override
    public AgentResult approvalCapitalChangeTask(AgentVo agentVo, String userId, String busId) throws Exception {
        try {
            if (agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())) {
                CapitalChangeApply capitalChangeApply = capitalChangeApplyMapper.selectByPrimaryKey(busId);
                agentVo.setOperationType(String.valueOf(capitalChangeApply.getOperationType()));
                agentVo.setAmt(String.valueOf(capitalChangeApply.getRealOperationAmt()));
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
        return AgentResult.ok();
    }

}
