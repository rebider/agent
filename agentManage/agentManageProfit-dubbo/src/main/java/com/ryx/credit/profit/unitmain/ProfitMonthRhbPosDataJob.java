package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.*;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.dao.ProfitSupplyDiffMapper;
import com.ryx.credit.profit.dao.TransProfitDetailMapper;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 瑞花宝月分润数据同步、定时 cxinfo
 */
@PropertySource("classpath:/config.properties")
@Service("profitMonthRhbPosDataJob")
@Transactional(rollbackFor = RuntimeException.class)
public class ProfitMonthRhbPosDataJob {
    private static final String rhbReqUrl = AppConfig.getProperty("rhb_req_url");
    private static final String rhb3desKey = AppConfig.getProperty("rhb_3des_Key");
    private static final String rhb3desIv = AppConfig.getProperty("rhb_3des_iv");
    private static final String environment = AppConfig.getProperty("jobEnvironment");
    org.slf4j.Logger logger = LoggerFactory.getLogger(ProfitMonthRhbPosDataJob.class);

    @Autowired
    private TransProfitDetailMapper transProfitDetailMapper;
    @Autowired
    private AgentBusinfoService businfoService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private IdService idService;
    @Autowired
    OrderService orderService;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * 同步瑞花宝月分润明细数据(TransProfitDetail)
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
        logger.info("=============手刷{}瑞花宝月分润明细数据同步执行开始===========", transDate);
        transDate = transDate == null ? DateUtil.sdfDays.format(DateUtil.addMonth(new Date(), -1)).substring(0, 6) : transDate;
        long t1 = System.currentTimeMillis();

        //清除旧数据
        transProfitDetailMapper.deleteBySourceIdAndMonth("RHPOS", transDate);
        //开始同步
        synchroProfitMonth(transDate,"00800653");
        synchroProfitMonth(transDate,"00806666");

        long t2 = System.currentTimeMillis();
        logger.info("=============手刷{}瑞花宝月分润明细数据同步执行结束,耗时{}ms===========", transDate, (t2 - t1));
    }


    public void synchroProfitMonth(String transDate,String branchId) {


        try {
            Map<String, String> map = new HashMap<>();
            map.put("application","TradeFrInfoQuery");
            map.put("branchId",branchId);
            map.put("month",transDate);
            String json = JsonUtil.objectToJson(map);
            String reqParamEncrypt = Des3Util.Encrypt(json, rhb3desKey, rhb3desIv.getBytes());
            String httpResult = HttpClientUtil.sendHttpPost(rhbReqUrl, reqParamEncrypt);
            String reqParamDecrypt = Des3Util.Decrypt(httpResult, rhb3desKey, rhb3desIv.getBytes());
            JSONObject respXMLObj = JSONObject.parseObject(reqParamDecrypt);
            logger.info("瑞花宝月分润明细数据同步返回报文==="+respXMLObj);
            if (respXMLObj.getString("MSG_CODE").equals("0000")){
                JSONObject data = respXMLObj.getJSONObject("data");
                JSONArray resultList = data.getJSONArray("resultList");
                if (resultList.size() > 0) {
                    //插入新拉取数据
                    insertTransProfit(resultList, transDate);
                    return ;
                }
            }else{
                String msg_text = respXMLObj.getString("MSG_TEXT");
                throw new RuntimeException("瑞花宝月分润明细数据同步失败:"+msg_text);
            }
        } catch (Exception e) {
            logger.error("瑞花宝月分润明细数据同步失败！");
            e.printStackTrace();
            throw new RuntimeException("瑞花宝月分润明细数据同步失败");
        }
    }

    public void insertTransProfit(JSONArray transData, String transDate) throws Exception {
        for (int i = 0; i < transData.size(); i++) {
            JSONObject tranData = transData.getJSONObject(i);
            TransProfitDetail detail = new TransProfitDetail();
            String agencyId = tranData.getString("agencyId");//瑞花宝平台的品牌编码——机构编号
            if (agencyId == null || agencyId.equals(""))
                continue;
            AgentBusInfo agentBusInfo = businfoService.selectBusInfo(agencyId);
            if (agentBusInfo == null) {
                logger.info("代理商详情查询失败-----agencyId：" + agencyId);
                continue;
            }
            Agent agent = agentService.getAgentById(agentBusInfo.getAgentId());

            detail.setAgentId(agentBusInfo.getAgentId());
            detail.setAgentName(agent.getAgName());
            detail.setBusNum(agencyId);
            detail.setBusCode(agentBusInfo.getBusPlatform());//平台号
            detail.setId(idService.genId(TabId.P_PROFIT_DETAIL_M));//主键
            detail.setProfitDate(transDate);//月份
            AgentBusInfo parentAgentBusInfo = null;
            if (agentBusInfo.getBusParent() != null) {
                parentAgentBusInfo = businfoService.getById(agentBusInfo.getBusParent());
            }
            if (parentAgentBusInfo != null) {  //上级代理商信息
                Agent parentAgent = agentService.getAgentById(parentAgentBusInfo.getAgentId());
                if (parentAgent != null) {
                    detail.setParentAgentId(parentAgent.getId());
                    detail.setParentAgentName(parentAgent.getAgName());
                    detail.setParentBusNum(parentAgentBusInfo.getBusNum());
                }
            }

            detail.setInTransAmt(tranData.getBigDecimal("tranAmount") == null ? BigDecimal.ZERO : tranData.getBigDecimal("tranAmount"));//付款交易金额
            detail.setTransFee(tranData.getBigDecimal("fee") == null ? BigDecimal.ZERO : tranData.getBigDecimal("fee"));//交易手续费
            detail.setServerAmt(tranData.getBigDecimal("serverFee") == null ? BigDecimal.ZERO : tranData.getBigDecimal("serverFee"));//服务费
            detail.setPayCompany(null == agentBusInfo ? "3" : agentBusInfo.getCloPayCompany());//打款公司
            detail.setAgentType(null == agentBusInfo ? "3" : agentBusInfo.getBusType());
            detail.setProfitAmt(tranData.getBigDecimal("frAmount"));//分润金额

            detail.setSourceInfo("RHPOS");
            logger.info("瑞花宝月分润明细数据同步成功agentId:"+agentBusInfo.getAgentId());
            transProfitDetailMapper.insertSelective(detail);
        }

    }

}
