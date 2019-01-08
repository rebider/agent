package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.CuserAgentMapper;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMergeBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMergeMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.AgentMergeService;
import com.ryx.credit.service.impl.order.TerminalTransferServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/***
 * 代理商合并
 * @Author liudh
 * @Description //TODO 
 * @Date 2019/1/7 16:24
 * @Param
 * @return
 **/
@Service("agentMergeService")
public class AgentMergeServiceImpl implements AgentMergeService,Cloneable {

    private static Logger log = LoggerFactory.getLogger(TerminalTransferServiceImpl.class);
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentMergeMapper agentMergeMapper;
    @Autowired
    private AgentMergeBusInfoMapper agentMergeBusInfoMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private CUserMapper cUserMapper;
    @Autowired
    private CuserAgentMapper cUserAgentMapper;


    /**
     * 处理任务
     * @param agentVo
     * @param userId
     * @param busId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalAgentMergeTask(AgentVo agentVo, String userId, String busId) throws Exception{
        try {
            if(agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())){

            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                log.error(result.getMsg());
                throw new MessageException("工作流处理任务异常");
            }
        }catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("catch工作流处理任务异常!" );
        }
        return AgentResult.ok();
    }


    /**
     * 审批结果监听
     * @param proIns
     * @param agStatus
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressAgentMergeActivity(String proIns, BigDecimal agStatus)throws Exception{

        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andStatusEqualTo(Status.STATUS_1.status).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            log.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            throw new MessageException("审批和数据关系有误");
        }
        BusActRel busActRel = list.get(0);
        AgentMerge agentMerge = agentMergeMapper.selectByPrimaryKey(busActRel.getBusId());
        agentMerge.setCloReviewStatus(agStatus);
        agentMerge.setuTime(new Date());
        int i = agentMergeMapper.updateByPrimaryKeySelective(agentMerge);
        if(i!=1){
            log.info("审批任务结束{}{}，代理商合并更新失败1", proIns, agStatus);
            throw new MessageException("代理商合并更新失败");
        }

        AgentMergeBusInfoExample agentMergeBusInfoExample = new AgentMergeBusInfoExample();
        AgentMergeBusInfoExample.Criteria criteria = agentMergeBusInfoExample.createCriteria();
        criteria.andAgentMargeIdEqualTo(busActRel.getBusId());
        criteria.andMergeStatusEqualTo(MergeStatus.WXS.getValue());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentMergeBusInfo> agentMergeBusInfos = agentMergeBusInfoMapper.selectByExample(agentMergeBusInfoExample);
        if(agentMergeBusInfos.size()==0){
            throw new MessageException("查询合并业务失败");
        }
        for (AgentMergeBusInfo agentMergeBusInfo : agentMergeBusInfos) {
            agentMergeBusInfo.setMergeStatus(MergeStatus.SX.getValue());
            //更新合并业务表合并状态为生效
            int j = agentMergeBusInfoMapper.updateByPrimaryKeySelective(agentMergeBusInfo);
            if(j!=1){
                log.info("审批任务结束{}{}，代理商合并更新合并状态", proIns, agStatus);
                throw new MessageException("代理商合并更新失败");
            }
            //删除副代理商被合并的平台
            AgentBusInfo agentBusInfo = new AgentBusInfo();
            agentBusInfo.setId(agentMergeBusInfo.getBusId());
            agentBusInfo.setStatus(Status.STATUS_0.status);
            agentBusInfo.setBusStatus(BusinessStatus.pause.status);
            int k = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
            if(k!=1){
                log.info("审批任务结束{}{}，代理商合并删除副代理商被合并的平台失败", proIns, agStatus);
                throw new MessageException("代理商合并更新失败");
            }
        }
        //代理商合并之后如果一个业务都没了,基本信息等保留,禁止登陆后台
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria  agentBusInfocriteria = agentBusInfoExample.createCriteria();
        agentBusInfocriteria.andAgentIdEqualTo(agentMerge.getSubAgentId());
        agentBusInfocriteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        if(agentBusInfos.size()==0){
            CuserAgentExample cuserAgentExample = new CuserAgentExample();
            CuserAgentExample.Criteria userAgentCriteria = cuserAgentExample.createCriteria();
            userAgentCriteria.andAgentidEqualTo(agentMerge.getSubAgentId());
            List<CuserAgent> cuserAgents = cUserAgentMapper.selectByExample(cuserAgentExample);
            if(cuserAgents.size()!=1){
                throw new MessageException("代理商信息有误");
            }
            CuserAgent cuserAgent = cuserAgents.get(0);
            int l = cUserMapper.updateStatusByPrimaryKey(Long.valueOf(cuserAgent.getUserid()));
            if(l!=1){
                log.info("审批任务结束{}{}，代理商合并禁止登陆后台失败", proIns, agStatus);
                throw new MessageException("代理商合并更新失败");
            }
        }
        //如果财务填写了，合并到那个被合并代理商的分期订单欠款,同步到分润其他扣款

        //通知业务系统

        return AgentResult.ok();
    }

}
