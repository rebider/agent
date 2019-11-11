package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PmsProfit;
import com.ryx.credit.profit.pojo.PmsProfitExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PmsProfitMapper {
    long countByExample(PmsProfitExample example);

    int deleteByExample(PmsProfitExample example);

    int insert(PmsProfit record);

    int insertSelective(PmsProfit record);

    List<PmsProfit> selectByExample(PmsProfitExample example);

    PmsProfit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PmsProfit record);

    int updateByPrimaryKey(PmsProfit record);


    /**
     * 查询全部对象
     *
     * @param params 传递的查询条件，包括page对象.
     * @return
     */
    List<PmsProfit> queryAllList(Map<String, Object> params);

    Integer queryAllCount(Map<String, Object> params);

    List<PmsProfit> queryAllListWithLower(Map<String, Object> params);

    Integer queryAllCountWithLower(Map<String, Object> params);


    /**
     * 查询存在多少tab页
     *
     * @param profit
     * @return
     * @throws Exception
     */
    List<PmsProfit> getTabCounts(PmsProfit profit);
    /**
     * 获取sheet页的列数
     *
     * @param profit
     * @return
     * @throws Exception
     */
    PmsProfit getSheetColumn(PmsProfit profit);
    /**
     * 根据下标获取table头
     *
     * @param profit
     * @return
     * @throws Exception
     */
    List<PmsProfit> getTableHeadList(PmsProfit profit);
    /**
     * 根据下标获取table内容
     *
     * @param profit
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getTableData(PmsProfit profit);

    /**
     * 根据agent_id 和 agent_name 查询是否有该代理商
     */
    List<Map<String,Object>> isAgent(Map<String, Object> params);

    String queryProfitAmt(@Param(value = "month")String month);
}