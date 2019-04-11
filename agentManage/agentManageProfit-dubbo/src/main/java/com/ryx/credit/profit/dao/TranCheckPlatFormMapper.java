package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.TranCheckPlatForm;
import com.ryx.credit.profit.pojo.TranCheckPlatFormExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TranCheckPlatFormMapper {
    long countByExample(TranCheckPlatFormExample example);

    int deleteByExample(TranCheckPlatFormExample example);

    int insert(TranCheckPlatForm record);

    int insertSelective(TranCheckPlatForm record);

    List<TranCheckPlatForm> selectByExample(TranCheckPlatFormExample example);

    TranCheckPlatForm selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(TranCheckPlatForm record);

    int updateByPrimaryKey(TranCheckPlatForm record);

    List<String> getAllPlatFormType();

    Map<String, Object> getTranAmtByMonth(String tranMonth);
}