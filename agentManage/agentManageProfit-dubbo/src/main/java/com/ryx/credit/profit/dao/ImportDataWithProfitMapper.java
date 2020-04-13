package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ImportDataWithProfit;
import com.ryx.credit.profit.pojo.ImportDataWithProfitExample;
import java.util.List;
import java.util.Map;

public interface ImportDataWithProfitMapper {
    long countByExample(ImportDataWithProfitExample example);

    int deleteByExample(ImportDataWithProfitExample example);

    int insert(ImportDataWithProfit record);

    int insertSelective(ImportDataWithProfit record);

    List<ImportDataWithProfit> selectByExample(ImportDataWithProfitExample example);

    ImportDataWithProfit selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImportDataWithProfit record);

    int updateByPrimaryKey(ImportDataWithProfit record);

    int insertProfitData(List<Map<String, Object>> profitDatas);

    int deleteProfitDataByBatchCode(String batchCode);
}