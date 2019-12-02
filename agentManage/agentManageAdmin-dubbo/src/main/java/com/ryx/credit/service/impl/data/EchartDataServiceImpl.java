package com.ryx.credit.service.impl.data;

import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.service.data.EchartDataService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private AgentMapper agentMapper;
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

    @Override
    public PageInfo echartDataList(Page page, Map map, Long userId) {
        PageInfo pageInfo = new PageInfo();
        List<Map<String,Object>> echartDataMapList = agentMapper.queryEchartDataList(map, page);
       if(null!=echartDataMapList && echartDataMapList.size()!=0){
           for (Map<String, Object> EchartDataMap : echartDataMapList) {
               if(null==EchartDataMap.get("RAPPROVE")){
                   EchartDataMap.put("RAPPROVE",0);
               }
               if(null==EchartDataMap.get("RAPPROVING")){
                   EchartDataMap.put("RAPPROVING",0);
               }
               if(null==EchartDataMap.get("PHL")){
                   EchartDataMap.put("PHL",0);
               }
               if(null==EchartDataMap.get("PDL")){
                   EchartDataMap.put("PDL",0);
               }
               if(null==EchartDataMap.get("FHL")){
                   EchartDataMap.put("FHL",0);
               }
               if(null==EchartDataMap.get("THSPZ")){
                   EchartDataMap.put("THSPZ",0);
               }
               if(null==EchartDataMap.get("YTH")){
                   EchartDataMap.put("YTH",0);
               }
           }
       }
        pageInfo.setRows(echartDataMapList);
        pageInfo.setTotal(agentMapper.queryEchartDataCount(map));
        return pageInfo;
    }
}
