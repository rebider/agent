package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.PAgentMergeMapper;
import com.ryx.credit.profit.dao.PosRewardDetailMapper;
import com.ryx.credit.profit.pojo.PosRewardDetail;
import com.ryx.credit.profit.service.PosRewardSDetailService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("posRewardSDetailService")
public class PosRewardSDetailServiceImpl implements PosRewardSDetailService {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(PosRewardSDetailServiceImpl.class);
    @Autowired
    private PosRewardDetailMapper posRewardDetailMapper;
    @Override
    public PageInfo getRewardDetailList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = posRewardDetailMapper.getRewardDetailCount(param);
        List<Map<String, Object>> list = posRewardDetailMapper.getRewardDetailList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        System.out.println("查询============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }
}
