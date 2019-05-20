package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.PosCheck;
import com.ryx.credit.profit.pojo.PosCheckExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PosCheckMapper {
    long countByExample(PosCheckExample example);

    int deleteByExample(PosCheckExample example);

    int insert(PosCheck record);

    int insertSelective(PosCheck record);

    List<PosCheck> selectByExample(PosCheckExample example);

    PosCheck selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PosCheck record);

    int updateByPrimaryKey(PosCheck record);

    List<PosCheck> exportPosCheck(PosCheck posCheck);

    List<PosCheck> selectByAgentId(@Param("agentId") String agentId,@Param("busNum")String busNum);

    List<Map<String,Object>> queryByAgentInfo(Map<String,String> map);

    List<Map<String,Object>> selectById(String id);
}