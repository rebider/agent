package com.ryx.credit.service.impl.agent;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.CuserAgentMapper;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.AgentQuitService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OCashReceivablesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author RYX
 * @Date 2019/1/26
 * @Desc 代理商退出
 */
@Service("agentQuitService")
public class AgentQuitServiceImpl extends AgentMergeServiceImpl implements AgentQuitService {

    private static Logger logger = LoggerFactory.getLogger(AgentQuitServiceImpl.class);
    @Autowired
    private AgentQuitMapper agentQuitMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private CapitalMapper capitalMapper;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private OCashReceivablesService cashReceivablesService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private CuserAgentMapper cUserAgentMapper;
    @Autowired
    private CUserMapper cUserMapper;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private OPaymentDetailMapper oPaymentDetailMapper;

    /**
     * 退出列表
     * @param agentQuit
     * @param page
     * @param dataRole
     * @param userId
     * @return
     */
    @Override
    public PageInfo queryAgentQuitList(AgentQuit agentQuit, Page page, String dataRole, Long userId) {
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("status", Status.STATUS_1.status);
        if (StringUtils.isNotBlank(agentQuit.getId())) {
            reqMap.put("id", agentQuit.getId());
        }
        if (StringUtils.isNotBlank(agentQuit.getAgentId())) {
            reqMap.put("agentId", agentQuit.getAgentId());
        }
        if (StringUtils.isNotBlank(agentQuit.getAgentName())) {
            reqMap.put("agentName", agentQuit.getAgentName());
        }
        if (null != agentQuit.getCloReviewStatus()) {
            reqMap.put("cloReviewStatus", agentQuit.getCloReviewStatus());
        }
//        if(StringUtils.isBlank(dataRole)){
//            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
//            if(orgCodeRes == null && orgCodeRes.size() != 1){
//                return null;
//            }
//            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
//            reqMap.put("orgId", String.valueOf(stringObjectMap.get("ORGID")));
//        }
        List<Map<String, Object>> agentMergeList = agentQuitMapper.queryAgentQuitList(reqMap, page);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(agentMergeList);
        pageInfo.setTotal(agentQuitMapper.queryAgentQuitCount(reqMap));
        return pageInfo;
    }

