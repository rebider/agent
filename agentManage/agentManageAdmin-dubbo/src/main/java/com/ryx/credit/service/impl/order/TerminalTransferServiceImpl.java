package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.TerminalTransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/***
 * @Author liudh
 * @Description //TODO 
 * @Date 2018/12/20 10:57
 * @Param
 * @return
 **/
@Service("terminalTransferService")
public class TerminalTransferServiceImpl implements TerminalTransferService {

    private static Logger log = LoggerFactory.getLogger(TerminalTransferServiceImpl.class);
    @Autowired
    private TerminalTransferMapper terminalTransferMapper;
    @Autowired
    private TerminalTransferDetailMapper terminalTransferDetailMapper;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private OLogisticsDetailMapper logisticsDetailMapper;
    @Autowired
    private OSubOrderMapper subOrderMapper;
    @Autowired
    private OSubOrderActivityMapper subOrderActivityMapper;



    @Override
    public PageInfo terminalTransferList(TerminalTransfer terminalTransfer, Page page) {

        TerminalTransferExample example = new TerminalTransferExample();
        TerminalTransferExample.Criteria criteria = example.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        example.setPage(page);
        List<TerminalTransfer> terminalTransferList = terminalTransferMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(terminalTransferList);
        pageInfo.setTotal((int)terminalTransferMapper.countByExample(example));
        return pageInfo;
    }

