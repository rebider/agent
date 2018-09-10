package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.PosRewardTemplateMapper;
import com.ryx.credit.profit.pojo.PosRewardTemplate;
import com.ryx.credit.profit.pojo.PosRewardTemplateExample;
import com.ryx.credit.profit.service.PosRewardTemplateService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * @author yangmx
 * @desc pos奖励通用模板API
 */
@Service("posRewardTemplateService")
public class PosRewardTemplateServiceImpl implements PosRewardTemplateService{

    @Autowired
    private PosRewardTemplateMapper posRewardTemplateMapper;
    @Autowired
    private IdService idService;
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;

    @Override
    public PageInfo getPosRewardTemplateList(Page page) {
        PosRewardTemplateExample posRewardTemplateExample = new PosRewardTemplateExample();
        posRewardTemplateExample.setPage(page);
        long count = posRewardTemplateMapper.countByExample(null);
        List<PosRewardTemplate> posRewardTemplates = posRewardTemplateMapper.selectByExample(posRewardTemplateExample);
        redisTemplate.opsForValue().set("POS_REWARD_TEMP", JSONObject.toJSONString(posRewardTemplates));
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotal((int) count);
        pageInfo.setRows(posRewardTemplates);
        return pageInfo;
    }

    @Override
    public PosRewardTemplate getPosRewardTemplate(String id) {
        return posRewardTemplateMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updatePosRewardTemplate(PosRewardTemplate posRewardTemplate) {
        posRewardTemplateMapper.updateByPrimaryKeySelective(posRewardTemplate);
    }

    @Override
    public void insertPosRewardTemplate(PosRewardTemplate posRewardTemplate) {
        if(posRewardTemplate != null){
            posRewardTemplate.setId(idService.genId(TabId.p_pos_reward));
            posRewardTemplateMapper.insertSelective(posRewardTemplate);
        }
    }

    @Override
    public List<PosRewardTemplate> getPosRewardTemplateList() {
        List<PosRewardTemplate> posRewardTemplates = posRewardTemplateMapper.selectByExample(null);
        redisTemplate.opsForValue().set("POS_REWARD_TEMP", JSONObject.toJSONString(posRewardTemplates));
        return posRewardTemplates;
    }
}
