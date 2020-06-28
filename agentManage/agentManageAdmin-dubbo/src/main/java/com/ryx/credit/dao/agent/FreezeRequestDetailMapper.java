package com.ryx.credit.dao.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.agent.FreezeRequestDetail;
import com.ryx.credit.pojo.admin.agent.FreezeRequestDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FreezeRequestDetailMapper {
    long countByExample(FreezeRequestDetailExample example);

    int deleteByExample(FreezeRequestDetailExample example);

    int insert(FreezeRequestDetail record);

    int insertSelective(FreezeRequestDetail record);

    List<FreezeRequestDetail> selectByExample(FreezeRequestDetailExample example);

    FreezeRequestDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FreezeRequestDetail record);

    int updateByPrimaryKey(FreezeRequestDetail record);

    List<Map<String,Object>> queryFreezeDetials(@Param("map") Map<String,Object> map, @Param("page")Page page);

    int queryFreezeDetialsCount(@Param("map") Map<String,Object> map, @Param("page")Page page);
}