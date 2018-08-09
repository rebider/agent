package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitMonth;
import com.ryx.credit.profit.pojo.ProfitUnfreeze;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangY
 * @desc 分润计算服务类
 */
public interface ProfitComputerService {

    /**
     * 瑞和宝日结分润汇总
     * @param agentPid
     */
    BigDecimal total_day(String agentPid,String month);


    /**
     * 商业保理汇总
     * @param agentPid
     * @return
     */
    BigDecimal total_factor(String agentPid,String month);

    /**
     * 其他补款汇总
     * @param agentPid
     * @param month
     * @return
     */
    BigDecimal total_supply(String agentPid,String month);
}
