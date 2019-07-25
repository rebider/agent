package com.ryx.credit.service.order;

import com.ryx.credit.common.util.FastMap;

/**
 * 作者：cx
 * 时间：2019/7/19
 * 描述：
 */
public interface OrderRepairService {
    /**
     * 物流重复修复删除
     * @param flg
     * @param orderIds
     * @return
     * @throws Exception
     */
    public FastMap repairDumpOrderLogic(int flg, String orderIds)throws Exception;
}
