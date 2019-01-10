package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.TaxDeductionDetail;

import java.util.List;

public interface ITaxDeductionDetailService {

    PageInfo posDeductTaxList(TaxDeductionDetail taxDeductionDetail, Page page,String dateStart,String dateEnd);

    PageInfo posDirectlyDeductTaxList (TaxDeductionDetail taxDeductionDetail,Page page,String dateStart,String dateEnd);

    int updateAdjust(TaxDeductionDetail taxDeductionDetail);

    TaxDeductionDetail selectById(String id);

    List<TaxDeductionDetail> query(TaxDeductionDetail taxDeductionDetail);

    PageInfo queryAndSubordinate(TaxDeductionDetail taxDeductionDetail,Page page);
}
