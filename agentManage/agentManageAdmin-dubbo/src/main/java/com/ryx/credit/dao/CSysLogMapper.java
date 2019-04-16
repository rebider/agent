package com.ryx.credit.dao;


import com.ryx.credit.pojo.admin.CSysLog;
import com.ryx.credit.pojo.admin.CSysLogExample;

import java.math.BigDecimal;
import java.util.List;

public interface CSysLogMapper {
    long countByExample(CSysLogExample example);

    int deleteByExample(CSysLogExample example);

    int insert(CSysLog record);

    int insertSelective(CSysLog record);

    List<CSysLog> selectByExample(CSysLogExample example);

    CSysLog selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CSysLog record);

    int updateByPrimaryKey(CSysLog record);
}