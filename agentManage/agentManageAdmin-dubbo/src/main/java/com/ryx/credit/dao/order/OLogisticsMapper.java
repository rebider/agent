package com.ryx.credit.dao.order;



import com.ryx.credit.pojo.admin.order.OLogistics;
import com.ryx.credit.pojo.admin.order.OLogisticsExample;

import java.util.List;

public interface OLogisticsMapper {
    int countByExample(OLogisticsExample example);

    int deleteByExample(OLogisticsExample example);

    int insert(OLogistics record);

    int insertSelective(OLogistics record);

    List<OLogistics> selectByExample(OLogisticsExample example);

    OLogistics selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OLogistics record);

    int updateByPrimaryKey(OLogistics record);
}