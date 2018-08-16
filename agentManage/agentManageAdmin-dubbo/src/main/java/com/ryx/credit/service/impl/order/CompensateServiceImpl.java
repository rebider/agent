package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AttachmentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.ORefundPriceDiffVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.CompensateService;
import com.ryx.credit.service.order.IAccountAdjustService;
import com.ryx.credit.service.order.OrderActivityService;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 补差价处理
 * Created by liudh on 2018/7/24.
 */
@Service("compensateService")
public class CompensateServiceImpl implements CompensateService {

    private static Logger log = LoggerFactory.getLogger(CompensateServiceImpl.class);
    @Autowired
    private OActivityMapper activityMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private ORefundPriceDiffMapper refundPriceDiffMapper;
    @Autowired
    private ORefundPriceDiffDetailMapper refundPriceDiffDetailMapper;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private ODeductCapitalMapper deductCapitalMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private CompensateService compensateService;
    @Autowired
    private OLogisticsDetailMapper logisticsDetailMapper;
    @Autowired
    private OrderActivityService orderActivityService;
    @Autowired
    private OLogisticsMapper logisticsMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private AgentService agentService;

    @Override
    public ORefundPriceDiff selectByPrimaryKey(String id){
        if(StringUtils.isBlank(id)){
            return null;
        }
        ORefundPriceDiff oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(id);
        return oRefundPriceDiff;
    }


