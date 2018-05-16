package com.ryx.credit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.pojo.admin.CStrategy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ICStrategyService
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/5/31
 * @Time: 16:43
 * To change this template use File | Settings | File Templates.
 */

public interface ICStrategyService {

    int insert(CStrategy record);

    int insertSelective(CStrategy record);

    CStrategy selectByPrimaryKey(BigDecimal id);

    int updateByPrimaryKeySelective(CStrategy record);

    int updateByPrimaryKey(CStrategy record);

    Map configExample(Page page, CStrategy cStrategy);
}
