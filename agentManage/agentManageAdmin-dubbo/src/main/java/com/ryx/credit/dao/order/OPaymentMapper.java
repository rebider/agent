package com.ryx.credit.dao.order;



import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.pojo.admin.order.OPaymentExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OPaymentMapper {
    long countByExample(OPaymentExample example);

    int deleteByExample(OPaymentExample example);

    int insert(OPayment record);

    int insertSelective(OPayment record);

    List<OPayment> selectByExample(OPaymentExample example);

    OPayment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OPayment record);

    int updateByPrimaryKey(OPayment record);

    List<Map<String,Object>> queryPaymentXXDK(@Param("params") Map<String,Object> params);
}