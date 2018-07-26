package com.ryx.credit.service.order;

import com.ryx.credit.common.util.PageInfo;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/07/21
 * 排单管理：物流信息
 */
public interface OLogisticsService {

    PageInfo getOLogisticsList(Map<String, Object> param, PageInfo pageInfo);



}
