package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.ProfitStatus;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.profit.dao.InvoiceDetailMapper;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.InvoiceMapper;
import com.ryx.credit.profit.pojo.Invoice;
import com.ryx.credit.profit.pojo.InvoiceDetail;
import com.ryx.credit.profit.pojo.InvoiceDetailExample;
import com.ryx.credit.profit.service.IOwnInvoiceService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import java.util.*;

/**
 * @Author: Zhang Lei
 * @Description: 欠票管理
 * @Date: 9:40 2018/12/20
 */
@Service("ownInvoiceService")
public class OwnInvoiceServiceImpl implements IOwnInvoiceService {

    Logger logger = LoggerFactory.getLogger(ProfitSupplyTaxServiceImpl.class);

    @Autowired
    InvoiceDetailMapper invoiceDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    ProfitDetailMonthMapper profitDetailMonthMapper;

    @Autowired
    protected InvoiceMapper invoiceMapper;

    /**
     * @Author: Zhang Lei
     * @Description: 欠票导入或重导后重算
     * @Date: 17:38 2018/12/22
     */
    public void ownInvoiceReComputer(Invoice invoice) {
        try {
            String profitMonth = invoice.getFactorMonth();
            String agentId = invoice.getAgentId();
            InvoiceDetailExample example = new InvoiceDetailExample();
            InvoiceDetailExample.Criteria criteria = example.createCriteria();
            criteria.andAgentIdEqualTo(agentId);
            List<InvoiceDetail> list = invoiceDetailMapper.selectByExample(example);
            if (list != null && list.size() > 0) {
                // 更新
                InvoiceDetail invoiceDetail = list.get(0);
                BigDecimal ownInvoice = invoiceDetail.getPreLeftAmt()
                        .add(invoiceDetail.getDayProfitAmt())
                        .add(invoiceDetail.getDayBackAmt())
                        .add(invoiceDetail.getPreProfitMonthAmt())
                        .add(invoiceDetail.getPreProfitMonthBlAmt())
                        .add(invoiceDetail.getAdjustAmt())
                        .subtract(invoice.getInvoiceAmt());
                invoiceDetail.setOwnInvoice(ownInvoice);
                invoiceDetail.setAddInvoiceAmt(invoice.getInvoiceAmt());
                invoiceDetail.setUpdateTime(DateUtils.dateToStringss(new Date()));
            } else {
                //插入
                Map<String, Object> params = new HashMap<>();
                params.put("profitMonth", profitMonth);
                params.put("preMonth", new SimpleDateFormat("yyyyMM").format(DateUtil.addMonth(new Date(), -2)));
                params.put("agentId", invoice.getAgentId());
                List<Map<String, Object>> agentList = invoiceDetailMapper.queryInvoiceAgents(params);
            }
        } catch (Exception e) {
            logger.error("{" + invoice.getAgentId() + "}欠票导入或重导后重算失败", e);
            throw new RuntimeException("{" + invoice.getAgentId() + "}欠票导入或重导后重算失败");
        }
    }


    /**
     * @Author: Zhang Lei
     * @Description: 欠票调整
     * @Date: 11:50 2018/12/24
     */
    public void ownInvoiceAdjust(String id, BigDecimal adjustAmt, String adjustAccount, String adjustReson) {

        InvoiceDetail invoiceDetail = invoiceDetailMapper.selectByPrimaryKey(id);
        if (invoiceDetail == null) {
            throw new RuntimeException("记录不存在");
        }

        BigDecimal ownInvoice = invoiceDetail.getPreLeftAmt()
                .add(invoiceDetail.getDayProfitAmt())
                .add(invoiceDetail.getDayBackAmt())
                .add(invoiceDetail.getPreProfitMonthAmt())
                .add(invoiceDetail.getPreProfitMonthBlAmt())
                .add(adjustAmt)
                .subtract(invoiceDetail.getAddInvoiceAmt());
        invoiceDetail.setOwnInvoice(ownInvoice);
        invoiceDetail.setAdjustAccount(adjustAccount);
        invoiceDetail.setAdjustReson(adjustReson);
        invoiceDetail.setAdjustTime(DateUtils.dateToStringss(new Date()));
        invoiceDetail.setAdjustAmt(adjustAmt);
        invoiceDetail.setUpdateTime(DateUtils.dateToStringss(new Date()));
        invoiceDetailMapper.updateByPrimaryKeySelective(invoiceDetail);
    }

