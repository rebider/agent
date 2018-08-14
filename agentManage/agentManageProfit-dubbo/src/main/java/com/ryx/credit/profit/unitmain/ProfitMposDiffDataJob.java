package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.service.IProfitDService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 手刷补差数据同步
 */
public class ProfitMposDiffDataJob {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private IProfitDService profitDService;
    @Autowired
    private IdService idService;
    private int index = 1;


    public static void main(String agrs[]){
        HashMap<String,String> map = new HashMap<String,String>();
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.bucha"),params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            //logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        System.out.println(data);
    }

    public void synchroProfitDiff(String month){
        HashMap<String,String> map = new HashMap<String,String>();
        month = month==null? DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6):month;
        map.put("Frmonth",month);
        map.put("pageNumber",index++ +"");
        map.put("pageSize","50");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.bucha"),params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        try {
            if(list.size()>0){
                insertProfitD(list,month);
            }
        } catch (Exception e) {
            logger.error("同步插入数据失败！");
            e.printStackTrace();
        }
    }

    public void insertProfitD(List<JSONObject> profitDays,String date){
        for(JSONObject json:profitDays){
            ProfitDay profitD = new ProfitDay();
            profitD.setId(idService.genId(TabId.P_PROFIT_D));

            profitDService.insert(profitD);

        }
        synchroProfitDiff(date);
    }
}
