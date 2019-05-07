package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.ChangeActMachineVo;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.OCashReceivablesVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.PlatFormService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OCashReceivablesService;
import com.ryx.credit.service.order.OldCompensateService;
import com.ryx.credit.service.order.OrderActivityService;
import com.ryx.credit.service.order.ProductService;
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
 * 旧订单补差价处理
 * Created by liudh on 2019/3/13.
 */
@Service("oldCompensateService")
public class OldCompensateServiceImpl implements OldCompensateService {

    private static Logger log = LoggerFactory.getLogger(OldCompensateServiceImpl.class);

    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ProductService productService;
    @Autowired
    private TermMachineService termMachineService;
    @Autowired
    private IdService idService;
    @Autowired
    private ORefundPriceDiffMapper refundPriceDiffMapper;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private OCashReceivablesService cashReceivablesService;
    @Autowired
    private ORefundPriceDiffDetailMapper refundPriceDiffDetailMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private OOrderMapper orderMapper;
    @Autowired
    private OSubOrderMapper subOrderMapper;
    @Autowired
    private OSubOrderActivityMapper subOrderActivityMapper;
    @Autowired
    private OActivityMapper activityMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private OrderActivityService orderActivityService;
    @Autowired
    private RedisService redisService;

    @Override
    public List<Map<String,Object>> getOrderMsgByExcel(List<List<Object>> excelList)throws MessageException{

        List<Map<String,Object>> resultList = new ArrayList<>();
        for (List<Object> excel : excelList) {
            Map<String,Object> resultMap = new HashMap();
            String snBegin = "";
            String snEnd = "";
            String count = "";
            String proModel = "";
            try {
                snBegin = String.valueOf(excel.get(0)).trim();
                snEnd = String.valueOf(excel.get(1)).trim();
                count = String.valueOf(excel.get(2)).trim();
                proModel = String.valueOf(excel.get(3)).trim();
            } catch (Exception e) {
                throw new MessageException(snBegin+"-"+snEnd+", 导入解析文件失败");
            }
            AgentResult agentResult = null;
            try {
                agentResult = orderActivityService.querySnInfoFromBusSystem(snBegin, snEnd, count, proModel);
            } catch (MessageException e) {
                throw new MessageException(snBegin+"-"+snEnd+","+e.getMsg());
            }
            FastMap fastMap = (FastMap)agentResult.getMapData();

            OProduct product = new OProduct();
            product.setProType(String.valueOf(fastMap.get("modelType")));
            List<Map> proMaps = productService.queryGroupByProCode(product);
            for (Map proMap : proMaps) {
                if(String.valueOf(proMap.get("proName")).equals("流量卡")){
                    proMaps.remove(proMap);
                    break;
                }
            }
            resultMap.put("snBegin",snBegin);
            resultMap.put("snEnd",snEnd);
            resultMap.put("count",count);
            resultMap.put("proMaps",proMaps);
            List list = new ArrayList((Set)fastMap.get("activity"));
            OActivity oActivity = (OActivity)list.get(0);
            resultMap.put("activity",oActivity);
            OActivityExample oActivityExample = new OActivityExample();
            OActivityExample.Criteria activityCriteria = oActivityExample.createCriteria();
            activityCriteria.andStatusEqualTo(Status.STATUS_1.status);
            activityCriteria.andActCodeNotEqualTo(oActivity.getActCode());
            activityCriteria.andVenderEqualTo(oActivity.getVender());
            activityCriteria.andProModelEqualTo(oActivity.getProModel());
            activityCriteria.andPlatformEqualTo(oActivity.getPlatform());
            activityCriteria.andProTypeEqualTo(oActivity.getProType());
            Date date = new Date();
            activityCriteria.andBeginTimeLessThanOrEqualTo(date);
            activityCriteria.andEndTimeGreaterThanOrEqualTo(date);
            List<OActivity> oActivities = activityMapper.selectByExample(oActivityExample);
            if(oActivities.size()==0){
                throw new MessageException(snBegin+"到"+snEnd+":无可变更活动,当前活动"+oActivity.getActivityName());
            }
            resultMap.put("changeActivitys",oActivities); //可变更的活动
            resultList.add(resultMap);
        }
        return resultList;

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
    public AgentResult compensateAmtSave(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,
                                         List<String> refundPriceDiffFile, String cUser, List<OCashReceivablesVo> oCashReceivablesVoList)throws Exception{

        try {
            if(PriceDiffType.DETAIN_AMT.code.equals(oRefundPriceDiff.getApplyCompType())){
                if(refundPriceDiffFile.size()==0){
                    return AgentResult.fail("代理商打款必须上传打款凭证");
                }
            }
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
            oRefundPriceDiff.setOrderType(OrderType.OLD.getValue());
            BigDecimal belowPayAmt = new BigDecimal(0);
            BigDecimal shareDeductAmt = new BigDecimal(0);
            for (OCashReceivablesVo oCashReceivablesVo : oCashReceivablesVoList) {
                if(oCashReceivablesVo.getPayType().equals(PayType.YHHK.code)){
                    belowPayAmt = belowPayAmt.add(oCashReceivablesVo.getAmount());
                }else if(oCashReceivablesVo.getPayType().equals(PayType.FRDK.code)){
                    shareDeductAmt = shareDeductAmt.add(oCashReceivablesVo.getAmount());
                }
            }
            oRefundPriceDiff.setBelowPayAmt(belowPayAmt);
            oRefundPriceDiff.setShareDeductAmt(shareDeductAmt);
            int refundDiffInsert = refundPriceDiffMapper.insert(oRefundPriceDiff);
            if(refundDiffInsert!=1){
                log.info("插入补退差价申请表异常");
                throw new MessageException("保存失败");
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

            //打款记录
            AgentResult agentResult = cashReceivablesService.addOCashReceivables(oCashReceivablesVoList,cUser,oRefundPriceDiff.getAgentId(),CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId());
            if(!agentResult.isOK()){
                log.info("退补差价保存打款记录失败1");
                throw new MessageException("保存打款记录失败");
            }
            for (ORefundPriceDiffDetail refundPriceDiffDetail : refundPriceDiffDetailList) {
                if(StringUtils.isBlank(refundPriceDiffDetail.getProId())){
                    throw new MessageException("请选择商品");
                }
                if(StringUtils.isBlank(refundPriceDiffDetail.getActivityRealId())){
                    throw new MessageException("请选择要变更的活动");
                }
                OActivity oldActivity = activityMapper.selectByPrimaryKey(refundPriceDiffDetail.getActivityFrontId());
                if(oldActivity==null){
                    throw new MessageException("旧活动不存在");
                }
                OActivity newActivity = activityMapper.selectByPrimaryKey(refundPriceDiffDetail.getActivityRealId());
                if(newActivity==null){
                    throw new MessageException("新活动不存在");
                }
                refundPriceDiffDetail.setAgentId(oRefundPriceDiff.getAgentId());
                refundPriceDiffDetail.setId(idService.genId(TabId.o_Refund_price_diff_d));
                refundPriceDiffDetail.setRefundPriceDiffId(priceDiffId);
                refundPriceDiffDetail.setFrontPrice(oldActivity.getPrice());
                refundPriceDiffDetail.setActivityName(newActivity.getActivityName());
                refundPriceDiffDetail.setActivityWay(newActivity.getActivityWay());
                refundPriceDiffDetail.setActivityRule(newActivity.getActivityRule());
                refundPriceDiffDetail.setPrice(newActivity.getPrice());
                refundPriceDiffDetail.setVender(newActivity.getVender());
                refundPriceDiffDetail.setProModel(newActivity.getProModel());
                refundPriceDiffDetail.setcTime(nowDate);
                refundPriceDiffDetail.setuTime(nowDate);
                refundPriceDiffDetail.setsTime(nowDate);
                refundPriceDiffDetail.setcUser(cUser);
                refundPriceDiffDetail.setuUser(cUser);
                refundPriceDiffDetail.setStatus(Status.STATUS_1.status);
                refundPriceDiffDetail.setVersion(Status.STATUS_0.status);
                refundPriceDiffDetail.setOrderType(OrderType.OLD.getValue());
                refundPriceDiffDetail.setSendStatus(Status.STATUS_0.status);
                int priceDiffDetailInsert = refundPriceDiffDetailMapper.insert(refundPriceDiffDetail);
                if(priceDiffDetailInsert!=1){
                    log.info("插入补退差价详情表异常");
                    throw new MessageException("保存失败");
                }
            }

            return AgentResult.ok(priceDiffId);
        } catch (MessageException e) {
            log.info("退补差价保存失败");
            throw new MessageException(e.getMessage());
        } catch (Exception e) {
            log.info("退补差价保存失败");
            throw new Exception(e.getMessage());
        }
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
        updateRefundPriceDiff.setVersion(oRefundPriceDiff.getVersion());
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
        Object party = startPar.get("party");
        //不同的业务类型找到不同的启动流程
        List<Dict> actlist = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ACT_COMPENSATE.name());
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
        record.setAgentId(oRefundPriceDiff.getAgentId());
        record.setDataShiro(BusActRelBusType.COMPENSATE.key);

        Agent agent = agentMapper.selectByPrimaryKey(oRefundPriceDiff.getAgentId());
        if(null!=agent)
            record.setAgentName(agent.getAgName());
        if (1 != busActRelMapper.insertSelective(record)) {
            log.info("订单提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("审批流启动失败:添加审批关系失败");
        }

        AgentResult agentResult = cashReceivablesService.startProcing(CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId(),cuser);
        if(!agentResult.isOK()){
            log.info("插入补退差价更新打款信息失败");
            throw new MessageException("补退差价更新打款信息失败");
        }
        return AgentResult.ok();
    }


    /**
     * 查询当前商品列表
     * @param orderId
     * @return
     * @throws MessageException
     */
    @Override
    public List<OSubOrder> queryOrder(String orderId)throws MessageException{

        OOrder oOrder = orderMapper.selectByPrimaryKey(orderId);
        if(oOrder==null){
            throw new MessageException("订单不存在");
        }
        if(oOrder.getStatus().compareTo(Status.STATUS_0.status)==0){
            throw new MessageException("订单不存在");
        }
        OSubOrderExample oSubOrderExample = new OSubOrderExample();
        OSubOrderExample.Criteria criteria = oSubOrderExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andOrderIdEqualTo(oOrder.getId());
        List<OSubOrder> oSubOrders = subOrderMapper.selectByExample(oSubOrderExample);
        return oSubOrders;
    }



    /**
     * 查询当前活动和可变更的活动
     * @param subOrderId
     * @return
     * @throws MessageException
     */
    @Override
    public OSubOrderActivity queryActivity(String subOrderId)throws MessageException{

        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
        OSubOrderActivityExample.Criteria criteria = oSubOrderActivityExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andSubOrderIdEqualTo(subOrderId);
        List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
        if(oSubOrderActivities==null){
            throw new MessageException("活动不存在");
        }
        if(oSubOrderActivities.size()==0){
            throw new MessageException("活动不存在");
        }
        if(oSubOrderActivities.size()!=1){
            throw new MessageException("采购和活动不唯一");
        }
        OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
        return oSubOrderActivity;

    }

    /**
     * 计算变更差价
     * @param oldActivityId
     * @param activityId
     * @param proNum
     * @return
     */
    @Override
    public BigDecimal calculatePriceDiff(String oldActivityId,String activityId,BigDecimal proNum)throws Exception{
        BigDecimal calculatePrice = BigDecimal.ZERO;
        OActivity oldActivity = activityMapper.selectByPrimaryKey(oldActivityId);
        if(null==oldActivity){
            throw new MessageException("旧活动信息不存在");
        }
        OActivity newActivity = activityMapper.selectByPrimaryKey(activityId);
        if(null==newActivity){
            throw new MessageException("新活动信息不存在");
        }
        BigDecimal oldActivityPrice = oldActivity.getPrice().multiply(proNum);
        BigDecimal newActivityPrice = newActivity.getPrice().multiply(proNum);
        calculatePrice = oldActivityPrice.subtract(newActivityPrice);

        return calculatePrice;
    }


    /**
     * 完成处理
     * @param proIns
     * @param agStatus
     * @return
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult compressCompensateActivity(String proIns, BigDecimal agStatus)throws Exception{

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

        AgentResult agentResult = AgentResult.fail();
        if(agStatus.compareTo(AgStatus.Refuse.getValue())==0){
            agentResult = cashReceivablesService.refuseProcing(CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId(),oRefundPriceDiff.getcUser());
        }
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            agentResult = cashReceivablesService.finishProcing(CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId(),oRefundPriceDiff.getcUser());
        }
        if(!agentResult.isOK()){
            throw new ProcessException("更新打款记录失败");
        }
        if(agStatus.compareTo(AgStatus.Approved.getValue())==0){
            ORefundPriceDiffDetailExample oRefundPriceDiffDetailExample = new ORefundPriceDiffDetailExample();
            ORefundPriceDiffDetailExample.Criteria criteria = oRefundPriceDiffDetailExample.createCriteria();
            criteria.andRefundPriceDiffIdEqualTo(rel.getBusId());
            List<ORefundPriceDiffDetail> oRefundPriceDiffDetails = refundPriceDiffDetailMapper.selectByExample(oRefundPriceDiffDetailExample);
            oRefundPriceDiffDetails.forEach(row->{

                try {

    //                ORefundPriceDiffDetailExample priceDiffDetailExample = new ORefundPriceDiffDetailExample();
    //                ORefundPriceDiffDetailExample.Criteria detailcriteria = priceDiffDetailExample.createCriteria();
    //                detailcriteria.andRefundPriceDiffIdEqualTo(oRefundPriceDiff.getId());
    //                List<ORefundPriceDiffDetail> oRefundPriceDiffDetails1 = refundPriceDiffDetailMapper.selectByExample(priceDiffDetailExample);
    //                for (ORefundPriceDiffDetail oRefundPriceDiffDetail : oRefundPriceDiffDetails1) {
    //                    OSubOrderExample oSubOrderExample = new OSubOrderExample();
    //                    OSubOrderExample.Criteria criteria2 = oSubOrderExample.createCriteria();
    //                    criteria2.andProIdEqualTo(oRefundPriceDiffDetail.getProId());
    //                    criteria2.andOrderIdEqualTo(oRefundPriceDiffDetail.getOrderId());
    //                    criteria2.andStatusEqualTo(Status.STATUS_1.status);
    //                    List<OSubOrder> oSubOrders = subOrderMapper.selectByExample(oSubOrderExample);
    //                    if(oSubOrders.size()!=1){
    //                        throw new ProcessException("退补差价:"+oRefundPriceDiffDetail.getOrderId()+":"+oRefundPriceDiffDetail.getProId()+"采购单查询数量不唯一");
    //                    }
    //                    OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
    //                    OSubOrderActivityExample.Criteria criteria3 = oSubOrderActivityExample.createCriteria();
    //                    criteria3.andSubOrderIdEqualTo(oSubOrders.get(0).getId()).andStatusEqualTo(Status.STATUS_1.status);
    //                    List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
    //                    if(oSubOrderActivities.size()!=1){
    //                        throw new ProcessException("退补差价:"+oRefundPriceDiffDetail.getOrderId()+":"+oRefundPriceDiffDetail.getProId()+"采购单活动查询数量不唯一");
    //                    }
    //                    OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
    //                    OActivity oActivity = activityMapper.selectByPrimaryKey(oRefundPriceDiffDetail.getActivityRealId());
    //                    oSubOrderActivity.setActivityId(oActivity.getId());
    //                    oSubOrderActivity.setActivityName(oActivity.getActivityName());
    //                    oSubOrderActivity.setRuleId(oActivity.getRuleId());
    //                    oSubOrderActivity.setActivityRule(oActivity.getActivityRule());
    //                    oSubOrderActivity.setActivityWay(oActivity.getActivityWay());
    //                    oSubOrderActivity.setProModel(oActivity.getProModel());
    //                    oSubOrderActivity.setVender(oActivity.getVender());
    //                    oSubOrderActivity.setgTime(oActivity.getgTime());
    //                    oSubOrderActivity.setuTime(new Date());
    //                    oSubOrderActivity.setuUser(oRefundPriceDiffDetail.getuUser());
    //                    oSubOrderActivity.setPlatform(oActivity.getPlatform());
    //                    oSubOrderActivity.setBusProCode(oActivity.getBusProCode());
    //                    oSubOrderActivity.setBusProName(oActivity.getBusProName());
    //                    oSubOrderActivity.setTermBatchcode(oActivity.getTermBatchcode());
    //                    oSubOrderActivity.setTermBatchname(oActivity.getTermBatchname());
    //                    oSubOrderActivity.setTermtype(oActivity.getTermtype());
    //                    oSubOrderActivity.setTermtypename(oActivity.getTermtypename());
    //                    oSubOrderActivity.setOriginalPrice(oActivity.getOriginalPrice());
    //                    oSubOrderActivity.setPrice(oActivity.getPrice());
    //                    oSubOrderActivity.setPosType(oActivity.getPosType());
    //                    oSubOrderActivity.setPosSpePrice(oActivity.getPosSpePrice());
    //                    oSubOrderActivity.setStandTime(oActivity.getStandTime());
    //                    int j = subOrderActivityMapper.updateByPrimaryKeySelective(oSubOrderActivity);
    //                    if(1!=j){
    //                        throw new ProcessException("退补差价数据更新活动失败");
    //                    }
    //                }


                    //待调整集合 cxinfo 机具的调整  调货明细
                    OOrder oo = orderMapper.selectByPrimaryKey(row.getOrderId());
                    AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oo.getBusId());
                    PlatformType platformType = platFormService.byPlatformCode(agentBusInfo.getBusPlatform());

                    OActivity activity = orderActivityService.findById(row.getActivityRealId());
                    OActivity activity_old = orderActivityService.findById(row.getActivityFrontId());
                    ChangeActMachineVo cav = new ChangeActMachineVo();
                    cav.setBusNum(agentBusInfo.getBusNum());
                    cav.setNewAct(activity.getBusProCode());
                    cav.setOldAct(activity_old.getBusProCode());
                    cav.setOptUser(row.getcUser());

                    String snBeginJson = redisService.hGet(row.getBeginSn() + "," + row.getEndSn(), row.getBeginSn());
                    Map<String, Object> snBeginMap = JsonUtil.jsonToMap(snBeginJson);
                    String snEndJson = redisService.hGet(row.getBeginSn() + "," + row.getEndSn(), row.getEndSn());
                    Map<String, Object> snEndMap = JsonUtil.jsonToMap(snEndJson);

                    cav.setSnStart(row.getBeginSn()+(StringUtils.isBlank(String.valueOf(snBeginMap.get("termCheck")))?"":String.valueOf(snBeginMap.get("termCheck"))));
                    cav.setSnEnd(row.getEndSn()+(StringUtils.isBlank(String.valueOf(snEndMap.get("termCheck")))?"":String.valueOf(snEndMap.get("termCheck"))));

                    cav.setPlatformType(platformType.code);
                    cav.setoRefundPriceDiffDetailId(row.getId());
                    List<OLogisticsDetail> logisticsDetailList = new ArrayList<>();
                    Set<String> snSet = redisService.hGet(row.getBeginSn() + "," + row.getEndSn());
                    for (String sn : snSet) {
                        OLogisticsDetail oLogisticsDetail = new OLogisticsDetail();
                        oLogisticsDetail.setSnNum(sn);
                        oLogisticsDetail.setBusProCode(activity.getBusProCode());
                        logisticsDetailList.add(oLogisticsDetail);
                    }
                    cav.setLogisticsDetailList(logisticsDetailList);
                    cav.setSnNum(String.valueOf(row.getChangeCount()));

                    //cxinfo 调用活动变更接口进行活动的变更
                    try {
                        AgentResult chAgentResult = termMachineService.changeActMachine(cav);
                        row.setSendMsg(chAgentResult.getMsg());
                        if(chAgentResult.isOK()){
                            row.setSendStatus(Status.STATUS_1.status);
                        }else{
                            row.setSendStatus(Status.STATUS_2.status);
                        }
                        refundPriceDiffDetailMapper.updateByPrimaryKeySelective(row);
                    }catch (ProcessException e) {
                        e.printStackTrace();
                        throw new ProcessException(e.getMessage());
                    }catch (Exception e) {
                        e.printStackTrace();
                        throw new ProcessException("处理失败");
                    }
                }catch (ProcessException e) {
                    e.printStackTrace();
                    throw new ProcessException(e.getMessage());
                }catch (Exception e) {
                    e.printStackTrace();
                    throw new ProcessException("处理失败");
                }
            });
        }
        return AgentResult.ok();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult compensateAmtEdit(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList,
                                         List<String> refundPriceDiffFile, String cUser, List<OCashReceivablesVo> cashReceivablesVoList) {

        try {
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
                oRefundPriceDiffDetail.setProId(row.getProId());
                oRefundPriceDiffDetail.setProName(row.getProName());
                int i = refundPriceDiffDetailMapper.updateByPrimaryKeySelective(oRefundPriceDiffDetail);
                if(i!=1){
                    throw new ProcessException("修改退补差价数据失败");
                }
            });
            if(null==oRefundPriceDiff.getId()){
                throw new ProcessException("退补差价数据id为空");
            }
            BigDecimal belowPayAmt = new BigDecimal(0);
            BigDecimal shareDeductAmt = new BigDecimal(0);
            for (OCashReceivablesVo oCashReceivablesVo : cashReceivablesVoList) {
                if(oCashReceivablesVo.getPayType().equals(PayType.YHHK.code)){
                    belowPayAmt = belowPayAmt.add(oCashReceivablesVo.getAmount());
                }else if(oCashReceivablesVo.getPayType().equals(PayType.FRDK.code)){
                    shareDeductAmt = shareDeductAmt.add(oCashReceivablesVo.getAmount());
                }
            }
            oRefundPriceDiff.setBelowPayAmt(belowPayAmt);
            oRefundPriceDiff.setShareDeductAmt(shareDeductAmt);
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
            ORefundPriceDiff refundPriceDiff = refundPriceDiffMapper.selectByPrimaryKey(oRefundPriceDiff.getId());
            cashReceivablesService.addOCashReceivables(cashReceivablesVoList,cUser,refundPriceDiff.getAgentId(),CashPayType.REFUNDPRICEDIFF,oRefundPriceDiff.getId());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("退补差价修改失败");
            throw new ProcessException("退补差价修改失败");
        }
        return AgentResult.ok();
    }

}
