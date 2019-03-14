package com.ryx.credit.service.order;

import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2019/3/13.
 */
public interface OldCompensateService {

    List<Map<String,Object>> getOrderMsgByExcel(List<List<Object>> excelList);
}
