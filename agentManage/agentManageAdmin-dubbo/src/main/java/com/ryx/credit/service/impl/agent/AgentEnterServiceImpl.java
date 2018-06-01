package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.BusActRelBusType;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.*;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cx on 2018/5/28.
 */
@Service("agentEnterService")
public class AgentEnterServiceImpl implements AgentEnterService {

    private static Logger logger = LoggerFactory.getLogger(AgentEnterServiceImpl.class);

    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentContractService agentContractService;
    @Autowired
    private AccountPaidItemService accountPaidItemService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private BusActRelMapper busActRelMapper;


    /**
     * 商户入网
     * @param agentVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO agentEnterIn(AgentVo agentVo) throws ProcessException {
        try {
            Agent agent = agentService.insertAgent(agentVo.getAgent(), agentVo.getAgentTableFile());
            agentVo.setAgent(agent);
            for (AgentContractVo item : agentVo.getContractVoList()) {
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                item.setCloReviewStatus(AgStatus.Create.status);
                agentContractService.insertAgentContract(item, item.getContractTableFile());
            }
            for (CapitalVo item : agentVo.getCapitalVoList()) {
                item.setcAgentId(agent.getId());
                item.setcUser(agent.getcUser());
                AgentResult res = accountPaidItemService.insertAccountPaid(item, item.getCapitalTableFile(), agentVo.getAgent().getcUser());
                if(!res.isOK()){
                    throw new ProcessException("添加交款项异常");
                }
            }
            for (AgentColinfoVo item : agentVo.getColinfoVoList()) {
                item.setAgentId(agent.getId());
                item.setcUser(agent.getcUser());
                item.setCloReviewStatus(AgStatus.Create.status);
                agentColinfoService.agentColinfoInsert(item,item.getColinfoTableFile());
            }
            for (AgentBusInfoVo item : agentVo.getBusInfoVoList()) {
                item.setcUser(agent.getcUser());
                item.setAgentId(agent.getId());
                item.setCloReviewStatus(AgStatus.Create.status);
                agentBusinfoService.agentBusInfoInsert(item);
            }
            return ResultVO.success(agentVo);
        }catch (Exception e){
            e.printStackTrace();
            throw new ProcessException("代理商信息录入失败");
        }
    }


    /**
     * 启动代理商审批
     * @param agentId
     * @param cuser
     * @return
     * @throws ProcessException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO startAgentEnterActivity(String agentId,String cuser)throws ProcessException{

        //检查是否有审批中的代理商新
        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(agentId).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        if(busActRelMapper.selectByExample(example).size()>0){
            logger.info("代理商审批,禁止重复提交审批{}:{}",agentId,cuser);
            return ResultVO.fail("代理商审批中，禁止重复提交审批");
        }

        Agent agent = agentService.getAgentById(agentId);
        if(agent.getAgStatus().equals(AgStatus.Approving.name())){
            logger.info("代理商审批,禁止重复提交审批{}:{}",agentId,cuser);
            return ResultVO.fail("代理商审批中，禁止重复提交审批");
        }
        if(!agent.getStatus().equals(Status.STATUS_1.status)){
            logger.info("代理商审批中,代理商信息已失效{}:{}",agentId,cuser);
            return ResultVO.fail("代理商信息已失效");
        }


        //更新代理商审批中
        agent.setAgStatus(AgStatus.Approving.name());
        if(1!=agentService.updateAgent(agent)){
            logger.info("代理商审批，更新代理商基本信息失败{}:{}",agentId,cuser);
            throw new ProcessException("启动审批异常，更新代理商基本信息失败");
        }

        //获取代理商有效的业务
        List<AgentBusInfo> aginfo = agentBusinfoService.agentBusInfoList(agent.getId());
        for (AgentBusInfo agentBusInfo : aginfo) {
            agentBusInfo.setcUtime(Calendar.getInstance().getTime());
            agentBusInfo.setCloReviewStatus(AgStatus.Approving.status);
            if(agentBusinfoService.updateAgentBusInfo(agentBusInfo)!=1){
                logger.info("代理商审批，更新业务本信息失败{}:{}",agentId,cuser);
                throw new ProcessException("启动审批异常，更新业务本信息失败");
            }
        }
        //代理商有效新建的合同
        List<AgentContract> ag  = agentContractService.queryAgentContract(agentId,null,AgStatus.Create.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Approving.status);
            if(1!=agentContractService.update(contract)){
                logger.info("代理商审批，合同状态更新失败{}:{}",agentId,cuser);
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(agentId,null,AgStatus.Create.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Approving.status);
            if(1!=agentColinfoService.update(agentColinfo)){
                logger.info("代理商审批，合同状态更新失败{}:{}",agentId,cuser);
                throw new ProcessException("合同状态更新失败");
            }
        }

        //启动审批
        String proce = activityService.createDeloyFlow(null, AppConfig.getProperty("agent_net_in_activity"),null,null);
        if(proce==null){
            logger.info("代理商审批，审批流启动失败{}:{}",agentId,cuser);
            throw new ProcessException("审批流启动失败!");
        }

        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(agentId);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.Agent.name());
        record.setActivStatus(AgStatus.Approving.name());
        if(1!=busActRelMapper.insertSelective(record)){
            logger.info("代理商审批，启动审批异常，添加审批关系失败{}:{}",agentId,proce);
        }

        return ResultVO.success(null);
    }


    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public ResultVO startAgentBusiEnterActivity(String busid,String cuser)throws ProcessException{
        AgentBusInfo abus = agentBusinfoService.getById(busid);
        if(abus==null){
            logger.info("代理商信息审批中,业务信息未找到{}:{}",busid,cuser);
            return ResultVO.fail("业务信息未找到");
        }
        //检查是否有审批中的代理商新
        Agent agent = agentService.getAgentById(abus.getAgentId());
        if(agent.getAgStatus().equals(AgStatus.Approving.name())){
            logger.info("代理商信息审批中,禁止启动业务审批{}:{}",busid,cuser);
            return ResultVO.fail("代理商信息审批中,禁止启动业务审批");
        }
        if(!agent.getAgStatus().equals(AgStatus.Approved.name())){
            logger.info("代理商信息未审批完成,禁止启动业务审批{}:{}",busid,cuser);
            return ResultVO.fail("代理商信息未审批完成,禁止启动业务审批");
        }
        if(!agent.getStatus().equals(Status.STATUS_1.status)){
            logger.info("代理商信息已失效{}:{}",busid,cuser);
            return ResultVO.fail("代理商信息已失效");
        }

        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(abus.getId()).andBusTypeEqualTo(BusActRelBusType.Business.name()).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        if(busActRelMapper.selectByExample(example).size()>0){
            logger.info("代理商审批中，禁止重复提交审批{}:{}",busid,cuser);
            return ResultVO.fail("代理商审批中，禁止重复提交审批");
        }

        //获取代理商有效的业务
        abus.setcUtime(Calendar.getInstance().getTime());
        abus.setCloReviewStatus(AgStatus.Approving.status);
        if(agentBusinfoService.updateAgentBusInfo(abus)!=1){
            logger.info("代理商业务启动审批异常，更新业务本信息失败{}:{}",busid,cuser);
            throw new ProcessException("代理商业务启动审批异常，更新业务本信息失败");
        }


        //代理商有效新建的合同
        List<AgentContract> ag  = agentContractService.queryAgentContract(abus.getAgentId(),null,AgStatus.Create.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Approving.status);
            if(1!=agentContractService.update(contract)){
                logger.info("代理商业务启动审批异常，合同状态更新失败{}:{}",busid,cuser);
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(abus.getAgentId(),null,AgStatus.Create.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Approving.status);
            if(1!=agentColinfoService.update(agentColinfo)){
                logger.info("代理商业务启动审批异常，收款账户状态更新失败{}:{}",busid,cuser);
                throw new ProcessException("收款账户状态更新失败");
            }
        }


        //启动审批
        String proce = activityService.createDeloyFlow(null, AppConfig.getProperty("agent_net_in_activity"),null,null);
        if(proce==null){
            logger.info("代理商业务启动审批异常，审批流启动失败{}:{}",busid,cuser);
            throw new ProcessException("审批流启动失败!");
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(abus.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.Business.name());
        record.setActivStatus(AgStatus.Approving.name());
        if(1!=busActRelMapper.insertSelective(record)){
            logger.info("代理商业务启动审批异常，添加审批关系失败{}:{}",record.getBusId(),proce);
        }
        return ResultVO.success(null);
    }


    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult completeTaskEnterActivity(AgentVo agentVo)throws ProcessException{

        AgentResult result = new AgentResult(500,"系统异常","");
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("rs",agentVo.getApprovalResult());
        reqMap.put("approvalOpinion",agentVo.getApprovalOpinion());

        Map resultMap = activityService.completeTask(agentVo.getTaskId(), reqMap);
        if(resultMap==null){
            return result;
        }
        return AgentResult.ok(resultMap);
    }


    @Override
    public ResultVO completeProcessing(String processingId, String processingStatus) throws ProcessException {
        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(processingId).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list =  busActRelMapper.selectByExample(example);
        if(list.size()!=1){
            logger.info("审批任务结束{}{}，未找到审批中的审批和数据关系",processingId,processingStatus);
        }
        BusActRel rel = list.get(0);
        if(rel.getBusType().equals(BusActRelBusType.Business.name())) {
            //审批通过
            if (AgStatus.Approved.name().equals(processingStatus)) {
               return  processingBusAproveApproved(rel,processingId,rel.getBusId());
            }
            //审批拒绝
            if (AgStatus.Refuse.name().equals(processingStatus)) {
                return  processingBusAproveRefuse(rel,processingId,rel.getBusId());
            }
        }
        if(rel.getBusType().equals(BusActRelBusType.Agent.name())) {
            //审批通过
            if (AgStatus.Approved.name().equals(processingStatus)) {
                return  processingAgentApproved(rel,processingId,rel.getBusId());
            }
            //审批拒绝
            if (AgStatus.Refuse.name().equals(processingStatus)) {
                return  processingAgentRefuse(rel,processingId,rel.getBusId());
            }
        }
        return ResultVO.success("");
    }


    /**
     * 业务审批同意
     * @param processingId
     * @param busId
     * @return
     */
    private ResultVO processingBusAproveApproved(BusActRel rel,String processingId, String busId ){
        rel.setActivStatus(AgStatus.Approved.name());
        if(1!=busActRelMapper.updateByPrimaryKeySelective(rel)){
            logger.info("代理商审批通过，更新BusActRel失败{}:{}",processingId,rel.getBusId());
        }

        AgentBusInfo bus =  agentBusinfoService.getById(busId);
        bus.setcUtime(Calendar.getInstance().getTime());
        bus.setCloReviewStatus(AgStatus.Approved.status);
        if(agentBusinfoService.updateAgentBusInfo(bus)!=1){
            logger.info("代理商审批通过，更新业务本信息失败{}:{}",processingId,bus.getId());
            throw new ProcessException("代理商审批通过，更新业务本信息失败");
        }
        //代理商有效新建的合同
        List<AgentContract> ag  = agentContractService.queryAgentContract(bus.getAgentId(),null,AgStatus.Approving.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Approved.status);
            if(1!=agentContractService.update(contract)){
                logger.info("代理商审批通过，合同状态更新失败{}:{}",processingId,contract.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(bus.getAgentId(),null,AgStatus.Approving.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Approved.status);
            if(1!=agentColinfoService.update(agentColinfo)){
                logger.info("代理商审批通过，收款状态更新失败{}:{}",processingId,agentColinfo.getId());
                throw new ProcessException("收款状态更新失败");
            }
        }

        return ResultVO.success(null);
    }

    /**
     * 业务审批拒绝
     * @param processingId
     * @param busId
     * @return
     */
    private ResultVO processingBusAproveRefuse(BusActRel rel,String processingId, String busId ){
        rel.setActivStatus(AgStatus.Refuse.name());
        if(1!=busActRelMapper.updateByPrimaryKeySelective(rel)){
            logger.info("代理商审批拒绝，更新BusActRel失败{}:{}",processingId,rel.getBusId());
        }

        AgentBusInfo bus =  agentBusinfoService.getById(busId);
        bus.setcUtime(Calendar.getInstance().getTime());
        bus.setCloReviewStatus(AgStatus.Refuse.status);
        if(agentBusinfoService.updateAgentBusInfo(bus)!=1){
            logger.info("代理商审批拒绝，更新业务本信息失败{}:{}",processingId,bus.getId());
            throw new ProcessException("代理商审批通过，更新业务本信息失败");
        }

        //代理商有效新建的合同
        List<AgentContract> ag  = agentContractService.queryAgentContract(bus.getAgentId(),null,AgStatus.Approving.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Refuse.status);
            if(1!=agentContractService.update(contract)){
                logger.info("代理商审批拒绝，合同状态更新失败{}:{}",processingId,contract.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(bus.getAgentId(),null,AgStatus.Approving.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Refuse.status);
            if(1!=agentColinfoService.update(agentColinfo)){
                logger.info("代理商审批拒绝，收款状态更新失败{}:{}",processingId,agentColinfo.getId());
                throw new ProcessException("收款状态更新失败");
            }
        }

        return ResultVO.success(null);
    }

    /**
     * 代理商业务审批同意
     * @param processingId
     * @param busId
     * @return
     */
    private ResultVO processingAgentApproved(BusActRel rel,String processingId, String busId ){

        rel.setActivStatus(AgStatus.Approved.name());
        if(1!=busActRelMapper.updateByPrimaryKeySelective(rel)){
            logger.info("代理商审批通过，更新BusActRel失败{}:{}",processingId,rel.getBusId());
        }

        Agent agent = agentService.getAgentById(busId);
        agent.setAgStatus(AgStatus.Approved.name());
        if(1!=agentService.updateAgent(agent)){
            logger.info("代理商审批通过，代理商信息失败{}:{}",processingId,agent.getId());
        }

        //获取代理商有效的业务
        List<AgentBusInfo> aginfo = agentBusinfoService.agentBusInfoList(agent.getId(),null,AgStatus.Approving.status);
        for (AgentBusInfo agentBusInfo : aginfo) {
            agentBusInfo.setcUtime(Calendar.getInstance().getTime());
            agentBusInfo.setCloReviewStatus(AgStatus.Approved.status);
            if(agentBusinfoService.updateAgentBusInfo(agentBusInfo)!=1){
                logger.info("代理商审批通过，更新业务本信息失败{}:{}",processingId,agentBusInfo.getId());
                throw new ProcessException("代理商审批通过，更新业务本信息失败");
            }
        }

        //代理商有效新建的合同
        List<AgentContract> ag  = agentContractService.queryAgentContract(agent.getId(),null,AgStatus.Approving.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Approved.status);
            if(1!=agentContractService.update(contract)){
                logger.info("代理商审批通过，合同状态更新失败{}:{}",processingId,contract.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(agent.getId(),null,AgStatus.Approving.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Approved.status);
            if(1!=agentColinfoService.update(agentColinfo)){
                logger.info("代理商审批通过，收款状态更新失败{}:{}",processingId,agentColinfo.getId());
                throw new ProcessException("收款状态更新失败");
            }
        }

        return ResultVO.success(null);
    }

    /**
     * 代理商业务审批拒绝
     * @param processingId
     * @param busId
     * @return
     */
    private ResultVO processingAgentRefuse(BusActRel rel,String processingId, String busId ){
        rel.setActivStatus(AgStatus.Refuse.name());
        if(1!=busActRelMapper.updateByPrimaryKeySelective(rel)){
            logger.info("代理商审批拒绝，更新BusActRel失败{}:{}",processingId,rel.getBusId());
        }

        Agent agent = agentService.getAgentById(busId);
        agent.setAgStatus(AgStatus.Refuse.name());
        if(1!=agentService.updateAgent(agent)){
            logger.info("代理商审批拒绝，代理商信息失败{}:{}",processingId,agent.getId());
        }

        //获取代理商有效的业务
        List<AgentBusInfo> aginfo = agentBusinfoService.agentBusInfoList(agent.getId(),null,AgStatus.Approving.status);
        for (AgentBusInfo agentBusInfo : aginfo) {
            agentBusInfo.setcUtime(Calendar.getInstance().getTime());
            agentBusInfo.setCloReviewStatus(AgStatus.Refuse.status);
            if(agentBusinfoService.updateAgentBusInfo(agentBusInfo)!=1){
                logger.info("代理商审批拒绝，更新业务本信息失败{}:{}",processingId,agentBusInfo.getId());
                throw new ProcessException("代理商审批通过，更新业务本信息失败");
            }
        }

        //代理商有效新建的合同
        List<AgentContract> ag  = agentContractService.queryAgentContract(agent.getId(),null,AgStatus.Approving.status);
        for (AgentContract contract : ag) {
            contract.setCloReviewStatus(AgStatus.Refuse.status);
            if(1!=agentContractService.update(contract)){
                logger.info("代理商审批拒绝，合同状态更新失败{}:{}",processingId,contract.getId());
                throw new ProcessException("合同状态更新失败");
            }
        }

        //代理商有效的新建的收款账户
        List<AgentColinfo> clolist = agentColinfoService.queryAgentColinfoService(agent.getId(),null,AgStatus.Approving.status);
        for (AgentColinfo agentColinfo : clolist) {
            agentColinfo.setCloReviewStatus(AgStatus.Refuse.status);
            if(1!=agentColinfoService.update(agentColinfo)){
                logger.info("代理商审批拒绝，收款状态更新失败{}:{}",processingId,agentColinfo.getId());
                throw new ProcessException("收款状态更新失败");
            }
        }

        return ResultVO.success(null);
    }



}
