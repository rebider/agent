package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitMonth;
import com.ryx.credit.profit.pojo.ProfitUnfreeze;
import org.omg.CosNaming.BindingHelper;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangY
 * @desc 分润计算服务类
 */
public interface ProfitComputerService {

    /**
     * 瑞和宝日结分润汇总
     * @param agentPid 代理商唯一码
     */
    BigDecimal total_day(String agentPid,String month);

    /**
     * 瑞和宝日结返现汇总
     * @param agentPid 代理商唯一码
     */
    BigDecimal total_dayR(String agentPid,String month);

    /**
     * 日结分润汇总
     * @param agentId 代理商编号
     * @param month
     * @return
     */
    BigDecimal total_day2(String agentId,String month);
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

    /**
     * 计算直发分润（追溯本源，逐级往下补款）
     */
    void computer_Supply_ZhiFa();

    /**
     * 计算直发分润（逐级往上扣款）
     */
    void computer_Buckle_ZhiFa();

    /**
     * 直发分润计算（上月所有）
     */
    void computer_ZhiFa();

    /**
     * 同步手刷月分润交易汇总
     * @param transDate 交易日期（空则为上一月）
     */
    public BigDecimal synchroSSTotalTransAmt(String transDate);

    /**
     * 本月税额、补下级税点、本月之前税额、本月分润、实发分润计算
     * 分润不足并存在扣税时，记录本月未扣足税额
     * PS：补下级税点计算必须先计算所有代理商的税前应发分润
     * @param profitA 基础分润
     * @param agentPid 代理商唯一码
     * @param subPgentPid 下级代理商唯一码
     * @param agentTax 代理商税点
     * @param transDate 月份
     * @return ProfitDetailMonth（本月税额、补下级税点、扣本月之前税额（含日）、本月分润、实发分润）
     */
    ProfitDetailMonth getTaxAndProfit(BigDecimal profitA,String agentPid,String subPgentPid,BigDecimal agentTax,String transDate);
}
