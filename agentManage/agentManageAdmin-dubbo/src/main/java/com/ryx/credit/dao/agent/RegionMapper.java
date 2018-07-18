package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.Region;
import com.ryx.credit.pojo.admin.agent.RegionExample;
import java.math.BigDecimal;
import java.util.List;

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

    int findCountByPcode(String pCode);

    List<Region> selectAll();
}