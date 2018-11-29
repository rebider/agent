package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.CapitalMapper;
import com.ryx.credit.dao.order.OCashReceivablesMapper;
import com.ryx.credit.dao.order.OOrderMapper;
import com.ryx.credit.dao.order.OPaymentDetailMapper;
import com.ryx.credit.dao.order.OPaymentMapper;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.Capital;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Zhang Lei
 * @Description: 代理商欠款(包含机具订单欠款 、 其他欠款)
 * @Date: 16:31 2018/7/24
 */
@Service("paymentDetailService")
public class PaymentDetailServiceImpl implements IPaymentDetailService {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(PaymentDetailServiceImpl.class);

    @Autowired
    OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    OPaymentMapper paymentMapper;
    @Autowired
    OPaymentMapper oPaymentMapper;
    @Autowired
    OOrderMapper oOrderMapper;
    @Autowired
    CapitalMapper capitalMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private OCashReceivablesMapper oCashReceivablesMapper;

    /**
     * @Author: Zhang Lei
     * @Description: 查询代理商可抵扣欠款, 先根据欠款类型排序，欠款类型相同的根据订单号排序
     * @Date: 17:01 2018/7/24
     */
    @Override
    public List<OPaymentDetail> getCanTakeoutPaymentsByAgentId(String agentId) throws ProcessException {
        OPaymentDetailExample example = new OPaymentDetailExample();

        //付款类型过滤条件
        List<String> payTypeList = new ArrayList<>();
        payTypeList.add(PaymentType.DKFQ.code);
        payTypeList.add(PaymentType.FRFQ.code);

        //订单状态过滤条件
        List<BigDecimal> paymentStatusList = new ArrayList<>();
        paymentStatusList.add(PaymentStatus.DF.code);
        paymentStatusList.add(PaymentStatus.BF.code);
        paymentStatusList.add(PaymentStatus.YQ.code);

        example.or()
                .andAgentIdEqualTo(agentId)
                .andPayTypeIn(payTypeList)
                .andPaymentStatusIn(paymentStatusList)
                .andPaymentTypeEqualTo(PamentIdType.ORDER_FKD.code);
        //example.setOrderByClause("Payment_type asc");
        //example.setOrderByClause("order_id asc");
        example.setOrderByClause("plan_pay_time asc");
        return oPaymentDetailMapper.selectByExample(example);
    }

    /**
     * @Author: Zhang Lei
     * @Description: 查询付款单
     * @Date: 9:32 2018/7/28
     */
    @Override
    public OPayment getPaymentById(String paymentId) throws ProcessException {
        return paymentMapper.selectByPrimaryKey(paymentId);
    }

    /**
     * @Author: Zhang Lei
     * @Description: 查询一个付款单下付款明细（只查分期类型），可根据付款状态筛选
     * @Date: 9:32 2018/7/28
     */
    @Override
    public List<OPaymentDetail> getPaymentDetails(String paymentId, String... paymentStatus) throws ProcessException {

        //付款类型过滤条件
        List<String> payTypeList = new ArrayList<>();
        payTypeList.add(PaymentType.DKFQ.code);
        payTypeList.add(PaymentType.FRFQ.code);

        OPaymentDetailExample example = new OPaymentDetailExample();
        OPaymentDetailExample.Criteria c = example.or();
        c.andPaymentIdEqualTo(paymentId);

        if (paymentStatus != null && paymentStatus.length > 0) {
            List<BigDecimal> paymentStatusList = new ArrayList<>();
            for (String status : paymentStatus) {
                paymentStatusList.add(PaymentStatus.valueOf(status).code);
                c.andPaymentStatusIn(paymentStatusList);
            }
        }

        c.andPayTypeIn(payTypeList);
        c.andPaymentTypeEqualTo(PamentIdType.ORDER_FKD.code);
        example.setOrderByClause("plan_num asc");
        return oPaymentDetailMapper.selectByExample(example);
    }


