package com.ryx.credit.dao.order;



import com.ryx.credit.pojo.admin.order.OLogistics;
import com.ryx.credit.pojo.admin.order.OLogisticsExample;
import com.ryx.credit.pojo.admin.order.OLogisticsUtil;
import org.apache.ibatis.annotations.Param;

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

    int countOLogistics(Map<String, Object> condition);

    List<OLogisticsUtil> selectOLogistics(Map<String, Object> condition);

    OLogistics selectByPrimaryKey(String id);

    List<OLogisticsUtil> selectExprotOLogistics(@Param("oLogistics")OLogistics oLogistics);

    List<Map<String,Object>> getOLogisticsList(Map <String, Object> param);

    Long getOLogisticsCount(Map <String, Object> param);
}