package com.ryx.credit.service.order;

import com.ryx.credit.pojo.admin.order.OPayDetail;

import java.util.Map;

public interface OPayDetailService {
    Map<String, Object> getAdjDetail(String srcId, String payType, String agentId);
}
