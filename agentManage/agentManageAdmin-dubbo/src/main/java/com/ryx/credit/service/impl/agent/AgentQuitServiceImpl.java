package com.ryx.credit.service.impl.agent;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.CUserMapper;
import com.ryx.credit.dao.CuserAgentMapper;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.profit.service.BusiPlatService;
import com.ryx.credit.profit.service.ProfitMonthService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.AgentQuitService;
import com.ryx.credit.service.agent.CapitalService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OCashReceivablesService;
import com.ryx.credit.util.Constants;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
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
    @Autowired
    private ProfitMonthService profitMonthService;
    @Autowired
    private AgentPlatFormSynMapper agentPlatFormSynMapper;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private CapitalService capitalService;
    @Autowired
    private CapitalFlowMapper capitalFlowMapper;
    @Autowired
    private BusiPlatService busiPlatService;

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
        if(StringUtils.isBlank(dataRole)){
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if(orgCodeRes == null && orgCodeRes.size() != 1){
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            if(String.valueOf(stringObjectMap.get("ORGANIZATIONCODE")).equals("agent")){
                reqMap.put("userId",userId);
            }else{
                reqMap.put("userId",userId);
                reqMap.put("orgId", String.valueOf(stringObjectMap.get("ORGID")));
            }
        }
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
        try {
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

            verifypprovaling(agentQuit.getAgentId());
            verifyProvinceInclude(agent.getId(),cUser);
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
                logger.info("代理商退出保存打款记录失败1");
                throw new ProcessException("保存打款记录失败");
            }
            if (saveFlag.equals(SaveFlag.TJSP.getValue())) {
                startAgentMergeActivity(quitId, cUser,true);
            }
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return AgentResult.ok();
    }

    /**
     * 不能有下级等验证
     * @param agentId
     * @throws Exception
     */
    public void verifyChild(String agentId)throws Exception{

        AgentQuitExample agentQuitExample = new AgentQuitExample();
        AgentQuitExample.Criteria criteria = agentQuitExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andAgentIdEqualTo(agentId);
        List<BigDecimal> cloReviewStatusList = new ArrayList<>();
        cloReviewStatusList.add(AgStatus.Approving.getValue());
        cloReviewStatusList.add(AgStatus.Create.getValue());
        criteria.andCloReviewStatusIn(cloReviewStatusList);
        List<AgentQuit> agentQuits = agentQuitMapper.selectByExample(agentQuitExample);
        if(agentQuits.size()!=0){
            throw new MessageException("已经提交申请请勿重复提交");
        }
    }

    /**
     * 断是否是自己省区下的
     * @param cUser
     * @throws Exception
     */
    public void verifyProvinceInclude(String agentId,String cUser)throws Exception{
        Map startPar = agentEnterService.startPar(cUser);
        if (null == startPar) {
            throw new MessageException("启动部门参数为空!");
        }
        Object party = startPar.get("party");
        if(!party.equals("agent")){
            //判断是否是自己省区下的
            List<Map<String, Object>>  org = iUserService.orgCode(Long.valueOf(cUser));
            if(org.size()==0){throw new ProcessException("部门信息未找到");}
            String orgId = String.valueOf(org.get(0).get("ORGID"));
            if(StringUtils.isBlank(orgId)){
                throw new ProcessException("省区部门参数为空");
            }
            Agent agent = agentMapper.selectByPrimaryKey(agentId);
            if(!orgId.equals(agent.getAgDocPro())){
                logger.info("不能提交其他省区的代理商退出");
                throw new ProcessException("不能提交其他省区的代理商退出");
            }
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
    public String quitPlatformIds(String quitPlatform,String agentId)throws Exception{
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
            if(StringUtils.isBlank(agentBusInfo.getBusNum())){
                throw new MessageException("代理商业务平台未入网成功");
            }
            List<AgentBusInfo> childLevelBusInfos = agentBusinfoService.queryChildLevelByBusNum(null, agentBusInfo.getBusPlatform(), agentBusInfo.getBusNum());
            if(childLevelBusInfos.size()!=0){
                throw new MessageException("代理商不能有下级");
            }
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
            if(!capital.getcType().equals(AgCapitalType.FUWUFEI.name()))
            sumAmt = sumAmt.add(capital.getcFqInAmount());
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
        criteria.andCloReviewStatusNotEqualTo(AgStatus.Create.getValue());
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
        List<Capital> capitals = capitalService.queryCapital(agentQuit.getAgentId(),PayType.YHHK.getValue());
        BigDecimal sumAmt = BigDecimal.ZERO;
        for (Capital capital : capitals) {
            sumAmt = capital.getcFqInAmount().add(sumAmt);
        }
        capitalService.disposeCapital(capitals, sumAmt, agentQuit.getId(), cUser, agentQuit.getAgentId(),
                                      agentQuit.getAgentName(),"代理商退出扣除");

        try {
            if(agentQuit.getQuitPlatform().equals(QuitPlatform.MPOS.getValue())){
                //冻结分润
                ArrayList<String> agList = new ArrayList<>();
                agList.add(agentQuit.getAgentId());
                Boolean result = busiPlatService.mPos_Frozen(agList);
                if(!result){
                    throw new MessageException("冻结分润失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MessageException("冻结分润失败");
        }

        AgentResult agentResult = cashReceivablesService.startProcing(CashPayType.AGENTQUIT,id,cUser);
        if(!agentResult.isOK()){
            logger.info("代理商退出更新打款信息失败");
            throw new MessageException("代理商退出更新打款信息失败");
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
        record.setDataShiro(BusActRelBusType.QUIT.key);

        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("代理商退出提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
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
            logger.info("审批任务结束{}{}，代理商退出更新失败1", proIns, agStatus);
            throw new MessageException("代理商退出更新失败");
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

        //通过
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
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
            //代理商状态为退出
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
                    logger.info("审批任务结束{}{}，代理商退出禁止登陆后台失败", proIns, agStatus);
                    throw new MessageException("代理商退出更新失败");
                }
                Agent agent = agentMapper.selectByPrimaryKey(agentQuit.getAgentId());
                agent.setcIncomStatus(AgentInStatus.QUIT.status);
                agent.setcUtime(new Date());
                int j = agentMapper.updateByPrimaryKeySelective(agent);
                if(j!=1){
                    throw new MessageException("代理商退出更新失败");
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
                    paymentDetail.setSrcType(PamentSrcType.AGENT_QUIT_DIKOU.code);
                    paymentDetail.setSrcId(busActRel.getBusId());
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
                    paymentDetail.setSrcType(PamentSrcType.AGENT_QUIT_DIKOU.code);
                    paymentDetail.setSrcId(busActRel.getBusId());
                    int j = oPaymentDetailMapper.updateByPrimaryKeySelective(paymentDetail);
                    if(j!=1){
                        throw new MessageException("代理商退出：更新付款明细失败");
                    }
                }
            }
            //清除分润
            if(agentQuit.getProfitDebt().compareTo(BigDecimal.ZERO)==1){

            }

            List<Capital> capitals = capitalService.queryCapital(agentQuit.getAgentId(),PayType.YHHK.getValue());
            BigDecimal sumAmt = BigDecimal.ZERO;
            for (Capital capital : capitals) {
                sumAmt = capital.getcFqInAmount().add(sumAmt);
            }
            for (Capital capital : capitals) {
                capital.setFreezeAmt(BigDecimal.ZERO);
                int k = capitalMapper.updateByPrimaryKeySelective(capital);
                if(k!=1){
                    throw new MessageException("通过更新冻结金额失败！");
                }
                CapitalFlowExample capitalFlowExample = new CapitalFlowExample();
                CapitalFlowExample.Criteria criteria1 = capitalFlowExample.createCriteria();
                criteria1.andStatusEqualTo(Status.STATUS_1.status);
                criteria1.andCapitalIdEqualTo(capital.getId());
                List<CapitalFlow> capitalFlows = capitalFlowMapper.selectByExample(capitalFlowExample);
                for (CapitalFlow capitalFlow : capitalFlows) {
                    capitalFlow.setFlowStatus(Status.STATUS_1.status);
                    int j = capitalFlowMapper.updateByPrimaryKey(capitalFlow);
                    if(j!=1){
                        throw new MessageException("通过更新资金流水记录失败！");
                    }
                }
            }

            //通知业务平台
            notityBusPlatform(agentQuit);
        }
        //拒绝
        if(agStatus.compareTo(AgStatus.Refuse.getValue())==0){
            try {
                //解冻分润
                ArrayList<String> agList = new ArrayList<>();
                agList.add(agentQuit.getAgentId());
                Boolean result = busiPlatService.mPos_unFrozen(agList);
                if(!result){
                    throw new MessageException("解冻分润失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new MessageException("代理商退出：分润解冻失败");
            }
            //还原平台状态
            String quitBusId = agentQuit.getQuitBusId();
            String[] quitBusIds = quitBusId.split(",");
            for(int j=0;j<quitBusIds.length;j++){
                String mergeBusId = quitBusIds[j];
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(mergeBusId);
                agentBusInfo.setBusStatus(BusinessStatus.Enabled.status);
                int k = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                if (k!=1) {
                    throw new MessageException("更新业务启用失败");
                }
            }

            List<Capital> capitals = capitalService.queryCapital(agentQuit.getAgentId(),PayType.YHHK.getValue());
            for (Capital capital : capitals) {
                capital.setcFqInAmount(capital.getcFqInAmount().add(capital.getFreezeAmt()));
                capital.setFreezeAmt(BigDecimal.ZERO);
                int j = capitalMapper.updateByPrimaryKeySelective(capital);
                if(j!=1){
                    throw new MessageException("拒绝更新冻结金额失败！");
                }
            }
        }

        return AgentResult.ok();
    }

    @Override
    public AgentQuit getAgentQuitById(String quitId) {
        return agentQuitMapper.selectByPrimaryKey(quitId);
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult editAgentQuit(AgentQuit agentQuit,String cUser,String[] agentMergeFiles,
                                      List<OCashReceivablesVo> oCashReceivables) throws Exception {
        if(StringUtils.isBlank(agentQuit.getId())){
            throw new MessageException("数据ID为空！");
        }
        try {

            AgentQuit queryAgentQuit = agentQuitMapper.selectByPrimaryKey(agentQuit.getId());
            String agentId = queryAgentQuit.getAgentId();

            verifypprovaling(agentQuit.getAgentId());
            verifyProvinceInclude(agentId,cUser);

            String platformIds = "";
            if(!queryAgentQuit.getQuitPlatform().equals(agentQuit.getQuitPlatform())){
                platformIds = quitPlatformIds(agentQuit.getQuitPlatform(), queryAgentQuit.getAgentId());
                if(StringUtils.isBlank(platformIds)){
                    throw new MessageException("请选择有效的平台！");
                }
                agentQuit.setQuitBusId(platformIds);
            }
            agentQuit.setAgentOweTicket(getSubAgentOweTicket(agentId));
            agentQuit.setSuppTicket(getSubAgentOweTicket(agentId));
            BigDecimal profitDebt = profitDebt(agentId);
            BigDecimal orderDebt = getOrderDebt(agentId);
            BigDecimal capitalDebt = getCapitalDebt(agentId);
            agentQuit.setProfitDebt(profitDebt);
            agentQuit.setOrderDebt(orderDebt);
            agentQuit.setCapitalDebt(capitalDebt);
            agentQuit.setAgentDept(getSubAgentDebt(agentId));
            BigDecimal capitalSumAmt = getCapitalSumAmt(agentId);
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
            agentQuit.setuTime(new Date());
            agentQuit.setuUser(cUser);

            if (1 != agentQuitMapper.updateByPrimaryKeySelective(agentQuit)) {
                logger.info("代理商退出修改审批，更新数据失败:{}", cUser);
                throw new MessageException("更新退出数据失败！");
            }

            //打款记录
            AgentResult agentResult = cashReceivablesService.addOCashReceivables(oCashReceivables,cUser,queryAgentQuit.getAgentId(),CashPayType.AGENTQUIT,agentQuit.getId());
            if(!agentResult.isOK()){
                logger.info("代理商退出保存打款记录失败1");
                throw new ProcessException("保存打款记录失败");
            }

            //附件修改
            AttachmentRelExample attachmentRelExample = new AttachmentRelExample();
            AttachmentRelExample.Criteria criteria = attachmentRelExample.createCriteria();
            criteria.andSrcIdEqualTo(agentQuit.getId());
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andBusTypeEqualTo(AttachmentRelType.agentQuit.name());
            List<AttachmentRel> attachmentRels = attachmentRelMapper.selectByExample(attachmentRelExample);
            attachmentRels.forEach(row->{
                row.setStatus(Status.STATUS_0.status);
                int i = attachmentRelMapper.updateByPrimaryKeySelective(row);
                if (1 != i) {
                    logger.info("删除代理商退出附件关系失败");
                    throw new ProcessException("删除附件失败");
                }
            });
            if(null!=agentMergeFiles && agentMergeFiles.length!=0){
                for(int i=0;i<agentMergeFiles.length;i++){
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(agentMergeFiles[i]);
                    record.setSrcId(agentQuit.getId());
                    record.setcUser(cUser);
                    record.setcTime(Calendar.getInstance().getTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.agentQuit.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    int f = attachmentRelMapper.insertSelective(record);
                    if (1 != f) {
                        logger.info("代理商退出附件关系失败");
                        throw new ProcessException("附件关系失败");
                    }
                }
            }
            if(!queryAgentQuit.getQuitPlatform().equals(agentQuit.getQuitPlatform()) &&
                queryAgentQuit.getCloReviewStatus().compareTo(AgStatus.Approving.getValue())==0){
                String quitBusIds = queryAgentQuit.getQuitBusId();
                String[] quitBusIdsSplit = quitBusIds.split(",");
                for(int i=0;i<quitBusIdsSplit.length;i++){
                    String quitBusId = quitBusIdsSplit[i];
                    AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(quitBusId);
                    agentBusInfo.setBusStatus(BusinessStatus.Enabled.status);
                    int j = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                    if (1 != j) {
                        throw new ProcessException("更新业务有效失败");
                    }
                }
                //新修改的业务改成锁定
                String[] editBusIds = platformIds.split(",");
                for(int i=0;i<editBusIds.length;i++){
                    String editBusId = editBusIds[i];
                    AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(editBusId);
                    agentBusInfo.setBusStatus(BusinessStatus.lock.status);
                    int j = agentBusInfoMapper.updateByPrimaryKeySelective(agentBusInfo);
                    if (1 != j) {
                        throw new ProcessException("更新业务锁定失败");
                    }
                }
            }
            return AgentResult.ok();
        } catch (MessageException e) {
            e.printStackTrace();
            throw new MessageException(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult deleteAgentQuit(String quitId, String cUser) throws Exception {
        if (StringUtils.isBlank(quitId)) {
            throw new MessageException("数据ID为空！");
        }
        AgentQuit agentQuit = agentQuitMapper.selectByPrimaryKey(quitId);
        agentQuit.setStatus(Status.STATUS_0.status);
        agentQuit.setuTime(new Date());
        agentQuit.setuUser(cUser);
        if (1 != agentQuitMapper.updateByPrimaryKeySelective(agentQuit)) {
            throw new MessageException("退出数据处理失败！");
        }
        return AgentResult.ok();
    }


    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public AgentResult agentQuitUploadRtc(AgentQuit agentQuit, String cUser, String[] agentMergeFiles) throws Exception {
        AttachmentRelExample attachmentRelExample = new AttachmentRelExample();
        AttachmentRelExample.Criteria criteria = attachmentRelExample.createCriteria();
        criteria.andSrcIdEqualTo(agentQuit.getId());
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andBusTypeEqualTo(AttachmentRelType.agentQuitUpload.name());
        List<AttachmentRel> attachmentRels = attachmentRelMapper.selectByExample(attachmentRelExample);
        attachmentRels.forEach(row->{
            row.setStatus(Status.STATUS_0.status);
            if (1 != attachmentRelMapper.updateByPrimaryKeySelective(row)) {
                logger.info("删除代理商退出附件关系失败");
                throw new ProcessException("删除附件失败！");
            }
        });
        if (null != agentMergeFiles && agentMergeFiles.length != 0) {
            for (int i = 0; i < agentMergeFiles.length; i++) {
                AttachmentRel record = new AttachmentRel();
                record.setAttId(agentMergeFiles[i]);
                record.setSrcId(agentQuit.getId());
                record.setcUser(cUser);
                record.setcTime(Calendar.getInstance().getTime());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.agentQuitUpload.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                if (1 != attachmentRelMapper.insertSelective(record)) {
                    logger.info("代理商退出附件关系失败");
                    throw new ProcessException("附件关系失败！");
                }
            }
        }
        AttachmentRelExample attachmentRelExamples = new AttachmentRelExample();
        AttachmentRelExample.Criteria criterias = attachmentRelExamples.createCriteria();
        criterias.andSrcIdEqualTo(agentQuit.getId());
        criterias.andStatusEqualTo(Status.STATUS_1.status);
        criterias.andBusTypeEqualTo(AttachmentRelType.agentQuitUpload.name());
        List<AttachmentRel> attachmentRelss = attachmentRelMapper.selectByExample(attachmentRelExamples);
        if (attachmentRelss.size() > 0) {
            agentQuit.setContractStatus(Status.STATUS_1.status);
            if (1 != agentQuitMapper.updateByPrimaryKeySelective(agentQuit)) {
                logger.info("更新上传解除合同状态成功");
                throw new ProcessException("更新上传解除合同状态成功！");
            }
        } else if (attachmentRelss.size() == 0) {
            agentQuit.setContractStatus(Status.STATUS_0.status);
            if (1 != agentQuitMapper.updateByPrimaryKeySelective(agentQuit)) {
                logger.info("更新上传解除合同状态失败");
                throw new ProcessException("更新上传解除合同状态失败！");
            }
        }
        return AgentResult.ok();
    }

    /**
     * 根据id查询退出的业务
     * @param id
     * @return
     */
    @Override
    public List<AgentBusInfo> getBusInfosById(String id){
        List<AgentBusInfo> resultList = new ArrayList<>();
        AgentQuit agentQuit = agentQuitMapper.selectByPrimaryKey(id);
        if(agentQuit==null){
            return null;
        }
        String quitBusIdsStr = agentQuit.getQuitBusId();
        if(StringUtils.isBlank(quitBusIdsStr)){
            return null;
        }
        String[] quitBusIds = quitBusIdsStr.split(",");
        for(int i=0;i<quitBusIds.length;i++){
            String quitBusId = quitBusIds[i];
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(quitBusId);
            resultList.add(agentBusInfo);
        }
        return resultList;
    }

    /**
     * 退出通知业务平台
     * @param agentQuit
     */
    public void notityBusPlatform(AgentQuit agentQuit){
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
                try {
                    String[] quitBusIds = agentQuit.getQuitBusId().split(",");
                    BigDecimal platformStatus = PlatformStatus.FAIL.getValue();
                    for (int i = 0; i < quitBusIds.length; i++) {
                        platformStatus = agentPlatFormSyn(agentQuit, platformStatus, quitBusIds[i]);
                    }
                    agentQuit.setPlatformStatus(platformStatus);
                    agentQuitMapper.updateByPrimaryKey(agentQuit);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public BigDecimal agentPlatFormSyn(AgentQuit agentQuit,BigDecimal platformStatus,String busId)throws Exception{
        AgentPlatFormSyn record = new AgentPlatFormSyn();
        record.setId(idService.genId(TabId.a_agent_platformsyn));
        record.setNotifyTime(new Date());
        record.setBusId(agentQuit.getId());
        record.setVersion(Status.STATUS_1.status);
        record.setcTime(new Date());
        record.setNotifyStatus(Status.STATUS_0.status);
        record.setNotifyCount(Status.STATUS_1.status);
        record.setcUser(agentQuit.getcUser());
        record.setAgentId(agentQuit.getAgentId());
        record.setNotifyType(NotifyType.AgentQuit.getValue());

        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(busId);
        PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
        String platType = platForm.getPlatformType();
        AgentResult agentResult = AgentResult.fail();
        record.setPlatformCode(agentBusInfo.getBusPlatform());
        if (platType.equals(PlatformType.POS.getValue())) {
            AgentNotifyVo agentNotifyVo = new AgentNotifyVo();
            agentNotifyVo.setOrgId(agentBusInfo.getBusNum());
            agentNotifyVo.setRemark("代理商退出");
            record.setSendJson("orgId:" + agentNotifyVo.getOrgId());
            agentResult = httpRequestForPos(agentNotifyVo);
        } else if (platType.equals(PlatformType.MPOS.getValue())) {
            AgentNotifyVo agentNotifyVo = new AgentNotifyVo();
            List<String> list = new ArrayList<>();
            list.add(agentBusInfo.getBusNum());
            agentNotifyVo.setBatchIds(list);
            record.setSendJson("batchIds:" + JsonUtil.objectToJson(list));
            agentResult = httpRequestForMPOS(agentNotifyVo);
        }
        //接口请求成功
        if (null != agentResult && !"".equals(agentResult) && agentResult.isOK()) {
            //添加请求记录
            record.setSuccesTime(new Date());
            record.setNotifyStatus(Status.STATUS_1.status);
            platformStatus = PlatformStatus.SUCCESS.getValue();
        }
        record.setNotifyJson(agentResult.getMsg());
        try {
            agentPlatFormSynMapper.insert(record);
        } catch (Exception e) {
            logger.info("代理商合并通知pos异常：{}" + e.getMessage());
            e.printStackTrace();
        }
        return platformStatus;
    }


    public static AgentResult httpRequestForPos(AgentNotifyVo agentNotifyVo)throws Exception{
        try {

            String cooperator = com.ryx.credit.util.Constants.cooperator;
            String charset = "UTF-8"; // 字符集
            String tranCode = "ORG007"; // 交易码
            String reqMsgId = UUID.randomUUID().toString().replace("-", ""); // 请求流水
            String reqDate = DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"); // 请求时间

            JSONObject jsonParams = new JSONObject();
            JSONObject data = new JSONObject();
            jsonParams.put("version", "1.0.0");
            jsonParams.put("msgType", "01");
            jsonParams.put("reqDate", reqDate);
            data.put("orgId",agentNotifyVo.getOrgId());
            data.put("remark",agentNotifyVo.getRemark());

            jsonParams.put("data", data);
            String plainXML = jsonParams.toString();
            // 请求报文加密开始
            String keyStr = AESUtil.getAESKey();
            byte[] plainBytes = plainXML.getBytes(charset);
            byte[] keyBytes = keyStr.getBytes(charset);
            String encryptData = new String(org.apache.commons.codec.binary.Base64.encodeBase64((AESUtil.encrypt(plainBytes, keyBytes, "AES", "AES/ECB/PKCS5Padding", null))), charset);
            String signData = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.digitalSign(plainBytes, Constants.privateKey, "SHA1WithRSA")), charset);
            String encrtptKey = new String(org.apache.commons.codec.binary.Base64.encodeBase64(RSAUtil.encrypt(keyBytes, Constants.publicKey, 2048, 11, "RSA/ECB/PKCS1Padding")), charset);
            // 请求报文加密结束

            Map<String, String> map = new HashMap<>();
            map.put("encryptData", encryptData);
            map.put("encryptKey", encrtptKey);
            map.put("cooperator", cooperator);
            map.put("signData", signData);
            map.put("tranCode", tranCode);
            map.put("reqMsgId", reqMsgId);

            logger.info("通知pos请求参数:{}",map);
            String httpResult = HttpClientUtil.doPost(AppConfig.getProperty("agent_pos_notify_url"), map);
            JSONObject jsonObject = JSONObject.parseObject(httpResult);
            if (!jsonObject.containsKey("encryptData") || !jsonObject.containsKey("encryptKey")) {
                logger.info("请求异常======" + httpResult);
                AppConfig.sendEmails("http请求异常", "入网通知POS失败报警");
                throw new Exception("http请求异常");
            } else {
                String resEncryptData = jsonObject.getString("encryptData");
                String resEncryptKey = jsonObject.getString("encryptKey");
                byte[] decodeBase64KeyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resEncryptKey.getBytes(charset));
                byte[] merchantAESKeyBytes = RSAUtil.decrypt(decodeBase64KeyBytes, Constants.privateKey, 2048, 11, "RSA/ECB/PKCS1Padding");
                byte[] decodeBase64DataBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resEncryptData.getBytes(charset));
                byte[] merchantXmlDataBytes = AESUtil.decrypt(decodeBase64DataBytes, merchantAESKeyBytes, "AES", "AES/ECB/PKCS5Padding", null);
                String respXML = new String(merchantXmlDataBytes, charset);
                logger.info("通知pos返回参数：{}",respXML);

                // 报文验签
                String resSignData = jsonObject.getString("signData");
                byte[] signBytes = org.apache.commons.codec.binary.Base64.decodeBase64(resSignData);
                if (!RSAUtil.verifyDigitalSign(respXML.getBytes(charset), signBytes, Constants.publicKey, "SHA1WithRSA")) {
                    logger.info("签名验证失败");
                } else {
                    logger.info("签名验证成功");
                    JSONObject respXMLObj = JSONObject.parseObject(respXML);
                    String respCode = String.valueOf(respXMLObj.get("respCode"));
                    if (respCode.equals("000000")){
                        return AgentResult.build(200,respXMLObj.toString());
                    }else{
                        AppConfig.sendEmails(respXML, "入网通知POS失败报警");
                        logger.info("http请求超时返回错误:{}",respXML);
                        return AgentResult.fail(respXMLObj.toString());
                    }
                }
                return new AgentResult(500,"http请求异常",respXML);
            }
        } catch (Exception e) {
            AppConfig.sendEmails("http请求超时:"+e.getStackTrace(), "入网通知POS失败报警");
            logger.info("http请求超时:{}",e.getMessage());
            throw e;
        }
    }

    private AgentResult httpRequestForMPOS(AgentNotifyVo agentNotifyVo)throws Exception{

        try {
            Map<String,Object> jsonParams = new HashMap<>();
            jsonParams.put("onlinechannel","000158114");
            jsonParams.put("batchIds",JsonUtil.objectToJson(agentNotifyVo.getBatchIds()));

            String params = JsonUtil .objectToJson(jsonParams);
            logger.info("通知手刷请求参数：{}",params);

            //发送请求
            String httpResult = HttpClientUtil.doPostJson(AppConfig.getProperty("agentQuit_mpos_notify_url"),params);

            logger.info("通知手刷返回参数：{}",httpResult);
            JSONObject respXMLObj = JSONObject.parseObject(httpResult);
            String respCode = String.valueOf(respXMLObj.get("respCode"));
            if (respCode.equals("000000")){
                return AgentResult.build(200,respXMLObj.toString());
            }else{
                AppConfig.sendEmails(httpResult, "入网通知手刷失败报警");
                logger.info("http请求超时返回错误:{}",httpResult);
                return AgentResult.fail(respXMLObj.toString());
            }
        } catch (Exception e) {
            AppConfig.sendEmails("通知手刷请求超时："+e.getStackTrace(), "入网通知手刷失败报警");
            logger.info("http请求超时:{}",e.getMessage());
            throw new Exception("http请求超时");
        }
    }

    @Override
    public AgentResult manualAgentQuitNotify(String busId,String platformCode){
        try {
            AgentQuit agentQuit = agentQuitMapper.selectByPrimaryKey(busId);
            AgentBusInfoExample agentBusInfoExample = new AgentBusInfoExample();
            AgentBusInfoExample.Criteria criteria = agentBusInfoExample.createCriteria();
            criteria.andAgentIdEqualTo(agentQuit.getAgentId());
            criteria.andBusPlatformEqualTo(platformCode);
            List<AgentBusInfo> agentBusInfos = agentBusInfoMapper.selectByExample(agentBusInfoExample);
            if(agentBusInfos==null){
                return AgentResult.fail();
            }
            if(agentBusInfos.size()!=1){
                return AgentResult.fail();
            }
            AgentBusInfo agentBusInfo = agentBusInfos.get(0);
            BigDecimal platformStatus = PlatformStatus.FAIL.getValue();
            platformStatus = agentPlatFormSyn(agentQuit, platformStatus, agentBusInfo.getId());
            agentQuit.setPlatformStatus(platformStatus);
            agentQuitMapper.updateByPrimaryKey(agentQuit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AgentResult.ok();
    }


    @Override
    public List<Capital> queryCapital(String id) {
        List<Capital> capitals = capitalMapper.paymentQuery(id);
        List<Capital> resultList = new ArrayList<>();
        if (null != capitals && capitals.size() > 0) {
            for (Capital capital : capitals) {
                capital.setAttachmentList(attachmentMapper.accessoryQuery(capital.getId(), AttachmentRelType.Capital.name()));
                if(!capital.getcType().equals(AgCapitalType.FUWUFEI.name())){
                    resultList.add(capital);
                }
            }
        }
        return resultList;
    }


}
