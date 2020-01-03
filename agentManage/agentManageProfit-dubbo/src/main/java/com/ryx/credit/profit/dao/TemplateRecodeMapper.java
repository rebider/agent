package com.ryx.credit.profit.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.TemplateRecode;
import com.ryx.credit.profit.pojo.TemplateRecodeExample;
import org.apache.ibatis.annotations.Param;

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

    List<Map<String,String>> checkAgentDoc(@Param("busNum") String busNum, @Param("docDic") String docDic);

    List<TemplateRecode> getListByTem(@Param("page") Page page,@Param("record") TemplateRecode record,@Param("map") Map<String,String> map);

    int getCountByTem(@Param("record") TemplateRecode record,@Param("map") Map<String,String> map);

    List<Map<String, Object>> queryBusInfo(Map<String,String> param);

    Map<String,Object> queryPlatFrom(@Param("PLATFORM_NUM")String PLATFORM_NUM);
}