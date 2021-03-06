package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;

import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.profit.dao.PAgentMergeMapper;
import com.ryx.credit.profit.pojo.PAgentMerge;
import com.ryx.credit.profit.service.BusiPlatService;
import com.ryx.credit.profit.service.IProfitAgentMergerService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.dict.IdService;
import org.apache.poi.ss.usermodel.Table;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    private AgentQueryService agentQueryService;

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
        return pageInfo;
    }

    /**
     * 启动审批流
     * @param pAgentMerge
     * @param userId
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
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
        record.setAgentId(pAgentMerge.getMainAgentId());
        record.setAgentName(pAgentMerge.getMainAgentName());
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
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
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
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult approveFinish(String insid, String status) throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel = taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                PAgentMerge pAgentMerge = pAgentMergeMapper.selectByPrimaryKey(rel.getBusId());

                //一、更改代理商信息的名称
                Agent agent = agentService.getAgentById(pAgentMerge.getSubAgentId());
                agent.setAgName(pAgentMerge.getMainAgentName() + "(" + pAgentMerge.getSubAgentName() + ")");
                if (1 != agentService.updateByPrimaryKeySelective(agent)) {
                    throw new ProcessException("更新数据库异常！");
                }
                String sendMsg = "";
                BigDecimal isSuccess = Status.STATUS_1.status;

                //二、手刷改名接口(agentName、agentId、agentColinfo)
                String agentName = pAgentMerge.getMainAgentName() + "(" + pAgentMerge.getSubAgentName() + ")";//变更后名称=主名称+(自己名称)
                List<String> platId = new ArrayList<>();
                platId.add(pAgentMerge.getSubAgentId());
//                List<AgentBusInfo> agentBusInfoList = agentBusinfoService.agentBusInfoList(pAgentMerge.getSubAgentId());
//                AgentBusInfo busInfo = agentBusInfoList.get(0);
//                PayComp payComp = apaycompService.selectById(busInfo.getCloPayCompany());
//                AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(pAgentMerge.getSubAgentId(), busInfo.getId());
//                agentColinfo.setAccountId(payComp.getId());
//                agentColinfo.setAccountName(payComp.getComName());
                try {
                    AgentResult mpos = busiPlatService.mPos_updateAgName(agentName, platId, null);
                    sendMsg = mpos.getMsg();
                    if (!mpos.isOK()) {
                        isSuccess = Status.STATUS_0.status;
                    }
                } catch (Exception e) {
                    logger.error("======mPos_updateAgName", e);
                    e.printStackTrace();
                    sendMsg = "首刷通知业务系统失败！";
                }

                //三、POS改名接口(uniqueId、orgName、orgType)
                List<AgentBusInfo> list = pAgentMergeMapper.getByBusPlatform(pAgentMerge.getSubAgentId());//根据附代理商ID查询平台编号
                for (AgentBusInfo agentBusInfo : list) {
                    AgentNotifyVo agentNotifyVo = new AgentNotifyVo();
                    agentNotifyVo.setUniqueId(agentBusInfo.getId());//代理商AB码
                    agentNotifyVo.setOrgName(pAgentMerge.getMainAgentName() + "(" + pAgentMerge.getSubAgentName() + ")");//变更后名称=主名称+(自己名称)
                    if (agentBusInfo.getBusType().equals("2") || agentBusInfo.getBusType().equals("6")) {
                        agentNotifyVo.setOrgType("01");//机构类型：01-普通机构
                    } else {
                        agentNotifyVo.setOrgType("02");//机构类型：02-直签机构
                    }
                    AgentResult pos_Item = busiPlatService.pos_updateAgName(agentNotifyVo);
                    sendMsg = sendMsg + "_" + pos_Item.getMsg();
                    if (!pos_Item.isOK()) {
                        isSuccess = Status.STATUS_0.status;
                    }
                }
                pAgentMerge.setSynStatus(isSuccess);
                pAgentMerge.setSynMsg(sendMsg);
                pAgentMerge.setMergeStatus(AgStatus.Approved.name());//审批状态：Approved 3: 审批通过
                pAgentMerge.setMergeDate(df.format(new Date()));//合并日期（生效日期）
                logger.info("1.更新代理商合并申请状态为通过，已生效");
                pAgentMergeMapper.updateByPrimaryKeySelective(pAgentMerge);
                logger.info("2.更新审批流与业务对象");
                rel.setActivStatus(AgStatus.Approved.name());
                taskApprovalService.updateABusActRel(rel);

//                //主代理商同步附代理商收款账户，根据主AG码查询，插入附AG码
//                AgentColinfo ACByAgentId = agentQueryService.queryUserColinfo(pAgentMerge.getMainAgentId());
//                ACByAgentId.setId(idService.genId(TabId.a_agent_colinfo));
//                ACByAgentId.setAgentId(pAgentMerge.getSubAgentId());
//
//                //插入数据前需把之前的附代理商数据状态更改为无效
//                AgentColinfo subAgentId = agentQueryService.queryUserColinfo(pAgentMerge.getSubAgentId());
//                subAgentId.setStatus(Status.STATUS_0.status);
//                if (!agentColinfoService.updateAgentColinfo(subAgentId).isOK()) {
//                    throw new MessageException("更新收款信息失败！");
//                }
//
//                //根据附代理商的收款账户、收款账户名、支行联行号、总行联行号进行比对，判断此条数据是否重复插入
//                AgentColinfo isRepeat = agentQueryService.queryUserColinfo(pAgentMerge.getSubAgentId());
//                if (isRepeat.getCloRealname().equals(ACByAgentId.getCloRealname())
//                        || isRepeat.getAccountName().equals(ACByAgentId.getAccountName())
//                        || isRepeat.getBranchLineNum().equals(ACByAgentId.getBranchLineNum())
//                        || isRepeat.getAllLineNum().equals(ACByAgentId.getAllLineNum())) {
//                    logger.info("对比数据已存在...");
//                } else {
//                    //插入附代理商数据
//                    AgentResult result = agentColinfoService.saveAgentColinfo(ACByAgentId);
//                    if (!result.isOK()) {
//                        throw new MessageException("新增收款信息失败！");
//                    }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("代理商合并申请审批流回调异常，activId：{}" + insid);
        }
        return AgentResult.ok();
    }

    /**
     * 手动更改手刷、POS代理商名称
     * @param insid
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult updateAgentName(String insid) throws Exception{
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BusActRel busActRel = new BusActRel();
        busActRel.setActivId(insid);
        try {
            BusActRel rel = taskApprovalService.queryBusActRel(busActRel);
            if (rel != null) {
                PAgentMerge pAgentMerge = pAgentMergeMapper.selectByPrimaryKey(rel.getBusId());
                String sendMsg = "";
                BigDecimal isSuccess = Status.STATUS_1.status;

                //一、手刷改名接口(agentName、agentId、agentColinfo)
                String agentName = pAgentMerge.getMainAgentName() + "(" + pAgentMerge.getSubAgentName() + ")";
                List<String> platId = new ArrayList<>();
                platId.add(pAgentMerge.getSubAgentId());
//                List<AgentBusInfo> agentBusInfoList = agentBusinfoService.agentBusInfoList(pAgentMerge.getSubAgentId());
//                AgentBusInfo busInfo = agentBusInfoList.get(0);
//                PayComp payComp = apaycompService.selectById(busInfo.getCloPayCompany());
//                AgentColinfo agentColinfo = agentColinfoService.selectByAgentIdAndBusId(pAgentMerge.getSubAgentId(), busInfo.getId());
//                agentColinfo.setAccountId(payComp.getId());
//                agentColinfo.setAccountName(payComp.getComName());
                try {
                    AgentResult mpos = busiPlatService.mPos_updateAgName(agentName, platId, null);
                    sendMsg = mpos.getMsg();
                    if (!mpos.isOK()) {
                        isSuccess = Status.STATUS_0.status;
                    }
                } catch (Exception e) {
                    logger.error("======mPos_updateAgName", e);
                    e.printStackTrace();
                    sendMsg = "首刷通知业务系统失败！";
                }

                //二、POS改名接口(uniqueId、orgName、orgType)
                List<AgentBusInfo> list = pAgentMergeMapper.getByBusPlatform(pAgentMerge.getSubAgentId());
                for (AgentBusInfo agentBusInfo : list) {
                    AgentNotifyVo agentNotifyVo = new AgentNotifyVo();
                    agentNotifyVo.setUniqueId(agentBusInfo.getId());
                    agentNotifyVo.setOrgName(pAgentMerge.getMainAgentName() + "(" + pAgentMerge.getSubAgentName() + ")");
                    if (agentBusInfo.getBusType().equals("2") || agentBusInfo.getBusType().equals("6")) {
                        agentNotifyVo.setOrgType("01");
                    } else {
                        agentNotifyVo.setOrgType("02");
                    }
                    AgentResult pos_Item = busiPlatService.pos_updateAgName(agentNotifyVo);
                    sendMsg = sendMsg + "_" + pos_Item.getMsg();
                    if (!pos_Item.isOK()) {
                        isSuccess = Status.STATUS_0.status;
                    }
                }
                pAgentMerge.setSynStatus(isSuccess);
                pAgentMerge.setSynMsg(sendMsg);
                pAgentMerge.setMergeDate(df.format(new Date()));
                pAgentMergeMapper.updateByPrimaryKeySelective(pAgentMerge);
                taskApprovalService.updateABusActRel(rel);

//                //主代理商同步附代理商收款账户，根据主AG码查询，插入附AG码
//                AgentColinfo ACByAgentId = agentQueryService.queryUserColinfo(pAgentMerge.getMainAgentId());
//                ACByAgentId.setId(idService.genId(TabId.a_agent_colinfo));
//                ACByAgentId.setAgentId(pAgentMerge.getSubAgentId());
//
//                //插入数据前需把之前的附代理商数据状态更改为无效
//                AgentColinfo subAgentId = agentQueryService.queryUserColinfo(pAgentMerge.getSubAgentId());
//                subAgentId.setStatus(Status.STATUS_0.status);
//                if (!agentColinfoService.updateAgentColinfo(subAgentId).isOK()) {
//                    throw new MessageException("更新收款信息失败！");
//                }
//
//                //根据附代理商的收款账户、收款账户名、支行联行号、总行联行号进行比对，判断此条数据是否重复插入
//                AgentColinfo isRepeat = agentQueryService.queryUserColinfo(pAgentMerge.getSubAgentId());
//                if (isRepeat.getCloRealname().equals(ACByAgentId.getCloRealname())
//                        || isRepeat.getAccountName().equals(ACByAgentId.getAccountName())
//                        || isRepeat.getBranchLineNum().equals(ACByAgentId.getBranchLineNum())
//                        || isRepeat.getAllLineNum().equals(ACByAgentId.getAllLineNum())) {
//                    logger.info("对比数据已存在...");
//                } else {
//                    //插入附代理商数据
//                    AgentResult result = agentColinfoService.saveAgentColinfo(ACByAgentId);
//                    if (!result.isOK()) {
//                        throw new MessageException("新增收款信息失败！");
//                    }
//                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("代理商合并申请-更改代理商名称异常，activId：{}" + insid);
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
