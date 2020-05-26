package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.*;
import org.springframework.transaction.annotation.Transactional;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;

public interface IPmsProfitLogService {
    long countByExample(PmsProfitLogExample example);

    int deleteByExample(PmsProfitLogExample example);

    int insert(PmsProfitLog record);

    int insertSelective(PmsProfitLog record);

    PageInfo selectByExample(PmsProfitLog example, Page page);

    PmsProfitLog selectByPrimaryKey(String batchNo);

    int updateByPrimaryKeySelective(PmsProfitLog record);

    int updateByPrimaryKey(PmsProfitLog record);

    /*void profitImportImportFile(MultipartFile file, String month, String user);*/

    int deletePmsProfitTemp(PmsProfitTempExample pmsProfitTempExample);

    int deletePmsProfit(PmsProfitExample pmsProfitExample);

    int updateByPrimaryKeySelectiveTemp(PmsProfitTempWithBLOBs record);

    int updateByPrimaryKeySelective(PmsProfit record);

    List<Map<String,Object>> checkoutData(String agentId , String busCode);

   int insertSelectiveTemp(PmsProfitTempWithBLOBs record) ;

  int insertSelective(PmsProfit record);

    int  save(PmsProfit record);

    Map<String, Object> disposeSheet(List<Map<String, String>> sheetlists, String sheetName, int columnNum, PmsProfitLog pmsProfitLog,int sheetOrder) throws MessageException;

    Map<String,Object> disposeUploadExcel(PmsProfitLog pmsProfitLog, String path) throws MessageException;

    Map<String,Object>  disposeUploadExcelSuccess(PmsProfitLog pmsProfitLog, String path) throws MessageException;

    /**
     * 获取页面按钮控制
     * @param MONTH
     * @return
     */
    Map<String, Object> btnIsNo(@Param("MONTH") String MONTH);

    /**
     * 按钮插入表
     * @param param
     * @return
     */
    Map<String, Object> btnInsert(Map<String, String> param);

    /**
     * 按钮修改表
     * @param param
     * @return
     */
    Map<String, Object> btnUpdate(Map<String, String> param);
}
