package com.ryx.credit.profit.service;/**
 * @Auther: zhaodw
 * @Date: 2018/8/6 14:08
 * @Description:
 */

import com.ryx.credit.profit.pojo.OrganTranMonthDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 代理商交易明细
 *
 * @author zhaodw
 * @create 2018/8/6
 * @since 1.0.0
 */
public interface OrganTranMonthDetailService {

    /***
    * @Description: 新增
    * @Param:  交易信息
    * @Author: zhaodw
    * @Date: 2018/8/6
    */
    void insert(OrganTranMonthDetail organTranMonthDetail);

    /***
     * @Description: 修改
     * @Param:  交易信息
     * @Author: zhaodw
     * @Date: 2018/8/6
     */
    void update(OrganTranMonthDetail organTranMonthDetail);
    
    /*** 
    * @Description: 根据条件获取机构交易金额
    * @Param:  organTranMonthDetail 交易明细
    * @return:  机构交易明细
    * @Author: zhaodw 
    * @Date: 2018/8/10 
    */ 
    List<OrganTranMonthDetail> getOrganTranMonthDetailList(OrganTranMonthDetail organTranMonthDetail);

    /***
    * @Description: 根据所有下级查询交易金额总和
    * @Param:  param childAgentIds 下级机构id列表
    * @return: 汇总的金额对象
    * @Author: zhaodw
    * @Date: 2018/8/10
    */
    OrganTranMonthDetail getChildSumTranAmt(Map<String, Object> param);
}
