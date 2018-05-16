package com.ryx.credit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.dao.CRulePlatformMapper;
import com.ryx.credit.pojo.admin.CRulePlatform;
import com.ryx.credit.pojo.admin.CRulePlatformExample;
import com.ryx.credit.service.ICRulePlatformService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CRulePlatformServiceImpl
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 17:40
 * To change this template use File | Settings | File Templates.
 */
@Service("cRulePlatformService")
public class CRulePlatformServiceImpl implements ICRulePlatformService {
    @Autowired
    private CRulePlatformMapper cRulePlatformMapper;

    @Override
    public int insert(CRulePlatform record) {
        return cRulePlatformMapper.insert(record);
    }

    @Override
    public int insertSelective(CRulePlatform record) {
        return cRulePlatformMapper.insertSelective(record);
    }

    @Override
    public CRulePlatform selectByPrimaryKey(BigDecimal id) {
        return cRulePlatformMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(CRulePlatform record) {
        return cRulePlatformMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(CRulePlatform record) {
        return cRulePlatformMapper.updateByPrimaryKey(record);
    }

    @Override
    public Map configExample(Page page, CRulePlatform cRulePlatform) {
        if(cRulePlatform!=null && page !=null){
            CRulePlatformExample example = new CRulePlatformExample();
            CRulePlatformExample.Criteria criteria = example.or();
            if(cRulePlatform.getId()!=null)
                criteria.andIdEqualTo(cRulePlatform.getId());
            if(cRulePlatform.getName()!=null && StringUtils.isNotBlank(String.valueOf(cRulePlatform.getName())))
                criteria.andNameEqualTo(cRulePlatform.getName());
            if(cRulePlatform.getDepartment()!=null)
                criteria.andDepartmentEqualTo(cRulePlatform.getDepartment());
            if(cRulePlatform.getMgroup()!=null)
                criteria.andMgroupEqualTo(cRulePlatform.getMgroup());
            if(cRulePlatform.getDescription()!=null)
                criteria.andDescriptionEqualTo(cRulePlatform.getDescription());
            if(cRulePlatform.getCode()!=null)
                criteria.andCodeEqualTo(cRulePlatform.getCode());
            int count = cRulePlatformMapper.countByExample(example);
            page.setCount(count);
            example.setPage(page);
            example.setOrderByClause("CREATE_TIME "+Page.ORDER_DIRECTION_DESC);
            HashMap hashMap = new HashMap();
            hashMap.put("list",cRulePlatformMapper.selectByExample(example));
            hashMap.put("page",page);
            return  hashMap;
        }else{
            return  null;
        }
    }

    @Override
    public int deleteByExample(CRulePlatform cRulePlatform) {
        if(cRulePlatform!=null ){
            CRulePlatformExample example = new CRulePlatformExample();
            CRulePlatformExample.Criteria criteria = example.or();
            if(cRulePlatform.getId()!=null)
                criteria.andIdEqualTo(cRulePlatform.getId());
            if(cRulePlatform.getName()!=null)
                criteria.andNameEqualTo(cRulePlatform.getName());
            if(cRulePlatform.getDepartment()!=null)
                criteria.andDepartmentEqualTo(cRulePlatform.getDepartment());
            if(cRulePlatform.getMgroup()!=null)
                criteria.andMgroupEqualTo(cRulePlatform.getMgroup());
            if(cRulePlatform.getDescription()!=null)
                criteria.andDescriptionEqualTo(cRulePlatform.getDescription());
            if(cRulePlatform.getCode()!=null)
                criteria.andCodeEqualTo(cRulePlatform.getCode());
            return  cRulePlatformMapper.deleteByExample(example);
        }else{
            return  -1;
        }
    }
}
