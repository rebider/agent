package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.ProfitDeduction;

/**
 * @author zhaodw
 * @Title: ProfitDeductionService
 * @ProjectName agentManage
 * @Description: 扣款业务逻辑接口
 * @date 2018/7/2417:27
 */
public interface ProfitDeductionService {

    /**
     * 获取扣款分页列表
     * @param profitDeduction 扣款信息
     * @param page 分页信息
     * @return 分页列表
     */
    PageInfo getProfitDeductionList(ProfitDeduction profitDeduction, Page page);

    /**
     * 获取扣款信息
     * @param id 不能为空
     * @return 扣款信息
     */
    ProfitDeduction getProfitDeductionById(String id);

    /**
     * 修改扣款分期状态
     * @param id  扣款id
     * @param status 状态
     */
    void updateStagingStatusById(String id, String status);
}
