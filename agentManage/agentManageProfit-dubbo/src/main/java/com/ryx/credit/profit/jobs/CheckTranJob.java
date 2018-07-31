package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.commons.utils.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * @author zhaodw
 * @Title: CheckTranJob
 * @ProjectName agentManage
 * @Description: 交易核对任务
 * @date 2018/7/2911:34
 */
@Service("checkTranJob")
public class CheckTranJob {

    private static final  String URL =  AppConfig.getProperty("check_tran_url");

//    @Scheduled(cron = "0 0/2 * * * ?")
    public void deal() {
        System.out.println(getTranDate());
    }

    private JSONObject getTranDate() {
        JSONObject json = new JSONObject();
        json.put("tranType","22");
        json.put("tranMon",   LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6));
        String result = HttpClientUtil.doPostJson(URL, json.toJSONString());
        if (StringUtils.isNotBlank(result)) {
            return  JSONObject.parseObject(result);
        }
        return null;
    }
}
