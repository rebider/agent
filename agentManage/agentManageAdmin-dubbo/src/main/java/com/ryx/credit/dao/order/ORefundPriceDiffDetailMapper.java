package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetailExample;

import java.util.List;

public interface ORefundPriceDiffDetailMapper {
    long countByExample(ORefundPriceDiffDetailExample example);

    int deleteByExample(ORefundPriceDiffDetailExample example);

    int insert(ORefundPriceDiffDetail record);

    int insertSelective(ORefundPriceDiffDetail record);

    List<ORefundPriceDiffDetail> selectByExample(ORefundPriceDiffDetailExample example);

    ORefundPriceDiffDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ORefundPriceDiffDetail record);

    int updateByPrimaryKey(ORefundPriceDiffDetail record);
}