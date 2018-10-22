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
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.BusActRel;

import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.PAgentMergeMapper;
import com.ryx.credit.profit.dao.ProfitOrganTranMonthMapper;
import com.ryx.credit.profit.pojo.PAgentMerge;
import com.ryx.credit.profit.pojo.PAgentQuit;
import com.ryx.credit.profit.service.BusiPlatService;
import com.ryx.credit.profit.service.IProfitAgentMergerService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.CapitalService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 代理商合并Impl
 * @version V1.0
 * @Description:
 * @author: LiuQY
 * @date: 2018/9/27 09:30
 */
@Service("profitAgentMergerService")
public class ProfitAgentMergerServiceImpl implements IProfitAgentMergerService {
    private org.slf4j.Logger logger = LoggerFactory.getLogger(ProfitAgentMergerServiceImpl.class);
    @Autowired
    private PAgentMergeMapper pAgentMergeMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private BusiPlatService busiPlatService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private IProfitAgentMergerService profitAgentMergerService;

    /**
     * 列表展示
     * @param param
     * @param pageInfo
     */
    /*@Autowired
    private CapitalService capitalService;*/
    /**代理商合并*/
    @Override
    public PageInfo getProfitAgentMergeList(Map<String, Object> param, PageInfo pageInfo) {
        Long count = pAgentMergeMapper.getProfitAgentMergeCount(param);
        List<Map<String, Object>> list = pAgentMergeMapper.getProfitAgentMergeList(param);
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        System.out.println("查询============================================" + JSONObject.toJSON(list));
        return pageInfo;
    }

    /**
     * 启动审批流
     * @param pAgentMerge
     * @param userId
     */
    @Override
    public ResultVO agentMergeTaxEnterIn(PAgentMerge pAgentMerge, Long userId) throws ProcessException, MessageException {
        pAgentMerge.setId(idService.genId(TabId.P_AGENT_MERGE));
        /**校验主附代理上是否可以合并*/
        logger.info("主代理商ID:" + pAgentMerge.getMainAgentId());
        logger.info("主代理商ID:" + pAgentMerge.getSubAgentId());
        if(pAgentMerge.getMainAgentId()== null || pAgentMerge.getMainAgentId()== pAgentMerge.getSubAgentId() ||pAgentMerge.getSubAgentId() == null){
            throw new MessageException("主附代理商重复!");
        }
        /**获取资金类型*/
        if(pAgentMerge.getMainAgentId()!= null && pAgentMerge.getSubAgentId() != null){
            //主代理商查业务类型
            List<AgentBusInfo> mainAgentlist = agentBusinfoService.selectByAgenId(pAgentMerge.getMainAgentId());
            //附代理商查询业务类型
            List<AgentBusInfo> subAgentlist =  agentBusinfoService.selectByAgenId(pAgentMerge.getSubAgentId());
            //判断附代理商是否已存在
            List<PAgentMerge> subAgentMergelist = profitAgentMergerService.selectBySubAgenId(pAgentMerge.getSubAgentId());
            if(subAgentMergelist.size() > 0){
                throw new ProcessException("该代理商已经存在!");
            }
            if(mainAgentlist.size() > 0 && subAgentlist.size() > 0){
                StringBuffer main = new StringBuffer();
                StringBuffer sub = new StringBuffer();
                //StringBuffer strSub = new StringBuffer();
                for(AgentBusInfo agentBusInfo:mainAgentlist){
                    main.append(agentBusInfo.getBusPlatform());
                }
                for(AgentBusInfo agentBusInfo:subAgentlist){
                    sub.append(agentBusInfo.getBusPlatform());
                }
                logger.info("主代理商业务类型" +main.toString() );
                logger.info("附代理商业务类型" +sub.toString() );
                boolean status = main.toString().contains(sub.toString());
                if(!status){
                    throw new ProcessException("业务类型不匹配!");
                }
            }
        }
        pAgentMergeMapper.insertSelective(pAgentMerge);
        Map startPar = agentEnterService.startPar(String.valueOf(userId));
        if (null == startPar) {
            throw new ProcessException("启动部门参数为空！");
        }
        //启动审批流
        String proceId = activityService.createDeloyFlow(null, "mergeCity", null, null, startPar);
        if (proceId == null) {
            logger.error("代理商合并审批流启动失败，代理商ID：{}", pAgentMerge.getMainAgentId());
            throw new ProcessException("代理商合审批流启动失败!");
        }
        BusActRel record = new BusActRel();
        record.setBusId(pAgentMerge.getId());
        record.setActivId(proceId);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(String.valueOf(userId));
        record.setBusType(BusActRelBusType.MERGE.name());
        try {
            taskApprovalService.addABusActRel(record);
            logger.info("代理商合并申请审批流启动成功");
        } catch (Exception e) {
            e.getStackTrace();
            logger.error("代理商合并申请审批流启动失败{}");
            throw new ProcessException("代理商合并申请审批流启动失败!:{}",e.getMessage());
        }
        pAgentMerge.setMergeStatus(AgStatus.Approving.name());
        pAgentMergeMapper.updateByPrimaryKeySelective(pAgentMerge);
        return ResultVO.success(record);
    }

