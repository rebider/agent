package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;

import java.util.List;
import java.util.Map;

/**
 *代理商缴纳款获取
 * @Author chenqiutian
 * @Create 2019/1/24 16：12
 */
public interface IContributionsService {

    /**
     * 批量新增缴纳款数据
     */
    public List<Map<String, Object>> batchInsertDeduction(List<Map<String, Object>> list, String deductionDate) throws ProcessException;;

}
