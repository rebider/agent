package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.Platform;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.MessageException;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/5/30 16:51
 */
@Service("taskApprovalService")
public class TaskApprovalServiceImpl implements TaskApprovalService {

    private Logger logger = LoggerFactory.getLogger(TaskApprovalServiceImpl.class);

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
     @Autowired
     private TaskApprovalService taskApprovalService;

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
            taskApprovalService.updateApproval(agentVo, userId);
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                logger.error(result.getMsg());
                throw new ProcessException("工作流处理任务异常");
            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new MessageException("catch工作流处理任务异常:",e.getMsg());
        }
        return AgentResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult updateApproval(AgentVo agentVo,String userId) throws Exception{

        if(agentVo.getApprovalResult().equals("pass")){
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
                //上级不为空判断是否与上级打款公司一致
                if(StringUtils.isNotBlank(agentBusInfo.getBusParent())){
                    AgentBusInfo parentBusInfo = agentBusInfoMapper.selectByPrimaryKey(agentBusInfo.getBusParent());
                    if(!agentBusInfoVo.getCloPayCompany().equals(parentBusInfo.getCloPayCompany())){
                        throw new ProcessException(Platform.getContentByValue(agentBusInfo.getBusPlatform())+"上级打款公司不一致");
                    }
                }

                agentBusInfoVo.setId(agentBusInfoVo.getId());
                agentBusInfoVo.setVersion(agentBusInfo.getVersion());
                agentBusInfoVo.setcUtime(new Date());
                int i = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfoVo);
                if(i!=1){
                    throw new ProcessException("更新打款公司或业务所属上级异常");
                }
            }
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
    public Map findBusActByBusId(String busId,String busType,String activStatus){
        BusActRel busActRel = queryBusActRel(busId, busType,activStatus);
        if(busActRel==null){
            return null;
        }
        Map resultMap = activityService.getImageByExecuId(busActRel.getActivId());
        return resultMap;
    }

    @Override
    public BusActRel queryBusActRel(String busId,String busType,String activStatus){
        BusActRelExample example = new BusActRelExample();
        BusActRelExample.Criteria criteria = example.createCriteria();
        criteria.andBusIdEqualTo(busId);
        criteria.andBusTypeEqualTo(busType);
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andActivStatusEqualTo(activStatus);
        List<BusActRel> busActRels = busActRelMapper.selectByExample(example);
        if(busActRels.size()!=1){
            return null;
        }
        BusActRel busActRel = busActRels.get(0);
        return busActRel;
    }

    @Override
    public List<Map<String, Object>> queryById(AgentBusInfo agentBusInfo) {
        if(StringUtils.isBlank(agentBusInfo.getAgentId())){
            return null;
        }
        return agentBusInfoMapper.queryById(agentBusInfo.getAgentId());
    }


    @Override
    public int addABusActRel(BusActRel busActRel)throws Exception {
        if(StringUtils.isBlank(busActRel.getBusId()))throw new ProcessException("BusId不能为空");
        if(StringUtils.isBlank(busActRel.getActivId()))throw new ProcessException("ActivId不能为空");
        if(StringUtils.isBlank(busActRel.getcUser()))throw new ProcessException("cUser不能为空");
        if(StringUtils.isBlank(busActRel.getBusType()))throw new ProcessException("BusType不能为空");
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(busActRel.getBusId());
        record.setActivId(busActRel.getActivId());
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(busActRel.getcUser());
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(busActRel.getBusType());
        record.setActivStatus(AgStatus.Approving.name());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("启动审批异常，添加审批关系失败{}:{}:{}", busActRel.getActivId(), busActRel.getBusId(),busActRel.getBusType());
        }
        return 1;
    }

    @Override
    public int updateABusActRel(BusActRel busActRel) {
        if(StringUtils.isBlank(busActRel.getBusId()))throw new ProcessException("BusId不能为空");
        if(StringUtils.isBlank(busActRel.getActivId()))throw new ProcessException("ActivId不能为空");
        if(StringUtils.isBlank(busActRel.getcUser()))throw new ProcessException("cUser不能为空");
        if(StringUtils.isBlank(busActRel.getBusType()))throw new ProcessException("BusType不能为空");
        BusActRelKey key = new BusActRelKey();
        key.setActivId(busActRel.getActivId());
        key.setBusId(busActRel.getBusId());
        BusActRel rel = busActRelMapper.selectByPrimaryKey(key);
        if(rel==null){
           return 0;
        }
        rel.setActivStatus(busActRel.getActivStatus());
        if(1==busActRelMapper.updateByPrimaryKeySelective(rel)){
            return 1;
        }
        return 0;
    }

    @Override
    public BusActRel queryBusActRel(BusActRel busActRel) {
        BusActRelExample example = new BusActRelExample();
        BusActRelExample.Criteria c = example.or().andStatusEqualTo(Status.STATUS_1.status);
        if(StringUtils.isNotBlank(busActRel.getBusId())) {
            c.andBusIdEqualTo(busActRel.getBusId());
        }
        if(StringUtils.isNotBlank(busActRel.getActivId())){
            c.andActivIdEqualTo(busActRel.getActivId());
        }
        if(StringUtils.isNotBlank(busActRel.getcUser())){
            c.andCUserEqualTo(busActRel.getcUser());
        }
        if(StringUtils.isNotBlank(busActRel.getActivStatus())){
            c.andActivStatusEqualTo(busActRel.getActivStatus());
        }
        if(StringUtils.isNotBlank(busActRel.getBusType())){
            c.andBusTypeEqualTo(busActRel.getBusType());
        }
        List<BusActRel>  list = busActRelMapper.selectByExample(example);
        return list.size()==1?list.get(0):null;
    }
}