    @Override
    public PAgentMerge getMergeById(String id) {
        if (StringUtils.isNotBlank(id)) {
            return pAgentMergeMapper.selectByPrimaryKey(id);
        }else{
            return null;
        }
    }

    /**
     * 处理审批任务
     * @param agentVo
     * @param userId
     * @throws Exception
     */
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception {
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

    /**
     * 审批流回调方法
     * @param insid
     * @param status
     */
    @Override
    public AgentResult approveFinish(String insid, String status) throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel =  taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                PAgentMerge pAgentMerge = pAgentMergeMapper.selectByPrimaryKey(rel.getBusId());

                //一、更改代理商信息的名称
                Agent agent = agentService.getAgentById(pAgentMerge.getSubAgentId());
                agent.setAgName(pAgentMerge.getMainAgentName()+"("+pAgentMerge.getSubAgentName()+")");
                agentService.updateByPrimaryKeySelective(agent);

                //二、手刷改名接口(agentName、agentId)
                String agentName = pAgentMerge.getMainAgentName()+"("+pAgentMerge.getSubAgentName()+")";
                List<String> platId = new ArrayList<>();
                platId.add(pAgentMerge.getSubAgentId());
                busiPlatService.mPos_updateAgName(agentName, platId);

                //二、POS改名接口(uniqueId、orgName、orgType)
                List<AgentBusInfo> list = pAgentMergeMapper.getByBusPlatform(pAgentMerge.getSubAgentId());//根据附代理商ID查询平台编号
                for (AgentBusInfo agentBusInfo : list) {
                    AgentNotifyVo agentNotifyVo = new AgentNotifyVo();
                    agentNotifyVo.setUniqueId(agentBusInfo.getId());//代理商AB码
                    agentNotifyVo.setOrgName(pAgentMerge.getMainAgentName()+"("+pAgentMerge.getSubAgentName()+")");//变更后名称=主名称+(自己名称)
                    if (agentBusInfo.getBusType().equals("2") || agentBusInfo.getBusType().equals("6")){
                        agentNotifyVo.setOrgType("01");//机构类型：01-普通机构
                    } else {
                        agentNotifyVo.setOrgType("02");//机构类型：02-直签机构
                    }
                    busiPlatService.pos_updateAgName(agentNotifyVo);
                }

                pAgentMerge.setMergeStatus(AgStatus.Approved.name());//审批状态：Approved 3: 审批通过
                pAgentMerge.setMergeDate(df.format(new Date()));//合并日期（生效日期）
                logger.info("1.更新代理商合并申请状态为通过，已生效");
                pAgentMergeMapper.updateByPrimaryKeySelective(pAgentMerge);
                logger.info("2.更新审批流与业务对象");
                rel.setStatus(Status.STATUS_2.status);
                taskApprovalService.updateABusActRel(rel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("代理商合并申请审批流回调异常，activId：{}" + insid);
        }
        return AgentResult.ok();
    }

    /**
     * 审批退回，修改申请信息
     * @param pAgentMerge
     * @throws Exception
     */
    @Override
    public void editMergeRegect(PAgentMerge pAgentMerge) throws Exception {
        try {
            pAgentMergeMapper.updateByPrimaryKeySelective(pAgentMerge);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
    }


    @Override
    public List<PAgentMerge> selectBySubAgenId(String subAgentId) {
        return pAgentMergeMapper.selectBySubAgenId(subAgentId);
    }


}
