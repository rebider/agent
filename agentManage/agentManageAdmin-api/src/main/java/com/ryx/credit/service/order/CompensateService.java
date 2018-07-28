package com.ryx.credit.service.order;

import com.ryx.credit.pojo.admin.order.OSubOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by RYX on 2018/7/24.
 */
public interface CompensateService {

    OSubOrder getOrderMsgByExcel(List<Object> excelList);

    BigDecimal calculateTotalPrice(String activityId, BigDecimal count);

    BigDecimal calculatePriceDiff(String subOrderId,String oldActivityId,String activityId,BigDecimal proNum);

}

