package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.ImportDataWithProfitMapper;
import com.ryx.credit.profit.pojo.InvoiceApply;
import com.ryx.credit.profit.service.IDayBackDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 线下导入-返现明细查询实现类
 * @Author cqt
 * @Date 2020-04-13 9：45
 */
@Service("dayBackDetailService")
public class DayBackDetailServiceImpl implements IDayBackDetailService {

    @Autowired
    private ImportDataWithProfitMapper importDataWithProfitMapper;

    @Override
    public PageInfo getImportList(Map<String, Object> map, Page page) {
        PageInfo pageInfo = new PageInfo();
        List<Map<String,Object>> list = importDataWithProfitMapper.getDistinctDayBackList(map,page);
        pageInfo.setRows(list);
        pageInfo.setTotal((int)importDataWithProfitMapper.getCountDistinctDayBackList(map, page));
        return pageInfo;
    }

    @Override
    public PageInfo getDetailByAgentIdAndMonth(Map<String, Object> map, Page page) {
        PageInfo pageInfo = new PageInfo();
        List<Map<String,Object>> list = importDataWithProfitMapper.getDayBackDetailList(map,page);
        pageInfo.setRows(list);
        pageInfo.setTotal((int)importDataWithProfitMapper.getCountDayBackDetailList(map,page));
        return pageInfo;
    }

    @Override
    public List<Map<String,Object>> getDistinctSheetInfo(Map<String,Object> param){
        return importDataWithProfitMapper.getDistinctSheetInfo(param);
    }

    @Override
    public List<Map<String, Object>> getListBySheet(Map<String, Object> map) {
        return importDataWithProfitMapper.getListBySheetInfo(map);
    }
}
