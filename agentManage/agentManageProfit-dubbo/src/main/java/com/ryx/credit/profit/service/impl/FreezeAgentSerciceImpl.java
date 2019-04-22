package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.FreezeAgentMapper;
import com.ryx.credit.profit.pojo.FreezeAgent;
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
}
