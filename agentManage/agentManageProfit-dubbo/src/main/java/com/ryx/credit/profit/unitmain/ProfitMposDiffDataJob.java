package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.profit.dao.ProfitSupplyDiffMapper;
import com.ryx.credit.profit.pojo.ProfitSupplyDiff;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 手刷补差数据同步 cxinfo 手刷补差数据同步 汪勇
 */
@Service("profitMposDiffDataJob")
public class ProfitMposDiffDataJob {

    org.slf4j.Logger logger = LoggerFactory.getLogger(NewProfitMonthMposDataJob.class);

    @Autowired
    private ProfitSupplyDiffMapper supplyDiffMapper;
    @Autowired
    private IdService idService;
    @Autowired
    ProfitSupplyDiffMapper profitSupplyDiffMapper;

    private int index = 1;


    public static void main(String agrs[]) {
        HashMap<String, String> map = new HashMap<String, String>();
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.bucha"), params);
        System.out.println(res);
        if (!JSONObject.parseObject(res).get("respCode").equals("000000")) {
            //logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败", "日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data, List.class);
        System.out.println(data);
    }

    /**
     * @Author: Zhang Lei
     * @Description: 手刷补差数据同步，每月3号10:30
     * @Date: 11:04 2019/1/24
     */
    @Scheduled(cron = "0 30 10 3 * ?")
    public void doCron(){
        String month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date(), -1)).substring(0, 6);
        excute(month);
    }

    @Transactional
    public void excute(String month) {
        month = month == null ? DateUtil.sdfDays.format(DateUtil.addMonth(new Date(), -1)).substring(0, 6) : month;
        long t1 = System.currentTimeMillis();
        index = 1;
        logger.info("========={}月手刷补差数据同步开始==========", month);

        //删除现有数据
        profitSupplyDiffMapper.deleteByMonth(month);

        synchroProfitDiff(month);

        long t2 = System.currentTimeMillis();
        logger.info("========={}月手刷补差数据同步结束，耗时{}ms==========", month, (t2 - t1));

    }

    public void synchroProfitDiff(String month) {

        try {

            //同步数据
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("frmonth", month);
            map.put("pageNumber", index++ + "");
            map.put("pageSize", "50");
            String params = JsonUtil.objectToJson(map);

            logger.debug("{}月手刷补差数据同步请求参数:{}", month, params);
            String res = HttpClientUtil.doPostJson(AppConfig.getProperty("profit.bucha"), params);
            logger.debug("{}月手刷补差数据同步返回数据:{}", month, res);


            JSONObject json = JSONObject.parseObject(res);
            if (!json.get("respCode").equals("000000")) {
                logger.error("手刷补差数据同步失败！");
                AppConfig.sendEmails("手刷补差数据同步失败! respCode=" + json.get("respCode") + ", respMsg=" + json.get("respMsg"), "手刷补差数据同步失败");
                throw new RuntimeException("手刷补差数据同步失败");
            }


            String data = JSONObject.parseObject(res).get("data").toString();
            List<JSONObject> list = JSONObject.parseObject(data, List.class);
            if (list.size() > 0) {
                insertProfitDiff(list, month);
            }

        } catch (Exception e) {
            logger.error("手刷补差数据同步失败！");
            e.printStackTrace();
            throw new RuntimeException("手刷补差数据同步失败");
        }

    }

    public void insertProfitDiff(List<JSONObject> profitDays, String date) {
        for (JSONObject json : profitDays) {
            logger.info("手刷补差数据同步{}", json.getString("AGENTID"));
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
        synchroProfitDiff(date);
    }

}
