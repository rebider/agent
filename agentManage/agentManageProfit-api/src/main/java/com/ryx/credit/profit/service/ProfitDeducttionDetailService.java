package com.ryx.credit.profit.service;

import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDeducttionDetail;

import java.util.List;

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
    public void insertDeducttionDetail(ProfitDeduction profitDeduction);

    /**
     * 查询扣款明细
     * @param profitDeduction
     * @return
     */
    public List<ProfitDeducttionDetail> getDeducttionDetailList(ProfitDeduction profitDeduction);
}
