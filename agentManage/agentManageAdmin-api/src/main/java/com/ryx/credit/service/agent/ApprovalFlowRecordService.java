package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.ApprovalFlowRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/9/4.
 */
public interface ApprovalFlowRecordService {

    String insert(ApprovalFlowRecord record)throws Exception;

    int update(ApprovalFlowRecord record);

    PageInfo approvalFlowList(ApprovalFlowRecord approvalFlowRecord, Page page);

    /**
     * 导出代理商合并数据
     * @param approvalFlowRecord
     * @return
     */
    List<Map<String, Object>> exportAgentMerge(ApprovalFlowRecord approvalFlowRecord) throws Exception;

    /**
     * 导出代理商入网数据
     * @param approvalFlowRecord
     * @return
     */
    List<Map<String, Object>> exportAgentNetln(ApprovalFlowRecord approvalFlowRecord) throws Exception;


    List<Map<String, Object>> exportAgentEdit(ApprovalFlowRecord approvalFlowRecord) throws Exception;


    List<Map<String,Object>> queryFlowByExecutionId(String executionId);
}
