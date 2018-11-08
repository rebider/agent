package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitDetailMonthExample;
import com.ryx.credit.profit.pojo.ProfitDirect;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProfitDetailMonthMapper {
    int countByExample(ProfitDetailMonthExample example);

    int deleteByExample(ProfitDetailMonthExample example);

    int insert(ProfitDetailMonth record);

    int insertSelective(ProfitDetailMonth record);

    List<ProfitDetailMonth> selectByExample(ProfitDetailMonthExample example);

    /**
     * 根据月份获取该月分润明细列表
     * @param profitDate
     * @return
     */
    List<ProfitDetailMonth> selectByDate(String profitDate);

    /**
     * 根据月份获取该月分润明细列表（小于0.06税点）
     * @param profitDate
     * @return
     */
    List<ProfitDetailMonth> selectByLessDate(String profitDate);

    /**
     * 根据月份获取该月分润明细列表（大于等于0.06税点）
     * @param profitDate
     * @return
     */
    List<ProfitDetailMonth> selectByGreaDate(String profitDate);
    /**
     * 根据所属下级id集合汇总他们的实际分润（税前计算时为基础分润汇总）
     * @param ids
     * @return
     */
    BigDecimal findByIds(List<String> ids);

    ProfitDetailMonth selectByPrimaryKey(String id);

    ProfitDetailMonth selectByAgentPid(String agentPid);

    ProfitDetailMonth selectByPIdAndMonth(ProfitDetailMonth record);

    ProfitDetailMonth selectByIdAndParent(ProfitDetailMonth record);

    int updateByPrimaryKeySelective(ProfitDetailMonth record);

    int updateByPrimaryKey(ProfitDetailMonth record);

    void payMoney(String profitDate);

    List<String> getDistrictAgent(String id);

    List<String> getProAgent(String id);

    /**
     * 导出数据
     * @param profitDetailMonth
     * @return
     */
    List<ProfitDirect> exportByFinance(ProfitDetailMonth profitDetailMonth);

    /**
     * 修改分润展示补款
     * */
    int updateProfitMonthDetail(ProfitDetailMonth profitDetailMonth);

    /***
    * @Description: 计算数据清零
    * @Param:
    * @return:
    * @Author: zhaodw
    * @Date: 2018/10/31
    */
    void clearComputData(String profitDate);
}