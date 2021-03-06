package com.ryx.credit.dao.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.order.OrganizationExample;
import com.ryx.credit.pojo.admin.vo.OrganizationSerchVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrganizationMapper {
    long countByExample(OrganizationExample example);

    int deleteByExample(OrganizationExample example);

    int insert(Organization record);

    int insertSelective(Organization record);

    int updateByPrimaryKeySelective(Organization record);

    int updateByPrimaryKey(Organization record);

    Organization selectByPrimaryKey(String orgId);

    List<Organization> selectByExample(OrganizationExample example);

    List<Map<String,Object>> organizationList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int organizationCount(@Param("map")Map<String, Object> map);

    List<Organization> selectOrganization(@Param("orgId")String orgId);

    List<Organization> queryByOrganName(Map map);

    List<OrganizationSerchVo> queryOrganization(@Param("orgId")String orgId);
}