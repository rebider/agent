package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;

import java.util.List;
import java.util.Map;

public interface PBalanceSerialService {

    PageInfo getListByMap(Map<String,String> param, Page page);

    List<Map<String,String>> getRefundLog(String balanceId) throws MessageException;

}
