package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.PosRewardDetailMapper;
import com.ryx.credit.profit.pojo.PosRewardDetail;
import com.ryx.credit.profit.pojo.PosRewardDetailExample;
import com.ryx.credit.profit.service.PosRewardSDetailService;
import com.ryx.credit.service.dict.IdService;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
        posrewardDetail.setId(UUID.randomUUID().toString());
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
        posRewardDetailMapper.updateByPrimaryKeySelective(posRewardDetail);
    }

    @Override
    public void clearPosRewardDetail(String profitDate) {
        posRewardDetailMapper.updateRewradData(profitDate);
    }

    @Override
    public void deleteCurrentDate(String currentDate) {
        PosRewardDetailExample example = new PosRewardDetailExample();
        example.createCriteria().andProfitPosDateEqualTo(currentDate);
        posRewardDetailMapper.deleteByExample(example);
    }

    @Override
    public List<String> queryChildLevelByAgentId(String agentId) {
        return posRewardDetailMapper.queryChildLevelByAgentId(agentId);
    }

    @Override
    public List<PosRewardDetail> getPosRewardDetailList(PosRewardDetail posRewardDetail, List<String> type, List<String> childAgentList) {
        PosRewardDetailExample posRewardDetailExample = new PosRewardDetailExample();
        PosRewardDetailExample.Criteria create = posRewardDetailExample.createCriteria();
        if(StringUtils.isNotBlank(posRewardDetail.getProfitPosDate())){
            create.andProfitPosDateEqualTo(posRewardDetail.getProfitPosDate());
        }
        if(type != null && !type.isEmpty()){
            create.andPosMechanismTypeIn(Arrays.asList("2", "6"));
        }
        if(childAgentList != null && !childAgentList.isEmpty()){
            create.andPosAgentIdIn(childAgentList);
        }

        return posRewardDetailMapper.selectByExample(posRewardDetailExample);
    }
}
