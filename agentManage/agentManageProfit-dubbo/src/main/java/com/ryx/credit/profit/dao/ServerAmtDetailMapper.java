package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ServerAmtDetail;
import com.ryx.credit.profit.pojo.ServerAmtDetailExample;

import java.util.List;

public interface ServerAmtDetailMapper {
    long countByExample(ServerAmtDetailExample example);

    int deleteByExample(ServerAmtDetailExample example);

    int insert(ServerAmtDetail record);

    int insertSelective(ServerAmtDetail record);

    List<ServerAmtDetail> selectByExample(ServerAmtDetailExample example);

    ServerAmtDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ServerAmtDetail record);

    int updateByPrimaryKey(ServerAmtDetail record);

}