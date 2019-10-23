package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PmsProfitLogMapper {
    long countByExample(PmsProfitLogExample example);

    int deleteByExample(PmsProfitLogExample example);

    int insert(PmsProfitLog record);

    int insertSelective(PmsProfitLog record);

    List<PmsProfitLog> selectByExample(PmsProfitLogExample example);

    PmsProfitLog selectByPrimaryKey(String batchNo);

    int updateByPrimaryKeySelective(PmsProfitLog record);

    int updateByPrimaryKey(PmsProfitLog record);

    List<Map<String,Object>> checkoutData(@Param("agentId")  String agentId ,@Param("busCode") String busCode);

    Map<String,Object> getLoginName(@Param("userId")  String userId );
    int  save(PmsProfit record);

}