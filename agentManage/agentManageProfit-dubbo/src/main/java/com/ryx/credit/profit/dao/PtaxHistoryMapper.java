package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PtaxHistory;
import com.ryx.credit.profit.pojo.PtaxHistoryExample;

import java.math.BigDecimal;
import java.util.List;

public interface PtaxHistoryMapper {
    long countByExample(PtaxHistoryExample example);

    int deleteByExample(PtaxHistoryExample example);

    int insert(PtaxHistory record);

    int insertSelective(PtaxHistory record);

    List<PtaxHistory> selectByExample(PtaxHistoryExample example);

    BigDecimal getHistoryAmt(PtaxHistory record);
}