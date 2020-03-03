package com.ryx.jobOrder.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.jobOrder.pojo.JoOrder;
import com.ryx.jobOrder.pojo.JoOrderExample;
import com.ryx.jobOrder.vo.JoTaskVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JoOrderMapper {
    long countByExample(JoOrderExample example);

    int deleteByExample(JoOrderExample example);

    int insert(JoOrder record);

    int insertSelective(JoOrder record);

    List<JoOrder> selectByExample(JoOrderExample example);

    JoOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(JoOrder record);

    int updateByPrimaryKey(JoOrder record);

    List<JoTaskVo> queryJobOrderList(Map<String, Object> param);

    int queryJobOrderListCount(Map<String, Object> param);

    List<JoTaskVo> queryAgentJobOrderList(Map<String, Object> param);

    int queryAgentJobOrderListCount(Map<String, Object> param);

    List<JoTaskVo> queryJobOrderLaunchList(@Param("map") Map<String, Object> param,@Param("page") Page page);

    int queryJobOrderLaunchListCount(@Param("map") Map<String, Object> param);

    List<JoOrder> queryListByTaskId(String taskId);

    List<Map> queryAgPro(String agId);
}