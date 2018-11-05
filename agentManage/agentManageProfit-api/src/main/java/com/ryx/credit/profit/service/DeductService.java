package com.ryx.credit.profit.service;

import java.util.Map;

/**
 * @author yangmx
 * @desc
 */
public interface DeductService {

    /**
     * 执行分润计算
     * @param map
     * @return
     * @throws Exception
     */
    public Map<String, Object> execut(Map<String, Object> map) throws Exception;

    /**
     * 其他操作
     * 1、机具：终审后，查询机具未扣款订单，通知订单系统，未扣款订单与金额
     * 2、Pos奖励：初始化POS奖励的基础数据
     */
    void otherOperate();

    /**
     *
     */
    void clearDetail();
}
