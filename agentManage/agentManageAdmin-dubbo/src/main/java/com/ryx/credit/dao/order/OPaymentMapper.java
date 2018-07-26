package com.ryx.credit.dao.order;



import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.pojo.admin.order.OPaymentExample;

import java.util.List;

public interface OPaymentMapper {
    long countByExample(OPaymentExample example);

    int deleteByExample(OPaymentExample example);

    int insert(OPayment record);

    int insertSelective(OPayment record);

    List<OPayment> selectByExample(OPaymentExample example);

    OPayment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OPayment record);

    int updateByPrimaryKey(OPayment record);
}