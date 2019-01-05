package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.dao.ProfitDayMapper;
import com.ryx.credit.profit.dao.ProfitSupplyDiffMapper;
import com.ryx.credit.profit.dao.TransProfitDetailMapper;
import com.ryx.credit.profit.pojo.ProfitSupplyDiff;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OrderService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 手刷月分润数据同步、定时 cxinfo 同步手刷月分润明细数据 汪勇
 */
@Service("newProfitMonthMposDataJob")
@Transactional(rollbackFor = RuntimeException.class)
public class NewProfitMonthMposDataJob {

    org.slf4j.Logger logger = LoggerFactory.getLogger(NewProfitMonthMposDataJob.class);

    @Autowired
    private TransProfitDetailMapper transProfitDetailMapper;
    @Autowired
    private ProfitSupplyDiffMapper diffMapper;
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

    private String transDate = "";
    private int index = 1;
    private static BigDecimal allAmount = BigDecimal.ZERO;

    public void excute(String transDate) {
        logger.info("=============手刷{}月分润明细数据同步执行开始===========", transDate);
        transDate = transDate == null ? DateUtil.sdfDays.format(DateUtil.addMonth(new Date(), -1)).substring(0, 6) : transDate;
        index = 1;
        long t1 = System.currentTimeMillis();

        //清除旧数据
        transProfitDetailMapper.deleteBySourceIdAndMonth("MPOS", transDate);
        //开始同步
        synchroProfitMonth(transDate);

        long t2 = System.currentTimeMillis();
        logger.info("=============手刷{}月分润明细数据同步执行结束,耗时{}ms===========", transDate, (t2 - t1));
    }

    /**
     * 同步手刷月分润明细数据(TransProfitDetail)
     * transDate 交易日期（空则为上一月）
     * 每月5号上午12点：@Scheduled(cron = "0 0 5 12 * ?")
     */
//    @Scheduled(cron = "0 55 10 22 * ?")

