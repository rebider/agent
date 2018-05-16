package com.ryx.credit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.dao.CConditionMapper;
import com.ryx.credit.pojo.admin.CCondition;
import com.ryx.credit.pojo.admin.CConditionExample;
import com.ryx.credit.service.ICConditionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CConditionServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 17:04
 * To change this template use File | Settings | File Templates.
 */
@Service("cConditionService")
public class CConditionServiceImpl implements ICConditionService{
    @Autowired
    private CConditionMapper cConditionMapper;

    @Override
    public int insert(CCondition record) {
        return cConditionMapper.insert(record);
    }

    @Override
    public int insertSelective(CCondition record) {
        return cConditionMapper.insertSelective(record);
    }

    @Override
    public CCondition selectByPrimaryKey(BigDecimal id) {
        return cConditionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CCondition record) {
        return cConditionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CCondition record) {
        return cConditionMapper.updateByPrimaryKey(record);
    }

    @Override
    public Map configExample(Page page, CCondition cCondition) {
        if(cCondition!=null && page !=null){
            CConditionExample cConditionExample = new CConditionExample();
            CConditionExample.Criteria criteria = cConditionExample.or();
            if(cCondition.getId()!=null)
                criteria.andIdEqualTo(cCondition.getId());
            if(cCondition.getConditionKey()!=null)
                criteria.andConditionKeyEqualTo(cCondition.getConditionKey());
            if(cCondition.getName()!=null && StringUtils.isNotBlank(String.valueOf(cCondition.getName())))
                criteria.andNameEqualTo(cCondition.getName());
            if(cCondition.getOperator()!=null)
                criteria.andOperatorEqualTo(cCondition.getOperator());
            if(cCondition.getType()!=null)
                criteria.andTypeEqualTo(cCondition.getType());
//            if(angelOrder.getStatusIn()!=null&& angelOrder.getStatusIn().size()>0)
//                criteria.andStatusIn(angelOrder.getStatusIn());
//            if(StringUtils.isNotBlank(angelOrder.getOrderNo()))
//                criteria.andOrderNoLike("%"+angelOrder.getOrderNo()+"%");
//            if (StringUtils.isNotBlank(angelOrder.getCreateTime1()))
//                criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.format(angelOrder.getCreateTime1()));
//            if(StringUtils.isNotBlank(angelOrder.getCreateTime2()))
//                criteria.andCreateTimeLessThanOrEqualTo(DateUtil.format(angelOrder.getCreateTime2()));
            int count = cConditionMapper.countByExample(cConditionExample);
            page.setCount(count);
            cConditionExample.setPage(page);
            cConditionExample.setOrderByClause("CREATE_TIME "+Page.ORDER_DIRECTION_DESC);
            HashMap hashMap = new HashMap();
            hashMap.put("list",cConditionMapper.selectByExample(cConditionExample));
            hashMap.put("page",page);
            return  hashMap;
        }else{
            return  null;
        }
    }
}
