package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.ProfitOrganTranMonth;
import com.ryx.credit.profit.pojo.ProfitOrganTranMonthExample;
import java.util.List;

public interface ProfitOrganTranMonthMapper {
    int countByExample(ProfitOrganTranMonthExample example);

    int deleteByExample(ProfitOrganTranMonthExample example);

    int insert(ProfitOrganTranMonth record);

    int insertSelective(ProfitOrganTranMonth record);

    List<ProfitOrganTranMonth> selectByExample(ProfitOrganTranMonthExample example);

    ProfitOrganTranMonth selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ProfitOrganTranMonth record);

    int updateByPrimaryKey(ProfitOrganTranMonth record);
}