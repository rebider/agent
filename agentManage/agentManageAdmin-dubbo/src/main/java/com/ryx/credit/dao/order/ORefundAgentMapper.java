package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.ORefundAgent;
import com.ryx.credit.pojo.admin.order.ORefundAgentExample;
import java.util.List;

public interface ORefundAgentMapper {
    long countByExample(ORefundAgentExample example);

    int deleteByExample(ORefundAgentExample example);

    int insert(ORefundAgent record);

    int insertSelective(ORefundAgent record);

    List<ORefundAgent> selectByExample(ORefundAgentExample example);

    ORefundAgent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ORefundAgent record);

    int updateByPrimaryKey(ORefundAgent record);
}