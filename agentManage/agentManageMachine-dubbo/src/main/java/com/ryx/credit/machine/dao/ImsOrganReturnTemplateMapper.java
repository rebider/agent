package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsOrganReturnTemplate;
import com.ryx.credit.machine.entity.ImsOrganReturnTemplateExample;
import java.util.List;

public interface ImsOrganReturnTemplateMapper {
    long countByExample(ImsOrganReturnTemplateExample example);

    int deleteByExample(ImsOrganReturnTemplateExample example);

    int insert(ImsOrganReturnTemplate record);

    int insertSelective(ImsOrganReturnTemplate record);

    List<ImsOrganReturnTemplate> selectByExample(ImsOrganReturnTemplateExample example);

    ImsOrganReturnTemplate selectByPrimaryKey(String templateId);

    int updateByPrimaryKeySelective(ImsOrganReturnTemplate record);

    int updateByPrimaryKey(ImsOrganReturnTemplate record);
}