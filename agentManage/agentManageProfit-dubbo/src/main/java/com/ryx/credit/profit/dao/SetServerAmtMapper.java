package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.SetServerAmt;
import com.ryx.credit.profit.pojo.SetServerAmtExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SetServerAmtMapper {
    long countByExample(SetServerAmtExample example);

    int deleteByExample(SetServerAmtExample example);

    int insert(SetServerAmt record);

    int insertSelective(SetServerAmt record);

    List<SetServerAmt> selectByExample(SetServerAmtExample example);

    SetServerAmt selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SetServerAmt record);

    int updateByPrimaryKey(SetServerAmt record);

    /**
     * chenliang
     * 查询平台号
     */
   List<Map<String,Object>> queryBumCode();

    /**
     * chenliang
     * 查询已经设置的服务费
     */
     List<SetServerAmt> setServerAmtList(Map<String,Object> param);
     Integer setServerAmtCount(Map<String,Object> param);

    List<Map<String,Object>> queryD(@Param("bumId")String bumId);

    /**
     * 按月份清楚服务费明细
     * @param profitDate
     * @return
     */
    int clearServerAmtDetailData(@Param("profitDate")String profitDate);
}