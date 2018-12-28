package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.dao.ProfitDirectMapper;
import com.ryx.credit.profit.pojo.ProfitDirect;
import com.ryx.credit.profit.service.IProfitDirectService;
import com.ryx.credit.profit.service.ProfitComputerService;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 直发分润数据同步、定时 cxinfo 直发分润数据同步 汪勇
 */
@Service("profitZhiFaDataJob")
public class ProfitZhiFaDataJob {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(ProfitZhiFaDataJob.class);

    @Autowired
    private IProfitDirectService profitDirectService;
    @Autowired
    private ProfitComputerService computerService;
    @Autowired
    private IdService idService;
    @Autowired
    private ProfitDeductionService profitDeductionService;
    @Autowired
    private AgentBusinfoService businfoService;
    @Autowired
    ProfitDirectMapper profitDirectMapper;

    private String month = "";
    private int index = 1;
    private static int c = 1;


    @Transactional
    public void excute(String transDate) {
        logger.info("==========={}月手刷直发分润数据同步开始===========", transDate);
        index = 1;
        long t1 = System.currentTimeMillis();
        month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date(), -1)).substring(0, 6);
        transDate = transDate == null ? month : transDate;

        profitDirectMapper.deleteByMonth(transDate);
        synchroProfitDirect(transDate);

        long t2 = System.currentTimeMillis();
        logger.info("==========={}月手刷直发分润数据同步结束,耗时{}ms===========", transDate, (t2 - t1));
    }

    /**
     * 同步直发分润数据
     * 交易月份（空则为上一月）
     * 每月5号上午10点：@Scheduled(cron = "0 0 5 10 * ?")
     */
//    @Scheduled(cron = "0 42 10 22 * ?")
    public void synchroProfitDirect(String transDate) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("frmonth", transDate);
        map.put("pageNumber", index++ + "");
        map.put("pageSize", "200");
        String params = JsonUtil.objectToJson(map);
        logger.debug("直发数据同步请求报文：{}", JSONObject.toJSONString(map));
        String res = HttpClientUtil.doPostJson(AppConfig.getProperty("profit.zhifa"), params);
        logger.debug("直发数据同步返回报文：{}", JSONObject.toJSONString(res));

        JSONObject json = JSONObject.parseObject(res);
        if (!JSONObject.parseObject(res).get("respCode").equals("000000")) {
            logger.error("请求同步失败！");
            AppConfig.sendEmails("直发分润数据同步失败！respCode="+json.get("respCode")+",respMsg="+json.get("respMsg"), "直发分润数据同步失败");
            throw new RuntimeException("直发分润数据同步失败！");
        }

        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data, List.class);
        if (list.size() > 0) {
            try {
                insertProfitDirect(list, transDate);
            } catch (Exception e) {
                logger.error("直发分润数据同步失败！", e);
                e.printStackTrace();
                throw new RuntimeException("直发分润数据同步失败");
            }
        } /*else {
            computer();
        }*/
    }

    public void insertProfitDirect(List<JSONObject> profitDirects, String transDate) {
        for (JSONObject json : profitDirects) {

            /*ProfitDeduction where = new ProfitDeduction();
            where.setAgentId(json.getString("AGENTID"));
            where.setDeductionType("01");
            where.setDeductionDate(DateUtil.sdf_Days.format(DateUtil.addMonth(new Date(), -1)).substring(0, 7));
            BigDecimal buckle = profitDeductionService.totalBuckleByMonth(where);//退单扣款
            */

            logger.info("{}月手刷直发分润数据同步，代理商：{}", transDate, json.getString("AGENTID"));

            AgentBusInfo fristAgent = businfoService.getByBusidAndCode("6000", json.getString("FRISTAGENTID"));
            ProfitDirect profitDirect = new ProfitDirect();
            profitDirect.setId(idService.genId(TabId.P_PROFIT_DIRECT));
            profitDirect.setAgentName(json.getString("AGENTNAME"));//代理商名称
            profitDirect.setAgentId(json.getString("AGENTID"));//代理商编号
            profitDirect.setParentAgentId(json.getString("PARENTAGENTID"));//上级代理商编号
            profitDirect.setParentAgentName(json.getString("PARENTAGENTNAME"));//上级代理商名称
            profitDirect.setFristAgentId(json.getString("FRISTAGENTNAME"));//一级代理商编号
            profitDirect.setFristAgentName(json.getString("FRISTAGENTID"));//一级代理商名称
            profitDirect.setFristAgentPid(fristAgent == null ? "" : fristAgent.getAgentId());//一级代理商唯一码
            profitDirect.setPaycompanyNum(fristAgent == null ? "" : fristAgent.getCloPayCompany());//一级代理商打款公司
            profitDirect.setTransAmt(json.getBigDecimal("TRANSAMT"));//直发交易金额
            profitDirect.setTransMonth(json.getString("TRANSMONTH"));//月份
            profitDirect.setTransFee(json.getBigDecimal("TRANSFEE"));//直发交易手续费
            profitDirect.setProfitAmt(json.getBigDecimal("PROFITAMT"));//实发分润
            //ProfitDirect.setActualProfit(BigDecimal.ZERO);//实发分润
            profitDirect.setActualProfit(json.getBigDecimal("PROFITAMT"));//实发分润
            profitDirect.setAgentEmail(json.getString("AGENTEMAIL"));//邮箱
            profitDirect.setAccountCode(json.getString("ACCOUNTCODE"));//账号
            profitDirect.setAccountName(json.getString("ACCOUNTNAME"));//户名
            profitDirect.setBankOpen(json.getString("BANKOPEN"));//开户行
            profitDirect.setBankCode(json.getString("BANKCODE"));//银行号
            profitDirect.setSupplyAmt(BigDecimal.ZERO);//补款
            profitDirect.setParentBuckle(BigDecimal.ZERO);//代下级扣款
            //ProfitDirect.setBuckleAmt(buckle == null ? BigDecimal.ZERO : buckle);//退单扣款
            profitDirect.setBuckleAmt(BigDecimal.ZERO);//退单扣款
            profitDirect.setDailyAmt(json.getBigDecimal("DAILYMONEY"));//日结分润总金额
            //退单补款、应发分润、应找上级扣款需计算赋值
            profitDirectService.insertSelective(profitDirect);
        }
        synchroProfitDirect(transDate);
    }

    public void computer() {
        try {
            Thread.sleep(1000);
            //计算直发补款
            computerService.computer_Supply_ZhiFa();
            //计算不足扣款找上级代扣，并记录
            computerService.computer_Buckle_ZhiFa();
            //计算直发实际分润
            computerService.computer_ZhiFa();
        } catch (Exception e) {
            logger.error("直发分润计算出错！");
            e.printStackTrace();
        }
    }


    public static void main(String agrs[]) {
        test();
    }

    public static void test() {
        String transDate = null;
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("transDate", transDate == null ? DateUtil.sdfDays.format(DateUtil.addMonth(new Date(), -2)).substring(0, 6) : transDate);
        map.put("pageNumber", c++ + "");
        map.put("pageSize", "50");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                ("http://12.3.10.161:8003/qtfr/agentInterface/agencyZfMoney.do", params);
        System.out.println(res);
        if (!JSONObject.parseObject(res).get("respCode").equals("000000")) {
            //logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败", "日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<HashMap> list = JSONObject.parseObject(data, List.class);
        if (list.size() > 0) {
            test();
        }
        System.out.println(data);
    }
}
