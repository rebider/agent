package com.ryx.credit.service.order;

import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/9/11.
 */
public interface OLogisticsDetailService {

    List<Map<String, Object>> queryCompensateLList(String beginSn, String endSn);

    List<String> querySnLList(String beginSn,String endSn);

    int querySnCount(String beginSn,String endSn);
}
