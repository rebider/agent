package com.ryx.credit.dao.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.order.TerminalTransfer;
import com.ryx.credit.pojo.admin.order.TerminalTransferExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TerminalTransferMapper {
    long countByExample(TerminalTransferExample example);

    int deleteByExample(TerminalTransferExample example);

    int insert(TerminalTransfer record);

    int insertSelective(TerminalTransfer record);

    List<TerminalTransfer> selectByExample(TerminalTransferExample example);

    TerminalTransfer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TerminalTransfer record);

    int updateByPrimaryKey(TerminalTransfer record);

    List<Map<String, Object>> selectTerminalTransferList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int selectTerminalTransferCount(@Param("map") Map<String, Object> map);

    List<String> appTerminalTransfer();

    Map<String, Object> getAgentType(String busNum);

    Map<String, Object> querySubBusNumTopAgent(String bus_num);

    List<Map<String, Object>> querySubBusNumTopAgentAll(String bus_num);

    Map<String, Object> querySubBusNumAgent(String bus_num);

    List<Map<String, Object>> getSN(@Param("common") String common);

    List<Map<String, Object>> querySubBusNum(String AGENT_ID);

    Map<String, Object> queryPlatFrom(String PLATFORM_NUM);

    List<Map<String, Object>> queryBusInfo(Map<String,String> param);

    int checkSnIsTransfer(Map map);

    /**
     * 查询划拨配置
     * @param param
     * @return
     */
    List<Map<String, Object>> queryToolsFloor(Map<String,String> param);

    /**
     * 根据平台码查询代理商基础配置
     * @param BUS_NUM 平台码
     * @return
     */
    Map<String, Object> agentBase(@Param("BUS_NUM") String BUS_NUM);

}