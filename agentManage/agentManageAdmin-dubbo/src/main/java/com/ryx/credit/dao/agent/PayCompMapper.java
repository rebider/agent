package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.pojo.admin.agent.PayCompExample;

import java.util.List;

public interface PayCompMapper {
    int countByExample(PayCompExample example);

    int deleteByExample(PayCompExample example);

    int insert(PayComp record);

    int insertSelective(PayComp record);

    List<PayComp> selectByExample(PayCompExample example);

    PayComp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PayComp record);

    int updateByPrimaryKey(PayComp record);
}