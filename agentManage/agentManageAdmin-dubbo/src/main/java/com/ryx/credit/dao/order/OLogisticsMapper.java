package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OLogistics;
import com.ryx.credit.pojo.admin.order.OLogisticsExample;
import java.util.List;
import java.util.Map;

public interface OLogisticsMapper {
    int countByExample(OLogisticsExample example);

    int deleteByExample(OLogisticsExample example);

    int insert(OLogistics record);

    int insertSelective(OLogistics record);

    List<OLogistics> selectByExample(OLogisticsExample example);

    int updateByPrimaryKeySelective(OLogistics record);

    int updateByPrimaryKey(OLogistics record);

    ////////////////////////////////////////////////////////////////////

    List<Map<String,Object>> getOLogisticsList(Map <String, Object> param);

    Long getOLogisticsCount(Map <String, Object> param);

    List<Map<String,Object>> queryLogisticsList(Map <String, Object> param);

}