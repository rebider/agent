package com.ryx.credit.service.data;

import java.util.Map;

/**
 * @description：图表服务层
 * @author：ZhaoYd
 * @date：2019/11/14 13:30
 */
public interface EchartService {

    /**
     * 图表一数据获取
     * @param paramMap
     * @return
     */
    Map<String, Object> getDataForBigData(Map<String, Object> paramMap);

}