    /**
     * 保存
     * @param agentQuit
     * @param agentQuitFiles
     * @param cUser
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult saveAgentQuit(AgentQuit agentQuit, String[] agentQuitFiles, String cUser,
                                     String saveFlag,List<OCashReceivablesVo> oCashReceivables)throws Exception {

        if(StringUtils.isBlank(agentQuit.getAgentId())){
            throw new MessageException("代理商ID为空！");
        }
        if(StringUtils.isBlank(agentQuit.getQuitPlatform())){
            throw new MessageException("代理商退出平台为空！");
        }
        Agent agent = agentMapper.selectByPrimaryKey(agentQuit.getAgentId());
        if(null==agent){
            throw new MessageException("代理商不存在！");
        }
        //验证
        verifypprovaling(agentQuit.getAgentId());
        verifyChild(agentQuit.getAgentId());

        String quitId = idService.genId(TabId.A_AGENT_QUIT);
        agentQuit.setId(quitId);
        String platformIds = quitPlatformIds(agentQuit.getQuitPlatform(), agentQuit.getAgentId());
        if(StringUtils.isBlank(platformIds)){
            throw new MessageException("请选择有效的平台！");
        }
        agentQuit.setQuitBusId(platformIds);
        agentQuit.setContractStatus(Status.STATUS_0.status);
        agentQuit.setRefundAmtStatus(Status.STATUS_0.status);
        agentQuit.setAppRefund(Status.STATUS_0.status);
        agentQuit.setPlatformStatus(PlatformStatus.NODISPOSE.getValue());
        if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
            agentQuit.setCloReviewStatus(AgStatus.Approving.status);
        } else {
            agentQuit.setCloReviewStatus(AgStatus.Create.status);
        }
        agentQuit.setAgentName(agent.getAgName());
        agentQuit.setAgentOweTicket(getSubAgentOweTicket(agent.getId()));
        agentQuit.setSuppTicket(getSubAgentOweTicket(agent.getId()));
        BigDecimal profitDebt = profitDebt(agent.getId());
        BigDecimal orderDebt = getOrderDebt(agent.getId());
        BigDecimal capitalDebt = getCapitalDebt(agent.getId());
        agentQuit.setProfitDebt(profitDebt);
        agentQuit.setOrderDebt(orderDebt);
        agentQuit.setCapitalDebt(capitalDebt);
        agentQuit.setAgentDept(getSubAgentDebt(agent.getId()));

        BigDecimal capitalSumAmt = getCapitalSumAmt(agent.getId());
        BigDecimal suppDept = capitalSumAmt.subtract(profitDebt).subtract(orderDebt).subtract(capitalDebt);
        String suppDeptStr = String.valueOf(suppDept);
        if(suppDept.compareTo(new BigDecimal(0))==0){
            agentQuit.setSuppType(SuppType.W.getValue());
            agentQuit.setSuppDept(suppDept);
        }else{
            if(suppDeptStr.contains("-")){
                agentQuit.setSuppType(SuppType.D.getValue());
                String substring = suppDeptStr.substring(1, suppDeptStr.length());
                agentQuit.setSuppDept(new BigDecimal(substring));
            }else{
                agentQuit.setSuppType(SuppType.G.getValue());
                agentQuit.setSuppDept(suppDept);
            }
        }
        //验证
        verifysuppType(agentQuit.getSuppType(),agentQuitFiles,oCashReceivables);
        agentQuit.setRealitySuppDept(agentQuit.getSuppDept());
        agentQuit.setSubtractAmt(BigDecimal.ZERO);
        agentQuit.setcTime(new Date());
        agentQuit.setuTime(new Date());
        agentQuit.setcUser(cUser);
        agentQuit.setuUser(cUser);
        agentQuit.setStatus(Status.STATUS_1.status);
        agentQuit.setVersion(BigDecimal.ONE);
        int i = agentQuitMapper.insertSelective(agentQuit);
        if(i!=1){
            throw new MessageException("保存退出申请失败！");
        }

        //添加新的附件
        if (agentQuitFiles != null && agentQuitFiles.length!=0) {
            for(int j=0;j<agentQuitFiles.length;j++){
                AttachmentRel record = new AttachmentRel();
                record.setAttId(agentQuitFiles[j]);
                record.setSrcId(quitId);
                record.setcUser(cUser);
                record.setcTime(Calendar.getInstance().getTime());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.agentQuit.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                int f = attachmentRelMapper.insertSelective(record);
                if (1 != f) {
                    logger.info("代理商退出保存附件关系失败");
                    throw new ProcessException("保存附件失败");
                }
            }
        }
        //打款记录
        AgentResult agentResult = cashReceivablesService.addOCashReceivables(oCashReceivables,cUser,agentQuit.getAgentId(),CashPayType.AGENTQUIT,quitId);
        if(!agentResult.isOK()){
            logger.info("代理商合并保存打款记录失败1");
            throw new ProcessException("保存打款记录失败");
        }
        if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
            startAgentMergeActivity(quitId, cUser,true);
        }
        return AgentResult.ok();
    }

    /**
     * 不能有下级等验证
     * @param agentId
     * @throws Exception
     */
    public void verifyChild(String agentId)throws Exception{

        AgentBusInfoExample subAgentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria subCriteria = subAgentBusInfoExample.createCriteria();
        subCriteria.andAgentIdEqualTo(agentId);
        subCriteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentBusInfo> subAgentBusInfos = agentBusInfoMapper.selectByExample(subAgentBusInfoExample);
        if(subAgentBusInfos.size()==0){
            throw new MessageException("代理商业务信息有误");
        }
        for (AgentBusInfo subAgentBusInfo : subAgentBusInfos) {
            if(StringUtils.isBlank(subAgentBusInfo.getBusNum())){
                throw new MessageException("代理商业务平台未入网成功");
            }
            List<AgentBusInfo> childLevelBusInfos = agentBusinfoService.queryChildLevelByBusNum(null, subAgentBusInfo.getBusPlatform(), subAgentBusInfo.getBusNum());
            if(childLevelBusInfos.size()!=0){
                throw new MessageException("代理商不能有下级");
            }
        }
        AgentQuitExample agentQuitExample = new AgentQuitExample();
        AgentQuitExample.Criteria criteria = agentQuitExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andAgentIdEqualTo(agentId);
        List<AgentQuit> agentQuits = agentQuitMapper.selectByExample(agentQuitExample);
        if(agentQuits.size()!=0){
            throw new MessageException("已经提交申请请勿重复提交");
        }
    }

