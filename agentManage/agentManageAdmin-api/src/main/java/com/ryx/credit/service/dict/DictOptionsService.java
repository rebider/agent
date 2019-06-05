package com.ryx.credit.service.dict;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Dict;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by cx on 2018/5/22.
 */
public interface DictOptionsService {

    List<Dict> dictList(String group,String artifact);

    Dict findDictByValue(String group, String artifact,String itemValue);


    Object dictList(PageInfo pageInfo);

    Dict selectByPrimaryKey(String id);

    boolean insertDict(Dict dict, @Param("tableName")String tableName);

    int updateByPrimaryKeySelective(Dict record);  // 删除（编辑）状态

    public Dict findDictByName(String group, String artifact,String itemName);

    String getApproveVersion(String approveName)throws ProcessException;
}
