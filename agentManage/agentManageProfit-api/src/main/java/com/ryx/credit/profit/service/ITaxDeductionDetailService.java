package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.TaxDeductionDetail;

import java.util.List;
import java.util.Map;

public interface ITaxDeductionDetailService {

    PageInfo posDeductTaxList(TaxDeductionDetail taxDeductionDetail, Page page,String dateStart,String dateEnd);

    PageInfo posDirectlyDeductTaxList (TaxDeductionDetail taxDeductionDetail,Page page,String dateStart,String dateEnd);

    int updateAdjust(TaxDeductionDetail taxDeductionDetail,TaxDeductionDetail adjustDetail);

    TaxDeductionDetail selectById(String id);

    List<TaxDeductionDetail> query(TaxDeductionDetail taxDeductionDetail);

    PageInfo queryAndSubordinate(TaxDeductionDetail taxDeductionDetail,Page page);

    Map<String,Object> profitCount(Map<String,Object> param,boolean isQuerySubordinate);
}
