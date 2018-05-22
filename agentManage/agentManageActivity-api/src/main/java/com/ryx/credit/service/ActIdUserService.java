package com.ryx.credit.service;

import com.ryx.credit.activity.entity.ActIdUser;
import com.ryx.credit.common.util.Page;

import java.util.HashMap;
import java.util.List;

/**
 * ActIdUserService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/5/22
 * @Time: 15:03
 * To change this template use File | Settings | File Templates.
 */

public interface ActIdUserService {

    int insert(ActIdUser record);

    int insertSelective(ActIdUser record);

    ActIdUser selectByPrimaryKey(Object id);

    int updateByPrimaryKeySelective(ActIdUser record);

    int updateByPrimaryKey(ActIdUser record);

    HashMap<String, Object> configExample(Page page, ActIdUser actIdUser);
}