    /**
     * 验证必填
     * @param suppType
     * @param agentQuitFiles
     * @param oCashReceivables
     * @throws Exception
     */
    public void verifysuppType(String suppType,String[] agentQuitFiles,List<OCashReceivablesVo> oCashReceivables)throws Exception{
        if(SuppType.D.getValue().equals(suppType)){
            if(null==agentQuitFiles){
                throw new MessageException("代理商打款时附件必传");
            }
            if(agentQuitFiles.length==0){
                throw new MessageException("代理商打款时附件必传");
            }
            if(oCashReceivables.size()==0){
                throw new MessageException("代理商打款时请填写打款记录");
            }
        }
    }

    /**
     * 查询所有业务id
     * @param quitPlatform
     * @param agentId
     * @return
     */
    public String quitPlatformIds(String quitPlatform,String agentId){
        PlatFormExample platFormExample = new PlatFormExample();
        PlatFormExample.Criteria criteria = platFormExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        if(quitPlatform.equals(QuitPlatform.POS.getValue())){
            List<String> platformTypeList = new ArrayList<>();
            platformTypeList.add(PlatformType.POS.getValue());
            platformTypeList.add(PlatformType.ZPOS.getValue());
            criteria.andPlatformTypeIn(platformTypeList);
        }else if(quitPlatform.equals(QuitPlatform.MPOS.getValue())){
            criteria.andPlatformTypeEqualTo(PlatformType.MPOS.getValue());
        }else if(quitPlatform.equals(QuitPlatform.POSANDMPOS.getValue())){
            List<String> platformTypeList = new ArrayList<>();
            platformTypeList.add(PlatformType.POS.getValue());
            platformTypeList.add(PlatformType.MPOS.getValue());
            platformTypeList.add(PlatformType.ZPOS.getValue());
            criteria.andPlatformTypeIn(platformTypeList);
        }
        List<PlatForm> platForms = platFormMapper.selectByExample(platFormExample);

        List<String> busPlatformList = new ArrayList<>();
        for (PlatForm platForm : platForms) {
            busPlatformList.add(platForm.getPlatformNum());
        }
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria agentBusInfoCriteria = agentBusInfoExample.createCriteria();
        agentBusInfoCriteria.andStatusEqualTo(Status.STATUS_1.status);
        List<BigDecimal> busStatusList = new ArrayList<>();
        busStatusList.add(BusinessStatus.Enabled.status);
        busStatusList.add(BusinessStatus.inactive.status);
        agentBusInfoCriteria.andBusStatusIn(busStatusList);
        agentBusInfoCriteria.andAgentIdEqualTo(agentId);
        agentBusInfoCriteria.andBusPlatformIn(busPlatformList);
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        String busIds = "";
        for (AgentBusInfo agentBusInfo : agentBusInfos) {
            busIds += agentBusInfo.getId()+",";
        }
        if(StringUtils.isNotBlank(busIds)){
            busIds.substring(0,busIds.length()-1);
        }
        return busIds;
    }

    /**
     * 获取缴纳款总金额
     * @param agentId
     * @return
     */
    @Override
    public BigDecimal getCapitalSumAmt(String agentId){
        CapitalExample capitalExample = new CapitalExample();
        CapitalExample.Criteria criteria = capitalExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andCPayTypeEqualTo(PayType.YHHK.getValue());
        criteria.andCAgentIdEqualTo(agentId);
        List<Capital> capitals = capitalMapper.selectByExample(capitalExample);
        BigDecimal sumAmt = new BigDecimal(0);
        for (Capital capital : capitals) {
            sumAmt = sumAmt.add(capital.getcAmount());
        }
        return sumAmt;
    }

