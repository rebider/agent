package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.agent.BusActRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.impl.agent.AccountPaidItemServiceImpl;
import com.ryx.credit.service.order.CompensateService;
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

    private static Logger log = LoggerFactory.getLogger(AccountPaidItemServiceImpl.class);
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

    @Override
    public OSubOrder getOrderMsgByExcel(List<Object> excelList){
        String agentName =  String.valueOf(excelList.get(0));
        String proCom =  String.valueOf(excelList.get(1));
        String model =  String.valueOf(excelList.get(2));
        String snBegin =  String.valueOf(excelList.get(3));
        String snEnd =  String.valueOf(excelList.get(4));
        String count =  String.valueOf(excelList.get(5));
        String subOrderNum =  String.valueOf(excelList.get(6));
        String orderNum =  String.valueOf(excelList.get(7));

        Map<String, Object> reqParam = new HashMap<>();
        reqParam.put("snBegin",snBegin);
        reqParam.put("snEnd",snEnd);
        reqParam.put("status",Status.STATUS_1.status);
        List<Map<String,Object>> oLogistics = logisticsMapper.queryLogisticsList(reqParam);
        if(oLogistics==null && oLogistics.size()==0 && oLogistics.size()!=1){
            log.info("数据有误异常返回01");
            return null;
        }
        Map<String, Object> logisticMap = oLogistics.get(0);
        String proId = String.valueOf(logisticMap.get("PRO_ID"));
        String orderId = String.valueOf(logisticMap.get("ORDER_ID"));

        OSubOrderExample oSubOrderExample = new OSubOrderExample();
        OSubOrderExample.Criteria criteria1 = oSubOrderExample.createCriteria();
        criteria1.andOrderIdEqualTo(orderId);
        criteria1.andProIdEqualTo(proId);
        List<OSubOrder> oSubOrders = subOrderMapper.selectByExample(oSubOrderExample);
        if(oSubOrders==null && oSubOrders.size()==0 && oLogistics.size()!=1){
            log.info("数据有误异常返回02");
            return null;
        }
        OSubOrder oSubOrder = oSubOrders.get(0);
        oSubOrder.setProNum(new BigDecimal(count));
        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
        OSubOrderActivityExample.Criteria criteria2 = oSubOrderActivityExample.createCriteria();
        criteria2.andSubOrderIdEqualTo(oSubOrder.getId());
        List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
        if(null!=oSubOrderActivities && oSubOrderActivities.size()==1){
            oSubOrder.setSubOrderActivity(oSubOrderActivities.get(0));
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

        BigDecimal oldPrice = oSubOrderActivity.getPrice().multiply(oSubOrder.getProNum());
        BigDecimal newPrice = calculateTotalPrice(activityId, proNum);
        BigDecimal resultPrice = oldPrice.subtract(newPrice);

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
            OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
            //变更后活动实体
            OActivity oActivity = activityMapper.selectByPrimaryKey(refundPriceDiffDetail.getActivityRealId());
            if(null==oActivity){
                log.info("查询oActivity异常");
                throw new ProcessException("系统异常");
            }
            refundPriceDiffDetail.setId(idService.genId(TabId.o_Refund_price_diff_detail));
            refundPriceDiffDetail.setRefundPriceDiffId(priceDiffId);
            refundPriceDiffDetail.setFrontPrice(oSubOrderActivity.getPrice());
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
            throw new ProcessException("启动部门参数为空!");
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
}
