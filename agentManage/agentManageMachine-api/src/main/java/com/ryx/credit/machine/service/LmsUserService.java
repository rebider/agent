package com.ryx.credit.machine.service;

import com.ryx.credit.machine.entity.LmsUser;

import java.util.List;
import java.util.Map;

/**
 * 内管账号用户接口
 */
public interface LmsUserService {

    /**
     * 查询有效的内管账号用户
     */
    List<Map<String, String>> queryAllLmsUser();

    /**
     * 通过登录名查询
     * @param loginName
     * @return
     */
    LmsUser queryByLogin(String loginName);
}
