package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.FreezeAgentMapper;
import com.ryx.credit.profit.dao.FreezeOperationRecordMapper;
import com.ryx.credit.profit.dao.InvoiceSumMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IInvoiceSumService;
import com.ryx.credit.service.dict.IdService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * chen liang
 * 发票汇总类
 */
@Service("invoiceSumService")
public class InvoiceSumServiceImpl implements IInvoiceSumService {

    Logger logger = LoggerFactory.getLogger(InvoiceSumServiceImpl.class);
    @Autowired
    InvoiceSumMapper invoiceSumMapper;
    @Autowired
    FreezeAgentMapper freezeAgentMapper;
    @Autowired
    FreezeOperationRecordMapper freezeOperationRecordMapper;

    @Autowired
    private IdService idService;

    @Override
    public PageInfo selectByMap(PageInfo pageInfo, Map<String, String> param) {

        InvoiceSumExample invoiceSumExample = new InvoiceSumExample();
        InvoiceSumExample.Criteria criteria = invoiceSumExample.createCriteria();
        if (param.get("agentName") != null && !param.get("agentName").equals("")) {
            criteria.andAgentIdEqualTo(param.get("agentName"));
        }
        if (param.get("agentId") != null && !param.get("agentId").equals("")) {
            criteria.andAgentNameEqualTo(param.get("agentId"));
        }
        if (param.get("topOrgName") != null && !param.get("topOrgName").equals("")) {
            criteria.andTopOrgNameEqualTo(param.get("topOrgName"));
        }
        if (param.get("topOrgId") != null && !param.get("topOrgId").equals("")) {
            criteria.andTopOrgIdEqualTo(param.get("topOrgId"));
        }
        if (param.get("invoiceStatus") != null && !param.get("invoiceStatus").equals("")) {
            criteria.andInvoiceStatusEqualTo(param.get("invoiceStatus"));
        }
        if (param.get("profitMonth") != null && !param.get("profitMonth").equals("")) {
            criteria.andProfitMonthEqualTo(param.get("profitMonth"));
        }
        if (param.get("invoiceCompany") != null && !param.get("invoiceCompany").equals("")) {
            criteria.andInvoiceCompanyEqualTo(param.get("invoiceCompany"));
        }

        List<InvoiceSum> invoiceSums = invoiceSumMapper.selectByExample(invoiceSumExample);
        int count = (int) invoiceSumMapper.countByExample(invoiceSumExample);
        pageInfo.setTotal(count);
        pageInfo.setRows(invoiceSums);
        return pageInfo;
    }

    /**
     * 发票欠款终审通过后调用，
     * @param param
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public Map<String, Object> getInvoiceFinalData(Map<String, Object> param) {
        Map<String, Object> resultMap = new HashMap<>();

        InvoiceSumExample invoiceSumExample = new InvoiceSumExample();
        InvoiceSumExample.Criteria invoiceSumExampleCriteria = invoiceSumExample.createCriteria();
        if (param.get("AGENT_ID") != null && param.get("AGENT_ID") != "") {
            invoiceSumExampleCriteria.andAgentIdEqualTo(param.get("AGENT_ID").toString());
        } else {
            logger.info("获取代理商唯一码失败");
            resultMap.put("returnCode", 0000);
            resultMap.put("returnInfo", "失败");
            return resultMap;
        }
        if (param.get("PROFIT_MONTH") != null && param.get("PROFIT_MONTH") != "") {
            invoiceSumExampleCriteria.andProfitMonthEqualTo(param.get("PROFIT_MONTH").toString());
        } else {
            logger.info("获取月份失败");
            resultMap.put("returnCode", 0000);
            resultMap.put("returnInfo", "失败");
            return resultMap;
        }
        if (param.get("INVOICE_COMPANY") != null && param.get("INVOICE_COMPANY") != "") {
            invoiceSumExampleCriteria.andInvoiceCompanyEqualTo(param.get("INVOICE_COMPANY").toString());
        } else {
            logger.info("获取打款公司失败");
            resultMap.put("returnCode", 0000);
            resultMap.put("returnInfo", "失败");
            return resultMap;
        }
        List<InvoiceSum> invoiceSums = invoiceSumMapper.selectByExample(invoiceSumExample);
        if (invoiceSums.size() != 1) {
            logger.info("查询本月代理商有误");
            resultMap.put("returnCode", 0000);
            resultMap.put("returnInfo", "失败");
            return resultMap;
        }
        InvoiceSum invoiceSum = invoiceSums.get(0);
        BigDecimal surplusAmt = invoiceSum.getOwnInvoice().subtract((BigDecimal) param.get("INVOICE_AMT"));
        try {
            if (surplusAmt.compareTo(BigDecimal.ZERO) != 1) {
                AdjustFreeze(param);
                invoiceSum.setInvoiceStatus("99");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("冻结和解冻代理商失败");
            resultMap.put("returnCode", 0000);
            resultMap.put("returnInfo", "失败");
            return resultMap;
        }

        BigDecimal realityAmt = invoiceSum.getAddInvoiceAmt().add((BigDecimal) param.get("INVOICE_AMT"));
        invoiceSum.setOwnInvoice(surplusAmt);
        invoiceSum.setAddInvoiceAmt(realityAmt);
        try {
            invoiceSumMapper.updateByPrimaryKeySelective(invoiceSum);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("修改发票汇总金额失败");
            resultMap.put("returnCode", 0000);
            resultMap.put("returnInfo", "失败");
            return resultMap;
        }
        resultMap.put("returnCode", 9999);
        resultMap.put("returnInfo", "成功");
        return resultMap;


    }

    /**
     * 代理商欠票小于0时，去除冻结，添加明细解冻；
     * chen liang
     *
     * @param param
     */
    public void AdjustFreeze(Map<String, Object> param) {
        FreezeAgentExample freezeAgentExample = new FreezeAgentExample();
        FreezeAgentExample.Criteria freezeAgentExampleCriteria = freezeAgentExample.createCriteria();
        freezeAgentExampleCriteria.andAgentIdEqualTo(param.get("AGENT_ID").toString());
        freezeAgentExampleCriteria.andFreezeTypeEqualTo("03");
        List<FreezeAgent> freezeAgents = freezeAgentMapper.selectByExample(freezeAgentExample);
        if (freezeAgents.size() > 0) {
            for (FreezeAgent freezeAgent : freezeAgents) {
                freezeAgentMapper.deleteInvoiceAgent(freezeAgent.getId());
                FreezeOperationRecord freezeOperationRecord = new FreezeOperationRecord();
                freezeOperationRecord.setAgentId(freezeAgent.getAgentId());
                freezeOperationRecord.setAgentName(freezeAgent.getAgentName());
               /* freezeOperationRecord.setParentAgentId(freezeAgent.getParentAgentId());
                freezeOperationRecord.setParentAgentName(freezeAgent.getParentAgentName());*/
                freezeOperationRecord.setFreezeType(freezeAgent.getFreezeType());
                freezeOperationRecord.setFreezeBatch(freezeAgent.getOperationBatch());
                freezeOperationRecord.setId(idService.genId(TabId.P_FREEZE_OPERATION_RECORD));
                Date now = new Date();
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                freezeOperationRecord.setOperationTime(f.format(now));
                freezeOperationRecord.setStatus("1");
                freezeOperationRecordMapper.insertSelective(freezeOperationRecord);
            }
        }
    }

