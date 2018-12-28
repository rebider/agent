package com.ryx.credit.profit.dao;

import com.ryx.credit.profit.pojo.InvoiceDetail;
import com.ryx.credit.profit.pojo.InvoiceDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InvoiceDetailMapper {
    long countByExample(InvoiceDetailExample example);

    int deleteByExample(InvoiceDetailExample example);

    int insert(InvoiceDetail record);

    int insertSelective(InvoiceDetail record);

    List<InvoiceDetail> selectByExample(InvoiceDetailExample example);

    InvoiceDetail selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(InvoiceDetail record);

    int updateByPrimaryKey(InvoiceDetail record);

    List<Map<String,Object>> queryInvoiceAgents(Map<String,Object> params);

    /**根据agentId获取agentPid*/
    String getAgentPidByAgentId(String agentId);

    /**根据上级获取全部下级的agentId*/
    List<String> getAgentIdByBusParent(String agentId);

    String getAgentIdbyAgentName(String agentId);

    void deleteByMonth(String profitMonth);
}