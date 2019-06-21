package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.Region;
import com.ryx.credit.pojo.admin.agent.RegionExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RegionMapper {

    int countByExample(RegionExample example);

    int deleteByExample(RegionExample example);

    int insert(Region record);

    int insertSelective(Region record);

    List<Region> selectByExample(RegionExample example);

    Region selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);

    List<Region> findByPcode(String pCode);

    int findCountByPcode(@Param("map") Map<String, Object> map);

    List<Region> selectAll();

    Region findByRcode(String rCode);

}