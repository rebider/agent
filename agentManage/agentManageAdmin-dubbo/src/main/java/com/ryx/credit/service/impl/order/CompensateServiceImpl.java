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
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.CompensateService;
import com.ryx.credit.service.order.IAccountAdjustService;
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
 * 补差价处理
 * Created by liudh on 2018/7/24.
 */
@Service("compensateService")
public class CompensateServiceImpl implements CompensateService {

    private static Logger log = LoggerFactory.getLogger(CompensateServiceImpl.class);
    @Autowired
    private OLogisticsMapper logisticsMapper;
    @Autowired
    private OSubOrderMapper subOrderMapper;
    @Autowired
    private OSubOrderActivityMapper subOrderActivityMapper;
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
    private IAccountAdjustService accountAdjustService;


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
    public OSubOrder getOrderMsgByExcel(List<Object> excelList)throws ProcessException{
        String agentName = "";
        String proCom = "";
        String proModel = "";
        String snBegin = "";
        String snEnd = "";
        String count = "";
        String orderNum = "";
        try {
            agentName =  String.valueOf(excelList.get(0));
            proCom =  String.valueOf(excelList.get(1));
            proModel =  String.valueOf(excelList.get(2));
            snBegin =  String.valueOf(excelList.get(3));
            snEnd =  String.valueOf(excelList.get(4));
            count =  String.valueOf(excelList.get(5));
            orderNum =  String.valueOf(excelList.get(6));
        } catch (Exception e) {
            throw new ProcessException("导入解析文件异常");
        }
        Map<String, Object> reqParam = new HashMap<>();
        reqParam.put("snBegin",snBegin);
        reqParam.put("snEnd",snEnd);
        reqParam.put("status",Status.STATUS_1.status);
        reqParam.put("orderId",orderNum);
        reqParam.put("proCom",proCom);
        reqParam.put("proModel",proModel);
        List<Map<String,Object>> oLogistics = logisticsMapper.queryLogisticsList(reqParam);
        if(oLogistics==null){
            log.info("数据有误异常返回01");
            throw new ProcessException("商品数据异常");
        }
        if(oLogistics.size()==0 || oLogistics.size()!=1){
            log.info("数据有误异常返回02");
            throw new ProcessException("未找到该商品");
        }
        Map<String, Object> logisticMap = oLogistics.get(0);
        String proId = String.valueOf(logisticMap.get("PRO_ID"));
        String orderId = String.valueOf(logisticMap.get("ORDER_ID"));

        OSubOrderExample oSubOrderExample = new OSubOrderExample();
        OSubOrderExample.Criteria criteria1 = oSubOrderExample.createCriteria();
        criteria1.andOrderIdEqualTo(orderId);
        criteria1.andProIdEqualTo(proId);
        List<OSubOrder> oSubOrders = subOrderMapper.selectByExample(oSubOrderExample);
        if(oSubOrders==null){
            log.info("数据有误异常返回03");
            throw new ProcessException("商品数据异常");
        }
        if(oSubOrders.size()==0 || oLogistics.size()!=1){
            log.info("数据有误异常返回04");
            throw new ProcessException("未找到该商品");
        }
        OSubOrder oSubOrder = oSubOrders.get(0);
        oSubOrder.setProNum(new BigDecimal(count));
        oSubOrder.setSnBegin(snBegin);
        oSubOrder.setSnEnd(snEnd);
        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
        OSubOrderActivityExample.Criteria criteria2 = oSubOrderActivityExample.createCriteria();
        criteria2.andSubOrderIdEqualTo(oSubOrder.getId());
        List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
        if(null==oSubOrderActivities){
            log.info("数据有误异常返回05");
            throw new ProcessException("商品活动内部服务器异常");
        }
        if(oSubOrderActivities.size()==1){
            OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
            BigDecimal gTime = oSubOrderActivity.getgTime();
            gTime = gTime.multiply(new BigDecimal(24)).multiply(new BigDecimal(60)).multiply(new BigDecimal(60)).multiply(new BigDecimal(1000));
            long activityCtime = oSubOrderActivity.getcTime().getTime();
            long nowTime = new Date().getTime();
            if((new BigDecimal(nowTime-activityCtime)).compareTo(gTime)==1){
                log.info("商品活动超出保价时间");
                throw new ProcessException("商品活动超出保价时间");
            }
            oSubOrder.setSubOrderActivity(oSubOrderActivity);
        }
        return oSubOrder;
    }

