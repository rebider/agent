package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.ProfitStatus;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.*;
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
 * @Description: huoq
 * @date 2018/7/2911:34
 */
@Service("profitDataJob")
@Transactional
public class ProfitDataJob {

    private static final Logger LOG = Logger.getLogger(ProfitDataJob.class);

    private static final  String URL =  AppConfig.getProperty("check_tran_url");

    @Autowired
    private IPosProfitDataService posProfitDataService;

    @Autowired
    private ProfitOrganTranMonthService profitOrganTranMonthService;


    @Autowired
    private BusinessPlatformService businessPlatformService;

    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;

    @Autowired
    private ProfitMonthService profitMonthServiceImpl;

    @Autowired
    private OrganTranMonthDetailService organTranMonthDetailService;


    @Autowired
    private IdService idService;

//    @Scheduled(cron = "0 0 12 10 * ?")
    public void deal() {
        String settleMonth = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        LOG.info("分润月份"+settleMonth);
        LOG.info("获取分润数据");
        AgentResult agentResult = posProfitDataService.getPosProfitDate(settleMonth);
        JSONObject jsonObject = getTranData(settleMonth);
        if (agentResult != null && agentResult.getData() != null) {
            JSONObject json = JSONObject.parseObject(agentResult.getData().toString());
            if (json != null) {
                addQr(json, settleMonth);//新增二维码
                if (jsonObject != null && jsonObject.containsKey("info")) {
                    JSONObject tranData = jsonObject.getJSONObject("info");
                    BigDecimal zyssAmt = tranData.getBigDecimal("zyssAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("zyssAmt");;// 自营代理手刷总金额
                    BigDecimal zydlPosAmt = tranData.getBigDecimal("zydlPosAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("zydlPosAmt");;// 自营代理pos总金额
                    BigDecimal zyPosAmt = tranData.getBigDecimal("zyPosAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("zyPosAmt");//自营交易总金额
                    BigDecimal hyxJwAmt = tranData.getBigDecimal("hyxJwAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("hyxJwAmt");//汇银讯境外卡交易总金额
                    BigDecimal orgJwAmt = tranData.getBigDecimal("orgJwAmt")==null?BigDecimal.ZERO:tranData.getBigDecimal("orgJwAmt");//代理商境外卡交易总金额
                    BigDecimal tranAmt = zydlPosAmt.subtract(zyPosAmt).subtract(hyxJwAmt).subtract(orgJwAmt);
                    LOG.info("新增pos数据");
                    addPos(json, settleMonth, tranAmt);//新增pos
                    LOG.info("新增或修改二维码数据");
                    insertOrUpdateQr(settleMonth, zyssAmt);
                }
                if (json.containsKey("PFT_DATA")) {
                    JSONArray array = json.getJSONArray("PFT_DATA");

                    if (array != null && array.size() > 0 ) {
//                        ProfitDetailMonth profitDetailMonth = new ProfitDetailMonth();
//                        profitDetailMonth.setProfitDate(settleMonth);
//                        List<ProfitDetailMonth> profitDetailMonthList = profitMonthServiceImpl.getProfitDetailMonthList(null, profitDetailMonth);
//                        // 存在数据，对数据进行更新
//                        if (profitDetailMonthList != null && profitDetailMonthList.size() > 0) {
//
//                        }
//                        //新增数据
//                        else{
                            array.parallelStream().forEach(object->{
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
//                        }

                    }
                }
            }else{
                LOG.error("月份："+settleMonth+"，二维码提供的没有获取到数据");
            }
        }
    }

    private String insertProfitMonth(Map<String, Object> agentMap, JSONObject profitData, String settleMonth) {
        // pos交易分润额
        BigDecimal posTranProFitAmt = profitData.getBigDecimal("PFT_01_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_01_AMT");
        // pos付款分润额
        BigDecimal posPayProFitAmt = profitData.getBigDecimal("PFT_01_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_01_AMT");

        ProfitMonth profitMonth = new ProfitMonth();

        profitMonth.setId(idService.genId(TabId.P_PROFIT_M));
        profitMonth.setAgentId((String)agentMap.get("AG_UNIQ_NUM"));
        profitMonth.setAgentName((String)agentMap.get("AG_NAME"));
        profitMonth.setStatus("4");
        profitMonth.setProfitDate(settleMonth);
        profitMonth.setTransProfitPos(posTranProFitAmt.add(posPayProFitAmt));
//        profitMonth.setTransSupplyProfitPos();
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
        organTranMonthDetail.setAgentId((String)agentMap.get("AG_UNIQ_NUM"));
        organTranMonthDetail.setAgentName((String)agentMap.get("AG_NAME"));
        organTranMonthDetail.setPosJlTranAmt(profitData.getBigDecimal("PFT_02_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_02_AMT"));
        organTranMonthDetail.setzPosTranAmt(profitData.getBigDecimal("PFT_02_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_02_AMT"));
        organTranMonthDetail.setPosTranAmt(profitData.getBigDecimal("PFT_02_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_02_AMT"));
        organTranMonthDetail.setProfitId(profitId);
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
        profitDetailMonthTemp.setAgentId((String)agentMap.get("AG_UNIQ_NUM"));
        profitDetailMonthTemp.setAgentPid(profitData.getString("ORG_ID"));
        profitDetailMonthTemp.setProfitDate(settleMonth);
        profitDetailMonthTemp.setAgentName((String)agentMap.get("AG_NAME"));
        profitDetailMonthTemp.setTranAmt(profitData.getBigDecimal("TRAN_01_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("TRAN_01_AMT"));
        profitDetailMonthTemp.setPayAmt(profitData.getBigDecimal("TRAN_01_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("TRAN_01_AMT"));
        profitDetailMonthTemp.setTranProfitScale(profitData.getString("PFT_01_RATE"));
        profitDetailMonthTemp.setPayProfitScale(profitData.getString("PFT_02_RATE"));
        profitDetailMonthTemp.setTranProfitAmt(profitData.getBigDecimal("PFT_01_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_01_AMT"));
        profitDetailMonthTemp.setPayProfitAmt(profitData.getBigDecimal("PFT_02_AMT")==null?BigDecimal.ZERO:profitData.getBigDecimal("PFT_02_AMT"));
        profitDetailMonthTemp.setProfitId(profitId);
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
        PageInfo pageInfo = businessPlatformService.queryBusinessPlatformList(agentBusInfo, null,null);
        if (pageInfo != null && pageInfo.getTotal() > 0) {
            return (Map<String, Object>) pageInfo.getRows().get(0);
        }
        return  null;
    }

    /***
    * @Description:
    * @Param:
    * @return:
    * @Author: zhaodw
    * @Date: 2018/8/3
    */
    private void insertOrUpdateQr(String settleMonth, BigDecimal zyssAmt) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setProductType("02");
        PageInfo pageInfo = profitOrganTranMonthService.getProfitOrganTranMonthList(profitOrganTranMonth, null);
        boolean hasQr = false;
        if(pageInfo != null && pageInfo.getTotal() > 0) {
            List<ProfitOrganTranMonth> profitOrganTranMonths = pageInfo.getRows();
            for (ProfitOrganTranMonth organTranMonth : profitOrganTranMonths) {
                    hasQr = true;
                    organTranMonth.setTranAmt(zyssAmt);
                    organTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
                    organTranMonth.setDifferenceAmt(organTranMonth.getSettleAmt().subtract(zyssAmt));
                    profitOrganTranMonthService.update(organTranMonth);
            }
        }
        if (!hasQr) {
            addMpos(settleMonth, zyssAmt);
        }
    }

    /***
     * @Description: 增加MPOS数据
     * @Param: json
     * @Author: zhaodw
     * @Date: 2018/8/1
     */
    private void addMpos(String settleMonth, BigDecimal tranAmt) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("02");
        profitOrganTranMonth.setProductName("MPOS");
        profitOrganTranMonth.setTranAmt(tranAmt);
        profitOrganTranMonthService.insert(profitOrganTranMonth);
    }

    /***
    * @Description: 增加 二维码核对数据
    * @Param: json
    * @Author: zhaodw
    * @Date: 2018/8/1
    */
    private void addQr(JSONObject json, String settleMonth) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("03");
        profitOrganTranMonth.setProductName("二维码");
        profitOrganTranMonth.setTranAmt(json.getBigDecimal("QR_TRAN_AMT"));
        profitOrganTranMonth.setSettleAmt(json.getBigDecimal("PFT_QR_TRAN_AMT"));
        profitOrganTranMonth.setDifferenceAmt(json.getBigDecimal("PFT_QR_TRAN_AMT").subtract(json.getBigDecimal("QR_TRAN_AMT")));
        profitOrganTranMonthService.insert(profitOrganTranMonth);
    }

    /***
     * @Description: 增加POS数据
     * @Param: json
     * @Author: zhaodw
     * @Date: 2018/8/1
     */
    private void addPos(JSONObject json, String settleMonth, BigDecimal tranAmt) {
        ProfitOrganTranMonth profitOrganTranMonth = new ProfitOrganTranMonth();
        profitOrganTranMonth.setProfitDate(settleMonth);
        profitOrganTranMonth.setCheckDate(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
        profitOrganTranMonth.setId(idService.genId(TabId.P_ORGAN_TRAN_MONTH));
        profitOrganTranMonth.setProductType("01");
        profitOrganTranMonth.setProductName("POS");
        profitOrganTranMonth.setSettleAmt(json.getBigDecimal("PFT_POS_TRAN_AMT"));
        profitOrganTranMonth.setTranAmt(tranAmt);
        profitOrganTranMonth.setDifferenceAmt(profitOrganTranMonth.getSettleAmt().subtract(tranAmt));
        profitOrganTranMonthService.insert(profitOrganTranMonth);
    }


    /***
     * @Description: 获取POS交易数据
     * @Param:  settleMonth 分润月份
     * @return:  数据json对象
     * @Author: zhaodw
     * @Date: 2018/8/2
     */
    private JSONObject getTranData(String settleMonth) {
        LOG.info("获取POS交易数据");
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
