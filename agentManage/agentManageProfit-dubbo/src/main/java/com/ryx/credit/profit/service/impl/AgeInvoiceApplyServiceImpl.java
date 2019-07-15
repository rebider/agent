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

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
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

    private String ACCESS_TOCKEN = "";
    private final static String CLIENT_ID = AppConfig.getProperty("jindie.clientId");// client_ID
    private final static String BASE_URL = AppConfig.getProperty("jindie.baseUrl");//baseUrl
    private final static String CLIENT_SECRET = AppConfig.getProperty("jindie.clientSecret"); // client_secret
    private final static String ACCESS_TOCKEN_URL = AppConfig.getProperty("jd.accessTocken");  // 获取tockenurl

    private final static String PASSWORD = AppConfig.getProperty("encrypt.key"); // 加密key
    private final static String TICKET_INFO_URL = AppConfig.getProperty("jd.ticketInfo")+"?access_token="; // 获取发票信息url

    private String tocken = "";



    @Autowired
    private InvoiceApplyMapper invoiceApplyMapper;
    @Autowired
    private IdService idService;
   @Autowired
   private AgentService agentService;
   @Autowired
   private IInvoiceSumService invoiceSumService;


    @Override
    public PageInfo queryInvoiceDetail(InvoiceApply invoiceApply, Page page,Map<String, Object> department) {
        InvoiceApplyExample example = new InvoiceApplyExample();
        example.setPage(page);
        InvoiceApplyExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(invoiceApply.getInvoiceCompany())){
            criteria.andInvoiceCompanyLike(invoiceApply.getInvoiceCompany());
        }
        if(StringUtils.isNotBlank(invoiceApply.getInvoiceNumber())){
            criteria.andInvoiceNumberEqualTo(invoiceApply.getInvoiceNumber());
        }
        if(StringUtils.isNotBlank(invoiceApply.getInvoiceCode())){
            criteria.andInvoiceCodeEqualTo(invoiceApply.getInvoiceCode());
        }
        if(StringUtils.isNotBlank(invoiceApply.getEsResult())){
            criteria.andEsResultEqualTo(invoiceApply.getEsResult());
        }
        if(StringUtils.isNotBlank(invoiceApply.getAgentId())){
            criteria.andAgentIdEqualTo(invoiceApply.getAgentId());
        }
        if(StringUtils.isNotBlank(invoiceApply.getYsResult())){
            criteria.andYsResultEqualTo(invoiceApply.getYsResult());
        }
        if (StringUtils.isNotBlank(invoiceApply.getAgentName())){
            criteria.andAgentNameLike(invoiceApply.getAgentName());
        }
        if(department != null){
            example.setInnerJoinDepartment(department.get("ORGANIZATIONCODE").toString(), department.get("ORGID").toString());
        }
        example.setOrderByClause("CREATE_DATE DESC ");
        List<InvoiceApply> list = invoiceApplyMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(list);
        pageInfo.setTotal((int)invoiceApplyMapper.countByExample(example));

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

    @Override
    public void saveInvoiceApply(List<Map<String,Object>> list, String agentId) throws MessageException{
        try {
            Agent agent = agentService.getAgentById(agentId);
            Map<String,Object> map1 = getAccessTocken();
            if("0000".equals(map1.get("errcode"))){
                tocken = map1.get("access_token").toString();
                for (Map<String,Object> map:list) {
                    InvoiceApply invoiceApply = new InvoiceApply();
                    Map<String,Object> result = getTicketInfo(map.get("invoiceCode").toString(),tocken);
                    if(result == null){//未在发票云找到该发票信息，将此信息返回
                        invoiceApply.setYsResult("0");
                        invoiceApply.setInvoiceCompany(map.get("sallerName").toString());
                        invoiceApply.setRemark("发票云库中未找到该发票信息");
                    }else{
                        invoiceApply.setYsResult("1");
                        if("1".equals(result.get("proxyMark").toString())){ // 代开
                            String remark = result.get("remark").toString();  // 获取代开公司
                            int flag = remark.indexOf("代开企业名称:");
                            if(flag != -1){
                                invoiceApply.setInvoiceCompany(remark.substring(flag+7));
                                invoiceApply.setSallerName(map.get("sallerName").toString());
                                invoiceApply.setRemark(remark);
                            }
                        }else {
                            invoiceApply.setInvoiceCompany(map.get("sallerName").toString());
                        }
                    }
                    if("1".equals(invoiceApply.getYsResult())){ // 初审通过
                        List<String> stringList = invoiceApplyMapper.getAgentIdByPayCompany(invoiceApply.getInvoiceCompany());
                        if(!(stringList.size() == 1  && stringList.get(0).equals(agentId))){
                            invoiceApply.setYsResult("0");
                            invoiceApply.setRev1("开票公司和该代理商不符！");
                        }
                    }
                    invoiceApply.setId(idService.genId(TabId.P_INVOICE_APPLY));
                    invoiceApply.setAgentId(agentId);
                    if(agent != null){
                        invoiceApply.setAgentName(agent.getAgName());
                    }
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
                    invoiceApply.setYsDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    invoiceApply.setStatus("0");
                    invoiceApplyMapper.insertSelective(invoiceApply);
                }
            }else{
                // 未获得tocken授权，请重新提交
            }
        }catch (Exception e){
           e.printStackTrace();
           throw  new MessageException("保存失败");
        }

    }

    @Override
    public List<Map<String,String>> finalCheckInvoice(List<Map<String,Object>> list){
        List<Map<String,String>> maps = new ArrayList<Map<String,String>>();
        for (Map<String,Object> map:list) {
            InvoiceApply invoiceApply = new InvoiceApply();
            invoiceApply.setSerialNo(map.get("serialNo").toString());
            invoiceApply.setInvoiceCode(map.get("invoiceCode").toString());
            invoiceApply.setStatus("0");
            List<InvoiceApply> invoiceApplies =  getListByExample(invoiceApply);
            if(invoiceApplies.size() >= 1){
                InvoiceApply invoiceApply1 = invoiceApplies.get(0);
                Calendar curr = Calendar.getInstance();
                curr.setTime(new Date(System.currentTimeMillis()));
                curr.add(Calendar.MONTH, -1);
                Map<String,Object> mmm = new HashMap<String,Object>();
                mmm.put("AGENT_ID",invoiceApply1.getAgentId());
                mmm.put("PROFIT_MONTH",new SimpleDateFormat("yyyyMM").format(curr.getTime()));
                mmm.put("INVOICE_AMT",invoiceApply1.getSumAmt());
                mmm.put("INVOICE_COMPANY",invoiceApply1.getInvoiceCompany());
                Map<String,Object> ma = invoiceSumService.getInvoiceFinalData(mmm);
                if(!"9999".equals(ma.get("returnCode").toString()) ){
                    Map<String,String> mm = new HashMap<String,String>();
                    mm.put("invoiceCode",invoiceApply.getInvoiceCode());
                    mm.put("errorInfo","汇总失败");
                    maps.add(mm);
                    invoiceApply1.setEsResult("0");
                }else{
                    invoiceApply1.setEsResult("1");
                    invoiceApply1.setStatus("1");
                }
                invoiceApply1.setProfitMonth(new SimpleDateFormat("yyyyMM").format(curr.getTime()));
                invoiceApplyMapper.updateByPrimaryKeySelective(invoiceApply1);
            }else{
                Map<String,String> mm = new HashMap<String,String>();
                mm.put("invoiceCode",invoiceApply.getInvoiceCode());
                mm.put("errorInfo","未找到该发票符合信息");
                maps.add(mm);
            }
        }
        return  maps;
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
       return  invoiceApplyMapper.selectByExample(example);
    }

    /**根据发票代码获取发票全部信息*/
    private Map<String,Object> getTicketInfo(String invoiceCode,String tocken)throws Exception{
           JSONObject param = new JSONObject();
           param.put("invoiceCode", invoiceCode);
           String result = HttpClientUtil.doPostJson(TICKET_INFO_URL+tocken,encrypt1(param.toJSONString()));
           logger.info("======获取========"+result+"===============");
           Map<String,Object> rr = JSONObject.parseObject(result);
           if("0000".equals(rr.get("errcode"))){
               List<Map<String,Object>> list = (List<Map<String,Object>>)rr.get("data");
               return list.get(0);
           }
           return null;
    }

    /**获取金蝶授权AccessTocken*/
    public  Map<String, Object> getAccessTocken() throws Exception{
        long timestamp = System.currentTimeMillis();
        JSONObject param = new JSONObject();
        param.put("client_id", CLIENT_ID);
        param.put("sign",getSign(timestamp));
        param.put("timestamp",timestamp);
        String result = HttpClientUtil.doPostJson(ACCESS_TOCKEN_URL, param.toJSONString());
        logger.info("=====获取金蝶授权：tocken======"+result+"=======");
        return JSONObject.parseObject(result);
    }

    //获取MD5加密sign
    private String getSign(long timestamp)throws Exception{
        String str = CLIENT_ID+CLIENT_SECRET+timestamp;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(str.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }

    /**
     * 使用ASE128进行加密
     */
    public static String encrypt1(String content) throws Exception {
        byte[] raw = PASSWORD.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
        return new org.apache.commons.codec.binary.Base64().encodeToString(encrypted);
    }

    /**
     * 使用ASE128进行解密
     */
    public static String decrypt1(String content) throws Exception {
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


}
