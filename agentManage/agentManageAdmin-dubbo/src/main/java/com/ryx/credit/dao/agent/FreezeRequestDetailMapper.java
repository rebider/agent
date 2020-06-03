package com.ryx.credit.dao.agent;

import com.ryx.credit.pojo.admin.agent.FreezeRequestDetail;
import com.ryx.credit.pojo.admin.agent.FreezeRequestDetailExample;
import java.util.List;

public interface FreezeRequestDetailMapper {
    long countByExample(FreezeRequestDetailExample example);

    int deleteByExample(FreezeRequestDetailExample example);

    int insert(FreezeRequestDetail record);

    int insertSelective(FreezeRequestDetail record);

    List<FreezeRequestDetail> selectByExample(FreezeRequestDetailExample example);

    FreezeRequestDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(FreezeRequestDetail record);

    int updateByPrimaryKey(FreezeRequestDetail record);
}