package com.ryx.credit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.dao.CStrategyMapper;
import com.ryx.credit.pojo.admin.CStrategy;
import com.ryx.credit.pojo.admin.CStrategyExample;
import com.ryx.credit.service.ICStrategyService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CStrategyServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 17:52
 * To change this template use File | Settings | File Templates.
 */
@Service("cStrategyService")
public class CStrategyServiceImpl implements ICStrategyService {
    @Autowired
    private CStrategyMapper cStrategyMapper;

    @Override
    public int insert(CStrategy record) {
        return cStrategyMapper.insert(record);
    }

    @Override
    public int insertSelective(CStrategy record) {
        return cStrategyMapper.insertSelective(record);
    }

    @Override
    public CStrategy selectByPrimaryKey(BigDecimal id) {
        return cStrategyMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CStrategy record) {
        return cStrategyMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CStrategy record) {
        return cStrategyMapper.updateByPrimaryKey(record);
    }

    @Override
    public Map configExample(Page page, CStrategy cStrategy) {
        if(cStrategy!=null && page !=null){
            CStrategyExample example = new CStrategyExample();
            CStrategyExample.Criteria criteria = example.or();
            if(cStrategy.getId()!=null)
                criteria.andIdEqualTo(cStrategy.getId());
            if(cStrategy.getName()!=null&& StringUtils.isNotBlank(String.valueOf(cStrategy.getName())))
                criteria.andNameEqualTo(cStrategy.getName());
            if(cStrategy.getOperator()!=null)
                criteria.andOperatorEqualTo(cStrategy.getOperator());
            if(cStrategy.getType()!=null)
                criteria.andTypeEqualTo(cStrategy.getType());
            if(cStrategy.getValue()!=null)
                criteria.andValueEqualTo(cStrategy.getValue());
            int count = cStrategyMapper.countByExample(example);
            page.setCount(count);
            example.setPage(page);
            example.setOrderByClause("CREATE_TIME "+Page.ORDER_DIRECTION_DESC);
            HashMap hashMap = new HashMap();
            hashMap.put("list",cStrategyMapper.selectByExample(example));
            hashMap.put("page",page);
            return  hashMap;
        }else{
            return  null;
        }
    }
}
