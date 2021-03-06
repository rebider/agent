package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.BeanUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.PaymentSendBusPlatformVo;
import com.ryx.credit.service.AgentKafkaService;
import com.ryx.credit.service.agent.CapitalService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.IPaymentDetailService;
import com.ryx.credit.service.order.OrderOffsetService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
    private CapitalService capitalService;
    @Autowired
    private OCashReceivablesMapper oCashReceivablesMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private AgentKafkaService agentKafkaService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private OrderAdjMapper orderAdjMapper;
    @Autowired
    private OrderOffsetService orderOffsetService;
    /**
     * @Author: Zhang Lei
     * @Description: 查询代理商可抵扣欠款, 先根据欠款类型排序，欠款类型相同的根据订单号排序
     * @Date: 17:01 2018/7/24
     */
    @Override
    public List<OPaymentDetail> getCanTakeoutPaymentsByAgentId(String agentId,String adjustType,String adjId) throws ProcessException {
        if (adjustType!=null && AdjustType.ORDER_ADJ.adjustType.equals(adjustType)){
            return  oPaymentDetailMapper.selectQkRefund(agentId, adjId);
        }else {
//            OPaymentDetailExample example = new OPaymentDetailExample();
//
//            //付款类型过滤条件
//            List<String> payTypeList = new ArrayList<>();
//            payTypeList.add(PaymentType.DKFQ.code);
//            payTypeList.add(PaymentType.FRFQ.code);
//
//            //订单状态过滤条件
//            List<BigDecimal> paymentStatusList = new ArrayList<>();
//            paymentStatusList.add(PaymentStatus.DF.code);
//            paymentStatusList.add(PaymentStatus.BF.code);
//            paymentStatusList.add(PaymentStatus.YQ.code);
//
//            example.or()
//                    .andAgentIdEqualTo(agentId)
//                    .andPayTypeIn(payTypeList)
//                    .andPaymentStatusIn(paymentStatusList)
//                    .andPaymentTypeEqualTo(PamentIdType.ORDER_FKD.code)
//            .andStatusEqualTo(Status.STATUS_1.status);
//            //example.setOrderByClause("Payment_type asc");
//            //example.setOrderByClause("order_id asc");
//            example.setOrderByClause("plan_pay_time asc");

            return oPaymentDetailMapper.selectThtkPaymentDetails(agentId);
        }
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
    public List<OPaymentDetail> getPaymentDetails(String paymentId,String adjustType, String... paymentStatus) throws ProcessException {

        if (adjustType!=null && AdjustType.ORDER_ADJ.adjustType.equals(adjustType)){
            return  oPaymentDetailMapper.selectPaymentDetails(paymentId);
        }else {
        //付款类型过滤条件
        List<String> payTypeList = new ArrayList<>();
        payTypeList.add(PaymentType.DKFQ.code);
        payTypeList.add(PaymentType.FRFQ.code);

        OPaymentDetailExample example = new OPaymentDetailExample();
        OPaymentDetailExample.Criteria c = example.or();
        c.andPaymentIdEqualTo(paymentId);
        c.andStatusEqualTo(Status.STATUS_1.status);

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

            maps =  oPaymentDetailMapper.SelectByMonthRefund(map);
          /*  maps = oPaymentDetailMapper.selectShareMoney(map);*/
        }else if (method.equals(GetMethod.ALLCAPITAL.code)) {
            if (StringUtils.isBlank(time)) {
                logger.info("分润查询:{}", "时间为空");
                throw new ProcessException("时间为空");
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("time", time);
            maps = oPaymentDetailMapper.getCapitalDebt(map);
        }else if (method.equals(GetMethod.CAPITAL.code)) {
            if (StringUtils.isBlank(agentId)) {
                logger.info("分润查询:{}", "代理商id为空");
                throw new ProcessException("代理商id为空");
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("agentId", agentId);
            maps = oPaymentDetailMapper.getCapitalDebt(map);
        }
        return maps;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO  fenrunDeduction(List<Map<String, Object>> maps,BigDecimal payStatus) {
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

               /* if (map.get("tools")!=null){
                    OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
                    OPaymentDetailExample.Criteria criteria = oPaymentDetailExample.createCriteria();
                    criteria.andOPdSumIdEqualTo(detailId);
                    criteria.andStatusEqualTo(Status.STATUS_1.status);
                    List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
                    if(oPaymentDetails.size()!=0){
                        for (OPaymentDetail oPaymentDetail:oPaymentDetails) {
                            oPaymentDetail.setPayTime(Calendar.getInstance().getTime());
                            if (payStatus.compareTo(PaySign.FKING.code)==0){//付款中
                                oPaymentDetail.setPaymentStatus(PaymentStatus.FKING.code);
                            }
                            //更新分期明细
                            if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)) {
                                logger.info("付款明细更新数据失败");
                                throw new ProcessException("付款明细更新数据失败");
                            }
                        }
                    }else{
                        throw new ProcessException("没有查找到相关数据");
                    }
                }*/
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
                    //对未扣足的金额生成新的付款明细
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

                        //保证金操作，添加可用余额 添加保证金操作明细
                        Capital capital = capitalMapper.selectByPrimaryKey(oPaymentDetail.getPaymentId());
                        capitalService.profitIncom(oPaymentDetail.getId(),capital.getId(),oPaymentDetail.getRealPayAmount(),"-1");
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
            }
        }




        if (payStatus.compareTo(PaySign.FKING.code)==0){//付款中
            for (Map<String, Object> map : maps) {
                    String detailId = (String) map.get("detailId");//付款汇总id
                    String srcId = (String) map.get("srcId");//付款源id
                    if (StringUtils.isBlank(detailId)) {
                        logger.info("付款汇总idID为空:{}", detailId);
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
                    oPaymentDetail.setSrcId(srcId);
                    if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)) {
                        logger.info("付款明细更新数据失败");
                        throw new ProcessException("付款明细更新数据失败");
                    }
            }
        }

        return ResultVO.success("");
    }

    /**
     * 分润抵扣
     * @param maps
     * @param payStatus
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public ResultVO uploadStatus(List<Map<String, Object>> maps,BigDecimal payStatus){
        if (null == maps && maps.size() < 0) {
            logger.info("更新数据为空:{}", maps);
            return ResultVO.fail("更新数据为空");
        }else if(null != maps || maps.size() >0){
            logger.info("分润抵扣有误:{}", maps);
            return ResultVO.fail("分润抵扣有误");
        }
        if (null==payStatus) {
            logger.info("支付状态为空:{}", maps);
            return ResultVO.fail("支付状态为空");
        }
        if(payStatus.compareTo(PaySign.JQ.code)==0 ||payStatus.compareTo(PaySign.YQ.code)==0||payStatus.compareTo(PaySign.BF.code)==0){
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
                try {
                    AgentResult agentResult= orderOffsetService.OffsetArrears(oPaymentDetails, new BigDecimal(realPayAmount),OffsetPaytype.FRDK.code, srcId);
                    if (!agentResult.isOK()) {
                        throw new MessageException(agentResult.getMsg());
                    }else if (agentResult.getMapData() != null && agentResult.getMapData().size()!=0) {
                        Map<String, Object> resMapCash = agentResult.getMapData();
                        BigDecimal residueAmt = new BigDecimal(resMapCash.get("residueAmt").toString());
                        if (residueAmt.compareTo(new BigDecimal(BigInteger.ZERO))==-1 || residueAmt.compareTo(new BigDecimal(BigInteger.ZERO))==1) {
                            logger.info("分润失败:{}", "抵扣金额大于或小于欠款金额"+residueAmt);
                            throw new MessageException("抵扣金额大于或小于欠款金额"+residueAmt);
                        }
                        List<OPaymentDetail> offsetPaymentDetails=(ArrayList)resMapCash.get("offsetPaymentDetails");
                        AgentResult agentResultCommit = orderOffsetService.OffsetArrearsCommit(new BigDecimal(realPayAmount), OffsetPaytype.FRDK.code, srcId);
                        if (!agentResultCommit.isOK()){
                            throw new MessageException("销分润数据提交更新异常！");
                        }
                    }
                }catch (Exception e){
                    logger.info("分润抵扣失败:{}",srcId);
                }
                OPaymentDetail oPaymentDetail = oPaymentDetails.get(0);
                if(payStatus.compareTo(PaySign.JQ.code)==0){//已结清
                    //判断如果未扣足大于0的话  需要再添加一条数据
                    BigDecimal notDeduction = new BigDecimal(payAmount).subtract(new BigDecimal(realPayAmount));
                    if(notDeduction.compareTo(new BigDecimal(notDeductionAmt))==1 ||notDeduction.compareTo(new BigDecimal(notDeductionAmt))==-1){
                        logger.info("未扣足金额有误");
                        throw new ProcessException("未扣足金额有误");
                    }
                    //对未扣足的金额生成新的付款明细
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

                        //保证金操作，添加可用余额 添加保证金操作明细
                        Capital capital = capitalMapper.selectByPrimaryKey(oPaymentDetail.getPaymentId());
                        capitalService.profitIncom(oPaymentDetail.getId(),capital.getId(),oPaymentDetail.getRealPayAmount(),"-1");
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
                OPaymentDetail o_payment_detail = oPaymentDetailMapper.selectByPrimaryKey(oPaymentDetail.getId());
                if (1 != oPaymentDetailMapper.updateByPrimaryKeySelective(o_payment_detail)) {
                    logger.info("付款明细更新数据失败");
                    throw new ProcessException("付款明细更新数据失败");
                }
            }
        }




        if (payStatus.compareTo(PaySign.FKING.code)==0){//付款中
            for (Map<String, Object> map : maps) {
                String detailId = (String) map.get("detailId");//付款汇总id
                String srcId = (String) map.get("srcId");//付款源id
                if (StringUtils.isBlank(detailId)) {
                    logger.info("付款汇总idID为空:{}", detailId);
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
                oPaymentDetail.setSrcId(srcId);
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

    /**
     *获取机具欠款总和
     * @param agentId
     * @return
     */
    @Override
    public BigDecimal getSumDebt(String agentId){
        HashMap<String, Object> reqMap = new HashMap<>();
        reqMap.put("agentId", agentId);
        List<Map<String, Object>> resultList = oPaymentDetailMapper.getOrderDebt(reqMap);
        if(resultList.size()==0){
            return BigDecimal.ZERO;
        }
        BigDecimal sumDebt = BigDecimal.ZERO;
        for (Map<String, Object> map : resultList) {
            String payAmount = String.valueOf(map.get("PAY_AMOUNT"));
            sumDebt = sumDebt.add(new BigDecimal(payAmount));
        }
        return sumDebt;
    }


    /**
     * 发送首付金额到业务系统
     */
    @Override
    public void sendSFPayMentToPlatform(String orderId) {
        try {
            logger.info("发送订单首付款到kafka {}",orderId);
            OOrder order  = oOrderMapper.selectByPrimaryKey(orderId);
            if(order !=null && Status.STATUS_1.status.compareTo(order.getStatus())==0
                    && AgStatus.Approved.status.compareTo(order.getReviewStatus())==0) {
                logger.info("发送订单首付款到kafka 订单有效 {}",orderId);
                //检查首付款金额
                OPaymentDetailExample example = new OPaymentDetailExample();
                example.or().andOrderIdEqualTo(orderId)
                        .andPayTypeIn(Arrays.asList(PaymentType.SF.code,PaymentType.DK.code))
                        .andPaymentTypeEqualTo(PamentIdType.ORDER_FKD.code)
                        .andPaymentStatusEqualTo(PaymentStatus.JQ.code);
                List<OPaymentDetail>  oPaymentDetails = oPaymentDetailMapper.selectByExample(example);
                if(oPaymentDetails.size()>0) {
                    //附件
                    AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
                    PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
                    if(platForm!=null) {
                        OPaymentDetail detail = oPaymentDetails.get(0);
                        List<String> attrs = new ArrayList<>();
                        List<Attachment> attr = attachmentMapper.accessoryQuery(orderId, AttachmentRelType.Order.name());
                        if(attr.size()>0) {
                            attrs = attr.stream().map(att -> {
                                return att.getAttDbpath();
                            }).collect(Collectors.toList());
                        }
                        PaymentSendBusPlatformVo paymentSendBusPlatformVo = new PaymentSendBusPlatformVo();
                        paymentSendBusPlatformVo.setAg(order.getAgentId());
                        paymentSendBusPlatformVo.setAmount(detail.getRealPayAmount().setScale(2, BigDecimal.ROUND_HALF_UP).toString());
                        paymentSendBusPlatformVo.setAmountType(detail.getPayType());
                        paymentSendBusPlatformVo.setBusNum(agentBusInfo.getBusNum());
                        paymentSendBusPlatformVo.setCreateTime(DateUtil.format(order.getcTime(), DateUtil.DATE_FORMAT_5));
                        paymentSendBusPlatformVo.setOrderNum(order.getId());
                        paymentSendBusPlatformVo.setFqflow(detail.getId());
                        paymentSendBusPlatformVo.setPayType(PamentSrcType.XXBK.code);
                        paymentSendBusPlatformVo.setOptType(Status.STATUS_1.status + "");
                        paymentSendBusPlatformVo.setPlatform(platForm.getPlatformType());
                        paymentSendBusPlatformVo.setImageList(attrs);
                        try {
                            agentKafkaService.sendPayMentMessage(order.getAgentId(),
                                    detail.getId(),
                                    order.getBusId(),
                                    agentBusInfo.getBusNum(),
                                    KafkaMessageType.PAYMENT,
                                    KafkaMessageTopic.agent_Payment.code,
                                    JSONObject.toJSONString(paymentSendBusPlatformVo)
                                    );
                        } catch (Exception e) {
                            logger.info("kafka接口调用失败 订单无效 {}",orderId);
                            e.printStackTrace();
                        }
                    }else{
                        logger.info("发送订单首付款到kafka PlatForm未找打 {}",orderId);
                    }
                }else{
                    logger.info("发送订单首付款到kafka 交易明细为空 {}",orderId);
                }
            }else{
                logger.info("发送订单首付款到kafka 订单无效 {}",orderId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("通知kafka消息失败 sendSFPayMentToPlatform {}",orderId);
        }
    }

    @Override
    public void sendBkPayMentToPlatform(String orderId) {

    }

    @Override
    public void sendRefundMentToPlatform(String adjId) {
        try {
            logger.info("发送订单退款信息到kafka {}",adjId);
            OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(adjId);
            OOrder order  = oOrderMapper.selectByPrimaryKey(orderAdj.getOrderId());
            if(order !=null && Status.STATUS_1.status.compareTo(order.getStatus())==0
                    && AgStatus.Approved.status.compareTo(order.getReviewStatus())==0) {
                logger.info("发送订单退款信息到kafka 订单有效 {}",adjId);

                //附件
                AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
                PlatForm platForm = platFormMapper.selectByPlatFormNum(agentBusInfo.getBusPlatform());
                if(platForm!=null) {
                    List<String> attrs = new ArrayList<>();
                    PaymentSendBusPlatformVo paymentSendBusPlatformVo = new PaymentSendBusPlatformVo();
                    paymentSendBusPlatformVo.setAg(order.getAgentId());
                    paymentSendBusPlatformVo.setAmount(orderAdj.getRealRefundAmo().setScale(2, BigDecimal.ROUND_HALF_UP).abs().toString());
                    paymentSendBusPlatformVo.setAmountType(PaymentType.TK.code);//退款
                    paymentSendBusPlatformVo.setBusNum(agentBusInfo.getBusNum());
                    paymentSendBusPlatformVo.setCreateTime(DateUtil.format(order.getcTime(), DateUtil.DATE_FORMAT_5));
                    paymentSendBusPlatformVo.setOrderNum(order.getId());
                    paymentSendBusPlatformVo.setFqflow(orderAdj.getId());
                    paymentSendBusPlatformVo.setPayType(PamentSrcType.ORDER_ADJ_REFUND.code);
                    paymentSendBusPlatformVo.setOptType(Status.STATUS_0.status + "");//退款
                    paymentSendBusPlatformVo.setPlatform(platForm.getPlatformType());
                    paymentSendBusPlatformVo.setImageList(attrs);
                    try {
                        agentKafkaService.sendPayMentMessage(order.getAgentId(),
                                orderAdj.getId(),
                                order.getBusId(),
                                agentBusInfo.getBusNum(),
                                KafkaMessageType.PAYMENT,
                                KafkaMessageTopic.agent_Payment.code,
                                JSONObject.toJSONString(paymentSendBusPlatformVo));
                    } catch (Exception e) {
                        logger.info("kafka接口调用失败 订单无效 {}",adjId);
                        e.printStackTrace();
                    }
                }else{
                    logger.info("发送订单退款到kafka PlatForm未找打 {}",adjId);
                }

            }else{
                logger.info("发送订单退款到kafka 订单无效 {}",adjId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("通知kafka消息失败 sendRefundMentToPlatform {}",adjId);
        }
    }


}
