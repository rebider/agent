package com.ryx.credit.profit.service;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.FreezeOperationRecord;
import com.ryx.credit.profit.pojo.FreezeAgent;

import java.util.List;
import java.util.Map;

public interface IFreezeAgentSercice {
    PageInfo getselectFreezeDate(Map<String, Object> param, PageInfo pageInfo);
    public void operationFreezeDate(List<FreezeOperationRecord> freezeOperationRecords, String user);

    PageInfo getFreezeData(FreezeAgent freezeAgent, String isQuerySubordinate, Page page, String orgId);

    List<FreezeAgent> getFreezeList();
    public PageInfo getselectDetailFreezeDate(Map<String, Object> param, PageInfo pageInfo);
}
