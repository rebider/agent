package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.PBalanceSerialLsMapper;
import com.ryx.credit.profit.pojo.PBalanceSerialLsExample;
import com.ryx.credit.profit.service.IPBalanceSerialLsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("pBalanceSerialLsService")
public class PBalanceSerialLsServiceImpl implements IPBalanceSerialLsService {

    @Autowired
    PBalanceSerialLsMapper pBalanceSerialLsMapper;

    @Override
    public PageInfo getPBalanceSerialLsList(Map<String, Object> param,PageInfo pageInfo) {
        List<Map<String, Object>> list = pBalanceSerialLsMapper.getPBalanceSerialLsList(param);
        Long total = pBalanceSerialLsMapper.getPBalanceSerialLsCount(param);

        pageInfo.setRows(list);
        pageInfo.setTotal(total.intValue());
        System.out.println("查询============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }
}
