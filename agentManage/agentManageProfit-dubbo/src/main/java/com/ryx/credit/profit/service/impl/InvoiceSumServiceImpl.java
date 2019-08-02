package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.profit.dao.FreezeAgentMapper;
import com.ryx.credit.profit.dao.FreezeOperationRecordMapper;
import com.ryx.credit.profit.dao.InvoiceSumMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IInvoiceSumService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private AgentService agentService;


    @Override
    public PageInfo selectByMap(Page page, Map<String, String> param, Map<String,Object> map) {

        InvoiceSumExample invoiceSumExample = new InvoiceSumExample();
        InvoiceSumExample.Criteria criteria = invoiceSumExample.createCriteria();
        invoiceSumExample.setPage(page);
        if (param.get("agentName") != null && !param.get("agentName").equals("")) {
            criteria.andAgentNameEqualTo(param.get("agentName"));
        }
        if (param.get("agentId") != null && !param.get("agentId").equals("")) {
            criteria.andAgentIdEqualTo(param.get("agentId"));

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
        if(map != null){
            invoiceSumExample.setInnerJoinDepartment(map.get("ORGANIZATIONCODE").toString(), map.get("ORGID").toString());
        }

        List<InvoiceSum> invoiceSums = invoiceSumMapper.selectByExample(invoiceSumExample);
        int count = (int) invoiceSumMapper.countByExample(invoiceSumExample);
        PageInfo pageInfo = new PageInfo();
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
            resultMap.put("returnInfo", "获取代理商唯一码失败");
            return resultMap;
        }
        if (param.get("PROFIT_MONTH") != null && param.get("PROFIT_MONTH") != "") {
            invoiceSumExampleCriteria.andProfitMonthEqualTo(param.get("PROFIT_MONTH").toString());
        } else {
            logger.info("获取月份失败");
            resultMap.put("returnCode", 0000);
            resultMap.put("returnInfo", "获取月份失败");
            return resultMap;
        }
        if (param.get("INVOICE_COMPANY") != null && param.get("INVOICE_COMPANY") != "") {
            invoiceSumExampleCriteria.andInvoiceCompanyEqualTo(param.get("INVOICE_COMPANY").toString());
        } else {
            logger.info("获取打款公司失败");
            resultMap.put("returnCode", 0000);
            resultMap.put("returnInfo", "获取打款公司失败");
            return resultMap;
        }
        List<InvoiceSum> invoiceSums = invoiceSumMapper.selectByExample(invoiceSumExample);
        if (invoiceSums.size() != 1) {
            logger.info("查询本月代理商有误");
            resultMap.put("returnCode", 0000);
            resultMap.put("returnInfo", "查询本月代理商有误");
            return resultMap;
        }
        InvoiceSum invoiceSum = invoiceSums.get(0);
        BigDecimal surplusAmt = invoiceSum.getOwnInvoice().subtract((BigDecimal) param.get("INVOICE_AMT"));
        try {
            if(surplusAmt.compareTo(BigDecimal.ZERO) < 0){
                logger.info("发票金额大于本月欠票数");
                resultMap.put("returnCode", 0000);
                resultMap.put("returnInfo", "发票金额大于本月欠票数");
                return resultMap;
            }
            if (surplusAmt.compareTo(BigDecimal.ZERO) == 0) {
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
     * 代理商欠票等于0时，去除冻结，添加明细解冻；
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
    public void invoicePreLeftAmt(List<List<Object>> param,String profitMonth) throws MessageException{

        if (null == param ||param.size() == 0) {
            logger.info("导入数据为空");
            throw new MessageException("导入数据为空");
        }

        for (List<Object> invoiceSumList : param) {

            if (invoiceSumList.size() ==10){
                if( null==invoiceSumList.get(0) || "".equals(invoiceSumList.get(0))){
                    logger.info("机构编码导入有误，请检查");
                    throw new MessageException("机构编码导入有误，请检查");
                }
                if( null==invoiceSumList.get(1) || "".equals(invoiceSumList.get(1))){
                    logger.info("机构名称导入有误，请检查");
                    throw new MessageException("机构名称导入有误，请检查");
                }
                if( null==invoiceSumList.get(2) || "".equals(invoiceSumList.get(2))){
                    logger.info("代理商唯一码导入有误，请检查");
                    throw new MessageException("代理商唯一码导入有误，请检查");
                }/*else{ //检验唯一码是否准确
                    Agent agent = agentService.getAgentById(invoiceSum.get(2).toString().trim());
                    if(agent == null){
                        throw new MessageException("代理商唯一码:"+invoiceSum.get(2).toString()+"错误，请检查");
                    }
                }*/
                if( null==invoiceSumList.get(3) || "".equals(invoiceSumList.get(3))){
                    logger.info("代理商名称导入有误，请检查");
                    throw new MessageException("代理商名称导入有误，请检查");
                }
                if( null==invoiceSumList.get(4) || "".equals(invoiceSumList.get(4))){
                    logger.info("开票公司导入有误，请检查");
                    throw new MessageException("开票公司导入有误，请检查");
                }

                if( null!=invoiceSumList.get(5) && !"".equals(invoiceSumList.get(5))){
                    try {
                        new BigDecimal(String.valueOf(invoiceSumList.get(5)));
                    }catch (Exception e){
                        logger.info("导入上月欠票基数格式不正确，请检查");
                        throw new MessageException("导入上月欠票基数不正确，请检查");
                    }
                }
                if( null==invoiceSumList.get(6) || "".equals(invoiceSumList.get(6))){
                    logger.info("导入本月日返现有误，请检查");
                    throw new MessageException("导入本月日返现有误，请检查");
                }else{
                    try {
                        new BigDecimal(String.valueOf(invoiceSumList.get(6)));
                    }catch (Exception e){
                        logger.info("导入本月日返现格式不正确，请检查");
                        throw new MessageException("导入本月日返现格式不正确，请检查");
                    }
                }

                if( null==invoiceSumList.get(7) || "".equals(invoiceSumList.get(7))){
                    logger.info("导入本月日分润有误，请检查");
                    throw new MessageException("导入本月日分润有误，请检查");
                }else{
                    try {
                        new BigDecimal(String.valueOf(invoiceSumList.get(7)));
                    }catch (Exception e){
                        logger.info("本月日分润格式不正确，请检查");
                        throw new MessageException("本月日分润格式不正确，请检查");
                    }
                }
                if( null==invoiceSumList.get(8) || "".equals(invoiceSumList.get(8))){
                    logger.info("导入上月月份润有误，请检查");
                    throw new MessageException("导入上月月份润有误，请检查");
                }else{
                    try {
                        new BigDecimal(String.valueOf(invoiceSumList.get(8)));
                    }catch (Exception e){
                        logger.info("上月月份润格式不正确，请检查");
                        throw new MessageException("上月月份润格式不正确，请检查");
                    }
                }

                if( null==invoiceSumList.get(9) || "".equals(invoiceSumList.get(9))){
                    logger.info("导入代下级开票有误，请检查");
                    throw new MessageException("导入代下级开票有误，请检查");
                }else{
                    try {
                        new BigDecimal(String.valueOf(invoiceSumList.get(9)));
                    }catch (Exception e){
                        logger.info("代下级开票格式不正确，请检查");
                        throw new MessageException("代下级开票格式不正确，请检查");
                    }
                }


                Agent agent = agentService.getAgentById(invoiceSumList.get(2).toString());
                if (agent == null) {
                    logger.info("代理商ID" + invoiceSumList.get(2).toString() + "不存在");
                    throw new MessageException("代理商ID" + invoiceSumList.get(2).toString() + "不存在");
                }
                if (!agent.getAgName().equals(invoiceSumList.get(3).toString())) {
                    logger.info("代理商名称" + invoiceSumList.get(3).toString() + "与ID不匹配");
                    throw new MessageException("代理商名称" + invoiceSumList.get(3).toString() + "与ID不匹配");
                }
                int number =0;
                for (List<Object> invoiceSum1 : param) {
                    if (invoiceSumList.get(1).toString().equals(invoiceSum1.get(1).toString())) {
                        if (invoiceSumList.get(4).toString().equals(invoiceSum1.get(4).toString())) {
                            number++;
                        }
                    }
                    if (number > 1) {
                        throw new MessageException("本次导入开票公司名称"+invoiceSum1.get(4).toString() +"重复，导入失败");
                    }
                }


                if (invoiceSumList.size() == 10) {
                    InvoiceSum invoiceSum = new InvoiceSum();
                    //判断本月是否再次传入，表中数据。
                    invoiceSum.setAgentId(invoiceSumList.get(2).toString().trim());
                    invoiceSum.setAgentName(invoiceSumList.get(3).toString().trim());
                    invoiceSum.setInvoiceCompany(invoiceSumList.get(4).toString().trim());
                    invoiceSum.setProfitMonth(profitMonth);
                    List<InvoiceSum> invoiceSum2 = getSumInvoice(invoiceSum);
               /* //判断是否上次导入中已经导入数据
                InvoiceSum invoiceSumA = new InvoiceSum();
                invoiceSumA.setInvoiceCompany(invoiceSumList.get(2).toString().trim());
                invoiceSum.setProfitMonth(profitMonth);
                List<InvoiceSum> invoiceSumA1 = getSumInvoice(invoiceSum);
                for (InvoiceSum invoice:invoiceSumA1) {
                    invoice.
                }*/
                    //第一次导入
                    if(null==invoiceSum2 || invoiceSum2.size()==0){
                        invoiceSum.setTopOrgId(invoiceSumList.get(0).toString().trim());
                        invoiceSum.setTopOrgName(invoiceSumList.get(1).toString().trim());
                        invoiceSum.setDayBackAmt(new BigDecimal(invoiceSumList.get(6).toString().trim()));
                        invoiceSum.setDayProfitAmt(new BigDecimal(invoiceSumList.get(7).toString().trim()));
                        invoiceSum.setPreProfitMonthAmt(new BigDecimal(invoiceSumList.get(8).toString().trim()));
                        invoiceSum.setSubAddInvoiceAmt(new BigDecimal(invoiceSumList.get(9).toString().trim()));
                        invoiceSum.setAddInvoiceAmt(BigDecimal.ZERO);
                        invoiceSum.setAdjustAmt(BigDecimal.ZERO);
                        invoiceSum.setInvoiceStatus("00");

                        //查上月剩余欠票基数


                        String nextProMonth="";
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                            Date date = sdf.parse(profitMonth);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            cal.add(Calendar.MONTH, -1);
                            nextProMonth=sdf.format(cal.getTime());
                        } catch (ParseException e) {
                            throw new MessageException("月份输入不合理");
                        }

                       /* String lastSettleMonth = LocalDate.now().plusMonths(-2).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);*/
                        InvoiceSumExample invoiceSumExample = new InvoiceSumExample();
                        InvoiceSumExample.Criteria criteria = invoiceSumExample.createCriteria();
                        criteria.andProfitMonthEqualTo(nextProMonth);
                        criteria.andInvoiceCompanyEqualTo(invoiceSum.getInvoiceCompany());
                        criteria.andAgentIdEqualTo(invoiceSum.getAgentId());
                        List<InvoiceSum> invoiceSums = invoiceSumMapper.selectByExample(invoiceSumExample);

                        InvoiceSum invoiceSum1=new InvoiceSum();
                        if(null!=invoiceSums && invoiceSums.size() ==1){
                            invoiceSum1 = invoiceSums.get(0);
                        }else if(invoiceSums.size() > 1){
                            throw new MessageException("同一代理商同一打款公司下同一月份含有相同代理商！");
                        }else if(invoiceSums.size()==0){
                            InvoiceSum invoiceSum3 = new InvoiceSum();
                            if( null!=invoiceSumList.get(5) && !"".equals(invoiceSumList.get(5))){
                                try {
                                    new BigDecimal(String.valueOf(invoiceSumList.get(5)));
                                }catch (Exception e){
                                    logger.info("导入上月欠票基数格式不正确，请检查");
                                    throw new MessageException("导入上月欠票基数不正确，请检查");
                                }
                                invoiceSum3.setPreLeftAmt( new BigDecimal(String.valueOf(invoiceSumList.get(5))));
                            }else{
                                invoiceSum3.setPreLeftAmt(BigDecimal.ZERO);
                            }
                            invoiceSum3.setDayBackAmt(new BigDecimal(invoiceSumList.get(6).toString().trim()));
                            invoiceSum3.setDayProfitAmt(new BigDecimal(invoiceSumList.get(7).toString().trim()));
                            invoiceSum3.setPreProfitMonthAmt(new BigDecimal(invoiceSumList.get(8).toString().trim()));
                            invoiceSum3.setSubAddInvoiceAmt(new BigDecimal(invoiceSumList.get(9).toString().trim()));
                            invoiceSum3.setTopOrgId(invoiceSumList.get(0).toString().trim());
                            invoiceSum3.setTopOrgName(invoiceSumList.get(1).toString().trim());
                            invoiceSum3.setAgentId(invoiceSumList.get(2).toString().trim());
                            invoiceSum3.setAgentName(invoiceSumList.get(3).toString().trim());
                            invoiceSum3.setInvoiceCompany(invoiceSumList.get(4).toString().trim());
                            invoiceSum3.setId(idService.genId(TabId.P_INVOICE_SUM));
                            invoiceSum3.setProfitMonth(profitMonth);
                            invoiceSum3.setInvoiceStatus("00");
                            invoiceSum3.setAddInvoiceAmt(BigDecimal.ZERO);
                            invoiceSum3.setAdjustAmt(BigDecimal.ZERO);
                            //invoiceSum.setOwnInvoice((invoiceSum2.get(0).getPreLeftAmt()).add(new BigDecimal(invoiceSumList.get(6).toString())).add(invoiceSum.getDayBackAmt()).add(invoiceSum.getDayProfitAmt()).add(invoiceSum.getPreProfitMonthAmt()).subtract(invoiceSum.getAddInvoiceAmt()==null?BigDecimal.ZERO:invoiceSum.getAddInvoiceAmt()).add(invoiceSum.getAdjustAmt()==null?BigDecimal.ZERO:invoiceSum.getAdjustAmt()));
                            invoiceSum3.setOwnInvoice((invoiceSum3.getPreLeftAmt()==null?BigDecimal.ZERO:invoiceSum3.getPreLeftAmt()).add(invoiceSum3.getSubAddInvoiceAmt()).add(invoiceSum3.getDayBackAmt()).add(invoiceSum3.getDayProfitAmt()).add(invoiceSum3.getPreProfitMonthAmt()).subtract(invoiceSum3.getAddInvoiceAmt()==null?BigDecimal.ZERO:invoiceSum3.getAddInvoiceAmt()).add(invoiceSum3.getAdjustAmt()==null?BigDecimal.ZERO:invoiceSum3.getAdjustAmt()));
                            invoiceSumMapper.insertSelective(invoiceSum3);
                           continue;
                        }
                        if (null == invoiceSumList.get(5) || "".equals(invoiceSumList.get(5))) {
                            invoiceSum.setPreLeftAmt(invoiceSum1.getOwnInvoice());
                        }else if (null != invoiceSumList.get(5) && !"".equals(invoiceSumList.get(5))) {
                            invoiceSum.setPreLeftAmt(new BigDecimal(invoiceSumList.get(5).toString()));
                        }
                        invoiceSum.setOwnInvoice((invoiceSum.getPreLeftAmt()==null?BigDecimal.ZERO:invoiceSum.getPreLeftAmt()).add(invoiceSum.getSubAddInvoiceAmt()).add(invoiceSum.getDayBackAmt()).add(invoiceSum.getDayProfitAmt()).add(invoiceSum.getPreProfitMonthAmt()).subtract(invoiceSum.getAddInvoiceAmt()==null?BigDecimal.ZERO:invoiceSum.getAddInvoiceAmt()).add(invoiceSum.getAdjustAmt()==null?BigDecimal.ZERO:invoiceSum.getAdjustAmt()));
                        invoiceSum.setId(idService.genId(TabId.P_INVOICE_SUM));

                        invoiceSumMapper.insertSelective(invoiceSum);
                        //重复导入
                    }else{
                        invoiceSum2.get(0).setTopOrgId(invoiceSumList.get(0).toString().trim());
                        invoiceSum2.get(0).setTopOrgName(invoiceSumList.get(1).toString().trim());
                        //查上月剩余欠票基数
                        String nextProMonth="";
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                            Date date = sdf.parse(profitMonth);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(date);
                            cal.add(Calendar.MONTH, -1);
                            nextProMonth=sdf.format(cal.getTime());
                        } catch (ParseException e) {
                            throw new MessageException("月份输入不合理");
                        }
                       /* String lastSettleMonth = LocalDate.now().plusMonths(-2).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);*/
                        InvoiceSumExample invoiceSumExample = new InvoiceSumExample();
                        InvoiceSumExample.Criteria criteria = invoiceSumExample.createCriteria();
                        criteria.andProfitMonthEqualTo(nextProMonth);
                        criteria.andInvoiceCompanyEqualTo(invoiceSum.getInvoiceCompany());
                        criteria.andAgentIdEqualTo(invoiceSum.getAgentId());
                        List<InvoiceSum> invoiceSums = invoiceSumMapper.selectByExample(invoiceSumExample);

                        InvoiceSum invoiceSum1=new InvoiceSum();
                        if(null!=invoiceSums && invoiceSums.size() ==1){
                            invoiceSum1 = invoiceSums.get(0);
                        }else if(invoiceSums.size() > 1){
                            throw new MessageException("同一代理商同一打款公司下同一月份含有相同代理商！");
                        }else if(invoiceSums.size()==0){
                            InvoiceSum invoiceSum3 = new InvoiceSum();
                            if( null!=invoiceSumList.get(5) && !"".equals(invoiceSumList.get(5))){
                                try {
                                    new BigDecimal(String.valueOf(invoiceSumList.get(5)));
                                }catch (Exception e){
                                    logger.info("导入上月欠票基数格式不正确，请检查");
                                    throw new MessageException("导入上月欠票基数不正确，请检查");
                                }
                                invoiceSum3.setPreLeftAmt( new BigDecimal(String.valueOf(invoiceSumList.get(5))));
                            }else{
                                invoiceSum3.setPreLeftAmt(BigDecimal.ZERO);
                            }
                            invoiceSum3.setDayBackAmt(new BigDecimal(invoiceSumList.get(6).toString().trim()));
                            invoiceSum3.setDayProfitAmt(new BigDecimal(invoiceSumList.get(7).toString().trim()));
                            invoiceSum3.setPreProfitMonthAmt(new BigDecimal(invoiceSumList.get(8).toString().trim()));
                            invoiceSum3.setSubAddInvoiceAmt(new BigDecimal(invoiceSumList.get(9).toString().trim()));
                            invoiceSum3.setTopOrgId(invoiceSumList.get(0).toString().trim());
                            invoiceSum3.setTopOrgName(invoiceSumList.get(1).toString().trim());
                            invoiceSum3.setAgentId(invoiceSumList.get(2).toString().trim());
                            invoiceSum3.setAgentName(invoiceSumList.get(3).toString().trim());
                            invoiceSum3.setInvoiceCompany(invoiceSumList.get(4).toString().trim());
                            invoiceSum3.setId(invoiceSum2.get(0).getId());
                            invoiceSum3.setProfitMonth(profitMonth);
                            invoiceSum3.setInvoiceStatus("00");
                            invoiceSum3.setAddInvoiceAmt(BigDecimal.ZERO);
                            invoiceSum3.setAdjustAmt(BigDecimal.ZERO);
                            invoiceSum3.setOwnInvoice((invoiceSum3.getPreLeftAmt()==null?BigDecimal.ZERO:invoiceSum3.getPreLeftAmt()).add(invoiceSum3.getSubAddInvoiceAmt()).add(invoiceSum3.getDayBackAmt()).add(invoiceSum3.getDayProfitAmt()).add(invoiceSum3.getPreProfitMonthAmt()).subtract(invoiceSum3.getAddInvoiceAmt()==null?BigDecimal.ZERO:invoiceSum3.getAddInvoiceAmt()).add(invoiceSum3.getAdjustAmt()==null?BigDecimal.ZERO:invoiceSum3.getAdjustAmt()));
                            //invoiceSum.setOwnInvoice((invoiceSum2.get(0).getPreLeftAmt()).add(new BigDecimal(invoiceSumList.get(6).toString())).add(invoiceSum.getDayBackAmt()).add(invoiceSum.getDayProfitAmt()).add(invoiceSum.getPreProfitMonthAmt()).subtract(invoiceSum.getAddInvoiceAmt()==null?BigDecimal.ZERO:invoiceSum.getAddInvoiceAmt()).add(invoiceSum.getAdjustAmt()==null?BigDecimal.ZERO:invoiceSum.getAdjustAmt()));
                            invoiceSumMapper.updateByPrimaryKeySelective(invoiceSum3);
                            continue;
                        }
                        if (null == invoiceSumList.get(5) || "".equals(invoiceSumList.get(5))) {
                            invoiceSum2.get(0).setPreLeftAmt(invoiceSum1.getOwnInvoice());
                        }else if (null != invoiceSumList.get(5) && !"".equals(invoiceSumList.get(5))) {
                            invoiceSum2.get(0).setPreLeftAmt(new BigDecimal(invoiceSumList.get(5).toString()));
                        }
                        invoiceSum2.get(0).setDayBackAmt(new BigDecimal(invoiceSumList.get(6).toString().trim()));
                        invoiceSum2.get(0).setDayProfitAmt(new BigDecimal(invoiceSumList.get(7).toString().trim()));
                        invoiceSum2.get(0).setPreProfitMonthAmt(new BigDecimal(invoiceSumList.get(8).toString().trim()));
                        invoiceSum2.get(0).setSubAddInvoiceAmt(new BigDecimal(invoiceSumList.get(9).toString().trim()));
                        if(invoiceSums.size()!=0){
                            invoiceSum2.get(0).setOwnInvoice(invoiceSum2.get(0).getPreLeftAmt().add(invoiceSum2.get(0).getSubAddInvoiceAmt()).add(invoiceSum2.get(0).getDayBackAmt()).add(invoiceSum2.get(0).getDayProfitAmt()).add(invoiceSum2.get(0).getPreProfitMonthAmt()).subtract(invoiceSum2.get(0).getAddInvoiceAmt()==null?BigDecimal.ZERO:invoiceSum2.get(0).getAddInvoiceAmt()).add(invoiceSum2.get(0).getAdjustAmt()==null?BigDecimal.ZERO:invoiceSum2.get(0).getAdjustAmt()));
                        }

                        invoiceSumMapper.updateByPrimaryKeySelective(invoiceSum2.get(0));
                    }

                }
            }else{
                throw new MessageException("excel中存在不合理数据！");
            }
        }



          /*
                if(invoiceSum2 == null){
                    invoiceSum.setId(idService.genId(TabId.P_INVOICE_SUM));
                    invoiceSum.setPreLeftAmt(new BigDecimal(invoiceSumList.get(5).toString()));
                    invoiceSum.setDayBackAmt(new BigDecimal(invoiceSumList.get(6).toString()));
                    invoiceSum.setDayProfitAmt(new BigDecimal(invoiceSumList.get(7).toString()));
                    invoiceSum.setPreProfitMonthAmt(new BigDecimal(invoiceSumList.get(8).toString()));
                    invoiceSum.setSubAddInvoiceAmt(new BigDecimal(invoiceSumList.get(9).toString()));
                    invoiceSum.setAddInvoiceAmt(BigDecimal.ZERO);
                    invoiceSum.setAdjustAmt(BigDecimal.ZERO);
                    invoiceSum.setInvoiceStatus("00");
                    invoiceSum.setOwnInvoice(invoiceSum.getPreLeftAmt().add(invoiceSum.getDayBackAmt()).add(invoiceSum.getDayProfitAmt()).add(invoiceSum.getPreProfitMonthAmt()).add(invoiceSum.getSubAddInvoiceAmt()).subtract(invoiceSum.getAddInvoiceAmt()).add(invoiceSum.getAdjustAmt()));
                    invoiceSumMapper.insertSelective(invoiceSum);
                }else{
                    invoiceSum2.setPreLeftAmt(new BigDecimal(invoiceSumList.get(5).toString()));
                    invoiceSum2.setDayBackAmt(new BigDecimal(invoiceSumList.get(6).toString()));
                    invoiceSum2.setDayProfitAmt(new BigDecimal(invoiceSumList.get(7).toString()));
                    invoiceSum2.setPreProfitMonthAmt(new BigDecimal(invoiceSumList.get(8).toString()));
                    invoiceSum2.setSubAddInvoiceAmt(new BigDecimal(invoiceSumList.get(9).toString()));
                    invoiceSum2.setInvoiceStatus("00");
                    invoiceSum2.setOwnInvoice(invoiceSum2.getPreLeftAmt().add(invoiceSum2.getDayBackAmt()).add(invoiceSum2.getDayProfitAmt()).add(invoiceSum2.getPreProfitMonthAmt()).add(invoiceSum2.getSubAddInvoiceAmt()).subtract(invoiceSum2.getAddInvoiceAmt()).add(invoiceSum2.getAdjustAmt()));
                    invoiceSumMapper.updateByPrimaryKeySelective(invoiceSum2);
                }*/

    }

    private List<InvoiceSum> getSumInvoice(InvoiceSum invoiceSum){
        InvoiceSumExample example = new InvoiceSumExample();
        InvoiceSumExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(invoiceSum.getProfitMonth())){
            criteria.andProfitMonthEqualTo(invoiceSum.getProfitMonth());
        }
        if(StringUtils.isNotBlank(invoiceSum.getAgentId())){
            criteria.andAgentIdEqualTo(invoiceSum.getAgentId());
        }
        if(StringUtils.isNotBlank(invoiceSum.getAgentName())){
            criteria.andAgentNameEqualTo(invoiceSum.getAgentName());
        }
        if(StringUtils.isNotBlank(invoiceSum.getInvoiceCompany())){
            criteria.andInvoiceCompanyEqualTo(invoiceSum.getInvoiceCompany());
        }
        if(StringUtils.isNotBlank(invoiceSum.getProfitMonth())){
            criteria.andProfitMonthEqualTo(invoiceSum.getProfitMonth());
        }


       return  invoiceSumMapper.selectByExample(example);

    }

}