package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.ReceiptPlan;
import com.ryx.credit.pojo.admin.order.ReceiptPlanExample;

import java.util.List;
import java.util.Map;

public interface ReceiptPlanMapper {
    long countByExample(ReceiptPlanExample example);

    int deleteByExample(ReceiptPlanExample example);

    int insert(ReceiptPlan record);

    int insertSelective(ReceiptPlan record);

    List<ReceiptPlan> selectByExample(ReceiptPlanExample example);

    ReceiptPlan selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReceiptPlan record);

    int updateByPrimaryKey(ReceiptPlan record);

    List<Map<String,Object>> getReceipPlanList(Map <String, Object> param);

    Long getReceipPlanCount(Map <String, Object> param);
}