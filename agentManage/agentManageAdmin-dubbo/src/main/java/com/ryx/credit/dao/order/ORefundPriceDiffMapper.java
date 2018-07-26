package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.ORefundPriceDiff;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffExample;

import java.util.List;

public interface ORefundPriceDiffMapper {
    long countByExample(ORefundPriceDiffExample example);

    int deleteByExample(ORefundPriceDiffExample example);

    int insert(ORefundPriceDiff record);

    int insertSelective(ORefundPriceDiff record);

    List<ORefundPriceDiff> selectByExample(ORefundPriceDiffExample example);

    ORefundPriceDiff selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ORefundPriceDiff record);

    int updateByPrimaryKey(ORefundPriceDiff record);
}