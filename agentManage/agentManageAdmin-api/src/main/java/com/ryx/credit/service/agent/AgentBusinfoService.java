package com.ryx.credit.service.agent;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
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

    public AgentBusInfo getByBusidAndCode(String platformCode,String busid);

    public ResultVO updateAgentBusInfoVo(List<AgentBusInfoVo> busInfoVoList, Agent agent,String userId,Boolean isPass)throws Exception;

    public List<Map> agentBus(String agentId);
    public List<Map> agentBusChild(String platformCode,String angetId);

    public List<Map> agentBusChild(String busId);


    public Map getRootFromBusInfo(List<Map> list,String busId);

    public List<Map> getParentListFromBusInfo(List<Map> list,String busId);

    /**
     * 根据业务平台id查询上级
     * @param list
     * @param busId
     * @return
     */
    public List<AgentBusInfo> queryParenLevel(List<AgentBusInfo> list, String busId);
    /**
     * 查询给定的代理商平台的顺序上级
     * @param list
     * @param platformCode
     * @param agentId
     * @return
     */
    public List<AgentBusInfo> queryParenFourLevel(List<AgentBusInfo> list ,String platformCode,String agentId);


    /**
     * 查询给定的代理商机构编号上级
     * @param list
     * @param platformCode
     * @param busNum
     * @return
     */
    public List<AgentBusInfo> queryParenFourLevelBusNum(List<AgentBusInfo> list, String platformCode, String busNum);

    public List<AgentBusInfo> queryChildLevel(List<AgentBusInfo> list ,String platformCode,String agentId);

    /**
     * 根据平台号，机构号，查询给定平台机构号的上下级
     * @param list
     * @param platformCode
     * @param busNum
     * @return
     */
    public List<AgentBusInfo> queryChildLevelByBusNum(List<AgentBusInfo> list,String platformCode, String busNum);
    /**根据代理商id查询业务类型*/
    public List<AgentBusInfo> selectByAgenId(String agentId);

    AgentBusInfo selectBusInfo(String busNum);

    List<AgentBusInfo> selectExistsById(String id);

    /**
     * 查询所有有多个打款公司的代理商并顺序更新为统一的一个
     * @return
     */
    AgentResult completAllAgentBusInfoCompany();
    /**
     * 修复代理商的打款公司为一个
     * @param agentId
     * @return
     */
    AgentResult completAgentBusInfoCompany(String agentId)throws Exception;

    Map selectComp(String busId);

    String queryAgentId(String busNum)throws MessageException;

    void updateBusLoginNum(String oldBusLoginNum,String busLoginNum)throws MessageException;
}