    public void synchroProfitMonth(String transDate) {

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("frMonth", transDate);
        map.put("pageNumber", index++ + "");
        map.put("pageSize", "1000");
        String params = JsonUtil.objectToJson(map);
        logger.debug("========手刷{}月分润明细请求报文：{}", transDate, JSONObject.toJSONString(map));
        String res = HttpClientUtil.doPostJson(AppConfig.getProperty("profit.newmonth"), params);
        logger.debug("========手刷{}月分润明细返回报文：{}", transDate, res);

        JSONObject json = JSONObject.parseObject(res);
        if (!json.get("respCode").equals("000000")) {
            logger.error("请求同步失败！");
            AppConfig.sendEmails("月分润同步失败! respCode="+json.get("respCode")+",respMsg="+json.get("respMsg"), "月分润同步失败");
            throw new RuntimeException("月分润同步失败！");
        }


        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data, List.class);
        try {
            if (list.size() > 0) {
                //插入新拉取数据
                insertTransProfit(list, transDate);
            }
        } catch (Exception e) {
            logger.error("同步月分润数据失败！");
            e.printStackTrace();
            throw new RuntimeException("同步月分润数据失败");
        }
    }

    public void insertTransProfit(List<JSONObject> transProfit, String transDate) throws Exception {

        for (JSONObject json : transProfit) {
            logger.info("手刷{}月分润明细数据同步{}", transDate, json.getString("AGENCYID"));
            String parentAgentId = null;
            TransProfitDetail detail = new TransProfitDetail();
            AgentBusInfo Busime = businfoService.getByBusidAndCode(json.getString("PLATFORMNUM"), json.getString("AGENCYID"));
            /*if(null==Busime){
                logger.info(json.getString("PLATFORMNUM")+"------"+json.getString("AGENCYID"));
                continue;
            }*/

            AgentBusInfo parent = businfoService.getByBusidAndCode(json.getString("PLATFORMNUM"), json.getString("ONLINEAGENCYID"));
            if (null == parent || "6000".equals(json.getString("PLATFORMNUM"))) {
                //直发一代或者上级为空，则无上级
                parentAgentId = null;
            } else {
                parentAgentId = parent.getAgentId();
            }

            /*ProfitDay day = new ProfitDay();
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
            }*/
//            else{
//                if(Busime.getAgentId().equals("JS00001159") || Busime.getAgentId().equals("JS00001160")) {//捷步、银点只算日结
//                    totalDay = dayMapper.totalRPByAgentId(day);
//                    logger.info("所属捷步、银点");
//                    logger.info("日结分润：" + totalDay);
//                }
//            }

            String platFormNum = json.getString("PLATFORMNUM") == null ? "" : json.getString("PLATFORMNUM");
            /*if (!"0001".equals(platFormNum) && !"2000".equals(platFormNum) && !"5000".equals(platFormNum)
                    && !"1111".equals(platFormNum) && !"3000".equals(platFormNum) && !"6000".equals(platFormNum)
                    && !"4000".equals(platFormNum)) {
                platFormNum = "1001";
            }*/

            detail.setId(idService.genId(TabId.P_PROFIT_DETAIL_M));//主键
            detail.setProfitDate(transDate);//月份
            detail.setBusNum(json.getString("AGENCYID"));//业务平台机构号
            detail.setParentBusNum(json.getString("ONLINEAGENCYID"));//业务平台上级机构号
            detail.setBusCode(platFormNum);//平台号
            detail.setParentAgentId(parentAgentId);//上级AG码
            detail.setParentAgentName(json.getString("ONLINEAGENCYNAME"));
            detail.setAgentId(null == Busime ? "AG01" : Busime.getAgentId());//AG码
            detail.setAgentName(json.getString("COMPANYNAME"));//代理商名称
            detail.setInTransAmt(json.getBigDecimal("SAMOUNT") == null ? BigDecimal.ZERO : json.getBigDecimal("SAMOUNT"));//付款交易额
            detail.setTransFee(json.getBigDecimal("FEEAMT"));//交易手续费
            detail.setUnicode(json.getString("UNIQUECODE"));//财务自编码

            detail.setPayCompany(null == Busime ? "3" : Busime.getCloPayCompany());//打款公司
            detail.setAgentType(null == Busime ? "3" : Busime.getBusType());

            //detail.setNotaxAmt(totalDay==null?BigDecimal.ZERO:totalDay);//未计税日结金额
            BigDecimal dailyMoney = json.getBigDecimal("DAILYMONEY") == null ? BigDecimal.ZERO : json.getBigDecimal("DAILYMONEY");//日结金额
            detail.setNotaxAmt(dailyMoney);

            BigDecimal profitTotal = json.getBigDecimal("PROFIT") == null ? BigDecimal.ZERO : json.getBigDecimal("PROFIT");//分润汇总金额
            BigDecimal monthMoney = profitTotal.subtract(dailyMoney);    //月结金额 = 分润汇总金额 - 日结金额
            detail.setProfitAmt(monthMoney);

            detail.setSourceInfo("MPOS");

            //手刷只有5000平台有补差
            if("5000".equals(json.getString("PLATFORMNUM"))){
                ProfitSupplyDiff where = new ProfitSupplyDiff();
                where.setDiffDate(transDate);
                where.setParentAgentid(json.getString("AGENCYID"));
                BigDecimal supplyAmt = diffMapper.selectAmtByWhere(where);
                detail.setSupplyAmt(supplyAmt == null ? BigDecimal.ZERO : supplyAmt);//补差金额
            }


            //计算
            /*AgentResult agentResult = orderService.queryPaymentXXDK(json.getString("AGENCYID"));//查询线下打款数据信息
            List<HashMap> maps = new ArrayList<HashMap>();
            if(!"".equals(agentResult.getData().toString())){
                maps = (List<HashMap>) agentResult.getData();
            }
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
                oPayment.setProfitTaxAmt(actualAmt);//已打款
                paymentList.add(oPayment);
            }*/
            transProfitDetailMapper.insertSelective(detail);
            //更新打款信息
            /*if (paymentList.size() > 0) {
                orderService.updateProfitTaxAmt(paymentList);//批量更新分润税点抵扣金额
            }*/
        }
        synchroProfitMonth(transDate);
    }

}
