package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AColinfoPayment;
import com.ryx.credit.pojo.admin.agent.AColinfoPaymentExample;

import java.util.List;

public interface AColinfoPaymentMapper {
    long countByExample(AColinfoPaymentExample example);

    int deleteByExample(AColinfoPaymentExample example);

    int insert(AColinfoPayment record);

    int insertSelective(AColinfoPayment record);

    List<AColinfoPayment> selectByExample(AColinfoPaymentExample example);

    AColinfoPayment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(AColinfoPayment record);

    int updateByPrimaryKey(AColinfoPayment record);
}