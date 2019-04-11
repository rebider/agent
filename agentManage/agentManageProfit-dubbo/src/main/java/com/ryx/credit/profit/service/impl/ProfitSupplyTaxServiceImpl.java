package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.dao.ProfitDirectMapper;
import com.ryx.credit.profit.dao.ProfitSupplyTaxMapper;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitDetailMonthExample;
import com.ryx.credit.profit.pojo.ProfitDirect;
import com.ryx.credit.profit.pojo.ProfitSupplyTax;
import com.ryx.credit.profit.service.ProfitSupplyTaxService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("profitSupplyTaxService")
public class ProfitSupplyTaxServiceImpl implements ProfitSupplyTaxService {

    Logger logger = LoggerFactory.getLogger(ProfitSupplyTaxServiceImpl.class);

    @Autowired
    private ProfitSupplyTaxMapper profitSupplyTaxMapper;
    @Autowired
    ProfitDetailMonthMapper profitDetailMonthMapper;
    @Autowired
    private IdService idService;
    @Autowired
    ProfitDirectMapper directMapper;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private AgentBusinfoService businfoService;

    private List<String> computedAgentIds = new ArrayList<>();

    @Override
    public PageInfo getProfitSupplyTaxList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = 0L;
        List<Map<String, Object>> listAll;
        if ("1".equals(param.get("concludeChild"))) {//包含下级
            count = profitSupplyTaxMapper.getClassificationCount(param);
            listAll = profitSupplyTaxMapper.getClassificationList(param);
            /*if ("".equals(param.get("SUPPLY_TAX_AGENT_NAME"))&&("".equals(param.get("SUPPLY_TAX_AGENT_ID")))){
                param.put("SUPPLY_TAX_TYPE", BusType.JG.key);
                List<Map<String, Object>> list1 = profitSupplyTaxMapper.getProfitSupplyTaxList(param);
                param.put("SUPPLY_TAX_TYPE",BusType.BZYD.key);
                List<Map<String, Object>> list2 = profitSupplyTaxMapper.getProfitSupplyTaxList(param);
                list=new ArrayList<>();
                list.addAll(list1);
                list.addAll(list2);
            }else{
                 list = profitSupplyTaxMapper.getProfitSupplyTaxList(param);
            }

             listAll = new ArrayList<>();
             if(list!=null){
               for(int i = 0;i< list.size();i++){
                   listAll.add(list.get(i));
                   String supplyTaxSubId = (String) list.get(i).get("SUPPLY_TAX_SUB_ID");
                   Map<String,Object> mapOne =  new TreeMap<>();
                   mapOne.put("SUPPLY_TAX_AGENT_ID",supplyTaxSubId);
                   mapOne.put("SUPPLY_TAX_PLATFORM","01");
                  List<Map<String, Object>> listTwo = profitSupplyTaxMapper.getProfitSupplyTaxList(mapOne);
                  if (listTwo != null){
                      for (int j = 0;j< listTwo.size();j++){
                          listAll.add(listTwo.get(j));
                          String supplyTaxSubId2 =  (String)listTwo.get(j).get("SUPPLY_TAX_SUB_ID");
                          Map<String,Object> mapTwo =  new TreeMap<>();
                          mapTwo.put("SUPPLY_TAX_AGENT_ID",supplyTaxSubId2);
                          mapTwo.put("SUPPLY_TAX_PLATFORM","01");
                          List<Map<String, Object>> listTh = profitSupplyTaxMapper.getProfitSupplyTaxList(mapTwo);

                     if (listTh !=null){
                         listAll.addAll(listTh);
                     }else{
                         continue;
                     }
                      }
                  }else {
                      continue;
                  }

               }
                 count = Long.valueOf(listAll.size());
             }else{
                 throw new ProcessException("没有此代理商！");
             }*/
        } else {
            count = profitSupplyTaxMapper.getProfitSupplyTaxCount(param);
            listAll = profitSupplyTaxMapper.getProfitSupplyTaxList(param);
        }
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(listAll);
        System.out.println("查询============================================" + JSONObject.toJSON(listAll));
        return pageInfo;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 代理商补税点计算
     * @Date: 0:12 2018/12/21
     */
    @Override
    @Transactional
    public void taxSupplyComputer(Map<String, Object> params) {

        String profitMonth = params.get("profitMonth") == null ? new SimpleDateFormat("yyyyMM").format(DateUtil.addMonth(new Date(), -1)) : (String) params.get("profitMonth");
        computedAgentIds.clear();

        logger.info("======={}补税点计算开始======", profitMonth);
        profitSupplyTaxMapper.deleteByMonth(profitMonth);

        logger.info("直签开票代理商无需补税点计算");

        logger.info("开始直签扣税代理商补税点计算 (只需计算税点小于6%且扣税的代理商)");
        List<Map<String, Object>> zqTaxAgents = profitSupplyTaxMapper.getZqTaxAgents(params);
        for (Map<String, Object> map : zqTaxAgents) {
            String parentAgentId = (String) map.get("AGENT_ID");
            doTaxSupplyComputer_ZQ(parentAgentId, profitMonth);
        }

        logger.info("开始直发开票代理商补税点计算");
        List<Map<String, Object>> zfInvoiceAgents = profitSupplyTaxMapper.getZfInvoiceAgents(params);
        for (Map<String, Object> map : zfInvoiceAgents) {
            String busNum = (String) map.get("BUS_NUM");
            String firstAgentId = (String) map.get("AGENT_ID");
            doTaxSupplyComputer_ZF(firstAgentId, busNum, profitMonth, 1);
        }

        logger.info("开始直发扣税代理商补税点计算（税率非6%）");
        List<Map<String, Object>> zfTaxAgents = profitSupplyTaxMapper.getZfTaxAgents(params);
        for (Map<String, Object> map : zfTaxAgents) {
            String busNum = (String) map.get("BUS_NUM");
            String firstAgentId = (String) map.get("AGENT_ID");
            doTaxSupplyComputer_ZF(firstAgentId, busNum, profitMonth, 2);
        }
        logger.info("======={}补税点计算结束======", profitMonth);

    }

