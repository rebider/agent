package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.AgentBusInfoExample;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.order.TerminalTransferDetail;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.AgentRelateDetailMapper;
import com.ryx.credit.profit.dao.AgentRelateMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IAgentRelateService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/***
 * @author renshenghao
 * @version 1.0
 * @
 */
@Service("agentRelateService")
public class AgentRelateServiceImpl implements IAgentRelateService {

    Logger logger = LoggerFactory.getLogger(AgentRelateServiceImpl.class);
    @Autowired
    private AgentRelateMapper agentRelateMapper;
    @Autowired
    private AgentRelateDetailMapper agentRelateDetailMapper;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;


    @Override
    public PageInfo getList(Map<String, Object> param, PageInfo pageInfo) {
        List<Map<String,Object>> list=agentRelateMapper.getList(param);
        pageInfo.setRows(list);
        AgentRelateExample example=new AgentRelateExample();
        AgentRelateExample.Criteria criteria=example.createCriteria();

        Object agentId=param.get("agentId");
        Object agentName=param.get("agentName");
        Object status=param.get("status");
        if(agentId!=null&&StringUtils.isNotBlank(agentId.toString())){
            criteria.andAgentIdEqualTo(agentId.toString());
        }
        if (agentName!=null&&StringUtils.isNotBlank(agentName.toString())) {
            criteria.andAgentIdEqualTo(agentName.toString());
        }
        if (status!=null&&StringUtils.isNotBlank(status.toString())){
            criteria.andStatusEqualTo(new BigDecimal(status.toString()));
        }
        long count=agentRelateMapper.countByExample(example);
        pageInfo.setTotal((int)count);
        return pageInfo;
    }

    @Override
    public int insertAgentRelate(AgentRelate agentRelate) {
        return agentRelateMapper.insert(agentRelate);
    }

    @Override
    public int updateAgentRelate(AgentRelate agentRelate) {
        return agentRelateMapper.updateByPrimaryKey(agentRelate);
    }

    @Override
    public List<AgentRelate> selectByExample(AgentRelateExample agentRelateExample) {
        return agentRelateMapper.selectByExample(agentRelateExample);
    }

    @Override
    public AgentRelate selectById(String id) {
        return agentRelateMapper.selectByPrimaryKey(id);
    }

    @Override
    public Map<String, String> queryParentAgentByAgentId(Map<String,String> param) {
        return agentRelateMapper.queryParentAgentByAgentId(param);
    }

    @Override
    public boolean applyAgentRelate(AgentRelate agentRelate, List<AgentRelateDetail> list, String userId, String workId) {
        agentRelate.setId(idService.genId(TabId.A_AGENT_RELATE));
        logger.info("序列ID......"+idService.genId(TabId.A_AGENT_RELATE));
        for (AgentRelateDetail agentRelateDetail:list){
            agentRelateDetail.setId(idService.genId(TabId.A_AGENT_RELATE_DETAIL));
            agentRelateDetail.setRelateId(agentRelate.getId());
            agentRelateDetailMapper.insert(agentRelateDetail);
        }
        agentRelateMapper.insert(agentRelate);
        Map<String,Object> map=agentEnterService.startPar(userId);
        Map<String,Object> param=new HashMap<String,Object>();
        if(map!=null&&map.get("party")!=null){
            param.put("part",map.get("party"));
        }
        String proceId = activityService.createDeloyFlow(null, workId, null, null, param);
        if (proceId == null) {
            AgentRelateExample agentRelateExample=new AgentRelateExample();
            AgentRelateExample.Criteria criteria = agentRelateExample.createCriteria();
            criteria.andIdEqualTo(agentRelate.getId());
            agentRelateMapper.deleteByExample(agentRelateExample);
            logger.error("代理商关联审批流启动失败，代理商ID：{}", agentRelate.getAgentId());
            throw new ProcessException("代理商关联审批流启动失败!");
        }

        BusActRel record = new BusActRel();
        record.setBusId(agentRelate.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userId);
        record.setBusType(BusActRelBusType.agentRelate.name());
        record.setAgentId(agentRelate.getAgentId());
        record.setAgentName(agentRelate.getAgentName());
        record.setDataShiro(BusActRelBusType.agentRelate.key);
        try {
            taskApprovalService.addABusActRel(record);
            logger.info("POS奖励审批流启动成功");
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("POS奖励审批流启动失败{}");
            throw new ProcessException("POS奖励审批流启动失败!:{}",e.getMessage());
        }
        return true;
    }
    @Override
    public List<AgentRelateDetail> queryDetailByBusId(String busId){
        AgentRelateDetailExample agentRelateDetailExam=new AgentRelateDetailExample();
        AgentRelateDetailExample.Criteria criteria=agentRelateDetailExam.createCriteria();
        if (StringUtils.isNotBlank(busId)){
            criteria.andRelateIdEqualTo(busId);
        }
        return agentRelateDetailMapper.selectByExample(agentRelateDetailExam);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalAgentRelateTask(AgentVo agentVo, String userId) throws Exception {
        logger.info("审批对象：{}", JSONObject.toJSON(agentVo));
        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", agentVo.getOrderAprDept());
        }
        if(Objects.equals("pass",agentVo.getApprovalResult())
                && StringUtils.isBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", "finish");
        }
        reqMap.put("rs", agentVo.getApprovalResult());
        reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
        reqMap.put("approvalPerson", userId);
        reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
        reqMap.put("taskId", agentVo.getTaskId());

        logger.info("创建下一审批流对象：{}", reqMap.toString());
        Map resultMap = activityService.completeTask(agentVo.getTaskId(), reqMap);
        Boolean rs = (Boolean) resultMap.get("rs");
        String msg = String.valueOf(resultMap.get("msg"));
        if (resultMap == null) {
            return result;
        }
        if (!rs) {
            result.setMsg(msg);
            return result;
        }
        return AgentResult.ok(resultMap);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void completeTaskEnterActivity(String insid, String status) {

        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                AgentRelate agentRelate = agentRelateMapper.selectByPrimaryKey(rel.getBusId());
                if("1".equals(status)){
                    agentRelate.setStatus(Status.STATUS_1.status); // PASS 1:生效
                }else if ("2".equals(status)){
                    agentRelate.setStatus(Status.STATUS_2.status); // PASS 1:驳回
                }
                agentRelateMapper.updateByPrimaryKeySelective(agentRelate);//将此审批通过的申请更新到数据
                logger.info("更新审批流与业务对象");
                rel.setActivStatus(AgStatus.Approved.name());
                taskApprovalService.updateABusActRel(rel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("代理商关联审批流回调异常，activId：{}" + insid);
        }
    }
}
