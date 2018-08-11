package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.*;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IPosCheckService;
import com.ryx.credit.profit.service.ProfitComputerService;
import com.ryx.credit.profit.service.ProfitSupplyService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.omg.CosNaming.BindingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 分润计算
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
    IdService idService;

    @Override
    public BigDecimal total_day(String agentPid,String month) {
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
        return totalDay;
    }

    @Override
    public BigDecimal total_day2(String agentId,String month) {
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
        return totalSupply;
    }

    @Override
    public void computer_Supply_ZhiFa() {
        List<ProfitDirect> profitDirects = directMapper.selectBySupply();//存在补款的记录，追溯上级补款
        String month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6);
        for(ProfitDirect profitDirect:profitDirects){
            ProfitSupply supplyWhere = new ProfitSupply();
            supplyWhere.setAgentId(profitDirect.getAgentId());
            supplyWhere.setSupplyDate(month);
            BigDecimal supply = profitSupplyMapper.getTotalByMonthAndPid(supplyWhere);//退单补款

            BigDecimal creditAmt = buckleRunMapper.getSumRunAmt(profitDirect.getAgentId());//一共被代扣总额
            //找不着扣款关系------------------给一代补款
            if(null==creditAmt||creditAmt.compareTo(BigDecimal.ZERO)==0){
                ProfitDetailMonth detailMonth = detailMonthMapper.selectByAgentPid(profitDirect.getFristAgentPid());
                if(null==detailMonth.getZhifaSupply()){
                    detailMonth.setZhifaSupply(supply);
                }else{
                    detailMonth.setZhifaSupply(detailMonth.getZhifaSupply().add(supply));
                }
                supply = BigDecimal.ZERO;//给一代补款，然后清零
                detailMonthMapper.updateByPrimaryKeySelective(detailMonth);
            }else{
                //有被代扣历史，追溯补款
                List<BuckleRun> buckleRuns = buckleRunMapper.selectListByAgentId(profitDirect.getAgentId());
                for(BuckleRun buckleRun:buckleRuns){
                    BigDecimal a = buckleRun.getRunAmt()==null?BigDecimal.ZERO:buckleRun.getRunAmt();//上级当时帮忙承担扣款金额
                    BigDecimal b = buckleRun.getSupplyAmt()==null?BigDecimal.ZERO:buckleRun.getSupplyAmt();//已通过补款补回金额
                    BigDecimal c = a.subtract(b);//还需帮上级找回补款余额c
                    supply = supply.subtract(c);
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
                            record.setAgentId(buckleRun.getAgentId());
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
                            record.setAgentId(buckleRun.getAgentId());
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
        List<ProfitDirect> profitDirects = directMapper.selectByBuckle();//存在本月分润不够扣的记录，逐级找上级补款。并记录代扣关系
        for(ProfitDirect profitDirect:profitDirects){
            getSurplus(profitDirect,1,profitDirect.getAgentId(),profitDirect.getParentBuckle());
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
    public BigDecimal getSurplus(ProfitDirect profitDirect,int index,String oldAgrnt,BigDecimal buck){
        ProfitDirect parentWhere = new ProfitDirect();
        parentWhere.setTransMonth(profitDirect.getTransMonth());
        parentWhere.setAgentId(profitDirect.getParentAgentId());
        ProfitDirect parentDirect = directMapper.selectByAgentAndMon(parentWhere);//上级数据
        BigDecimal kou = parentDirect.getShouldProfit().subtract(buck);//扣差
        if(profitDirect.getFristAgentId().equals(profitDirect.getParentAgentId())){ //上级就是一级代理商
            //帮下级承担扣款
            ProfitDetailMonth where = new ProfitDetailMonth();
            where.setProfitDate(profitDirect.getTransMonth());
            where.setAgentPid(profitDirect.getFristAgentPid());
            ProfitDetailMonth detailMonth = detailMonthMapper.selectByPIdAndMonth(where);//上级一代数据
            BigDecimal buckle = detailMonth.getZhifaBuckle();
            detailMonth.setZhifaBuckle(buckle.add(profitDirect.getParentBuckle()));
            detailMonthMapper.updateByPrimaryKeySelective(detailMonth);
            //记录代扣承担关系
            BuckleRun buckleRun = new BuckleRun();
            buckleRun.setId(idService.genId(TabId.P_BUCKLE_RUN));
            buckleRun.setAgentId(oldAgrnt);
            buckleRun.setBearAgentPid(profitDirect.getFristAgentPid());
            buckleRun.setRunAmt(profitDirect.getParentBuckle());
            buckleRun.setSupplyAmt(BigDecimal.ZERO);
            buckleRun.setRunDate(DateUtil.getDays());
            buckleRun.setRunLevel(index+"");
            buckleRun.setRunStatus("0");
            buckleRunMapper.insertSelective(buckleRun);
        }else{
            if(kou.compareTo(BigDecimal.ZERO)<0){//不够扣
                BigDecimal bearAmt = parentDirect.getShouldProfit();//上级的分润
                parentDirect.setShouldProfit(BigDecimal.ZERO);//因为不够扣，分润肯定没了
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
                return getSurplus(parentDirect,index++,oldAgrnt,buck);
            }else {
                parentDirect.setShouldProfit(parentDirect.getShouldProfit().subtract(profitDirect.getParentBuckle()));
                directMapper.updateByPrimaryKeySelective(parentDirect);
                //记录代扣承担关系
                BuckleRun buckleRun = new BuckleRun();
                buckleRun.setId(idService.genId(TabId.P_BUCKLE_RUN));
                buckleRun.setAgentId(oldAgrnt);
                buckleRun.setBearAgentId(profitDirect.getParentAgentId());
                buckleRun.setRunAmt(profitDirect.getParentBuckle());//上级承担的扣款
                buckleRun.setSupplyAmt(BigDecimal.ZERO);
                buckleRun.setRunDate(DateUtil.getDays());
                buckleRun.setRunLevel(index +"");
                buckleRun.setRunStatus("0");
                buckleRunMapper.insertSelective(buckleRun);
            }

        }
        return kou;
    }

    @Override
    public void computer_ZhiFa() {
        String month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1));
        month = month.substring(0,6);//计算上月。YYYYMM
        ProfitDirect where = new ProfitDirect();
        where.setTransMonth(month);
        List<ProfitDirect> profitDirects = directMapper.selectByMonth(where);//上月的所有直发分润数据
        for(ProfitDirect profitDirect:profitDirects){
            where.setAgentId(profitDirect.getAgentId());
            BigDecimal total_day2 = total_day2(profitDirect.getAgentId(),month);//日结分润
            BigDecimal profit = profitDirect.getProfitAmt()==null?BigDecimal.ZERO:profitDirect.getProfitAmt();//直发分润
            BigDecimal tax = profit.add(total_day2).multiply(new BigDecimal("0.06"));//应扣税额
            //BigDecimal sub = directMapper.getSubBuckleByMonth(where);//下级欠扣款
            BigDecimal supply = profitDirect.getSupplyAmt()==null?BigDecimal.ZERO:profitDirect.getSupplyAmt();//退单补款
            BigDecimal buckle = profitDirect.getBuckleAmt()==null?BigDecimal.ZERO:profitDirect.getBuckleAmt();//退单扣款
            BigDecimal should = profit.add(supply).subtract(buckle).subtract(tax);//应发分润 = 直发分润+退单补款-退单扣款-应扣税额
            BigDecimal parent = BigDecimal.ZERO;//应找上级扣款
            if(should.compareTo(BigDecimal.ZERO)<0){ //应发系负数
                should = BigDecimal.ZERO;
                parent = should.multiply(new BigDecimal("-1"));//此时该代理商上级如果是一代？
            }
            profitDirect.setShouldProfit(should);
            profitDirect.setParentBuckle(parent);
            directMapper.updateByPrimaryKeySelective(profitDirect);
        }

    }


}
