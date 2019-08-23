package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.TemplateRecode;
import com.ryx.credit.profit.pojo.TemplateRecodeExample;

import java.util.List;
import java.util.Map;

public interface TemplateRecodeMapper {
    long countByExample(TemplateRecodeExample example);

    int deleteByExample(TemplateRecodeExample example);

    int insert(TemplateRecode record);

    int insertSelective(TemplateRecode record);

    List<TemplateRecode> selectByExample(TemplateRecodeExample example);

    TemplateRecode selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TemplateRecode record);

    int updateByPrimaryKey(TemplateRecode record);

    List<Map<String,String>> getAgentInfoByBusNum(String busNum);
}