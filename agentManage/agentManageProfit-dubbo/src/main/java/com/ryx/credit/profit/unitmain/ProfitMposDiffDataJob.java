package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.profit.dao.ProfitSupplyDiffMapper;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.ProfitSupplyDiff;
import com.ryx.credit.profit.service.IProfitDService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 手刷补差数据同步
 */
@Service
@Transactional(rollbackFor=RuntimeException.class)
public class ProfitMposDiffDataJob {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private ProfitSupplyDiffMapper supplyDiffMapper;
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

    public void excute(){
        synchroProfitDiff();
    }

    /**
     * 同步手刷月分润明细数据
     * transDate 交易日期（空则为上一月）
     * 每月5号上午8点：@Scheduled(cron = "0 0 5 8 * ?")
     * 2018.9.7 17:40："0 40 17 7 * ?"
     */
    @Scheduled(cron = "0 40 17 7 * ?")
    public void synchroProfitDiff(){
        String month = null;
        HashMap<String,String> map = new HashMap<String,String>();
        month = month==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date(),-2)).substring(0,6):month;
        map.put("frMonth", month);
        map.put("pageNumber", index++ +"");
        map.put("pageSize", "50");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.bucha"), params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data, List.class);
        try {
            if(list.size() > 0){
                insertProfitDiff(list, month);
            }
        } catch (Exception e) {
            logger.error("同步插入数据失败！");
            e.printStackTrace();
            throw new RuntimeException("分润数据处理失败");
        }
    }

    public void insertProfitDiff(List<JSONObject> profitDays, String date){
        for(JSONObject json : profitDays){
            ProfitSupplyDiff profitDiff = new ProfitSupplyDiff();
            profitDiff.setId(idService.genId(TabId.P_PROFIT_SUPPLU_DIFF));
            profitDiff.setAgentId(json.getString("AGENTID"));
            profitDiff.setAgentPid(json.getString("AGENTPID"));
            profitDiff.setDiffAmt(json.getBigDecimal("DIFFAMT"));
            profitDiff.setDiffDate(json.getString("DIFFDATE"));
            profitDiff.setDiffType(json.getString("DIFFTYPE"));
            profitDiff.setParentAgentid(json.getString("PARENTAGENTID"));
            profitDiff.setParentAgentpid(json.getString("PARENTAGENTPID"));
            profitDiff.setAgentName(json.getString("AGENTNAME"));
            profitDiff.setParentAgentname(json.getString("PARENTAGENTNAME"));
            supplyDiffMapper.insert(profitDiff);

        }
        synchroProfitDiff();
    }

}
