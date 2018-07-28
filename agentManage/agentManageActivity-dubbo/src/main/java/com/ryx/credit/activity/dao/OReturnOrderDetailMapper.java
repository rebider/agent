package com.ryx.credit.activity.dao;

import com.ryx.credit.activity.entity.OReturnOrderDetail;
import com.ryx.credit.activity.entity.OReturnOrderDetailExample;
import java.util.List;

public interface OReturnOrderDetailMapper {
    long countByExample(OReturnOrderDetailExample example);

    int deleteByExample(OReturnOrderDetailExample example);

    int insert(OReturnOrderDetail record);

    int insertSelective(OReturnOrderDetail record);

    List<OReturnOrderDetail> selectByExample(OReturnOrderDetailExample example);

    OReturnOrderDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OReturnOrderDetail record);

    int updateByPrimaryKey(OReturnOrderDetail record);
}