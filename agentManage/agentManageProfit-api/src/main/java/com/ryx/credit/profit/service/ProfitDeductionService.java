package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.ProfitDeduction;

import java.util.List;

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
     * 修改扣款信息
     * @param  deduction 扣款信息
     */
    void updateProfitDeduction(ProfitDeduction deduction);
    
    /*** 
    * @Description: 新增扣款信息
    * @Param:  扣款对象
    * @Author: zhaodw
    * @Date: 2018/7/30 
    */ 
    void insert(ProfitDeduction deduction);

    /*** 
    * @Description: 批量新增其他扣款
    * @Param:  datas 扣款信息
    * @Author: zhaodw
    * @Date: 2018/7/31 
    */ 
    void batchInsertOtherDeduction(List<List<Object>> datas);
}
