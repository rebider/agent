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

    private final static String[] ARRAY_INVOICE = {"研发和技术服务","研发服务","信息技术服务","软件服务","信息系统服务","现代服务","技术服务","信息技术服务","咨询服务","服务费"};


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
    public PageInfo queryInvoiceDetail(InvoiceApply invoiceApply, Page page,Map<String, Object> department,boolean flag) {
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
            criteria.andAgentNameLike("%"+invoiceApply.getAgentName()+"%");
        }
        if(flag){
            criteria.andExpressCompanyIsNotNull();
            criteria.andExpressDateIsNotNull();
            criteria.andExpressNumberIsNotNull();
        }
        /*if(department != null){
            example.setInnerJoinDepartment(department.get("ORGANIZATIONCODE").toString(), department.get("ORGID").toString());
        }*/
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

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public void saveInvoiceApply(List<Map<String,Object>> list, String agentId) throws MessageException{
        try {
            Agent agent = agentService.getAgentById(agentId);

            if(agent == null){
                throw new MessageException("未获取到该代理商的信息！");
            }

            List<InvoiceApply> invoiceApplyList = new ArrayList<InvoiceApply>();
            Map<String,BigDecimal> mapInvoice = new HashMap<String,BigDecimal>();
            Map<String,Object> map1 = getAccessTocken();

            if("0000".equals(map1.get("errcode"))){
                // 获取金蝶token授权
                tocken = map1.get("access_token").toString();
                for (Map<String,Object> map:list) {

                    InvoiceApply invoiceApply = new InvoiceApply();

                    //获取发票全部信息
                    Map<String,Object> result = getTicketInfo(map.get("invoiceCode").toString(),map.get("invoiceNo").toString(),tocken);
                    if(result == null){
                        invoiceApply.setYsResult("0");
                        invoiceApply.setInvoiceCompany(map.get("sallerName").toString());
                        invoiceApply.setRev1("发票云库中未找到该发票信息");
                    }else {
                        invoiceApply.setYsResult("1");
                        if ("1".equals(result.get("proxyMark").toString())) { // 代开
                            String remark = result.get("remark").toString();  // 获取发票备注
                            int flag = remark.indexOf("代开企业名称:");// 截取代开企业名称
                            if (flag != -1) {
                                String string = remark.substring(flag + 7);
                                List<String> stringList = invoiceApplyMapper.getPayCompanyById(agentId);
                                if(stringList.contains(string.trim())){
                                    invoiceApply.setInvoiceCompany(string.trim());
                                }else{
                                    int brdex = string.indexOf("<br/>");
                                    if(brdex != -1 ){
                                        invoiceApply.setInvoiceCompany(string.substring(0,brdex));
                                    }else{
                                        invoiceApply.setYsResult("0");
                                        invoiceApply.setRev1("获取开票公司名称错误,请在备注中开票公司名称后面使用换行符!");
                                    }
                                }
                                invoiceApply.setSallerName(map.get("sallerName").toString());
                                invoiceApply.setRemark(remark);
                            } else {
                                invoiceApply.setYsResult("0");
                                invoiceApply.setInvoiceCompany(map.get("sallerName").toString());
                                invoiceApply.setRev1("未获取到代开公司数据,请在备注中开票公司前添加'代开企业名称:'字段！");
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

                    //判断发票类型
                    if("1".equals(invoiceApply.getYsResult())){
                        if("4".equals(map.get("invoiceType").toString())){
                            invoiceApply.setYsResult("1");
                        }else{
                            invoiceApply.setYsResult("0");
                            invoiceApply.setRev1("该发票类型不是'专用纸质发票'类型！");
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
                            if(invoiceTax.compareTo(agentTax) != 0){
                                invoiceApply.setYsResult("0");
                                invoiceApply.setRev1("该发票税点和代理商税点不相同");
                            }
                        }else {
                            invoiceApply.setYsResult("0");
                            invoiceApply.setRev1("获取发票税点错误！");
                        }
                    }

                    invoiceApply.setId(idService.genId(TabId.P_INVOICE_APPLY));
                    invoiceApply.setAgentId(agentId);
                    if(StringUtils.isNotBlank(agent.getAgName())){
                        invoiceApply.setAgentName(agent.getAgName());
                    }else {
                        throw new MessageException("该代理商的名称为空，请检查！");
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

                    // 对发票金额进行判断，若通过初审金额大于欠票金额 则不能导入
                    if("1".equals(invoiceApply.getYsResult())){
                        //本月已初审的票
                        BigDecimal bg = invoiceApplyMapper.getSumInvoice(invoiceApply.getAgentId(),invoiceApply.getInvoiceCompany(),new SimpleDateFormat("yyyy-MM").format(new Date()));
                        bg = bg == null ? BigDecimal.ZERO : bg;
                        BigDecimal mg = mapInvoice.get(invoiceApply.getInvoiceCompany()) == null ? BigDecimal.ZERO : mapInvoice.get(invoiceApply.getInvoiceCompany());
                        mg = mg.add(invoiceApply.getSumAmt());
                        // 获取本月该欠票公司的欠票金额
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(new Date());
                        cal.add(Calendar.MONTH, -1);
                        String month = sdf.format(cal.getTime());
                        BigDecimal ownInvoice = invoiceApplyMapper.getOwnInvoice(invoiceApply.getAgentId(),invoiceApply.getInvoiceCompany(),month);
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

                //将通过的初审，或者初审出现错误的数据保存
                insertList(invoiceApplyList);
            }
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

    @Override
    public List<Map<String,String>> finalCheckInvoice(List<Map<String,Object>> list,String user){
        List<Map<String,String>> maps = new ArrayList<Map<String,String>>();
        for (Map<String,Object> map:list) {
            InvoiceApply invoiceApply = new InvoiceApply();
            invoiceApply.setInvoiceNumber(map.get("invoiceNo").toString());
            invoiceApply.setInvoiceCode(map.get("invoiceCode").toString());
            invoiceApply.setYsResult("1");
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
                mmm.put("user",user);
                Map<String,Object> ma = invoiceSumService.getInvoiceFinalData(mmm);
                if(!"9999".equals(ma.get("returnCode").toString()) ){
                    Map<String,String> mm = new HashMap<String,String>();
                    mm.put("invoiceCode",invoiceApply.getInvoiceCode()+"（发票代码："+invoiceApply.getInvoiceNumber()+")");
                    mm.put("errorInfo","汇总失败："+ma.get("returnInfo").toString());
                    maps.add(mm);
                    invoiceApply1.setEsResult("0");
                }else{
                    invoiceApply1.setEsResult("1");
                    invoiceApply1.setStatus("1");
                }
                invoiceApply1.setEsDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
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
        logger.info("======获取========"+result+"===============");
        Map<String,Object> rr = JSONObject.parseObject(result);
        if("0000".equals(rr.get("errcode"))){
            List<Map<String,Object>> list = (List<Map<String,Object>>)rr.get("data");
            return list.get(0);
        }
        return null;
    }

    /**获取金蝶授权AccessTocken*/
    public  Map<String, Object> getAccessTocken() throws MessageException{
       try {
           long timestamp = System.currentTimeMillis();
           JSONObject param = new JSONObject();
           param.put("client_id", CLIENT_ID);
           param.put("sign",getSign(timestamp));
           param.put("timestamp",timestamp);
           logger.info("ACCESS_TOCKEN_URL:-"+ACCESS_TOCKEN_URL+"-----param:"+param.toJSONString());
           String result = HttpClientUtil.doPostJson(ACCESS_TOCKEN_URL, param.toJSONString());
           logger.info("=====获取金蝶授权：tocken======"+result+"=======");
           return JSONObject.parseObject(result);
       }catch (Exception e){
           e.printStackTrace();
           throw new MessageException("获取金蝶tocken授权失败,请重试！");
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
    public static String encrypt1(String content) throws Exception {
        byte[] raw = PASSWORD.getBytes("utf-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
        return new org.apache.commons.codec.binary.Base64().encodeToString(encrypted);
    }

    /*** 使用ASE128进行解密*/
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

    @Override
    public List<Map<String, Object>> exports(InvoiceApply invoiceApply) {
        return invoiceApplyMapper.exports(invoiceApply);
    }
}
