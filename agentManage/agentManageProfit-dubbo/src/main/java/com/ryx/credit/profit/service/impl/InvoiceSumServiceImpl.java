package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.FreeType;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.vo.AgentFreezePort;
import com.ryx.credit.profit.dao.FreezeAgentMapper;
import com.ryx.credit.profit.dao.FreezeOperationRecordMapper;
import com.ryx.credit.profit.dao.InvoiceSumMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IInvoiceSumService;
import com.ryx.credit.service.agent.AgentFreezeService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
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
    @Autowired
    private AgentFreezeService agentFreezeService;


    /**
     * 获取数据列表
     * @param page
     * @param param
     * @param map
     * @return
     */
    @Override
    public PageInfo selectByMap(Page page, Map<String, String> param, Map<String,Object> map) {
        if(map != null){
            String deft = map.get("ORGANIZATIONCODE").toString();
            if(deft.endsWith("city")){
                param.put("docPro",map.get("ORGID").toString()); //市
            }else if((deft.startsWith("region") && !deft.endsWith("city")) || deft.equals("beijing")){
                param.put("docDis",map.get("ORGID").toString()); // 区
            }
        }
        PageInfo pageInfo = new PageInfo();
        List<Map<String,Object>> list = invoiceSumMapper.getListByMap(page,param);
        int count = invoiceSumMapper.getCountByMap(param);
        pageInfo.setTotal(count);
        pageInfo.setRows(list);
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
            resultMap.put("returnInfo", "查询本月代理商欠票信息有误！");
            return resultMap;
        }
        InvoiceSum invoiceSum = invoiceSums.get(0);
        BigDecimal surplusAmt = invoiceSum.getOwnInvoice().subtract((BigDecimal) param.get("INVOICE_AMT"));
        try {
            if(surplusAmt.compareTo(BigDecimal.ZERO) < 0){
                if( surplusAmt.add(new BigDecimal("9999")).compareTo(BigDecimal.ZERO) < 0){
                    logger.info("发票金额超过本月欠票数"+surplusAmt+"元");
                    resultMap.put("returnCode", 0000);
                    resultMap.put("returnInfo", "发票金额超过本月欠票数"+surplusAmt+"元");
                    return resultMap;
                }
            }
            if (surplusAmt.compareTo(BigDecimal.ZERO) <= 0) {
                //invocationIntUnFreeze(invoiceSum.getAgentId(),param.get("user").toString(),"到票解冻");
                invoiceSum.setInvoiceStatus("99");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("冻结和解冻代理商失败");
            resultMap.put("returnCode", 0000);
            resultMap.put("returnInfo", "冻结和解冻代理商失败");
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
            resultMap.put("returnInfo", "修改发票汇总金额失败");
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
        freezeAgent.setId(idService.genId(TabId.P_FREEZE_AGENT));
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
    public void invoicePreLeftAmt(List<List<Object>> param,String profitMonth,String userId) throws MessageException{
        try{
            if (null == param || param.size() == 0) {
                throw new MessageException("导入数据为空");
            }
            for (List<Object> invoiceSumList : param) {
                if (invoiceSumList.size() ==10){
                    if( null==invoiceSumList.get(0) || "".equals(invoiceSumList.get(0))){
                        throw new MessageException("机构编码导入有误，请检查");
                    }
                    if( null==invoiceSumList.get(1) || "".equals(invoiceSumList.get(1))){
                        throw new MessageException("机构名称导入有误，请检查");
                    }
                    if( null==invoiceSumList.get(2) || "".equals(invoiceSumList.get(2))){
                        throw new MessageException("代理商唯一码导入有误，请检查");
                    }
                    if( null==invoiceSumList.get(3) || "".equals(invoiceSumList.get(3))){
                        throw new MessageException("代理商名称导入有误，请检查");
                    }
                    if( null==invoiceSumList.get(4) || "".equals(invoiceSumList.get(4))){
                        throw new MessageException("开票公司导入有误，请检查");
                    }
                    if( null!=invoiceSumList.get(5) && !"".equals(invoiceSumList.get(5))){
                        try {
                            new BigDecimal(invoiceSumList.get(5).toString().trim());
                        }catch (Exception e){
                            throw new MessageException("导入上月欠票基数不正确，请检查");
                        }
                    }
                    if( null==invoiceSumList.get(6) || "".equals(invoiceSumList.get(6))){
                        throw new MessageException("导入本月日返现有误，请检查");
                    }else{
                        try {
                            new BigDecimal(invoiceSumList.get(6).toString().trim());
                        }catch (Exception e){
                            throw new MessageException("导入本月日返现格式不正确，请检查");
                        }
                    }
                    if( null==invoiceSumList.get(7) || "".equals(invoiceSumList.get(7))){
                        throw new MessageException("导入本月日分润有误，请检查");
                    }else{
                        try {
                            new BigDecimal(invoiceSumList.get(7).toString().trim());
                        }catch (Exception e){
                            throw new MessageException("本月日分润格式不正确，请检查");
                        }
                    }
                    if( null==invoiceSumList.get(8) || "".equals(invoiceSumList.get(8))){
                        throw new MessageException("导入上月月份润有误，请检查");
                    }else{
                        try {
                            new BigDecimal(invoiceSumList.get(8).toString().trim());
                        }catch (Exception e){
                            throw new MessageException("上月月份润格式不正确，请检查");
                        }
                    }
                    if( null==invoiceSumList.get(9) || "".equals(invoiceSumList.get(9))){
                        throw new MessageException("导入代下级开票有误，请检查");
                    }else{
                        try {
                            new BigDecimal(invoiceSumList.get(9).toString().trim());
                        }catch (Exception e){
                            throw new MessageException("代下级开票格式不正确，请检查");
                        }
                    }
                    //判断代理商唯一码准确性
                    Agent agent = agentService.getAgentById(invoiceSumList.get(2).toString());
                    if (agent == null) {
                        throw new MessageException("代理商AG码" + invoiceSumList.get(2).toString() + "不存在");
                    }
                    if (!agent.getAgName().equals(invoiceSumList.get(3).toString().trim())) {
                        throw new MessageException("代理商名称" + invoiceSumList.get(3).toString() + "与AG码不匹配");
                    }
                    // 判断是否重复导入
                    int number = 0;
                    for (List<Object> invoiceSum1 : param) {
                        if (invoiceSumList.get(2).toString().equals(invoiceSum1.get(2).toString())) {
                            if (invoiceSumList.get(4).toString().equals(invoiceSum1.get(4).toString())) {
                                number ++;
                            }
                            if(number > 1){
                                throw new MessageException("导入数据：代理商"+invoiceSumList.get(2).toString()+",开票公司为"+invoiceSumList.get(4).toString()+"数据重复导入！");
                            }
                        }
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                    Date date = sdf.parse(profitMonth);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    cal.add(Calendar.MONTH, -1);
                    String  nextProMonth=sdf.format(cal.getTime());

                    // 顶级结构数值读取时可能会存在读取格式问题
                    String topOrgId = invoiceSumList.get(0).toString().trim();
                    if(topOrgId.endsWith(".00")){
                        topOrgId = topOrgId.substring(0,topOrgId.indexOf("."));
                    }

                    InvoiceSum invoiceSum = new InvoiceSum();
                    //判断本月是否再次传入，表中数据。
                    invoiceSum.setAgentId(invoiceSumList.get(2).toString().trim());
                    invoiceSum.setAgentName(invoiceSumList.get(3).toString().trim());
                    invoiceSum.setInvoiceCompany(invoiceSumList.get(4).toString().trim());
                    invoiceSum.setProfitMonth(profitMonth);
                    invoiceSum.setTopOrgId(topOrgId);
                    invoiceSum.setTopOrgName(invoiceSumList.get(1).toString().trim());
                    invoiceSum.setDayBackAmt(new BigDecimal(invoiceSumList.get(6).toString()));
                    invoiceSum.setDayProfitAmt(new BigDecimal(invoiceSumList.get(7).toString().trim()));
                    invoiceSum.setPreProfitMonthAmt(new BigDecimal(invoiceSumList.get(8).toString().trim()));
                    invoiceSum.setSubAddInvoiceAmt(new BigDecimal(invoiceSumList.get(9).toString().trim()));

                    // 获取上月剩余欠票基数
                    InvoiceSumExample invoiceSumExample = new InvoiceSumExample();
                    InvoiceSumExample.Criteria criteria = invoiceSumExample.createCriteria();
                    criteria.andProfitMonthEqualTo(nextProMonth);
                    criteria.andInvoiceCompanyEqualTo(invoiceSum.getInvoiceCompany());
                    criteria.andAgentIdEqualTo(invoiceSum.getAgentId());
                    List<InvoiceSum> invoiceSums = invoiceSumMapper.selectByExample(invoiceSumExample);
                    if(null!=invoiceSums && invoiceSums.size() ==1){
                        if("" == invoiceSumList.get(5) || null == invoiceSumList.get(5)){
                            invoiceSum.setPreLeftAmt(invoiceSums.get(0).getOwnInvoice());
                        }else {
                            invoiceSum.setPreLeftAmt(new BigDecimal(invoiceSumList.get(5).toString()));
                        }
                    }else if(invoiceSums.size() > 1){
                        throw new MessageException(""+invoiceSum.getAgentId()+"-"+invoiceSum.getInvoiceCompany()+"-"
                                +nextProMonth+"查询上月欠票基数时获取到"+invoiceSums.size()+"条数据，导致代理商欠票计算失败！");
                    }else if(invoiceSums.size()==0){
                        if("" == invoiceSumList.get(5) || null == invoiceSumList.get(5)){
                            invoiceSum.setPreLeftAmt(BigDecimal.ZERO);
                        }else {
                            invoiceSum.setPreLeftAmt(new BigDecimal(invoiceSumList.get(5).toString()));
                        }
                    }
                    List<InvoiceSum> invoiceSum2 = getSumInvoice(invoiceSum);
                    // 第一次导入
                    if(null==invoiceSum2 || invoiceSum2.size()==0){
                        invoiceSum.setId(idService.genId(TabId.P_INVOICE_SUM));
                        invoiceSum.setAddInvoiceAmt(BigDecimal.ZERO);
                        invoiceSum.setAdjustAmt(BigDecimal.ZERO);
                        invoiceSum.setOwnInvoice(
                                invoiceSum.getPreLeftAmt().
                                        add(invoiceSum.getSubAddInvoiceAmt())
                                        .add(invoiceSum.getDayBackAmt()
                                        .add(invoiceSum.getDayProfitAmt())
                                        .add(invoiceSum.getPreProfitMonthAmt())
                                        .add(invoiceSum.getAdjustAmt())
                                        .subtract(invoiceSum.getAddInvoiceAmt())
                                        ));
                        if(invoiceSum.getOwnInvoice().compareTo(BigDecimal.ZERO) == 1){
                            invoiceSum.setInvoiceStatus("00");
                           // invocationIntFreeze(invoiceSum.getAgentId(),userId,invoiceSum.getId(),"欠票数据导入-冻结");
                        }else {
                            invoiceSum.setInvoiceStatus("99");
                           // invocationIntUnFreeze(invoiceSum.getAgentId(),userId,"欠票数据导入-解冻 ");
                        }
                        invoiceSumMapper.insertSelective(invoiceSum);
                    }else{
                        invoiceSum.setId(invoiceSum2.get(0).getId());
                        invoiceSum.setAddInvoiceAmt(invoiceSum2.get(0).getAddInvoiceAmt());
                        invoiceSum.setAdjustAmt(invoiceSum2.get(0).getAdjustAmt());
                        invoiceSum.setOwnInvoice(
                                invoiceSum.getPreLeftAmt()
                                        .add(invoiceSum.getSubAddInvoiceAmt())
                                        .add(invoiceSum.getDayBackAmt())
                                        .add(invoiceSum.getDayProfitAmt())
                                        .add(invoiceSum.getPreProfitMonthAmt())
                                        .add(invoiceSum.getAdjustAmt())
                                        .subtract(invoiceSum.getAddInvoiceAmt())
                                       );
                        if(invoiceSum.getOwnInvoice().compareTo(BigDecimal.ZERO) == 1){
                            invoiceSum.setInvoiceStatus("00");
                           // invocationIntFreeze(invoiceSum.getAgentId(),userId,invoiceSum.getId(),"欠票数据导入-冻结");
                        }else {
                            invoiceSum.setInvoiceStatus("99");
                           // invocationIntUnFreeze(invoiceSum.getAgentId(),userId,"欠票数据导入-解冻");
                        }
                        invoiceSumMapper.updateByPrimaryKeySelective(invoiceSum);
                    }
                }else{
                    throw new MessageException("excel中存在不合理数据！");
                }
            }
        }catch(ParseException e){
            e.printStackTrace();
            throw new MessageException("月份输入不合理");
        }catch (MessageException e){
            logger.info("导入失败："+e.getMsg());
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        }catch (Exception e){
            logger.info("导入数据失败，请重试！");
            e.printStackTrace();
            throw new MessageException("导入数据失败，请重试！");
        }
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
       return  invoiceSumMapper.selectByExample(example);
    }

    /**
     * 调用冻结模块接口，将代理商信息冻结
     * @param agentId
     * @param user
     * @param id
     * @return
     * @throws MessageException
     */
    @Override
    public boolean invocationIntFreeze(String agentId, String user,String id,String reason) throws MessageException{
        AgentFreezePort agentFreezePort = new AgentFreezePort();
        agentFreezePort.setAgentId(agentId);
        agentFreezePort.setOperationPerson(user);
        agentFreezePort.setFreezeCause("QPDJ");
        agentFreezePort.setFreezeNum(agentId);
        agentFreezePort.setRemark(reason);
        agentFreezePort.setFreeType(Arrays.asList(FreeType.AGNET.code));
        AgentResult agentResult = agentFreezeService.agentFreeze(agentFreezePort);
        if("200".equals(agentResult.getStatus())){
            logger.info("欠票冻结："+agentId+",代理商冻结成功！");
            return true;
        }else if("500".equals(agentResult.getStatus())){
            if("系统处理中,请勿重复提交！".equals(agentResult.getMsg())){
                logger.info("欠票冻结："+agentId+",系统处理中,请勿重复提交！");
                return true;
            }else if("代理商此原因已被冻结".equals(agentResult.getMsg())){
                logger.info("欠票冻结："+agentId+",代理商此原因已被冻结");
                return true;
            }else{
                throw new MessageException("代理商欠票冻结：代理商："+agentId+agentResult.getMsg());
            }
        }
        return false;
    }

    /**
     * 调用冻结/解冻模块 对代理商欠票冻结 进行解冻
     * @param agentId
     * @param user
     * @return
     * @throws MessageException
     */
    @Override
    public boolean invocationIntUnFreeze(String agentId,String user,String reason) throws MessageException{
        AgentFreezePort agentFreezePort = new AgentFreezePort();
        agentFreezePort.setAgentId(agentId);
        agentFreezePort.setOperationPerson(user);
        agentFreezePort.setFreezeCause("QPDJ");
        agentFreezePort.setUnfreezeCause(reason);
        AgentResult agentResult = agentFreezeService.agentUnFreeze(agentFreezePort);
        if("200".equals(agentResult.getStatus())){
            logger.info("发票管理解冻："+agentId+",代理商解冻成功！");
            return true;
        }else if("500".equals(agentResult.getStatus())){
            if("系统处理中,请勿重复提交！".equals(agentResult.getMsg())){
                logger.info("欠票解冻："+agentId+",系统处理中,请勿重复提交！");
                return true;  //"解冻信息不存在"
            }else if("解冻信息不存在".equals(agentResult.getMsg())){
                logger.info("欠票解冻："+agentId+",解冻信息不存在");
                return true;
            }else{
                throw new MessageException("代理商欠票冻结：代理商："+agentId+agentResult.getMsg());
            }
        }
        return false;
    }

}