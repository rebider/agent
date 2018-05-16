package com.ryx.credit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.CCondition;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ICConditionService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 15:24
 * To change this template use File | Settings | File Templates.
 */

public interface ICConditionService {
    int insert(CCondition record);

    int insertSelective(CCondition record);

    CCondition selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CCondition record);

    int updateByPrimaryKey(CCondition record);

    Map configExample(Page page, CCondition cCondition);
}
