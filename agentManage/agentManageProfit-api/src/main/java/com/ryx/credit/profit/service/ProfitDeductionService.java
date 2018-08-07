package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.exceptions.DeductionException;
import com.ryx.credit.profit.pojo.ProfitDeduction;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
    public void batchInsertOtherDeduction(List<List<Object>> datas , String userId);

    /**
     * 根据扣款类型、扣款日期、查询总数
     * @param deductType
     * @param deductDate
     * @return
     */
    public int getProfitDeductionCount(String deductType, String deductDate);

    /**
     * 获取本月调整的扣款明细
     * @param deductDate
     * @return
     */
    public List<Map<String, Object>> getDeductDetail(String deductDate);

    /**
     * 查询一条扣款信息
     * @param profitDeduction
     * @return
     */
    public List<ProfitDeduction> getProfitDeduction(ProfitDeduction profitDeduction);
}
