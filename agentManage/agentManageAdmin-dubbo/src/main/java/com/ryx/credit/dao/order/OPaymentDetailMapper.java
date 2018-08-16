package com.ryx.credit.dao.order;



import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.order.OPaymentDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    void updatePaymentDetailByPaymentId(@Param("paymentId") String paymentId,@Param("srcId") String srcId,@Param("srcType") String srcType);

    List<Map<String,Object>> selectShareMoney(@Param("map") Map<String, Object> map);

}