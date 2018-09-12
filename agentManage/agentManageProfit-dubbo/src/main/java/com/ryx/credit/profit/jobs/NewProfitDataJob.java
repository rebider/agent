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
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.profit.service.TransProfitDetailService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.profit.IPosProfitDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhaodw
 * @Title: NewProfitDataJob
 * @ProjectName agentManage
 * @Description: 新版本分润数据处理
 * @date 2018/7/2911:34
 */
@Service("newProfitDataJob")
@Transactional(rollbackFor=RuntimeException.class)
public class NewProfitDataJob {

    private static final Logger LOG = Logger.getLogger(NewProfitDataJob.class);

    @Autowired
    private IPosProfitDataService posProfitDataService;

    @Autowired
    private BusinessPlatformService businessPlatformService;

    @Autowired
    private TransProfitDetailService transProfitDetailService;


    @Autowired
    private AgentBusinfoService agentBusinfoService;

    @Autowired
    private ProfitDetailMonthMapper profitDetailMonthMapper;

    @Autowired
    private IdService idService;

    @Autowired
    private AgentColinfoService agentColinfoService;

    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;


    @Scheduled(cron = "0 0 11 10 * ?")
    public void deal() {
        String profitDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        LOG.info("分润月份"+profitDate);
        LOG.info("获取分润数据");
        try {
            AgentResult agentResult = posProfitDataService.getPosProfitDate(profitDate);
            if (agentResult != null && agentResult.getData() != null) {
                JSONObject json = JSONObject.parseObject(agentResult.getData().toString());
                if (json != null && json.size() > 0) {
                    if (json.containsKey("pftData")) {
                        JSONArray array = json.getJSONArray("pftData");
                        if (array != null && array.size() > 0 ) {
                            array.stream().forEach(object->{
                                JSONObject profitData = (JSONObject) object;
                                Map<String, Object> agentMap = getAgentId(profitData.getString("ORG_ID"));
                                if (agentMap != null) {
                                    insertTransProfitDetail(agentMap, profitData, profitDate);
                                }
                            });
                        }
                    }
                    doSum(profitDate);
                }else{
                    LOG.error("月份："+profitDate+"，二维码提供的没有获取到数据");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            LOG.error("分润数据处理失败");
            throw new RuntimeException("分润数据处理失败");
        }

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
    private void insertTransProfitDetail( Map<String, Object> agentMap, JSONObject profitData, String settleMonth) {
        TransProfitDetail transProfitDetail = new TransProfitDetail();
        transProfitDetail.setAgentId((String)agentMap.get("AGENT_ID"));
        transProfitDetail.setBusNum(profitData.getString("ORG_ID"));
        transProfitDetail.setAgentName((String)agentMap.get("AG_NAME"));
        transProfitDetail.setParentAgentId((String)agentMap.get("parentAgentPid"));
        transProfitDetail.setParentBusNum((String)agentMap.get("parentAgentId"));
        transProfitDetail.setProfitDate(settleMonth);
        transProfitDetail.setInTransAmt(profitData.getBigDecimal("TRAN_01_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("TRAN_01_AMT"));
        transProfitDetail.setOutTransAmt(profitData.getBigDecimal("TRAN_02_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("TRAN_02_AMT"));
        transProfitDetail.setInProfitScale(profitData.getBigDecimal("PFT_01_RATE"));
        transProfitDetail.setOutProfitScale(profitData.getBigDecimal("PFT_02_RATE"));
        transProfitDetail.setInProfitAmt(profitData.getBigDecimal("PFT_01_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_01_AMT"));
        transProfitDetail.setOutProfitAmt(profitData.getBigDecimal("PFT_02_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_02_AMT"));
        transProfitDetail.setSourceInfo("POS");
        transProfitDetail.setBusCode((String)agentMap.get("BUS_PLATFORM"));
        transProfitDetail.setSupplyAmt(profitData.getBigDecimal("PFT_DIFF_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_DIFF_AMT"));
        transProfitDetail.setPosRewardAmt(profitData.getBigDecimal("POS_03_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("POS_03_AMT"));
        transProfitDetail.setIposCreditAmt(profitData.getBigDecimal("POS_02_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("POS_02_AMT"));
        transProfitDetail.setPosCreditAmt(profitData.getBigDecimal("POS_01_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("POS_01_AMT"));
        transProfitDetail.setId(idService.genId(TabId.TPD));
        transProfitDetail.setAgentType((String)agentMap.get("BUS_TYPE"));
        transProfitDetail.setPayCompany((String)agentMap.get("CLO_PAY_COMPANY"));
        transProfitDetailService.insert(transProfitDetail);
        transProfitDetail = null;
    }

    /***
    * @Description: 执行汇总
    * @Param:  profitDate 分润日期
    * @Author: zhaodw
    * @Date: 2018/8/27
    */
    private void doSum(String profitDate) {
        // 汇总到月分润明细中
        List<TransProfitDetail> transProfitDetails = transProfitDetailService.getPosTransProfitDetailSumList(profitDate);
        if (transProfitDetails != null && transProfitDetails.size() > 0) {
            transProfitDetails.forEach(transProfitDetail -> {
                ProfitDetailMonth profitDetailMonthTemp = new ProfitDetailMonth();
                profitDetailMonthTemp.setParentAgentId(transProfitDetail.getParentAgentId());
                profitDetailMonthTemp.setAgentId(transProfitDetail.getAgentId());
                transProfitDetail.setProfitDate(profitDate);
                profitDetailMonthTemp= profitDetailMonthMapper.selectByIdAndParent(profitDetailMonthTemp);
                if (profitDetailMonthTemp==null) {
                    profitDetailMonthTemp = new ProfitDetailMonth();
                }
                insertProfitMonthDetail(profitDetailMonthTemp, transProfitDetail);
            });
        }
    }

    /***
     * @Description: 插入分润汇总
     * @Author: zhaodw
     * @Date: 2018/8/6
     */
    private void insertProfitMonthDetail( ProfitDetailMonth profitDetailMonthTemp, TransProfitDetail transProfitDetail) {
        profitDetailMonthTemp.setAgentId(transProfitDetail.getAgentId());
        profitDetailMonthTemp.setProfitDate(transProfitDetail.getProfitDate());
        profitDetailMonthTemp.setAgentName(transProfitDetail.getAgentName());
        profitDetailMonthTemp.setTranAmt(transProfitDetail.getInTransAmt());
        profitDetailMonthTemp.setPayAmt(transProfitDetail.getOutTransAmt());
        profitDetailMonthTemp.setTranProfitScale(transProfitDetail.getInProfitScale().toString());
        profitDetailMonthTemp.setPayProfitScale(transProfitDetail.getOutProfitScale().toString());
        profitDetailMonthTemp.setTranProfitAmt(transProfitDetail.getInProfitAmt());
        profitDetailMonthTemp.setPayProfitAmt(transProfitDetail.getOutProfitAmt());
        profitDetailMonthTemp.setPosZqSupplyProfitAmt(transProfitDetail.getSupplyAmt());
        profitDetailMonthTemp.setParentAgentId(transProfitDetail.getParentAgentId());
        profitDetailMonthTemp.setStatus("4");
        profitDetailMonthTemp.setPayCompany(transProfitDetail.getPayCompany());
        // 获取账户信息
        List<AgentColinfo> agentColinfos= agentColinfoService.queryAgentColinfoService(transProfitDetail.getAgentId(), null,AgStatus.Approved.status);

        if (agentColinfos != null && agentColinfos.size() > 0) {
            AgentColinfo agentColinfo = agentColinfos.get(0);
            profitDetailMonthTemp.setAccountId(agentColinfo.getCloBankAccount());
            profitDetailMonthTemp.setAccountName(agentColinfo.getCloRealname());
            profitDetailMonthTemp.setOpenBankName(agentColinfo.getCloBank());
            profitDetailMonthTemp.setBankCode(agentColinfo.getBranchLineNum());
            profitDetailMonthTemp.setTax(agentColinfo.getCloTaxPoint());
            profitDetailMonthTemp.setPayStatus(agentColinfo.getCloType().toString());
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
        PageInfo pageInfo = businessPlatformService.queryBusinessPlatformList(agentBusInfo, new Agent(),null,null,"");
        if (pageInfo != null && pageInfo.getTotal() > 0) {
            Map<String, Object> agentMap = (Map<String, Object>) pageInfo.getRows().get(0);
            try {
                getParentAgentId(agentMap);
                return agentMap;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  null;
    }

    /***
     * @Description: 获取上级代理商信息
     * @Param:
     * @return:
     * @Author: zhaodw
     * @Date: 2018/8/21
     */
    private void getParentAgentId(Map<String, Object> posMap) throws Exception{
        List<AgentBusInfo> agentBusInfo = agentBusinfoService.queryParenFourLevelBusNum(new ArrayList<AgentBusInfo>(), (String)posMap.get("BUS_PLATFORM"), (String)posMap.get("BUS_NUM"));
        if(agentBusInfo != null && !agentBusInfo.isEmpty()){
            posMap.put("parentAgentId", agentBusInfo.get(0).getBusNum());
            posMap.put("parentAgentPid", agentBusInfo.get(0).getAgentId());
        }
    }
}
