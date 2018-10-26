package com.ryx.credit.dao.order;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiff;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ORefundPriceDiffMapper {
    long countByExample(ORefundPriceDiffExample example);

    int deleteByExample(ORefundPriceDiffExample example);

    int insert(ORefundPriceDiff record);

    int insertSelective(ORefundPriceDiff record);

    List<ORefundPriceDiff> selectByExample(ORefundPriceDiffExample example);

    ORefundPriceDiff selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ORefundPriceDiff record);

    int updateByPrimaryKey(ORefundPriceDiff record);

    List<Map<String,Object>> selectBySnAndOrderId(@Param("map") Map<String, Object> map);

    List<Map<String,Object>> selectByAgent(@Param("map") Map<String, Object> map,@Param("page") Page page);

    int selectByAgentCount(@Param("map") Map<String, Object> map);
}