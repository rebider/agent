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
}
