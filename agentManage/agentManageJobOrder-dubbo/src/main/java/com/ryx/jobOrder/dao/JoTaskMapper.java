package com.ryx.jobOrder.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.jobOrder.pojo.JoTask;
import com.ryx.jobOrder.pojo.JoTaskExample;
import com.ryx.jobOrder.vo.JoTaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JoTaskMapper {
    long countByExample(JoTaskExample example);

    int deleteByExample(JoTaskExample example);

    int insert(JoTask record);

    int insertSelective(JoTask record);

    List<JoTask> selectByExample(JoTaskExample example);

    JoTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JoTask record);

    int updateByPrimaryKey(JoTask record);

    List<JoTaskVo> selectByJoTaskVo(JoTaskVo example, @Param("page") Page page);
}