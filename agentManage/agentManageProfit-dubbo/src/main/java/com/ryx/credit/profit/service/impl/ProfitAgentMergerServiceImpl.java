package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;

import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.PAgentMergeMapper;
import com.ryx.credit.profit.dao.ProfitOrganTranMonthMapper;
import com.ryx.credit.profit.pojo.PAgentMerge;
import com.ryx.credit.profit.pojo.PAgentQuit;
import com.ryx.credit.profit.service.IProfitAgentMergerService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代理商合并Impl
 * @version V1.0
 * @Description:
 * @author: LiuQY
 * @date: 2018/9/27 09:30
 */
@Service("profitAgentMergerServiceImpl")
public class ProfitAgentMergerServiceImpl implements IProfitAgentMergerService {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(ProfitAgentMergerServiceImpl.class);
    @Autowired
    private PAgentMergeMapper pAgentMergeMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private AgentEnterService agentEnterService;

    /**代理商合并*/
    @Override
    public PageInfo getProfitAgentMergeList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = pAgentMergeMapper.getProfitAgentMergeCount(param);
        List<Map<String, Object>> list = pAgentMergeMapper.getProfitAgentMergeList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        System.out.println("查询============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }
    /**代理商合并提交存库*/
    @Override
    public void agentMergeTaxEnterIn(PAgentMerge pAgentMerge,Long  userId) throws ProcessException {

        pAgentMerge.setId(idService.genId(TabId.P_AGENT_MERGE));
        pAgentMergeMapper.insertSelective(pAgentMerge);
        Map startPar = agentEnterService.startPar(String.valueOf(userId));
        if (null == startPar) {
            throw new ProcessException("启动部门参数为空！");
        }
        //启动审批流
        String proceId = activityService.createDeloyFlow(null, "mergeCity", null, null, startPar);
        if (proceId == null) {
            logger.error("代理商合并审批流启动失败，代理商ID：{}", pAgentMerge.getMainAgentId());
            throw new ProcessException("代理商合审批流启动失败!");
        }
        BusActRel record = new BusActRel();
        record.setBusId(pAgentMerge.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(String.valueOf(userId));
        record.setBusType(BusActRelBusType.MERGE.name());
        try {
            taskApprovalService.addABusActRel(record);
            logger.info("代理商合并申请审批流启动成功");
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("代理商合并申请审批流启动失败{}");
            throw new ProcessException("代理商合并申请审批流启动失败!:{}",e.getMessage());
        }
        pAgentMerge.setMergeStatus(AgStatus.Approving.name());
        pAgentMergeMapper.updateByPrimaryKeySelective(pAgentMerge);
    }

    @Override
    public PAgentMerge getMergeById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return pAgentMergeMapper.selectByPrimaryKey(id);
        }else{
            return null;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception {
        logger.info("审批对象：{}", JSONObject.toJSON(agentVo));
        try {
            taskApprovalService.updateApproval(agentVo, userId);
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                logger.error(result.getMsg());
                throw new ProcessException("工作流处理任务异常");
            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException("catch工作流处理任务异常!");
        }
        return AgentResult.ok();
    }

    @Override
    public void completeTaskEnterActivity(String insid, String status) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                PAgentMerge pAgentMerge = pAgentMergeMapper.selectByPrimaryKey(rel.getBusId());
                pAgentMerge.setMergeStatus(AgStatus.Approved.name());//审批状态：Approved 3: 审批通过
                pAgentMerge.setMergeDate(df.format(new Date()));//合并日期（生效日期）
                logger.info("1.更新代理商退出申请状态为通过，已生效");
                pAgentMergeMapper.updateByPrimaryKeySelective(pAgentMerge);
                logger.info("2.更新审批流与业务对象");
                rel.setStatus(Status.STATUS_2.status);
                taskApprovalService.updateABusActRel(rel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("代理商退出申请审批流回调异常，activId：{}" + insid);
        }
    }

}
