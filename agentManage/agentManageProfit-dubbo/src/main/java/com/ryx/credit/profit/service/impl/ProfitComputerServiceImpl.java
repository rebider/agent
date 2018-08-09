package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.PProfitFactorMapper;
import com.ryx.credit.profit.dao.PosCheckMapper;
import com.ryx.credit.profit.dao.ProfitDayMapper;
import com.ryx.credit.profit.dao.ProfitSupplyMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IPosCheckService;
import com.ryx.credit.profit.service.ProfitComputerService;
import com.ryx.credit.profit.service.ProfitSupplyService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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

    @Override
    public BigDecimal total_day(String agentPid,String month) {
        if(null==month || "".equals(month)){
            month = DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1));
            month = month.substring(0,6);
        }
        ProfitDay day = new ProfitDay();
        day.setAgentPid(agentPid);
        day.setTransDate(month);
        long dayLong = dayMapper.totalMonthByAgentPid(day);
        BigDecimal totalDay = new BigDecimal(dayLong);
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
        long dayLong = dayMapper.totalMonthByAgentId(day);
        BigDecimal totalDay = new BigDecimal(dayLong);
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
        long factor1Long = factorMapper.getSumFactor(factor);
        BigDecimal totalFactor = new BigDecimal(factor1Long);
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
        long supplyLong = profitSupplyMapper.getTotalByMonthAndPid(supply);
        BigDecimal totalSupply = new BigDecimal(supplyLong);
        return totalSupply;
    }
}
