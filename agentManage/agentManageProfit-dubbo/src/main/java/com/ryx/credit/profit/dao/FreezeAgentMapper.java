package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.FreezeAgent;
import com.ryx.credit.profit.pojo.FreezeAgentExample;

import java.util.List;
import java.util.Map;

public interface FreezeAgentMapper {
    long countByExample(FreezeAgentExample example);

    int deleteByExample(FreezeAgentExample example);

    int insert(FreezeAgent record);

    int insertSelective(FreezeAgent record);

    List<FreezeAgent> selectByExample(FreezeAgentExample example);

    FreezeAgent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FreezeAgent record);

    int updateByPrimaryKey(FreezeAgent record);
        //正常查询不冻洁
    List<FreezeAgent> selectAllNotFreeze(Map<String,Object> perms);
    Integer selectAllNotFreezeCount(Map<String,Object> perms);
    //不冻结查询下级
    List<FreezeAgent> selectAllNotFreezeLower(Map<String,Object> perms);
    Integer selectAllNotFreezeLowerCount (Map<String,Object> perms);
}