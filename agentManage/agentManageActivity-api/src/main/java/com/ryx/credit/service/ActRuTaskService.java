package com.ryx.credit.service;

import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ActRuTaskService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/5/22
 * @Time: 15:17
 * To change this template use File | Settings | File Templates.
 */

public interface ActRuTaskService {

    int insert(ActRuTask record);

    int insertSelective(ActRuTask record);

    ActRuTask selectByPrimaryKey(Object id);

    int updateByPrimaryKeySelective(ActRuTask record);

    int updateByPrimaryKey(ActRuTask record);

    HashMap<String, Object> configExample(Page page, ActRuTask actRuTask);

    List<Map<String, Object>> queryMyTask(Map<String,Object> param);

    PageInfo queryMyTaskPage(Page page, Map<String,Object> param);

    List<Map<String,Object>> queryHuddleMyTask(Map<String,Object> params);
}
