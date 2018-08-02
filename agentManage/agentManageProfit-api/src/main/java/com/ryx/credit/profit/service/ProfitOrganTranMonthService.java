package com.ryx.credit.profit.service;/**
 * @Auther: zhaodw
 * @Date: 2018/8/1 11:33
 * @Description:
 */

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.ProfitOrganTranMonth;

/**
 * 每月交易核对
 *
 * @author zhaodw
 * @create 2018/8/1
 * @since 1.0.0
 */
public interface ProfitOrganTranMonthService {
    /***
    * @Description:修改交易信息
    * @Param: profitOrganTranMonth 交易信息
    * @Author: zhaodw
    * @Date: 2018/8/1
    */
    void insert(ProfitOrganTranMonth profitOrganTranMonth);

    /***
    * @Description:修改交易信息
    * @Param: profitOrganTranMonth 交易信息
    * @Author: zhaodw
    * @Date: 2018/8/1
    */
    void update(ProfitOrganTranMonth profitOrganTranMonth);

    /*** 
    * @Description: 查询列表
    * @Param:  profitOrganTranMonth 交易信息
    * @return:  分页对象
    * @Author: zhaodw 
    * @Date: 2018/8/1 
    */ 
    PageInfo getProfitOrganTranMonthList(ProfitOrganTranMonth profitOrganTranMonth, Page page);
}
