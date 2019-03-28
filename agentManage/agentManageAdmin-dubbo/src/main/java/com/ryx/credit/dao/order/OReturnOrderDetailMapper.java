package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OReturnOrderDetail;
import com.ryx.credit.pojo.admin.order.OReturnOrderDetailExample;
import java.util.List;
import java.util.Map;

public interface OReturnOrderDetailMapper {
    long countByExample(OReturnOrderDetailExample example);

    int deleteByExample(OReturnOrderDetailExample example);

    int insert(OReturnOrderDetail record);

    int insertSelective(OReturnOrderDetail record);

    List<OReturnOrderDetail> selectByExample(OReturnOrderDetailExample example);

    OReturnOrderDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OReturnOrderDetail record);

    int updateByPrimaryKey(OReturnOrderDetail record);

    int checkSnIsReturn(Map par);
}