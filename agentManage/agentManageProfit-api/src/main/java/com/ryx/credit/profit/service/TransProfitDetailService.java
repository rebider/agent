package com.ryx.credit.profit.service;/**
 * @Auther: zhaodw
 * @Date: 2018/8/23 09:55
 * @Description:
 */

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    /**
     * POS基础分润
     * @param params
     * @return
     * @AUTHOR chenliang
     */
    PageInfo posBaseProfitList(Map<String,Object> params, PageInfo  page);

    /**
     * 根据BusNum查询基础分润
     * @param transProfitDetail
     * @return
     */
    List<TransProfitDetail> getTransProfitDetailByBusNum(TransProfitDetail transProfitDetail);


    /**
     * 根据类型查询平台号
     * @param type
     * @return
     */
    List<Map<String,Object>> queryBusNum(@Param("type") String type);

}
