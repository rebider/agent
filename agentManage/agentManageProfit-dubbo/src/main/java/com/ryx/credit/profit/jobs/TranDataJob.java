package com.ryx.credit.profit.jobs;/**
 * @Auther: zhaodw
 * @Date: 2018/8/1 10:12
 * @Description:
 */

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.ProfitOrganTranMonth;
import com.ryx.credit.profit.service.ProfitOrganTranMonthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 交易总量获取
 * @author zhaodw
 * @create 2018/8/1
 * @since 1.0.0
 */
@Service("tranDataJob")
public class TranDataJob {

    private Logger LOGGER = LoggerFactory.getLogger(TranDataJob.class);
    private static final  String URL =  AppConfig.getProperty("check_tran_url");


    @Autowired
    private ProfitOrganTranMonthService profitOrganTranMonthService;

    //   @Scheduled(cron = "0 30 0 10 * ?")
    public void deal() {
        String settleMonth = "201806";//LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        JSONObject json = getTranData(settleMonth);
        if (json != null && json.containsKey("info")) {
            BigDecimal posSumAmt = json.getJSONObject("info").getBigDecimal("zydlPosAmt");
            ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
            profitOrganTranMonth.setProfitDate(settleMonth);
            PageInfo pageInfo = profitOrganTranMonthService.getProfitOrganTranMonthList(profitOrganTranMonth, null);
        }

    }

    private JSONObject getTranData(String settleMonth) {
        JSONObject json = new JSONObject();
        json.put("tranType","22");
        json.put("tranMon",   settleMonth);
        String result = HttpClientUtil.doPostJson(URL, json.toJSONString());
        if (StringUtils.isNotBlank(result)) {
            return  JSONObject.parseObject(result);
        }
        return null;
    }
}
