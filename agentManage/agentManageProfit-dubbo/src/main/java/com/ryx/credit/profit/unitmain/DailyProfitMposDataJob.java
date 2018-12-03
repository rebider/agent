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
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 首刷日结同步 cxinfo 三期分润汪勇
 */
@Service
@Transactional(rollbackFor=RuntimeException.class)
public class DailyProfitMposDataJob {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private IProfitDService profitDService;
    @Autowired
    private IdService idService;
    private int index = 1;  //页数

    public static void main(String agrs[]){
        HashMap<String,String> map = new HashMap<String,String>();
        String frDate = null;
        map.put("frDate", frDate==null?DateUtil.getAfterDayDate("-2", DateUtil.sdfDays):frDate);
        map.put("pageNumber", "1");
        map.put("pageSize", "2");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson(AppConfig.getProperty("profit.day"), params);
        System.out.println("输出================================" + res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            //logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败！","日分润同步失败！");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data, List.class);
        System.out.println("输出================================" + data);
    }

//    @Scheduled(cron = "0 20 10 22 * ?")
//    public void excute(){
//        List<String> dates = new ArrayList<String>();
//        dates.add("20181001");
//        dates.add("20181002");
//        dates.add("20181003");
//        dates.add("20181004");
//        dates.add("20181005");
//        dates.add("20181006");
//        dates.add("20181007");
//        dates.add("20181008");
//        dates.add("20181009");
//        dates.add("20181010");
//        dates.add("20181011");
//        dates.add("20181012");
//        dates.add("20181013");
//        dates.add("20181014");
//        dates.add("20181015");
//        dates.add("20181016");
//        dates.add("20181017");
//        dates.add("20181018");
//        dates.add("20181019");
//        dates.add("20181020");
//        dates.add("20181021");
//        dates.add("20181022");
//        dates.add("20181023");
//        dates.add("20181024");
//        dates.add("20181025");
//        dates.add("20181026");
//        dates.add("20181027");
//        dates.add("20181028");
//        dates.add("20181029");
//        dates.add("20181030");
//        dates.add("20181031");
//        for(int i = 0; i < dates.size(); i++){
//            synchroProfitDay(dates.get(i));
//            index = 1;
//        }
//    }

    /**
     * 同步日结分润数据
     * 分润月份（空则为当前日期上2天）yyyymmdd
     * 每日凌晨5点：@Scheduled(cron = "0 0 5 * * ?")
     */
//    @Scheduled(cron = "0 0 5 * * ?")
    public void synchroProfitDay(String frDate){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("frDate", frDate==null?frDate:frDate);
        map.put("pageNumber", index++ +"");
        map.put("pageSize", "50");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson(AppConfig.getProperty("profit.newday"), params);
        System.out.println("rj" + res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败！","日分润同步失败！");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data, List.class);
        try {
            if(list.size()>0){
                insertProfitD(list, frDate);
            }
        } catch (Exception e) {
            logger.error("同步插入数据失败！");
            e.printStackTrace();
            throw new RuntimeException("分润数据处理失败！");
        }
    }

    public void insertProfitD(List<JSONObject> profitDays, String frDate){
        for(JSONObject json:profitDays){
            ProfitDay profitD = new ProfitDay();
            profitD.setId(idService.genId(TabId.P_PROFIT_D));
            profitD.setAgentId(json.getString("AGENCYID"));//代理商编号
            profitD.setAgentName(json.getString("COMPANYNAME"));//代理商名称
            profitD.setTransDate(json.getString("TRANDATE"));//交易时间
            profitD.setRemitDate(json.getString("BACKDATE"));//打款时间
            profitD.setRedoMoney(json.getBigDecimal("REDOMONEY"));//补款
            profitD.setTotalProfit(json.getBigDecimal("TOTALPROFIT")==null?BigDecimal.ZERO:json.getBigDecimal("TOTALPROFIT"));//应发金额
            profitD.setRealMoney(json.getBigDecimal("REALMONEY")==null?BigDecimal.ZERO:json.getBigDecimal("REALMONEY"));//实发金额
            profitD.setFrozenMoney(json.getBigDecimal("FROZENMONEY")==null?BigDecimal.ZERO:json.getBigDecimal("FROZENMONEY"));//冻结金额
            profitD.setSuccessMoney(json.getBigDecimal("SUCCESSMONEY")==null?BigDecimal.ZERO:json.getBigDecimal("SUCCESSMONEY"));//成功金额
            profitD.setFailMoney(json.getBigDecimal("FAILMONEY")==null?BigDecimal.ZERO:json.getBigDecimal("FAILMONEY"));//失败金额
            profitD.setReturnMoney(json.getBigDecimal("RETURNMONEY")==null?BigDecimal.ZERO:json.getBigDecimal("RETURNMONEY"));//返现金额
            profitD.setPlatformNum(json.getString("PLATFORMNUM"));//平台编号
            profitDService.insertSelective(profitD);
        }
        synchroProfitDay(frDate);
    }

}