    @Override
    public List<Map<String, Object>> getShareMoney(String method, String agentId, String time) throws ParseException {
        List<Map<String, Object>> maps = null;
        if (StringUtils.isBlank(method)) {
            logger.info("分润查询:{}", "获取方式为空");
            throw new ProcessException("获取方式为空");
        }
        if (method.equals(GetMethod.AGENTORDER.code)) {
            //代理商订单分期--需要agentId 和 time
            if (StringUtils.isBlank(agentId)) {
                logger.info("分润查询:{}", "代理商id为空");
                throw new ProcessException("代理商id为空");
            }
            if (StringUtils.isBlank(time)) {
                logger.info("分润查询:{}", "时间为空");
                throw new ProcessException("时间为空");
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("agentId", agentId);
            map.put("time", time);
            maps = oPaymentDetailMapper.selectShareMoney(map);
        } else if (method.equals(GetMethod.AGENTDATE.code)) {
            //所有当月分期---只需要时间
            if (StringUtils.isBlank(time)) {
                logger.info("分润查询:{}", "时间为空");
                throw new ProcessException("时间为空");
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("time", time);
            maps = oPaymentDetailMapper.selectShareMoney(map);
        }
        return maps;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO uploadStatus(List<Map<String, Object>> maps,BigDecimal payStatus) {
        if (null == maps && maps.size() < 0) {
            logger.info("更新数据为空:{}", maps);
            return ResultVO.fail("更新数据为空");
        }
        if (null==payStatus) {
            logger.info("支付状态为空:{}", maps);
            return ResultVO.fail("支付状态为空");
        }
        if(payStatus.compareTo(PaySign.JQ.code)==0){
            for (Map<String, Object> map : maps) {
            String detailId = (String) map.get("detailId");//付款明细id
            String srcId = (String) map.get("srcId");//付款源id
            String payAmount = (String) map.get("mustDeductionAmtSum");//应扣
            String realPayAmount = (String) map.get("actualDeductionAmtSum");//实扣
            String notDeductionAmt = (String) map.get("notDeductionAmt");//未扣足
            String deductTime = (String) map.get("deductTime");//扣款时间
            if (StringUtils.isBlank(detailId)) {
                logger.info("付款明细ID为空:{}", detailId);
                throw new ProcessException("付款明细ID为空");
            }
            if (StringUtils.isBlank(srcId)) {
                logger.info("付款源ID为空:{}", srcId);
                throw new ProcessException("付款源ID为空");
            }
            if (StringUtils.isBlank(payAmount)) {
                logger.info("应扣金额为空:{}", payAmount);
                throw new ProcessException("应扣金额为空");
            }
            if (StringUtils.isBlank(realPayAmount)) {
                logger.info("实扣金额为空:{}", realPayAmount);
                throw new ProcessException("实扣金额为空");
            }
            if (StringUtils.isBlank(notDeductionAmt)) {
                logger.info("未扣足金额为空:{}", notDeductionAmt);
                throw new ProcessException("未扣足金额为空");
            }
            if (StringUtils.isBlank(deductTime)) {
                logger.info("扣款时间为空:{}", deductTime);
                throw new ProcessException("扣款时间为空");
            }
            //分别去查询付款单数据  和    付款明细的数据
            OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
            OPaymentDetailExample.Criteria criteria = oPaymentDetailExample.createCriteria();
            criteria.andIdEqualTo(detailId);
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
            if (1 != oPaymentDetails.size())
                throw new ProcessException("没有查找到相关数据");
            OPaymentDetail oPaymentDetail = oPaymentDetails.get(0);
            oPaymentDetail.setPayTime(Calendar.getInstance().getTime());
            if (payStatus.compareTo(PaySign.FKING.code)==0){//付款中
                oPaymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
            }else if(payStatus.compareTo(PaySign.JQ.code)==0){//已结清
                if(new BigDecimal(payAmount).compareTo(oPaymentDetail.getPayAmount())==1 ||new BigDecimal(payAmount).compareTo(oPaymentDetail.getPayAmount())==-1){//判断传的参数应扣款和库里是否一致
                    logger.info("应扣金额有误");
                    throw new ProcessException("应扣金额有误");
                }
                oPaymentDetail.setRealPayAmount(new BigDecimal(realPayAmount));
//                查询库里的实扣金额
                if(new BigDecimal(payAmount).compareTo(new BigDecimal(realPayAmount))==0){
                    oPaymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
                }else{
                    oPaymentDetail.setPaymentStatus(PaymentStatus.YF.code);
                }
                //判断如果未扣足大于0的话  需要再添加一条数据
                BigDecimal notDeduction = new BigDecimal(payAmount).subtract(new BigDecimal(realPayAmount));
                if(notDeduction.compareTo(new BigDecimal(notDeductionAmt))==1 ||notDeduction.compareTo(new BigDecimal(notDeductionAmt))==-1){
                    logger.info("未扣足金额有误");
                    throw new ProcessException("未扣足金额有误");
                }
                if (notDeduction.compareTo(BigDecimal.ZERO)==1){
                    OPaymentDetail detail = new OPaymentDetail();
                    BeanUtils.copyProperties(oPaymentDetail,detail);
                    detail.setId(idService.genId(TabId.o_payment_detail));
                    detail.setPayAmount(notDeduction);
                    detail.setPaymentStatus(PaymentStatus.DF.code);
                    detail.setRealPayAmount(BigDecimal.ZERO);
                    if(1!= oPaymentDetailMapper.insertSelective(detail)){
                        logger.info("拆分失败");
                        throw new ProcessException("拆分失败");
                    }
                }
                oPaymentDetail.setSrcId(srcId);
                oPaymentDetail.setSrcType(PamentSrcType.FENRUN_DIKOU.code);

                //判断源类型   如果是付款单则更新付款金额   如果是保证金则更新资金表的抵扣金额
                if (PamentIdType.ORDER_BZJ.code.equals(oPaymentDetail.getPaymentType())){

                    Capital capital = capitalMapper.selectByPrimaryKey(oPaymentDetail.getPaymentId());

                    BigDecimal cFqInAmount = capital.getcFqInAmount()==null?new BigDecimal(0):capital.getcFqInAmount();

                    capital.setcFqInAmount(cFqInAmount.add(oPaymentDetail.getRealPayAmount()));

                    if (1!=capitalMapper.updateByPrimaryKeySelective(capital)){
                        logger.info("资金记录抵扣金额更新失败");
                        throw new ProcessException("资金记录抵扣金额更新失败");
                    }
                    //付款单分期抵扣处理
                }else if (PamentIdType.ORDER_FKD.code.equals(oPaymentDetail.getPaymentType())) {
                    OPaymentExample oPaymentExample = new OPaymentExample();
                    OPaymentExample.Criteria criteri = oPaymentExample.createCriteria();
                    criteri.andStatusEqualTo(Status.STATUS_1.status);
                    criteri.andIdEqualTo(oPaymentDetail.getPaymentId());
                    List<OPayment> oPayments = oPaymentMapper.selectByExample(oPaymentExample);
                    if (1 != oPayments.size())
                        throw new ProcessException("没有查找到相关数据");
                    OPayment oPaymentss = oPayments.get(0);
                    if (oPaymentss.getRealAmount() == null) {
                        oPaymentss.setRealAmount(BigDecimal.ZERO);
                    }
                    //已付款金额
                    oPaymentss.setRealAmount(oPaymentss.getRealAmount().add(oPaymentDetail.getRealPayAmount()));
                    //待付款金额
                    oPaymentss.setOutstandingAmount(oPaymentss.getOutstandingAmount().subtract(oPaymentDetail.getRealPayAmount()));
                    if (1 != oPaymentMapper.updateByPrimaryKeySelective(oPaymentss)) {
                        logger.info("付款单更新数据失败");
                        throw new ProcessException("付款单更新数据失败");
                    }
                    //查询当前订单是否还有未结清的订单
                    OPaymentDetailExample detailExample = new OPaymentDetailExample();
                    OPaymentDetailExample.Criteria riteria = detailExample.createCriteria();
                    riteria.andPaymentIdEqualTo(oPaymentDetail.getPaymentId());
                    riteria.andStatusEqualTo(Status.STATUS_1.status);
                    riteria.andPaymentStatusIn(Arrays.asList(PaymentStatus.DF.code, PaymentStatus.BF.code, PaymentStatus.YQ.code, PaymentStatus.FKING.code));
                    List<OPaymentDetail> oPaymentDetai = oPaymentDetailMapper.selectByExample(detailExample);

                    if (null == oPaymentDetai && oPaymentDetai.size() == 0 && oPaymentss.getOutstandingAmount().compareTo(BigDecimal.ZERO) == 0) {

                        //说明没有未结清的订单
                        OPayment payment = new OPayment();
                        payment.setId(oPaymentDetail.getPaymentId());
                        payment.setPayStatus(PayStatus.CLOSED.code);
                        payment.setPayCompletTime(Calendar.getInstance().getTime());
                        if (1 != paymentMapper.updateByPrimaryKeySelective(payment)) {
                            logger.info("付款明细更新数据失败");
                            throw new ProcessException("付款明细更新数据失败");
                        }
                        //更新订单的状态
                        OOrder oOrder = new OOrder();
                        oOrder.setId(oPaymentss.getOrderId());
                        oOrder.setClearStatus(ClearStatus.CLEARED.status);
                        if (1 != oOrderMapper.updateByPrimaryKeySelective(oOrder)) {
                            logger.info("订单更新数据失败");
                            throw new ProcessException("订单更新数据失败");
                        }
                    }
                }else if(PamentIdType.ORDER_XX.code.equals(oPaymentDetail.getPaymentType())){
                    OPaymentDetail oPaymentDe = oPaymentDetailMapper.selectById(detailId);
                    if (null==oPaymentDe){
                        logger.info("无付款明细数据");
                        throw new ProcessException("无付款明细数据");
                    }
                    OCashReceivables oCashReceivables = oCashReceivablesMapper.selectByPrimaryKey(oPaymentDe.getPaymentId());
                    OCashReceivables receivables = new OCashReceivables();
                    if(oCashReceivables.getAmount().compareTo(oCashReceivables.getRealAmount())==0){
                        receivables.setId(oPaymentDe.getPaymentId());
                        receivables.setPayStatus(PaySign.JQ.code);
                        if (1!=oCashReceivablesMapper.updateByPrimaryKeySelective(receivables)){
                            logger.info("更新现款明细数据失败");
                            throw new ProcessException("更新现款明细数据失败");
                        }
                    }else{
                        receivables.setId(oPaymentDe.getPaymentId());
                        receivables.setPayStatus(PaySign.FKING.code);
                        receivables.setRealAmount(receivables.getRealAmount().add(oPaymentDe.getRealPayAmount()));
                        if (1!=oCashReceivablesMapper.updateByPrimaryKeySelective(receivables)){
                            logger.info("更新现款明细数据失败");
                            throw new ProcessException("更新现款明细数据失败");
                        }
                    }
                }
            }
            //更新分期明细
            if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)) {
                logger.info("付款明细更新数据失败");
                throw new ProcessException("付款明细更新数据失败");
            }
        } }

        if (payStatus.compareTo(PaySign.FKING.code)==0){//付款中
            for (Map<String, Object> map : maps) {
                String detailId = (String) map.get("detailId");//付款明细id
                String srcId = (String) map.get("srcId");//付款源id
                if (StringUtils.isBlank(detailId)) {
                    logger.info("付款明细ID为空:{}", detailId);
                    throw new ProcessException("付款明细ID为空");
                }
                if (StringUtils.isBlank(srcId)) {
                    logger.info("付款源ID为空:{}", srcId);
                    throw new ProcessException("付款源ID为空");
                }
                //分别去查询付款单数据  和    付款明细的数据
                OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
                OPaymentDetailExample.Criteria criteria = oPaymentDetailExample.createCriteria();
                criteria.andIdEqualTo(detailId);
                criteria.andStatusEqualTo(Status.STATUS_1.status);
                List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
                if (1 != oPaymentDetails.size())
                    throw new ProcessException("没有查找到相关数据");
                OPaymentDetail oPaymentDetail = oPaymentDetails.get(0);
                oPaymentDetail.setPayTime(Calendar.getInstance().getTime());
                oPaymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
                if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)) {
                    logger.info("付款明细更新数据失败");
                    throw new ProcessException("付款明细更新数据失败");
                }
            }
        }

