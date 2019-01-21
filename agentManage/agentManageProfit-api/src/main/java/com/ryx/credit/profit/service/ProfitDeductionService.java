package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.MessageException;
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
    PageInfo getProfitDeductionList(Map<String, Object> map, ProfitDeduction profitDeduction, Page page);

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

    /***
    * @Description:其他扣款计算
    * @Param: param profitAmt 分润金额 agentId 机构id  parentAgentPid 机构id  type 扣款类型
    * @return: 应扣余额 扣足返回已扣款金额
    * @Author: zhaodw
    * @Date: 2018/8/7
    */
    BigDecimal otherDeductionByType(Map<String, Object> param) throws DeductionException;

    Map<String, Object> otherDeductionHbByType(Map<String, Object> param) throws DeductionException;

    /***
     * @Description:退单扣款计算
     * @Param: param profitAmt 分润金额  bussType 业务类型 02 01 agentPid 机构id  parentAgentPid 机构id
     * @return: 应扣余额 扣足返回已扣款金额
     * @Author: zhaodw
     * @Date: 2018/8/7
     */
    BigDecimal settleErrDeduction(Map<String, Object> param) throws DeductionException;

    /*** 
    * @Description: 扣除合并代理商金额
    * @Param:  
    * @return:  
    * @Author: zhaodw 
    * @Date: 2018/10/17 
    */ 
    Map<String, Object> settleErrHbDeduction(Map<String, Object> param) throws DeductionException;

    /*** 
    * @Description: 获取退单扣款总金额
    * @Param:  profitDeduction 扣款信息
    * @return: 总扣款金额
    * @Author: zhaodw 
    * @Date: 2018/8/12 
    */ 
    BigDecimal getSettleErrDeductionAmt( ProfitDeduction profitDeduction);
    
    /**
     * 查询一条扣款信息
     * @param profitDeduction
     * @return
     */
    public List<ProfitDeduction> getProfitDeduction(ProfitDeduction profitDeduction);

    BigDecimal totalBuckleByMonth(ProfitDeduction profitDeduction);

    /***
    * @Description: 获取所有未扣足的总额
    * @Param:  agentId 代理商id  pare
    * @return:
    * @Author: zhaodw
    * @Date: 2018/10/15
    */
    BigDecimal getNotDeductionSum(String agentId);

    /**
     * 清楚对应考核上月数据
     * @param type
     * @return
     */
    int resetDataDeduction(String type);

    void updateProfitDeductionByMap(Map<String, BigDecimal> deductionMap);



    /**
     * 批量插入考核扣款数据
     * @Create 2018/12/17
     * @Author CQT
     * @param datas
     * @param userId
     */
    public void batchInsertCheckDeduction(List<List<Object>> datas , String userId);

    /**
     * @Author: Zhang Lei
     * @Description: 计算考核扣款
     * @Date: 16:35 2019/1/4
     */
    BigDecimal khDeduction(Map<String,Object> param);

    /**
     * 获取当前代理商总欠款
     * @param map
     * @return
     */
    Map<String,BigDecimal> getNotDeduction(Map<String,String> map);

}
