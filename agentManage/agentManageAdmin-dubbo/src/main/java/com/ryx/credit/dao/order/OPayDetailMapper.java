package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.OPayDetail;
import com.ryx.credit.pojo.admin.order.OPayDetailExample;

import java.util.List;

public interface OPayDetailMapper {
    long countByExample(OPayDetailExample example);

    int deleteByExample(OPayDetailExample example);

    int insert(OPayDetail record);

    int insertSelective(OPayDetail record);

    List<OPayDetail> selectByExample(OPayDetailExample example);

    OPayDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OPayDetail record);

    int updateByPrimaryKey(OPayDetail record);
}