    /**
     * @Author: Zhang Lei
     * @Description: 直发代理商补税点计算
     * @Date: 18:31 2018/12/21
     */
    private void doTaxSupplyComputer_ZF(String firstAgentId, String parentBusNum, String profitMonth, int type) {
        logger.info("直发代理商{},{}", parentBusNum, firstAgentId);
        ProfitDirect dirct = new ProfitDirect();
        dirct.setFristAgentPid(parentBusNum);
        dirct.setTransMonth(profitMonth);

        BigDecimal shouldProfitAmt = BigDecimal.ZERO;
        if(type==1){
            shouldProfitAmt = directMapper.selectSumTaxAmt(dirct);
        }else if(type==2){
            shouldProfitAmt = directMapper.selectSumTaxAmt2(dirct);
        }

        if (shouldProfitAmt == null || shouldProfitAmt.compareTo(BigDecimal.ZERO) <= 0) {
            logger.info("无下级分润代理商，不计算");
            return;
        }

        //补税点总数
        BigDecimal tax = BigDecimal.ZERO;
        BigDecimal addTaxAmt = BigDecimal.ZERO;
        if (type == 1) {    //开票
            tax = new BigDecimal("0.06");
            addTaxAmt = shouldProfitAmt.multiply(tax);
        } else if (type == 2) { //扣税且税率小于0.06
            AgentColinfo where = new AgentColinfo();
            where.setAgentId(firstAgentId);
            AgentColinfo agentColinfo = agentColinfoService.queryPoint(where);
            tax = new BigDecimal("0.06").subtract(agentColinfo.getCloTaxPoint());
            addTaxAmt = shouldProfitAmt.multiply(tax);
        }


        logger.info("直发代理商{}补税点金额：{}", firstAgentId, addTaxAmt);

        //查询一代上级代理商
        AgentBusInfo Busime = businfoService.getByBusidAndCode("6000", parentBusNum);
        if (Busime == null) {
            logger.error("直发代理商{}补税点计算失败，未找到代理商业务信息基础数据", parentBusNum);
            throw new RuntimeException("直发代理商{" + parentBusNum + "}补税点计算失败，未找到代理商业务信息基础数据");
        }

        String parentAgentId = "";
        String parentId = Busime.getBusParent();
        if(StringUtils.isNotBlank(parentId)){
            AgentBusInfo agentBusInfo = businfoService.getById(parentId);
            parentAgentId = agentBusInfo.getAgentId();
        }

        ProfitDetailMonthExample example = new ProfitDetailMonthExample();
        ProfitDetailMonthExample.Criteria where = example.createCriteria();
        where.andAgentIdEqualTo(firstAgentId);

        if(StringUtils.isNotBlank(parentAgentId)){
            where.andParentAgentIdEqualTo(parentAgentId);
        }else {
            where.andParentAgentIdIsNull();
        }

        where.andProfitDateEqualTo(profitMonth);
        List<ProfitDetailMonth> parentProfitDetailMonths = profitDetailMonthMapper.selectByExample(example);
        if (parentProfitDetailMonths != null && parentProfitDetailMonths.size() > 0) {
            ProfitDetailMonth pdm = parentProfitDetailMonths.get(0);
            pdm.setSupplyTaxAmt(pdm.getSupplyTaxAmt().add(addTaxAmt));
            profitDetailMonthMapper.updateByPrimaryKeySelective(pdm);

            //记录补税点明细
            insertProfitSupplyTax(pdm, addTaxAmt, pdm, "02");
        }


    }


