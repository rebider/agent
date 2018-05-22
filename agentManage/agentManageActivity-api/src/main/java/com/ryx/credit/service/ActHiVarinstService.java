package com.ryx.credit.service;

import com.ryx.credit.activity.entity.ActHiVarinst;
import com.ryx.credit.common.util.Page;

import java.util.HashMap;
import java.util.List;

/**
 * ActHiVarinstService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/5/22
 * @Time: 14:44
 * @description: ActHiVarinstService
 * To change this template use File | Settings | File Templates.
 */

public interface ActHiVarinstService {
    int insert(ActHiVarinst record);

    int insertSelective(ActHiVarinst record);

    ActHiVarinst selectByPrimaryKey(Object id);

    int updateByPrimaryKeySelective(ActHiVarinst record);

    int updateByPrimaryKey(ActHiVarinst record);

    HashMap<String, Object> configExample(Page page, ActHiVarinst actHiVarinst);
}
