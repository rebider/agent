package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitSettleErrLs;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ProfitSettleErrLsService;
import com.ryx.credit.profit.service.StagingService;
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
public class RefundJob {
    private static final Logger LOG = Logger.getLogger(RefundJob.class);
    private static final  String URL =  AppConfig.getProperty("refund_url");

    private static  final  String DEDUCTION_DESC = "退单扣款";

    private final Object object = new Object();

    @Autowired
    private ProfitSettleErrLsService profitSettleErrLsService;

    @Autowired
    private ProfitDeductionService profitDeductionService;

    @Autowired
    private IdService idService;




//    @Scheduled(cron = "0 0 12 * * ?")
    @Transactional
    public void deal() {
        Map<String, BigDecimal> orgMap = new HashMap<>(10);
        Map<String, String> deductionIdMap = new HashMap<>(10);
        LOG.info("每月定时获取退单数据。");
       JSONObject result = getRefundList();
       if (result.containsKey("info") ) {
           JSONArray array = result.getJSONArray("info");
           try {
               LOG.info("对数据汇总并生成退单明细。");
               insertSettleErrLs(array, orgMap, deductionIdMap);
               LOG.info("对数据汇总并生成扣款信息。");
               if (orgMap.size() > 0) {
                  Set<String> keys = orgMap.keySet();
                  for (String key : keys) {
                      insertProfitDeduction(key, orgMap.get(key), deductionIdMap.get(key));
                  }
               }
           }catch (Exception e) {
               e.printStackTrace();
               LOG.info("退单数据处理失败");
           }


       }
    }

    /***
    * @Description: 获取扣款信息对象
    * @Param:  agentId 机构id
    * @Param:  addsum 新增扣款金额
    * @Param:  id 主键
    * @Author: zhaodw
    * @Date: 2018/7/30
    */
    private void insertProfitDeduction(String agentId, BigDecimal addAmt, String id) {
        ProfitDeduction deduction = new ProfitDeduction();
        deduction.setDeductionType(DeductionType.SETTLE_ERR.getType());
        deduction.setAgentId(agentId);
        PageInfo pageInfo = profitDeductionService.getProfitDeductionList(deduction, null);
        if (pageInfo.getRows() != null && pageInfo.getRows().size()==1) {
            deduction = (ProfitDeduction) pageInfo.getRows().get(0);
        }else{
            deduction.setId(id);
            deduction.setStagingStatus(DeductionStatus.UNREVIEWED.getStatus());
            deduction.setDeductionDesc(DEDUCTION_DESC);
        }
        deduction.setAddDeductionAmt(addAmt);
        deduction.setSumDeductionAmt(deduction.getSumDeductionAmt().add(addAmt));
        deduction.setMustDeductionAmt(deduction.getSumDeductionAmt());
        profitDeductionService.insert(deduction);
    }

    /*** 
    * @Description: 插入退单数据并对相同机构的做合并
    * @Param:   array 退单数据
    * @Param:  orgMap 计算代理商汇总
    * @Author: zhaodw
    * @Date: 2018/7/30 
    */ 
    private void insertSettleErrLs(JSONArray array,  Map<String, BigDecimal> orgMap, Map<String, String> deductionIdMap) {
       array.stream().
               filter(json->(StringUtils.isNotBlank(((JSONObject)json).getString("instId"))) && ((JSONObject)json).getBigDecimal("balanceAmt").intValue() < 0)
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
        settleErrLs.setHostLs(jsonObject.getString("hostLs"));
        settleErrLs.setChargebackDate(jsonObject.getString("chargebackDate"));
        settleErrLs.setTranDate(jsonObject.getString("tranDate"));
        settleErrLs.setMerchId(jsonObject.getString("merchId"));
        settleErrLs.setId(jsonObject.getString("instId"));
        settleErrLs.setTranAmt(jsonObject.getBigDecimal("tranAmt"));
        settleErrLs.setNetAmt(jsonObject.getBigDecimal("netAmt"));
        settleErrLs.setOffsetBalanceAmt(jsonObject.getBigDecimal("offsetBalanceAmt"));
        settleErrLs.setBalanceAmt(jsonObject.getBigDecimal("balanceAmt"));// 剩余未销账金额
        settleErrLs.setBusinessType(jsonObject.getString("businessType"));
        settleErrLs.setCardNo(jsonObject.getString("cardNo"));
        settleErrLs.setErrDate(jsonObject.getString("errDate"));
        settleErrLs.setNettingStatus(jsonObject.getString("nettingStatus"));
        settleErrLs.setErrDesc(jsonObject.getString("errDesc"));
        settleErrLs.setRealDeductAmt(jsonObject.getBigDecimal("shouldDeductAmt"));// 应扣分润
        settleErrLs.setMakeAmt(jsonObject.getBigDecimal("shouldMakeAmt"));//应补分润
        synchronized (object) {
            if (orgMap.containsKey(jsonObject.getString("instId"))) {
                orgMap.put(jsonObject.getString("instId"), orgMap.get(jsonObject.getString("instId")).add(jsonObject.getBigDecimal("shouldDeductAmt")));
            }else {
                orgMap.put(jsonObject.getString("instId"), jsonObject.getBigDecimal("shouldDeductAmt"));
                deductionIdMap.put(jsonObject.getString("instId"),  idService.genId(TabId.P_DEDUCTION));
            }
        }
        settleErrLs.setSourceId(deductionIdMap.get(jsonObject.getString("instId")));
        profitSettleErrLsService.inset(settleErrLs);
    }

    /*** 
    * @Description:  获取退单列表
    * @return:  返回的退单列表
    * @Author: zhaodw 
    * @Date: 2018/7/29 
    */ 
    private JSONObject getRefundList() {
        // 上月的开始及结束日期
        LocalDate date = LocalDate.now().plusMonths(-1);
        String chargebackEnd = date.with(TemporalAdjusters.lastDayOfMonth()).format(DateTimeFormatter.BASIC_ISO_DATE);
        String chargebackStart = date.with(TemporalAdjusters.firstDayOfMonth()).format(DateTimeFormatter.BASIC_ISO_DATE);

        JSONObject param = new JSONObject();
        param.put("chargebackStart", chargebackStart);
        param.put("chargebackEnd", chargebackEnd);
        String result = HttpClientUtil.doPostJson(URL, param.toJSONString());
        if (StringUtils.isNotBlank(result)) {
            return  JSONObject.parseObject(result);
        }
        return null;
    }

}
