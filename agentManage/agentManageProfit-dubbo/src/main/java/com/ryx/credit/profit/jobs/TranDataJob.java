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
import java.util.List;

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

    //   @Scheduled(cron = "0 30 12 10 * ?")
    public void deal() {
        String settleMonth = "201806";//LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        JSONObject json = getTranData(settleMonth);
        if (json != null && json.containsKey("info")) {

//            BigDecimal zydlPosAmt = json.getJSONObject("info").getBigDecimal("zydlPosAmt");// 自营代理pos总金额
            BigDecimal zydlPosAmt = json.getJSONObject("info").getBigDecimal("zydlPosAmt");// 自营代理pos总金额
            BigDecimal zyPosAmt = json.getJSONObject("info").getBigDecimal("zyPosAmt");//自营交易总金额
            BigDecimal hyxJwAmt = json.getJSONObject("info").getBigDecimal("hyxJwAmt");//汇银讯境外卡交易总金额
            BigDecimal orgJwAmt = json.getJSONObject("info").getBigDecimal("orgJwAmt");//代理商境外卡交易总金额
            BigDecimal tranAmt = zydlPosAmt.subtract(zyPosAmt).subtract(hyxJwAmt).subtract(orgJwAmt);
            ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
            profitOrganTranMonth.setProfitDate(settleMonth);
            PageInfo pageInfo = profitOrganTranMonthService.getProfitOrganTranMonthList(profitOrganTranMonth, null);
            if(pageInfo != null && pageInfo.getTotal() > 0) {
                List<ProfitOrganTranMonth> profitOrganTranMonths = pageInfo.getRows();
                profitOrganTranMonths.forEach(
                        organTranMonth->{
                           if ("01".equals(organTranMonth.getProductType())) {
                               organTranMonth = (ProfitOrganTranMonth) pageInfo.getRows().get(0);
                               organTranMonth.setTranAmt(tranAmt);
                               organTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
                               organTranMonth.setDifferenceAmt(profitOrganTranMonth.getSettleAmt().subtract(tranAmt));
                               profitOrganTranMonthService.update(organTranMonth);
                           }else if ("02".equals(organTranMonth.getProductType())) {
                               organTranMonth = (ProfitOrganTranMonth) pageInfo.getRows().get(0);
                               organTranMonth.setTranAmt(tranAmt);
                               organTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
                               organTranMonth.setDifferenceAmt(profitOrganTranMonth.getSettleAmt().subtract(tranAmt));
                               profitOrganTranMonthService.update(organTranMonth);
                           }else{

                           }
                        }
                );

            }
        }

    }

    /*** 
    * @Description: 获取POS交易数据
    * @Param:  settleMonth 分润月份
    * @return:  数据json对象
    * @Author: zhaodw 
    * @Date: 2018/8/2 
    */ 
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
