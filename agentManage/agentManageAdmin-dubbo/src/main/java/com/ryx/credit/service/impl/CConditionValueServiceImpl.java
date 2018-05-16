package com.ryx.credit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.dao.CConditionValueMapper;
import com.ryx.credit.pojo.admin.CConditionValue;
import com.ryx.credit.pojo.admin.CConditionValueExample;
import com.ryx.credit.service.ICConditionValueService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CConditionValueServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 17:17
 * To change this template use File | Settings | File Templates.
 */
@Service("cConditionValueService")
public class CConditionValueServiceImpl implements ICConditionValueService {
    @Autowired
    private CConditionValueMapper cConditionValueMapper;

    @Override
    public int insert(CConditionValue record) {
        return cConditionValueMapper.insert(record);
    }

    @Override
    public int insertSelective(CConditionValue record) {
        return cConditionValueMapper.insertSelective(record);
    }

    @Override
    public CConditionValue selectByPrimaryKey(BigDecimal id) {
        return cConditionValueMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CConditionValue record) {
        return cConditionValueMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CConditionValue record) {
        return cConditionValueMapper.updateByPrimaryKey(record);
    }

    @Override
    public Map configExample(Page page, CConditionValue cConditionValue) {
        if(cConditionValue!=null && page !=null){
            CConditionValueExample cConditionValueExample = new CConditionValueExample();
            CConditionValueExample.Criteria criteria = cConditionValueExample.or();
            if(cConditionValue.getId()!=null)
                criteria.andIdEqualTo(cConditionValue.getId());
            if(cConditionValue.getConditionId()!=null)
                criteria.andConditionIdEqualTo(cConditionValue.getConditionId());
            if(cConditionValue.getName()!=null && StringUtils.isNotBlank(String.valueOf(cConditionValue.getName())))
                criteria.andNameEqualTo(cConditionValue.getName());
            if(cConditionValue.getOperator()!=null)
                criteria.andOperatorEqualTo(cConditionValue.getOperator());
            if(cConditionValue.getValue()!=null)
                criteria.andValueEqualTo(cConditionValue.getValue());
            if(cConditionValue.getOperate()!=null)
                criteria.andOperateEqualTo(cConditionValue.getOperate());
//            if(angelOrder.getStatusIn()!=null&& angelOrder.getStatusIn().size()>0)
//                criteria.andStatusIn(angelOrder.getStatusIn());
//            if(StringUtils.isNotBlank(angelOrder.getOrderNo()))
//                criteria.andOrderNoLike("%"+angelOrder.getOrderNo()+"%");
//            if (StringUtils.isNotBlank(angelOrder.getCreateTime1()))
//                criteria.andCreateTimeGreaterThanOrEqualTo(DateUtil.format(angelOrder.getCreateTime1()));
//            if(StringUtils.isNotBlank(angelOrder.getCreateTime2()))
//                criteria.andCreateTimeLessThanOrEqualTo(DateUtil.format(angelOrder.getCreateTime2()));
            int count = cConditionValueMapper.countByExample(cConditionValueExample);
            page.setCount(count);
            cConditionValueExample.setPage(page);
            cConditionValueExample.setOrderByClause("CREATE_TIME "+Page.ORDER_DIRECTION_DESC);
            HashMap hashMap = new HashMap();
            hashMap.put("list",cConditionValueMapper.selectByExample(cConditionValueExample));
            hashMap.put("page",page);
            return  hashMap;
        }else{
            return  null;
        }
    }
}
