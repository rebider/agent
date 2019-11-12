package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OReceiptPro;
import com.ryx.credit.pojo.admin.order.OReceiptProExample;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OReceiptProMapper {
    long countByExample(OReceiptProExample example);

    int deleteByExample(OReceiptProExample example);

    int insert(OReceiptPro record);

    int insertSelective(OReceiptPro record);

    List<OReceiptPro> selectByExample(OReceiptProExample example);

    OReceiptPro selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OReceiptPro record);

    int updateByPrimaryKey(OReceiptPro record);

    BigDecimal receiptCountTotal(@Param("orderId") String orderId);
}