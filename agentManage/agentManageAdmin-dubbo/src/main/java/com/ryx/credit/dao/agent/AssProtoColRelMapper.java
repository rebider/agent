package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.AssProtoColRel;
import com.ryx.credit.pojo.admin.agent.AssProtoColRelExample;
import com.ryx.credit.pojo.admin.agent.AssProtoColRelKey;

import java.util.List;

public interface AssProtoColRelMapper {
    int countByExample(AssProtoColRelExample example);

    int deleteByExample(AssProtoColRelExample example);

    int insert(AssProtoColRel record);

    int insertSelective(AssProtoColRel record);

    List<AssProtoColRel> selectByExample(AssProtoColRelExample example);

    AssProtoColRel selectByPrimaryKey(AssProtoColRelKey key);

    int updateByPrimaryKeySelective(AssProtoColRel record);

    int updateByPrimaryKey(AssProtoColRel record);
}