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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * 手刷日结分润数据同步、定时
 */
@Service
@Transactional(rollbackFor=RuntimeException.class)
public class ProfitDayMposDataJob {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private IProfitDService profitDService;
    @Autowired
    private IdService idService;
    private int index = 1;//页数

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
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        System.out.println(data);
    }

    /**
     * 同步日结分润数据
     * @param transDate1 交易时间起（空则为当前日期上2天）yyyymmdd
     * @param transDate2 交易时间止（空则为当前日期上2天）yyyymmdd
     */
    public void synchroProfitD(String transDate1,String transDate2){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("transStartDate",transDate1==null?DateUtil.getAfterDayDate("-2",DateUtil.sdfDays):transDate1);
        map.put("transEndDate",transDate2==null?DateUtil.getAfterDayDate("-2",DateUtil.sdfDays):transDate2);
        map.put("pageNumber",index++ +"");
        map.put("pageSize","50");
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
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        try {
            if(list.size()>0){
                insertProfitD(list,transDate1,transDate2);
            }
        } catch (Exception e) {
            logger.error("同步插入数据失败！");
            e.printStackTrace();
            throw new RuntimeException("分润数据处理失败");
        }
    }

    public void insertProfitD(List<JSONObject> profitDays,String transDate1,String transDate2){
        for(JSONObject json:profitDays){
            ProfitDay profitD = new ProfitDay();
            profitD.setId(idService.genId(TabId.P_PROFIT_D));
            profitD.setAgentId(json.getString("AGENTID"));
            profitD.setAgentName(json.getString("AGENTNAME"));
            profitD.setRemitDate(json.getString("REMITDATE"));
            profitD.setTransDate(json.getString("TRANSDATE"));
            profitD.setAgentPid(json.getString("AGENTPID"));
            profitD.setTotalProfit(json.getBigDecimal("TOTALPROFIT"));
            profitD.setFrozenMoney(json.getBigDecimal("FROZENMONEY "));
            profitD.setSuccessMoney(json.getBigDecimal("SUCCESSMONEY "));
            profitD.setFailMoney(json.getBigDecimal("FAILMONEY "));
            profitD.setRedoMoney(json.getBigDecimal("REDOMONEY "));
            profitD.setReturnMoney(json.getBigDecimal("RETURNMONEY "));
            profitD.setRealMoney(json.getBigDecimal("REALMONEY "));
            profitDService.insertSelective(profitD);
        }
        synchroProfitD(transDate1,transDate2);
    }
}
