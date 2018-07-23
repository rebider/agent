package com.ryx.credit.dao.order;

import com.ryx.credit.pojo.admin.order.ODeductCapital;
import com.ryx.credit.pojo.admin.order.ODeductCapitalExample;

import java.util.List;

public interface ODeductCapitalMapper {
    long countByExample(ODeductCapitalExample example);

    int deleteByExample(ODeductCapitalExample example);

    int insert(ODeductCapital record);

    int insertSelective(ODeductCapital record);

    List<ODeductCapital> selectByExample(ODeductCapitalExample example);

    ODeductCapital selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ODeductCapital record);

    int updateByPrimaryKey(ODeductCapital record);
}