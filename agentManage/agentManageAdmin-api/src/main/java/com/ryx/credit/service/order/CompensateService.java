package com.ryx.credit.service.order;

import com.ryx.credit.pojo.admin.order.OSubOrder;

import java.util.List;

/**
 * Created by RYX on 2018/7/24.
 */
public interface CompensateService {

    OSubOrder getOrderMsgByExcel(List<Object> excelList);

}

