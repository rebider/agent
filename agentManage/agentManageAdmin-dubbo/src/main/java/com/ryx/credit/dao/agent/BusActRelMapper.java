package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.BusActRelExample;
import com.ryx.credit.pojo.admin.agent.BusActRelKey;

import java.util.List;

public interface BusActRelMapper {
    int countByExample(BusActRelExample example);

    int deleteByExample(BusActRelExample example);

    int insert(BusActRel record);

    int insertSelective(BusActRel record);

    List<BusActRel> selectByExample(BusActRelExample example);

    BusActRel selectByPrimaryKey(BusActRelKey key);

    int updateByPrimaryKeySelective(BusActRel record);

    int updateByPrimaryKey(BusActRel record);

    BusActRel findById(String activId);
}