package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.PaySign;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.profit.dao.ProfitDeductionMapper;
import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeductionExample;
import com.ryx.credit.profit.service.IContributionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("contributionsServiceImpl")
public class ContributionsServiceImpl implements IContributionsService {

    Logger logger = LoggerFactory.getLogger(ContributionsServiceImpl.class);

    @Autowired
    private ProfitDeductionMapper profitDeductionMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private IPaymentDetailService iPaymentDetailService;

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public List<Map<String, Object>> batchInsertDeduction(List<Map<String, Object>> list, String deductionDate) throws ProcessException {

        if(list != null && !list.isEmpty()){
            List<Map<String,Object>> succList = new ArrayList<Map<String,Object>>(list.size());
            list.forEach((Map<String,Object> map) -> {

                if(map.get("ID") != null){
                    try{
                        ProfitDeductionExample profitDeductionExample = new ProfitDeductionExample();
                        ProfitDeductionExample.Criteria criteria = profitDeductionExample.createCriteria();
                        criteria.andSourceIdEqualTo(map.get("ID").toString());
                        criteria.andAgentPidEqualTo(map.get("AGENT_ID").toString());
                        criteria.andDeductionDateEqualTo(deductionDate);
                        criteria.andParentAgentPidEqualTo(map.get("GUARANTEE_AGENT") == null ? "" : map.get("GUARANTEE_AGENT").toString());
                        int count = profitDeductionMapper.countByExample(profitDeductionExample);
                        //表示数据不存在
                        if(count == 0){

                            ProfitDeduction profitDeduction = new ProfitDeduction();
                            profitDeduction.setId(idService.genId(TabId.P_DEDUCTION));
                            profitDeduction.setParentAgentId(map.get("GUARANTEE_AGENT") == null ? "" : map.get("GUARANTEE_AGENT").toString());
                            profitDeduction.setParentAgentPid(map.get("GUARANTEE_AGENT") == null ? "" : map.get("GUARANTEE_AGENT").toString());
                            profitDeduction.setAgentId(map.get("AGENT_ID") == null ? "" : map.get("AGENT_ID").toString());
                            profitDeduction.setAgentPid(map.get("AGENT_ID") == null ? "" : map.get("AGENT_ID").toString());
                            profitDeduction.setAgentName(map.get("AG_NAME") == null ? "" : map.get("AG_NAME").toString());
                            profitDeduction.setDeductionDate(deductionDate);
                            profitDeduction.setDeductionType(DeductionType.CONTRIBUTIONS.getType());
                            profitDeduction.setSumDeductionAmt(map.get("PAY_AMOUNT") == null ? BigDecimal.ZERO : new BigDecimal(map.get("PAY_AMOUNT").toString()));
                            profitDeduction.setAddDeductionAmt(map.get("PAY_AMOUNT") == null ? BigDecimal.ZERO : new BigDecimal(map.get("PAY_AMOUNT").toString()));
                            profitDeduction.setMustDeductionAmt(map.get("PAY_AMOUNT") == null ? BigDecimal.ZERO : new BigDecimal(map.get("PAY_AMOUNT").toString()));
                            profitDeduction.setActualDeductionAmt(BigDecimal.ZERO);
                            profitDeduction.setNotDeductionAmt(BigDecimal.ZERO);
                            profitDeduction.setSourceId(map.get("ID").toString());
                            profitDeduction.setUpperNotDeductionAmt(BigDecimal.ZERO);//上月未扣足
                            profitDeduction.setStagingStatus(DeductionStatus.NOT_APPLIED.getStatus());//分期状态
                            profitDeduction.setCreateDateTime(new Date());
                            profitDeduction.setRemark("缴纳款");

                            profitDeductionMapper.insertSelective(profitDeduction);

                            Map<String, Object> successMap = new HashMap<String, Object>(2);
                            successMap.put("detailId", map.get("ID"));
                            successMap.put("srcId", profitDeduction.getId());

                            succList.add(successMap);
                        }
                    }catch (Exception e){
                        logger.error("，分期流水初始化缴纳款分期失败号:{}",map.get("ID").toString());
                        e.printStackTrace();
                        throw new ProcessException("缴纳款数据初始化失败");
                    }
                }
            });

            logger.info("通知订单系统，缴纳款付款中...", JSONObject.toJSON(succList));
            try{
                if(succList.size() > 0){
                    //通知订单系统，订单付款中...
                    iPaymentDetailService.uploadStatus(succList, PaySign.FKING.code);
                }
            }catch (Exception e){
                e.printStackTrace();
                throw  new ProcessException("通知订单系统更新付款状态异常！");
            }
            return succList;
        }
        return null;
    }
}
