package com.ryx.credit.service.agent;

import com.ryx.credit.pojo.admin.agent.Capital;

import java.util.List;

/**
 * @Author Lihl
 * @Date 2018/10/11
 * 资金记录：查询保证金信息
 */
public interface CapitalService {

    List<Capital> queryCapital(String agentId);

}
