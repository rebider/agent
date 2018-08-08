package com.ryx.credit.profit.service;/**
 * @Auther: zhaodw
 * @Date: 2018/8/6 09:06
 * @Description:
 */

import com.ryx.credit.profit.pojo.ProfitDetailMonth;

/**
 * 月度分润明细业务接口
 *
 * @author zhaodw
 * @create 2018/8/6
 * @since 1.0.0
 */
public interface ProfitDetailMonthService {

    /*** 
    * @Description: 新增分润明细
    * @Param:  分润明细对象
    * @Author: zhaodw
    * @Date: 2018/8/6 
    */ 
    void insert(ProfitDetailMonth profitDetailMonth);

    /***
     * @Description: 修改分润明细
     * @Param:  分润明细对象
     * @Author: zhaodw
     * @Date: 2018/8/6
     */
    void update(ProfitDetailMonth profitDetailMonth);
}