    /**
     * 代理商通过调整，使的欠票金额重新大于0时，重新冻结该代理商
     * chen liang
     *
     * @param param
     */
    public void againFreeze(Map<String, Object> param) {
        FreezeAgent freezeAgent = new FreezeAgent();
        freezeAgent.setId(idService.genId(TabId.P_FREEZE_OPERATION_RECORD));
        freezeAgent.setAgentId(param.get("AGENT_ID").toString());
        freezeAgent.setAgentName(param.get("AGENT_NAME").toString());
    /*    freezeAgent.setParentAgentName(param.get("PARENT_AGENT_NAME").toString());
        freezeAgent.setParentAgentId(param.get("PARENT_AGENT_Id").toString());*/
        freezeAgent.setFreezeType("03");   /* 00 月份润 01 日分润 02 日返现 03 欠票冻结*/
        freezeAgent.setStatus("1");/*0.正常(已解冻) 1.已冻结 2.解冻审批中*/
        freezeAgent.setOperationBatch(UUID.randomUUID().toString().replaceAll("-",""));
        freezeAgentMapper.insertSelective(freezeAgent);
    }

    @Override
    public int updateByPrimaryKeySelective(InvoiceSum record) {
        return invoiceSumMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public InvoiceSum selectByPrimaryKey(String id) {
        return invoiceSumMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public void invoicePreLeftAmt(List<List<Object>> param,String profitMonth) throws Exception{
        if (null == param && param.size() == 0) {
            logger.info("导入数据为空");
            throw new MessageException("导入数据为空");
        }
        for (List<Object> invoiceSum : param) {
            if (invoiceSum.size() == 4){
                if(null==invoiceSum.get(0) || "".equals(invoiceSum.get(0))){
                    logger.info("代理商唯一码不能为空");
                    throw new MessageException("代理商唯一码导入有误，请检查");
                }
                if( null==invoiceSum.get(1) || "".equals(invoiceSum.get(1))){
                    logger.info("代理商名称导入有误，请检查");
                    throw new MessageException("代理商名称导入有误，请检查");
                }
                if( null==invoiceSum.get(2) || "".equals(invoiceSum.get(2))){
                    logger.info("开票公司导入有误，请检查");
                    throw new MessageException("开票公司导入有误，请检查");
                }else{
                    try {
                        new BigDecimal(String.valueOf(invoiceSum.get(3)));
                    }catch (Exception e){
                        logger.info("补款金额格式不正确，请检查");
                        throw new MessageException("补款金额格式不正确，请检查");
                    }
                }
            }

        }

        for (List<Object> invoiceSumList : param) {
            if (invoiceSumList.size() == 4){
                InvoiceSumExample invoiceSumExample = new InvoiceSumExample();
                InvoiceSumExample.Criteria criteria = invoiceSumExample.createCriteria();
                criteria.andAgentIdEqualTo(invoiceSumList.get(0).toString());
                criteria.andAgentNameEqualTo(invoiceSumList.get(1).toString());
                criteria.andInvoiceCompanyEqualTo(invoiceSumList.get(2).toString());
                criteria.andProfitMonthEqualTo(profitMonth);
                List<InvoiceSum> invoiceSums = invoiceSumMapper.selectByExample(invoiceSumExample);
                if(invoiceSums.size()!=1){
                    logger.info("查询本月代理商有误");
                    throw new MessageException("查询本月代理商有误");
                }
                InvoiceSum invoiceSum= invoiceSums.get(0);
                invoiceSum.setPreLeftAmt(new BigDecimal(invoiceSumList.get(3).toString()));
                invoiceSum.setOwnInvoice(invoiceSum.getPreLeftAmt().add(invoiceSum.getDayBackAmt()).add(invoiceSum.getDayProfitAmt()).add(invoiceSum.getPreProfitMonthAmt()).subtract(invoiceSum.getAddInvoiceAmt()).add(invoiceSum.getAdjustAmt()));
                invoiceSumMapper.updateByPrimaryKeySelective(invoiceSum);
            }
        }
    }

}