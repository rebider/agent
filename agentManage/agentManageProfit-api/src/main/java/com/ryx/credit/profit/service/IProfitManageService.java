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
}