    /**
     * 计算变更差价
     * @param subOrderId
     * @param oldActivityId
     * @param activityId
     * @param proNum
     * @return
     */
    @Override
    public BigDecimal calculatePriceDiff(String subOrderId,String oldActivityId,String activityId,BigDecimal proNum){
        BigDecimal resultPrice = new BigDecimal(0);
        //之前参加过活动
        if(StringUtils.isNotBlank(oldActivityId)){
            OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
            OSubOrderActivityExample.Criteria criteria = oSubOrderActivityExample.createCriteria();
            criteria.andSubOrderIdEqualTo(subOrderId);
            criteria.andActivityIdEqualTo(oldActivityId);
            List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
            if(null==oSubOrderActivities){
                log.info("calculatePriceDiff数据有误异常返回1");
                return null;
            }
            if(oSubOrderActivities.size()!=1){
                log.info("calculatePriceDiff数据有误异常返回2");
                return null;
            }
            OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
            OSubOrder oSubOrder = subOrderMapper.selectByPrimaryKey(oSubOrderActivity.getSubOrderId());

            BigDecimal oldPrice = oSubOrderActivity.getPrice().multiply(proNum);
            BigDecimal newPrice = calculateTotalPrice(activityId, proNum);
            resultPrice = oldPrice.subtract(newPrice);
        }else{
            OSubOrder oSubOrder = subOrderMapper.selectByPrimaryKey(subOrderId);
            BigDecimal oldPrice = oSubOrder.getProPrice().multiply(proNum);
            BigDecimal newPrice = calculateTotalPrice(activityId, proNum);
            resultPrice = oldPrice.subtract(newPrice);
        }
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
            throw new ProcessException("系统异常");
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
                    throw new ProcessException("系统异常");
                }
            });
        }

        refundPriceDiffDetailList.forEach(refundPriceDiffDetail->{
            OSubOrderActivity oSubOrderActivity = null;
            if(StringUtils.isNotBlank(refundPriceDiffDetail.getActivityFrontId())){
                OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
                OSubOrderActivityExample.Criteria criteria = oSubOrderActivityExample.createCriteria();
                criteria.andSubOrderIdEqualTo(refundPriceDiffDetail.getSubOrderId());
                criteria.andActivityIdEqualTo(refundPriceDiffDetail.getActivityFrontId());
                List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
                if(null==oSubOrderActivities){
                    log.info("查询oSubOrderActivities异常");
                    throw new ProcessException("系统异常");
                }
                if(oSubOrderActivities.size()!=1){
                    log.info("查询oSubOrderActivities.size()异常");
                    throw new ProcessException("系统异常");
                }
                oSubOrderActivity = oSubOrderActivities.get(0);
            }
            //变更后活动实体
            OActivity oActivity = activityMapper.selectByPrimaryKey(refundPriceDiffDetail.getActivityRealId());
            if(null==oActivity){
                log.info("查询oActivity异常");
                throw new ProcessException("系统异常");
            }
            refundPriceDiffDetail.setId(idService.genId(TabId.o_Refund_price_diff_detail));
            refundPriceDiffDetail.setRefundPriceDiffId(priceDiffId);
            refundPriceDiffDetail.setFrontPrice(oSubOrderActivity!=null?oSubOrderActivity.getPrice():new BigDecimal(0));
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
                throw new ProcessException("系统异常");
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
            throw new ProcessException("退补差价提交审批，更新退补差价基本信息失败");
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
            throw new ProcessException("审批流启动失败!");
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
            throw new ProcessException("审批流启动失败:添加审批关系失败");
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
            if(StringUtils.isNotBlank(oRefundPriceDiffDetail.getActivityFrontId())){
                OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
                OSubOrderActivityExample.Criteria criteria1 = oSubOrderActivityExample.createCriteria();
                criteria1.andSubOrderIdEqualTo(oRefundPriceDiffDetail.getSubOrderId());
                criteria1.andActivityIdEqualTo(oRefundPriceDiffDetail.getActivityFrontId());
                List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
                if(null==oSubOrderActivities){
                    return null;
                }
                OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
                Dict dict1 = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.ACTIVITY_DIS_TYPE.name(),oSubOrderActivity.getActivityWay());
                oSubOrderActivity.setActivityWay(dict1.getdItemname());
                oRefundPriceDiffDetail.setSubOrderActivity(oSubOrderActivity);
            }
        }
        return oRefundPriceDiff;
    }

}
