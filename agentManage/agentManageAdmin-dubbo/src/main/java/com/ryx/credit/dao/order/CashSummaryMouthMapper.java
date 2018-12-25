package com.ryx.credit.dao.order;



import com.ryx.credit.pojo.admin.order.CashSummaryMouth;
import com.ryx.credit.pojo.admin.order.CashSummaryMouthExample;
import com.ryx.credit.pojo.admin.order.CashSummaryMouthKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CashSummaryMouthMapper {
    long countByExample(CashSummaryMouthExample example);

    int deleteByExample(CashSummaryMouthExample example);

    int insert(CashSummaryMouth record);

    int insertSelective(CashSummaryMouth record);

    List<CashSummaryMouth> selectByExample(CashSummaryMouthExample example);

    CashSummaryMouth selectByPrimaryKey(CashSummaryMouthKey key);

    int updateByPrimaryKeySelective(CashSummaryMouth record);

    int updateByPrimaryKey(CashSummaryMouth record);

    List<CashSummaryMouth> selectCashSummaryMouthData(@Param("date_str")String dateString,@Param("is_clo_invoice")String isCloInvoice);
}