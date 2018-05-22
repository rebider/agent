package com.ryx.credit.activity.service.impl;

import com.ryx.credit.activity.dao.ActHiVarinstMapper;
import com.ryx.credit.activity.entity.ActHiVarinst;
import com.ryx.credit.activity.entity.ActHiVarinstExample;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.service.ActHiVarinstService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * ActHiVarinstServiceimpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/5/22
 * @Time: 14:51
 * @description: ActHiVarinstServiceimpl
 * To change this template use File | Settings | File Templates.
 */

public class ActHiVarinstServiceimpl implements ActHiVarinstService {
    @Autowired
    private ActHiVarinstMapper actHiVarinstMapper;

    @Override
    public int insert(ActHiVarinst record) {
        return actHiVarinstMapper.insert(record);
    }

    @Override
    public int insertSelective(ActHiVarinst record) {
        return actHiVarinstMapper.insertSelective(record);
    }

    @Override
    public ActHiVarinst selectByPrimaryKey(Object id) {
        return actHiVarinstMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ActHiVarinst record) {
        return actHiVarinstMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ActHiVarinst record) {
        return actHiVarinstMapper.updateByPrimaryKey(record);
    }

    @Override
    public HashMap<String, Object> configExample(Page page, ActHiVarinst actHiVarinst) {
        if (actHiVarinst != null && page != null) {
            ActHiVarinstExample actHiVarinstExample = new ActHiVarinstExample();
            ActHiVarinstExample.Criteria criteria = actHiVarinstExample.or();
            if (actHiVarinst.getId() != null) {
                criteria.andIdEqualTo(actHiVarinst.getId());
            }

            if (actHiVarinst.getProcInstId() != null) {
                criteria.andProcInstIdEqualTo(actHiVarinst.getProcInstId());
            }
            if (actHiVarinst.getName() != null && StringUtils.isNotBlank(String.valueOf(actHiVarinst.getName()))) {
                criteria.andNameEqualTo(actHiVarinst.getName());
            }

            if (actHiVarinst.getExecutionId() != null) {
                criteria.andExecutionIdEqualTo(actHiVarinst.getExecutionId());
            }

            int count = actHiVarinstMapper.countByExample(actHiVarinstExample);
            page.setCount(count);
            actHiVarinstExample.setPage(page);
            actHiVarinstExample.setOrderByClause("CREATE_TIME_ " + Page.ORDER_DIRECTION_DESC);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("list", actHiVarinstMapper.selectByExample(actHiVarinstExample));
            hashMap.put("page", page);
            return hashMap;
        } else {
            return null;
        }
    }
}
