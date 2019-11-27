package com.ryx.credit.service.data;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;

import java.util.Map;

/**
 * @Description: 图表数据获取
 * @Author: ZhaoYd
 * @Date: 2019/11/15
 */
public interface EchartDataService {

    /**
     * 获取图表数据
     * @param paramMap
     * @return
     */
    Map<String, Object> getChartData(Map<String, Object> paramMap);

    PageInfo echartDataList(Page page, Map map, Long userId);
}