    @Override
    public PageInfo compensateList(ORefundPriceDiffVo refundPriceDiff, Page page){

        ORefundPriceDiffExample example = new ORefundPriceDiffExample();
        ORefundPriceDiffExample.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(refundPriceDiff.getApplyBeginTime())){
            criteria.andSTimeGreaterThanOrEqualTo(DateUtil.getDateFromStr(refundPriceDiff.getApplyBeginTime(),DateUtil.DATE_FORMAT_1));
        }
        if(StringUtils.isNotBlank(refundPriceDiff.getApplyEndTime())){
            criteria.andSTimeLessThanOrEqualTo(DateUtil.getDateFromStr(refundPriceDiff.getApplyEndTime(),DateUtil.DATE_FORMAT_1));
        }
        if(null!=refundPriceDiff.getReviewStatus()){
            criteria.andReviewStatusEqualTo(refundPriceDiff.getReviewStatus());
        }
        criteria.andCUserEqualTo(refundPriceDiff.getcUser());
        example.setPage(page);
        example.setOrderByClause("c_time desc");
        List<ORefundPriceDiff> refundPriceDiffs = refundPriceDiffMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(refundPriceDiffs);
        long count = refundPriceDiffMapper.countByExample(example);
        pageInfo.setTotal(new Long(count).intValue());
        return pageInfo;
    }

    @Override
    public List<Map<String,Object>> getOrderMsgByExcel(List<Object> excelList,Long userId)throws ProcessException{
        String proCom = "";
        String proModel = "";
        String snBegin = "";
        String snEnd = "";
        String count = "";
        try {
            proCom =  String.valueOf(excelList.get(0));
            proModel =  String.valueOf(excelList.get(1));
            snBegin =  String.valueOf(excelList.get(2));
            snEnd =  String.valueOf(excelList.get(3));
            count =  String.valueOf(excelList.get(4));
        } catch (Exception e) {
            throw new ProcessException("导入解析文件失败");
        }

        Map<String, Object> reqParam = new HashMap<>();
        reqParam.put("snBegin",snBegin);
        reqParam.put("snEnd",snEnd);
        reqParam.put("status",OLogisticsDetailStatus.STATUS_FH.code);
        ArrayList<Object> recordStatusList = new ArrayList<>();
        recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
        reqParam.put("recordStatusList",recordStatusList);
        Dict dictByName = dictOptionsService.findDictByName(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), proCom);
        reqParam.put("proCom",dictByName.getdItemvalue());
        reqParam.put("proModel",proModel);
        List<Map<String,Object>> compensateLList = logisticsDetailMapper.queryCompensateLList(reqParam);
        if(null==compensateLList){
            throw new ProcessException("导入解析文件失败");
        }
        if(compensateLList.size()==0){
            throw new ProcessException("sn号在审批中或已退货");
        }
        String proNum = String.valueOf(compensateLList.get(0).get("PRO_NUM"));
        if(!proNum.equals(count)){
            throw new ProcessException("sn号数量不匹配");
        }
        //判断是否是自己省区下的订单
        List<Map<String, Object>>  org = userService.orgCode(userId);
        if(org.size()==0){throw new ProcessException("部门信息未找到");}
        String orgId = String.valueOf(org.get(0).get("ORGID"));
        if(StringUtils.isBlank(orgId)){
            throw new ProcessException("省区部门参数为空");
        }
        String logisticsId = String.valueOf(compensateLList.get(0).get("LOGISTICS_ID"));
        OLogistics oLogistics = logisticsMapper.selectByPrimaryKey(logisticsId);
        for (Map<String, Object> stringObjectMap : compensateLList) {
            Agent agent = agentService.getAgentById(String.valueOf(stringObjectMap.get("AGENT_ID")));
            if(!orgId.equals(agent.getAgDocPro())){
                log.info("不能提交其他省区的退补差价");
                throw new ProcessException("不能提交其他省区的退补差价");
            }
            String gTime = String.valueOf(stringObjectMap.get("G_TIME"));
            if(StringUtils.isNotBlank(gTime) && gTime!="null"){
                BigDecimal gTimeB = new BigDecimal(gTime);
                gTimeB = gTimeB.multiply(new BigDecimal(24)).multiply(new BigDecimal(60)).multiply(new BigDecimal(60)).multiply(new BigDecimal(1000));
                long activityCtime = oLogistics.getcTime().getTime();
                long nowTime = new Date().getTime();
                if((new BigDecimal(nowTime-activityCtime)).compareTo(gTimeB)==1){
                    log.info("商品活动超出保价时间");
                    throw new ProcessException("商品活动超出保价时间");
                }
            }
        }
        return compensateLList;
    }

    /**
     * 计算变更差价
     * @param oldActivityId
     * @param activityId
     * @param proNum
     * @return
     */
    @Override
    public BigDecimal calculatePriceDiff(String beginSn,String endSn,String oldActivityId,String activityId,BigDecimal proNum){
        BigDecimal resultPrice = new BigDecimal(0);

        Map<String, Object> reqParam = new HashMap<>();
        reqParam.put("snBegin",beginSn);
        reqParam.put("snEnd",endSn);
        reqParam.put("status",OLogisticsDetailStatus.STATUS_FH.code);
        ArrayList<Object> recordStatusList = new ArrayList<>();
        recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
        reqParam.put("recordStatusList",recordStatusList);
        List<Map<String, Object>> oLogisticsDetails = logisticsDetailMapper.queryCompensateLList(reqParam);
        if(null==oLogisticsDetails){
            log.info("calculatePriceDiff数据有误异常返回1");
            return null;
        }
        if(oLogisticsDetails.size()!=1){
            log.info("calculatePriceDiff数据有误异常返回2");
            return null;
        }
        Map<String, Object> logisticsDetail = oLogisticsDetails.get(0);
        BigDecimal oldPrice = new BigDecimal(logisticsDetail.get("SETTLEMENT_PRICE").toString()).multiply(proNum);
        BigDecimal newPrice = calculateTotalPrice(activityId, proNum);
        resultPrice = oldPrice.subtract(newPrice);
        return resultPrice;
    }

    /**
     * 计算总金额
     * @param activityId  活动id
     * @param count
     * @return
     */
    @Override
    public BigDecimal calculateTotalPrice(String activityId,BigDecimal count){

        if(StringUtils.isBlank(activityId)){
            return null;
        }
        OActivity oActivity = activityMapper.selectByPrimaryKey(activityId);
        //判断是否满足  暂未实现

        BigDecimal sumPrice = count.multiply(oActivity.getPrice());
        return sumPrice;
    }

    /**
     * save
     * @param oRefundPriceDiff
     * @param refundPriceDiffDetailList
     * @param cUser
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult compensateAmtSave(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,List<String> refundPriceDiffFile, String cUser){

        String priceDiffId = idService.genId(TabId.o_Refund_price_diff);
        oRefundPriceDiff.setId(priceDiffId);
        Date nowDate = new Date();
        oRefundPriceDiff.setRelCompType(oRefundPriceDiff.getApplyCompType());
        oRefundPriceDiff.setRelCompAmt(oRefundPriceDiff.getApplyCompAmt());
        oRefundPriceDiff.setMachOweAmt(new BigDecimal(0));
        oRefundPriceDiff.setDeductAmt(new BigDecimal(0));
        oRefundPriceDiff.setGatherAmt(new BigDecimal(0));
        oRefundPriceDiff.setcTime(nowDate);
        oRefundPriceDiff.setuTime(nowDate);
        oRefundPriceDiff.setsTime(nowDate);
        oRefundPriceDiff.setcUser(cUser);
        oRefundPriceDiff.setuUser(cUser);
        oRefundPriceDiff.setReviewStatus(AgStatus.Create.status);
        oRefundPriceDiff.setStatus(Status.STATUS_1.status);
        oRefundPriceDiff.setVersion(Status.STATUS_0.status);
        int refundDiffInsert = refundPriceDiffMapper.insert(oRefundPriceDiff);
        if(refundDiffInsert!=1){
            log.info("插入补退差价申请表异常");
            throw new ProcessException("保存失败");
        }

        //添加新的附件
        if (refundPriceDiffFile != null) {
            refundPriceDiffFile.forEach(fileId->{
                AttachmentRel record = new AttachmentRel();
                record.setAttId(fileId);
                record.setSrcId(priceDiffId);
                record.setcUser(cUser);
                record.setcTime(Calendar.getInstance().getTime());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.ActivityEdit.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                int i = attachmentRelMapper.insertSelective(record);
                if (1 != i) {
                    log.info("活动变更附件关系失败");
                    throw new ProcessException("保存失败");
                }
            });
        }

        refundPriceDiffDetailList.forEach(refundPriceDiffDetail->{
            Map<String, Object> logisticsDetail = null;
            if(StringUtils.isNotBlank(refundPriceDiffDetail.getActivityFrontId()) && !refundPriceDiffDetail.getActivityFrontId().equals("undefined")){
                Map<String, Object> reqParam = new HashMap<>();
                reqParam.put("snBegin",refundPriceDiffDetail.getBeginSn());
                reqParam.put("snEnd",refundPriceDiffDetail.getEndSn());
                reqParam.put("status",OLogisticsDetailStatus.STATUS_FH.code);
                ArrayList<Object> recordStatusList = new ArrayList<>();
                recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                reqParam.put("recordStatusList",recordStatusList);
                reqParam.put("activityId",refundPriceDiffDetail.getActivityFrontId());
                List<Map<String, Object>> oLogisticsDetails = logisticsDetailMapper.queryCompensateLList(reqParam);
                if(null==oLogisticsDetails){
                    log.info("calculatePriceDiff数据有误异常返回1");
                    throw new ProcessException("保存失败");
                }
                if(oLogisticsDetails.size()!=1){
                    log.info("calculatePriceDiff数据有误异常返回2");
                    throw new ProcessException("保存失败");
                }
                logisticsDetail = oLogisticsDetails.get(0);
            }
            //变更后活动实体
            OActivity oActivity = activityMapper.selectByPrimaryKey(refundPriceDiffDetail.getActivityRealId());
            if(null==oActivity){
                log.info("查询oActivity异常");
                throw new ProcessException("保存失败");
            }
            refundPriceDiffDetail.setId(idService.genId(TabId.o_Refund_price_diff_d));
            refundPriceDiffDetail.setRefundPriceDiffId(priceDiffId);
            refundPriceDiffDetail.setFrontPrice(logisticsDetail!=null?new BigDecimal(logisticsDetail.get("SETTLEMENT_PRICE").toString()):new BigDecimal(0));
            refundPriceDiffDetail.setActivityName(oActivity.getActivityName());
            refundPriceDiffDetail.setActivityWay(oActivity.getActivityWay());
            refundPriceDiffDetail.setActivityRule(oActivity.getActivityRule());
            refundPriceDiffDetail.setPrice(oActivity.getPrice());
            refundPriceDiffDetail.setVender(oActivity.getVender());
            refundPriceDiffDetail.setProModel(oActivity.getProModel());
            refundPriceDiffDetail.setcTime(nowDate);
            refundPriceDiffDetail.setuTime(nowDate);
            refundPriceDiffDetail.setsTime(nowDate);
            refundPriceDiffDetail.setcUser(cUser);
            refundPriceDiffDetail.setuUser(cUser);
            refundPriceDiffDetail.setStatus(Status.STATUS_1.status);
            refundPriceDiffDetail.setVersion(Status.STATUS_0.status);
            int priceDiffDetailInsert = refundPriceDiffDetailMapper.insert(refundPriceDiffDetail);
            if(priceDiffDetailInsert!=1){
                log.info("插入补退差价详情表异常");
                throw new ProcessException("保存失败");
            }
        });
        return AgentResult.ok(priceDiffId);
    }


    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startCompensateActiviy(String id, String cuser) throws Exception {

        if (StringUtils.isBlank(id)) {
            log.info("退补差价提交审批,订单ID为空{}:{}", id, cuser);
            return AgentResult.fail("退补差价提交审批，订单ID为空");
        }
        if (StringUtils.isBlank(cuser)) {
            log.info("退补差价提交审批,操作用户为空{}:{}", id, cuser);
            return AgentResult.fail("退补差价审批中，操作用户为空");
        }
        //更新审批中
        ORefundPriceDiff oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(id);
        if (oRefundPriceDiff.getReviewStatus().equals(AgStatus.Approving.name())) {
            log.info("退补差价提交审批,禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("退补差价提交审批，禁止重复提交审批");
        }

        if (!oRefundPriceDiff.getStatus().equals(Status.STATUS_1.status)) {
            log.info("退补差价提交审批,代理商信息已失效{}:{}", id, cuser);
            return AgentResult.fail("退补差价信息已失效");
        }

        ORefundPriceDiff updateRefundPriceDiff = new ORefundPriceDiff();
        updateRefundPriceDiff.setId(id);
        updateRefundPriceDiff.setReviewStatus(AgStatus.Approving.status);
        int i = refundPriceDiffMapper.updateByPrimaryKeySelective(updateRefundPriceDiff);
        if (1 != i) {
            log.info("退补差价提交审批，更新订单基本信息失败{}:{}", id, cuser);
            throw new MessageException("退补差价提交审批，更新退补差价基本信息失败");
        }

        Map startPar = agentEnterService.startPar(cuser);
        if (null == startPar) {
            log.info("========用户{}{}启动部门参数为空", id, cuser);
            throw new MessageException("启动部门参数为空!");
        }

        //不同的业务类型找到不同的启动流程
        List<Dict> actlist = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ACT_COMPENSATE.name());
        String workId = null;
        for (Dict dict : actlist) {
            workId = dict.getdItemvalue();
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, workId, null, null, startPar);
        if (proce == null) {
            log.info("退补差价提交审批，审批流启动失败{}:{}", id, cuser);
            throw new MessageException("审批流启动失败!");
        }
        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(id);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.COMPENSATE.name());
        record.setActivStatus(AgStatus.Approving.name());
        if (1 != busActRelMapper.insertSelective(record)) {
            log.info("订单提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("审批流启动失败:添加审批关系失败");
        }

        ORefundPriceDiffDetailExample oRefundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
        ORefundPriceDiffDetailExample.Criteria criteria = oRefundPriceDiffDetailExample.createCriteria();
        criteria.andRefundPriceDiffIdEqualTo(id);
        List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(oRefundPriceDiffDetailExample);
        if (null == oRefundPriceDiffDetails) {
            throw new MessageException("审批流启动失败:查询明细异常");
        }
        for (ORefundPriceDiffDetail row : oRefundPriceDiffDetails) {
            Map<String, Object> updateMap = new HashMap<>();
            updateMap.put("setRecordStatus", OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
            updateMap.put("refundPriceDiffDetailId", row.getId());
            //where条件
            updateMap.put("snBegin", row.getBeginSn());
            updateMap.put("snEnd", row.getEndSn());
            updateMap.put("status", OLogisticsDetailStatus.STATUS_FH.code);
            updateMap.put("recordStatus", OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
            int j = logisticsDetailMapper.updateCompensate(updateMap);
            if (!row.getChangeCount().equals(new BigDecimal(j))) {
                log.info("插入补退差价锁定物流明细表异常");
                throw new MessageException("已锁定请核对补差价信息!");
            }
        }
        return AgentResult.ok();
    }


    /**
     * 处理任务
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception{
        try {
            if(agentVo.getApprovalResult().equals("pass")){
                BigDecimal deductAmt = new BigDecimal(0);
                if(agentVo.getDeductCapitalList()!=null && agentVo.getDeductCapitalList().size()!=0){
                    if(agentVo.getDeductCapitalList()!=null)
                    for (ODeductCapital oDeductCapital : agentVo.getDeductCapitalList()) {
                        oDeductCapital.setId(idService.genId(TabId.o_deduct_capital));
                        Date nowDate = new Date();
                        oDeductCapital.setcTime(nowDate);
                        oDeductCapital.setcUtime(nowDate);
                        oDeductCapital.setStatus(Status.STATUS_1.status);
                        oDeductCapital.setVersion(Status.STATUS_0.status);
                        deductAmt = deductAmt.add(oDeductCapital.getcAmount());
                        int insert = deductCapitalMapper.insert(oDeductCapital);
                        if(insert!=1){
                            throw new ProcessException("工作流处理任务DeductCapita异常");
                        }
                    }
                }
                //添加新的附件
                if (agentVo.getRefundPriceDiffFinanceFile()!= null) {
                    agentVo.getRefundPriceDiffFinanceFile().forEach(fileId->{
                        AttachmentRel record = new AttachmentRel();
                        record.setAttId(fileId);
                        record.setSrcId(agentVo.getAgentBusId());
                        record.setcUser(userId);
                        record.setcTime(Calendar.getInstance().getTime());
                        record.setStatus(Status.STATUS_1.status);
                        record.setBusType(AttachmentRelType.ActivityFinanceEdit.name());
                        record.setId(idService.genId(TabId.a_attachment_rel));
                        int i = attachmentRelMapper.insertSelective(record);
                        if (1 != i) {
                            log.info("活动变更财务审批附件关系失败");
                            throw new ProcessException("系统异常");
                        }
                    });
                }
                if(agentVo.getFlag().equals("1")){
                    ORefundPriceDiff oRefundPriceDiff= refundPriceDiffMapper.selectByPrimaryKey(agentVo.getAgentBusId());
                    BigDecimal subtract = oRefundPriceDiff.getRelCompAmt().subtract(agentVo.getoRefundPriceDiffVo().getMachOweAmt());
                    String subtractStr = String.valueOf(subtract);
                    if(subtractStr.contains("-")){
                        agentVo.getoRefundPriceDiffVo().setMachOweAmt(oRefundPriceDiff.getRelCompAmt());
                    }
                    agentVo.getoRefundPriceDiffVo().setRelCompAmt(subtractStr.contains("-")?new BigDecimal(0):subtract);
                    deductAmt = oRefundPriceDiff.getDeductAmt().add(agentVo.getoRefundPriceDiffVo().getMachOweAmt());
                }
                AgentResult agentResult = compensateService.updateTask(agentVo, deductAmt);
                if(!agentResult.isOK()){
                    throw new ProcessException("更新退补差价主表异常");
                }
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

    /**
     * 退补差价处理
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult updateTask(AgentVo agentVo,BigDecimal deductAmt){
        ORefundPriceDiff oRefundPriceDiff= refundPriceDiffMapper.selectByPrimaryKey(agentVo.getAgentBusId());
        ORefundPriceDiffVo updatePriceDiff = new ORefundPriceDiffVo();
        updatePriceDiff.setId(oRefundPriceDiff.getId());
        if(agentVo.getDeductCapitalList()!=null && agentVo.getDeductCapitalList().size()!=0) {
            updatePriceDiff.setDeductAmt(deductAmt);
            BigDecimal relCompAmt = oRefundPriceDiff.getApplyCompAmt().subtract(deductAmt);
            String relCompAmtStr = String.valueOf(relCompAmt);
            updatePriceDiff.setRelCompType(relCompAmtStr.contains("-") ? PriceDiffType.DETAIN_AMT.getValue() : PriceDiffType.REPAIR_AMT.getValue());
            updatePriceDiff.setRelCompAmt(relCompAmtStr.contains("-") ? new BigDecimal(relCompAmtStr.substring(1)) : new BigDecimal(relCompAmtStr));
        }
        if(null!=agentVo.getoRefundPriceDiffVo())
        if(StringUtils.isNotBlank(agentVo.getoRefundPriceDiffVo().getGatherTimeStr())){
            updatePriceDiff.setGatherTime(DateUtil.getDateFromStr(agentVo.getoRefundPriceDiffVo().getGatherTimeStr(),DateUtil.DATE_FORMAT_1));
            updatePriceDiff.setGatherAmt(agentVo.getoRefundPriceDiffVo().getGatherAmt());
        }
        if(agentVo.getFlag().equals("1")){
            updatePriceDiff.setRelCompAmt(agentVo.getoRefundPriceDiffVo().getRelCompAmt());
            updatePriceDiff.setMachOweAmt(agentVo.getoRefundPriceDiffVo().getMachOweAmt());
            updatePriceDiff.setDeductAmt(deductAmt);
        }
        updatePriceDiff.setuTime(new Date());
        int i = refundPriceDiffMapper.updateByPrimaryKeySelective(updatePriceDiff);
        if(i!=1){
            return AgentResult.fail("更新异常");
        }
        return AgentResult.ok();
    }

    /**
     * 完成处理
     * @param proIns
     * @param agStatus
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressCompensateActivity(String proIns, BigDecimal agStatus){

        BusActRelExample example = new BusActRelExample();
        example.or().andActivIdEqualTo(proIns).andActivStatusEqualTo(AgStatus.Approving.name());
        List<BusActRel> list = busActRelMapper.selectByExample(example);
        if (list.size() != 1) {
            log.info("审批任务结束{}{}，未找到审批中的审批和数据关系", proIns, agStatus);
            return null;
        }
        BusActRel rel = list.get(0);
        ORefundPriceDiff oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(rel.getBusId());
        if (null==oRefundPriceDiff) {
            log.info("审批任务结束{}{}，ORefundPriceDiff为null", proIns, agStatus);
            return null;
        }
        oRefundPriceDiff.setReviewStatus(agStatus);
        oRefundPriceDiff.setuTime(new Date());
        int i = refundPriceDiffMapper.updateByPrimaryKeySelective(oRefundPriceDiff);
        if(i!=1){
            throw new ProcessException("更新退补差价数据申请失败");
        }

        ORefundPriceDiffDetailExample oRefundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
        ORefundPriceDiffDetailExample.Criteria criteria = oRefundPriceDiffDetailExample.createCriteria();
        criteria.andRefundPriceDiffIdEqualTo(rel.getBusId());
        List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(oRefundPriceDiffDetailExample);
        oRefundPriceDiffDetails.forEach(row->{
            OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
            OLogisticsDetailExample.Criteria criteria1 = oLogisticsDetailExample.createCriteria();
            criteria1.andSnNumBetween(row.getBeginSn(),row.getEndSn());
            criteria1.andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code);
            criteria1.andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
            List<OLogisticsDetail> oLogisticsDetails = logisticsDetailMapper.selectByExample(oLogisticsDetailExample);
            if(null==oLogisticsDetails){
                throw new ProcessException("退补差价数据完成失败");
            }
            oLogisticsDetails.forEach(oLogisticsDetail->{
                OLogisticsDetail upoLogisticsDetail = new OLogisticsDetail();
                upoLogisticsDetail.setId(oLogisticsDetail.getId());
                upoLogisticsDetail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_HIS.code);
                int update = logisticsDetailMapper.updateByPrimaryKeySelective(upoLogisticsDetail);
                if(1!=update){
                    throw new ProcessException("退补差价数据更新完成失败");
                }
                oLogisticsDetail.setId(idService.genId(TabId.o_logistics_detail));
                oLogisticsDetail.setOptId(row.getId());
                oLogisticsDetail.setOptType(OLogisticsDetailOptType.BCJ.code);
                oLogisticsDetail.setActivityId(row.getActivityRealId());
                oLogisticsDetail.setActivityName(row.getActivityName());
                OActivity activity = orderActivityService.findById(row.getActivityRealId());
                oLogisticsDetail.setgTime(activity.getgTime());
                oLogisticsDetail.setSettlementPrice(row.getPrice());
                oLogisticsDetail.setcTime(new Date());
                oLogisticsDetail.setuTime(new Date());
                oLogisticsDetail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                int insert = logisticsDetailMapper.insert(oLogisticsDetail);
                if(1!=insert){
                    throw new ProcessException("退补差价数据新增完成失败");
                }
            });
        });
        return AgentResult.ok();
    }

    /**
     * 查看退补差价明细
     * @param id
     * @return
     */
    @Override
    public ORefundPriceDiff queryRefDiffDetail(String id){
        if(StringUtils.isBlank(id)){
            return null;
        }
        ORefundPriceDiff oRefundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(id);
        if(null==oRefundPriceDiff){
            return null;
        }
        //查询关联附件
        List<Attachment> attachments = attachmentMapper.accessoryQuery(oRefundPriceDiff.getId(), AttachmentRelType.ActivityEdit.name());
        oRefundPriceDiff.setAttachmentList(attachments);
        //查询财务打款关联附件
        List<Attachment> attachmentFianceList = attachmentMapper.accessoryQuery(oRefundPriceDiff.getId(), AttachmentRelType.ActivityFinanceEdit.name());
        oRefundPriceDiff.setAttachmentFianceList(attachmentFianceList);
        //查询扣除款项
        ODeductCapitalExample oDeductCapitalExample = new ODeductCapitalExample();
        ODeductCapitalExample.Criteria criteria2 = oDeductCapitalExample.createCriteria();
        criteria2.andSourceIdEqualTo(oRefundPriceDiff.getId());
        List<ODeductCapital> oDeductCapitals = deductCapitalMapper.selectByExample(oDeductCapitalExample);
        oRefundPriceDiff.setDeductCapitalList(oDeductCapitals);

        oRefundPriceDiff.setApplyCompName(PriceDiffType.getContentByValue(oRefundPriceDiff.getApplyCompType()));
        oRefundPriceDiff.setRelCompName(PriceDiffType.getContentByValue(oRefundPriceDiff.getRelCompType()));
        ORefundPriceDiffDetailExample oRefundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
        ORefundPriceDiffDetailExample.Criteria criteria = oRefundPriceDiffDetailExample.createCriteria();
        criteria.andRefundPriceDiffIdEqualTo(oRefundPriceDiff.getId());
        List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(oRefundPriceDiffDetailExample);
        oRefundPriceDiff.setRefundPriceDiffDetailList(oRefundPriceDiffDetails);
        for (ORefundPriceDiffDetail oRefundPriceDiffDetail : oRefundPriceDiffDetails) {
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.ACTIVITY_DIS_TYPE.name(),oRefundPriceDiffDetail.getActivityWay());
            oRefundPriceDiffDetail.setActivityWay(dict.getdItemname());
            if(StringUtils.isNotBlank(oRefundPriceDiffDetail.getActivityFrontId()) && !oRefundPriceDiffDetail.getActivityFrontId().equals("undefined")){
                List<Map<String, Object>> oLogisticsDetails = null;
                Map<String, Object> reqParam = new HashMap<>();
                reqParam.put("snBegin",oRefundPriceDiffDetail.getBeginSn());
                reqParam.put("snEnd",oRefundPriceDiffDetail.getEndSn());
                reqParam.put("status",OLogisticsDetailStatus.STATUS_FH.code);
                reqParam.put("activityId",oRefundPriceDiffDetail.getActivityFrontId());
                if(oRefundPriceDiff.getReviewStatus().equals(AgStatus.Create.getValue())){
                    ArrayList<Object> recordStatusList = new ArrayList<>();
                    recordStatusList.add(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                    reqParam.put("recordStatusList",recordStatusList);
                }else{
                    reqParam.put("optId",oRefundPriceDiffDetail.getId());
                }
                oLogisticsDetails = logisticsDetailMapper.queryCompensateLList(reqParam);
                if(null==oLogisticsDetails){
                    log.info("calculatePriceDiff数据有误异常返回1");
                    throw new ProcessException("查询活动异常");
                }
                if(oLogisticsDetails.size()!=1){
                    log.info("calculatePriceDiff数据有误异常返回2");
                    throw new ProcessException("查询活动异常");
                }
                Map<String, Object> oLogisticsDetailMap = oLogisticsDetails.get(0);
                oRefundPriceDiffDetail.setRefundPriceDiffDetailMap(oLogisticsDetailMap);
            }
        }
        return oRefundPriceDiff;
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult compensateAmtEdit(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,List<String> refundPriceDiffFile, String cUser) {

        if(null==refundPriceDiffDetailList){
            throw new ProcessException("退补差价明细数据为空");
        }
        if(null==oRefundPriceDiff){
            throw new ProcessException("退补差价金额数据为空");
        }
        refundPriceDiffDetailList.forEach(row->{
            //查询最新活动
            OActivity oActivity = activityMapper.selectByPrimaryKey(row.getActivityRealId());
            ORefundPriceDiffDetail oRefundPriceDiffDetail = refundPriceDiffDetailMapper.selectByPrimaryKey(row.getId());
            oRefundPriceDiffDetail.setActivityRealId(row.getActivityRealId());
            oRefundPriceDiffDetail.setActivityName(oActivity.getActivityName());
            oRefundPriceDiffDetail.setActivityWay(oActivity.getActivityWay());
            oRefundPriceDiffDetail.setActivityRule(oActivity.getActivityRule());
            oRefundPriceDiffDetail.setPrice(oActivity.getPrice());
            int i = refundPriceDiffDetailMapper.updateByPrimaryKeySelective(oRefundPriceDiffDetail);
            if(i!=1){
                throw new ProcessException("修改退补差价数据失败");
            }
        });
        if(null==oRefundPriceDiff.getId()){
            throw new ProcessException("退补差价数据id为空");
        }
        int k = refundPriceDiffMapper.updateByPrimaryKeySelective(oRefundPriceDiff);
        if(k!=1){
            throw new ProcessException("更新退补差价数据失败");
        }
        //附件修改
        if(null!=refundPriceDiffFile){
            AttachmentRelExample attachmentRelExample = new AttachmentRelExample();
            AttachmentRelExample.Criteria criteria = attachmentRelExample.createCriteria();
            criteria.andSrcIdEqualTo(oRefundPriceDiff.getId());
            criteria.andBusTypeEqualTo(AttachmentRelType.ActivityEdit.name());
            List<AttachmentRel> attachmentRels = attachmentRelMapper.selectByExample(attachmentRelExample);
            attachmentRels.forEach(row->{
                row.setStatus(Status.STATUS_0.status);
                int i = attachmentRelMapper.updateByPrimaryKeySelective(row);
                if (1 != i) {
                    log.info("删除活动变更附件关系失败");
                    throw new ProcessException("删除附件失败");
                }
            });

            refundPriceDiffFile.forEach(row->{
                AttachmentRel record = new AttachmentRel();
                record.setAttId(row);
                record.setSrcId(oRefundPriceDiff.getId());
                record.setcUser(cUser);
                record.setcTime(Calendar.getInstance().getTime());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.ActivityEdit.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                int i = attachmentRelMapper.insertSelective(record);
                if (1 != i) {
                    log.info("活动变更附件关系失败");
                    throw new ProcessException("系统异常");
                }
            });
        }


        return AgentResult.ok();
    }
}
