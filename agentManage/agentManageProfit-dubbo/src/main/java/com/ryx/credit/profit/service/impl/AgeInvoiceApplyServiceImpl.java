package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.profit.dao.InvoiceApplyMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IAgeInvoiceApplyService;
import com.ryx.credit.profit.service.IInvoiceSumService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.IdService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代理商发票明细 维护类
 * @Author CQT
 * @Date 2019/2/18
 */
@Service("ageInvoiceApplyService")
public class AgeInvoiceApplyServiceImpl implements IAgeInvoiceApplyService {

    Logger logger = LoggerFactory.getLogger(AgeInvoiceApplyServiceImpl.class);

    private final static String CLIENT_ID = AppConfig.getProperty("jindie.clientId");
    private final static String BASE_URL = AppConfig.getProperty("jindie.baseUrl");
    private final static String CLIENT_SECRET = AppConfig.getProperty("jindie.clientSecret");
    private final static String ACCESS_TOCKEN_URL = AppConfig.getProperty("jd.accessTocken");
    private final static String PASSWORD = AppConfig.getProperty("encrypt.key");
    private final static String TICKET_INFO_URL = AppConfig.getProperty("jd.ticketInfo")+"?access_token=";
    private final static String[] ARRAY_INVOICE = {"研发和技术服务","研发服务","信息技术服务","软件服务","信息系统服务","现代服务","技术服务","信息技术服务","咨询服务","服务费"};
    private String tocken = "";
    private String importBatch = ""; // 导入批次号
    private BigDecimal batchNo = BigDecimal.ZERO; //发票排序

    @Autowired
    private InvoiceApplyMapper invoiceApplyMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private IInvoiceSumService invoiceSumService;

