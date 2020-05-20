package com.ryx.credit.service.agent;

import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentCertification;
import com.ryx.credit.pojo.admin.vo.AgentCertifiVo;

import java.util.List;
import java.util.Map;

/**
 * @Auther: ssx
 * @Date: 2019/9/18 13:26
 * @Description:
 */
public interface AgentCertificationService {
    //代理商认证明细
    PageInfo agentCertifiDetails(Page page, Map map);
    //添加待认证代理商信息
    AgentResult addAgentCertifis(List<Agent> agents,Long userId);
    //查询代理商认证列表
    PageInfo agentCertifiListView(Page page, Map map);
    //获取并更新认证记录为正在处理
    List<AgentCertification> fetchFhData();
    //处理认证信息
    AgentResult processData(Agent agent,String id,String orgid) throws MessageException;

    //代理商认证管理列表导出
    List<AgentCertifiVo> exportAgentCertifications(Map map);

    AgentCertification  getMaxId(Map map);

    int updateCertifi(AgentCertification agentCertification);

    List<AgentCertification> queryCersByAgent(List ids);
}
