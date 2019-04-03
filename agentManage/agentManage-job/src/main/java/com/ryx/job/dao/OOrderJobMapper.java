package com.ryx.job.dao;


import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OOrderExample;
import com.ryx.credit.pojo.admin.vo.OrderoutVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OOrderJobMapper {
    long countByExample(OOrderExample example);

    int deleteByExample(OOrderExample example);

    int insert(OOrder record);

    int insertSelective(OOrder record);

    List<OOrder> selectByExample(OOrderExample example);

    OOrder selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OOrder record);

    int updateByPrimaryKey(OOrder record);


}