    /**
     * @Author: Zhang Lei
     * @Description: 直签代理商补税点计算
     * @Date: 18:29 2018/12/21
     */
    public void doTaxSupplyComputer_ZQ(String parentAgentId, String profitMonth) {

        //查询本级月分润明细
        ProfitDetailMonthExample example = new ProfitDetailMonthExample();
        ProfitDetailMonthExample.Criteria where = example.createCriteria();
        where.andAgentIdEqualTo(parentAgentId);
        where.andProfitDateEqualTo(profitMonth);

        List<ProfitDetailMonth> parentProfitDetailMonths = profitDetailMonthMapper.selectByExample(example);
        List<ProfitDetailMonth> subProfitDetailMonths = new ArrayList<ProfitDetailMonth>();
        Map<String, Object> params = new HashMap<>();
        params.put("parentAgentId", parentAgentId);
        params.put("profitDate", profitMonth);

        if (parentProfitDetailMonths == null || parentProfitDetailMonths.size() <= 0) {  //上级本月不发分润，下级无需给其补税点
            logger.info("{}本月不发分润，下级无需给其补税点", parentAgentId);
            subProfitDetailMonths = profitDetailMonthMapper.selectListByParams(params);

        } else { //下级需要补上级税点
            ProfitDetailMonth parentProfitDetailMonth = parentProfitDetailMonths.get(0);

            params.put("taxnoteql", parentProfitDetailMonth.getTax());//查询本月有分润且税率不一致的下级
            subProfitDetailMonths = profitDetailMonthMapper.selectListByParams(params);

            for (ProfitDetailMonth sub : subProfitDetailMonths) {
                //计算下级月扣税基数 = 扣税金额/税点
                String subAgentId = sub.getAgentId();
                params = new HashMap<>();
                params.put("subAgentId", subAgentId);
                params.put("parentAgentId", parentAgentId);
                params.put("profitDate", profitMonth);
                params.put("parentTax", parentProfitDetailMonth.getTax());
                BigDecimal addTaxAmt = profitDetailMonthMapper.getSubAgentTaxBaseTotal(params);

                //上级补税点合计
                BigDecimal tmp = parentProfitDetailMonth.getSupplyTaxAmt() == null ? BigDecimal.ZERO : parentProfitDetailMonth.getSupplyTaxAmt();
                parentProfitDetailMonth.setSupplyTaxAmt(tmp.add(addTaxAmt));
                profitDetailMonthMapper.updateByPrimaryKeySelective(parentProfitDetailMonth);
                logger.info("{}给上级{}补税点金额：{}", subAgentId, parentAgentId, addTaxAmt);

                //记录补税点明细
                insertProfitSupplyTax(sub, addTaxAmt, parentProfitDetailMonth, "01");
            }
        }

        //递归补下级税点
        for (ProfitDetailMonth sub : subProfitDetailMonths) {
            String nextParentAgentId = sub.getAgentId();
            if (!computedAgentIds.contains(nextParentAgentId)) {//防止重複計算
                computedAgentIds.add(nextParentAgentId);
                doTaxSupplyComputer_ZQ(nextParentAgentId, profitMonth);
            }
        }

    }

    /**
     * @Author: Zhang Lei
     * @Description: 记录补税点明细
     * @Date: 17:14 2018/12/21
     */
    private void insertProfitSupplyTax(ProfitDetailMonth sub, BigDecimal addTaxAmt, ProfitDetailMonth parentProfitDetailMonth, String platType) {
        ProfitSupplyTax profitSupplyTax = new ProfitSupplyTax();
        profitSupplyTax.setId(idService.genId(TabId.PROFIT_SUPPLU_TAX));
        profitSupplyTax.setSupplyTaxDate(parentProfitDetailMonth.getProfitDate());
        profitSupplyTax.setSupplyTaxAgentId(parentProfitDetailMonth.getAgentId());
        profitSupplyTax.setSupplyTaxAgentName(parentProfitDetailMonth.getAgentName());
        profitSupplyTax.setSupplyTaxPlatform(platType);
        profitSupplyTax.setSupplyTaxSubId(sub.getAgentId());
        profitSupplyTax.setSupplyTaxSubName(sub.getAgentName());
        profitSupplyTax.setSupplyTaxAmt(addTaxAmt);
        //profitSupplyTax.setSupplyTaxType(parentProfitDetailMonth.get);
        profitSupplyTax.setCreateTime(DateUtils.dateToStringss(new Date()));
        profitSupplyTaxMapper.insertSelective(profitSupplyTax);
    }

    @Override
    public Map<String, Object> profitCount(Map<String, Object> param, boolean isQuerySubordinate) {
        if (isQuerySubordinate) {//包含下级
            return profitSupplyTaxMapper.profitCountWithSubordinate(param);
        }else{
            return profitSupplyTaxMapper.profitCount(param);
        }
    }
}
