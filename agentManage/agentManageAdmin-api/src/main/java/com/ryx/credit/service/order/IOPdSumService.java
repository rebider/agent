package com.ryx.credit.service.order;

import com.ryx.credit.pojo.admin.order.OPdSum;

public interface IOPdSumService {

   void insertOPdSum();


   int updateByPrimaryKeySelective(OPdSum record);
}
