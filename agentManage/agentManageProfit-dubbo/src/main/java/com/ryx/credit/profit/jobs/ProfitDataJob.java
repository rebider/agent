package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.profit.pojo.OrganTranMonthDetail;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitMonth;
import com.ryx.credit.profit.service.OrganTranMonthDetailService;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.profit.service.ProfitMonthService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.profit.IPosProfitDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * @author zhaodw
 * @Title: CheckTranJob
 * @ProjectName agentManage
 * @Description: 分润数据处理
 * @date 2018/7/2911:34
 */
@Service("profitDataJob")
@Transactional(rollbackFor=RuntimeException.class)
public class ProfitDataJob {

    private static final Logger LOG = Logger.getLogger(ProfitDataJob.class);

    @Autowired
    private IPosProfitDataService posProfitDataService;

    @Autowired
    private BusinessPlatformService businessPlatformService;

    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;

    @Autowired
    private ProfitMonthService profitMonthServiceImpl;

    @Autowired
    private OrganTranMonthDetailService organTranMonthDetailService;

    @Autowired
    private AgentColinfoService agentColinfoService;


    @Autowired
    private IdService idService;

//    @Scheduled(cron = "0 0 12 10 * ?")
    public void deal() {
        String settleMonth = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        LOG.info("分润月份"+settleMonth);
        LOG.info("获取分润数据");
        try {
            AgentResult agentResult = posProfitDataService.getPosProfitDate(settleMonth);
            if (agentResult != null && agentResult.getData() != null) {
                JSONObject json = JSONObject.parseObject(agentResult.getData().toString());
                if (json != null && json.size() > 0) {
                    if (json.containsKey("pftData")) {
                        JSONArray array = json.getJSONArray("pftData");
                        if (array != null && array.size() > 0 ) {
                            array.stream().limit(10).forEach(object->{
                                JSONObject profitData = (JSONObject) object;
                                Map<String, Object> agentMap = getAgentId(profitData.getString("ORG_ID"));
                                if (agentMap != null) {
                                    LOG.info("新增月分润数据");
                                    String profitId =  insertProfitMonth(agentMap,profitData, settleMonth);
                                    LOG.info("新增月分润明细数据");
                                    insertProfitMonthDetail(agentMap,profitData, settleMonth, profitId );
                                    LOG.info("新增月分润交易明细数据");
                                    insertOrganTranDetail(agentMap,profitData, settleMonth, profitId );
                                    profitId = null;
                                }
                            });
                        }
                    }
                }else{
                    LOG.error("月份："+settleMonth+"，二维码提供的没有获取到数据");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOG.error("分润数据处理失败");
            throw new RuntimeException("分润数据处理失败");
        }

    }

    /***
     * @Description: 插入月分润
     * @Param:agentMap 代理商信息
     * @Param:profitData 分润信息
     * @Param:settleMonth 分润月份
     * @Author: zhaodw
     * @Date: 2018/8/6
     */
    private String insertProfitMonth(Map<String, Object> agentMap, JSONObject profitData, String settleMonth) {
        // pos交易分润额
        BigDecimal posTranProFitAmt = profitData.getBigDecimal("PFT_01_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_01_AMT");
        // pos付款分润额
        BigDecimal posPayProFitAmt = profitData.getBigDecimal("PFT_02_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_02_AMT");

        ProfitMonth profitMonth = new ProfitMonth();

        profitMonth.setId(idService.genId(TabId.P_PROFIT_DETAIL_M));
        profitMonth.setAgentPid((String)agentMap.get("AG_UNIQ_NUM"));
        profitMonth.setAgentId(profitData.getString("ORG_ID"));
        profitMonth.setAgentName((String)agentMap.get("AG_NAME"));
        profitMonth.setStatus("4");
        profitMonth.setProfitDate(settleMonth);
        profitMonth.setTransProfitPos(posTranProFitAmt.add(posPayProFitAmt));
        profitMonth.setTransSupplyProfitPos(profitData.getBigDecimal("PFT_DIFF_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_DIFF_AMT"));
        profitMonth.setPayProfit(profitMonth.getTransProfitPos().add(profitMonth.getTransSupplyProfitPos()));
        profitMonthServiceImpl.insertProfitMonth(profitMonth);

        return  profitMonth.getId();
    }

    /***
     * @Description: 插入交易明细
     * @Param:agentMap 代理商信息
     * @Param:profitData 分润信息
     * @Param:settleMonth 分润月份
     * @Param:profitId 分润id
     * @Author: zhaodw
     * @Date: 2018/8/6
     */
    private void insertOrganTranDetail(Map<String, Object> agentMap, JSONObject profitData, String settleMonth, String profitId) {
        OrganTranMonthDetail organTranMonthDetail = new OrganTranMonthDetail();
        organTranMonthDetail.setAgentPid((String)agentMap.get("AG_UNIQ_NUM"));
        organTranMonthDetail.setAgentName((String)agentMap.get("AG_NAME"));
        organTranMonthDetail.setAgentId(profitData.getString("ORG_ID"));
        organTranMonthDetail.setPosJlTranAmt(profitData.getBigDecimal("POS_03_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("POS_03_AMT"));
        organTranMonthDetail.setzPosTranAmt(profitData.getBigDecimal("POS_02_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("POS_02_AMT"));
        organTranMonthDetail.setPosTranAmt(profitData.getBigDecimal("POS_01_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("POS_01_AMT"));
        organTranMonthDetail.setProfitId(profitId);
        organTranMonthDetail.setProfitDate(settleMonth);
        organTranMonthDetail.setAgentType((String)agentMap.get("BUS_TYPE"));
        organTranMonthDetailService.insert(organTranMonthDetail);
        organTranMonthDetail = null;
    }

    /***
    * @Description: 插入分润明细
     * @Param:agentMap 代理商信息
     * @Param:profitData 分润信息
     * @Param:settleMonth 分润月份
     * @Param:profitId 分润id
    * @Author: zhaodw
    * @Date: 2018/8/6
    */
    private void insertProfitMonthDetail( Map<String, Object> agentMap, JSONObject profitData, String settleMonth, String profitId) {
        ProfitDetailMonth profitDetailMonthTemp = new ProfitDetailMonth();
        profitDetailMonthTemp.setAgentPid((String)agentMap.get("AG_UNIQ_NUM"));
        profitDetailMonthTemp.setAgentId(profitData.getString("ORG_ID"));
        profitDetailMonthTemp.setProfitDate(settleMonth);
        profitDetailMonthTemp.setAgentName((String)agentMap.get("AG_NAME"));
        profitDetailMonthTemp.setTranAmt(profitData.getBigDecimal("TRAN_01_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("TRAN_01_AMT"));
        profitDetailMonthTemp.setPayAmt(profitData.getBigDecimal("TRAN_02_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("TRAN_02_AMT"));
        profitDetailMonthTemp.setTranProfitScale(profitData.getString("PFT_01_RATE"));
        profitDetailMonthTemp.setPayProfitScale(profitData.getString("PFT_02_RATE"));
        profitDetailMonthTemp.setTranProfitAmt(profitData.getBigDecimal("PFT_01_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_01_AMT"));
        profitDetailMonthTemp.setPayProfitAmt(profitData.getBigDecimal("PFT_02_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_02_AMT"));
        profitDetailMonthTemp.setProfitId(profitId);
        profitDetailMonthTemp.setBusPlatForm((String)agentMap.get("BUS_PLATFORM"));
        profitDetailMonthTemp.setPosZqSupplyProfitAmt(profitData.getBigDecimal("PFT_DIFF_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_DIFF_AMT"));
        // 获取账户信息
        List<AgentColinfo> agentColinfos= agentColinfoService.queryAgentColinfoService((String)agentMap.get("AG_UNIQ_NUM"), null,AgStatus.Approved.status);

        if (agentColinfos != null && agentColinfos.size() > 0) {
            AgentColinfo agentColinfo = agentColinfos.get(0);
            profitDetailMonthTemp.setAccountId(agentColinfo.getCloBankAccount());
            profitDetailMonthTemp.setAccountName(agentColinfo.getCloRealname());
            profitDetailMonthTemp.setOpenBankName(agentColinfo.getCloBankBranch());
            profitDetailMonthTemp.setBankCode(agentColinfo.getBranchLineNum());
            profitDetailMonthTemp.setTax(agentColinfo.getCloTaxPoint());
        }

        profitDetailMonthServiceImpl.insert(profitDetailMonthTemp);
        profitDetailMonthTemp = null;
    }

    /*** 
    * @Description: 获取代理商信息
    * @Param:  orgId 代理商id
    * @return:  代理商系统代理商唯一id
    * @Author: zhaodw 
    * @Date: 2018/8/3 
    */ 
    private Map<String, Object> getAgentId(String orgId) {
        // 获取代理商平台id
        AgentBusInfo agentBusInfo = new AgentBusInfo();
        agentBusInfo.setBusNum(orgId);
        PageInfo pageInfo = businessPlatformService.queryBusinessPlatformList(agentBusInfo, new Agent(),null);
        if (pageInfo != null && pageInfo.getTotal() > 0) {
            return (Map<String, Object>) pageInfo.getRows().get(0);
        }
        return  null;
    }
}
