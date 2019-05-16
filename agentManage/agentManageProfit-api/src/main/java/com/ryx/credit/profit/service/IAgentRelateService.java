package com.ryx.credit.profit.service;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.AgentRelate;
import com.ryx.credit.profit.pojo.AgentRelateDetail;
import com.ryx.credit.profit.pojo.AgentRelateExample;
import com.sun.corba.se.spi.ior.ObjectKey;

import java.util.List;
import java.util.Map;

/**
 * @author renshenghao
 * @Description: 代理商机具扣款关联关系
 * @date 2019/01/24
 */
public interface IAgentRelateService {

    PageInfo getList(Map<String,Object> param,PageInfo pageInfo);

    int insertAgentRelate(AgentRelate agentRelate);

    int updateAgentRelate(AgentRelate agentRelate);

    List<AgentRelate> selectByExample(AgentRelateExample agentRelateExample);

    AgentRelate selectById(String id);

    Map<String,String> queryParentAgentByAgentId(Map<String,String> param);

    boolean applyAgentRelate(AgentRelate agentRelate, List<AgentRelateDetail> list, String userId, String workId);

    List<AgentRelateDetail> queryDetailByBusId(String busId);

    AgentResult approvalAgentRelateTask(AgentVo agentVo, String userId) throws Exception;

    /**
     * 审批流回调方法
     * @param insid
     * @param status
     */
    public void completeTaskEnterActivity(String insid, String status);

    public List<String> getRelateAgentIdByAgentIdAndTime(String agentId,String profitDate);
}
