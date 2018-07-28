package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.Status;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.order.*;
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
    public AgentResult compensateAmtSave(ORefundPriceDiff oRefundPriceDiff, List<ORefundPriceDiffDetail> refundPriceDiffDetailList, String cUser){

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
            refundPriceDiffDetail.setStatus(Status.STATUS_1.status);
            refundPriceDiffDetail.setVersion(Status.STATUS_0.status);
            int priceDiffDetailInsert = refundPriceDiffDetailMapper.insert(refundPriceDiffDetail);
            if(priceDiffDetailInsert!=1){
                log.info("插入补退差价详情表异常");
                throw new ProcessException("系统异常");
            }
        });
        return AgentResult.ok();
    }
}
