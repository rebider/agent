package com.ryx.credit.profit.dao;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.profit.pojo.PCityApplicationDetail;
import com.ryx.credit.profit.pojo.PCityApplicationDetailExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PCityApplicationDetailMapper {
    long countByExample(PCityApplicationDetailExample example);

    int deleteByExample(PCityApplicationDetailExample example);

    int insert(PCityApplicationDetail record);

    int insertSelective(PCityApplicationDetail record);

    List<PCityApplicationDetail> selectByExample(PCityApplicationDetailExample example);

    PCityApplicationDetail selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PCityApplicationDetail record, @Param("example") PCityApplicationDetailExample example);

    int updateByExample(@Param("record") PCityApplicationDetail record, @Param("example") PCityApplicationDetailExample example);

    int updateByPrimaryKeySelective(PCityApplicationDetail record);

    int updateByPrimaryKey(PCityApplicationDetail record);

    /**获取该省区申请扣款数据*/
    List<Map<String,String>> getDeductionAppList(@Param("map") Map<String,String> map,@Param("page") Page page);
    long getDeductionAppListCount(Map<String,String> map);

    /**根据代理商AG，获得代理商姓名*/
    String getAgentNameByAgentId(String id);

    /**根据AG获取上级代理商信息*/
    List<String> getParentIdByAgentId(String agentId);

}