package com.ryx.credit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.CRulePlatform;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ICRulePlatformService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 17:00
 * To change this template use File | Settings | File Templates.
 */

public interface ICRulePlatformService {

    int insert(CRulePlatform record);

    int insertSelective(CRulePlatform record);

    CRulePlatform selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CRulePlatform record);

    int updateByPrimaryKey(CRulePlatform record);

    Map configExample(Page page, CRulePlatform cRulePlatform);

    int deleteByExample(CRulePlatform cRulePlatform);
}
