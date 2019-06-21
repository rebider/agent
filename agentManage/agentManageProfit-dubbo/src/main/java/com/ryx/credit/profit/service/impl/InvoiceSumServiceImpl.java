package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.InvoiceSumMapper;
import com.ryx.credit.profit.pojo.InvoiceSum;
import com.ryx.credit.profit.pojo.InvoiceSumExample;
import com.ryx.credit.profit.service.IInvoiceSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("invoiceSumService")
public class InvoiceSumServiceImpl implements IInvoiceSumService {
    @Autowired
    InvoiceSumMapper invoiceSumMapper;


    @Override
    public PageInfo selectByMap(PageInfo pageInfo, Map<String, String> param) {

        InvoiceSumExample invoiceSumExample = new InvoiceSumExample();
        InvoiceSumExample.Criteria criteria = invoiceSumExample.createCriteria();
        if (param.get("agentName") != null && param.get("agentName") != "") {
            criteria.andAgentIdEqualTo(param.get("agentName"));
        }
        if (param.get("agentId") != null && param.get("agentId") != "") {
            criteria.andAgentNameEqualTo(param.get("agentId"));
        }
        if (param.get("topOrgName") != null && param.get("topOrgName") != "") {
            criteria.andTopOrgNameEqualTo(param.get("topOrgName"));
        }
        if (param.get("topOrgId") != null && param.get("topOrgId") != "") {
            criteria.andTopOrgIdEqualTo(param.get("topOrgId"));
        }
        if (param.get("invoiceStatus") != null && param.get("invoiceStatus") != "") {
            criteria.andInvoiceStatusEqualTo(param.get("invoiceStatus"));
        }
        if (param.get("profitMonth") != null && param.get("profitMonth") != "") {
            criteria.andProfitMonthEqualTo(param.get("profitMonth"));
        }
        if (param.get("profitMonth") != null && param.get("profitMonth") != "") {
            criteria.andProfitMonthEqualTo(param.get("profitMonth"));
        }
        if (param.get("invoiceCompany") != null && param.get("invoiceCompany") != "") {
            criteria.andInvoiceCompanyEqualTo(param.get("invoiceCompany"));
        }

        List<InvoiceSum> invoiceSums = invoiceSumMapper.selectByExample(invoiceSumExample);
        int count = (int) invoiceSumMapper.countByExample(invoiceSumExample);
        pageInfo.setTotal(count);
        pageInfo.setRows(invoiceSums);
        return pageInfo;
    }
}