    @Override
    public PageInfo terminalTransferDetailList(TerminalTransferDetail terminalTransferDetail, Page page) {

        Map<String,Object> reqMap = new HashMap<>();
        reqMap.put("status",Status.STATUS_1.status);
        List<Map<String,Object>> terminalTransferList = terminalTransferDetailMapper.selectTerminalTransferDetailList(reqMap,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(terminalTransferList);
        pageInfo.setTotal(terminalTransferDetailMapper.selectTerminalTransferDetailCount(reqMap));
        return pageInfo;
    }

    /**
     *
     * saveFlag 1暂存2提交审批
     * @param terminalTransfer
     * @param terminalTransferDetailList
     * @param cuser
     * @param saveFlag
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult saveTerminalTransfer(TerminalTransfer terminalTransfer,List<TerminalTransferDetail> terminalTransferDetailList, String cuser,String agentId,String saveFlag)throws Exception{

        if (StringUtils.isBlank(cuser) || StringUtils.isBlank(agentId)) {
            log.info("终端划拨提交审批,操作用户为空:{}", cuser);
            return AgentResult.fail("终端划拨审批中，操作用户为空");
        }
        try {
            if(saveFlag.equals(SaveFlag.TJSP.getValue())){
                terminalTransfer.setReviewStatus(AgStatus.Approving.status);
            }else{
                terminalTransfer.setReviewStatus(AgStatus.Create.status);
            }
            String terminalTransferId = idService.genId(TabId.O_TERMINAL_TRANSFER);
            terminalTransfer.setId(terminalTransferId);
            Date date = new Date();
            terminalTransfer.setcTime(date);
            terminalTransfer.setuTime(date);
            terminalTransfer.setcUser(cuser);
            terminalTransfer.setuUser(cuser);
            terminalTransfer.setStatus(Status.STATUS_1.status);
            terminalTransfer.setVersion(Status.STATUS_1.status);
            int i = terminalTransferMapper.insert(terminalTransfer);
            if (1 != i) {
                log.info("终端划拨提交审批，更新订单基本信息失败:{}", cuser);
                throw new MessageException("终端划拨提交审批，更新终端划拨基本信息失败");
            }
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
                if(StringUtils.isBlank(terminalTransferDetail.getGoalOrgId()) || StringUtils.isBlank(terminalTransferDetail.getOriginalOrgId())){
                    throw new MessageException("缺少参数");
                }
                Map<String, Object> reqParam = new HashMap<>();
                reqParam.put("snBegin",terminalTransferDetail.getSnBeginNum());
                reqParam.put("snEnd",terminalTransferDetail.getSnEndNum());
                reqParam.put("status",OLogisticsDetailStatus.STATUS_FH.code);
                ArrayList<Object> recordStatusList = new ArrayList<>();
                recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                reqParam.put("recordStatusList",recordStatusList);
                List<Map<String,Object>> logisticsDetailList = logisticsDetailMapper.queryCompensateLList(reqParam);
//                if(logisticsDetailList.size()==0){
//                    throw new ProcessException("sn号在审批中或已退货");
//                }
//                BigDecimal proNumSum = new BigDecimal(0);
//                for (Map<String, Object> stringObjectMap : logisticsDetailList) {
//                    proNumSum = proNumSum.add(new BigDecimal(stringObjectMap.get("PRO_NUM").toString()));
//                }
//                if(!String.valueOf(proNumSum).equals(terminalTransferDetail.getSnCount())){
//                    throw new ProcessException("sn号数量不匹配");
//                }
//                Set<String> proComSet = new HashSet<>();
//                Set<String> proModelSet = new HashSet<>();
//                for (Map<String, Object> logisticsDetail : logisticsDetailList) {
//                    String orderId = String.valueOf(logisticsDetail.get("ORDER_ID"));
//                    String proId = String.valueOf(logisticsDetail.get("PRO_ID"));
//                    String activityId = String.valueOf(logisticsDetail.get("ACTIVITY_ID"));
//                    OSubOrderExample oSubOrderExample = new OSubOrderExample();
//                    OSubOrderExample.Criteria criteria = oSubOrderExample.createCriteria();
//                    criteria.andStatusEqualTo(Status.STATUS_1.status);
//                    criteria.andOrderIdEqualTo(orderId);
//                    criteria.andProIdEqualTo(proId);
//                    List<OSubOrder> oSubOrders = subOrderMapper.selectByExample(oSubOrderExample);
//                    if(oSubOrders.size()!=1){
//                        throw new ProcessException("查询采购单数据有误");
//                    }
//                    OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
//                    OSubOrderActivityExample.Criteria criteria1 = oSubOrderActivityExample.createCriteria();
//                    criteria1.andSubOrderIdEqualTo(oSubOrders.get(0).getId());
//                    criteria1.andActivityIdEqualTo(activityId);
//                    criteria1.andStatusEqualTo(Status.STATUS_1.status);
//                    List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
//                    if(oSubOrderActivities.size()!=1){
//                        throw new ProcessException("查询活动快照数据有误");
//                    }
//                    OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
//                    proComSet.add(oSubOrderActivity.getVender());
//                    proModelSet.add(oSubOrderActivity.getProModel());
//                }
//                if(proComSet.size()!=1){
//                    throw new ProcessException(terminalTransferDetail.getSnBeginNum()+"到"+terminalTransferDetail.getSnEndNum()+"不是同一厂商");
//                }
//                if(proModelSet.size()!=1){
//                    throw new ProcessException(terminalTransferDetail.getSnBeginNum()+"到"+terminalTransferDetail.getSnEndNum()+"不是同一型号");
//                }
//
//                AgentBusInfoExample originalExample = new AgentBusInfoExample();
//                AgentBusInfoExample.Criteria originalCriteria = originalExample.createCriteria();
//                originalCriteria.andStatusEqualTo(Status.STATUS_1.status);
//                originalCriteria.andBusNumEqualTo(terminalTransferDetail.getOriginalOrgId());
//                List<AgentBusInfo> originalAgentBusInfos = agentBusInfoMapper.selectByExample(originalExample);
//                if(originalAgentBusInfos.size()!=1){
//                    throw new MessageException("原机构数据有误");
//                }
//                AgentBusInfoExample goalExample = new AgentBusInfoExample();
//                AgentBusInfoExample.Criteria goalCriteria = goalExample.createCriteria();
//                goalCriteria.andStatusEqualTo(Status.STATUS_1.status);
//                goalCriteria.andBusNumEqualTo(terminalTransferDetail.getOriginalOrgId());
//                List<AgentBusInfo> goalBusInfos = agentBusInfoMapper.selectByExample(goalExample);
//                if(goalBusInfos.size()!=1){
//                    throw new MessageException("原机构数据有误");
//                }
//                AgentBusInfo originalAgentBusInfo = originalAgentBusInfos.get(0);
//                AgentBusInfo goalAgentBusInfo = goalBusInfos.get(0);

                terminalTransferDetail.setId(idService.genId(TabId.O_TERMINAL_TRANSFER_DETAIL));
                terminalTransferDetail.setTerminalTransferId(terminalTransferId);
                terminalTransferDetail.setcUser(cuser);
                terminalTransferDetail.setuUser(cuser);
                terminalTransferDetail.setcTime(date);
                terminalTransferDetail.setuTime(date);
                terminalTransferDetail.setStatus(Status.STATUS_1.status);
                terminalTransferDetail.setVersion(Status.STATUS_1.status);
                terminalTransferDetail.setAgentId(agentId);
                terminalTransferDetail.setAdjustStatus(AdjustStatus.WTZ.getValue());
//                terminalTransferDetail.setOriginalBusId(originalAgentBusInfo.getId());
//                terminalTransferDetail.setGoalBusId(goalAgentBusInfo.getId());
//                terminalTransferDetail.setProCom(proComSet.iterator().next());
//                terminalTransferDetail.setProModel(proModelSet.iterator().next());
                terminalTransferDetailMapper.insert(terminalTransferDetail);
            }
            if(saveFlag.equals(SaveFlag.TJSP.getValue())){
                startTerminalTransferActivity(terminalTransferId,cuser,agentId);
            }
            return AgentResult.ok();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("新增失败");
        }
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startTerminalTransferActivity(String id, String cuser, String agentId) throws Exception {

        //启动审批
        String proce = activityService.createDeloyFlow(null, BusActRelBusType.agentTerminal.name(), null, null, null);
        if (proce == null) {
            log.info("终端划拨提交审批，审批流启动失败{}:{}", id, cuser);
            throw new MessageException("审批流启动失败!");
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(id);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.agentTerminal.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agentId);
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        if(null!=agent)
            record.setAgentName(agent.getAgName());
        if (1 != busActRelMapper.insertSelective(record)) {
            log.info("订单提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("审批流启动失败:添加审批关系失败");
        }
        return AgentResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTerminalTransferTask(AgentVo agentVo, String userId) throws Exception{
        try {
            if(agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())){

            }
            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo,userId);
            if(!result.isOK()){
                log.error(result.getMsg());
                throw new ProcessException("工作流处理任务异常");
            }
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException("catch工作流处理任务异常!");
        }
        return AgentResult.ok();
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressTerminalTransferActivity(String proIns, BigDecimal agStatus)throws Exception{

        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andStatusEqualTo(Status.STATUS_1.status).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            log.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            throw new MessageException("审批和数据关系有误");
        }
        BusActRel busActRel = list.get(0);
        TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(busActRel.getBusId());
        terminalTransfer.setReviewStatus(agStatus);
        terminalTransfer.setuTime(new Date());
        int i = terminalTransferMapper.updateByPrimaryKeySelective(terminalTransfer);
        if(i!=1) {
            log.info("审批任务结束{}{}，终端划拨更新失败1", proIns, agStatus);
            throw new MessageException("终端划拨更新失败");
        }
        busActRel.setActivStatus(AgStatus.getAgStatusString(agStatus));
        int j = busActRelMapper.updateByPrimaryKey(busActRel);
        if(j!=1) {
            log.info("审批任务结束{}{}，终端划拨更新失败2", proIns, agStatus);
            throw new MessageException("终端划拨更新失败");
        }
        return AgentResult.ok();
    }

    /**
     * 根据id查询带明细
     * @param terminalTransferId
     * @return
     */
    @Override
    public TerminalTransfer queryTerminalTransfer(String terminalTransferId){
        if(StringUtils.isBlank(terminalTransferId)){
            return null;
        }
        TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(terminalTransferId);
        if(null==terminalTransfer){
            return null;
        }
        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andTerminalTransferIdEqualTo(terminalTransfer.getId());
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
        terminalTransfer.setTerminalTransferDetailList(terminalTransferDetails);
        return terminalTransfer;
    }

}
