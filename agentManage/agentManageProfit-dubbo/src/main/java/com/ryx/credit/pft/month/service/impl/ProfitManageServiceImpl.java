package com.ryx.credit.pft.month.service.impl;

import com.ryx.credit.common.enumc.BillStatus;
import com.ryx.credit.common.enumc.FreeCause;
import com.ryx.credit.common.enumc.FreeStatus;
import com.ryx.credit.common.enumc.ProfitDataImportType;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.PageInfo;

import com.ryx.credit.pft.month.dao.PmsProfitMapper;
import com.ryx.credit.profit.pojo.PmsProfit;
import com.ryx.credit.profit.service.IProfitManageService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("profitManageService")
public class ProfitManageServiceImpl implements IProfitManageService {

    private static Logger logger = LoggerFactory.getLogger(ProfitManageServiceImpl.class);

    @Autowired
    private PmsProfitMapper pmsProfitMapper;

    @Autowired
    IdService idService;

    @Override
    public String queryProfitAmt(String month) {
        String totalProfitAmt = pmsProfitMapper
                .queryProfitAmt(month);
        return totalProfitAmt;
    }

    @Override
    public PageInfo profitDayList(Map<String, Object> map, PageInfo pageInfo) {
        List<Map<String, Object>> pmsProfits = new ArrayList<>();
        Integer count = 0;


        //是否查看下级标识
        String isNo = map.get("isNo") == null ? null : map.get("isNo").toString();
        logger.info("是否查看下级标识： isNO=====" + isNo);
        if ("true".equals(isNo)) {
            List<Map<String, Object>> agents = pmsProfitMapper.isAgent(map);
            if (null != agents && agents.size() != 1) {
                logger.info("查看下级条件未满足============agents.size:" + (agents == null ? 0 : agents.size()));
                return pageInfo;
            } else {
                if (map.get("AGENT_ID") == null) {
                    map.put("AGENT_ID", agents.get(0).toString());
                }
            }
            pmsProfits = pmsProfitMapper.queryAllListWithLower(map);
            count = pmsProfitMapper.queryAllCountWithLower(map);
        } else {
            pmsProfits = pmsProfitMapper.queryAllList(map);
            count = pmsProfitMapper.queryAllCount(map);
        }
        pmsProfits.forEach(pmsProfit -> {
            if(BillStatus.WCK.key.equals(String.valueOf(pmsProfit.get("BILL_STATUS")))||BillStatus.CKSB.key.equals(String.valueOf(pmsProfit.get("BILL_STATUS")))||BillStatus.SPSB.key.equals(String.valueOf(pmsProfit.get("BILL_STATUS")))){
                pmsProfit.put("BALANCE_ID_ID", pmsProfit.get("BALANCE_ID"));
            }
            pmsProfit.put("FREESTATUS_MSG", FreeStatus.getContentByValue(new BigDecimal(String.valueOf(pmsProfit.get("FREESTATUS")))));
            pmsProfit.put("BILL_STATUS_MSG", BillStatus.getContentByValue(String.valueOf(pmsProfit.get("BILL_STATUS"))));
            pmsProfit.put("PROFIT_TYPE_MSG", ProfitDataImportType.getContentByValue(String.valueOf(pmsProfit.get("PROFIT_TYPE"))));
            if (String.valueOf(pmsProfit.get("FREESTATUS")).equals(String.valueOf(FreeStatus.DJ.code))) {
                List<Map<String, Object>> queryFrezzeCauseMap = pmsProfitMapper.queryFrezzeCause(FastMap.fastMap("AGENT_ID", pmsProfit.get("UNIQUE_FLAG")).putKeyV("FREEZE_STATUS", pmsProfit.get("FREESTATUS")));
                for (Map<String, Object> stringObjectMap : queryFrezzeCauseMap) {
                    pmsProfit.put("FREEZE_CAUSE_MSG", (pmsProfit.get("FREEZE_CAUSE") == null ? "" : pmsProfit.get("FREEZE_CAUSE")) + FreeCause.getContentByValue(String.valueOf(stringObjectMap.get("FREEZE_CAUSE"))) + ",");
                }
            }
        });
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
            /*  profit.setQueryField("/data/Cell");*/
            List<PmsProfit> profits = pmsProfitMapper.getTableHeadList(profit);

            for (PmsProfit t : profits) {//跳过空标题列
                if (t.getSheetHead() == null) {
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
        for (Map<String, Object> a : ma) {
            String sheet_data = a.get("SHEET_DATA").toString();
            logger.info("=========sheet_data:" + sheet_data);
        }
        return ma;
    }

    @Override
    public List<String> queryAllListWithLowerBalanceId(Map<String, Object> params) {
        return pmsProfitMapper.queryAllListWithLowerBalanceId(params);
    }

    @Override
    public List<String> queryAllListBalanceId(Map<String, Object> params) {
        return pmsProfitMapper.queryAllListBalanceId(params);
    }


    @Override
   public String billFunction( List<String> parm){
         /* 出款批次号*/
       String BATCH_NO = idService.getPPT2Id();


        /*胜豪接着写*/
        /*记得审批启动后更新我的出款状态，出款批次号，申请时间等*/
        return "出款成功";
    }

}
