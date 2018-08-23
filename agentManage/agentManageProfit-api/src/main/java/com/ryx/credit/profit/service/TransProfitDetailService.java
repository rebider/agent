package com.ryx.credit.profit.service;/**
 * @Auther: zhaodw
 * @Date: 2018/8/23 09:55
 * @Description:
 */

import com.ryx.credit.profit.pojo.TransProfitDetail;

/**
 * 分润交易明细接口
 *
 * @author zhaodw
 * @create 2018/8/23
 * @since 1.0.0
 */
public interface TransProfitDetailService {
    
    /*** 
    * @Description: 新增分润交易明细 
    * @Param: transProfitDetail 分润交易明细
    * @Author: zhaodw
    * @Date: 2018/8/23 
    */ 
    void insert(TransProfitDetail transProfitDetail);
}
