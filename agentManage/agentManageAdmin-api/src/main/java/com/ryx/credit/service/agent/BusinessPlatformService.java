package com.ryx.credit.service.agent;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.PlatForm;
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

    PageInfo queryBusinessPlatformList(AgentBusInfo agentBusInfo, Agent agent, Page page,String flag,String isZpos);

    AgentResult verifyAgent(String agUniqNum);

    AgentBusInfo findById(String id);

    int updateByPrimaryKeySelective(AgentBusInfo agentBusInfo);

    List<PlatForm> queryAblePlatForm();

    AgentResult saveBusinessPlatform(AgentVo agentVo) throws Exception;

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
     * 业务部的导出数据
     *
     */
    public List<BusinessOutVo> exportAgent(Map map, Long userId) throws ParseException;


    List<Map<String, Object>> queryByBusNum(String busNum);
}
