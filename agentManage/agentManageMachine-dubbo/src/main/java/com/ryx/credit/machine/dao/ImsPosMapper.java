package com.ryx.credit.machine.dao;

import com.ryx.credit.machine.entity.ImsPos;
import com.ryx.credit.machine.entity.ImsPosExample;
import java.util.List;

public interface ImsPosMapper {
    long countByExample(ImsPosExample example);

    int deleteByExample(ImsPosExample example);

    int insert(ImsPos record);

    int insertSelective(ImsPos record);

    List<ImsPos> selectByExample(ImsPosExample example);

    ImsPos selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ImsPos record);

    int updateByPrimaryKey(ImsPos record);
}