package com.ryx.credit.profit.service;

import com.ryx.credit.profit.pojo.ProfitSettleErrLs;
import com.ryx.credit.profit.pojo.ProfitSettleErrLsExample;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 退单管理业务接口
 * @author zhaodw
 * @create 2018/7/24
 * @since 1.0.0
 */
public interface ProfitSettleErrLsService {

    /**
     * 根据条件获取退单列表
     * @author zhaodw
     * @param settleErr 退单查询条件
     * @param page 分页对象
     * @return 返回退单分页列表
     */
    Page<ProfitSettleErrLs> getProfitSettleErrList(ProfitSettleErrLs settleErr, Page page);
}