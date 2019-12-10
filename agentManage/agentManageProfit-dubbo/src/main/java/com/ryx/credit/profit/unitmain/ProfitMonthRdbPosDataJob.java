package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.pojo.admin.agent.Agent;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * 瑞大宝月分润数据同步、定时 cxinfo
 */
@PropertySource("classpath:/config.properties")
@Service("profitMonthRdbPosDataJob")
@Transactional(rollbackFor = RuntimeException.class)
public class ProfitMonthRdbPosDataJob {
    private static final String environment = AppConfig.getProperty("jobEnvironment");
    org.slf4j.Logger logger = LoggerFactory.getLogger(ProfitMonthRdbPosDataJob.class);

    @Autowired
    private TransProfitDetailMapper transProfitDetailMapper;
    @Autowired
    private ProfitSupplyDiffMapper diffMapper;
    @Autowired
    private AgentBusinfoService businfoService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private IdService idService;
    @Autowired
    OrderService orderService;

    private List<String> notSuccessAgent;
    private Map<String,Integer> repeatAgent;
    //未成功的代理商

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * 同步瑞大宝月分润明细数据(TransProfitDetail)
     * transDate 交易日期（空则为上一月）
     * 每月3号上午11点
     */
    @Scheduled(cron = "${shoushua_yuejie_job_cron}")
    public void doCron() {
        if (!"preproduction".equals(environment)) {
            String transDate = DateUtil.sdfDays.format(DateUtil.addMonth(new Date(), -1)).substring(0, 6);
            excute(transDate);
        }
    }

    @Transactional
    public void excute(String transDate) {
        logger.info("=============手刷{}瑞大宝月分润明细数据同步执行开始===========", transDate);
        transDate = transDate == null ? DateUtil.sdfDays.format(DateUtil.addMonth(new Date(), -1)).substring(0, 6) : transDate;
        long t1 = System.currentTimeMillis();

        //清除旧数据
        transProfitDetailMapper.deleteBySourceIdAndMonth("RDBPOS", transDate);
        //开始同步
        synchroProfitMonth(transDate);

        long t2 = System.currentTimeMillis();
        logger.info("=============手刷{}瑞大宝月分润明细数据同步执行结束,耗时{}ms===========", transDate, (t2 - t1));
    }


    public void synchroProfitMonth(String transDate) {

        notSuccessAgent=new ArrayList<String>();
        repeatAgent=new HashMap<String,Integer>();

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("month",transDate);
        String json = JsonUtil.objectToJson(map);
        logger.info("瑞大宝月分润明细数据接口请求参数：{}",json);

        StringBuffer urlBuffer=new StringBuffer();
        urlBuffer.append(AppConfig.getProperty("profit.rdbmonth"));
        urlBuffer.append("?");
        Set<String> keys = map.keySet();
        for (String key:keys){
            String value = map.get(key);
            urlBuffer.append(key+"="+value+"&");
        }
        urlBuffer.deleteCharAt(urlBuffer.length()-1);
        String res = HttpClientUtil.doGet(urlBuffer.toString());
        logger.info("瑞大宝月分润明细数据接口返回报文：{}",res);
        JSONObject resultObj = JSONObject.parseObject(res);

        if (!resultObj.getString("code").equals("0000")) {
            logger.error("请求同步失败！");
            throw new RuntimeException("瑞大宝月分润明细数据同步失败！");
        }

        JSONArray data = resultObj.getJSONArray("info");

        try {
            if (data.size() > 0) {
                //插入新拉取数据
                insertTransProfit(data, transDate);
                logger.info("未同步成功代理商"+notSuccessAgent.toString());
                logger.info("代理商:"+repeatAgent.toString());
                return ;
            }
        } catch (Exception e) {
            logger.error("同步月分润数据失败！");
            e.printStackTrace();
            throw new RuntimeException("同步月分润数据失败");
        }
    }

    public void insertTransProfit(JSONArray transData, String transDate) throws Exception {

        for (int i = 0; i < transData.size(); i++) {
            JSONObject jsonObject = transData.getJSONObject(i);
            String agencyName = jsonObject.getString("agencyName");
            JSONArray agencyData = jsonObject.getJSONArray("agencyData");//同名代理商集


            for (int j = 0; j < agencyData.size(); j++) {//代理商数据
                JSONObject tranData = agencyData.getJSONObject(j);
                String agencyId = tranData.getString("agencyId");

                if (repeatAgent.containsKey(agencyId)){
                    repeatAgent.put(agencyId,repeatAgent.get(agencyId)+1);
                }else{
                    repeatAgent.put(agencyId,1);
                }

                TransProfitDetail detail = new TransProfitDetail();
                if (agencyId==null ||agencyId.equals(""))
                    continue;

                AgentBusInfo agentBusInfo;
                try {
                    agentBusInfo = businfoService.queryAgentBusInfo(agencyId);
                }catch (Exception e){
                    notSuccessAgent.add(agencyId);
                    logger.info("代理商详情查询失败-----agencyId："+agencyId);
                    e.printStackTrace();
                    continue;
                }

                detail.setAgentId(agentBusInfo.getAgentId());
                detail.setAgentName(agencyName);
                detail.setBusNum(agencyId);
                detail.setBusCode(agentBusInfo.getBusPlatform());//平台号
                detail.setId(idService.genId(TabId.P_PROFIT_DETAIL_M));//主键
                detail.setProfitDate(transDate);//月份
                AgentBusInfo parentAgentBusInfo=null;
                if (agentBusInfo.getBusParent()!=null) {
                    parentAgentBusInfo = businfoService.getById(agentBusInfo.getBusParent());
                }
                if (parentAgentBusInfo!=null){  //上级代理商信息
                    Agent parentAgent = agentService.getAgentById(parentAgentBusInfo.getAgentId());
                    if (parentAgent!=null){
                        detail.setParentAgentId(parentAgent.getId());
                        detail.setParentAgentName(parentAgent.getAgName());
                        detail.setParentBusNum(parentAgentBusInfo.getBusNum());
                    }
                }

                detail.setInTransAmt(tranData.getBigDecimal("tranAmount")==null?BigDecimal.ZERO:tranData.getBigDecimal("tranAmount"));//付款交易金额
                detail.setTransFee(tranData.getBigDecimal("tranFeeAmount")==null?BigDecimal.ZERO:tranData.getBigDecimal("tranFeeAmount"));//交易手续费
                detail.setServerAmt(tranData.getBigDecimal("upFee")==null?BigDecimal.ZERO:tranData.getBigDecimal("upFee"));//服务费
                detail.setPayCompany(null == agentBusInfo ? "3" : agentBusInfo.getCloPayCompany());//打款公司
                detail.setAgentType(null == agentBusInfo ? "3" : agentBusInfo.getBusType());
                detail.setProfitAmt(tranData.getBigDecimal("frAmount"));//分润金额

                detail.setSourceInfo("RDBPOS");
                detail.setSupplyAmt(tranData.getBigDecimal("disc"));//补款
                transProfitDetailMapper.insertSelective(detail);
            }
        }

    }

}
