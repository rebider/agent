package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.PProfitFactor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Author Lihl
 * @Date 2018/08/02
 * 分润管理：商业保理
 */
public interface ProfitFactorService {

    PageInfo getProfitFactorList(Map<String, Object> param, PageInfo pageInfo);

    public int insertImportData(PProfitFactor pProfitFactor);

    public List<String> addList(List<List<String>> data, String userId)throws Exception;

    PProfitFactor selectByData(PProfitFactor profitFactor);

    int resetDataFactor();

    Map<String,Object> profitCount(Map<String, Object> param);

    /**
     * @Author: Zhang Lei
     * @Description: 计算保理扣款
     * @Date: 16:58 2019/1/14
     */
    BigDecimal blDeduction(Map<String,Object> param);
}
