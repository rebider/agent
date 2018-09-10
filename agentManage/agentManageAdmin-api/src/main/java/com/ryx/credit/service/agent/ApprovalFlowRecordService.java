package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.ApprovalFlowRecord;

/**
 * Created by RYX on 2018/9/4.
 */
public interface ApprovalFlowRecordService {

    String insert(ApprovalFlowRecord record)throws Exception;

    int update(ApprovalFlowRecord record);

    PageInfo approvalFlowList(ApprovalFlowRecord approvalFlowRecord, Page page);
}
