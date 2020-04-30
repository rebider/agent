package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.PmsProfit;

import java.util.List;
import java.util.Map;

public interface IProfitManageService {

    public String queryProfitAmt(String month);

    public PageInfo profitDayList(Map<String,Object> map, PageInfo pageInfo);

    public List<PmsProfit> getTabCounts(PmsProfit profit);

    public List<String> getTableHead(PmsProfit profit);

    public List<Map<String, Object>> getTableData(PmsProfit profit);

    /**
     * 本次查询的可以出款的批次（下级查询的）
     * @param params
     * @return
     */
    List<String> queryAllListWithLowerBalanceId(Map<String, Object> params);

    /**
     * 本次查询的可以出款的批次
     *
     * @param params 传递的查询条件
     * @return
     */
    List<String> queryAllListBalanceId(Map<String, Object> params);

    /**
     * 出款
     * @param parm
     * @return
     */
    String billFunction( List<String> parm);
}
