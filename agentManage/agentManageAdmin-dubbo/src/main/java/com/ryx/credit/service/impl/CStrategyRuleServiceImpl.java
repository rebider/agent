package com.ryx.credit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.dao.CStrategyRuleMapper;
import com.ryx.credit.pojo.admin.CStrategyRule;
import com.ryx.credit.pojo.admin.CStrategyRuleExample;
import com.ryx.credit.service.ICStrategyRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CStrategyRuleServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 17:48
 * To change this template use File | Settings | File Templates.
 */
@Service("cStrategyRuleService")
public class CStrategyRuleServiceImpl implements ICStrategyRuleService {
    @Autowired
    private CStrategyRuleMapper cStrategyRuleMapper;

    @Override
    public int insert(CStrategyRule record) {
        return cStrategyRuleMapper.insert(record);
    }

    @Override
    public int insertSelective(CStrategyRule record) {
        return cStrategyRuleMapper.insertSelective(record);
    }

    @Override
    public CStrategyRule selectByPrimaryKey(BigDecimal id) {
        return cStrategyRuleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CStrategyRule record) {
        return cStrategyRuleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CStrategyRule record) {
        return cStrategyRuleMapper.updateByPrimaryKey(record);
    }

    @Override
    public Map configExample(Page page, CStrategyRule cStrategyRule) {
        if(cStrategyRule!=null && page !=null){
            CStrategyRuleExample example = new CStrategyRuleExample();
            CStrategyRuleExample.Criteria criteria = example.or();
            if(cStrategyRule.getId()!=null)
                criteria.andIdEqualTo(cStrategyRule.getId());
            if(cStrategyRule.getRuleId()!=null)
                criteria.andRuleIdEqualTo(cStrategyRule.getRuleId());
            if(cStrategyRule.getStrategyId()!=null)
                criteria.andStrategyIdEqualTo(cStrategyRule.getStrategyId());
            int count = cStrategyRuleMapper.countByExample(example);
            page.setCount(count);
            example.setPage(page);
            example.setOrderByClause("UPDATE_TIME "+Page.ORDER_DIRECTION_DESC);
            HashMap hashMap = new HashMap();
            hashMap.put("list",cStrategyRuleMapper.selectByExample(example));
            hashMap.put("page",page);
            return  hashMap;
        }else{
            return  null;
        }
    }

    public int deleteByExample(CStrategyRule cStrategyRule){
        if(cStrategyRule!=null){
            CStrategyRuleExample example = new CStrategyRuleExample();
            CStrategyRuleExample.Criteria criteria = example.or();
            if(cStrategyRule.getId()!=null)
                criteria.andIdEqualTo(cStrategyRule.getId());
            if(cStrategyRule.getStrategyId()!=null)
                criteria.andStrategyIdEqualTo(cStrategyRule.getStrategyId());
            if(cStrategyRule.getRuleId()!=null)
                criteria.andRuleIdEqualTo(cStrategyRule.getRuleId());
            return  cStrategyRuleMapper.deleteByExample(example);
        }else{
            return  -1;
        }
    }
}
