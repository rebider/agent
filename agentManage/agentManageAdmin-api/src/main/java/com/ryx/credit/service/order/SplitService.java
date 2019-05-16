package com.ryx.credit.service.order;

import com.ryx.credit.common.exception.MessageException;

import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2019/4/25.
 */
public interface SplitService {

    List<Map<String,Object>> getOrderMsgByExcel(List<List<Object>> excelList,String cUser)throws MessageException;

    List<Map<String, Object>> exportSplit(String exportType,String cUser)throws MessageException;

}
