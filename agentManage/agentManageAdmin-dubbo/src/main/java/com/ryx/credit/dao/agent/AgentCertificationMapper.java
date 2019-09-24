package com.ryx.credit.dao.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.AgentCertification;
import com.ryx.credit.pojo.admin.agent.AgentCertificationExample;
import com.ryx.credit.pojo.admin.vo.AgentCertifiVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AgentCertificationMapper {
    long countByExample(AgentCertificationExample example);

    int deleteByExample(AgentCertificationExample example);

    int insert(AgentCertification record);

    int insertSelective(AgentCertification record);

    List<AgentCertification> selectByExample(AgentCertificationExample example);

    AgentCertification selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AgentCertification record);

    int updateByPrimaryKey(AgentCertification record);
    //查询当前认证信息明细总计
    int queryAgentCerDetailsCount(@Param("map") Map<String,Object> map);
    //查询当前认证信息明细
    List<Map<String,Object>> queryAgentCerDetails(@Param("map") Map<String,Object> map,@Param("page") Page page);
    //查询当前认证信息明细总计
    int queryAgentCerDetailsSingleCount(@Param("map") Map<String,Object> map);
    //查询当前认证信息明细
    List<Map<String,Object>> queryAgentCerDetailsSingle(@Param("map") Map<String,Object> map,@Param("page") Page page);

    //批量插入待认证信息
    int insertBatch(@Param("agentCertifications") List<AgentCertification> agentCertifications);

    List<Map<String,Object>> queryAgentCerList(@Param("map") Map<String,Object> map,@Param("page") Page page);

    int queryAgentCerListCount(@Param("map") Map<String,Object> map);

    List<AgentCertifiVo> exportAgentcertifis(@Param("map") Map<String,Object> map);

    AgentCertification queryMaxIdByAgentid(@Param("map") Map<String,Object> map);

}