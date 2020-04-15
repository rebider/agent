package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 线下导入-返现明细查询接口
 * @Author cqt
 * @Date 2020-04-13 9：45
 */
public interface IDayBackDetailService {

    /**
     * 获取返现明细数据列表--区分大区/省区/代理商/其他
     * @param param
     * @return
     */
    PageInfo getImportList(Map<String,Object> param, Page page);

    /**
     * 获取代理商某月的sheet页并字段信息
     */
    List<Map<String,Object>> getDistinctSheetInfo(Map<String,Object> param);

    /**
     * 根据条件获取某sheet页内全部信息-AG，月份，代理商名称，所属省区，sheet页编号，sheet页名称，类型,支持分页
     * @param param
     * @return
     */
    List<Map<String,Object>> getListBySheet(Map<String,Object> param);


}
