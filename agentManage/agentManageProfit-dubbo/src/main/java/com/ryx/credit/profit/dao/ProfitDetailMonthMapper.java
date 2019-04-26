package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.FreezeAgent;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitDetailMonthExample;
import com.ryx.credit.profit.pojo.ProfitDirect;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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

    List<ProfitDetailMonth> getProfitDetailMonthListByParam(Map<String, Object> param);

    List<Map<String,Object>> queryProfitDetailMonthList(Map<String, Object> param);

    Long queryProfitDetailMonthCount(Map<String, Object> param);

    ProfitDetailMonth selectByAgentId(String agentId);

    List<ProfitDetailMonth> selectListByParams(Map<String, Object> param);

    BigDecimal getSubAgentTaxBaseTotal(Map<String,Object> params);

    void updateRealProfitAmt(Map<String,Object> params);

    Map<String,Object> profitCount(Map<String, Object> param);

    Map<String,Object> profitCountWithSubordinate(Map<String, Object> param);

    List<Map<String,Object>> queryProfitDetailLowerMonthList (Map<String, Object> param);

    Long queryProfitDetailLowerMonthCount(Map<String, Object> param);

    /**
     * 不包含下级的导出数据
     * @param param
     * @return
     */
    List<Map<String,Object>> exportByFNoChild (Map<String, Object> param);

    /**
     * 包含数据的导出下级
     * @param param
     * @return
     */
    List<Map<String,Object>> exportByFHaveChild (Map<String, Object> param);

    /**
     * @Author: Zhang Lei
     * @Description: 分润计算前更新代理商信息
     * @Date: 12:42 2019/1/24
     */
    void updateAgentInfoBeforeComput(@Param("profitDate") String profitDate);

    ProfitDetailMonth selectByIdAndMonth(ProfitDetailMonth record);

    void updateAgentPayCompany(@Param("profitDate")String profitDate);

    /**代理商退出冻结*/
    void updateMonthProfitFozzen(@Param("profitDate")String profitDate);

    /**更改月分润分润状态*/
    void updateStatusFreeze(@Param("date") String date,@Param("freezeAgent") FreezeAgent freezeAgent);

}