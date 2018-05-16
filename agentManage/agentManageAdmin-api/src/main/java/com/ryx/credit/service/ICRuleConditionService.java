package com.ryx.credit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.CRuleCondition;
import com.ryx.credit.pojo.admin.CRuleConditionExample;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ICRuleConditionService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 16:00
 * To change this template use File | Settings | File Templates.
 */

public interface ICRuleConditionService {

    int insert(CRuleCondition record);

    int insertSelective(CRuleCondition record);

    CRuleCondition selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CRuleCondition record);

    int updateByPrimaryKey(CRuleCondition record);

    Map configExample(Page page, CRuleCondition cRuleCondition);

    int deleteByExample(CRuleCondition cRuleCondition);

}
