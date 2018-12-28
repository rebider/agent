package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.profit.dao.ProfitDayMapper;
import com.ryx.credit.profit.dao.TransProfitDetailMapper;
import com.ryx.credit.profit.pojo.ProfitDay;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OrderService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class ProfitMonthTest {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private TransProfitDetailMapper transProfitDetailMapper;
    @Autowired
    private ProfitDayMapper dayMapper;
    @Autowired
    private AgentBusinfoService businfoService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private IdService idService;
    @Autowired
    OrderService orderService;

    private String month = "";
    private int index = 1;

    @Test
    public void test() throws Exception {
//        String transDate = "201807";
//        synchroProfitMonth(transDate);
        synchroProfitMonth();
    }

    /**
     * 每一个小时执行一次：@Scheduled(cron = "0 0 * * * ?")
     * 每月10号凌晨20点执行一次：@Scheduled(cron = "0 0 10 20 * ?")
     * @throws Exception
     */
    @Test
    //@Scheduled(cron = "0 0 * * * ?")
    public void synchroProfitMonth(){
        String transDate = "201807";
//        String transDate = null;
        transDate = transDate==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date(),-1)).substring(0,6):transDate;
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("frMonth", transDate);
        map.put("pageNumber", "1");
        map.put("pageSize", "2");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.newmonth"), params);
        System.out.println("输出====================================" + res);
        JSONObject json = JSONObject.parseObject(res);
        if(!json.get("respCode").equals("000000")){
            logger.error("请求同步失败！");
            AppConfig.sendEmails("月分润同步失败","月分润同步失败");
            return;
        }

        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data, List.class);
        try {
            if(list.size() > 0){
                insertTransProfit(list, transDate);
            }
        } catch (Exception e) {
            logger.error("同步月分润数据失败！");
            e.printStackTrace();
            throw new RuntimeException("分润数据处理失败");
        }
    }

    public void insertTransProfit(List<JSONObject> transProfit, String transDate) throws Exception {
        for(JSONObject json : transProfit){
            String parentAgentId = null;
            TransProfitDetail detail = new TransProfitDetail();
            AgentBusInfo Busime = businfoService.getByBusidAndCode(json.getString("PLATFORMNUM"), json.getString("AGENCYID"));
            AgentBusInfo parent = businfoService.getByBusidAndCode(json.getString("PLATFORMNUM"), json.getString("ONLINEAGENCYID"));
            if(null == parent || "6000".equals(json.getString("PLATFORMNUM"))){
                //直发一代或者上级为空，则无上级
                parentAgentId = null;
            }else{
                parentAgentId = parent.getAgentId();
            }
            ProfitDay day = new ProfitDay();
            day.setAgentId(json.getString("AGENCYID"));
            day.setTransDate(transDate);
            BigDecimal totalDay = dayMapper.totalProfitAndReturnById(day);

            List<AgentBusInfo> parents = businfoService.queryParenFourLevelBusNum(null, json.getString("PLATFORMNUM"), json.getString("AGENCYID"));//父类
            if(parents.size() > 0){
                AgentBusInfo first = parents.get(parents.size() - 1);
                Agent agent = agentService.getAgentById(first.getAgentId());
                if(agent.getAgUniqNum().equals("JS00001159") || agent.getAgUniqNum().equals("JS00001160")) {//捷步、银点只算日结
                    totalDay = dayMapper.totalRPByAgentId(day);
                    logger.info("所属捷步、银点");
                    logger.info("日结分润：" + totalDay);
                }
            }else{
                if(Busime.getAgentId().equals("JS00001159") || Busime.getAgentId().equals("JS00001160")) {//捷步、银点只算日结
                    totalDay = dayMapper.totalRPByAgentId(day);
                    logger.info("所属捷步、银点");
                    logger.info("日结分润：" + totalDay);
                }
            }

            String platFormNum = json.getString("PLATFORMNUM")==null?"":json.getString("PLATFORMNUM");
            if(!"0001".equals(platFormNum) && !"2000".equals(platFormNum) && !"5000".equals(platFormNum)
                    && !"1111".equals(platFormNum) && !"3000".equals(platFormNum) && !"6000".equals(platFormNum)
                    && !"4000".equals(platFormNum)){
                platFormNum = "1001";
            }

            detail.setId(idService.genId(TabId.P_PROFIT_DETAIL_M));//主键
            detail.setProfitDate(transDate);//月份
            detail.setBusNum(json.getString("AGENCYID"));//机构号
            detail.setParentBusNum(json.getString("ONLINEAGENCYID"));//上级机构号
            detail.setBusCode(platFormNum);//平台号
            detail.setParentAgentId(parentAgentId);//上级AG码
            detail.setAgentId(Busime.getAgentId());//AG码
            detail.setAgentName(json.getString("COMPANYNAME"));//代理商名称
            detail.setInTransAmt(json.getBigDecimal("SAMOUNT")==null?BigDecimal.ZERO:json.getBigDecimal("SAMOUNT"));//付款交易额
            detail.setTransFee(json.getBigDecimal("FEEAMT"));//交易手续费
            detail.setUnicode(json.getString("UNIQUECODE"));//财务自编码
            detail.setProfitAmt(json.getBigDecimal("PROFIT")==null?BigDecimal.ZERO:json.getBigDecimal("PROFIT"));//分润金额
            detail.setPayCompany(Busime.getCloPayCompany());//打款公司
            detail.setNotaxAmt(totalDay==null?BigDecimal.ZERO:totalDay);//未计税日结金额
            detail.setSourceInfo("MPOS");

            //计算
            AgentResult agentResult = orderService.queryPaymentXXDK(json.getString("AGENCYID"));//查询线下打款数据信息
            List<HashMap> maps = (List<HashMap>) agentResult.getData();
            List<OPayment> paymentList = new ArrayList<>();
            BigDecimal notaxAmt = detail.getNotaxAmt();//本月未计税打款金额
            BigDecimal actualAmt = BigDecimal.ZERO;
            for (HashMap map : maps) {
                BigDecimal amts = (BigDecimal) map.get("ACTUAL_RECEIPT");//剩余可抵用打款金额
                actualAmt = actualAmt.add(amts);

                //判断如果打款金额大于日结为计税直接等于更新
                if (notaxAmt.subtract(actualAmt).compareTo(BigDecimal.ZERO) < 0) {
                    actualAmt = notaxAmt;
                }
                //更新月分润明细
                detail.setUnlineAmt(actualAmt);//线下打款金额
                OPayment oPayment = new OPayment();
                oPayment.setId((String) map.get("ID"));
                oPayment.setProfitTaxAmt(actualAmt);//已用金额
                paymentList.add(oPayment);
            }
            transProfitDetailMapper.insertSelective(detail);
            //更新打款信息
            if (paymentList.size() > 0) {
                orderService.updateProfitTaxAmt(paymentList);//批量更新分润税点抵扣金额
            }
        }
//        synchroProfitMonth(transDate);
    }

}
