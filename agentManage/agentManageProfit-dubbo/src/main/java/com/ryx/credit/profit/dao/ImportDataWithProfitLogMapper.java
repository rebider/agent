package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ImportDataWithProfitLog;
import com.ryx.credit.profit.pojo.ImportDataWithProfitLogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ImportDataWithProfitLogMapper {
    long countByExample(ImportDataWithProfitLogExample example);

    int deleteByExample(ImportDataWithProfitLogExample example);

    int insert(ImportDataWithProfitLog record);

    int insertSelective(ImportDataWithProfitLog record);

    List<ImportDataWithProfitLog> selectByExample(ImportDataWithProfitLogExample example);

    int updataNoteAndStatusByBatchCode(@Param("batchCode") String batchCode, @Param("status") String status, @Param("note") String note);
}