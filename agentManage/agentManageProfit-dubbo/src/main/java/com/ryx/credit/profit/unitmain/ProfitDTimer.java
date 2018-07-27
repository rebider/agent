package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.service.IProfitDService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

public class ProfitDTimer {
    protected Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private IProfitDService profitDService;


    public static void main(String agrs[]){
        HashMap<String,String> map = new HashMap<String,String>();
        //map.put("paymoneyStartDate","20180724");
        //map.put("paymoneyEndDate","20180724");
        String transDate1 = null;
        String transDate2 = null;
        map.put("transStartDate",transDate1==null?DateUtil.getAfterDayDate("-2",DateUtil.sdfDays):transDate1);
        map.put("transEndDate",transDate2==null?DateUtil.getAfterDayDate("-2",DateUtil.sdfDays):transDate2);
        map.put("pageNumber","1");
        map.put("pageSize","20");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                ("http://12.3.10.161:8003/qtfr/agentInterface/queryfrbyday.do",params);
        System.out.println(res);
    }

    public void synchroProfitD(String transDate1,String transDate2){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("transStartDate",transDate1==null?DateUtil.getAfterDayDate("-1",DateUtil.sdfDays):transDate1);
        map.put("transEndDate",transDate2==null?DateUtil.getAfterDayDate("-1",DateUtil.sdfDays):transDate2);
        map.put("pageNumber","1");
        map.put("pageSize","20");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                ("http://12.3.10.161:8003/qtfr/agentInterface/queryfrbyday.do",params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        HashMap 
    }

    public void insertProfitD(List<ProfitDay> profitDays){
        for(ProfitDay profitD:profitDays){
            profitDService.insert(profitD);
        }
    }
}
