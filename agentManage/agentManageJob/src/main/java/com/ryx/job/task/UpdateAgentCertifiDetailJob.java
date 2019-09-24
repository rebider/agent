package com.ryx.job.task;


import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentCertification;
import com.ryx.credit.service.agent.AgentCertificationService;
import com.ryx.credit.service.agent.BusinessCAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: agentManage
 *
 * @description: 工商认证
 *
 * @author: zxb
 *
 * @create: 2019-09-19 18:50
 **/
@Service("updateAgentCertifiDetailJob")
public class UpdateAgentCertifiDetailJob implements DataflowJob<AgentCertification> {
    private static Logger logger = LoggerFactory.getLogger(UpdateAgentCertifiDetailJob.class);
    @Autowired
    private AgentCertificationService agentCertificationService;

    @Override
    public List<AgentCertification> fetchData(ShardingContext shardingContext) {

        try {
            logger.debug("认证工商信息开始获取数据");
            return agentCertificationService.fetchFhData();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("认证工商信息开始获取数据失败",e);
        }
        return new ArrayList<>();


    }

    @Override
    public void processData(ShardingContext shardingContext, List<AgentCertification> list) {
        logger.info("开始执行认证任务");
        list.forEach(cer->{
            try {
                logger.info("商户唯一编码{},认证记录id{}",cer.getAgentId(),cer.getId());
                Agent agent = new Agent();
                agent.setAgUniqNum(cer.getAgentId());
                FastMap par = FastMap.fastMap("agentId",cer.getAgentId());
                AgentCertification  agentCertification = agentCertificationService.getMaxId(par);
                String orgCerId = "";
                if (null!=agentCertification)
                    orgCerId=agentCertification.getId();
                AgentResult agentResult = agentCertificationService.processData(agent, cer.getId(),orgCerId);
                if (200!=agentResult.getStatus()){
                    cer.setCerProStat(Status.STATUS_2.status);
                    agentCertificationService.updateCertifi(cer);
                }
            }catch (Exception e){
                logger.error(e.toString());
                cer.setCerProStat(Status.STATUS_2.status);
                agentCertificationService.updateCertifi(cer);
                logger.error("认证任务执行出错!商户唯一编码{},认证记录id{}",cer.getAgentId(),cer.getId());
            }

        });
        logger.info("认证任务执行成功");


    }
}
