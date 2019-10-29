package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.AnnoPlatformRela;
import com.ryx.credit.pojo.admin.agent.AnnoPlatformRelaExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AnnoPlatformRelaMapper {
    long countByExample(AnnoPlatformRelaExample example);

    int deleteByExample(AnnoPlatformRelaExample example);

    int insert(AnnoPlatformRela record);

    int insertSelective(AnnoPlatformRela record);

    List<AnnoPlatformRela> selectByExample(AnnoPlatformRelaExample example);

    AnnoPlatformRela selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AnnoPlatformRela record);

    int updateByPrimaryKey(AnnoPlatformRela record);

    List<String> selectAnnoIds(@Param("map")Map<String,Object> map);

    int saveBatch(@Param("records") List<AnnoPlatformRela> records);

}