        return ResultVO.success("");
    }


    /**
     * 生成付款明细
     * @param srcId
     * @param pamentIdType
     * @param orderId
     * @param paymentType
     * @param payAmount
     * @param RealPayAmount
     * @param planPayTime
     * @param planNum
     * @param paymentStatus
     * @param agentId
     * @param cuser
     * @return
     * @throws Exception
     */
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public AgentResult createPayMentDetail(String batchCode,String srcId,PamentIdType pamentIdType,String orderId,PaymentType paymentType,BigDecimal payAmount,BigDecimal RealPayAmount, Date planPayTime,BigDecimal planNum,PaymentStatus paymentStatus,String agentId,String cuser) throws Exception {
        Calendar c = Calendar.getInstance();
        if(StringUtils.isNotBlank(batchCode)) {
             batchCode = c.getTime().getTime() + "";
         }
        OPaymentDetail record = new OPaymentDetail();
        record.setId(idService.genId(TabId.o_payment_detail));
        record.setBatchCode(batchCode);
        record.setPaymentId(srcId);
        record.setPaymentType(pamentIdType.code);
        record.setOrderId(orderId);
        record.setPayType(paymentType.code);
        record.setPayAmount(payAmount);
        record.setRealPayAmount(RealPayAmount);
        record.setPlanPayTime(planPayTime);
        record.setPlanNum(planNum);
        record.setAgentId(agentId);
        record.setPaymentStatus(paymentStatus.code);
        record.setcUser(cuser);
        record.setcDate(c.getTime());
        record.setStatus(Status.STATUS_1.status);
        record.setVersion(Status.STATUS_1.status);
        if (1 != oPaymentDetailMapper.insert(record)) {
            logger.info("OPaymentDetail:明细生成失败:srcid:{},pamentIdType:{},paymentType:{}，明细ID:{},金额{},已付金额{},状态{}",
                    srcId,
                    pamentIdType.msg,
                    paymentType.msg,
                    record.getId(),
                    record.getPayAmount(),
                    record.getRealPayAmount(),
                    paymentStatus.msg);
            throw new MessageException("分期处理");
        }
        logger.info("OPaymentDetail:明细生成成功:srcid:{},pamentIdType:{},paymentType:{}，明细ID:{},金额{},已付金额{},状态{}",
                srcId,
                pamentIdType.msg,
                paymentType.msg,
                record.getId(),
                record.getPayAmount(),
                record.getRealPayAmount(),
                paymentStatus.msg);
        return AgentResult.ok(record);

    }


    @Override
    public String createBatchCode() {
          return Calendar.getInstance().getTime().getTime() + "";
    }


}
