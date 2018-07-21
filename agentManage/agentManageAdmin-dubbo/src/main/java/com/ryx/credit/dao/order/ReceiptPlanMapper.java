package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.ReceiptPlan;
import com.ryx.credit.pojo.admin.order.ReceiptPlanExample;

import java.util.List;

public interface ReceiptPlanMapper {
    long countByExample(ReceiptPlanExample example);

    int deleteByExample(ReceiptPlanExample example);

    int insert(ReceiptPlan record);

    int insertSelective(ReceiptPlan record);

    List<ReceiptPlan> selectByExample(ReceiptPlanExample example);

    ReceiptPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReceiptPlan record);

    int updateByPrimaryKey(ReceiptPlan record);
}