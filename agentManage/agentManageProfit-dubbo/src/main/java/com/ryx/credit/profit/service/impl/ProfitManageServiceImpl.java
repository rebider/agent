package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.PmsProfitMapper;
import com.ryx.credit.profit.pojo.PmsProfit;
import com.ryx.credit.profit.service.IProfitManageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("profitManageService")
public class ProfitManageServiceImpl implements IProfitManageService {

    private static Logger logger = LoggerFactory.getLogger(ProfitManageServiceImpl.class);

    @Autowired
    private PmsProfitMapper pmsProfitMapper;

    @Override
    public String queryProfitAmt(String month) {
        String totalProfitAmt = pmsProfitMapper
                .queryProfitAmt(month);
        return totalProfitAmt;
    }

    @Override
    public PageInfo profitDayList(Map<String,Object> map, PageInfo pageInfo){
        List<PmsProfit> pmsProfits = new ArrayList<>();
        Integer count = 0;


        //是否查看下级标识
        String isNo = map.get("isNo")==null?null:map.get("isNo").toString();
        logger.info("是否查看下级标识： isNO====="+isNo);
        if ("true".equals(isNo)){       //查看下级
            List<Map<String, Object>> agents = pmsProfitMapper.isAgent(map);
            if (null!=agents && agents.size()!=1){
                logger.info("查看下级条件未满足============agents.size:"+(agents==null?0:agents.size()));
                return pageInfo;
            }else {
                if (map.get("AGENT_ID") == null) {
                    map.put("AGENT_ID", agents.get(0).toString());
                }
            }
            pmsProfits = pmsProfitMapper.queryAllListWithLower(map);
            count = pmsProfitMapper.queryAllCountWithLower(map);
        }else{
            pmsProfits = pmsProfitMapper.queryAllList(map);
            count = pmsProfitMapper.queryAllCount(map);
        }
        pageInfo.setRows(pmsProfits);
        pageInfo.setTotal(count);
        return pageInfo;
    }

    @Override
    public List<PmsProfit> getTabCounts(PmsProfit profit) {
        return pmsProfitMapper.getTabCounts(profit);
    }

    @Override
    public List<String> getTableHead(PmsProfit profit) {
        List<String> resultLists = new ArrayList<String>();
        // 获取列数
        PmsProfit pf = pmsProfitMapper.getSheetColumn(profit);
        if (pf != null) {
            profit.setSheetColumn(pf.getSheetColumn());//
            profit.setQueryField("/data/Cell");
            List<PmsProfit> profits = pmsProfitMapper.getTableHeadList(profit);

            for(PmsProfit t: profits) {//跳过空标题列
                if(t.getSheetHead()==null) {
                    continue;
                }
                resultLists.add(t.getSheetHead());
            }
        }
        return resultLists;
    }

    @Override
    public List<Map<String, Object>> getTableData(PmsProfit profit) {
        List<Map<String, Object>> ma = pmsProfitMapper.getTableData(profit);
        for (Map<String,Object> a :ma){
            String sheet_data = a.get("SHEET_DATA").toString();
            logger.info("=========sheet_data:"+sheet_data);
        }
        return ma;
    }
}
