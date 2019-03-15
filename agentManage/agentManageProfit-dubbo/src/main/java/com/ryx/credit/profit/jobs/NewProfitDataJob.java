package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.dao.TransProfitDetailMapper;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.profit.service.DeductService;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.profit.service.TransProfitDetailService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.profit.IPosProfitDataService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
@Transactional(rollbackFor = RuntimeException.class)
public class NewProfitDataJob {

    private org.slf4j.Logger LOG = LoggerFactory.getLogger(NewProfitDataJob.class);

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

    @Autowired
    private AgentService agentService;

    @Autowired
    TransProfitDetailMapper transProfitDetailMapper;

    @Autowired
    @Qualifier("posProfitComputeServiceImpl")
    private DeductService posProfitComputeServiceImpl;

    /**
     * @Author: Zhang Lei
     * POS分润明细数据同步（TransProfitDetail）
     * 同步完成后执行月汇总（ProfitDetailMonth）
     * 每月3号9点10分执行
     * @Date: 11:25 2019/1/24
     */
    @Scheduled(cron = "0 10 9 3 * ?")
    public void doCron() {
        String profitDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);
        deal(profitDate);
    }

    public void deal(String profitDate) {

        profitDate = profitDate == null ? LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6) : profitDate;
        LOG.info("分润月份" + profitDate);
        LOG.info("========获取POS分润数据开始=====");
        long t1 = System.currentTimeMillis();
        final String settleMonth = profitDate;
        try {
            AgentResult agentResult = posProfitDataService.getPosProfitDate(profitDate);
            if (agentResult != null && agentResult.getData() != null) {
                JSONObject json = JSONObject.parseObject(agentResult.getData().toString());
                if (json != null && json.size() > 0) {
                    if (json.containsKey("pftData")) {
                        JSONArray array = json.getJSONArray("pftData");
                        if (array != null && array.size() > 0) {
                            //清除数据
                            transProfitDetailMapper.deleteBySourceIdAndMonth("POS", profitDate);
                            //插入数据
                            array.stream().forEach(object -> {
                                JSONObject profitData = (JSONObject) object;
                                LOG.info("POS分润数据同步:{}", profitData.getString("ORG_ID"));
                                Map<String, Object> agentMap = getAgentId(profitData.getString("ORG_ID"));
                                if (agentMap != null) {
                                    insertTransProfitDetail(agentMap, profitData, settleMonth);
                                }
                            });
                        }
                    }
                    doSum(profitDate);
                } else {
                    LOG.error("月份：" + profitDate + "，二维码提供的没有获取到数据");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("分润数据处理失败");
            throw new RuntimeException("分润数据处理失败");
        }

        long t2 = System.currentTimeMillis();
        LOG.info("========获取POS分润数据结束，耗时{}ms=====", (t2 - t1));


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
    private void insertTransProfitDetail(Map<String, Object> agentMap, JSONObject profitData, String settleMonth) {
        TransProfitDetail transProfitDetail = new TransProfitDetail();
        transProfitDetail.setAgentId((String) agentMap.get("AGENT_ID"));
        transProfitDetail.setBusNum(profitData.getString("ORG_ID"));
        transProfitDetail.setAgentName((String) agentMap.get("AG_NAME"));
        transProfitDetail.setParentAgentId((String) agentMap.get("parentAgentId"));
        transProfitDetail.setParentBusNum((String) agentMap.get("parentBusNum"));
        transProfitDetail.setParentAgentName((String) agentMap.get("parentAgentName"));
        transProfitDetail.setProfitDate(settleMonth);
        transProfitDetail.setProfitAmt(profitData.getBigDecimal("PFT_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("PFT_AMT"));
        transProfitDetail.setInTransAmt(profitData.getBigDecimal("TRAN_01_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("TRAN_01_AMT"));
        transProfitDetail.setOutTransAmt(profitData.getBigDecimal("TRAN_02_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("TRAN_02_AMT"));

        transProfitDetail.setQrTranAmt(profitData.getBigDecimal("QR_TRAN_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("QR_TRAN_AMT"));
        transProfitDetail.setQrTranFee(profitData.getBigDecimal("QR_TRAN_FEE") == null ? BigDecimal.ZERO : profitData.getBigDecimal("QR_TRAN_FEE"));
        transProfitDetail.setQrPftAmt(profitData.getBigDecimal("QR_PFT_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("QR_PFT_AMT"));
        transProfitDetail.setPosTranAmt(profitData.getBigDecimal("POS_TRAN_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("POS_TRAN_AMT"));
        transProfitDetail.setPosTranFee(profitData.getBigDecimal("POS_TRAN_FEE") == null ? BigDecimal.ZERO : profitData.getBigDecimal("POS_TRAN_FEE"));
        transProfitDetail.setPosPftAmt(profitData.getBigDecimal("POS_PFT_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("POS_PFT_AMT"));


        transProfitDetail.setInProfitScale(profitData.getBigDecimal("PFT_01_RATE"));
        transProfitDetail.setOutProfitScale(profitData.getBigDecimal("PFT_02_RATE"));
        transProfitDetail.setInProfitAmt(profitData.getBigDecimal("PFT_01_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("PFT_01_AMT"));
        transProfitDetail.setOutProfitAmt(profitData.getBigDecimal("PFT_02_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("PFT_02_AMT"));
        transProfitDetail.setSourceInfo("POS");
        transProfitDetail.setBusCode((String) agentMap.get("BUS_PLATFORM"));
        transProfitDetail.setSupplyAmt(profitData.getBigDecimal("PFT_DIFF_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("PFT_DIFF_AMT"));
        transProfitDetail.setPosRewardAmt(profitData.getBigDecimal("POS_03_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("POS_03_AMT"));
        transProfitDetail.setIposCreditAmt(profitData.getBigDecimal("POS_02_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("POS_02_AMT"));
        transProfitDetail.setPosCreditAmt(profitData.getBigDecimal("POS_01_AMT") == null ? BigDecimal.ZERO : profitData.getBigDecimal("POS_01_AMT"));
        transProfitDetail.setId(idService.genId(TabId.TPD));
        transProfitDetail.setAgentType((String) agentMap.get("BUS_TYPE"));
        transProfitDetail.setPayCompany((String) agentMap.get("CLO_PAY_COMPANY"));
        transProfitDetailService.insert(transProfitDetail);
        transProfitDetail = null;
    }

    /***
     * @Description: 执行汇总
     * @Param: profitDate 分润日期
     * @Author: zhaodw
     * @Date: 2018/8/27
     */
    public void doSum(String profitDate) {
        // 汇总到月分润明细中
        List<TransProfitDetail> transProfitDetails = transProfitDetailService.getPosTransProfitDetailSumList(profitDate);
        if (transProfitDetails != null && transProfitDetails.size() > 0) {
            transProfitDetails.forEach(transProfitDetail -> {
                LOG.info("POS分润数据汇总：{}", transProfitDetail.getAgentId());
                ProfitDetailMonth profitDetailMonthTemp = new ProfitDetailMonth();
                profitDetailMonthTemp.setParentAgentId(transProfitDetail.getParentAgentId());
                profitDetailMonthTemp.setAgentId(transProfitDetail.getAgentId());
                profitDetailMonthTemp.setProfitDate(profitDate);
                profitDetailMonthTemp = profitDetailMonthMapper.selectByIdAndParent(profitDetailMonthTemp);
                if (profitDetailMonthTemp == null) {
                    profitDetailMonthTemp = new ProfitDetailMonth();
                    insertProfitMonthDetail(profitDetailMonthTemp, transProfitDetail);
                } else {
                    //profitDetailMonthTemp.setTranAmt((profitDetailMonthTemp.getTranAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getTranAmt()).add(transProfitDetail.getInTransAmt()));
                    //profitDetailMonthTemp.setPayAmt((profitDetailMonthTemp.getPayAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getPayAmt()).add(transProfitDetail.getOutTransAmt()));
                    profitDetailMonthTemp.setTranAmt( transProfitDetail.getInTransAmt());
                    profitDetailMonthTemp.setPayAmt(transProfitDetail.getOutTransAmt());
                    profitDetailMonthTemp.setTranProfitScale(transProfitDetail.getInProfitScale().toString());
                    profitDetailMonthTemp.setPayProfitScale(transProfitDetail.getOutProfitScale().toString());
                    //profitDetailMonthTemp.setTranProfitAmt((profitDetailMonthTemp.getTranProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getTranProfitAmt()).add(transProfitDetail.getInProfitAmt()));
                    //profitDetailMonthTemp.setPayProfitAmt((profitDetailMonthTemp.getPayProfitAmt() == null ? BigDecimal.ZERO : profitDetailMonthTemp.getPayProfitAmt()).add(transProfitDetail.getOutProfitAmt()));
                    profitDetailMonthTemp.setTranProfitAmt(transProfitDetail.getInProfitAmt());
                    profitDetailMonthTemp.setPayProfitAmt( transProfitDetail.getOutProfitAmt());
                    profitDetailMonthTemp.setPosZqSupplyProfitAmt(transProfitDetail.getSupplyAmt());
                    profitDetailMonthServiceImpl.update(profitDetailMonthTemp);
                }
            });
        }
    }

    /***
     * @Description: 插入分润汇总
     * @Author: zhaodw
     * @Date: 2018/8/6
     */
    private void insertProfitMonthDetail(ProfitDetailMonth profitDetailMonthTemp, TransProfitDetail transProfitDetail) {
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
        profitDetailMonthTemp.setBusPlatform(transProfitDetail.getBusCode());
        profitDetailMonthTemp.setPayCompany(transProfitDetail.getPayCompany());
        // 获取账户信息
        List<AgentColinfo> agentColinfos = agentColinfoService.queryAgentColinfoService(transProfitDetail.getAgentId(), null, AgStatus.Approved.status);

        if (agentColinfos != null && agentColinfos.size() > 0) {
            AgentColinfo agentColinfo = agentColinfos.get(0);
            profitDetailMonthTemp.setAccountId(agentColinfo.getCloBankAccount());
            profitDetailMonthTemp.setAccountName(agentColinfo.getCloRealname());
            profitDetailMonthTemp.setOpenBankName(agentColinfo.getCloBank());
            profitDetailMonthTemp.setBankCode(agentColinfo.getBranchLineNum());
            profitDetailMonthTemp.setTax(String.valueOf(agentColinfo.getCloTaxPoint()));
            profitDetailMonthTemp.setPayStatus(agentColinfo.getCloType().toString());
        }

        profitDetailMonthServiceImpl.insert(profitDetailMonthTemp);
        profitDetailMonthTemp = null;
    }


    /***
     * @Description: 获取代理商信息
     * @Param: orgId 代理商id
     * @return: 代理商系统代理商唯一id
     * @Author: zhaodw
     * @Date: 2018/8/3
     */
    private Map<String, Object> getAgentId(String orgId) {
        // 获取代理商平台id
        List<Map<String, Object>> agentList = businessPlatformService.queryByBusNum(orgId);
        if (agentList != null && agentList.size() > 0) {
            Map<String, Object> agentMap = agentList.get(0);
            try {
                getParentAgentId(agentMap);
                return agentMap;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /***
     * @Description: 获取上级代理商信息
     * @Param:
     * @return:
     * @Author: zhaodw
     * @Date: 2018/8/21
     */
    private void getParentAgentId(Map<String, Object> posMap) throws Exception {
        List<AgentBusInfo> agentBusInfo = agentBusinfoService.queryParenFourLevelBusNum(new ArrayList<AgentBusInfo>(), (String) posMap.get("BUS_PLATFORM"), (String) posMap.get("BUS_NUM"));
        if (agentBusInfo != null && !agentBusInfo.isEmpty()) {
            posMap.put("parentBusNum", agentBusInfo.get(0).getBusNum());
            posMap.put("parentAgentId", agentBusInfo.get(0).getAgentId());
            Agent agent = agentService.getAgentById(agentBusInfo.get(0).getAgentId());
            posMap.put("parentAgentName", agent != null ? agent.getAgName() : null);
        }
    }
}
