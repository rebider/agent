package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OReturnOrderRel;
import com.ryx.credit.pojo.admin.order.OReturnOrderRelExample;
import java.util.List;

public interface OReturnOrderRelMapper {
    long countByExample(OReturnOrderRelExample example);

    int deleteByExample(OReturnOrderRelExample example);

    int insert(OReturnOrderRel record);

    int insertSelective(OReturnOrderRel record);

    List<OReturnOrderRel> selectByExample(OReturnOrderRelExample example);
}