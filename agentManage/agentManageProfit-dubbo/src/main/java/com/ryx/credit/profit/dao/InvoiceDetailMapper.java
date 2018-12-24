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

    /**根据agentId 获得下级agentId*/
    List<String> getAgentId(String agentId);
    /**根据agentId获取agentPid*/
    String getAgentPidByAgentId(String agentId);


}