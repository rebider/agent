package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.FreezeAgentMapper;
import com.ryx.credit.profit.pojo.FreezeAgent;
import com.ryx.credit.profit.pojo.FreezeAgentExample;
import com.ryx.credit.profit.service.IFreezeAgentSercice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * chenliang
 * 2019/4/22
 */
@Service("freezeAgentSercice")
public class FreezeAgentSerciceImpl implements IFreezeAgentSercice {
    Logger logger = LoggerFactory.getLogger(FreezeAgentSerciceImpl.class);
    @Autowired
    FreezeAgentMapper freezeAgentMapper;

    @Override
    public PageInfo getselectFreezeDate(Map<String, Object> param, PageInfo pageInfo){
       Integer count = 0;
        List<FreezeAgent> listAll;
        if ("true".equals(param.get("isQuerySubordinate"))){
            listAll = freezeAgentMapper.selectAllNotFreezeLower(param);
            count = freezeAgentMapper.selectAllNotFreezeLowerCount(param);
        }else{
            listAll = freezeAgentMapper.selectAllNotFreeze(param);
            count = freezeAgentMapper.selectAllNotFreezeCount(param);
        }

        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(listAll);
        logger.info("查询============================================" + JSONObject.toJSON(listAll));
        return pageInfo;

  }

    @Override
    public PageInfo getFreezeData(FreezeAgent freezeAgent, String isQuerySubordinate, Page page, String orgId) {
        List<FreezeAgent> listAll;
        long count;
        if ("false".equals(isQuerySubordinate)){
            FreezeAgentExample example=new FreezeAgentExample();
            FreezeAgentExample.Criteria criteria=example.createCriteria();
            criteria.andStatusEqualTo("1");
            if(StringUtils.isNotBlank(freezeAgent.getAgentId())){
                criteria.andAgentIdEqualTo(freezeAgent.getAgentId());
            }
            if(StringUtils.isNotBlank(freezeAgent.getAgentName())){
                criteria.andAgentNameEqualTo(freezeAgent.getAgentName());
            }
            if(StringUtils.isNotBlank(freezeAgent.getParentAgentId())){
                criteria.andParentAgentIdEqualTo(freezeAgent.getParentAgentId());
            }
            if(StringUtils.isNotBlank(freezeAgent.getParentAgentName())){
                criteria.andParentAgentNameEqualTo(freezeAgent.getParentAgentName());
            }
            example.setPage(page);

            listAll=freezeAgentMapper.selectByExampleWithCity(freezeAgent,page,orgId);
            count = freezeAgentMapper.countByExampleWithCity(freezeAgent,orgId);

        }else{
            listAll=freezeAgentMapper.selectAllFreezeWithSubordinate(freezeAgent,page,orgId);
            count=freezeAgentMapper.countAllFreezeWithSubordinate(freezeAgent,orgId);
        }
        PageInfo pageInfo=new PageInfo();
        pageInfo.setRows(listAll);
        pageInfo.setTotal((int) count);
        return pageInfo;
    }
}
