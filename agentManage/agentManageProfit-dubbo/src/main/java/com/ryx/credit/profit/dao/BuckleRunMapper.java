package com.ryx.credit.profit.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ryx.credit.profit.pojo.BuckleRun;
import com.ryx.credit.profit.pojo.BuckleRunExample;
import org.apache.ibatis.annotations.Param;

public interface BuckleRunMapper {
    long countByExample(BuckleRunExample example);

    int deleteByExample(BuckleRunExample example);

    int insert(BuckleRun record);

    int insertSelective(BuckleRun record);

    List<BuckleRun> selectByExample(BuckleRunExample example);

    BuckleRun selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") BuckleRun record, @Param("example") BuckleRunExample example);

    int updateByExample(@Param("record") BuckleRun record, @Param("example") BuckleRunExample example);

    int updateByPrimaryKeySelective(BuckleRun record);

    int updateByPrimaryKey(BuckleRun record);

    BigDecimal getSumRunAmt(String agentId);

    List<BuckleRun> selectListByAgentId(String agentId);
    /**
     * 根据代理商AG码和代扣款日期查询
     * @param param
     * @return
     */
    List<Map<String,Object>> selectListByAgentIdAndRunDate(Map<String,Object> param);
    /**
     * 根据代扣代理商AG码和代扣款日期查询
     * @param param
     * @return
     */
    List<BuckleRun> selectListByBearAgentIdAndRunDate(Map<String,Object> param);
    /**
     * 根据代理商AG码和代扣日期查询退单补款明细
     * @param param
     * @return
     */
    List<BuckleRun> selectListByBearAgentIdAndRunDateWithSupply(Map<String, Object> param);

    List<Map<String, Object>> selectListByAgentIdAndRunDateWithSupply(Map<String, Object> param);
}