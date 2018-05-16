package com.ryx.credit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.CStrategyRule;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ICStrategyRuleService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 16:45
 * To change this template use File | Settings | File Templates.
 */

public interface ICStrategyRuleService {
    int insert(CStrategyRule record);

    int insertSelective(CStrategyRule record);

    CStrategyRule selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CStrategyRule record);

    int updateByPrimaryKey(CStrategyRule record);

    Map configExample(Page page, CStrategyRule cStrategyRule);

    int deleteByExample(CStrategyRule cStrategyRule);
}
