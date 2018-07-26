package com.ryx.credit.dao.order;



import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.OPaymentDetailExample;

import java.util.List;

public interface OPaymentDetailMapper {
    int countByExample(OPaymentDetailExample example);

    int deleteByExample(OPaymentDetailExample example);

    int insert(OPaymentDetail record);

    int insertSelective(OPaymentDetail record);

    List<OPaymentDetail> selectByExample(OPaymentDetailExample example);

    OPaymentDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OPaymentDetail record);

    int updateByPrimaryKey(OPaymentDetail record);

    OPaymentDetail selectById(String id);

}