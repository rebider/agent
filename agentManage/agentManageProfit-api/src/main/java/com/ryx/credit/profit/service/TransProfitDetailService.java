package com.ryx.credit.profit.service;/**
 * @Auther: zhaodw
 * @Date: 2018/8/23 09:55
 * @Description:
 */

import com.ryx.credit.profit.pojo.TransProfitDetail;

import java.util.List;

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
    
    /*** 
    * @Description:  
    * @Param:  transProfitDetail 交易分润数据对象
    * @return:  交易分润数据列表
    * @Author: zhaodw 
    * @Date: 2018/8/24 
    */ 
    List<TransProfitDetail> getTransProfitDetailList(TransProfitDetail transProfitDetail);

     /*** 
     * @Description: 汇总交易分润明细
     * @Param:  prfitDate 分润日期
     * @return:  交易分润明细
     * @Author: zhaodw 
     * @Date: 2018/8/27 
     */ 
     List<TransProfitDetail> getPosTransProfitDetailSumList(String prfitDate);

}