    /**
     * 计算补缴金额
     * @param agentId
     */
    @Override
    public Map<String,Object> calculateSuppDept(String agentId){
        Map<String,Object> resultMap = new HashMap<>();
        AgentQuitExample agentQuitExample = new AgentQuitExample();
        AgentQuitExample.Criteria criteria = agentQuitExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andAgentIdEqualTo(agentId);
        List<AgentQuit> agentQuits = agentQuitMapper.selectByExample(agentQuitExample);
        if (agentQuits != null && agentQuits.size()!=0) {
            AgentQuit agentQuit = agentQuits.get(0);
            resultMap.put("realitySuppDept",agentQuit.getRealitySuppDept());
            resultMap.put("subtractAmt",agentQuit.getSubtractAmt());
            resultMap.put("suppType",SuppType.getContentByValue(agentQuit.getSuppType()));
            resultMap.put("suppDept",agentQuit.getSuppDept());
            resultMap.put("subAgentOweTicket",agentQuit.getAgentOweTicket());
            resultMap.put("subAgentDebt",agentQuit.getAgentDept());
            resultMap.put("profitDebt",agentQuit.getProfitDebt());
            resultMap.put("orderDebt",agentQuit.getOrderDebt());
            resultMap.put("capitalDebt",agentQuit.getCapitalDebt());
        }else{
            BigDecimal capitalSumAmt = getCapitalSumAmt(agentId);
            BigDecimal profitDebt = profitDebt(agentId);
            BigDecimal orderDebt = getOrderDebt(agentId);
            BigDecimal capitalDebt = getCapitalDebt(agentId);
            BigDecimal suppDept = capitalSumAmt.subtract(profitDebt).subtract(orderDebt).subtract(capitalDebt);
            String suppDeptStr = String.valueOf(suppDept);
            if(suppDept.compareTo(new BigDecimal(0))==0){
                resultMap.put("suppType",SuppType.W.getContent());
                resultMap.put("suppDept",suppDept);
            }else{
                if(suppDeptStr.contains("-")){
                    resultMap.put("suppType",SuppType.D.getContent());
                    resultMap.put("suppDept",suppDeptStr.substring(1,suppDeptStr.length()));
                }else{
                    resultMap.put("suppType",SuppType.G.getContent());
                    resultMap.put("suppDept",suppDept);
                }
            }
        }
        return resultMap;
    }

