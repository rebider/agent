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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class ProfitDTimer {
    protected Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private IProfitDService profitDService;
    @Autowired
    private IdService idService;


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
                (AppConfig.getProperty("profit.day"),params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            //logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<HashMap> list = JSONObject.parseObject(data,List.class);
        System.out.println(data);
    }

    /**
     * 同步日结分润数据
     * @param transDate1 交易时间起（空则为当前日期上一天）
     * @param transDate2 交易时间止（空则为当前日期上一天）
     */
    public void synchroProfitD(String transDate1,String transDate2){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("transStartDate",transDate1==null?DateUtil.getAfterDayDate("-1",DateUtil.sdfDays):transDate1);
        map.put("transEndDate",transDate2==null?DateUtil.getAfterDayDate("-1",DateUtil.sdfDays):transDate2);
        map.put("pageNumber","1");
        map.put("pageSize","20");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.day"),params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<HashMap> list = JSONObject.parseObject(data,List.class);
        try {
            insertProfitD(list);
        } catch (Exception e) {
            logger.error("同步插入数据失败！");
            e.printStackTrace();
        }
    }

    public void insertProfitD(List<HashMap> profitDays){
        for(HashMap map:profitDays){
            ProfitDay profitD = new ProfitDay();
            profitD.setId(idService.genId(TabId.p_profit_adjust));
            profitD.setAgentId((String)map.get("AGENTID"));
            profitD.setAgentName((String)map.get("AGENTNAME"));
            profitD.setDailyMakeup((BigDecimal) map.get("DAILYMAKEUP"));
            profitD.setRemitDate((String)map.get("REMITDATE"));
            profitD.setTransDate((String)map.get("TRANSDATE"));
            profitD.setAgentPid((String)map.get("AGENTPID"));
            profitD.setRhbProfit((BigDecimal) map.get("RHBPROFIT"));
            profitD.setZfProfit((BigDecimal) map.get("ZFPROFIT"));
            profitD.setTotalProfit((BigDecimal) map.get("TOTALPROFIT"));
            profitDService.insert(profitD);
        }
    }
}
