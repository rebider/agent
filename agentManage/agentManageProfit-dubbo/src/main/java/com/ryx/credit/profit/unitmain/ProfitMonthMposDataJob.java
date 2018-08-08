package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.service.IProfitDService;
import com.ryx.credit.profit.service.ProfitComputerService;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 手刷月分润数据同步、定时
 */
public class ProfitMonthMposDataJob {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private ProfitDetailMonthService profitDetailMonthService;
    @Autowired
    private ProfitComputerService computerService;
    @Autowired
    private IdService idService;


    public static void main(String agrs[]){
        String transDate = null;
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("transDate",transDate==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6):transDate);
        map.put("pageNumber","1");
        map.put("pageSize","20");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.month"),params);
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
     * 同步月分润数据
     * @param transDate 交易日期（空则为上一月）
     */
    public void synchroProfitMonth(String transDate){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("transDate",transDate==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6):transDate);
        map.put("pageNumber","1");
        map.put("pageSize","20");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.month"),params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            logger.error("请求同步失败！");
            AppConfig.sendEmails("月分润同步失败","月分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<HashMap> list = JSONObject.parseObject(data,List.class);
        try {
            insertProfitMonth(list);
        } catch (Exception e) {
            logger.error("同步月分润数据失败！");
            e.printStackTrace();
        }
    }

    public void insertProfitMonth(List<HashMap> profitMonths){
        for(HashMap map:profitMonths){
            ProfitDetailMonth where = new ProfitDetailMonth();
            where.setAgentPid((String)map.get("AGENTPID"));
            where.setProfitDate((String)map.get("PROFITDATE"));
            ProfitDetailMonth detailMonth = profitDetailMonthService.selectByPIdAndMonth(where);
            BigDecimal totalDay = computerService.total_day((String)map.get("AGENTPID"),(String)map.get("PROFITDATE"));//瑞和宝日结分润汇总
            BigDecimal factor = computerService.total_factor((String)map.get("AGENTPID"),(String)map.get("PROFITDATE"));//商业保理扣款
            BigDecimal otherSupply = computerService.total_supply((String)map.get("AGENTPID"),(String)map.get("PROFITDATE"));//其他补款汇总
            if(null!=detailMonth){//已存在该分润
                detailMonth.setRhbDgMustDeductionAmt((BigDecimal) map.get(""));
                detailMonth.setRhbDgRealDeductionAmt((BigDecimal) map.get(""));

                BigDecimal rhbProfit = (BigDecimal) map.get("");//瑞和宝分润
                detailMonth.setRhbProfitAmt(rhbProfit.subtract(totalDay));//瑞和宝分润得减去日结分润
                detailMonth.setBuDeductionAmt(factor);
                detailMonth.setOtherSupplyAmt(otherSupply);
                profitDetailMonthService.updateByPrimaryKeySelective(detailMonth);
            }else{
                detailMonth = new ProfitDetailMonth();
                detailMonth.setId(idService.genId(TabId.P_PROFIT_M));
                BigDecimal rhbProfit = (BigDecimal) map.get("");//瑞和宝分润
                detailMonth.setRhbProfitAmt(rhbProfit.subtract(totalDay));//瑞和宝分润得减去日结分润
                detailMonth.setBuDeductionAmt(factor);
                detailMonth.setOtherSupplyAmt(otherSupply);
                profitDetailMonthService.insertSelective(detailMonth);
            }
        }
    }
}
