package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.PPosHuddleRewardMapper;
import com.ryx.credit.profit.dao.PosHuddleRewardDetailMapper;
import com.ryx.credit.profit.dao.PosRewardMapper;
import com.ryx.credit.profit.dao.PosRewardTemplateMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IPosRewardService;
import com.ryx.credit.service.ActRuTaskService;
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

import java.text.SimpleDateFormat;
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
    private PosRewardTemplateMapper templateMaper;
    @Autowired
    private PPosHuddleRewardMapper pPosHuddleRewardMapper;
    @Autowired
    private PosHuddleRewardDetailMapper posHuddleRewardDetailMapper;


    @Override
    public PageInfo posRewardList(PosReward record, Page page) {
        PosRewardExample example = rewardEqualsTo(record);
        example.setPage(page);
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
        if(StringUtils.isNotBlank(reward.getApplyStatus())){
            criteria.andApplyStatusEqualTo(reward.getApplyStatus());
        }
        return posRewardExample;
    }


    @Override
    public PageInfo posHuddleRewardList(PosHuddleRewardDetail posHuddleRewardDetail, Page page) {

        PosHuddleRewardDetailExample posHuddleRewardDetailExample = new PosHuddleRewardDetailExample();
        PosHuddleRewardDetailExample.Criteria criteria = posHuddleRewardDetailExample.createCriteria();
        if(StringUtils.isNotBlank(posHuddleRewardDetail.getPosAgentId())){
            criteria.andPosAgentIdEqualTo(posHuddleRewardDetail.getPosAgentId());
        }
        if(StringUtils.isNotBlank(posHuddleRewardDetail.getPosAgentName())){
            criteria.andPosAgentNameEqualTo(posHuddleRewardDetail.getPosAgentName());
        }
        List<PosHuddleRewardDetail> listDetail= posHuddleRewardDetailMapper.selectByExample(posHuddleRewardDetailExample);
        List<String> listQuvery = new ArrayList<>();
        for (PosHuddleRewardDetail detail : listDetail) {
            listQuvery.add(detail.getHuddleCode());
        }


        List<PPosHuddleReward> profitD = pPosHuddleRewardMapper.selectByList(listQuvery);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(profitD);
        pageInfo.setTotal((int)pPosHuddleRewardMapper.selectByCount(listQuvery));
        return pageInfo;
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


    /**
     * POS抱团奖励进行审批流
     * @param pPosHuddleReward
     * @param userId
     * @param workId
     * @throws ProcessException
     */
    @Override
    public void applyHuddlePosReward(PPosHuddleReward pPosHuddleReward, String userId, String workId) throws ProcessException {
        pPosHuddleReward.setId((idService.genId(TabId.p_pos_reward)));
        logger.info("序列ID......"+idService.genId(TabId.p_pos_reward));
        pPosHuddleRewardMapper.insertSelective(pPosHuddleReward);

        //启动审批流
        String proceId = activityService.createDeloyFlow(null, workId, null, null, null);
        if (proceId == null) {
            PPosHuddleRewardExample pPosHuddleRewardExample = new PPosHuddleRewardExample();
            pPosHuddleRewardExample.createCriteria().andIdEqualTo(pPosHuddleReward.getId());
            pPosHuddleRewardMapper.deleteByExample(pPosHuddleRewardExample);
            logger.error("POS奖励审批流启动失败，代理商ID：{}", pPosHuddleReward.getAgentName());
            throw new ProcessException("POS抱团奖励审批流启动失败!");
        }
        BusActRel record = new BusActRel();
        record.setBusId(pPosHuddleReward.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setBusType(BusActRelBusType.POSHUDDLEREWARD.name());
        record.setAgentName(pPosHuddleReward.getAgentName());
        try {
            taskApprovalService.addABusActRel(record);
            logger.info("POS奖励审批流启动成功");
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("POS奖励审批流启动失败{}");
            throw new ProcessException("POS奖励审批流启动失败!:{}",e.getMessage());
        }
        pPosHuddleReward.setApplyStatus(RewardStatus.REVIEWING.getStatus());//REVIEWING 0:审核中
        pPosHuddleRewardMapper.updateByPrimaryKeySelective(pPosHuddleReward);
    }

    @Override
    public PPosHuddleReward selectByPrimaryKey(String id) {
        return  pPosHuddleRewardMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PosHuddleRewardDetail> selectByHuddleCode(String huddleCode) {
        return posHuddleRewardDetailMapper.selectByHuddleCode(huddleCode);
    }

    @Override
    public Boolean isRepetition(PosReward posReward) {
        PosRewardExample example = new PosRewardExample();
        PosRewardExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(posReward.getAgentPid())){
            criteria.andAgentPidEqualTo(posReward.getAgentPid());
        }
        if(StringUtils.isNotBlank(posReward.getAgentId())){
            criteria.andAgentIdEqualTo(posReward.getAgentId());
        }
        if(StringUtils.isNotBlank(posReward.getApplyStatus())){
            criteria.andApplyStatusEqualTo(posReward.getApplyStatus());
        }
        List<PosReward> list=rewardMapper.selectByExample(example);
        List<String> month=getMonthBetween(posReward.getTotalConsMonth(),posReward.getCreditConsMonth());
        for (PosReward p :list) {
            for (String mon:month) {
                if(mon.compareTo(p.getTotalConsMonth())>=0&&mon.compareTo(p.getCreditConsMonth())<=0){
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException {


        String taskId = agentVo.getTaskId();
        if (taskId!=null){
            Map<String, Object>  identitylink  = rewardMapper.selectByTaskId(taskId);
            String groupId = (String) identitylink.get("GROUP_ID_");
            Map<String, Object> mapactRuTask = rewardMapper.selectById(taskId);
            String activid = (String) mapactRuTask.get("EXECUTION_ID_");
            Map<String, Object> mapActRel = rewardMapper.selectByActiv(activid);
            PPosHuddleRewardExample pPosHuddleRewardExample = new PPosHuddleRewardExample();
            PPosHuddleRewardExample.Criteria criteria = pPosHuddleRewardExample.createCriteria();
            criteria.andAgentNameEqualTo((String) mapActRel.get("AGENT_NAME"));
            criteria.andIdEqualTo((String) mapActRel.get("BUS_ID"));
            List<PPosHuddleReward> listPos = pPosHuddleRewardMapper.selectByExample(pPosHuddleRewardExample);

            if(agentVo.getPretest()!=null || "".equals(agentVo.getOrderAprDept())) {

                if (listPos != null) {
                    for (PPosHuddleReward pos : listPos) {
                        String Status1 =Status.STATUS_1.status.toString();
                        if("".equals(agentVo.getOrderAprDept()) &&(pos.getRev1()!=null)){

                            if ("pass".equals(agentVo.getApprovalResult()) && (!"city".equals(groupId))) {
                                if(!"business".equals(groupId)){
                                    pos.setApplyStatus(Status.STATUS_1.status.toString());
                                }


                            }
                        }else if(pos.getRev1()==null){
                            pos.setRev1(agentVo.getPretest());
                        }
                        pPosHuddleRewardMapper.updateByPrimaryKeySelective(pos);
                    }
                }
            }
        }



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
                String agentId=posReward.getAgentId();
                PosReward tempPosReward=new PosReward();
                tempPosReward.setAgentId(agentId);
                tempPosReward.setApplyStatus(RewardStatus.PASS.getStatus());
                List<PosReward> oldList=rewardMapper.selectByExample(rewardEqualsTo(tempPosReward));//查出之前此代理商通过的所有特殊奖励申请单
                rewardMapper.updateByPrimaryKeySelective(posReward);//将此审批通过的申请更新到数据库
                List<PosReward> updateList=new ArrayList<PosReward>();
                List<String> monthList=getMonthBetween(posReward.getTotalConsMonth(),posReward.getCreditConsMonth());
                oldList.stream().forEach(oldPosReward->{
                    List<String> tempMonth=getMonthBetween(oldPosReward.getTotalConsMonth(),oldPosReward.getCreditConsMonth());
                    for (String str1:tempMonth){
                        for (String str2:monthList){
                            if(str1.equals(str2)){
                                oldPosReward.setApplyStatus(RewardStatus.UN_PASS.getStatus());
                                updateList.add(oldPosReward);
                                return;
                            }
                        }
                    }
                });
                for (PosReward updatePosReward:updateList){
                    rewardMapper.updateByPrimaryKeySelective(updatePosReward);
                }
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
    public List<PPosHuddleReward> getHuddlePosRewardByDataId(String id) {
        if(StringUtils.isNotBlank(id)){
            PPosHuddleRewardExample pPosHuddleRewardExample = new PPosHuddleRewardExample();
            PPosHuddleRewardExample.Criteria criteria = pPosHuddleRewardExample.createCriteria();
            criteria.andIdEqualTo(id);
            return pPosHuddleRewardMapper.selectByExample(pPosHuddleRewardExample);
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
        }*/
        if(StringUtils.isNotBlank(posReward.getCreditConsMonth())){
            criteria.andCreditConsMonthLike("%"+posReward.getCreditConsMonth()+"%");
        }
       /* if(StringUtils.isNotBlank(posReward.getAgentPid())){
            criteria.andAgentPidEqualTo(posReward.getAgentPid());
        }
        if(StringUtils.isNotBlank(posReward.getTotalEndMonth())){
            criteria.andTotalEndMonthLike("%"+posReward.getTotalEndMonth());
        }
        if(StringUtils.isNotBlank(posReward.getAgentId())){
            criteria.andAgentIdEqualTo(posReward.getAgentId());
        }*/
        if(StringUtils.isNotBlank(posReward.getApplyStatus())){
            criteria.andApplyStatusEqualTo(posReward.getApplyStatus());
        }
        return rewardMapper.selectByExample(example);
    }

    @Override
    public int insertHuddleDetail(PosHuddleRewardDetail record) {
        return posHuddleRewardDetailMapper.insertSelective(record);
    }

    @Override
    public List<Map<String, Object>> huddlePos(Map<String, Object> param) {
        return rewardMapper.huddlePos(param);
    }



    private static List<String> getMonthBetween(String minDate, String maxDate) {
        try {
            ArrayList<String> result = new ArrayList<String>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

            Calendar min = Calendar.getInstance();
            Calendar max = Calendar.getInstance();

            min.setTime(sdf.parse(minDate));
            min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

            max.setTime(sdf.parse(maxDate));
            max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

            Calendar curr = min;
            while (curr.before(max)) {
                result.add(sdf.format(curr.getTime()));
                curr.add(Calendar.MONTH, 1);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
