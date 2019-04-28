package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OLogisticsDetailH;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailHExample;

import java.util.List;

public interface OLogisticsDetailHMapper {
    long countByExample(OLogisticsDetailHExample example);

    int deleteByExample(OLogisticsDetailHExample example);

    int insert(OLogisticsDetailH record);

    int insertSelective(OLogisticsDetailH record);

    List<OLogisticsDetailH> selectByExample(OLogisticsDetailHExample example);

    OLogisticsDetailH selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OLogisticsDetailH record);

    int updateByPrimaryKey(OLogisticsDetailH record);
}