package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.AgentoutVo;
import com.ryx.credit.pojo.admin.vo.BusinessOutVo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 业务平台管理
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/22 9:27
 */
public interface BusinessPlatformService {

    PageInfo queryBusinessPlatformList(AgentBusInfo agentBusInfo, Agent agent, Page page,Long userId);
    PageInfo queryBusinessPlatformListManager(AgentBusInfo agentBusInfo, Agent agent, Page page,Long userId);

    AgentResult verifyAgent(String agUniqNum,List<String> agStatusList);

    AgentBusInfo findById(String id);

    AgentBusInfo findByAgentId(String id);

    int updateByPrimaryKeySelective(AgentBusInfo agentBusInfo);

    void updateBusInfoList(List<AgentBusInfoVo> busInfoVoList)throws Exception;

    void updateBusinfoData(List<AgentBusInfoVo> busInfoVoList) throws Exception;

    List<PlatForm> queryAblePlatForm();

    AgentResult saveBusinessPlatform(AgentVo agentVo) throws ProcessException;

    /**
     * 导入业务信息
     * @param list
     * @param userid
     * @return
     * @throws Exception
     */
    public List<String> addList(List<List<Object>> list, String userid) throws Exception;

    int updateBusPlatDkgsBySelective(AgentBusInfo agentBusInfo,String userId);

    /**
     * 代理商入网修改打款公司
     * @param agentBusInfo
     * @param userId
     * @return
     */
    int updateCompany(AgentBusInfo agentBusInfo,String userId) throws MessageException;

    /**
     * 业务部的导出数据
     *
     */
    public List<BusinessOutVo> exportAgent(Map map, Long userId) throws ParseException;


    List<Map<String, Object>> queryByBusNum(String busNum);


    Map<String,Object> queryIsBZYD(String agBusLic,List<AgentBusInfoVo> busInfoVoList);


    List<Map<String,Object>> queryIsBZYDList(String agBusLic,List<AgentBusInfo> busInfoVoList);


    AgentResult selectByAgentApproved(String id);


    Boolean selectByBusLoginNumExist(String busLoginNum,String agentId);

    List<AgentBusInfo> selectByAgentId(String agentId);
}
