package com.ryx.credit.profit.service;

import com.ryx.credit.profit.pojo.ProfitDeduction;

import java.util.Map;

public interface IProfitMergeDeductionService {

    /***
     * @Description: 新增扣款信息
     * @Param: 扣款对象
     * @Author: chenliang
     * @Date: 2019/1/17
     */
    int insert(ProfitDeduction deduction);

    Map ProfitMergeDeduction(Map<String,Object> params);
}
