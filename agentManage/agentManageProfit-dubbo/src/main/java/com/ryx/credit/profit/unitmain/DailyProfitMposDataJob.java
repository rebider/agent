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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        //map.put("frDate", "20180625");
        String frDate = null;
        map.put("frDate", frDate==null?DateUtil.getAfterDayDate("-2",DateUtil.sdfDays):frDate);
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
     * 同步日结分润数据
     * @param frDate 分润月份（空则为当前日期上2天）yyyymmdd
     */
    public void synchroProfitD(String frDate){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("frDate", frDate==null?DateUtil.getAfterDayDate("-2",DateUtil.sdfDays):frDate);
        map.put("pageNumber", index++ +"");
        map.put("pageSize", "2");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson(AppConfig.getProperty("profit.day"), params);
        System.out.println("输出================================" + res);
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
            profitD.setAgentId(json.getString("AGENCYID"));      //代理商编号
            profitD.setAgentName(json.getString("COMPANYNAME")); //代理商名称
            profitD.setTransDate(json.getString("TRANDATE"));    //交易时间
            profitD.setRemitDate(json.getString("BACKDATE"));    //打款时间
            profitD.setRedoMoney(json.getBigDecimal("REDOMONEY "));       //补款
            profitD.setTotalProfit(json.getBigDecimal("TOTALPROFIT"));    //应发金额
            profitD.setRealMoney(json.getBigDecimal("REALMONEY "));       //实发金额
            profitD.setFrozenMoney(json.getBigDecimal("FROZENMONEY "));   //冻结金额
            profitD.setSuccessMoney(json.getBigDecimal("SUCCESSMONEY ")); //成功金额
            profitD.setFailMoney(json.getBigDecimal("FAILMONEY "));       //失败金额
            profitD.setReturnMoney(json.getBigDecimal("RETURNMONEY "));   //返现金额
            profitD.setPlatformNum(json.getString("PLATFORMNUM "));       //平台编号
            profitDService.insertSelective(profitD);
        }
        synchroProfitD(frDate);
    }

    @Test
    public void DailyProfitTest(){
        String frDate = "20180623";
        synchroProfitD(frDate);
    }

}
