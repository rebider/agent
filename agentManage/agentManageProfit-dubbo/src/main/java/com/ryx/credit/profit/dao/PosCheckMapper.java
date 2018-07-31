package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PosCheck;
import com.ryx.credit.profit.pojo.PosCheckExample;

import java.util.List;

public interface PosCheckMapper {
    long countByExample(PosCheckExample example);

    int deleteByExample(PosCheckExample example);

    int insert(PosCheck record);

    int insertSelective(PosCheck record);

    List<PosCheck> selectByExample(PosCheckExample example);

    PosCheck selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PosCheck record);

    int updateByPrimaryKey(PosCheck record);
}