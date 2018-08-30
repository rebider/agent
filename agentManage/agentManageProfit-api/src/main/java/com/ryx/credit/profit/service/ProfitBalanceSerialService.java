package com.ryx.credit.profit.service;

import com.ryx.credit.profit.pojo.ProfitBalanceSerial;

/**
 * 分润出款流水业务接口
 * @author zhaodw
 * @create 2018/8/29
 * @since 1.0.0
 */
public interface ProfitBalanceSerialService {
    
    /*** 
    * @Description: 生成出款流水
    * @Param:  profitBalanceSerial 出款信息
    * @Author: zhaodw
    * @Date: 2018/8/29 
    */ 
    void insert (ProfitBalanceSerial profitBalanceSerial);

    /***
    * @Description: 修改出款状态及失败原因
    * @Param:  profitBalanceSerial 出款原因
    * @Author: zhaodw
    * @Date: 2018/8/29
    */
    void updateById(ProfitBalanceSerial profitBalanceSerial);

    /***
    * @Description: 通过流水号获取出款信息
    * @Param:  balanceId 出款流水号
    * @return: 出款信息 无则返回null
    * @Author: zhaodw
    * @Date: 2018/8/29
    */
    ProfitBalanceSerial getProfitBalanceSerialById(String balanceId);
}
