package com.ryx.credit.service.agent;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;

/**
 * Created by cx on 2018/5/23.
 */
public interface AgentBusinfoService {
    /**
     * 代理商查询插件数据获取
     * @param par
     * @return
     */
    PageInfo agentBusInfoSelectViewList(Map par,  PageInfo page);

    AgentBusInfo agentBusInfoInsert(AgentBusInfo agentBusInfo) throws Exception;

    /**
     * 代理商业务信息
     * @param agentId
     * @return
     */
    public List<AgentBusInfo> agentBusInfoList(String agentId);
    public List<AgentBusInfo> agentBusInfoList(String agentId, String id, BigDecimal appStatus);

    public int updateAgentBusInfo(AgentBusInfo agentBusInfo);

    public AgentBusInfo getById(String id);

    public ResultVO updateAgentBusInfoVo(List<AgentBusInfoVo> busInfoVoList, Agent agent)throws Exception;


    public List<Map> agentBusChild(String platformCode,String angetId);

    public List<Map> agentBusChild(String busId);


    public Map getRootFromBusInfo(String busId);


    /**
     * 查询给定的代理商平台的顺序上级
     * @param list
     * @param platformCode
     * @param agentId
     * @return
     */
    public List<AgentBusInfo> queryParenFourLevel(List<AgentBusInfo> list ,String platformCode,String agentId);

    @Deprecated
    public List<AgentBusInfo> queryChildFourLevel(List<AgentBusInfo> list ,String platformCode,String agentId);


}
