package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.InvoiceApplyMapper;
import com.ryx.credit.profit.dao.InvoiceDetailMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IAgeInvoiceApplyService;
import com.ryx.credit.profit.service.IOwnInvoiceService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代理商发票线上维护实现类
 * @Author chenqiutian
 * @Date 2019/2/18
 */
@Service("ageInvoiceApplyService")
public class AgeInvoiceApplyServiceImpl implements IAgeInvoiceApplyService {

    Logger logger = LoggerFactory.getLogger(AgeInvoiceApplyServiceImpl.class);

    @Autowired
    private InvoiceApplyMapper invoiceApplyMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    InvoiceDetailMapper invoiceDetailMapper;
    @Autowired
    IOwnInvoiceService ownInvoiceService;
    @Autowired
    private RedisService p_redisService;

    /**
     * 代理商获取发票申请明细
     */
    @Override
    public PageInfo agentGetInvoiceApplyList(InvoiceApply invoiceApply, Page page) {
        InvoiceApplyExample example = new InvoiceApplyExample();
        example.setPage(page);
        InvoiceApplyExample.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(invoiceApply.getInvoiceCompany())){
            criteria.andInvoiceCompanyEqualTo(invoiceApply.getInvoiceCompany());
        }
        if(StringUtils.isNotBlank(invoiceApply.getInvoiceNumber())){
            criteria.andInvoiceNumberEqualTo(invoiceApply.getInvoiceNumber());
        }
        if(StringUtils.isNotBlank(invoiceApply.getExpressNumber())){
            criteria.andExpressNumberEqualTo(invoiceApply.getExpressNumber());
        }
        if(StringUtils.isNotBlank(invoiceApply.getExpressDate())){
            criteria.andExpressDateEqualTo(invoiceApply.getExpressDate());
        }
        if(StringUtils.isNotBlank(invoiceApply.getAgentId())){
            criteria.andAgentIdEqualTo(invoiceApply.getAgentId());
        }
        if(StringUtils.isNotBlank(invoiceApply.getAgentName())){
            criteria.andAgentNameEqualTo(invoiceApply.getAgentName());
        }
        if("'1','2'".equals(invoiceApply.getShResult())){
            List<String> list = new ArrayList<String>();
            list.add("1");
            list.add("2");
            criteria.andShResultIn(list);
        }else if(StringUtils.isNotBlank(invoiceApply.getShResult())){
            criteria.andShResultEqualTo(invoiceApply.getShResult());
        }
        example.setOrderByClause("CREATE_DATE  desc");
        PageInfo pageInfo = new PageInfo();
        List<InvoiceApply> list = invoiceApplyMapper.selectByExample(example);
        Long count = invoiceApplyMapper.countByExample(example);
        pageInfo.setTotal(Integer.parseInt(count.toString()));
        pageInfo.setRows(list);
        return pageInfo;
    }

    @Override
    public void insertInvoiceApplyInfo(InvoiceApply invoiceApply) {
        invoiceApply.setId(idService.genId(TabId.P_INVOICE_APPLY));
        invoiceApply.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date()));
        invoiceApply.setShResult("0");  //申请中
        invoiceApplyMapper.insertSelective(invoiceApply);
    }

    @Override
    public InvoiceApply getInvoiceApplyById(String id) {
        return invoiceApplyMapper.selectByPrimaryKey(id);
    }

    @Override
    public void volumeImportData(List<List<Object>> lists,String agentId,String agentName) {
        if(lists.size()<=0){
            throw new ProcessException("导入数据为空");
        }
        try{
            for (List<Object> list:lists) {
                if(list.size()>0 && list!= null){
                    insertInvoiceApply(list,agentId,agentName);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ProcessException("导入数据格式不正确");
        }
    }

    private void insertInvoiceApply(List<Object> list,String agentId,String agentName){
        InvoiceApply invoiceApply = new InvoiceApply();
        invoiceApply.setId(idService.genId(TabId.P_INVOICE_APPLY));
        invoiceApply.setInvoiceCompany(list.get(0).toString());
        invoiceApply.setInvoiceDate(list.get(1).toString());
        String str = list.get(2).toString();
        if(str.indexOf(".") != -1){
            str = str.substring(0,str.indexOf("."));
        }
        invoiceApply.setInvoiceNumber(str);
        String str1 = list.get(3).toString();
        if(str1.indexOf(".") != -1){
            str1= str1.substring(0,str1.indexOf("."));
        }
        invoiceApply.setInvoiceCode(str1);
        invoiceApply.setInvoiceItem(list.get(4).toString());
        invoiceApply.setUnitPrice(new BigDecimal(list.get(5).toString()));
        String str2 = list.get(6).toString().trim();
        invoiceApply.setNumberSl(Long.valueOf(str2.substring(0,str2.indexOf("."))));
        invoiceApply.setAmountBeforeTax(new BigDecimal(list.get(7).toString()));
        invoiceApply.setTax(new BigDecimal(list.get(8).toString()));
        invoiceApply.setAmountTax(new BigDecimal(list.get(9).toString()));
        invoiceApply.setSumAmt(new BigDecimal(list.get(10).toString()));
        invoiceApply.setExpressDate(list.get(11).toString());
        invoiceApply.setExpressCompany(list.get(12).toString());
        String str3 = list.get(13).toString();
        if(str3.indexOf(".") != -1){
            str3 = str3.substring(0,str3.indexOf("."));
        }
        invoiceApply.setExpressNumber(str3);
        invoiceApply.setShResult("0"); //申请中
        invoiceApply.setCreateDate(new SimpleDateFormat("yyyy-MM-dd HH:SS:mm").format(new Date()));
        invoiceApply.setAgentName(agentName);
        invoiceApply.setAgentId(agentId);
        invoiceApplyMapper.insertSelective(invoiceApply);
    }


    @Override
    public InvoiceApply getInvoiceApplyByInvoiceNumber(String invoiceNumber,String agentId) {
        InvoiceApplyExample example = new InvoiceApplyExample();
        InvoiceApplyExample.Criteria criteria = example.createCriteria();
        criteria.andInvoiceNumberEqualTo(invoiceNumber);
        criteria.andAgentIdEqualTo(agentId);
        List<InvoiceApply> list = invoiceApplyMapper.selectByExample(example);
        if(list.size() > 0){
            return  list.get(0);
        }
        return null;
    }

    @Override
    public void insertInvoiceApply(InvoiceApply invoiceApply) {
        invoiceApplyMapper.updateByPrimaryKeySelective(invoiceApply);
    }

    @Override
    public PageInfo getList(Page page, InvoiceApply invoiceApply) {
        InvoiceApplyExample example = new InvoiceApplyExample();
        example.setPage(page);
        InvoiceApplyExample.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(invoiceApply.getAgentName())){
            criteria.andAgentNameEqualTo(invoiceApply.getAgentName());
        }
        if(StringUtils.isNotBlank(invoiceApply.getAgentId())){
            criteria.andAgentIdEqualTo(invoiceApply.getAgentId());
        }
        if(StringUtils.isNotBlank(invoiceApply.getExpressNumber())){
            criteria.andExpressNumberEqualTo(invoiceApply.getExpressNumber());
        }
        if(StringUtils.isNotBlank(invoiceApply.getExpressDate())){
            criteria.andExpressDateEqualTo(invoiceApply.getExpressDate());
        }
        if("0".equals(invoiceApply.getShResult())){
            criteria.andShResultEqualTo(invoiceApply.getShResult());
            criteria.andFilenameIsNotNull();
        }

        List<InvoiceApply> list = invoiceApplyMapper.selectByExample(example);
        Long count = invoiceApplyMapper.countByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(list);
        pageInfo.setTotal(Integer.valueOf(count.toString()));
        return pageInfo;
    }

    @Override
    public void commitSHResult(InvoiceApply invoiceApply) {
        //获取终审状态
        String finalStatus = p_redisService.getValue("commitFinal");
        if("1".equals(finalStatus)){//终审状态
            String profit_Month = new SimpleDateFormat("yyyyMM").format(new Date());
            invoiceApply.setProfitMonth(profit_Month);
        }else{//非终审状态
            Calendar curr = Calendar.getInstance();
            curr.setTime(new Date(System.currentTimeMillis()));
            curr.add(Calendar.MONTH, -1);
            SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("yyyyMM");
            String profitMonth = simpleDateFormatMonth.format(curr.getTime());
            invoiceApply.setProfitMonth(profitMonth);
        }
        invoiceApply.setShDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        invoiceApplyMapper.updateByPrimaryKeySelective(invoiceApply);
        if("1".equals(invoiceApply.getShResult())){
            //根据id获得数据
            InvoiceApply invoiceApply1 = invoiceApplyMapper.selectByPrimaryKey(invoiceApply.getId());
            try{
                ownInvoiceService.invoiceApplyComputer(invoiceApply1);
            }catch (Exception e){
                throw new ProcessException("欠票汇总失败");

            }
        }
    }
}
