package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.*;

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

    int deletePmsProfitTemp(String month,String user);

    int deletePmsProfit(String month,String user);

    int updateByPrimaryKeySelective(PmsProfitTempWithBLOBs record);

    int updateByPrimaryKeySelective(PmsProfit record);

    List<Map<String,Object>> checkoutData(String agentId , String busCode);

   int insertSelective(PmsProfitTempWithBLOBs record) ;

  int insertSelective(PmsProfit record);

    int  save(PmsProfit record);

    Map<String,Object> disposeSheet(List<Map<String, String>> sheetlists, String sheetName, int columnNum, String month, String userId, int sheetOrder);

  PageInfo selectByMap(Map<String,String> param,Page page);
}
