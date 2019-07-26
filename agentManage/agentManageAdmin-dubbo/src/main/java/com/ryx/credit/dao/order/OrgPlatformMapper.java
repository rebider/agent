package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OrgPlatform;
import com.ryx.credit.pojo.admin.order.OrgPlatformExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrgPlatformMapper {
    long countByExample(OrgPlatformExample example);

    int deleteByExample(OrgPlatformExample example);

    int insert(OrgPlatform record);

    int insertSelective(OrgPlatform record);

    List<OrgPlatform> selectByExample(OrgPlatformExample example);

    OrgPlatform selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrgPlatform record);

    int updateByPrimaryKey(OrgPlatform record);

    int deleteOrgPlatform(@Param("id") String id);
}