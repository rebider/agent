package com.ryx.credit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.CuserAgentKey;

import java.util.List;
import java.util.Map;

/**
 * ICuserAgentService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2018/8/2
 * @Time: 19:49
 * To change this template use File | Settings | File Templates.
 */

public interface ICuserAgentService {
    long countByExample(CuserAgentExample example);

    int deleteByExample(CuserAgentExample example);

    int insert(CuserAgent record);

    int insertSelective(CuserAgent record);

    List<CuserAgent> selectByExample(CuserAgentExample example);

    CuserAgent selectByPrimaryKey(CuserAgentKey key);

    int updateByPrimaryKeySelective(CuserAgent record);

    int updateByPrimaryKey(CuserAgent record);

    Map configExample(Page page, CuserAgent cuserAgent);
}
