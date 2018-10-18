package com.ryx.credit.profit.service;

import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.pojo.PAgentMerge;

import java.util.Map;

/**
 * 代理商合并Service
 * @version V1.0
 * @Description:
 * @author: LiuQY
 * @date: 2018/9/27 09:30
 */
public interface IProfitAgentMergerService {
    /**代理商合并查询*/
    PageInfo getProfitAgentMergeList(Map<String, Object> param, PageInfo pageInfo);
    /**代理商合并提交存库*/
    void agentMergeTaxEnterIn(PAgentMerge record,Long  userId)throws ProcessException;
    /**代理商合并查询一条数据*/
    PAgentMerge getMergeById(String var1);

    /**
     * 处理审批任务
     * @param agentVo
     * @param userId
     * @throws Exception
     */
    public AgentResult approvalTask(AgentVo agentVo, String userId)throws Exception;

    /**
     * 审批流回调方法
     * @param insid
     * @param status
     */
    public AgentResult approveFinish(String insid, String status)throws Exception;

    /**
     * 审批退回，修改申请信息
     * @param pAgentMerge
     * @throws Exception
     */
    public void editMergeRegect(PAgentMerge pAgentMerge)throws Exception;

}
