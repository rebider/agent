package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitSettleErrLs;
import com.ryx.credit.profit.pojo.ProfitSupply;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ProfitSettleErrLsService;
import com.ryx.credit.profit.service.ProfitSupplyService;
import com.ryx.credit.profit.service.StagingService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhaodw
 * @Title: RefundJob
 * @ProjectName agentManage
 * @Description: 退单任务
 * @date 2018/7/2911:33
 */
@Service("refundJob")
@Transactional(rollbackFor=RuntimeException.class)
public class RefundJob {
    private static final Logger LOG = Logger.getLogger(RefundJob.class);
    private static final  String URL =  AppConfig.getProperty("refund_url");

    private static  final  String DEDUCTION_DESC = "退单扣款";

    public static  final  String SUPPLY_DESC = "退单补款";

    private final Object object = new Object();

    @Autowired
    private ProfitSettleErrLsService profitSettleErrLsService;

    @Autowired
    private ProfitDeductionService profitDeductionService;

    @Autowired
    private IdService idService;

    @Autowired
    private BusinessPlatformService businessPlatformService;

    @Autowired
    private ProfitSupplyService profitSupplyServiceImpl;


    @Autowired
    private StagingService stagingServiceImpl;



//    @Scheduled(cron = "0 0 12 * * ?")
    @Transactional
    public void deal() {
        // 上月的开始及结束日期
        JSONObject param = new JSONObject();
        // pos退单应扣分润
        param.put("bussType", "02");
        getDeductionListAndDeal(param);
        // pos退单应补分润
        getSupplyListAndDeal(param);

        param.put("bussType", "01");
        // mpos退单应扣分润
        getDeductionListAndDeal(param);
        // 退mpos单应补分润
        getSupplyListAndDeal(param);
    }

    /***
     * @Description: 获取退单应补分润并做处理
     * @Param:  param 查询条件
     * @Author: zhaodw
     * @Date: 2018/8/7
     */
    private void getSupplyListAndDeal(JSONObject param) {
        param.put("flag","2");
        JSONObject result = getRefundList(param);
        if (result.containsKey("info") ) {
            JSONArray array = result.getJSONArray("info");
            LOG.info("生成补款信息。");
            insertSupplyList(array, param.getString("bussType"));
        }else{
            LOG.info("没有获取到补款信息。");
        }
    }

    private void insertSupplyList(JSONArray array, String bussType) {
        array.stream().
                filter(json->(StringUtils.isNotBlank(((JSONObject)json).getString("instId"))) &&
                                ((JSONObject)json).getBigDecimal("shouldMakeAmt").doubleValue()> 0)
                .forEach(json->{
                            insertSupply((JSONObject) json, bussType);
                        }
                );
    }

    /***
    * @Description: 新增补款信息
    * @Param:  jsonObject 应补分润
    * @Author: zhaodw
    * @Date: 2018/8/7
    */
    private void insertSupply(JSONObject jsonObject, String bussType) {
        Map<String,Object> agentMap = getAgentId(jsonObject.getString("instId"));
        String supplyDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        ProfitSupply profitSupply = new ProfitSupply();
        profitSupply.setId(idService.genId(TabId.p_profit_supply));
        profitSupply.setSupplyType(SUPPLY_DESC);
        profitSupply.setRemerk(SUPPLY_DESC);
        if (agentMap !=null){
            profitSupply.setAgentPid((String)agentMap.get("AG_UNIQ_NUM"));
            profitSupply.setAgentName((String)agentMap.get("AG_NAME"));
        }
        profitSupply.setAgentId(jsonObject.getString("instId"));
        profitSupply.setSupplyAmt(jsonObject.getBigDecimal("shouldMakeAmt"));
        profitSupply.setSupplyDate(supplyDate);
        profitSupply.setSourceId(bussType);
        profitSupplyServiceImpl.insert(profitSupply);
    }

    /***
    * @Description: 获取退单应扣分润并做处理
    * @Param:  param 查询条件
    * @Author: zhaodw
    * @Date: 2018/8/7
    */
    private void getDeductionListAndDeal(JSONObject param) {
        Map<String, BigDecimal> orgMap = new HashMap<>(10);
        Map<String, String> deductionIdMap = new HashMap<>(10);
        LOG.info("每月定时获取退单应扣数据。");
        param.put("flag","1");
        JSONObject result = getRefundList(param);
        if (result.containsKey("info") ) {
            JSONArray array = result.getJSONArray("info");
            LOG.info("对数据汇总并生成退单明细。");
            insertSettleErrList(array, orgMap, deductionIdMap);
            LOG.info("对数据汇总并生成扣款信息。");
            if (orgMap.size() > 0) {
                Set<String> keys = orgMap.keySet();
                for (String key : keys) {
                    insertProfitDeduction(key, orgMap.get(key), deductionIdMap, param.getString("bussType"));
                }
            }
        }
    }

    /***
     * @Description:  获取退单应扣分润列表
     * @return:  返回的退单应扣分润列表
     * @Author: zhaodw
     * @Date: 2018/7/29
     */
    private JSONObject getRefundList(JSONObject param) {
        String result = HttpClientUtil.doPostJson(URL, param.toJSONString());
        if (StringUtils.isNotBlank(result)) {
            return  JSONObject.parseObject(result);
        }
        return null;
    }

