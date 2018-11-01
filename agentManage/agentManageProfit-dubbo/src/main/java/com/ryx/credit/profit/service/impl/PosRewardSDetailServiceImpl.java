package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.PAgentMergeMapper;
import com.ryx.credit.profit.dao.PosRewardDetailMapper;
import com.ryx.credit.profit.pojo.PosRewardDetail;
import com.ryx.credit.profit.pojo.PosRewardDetailExample;
import com.ryx.credit.profit.service.PosRewardSDetailService;
import com.ryx.credit.service.dict.IdService;
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
    @Autowired
    private IdService idService;
    @Override
    public PageInfo getRewardDetailList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = posRewardDetailMapper.getRewardDetailCount(param);
        List<Map<String, Object>> list = posRewardDetailMapper.getRewardDetailList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        System.out.println("查询============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }

    @Override
    public void insert(PosRewardDetail posrewardDetail) {
        posrewardDetail.setId(idService.genId(TabId.p_pos_reward));
        posRewardDetailMapper.insert(posrewardDetail);
    }

    @Override
    public PosRewardDetail getPosRewardDetail(PosRewardDetail posRewardDetail) {
        PosRewardDetailExample posRewardDetailExample = new PosRewardDetailExample();
        PosRewardDetailExample.Criteria create = posRewardDetailExample.createCriteria();
        create.andPosAgentIdEqualTo(posRewardDetail.getPosAgentId());
        create.andProfitPosDateEqualTo(posRewardDetail.getProfitPosDate());
        List<PosRewardDetail> list = posRewardDetailMapper.selectByExample(posRewardDetailExample);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void updatePosRewardDetail(PosRewardDetail posRewardDetail) {
//        posRewardDetailMapper.
    }
}
