package com.ryx.credit.service.impl.data;

import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.service.data.EchartDataService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 图表数据获取实现类
 * @Author: ZhaoYd
 * @Date: 2019/11/15
 */
@Service("echartDataService")
public class EchartDataServiceImpl implements EchartDataService {

    /**
     * 图表数据获取
     * @param paramMap
     * @return
     */
    @Override
    public Map<String, Object> getChartData(Map<String, Object> paramMap) {

//        "[5, 20, 36, 10, 10, 20]";
//        ["衬衫", "羊毛衫", "雪纺衫", "裤子", "高跟鞋", "袜子"]
        List<String> xName = new ArrayList<>();
        List<String> yData = new ArrayList<>();
        xName.add("1");
        xName.add("2");
        xName.add("3");
        xName.add("4");
        xName.add("5");
        xName.add("6");
        yData.add("10");
        yData.add("30");
        yData.add("50");
        yData.add("40");
        yData.add("70");
        yData.add("20");
        return FastMap.fastMap("xName", xName).putKeyV("yData", yData);
    }
}
