package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OLogisticsDetail;
import com.ryx.credit.pojo.admin.order.OLogisticsDetailExample;

import java.util.List;

public interface OLogisticsDetailMapper {
    long countByExample(OLogisticsDetailExample example);

    int deleteByExample(OLogisticsDetailExample example);

    int insert(OLogisticsDetail record);

    int insertSelective(OLogisticsDetail record);

    List<OLogisticsDetail> selectByExample(OLogisticsDetailExample example);

    OLogisticsDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OLogisticsDetail record);

    int updateByPrimaryKey(OLogisticsDetail record);
}