    /***
    * @Description: 获取扣款信息对象
    * @Param:  agentId 机构id
    * @Param:  addsum 新增扣款金额
    * @Param:  bussType 业务类型
    * @Param:  id 主键
    * @Author: zhaodw
    * @Date: 2018/7/30
    */
    private void insertProfitDeduction(String agentId, BigDecimal addAmt, Map<String, String> deductionIdMap, String bussType) {
        Map<String,Object> agentMap = getAgentId(agentId);
        ProfitDeduction deduction = new ProfitDeduction();
        deduction.setDeductionType(DeductionType.SETTLE_ERR.getType());
        if (agentMap!=null) {
            deduction.setAgentId((String) agentMap.get("AG_UNIQ_NUM"));
            deduction.setAgentName((String) agentMap.get("AG_NAME"));
        }else{
            deduction.setAgentId(agentId);
        }
        deduction.setAgentPid(agentId);
        deduction.setId(deductionIdMap.get(agentId));
        deduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
        deduction.setDeductionDesc(DEDUCTION_DESC);
        // 获取分期未扣完金额
        BigDecimal stagNotDeductionSumAmt = stagingServiceImpl.getNotDeductionAmt(agentId);
        stagNotDeductionSumAmt = stagNotDeductionSumAmt==null?BigDecimal.ZERO:stagNotDeductionSumAmt;
        addAmt = addAmt.subtract(stagNotDeductionSumAmt);
        deduction.setAddDeductionAmt(addAmt);
        deduction.setSumDeductionAmt(addAmt);
        deduction.setMustDeductionAmt(addAmt);
        deduction.setSourceId(bussType);
        deduction.setDeductionDate(LocalDate.now().plusMonths(-1).toString().substring(0,7));
        profitDeductionService.insert(deduction);
    }

    /*** 
    * @Description: 插入退单数据并对相同机构的做合并
    * @Param:   array 退单数据
    * @Param:  orgMap 计算代理商汇总
    * @Author: zhaodw
    * @Date: 2018/7/30 
    */ 
    private void insertSettleErrList(JSONArray array,  Map<String, BigDecimal> orgMap, Map<String, String> deductionIdMap) {
       array.stream().
               filter(json->(StringUtils.isNotBlank(((JSONObject)json).getString("instId"))) &&
                       ((JSONObject)json).getBigDecimal("shouldDeductAmt").doubleValue()< 0  )
               .forEach(json->{
                   insertSettleErr((JSONObject)json, orgMap, deductionIdMap);
            }
        );
    }

    /*** 
    * @Description: json对象转换为退单对象
    * @Param:  jsonObject 退单json对象
    * @Param:  orgMap 计算代理商汇总
    * @return:  退单对象
    * @Author: zhaodw 
    * @Date: 2018/7/30 
    */ 
    private void insertSettleErr( JSONObject jsonObject, Map<String, BigDecimal> orgMap, Map<String, String> deductionIdMap) {
        ProfitSettleErrLs settleErrLs = new ProfitSettleErrLs();
        settleErrLs.setId(idService.genId(TabId.P_SETTLE_ERR_LS));
        settleErrLs.setChargebackDate(jsonObject.getString("chargebackDate"));
        settleErrLs.setTranDate(jsonObject.getString("tranDate"));
        settleErrLs.setMerchId(jsonObject.getString("merchId"));
        settleErrLs.setTranAmt(jsonObject.getBigDecimal("tranAmt"));
        settleErrLs.setNetAmt(jsonObject.getBigDecimal("netAmt"));
        settleErrLs.setOffsetBalanceAmt(jsonObject.getBigDecimal("offsetBalanceAmt"));
        settleErrLs.setBalanceAmt(jsonObject.getBigDecimal("balanceAmt"));// 剩余未销账金额
        settleErrLs.setHostLs(jsonObject.getString("hostLs"));
        settleErrLs.setMerchName(jsonObject.getString("merchName"));
        settleErrLs.setId(idService.genId(TabId.P_SETTLE_ERR_LS));
        settleErrLs.setBusinessType(jsonObject.getString("businessType"));
        settleErrLs.setCardNo(jsonObject.getString("cardNo"));
        settleErrLs.setErrDate(jsonObject.getString("errDate"));
        settleErrLs.setNettingStatus(jsonObject.getString("nettingStatusText"));
        settleErrLs.setErrDesc(jsonObject.getString("errDesc"));
        settleErrLs.setMustDeductionAmt(jsonObject.getBigDecimal("shouldDeductAmt").abs());// 应扣分润
        settleErrLs.setMustSupplyAmt(jsonObject.getBigDecimal("shouldMakeAmt"));//应补分润
        settleErrLs.setErrCode(jsonObject.getString("errCode"));
        settleErrLs.setCooperationMode(jsonObject.getString("cooperationMode"));
        settleErrLs.setRealDeductAmt(jsonObject.getBigDecimal("realDeductAmt"));
        settleErrLs.setOffsetLossAmt(jsonObject.getBigDecimal("offsetLossAmt"));
        settleErrLs.setYswsAmt(jsonObject.getBigDecimal("ingshou"));
        synchronized (object) {
            if (orgMap.containsKey(jsonObject.getString("instId"))) {
                orgMap.put(jsonObject.getString("instId"), orgMap.get(jsonObject.getString("instId")).add(jsonObject.getBigDecimal("shouldDeductAmt").abs()));
            }else {
                orgMap.put(jsonObject.getString("instId"), jsonObject.getBigDecimal("shouldDeductAmt").abs());
                deductionIdMap.put(jsonObject.getString("instId"),  idService.genId(TabId.P_DEDUCTION));
            }
        }
        settleErrLs.setSourceId(deductionIdMap.get(jsonObject.getString("instId")));
        profitSettleErrLsService.inset(settleErrLs);
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