    /**
     * @Author: Zhang Lei
     * @Description: 代理商欠票计算
     * @Date: 11:57 2018/12/22
     */
    @Override
    public void invoiceOwnComputer(Map<String, Object> params) {
        String month = (String) params.get("profitMonth");
        final String profitMonth = month == null ? new SimpleDateFormat("yyyyMM").format(DateUtil.addMonth(new Date(), -1)) : month;
        params.put("profitMonth", profitMonth);
        params.put("preMonth", new SimpleDateFormat("yyyyMM").format(DateUtil.addMonth(new Date(), -2)));
        logger.info("=======欠票计算开始=======");
        try {
            //清除数据
            invoiceDetailMapper.deleteByMonth(profitMonth);

            List<Map<String, Object>> agentList = invoiceDetailMapper.queryInvoiceAgents(params);
            for (Map<String, Object> map : agentList) {

                //本月欠票=上月剩余欠票基数+本月日返现+本月日分润+上月月分润+上月瑞银信保理款+调整金额-本月实际到票
                BigDecimal ownInvoice = ((BigDecimal) map.get("PRE_LEFT_AMT"))
                        .add(((BigDecimal) map.get("DAYS_PROFIT_AMT")))
                        .add(((BigDecimal) map.get("RETURN_MONEY")))
                        .add(((BigDecimal) map.get("PRE_PROFIT_AMT")))
                        .add(((BigDecimal) map.get("BL_AMT")))
                        .add(((BigDecimal) map.get("ADJUST_AMT")))
                        .subtract(((BigDecimal) map.get("ADD_INVOICE_AMT")));
                map.put("ownInvoice", ownInvoice);
                insertInvoiceOwnDetail(map, profitMonth);
                logger.info("代理商{}本月欠票{}", map.get("AGENT_ID"), ownInvoice);

                if (ownInvoice.compareTo(BigDecimal.ZERO) > 0) {
                    //欠票冻结
                    ProfitDetailMonthExample example = new ProfitDetailMonthExample();
                    ProfitDetailMonthExample.Criteria criteria = example.createCriteria();
                    criteria.andAgentIdEqualTo((String) map.get("AGENT_ID"));
                    List<ProfitDetailMonth> list = profitDetailMonthMapper.selectByExample(example);
                    for (ProfitDetailMonth profitDetailMonth : list) {
                        profitDetailMonth.setStatus(ProfitStatus.STATUS_1.value());
                        profitDetailMonthMapper.updateByPrimaryKeySelective(profitDetailMonth);
                        logger.info("冻结{}代理商分润", map.get("AGENT_ID"));
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("代理商欠票计算出现异常错误",e);
        }
        logger.info("=======欠票计算結束=======");
    }

    /**
     * @Author: Zhang Lei
     * @Description: 插入欠票明细
     * @Date: 17:37 2018/12/22
     */
    public void insertInvoiceOwnDetail(Map<String, Object> map, String profitMonth) {
        InvoiceDetail invoiceDetail = new InvoiceDetail();
        invoiceDetail.setId(idService.genId(TabId.P_INVOICE_DETAIL));
        invoiceDetail.setAgentName((String) map.get("AGENT_NAME"));
        invoiceDetail.setAgentId((String) map.get("AGENT_ID"));
        invoiceDetail.setProfitMonth(profitMonth);
        invoiceDetail.setPreLeftAmt((BigDecimal) map.get("PRE_LEFT_AMT"));
        invoiceDetail.setDayProfitAmt((BigDecimal) map.get("DAYS_PROFIT_AMT"));
        invoiceDetail.setDayBackAmt((BigDecimal) map.get("RETURN_MONEY"));
        invoiceDetail.setPreProfitMonthAmt((BigDecimal) map.get("PRE_PROFIT_AMT"));
        invoiceDetail.setPreProfitMonthBlAmt((BigDecimal) map.get("BL_AMT"));
        invoiceDetail.setAddInvoiceAmt((BigDecimal) map.get("ADD_INVOICE_AMT"));
        invoiceDetail.setAdjustAmt((BigDecimal) map.get("ADJUST_AMT"));
        invoiceDetail.setOwnInvoice((BigDecimal) map.get("ownInvoice"));
        invoiceDetail.setCreateTime(DateUtils.dateToStringss(new Date()));
        invoiceDetailMapper.insertSelective(invoiceDetail);
    }

    /**
     * @Author CQT
     * @Description获取发票信息列表
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
        if(StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)){
            criteria.andProfitMonthBetween(dateStart,dateEnd);
        }else if(StringUtils.isNotBlank(dateStart)){
            criteria.andProfitMonthEqualTo(dateStart);
        }else if(StringUtils.isNotBlank(dateEnd)){
            criteria.andProfitMonthEqualTo(dateEnd);
        }
        if("1".equals(concludeChild)  && StringUtils.isNotBlank(agentId)){
            List<String> lists = invoiceDetailMapper.getAgentIdByBusParent(agentId);
            lists.add(agentId);
            criteria.andAgentIdIn(lists);
        }else if("1".equals(concludeChild) && StringUtils.isBlank(agentId) && StringUtils.isNotBlank(agentName)){
            String agentIde = invoiceDetailMapper.getAgentIdbyAgentName(agentName);
            List<String> lists = invoiceDetailMapper.getAgentIdByBusParent(agentIde);
            lists.add(agentIde);
            criteria.andAgentIdIn(lists);
        }else if(StringUtils.isNotBlank(agentId)){
            criteria.andAgentIdEqualTo(agentId);
        }
        List<InvoiceDetail> lists = invoiceDetailMapper.selectByExample(example);
        Long count =invoiceDetailMapper.countByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(lists);
        pageInfo.setTotal(count.intValue());
        return pageInfo;
    }

    /**
     * @Author CQT
     * 向欠票导入表中导入数据
     * @param datas
     * @param loginName
     */
    @Override
    public void exportData(List<List<Object>> datas,String loginName) {
        if(datas != null && datas.size() > 0 ) {
            datas.stream().filter(list->list!=null && list.size() > 0 && list.get(0) != null && list.get(1) != null && list.get(2) != null).forEach(list->{
                insertInvoice(list,loginName);
            });
        }
    }

    /**
     * @Author CQT
     * 根据id获取对应代理商数据信息
     * @param id
     * @return
     */
    @Override
    public InvoiceDetail getInvoiceById(String id) {
        return invoiceDetailMapper.selectByPrimaryKey(id);
    }

    /**
     * @Author CQT
     * 设置调整金额
     * @param invoiceDetail
     * @return
     */
    @Override
    public int setAdjustAMT(InvoiceDetail invoiceDetail) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        invoiceDetail.setUpdateTime(sdf.format(new Date()));
        invoiceDetail.setAdjustTime(sdf.format(new Date()));//设置调整时间
        return invoiceDetailMapper.updateByPrimaryKeySelective(invoiceDetail);
    }

    /**
     * @Author CQT
     * @Description导出数据
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
        InvoiceDetailExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(agentName)){
            criteria.andAgentNameEqualTo(agentName);
        }
        if(StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)){
            criteria.andProfitMonthBetween(dateStart,dateEnd);
        }else if(StringUtils.isNotBlank(dateStart)){
            criteria.andProfitMonthEqualTo(dateStart);
        }else if(StringUtils.isNotBlank(dateEnd)){
            criteria.andProfitMonthEqualTo(dateEnd);
        }
        if("1".equals(concludeChild) && StringUtils.isNotBlank(agentId)){
            List<String> lists = invoiceDetailMapper.getAgentIdByBusParent(agentId);
            lists.add(agentId);
            criteria.andAgentIdIn(lists);
        }else if("1".equals(concludeChild) && StringUtils.isBlank(agentId) && StringUtils.isNotBlank(agentName)){
            String agent = invoiceDetailMapper.getAgentIdbyAgentName(agentName);
            List<String> lists = invoiceDetailMapper.getAgentIdByBusParent(agent);
            lists.add(agent);
            criteria.andAgentIdIn(lists);
        }else if(StringUtils.isNotBlank(agentId)){
            criteria.andAgentIdEqualTo(agentId);
        }
        List<InvoiceDetail> lists = invoiceDetailMapper.selectByExample(example);
        return lists;
    }

    /**
     * @Author CQT
     *@Description 插入数据
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
            //表示存在有效记录，将其设置为无效
            invoiceMapper.setStatusToInvoice(invoice);
        }
        invoiceMapper.insertSelective(invoice);
    }

    /**
     * @Author CQT
     * @Description判断导入欠票表中是否存在有效且已有数据
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
