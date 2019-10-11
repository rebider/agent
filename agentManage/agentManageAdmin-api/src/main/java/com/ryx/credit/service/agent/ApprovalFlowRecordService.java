package com.ryx.credit.service.agent;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.ApprovalFlowRecord;
import com.ryx.credit.pojo.admin.vo.ApprovalFlowRecordVo;

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
     * 导出代理商入网、业务数据
     * @param approvalFlowRecord
     * @return
     */
    List<ApprovalFlowRecordVo> exportAgentNetln(ApprovalFlowRecord approvalFlowRecord) throws Exception;

    /**
     * 导出代理商业务修改数据
     * @param approvalFlowRecord
     * @return
     */
    List<Map<String, Object>> exportAgentEdit(ApprovalFlowRecord approvalFlowRecord) throws Exception;

    /**
     * 导出代理商账户修改数据
     * @param approvalFlowRecord
     * @return
     */
    List<Map<String, Object>> exportAgentColinfo(ApprovalFlowRecord approvalFlowRecord) throws Exception;


    List<Map<String,Object>> queryFlowByExecutionId(String executionId);
}
