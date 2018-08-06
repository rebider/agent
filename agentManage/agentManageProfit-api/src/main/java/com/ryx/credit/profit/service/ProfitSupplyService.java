package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.PProfitFactor;
import com.ryx.credit.profit.pojo.ProfitSupply;
import com.ryx.credit.profit.pojo.ProfitSupplyExample;

import java.util.List;
import java.util.Map;

/**
 * @Author Wangy
 * @Date 2018/08/02
 * 分润管理：补款管理
 */
public interface ProfitSupplyService {

    PageInfo getProfitSupplyList(Map<String, Object> param, PageInfo pageInfo);

    int countByExample(ProfitSupplyExample example);

    int deleteByExample(ProfitSupplyExample example);

    int insert(ProfitSupply record);

    int insertSelective(ProfitSupply record);

    List<ProfitSupply> selectByExample(ProfitSupplyExample example);

    ProfitSupply selectByPrimaryKey(String id);

    ProfitSupply selectByAgentMonth(ProfitSupply record);

    int updateByPrimaryKeySelective(ProfitSupply record);

    int updateByPrimaryKey(ProfitSupply record);

    List<String> importSupplyList(List<List<Object>> data) throws Exception;
}