    /**
     * 提交数据并审批
     * @param id
     * @param cUser
     * @param isSave 是否已保存
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startAgentMergeActivity(String id, String cUser, Boolean isSave) throws Exception {
        AgentQuit agentQuit = agentQuitMapper.selectByPrimaryKey(id);
        if (agentQuit == null) {
            throw new MessageException("提交审批信息有误！");
        }
        if (!isSave) {
            agentQuit.setuUser(cUser);
            agentQuit.setuTime(new Date());
            agentQuit.setCloReviewStatus(AgStatus.Approving.status);
            if (1 != agentQuitMapper.updateByPrimaryKeySelective(agentQuit)) {
                throw new MessageException("提交审批处理失败！");
            }
        }
        //提交审批进行锁定
        String quitBusId = agentQuit.getQuitBusId();
        String[] quitBusIds = quitBusId.split(",");
        for(int i=0;i<quitBusIds.length;i++){
            String mergeBusId = quitBusIds[i];
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(mergeBusId);
            agentBusInfo.setBusStatus(BusinessStatus.lock.status);
            int j = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
            if (j!=1) {
                throw new MessageException("更新业务锁定失败");
            }
        }

        AgentResult agentResult = cashReceivablesService.startProcing(CashPayType.AGENTQUIT,id,cUser);
        if(!agentResult.isOK()){
            logger.info("代理商合并更新打款信息失败");
            throw new MessageException("代理商合并更新打款信息失败");
        }

        Map startPar = agentEnterService.startPar(cUser);
        if (null == startPar) {
            logger.info("========用户{}{}启动部门参数为空", id, cUser);
            throw new MessageException("启动部门参数为空!");
        }
        Object party = startPar.get("party");
        //不同的业务类型找到不同的启动流程
        List<Dict> actlist = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.AGENTQUIT.name());
        String workId = null;
        for (Dict dict : actlist) {
            //根据不同的部门信息启动不同的流程
            if(party.equals(dict.getdItemvalue())) {
                workId = dict.getdItemname();
            }
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, workId, null, null, startPar);
        if (proce == null) {
            logger.info("退补差价提交审批，审批流启动失败{}:{}", id, cUser);
            throw new MessageException("审批流启动失败!");
        }

        //代理商业务&工作流关系
        BusActRel record = new BusActRel();
        record.setBusId(id);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cUser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.QUIT.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agentQuit.getAgentId());
        record.setAgentName(agentQuit.getAgentName());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("代理商合并提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("审批流启动失败：添加审批关系失败！");
        }
        return AgentResult.ok();
    }

    /**
     * 查看
     * @param id
     * @return
     */
    @Override
    public AgentQuit queryAgentQuit(String id){
        AgentQuit agentQuit = agentQuitMapper.selectByPrimaryKey(id);
        //查询关联附件
        List<Attachment> attachments = attachmentMapper.accessoryQuery(agentQuit.getId(), AttachmentRelType.agentQuit.name());
        agentQuit.setAttachments(attachments);
        return agentQuit;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalAgentQuitTask(AgentVo agentVo, String userId, String busId) throws Exception {
        try{
            if (agentVo.getApprovalResult().equals(ApprovalType.PASS.getValue())) {
                List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(userId));
                if (null == orgCodeRes) {
                    throw new ProcessException("部门参数为空！");
                }
                Map<String, Object> map = orgCodeRes.get(0);
                Object orgCode = map.get("ORGANIZATIONCODE");
                AgentQuit agentQuit = agentQuitMapper.selectByPrimaryKey(busId);
                AgentQuit agentQuitVo = agentVo.getAgentQuit();
                //市场审批
                if (String.valueOf(orgCode).equals("market")) {
                    agentQuit.setuTime(new Date());
                    agentQuit.setRefundAmtDeadline(agentQuitVo.getRefundAmtDeadline());
                    agentQuit.setMigrationPlatform(agentQuitVo.getMigrationPlatform());
                    int i = agentQuitMapper.updateByPrimaryKey(agentQuit);
                    if(i!=1){
                        throw new ProcessException("更新市场部处理失败");
                    }
                }
                //于华审批
                if (String.valueOf(orgCode).equals("manage")) {
                    agentQuit.setuTime(new Date());
                    if(agentQuitVo.getSubtractAmt().compareTo(agentQuit.getSuppDept())==1){
                        throw new ProcessException("减免金额大于实际金额");
                    }
                    agentQuit.setSubtractAmt(agentQuitVo.getSubtractAmt());
                    BigDecimal subtract = agentQuit.getSuppDept().subtract(agentQuitVo.getSubtractAmt());
                    agentQuit.setRealitySuppDept(subtract);
                    int i = agentQuitMapper.updateByPrimaryKey(agentQuit);
                    if(i!=1){
                        throw new ProcessException("更新处理失败");
                    }
                }
                //财务审批
                if (String.valueOf(orgCode).equals("finance")) {
                    AgentResult cashAgentResult = cashReceivablesService.approveTashBusiness(CashPayType.AGENTQUIT,busId,userId,new Date(),agentVo.getoCashReceivablesVoList());
                    if(!cashAgentResult.isOK()){
                        throw new ProcessException("更新收款信息失败");
                    }
                    agentQuit.setuTime(new Date());
                    agentQuit.setAppRefund(agentQuitVo.getAppRefund());
                    int i = agentQuitMapper.updateByPrimaryKey(agentQuit);
                    if(i!=1){
                        throw new ProcessException("更新财务部处理失败");
                    }
                }
            }

            AgentResult result = agentEnterService.completeTaskEnterActivity(agentVo, userId);
            if (!result.isOK()) {
                logger.error(result.getMsg());
                throw new MessageException("工作流处理任务异常");
            }
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException(e.getMessage());
        }
        return AgentResult.ok();
    }



    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressAgentQuitActivity(String proIns, BigDecimal agStatus)throws Exception{


        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andStatusEqualTo(Status.STATUS_1.status).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            logger.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            throw new MessageException("审批和数据关系有误");
        }
        BusActRel busActRel = list.get(0);
        busActRel.setActivStatus(AgStatus.getAgStatusString(agStatus));
        int z = busActRelMapper.updateByPrimaryKey(busActRel);
        if(z!=1) {
            logger.info("审批任务结束{}{}，代理商退出更新失败2", proIns, agStatus);
            throw new MessageException("代理商退出更新失败");
        }
        AgentQuit agentQuit = agentQuitMapper.selectByPrimaryKey(busActRel.getBusId());
        agentQuit.setCloReviewStatus(agStatus);
        agentQuit.setuTime(new Date());
        agentQuit.setApproveTime(new Date());
        int i = agentQuitMapper.updateByPrimaryKeySelective(agentQuit);
        if(i!=1){
            logger.info("审批任务结束{}{}，代理商合并更新失败1", proIns, agStatus);
            throw new MessageException("代理商合并更新失败");
        }
        AgentResult agentResult = AgentResult.fail();
        if(agStatus.compareTo(AgStatus.Refuse.getValue())==0){
            agentResult = cashReceivablesService.refuseProcing(CashPayType.AGENTQUIT,agentQuit.getId(),agentQuit.getcUser());
        }
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            agentResult = cashReceivablesService.finishProcing(CashPayType.AGENTQUIT,agentQuit.getId(),agentQuit.getcUser());
        }
        if(!agentResult.isOK()){
            throw new ProcessException("更新打款记录失败");
        }

        //删除退出平台
        String[] quitBusIds = agentQuit.getQuitBusId().split(",");
        for(int j=0;j<quitBusIds.length;j++){
            String quitBusId = quitBusIds[j];
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(quitBusId);
            agentBusInfo.setBusStatus(BusinessStatus.pause.status);
            agentBusInfo.setStatus(Status.STATUS_0.status);
            int k = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
            if(k!=1){
                throw new ProcessException("更新业务平台失败");
            }
        }

        //代理商退出之后如果一个业务都没了,基本信息等保留,禁止登陆后台
        AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
        AgentBusInfoExample.Criteria agentBusInfocriteria = agentBusInfoExample.createCriteria();
        agentBusInfocriteria.andAgentIdEqualTo(agentQuit.getAgentId());
        agentBusInfocriteria.andStatusEqualTo(Status.STATUS_1.status);
        List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
        if(agentBusInfos.size()==0){
            CuserAgentExample cuserAgentExample = new CuserAgentExample();
            CuserAgentExample.Criteria userAgentCriteria = cuserAgentExample.createCriteria();
            userAgentCriteria.andAgentidEqualTo(agentQuit.getAgentId());
            List<CuserAgent> cuserAgents = cUserAgentMapper.selectByExample(cuserAgentExample);
            if(cuserAgents.size()!=1){
                throw new MessageException("代理商信息有误");
            }
            CuserAgent cuserAgent = cuserAgents.get(0);
            int l = cUserMapper.updateStatusByPrimaryKey(Long.valueOf(cuserAgent.getUserid()));
            if(l!=1){
                logger.info("审批任务结束{}{}，代理商合并禁止登陆后台失败", proIns, agStatus);
                throw new MessageException("代理商合并更新失败");
            }
        }

        //清除缴纳款欠款
        if(agentQuit.getCapitalDebt().compareTo(BigDecimal.ZERO)==1){
            HashMap<String, Object> queryMap = new HashMap<>();
            queryMap.put("agentId", agentQuit.getAgentId());
            List<Map<String, Object>> maps = oPaymentDetailMapper.getCapitalDebt(queryMap);
            if(maps.size()==0){
                throw new MessageException("代理商退出：查询缴纳款明细失败");
            }
            for (Map<String, Object> map : maps) {
                String id = String.valueOf(map.get("ID"));
                OPaymentDetail paymentDetail = oPaymentDetailMapper.selectByPrimaryKey(id);
                paymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
                paymentDetail.setRealPayAmount(paymentDetail.getPayAmount());
                paymentDetail.setPayTime(new Date());
                int j = oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail);
                if(j!=1){
                    throw new MessageException("代理商退出：更新付款明细失败");
                }
            }
        }
        //清除订单欠款
        if(agentQuit.getOrderDebt().compareTo(BigDecimal.ZERO)==1){
            HashMap<String, Object> queryMap = new HashMap<>();
            queryMap.put("agentId", agentQuit.getAgentId());
            List<Map<String, Object>> maps = oPaymentDetailMapper.getOrderDebt(queryMap);
            if(maps.size()==0){
                throw new MessageException("代理商退出：查询订单明细失败");
            }
            for (Map<String, Object> map : maps) {
                String id = String.valueOf(map.get("ID"));
                OPaymentDetail paymentDetail = oPaymentDetailMapper.selectByPrimaryKey(id);
                paymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
                paymentDetail.setRealPayAmount(paymentDetail.getPayAmount());
                paymentDetail.setPayTime(new Date());
                int j = oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail);
                if(j!=1){
                    throw new MessageException("代理商退出：更新付款明细失败");
                }
            }
        }
        //清除分润
        if(agentQuit.getProfitDebt().compareTo(BigDecimal.ZERO)==1){

        }

        return AgentResult.ok();
    }
    @Override
    public AgentQuit getAgentQuitById(String quitId) {
        return agentQuitMapper.selectByPrimaryKey(quitId);
    }

}
