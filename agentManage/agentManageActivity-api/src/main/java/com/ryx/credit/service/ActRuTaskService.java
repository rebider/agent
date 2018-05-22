package com.ryx.credit.service;

import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.common.util.Page;

import java.util.HashMap;

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
}
