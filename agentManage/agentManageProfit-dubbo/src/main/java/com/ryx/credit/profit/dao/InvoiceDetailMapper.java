package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.InvoiceDetail;
import com.ryx.credit.profit.pojo.InvoiceDetailExample;

import java.util.List;

public interface InvoiceDetailMapper {
    int countByExample(InvoiceDetailExample example);

    int deleteByExample(InvoiceDetailExample example);

    int insert(InvoiceDetail record);

    int insertSelective(InvoiceDetail record);

    List<InvoiceDetail> selectByExample(InvoiceDetailExample example);

    InvoiceDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InvoiceDetail record);

    int updateByPrimaryKey(InvoiceDetail record);

    /**根据agentId获取agentPid*/
    String getAgentPidByAgentId(String agentId);

    /**根据上级获取全部下级的agentId*/
    List<String> getAgentIdByBusParent(String agentId);

}