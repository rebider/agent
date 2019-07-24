package com.ryx.credit.dao.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailH;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailHExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OLogisticsDetailHMapper {
    long countByExample(OLogisticsDetailHExample example);

    int deleteByExample(OLogisticsDetailHExample example);

    int insert(OLogisticsDetailH record);

    int insertSelective(OLogisticsDetailH record);

    List<OLogisticsDetailH> selectByExample(OLogisticsDetailHExample example);

    OLogisticsDetailH selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OLogisticsDetailH record);

    int updateByPrimaryKey(OLogisticsDetailH record);

    List<Map<String,Object>> getOLogDetailHistoryList(@Param("map") Map<String, Object> map, @Param("page") Page page);

    int getOLogDetailHistoryCount(@Param("map") Map<String, Object> map);

    List<Map<String,Object>>  queryMultipleOrderLogicInfo();
}