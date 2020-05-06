package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PBalanceSerialService {

    PageInfo getListByMap(Map<String,String> param, Page page);

    List<Map<String,String>> getRefundLog(String balanceId) ;

    List<Map<String,String>> getListToDownload(Map<String,String> param);

    Map<String,Object> statisticalData(Map<String,String> param);

    List<Map<String,String>> getNeedNoticeList();

    void updateNoticeStatus( List<Map<String,String>> list, String status);
}
