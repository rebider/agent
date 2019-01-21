package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
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
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.profit.PosOrganDataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

    @Autowired
    private AgentBusinfoService agentBusinfoService;

    @Autowired
    private PosOrganDataService posOrganDataServiceImpl;

    @Autowired
    private AgentService agentService;

    @Scheduled(cron = "0 0 1 1 * ?")
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
        // 获取现有补款数据
        Map<String, BigDecimal> orgMap = new HashMap<>(10);
        Map<String, String> supplyIdMap = new HashMap<>(10);
        LOG.info("对数据汇总并生成补款明细。");
        // 获取现有未扣完数据
        String supplyDate = LocalDate.now().plusMonths(-1).toString().substring(0,7).replace("-","");
        Map<String, Object> query = new HashMap<>();
        query.put("supplyDate", supplyDate);
        query.put("bussType", "02".equals(bussType)?"POS":"MPOS");
        List<ProfitSettleErrLs> settleErrLs = profitSettleErrLsService.getNotSupplyProfitSettleErrLsList(query);
        insertSettleErrList(array, orgMap, supplyIdMap, settleErrLs, "2");

        if (orgMap.size() > 0) {
            Set<String> keys = orgMap.keySet();
            for (String key : keys) {
                insertSupply(key, orgMap.get(key), supplyIdMap, bussType);
            }
        }
    }

    /***
    * @Description: 新增补款信息
    * @Param:  jsonObject 应补分润
    * @Author: zhaodw
    * @Date: 2018/8/7
    */
    private void insertSupply(String instId, BigDecimal supplyAmt, Map<String, String> supplyIdMap,  String bussType) {
        Map<String,Object> agentMap = getAgentId(instId);
        String supplyDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
        ProfitSupply profitSupply = new ProfitSupply();
        profitSupply.setId(supplyIdMap.get(instId));
        profitSupply.setSupplyType(SUPPLY_DESC);
        profitSupply.setRemerk(SUPPLY_DESC);
        if (agentMap !=null){
            profitSupply.setAgentId((String)agentMap.get("AGENT_ID"));
            profitSupply.setAgentName((String)agentMap.get("AG_NAME"));
            profitSupply.setParentAgentId((String)agentMap.get("parentAgentId"));
            profitSupply.setParentAgentName((String)agentMap.get("parentAgentName"));
        }else {
            profitSupply.setAgentId(instId);
        }
        profitSupply.setSupplyAmt(supplyAmt);
        profitSupply.setSupplyDate(supplyDate);
        profitSupply.setBusType(bussType);
        profitSupply.setBusBigType("01");
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
            // 获取现有未扣完数据
            String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0,7).replace("-","");
            Map<String, Object> query = new HashMap<>();
            query.put("deductionDate", deductionDate);
            query.put("bussType", ((String)param.get("bussType")).equals("02")?"POS":"MPOS");
            List<ProfitSettleErrLs> settleErrLs = profitSettleErrLsService.getNotDeductionProfitSettleErrLsList(query);
            LOG.info("对数据汇总并生成退单明细。");
            insertSettleErrList(array, orgMap, deductionIdMap, settleErrLs, "1");
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
    private void insertProfitDeduction(String instId, BigDecimal addAmt, Map<String, String> deductionIdMap, String bussType) {
        String deductionDate = LocalDate.now().plusMonths(-1).toString().substring(0,7).replace("-","");
        Map<String,Object> agentMap = getAgentId(instId);
        ProfitDeduction deduction = new ProfitDeduction();
        deduction.setDeductionType(DeductionType.SETTLE_ERR.getType());
        if (agentMap!=null) {
            deduction.setAgentId((String) agentMap.get("AGENT_ID"));
            deduction.setParentAgentId((String) agentMap.get("parentAgentId"));
            deduction.setParentAgentName((String)agentMap.get("parentAgentName"));
            deduction.setAgentName((String) agentMap.get("AG_NAME"));
        }else{
            deduction.setAgentId(instId);
        }
        deduction.setId(deductionIdMap.get(instId));
        deduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());
        deduction.setDeductionDesc(DEDUCTION_DESC);
        // 获取本月后面分期未扣完金额
        Map<String, Object> param = new HashMap<>(2);
        param.put("agentId", deduction.getAgentId());
        param.put("parentAgentId",deduction.getParentAgentId());

        deduction.setAddDeductionAmt(addAmt);
        deduction.setSumDeductionAmt(addAmt);
        deduction.setMustDeductionAmt(addAmt);
        deduction.setSourceId(bussType);
        deduction.setDeductionDate(deductionDate);
        deduction.setCreateDateTime(new Date());
        deduction.setDeductionStatus("0");
        profitDeductionService.insert(deduction);
    }

    /***
     * 获取本月应扣金额
     * @param deduction 扣款对象
     * @return 应扣总金额
     */
    private BigDecimal getCurrentMonthDeductionAmt( ProfitDeduction deduction ) {
        // 获取本月退单扣款信息
        ProfitDeduction query = new ProfitDeduction();
        query.setDeductionDate(deduction.getDeductionDate());
        query.setAgentId(deduction.getAgentId());
        query.setDeductionType(DeductionType.SETTLE_ERR.getType());
        query.setParentAgentId(deduction.getParentAgentId());
        query.setActualDeductionAmt(BigDecimal.ZERO);
        List<ProfitDeduction> profitDeductions = profitDeductionService.getProfitDeduction(query);
        if (profitDeductions != null && profitDeductions.size() > 0) {
            return profitDeductions.stream().map(ProfitDeduction::getMustDeductionAmt).reduce(BigDecimal::add).get();
        }else{
            return BigDecimal.ZERO;
        }
    }

    /*** 
    * @Description: 插入退单数据并对相同机构的做合并
    * @Param:   array 退单数据
    * @Param:  orgMap 计算代理商汇总
    * @Author: zhaodw
    * @Date: 2018/7/30 
    */ 
    private void insertSettleErrList(JSONArray array,  Map<String, BigDecimal> orgMap,
                                     Map<String, String> deductionIdMap, List<ProfitSettleErrLs> settleErrLs,
                                     String operate) {

        if (settleErrLs != null && settleErrLs.size() > 0) {
            String sourceId = null;
            Map<String , BigDecimal> sourceIdMap = new HashMap<>();
            JSONArray delArray = new JSONArray();
            for (ProfitSettleErrLs profitSettleErrLs : settleErrLs) {
                boolean hasNode = false;
                sourceId = profitSettleErrLs.getSourceId();
                // 匹配退单是否有轧差或已追回的
                if (profitSettleErrLs.getHostLs() != null) {
                    for (Object obj : array) {
                        JSONObject json = (JSONObject) obj;
                        if (profitSettleErrLs.getHostLs().equals(json.getString("hostLs"))) {
                            BigDecimal result = profitSettleErrLs.getMustDeductionAmt().subtract(json.getBigDecimal("shouldDeductAmt").abs());
                            if (result.doubleValue() > 0) {
                                if (sourceIdMap.containsKey(sourceId)) {
                                    sourceIdMap.put(sourceId, sourceIdMap.get(sourceId).add(result));
                                } else {
                                    sourceIdMap.put(sourceId, result);
                                }
                            }
                            hasNode = true;
                            delArray.add(obj);
                            break;
                        }
                    }
                    array.removeAll(delArray);// 删除重复的退单
                }else{
                    hasNode = true;
                }
                if (!hasNode) {
                    if (sourceIdMap.containsKey(sourceId)) {
                        sourceIdMap.put(sourceId, sourceIdMap.get(sourceId).add(profitSettleErrLs.getMustDeductionAmt()));
                    }else{
                        sourceIdMap.put(sourceId, profitSettleErrLs.getMustDeductionAmt());
                    }
                }
            }

            // 异步处理已变更退单
            if (!sourceIdMap.isEmpty()) {
                new Thread(() -> {
                    profitDeductionService.updateProfitDeductionByMap(sourceIdMap);
                }).start();
            }
        }
        array.stream().
               filter(json->(StringUtils.isNotBlank(((JSONObject)json).getString("instId"))) &&
                       (((JSONObject)json).getBigDecimal("shouldDeductAmt").doubleValue()< 0 ||
                               ((JSONObject)json).getBigDecimal("shouldMakeAmt").doubleValue()> 0) &&
                       ((JSONObject)json).getString("hostLs") != null   )
               .forEach(json->{
                   insertSettleErr((JSONObject)json, orgMap, deductionIdMap, operate);
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
    private void insertSettleErr( JSONObject jsonObject, Map<String, BigDecimal> orgMap, Map<String, String> deductionIdMap, String operate) {
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
        String orgId = jsonObject.getString("instId");

        synchronized (object) {
            if ("POS".equals(jsonObject.getString("businessType"))){
                //TODO 获取一级代理商业务码
                AgentResult result = posOrganDataServiceImpl.getFirstAgent(orgId);
                if (result != null ) {
                    JSONObject json = JSONObject.parseObject(result.getData().toString());
                    if (json != null && json.containsKey("PFT_ORG_ID")) {
                        orgId = json.getString("PFT_ORG_ID");
                    }
                }
            }
            if (orgMap.containsKey(orgId)) {
                if ("1".equals(operate)) {
                    orgMap.put(orgId, orgMap.get(orgId).add(jsonObject.getBigDecimal("shouldDeductAmt").abs()));
                }else{
                    orgMap.put(orgId, orgMap.get(orgId).add(jsonObject.getBigDecimal("shouldMakeAmt")));
                }

            }else {
                if ("1".equals(operate)) {
                    orgMap.put(orgId, jsonObject.getBigDecimal("shouldDeductAmt").abs());
                    deductionIdMap.put(orgId,  idService.genId(TabId.P_DEDUCTION));
                }else{
                    orgMap.put(orgId, jsonObject.getBigDecimal("shouldMakeAmt"));
                    deductionIdMap.put(orgId,  idService.genId(TabId.p_profit_supply));
                }


            }
        }
        settleErrLs.setSourceId(deductionIdMap.get(orgId));
        settleErrLs.setInstId(orgId);
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
        List<Map<String, Object>> resultList = businessPlatformService.queryByBusNum(orgId);
        if (resultList != null && resultList.size() > 0) {
            Map<String, Object> agentMap = resultList.get(0);
            try {
                if (agentMap != null && !agentMap.isEmpty()) {
                    getParentAgentId(agentMap);
                    return agentMap;
                }
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
            posMap.put("parentAgentId", agentBusInfo.get(0).getAgentId());
            Agent agent = agentService.getAgentById(agentBusInfo.get(0).getAgentId());
            posMap.put("parentAgentName", agent!= null?agent.getAgName():null);
        }
    }
}
