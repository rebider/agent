package com.ryx.credit.profit.service;

import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeducttionDetail;

/**
 * @author yangmx
 * @desc 分润扣款明细
 */
public interface ProfitDeducttionDetailService {

    /**
     * 查询是否存在扣款明细
     * @param profitDeduction
     * @return
     */
    public ProfitDeducttionDetail getProfitDeducttionDetail(ProfitDeduction profitDeduction);

    /**
     * 新增扣款明细
     * @param profitDeduction
     */
    public void insertDeducttionDetail(ProfitDeduction profitDeduction)throws Exception;
}
