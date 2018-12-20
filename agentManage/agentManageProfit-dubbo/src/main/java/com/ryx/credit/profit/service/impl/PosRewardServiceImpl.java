package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.PosRewardMapper;
import com.ryx.credit.profit.dao.PosRewardTemplateMapper;
import com.ryx.credit.profit.pojo.PosReward;
import com.ryx.credit.profit.pojo.PosRewardExample;
import com.ryx.credit.profit.pojo.PosRewardTemplate;
import com.ryx.credit.profit.service.IPosRewardService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * ProfitDServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang y
 * @Date 2017/7/31
 * @Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
@Service("iPosRewardService")
public class PosRewardServiceImpl implements IPosRewardService {
    Logger logger = LoggerFactory.getLogger(PosRewardServiceImpl.class);
    @Autowired
    private PosRewardMapper rewardMapper;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private IdService idService;
    @Autowired
    private PosRewardTemplateMapper templateMapper;

    @Override
    public PageInfo posRewardList(PosReward record, Page page) {
        PosRewardExample example = rewardEqualsTo(record);
        example.setOrderByClause("TOTAL_END_MONTH "+Page.ORDER_DIRECTION_DESC);
        List<PosReward> profitD = rewardMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(profitD);
        pageInfo.setTotal((int)rewardMapper.countByExample(example));
        return pageInfo;
    }

    private PosRewardExample rewardEqualsTo(PosReward reward) {
        PosRewardExample posRewardExample = new PosRewardExample();
        if(reward == null ){
            return posRewardExample;
        }
        PosRewardExample.Criteria criteria = posRewardExample.createCriteria();
        if(StringUtils.isNotBlank(reward.getAgentName())){
            criteria.andAgentNameEqualTo(reward.getAgentName());
        }
        if(StringUtils.isNotBlank(reward.getAgentId())){
            criteria.andAgentIdEqualTo(reward.getAgentId());
        }
        return posRewardExample;
    }

    /**
     * @author: Lihl
     * @desc POS奖励申请调整，进行审批流
     * @param posReward
     * @param userId
     * @param workId
     */
    @Override
    public void applyPosReward(PosReward posReward, String userId, String workId) throws ProcessException {
        posReward.setId((idService.genId(TabId.p_pos_reward)));
        logger.info("序列ID......"+idService.genId(TabId.p_pos_reward));
        rewardMapper.insertSelective(posReward);

        //启动审批流
        String proceId = activityService.createDeloyFlow(null, workId, null, null, null);
        if (proceId == null) {
            PosRewardExample posRewardExample = new PosRewardExample();
            posRewardExample.createCriteria().andIdEqualTo(posReward.getId());
            rewardMapper.deleteByExample(posRewardExample);
            logger.error("POS奖励审批流启动失败，代理商ID：{}", posReward.getAgentId());
            throw new ProcessException("POS奖励审批流启动失败!");
        }
        BusActRel record = new BusActRel();
        record.setBusId(posReward.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setBusType(BusActRelBusType.POSREWARD.name());
        record.setAgentId(posReward.getAgentId());
        record.setAgentName(posReward.getAgentName());
        try {
            taskApprovalService.addABusActRel(record);
            logger.info("POS奖励审批流启动成功");
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("POS奖励审批流启动失败{}");
            throw new ProcessException("POS奖励审批流启动失败!:{}",e.getMessage());
        }
        posReward.setApplyStatus(RewardStatus.REVIEWING.getStatus());//REVIEWING 0:审核中
        rewardMapper.updateByPrimaryKeySelective(posReward);
    }

    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException {
        logger.info("审批对象：{}", JSONObject.toJSON(agentVo));
        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", agentVo.getOrderAprDept());
        }
        if(Objects.equals("pass",agentVo.getApprovalResult())
                && StringUtils.isBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", "finish");
        }
        reqMap.put("rs", agentVo.getApprovalResult());
        reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
        reqMap.put("approvalPerson", userId);
        reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
        reqMap.put("taskId", agentVo.getTaskId());

        logger.info("创建下一审批流对象：{}", reqMap.toString());
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

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void completeTaskEnterActivity(String insid, String status){
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                PosReward posReward = rewardMapper.selectByPrimaryKey(rel.getBusId());
                posReward.setApplyStatus(RewardStatus.PASS.getStatus());   // PASS 1:生效
                rewardMapper.updateByPrimaryKeySelective(posReward);
                logger.info("2更新审批流与业务对象");
                rel.setActivStatus(AgStatus.Approved.name());
                taskApprovalService.updateABusActRel(rel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("POS奖励审批流回调异常，activId：{}" + insid);
        }
    }

    @Override
    public PosReward getPosRewardById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return rewardMapper.selectByPrimaryKey(id);
        }else{
            return null;
        }
    }

    @Override
    public List<PosReward> getPosRewardByDataId(String id) {
        if(StringUtils.isNotBlank(id)){
            PosRewardExample posRewardExample = new PosRewardExample();
            PosRewardExample.Criteria criteria = posRewardExample.createCriteria();
            criteria.andIdEqualTo(id);
            return rewardMapper.selectByExample(posRewardExample);
        }
        return null;
    }

    @Override
    public void editRewardRegect(PosReward posReward) throws Exception {
        try {
            rewardMapper.updateByPrimaryKeySelective(posReward);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }

    /**
     * 查询此交易月份是否已申请
     * @param posReward
     * @return
     */
    @Override
    public List<PosReward> selectRewardByMonth(PosReward posReward) {
        PosRewardExample example = new PosRewardExample();
        PosRewardExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(posReward.getTotalConsMonth())){
            criteria.andTotalConsMonthLike("%"+posReward.getTotalConsMonth()+"%");
        }
        if(StringUtils.isNotBlank(posReward.getCreditConsMonth())){
            criteria.andCreditConsMonthLike("%"+posReward.getCreditConsMonth()+"%");
        }
        if(StringUtils.isNotBlank(posReward.getAgentPid())){
            criteria.andAgentPidEqualTo(posReward.getAgentPid());
        }
        if(StringUtils.isNotBlank(posReward.getAgentId())){
            criteria.andAgentIdEqualTo(posReward.getAgentId());
        }
        if(StringUtils.isNotBlank(posReward.getApplyStatus())){
            criteria.andApplyStatusEqualTo(posReward.getApplyStatus());
        }
        return rewardMapper.selectByExample(example);
    }

    @Override
    public List<PosReward> selectByEndMonth(PosReward posReward) {
        PosRewardExample example = new PosRewardExample();
        PosRewardExample.Criteria criteria = example.createCriteria();
        /*if(StringUtils.isNotBlank(posReward.getTotalConsMonth())){
            criteria.andTotalConsMonthLike("%"+posReward.getTotalConsMonth()+"%");
        }
        if(StringUtils.isNotBlank(posReward.getCreditConsMonth())){
            criteria.andCreditConsMonthLike("%"+posReward.getCreditConsMonth()+"%");
        }*/
        if(StringUtils.isNotBlank(posReward.getTotalEndMonth())){
            criteria.andTotalEndMonthLike("%"+posReward.getCreditConsMonth());
        }
       /* if(StringUtils.isNotBlank(posReward.getAgentPid())){
            criteria.andAgentPidEqualTo(posReward.getAgentPid());
        }
        if(StringUtils.isNotBlank(posReward.getAgentId())){
            criteria.andAgentIdEqualTo(posReward.getAgentId());
        }*/
        if(StringUtils.isNotBlank(posReward.getApplyStatus())){
            criteria.andApplyStatusEqualTo(posReward.getApplyStatus());
        }
        return rewardMapper.selectByExample(example);
    }

}
