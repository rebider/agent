package com.ryx.credit.service.order;

import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.order.OPdSum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface IOPdSumService {

   void insertOPdSum();


   int updateByPrimaryKeySelective(OPdSum record);

   /**
    * 更新状态
    */
   ResultVO uploadStatus(List<Map<String, Object>> maps, BigDecimal payStatus );
}
