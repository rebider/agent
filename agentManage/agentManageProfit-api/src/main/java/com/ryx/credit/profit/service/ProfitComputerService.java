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
}
