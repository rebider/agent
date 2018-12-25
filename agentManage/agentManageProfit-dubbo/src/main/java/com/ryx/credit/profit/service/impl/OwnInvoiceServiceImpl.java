package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.InvoiceDetailMapper;
import com.ryx.credit.profit.dao.InvoiceMapper;
import com.ryx.credit.profit.pojo.Invoice;
import com.ryx.credit.profit.pojo.InvoiceDetail;
import com.ryx.credit.profit.pojo.InvoiceDetailExample;
import com.ryx.credit.profit.service.IOwnInvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Author: Zhang Lei
 * @Description: 欠票管理
 * @Date: 9:40 2018/12/20
 */
@Service("ownInvoiceService")
public class OwnInvoiceServiceImpl implements IOwnInvoiceService {

    @Autowired
    private InvoiceDetailMapper invoiceDetailMapper;

    @Autowired
    protected InvoiceMapper invoiceMapper;

    private static Logger logger = LoggerFactory.getLogger(OwnInvoiceServiceImpl.class);


    /**
     * 获取发票信息列表
     * @param page
     * @return
     */
    @Override
    public PageInfo getInvoiceDetailList(Page page, String agentId, String agentName, String concludeChild, String dateStart, String dateEnd) {
        InvoiceDetailExample example = new InvoiceDetailExample();
        example.setPage(page);
        InvoiceDetailExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(agentName)){
            criteria.andAgentNameEqualTo(agentName);
        }
        if(StringUtils.isNotBlank(dateStart) || StringUtils.isNotBlank(dateEnd)){
            if(StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)){
                criteria.andProfitMonthBetween(dateStart,dateEnd);
            }else if(StringUtils.isNotBlank(dateStart)){
                criteria.andProfitMonthEqualTo(dateStart);
            }else{
                criteria.andProfitMonthEqualTo(dateEnd);
            }
        }
        //如果查询条件：包含下级
        if("1".equals(concludeChild)  && StringUtils.isNotBlank(agentId)){
            List<String> lists = invoiceDetailMapper.getAgentIdByBusParent(agentId);
            lists.add(agentId);
            criteria.andAgentIdIn(lists);
        }else{
            if(StringUtils.isNotBlank(agentId)){
                criteria.andAgentIdEqualTo(agentId);
            }
        }
        List<InvoiceDetail> lists = invoiceDetailMapper.selectByExample(example);
        int count = invoiceDetailMapper.countByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(lists);
        pageInfo.setTotal(count);
        return pageInfo;
    }

    /**
     *向欠票导入表中导入数据
     */
    @Override
    @Transactional
    public void exportData(List<List<Object>> datas,String loginName) {
        if(datas != null && datas.size() > 0 ) {
            datas.stream().filter(list->list!=null && list.size() > 0 && list.get(0) != null && list.get(1) != null && list.get(2) != null).forEach(list->{
                insertInvoice(list,loginName);
            });
        }
    }

    /**
     * 根据id获取对应代理商数据信息
     * @param id
     * @return
     */
    @Override
    public InvoiceDetail getInvoiceById(String id) {
        return invoiceDetailMapper.selectByPrimaryKey(id);
    }

    /**
     * 设置调整金额
     * @param invoiceDetail
     * @return
     */
    @Override
    public int setAdjustAMT(InvoiceDetail invoiceDetail) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        invoiceDetail.setUpdateTime(sdf.format(new Date()));
        return invoiceDetailMapper.updateByPrimaryKeySelective(invoiceDetail);
    }

    /**
     * 导出数据：
     * @param agentId
     * @param agentName
     * @param concludeChild
     * @param dateStart
     * @param dateEnd
     * @return
     */
    @Override
    public List<InvoiceDetail> exportInvoiceData(String agentId, String agentName, String concludeChild, String dateStart, String dateEnd) {
        InvoiceDetailExample example = new InvoiceDetailExample();
       //example.setPage(page);
        InvoiceDetailExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(agentName)){
            criteria.andAgentNameEqualTo(agentName);
        }
        if(StringUtils.isNotBlank(dateStart) || StringUtils.isNotBlank(dateEnd)){
            if(StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)){
                criteria.andProfitMonthBetween(dateStart,dateEnd);
            }else if(StringUtils.isNotBlank(dateStart)){
                criteria.andProfitMonthEqualTo(dateStart);
            }else{
                criteria.andProfitMonthEqualTo(dateEnd);
            }
        }
        if("1".equals(concludeChild) && StringUtils.isNotBlank(agentId)){
            List<String> lists = invoiceDetailMapper.getAgentIdByBusParent(agentId);
            lists.add(agentId);
            criteria.andAgentIdIn(lists);
        }else{
            if(StringUtils.isNotBlank(agentId)){
                criteria.andAgentIdEqualTo(agentId);
            }
        }
        List<InvoiceDetail> lists = invoiceDetailMapper.selectByExample(example);
        return lists;
    }

    /**
     * 插入数据
     * @param list
     * @param loginName
     */
    private void insertInvoice(List list,String loginName){
        Invoice invoice = new Invoice();
        String agentId = list.get(1).toString();
        String agentName = list.get(0).toString();
        BigDecimal amt = new BigDecimal(list.get(2).toString());
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().replace("-","");
        invoice.setId(str); //设置id
        invoice.setAgentId(agentId);
        invoice.setAgentName(agentName);
        String agentPid = invoiceDetailMapper.getAgentPidByAgentId(agentId);//获取代理商唯一码
        invoice.setAgentPid(agentPid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");//将每次导入的欠票月份当设为当月
        invoice.setFactorMonth(sdf.format(new Date()));
        invoice.setInvoiceAmt(amt);
        invoice.setStatus("1");
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        invoice.setOptDate(ss.format(new Date()));
        invoice.setOptUser(loginName);
        boolean isHave = getInvoiceByInvoice(invoice);
        if(isHave == false){
            //表示存在有效记录，更改数据状态
            invoiceMapper.setStatusToInvoice(invoice);
        }
        invoiceMapper.insertSelective(invoice);
    }

    /**
     * 判断导入欠票表中是否存在有效且已有数据
     * 有效：指月份，id，name，相同，且staus=1
     * @param invoice
     * @return  不存在，返回true
     */
    private boolean getInvoiceByInvoice(Invoice invoice){
        Invoice invoce = invoiceMapper.getInvoiceByInvoice(invoice);
        if(invoce == null){
            return true;
        }
        return false;
    }


}
