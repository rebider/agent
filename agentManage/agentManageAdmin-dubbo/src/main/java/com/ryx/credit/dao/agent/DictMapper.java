package com.ryx.credit.dao.agent;


import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.DictExample;
import com.ryx.credit.pojo.admin.agent.DictKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface DictMapper {
    int countByExample(DictExample example);

    int deleteByExample(DictExample example);

    int insert(Dict record);

    int insertSelective(Dict record);

    List<Dict> selectByExample(DictExample example);

    Dict selectByPrimaryKey(DictKey key);

    int updateByPrimaryKey(Dict record);

    long sqlId(@Param("tableName")String tableName);


    int countDict(Map<String, Object> condition);

    List<Dict> selectDict(Map<String, Object> condition);

    Dict selectByPrimaryKey(String id);

    int insertDict(Dict dict);

    int updateByPrimaryKeySelective(Dict record);  // 删除（编辑）状态

}