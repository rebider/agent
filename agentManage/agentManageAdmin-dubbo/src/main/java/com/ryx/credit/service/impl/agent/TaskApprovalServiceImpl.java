package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentColinfoMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.AgentBusInfoVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.TaskApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/30 16:51
 */
@Service("taskApprovalService")
public class TaskApprovalServiceImpl implements TaskApprovalService {

     @Autowired
     private AgentBusInfoMapper agentBusInfoMapper;
     @Autowired
     private AgentColinfoMapper agentColinfoMapper;
     @Autowired
     private AgentEnterService agentEnterService;
     @Autowired
     private AgentColinfoService agentColinfoService;
     @Autowired
     private ActivityService activityService;
     @Autowired
     private BusActRelMapper busActRelMapper;

     @Override
     public List<Map<String,Object>> queryBusInfoAndRemit(AgentBusInfo agentBusInfo){

         if(StringUtils.isBlank(agentBusInfo.getAgentId())){
            return null;
         }
         return agentBusInfoMapper.queryBusInfoAndRemit(agentBusInfo.getAgentId());
     }


    @Override
    public List<Map<String, Object>> queryBusInfoAndRemitByBusId(String busId) {
        if(StringUtils.isBlank(busId)){
            return new ArrayList<>();
        }
        return agentBusInfoMapper.queryBusInfoAndRemitByBusId(busId);
    }

    /**
     * 处理任务
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo,String userId) throws Exception{

        try {
            //处理财务修改
            for (AgentColinfoRel agentColinfoRel : agentVo.getAgentColinfoRelList()) {
                AgentResult result = agentColinfoService.saveAgentColinfoRel(agentColinfoRel, userId);
                if(!result.isOK()){
                    throw new ProcessException("保存收款关系异常");
                }
            }
            //处理业务修改
            for (AgentBusInfoVo agentBusInfoVo : agentVo.getBusInfoVoList()) {
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfoVo.getId());
                agentBusInfoVo.setId(agentBusInfoVo.getId());
                agentBusInfoVo.setVersion(agentBusInfo.getVersion());
                int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfoVo);
                if(i!=1){
                    throw new ProcessException("更新打款公司或业务所属上级异常");
                }
            }

            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                throw new ProcessException("工作流处理任务异常");
            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException("catch工作流处理任务异常!");
        }
        return AgentResult.ok();
    }

    /**
     * 查询工作流程
     * @param busId
     * @param busType
     * @return
     */
    @Override
    public Map findBusActByBusId(String busId,String busType){
        BusActRelExample example = new BusActRelExample();
        BusActRelExample.Criteria criteria = example.createCriteria();
        criteria.andBusIdEqualTo(busId);
        criteria.andBusTypeEqualTo(busType);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> busActRels = busActRelMapper.selectByExample(example);
        if(busActRels.size()!=1){
            return null;
        }
        BusActRel busActRel = busActRels.get(0);
        Map resultMap = activityService.getImageByExecuId(busActRel.getActivId());
        return resultMap;
    }
}
