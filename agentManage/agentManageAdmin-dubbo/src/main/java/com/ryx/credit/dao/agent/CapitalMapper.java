package com.ryx.credit.dao.agent;


import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.pojo.admin.agent.CapitalExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CapitalMapper {
    int countByExample(CapitalExample example);

    int deleteByExample(CapitalExample example);

    int insert(Capital record);

    int insertSelective(Capital record);

    List<Capital> selectByExample(CapitalExample example);

    Capital selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Capital record);

    int updateByPrimaryKey(Capital record);

    public List<Capital> paymentQuery(String id);

    public List<Capital> selectAmount(String paymentId);

    List<Capital> selectByAgenId(String agentId);

    List<Map<String,Object>> getCapitalSummaryList(Map<String, Object> param);

    Long getCapitalSummaryCount(Map<String, Object> param);

    List<Map<String, Object>> queryCapitalList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int queryCapitalCount(@Param("map") Map<String, Object> map);
}