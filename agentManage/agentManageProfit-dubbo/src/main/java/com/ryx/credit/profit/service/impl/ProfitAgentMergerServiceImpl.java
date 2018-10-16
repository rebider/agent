package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;

import com.ryx.credit.profit.dao.PAgentMergeMapper;
import com.ryx.credit.profit.dao.ProfitOrganTranMonthMapper;
import com.ryx.credit.profit.pojo.PAgentMerge;
import com.ryx.credit.profit.service.IProfitAgentMergerService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

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
            logger.info("代理商合申请审批流启动成功");
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("代理商合申请审批流启动失败{}");
            throw new ProcessException("代理商合申请审批流启动失败!:{}",e.getMessage());
        }
        pAgentMerge.setMergeStatus(AgStatus.Approving.name());
        pAgentMergeMapper.updateByPrimaryKeySelective(pAgentMerge);
    }

    /*@Override
    public PAgentMerge getMergeById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return pAgentMergeMapper.selectByPrimaryKey(id);
        }else{
            return null;
        }
    }*/
}
