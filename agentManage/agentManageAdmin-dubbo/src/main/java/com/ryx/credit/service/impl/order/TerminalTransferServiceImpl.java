package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.IDUtils;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
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
    @Autowired
    private RedisService redisService;
    @Autowired
    private IUserService userService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;


    @Override
    public PageInfo terminalTransferList(TerminalTransfer terminalTransfer, Page page, String agName) {

        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status",Status.STATUS_1.status);
        if(StringUtils.isNotBlank(terminalTransfer.getAgentId())){
            reqMap.put("agentId",terminalTransfer.getAgentId());
        }
        if(StringUtils.isNotBlank(agName)){
            reqMap.put("agName",agName);
        }
        if(StringUtils.isNotBlank(terminalTransfer.getId())){
            reqMap.put("id",terminalTransfer.getId());
        }
        if(null!=terminalTransfer.getReviewStatus()){
            reqMap.put("reviewStatus",terminalTransfer.getReviewStatus());
        }
        List<Map<String,Object>> terminalTransferList = terminalTransferMapper.selectTerminalTransferList(reqMap,page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(terminalTransferList);
        pageInfo.setTotal(terminalTransferMapper.selectTerminalTransferCount(reqMap));
        return pageInfo;
    }

    @Override
    public PageInfo terminalTransferDetailList(TerminalTransferDetail terminalTransferDetail, Page page, String agName) {

        Map<String,Object> reqMap = new HashMap<>();
        reqMap.put("status",Status.STATUS_1.status);
        if(StringUtils.isNotBlank(terminalTransferDetail.getTerminalTransferId())){
            reqMap.put("terminalTransferId",terminalTransferDetail.getTerminalTransferId());
        }
        if(StringUtils.isNotBlank(terminalTransferDetail.getAgentId())){
            reqMap.put("agentId",terminalTransferDetail.getAgentId());
        }
        if(StringUtils.isNotBlank(terminalTransferDetail.getSnBeginNum())){
            reqMap.put("snBeginNum",terminalTransferDetail.getSnBeginNum());
        }
        if(StringUtils.isNotBlank(terminalTransferDetail.getSnEndNum())){
            reqMap.put("snEndNum",terminalTransferDetail.getSnEndNum());
        }
        if(null!=terminalTransferDetail.getAdjustStatus()){
            reqMap.put("adjustStatus",terminalTransferDetail.getAdjustStatus());
        }
        if(StringUtils.isNotBlank(agName)){
            reqMap.put("agName",agName);
        }
        if(StringUtils.isNotBlank(terminalTransferDetail.getGoalOrgId())){
            reqMap.put("goalOrgId",terminalTransferDetail.getGoalOrgId());
        }
        if(StringUtils.isNotBlank(terminalTransferDetail.getGoalOrgName())){
            reqMap.put("goalOrgName",terminalTransferDetail.getGoalOrgName());
        }
        if(StringUtils.isNotBlank(terminalTransferDetail.getOriginalOrgId())){
            reqMap.put("originalOrgId",terminalTransferDetail.getOriginalOrgId());
        }
        if(StringUtils.isNotBlank(terminalTransferDetail.getOriginalOrgName())){
            reqMap.put("originalOrgName",terminalTransferDetail.getOriginalOrgName());
        }
        List<Map<String,Object>> terminalTransferList = null;
        if(page==null){
            terminalTransferList = terminalTransferDetailMapper.selectTerminalTransferDetailList(reqMap,page);
        }else{
            terminalTransferList = terminalTransferDetailMapper.exprotTerminalTransferDetails(reqMap);
        }
        for (Map<String, Object> queryMap : terminalTransferList) {
            BigDecimal adjustStatus = new BigDecimal(queryMap.get("ADJUST_STATUS").toString());
            queryMap.put("ADJUST_STATUS_MSG",AdjustStatus.getContentByValue(adjustStatus));
        }
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
            return AgentResult.fail("终端划拨，操作用户为空");
        }
        try {
            if(saveFlag.equals(SaveFlag.TJSP.getValue())){
                terminalTransfer.setReviewStatus(AgStatus.Approving.status);
            }else{
                terminalTransfer.setReviewStatus(AgStatus.Create.status);
            }
            String terminalTransferId = idService.genId(TabId.O_TERMINAL_TRANSFER);
            terminalTransfer.setId(terminalTransferId);
            terminalTransfer.setAgentId(agentId);
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
            if(terminalTransferDetailList.size()==0){
                throw new MessageException("请填写明细最少一条");
            }
            for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
                Map<String, String> resultMap = saveOrEditVerify(terminalTransferDetail, agentId);
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
                terminalTransferDetail.setGoalBusId(resultMap.get("goalBusId"));
                terminalTransferDetail.setOriginalBusId(resultMap.get("originalBusId"));
                terminalTransferDetail.setProCom(resultMap.get("proCom"));
                terminalTransferDetail.setProModel(resultMap.get("proModel"));
                terminalTransferDetailMapper.insert(terminalTransferDetail);
            }
            if(saveFlag.equals(SaveFlag.TJSP.getValue())){
                startTerminalTransferActivity(terminalTransferId,cuser,agentId,true);
            }
            return AgentResult.ok();
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("新增失败");
        }
    }

    /**
     * 保存修改校验
     * @param terminalTransferDetail
     * @param agentId
     * @return
     * @throws Exception
     */
    private Map<String,String> saveOrEditVerify(TerminalTransferDetail terminalTransferDetail,String agentId)throws Exception{

        Map<String, String> resultMap = new HashMap<>();
        if(StringUtils.isBlank(terminalTransferDetail.getGoalOrgId()) || StringUtils.isBlank(terminalTransferDetail.getOriginalOrgId())){
            throw new MessageException("缺少参数");
        }
        //验证目标代理商是否存在
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria agentBusInfoCriteria = agentBusInfoExample.createCriteria();
        agentBusInfoCriteria.andStatusEqualTo(Status.STATUS_1.status);
        agentBusInfoCriteria.andBusStatusEqualTo(Status.STATUS_1.status);
        agentBusInfoCriteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
        agentBusInfoCriteria.andBusNumEqualTo(terminalTransferDetail.getGoalOrgId());
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        if(agentBusInfos.size()!=1){
            throw new MessageException("目标机构ID(不存在或存在多个或审批未通过)");
        }
        AgentBusInfo goalAgentBusInfo = agentBusInfos.get(0);
        Agent agent = agentMapper.selectByPrimaryKey(goalAgentBusInfo.getAgentId());
        if(!agent.getAgName().equals(terminalTransferDetail.getGoalOrgName())){
            throw new MessageException("目标机构ID和名称不匹配");
        }
        //查询目标机构是否是当前代理商下的
        AgentBusInfoExample agentExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria agentCriteria = agentExample.createCriteria();
        agentCriteria.andStatusEqualTo(Status.STATUS_1.status);
        agentCriteria.andBusStatusEqualTo(Status.STATUS_1.status);
        agentCriteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
        agentCriteria.andAgentIdEqualTo(agentId);
        List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(agentExample);
        Boolean isSub = false; //是否是下级
        here:
        for (AgentBusInfo busInfo : agentBusInfoList) {
            List<AgentBusInfo> childLevelBusInfos = agentBusinfoService.queryChildLevelByBusNum(null, busInfo.getBusPlatform(), busInfo.getBusNum());
            for (AgentBusInfo childLevelBusInfo : childLevelBusInfos) {
                if(childLevelBusInfo.getBusNum().equals(terminalTransferDetail.getGoalOrgId())){
                    isSub = true;
                }
                break here;
            }
        }
        if(!isSub){
            throw new MessageException("目标机构不是当前代理商下级");
        }
        Map<String, Object> reqParam = new HashMap<>();
        reqParam.put("snBegin",terminalTransferDetail.getSnBeginNum());
        reqParam.put("snEnd",terminalTransferDetail.getSnEndNum());
        reqParam.put("status",OLogisticsDetailStatus.STATUS_FH.code);
        ArrayList<Object> recordStatusList = new ArrayList<>();
        recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
        reqParam.put("recordStatusList",recordStatusList);
        List<Map<String,Object>> logisticsDetailList = logisticsDetailMapper.queryCompensateLList(reqParam);
        if(logisticsDetailList.size()==0){
            throw new MessageException("sn号在审批中或已退货");
        }
        BigDecimal proNumSum = new BigDecimal(0);
        for (Map<String, Object> stringObjectMap : logisticsDetailList) {
            proNumSum = proNumSum.add(new BigDecimal(stringObjectMap.get("PRO_NUM").toString()));
        }
        if(proNumSum.compareTo(terminalTransferDetail.getSnCount())!=0){
            throw new MessageException("sn号数量不匹配");
        }
        Set<String> proComSet = new HashSet<>();
        Set<String> proModelSet = new HashSet<>();
        for (Map<String, Object> logisticsDetail : logisticsDetailList) {
            String orderId = String.valueOf(logisticsDetail.get("ORDER_ID"));
            String proId = String.valueOf(logisticsDetail.get("PRO_ID"));
            String activityId = String.valueOf(logisticsDetail.get("ACTIVITY_ID"));
            OSubOrderExample oSubOrderExample = new OSubOrderExample();
            OSubOrderExample.Criteria criteria = oSubOrderExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andOrderIdEqualTo(orderId);
            criteria.andProIdEqualTo(proId);
            List<OSubOrder> oSubOrders = subOrderMapper.selectByExample(oSubOrderExample);
            if(oSubOrders.size()!=1){
                throw new MessageException("查询采购单数据有误");
            }
            OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
            OSubOrderActivityExample.Criteria criteria1 = oSubOrderActivityExample.createCriteria();
            criteria1.andSubOrderIdEqualTo(oSubOrders.get(0).getId());
            criteria1.andActivityIdEqualTo(activityId);
            criteria1.andStatusEqualTo(Status.STATUS_1.status);
            List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
            if(oSubOrderActivities.size()!=1){
                throw new MessageException("查询活动快照数据有误");
            }
            OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
            proComSet.add(oSubOrderActivity.getVender());
            proModelSet.add(oSubOrderActivity.getProModel());
        }
        if(proComSet.size()!=1){
            throw new MessageException(terminalTransferDetail.getSnBeginNum()+"到"+terminalTransferDetail.getSnEndNum()+"不是同一厂商");
        }
        if(proModelSet.size()!=1){
            throw new MessageException(terminalTransferDetail.getSnBeginNum()+"到"+terminalTransferDetail.getSnEndNum()+"不是同一型号");
        }
        AgentBusInfoExample originalOrgExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria originalOrgCriteria = originalOrgExample.createCriteria();
        originalOrgCriteria.andStatusEqualTo(Status.STATUS_1.status);
        originalOrgCriteria.andBusStatusEqualTo(Status.STATUS_1.status);
        originalOrgCriteria.andCloReviewStatusEqualTo(AgStatus.Approved.getValue());
        originalOrgCriteria.andBusNumEqualTo(terminalTransferDetail.getOriginalOrgId());
        List<AgentBusInfo> originalOrgBusInfoList = agentBusInfoMapper.selectByExample(agentExample);
        if(originalOrgBusInfoList.size()==1){
            resultMap.put("originalBusId",originalOrgBusInfoList.get(0).getId());
        }
        resultMap.put("proCom",proComSet.iterator().next());
        resultMap.put("proModel",proModelSet.iterator().next());
        resultMap.put("goalBusId",goalAgentBusInfo.getId());
        return resultMap;
    }


    /**
     * 提交审批
     * @param id
     * @param cuser
     * @param agentId
     * @param isSave 是否已保存
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startTerminalTransferActivity(String id, String cuser, String agentId,Boolean isSave) throws Exception {

        if(!isSave){
            TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(id);
            if(terminalTransfer==null){
                throw new MessageException("提交审批信息有误");
            }
            terminalTransfer.setuUser(cuser);
            terminalTransfer.setuTime(new Date());
            terminalTransfer.setReviewStatus(AgStatus.Approving.status);
            int i = terminalTransferMapper.updateByPrimaryKeySelective(terminalTransfer);
            if(i!=1){
                throw new MessageException("提交审批处理失败");
            }
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, BusActRelBusType.agentTerminal.name(), null, null, null);
        if (proce == null) {
            log.info("终端划拨提交审批，审批流启动失败{}:{}", id, cuser);
            throw new MessageException("审批流启动失败!");
        }
        //代理商业务和工作流关系
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
    public AgentResult approvalTerminalTransferTask(AgentVo agentVo, String userId, String busId) throws Exception{
        try {
            if(agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())){
                //财务审批
                List<TerminalTransferDetail> terminalTransferDetails = queryDetailByTerminalId(busId);
                if(agentVo.getSid().equals("cw")){
                    List<TerminalTransferDetail> terminalTransferDetailsRedis = queryImprotMsgList(busId);
                    if(terminalTransferDetails.size()!=terminalTransferDetailsRedis.size()){
                        throw new MessageException("请导入信息后在提交审批");
                    }
                }else{
                    //目前就省区一个节点直接else
                    for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
                        terminalTransferDetail.setButtJointPerson(userId);
                        int i = terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                        if(i!=1){
                            throw new MessageException("更新审批失败");
                        }
                    }
                }
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
        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andTerminalTransferIdEqualTo(terminalTransfer.getId());
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
        if(terminalTransferDetails.size()==0){
            throw new MessageException("终端划拨更新失败");
        }
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            try {
                //从redis取出导入的数据更新到DB
                String terminalTransferJson = redisService.hGet(RedisCachKey.TERMINAL_TRANSFER.code, terminalTransferDetail.getId());
                TerminalTransferDetail terminal = JsonUtil.jsonToPojo(terminalTransferJson, TerminalTransferDetail.class);
                TerminalTransferDetail upTerminal = new TerminalTransferDetail();
                upTerminal.setId(terminalTransferDetail.getId());
                upTerminal.setAdjustStatus(terminal.getAdjustStatus());
                upTerminal.setRemark(terminal.getRemark());
                upTerminal.setAdjustTime(terminal.getAdjustTime());
                upTerminal.setBatchNum(terminal.getBatchNum());
                int j = terminalTransferDetailMapper.updateByPrimaryKeySelective(upTerminal);
                if(j!=1){
                    throw new MessageException("终端划拨明细更新到DB失败");
                }
                redisService.hDel(RedisCachKey.TERMINAL_TRANSFER.code,terminalTransferDetail.getId());
            }catch (Exception e){
                e.getStackTrace();
                throw new MessageException("终端划拨明细更新到DB失败");
            }
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
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            if(StringUtils.isNotBlank(terminalTransferDetail.getButtJointPerson())){
                CUser cUser = userService.selectById(Integer.valueOf(terminalTransferDetail.getButtJointPerson()));
                if(cUser!=null){
                    terminalTransferDetail.setButtJointPerson(cUser.getName());
                }
            }

        }
        terminalTransfer.setTerminalTransferDetailList(terminalTransferDetails);
        return terminalTransfer;
    }

    /**
     * 根据申请id查询明细
     * @param terminalTransferId
     * @return
     */
    @Override
    public List<TerminalTransferDetail> queryDetailByTerminalId(String terminalTransferId){
        if(StringUtils.isBlank(terminalTransferId)){
            return null;
        }
        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andTerminalTransferIdEqualTo(terminalTransferId);
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
        return terminalTransferDetails;
    }

    /**
     * 查询redis已导入信息
     * @param terminalTransferId
     * @return
     */
    @Override
    public List<TerminalTransferDetail> queryImprotMsgList(String terminalTransferId){
        List<TerminalTransferDetail> terminalTransferDetails = queryDetailByTerminalId(terminalTransferId);
        if(terminalTransferDetails==null){
            return null;
        }
        List<TerminalTransferDetail> resultList = new ArrayList<>();
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            String terminalTransferJson = redisService.hGet(RedisCachKey.TERMINAL_TRANSFER.code, terminalTransferDetail.getId());
            TerminalTransferDetail terminal = JsonUtil.jsonToPojo(terminalTransferJson, TerminalTransferDetail.class);
            if(terminal!=null){
                terminal.setAdjustMsg(AdjustStatus.getContentByValue(terminal.getAdjustStatus()));
                resultList.add(terminal);
            }
        }
        return resultList;
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult importTerminal(List<List<Object>> excelList,String cUser,String busId)throws Exception{

        int i = 1;
        String batchNo = IDUtils.getBatchNo();
        List<Map<String,String>> resultList = new ArrayList<>();
        for (List<Object> objects : excelList) {
            String id = String.valueOf(objects.get(0));
            String adjustStatusCon = String.valueOf(objects.get(12));
            String remark = String.valueOf(objects.get(13));
            BigDecimal adjustStatus = AdjustStatus.getValueByContent(adjustStatusCon);
            if(adjustStatus==null || adjustStatusCon.equals(AdjustStatus.TZZ.msg) || adjustStatusCon.equals(AdjustStatus.WTZ.msg) ){
                throw new MessageException("第"+i+"个调整结果类型错误");
            }
            if(StringUtils.isBlank(id)){
                throw new MessageException("第"+i+"个编号为空");
            }
            TerminalTransferDetail terminalTransferDetail = terminalTransferDetailMapper.selectByPrimaryKey(id);
            if(!busId.equals(terminalTransferDetail.getTerminalTransferId())){
                throw new MessageException("第"+i+"个数据错误,不在该订单下！");
            }
            if(null==terminalTransferDetail){
                throw new MessageException("第"+i+"个编号不存在");
            }
            try {
                terminalTransferDetail.setRemark(remark);
                Date date = new Date();
                terminalTransferDetail.setAdjustTime(date);
                terminalTransferDetail.setAdjustStatus(adjustStatus);
                terminalTransferDetail.setuUser(cUser);
                terminalTransferDetail.setuTime(date);
                terminalTransferDetail.setBatchNum(batchNo);
                TerminalTransferDetail upTransferDetail = new TerminalTransferDetail();
                upTransferDetail.setId(id);
                upTransferDetail.setuUser(cUser);
                upTransferDetail.setuTime(date);
                upTransferDetail.setAdjustStatus(AdjustStatus.TZZ.getValue());
                int j = terminalTransferDetailMapper.updateByPrimaryKeySelective(upTransferDetail);
                if(j!=1){
                    throw new MessageException("第"+i+"个数据更新失败");
                }
                redisService.hSet(RedisCachKey.TERMINAL_TRANSFER.code,id, JsonUtil.objectToJson(terminalTransferDetail));
            } catch (Exception e) {
                e.printStackTrace();
                throw new MessageException("第"+i+"个数据处理失败");
            }
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("id",id);
            resultMap.put("adjustStatusCon",adjustStatusCon);
            resultMap.put("remark",remark);
            resultList.add(resultMap);
        }
        return AgentResult.ok(resultList);
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult delTerminalTransfer(String terminalTransferId,String cUser)throws Exception{

        if(StringUtils.isBlank(terminalTransferId)){
            throw new MessageException("数据ID为空");
        }
        TerminalTransfer terminalTransfer = terminalTransferMapper.selectByPrimaryKey(terminalTransferId);
        terminalTransfer.setStatus(Status.STATUS_0.status);
        Date date = new Date();
        terminalTransfer.setuTime(date);
        terminalTransfer.setuUser(cUser);
        int i = terminalTransferMapper.updateByPrimaryKeySelective(terminalTransfer);
        if(i!=1){
            throw new MessageException("数据处理失败");
        }
        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
        criteria.andTerminalTransferIdEqualTo(terminalTransferId);
        List<TerminalTransferDetail> terminalTransferDetails = terminalTransferDetailMapper.selectByExample(terminalTransferDetailExample);
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetails) {
            terminalTransferDetail.setStatus(Status.STATUS_0.status);
            terminalTransferDetail.setuTime(date);
            terminalTransferDetail.setuUser(cUser);
            int j = terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
            if(j!=1){
                throw new MessageException("数据明细处理失败");
            }
        }
        return AgentResult.ok();
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult editTerminalTransfer(TerminalTransfer terminalTransfer,List<TerminalTransferDetail> terminalTransferDetailList, String cuser,String agentId)throws Exception{

        if(StringUtils.isBlank(terminalTransfer.getId())){
            throw new MessageException("数据ID为空");
        }
        Date date = new Date();
        terminalTransfer.setuTime(date);
        terminalTransfer.setuUser(cuser);
        int i = terminalTransferMapper.updateByPrimaryKeySelective(terminalTransfer);
        if(i!=1){
            throw new MessageException("更新数据明细失败");
        }
        if(terminalTransferDetailList.size()==0){
            throw new MessageException("请填写明细最少一条");
        }
        int updateCount = 0;
        for (TerminalTransferDetail terminalTransferDetail : terminalTransferDetailList) {
            Map<String, String> resultMap = saveOrEditVerify(terminalTransferDetail, agentId);
            //新增
            if(StringUtils.isBlank(terminalTransferDetail.getId())){
                terminalTransferDetail.setId(idService.genId(TabId.O_TERMINAL_TRANSFER_DETAIL));
                terminalTransferDetail.setTerminalTransferId(terminalTransfer.getId());
                terminalTransferDetail.setcUser(cuser);
                terminalTransferDetail.setuUser(cuser);
                terminalTransferDetail.setcTime(date);
                terminalTransferDetail.setuTime(date);
                terminalTransferDetail.setStatus(Status.STATUS_1.status);
                terminalTransferDetail.setVersion(Status.STATUS_1.status);
                terminalTransferDetail.setAgentId(agentId);
                terminalTransferDetail.setAdjustStatus(AdjustStatus.WTZ.getValue());
                terminalTransferDetail.setGoalBusId(resultMap.get("goalBusId"));
                terminalTransferDetail.setOriginalBusId(resultMap.get("originalBusId"));
                terminalTransferDetail.setProCom(resultMap.get("proCom"));
                terminalTransferDetail.setProModel(resultMap.get("proModel"));
                terminalTransferDetailMapper.insert(terminalTransferDetail);
            }else{
                terminalTransferDetail.setuUser(cuser);
                terminalTransferDetail.setuTime(date);
                terminalTransferDetail.setGoalBusId(resultMap.get("goalBusId"));
                terminalTransferDetail.setOriginalBusId(resultMap.get("originalBusId"));
                terminalTransferDetail.setProCom(resultMap.get("proCom"));
                terminalTransferDetail.setProModel(resultMap.get("proModel"));
                int j = terminalTransferDetailMapper.updateByPrimaryKeySelective(terminalTransferDetail);
                if(j!=1){
                    throw new MessageException("更新数据明细失败");
                }
                updateCount++;
            }
        }

        TerminalTransferDetailExample terminalTransferDetailExample = new TerminalTransferDetailExample();
        TerminalTransferDetailExample.Criteria criteria = terminalTransferDetailExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andTerminalTransferIdEqualTo(terminalTransfer.getId());
        int selectCount = (int)terminalTransferDetailMapper.countByExample(terminalTransferDetailExample);
        if(updateCount!=selectCount){
            throw new MessageException("数据存在异常,请联系管理员！");
        }
        return AgentResult.ok();
    }
}
