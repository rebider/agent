package com.ryx.credit.dao.order;


import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OOrderExample;
import com.ryx.credit.pojo.admin.vo.OrderoutVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OOrderMapper {
    int countByExample(OOrderExample example);

    int deleteByExample(OOrderExample example);

    int insert(OOrder record);

    int insertSelective(OOrder record);

    List<OOrder> selectByExample(OOrderExample example);

    OOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OOrder record);

    int updateByPrimaryKey(OOrder record);

    List<Map<String,Object>> getOrderList(Map <String, Object> param);

    Long getOrderCount(Map <String, Object> param);

    List<Map<String,Object>> queryOrderListView(Map <String, Object> param);
    List<Map<String,Object>> queryAllOrderListView(Map <String, Object> param);

    int queryOrderListViewCount(Map <String, Object> param);
    int queryAllOrderListViewCount(Map <String, Object> param);

    List<Map<String,Object>> queryOrderSubOrderProduct(@Param("orderId") String orderId,@Param("agentId") String agentId);

    List<Map<String,Object>> queryHavePeiHuoProduct(@Param("orderId") String orderId,@Param("agentId") String agentId);

    List<OrderoutVo> excelOrder(Map map);
}