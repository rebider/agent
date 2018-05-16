package com.ryx.credit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.dao.CRuleConditionMapper;
import com.ryx.credit.pojo.admin.CConditionValueExample;
import com.ryx.credit.pojo.admin.CRuleCondition;
import com.ryx.credit.pojo.admin.CRuleConditionExample;
import com.ryx.credit.service.ICRuleConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CRuleConditionServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 17:34
 * To change this template use File | Settings | File Templates.
 */
@Service("cRuleConditionService")
public class CRuleConditionServiceImpl implements ICRuleConditionService {
    @Autowired
    private CRuleConditionMapper cRuleConditionMapper;

    @Override
    public int insert(CRuleCondition record) {
        return cRuleConditionMapper.insert(record);
    }

    @Override
    public int insertSelective(CRuleCondition record) {
        return cRuleConditionMapper.insertSelective(record);
    }

    @Override
    public CRuleCondition selectByPrimaryKey(BigDecimal id) {
        return cRuleConditionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CRuleCondition record) {
        return cRuleConditionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CRuleCondition record) {
        return cRuleConditionMapper.updateByPrimaryKey(record);
    }

    @Override
    public Map configExample(Page page, CRuleCondition cRuleCondition) {
        if(cRuleCondition!=null && page !=null){
            CRuleConditionExample example = new CRuleConditionExample();
            CRuleConditionExample.Criteria criteria = example.or();
            if(cRuleCondition.getId()!=null)
                criteria.andIdEqualTo(cRuleCondition.getId());
            if(cRuleCondition.getConditionValueId()!=null)
                criteria.andConditionValueIdEqualTo(cRuleCondition.getConditionValueId());
            if(cRuleCondition.getRuleId()!=null)
                criteria.andRuleIdEqualTo(cRuleCondition.getRuleId());
            int count = cRuleConditionMapper.countByExample(example);
            page.setCount(count);
            example.setPage(page);
            example.setOrderByClause("UPDATE_TIME "+Page.ORDER_DIRECTION_DESC);
            HashMap hashMap = new HashMap();
            hashMap.put("list",cRuleConditionMapper.selectByExample(example));
            hashMap.put("page",page);
            return  hashMap;
        }else{
            return  null;
        }
    }

    public int deleteByExample(CRuleCondition cRuleCondition){
        if(cRuleCondition!=null ){
            CRuleConditionExample example = new CRuleConditionExample();
            CRuleConditionExample.Criteria criteria = example.or();
            if(cRuleCondition.getId()!=null)
                criteria.andIdEqualTo(cRuleCondition.getId());
            if(cRuleCondition.getRuleId()!=null)
                criteria.andRuleIdEqualTo(cRuleCondition.getRuleId());
            if(cRuleCondition.getConditionValueId()!=null)
                criteria.andConditionValueIdEqualTo(cRuleCondition.getConditionValueId());
            return  cRuleConditionMapper.deleteByExample(example);
        }else{
            return  -1;
        }
    }
}
