package com.ryx.credit.profit.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.ImportDataWithProfit;
import com.ryx.credit.profit.pojo.ImportDataWithProfitExample;
import org.apache.ibatis.annotations.Param;

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

    List<Map<String,String>> checkDataAll(List<Map<String,String>> datas);

    List<Map<String,Object>> getDistinctDayBackList(@Param("param") Map<String,Object> param, @Param("page")Page page);

    long getCountDistinctDayBackList(@Param("param") Map<String,Object> param, @Param("page")Page page);

    List<Map<String,Object>> getDistinctSheetInfo(@Param("param") Map<String,Object> param);

    List<Map<String,Object>> getListBySheetInfo(@Param("param") Map<String,Object> param);

}