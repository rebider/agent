package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.dao.*;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.ProfitComputerService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentQueryService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 分润计算(财务自编码)
 * Created by IntelliJ IDEA.
 *
 * @Author Wang y
 * @Date 2018/8/5
 * @Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
@Service("profitComputerService")
public class ProfitComputerServiceImpl implements ProfitComputerService {
    protected Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    ProfitDayMapper dayMapper;
    @Autowired
    PProfitFactorMapper factorMapper;
    @Autowired
    ProfitSupplyMapper profitSupplyMapper;
    @Autowired
    private BuckleRunMapper buckleRunMapper;
    @Autowired
    ProfitDirectMapper directMapper;
    @Autowired
    ProfitDetailMonthMapper detailMonthMapper;
    @Autowired
    PTaxAdjustMapper adjustMapper;
    @Autowired
    PtaxHistoryMapper historyMapper;
    @Autowired
    IdService idService;
    @Autowired
    AgentBusinfoService businfoService;
    @Autowired
    AgentService agentService;
    @Autowired
    AgentQueryService agentQueryService;
    @Autowired
    PAgentPidLinkMapper pidLinkMapper;

    private int index = 1;
    private BigDecimal tranAmount = BigDecimal.ZERO;

    @Override
    public BigDecimal totalP_day_RHB(String agentPid,String month) {
        if(null==month || "".equals(month)){
            month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1));
            month = month.substring(0,6);
        }
        ProfitDay day = new ProfitDay();
        day.setAgentPid(agentPid);
        day.setTransDate(month);
        BigDecimal totalDay = dayMapper.totalMonthByAgentPid(day);
        if(null == totalDay){
            totalDay = BigDecimal.ZERO;
        }
        logger.info(agentPid+"在【"+month+"】瑞和宝日结分润共计："+totalDay);
        return totalDay;
    }

    @Override
    public BigDecimal totalR_day(String agentPid,String month) {
        if(null==month || "".equals(month)){
            month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1));
            month = month.substring(0,6);
        }
        ProfitDay day = new ProfitDay();
        day.setAgentPid(agentPid);
        day.setTransDate(month);
        BigDecimal totalDay = dayMapper.totalReturnByAgentPid(day);
        if(null == totalDay){
            totalDay = BigDecimal.ZERO;
        }
        logger.info(agentPid+"在【"+month+"】日结返现共计："+totalDay);
        return totalDay;
    }

    @Override
    public BigDecimal totalP_day(String agentPid,String month) {
        if(null==month || "".equals(month)){
            month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1));
            month = month.substring(0,6);
        }
        ProfitDay day = new ProfitDay();
        day.setAgentPid(agentPid);
        day.setTransDate(month);
        BigDecimal totalDay = dayMapper.totalRPByAgentPid(day);
        if(null == totalDay){
            totalDay = BigDecimal.ZERO;
        }
        logger.info(agentPid+"在【"+month+"】日结分润共计："+totalDay);
        return totalDay;
    }

    @Override
    public BigDecimal totalP_day_ZF(String agentId,String month) {
        if(null==month || "".equals(month)){
            month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1));
            month = month.substring(0,6);
        }
        ProfitDay day = new ProfitDay();
        day.setAgentId(agentId);
        day.setTransDate(month);
        BigDecimal totalDay = dayMapper.totalMonthByAgentId(day);
        if(null == totalDay){
            totalDay = BigDecimal.ZERO;
        }
        logger.info(agentId+"在【"+month+"】直发日结分润共计："+totalDay);
        return totalDay;
    }

    @Override
    public BigDecimal total_factor(String agentPid,String month) {
        if(null==month || "".equals(month)){
            month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1));
            month = month.substring(0,6);
        }
        PProfitFactor factor = new PProfitFactor();
        factor.setAgentPid(agentPid);
        factor.setFactorMonth(month);
        BigDecimal totalFactor = factorMapper.getSumFactor(factor);
        if(null == totalFactor){
            totalFactor = BigDecimal.ZERO;
        }
        logger.info(agentPid+"在【"+month+"】商业保理扣款共计："+totalFactor);
        return totalFactor;
    }

    @Override
    public BigDecimal total_supply(String agentPid,String month) {
        if(null==month || "".equals(month)){
            month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1));
            month = month.substring(0,6);
        }
        ProfitSupply supply = new ProfitSupply();
        supply.setAgentPid(agentPid);
        supply.setSupplyDate(month);
        BigDecimal totalSupply = profitSupplyMapper.getTotalByMonthAndPid(supply);
        if(null == totalSupply){
            totalSupply = BigDecimal.ZERO;
        }
        logger.info(agentPid+"在【"+month+"】其他补款共计："+totalSupply);
        return totalSupply;
    }



    @Override
    public BigDecimal total_SupplyAndCashBack(String agentPid,String month){
        return total_supply(agentPid,month).add(totalR_day(agentPid,month));
    }

    @Override
    public void computer_Supply_ZhiFa() {
        List<ProfitDirect> profitDirects = directMapper.selectBySupply();//存在补款的记录，追溯上级补款
        String month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6);
        for(ProfitDirect profitDirect:profitDirects){
            ProfitSupply supplyWhere = new ProfitSupply();
            supplyWhere.setAgentId(profitDirect.getAgentId());
            supplyWhere.setSupplyDate(month);
            BigDecimal supply = profitSupplyMapper.getBuckleByMonthAndPid(supplyWhere);//退单补款
            if(null==supply || supply.compareTo(BigDecimal.ZERO)==0){
                //无补款
                continue;
            }
            BigDecimal creditAmt = buckleRunMapper.getSumRunAmt(profitDirect.getAgentId());//一共被代扣总额
            //找不着扣款关系------------------
            if(null==creditAmt||creditAmt.compareTo(BigDecimal.ZERO)==0){
                /*ProfitDetailMonth detailMonth = detailMonthMapper.selectByAgentPid(profitDirect.getFristAgentPid());
                if(null==detailMonth.getZhifaSupply()){
                    detailMonth.setZhifaSupply(supply);
                }else{
                    detailMonth.setZhifaSupply(detailMonth.getZhifaSupply().add(supply));
                }
                supply = BigDecimal.ZERO;//给一代补款，然后清零
                detailMonthMapper.updateByPrimaryKeySelective(detailMonth);*/

            }else{
                //有被代扣历史，追溯补款
                List<BuckleRun> buckleRuns = buckleRunMapper.selectListByAgentId(profitDirect.getAgentId());
                for(BuckleRun buckleRun:buckleRuns){
                    BigDecimal a = buckleRun.getRunAmt()==null?BigDecimal.ZERO:buckleRun.getRunAmt();//上级当时帮忙承担扣款金额
                    BigDecimal b = buckleRun.getSupplyAmt()==null?BigDecimal.ZERO:buckleRun.getSupplyAmt();//已通过补款补回金额
                    BigDecimal c = a.subtract(b);//还需帮上级找回补款余额
                    if(c.compareTo(BigDecimal.ZERO)==0){
                        continue;
                    }
                    supply = supply.subtract(c);//600,100
                    if(supply.compareTo(BigDecimal.ZERO)<=0){//补款没了
                        buckleRun.setSupplyAmt(b.add(supply).add(c));
                        buckleRunMapper.updateByPrimaryKeySelective(buckleRun);

                        if(buckleRun.getBearAgentPid()!=null){//扣款历史为一代，更新一代补款
                            ProfitDetailMonth detailMonth = detailMonthMapper.selectByAgentPid(buckleRun.getBearAgentPid());
                            BigDecimal s = detailMonth.getZhifaSupply()==null?BigDecimal.ZERO:detailMonth.getZhifaSupply();
                            detailMonth.setZhifaSupply(s.add(supply).add(c));
                            detailMonthMapper.updateByPrimaryKeySelective(detailMonth);
                        }else{//扣款历史为非一代，更新上级补款
                            ProfitDirect record = new ProfitDirect();
                            record.setTransMonth(month);
                            record.setAgentId(buckleRun.getBearAgentId());
                            ProfitDirect direct = directMapper.selectByAgentAndMon(record);
                            BigDecimal s = direct.getSupplyAmt()==null?BigDecimal.ZERO:direct.getSupplyAmt();
                            direct.setSupplyAmt(s.add(supply).add(c));
                            directMapper.updateByPrimaryKeySelective(direct);
                        }
                        supply = BigDecimal.ZERO;
                        break;
                    }else{
                        buckleRun.setSupplyAmt(a);
                        buckleRunMapper.updateByPrimaryKeySelective(buckleRun);
                        if(buckleRun.getBearAgentPid()!=null){//扣款历史为一代，更新一代补款
                            ProfitDetailMonth detailMonth = detailMonthMapper.selectByAgentPid(buckleRun.getBearAgentPid());
                            BigDecimal s = detailMonth.getZhifaSupply()==null?BigDecimal.ZERO:detailMonth.getZhifaSupply();
                            detailMonth.setZhifaSupply(s.add(c));
                            detailMonthMapper.updateByPrimaryKeySelective(detailMonth);
                        }else{//扣款历史为非一代，更新上级补款
                            ProfitDirect record = new ProfitDirect();
                            record.setTransMonth(month);
                            record.setAgentId(buckleRun.getBearAgentId());
                            ProfitDirect direct = directMapper.selectByAgentAndMon(record);
                            BigDecimal s = direct.getSupplyAmt()==null?BigDecimal.ZERO:direct.getSupplyAmt();
                            direct.setSupplyAmt(s.add(c));
                            directMapper.updateByPrimaryKeySelective(direct);
                        }
                    }
                }
            }
            profitDirect.setSupplyAmt(supply);
            directMapper.updateByPrimaryKeySelective(profitDirect);
        }
    }

    @Override
    public void computer_Buckle_ZhiFa() {
        List<ProfitDirect> profitDirects = directMapper.selectByBuckle();//存在退单扣款的记录
        for(ProfitDirect profitDirect:profitDirects){
            logger.info("本月分润不够扣的记录，逐级找上级补款。并记录代扣关系");
            BigDecimal lackAmt = profitDirect.getProfitAmt().add(profitDirect.getSupplyAmt()).subtract(profitDirect.getBuckleAmt());
            if(lackAmt.compareTo(BigDecimal.ZERO)<0){//说明还需找上级代扣的退单金额
                lackAmt = lackAmt.multiply(new BigDecimal("-1"));
                computerSurplus(profitDirect,1,profitDirect.getAgentId(),lackAmt);
            }
        }
    }

    /**
     * //递归找上级补扣，直到能扣完为止
     * @param profitDirect
     * @param index 追溯层级
     * @param oldAgrnt 追溯底层代理商编号
     * @param buck 剩余扣款
     * @return
     */
    public void computerSurplus(ProfitDirect profitDirect,int index,String oldAgrnt,BigDecimal buck){
        ProfitDirect parentWhere = new ProfitDirect();
        parentWhere.setTransMonth(profitDirect.getTransMonth());
        parentWhere.setAgentId(profitDirect.getParentAgentId());
        ProfitDirect parentDirect = directMapper.selectByAgentAndMon(parentWhere);//上级数据
        if(profitDirect.getFristAgentId().equals(profitDirect.getParentAgentId())){ //上级就是一级代理商

            //帮下级承担扣款
            ProfitDetailMonth where = new ProfitDetailMonth();
            where.setProfitDate(profitDirect.getTransMonth());
            where.setAgentPid(profitDirect.getFristAgentPid());
            ProfitDetailMonth detailMonth = detailMonthMapper.selectByPIdAndMonth(where);//上级一代数据
            BigDecimal buckle = isDecimalNull(detailMonth.getZhifaBuckle());
            detailMonth.setZhifaBuckle(buckle.add(buck));
            detailMonthMapper.updateByPrimaryKeySelective(detailMonth);
            //记录代扣承担关系
            BuckleRun buckleRun = new BuckleRun();
            buckleRun.setId(idService.genId(TabId.P_BUCKLE_RUN));
            buckleRun.setAgentId(oldAgrnt);
            buckleRun.setBearAgentPid(profitDirect.getFristAgentPid());
            buckleRun.setBearAgentId(profitDirect.getFristAgentId());
            buckleRun.setRunAmt(buck);
            buckleRun.setSupplyAmt(BigDecimal.ZERO);
            buckleRun.setRunDate(DateUtil.getDays());
            buckleRun.setRunLevel(index+"");
            buckleRun.setRunStatus("0");
            buckleRunMapper.insertSelective(buckleRun);
        }else{
            BigDecimal bearAmt = parentDirect.getProfitAmt().add(parentDirect.getSupplyAmt()).subtract(parentDirect.getBuckleAmt()).subtract(parentDirect.getParentBuckle());//上级的分润
            if(bearAmt.compareTo(BigDecimal.ZERO)<0){//上级自己还差钱
                computerSurplus(parentDirect,index++,oldAgrnt,buck);
                return;
            }
            if(bearAmt.compareTo(buck)<0){//上级分润不够扣
                parentDirect.setParentBuckle(bearAmt);//替下级扣款(自己全部分润搭进去了)
                directMapper.updateByPrimaryKeySelective(parentDirect);
                //记录代扣承担关系
                BuckleRun buckleRun = new BuckleRun();
                buckleRun.setId(idService.genId(TabId.P_BUCKLE_RUN));
                buckleRun.setAgentId(oldAgrnt);
                buckleRun.setBearAgentId(profitDirect.getParentAgentId());
                buckleRun.setRunAmt(bearAmt);//上级承担的扣款也就只能是自己不够扣的分润了
                buckleRun.setSupplyAmt(BigDecimal.ZERO);
                buckleRun.setRunDate(DateUtil.getDays());
                buckleRun.setRunLevel(index +"");
                buckleRun.setRunStatus("0");
                buckleRunMapper.insertSelective(buckleRun);
                buck = buck.subtract(bearAmt);//剩余扣款
                computerSurplus(parentDirect,index++,oldAgrnt,buck);
            }else {
                parentDirect.setParentBuckle(buck);//替下级扣款
                directMapper.updateByPrimaryKeySelective(parentDirect);
                //记录代扣承担关系
                BuckleRun buckleRun = new BuckleRun();
                buckleRun.setId(idService.genId(TabId.P_BUCKLE_RUN));
                buckleRun.setAgentId(oldAgrnt);
                buckleRun.setBearAgentId(profitDirect.getParentAgentId());
                buckleRun.setRunAmt(buck);//上级承担的扣款
                buckleRun.setSupplyAmt(BigDecimal.ZERO);
                buckleRun.setRunDate(DateUtil.getDays());
                buckleRun.setRunLevel(index +"");
                buckleRun.setRunStatus("0");
                buckleRunMapper.insertSelective(buckleRun);
            }

        }
    }

    @Override
    public void computer_ZhiFa() {
        String month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1));
        month = month.substring(0,6);//计算上月。YYYYMM
        ProfitDirect where = new ProfitDirect();
        where.setTransMonth(month);
        List<ProfitDirect> profitDirects = directMapper.selectByMonth(where);//上月的所有直发分润数据
        for(ProfitDirect profitDirect:profitDirects){
            BigDecimal total_day2 = totalP_day_ZF(profitDirect.getAgentId(),month);//直发日结分润
            BigDecimal profit = profitDirect.getProfitAmt()==null?BigDecimal.ZERO:profitDirect.getProfitAmt();//直发分润

            //BigDecimal sub = directMapper.getSubBuckleByMonth(where);//下级欠扣款
            BigDecimal supply = profitDirect.getSupplyAmt()==null?BigDecimal.ZERO:profitDirect.getSupplyAmt();//退单补款
            BigDecimal buckle = profitDirect.getBuckleAmt()==null?BigDecimal.ZERO:profitDirect.getBuckleAmt();//退单扣款
            BigDecimal parentBuckle = profitDirect.getParentBuckle()==null?BigDecimal.ZERO:profitDirect.getParentBuckle();//代下级退单扣款
            BigDecimal preTax = profit.add(supply).subtract(buckle).subtract(parentBuckle);//税前分润 = 直发分润+退单补款-退单扣款-代下级退单扣款
            BigDecimal tax = preTax.add(total_day2).multiply(new BigDecimal("0.06"));//应扣税额
            BigDecimal should = preTax.subtract(tax);//应发分润 = 税前分润-应扣税额
            if(should.compareTo(BigDecimal.ZERO)<0){ //应发系负数
                should = BigDecimal.ZERO;
                //parent = should.multiply(new BigDecimal("-1"));//此时该代理商上级如果是一代？
            }
            profitDirect.setShouldProfit(should);
            //profitDirect.setParentBuckle(parent);
            directMapper.updateByPrimaryKeySelective(profitDirect);
        }

    }


    /**
     * 同步手刷月分润交易汇总
     * @param transDate 交易日期（空则为上一月）
     */
    @Override
    public BigDecimal synchroSSTotalTransAmt(String transDate){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("transDate",transDate==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6):transDate);
        map.put("pageNumber",index++ +"");
        map.put("pageSize","50");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.month"),params);
        System.out.println(res);
        JSONObject json = JSONObject.parseObject(res);
        if(!json.get("respCode").equals("000000")){
            logger.error("请求同步失败！");
            AppConfig.sendEmails("手刷月分润交易汇总失败","手刷月分润交易汇总失败");
            return null;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        if(list.size()>0){
            addTransAmt(list,transDate);//手刷交易额汇总
        }
        BigDecimal fxAmount = isDecimalNull(json.getBigDecimal("fxAmount"));//分销系统交易汇总
        BigDecimal wjrAmount = isDecimalNull(json.getBigDecimal("wjrAmount"));//未计入分润汇总
        BigDecimal wtbAmount = isDecimalNull(json.getBigDecimal("wtbAmount"));//未同步到分润
        return tranAmount.add(fxAmount).add(wjrAmount).add(wtbAmount);

    }
    public void addTransAmt(List<JSONObject> profitMonths,String transDate){
        for(JSONObject json:profitMonths){
            BigDecimal transAmt = isDecimalNull(json.getBigDecimal("TRANAMT"));
            tranAmount = tranAmount.add(transAmt);
        }
        synchroSSTotalTransAmt(transDate);
    }

    @Override
    public void computerTax(String profitDate){
        profitDate = profitDate==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6):profitDate;
        List<ProfitDetailMonth> detailMonths = detailMonthMapper.selectByDate(profitDate);
        for(ProfitDetailMonth detailMonth:detailMonths){
            boolean isJieYin = false;//是否所属捷步、银点
            boolean isRYX = false;//是否瑞银信打款
            List<PAgentPidLink> links = pidLinkMapper.selectListByPid(detailMonth.getAgentPid());//获取代理商所有业务平台编码
            if(null!=detailMonth.getAgentId() && null!=detailMonth.getBusPlatForm()){
                PAgentPidLink pos = new PAgentPidLink();
                pos.setAgentId(detailMonth.getAgentId());
                pos.setDeptCode(detailMonth.getBusPlatForm());
                links.add(pos);
            }
            AgentBusInfo me = new AgentBusInfo();//本代理商业务信息
            List<String> allSubs = new ArrayList<String>();//该代理商所有业务平台下级agentid
            for(PAgentPidLink link:links){
                List<AgentBusInfo> subs = businfoService.queryChildLevelByBusNum(null,link.getDeptCode(),link.getAgentId());//子类
                for(AgentBusInfo sub:subs){
                    allSubs.add(sub.getAgentId());
                }

                if(null==me || me.getCloPayCompany()==null){//如果打款公司为空。轮番轰炸
                    me = businfoService.getByBusidAndCode(link.getDeptCode(),link.getAgentId());
                }

            }
            List<Agent> agents = agentQueryService.queryAgentListByIds(allSubs);
            List<String> ids = new ArrayList<String>();
            for(Agent agent:agents){
                ids.add(agent.getAgUniqNum());//唯一码
            }
            if(ids.size()==0){
                ids.add("0");
            }
            BigDecimal subAmt = detailMonthMapper.findByIds(ids);//所有下级的基础分润汇总

            List<AgentBusInfo> parents = businfoService.queryParenFourLevelBusNum(null,links.get(0).getDeptCode(),links.get(0).getAgentId());//父类
            if(me.getCloPayCompany()!=null && me.getCloPayCompany().equals("6")){//打款公司瑞银信
                isRYX = true;
            }
            if(parents.size()>0){
                AgentBusInfo first = parents.get(parents.size()-1);
                Agent agent = agentService.getAgentById(first.getAgentId());
                if(agent.getAgUniqNum().equals("JS00001159")||agent.getAgUniqNum().equals("JS00001160")) {//捷步、银点只算日结
                    isJieYin = true;
                }
            }else{
                if(detailMonth.getAgentPid().equals("JS00001159")||detailMonth.getAgentPid().equals("JS00001160")) {//捷步、银点只算日结
                    isJieYin = true;
                }
            }
            ProfitDetailMonth updateDetail = getTaxAndProfit(detailMonth.getRealProfitAmt(),detailMonth.getAgentPid(),isDecimalNull(subAmt),detailMonth.getTax(),profitDate,isJieYin,isRYX);
            updateDetail.setId(detailMonth.getId());
            detailMonthMapper.updateByPrimaryKeySelective(updateDetail);
        }

    }

    @Override
    public ProfitDetailMonth getTaxAndProfit(BigDecimal profitA,String agentPid,BigDecimal subAmt,BigDecimal agentTax,String transDate,boolean isJieYin,boolean isRYX){
        ProfitDetailMonth detail = new ProfitDetailMonth();
        PtaxHistory where  = new PtaxHistory();
        where.setAgentPid(agentPid);
        where.setTaxMonth(DateUtil.convert(transDate));//计算日期的上个月
        //如果没有基础分润金额，则查询是否有历史欠税。如果没有历史欠税则反null
        if(profitA.compareTo(BigDecimal.ZERO)<=0){
            logger.info("基础分润金额不足，无需计算税点税额！");
            BigDecimal history = historyMapper.getHistoryAmt(where);
            detail.setDeductionTaxMonthAmt(BigDecimal.ZERO);
            detail.setSupplyTaxAmt(BigDecimal.ZERO);
            detail.setRealProfitAmt(BigDecimal.ZERO);
            detail.setProfitMonthAmt(BigDecimal.ZERO);
            if(null==history){
                detail.setDeductionTaxMonthAgoAmt(BigDecimal.ZERO);
                return detail;
            }
            logger.info("历史欠税转移："+history);
            detail.setDeductionTaxMonthAgoAmt(history);
            inserHistory(transDate,agentPid,history);
            return detail;
        }
        //-------------------本月税额计算-------------------
        BigDecimal tax = adjustMapper.getTax(agentPid);
        if (null != tax){
            tax = tax.multiply(new BigDecimal("0.01"));
        }else{
            tax = agentTax==null?new BigDecimal("0.06"):agentTax.multiply(new BigDecimal("0.01"));
        }
        logger.info("税点："+tax);
        //-------------------查询该代理商下级代理应补税额-------------------
        BigDecimal subTax1 = BigDecimal.ZERO;//直发补税
        BigDecimal subTax2 = BigDecimal.ZERO;//非直发下级补税
        logger.info("税点小于常规0.06，判断是否需要补税");
        ProfitDirect dirct = new ProfitDirect();
        dirct.setFristAgentPid(agentPid);
        dirct.setTransMonth(transDate);
        if(tax.compareTo(new BigDecimal("0.06"))<0 && isRYX){//小于0.06才存在补税 & 开票代理商无需再计算税务部分
            subTax1 = directMapper.selectSumTaxAmt(dirct);//下级应发分润汇总(直发部分)
            subTax1 = subTax1==null?BigDecimal.ZERO:subTax1;
            logger.info("开票补所有");
            subTax1 = subTax1.multiply(new BigDecimal("0.06"));
            logger.info("直发所有下级分润补税："+subTax1);
            subTax2 = subAmt.multiply(new BigDecimal("0.06"));
            subTax2 = subTax2==null?BigDecimal.ZERO:subTax2;
            logger.info("下级分润补税点差额："+subTax2);
            detail.setSupplyTaxAmt(subTax1.add(subTax2));//@@@@@@VALUE：补下级税点
            detail.setDeductionTaxMonthAmt(BigDecimal.ZERO);//本月税额
            detail.setProfitMonthAmt(profitA.add(detail.getSupplyTaxAmt()));//本月分润]c
            detail.setRealProfitAmt(detail.getProfitMonthAmt());//实发分润
            detail.setDeductionTaxMonthAgoAmt(BigDecimal.ZERO);//本月及本月之前欠税
            return detail;
        }else if(tax.compareTo(new BigDecimal("0.06"))<0){//小于0.06才存在补税
            logger.info("不开票补税点");
            subTax1 = directMapper.selectSumTaxAmt(dirct);//下级应发分润汇总(直发部分)
            subTax1 = subTax1==null?BigDecimal.ZERO:subTax1;
            subTax1 = subTax1.multiply(new BigDecimal("0.06")).subtract(subTax1.multiply(tax));//下级分润*下级税点-下级分润*上级税点
            subTax2 = subAmt.multiply(new BigDecimal("0.06")).subtract(subAmt.multiply(tax));//下级分润*下级税点-下级分润*上级税点
        }
        detail.setSupplyTaxAmt(subTax1.add(subTax2));//@@@@@@VALUE：补下级税点
        logger.info("@补下级税点："+subTax1.add(subTax2));
        //-------------------计算本月之前税额（[日结+日返现]*税点+记录中上月税额）-------------------
        ProfitDay day = new ProfitDay();
        day.setAgentPid(agentPid);
        day.setTransDate(transDate);
        BigDecimal totalDay = dayMapper.totalProfitAndReturn(day);
        logger.info("日结分润+日结返现："+totalDay);
        if(isJieYin){//捷步、银点只算日结
            totalDay = dayMapper.totalRPByAgentPid(day);
            logger.info("所属捷步、银点");
            logger.info("日结分润："+totalDay);
        }
        BigDecimal taxDay = isDecimalNull(totalDay).multiply(tax);//日结分润应补税额部分
        BigDecimal taxHistory = historyMapper.getHistoryAmt(where);//上月日结欠税额
        detail.setDeductionTaxMonthAgoAmt(taxDay.add(isDecimalNull(taxHistory)));//@@@@@@VALUE：扣本月之前税额（含本月日）
        logger.info("@扣本月之前税额（含本月日）："+detail.getDeductionTaxMonthAgoAmt());
        //-------------------本月分润=基础分润-本月之前欠税额+下级补税点。
        //-------------------实际分润=本月分润-本月税额。
        // ------------------不足后，需记录本月之前欠税额-------------------
        BigDecimal must = profitA.subtract(detail.getDeductionTaxMonthAgoAmt())
                .add(detail.getSupplyTaxAmt());
        if(must.compareTo(BigDecimal.ZERO)<0){//分润不足扣上月欠税
            inserHistory(transDate,agentPid,must.multiply(new BigDecimal("-1")));
            must = BigDecimal.ZERO;
        }
        detail.setProfitMonthAmt(must);//@@@@@@VALUE：本月分润
        logger.info("@本月分润:"+detail.getProfitMonthAmt());
        detail.setDeductionTaxMonthAmt(must.multiply(tax));//@@@@@@VALUE：本月税额
        logger.info("@本月税额:"+detail.getDeductionTaxMonthAmt());
        BigDecimal actual = must.subtract(detail.getDeductionTaxMonthAmt());
        detail.setRealProfitAmt(actual);//@@@@@@VALUE：实发分润
        logger.info("@实发分润:"+actual);
        return detail;
    }

    /**
     * 插入欠税历史
     * @param transDate 欠税月份
     * @param agentPid
     * @param amt 欠税金额
     */
    public void inserHistory(String transDate,String agentPid,BigDecimal amt){
        PtaxHistory history = new PtaxHistory();
        history.setId(idService.genId(TabId.p_profit_adjust));
        history.setTaxMonth(transDate);
        history.setAgentPid(agentPid);
        history.setTaxAmount(amt);
        historyMapper.insert(history);
    }

    public BigDecimal isDecimalNull(BigDecimal value){
        return value==null?BigDecimal.ZERO:value;
    }
}
