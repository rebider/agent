package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PRemitInfo;
import com.ryx.credit.profit.pojo.PRemitInfoExample;

import java.util.List;

public interface PRemitInfoMapper {
    long countByExample(PRemitInfoExample example);

    int deleteByExample(PRemitInfoExample example);

    int insert(PRemitInfo record);

    int insertSelective(PRemitInfo record);

    List<PRemitInfo> selectByExample(PRemitInfoExample example);

    PRemitInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PRemitInfo record);

    int updateByPrimaryKey(PRemitInfo record);
}