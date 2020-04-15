package com.ryx.credit.dao.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfoExample;
import com.ryx.credit.pojo.admin.vo.BusinessOutVo;
import org.apache.ibatis.annotations.Param;
import org.apache.kafka.common.protocol.types.Field;

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

    List<Map<String,Object>> selectDgKfp(@Param("dg")  String dg);

    List<Map<String,Object>> selectDgBkfp();

    List<Map<String,Object>> selectDs();

    List<Map<String,Object>> selectComp(@Param("agentId")  String agentId);

    int updateBusLoginNum(@Param("reqMap")Map<String, Object> reqMap);

    List<AgentBusInfo> selectBusInfo(@Param("reqMap")Map<String, Object> reqMap);

    // 有条件的分页查询
    List<AgentBusInfo> selectByConditionForPage(@Param("page")Page page, @Param("agentBusInfo") AgentBusInfo agentBusInfo);

    //通过订单ID查询业务信息
    AgentBusInfo selectByOrderId(String orderId);

    //查询排单对应的（退货子订单明细-->>查询原订单-->>查询原来排单-->>查询原来业务信息表--busNum）
    Map<String,Object> selectByReturnDetailId(Map<String, Object> reqMap);

    //通过ID查询编码，和POS升级首字母（后期可升级）
    Map<String, Object> selectByIdForPosUpSingCheck(@Param("id") String id);

    //通过busnum查询是否已经升级成功
    int selectByBusNum(Map<String, Object> paramMap);

    List<String> queryBusPlatform(@Param("reqMap") Map reqMap);
    List<String> queryAgDocPro(@Param("reqMap") Map reqMap);
    List<String> queryBusType(@Param("reqMap") Map reqMap);

    List<AgentBusInfo> queryBusinfo(@Param("map") Map<String, String> map);

    //顶级菜单客服服务-列表查询
    List<Map<String, Object>> queryBusinfoTopMenuList(@Param("reqMap")Map<String, Object> reqMap, @Param("page")Page page);
    int queryBusinfoTopMenuCount(@Param("reqMap") Map<String,Object> reqMap);
    // 返回字典对应 业务数据
    List<Map<String,Object>> queryTreeByBusInfoAndDict(Map<String,Object> reqMap);


    List<Map<String, Object>> queyrBusInfoByBusNumAndPlatformType(Map<String,Object> reqMap);
    List<Map<String, Object>> queyrBusInfoByBusNumAndPlatformTypePage(Map<String,Object> reqMap);
    Integer queyrBusInfoByBusNumAndPlatformTypePageCount(Map<String,Object> reqMap);
    List<Map<String, Object>> queryByBusInfo(Map<String, Object> map);

    List<String> selectBusNumByBusProCode(Map<String, Object> reqMap);
    List<Map<String, Object>> queryRegionByAccount(Map<String,Object> map);
}