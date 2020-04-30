package com.ryx.credit.profit.service;

import com.ryx.credit.profit.pojo.PmsProfitLog;

import java.util.Map;
import java.util.List;

public interface IImportDataWithProfitService {
    /**
     * 插入导入记录
     * @param importLog
     * @return
     */
    int insertImportLog(PmsProfitLog importLog);

    /**
     * 获取导入记录
     * @param batchCode
     * @return
     */
    PmsProfitLog getImportLogByBatchCode(String batchCode);
    /**
     * 插入分润数据
     * @param profitDatas
     * @return
     */
    int insertProfitData(List<Map<String,String>> profitDatas);

    /**
     * 根据分润信息删除分润数据
     * @param profitInfo 分润月份\上传人、业务类型
     * @return
     */
    int deleteProfitDataByProfitInfo(Map<String,Object> profitInfo);

    /**
     * 根据导入批次号删除分润数据
     * @param batchCode
     * @return
     */
    int deleteProfitDataByBatchCode(String batchCode);

    /**
     * 根据导入批次插入分润数据
     * @param batchCode
     * @return
     */
    String insertProfitDataByBatchCode(String batchCode) throws Exception;


}
