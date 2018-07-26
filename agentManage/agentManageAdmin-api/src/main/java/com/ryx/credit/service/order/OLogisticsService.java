package com.ryx.credit.service.order;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.order.OLogistics;
import com.ryx.credit.pojo.admin.order.OLogisticsUtil;

import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/07/21
 * 排单管理：物流信息
 */
public interface OLogisticsService {

    Object oLogisticsList(PageInfo pageInfo);

    OLogistics selectByPrimaryKey(String id);

    PageInfo getOLogisticsList(Map<String, Object> param, PageInfo pageInfo);

    List<Map<String, Object>> getLogisticsBySn(String startSn, String endSn, String agentId) throws ProcessException;
}
