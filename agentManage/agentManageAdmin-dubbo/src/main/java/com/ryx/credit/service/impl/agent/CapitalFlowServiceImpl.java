package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.CapitalFlowMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.pojo.admin.agent.CapitalFlow;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.CapitalFlowService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2019/2/12.
 * 资金流水
 */
@Service("capitalFlowService")
public class CapitalFlowServiceImpl implements CapitalFlowService {

    private static Logger logger = LoggerFactory.getLogger(CapitalFlowServiceImpl.class);
    @Autowired
    private CapitalFlowMapper capitalFlowMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentMapper agentMapper;


    /**
     * 资金流水列表
     * @param capitalFlow
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    @Override
    public PageInfo queryCapitalFlowList(CapitalFlow capitalFlow, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(capitalFlow.getId())) {
            reqMap.put("id", capitalFlow.getId());
        }
        if (StringUtils.isNotBlank(capitalFlow.getAgentId())) {
            reqMap.put("agentId", capitalFlow.getAgentId());
        }
        if (StringUtils.isNotBlank(capitalFlow.getAgentName())) {
            reqMap.put("agentName", capitalFlow.getAgentName());
        }
        if (null!=capitalFlow.getFlowStatus()) {
            reqMap.put("flowStatus", capitalFlow.getFlowStatus());
        }
        if(StringUtils.isBlank(dataRole)){
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if(orgCodeRes == null && orgCodeRes.size() != 1){
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            reqMap.put("orgId", String.valueOf(stringObjectMap.get("ORGID")));
        }
        List<Map<String, Object>> capitalChangeList = capitalFlowMapper.queryCapitalFlowList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(capitalChangeList);
        pageInfo.setTotal(capitalFlowMapper.queryCapitalFlowCount(reqMap));
        return pageInfo;
    }

    /**
     * 插入资金流水记录
     * @param capital
     * @param srcId
     * @param remark
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public void insertCapitalFlow(Capital capital,BigDecimal beforeAmount,String srcId,String remark)throws Exception{
        if(capital.getcPayType().equals(PayType.YHHK.getValue())){
            CapitalFlow capitalFlow = new CapitalFlow();
            capitalFlow.setId(idService.genId(TabId.A_CAPITAL_FLOW));
            capitalFlow.setcType(capital.getcType());
            capitalFlow.setCapitalId(capital.getId());
            capitalFlow.setSrcType(SrcType.RW.getValue());
            capitalFlow.setSrcId(srcId);
            capitalFlow.setBeforeAmount(beforeAmount);
            capitalFlow.setcAmount(capital.getcAmount());
            capitalFlow.setOperationType(OperateTypes.RZ.getValue());
            capitalFlow.setAgentId(capital.getcAgentId());
            Agent agent = agentMapper.selectByPrimaryKey(capital.getcAgentId());
            capitalFlow.setAgentName(agent.getAgName());
            capitalFlow.setRemark(remark);
            capitalFlow.setcTime(new Date());
            capitalFlow.setuTime(new Date());
            capitalFlow.setcUser(capital.getcUser());
            capitalFlow.setuUser(capital.getcUser());
            capitalFlow.setStatus(Status.STATUS_1.status);
            capitalFlow.setVersion(BigDecimal.ZERO);
            capitalFlow.setFlowStatus(Status.STATUS_1.status);
            int i = capitalFlowMapper.insertSelective(capitalFlow);
            if(i!=1){
                throw new ProcessException("插入资金流水失败");
            }
        }
    }

}
