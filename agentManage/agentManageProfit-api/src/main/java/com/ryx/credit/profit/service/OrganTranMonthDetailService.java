package com.ryx.credit.profit.service;/**
 * @Auther: zhaodw
 * @Date: 2018/8/6 14:08
 * @Description:
 */

import com.ryx.credit.profit.pojo.OrganTranMonthDetail;

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
}
