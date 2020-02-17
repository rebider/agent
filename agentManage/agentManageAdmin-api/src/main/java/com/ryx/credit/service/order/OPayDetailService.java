package com.ryx.credit.service.order;


import java.util.Map;

public interface OPayDetailService {
    Map<String, Object> getAdjDetail(String srcId, String payType);
}
