package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.PDataAdjustType;
import com.ryx.credit.common.enumc.ProfitStatus;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.profit.dao.InvoiceDetailMapper;
import com.ryx.credit.profit.dao.PDataAdjustMapper;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.InvoiceMapper;
import com.ryx.credit.profit.pojo.Invoice;
import com.ryx.credit.profit.service.IOwnInvoiceService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    @Autowired
    private PDataAdjustMapper pDataAdjustMapper;

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
            criteria.andProfitMonthEqualTo(profitMonth);
            List<InvoiceDetail> list = invoiceDetailMapper.selectByExample(example);
            if (list != null && list.size() > 0) {
                // 更新
                InvoiceDetail invoiceDetail = list.get(0);
                invoiceDetail.setDrAddInvoiceAmt(invoice.getInvoiceAmt());  //线下
                invoiceDetail.setAddInvoiceAmt(invoiceDetail.getDrAddInvoiceAmt().add(invoiceDetail.getShAddInvoceAmt())); //总到票
                BigDecimal ownInvoice = invoiceDetail.getPreLeftAmt()  //欠票
                        .add(invoiceDetail.getDayProfitAmt())
                        .add(invoiceDetail.getDayBackAmt())
                        .add(invoiceDetail.getPreProfitMonthAmt())
                        .add(invoiceDetail.getPreProfitMonthBlAmt())
                        .add(invoiceDetail.getAdjustAmt())
                        .subtract(invoiceDetail.getAddInvoiceAmt());
                invoiceDetail.setOwnInvoice(ownInvoice);
                invoiceDetail.setUpdateTime(DateUtils.dateToStringss(new Date()));
                invoiceDetailMapper.updateByPrimaryKeySelective(invoiceDetail);
            } else {
                //插入
                Map<String, Object> params = new HashMap<>();
                params.put("profitMonth", profitMonth);
                params.put("preMonth", new SimpleDateFormat("yyyyMM").format(DateUtil.addMonth(new Date(), -2)));
                params.put("agentId", invoice.getAgentId());
                List<Map<String, Object>> agentList = invoiceDetailMapper.queryInvoiceAgents(params);
                if (agentList != null && agentList.size() > 0) {
                    Map<String, Object> map = agentList.get(0);
                    insertInvoiceOwnDetail(map, profitMonth);
                }
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
        invoiceDetail.setDrAddInvoiceAmt((BigDecimal) map.get("DR_ADD_INVOICE_AMT"));
        invoiceDetail.setShAddInvoceAmt((BigDecimal) map.get("SH_ADD_INVOCE_AMT"));
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
    public PageInfo getInvoiceDetailList(Page page, Map<String,String> map) {
        InvoiceDetailExample example = new InvoiceDetailExample();
        example.setPage(page);
        InvoiceDetailExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(map.get("dateStart")) && StringUtils.isNotBlank(map.get("dateEnd"))){
            criteria.andProfitMonthBetween(map.get("dateStart"),map.get("dateEnd"));
        }else if(StringUtils.isNotBlank(map.get("dateStart"))){
            criteria.andProfitMonthGreaterThanOrEqualTo(map.get("dateStart"));
        }else if(StringUtils.isNotBlank(map.get("dateEnd"))){
            criteria.andProfitMonthLessThanOrEqualTo(map.get("dateEnd"));
        }
        if (!"1".equals(map.get("concludeChild")) && StringUtils.isNotBlank(map.get("agentId"))) {
            criteria.andAgentIdEqualTo(map.get("agentId"));
        }
        if(!"1".equals(map.get("concludeChild")) && StringUtils.isNotBlank(map.get("agentName"))){
            criteria.andAgentNameEqualTo(map.get("agentName"));
        }
        if ("1".equals(map.get("concludeChild")) && StringUtils.isNotBlank(map.get("agentId"))) {
            List<String> lists = invoiceDetailMapper.getAgentIdByBusParent(map.get("agentId"));
            lists.add(map.get("agentId"));
            criteria.andAgentIdIn(lists);
        } else if ("1".equals(map.get("concludeChild")) && StringUtils.isBlank(map.get("agentId")) && StringUtils.isNotBlank(map.get("agentName"))) {
           String aId = invoiceDetailMapper.getAgentIdbyAgentName(map.get("agentName"));
            List<String> lists = invoiceDetailMapper.getAgentIdByBusParent(aId);
            lists.add(aId);
            criteria.andAgentIdIn(lists);
        }

        List<InvoiceDetail> lists = invoiceDetailMapper.selectByExample(example);
        Long count = invoiceDetailMapper.countByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(lists);
        pageInfo.setTotal(count.intValue());
        return pageInfo;
    }

    /**
     * @param datas
     * @param loginName
     * @Author CQT
     * 向欠票导入表中导入数据
     */
    @Override
    public void exportData(List<List<Object>> datas, String loginName)throws RuntimeException{
        if(datas == null || datas.size() == 0){
            throw new RuntimeException("导入的数据为空");
        }
        for (List<Object> list : datas) {

            if(list.size() != 3 || StringUtils.isBlank(list.get(0).toString()) || StringUtils.isBlank(list.get(1).toString()) ||StringUtils.isBlank(list.get(2).toString())){
                throw new RuntimeException("导入的数据存在空数据");
            }

            try{
                insertInvoice(list, loginName);
            }catch(Exception e){
                logger.info(e.getMessage());
                throw new RuntimeException("数据格式异常！请检查文件格式");
            }

        }
           /* if (datas != null && datas.size() > 0) {
                datas.stream().filter(list -> list != null && list.size()== 3 && list.get(0) != null && list.get(1) != null && list.get(2) != null).forEach(list -> {
                    insertInvoice(list, loginName);
                });
            }*/
    }

    /**
     * @param id
     * @return
     * @Author CQT
     * 根据id获取对应代理商数据信息
     */
    @Override
    public InvoiceDetail getInvoiceById(String id) {
        return invoiceDetailMapper.selectByPrimaryKey(id);
    }

    /**
     * @param invoiceDetail
     * @return
     * @Author CQT
     * 设置调整金额
     */
    @Override
    @Transactional
    public void setAdjustAMT(InvoiceDetail invoiceDetail,InvoiceDetail adjustDetail) {
        ownInvoiceAdjust(invoiceDetail.getId(), new BigDecimal(invoiceDetail.getAdjustAmt().toString()), invoiceDetail.getAdjustAccount(), invoiceDetail.getAdjustReson());

        //记录调整
        PDataAdjust pDataAdjust = new PDataAdjust();
        pDataAdjust.setId(idService.genId(TabId.P_DATA_ADJUST));
        pDataAdjust.setProfitMonth(invoiceDetail.getProfitMonth());
        pDataAdjust.setAgentId(invoiceDetail.getAgentId());
        pDataAdjust.setParentAgentId(invoiceDetail.getParentAgentId());
        pDataAdjust.setAdjustType(PDataAdjustType.QP.adjustType);
        pDataAdjust.setAdjustAmt(adjustDetail.getAdjustAmt());
        pDataAdjust.setAdjustAccount(invoiceDetail.getAdjustAccount());
        pDataAdjust.setAdjustReson(invoiceDetail.getAdjustReson());
        pDataAdjust.setAdjustTime(DateUtils.dateToStringss(new Date()));
        pDataAdjustMapper.insertSelective(pDataAdjust);
    }

    /**
     * @param agentId
     * @param agentName
     * @param concludeChild
     * @param dateStart
     * @param dateEnd
     * @return
     * @Author CQT
     * @Description导出数据
     */
    @Override
    public List<InvoiceDetail> exportInvoiceData(String agentId, String agentName, String concludeChild, String dateStart, String dateEnd) {
        InvoiceDetailExample example = new InvoiceDetailExample();
        InvoiceDetailExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)){
            criteria.andProfitMonthBetween(dateStart,dateEnd);
        }else if(StringUtils.isNotBlank(dateStart)){
            criteria.andProfitMonthGreaterThanOrEqualTo(dateStart);
        }else if(StringUtils.isNotBlank(dateEnd)){
            criteria.andProfitMonthLessThanOrEqualTo(dateEnd);
        }
        if ("1".equals(concludeChild) && StringUtils.isNotBlank(agentId)) {
            List<String> lists = invoiceDetailMapper.getAgentIdByBusParent(agentId);
            lists.add(agentId);
            criteria.andAgentIdIn(lists);
        } else if ("1".equals(concludeChild) && StringUtils.isBlank(agentId) && StringUtils.isNotBlank(agentName)) {
            String aId = invoiceDetailMapper.getAgentIdbyAgentName(agentName);
            List<String> lists = invoiceDetailMapper.getAgentIdByBusParent(aId);
            lists.add(aId);
            criteria.andAgentIdIn(lists);
        }else if (StringUtils.isNotBlank(agentId)) {
            criteria.andAgentIdEqualTo(agentId);
        }else if(StringUtils.isNotBlank(agentName)){
            criteria.andAgentNameEqualTo(agentName);
        }
        List<InvoiceDetail> lists = invoiceDetailMapper.selectByExample(example);
        return lists;
    }

    @Override
    public Map<String, Object> profitCount(Map<String, Object> param) {
        Map<String,Object> map=null;
        InvoiceDetailExample example = new InvoiceDetailExample();
        InvoiceDetailExample.Criteria criteria = example.createCriteria();
        String dateStart=param.get("dateStart")==null?null:param.get("dateStart").toString();
        String dateEnd=param.get("dateEnd")==null?null:param.get("dateEnd").toString();
        String agentId=param.get("agentId")==null?null:param.get("agentId").toString();
        String agentName=param.get("agentName")==null?null:param.get("agentName").toString();
        String concludeChild=param.get("concludeChild")==null?null:param.get("concludeChild").toString();
        if(StringUtils.isNotBlank(dateStart) && StringUtils.isNotBlank(dateEnd)){
            criteria.andProfitMonthBetween(dateStart,dateEnd);
        }else if(StringUtils.isNotBlank(dateStart)){
            criteria.andProfitMonthGreaterThanOrEqualTo(dateStart);
        }else if(StringUtils.isNotBlank(dateEnd)){
            criteria.andProfitMonthLessThanOrEqualTo(dateEnd);
        }
        if ("1".equals(concludeChild) && StringUtils.isNotBlank(agentId)) {
            List<String> lists = invoiceDetailMapper.getAgentIdByBusParent(agentId);
            lists.add(agentId);
            criteria.andAgentIdIn(lists);
        } else if ("1".equals(concludeChild) && StringUtils.isBlank(agentId) && StringUtils.isNotBlank(agentName)) {
            String aId = invoiceDetailMapper.getAgentIdbyAgentName(agentName);
            List<String> lists = invoiceDetailMapper.getAgentIdByBusParent(aId);
            lists.add(aId);
            criteria.andAgentIdIn(lists);
        }else if (StringUtils.isNotBlank(agentId)) {
            criteria.andAgentIdEqualTo(agentId);
        }else if(StringUtils.isNotBlank(agentName)){
            criteria.andAgentNameEqualTo(agentName);
        }
        map=invoiceDetailMapper.profitCount(example);
        return map;
    }

    @Override
    public Map<String, Object> getOwmInvoice(Map<String, String> map) {
        return invoiceDetailMapper.getOwnInvoice(map);
    }

    /**
     * @param list
     * @param loginName
     * @Author CQT
     * @Description 插入数据
     */
    private void insertInvoice(List list, String loginName) {
        Invoice invoice = new Invoice();
        String agentId = list.get(1).toString();
        String agentName = list.get(0).toString();
        BigDecimal amt = new BigDecimal(list.get(2).toString());
        //获取系统当前时间
        Calendar curr = Calendar.getInstance();
        curr.setTime(new Date(System.currentTimeMillis()));
        curr.add(Calendar.MONTH, -1);
        SimpleDateFormat simpleDateFormatMonth = new SimpleDateFormat("yyyyMM");
        String profitMonth = simpleDateFormatMonth.format(curr.getTime());
        //设置id
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString().replace("-", "");

        invoice.setId(str); //设置id
        invoice.setFactorMonth(profitMonth);
        invoice.setAgentId(agentId);
        invoice.setAgentName(agentName);
        String agentPid = invoiceDetailMapper.getAgentPidByAgentId(agentId);//获取代理商唯一码
        invoice.setAgentPid(agentPid);
        invoice.setInvoiceAmt(amt);
        invoice.setStatus("1");
        SimpleDateFormat ss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        invoice.setOptDate(ss.format(new Date()));
        invoice.setOptUser(loginName);

        boolean isHave = getInvoiceByInvoice(invoice);
        if (isHave == false) {
            //表示存在有效记录，将其设置为无效
            invoiceMapper.setStatusToInvoice(invoice);
        }

        invoiceMapper.insertSelective(invoice);//插入欠票表中
        ownInvoiceReComputer(invoice);//欠票导入或者重导后计算
    }

    /**
     * @param invoice
     * @return 不存在，返回true
     * @Author CQT
     * @Description判断导入欠票表中是否存在有效且已有数据
     */
    private boolean getInvoiceByInvoice(Invoice invoice) {
        Invoice invoce = invoiceMapper.getInvoiceByInvoice(invoice);
        if (invoce == null) {
            return true;
        }
        return false;
    }


    @Override
    public List<InvoiceDetail> getAgentInvoiceDetailList(Page page, String loginName, InvoiceDetail invoiceDetail) {
        InvoiceDetailExample example = new InvoiceDetailExample();
        example.setOrderByClause("PROFIT_MONTH desc");
        example.setPage(page);
        InvoiceDetailExample.Criteria criteria = example.createCriteria();
        criteria.andAgentIdEqualTo(loginName);
        if(StringUtils.isNotBlank(invoiceDetail.getAgentName())){
            criteria.andAgentNameEqualTo(invoiceDetail.getAgentName());
        }
        if(StringUtils.isNotBlank(invoiceDetail.getProfitMonth())){
            criteria.andProfitMonthEqualTo(invoiceDetail.getProfitMonth());
        }
        return invoiceDetailMapper.selectByExample(example);
    }

    /**
     * 线上维护，计算欠票
     */
    @Override
    public void invoiceApplyComputer(InvoiceApply invoiceApply) {
        String profitMonth = invoiceApply.getProfitMonth();
        String agentId = invoiceApply.getAgentId();

        InvoiceDetailExample example = new InvoiceDetailExample();//根据agentId 和月份判断本月数据信息是否存在
        InvoiceDetailExample.Criteria criteria = example.createCriteria();
        criteria.andAgentIdEqualTo(agentId);
        criteria.andProfitMonthEqualTo(profitMonth);
        List<InvoiceDetail> list = invoiceDetailMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            //若存在数据则更新数据
            InvoiceDetail invoiceDetail = list.get(0);
            //线上本月到票总计
            BigDecimal sum = invoiceDetail.getShAddInvoceAmt() == null ? BigDecimal.ZERO : invoiceDetail.getShAddInvoceAmt();
            BigDecimal shAmt = invoiceApply.getSumAmt().add(sum);//线上实际到票
            invoiceDetail.setShAddInvoceAmt(shAmt);
            //本月到票合计
            BigDecimal dr = invoiceDetail.getDrAddInvoiceAmt() == null ?BigDecimal.ZERO : invoiceDetail.getDrAddInvoiceAmt();
            BigDecimal addAmt = dr.add(shAmt);
            invoiceDetail.setAddInvoiceAmt(addAmt);
            //计算本月欠票
            BigDecimal ownInvoice = invoiceDetail.getPreLeftAmt()//计算本月欠票
                    .add(invoiceDetail.getDayProfitAmt())
                    .add(invoiceDetail.getDayBackAmt())
                    .add(invoiceDetail.getPreProfitMonthAmt())
                    .add(invoiceDetail.getPreProfitMonthBlAmt())
                    .add(invoiceDetail.getAdjustAmt())
                    .subtract(invoiceDetail.getAddInvoiceAmt());
            invoiceDetail.setOwnInvoice(ownInvoice);
            invoiceDetail.setUpdateTime(DateUtils.dateToStringss(new Date()));
            invoiceDetailMapper.updateByPrimaryKeySelective(invoiceDetail);
        }else{//插入新数据
            try{
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
                Date date = sdf.parse(invoiceApply.getProfitMonth());
                Calendar curr = Calendar.getInstance();
                curr.setTime(date);
                curr.add(Calendar.MONTH, -1);
                String preMonth = sdf.format(curr.getTime());
                Map<String, Object> params = new HashMap<>();
                params.put("profitMonth", invoiceApply.getProfitMonth());
                params.put("preMonth",preMonth);
                params.put("agentId", agentId);
                List<Map<String, Object>> agentList = invoiceDetailMapper.queryInvoiceAgents(params);
                if(agentList != null && agentList.size()>0){
                    Map<String, Object> map = agentList.get(0);
                    insertInvoiceOwnDetail(map,profitMonth);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public InvoiceDetail getInvoice(String agentId) {
        InvoiceDetailExample example = new InvoiceDetailExample();//根据agentId 和月份判断本月数据信息是否存在
        InvoiceDetailExample.Criteria criteria = example.createCriteria();
        criteria.andAgentIdEqualTo(agentId);
        example.setOrderByClause("PROFIT_MONTH desc");
        List<InvoiceDetail> list = invoiceDetailMapper.selectByExample(example);
        if(list.size()!= 0){
            return list.get(0);
        }
            return null;
    }
}
