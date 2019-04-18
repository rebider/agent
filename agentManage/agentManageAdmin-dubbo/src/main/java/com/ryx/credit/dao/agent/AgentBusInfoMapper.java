package com.ryx.credit.dao.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfoExample;
import com.ryx.credit.pojo.admin.vo.BusinessOutVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AgentBusInfoMapper {
    int countByExample(AgentBusInfoExample example);

    int deleteByExample(AgentBusInfoExample example);

    int insert(AgentBusInfo record);

    int insertSelective(AgentBusInfo record);

    List<AgentBusInfo> selectByExample(AgentBusInfoExample example);

    AgentBusInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentBusInfo record);

    int updateByPrimaryKey(AgentBusInfo record);

    /**
     * 代理商业务列表查询
     * @param par
     * @return
     */
    List<Map<String,Object>> queryAgentBusList(@Param("par") Map<String,Object> par);

    /**
     * 代理商业务列表统计
     * @param par
     * @return
     */
    int queryAgentBusListCount(@Param("par") Map<String,Object> par);


    List<Map<String,Object>> queryBusinessPlatformList(@Param("reqMap") Map<String,Object> reqMap,@Param("page")Page page);


    int queryBusinessPlatformCount(@Param("reqMap") Map<String,Object> reqMap);

    List<AgentBusInfo> businessQuery(String id);

    Map<String,Object> queryAgentName(String id);

    List<Map<String,Object>> queryBusInfoAndRemit(String agentId);

    List<Map<String,Object>> queryBusInfoAndRemitByBusId(String busId);

    List<Map<String,Object>> queryById(String agentId);


    List<Map<String,Object>> queryTreeByBusInfo(Map<String,Object> reqMap);

    List<BusinessOutVo> excelAgent(@Param("reqMap")Map<String,Object> reqMap);

    List<Map<String,Object>> queryAgentNameByBusId();

    List<Map<String,Object>> queryByBusNum(String busNum);

    /**根据代理商信息查询业务类型*/
    List<AgentBusInfo> selectByAgenId(String agentId);

    /*业务信息上级查询*/
    Map<String,Object> queryBusInfoParent(Map<String,Object> reqMap);

    List<Map<String,Object>> queryEditAgentMerge(Map<String,Object> reqMap);

    /**
     * 询有重复的打款公司的代理商
     * @return
     */
    List<String> queryAgentHaveMutPayCompany();

    List<Map<String,Object>> selectAgentHaveMutPayCompany();
}