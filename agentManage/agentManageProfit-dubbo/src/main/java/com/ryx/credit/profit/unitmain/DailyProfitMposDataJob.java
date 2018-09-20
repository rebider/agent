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
import java.util.List;

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

    /**
     * 2018.9.10 11:10："0 10 11 10 * ?"
     */
//    @Scheduled(cron = "0 54 14 10 * ?")
//    public void excute(){
//        List<String> dates = new ArrayList<String>();
//        dates.add("20180701");
//        dates.add("20180702");
//        dates.add("20180703");
//        dates.add("20180704");
//        dates.add("20180705");
//        dates.add("20180706");
//        dates.add("20180707");
//        dates.add("20180708");
//        dates.add("20180709");
//        dates.add("20180710");
//        dates.add("20180711");
//        dates.add("20180712");
//        dates.add("20180713");
//        dates.add("20180714");
//        dates.add("20180715");
//        dates.add("20180716");
//        dates.add("20180717");
//        dates.add("20180718");
//        dates.add("20180719");
//        dates.add("20180720");
//        dates.add("20180721");
//        dates.add("20180722");
//        dates.add("20180723");
//        dates.add("20180724");
//        dates.add("20180725");
//        dates.add("20180726");
//        dates.add("20180727");
//        dates.add("20180728");
//        dates.add("20180729");
//        dates.add("20180730");
//        dates.add("20180731");
//        for(int i=0;i<dates.size();i++){
//            synchroProfitDay(dates.get(i));
//        }
//    }

    /**
     * 同步日结分润数据
     * 分润月份（空则为当前日期上2天）yyyymmdd
     * 每日凌晨5点：@Scheduled(cron = "0 0 5 * * ?")
     */
//    @Scheduled(cron = "0 23 15 17 * ?")
    public void synchroProfitDay(){
        String frDate = "20180705";
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
        synchroProfitDay();
    }

}
