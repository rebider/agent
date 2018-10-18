package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.PAgentQuitMapper;
import com.ryx.credit.profit.pojo.PAgentQuit;
import com.ryx.credit.profit.pojo.PAgentQuitExample;
import com.ryx.credit.profit.service.PAgentQuitService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Lihl
 * @Date 2018/9/27
 * @Desc 代理商退出申请
 */
@Service("pAgentQuitService")
public class PAgentQuitServiceImpl implements PAgentQuitService {
    Logger logger = LoggerFactory.getLogger(PAgentQuitServiceImpl.class);
    @Autowired
    private PAgentQuitMapper pAgentQuitMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private AgentEnterService agentEnterService;

    @Override
    public PageInfo getAgentQuitList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = pAgentQuitMapper.getAgentQuitCount(param);
        List<Map<String, Object>> list = pAgentQuitMapper.getAgentQuitList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        System.out.println("查询列表------------------------------------------" + JSONObject.toJSON(list));
        return pageInfo;
    }

    @Override
    public ResultVO applyPAgentQuit(PAgentQuit pAgentQuit, String cuser, String workId) throws ProcessException {
        pAgentQuit.setId(idService.genId(TabId.P_AGENT_QUIT));
        System.out.println("序列ID---------------------"+idService.genId(TabId.P_AGENT_QUIT));
        pAgentQuitMapper.insertSelective(pAgentQuit);

        Map startPar = agentEnterService.startPar(cuser);
        if (null == startPar) {
            throw new ProcessException("启动部门参数为空！");
        }
        //启动审批流
        String proceId = activityService.createDeloyFlow(null, workId, null, null, startPar);
        if (proceId == null) {
            logger.error("代理商退出申请审批流启动失败，代理商ID：{}", pAgentQuit.getAgentId());
            throw new ProcessException("代理商退出申请审批流启动失败！");
        }
        BusActRel record = new BusActRel();
        record.setBusId(pAgentQuit.getAgentId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setBusType(BusActRelBusType.QUIT.name());
        try {
            taskApprovalService.addABusActRel(record);
            logger.info("代理商退出申请审批流启动成功！");
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("代理商退出申请审批流启动失败{}");
            throw new ProcessException("代理商退出申请审批流启动失败！:{}",e.getMessage());
        }
        pAgentQuit.setFlowStatus(AgStatus.Approving.name());//审批状态：Approving 2: 审批中
        pAgentQuitMapper.updateByPrimaryKeySelective(pAgentQuit);
        return ResultVO.success(record);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public void completeTaskEnterActivity(String insid, String status) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                PAgentQuit pAgentQuit = pAgentQuitMapper.selectByPrimaryKey(rel.getBusId());
                pAgentQuit.setFlowStatus(AgStatus.Approved.name());//审批状态：Approved 3: 审批通过
                pAgentQuit.setPassDate(df.format(new Date()));//退出日期（生效日期）
                logger.info("1.更新代理商退出申请状态为通过，已生效");
                pAgentQuitMapper.updateByPrimaryKeySelective(pAgentQuit);

//                String agentId = taxAdjust.getAgentPid();//代理商唯一码
//                //更新收款账户表的 税点值
//                AgentColinfo colinfo = new AgentColinfo();
//                colinfo.setAgentId(agentId);
//                AgentColinfo agentColinfo = agentColinfoService.queryPoint(colinfo);//查询数据
//                if (agentColinfo != null) {
//                    BigDecimal colinfoPoint = taxAdjust.getTaxIng();
//                    agentColinfo.setCloTaxPoint(colinfoPoint);
//                    if (agentColinfoService.updateByPrimaryKeySelective(agentColinfo)!=1) {
//                        throw new MessageException("更新收款账户税点值失败！");
//                    }
//                }
//                // 更新代理商表的 税点值
//                Agent agent = agentService.getAgentById(agentId);//查询数据
//                if (agent != null) {
//                    BigDecimal agentPoint = taxAdjust.getTaxIng();
//                    agent.setCloTaxPoint(agentPoint);
//                    if (agentService.updateByPrimaryKeySelective(agent)!=1) {
//                        throw new MessageException("更新代理商税点值失败！");
//                    }
//                }

                logger.info("2.更新审批流与业务对象");
                rel.setStatus(Status.STATUS_2.status);
                taskApprovalService.updateABusActRel(rel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("代理商退出申请审批流回调异常，activId：{}" + insid);
        }
    }

    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException {
        logger.info("审批对象：{}", JSONObject.toJSON(agentVo));
        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();
        if(StringUtils.isNotBlank(agentVo.getOrderAprDept())){
            reqMap.put("dept", agentVo.getOrderAprDept());
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


//    @Override
//    public void applyPAgentQuit(PAgentQuit applyfor) {
//        applyfor.setId(idService.genId(TabId.P_AGENT_QUIT));
//        pAgentQuitMapper.insertSelective(applyfor);
//    }


    @Override
    public PAgentQuit getBusIdByAgentId(String agentId) {
        if (StringUtils.isNotBlank(agentId)) {
            return pAgentQuitMapper.getBusIdByAgentId(agentId);
        }else{
            return null;
        }
    }

    @Override
    public PAgentQuit selectByPrimaryKey(String id) {
        return pAgentQuitMapper.selectByPrimaryKey(id);
    }

//    @Override
//    public PAgentQuit getAgUniqNumAndId(String agUniqNum) {
//        return pAgentQuitMapper.getAgUniqNumAndId(agUniqNum);
//    }

    @Override
    public List<Map<String, Object>> queryBusPlat(Map<String, Object> param) {
        List<Map<String, Object>> mapList = pAgentQuitMapper.queryBusPlat(param);
        return mapList;
    }

    @Override
    public void updateAgentQuit(PAgentQuit pAgentQuit) {
        pAgentQuitMapper.updateByPrimaryKeySelective(pAgentQuit);
    }

    @Override
    public List<PAgentQuit> getAgentQuitList() {
        List<PAgentQuit> pAgentQuits = pAgentQuitMapper.selectByExample(null);
        return pAgentQuits;
    }

}
