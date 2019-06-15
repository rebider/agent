package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.Organization;
import com.ryx.credit.pojo.admin.order.OrganizationExample;

import java.util.List;
import java.util.Map;

public interface OrganizationMapper {
    long countByExample(OrganizationExample example);

    int deleteByExample(OrganizationExample example);

    int insert(Organization record);

    int insertSelective(Organization record);

    List<Organization> selectByExample(OrganizationExample example);

    List<Organization> queryByOrganName(Map map);
}