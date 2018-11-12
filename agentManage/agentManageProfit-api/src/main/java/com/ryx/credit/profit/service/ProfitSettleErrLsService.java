package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.ProfitSettleErrLs;

import java.util.List;
import java.util.Map;

/**
 * 退单管理业务接口
 * @author zhaodw
 * @create 2018/7/24
 * @since 1.0.0
 */
public interface ProfitSettleErrLsService {

    /**
     * 根据条件获取退单列表
     * @author zhaodw
     * @param settleErr 退单查询条件
     * @param page 分页对象
     * @return 返回退单分页列表
     */
    PageInfo getProfitSettleErrList(ProfitSettleErrLs settleErr, Page page);

    List<ProfitSettleErrLs> getNotDeductionProfitSettleErrLsList(Map<String, Object> param);
    
    /*** 
    * @Description:插入退单信息
    * @Param:  profitSettleErrLs 退单信息
    * @Author: zhaodw
    * @Date: 2018/7/30 
    */ 
    void inset(ProfitSettleErrLs profitSettleErrLs);
}