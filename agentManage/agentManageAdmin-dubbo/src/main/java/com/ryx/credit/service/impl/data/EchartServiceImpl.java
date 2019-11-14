package com.ryx.credit.service.impl.data;

import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.service.data.EchartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description：数据图图表实现类
 * @author：ZhaoYd
 * @date：2019/11/14 13:30
 */
@Service("echartService")
public class EchartServiceImpl implements EchartService {

    private static Logger logger = LoggerFactory.getLogger(EchartServiceImpl.class);

    /**
     * 图表一数据获取
     * @param paramMap
     * @return
     */
    @Override
    public Map<String, Object> getDataForBigData(Map<String, Object> paramMap) {
        List<String> xName = new ArrayList<>();
        xName.add("1");
        xName.add("2");
        xName.add("3");
        xName.add("4");
        xName.add("5");
        xName.add("6");
        List<String> yData = new ArrayList<>();
        yData.add("100");
        yData.add("45");
        yData.add("78");
        yData.add("300");
        yData.add("48");
        yData.add("28");
        return FastMap.fastMap("xName", xName).putKeyV("yData", yData);
    }
}