    @Override
    public PageInfo queryInvoiceDetail(Page page,Map map) {
        InvoiceApplyExample example = new InvoiceApplyExample();
        example.setPage(page);
        InvoiceApplyExample.Criteria criteria = example.createCriteria();
        setValueExample(criteria,map);
        example.setOrderByClause("substr(CREATE_DATE,0,10) desc,IMPORT_BATCH desc , BATCH_NO asc");
        List<Map> list = invoiceApplyMapper.selectByExampleOwn(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(list);
        pageInfo.setTotal((int)invoiceApplyMapper.countByExampleOwn(example));
        return pageInfo;
    }

    @Override
    public void deleteById(String id) {
        InvoiceApply invoiceApply = invoiceApplyMapper.selectByPrimaryKey(id);
        if(invoiceApply != null){
            invoiceApplyMapper.deleteById(id);
        }
    }

    @Override
    public void updateExpressInfo(InvoiceApply invoiceApply) {
        invoiceApplyMapper.updateByPrimaryKeySelective(invoiceApply);
    }

    @Override
    public InvoiceApply getInvoiceApplyById(String id) {
        return invoiceApplyMapper.selectByPrimaryKey(id);
    }

    /**
     * 发票信息处理
     * @param list
     * @param map
     * @param agentId
     * @throws MessageException
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public void dealWithInvoiceData(List<Map<String, Object>> list, Map map, String agentId) throws MessageException {
        String role = map.get("ORGANIZATIONCODE").toString();
        String userName = null;
        String user = null;

        if( Objects.equals(role, "agent")){
            if(agentId == null) throw new MessageException("登陆账号查询代AG码失败,请联系管理员处理");
            Agent agent = agentService.getAgentById(agentId);
            if(agent == null)  throw new MessageException("未获取到账号对应代理商信息");
            if(StringUtils.isBlank(agent.getAgName()))  throw new MessageException("代理商的名称为空,请检查");
            userName = agent.getAgName();
            user = agentId;
        }else if( Objects.equals(role, "finance")){
            user = map.get("ID").toString();
            String dateStr = addMonth("yyyyMMdd",0);
            Map<String,Object> batchUser = invoiceApplyMapper.getImportBatch(map.get("ID").toString(),dateStr);
            if(batchUser != null){
                importBatch = batchUser.get("IMPORT_BATCH").toString();
                batchNo = new BigDecimal(batchUser.get("BATCH_NO").toString());
            }else{
                String maxBatch = invoiceApplyMapper.getMaxImportBatch(dateStr);
                if(StringUtils.isNotBlank(maxBatch)){
                    importBatch = Long.valueOf(maxBatch)+1+"";
                    batchNo = new BigDecimal(importBatch +""+"0");
                }else{
                    importBatch = dateStr + "" +"001";
                    batchNo = new BigDecimal(importBatch +""+"0");
                }
            }
        }
        try{
            tocken = getTockenString();
            for (Map<String,Object> param:list) {
                List<InvoiceApply> list1 = invoiceApplyMapper.selectListForDeal(param.get("invoiceNo").toString(),param.get("invoiceCode").toString());
                if(list1.size() == 0){// 发票第一次导入处理
                    dealWithFirst(param,role,user,userName);
                }else if(list1.size() >=1){ // 发票汇总
                    InvoiceApply invoiceApply = list1.get(0);
                    dealWithSecond(invoiceApply,role,user,userName);
                }
            }
        }catch (MessageException e){
            e.printStackTrace();
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            throw  new MessageException("数据处理失败");
        }
    }

    /**
     * 发票第一次处理：保存数据及各条件判断
     * @throws MessageException
     */
    private void dealWithFirst(Map<String,Object> param,String role,String user,String agentName) throws MessageException{
       try{
           InvoiceApply invoiceApply = new InvoiceApply();
           invoiceApply.setId(idService.genId(TabId.P_INVOICE_APPLY));
           invoiceApply.setSerialNo(param.get("serialNo").toString());
           invoiceApply.setInvoiceCode(param.get("invoiceCode").toString());
           invoiceApply.setInvoiceNumber(param.get("invoiceNo").toString());
           invoiceApply.setAmountTax(new BigDecimal(param.get("taxAmount").toString()));
           invoiceApply.setSumAmt(new BigDecimal(param.get("totalAmount").toString()));
           invoiceApply.setAmount(new BigDecimal(param.get("amount").toString()));
           invoiceApply.setSallerNo(param.get("sallerTaxNo").toString());
           invoiceApply.setInvoiceDate(param.get("invoiceDate").toString());
           invoiceApply.setInvoiceType(param.get("invoiceType").toString());
           invoiceApply.setExpenseStatus(param.get("expenseStatus").toString());
           invoiceApply.setStatus("0");
           invoiceApply.setCreateDate(addMonth("yyyy-MM-dd HH:mm:ss",0));

           if("agent".equals(role)){
               invoiceApply.setAgentId(user);
               invoiceApply.setAgentName(agentName);
           }

           String shStatus = "1";
           String shMessage = "";

           //获取发票详细信息
           Map<String,Object> result = getTicketInfo(param.get("invoiceCode").toString(),param.get("invoiceNo").toString(),tocken);
           if(result == null){
               shStatus = "0";
               shMessage = "发票云库中未找到该发票信息";
               invoiceApply.setInvoiceCompany(param.get("sallerName").toString());
           }else {
               if ("1".equals(result.get("proxyMark").toString())) { // 代开
                   String remark = result.get("remark").toString();  // 获取发票备注
                   int flag = remark.indexOf("代开企业名称:");// 截取代开企业名称
                   if (flag != -1) {
                       String string = remark.substring(flag + 7);
                       int brdex = string.indexOf("<br/>");
                       if(brdex != -1){
                           invoiceApply.setInvoiceCompany(string.substring(0,brdex));
                       }else{
                           invoiceApply.setInvoiceCompany(string.trim());
                       }
                       invoiceApply.setSallerName(param.get("sallerName").toString());
                       invoiceApply.setRemark(remark);
                   } else {
                       shStatus = "0";
                       shMessage = "未获取到发票备注中开票公司数据";
                   }
               } else {
                   invoiceApply.setInvoiceCompany(param.get("sallerName").toString());
               }
               //获取该发票税率及商品名称
               List<Map<String,Object>> items = (List<Map<String, Object>>) result.get("items");
               if(items.size() > 0){
                   invoiceApply.setTax(new BigDecimal(items.get(0).get("taxRate").toString()));
                   invoiceApply.setInvoiceItem(items.get(0).get("goodsName").toString());
               }
           }

           //判断发票类型 （税点0的普通纸质、所有专用纸质）
           if("1".equals(shStatus)){
               if(!Objects.equals(param.get("invoiceType"),"3")
                       && ! Objects.equals(param.get("invoiceType"),"4")){
                   shStatus = "0";
                   shMessage = "发票类型不符";
               }
               if(Objects.equals(param.get("invoiceType"),"3")
                       && !BigDecimal.ZERO.equals(invoiceApply.getTax())){
                   shStatus = "0";
                   shMessage = "普票税点不符";
               }
           }

           // 判断商品名称是否符合要求
           if("1".equals(shStatus)){
               Boolean flag = false;
               if(StringUtils.isNotBlank(invoiceApply.getInvoiceItem())){
                   for (int i = 0;i < ARRAY_INVOICE.length ; i++) {
                       String str = ARRAY_INVOICE[i];
                       if(invoiceApply.getInvoiceItem().indexOf(str) != -1){
                           flag = true;
                           break;
                       }
                   }
                   if(!flag){
                       shStatus = "0";
                       shMessage = "商品名称不符";
                   }
               }else{
                   shStatus = "0";
                   shMessage = "未获取到该发票对应商品名称";
               }
           }

           //判断开票公司是否符合
           if("1".equals(shStatus)){
               if("finance".equals(role)){
                   if(StringUtils.isNotBlank(invoiceApply.getInvoiceCompany())){
                       List<Map<String,String>> agList = invoiceApplyMapper.getAgentIdByInvoiceCompany(invoiceApply.getInvoiceCompany(),addMonth("yyyyMM",-1));
                       if(agList.size() != 0){
                           invoiceApply.setAgentId(agList.get(0).get("AGENT_ID"));
                           invoiceApply.setAgentName(agList.get(0).get("AGENT_NAME"));
                       }else{
                           shStatus = "0";
                           shMessage = "未查询到该发票对应代理商";
                       }
                   }
               }else if("agent".equals(role)){
                   List<String> stringList = invoiceApplyMapper.getPayCompanyById(user);
                   if(stringList.size()<= 0){
                       shStatus = "0";
                       shMessage = "未找到该代理商开票公司";
                   }else{
                       if(!stringList.contains(invoiceApply.getInvoiceCompany())){
                           shStatus = "0";
                           shMessage = "开票公司和该代理商不符";
                       }
                   }
               }
           }

           //判断税率
           if("1".equals(shStatus)){
               BigDecimal invoiceTax = invoiceApply.getTax();
               if(invoiceTax != null){
                   BigDecimal agentTax = invoiceApplyMapper.getAgentTaxByAgentId(invoiceApply.getAgentId());
                   if(agentTax == null){
                       shStatus = "0";
                       shMessage = "未查询到该发票对应代理商的税点";
                   }else if(agentTax.compareTo(new BigDecimal("0.03")) == 0){
                       if(invoiceTax.compareTo(new BigDecimal("0.03")) != 0 && invoiceTax.compareTo(new BigDecimal("0.01")) != 0 && invoiceTax.compareTo(BigDecimal.ZERO) != 0){
                           shStatus = "0";
                           shMessage = "代理商税点为0.03时,只允许发票税点为(0.03、0.01、0)";
                       }
                   }else if(invoiceTax.compareTo(agentTax) != 0){
                       shStatus = "0";
                       shMessage = "该发票税点和代理商税点不相同";
                   }
               }else {
                   shStatus = "0";
                   shMessage = "发票税点获取有误";
               }
           }

           if("agent".equals(role)){
              invoiceApply.setYsResult(shStatus);
              invoiceApply.setRev1(shMessage);
              invoiceApply.setYsDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
           }else if("finance".equals(role)){
               invoiceApply.setEsResult(shStatus);
               invoiceApply.setRev2(shMessage);
               invoiceApply.setEsDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
               invoiceApply.setCreateName(user);
               invoiceApply.setYsResult("2");
               invoiceApply.setImportBatch(importBatch);
               BigDecimal aaa = new BigDecimal(batchNo.toString().substring(importBatch.length())).add(new BigDecimal(1));
               batchNo = new BigDecimal(importBatch+aaa);
               invoiceApply.setBatchNo(batchNo);
           }

           // 对发票金额进行判断,则若第一次审核金额大于欠票金额不能导入
           if("1".equals(shStatus)){
               //未汇总发票
               BigDecimal bg = invoiceApplyMapper.getSumInvoice(invoiceApply.getAgentId(),invoiceApply.getInvoiceCompany(),addMonth("yyyy-MM",0));
               bg = bg == null ? BigDecimal.ZERO : bg;
               // 本月欠票
               BigDecimal ownInvoice = invoiceApplyMapper.getOwnInvoice(invoiceApply.getAgentId(),invoiceApply.getInvoiceCompany(),addMonth("yyyyMM",-1));
               if(null == ownInvoice){
                   throw new MessageException("开票公司是：（"+invoiceApply.getInvoiceCompany()+"）未获取到欠票数据！");
               }
               BigDecimal subtra = bg.subtract(ownInvoice);  //到票金额-欠票金额
               if(subtra.compareTo(new BigDecimal(10)) > 0 ){ //到票金额-欠票金额 大于 10元时
                   throw new MessageException("开票公司：("+invoiceApply.getInvoiceCompany()+")所开发票金额总计超过本月欠票"+subtra+"元，不符合条件，请重新导入");
               }
           }
           invoiceApplyMapper.insertSelective(invoiceApply);
       }catch (MessageException e){
           e.printStackTrace();
           throw e;
       }catch (Exception e){
           e.printStackTrace();
           throw new MessageException("数据处理失败！");
       }
    }

    /**
     * 第二次处理：发票核销及汇总
     * @throws MessageException
     */
    private void dealWithSecond(InvoiceApply invoiceApply,String role,String user,String agentName)throws MessageException{
        if("agent".equals(role) && "1".equals(invoiceApply.getEsResult()) ){
            // 代理商方汇总处理
            Map<String,Object> mmm = new HashMap<String,Object>();
            mmm.put("AGENT_ID",invoiceApply.getAgentId());
            mmm.put("PROFIT_MONTH",addMonth("yyyyMM",-1));
            mmm.put("INVOICE_AMT",invoiceApply.getSumAmt());
            mmm.put("INVOICE_COMPANY",invoiceApply.getInvoiceCompany());
            mmm.put("user",user);
            Map<String,Object> ma = invoiceSumService.getInvoiceFinalData(mmm);
            if(!"9999".equals(ma.get("returnCode").toString()) ){
                invoiceApply.setYsResult("0");
                invoiceApply.setRev1("到票金额汇总失败："+ma.get("returnInfo").toString());
            }else{
                invoiceApply.setYsResult("1");
                invoiceApply.setRev1("");
                invoiceApply.setStatus("1");
            }
            invoiceApply.setYsDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
            invoiceApply.setProfitMonth(addMonth("yyyyMM",-1));
            invoiceApplyMapper.updateByPrimaryKeySelective(invoiceApply);
        }else if ("finance".equals(role) && "1".equals(invoiceApply.getYsResult())){
            //财务方汇总处理
            Map<String,Object> mmm = new HashMap<String,Object>();
            mmm.put("AGENT_ID",invoiceApply.getAgentId());
            mmm.put("PROFIT_MONTH",addMonth("yyyyMM",-1));
            mmm.put("INVOICE_AMT",invoiceApply.getSumAmt());
            mmm.put("INVOICE_COMPANY",invoiceApply.getInvoiceCompany());
            mmm.put("user",user);
            Map<String,Object> ma = invoiceSumService.getInvoiceFinalData(mmm);
            if(!"9999".equals(ma.get("returnCode").toString()) ){
                invoiceApply.setEsResult("0");
                invoiceApply.setRev2("到票金额汇总失败："+ma.get("returnInfo").toString());
            }else{
                invoiceApply.setEsResult("1");
                invoiceApply.setRev2("");
                invoiceApply.setStatus("1");
            }
            invoiceApply.setEsDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
            invoiceApply.setProfitMonth(addMonth("yyyyMM",-1));
            if(StringUtils.isBlank(invoiceApply.getImportBatch())){
                invoiceApply.setImportBatch(importBatch);
                BigDecimal aaa = new BigDecimal(batchNo.toString().substring(importBatch.length())).add(new BigDecimal(1));
                batchNo = new BigDecimal(importBatch+aaa);
                invoiceApply.setBatchNo(batchNo);
            }
            invoiceApply.setCreateName(user);
            invoiceApply.setCreateDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
            invoiceApplyMapper.updateByPrimaryKeySelective(invoiceApply);
        }else if("agent".equals(role) && "1".equals(invoiceApply.getYsResult())){
            invoiceApply.setId(idService.genId(TabId.P_INVOICE_APPLY));
            invoiceApply.setYsResult("0");
            invoiceApply.setYsDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
            invoiceApply.setRev1("该发票代理商方重复导入");
            invoiceApply.setEsResult("");
            invoiceApply.setEsDate("");
            invoiceApply.setRev2("");
            invoiceApply.setCreateDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
            invoiceApplyMapper.insertSelective(invoiceApply);
        }else if("finance".equals(role) && "1".equals(invoiceApply.getEsResult())){
            invoiceApply.setId(idService.genId(TabId.P_INVOICE_APPLY));
            invoiceApply.setYsResult("2");
            invoiceApply.setYsDate("");
            invoiceApply.setRev1("");
            invoiceApply.setEsResult("0");
            invoiceApply.setEsDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
            invoiceApply.setRev2("该发票财务方重复导入");
            invoiceApply.setImportBatch(importBatch);
            BigDecimal aaa = new BigDecimal(batchNo.toString().substring(importBatch.length())).add(new BigDecimal(1));
            batchNo = new BigDecimal(importBatch+aaa);
            invoiceApply.setBatchNo(batchNo);
            invoiceApply.setCreateName(user);
            invoiceApply.setCreateDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
            invoiceApplyMapper.insertSelective(invoiceApply);
        }else{
            logger.info("******不会出现这种情况********");
        }
    }


    /**
     * 初审数据导入
     * @param list
     * @param agentId
     * @throws MessageException
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public void saveInvoiceApply(List<Map<String,Object>> list, String agentId) throws MessageException{
        try {
            Agent agent = agentService.getAgentById(agentId);
            if(agent == null)
                throw new MessageException("未获取到该代理商的信息！");
            if(StringUtils.isBlank(agent.getAgName()))
                throw new MessageException("代理商的名称为空,请检查！");
            List<InvoiceApply> invoiceApplyList = new ArrayList<InvoiceApply>();
            Map<String,BigDecimal> mapInvoice = new HashMap<String,BigDecimal>();
            tocken = getTockenString();
            for (Map<String,Object> map:list) {
                    InvoiceApply invoiceApply = new InvoiceApply();
                    Map<String,Object> result = getTicketInfo(map.get("invoiceCode").toString(),map.get("invoiceNo").toString(),tocken);
                    if(result == null){
                        invoiceApply.setYsResult("0");
                        invoiceApply.setInvoiceCompany(map.get("sallerName").toString());
                        invoiceApply.setRev1("发票云库中未找到该发票信息!");
                    }else {
                        invoiceApply.setYsResult("1");
                        if ("1".equals(result.get("proxyMark").toString())) { // 代开
                            String remark = result.get("remark").toString();  // 获取发票备注
                            int flag = remark.indexOf("代开企业名称:");// 截取代开企业名称
                            if (flag != -1) {
                                String string = remark.substring(flag + 7);
                                int brdex = string.indexOf("<br/>");
                                if(brdex != -1){
                                    invoiceApply.setInvoiceCompany(string.substring(0,brdex));
                                }else{
                                    invoiceApply.setInvoiceCompany(string.trim());
                                }
                                invoiceApply.setSallerName(map.get("sallerName").toString());
                                invoiceApply.setRemark(remark);
                            } else {
                                invoiceApply.setYsResult("0");
                                invoiceApply.setRev1("未获取到发票备注中开票公司数据!");
                            }
                        } else {
                            invoiceApply.setInvoiceCompany(map.get("sallerName").toString());
                        }

                        //获取该发票税率及商品名称
                        List<Map<String,Object>> items = (List<Map<String, Object>>) result.get("items");
                        if(items.size() > 0){
                            invoiceApply.setTax(new BigDecimal(items.get(0).get("taxRate").toString()));
                            invoiceApply.setInvoiceItem(items.get(0).get("goodsName").toString());
                        }
                    }

                    //判断发票类型 （税点0的普通纸质、所有专用纸质）
                    if("1".equals(invoiceApply.getYsResult())){
                        if("3".equals(map.get("invoiceType").toString())
                                && BigDecimal.ZERO.equals(invoiceApply.getTax())){
                            invoiceApply.setYsResult("1");
                        }else if("4".equals(map.get("invoiceType").toString())){
                            invoiceApply.setYsResult("1");
                        }else{
                            invoiceApply.setYsResult("0");
                            invoiceApply.setRev1("该发票类型不是'专用纸质发票'类型！");
                        }
                    }

                    // 判断商品名称是否符合要求
                    if("1".equals(invoiceApply.getYsResult())){
                        Boolean flag = false;
                        if(StringUtils.isNotBlank(invoiceApply.getInvoiceItem())){
                            for (int i = 0;i < ARRAY_INVOICE.length ; i++) {
                                String str = ARRAY_INVOICE[i];
                                if(invoiceApply.getInvoiceItem().indexOf(str) != -1){
                                    flag = true;
                                    break;
                                }
                            }
                            if(!flag){
                                invoiceApply.setYsResult("0");
                                invoiceApply.setRev1("商品名称不符！");
                            }
                        }else{
                            invoiceApply.setYsResult("0");
                            invoiceApply.setRev1("未获取到该发票对应商品名称！");
                        }
                    }

                    //判断开票公司是否符合
                    if("1".equals(invoiceApply.getYsResult())){
                        List<String> stringList = invoiceApplyMapper.getPayCompanyById(agentId);
                        if(stringList.size()<= 0){
                            invoiceApply.setYsResult("0");
                            invoiceApply.setRev1("未找到该代理商开票公司！");
                        }else{
                            if(!stringList.contains(invoiceApply.getInvoiceCompany())){
                                invoiceApply.setYsResult("0");
                                invoiceApply.setRev1("开票公司和该代理商不符！");
                            }
                        }
                    }

                    //判断税率
                    if("1".equals(invoiceApply.getYsResult())){
                       // 先获取代理商税率
                        BigDecimal invoiceTax = invoiceApply.getTax();
                        if(invoiceTax != null){
                            BigDecimal agentTax = invoiceApplyMapper.getAgentTaxByAgentId(agentId);
                            if(agentTax.compareTo(new BigDecimal("0.03")) == 0){
                                if(invoiceTax.compareTo(new BigDecimal("0.03")) != 0
                                        && invoiceTax.compareTo(new BigDecimal("0.01")) != 0
                                        && invoiceTax.compareTo(BigDecimal.ZERO) != 0){
                                    invoiceApply.setYsResult("0");
                                    invoiceApply.setRev1("代理商税点为0.03时,只允许发票税点为(0.03、0.01、0)");
                                }
                            }else if(invoiceTax.compareTo(agentTax) != 0){
                                invoiceApply.setYsResult("0");
                                invoiceApply.setRev1("该发票税点和代理商税点不相同");
                            }
                        }else {
                            invoiceApply.setYsResult("0");
                            invoiceApply.setRev1("获取发票税点错误！");
                        }
                    }
                    // 判断发票是否重复导入
                    if("1".equals(invoiceApply.getYsResult())){
                        InvoiceApply invoiceApply1 = new InvoiceApply();
                        invoiceApply1.setInvoiceCode(map.get("invoiceCode").toString());
                        invoiceApply1.setInvoiceNumber(map.get("invoiceNo").toString());
                        invoiceApply1.setYsResult("1");
                        List<InvoiceApply> list1 = getListByExample(invoiceApply1);
                        if(list1.size() >= 1){
                            invoiceApply.setYsResult("0");
                            invoiceApply.setRev1("该发票重复导入！");
                        }
                    }

                    invoiceApply.setId(idService.genId(TabId.P_INVOICE_APPLY));
                    invoiceApply.setAgentId(agentId);
                    invoiceApply.setAgentName(agent.getAgName());
                    invoiceApply.setSerialNo(map.get("serialNo").toString());
                    invoiceApply.setInvoiceCode(map.get("invoiceCode").toString());
                    invoiceApply.setInvoiceNumber(map.get("invoiceNo").toString());
                    invoiceApply.setAmountTax(new BigDecimal(map.get("taxAmount").toString()));
                    invoiceApply.setSumAmt(new BigDecimal(map.get("totalAmount").toString()));
                    invoiceApply.setAmount(new BigDecimal(map.get("amount").toString()));
                    invoiceApply.setSallerNo(map.get("sallerTaxNo").toString());
                    invoiceApply.setInvoiceDate(map.get("invoiceDate").toString());
                    invoiceApply.setInvoiceType(map.get("invoiceType").toString());
                    invoiceApply.setExpenseStatus(map.get("expenseStatus").toString());
                    invoiceApply.setYsDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
                    invoiceApply.setStatus("0");
                    invoiceApply.setCreateDate(addMonth("yyyy-MM-dd HH:mm:ss",0));

                    // 对发票金额进行判断， 则若通过初审金额大于欠票金额不能导入
                    if("1".equals(invoiceApply.getYsResult())){
                        BigDecimal bg = invoiceApplyMapper.getSumInvoice(invoiceApply.getAgentId(),invoiceApply.getInvoiceCompany(),addMonth("yyyy-MM",0));
                        bg = bg == null ? BigDecimal.ZERO : bg;
                        BigDecimal mg = mapInvoice.get(invoiceApply.getInvoiceCompany()) == null ? BigDecimal.ZERO : mapInvoice.get(invoiceApply.getInvoiceCompany());
                        mg = mg.add(invoiceApply.getSumAmt());
                        // 获取本月该欠票公司的欠票金额
                        BigDecimal ownInvoice = invoiceApplyMapper.getOwnInvoice(invoiceApply.getAgentId(),invoiceApply.getInvoiceCompany(),addMonth("yyyyMM",-1));
                        if(null == ownInvoice){
                            throw new MessageException("开票公司是：（"+invoiceApply.getInvoiceCompany()+"）未获取到欠票数据！");
                        }
                        BigDecimal subtra = bg.add(mg).subtract(ownInvoice);  //到票金额-欠票金额
                        if(subtra.compareTo(new BigDecimal(10)) > 0 ){ //到票金额-欠票金额 大于 10元时
                            throw new MessageException("开票公司：("+invoiceApply.getInvoiceCompany()+")所开发票金额总计超过本月欠票"+subtra+"元，不符合条件，请重新导入");
                        }else{
                            mapInvoice.put(invoiceApply.getInvoiceCompany(),mg);
                        }
                    }
                    invoiceApplyList.add(invoiceApply);
            }
            //将通过的初误审，或者初审出现错的数据保存
            insertList(invoiceApplyList);
        }catch (MessageException e){
            e.printStackTrace();
            logger.info(e.getMsg());
            throw  new MessageException(e.getMsg());
        }catch (Exception e){
           e.printStackTrace();
           throw  new MessageException("保存失败!");
        }
    }

    private void insertList(List<InvoiceApply> list){
        for (InvoiceApply invoiceApply:list) {
            invoiceApplyMapper.insertSelective(invoiceApply);
        }
    }

    /**
     * 终审，即财务审核
     * @param list
     * @param user
     * @throws MessageException
     */
    @Override
    public void finalCheckInvoice(List<Map<String,Object>> list,String user) throws MessageException{
        try{

            String dateStr = addMonth("yyyyMMdd",0);
            Map<String,Object> batchUser = invoiceApplyMapper.getImportBatch(user,dateStr);
            if(batchUser != null){
                importBatch = batchUser.get("IMPORT_BATCH").toString();
                batchNo = new BigDecimal(batchUser.get("BATCH_NO").toString());
            }else{
                String maxBatch = invoiceApplyMapper.getMaxImportBatch(dateStr);
                if(StringUtils.isNotBlank(maxBatch)){
                    importBatch = Long.valueOf(maxBatch)+1+"";
                    batchNo = new BigDecimal(importBatch +""+"0");
                }else{
                    importBatch = dateStr + "" +"001";
                    batchNo = new BigDecimal(importBatch +""+"0");
                }
            }
            for (Map<String,Object> map:list) {
                InvoiceApply invoiceApply = new InvoiceApply();
                invoiceApply.setInvoiceNumber(map.get("invoiceNo").toString());
                invoiceApply.setInvoiceCode(map.get("invoiceCode").toString());
                invoiceApply.setYsResult("1");
                invoiceApply.setStatus("0");
                List<InvoiceApply> invoiceApplies =  getListByExample(invoiceApply);
                if(invoiceApplies.size() >= 1){
                    InvoiceApply invoiceApply1 = invoiceApplies.get(0);
                    Map<String,Object> mmm = new HashMap<String,Object>();
                    mmm.put("AGENT_ID",invoiceApply1.getAgentId());
                    mmm.put("PROFIT_MONTH",addMonth("yyyyMM",-1));
                    mmm.put("INVOICE_AMT",invoiceApply1.getSumAmt());
                    mmm.put("INVOICE_COMPANY",invoiceApply1.getInvoiceCompany());
                    mmm.put("user",user);
                    Map<String,Object> ma = invoiceSumService.getInvoiceFinalData(mmm);
                    if(!"9999".equals(ma.get("returnCode").toString()) ){
                        invoiceApply1.setEsResult("0");
                        invoiceApply1.setRev2("到票金额汇总失败："+ma.get("returnInfo").toString());
                    }else{
                        invoiceApply1.setEsResult("1");
                        invoiceApply1.setRev2("");
                        invoiceApply1.setStatus("1");
                    }
                    invoiceApply1.setEsDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
                    invoiceApply1.setProfitMonth(addMonth("yyyyMM",-1));
                    if(StringUtils.isBlank(invoiceApply1.getImportBatch())){
                        invoiceApply1.setImportBatch(importBatch);
                        BigDecimal aaa = new BigDecimal(batchNo.toString().substring(importBatch.length())).add(new BigDecimal(1));
                        batchNo = new BigDecimal(importBatch+aaa);
                        invoiceApply1.setBatchNo(batchNo);
                    }
                    invoiceApply1.setCreateName(user);
                    invoiceApply1.setCreateDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
                    invoiceApplyMapper.updateByPrimaryKeySelective(invoiceApply1);
                }else{
                    saveEsFailInvoiceInfo(map,user,"该发票未进行初审");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException(e.getMessage());
        }
    }

    /**
     *  对于终审失败，且失败原因是 该发票未进行初审的记录进行保存
     * @param map
     */
    private void saveEsFailInvoiceInfo(Map<String,Object> map,String user,String rev2)throws Exception{
        // 去发票汇总表根据开票公司查询对应的代理商，保存发票数据
        InvoiceApply invoiceApply = new InvoiceApply();
        tocken = getTockenString();
        Map<String,Object> result = getTicketInfo(map.get("invoiceCode").toString(),map.get("invoiceNo").toString(),tocken);
        if(result != null){
            List<Map<String,Object>> items = (List<Map<String, Object>>) result.get("items");
            if(items.size() > 0){
                invoiceApply.setTax(new BigDecimal(items.get(0).get("taxRate").toString()));
                invoiceApply.setInvoiceItem(items.get(0).get("goodsName").toString());
            }
            //代开发票截取开票公司名称
            if ("1".equals(result.get("proxyMark").toString())) {
                    String remark = result.get("remark").toString();
                    int flag = remark.indexOf("代开企业名称:");
                    if (flag != -1) {
                        String str = remark.substring(flag + 7);
                        int brdex = str.indexOf("<br/>");
                        if(brdex != -1){
                            invoiceApply.setInvoiceCompany(str.substring(0,brdex));
                        }else{
                            invoiceApply.setInvoiceCompany(str.trim());
                        }
                        invoiceApply.setRemark(remark);
                    }else{
                        invoiceApply.setInvoiceCompany("");
                    }
            }else{
                    invoiceApply.setInvoiceCompany(map.get("sallerName").toString());
            }
        }

        if(StringUtils.isNotBlank(invoiceApply.getInvoiceCompany())){
            List<Map<String,String>> agList = invoiceApplyMapper.getAgentIdByInvoiceCompany(
                    invoiceApply.getInvoiceCompany(),addMonth("yyyyMM",-1));
            if(agList.size() != 0){
                invoiceApply.setAgentId(agList.get(0).get("AGENT_ID"));
                invoiceApply.setAgentName(agList.get(0).get("AGENT_NAME"));
            }
        }

        invoiceApply.setId(idService.genId(TabId.P_INVOICE_APPLY));
        invoiceApply.setCreateName(user);
        invoiceApply.setYsResult("2");
        invoiceApply.setYsDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
        invoiceApply.setRev1("该条数据未进行初审，由终审导入");
        invoiceApply.setEsResult("0");
        invoiceApply.setEsDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
        invoiceApply.setRev2(rev2);
        invoiceApply.setSerialNo(map.get("serialNo").toString());
        invoiceApply.setInvoiceCode(map.get("invoiceCode").toString());
        invoiceApply.setInvoiceNumber(map.get("invoiceNo").toString());
        invoiceApply.setAmountTax(new BigDecimal(map.get("taxAmount").toString()));
        invoiceApply.setSumAmt(new BigDecimal(map.get("totalAmount").toString()));
        invoiceApply.setAmount(new BigDecimal(map.get("amount").toString()));
        invoiceApply.setSallerNo(map.get("sallerTaxNo").toString());
        invoiceApply.setInvoiceDate(map.get("invoiceDate").toString());
        invoiceApply.setInvoiceType(map.get("invoiceType").toString());
        invoiceApply.setExpenseStatus(map.get("expenseStatus").toString());
        invoiceApply.setStatus("0");
        invoiceApply.setCreateDate(addMonth("yyyy-MM-dd HH:mm:ss",0));
        invoiceApply.setCreateName(user);
        invoiceApply.setImportBatch(importBatch);
        BigDecimal aaa = new BigDecimal(batchNo.toString().substring(importBatch.length())).add(new BigDecimal(1));
        batchNo = new BigDecimal(importBatch+aaa);
        invoiceApply.setBatchNo(batchNo);

        invoiceApplyMapper.insert(invoiceApply);
    }

    /**日期格式转换*/
    private String addMonth(String str,int num){
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, num);
        return sdf.format(cal.getTime());
    }

    private  List<InvoiceApply> getListByExample(InvoiceApply invoiceApply){
        InvoiceApplyExample example = new InvoiceApplyExample();
        InvoiceApplyExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(invoiceApply.getSerialNo())){
            criteria.andSerialNoEqualTo(invoiceApply.getSerialNo());
        }
        if(StringUtils.isNotBlank(invoiceApply.getInvoiceCode())){
            criteria.andInvoiceCodeEqualTo(invoiceApply.getInvoiceCode());
        }
        if(StringUtils.isNotBlank(invoiceApply.getId())){
            criteria.andIdEqualTo(invoiceApply.getId());
        }
        if(StringUtils.isNotBlank(invoiceApply.getStatus())){
            criteria.andStatusEqualTo(invoiceApply.getStatus());
        }
        if(StringUtils.isNotBlank(invoiceApply.getYsResult())){
            criteria.andYsResultEqualTo(invoiceApply.getYsResult());
        }
        if(StringUtils.isNotBlank(invoiceApply.getInvoiceNumber())){
            criteria.andInvoiceNumberEqualTo(invoiceApply.getInvoiceNumber());
        }
       return  invoiceApplyMapper.selectByExample(example);
    }

    /**根据发票代码获取发票全部信息*/
    private Map<String,Object> getTicketInfo(String invoiceCode,String invoiceNo,String tocken)throws Exception{
        JSONObject param = new JSONObject();
        param.put("invoiceCode", invoiceCode);
        param.put("invoiceNo",invoiceNo);
        String result = HttpClientUtil.doPostJson(TICKET_INFO_URL+tocken,encrypt1(param.toJSONString()));
        logger.info("======获取发票全部信息:("+result+")======");
        Map<String,Object> rr = JSONObject.parseObject(result);
        if("0000".equals(rr.get("errcode"))){
            List<Map<String,Object>> list = (List<Map<String,Object>>)rr.get("data");
            return list.get(0);
        }
        return null;
    }

    /**获取金蝶授权AccessTocken*/
    private String getTockenString() throws MessageException{
        try {
            long timestamp = System.currentTimeMillis();
            JSONObject param = new JSONObject();
            param.put("client_id", CLIENT_ID);
            param.put("sign",getSign(timestamp));
            param.put("timestamp",timestamp);
            logger.info("ACCESS_TOCKEN_URL:-"+ACCESS_TOCKEN_URL+"-----param:"+param.toJSONString());
            String result = HttpClientUtil.doPostJson(ACCESS_TOCKEN_URL, param.toJSONString());
            logger.info("=====获取金蝶授权：tocken======"+result+"=======");
            Map<String,Object> map1 = JSONObject.parseObject(result);
            if("0000".equals(map1.get("errcode"))){
                return map1.get("access_token").toString();
            }else{
                throw new MessageException("获取金蝶授权失败,请稍后重试!");
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new MessageException("获取金蝶授权失败,请稍后重试!");
        }
    }

    /***获取MD5加密sign*/
    private String getSign(long timestamp)throws Exception{
        String str = CLIENT_ID+CLIENT_SECRET+timestamp;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }

    /**使用ASE128进行加密*/
    private String encrypt1(String content) throws Exception {
        byte[] raw = PASSWORD.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
        return new org.apache.commons.codec.binary.Base64().encodeToString(encrypted);
    }

    /*** 使用ASE128进行解密*/
    private String decrypt1(String content) throws Exception {
        try {
            byte[] raw = PASSWORD.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(content);//先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original,"utf-8");
            return originalString;
        } catch (Exception ex) {
            return "";
        }
    }

    @Override
    public List<Map<String,Object>> exports(Map map) {
        InvoiceApplyExample example = new InvoiceApplyExample();
        InvoiceApplyExample.Criteria criteria = example.createCriteria();
        setValueExample(criteria,map);
        example.setOrderByClause("substr(CREATE_DATE,0,10) desc,IMPORT_BATCH desc , BATCH_NO desc");
        List<Map<String,Object>> list = invoiceApplyMapper.exports(example);
        return list;
    }

    /**
     * 获取财务导入用户
     * @return
     */
    @Override
    public List<Map<String, String>> getCWImportUserList() {
        return invoiceApplyMapper.getImportUserList();
    }

    private void setValueExample(InvoiceApplyExample.Criteria criteria,Map map){
        if(map.get("invoiceCode")!= null && StringUtils.isNotBlank(map.get("invoiceCode").toString())){
            criteria.andInvoiceCodeEqualTo(map.get("invoiceCode").toString());
        }
        if(map.get("invoiceCompany") != null && StringUtils.isNotBlank(map.get("invoiceCompany").toString())){
            criteria.andInvoiceCompanyLike("%"+map.get("invoiceCompany")+"%");
        }
        if(map.get("agentId") != null && StringUtils.isNotBlank(map.get("agentId").toString())){
            criteria.andAgentIdEqualTo(map.get("agentId").toString());
        }
        if(map.get("importBatch") != null  && StringUtils.isNotBlank(map.get("importBatch").toString())){
            criteria.andImportBatchEqualTo(map.get("importBatch").toString());
        }
        if(map.get("createName") != null && StringUtils.isNotBlank(map.get("createName").toString())){
            criteria.andCreateNameIn(Arrays.asList(map.get("createName").toString().split(",")));
        }
        if(map.get("invoiceNumber") != null && StringUtils.isNotBlank(map.get("invoiceNumber").toString())){
            criteria.andInvoiceNumberEqualTo(map.get("invoiceNumber").toString());
        }
        if(map.get("batchNo") != null && StringUtils.isNotBlank(map.get("batchNo").toString())){
            criteria.andBatchNoEqualTo(new BigDecimal(map.get("batchNo").toString()));
        }
        if(map.get("agentName") != null && StringUtils.isNotBlank(map.get("agentName").toString())){
            criteria.andAgentNameEqualTo(map.get("agentName").toString());
        }
        if(map.get("esResult") != null && StringUtils.isNotBlank(map.get("esResult").toString())){
            criteria.andEsResultEqualTo(map.get("esResult").toString());
        }
        if(map.get("ysDateStart") != null && map.get("ysDateEnd") != null && StringUtils.isNotBlank(map.get("ysDateStart").toString())
                && StringUtils.isNotBlank(map.get("ysDateEnd").toString())){
            criteria.andYsDateBetween(map.get("ysDateStart").toString(),map.get("ysDateEnd").toString());
        }else if(map.get("ysDateStart") != null && StringUtils.isNotBlank(map.get("ysDateStart").toString())){
            criteria.andYsDateGreaterThanOrEqualTo(map.get("ysDateStart").toString());
        }else if(map.get("ysDateEnd") != null && StringUtils.isNotBlank(map.get("ysDateEnd").toString())){
            criteria.andYsDateLessThanOrEqualTo(map.get("ysDateEnd").toString());
        }
        if(map.get("createDateStart") != null&& map.get("createDateEnd")!=null && StringUtils.isNotBlank(map.get("createDateStart").toString())
                && StringUtils.isNotBlank(map.get("createDateEnd").toString())){
            criteria.andCreateDateBetween(map.get("createDateStart").toString(),map.get("createDateEnd").toString());
        }else if(map.get("createDateStart") != null && StringUtils.isNotBlank(map.get("createDateStart").toString())){
            criteria.andCreateDateGreaterThanOrEqualTo(map.get("createDateStart").toString());
        }else if(map.get("createDateEnd") != null && StringUtils.isNotBlank(map.get("createDateEnd").toString())){
            criteria.andCreateDateLessThanOrEqualTo(map.get("createDateEnd").toString());
        }
        if(map.get("esDateStart") != null && map.get("esDateEnd") != null &&  StringUtils.isNotBlank(map.get("esDateStart").toString())
                && StringUtils.isNotBlank(map.get("esDateEnd").toString())){
            criteria.andEsDateBetween(map.get("esDateStart").toString(),map.get("esDateEnd").toString());
        }else if(map.get("esDateStart") != null && StringUtils.isNotBlank(map.get("esDateStart").toString())){
            criteria.andEsDateGreaterThanOrEqualTo(map.get("esDateStart").toString());
        }else if(map.get("esDateEnd") != null && StringUtils.isNotBlank(map.get("esDateEnd").toString())){
            criteria.andEsDateLessThanOrEqualTo(map.get("esDateEnd").toString());
        }
    }

}
