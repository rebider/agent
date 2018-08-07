package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.OrganTranMonthDetail;
import com.ryx.credit.profit.pojo.OrganTranMonthDetailExample;
import java.util.List;

public interface OrganTranMonthDetailMapper {
    long countByExample(OrganTranMonthDetailExample example);

    int deleteByExample(OrganTranMonthDetailExample example);

    int insert(OrganTranMonthDetail record);

    int insertSelective(OrganTranMonthDetail record);

    List<OrganTranMonthDetail> selectByExample(OrganTranMonthDetailExample example);

    OrganTranMonthDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(OrganTranMonthDetail record);

    int updateByPrimaryKey(OrganTranMonthDetail record);
}