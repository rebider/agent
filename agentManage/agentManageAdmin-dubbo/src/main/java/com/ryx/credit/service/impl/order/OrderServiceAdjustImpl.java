package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.activity.entity.ActRuTask;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.common.util.agentUtil.StageUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.ActRuTaskService;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

import static com.ryx.credit.common.enumc.OffsetPaytype.DDTZ;
import static java.util.stream.Collectors.toList;

/**
 * Created by RYX on 2018/7/13.
 */
@Service("orderAdjustService")
public class OrderServiceAdjustImpl implements OrderAdjustService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceAdjustImpl.class);

    @Autowired
    private OOrderMapper orderMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private OSubOrderMapper oSubOrderMapper;
    @Autowired
    private OPaymentMapper oPaymentMapper;
    @Autowired
    private OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private OReceiptProMapper oReceiptProMapper;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IResourceService iResourceService;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private IPaymentDetailService paymentDetailService;
    @Autowired
    private OrderAdjMapper orderAdjMapper;
    @Autowired
    private OrderAdjDetailMapper orderAdjDetailMapper;
    @Autowired
    DataHistoryMapper dataHistoryMapper;
    @Autowired
    private OPaymentMapper paymentMapper;
    @Autowired
    private OSupplementMapper oSupplementMapper;
    @Autowired
    private SettleAccountsMapper settleAccountsMapper;
    @Autowired
    private AgentColinfoMapper agentColinfoMapper;
    @Autowired
    private OrderOffsetService orderOffsetService;
    @Autowired
    private OrderAdjAccountMapper orderAdjAccountMapper;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private OrderAdjustService orderAdjustService;

    @Override
    public AgentResult refreshPaymentDetail(String orderId) {
        //支付信息
        OPaymentExample oPaymentExample = new OPaymentExample();
        oPaymentExample.or().andStatusEqualTo(Status.STATUS_1.status).andOrderIdEqualTo(orderId);
        List<OPayment> oPaymentList = oPaymentMapper.selectByExample(oPaymentExample);
        if (oPaymentList.size() != 1) {
            return AgentResult.fail("支付信息错误");
        }
        OPayment oPayment = oPaymentList.get(0);
        List<BigDecimal> paymentStatus = Stream.of(PaymentStatus.DF.code,PaymentStatus.YQ.code,PaymentStatus.BF.code).collect(toList()) ;
        OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
        oPaymentDetailExample.or()
                .andStatusEqualTo(Status.STATUS_1.status)
                .andPaymentStatusIn(paymentStatus)
                .andPaymentIdEqualTo(oPayment.getId()).andOrderIdEqualTo(orderId);
        oPaymentDetailExample.setOrderByClause(" pay_time asc, plan_num asc, plan_pay_time asc ");
        List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);

        //计算待付款分期款
        BigDecimal[] price = {new BigDecimal(0)};
        Integer[] outstandingNum = {0};
        if (null!=oPaymentDetails && oPaymentDetails.size()>0){
            oPaymentDetails.forEach(oPaymentDetail -> {
                if (oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.BF.code) == 0){
                    price[0] = price[0].add(oPaymentDetail.getPayAmount().subtract(oPaymentDetail.getRealPayAmount()));
                }else {
                    price[0] = price[0].add(oPaymentDetail.getPayAmount());
                    outstandingNum[0]++;
                }
            });
        }
        FastMap f = FastMap.fastMap("outstandingAmount", price[0]);//待还金额
        f.putKeyV("outstandingNum",outstandingNum[0]);
        return AgentResult.ok(f);


    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public AgentResult saveAdjInfo(OrderUpModelVo orderUpModelVo, Map map) throws Exception {
        AgentResult agentResult = AgentResult.fail("保存失败!");
        boolean adjFlag = true;
        List<AdjProVo> adjPros = orderUpModelVo.getAdjPros();
        OOrder order = orderMapper.selectByPrimaryKey(orderUpModelVo.getOrderId());
        if (null == order){
            agentResult.setMsg("该订单不存在!");
            return agentResult;
        }
        if (new BigDecimal(orderUpModelVo.getRefundAmount()).compareTo(BigDecimal.ZERO) != 0){
            List<OrderAdjAccountVo>  accounts = orderUpModelVo.getAccounts();
            if (null == accounts || accounts.size() == 0){
                agentResult.setMsg("请填写账户信息！");
                return agentResult;
            }
        }
        order.setOrderStatus(OrderStatus.LOCK.status);
        if (orderMapper.updateByPrimaryKey(order)!=1){
            agentResult.setMsg("更新订单为["+OrderStatus.LOCK.msg+"]失败!");
            return agentResult;
        };
        //检查配货
        for (AdjProVo adjProVo:adjPros){
            OSubOrderExample osubOrderExample = new OSubOrderExample();
            osubOrderExample.or().andIdEqualTo(adjProVo.getoSubId()).andStatusEqualTo(Status.STATUS_1.status);
            List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(osubOrderExample);
            if (oSubOrders.size() > 0) {
                logger.info("开始核对可调整数量");
                FastMap par = FastMap.fastMap("subOrderId",oSubOrders.get(0).getId());
//                BigDecimal countPlans = receiptPlanMapper.planCountTotal(orderAdj.getOrderId(), oSubOrders.get(0).getProId());//排单数量
                BigDecimal oReceiptPros = oReceiptProMapper.receiptCountTotal(orderUpModelVo.getOrderId(), oSubOrders.get(0).getProId());//配货数量
                BigDecimal enableNum = oSubOrders.get(0).getProNum().subtract(oReceiptPros);
                if (new BigDecimal(adjProVo.getAdjNum()).compareTo(enableNum) > 0){
                    agentResult.setMsg("可调整机具数量错误!");
                    adjFlag = false;
                    break;
                }
            }
        }

        if (!adjFlag){
            return agentResult;
        }
        OPaymentExample oPaymentExample = new OPaymentExample();
        oPaymentExample.or().andOrderIdEqualTo(orderUpModelVo.getOrderId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OPayment> payments = paymentMapper.selectByExample(oPaymentExample);
        //生成自定义分期
        OPayment payment = payments.get(0);
        String paymentMethod = payment.getPayMethod();
        OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
        oPaymentDetailExample.or()
                .andOrderIdEqualTo(orderUpModelVo.getOrderId())
                .andPaymentTypeEqualTo(PamentIdType.ORDER_FKD.code)
                .andStatusEqualTo(Status.STATUS_1.status)
                .andPaymentStatusIn(Arrays.asList(PaymentStatus.DF.code, PaymentStatus.BF.code, PaymentStatus.YQ.code));
        oPaymentDetailExample.setOrderByClause(" pay_time asc, plan_num asc, plan_pay_time asc ");
        List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
        BigDecimal unpaySize = BigDecimal.ZERO;
        for (OPaymentDetail oPaymentDetail:oPaymentDetails){
            if (oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.DF.code) == 0 || oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.YQ.code) == 0 ){
                unpaySize = unpaySize.add(new BigDecimal("1"));
            }
        }
        OrderAdj orderAdj = new OrderAdj();
        orderAdj.setId(idService.genIdInTran(TabId.o_order_adj));
        orderAdj.setOrderId(orderUpModelVo.getOrderId());            //订单id
        orderAdj.setAgentId(orderUpModelVo.getAgentId());            //代理商ID
        orderAdj.setAdjTm(new Date());                               //申请时间
        orderAdj.setAdjUserId(String.valueOf(map.get("userId")));    //调整人id
        orderAdj.setOrgOAmo(order.getoAmo());                        //原订单总计金额
        orderAdj.setOrgIncentiveAmo(order.getIncentiveAmo());        //原订单优惠金额
        orderAdj.setOrgPayAmo(order.getPayAmo());                    //原订单应付金额
        orderAdj.setOrgPlanNum(unpaySize);//剩余分期次数
        orderAdj.setStagesAmount(new BigDecimal(orderUpModelVo.getAdjRepayment()));//预计分期金额
        orderAdj.setRefundAmount(new BigDecimal(orderUpModelVo.getRefundAmount()));//退款金额
        orderAdj.setOrgPaymentId(oPaymentDetails.size()==0?"":oPaymentDetails.get(0).getBatchCode());//原还款计划批次号
        orderAdj.setReson(orderUpModelVo.getReson());
        orderAdj.setRefundMethod(new BigDecimal(orderUpModelVo.getRefundMethod()));
        orderAdj.setProRefundAmount(BigDecimal.ZERO);
        orderAdj.setOffsetAmount(BigDecimal.ZERO);
        orderAdj.setStatus(Status.STATUS_1.status);
        orderAdj.setVersion(Status.STATUS_0.status);
        orderAdj.setReviewsStat(AgStatus.Create.status);
        orderAdj.setLogicalVersion(BigDecimal.ONE);
        BigDecimal difAmount = BigDecimal.ZERO;
        for (AdjProVo adjProVo:adjPros){
            OSubOrder oSubOrder = oSubOrderMapper.selectByPrimaryKey(adjProVo.getoSubId());
            OrderAdjDetail orderAdjDetail = new OrderAdjDetail();
            orderAdjDetail.setAdjId(orderAdj.getId());
            orderAdjDetail.setAdjNum(new BigDecimal(adjProVo.getAdjNum()));
            orderAdjDetail.setOrgProNum(oSubOrder.getProNum());
            orderAdjDetail.setProNum(oSubOrder.getProNum().subtract(new BigDecimal(adjProVo.getAdjNum())));
            orderAdjDetail.setStatus(Status.STATUS_1.status);
            orderAdjDetail.setDifAmount(adjProVo.getCalPrice());
            orderAdjDetail.setSubOrderId(adjProVo.getoSubId());
            orderAdjDetail.setId(idService.genIdInTran(TabId.o_order_adj_detail));
            orderAdjDetail.setVersion(Status.STATUS_0.status);
            difAmount = difAmount.add(adjProVo.getCalPrice());
            orderAdjDetailMapper.insert(orderAdjDetail);
        }
        orderAdj.setDifAmount(difAmount);
        List<String> attFiles = orderUpModelVo.getFiles();
        AttachmentRel recordAtt = new AttachmentRel();
        if (attFiles.size()>0){
            attFiles.forEach(attfile->{
                recordAtt.setAttId(attfile);
                recordAtt.setSrcId(orderAdj.getId());
                recordAtt.setcUser(orderAdj.getAdjUserId());
                recordAtt.setcTime(orderAdj.getAdjTm());
                recordAtt.setStatus(Status.STATUS_1.status);
                recordAtt.setBusType(AttachmentRelType.orderAdjust.name());
                recordAtt.setId(idService.genId(TabId.a_attachment_rel));
                logger.info("添加订单调整附件关系,订单调整ID{},附件ID{}",orderAdj.getId(),attfile);
                if (1 != attachmentRelMapper.insertSelective(recordAtt)) {
                    logger.info("订单调整:{}", "添加订单调整附件关系失败");
                    throw new ProcessException("添加订单调整附件关系失败");
                }
            });
        }

        for (OrderAdjAccountVo adjAccount:orderUpModelVo.getAccounts()){
            OrderAdjAccount account = new OrderAdjAccount();
            account.setId(idService.genId(TabId.O_ORDER_ADJ_ACCOUNT));
            account.setAdjId(orderAdj.getId());
            account.setOrderId(orderAdj.getOrderId());
            account.setType(adjAccount.getType());
            account.setRefundAccount(adjAccount.getRefundAccount());
            account.setAccountName(adjAccount.getAccountName());
            account.setAccountBank(adjAccount.getAccountBank());
            account.setBranchLineNum(adjAccount.getBranchLineNum());
            account.setAllLineNum(adjAccount.getAllLineNum());
            account.setRefundAmo(BigDecimal.ZERO);
            account.setRefundStat(RefundStat.UNREFUND.key);
            account.setStatus(Status.STATUS_1.status);
            account.setVersion(Status.STATUS_1.status);
            if (1 !=orderAdjAccountMapper.insert(account) ){
                logger.info("订单调整:{}", "保存账户信息失败");
                throw new ProcessException("保存账户信息失败");
            }

            List<String> accountFile = adjAccount.getAccountFile();
            if (null != accountFile && accountFile.size()>0){
                for (String s : accountFile) {
                    AttachmentRel recordAccountAtt = new AttachmentRel();
                    recordAccountAtt.setAttId(s);
                    recordAccountAtt.setSrcId(account.getId());
                    recordAccountAtt.setcUser(orderAdj.getAdjUserId());
                    recordAccountAtt.setcTime(orderAdj.getAdjTm());
                    recordAccountAtt.setStatus(Status.STATUS_1.status);
                    recordAccountAtt.setBusType(AttachmentRelType.orderAdjustDk.name());
                    recordAccountAtt.setId(idService.genId(TabId.a_attachment_rel));
                    logger.info("添加订单调整附件关系,订单调整ID{},打款附件ID{}",orderAdj.getId(),accountFile);
                    if (1 != attachmentRelMapper.insertSelective(recordAccountAtt)) {
                        logger.info("订单调整:{}", "添加订单调整打款附件关系失败");
                        throw new ProcessException("添加订单调整打款附件关系失败");
                    }
                }
            }else {
                throw new MessageException("未上传打款附件");
            }

        }

        List<String> data = orderUpModelVo.getCustomStagingUser();

        Date da = Calendar.getInstance().getTime();
        orderAdj.setNewPaymentId(da.getTime()+"");
        //记录分期金额
        BigDecimal fqje = BigDecimal.ZERO;
        if(StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(orderUpModelVo.getCustomStaging())) {
            //初始化生成明细
            switch (paymentMethod) {
                case "FKFQ":
                    //生成自定义预分期数据
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        Date DownPaymentDate = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(DownPaymentDate);
                        c.add(Calendar.MONTH,-1);
                        c.set(Calendar.DAY_OF_MONTH, 1);
                        for (int i = 0; i < data.size(); i++) {

                            c.add(Calendar.MONTH,1);

                            String amount = data.get(i);
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(da.getTime()+"");
                            record.setPaymentId(payment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(payment.getOrderId());
                            record.setPayType(PaymentType.DKFQ.code);
                            record.setPayAmount(new BigDecimal(amount));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime(c.getTime());
                            record.setPlanNum(new BigDecimal(i).add(BigDecimal.ONE));
                            record.setAgentId(payment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DS.code);
                            record.setcUser(payment.getUserId());
                            record.setcDate(da);
                            record.setStatus(Status.STATUS_0.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                throw new MessageException("分期处理");
                            }
                            fqje = fqje.add(record.getPayAmount());

                        }
                    }
                    break;
                case "FRFQ":
                    //生成自定义预分期数据
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        Date DownPaymentDate = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(DownPaymentDate);
                        c.add(Calendar.MONTH,-1);
                        c.set(Calendar.DAY_OF_MONTH, 1);
                        for (int i = 0; i < data.size(); i++) {

                            c.add(Calendar.MONTH,1);

                            String amount = data.get(i);
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(da.getTime()+"");
                            record.setPaymentId(payment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(payment.getOrderId());
                            record.setPayType(PaymentType.FRFQ.code);
                            record.setPayAmount(new BigDecimal(amount));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime(c.getTime());
                            record.setPlanNum(new BigDecimal(i).add(BigDecimal.ONE));
                            record.setAgentId(payment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DS.code);
                            record.setcUser(payment.getUserId());
                            record.setcDate(da);
                            record.setStatus(Status.STATUS_0.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                throw new MessageException("分期处理");
                            }
                            fqje = fqje.add(record.getPayAmount());
                        }
                    }
                    break;
                case "XXDK":
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        throw new MessageException("线下打款不支持自定义分期");
                    }
                    break;
                case "SF1"://首付+分润分期
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        Date DownPaymentDate = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(DownPaymentDate);
                        c.add(Calendar.MONTH,-1);
                        c.set(Calendar.DAY_OF_MONTH, 1);
                        for (int i = 0; i < data.size(); i++) {

                            c.add(Calendar.MONTH,1);

                            String amount = data.get(i);
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(da.getTime()+"");
                            record.setPaymentId(payment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(payment.getOrderId());
                            record.setPayType(PaymentType.FRFQ.code);
                            record.setPayAmount(new BigDecimal(amount));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime(c.getTime());
                            record.setPlanNum(new BigDecimal(i).add(BigDecimal.ONE));
                            record.setAgentId(payment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DS.code);
                            record.setcUser(payment.getUserId());
                            record.setcDate(da);
                            record.setStatus(Status.STATUS_0.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                throw new MessageException("分期处理");
                            }
                            fqje = fqje.add(record.getPayAmount());
                        }
                    }
                    break;
                case "SF2"://打款分期
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        Date DownPaymentDate = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(DownPaymentDate);
                        c.add(Calendar.MONTH,-1);
                        c.set(Calendar.DAY_OF_MONTH, 1);
                        for (int i = 0; i < data.size(); i++) {
                            c.add(Calendar.MONTH,1);
                            String amount = data.get(i);
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(da.getTime()+"");
                            record.setPaymentId(payment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(payment.getOrderId());
                            record.setPayType(PaymentType.DKFQ.code);
                            record.setPayAmount(new BigDecimal(amount));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime(c.getTime());
                            record.setPlanNum(new BigDecimal(i).add(BigDecimal.ONE));
                            record.setAgentId(payment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DS.code);
                            record.setcUser(payment.getUserId());
                            record.setcDate(da);
                            record.setStatus(Status.STATUS_0.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                throw new MessageException("分期处理");
                            }
                            fqje = fqje.add(record.getPayAmount());
                        }
                    }
                    break;
                case "QT"://抵扣金额必须等于待付金额
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        throw new MessageException("其他付款方式不支持自定义分期");
                    }
                    break;
            }

            if(payment.getDeductionAmount()==null){
                payment.setDeductionAmount(BigDecimal.ZERO);
            }
            if(payment.getDownPayment()==null){
                payment.setDownPayment(BigDecimal.ZERO);
            }
            //TODO 检查付款明细及打款信息是否和订单金额一致 订单原应付金额 - 订单调整差价  = 已付金额 + 分期金额
            if(payment.getPayAmount().subtract(difAmount).compareTo(payment.getRealAmount().add(fqje))!=0){
                throw new MessageException("分期金额配置失败:金额总计应为"+ payment.getPayAmount().subtract(payment.getRealAmount()).subtract(difAmount));
            }
        }
        if (1 == orderAdjMapper.insert(orderAdj)) {
            agentResult.setStatus(AgentResult.OK);
            agentResult.setMsg("保存成功");
        }

        //是否启动流程
        if (org.apache.commons.lang.StringUtils.isNotEmpty(orderUpModelVo.getIsApproveWhenSubmit()) && "1".equals(orderUpModelVo.getIsApproveWhenSubmit())) {
            //启动流程审批
            String userId = String.valueOf(map.get("userId"));
            AgentResult result = startOrderAdjust(orderAdj.getId(), userId);
            agentResult.setMsg("提交审批成功");
            if (!result.isOK()) {
                throw new Exception(result.getMsg());
            }
        }
        return agentResult;
    }

    @Override
    public PageInfo queryAgentUpModelList(Map par, Page page) {
        PageInfo pageInfo = new PageInfo();
        if (par == null) return pageInfo;
        if(null!=par.get("userId")) {
            Long userId = (Long) par.get("userId");
            List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
            par.put("platfromPerm", platfromPerm);
        }
        par.put("page", page);
        pageInfo.setTotal(orderAdjMapper.selectAgentUpModelViewCount(par));
        pageInfo.setRows(orderAdjMapper.selectAgentUpModelView(par,page));
        return pageInfo;
    }

    @Override
    public AgentResult loadUpModelInfo(String adjId) {
        FastMap res= FastMap.fastFailMap();
        try {
            //订单
            OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(adjId);
            res.putKeyV("orderAdj", orderAdj);
            AgentColinfo agentColinfo = agentColinfoMapper.selectByAgentId(orderAdj.getAgentId());
            res.putKeyV("cloType",agentColinfo.getCloType());
            OrderAdjDetailExample orderAdjDetailExample = new OrderAdjDetailExample();
            orderAdjDetailExample.or().andAdjIdEqualTo(orderAdj.getId()).andStatusEqualTo(Status.STATUS_1.status);
            List<OrderAdjDetail> orderAdjDetails = orderAdjDetailMapper.selectByExample(orderAdjDetailExample);
            for (OrderAdjDetail orderAdjDetail : orderAdjDetails) {
                OSubOrderExample osubOrderExample = new OSubOrderExample();
                osubOrderExample.or().andOrderIdEqualTo(orderAdj.getOrderId()).andStatusEqualTo(Status.STATUS_1.status);
                List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(osubOrderExample);
                for (OSubOrder oSubOrder:oSubOrders){
                    if (oSubOrder.getId().equals(orderAdjDetail.getSubOrderId()) ){
                        FastMap fastMap = FastMap.fastMap("subOrderId", oSubOrder.getId());
                        BigDecimal oReceiptPros = oReceiptProMapper.receiptCountTotal(orderAdj.getOrderId(), oSubOrder.getProId());//配货数量
                        BigDecimal countPlans = receiptPlanMapper.planCountTotal(orderAdj.getOrderId(), oSubOrder.getProId());//排单数量
                        orderAdjDetail.setAdjustCount(oSubOrder.getProNum().subtract(oReceiptPros));
                    }
                }
            }
//        //查询扣款款项
//        ODeductCapitalExample deductCapitalExample = new ODeductCapitalExample();
//        deductCapitalExample.or().andSourceIdEqualTo(adjId);
//        List<ODeductCapital> deductCapitals = deductCapitalMapper.selectByExample(deductCapitalExample);
//        res.putKeyV("deductCapitals",deductCapitals);

            OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
            OPaymentDetailExample oPaymentDetailExample1 = new OPaymentDetailExample();
            if (orderAdj.getReviewsStat().compareTo(AgStatus.Approved.status) == 0){
                oPaymentDetailExample.or()
                        .andBatchCodeEqualTo(orderAdj.getNewPaymentId()==null?"":orderAdj.getNewPaymentId())
                        .andOrderIdEqualTo(orderAdj.getOrderId())
                        .andStatusEqualTo(Status.STATUS_1.status);
                oPaymentDetailExample1.or()
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andOrderIdEqualTo(orderAdj.getOrderId())
                        .andBatchCodeEqualTo(orderAdj.getNewPaymentId()==null?"":orderAdj.getNewPaymentId());
                oPaymentDetailExample1.setOrderByClause(" pay_time asc, plan_num asc, plan_pay_time asc ");
                List<OPaymentDetail> oPaymentDetails1 = oPaymentDetailMapper.selectByExample(oPaymentDetailExample1);
                res.putKeyV("beginDate",oPaymentDetails1.size()==0?"":oPaymentDetails1.get(0).getPlanPayTime());
            }else {
                oPaymentDetailExample.or()
                        .andBatchCodeEqualTo(orderAdj.getNewPaymentId()==null?"":orderAdj.getNewPaymentId())
                        .andOrderIdEqualTo(orderAdj.getOrderId())
                        .andPaymentStatusEqualTo(PaymentStatus.DS.code)
                        .andStatusEqualTo(Status.STATUS_0.status);
                oPaymentDetailExample1.or()
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andOrderIdEqualTo(orderAdj.getOrderId())
                        .andPaymentStatusIn(Arrays.asList(PaymentStatus.DF.code, PaymentStatus.BF.code, PaymentStatus.YQ.code))
                        .andBatchCodeEqualTo(orderAdj.getOrgPaymentId()==null?"":orderAdj.getOrgPaymentId());
                Date DownPaymentDate = new Date();
                Calendar c = Calendar.getInstance();
                c.setTime(DownPaymentDate);
                c.set(Calendar.DAY_OF_MONTH, 1);
                res.putKeyV("beginDate",c.getTime());
            }
            oPaymentDetailExample.setOrderByClause(" pay_time asc, plan_num asc, plan_pay_time asc ");
            List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
            res.putKeyV("oPaymentDetails", oPaymentDetails);
            BigDecimal Arrears = BigDecimal.ZERO;
            for (OPaymentDetail oPaymentDetail:oPaymentDetails){
                Arrears = Arrears.add(oPaymentDetail.getPayAmount());
            }
            res.putKeyV("arrears",Arrears);
            //查找账户信息
            OrderAdjAccount account = new OrderAdjAccount();
            account.setAdjId(orderAdj.getId());
            account.setStatus(Status.STATUS_1.status);
            account.setOrderId(orderAdj.getOrderId());

            List<OrderAdjAccountVo> orderAdjAccounts = orderAdjAccountMapper.selectListByExample(account);


            for (OrderAdjAccountVo orderAdjAccount:orderAdjAccounts){
                List<Attachment> attachments = agentQueryService.accessoryQuery(orderAdjAccount.getId(), AttachmentRelType.orderAdjustDk.name());
                orderAdjAccount.setAttachments(attachments);
                List<Attachment> attachmentsrefund = agentQueryService.accessoryQuery(orderAdjAccount.getId(), AttachmentRelType.orderAdjust_refund.name());
                orderAdjAccount.setTkattachments(attachmentsrefund);
            }
            res.putKeyV("accounts",orderAdjAccounts);
            OPaymentExample oPaymentExample = new OPaymentExample();
            oPaymentExample.or().andStatusEqualTo(Status.STATUS_1.status).andOrderIdEqualTo(orderAdj.getOrderId());
            List<OPayment> oPaymentList = oPaymentMapper.selectByExample(oPaymentExample);
            if (oPaymentList.size() != 1) {
                return AgentResult.fail("支付信息错误");
            }

            OPayment oPayment = oPaymentList.get(0);
            res.putKeyV("orderAdjDetails",orderAdjDetails);
            String refundMethod = RefundMehod.getContentByValue(orderAdj.getRefundMethod());
            res.putKeyV("refundMethod",refundMethod);
            return AgentResult.ok(res);
        }catch (Exception e){
            logger.error("查询订单调整信息失败,adjId{},失败原因{}",adjId,e);
            return AgentResult.fail("查询订单调整信息失败!"+e);
        }
    }

    /**
     * 启动订单调整审批
     * @param id
     * @param cuser
     * @return startOrderAdjust
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startOrderAdjust(String id, String cuser) throws Exception {
        OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(id);
        if (StringUtils.isBlank(id)) {
            logger.info("订单调整提交审批，订单调整ID为空{}:{}", id, cuser);
            return AgentResult.fail("订单调整提交审批，订单调整ID为空!");
        }
        if (StringUtils.isBlank(cuser)) {
            logger.info("订单调整提交审批，操作用户为空{}:{}", id, cuser);
            return AgentResult.fail("订单调整审批中，操作用户为空！");
        }
        if (!orderAdj.getAdjUserId().equals(cuser)) {
            logger.info("提交审批的用户必须是创建订单的用户{}:{}", id, cuser);
            return AgentResult.fail("提交审批的用户必须是创建订单的用户！");
        }
        if (!orderAdj.getStatus().equals(Status.STATUS_1.status)) {
            logger.info("订单调整提交审批，订单调整信息已失效{}:{}", id, cuser);
            return AgentResult.fail("订单调整信息已失效！");
        }
        if (orderAdj.getReviewsStat().equals(AgStatus.Approving.name())) {
            logger.info("订单调整提交审批，禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("订单调整提交审批，禁止重复提交审批！");
        }
        if (orderAdj.getReviewsStat().equals(AgStatus.Approved.name())) {
            logger.info("订单调整提交审批，禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("订单调整提交审批，禁止重复提交审批！");
        }

        //更新订单调整数据为审批中
        orderAdj.setReviewsStat(AgStatus.Approving.status);
        orderAdj.setReviewsDate(new Date());
        orderAdj.setLogicalVersion(BigDecimal.ONE);
        if (1 != orderAdjMapper.updateByPrimaryKeySelective(orderAdj)) {
            logger.info("订单调整提交审批，更新订单调整数据失败{}:{}", id, cuser);
            throw new MessageException("订单调整提交审批，更新订单调整数据失败！");
        }

        //流程中的部门参数
        Map startPar = agentEnterService.startPar(cuser);
        if (null == startPar) {
            logger.info("========用户{}{}启动部门参数为空", id, cuser);
            throw new MessageException("启动部门参数为空！");
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("orderAdjust"), null, null, startPar);
        if (proce == null) {
            logger.info("订单调整提交审批，审批流启动失败{}:{}", id, cuser);
            throw new MessageException("审批流启动失败！");
        }

        Agent agent = agentMapper.selectByPrimaryKey(orderAdj.getAgentId());
        OOrder order = orderMapper.selectByPrimaryKey(orderAdj.getOrderId());
        order.setOrderStatus(OrderStatus.LOCK.status);
        if (1 != orderMapper.updateByPrimaryKeySelective(order)){
            logger.info("更新订单为锁定状态失败:{}", orderAdj.getOrderId());
            throw new MessageException("更新订单为锁定状态失败！");
        };
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
        //添加审批关系
        BusActRel record = new BusActRel();
        record.setBusId(orderAdj.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.orderAdjust.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(orderAdj.getAgentId());
        record.setAgentName(agent.getAgName());
        record.setNetInBusType("ACTIVITY_"+agentBusInfo.getBusPlatform());//数据权限
        record.setDataShiro(BusActRelBusType.orderAdjust.key);
        record.setAgDocPro(agentBusInfo.getAgDocPro());
        record.setAgDocDistrict(agentBusInfo.getAgDocDistrict());
//        record.setNetInBusType("ACTIVITY_"+order.getOrderPlatform());//入网流程需要该字段
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("订单调整提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("订单调整审批流启动失败：添加审批关系失败！");
        }
        return AgentResult.ok();
    }

    /**
     * 修改订单调整数据
     * @param orderUpModelVo
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult updateOrderAdjust(OrderUpModelVo orderUpModelVo, String userId) throws Exception {
        AgentResult agentResult = AgentResult.fail("修改失败!");
        boolean adjFlag = true;
        List<AdjProVo> adjPros = orderUpModelVo.getAdjPros();
        OOrder order = orderMapper.selectByPrimaryKey(orderUpModelVo.getOrderId());
        if (null == order) {
            agentResult.setMsg(orderUpModelVo.getOrderId()+"-此订单不存在！");
            return agentResult;
        }
        if (new BigDecimal(orderUpModelVo.getRefundAmount()).compareTo(BigDecimal.ZERO) != 0){
            List<OrderAdjAccountVo>  accounts = orderUpModelVo.getAccounts();
            if (null == accounts || accounts.size() == 0){
                agentResult.setMsg("请填写账户信息！");
                return agentResult;
            }
        }
        List<OrderAdjAccountVo>  accounts = orderUpModelVo.getAccounts();
        if (null == accounts || accounts.size() == 0){
            agentResult.setMsg("请填写账户信息！");
            return agentResult;
        }
        //检查配货+排单数量
        for (AdjProVo adjProVo : adjPros) {
            OSubOrderExample osubOrderExample = new OSubOrderExample();
            osubOrderExample.or().andIdEqualTo(adjProVo.getoSubId()).andStatusEqualTo(Status.STATUS_1.status);
            List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(osubOrderExample);
            if (oSubOrders.size() > 0) {
                FastMap fastMap = FastMap.fastMap("subOrderId", oSubOrders.get(0).getId());
                BigDecimal oReceiptPros = oReceiptProMapper.receiptCountTotal(oSubOrders.get(0).getOrderId(), oSubOrders.get(0).getProId());//配货数量
                BigDecimal countPlans = receiptPlanMapper.planCountTotal(oSubOrders.get(0).getOrderId(), oSubOrders.get(0).getProId());//排单数量
                BigDecimal enableNum = oSubOrders.get(0).getProNum().subtract(oReceiptPros);
                if (new BigDecimal(adjProVo.getAdjNum()).compareTo(enableNum) > 0) {
                    agentResult.setMsg("可调整机具数量错误！");
                    adjFlag = false;
                    break;
                }
            }
        }
        if (!adjFlag) {
            return agentResult;
        }

        OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(orderUpModelVo.getId());
        orderAdj.setAdjUserId(userId);
        orderAdj.setReson(orderUpModelVo.getReson());//原因
        orderAdj.setRefundAmount(new BigDecimal(orderUpModelVo.getRefundAmount()));//退款金额
        orderAdj.setRefundMethod(new BigDecimal(orderUpModelVo.getRefundMethod()));//退款方式
        orderAdj.setStagesAmount(new BigDecimal(orderUpModelVo.getAdjRepayment()));//预计分期金额
        BigDecimal difAmount = BigDecimal.ZERO;
        for (AdjProVo adjProVo : adjPros) {
            OSubOrder oSubOrder = oSubOrderMapper.selectByPrimaryKey(adjProVo.getoSubId());
            OrderAdjDetail orderAdjDetail = orderAdjDetailMapper.selectByAdjustId(orderAdj.getId(), adjProVo.getAdjDetailId());
            orderAdjDetail.setAdjNum(new BigDecimal(adjProVo.getAdjNum()));
            orderAdjDetail.setOrgProNum(oSubOrder.getProNum());
            orderAdjDetail.setProNum(oSubOrder.getProNum().subtract(new BigDecimal(adjProVo.getAdjNum())));
            orderAdjDetail.setDifAmount(adjProVo.getCalPrice());
            difAmount = difAmount.add(adjProVo.getCalPrice());
            if (1 != orderAdjDetailMapper.updateByPrimaryKeySelective(orderAdjDetail)) {
                logger.info("订单调整明细:{}", "订单调整明细修改失败！");
                throw new ProcessException("订单调整明细修改失败！");
            }
            logger.info("订单调整明细:{},{},{},{}", orderAdj.getId(), adjProVo.getAdjDetailId(), "订单调整明细修改失败！", userId);
        }
        orderAdj.setDifAmount(difAmount);
        List<String> attFiles = orderUpModelVo.getFiles();
        //删除附件
        AttachmentRelExample deleAttr = new AttachmentRelExample();
        deleAttr.or()
                .andBusTypeEqualTo(AttachmentRelType.orderAdjust.name())
                .andSrcIdEqualTo(orderUpModelVo.getId())
                .andStatusEqualTo(Status.STATUS_1.status);
        List<AttachmentRel> attachmentRels = attachmentRelMapper.selectByExample(deleAttr);
        if (attachmentRels.size() > 0) {
            for (AttachmentRel attachmentRelItem : attachmentRels) {
                attachmentRelItem.setStatus(Status.STATUS_0.status);
                if (1 != attachmentRelMapper.updateByPrimaryKeySelective(attachmentRelItem)) {
                    logger.info("订单调整:{},{},{}", orderAdj.getId(), "删除附件失败", userId);
                    throw new ProcessException("删除附件失败！");
                }
                logger.info("订单调整:{},{},{}", orderAdj.getId(), "删除附件成功！", userId);
            }
        }
        //添加新附件
        AttachmentRel recordAtt = new AttachmentRel();
        if (attFiles.size() > 0) {
            for (String attFile : attFiles) {
                recordAtt.setAttId(attFile);
                recordAtt.setSrcId(orderAdj.getId());
                recordAtt.setcUser(orderAdj.getAdjUserId());
                recordAtt.setcTime(orderAdj.getAdjTm());
                recordAtt.setStatus(Status.STATUS_1.status);
                recordAtt.setBusType(AttachmentRelType.orderAdjust.name());
                recordAtt.setId(idService.genId(TabId.a_attachment_rel));
                logger.info("添加订单调整附件关系,订单调整ID{},附件ID{}", orderAdj.getId(), attFile);
                if (1 != attachmentRelMapper.insertSelective(recordAtt)) {
                    logger.info("订单调整添加附件:{}", "添加订单调整附件关系失败！");
                    throw new ProcessException("添加订单调整附件关系失败！");
                }
            }
        }

        //删除原账户信息
        OrderAdjAccountExample orderAdjAccountDel = new OrderAdjAccountExample();
        orderAdjAccountDel.or().andAdjIdEqualTo(orderAdj.getId())
                .andOrderIdEqualTo(orderAdj.getOrderId())
                .andStatusEqualTo(Status.STATUS_1.status);
        orderAdjAccountMapper.deleteByExample(orderAdjAccountDel);

        for (OrderAdjAccountVo adjAccount:orderUpModelVo.getAccounts()){
            OrderAdjAccount account = new OrderAdjAccount();
            account.setId(idService.genId(TabId.O_ORDER_ADJ_ACCOUNT));
            account.setAdjId(orderAdj.getId());
            account.setOrderId(orderAdj.getOrderId());
            account.setType(adjAccount.getType());
            account.setRefundAccount(adjAccount.getRefundAccount());
            account.setAccountName(adjAccount.getAccountName());
            account.setAccountBank(adjAccount.getAccountBank());
            account.setBranchLineNum(adjAccount.getBranchLineNum());
            account.setAllLineNum(adjAccount.getAllLineNum());
            account.setRefundAmo(BigDecimal.ZERO);
            account.setRefundStat(RefundStat.UNREFUND.key);
            account.setStatus(Status.STATUS_1.status);
            account.setVersion(Status.STATUS_1.status);
            if (1 !=orderAdjAccountMapper.insert(account) ){
                logger.info("订单调整:{}", "保存账户信息失败");
                throw new MessageException("保存账户信息失败");
            }

            List<String> accountFile = adjAccount.getAccountFile();
            if (null != accountFile&&accountFile.size()>0){
                for (String s : accountFile) {
                    AttachmentRel recordAccountAtt = new AttachmentRel();
                    recordAccountAtt.setAttId(s);
                    recordAccountAtt.setSrcId(account.getId());
                    recordAccountAtt.setcUser(orderAdj.getAdjUserId());
                    recordAccountAtt.setcTime(orderAdj.getAdjTm());
                    recordAccountAtt.setStatus(Status.STATUS_1.status);
                    recordAccountAtt.setBusType(AttachmentRelType.orderAdjustDk.name());
                    recordAccountAtt.setId(idService.genId(TabId.a_attachment_rel));
                    logger.info("添加订单调整附件关系,订单调整ID{},打款附件ID{}",orderAdj.getId(),accountFile);
                    if (1 != attachmentRelMapper.insertSelective(recordAccountAtt)) {
                        logger.info("订单调整:{}", "添加订单调整打款附件关系失败");
                        throw new ProcessException("添加订单调整打款附件关系失败");
                    }
                }
            }else {
                throw new MessageException("未上传打款附件");
            }

        }

        OPaymentExample oPaymentExample = new OPaymentExample();
        oPaymentExample.or().andOrderIdEqualTo(orderUpModelVo.getOrderId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OPayment> payments = paymentMapper.selectByExample(oPaymentExample);
        //生成自定义分期
        OPayment payment = payments.get(0);

        String paymentMethod = payment.getPayMethod();
        List<String> data = orderUpModelVo.getCustomStagingUser();

        //将已生成的分期删除重新生成分期
        OPaymentDetailExample example = new OPaymentDetailExample();
        example.or().andOrderIdEqualTo(payment.getOrderId())
                .andBatchCodeEqualTo(orderAdj.getNewPaymentId())
                .andPaymentStatusEqualTo(PaymentStatus.DS.code);
        oPaymentDetailMapper.deleteByExample(example);
        logger.info("生成自定义分期 删除付款明细 {}"+payment.getOrderId());

        Date da = Calendar.getInstance().getTime();
        orderAdj.setNewPaymentId(da.getTime()+"");
        //记录分期金额
        BigDecimal fqje = BigDecimal.ZERO;
        if(StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging()) &&  Status.STATUS_1.status.toString().equals(orderUpModelVo.getCustomStaging())) {
            //初始化生成明细
            switch (paymentMethod) {
                case "FKFQ":
                    //生成自定义预分期数据
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        Date DownPaymentDate = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(DownPaymentDate);
                        c.add(Calendar.MONTH,-1);
                        c.set(Calendar.DAY_OF_MONTH, 1);
                        for (int i = 0; i < data.size(); i++) {

                            c.add(Calendar.MONTH,1);

                            String amount = data.get(i);
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(da.getTime()+"");
                            record.setPaymentId(payment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(payment.getOrderId());
                            record.setPayType(PaymentType.DKFQ.code);
                            record.setPayAmount(new BigDecimal(amount));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime(c.getTime());
                            record.setPlanNum(new BigDecimal(i).add(BigDecimal.ONE));
                            record.setAgentId(payment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DS.code);
                            record.setcUser(payment.getUserId());
                            record.setcDate(da);
                            record.setStatus(Status.STATUS_0.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                throw new MessageException("分期处理");
                            }
                            fqje = fqje.add(record.getPayAmount());

                        }
                    }
                    break;
                case "FRFQ":
                    //生成自定义预分期数据
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        Date DownPaymentDate = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(DownPaymentDate);
                        c.add(Calendar.MONTH,-1);
                        c.set(Calendar.DAY_OF_MONTH, 1);
                        for (int i = 0; i < data.size(); i++) {

                            c.add(Calendar.MONTH,1);

                            String amount = data.get(i);
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(da.getTime()+"");
                            record.setPaymentId(payment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(payment.getOrderId());
                            record.setPayType(PaymentType.FRFQ.code);
                            record.setPayAmount(new BigDecimal(amount));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime(c.getTime());
                            record.setPlanNum(new BigDecimal(i).add(BigDecimal.ONE));
                            record.setAgentId(payment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DS.code);
                            record.setcUser(payment.getUserId());
                            record.setcDate(da);
                            record.setStatus(Status.STATUS_0.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                throw new MessageException("分期处理");
                            }
                            fqje = fqje.add(record.getPayAmount());
                        }
                    }
                    break;
                case "XXDK":
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        throw new MessageException("线下打款不支持自定义分期");
                    }
                    break;
                case "SF1"://首付+分润分期
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        Date DownPaymentDate = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(DownPaymentDate);
                        c.add(Calendar.MONTH,-1);
                        c.set(Calendar.DAY_OF_MONTH, 1);
                        for (int i = 0; i < data.size(); i++) {

                            c.add(Calendar.MONTH,1);

                            String amount = data.get(i);
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(da.getTime()+"");
                            record.setPaymentId(payment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(payment.getOrderId());
                            record.setPayType(PaymentType.FRFQ.code);
                            record.setPayAmount(new BigDecimal(amount));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime(c.getTime());
                            record.setPlanNum(new BigDecimal(i).add(BigDecimal.ONE));
                            record.setAgentId(payment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DS.code);
                            record.setcUser(payment.getUserId());
                            record.setcDate(da);
                            record.setStatus(Status.STATUS_0.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                throw new MessageException("分期处理");
                            }
                            fqje = fqje.add(record.getPayAmount());
                        }
                    }
                    break;
                case "SF2"://打款分期
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        Date DownPaymentDate = new Date();
                        Calendar c = Calendar.getInstance();
                        c.setTime(DownPaymentDate);
                        c.add(Calendar.MONTH,-1);
                        c.set(Calendar.DAY_OF_MONTH, 1);
                        for (int i = 0; i < data.size(); i++) {
                            c.add(Calendar.MONTH,1);
                            String amount = data.get(i);
                            OPaymentDetail record = new OPaymentDetail();
                            record.setId(idService.genId(TabId.o_payment_detail));
                            record.setBatchCode(da.getTime()+"");
                            record.setPaymentId(payment.getId());
                            record.setPaymentType(PamentIdType.ORDER_FKD.code);
                            record.setOrderId(payment.getOrderId());
                            record.setPayType(PaymentType.DKFQ.code);
                            record.setPayAmount(new BigDecimal(amount));
                            record.setRealPayAmount(BigDecimal.ZERO);
                            record.setPlanPayTime(c.getTime());
                            record.setPlanNum(new BigDecimal(i).add(BigDecimal.ONE));
                            record.setAgentId(payment.getAgentId());
                            record.setPaymentStatus(PaymentStatus.DS.code);
                            record.setcUser(payment.getUserId());
                            record.setcDate(da);
                            record.setStatus(Status.STATUS_0.status);
                            record.setVersion(Status.STATUS_1.status);
                            if (1 != oPaymentDetailMapper.insert(record)) {
                                throw new MessageException("分期处理");
                            }
                            fqje = fqje.add(record.getPayAmount());
                        }
                    }
                    break;
                case "QT"://抵扣金额必须等于待付金额
                    if (StringUtils.isNotBlank(payment.getCustomStaging()) && Status.STATUS_1.status.toString().equals(payment.getCustomStaging())) {
                        throw new MessageException("其他付款方式不支持自定义分期");
                    }
                    break;
            }

            if(payment.getDeductionAmount()==null){
                payment.setDeductionAmount(BigDecimal.ZERO);
            }
            if(payment.getDownPayment()==null){
                payment.setDownPayment(BigDecimal.ZERO);
            }
            //TODO 检查付款明细及打款信息是否和订单金额一致 订单原应付金额 - 订单调整差价  = 已付金额  + 抵扣金额 + 分期金额
            if(payment.getPayAmount().subtract(difAmount).compareTo(payment.getRealAmount().add(payment.getDeductionAmount()).add(fqje))!=0){
                throw new MessageException("分期金额配置失败:金额总计应为"+ payment.getPayAmount().subtract(payment.getRealAmount()).subtract(difAmount));
            }
        }

        if (1 == orderAdjMapper.updateByPrimaryKeySelective(orderAdj)) {
            agentResult.setStatus(AgentResult.OK);
            agentResult.setMsg("保存成功");
        }
        return agentResult;
    }

    @Override
    public OrderAdj getByAdjIdStatus(String adjId) {
        OrderAdjExample orderAdjExample = new OrderAdjExample();
        orderAdjExample.or()
                .andStatusEqualTo(Status.STATUS_1.status)
                .andIdEqualTo(adjId);
        List<OrderAdj> orderAdjList = orderAdjMapper.selectByExample(orderAdjExample);
        OrderAdj orderAdj = orderAdjList.get(0);
        return orderAdj;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public Map<String, Object> approvalTaskOrderAdjustBusiness(OrderUpModelVo orderUpModelVo, String userId) throws Exception {
        //处理审批数据
        logger.info("订单调整提交审批，完成任务{}:{}：{}", orderUpModelVo.getTaskId(), userId, JSONObject.toJSONString(orderUpModelVo));
//            //只有通过才处理业务
//            if (orderUpModelVo.getApprovalResult().equals(ApprovalType.PASS.getValue())) {
//                AgentResult busres = orderService.approvalTaskBussiData(orderUpModelVo, userId);
//                if (!busres.isOK()) {
//                    return busres;
//                }
//            }
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(userId));
        if(orgCodeRes==null && orgCodeRes.size()!=1){
            throw new MessageException("部门参数为空");
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        //完成任务
        AgentResult result = new AgentResult(500, "系统异常", "");
        Map<String, Object> reqMap = new HashMap<>();
        reqMap.put("rs", orderUpModelVo.getApprovalResult());
        reqMap.put("approvalOpinion", orderUpModelVo.getApprovalOpinion());
        reqMap.put("approvalPerson", userId);
        reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
        reqMap.put("taskId", orderUpModelVo.getTaskId());
        //下一个节点参数
        if (org.apache.commons.lang.StringUtils.isNotEmpty(orderUpModelVo.getOrderAdjAprDept()))
            reqMap.put("dept", orderUpModelVo.getOrderAdjAprDept());
        //传递部门信息
        Map startPar = agentEnterService.startPar(userId);
        if (null != startPar) {
            reqMap.put("party",startPar.get("party"));
        }
        OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(orderUpModelVo.getId());
        // 业务部处理
        if (orderUpModelVo.getSid().equals("sid-C911F512-9E63-44CC-9E6E-763484FA0E5B")&& orderUpModelVo.getApprovalResult().equals(ApprovalType.PASS.getValue())){
            if (orderAdj.getReviewsStat().compareTo(AgStatus.Approving.status) != 0){
                throw new MessageException("该任务审批状态异常!");
            }
            //应该退款金额
            BigDecimal amount = orderAdj.getRefundAmount() == null ? new BigDecimal(0) : orderAdj.getRefundAmount();
            //手续费
            BigDecimal proRefundAmo = orderAdj.getProRefundAmount() == null ? BigDecimal.ZERO : orderAdj.getProRefundAmount();
            //机具已抵扣金额
            BigDecimal takeout_amount = orderAdj.getOffsetAmount() == null ? new BigDecimal(0) : orderAdj.getOffsetAmount();
            if (amount.subtract(proRefundAmo).subtract(takeout_amount).compareTo(BigDecimal.ZERO) == 1){
                logger.info("退款金额大于0,进行抵扣入库;应退金额:"+amount+",手续费:"+proRefundAmo+"已抵扣金额:"+takeout_amount);
                List<OPaymentDetail> paymentDetails = paymentDetailService.getCanTakeoutPaymentsByAgentId(orderAdj.getAgentId(), AdjustType.ORDER_ADJ.adjustType, orderAdj.getId());
                AgentResult agentResult= orderOffsetService.OffsetArrears(paymentDetails, amount.subtract(proRefundAmo).subtract(takeout_amount), DDTZ.code, orderAdj.getId());
                if (agentResult.isOK()){
                    BigDecimal residue =  (BigDecimal) agentResult.getMapData().get("residueAmt");
                    orderAdj.setOffsetAmount(amount.subtract(proRefundAmo).subtract(takeout_amount).subtract(residue));
                    orderAdj.setRealRefundAmo(amount.subtract(proRefundAmo).subtract(takeout_amount).subtract(orderAdj.getOffsetAmount()));
                    if(!approvalTaskSettle(orderAdj).isOK()){
                        throw new MessageException("更新订单调整记录失败!");
                    };
                }
            }
        }
        //财务审批

        if(orgCode.equals("finance") && orderUpModelVo.getApprovalResult().equals(ApprovalType.PASS.getValue())){
            if (orderAdj.getReviewsStat().compareTo(AgStatus.Approving.status) != 0){
                throw new MessageException("该任务审批状态异常!");
            }
            //财务节点,挂账-无退款
            logger.info("财务审批进行分期变更");
            //挂账审批通过
            if(String.valueOf(OrderAdjRefundType.CDFQ_GZ.code).equals(orderUpModelVo.getRefundType()) ){
                logger.info("财务选择挂账:"+orderUpModelVo.getId());
                orderAdj.setSettleAmount(new BigDecimal(orderUpModelVo.getRefundAmount()));
                orderAdj.setRealRefundAmo(BigDecimal.ZERO);
                orderAdj.setRefundType(new BigDecimal(orderUpModelVo.getRefundType()));
                orderAdj.setRefundStat(RefundStat.UNREFUND.key);
                reqMap.put("remit",false);
                AgentResult agentResult = adjustDoPayPlan(orderAdj.getId(),orderAdj);
                if (agentResult.isOK()){
                    Map<String, Object> mapData = agentResult.getMapData();
                    if (null!= mapData.get("refundAmount")
                            && ((BigDecimal)mapData.get("refundAmount")).compareTo(BigDecimal.ZERO)<0
                            && String.valueOf(OrderAdjRefundType.CDFQ_XXTK.code).equals(orderUpModelVo.getRefundType())){
                    }
                }else {
                    throw new MessageException("执行抵扣失败!");
                }
                orderAdj.setReviewsStat(AgStatus.Approved.status);
                orderAdj.setReviewsDate(new Date());
                // 订单调整更新
                if (1 != orderAdjMapper.updateByPrimaryKeySelective(orderAdj)) {
                    throw new MessageException("订单调整数据更新异常！");
                }
            }else if(String.valueOf(OrderAdjRefundType.CDFQ_XXTK.code).equals(orderUpModelVo.getRefundType())){
                logger.info("财务选择线下退款");
                orderAdj.setRealRefundAmo(new BigDecimal(orderUpModelVo.getRefundAmount()));
                orderAdj.setSettleAmount(BigDecimal.ZERO);
                orderAdj.setRefundType(new BigDecimal(orderUpModelVo.getRefundType()));
                orderAdj.setRefundStat(RefundStat.REFUNDING.key);
                reqMap.put("remit",true);
            }
            AgentResult agentResultcheck = adjustCheckAmo(orderUpModelVo);
            if (!agentResultcheck.isOK()){
                throw new MessageException(agentResultcheck.getMsg());
            }
            AgentResult agentResult1 = saveAdjAccounts(orderUpModelVo);
            if (!agentResult1.isOK()){
                throw new MessageException(agentResult1.getMsg());
            }

        }
        if ("boss".equals(orgCode) && orderUpModelVo.getApprovalResult().equals(ApprovalType.PASS.getValue()) ){
            if (orderAdj.getReviewsStat().compareTo(AgStatus.Approving.status) != 0){
                throw new MessageException("该任务审批状态异常!");
            }
            AgentResult agentResult = adjustDoPayPlan(orderAdj.getId(),orderAdj);
            if (agentResult.isOK()){
                Map<String, Object> mapData = agentResult.getMapData();
                if (null!= mapData.get("refundAmount")
                        && ((BigDecimal)mapData.get("refundAmount")).compareTo(BigDecimal.ZERO)<0
                        && String.valueOf(OrderAdjRefundType.CDFQ_XXTK.code).equals(orderUpModelVo.getRefundType())){
                    reqMap.put("remit",false);
                };
            }else {
                throw new MessageException("执行抵扣失败!");
            }
            orderAdj.setReviewsStat(AgStatus.Approved.status);
            orderAdj.setReviewsDate(new Date());
            // 订单调整更新
            if (1 != orderAdjMapper.updateByPrimaryKeySelective(orderAdj)) {
                throw new MessageException("订单调整数据更新异常！");
            }
        }
        return reqMap;
    }
    /**
     * 订单调整审批处理
     * @param orderUpModelVo
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTaskOrderAdjust(OrderUpModelVo orderUpModelVo, String userId) throws Exception {
        try {
//            Map<String, Object> reqMap = orderAdjustService.approvalTaskOrderAdjustBusiness(orderUpModelVo, userId);
            //处理审批数据
            logger.info("订单调整提交审批，完成任务{}:{}：{}", orderUpModelVo.getTaskId(), userId, JSONObject.toJSONString(orderUpModelVo));
//            //只有通过才处理业务
//            if (orderUpModelVo.getApprovalResult().equals(ApprovalType.PASS.getValue())) {
//                AgentResult busres = orderService.approvalTaskBussiData(orderUpModelVo, userId);
//                if (!busres.isOK()) {
//                    return busres;
//                }
//            }
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(userId));
            if(orgCodeRes==null && orgCodeRes.size()!=1){
                throw new MessageException("部门参数为空");
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String orgCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
            //完成任务
            AgentResult result = new AgentResult(500, "系统异常", "");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("rs", orderUpModelVo.getApprovalResult());
            reqMap.put("approvalOpinion", orderUpModelVo.getApprovalOpinion());
            reqMap.put("approvalPerson", userId);
            reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
            reqMap.put("taskId", orderUpModelVo.getTaskId());
            reqMap.put("mySid", orderUpModelVo.getSid());
            //下一个节点参数
            if (org.apache.commons.lang.StringUtils.isNotEmpty(orderUpModelVo.getOrderAdjAprDept()))
                reqMap.put("dept", orderUpModelVo.getOrderAdjAprDept());
            //传递部门信息
            Map startPar = agentEnterService.startPar(userId);
            if (null != startPar) {
                reqMap.put("party",startPar.get("party"));
            }
            OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(orderUpModelVo.getId());
            // 业务部处理
            if (orderUpModelVo.getSid().equals("sid-C911F512-9E63-44CC-9E6E-763484FA0E5B")&& orderUpModelVo.getApprovalResult().equals(ApprovalType.PASS.getValue())){
                if (orderAdj.getReviewsStat().compareTo(AgStatus.Approving.status) != 0){
                    throw new MessageException("该任务审批状态异常!");
                }
                //应该退款金额
                BigDecimal amount = orderAdj.getRefundAmount() == null ? new BigDecimal(0) : orderAdj.getRefundAmount();
                //手续费
                BigDecimal proRefundAmo = orderAdj.getProRefundAmount() == null ? BigDecimal.ZERO : orderAdj.getProRefundAmount();
                //机具已抵扣金额
                BigDecimal takeout_amount = orderAdj.getOffsetAmount() == null ? new BigDecimal(0) : orderAdj.getOffsetAmount();
                if (amount.subtract(proRefundAmo).subtract(takeout_amount).compareTo(BigDecimal.ZERO) == 1){
                    logger.info("退款金额大于0,进行抵扣入库;应退金额:"+amount+",手续费:"+proRefundAmo+"已抵扣金额:"+takeout_amount);
                    List<OPaymentDetail> paymentDetails = paymentDetailService.getCanTakeoutPaymentsByAgentId(orderAdj.getAgentId(), AdjustType.ORDER_ADJ.adjustType, orderAdj.getId());
                    AgentResult agentResult= orderOffsetService.OffsetArrears(paymentDetails, amount.subtract(proRefundAmo).subtract(takeout_amount), DDTZ.code, orderAdj.getId());
                    if (agentResult.isOK()){
                        BigDecimal residue =  (BigDecimal) agentResult.getMapData().get("residueAmt");
                        orderAdj.setOffsetAmount(amount.subtract(proRefundAmo).subtract(takeout_amount).subtract(residue));
                        orderAdj.setRealRefundAmo(amount.subtract(proRefundAmo).subtract(takeout_amount).subtract(orderAdj.getOffsetAmount()));
                        if(!approvalTaskSettle(orderAdj).isOK()){
                            throw new MessageException("更新订单调整记录失败!");
                        };
                    }
                }
            }
            //财务审批

            if(orgCode.equals("finance") && orderUpModelVo.getApprovalResult().equals(ApprovalType.PASS.getValue())){
                if (orderAdj.getReviewsStat().compareTo(AgStatus.Approving.status) != 0){
                    throw new MessageException("该任务审批状态异常!");
                }
                //财务节点,挂账-无退款
                logger.info("财务审批进行分期变更");
                //挂账审批通过
                if(String.valueOf(OrderAdjRefundType.CDFQ_GZ.code).equals(orderUpModelVo.getRefundType()) ){
                    logger.info("财务选择挂账:"+orderUpModelVo.getId());
                    orderAdj.setSettleAmount(new BigDecimal(orderUpModelVo.getRefundAmount()));
                    orderAdj.setRealRefundAmo(BigDecimal.ZERO);
                    orderAdj.setRefundType(new BigDecimal(orderUpModelVo.getRefundType()));
                    orderAdj.setRefundStat(RefundStat.UNREFUND.key);
                    reqMap.put("remit",false);
                    //完成任务
                    Map resultMap = activityService.completeTask(orderUpModelVo.getTaskId(), reqMap);
                    if (resultMap == null) {
                        throw new MessageException("catch工作流处理任务异常！");
                    }
                    Boolean rs = (Boolean) resultMap.get("rs");
                    String msg = String.valueOf(resultMap.get("msg"));
                    if (!rs) {
                        throw new MessageException("catch工作流处理任务异常！");
                    }
                    return AgentResult.ok(null);
                }else if(String.valueOf(OrderAdjRefundType.CDFQ_XXTK.code).equals(orderUpModelVo.getRefundType())){
                    logger.info("财务选择线下退款");
                    orderAdj.setRealRefundAmo(new BigDecimal(orderUpModelVo.getRefundAmount()));
                    orderAdj.setSettleAmount(BigDecimal.ZERO);
                    orderAdj.setRefundType(new BigDecimal(orderUpModelVo.getRefundType()));
                    orderAdj.setRefundStat(RefundStat.REFUNDING.key);
                    // 订单调整更新
                    if (1 != orderAdjMapper.updateByPrimaryKeySelective(orderAdj)) {
                        throw new MessageException("订单调整数据更新异常！");
                    }
                    reqMap.put("remit",true);
                }
                AgentResult agentResultcheck = adjustCheckAmo(orderUpModelVo);
                if (!agentResultcheck.isOK()){
                    throw new MessageException(agentResultcheck.getMsg());
                }
                AgentResult agentResult1 = saveAdjAccounts(orderUpModelVo);
                if (!agentResult1.isOK()){
                    throw new MessageException(agentResult1.getMsg());
                }

            }
            if ("boss".equals(orgCode) && orderUpModelVo.getApprovalResult().equals(ApprovalType.PASS.getValue()) ){
                if (orderAdj.getReviewsStat().compareTo(AgStatus.Approving.status) != 0){
                    throw new MessageException("该任务审批状态异常!");
                }
            }
            //退回时需取消抵扣记录
            if( !"boss".equals(orgCode) && orderUpModelVo.getApprovalResult().equals(ApprovalType.BACK.getValue())){
                if(orderAdj.getOffsetAmount().compareTo(BigDecimal.ZERO)>0&& orderAdj.getLogicalVersion()!=null && orderAdj.getLogicalVersion().compareTo(BigDecimal.ONE)==0){
                    AgentResult agentResult = orderOffsetService.OffsetArrearsCancle(orderAdj.getOffsetAmount(), DDTZ.code, orderAdj.getId());
                    if (!agentResult.isOK()){
                        throw new MessageException("订单调整抵扣欠款数据更新异常！");
                    }else {
                        orderAdj.setOffsetAmount(BigDecimal.ZERO);
                        orderAdj.setRealRefundAmo(BigDecimal.ZERO);
                        orderAdj.setProRefundAmount(BigDecimal.ZERO);
                        orderAdj.setRefundType(OrderAdjRefundType.CDFQ_GZ.code);
                        if(!approvalTaskSettle(orderAdj).isOK()){
                            throw new MessageException("更新订单调整记录失败!");
                        };
                    }
                }
            }
            //完成任务
            Map resultMap = activityService.completeTask(orderUpModelVo.getTaskId(), reqMap);
            if (resultMap == null) {
                throw new MessageException("catch工作流处理任务异常！");
            }
            Boolean rs = (Boolean) resultMap.get("rs");
            String msg = String.valueOf(resultMap.get("msg"));
            if (!rs) {
                throw new MessageException("catch工作流处理任务异常！");
            }
            return AgentResult.ok(null);
        } catch (MessageException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 订单调整审批通过
     * @param insid
     * @param actname
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    @Override
    public AgentResult approveFinishOrderAdjust(String insid, String actname) throws Exception {
        logger.info("订单调整审批完成:{},{}", insid, actname);
        //审批流关系
        BusActRel busActRel = busActRelService.findById(insid);
        if (actname.equals("finish_end")) { //审批完成
            logger.info("订单调整审批完成,审批通过{}", busActRel.getBusId());
            busActRel.setActivStatus(AgStatus.Approved.name());
            if (1 != busActRelService.updateByPrimaryKey(busActRel)) {
                throw new MessageException("请重新提交！");
            }
            OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(busActRel.getBusId());
            if (orderAdj.getReviewsStat().compareTo(AgStatus.Approving.status) != 0){
                throw new MessageException("该任务审批状态异常!");
            }
            AgentResult adjustResult = adjustDoPayPlan(orderAdj.getId(),orderAdj);
            if (adjustResult.isOK()){
//                Map<String, Object> mapData = agentResult.getMapData();
//                if (null!= mapData.get("refundAmount")
//                        && ((BigDecimal)mapData.get("refundAmount")).compareTo(BigDecimal.ZERO)<0
//                        && String.valueOf(OrderAdjRefundType.CDFQ_XXTK.code).equals(orderUpModelVo.getRefundType())){
//                };
            }else {
                throw new MessageException("执行抵扣失败!");
            }
            //应该退款金额
            BigDecimal amount = orderAdj.getRefundAmount() == null ? new BigDecimal(0) : orderAdj.getRefundAmount();
            //手续费
            BigDecimal proRefundAmo = orderAdj.getProRefundAmount() == null ? BigDecimal.ZERO : orderAdj.getProRefundAmount();
            if (amount.subtract(proRefundAmo).compareTo(BigDecimal.ZERO) == 1) {
                AgentResult agentResult = orderOffsetService.OffsetArrearsCommit(orderAdj.getOffsetAmount(), DDTZ.code, orderAdj.getId());
                if (!agentResult.isOK()) {
                    throw new MessageException("订单调整数据更新异常！");
                }
            }
//            if (orderAdj.getReviewsStat().compareTo(AgStatus.Approved.status) == 0) {
//                logger.info("订单调整审批完成:已审批过:{}", orderAdj.getId());
//                return AgentResult.ok();
//            }
            // TODO 判断是否有退款  有： 生成出纳审批  没有 ： 不生成审批
            OrderAdjAccountExample orderAdjAccountExample = new OrderAdjAccountExample();
            orderAdjAccountExample.or().andAdjIdEqualTo(orderAdj.getId());
            List<OrderAdjAccount> orderAdjAccountList = orderAdjAccountMapper.selectByExample(orderAdjAccountExample);
            BigDecimal countSum = new BigDecimal(0);
            for (OrderAdjAccount orderAdjAccount : orderAdjAccountList) {
                countSum = countSum.add(orderAdjAccount.getRefundAmo());
            }
            if(countSum.compareTo(new BigDecimal(0))==0){// 无需退款
//                orderAdj.setReviewsStat(AgStatus.Approved.status);
//                orderAdj.setReviewsDate(new Date());
//                // 订单调整更新
//                if (1 != orderAdjMapper.updateByPrimaryKeySelective(orderAdj)) {
//                    throw new MessageException("订单调整数据更新异常！");
//                }
            }else{// 有退款 申请出纳审批流
                String workId = dictOptionsService.getApproveVersion("OrderAdjustRefund");
                Map startMap = new HashMap<>();
                OrderAdjAccountExample settleAC = new OrderAdjAccountExample();
                settleAC.or().andAdjIdEqualTo(orderAdj.getId());
                List<OrderAdjAccount> settlementList =  orderAdjAccountMapper.selectByExample(settleAC);
                for (OrderAdjAccount orderAdjAccount : settlementList) {
                    if(orderAdjAccount.getType().compareTo(new BigDecimal(2)) == 0){
                        startMap.put("settlementCardDs","1");// 生成对私记录 值1
                    }else if(startMap.get("settlementCardDs") == null || (!startMap.get("settlementCardDs").equals("1"))){
                            startMap.put("settlementCardDs","0");
                        }
                    if(orderAdjAccount.getType().compareTo(new BigDecimal(1)) == 0){
                        startMap.put("settlementCardDg","1");// 生成对公记录 值1
                    }else if(startMap.get("settlementCardDg") == null || (!startMap.get("settlementCardDg").equals("1"))){
                            startMap.put("settlementCardDg","0");
                        }
                }
                // 启动审批
                String proce = activityService.createDeloyFlow(null, workId, null, null, startMap);
                if (proce == null) {
                    throw new MessageException("审批流启动失败!");
                }
                //添加审批关系
                BusActRel record = new BusActRel();
                record.setBusId(orderAdj.getId());
                record.setActivId(proce);
                record.setcTime(Calendar.getInstance().getTime());
                record.setcUser(busActRel.getcUser());
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(BusActRelBusType.cashierApprove.name());
                record.setActivStatus(AgStatus.Approving.name());
                record.setAgentId(orderAdj.getAgentId());
                record.setAgentName(busActRel.getAgentName());
                record.setNetInBusType(busActRel.getNetInBusType());// 数据权限
                record.setDataShiro(BusActRelBusType.cashierApprove.key);
                record.setAgDocPro(busActRel.getAgDocPro());
                record.setAgDocDistrict(busActRel.getAgDocDistrict());

                if (1 != busActRelMapper.insertSelective(record)) {
                    logger.info("出纳申请提交审批，启动审批异常，添加审批关系失败{}:{}", orderAdj.getId(), proce);
                    throw new MessageException("出纳审批流启动失败：添加审批关系失败！");
                }

//                orderAdj.setReviewsStat(AgStatus.Approved.status);
//                orderAdj.setReviewsDate(new Date());
//                // 订单调整更新
//                if (1 != orderAdjMapper.updateByPrimaryKeySelective(orderAdj)) {
//                    throw new MessageException("订单调整数据更新异常！");
//                }

            }
            OOrderExample oOrderExample = new OOrderExample();
            oOrderExample.or().andIdEqualTo(orderAdj.getOrderId()).andStatusEqualTo(Status.STATUS_1.status);
            List<OOrder> oOrders = orderMapper.selectByExample(oOrderExample);
            oOrders.get(0).setOrderStatus(OrderStatus.ENABLE.status);
            orderMapper.updateByPrimaryKeySelective(oOrders.get(0));
            if (orderAdj.getRealRefundAmo().compareTo(BigDecimal.ZERO)>0){
                logger.info("暂时不发送瑞大宝免税额度");
                logger.info("订单调整审批通过,有退款,信息开始发送到kafka:{}",orderAdj.getId());
                paymentDetailService.sendRefundMentToPlatform(orderAdj.getId());
            }
        } else if(actname.equals("reject_end")) { //审批拒绝
            logger.info("订单调整审批完审批拒绝{}", busActRel.getBusId());
            busActRel.setActivStatus(AgStatus.Refuse.name());
            if (1 != busActRelService.updateByPrimaryKey(busActRel)) {
                throw new MessageException("请重新提交！");
            }
            //订单调整数据
            OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(busActRel.getBusId());
            if (orderAdj.getReviewsStat().compareTo(AgStatus.Approving.status) != 0){
                throw new MessageException("该任务审批状态异常!");
            }
            orderAdj.setReviewsStat(AgStatus.Refuse.status);
            orderAdj.setReviewsDate(new Date());
            orderAdj.setRefundStat(null);
            OOrderExample oOrderExample = new OOrderExample();
            oOrderExample.or().andIdEqualTo(orderAdj.getOrderId()).andStatusEqualTo(Status.STATUS_1.status);
            List<OOrder> oOrders = orderMapper.selectByExample(oOrderExample);
            oOrders.get(0).setOrderStatus(OrderStatus.ENABLE.status);
            orderMapper.updateByPrimaryKeySelective(oOrders.get(0));
            if(orderAdj.getOffsetAmount().compareTo(BigDecimal.ZERO)>0&& orderAdj.getLogicalVersion()!=null && orderAdj.getLogicalVersion().compareTo(BigDecimal.ONE)==0){
                AgentResult agentResult = orderOffsetService.OffsetArrearsCancle(orderAdj.getOffsetAmount(), DDTZ.code, orderAdj.getId());
                if (!agentResult.isOK()){
                    throw new MessageException("订单调整抵扣欠款数据更新异常！");
                }
            }

            //订单调整更新
            if (1 != orderAdjMapper.updateByPrimaryKeySelective(orderAdj)) {
                throw new MessageException("订单调整数据更新异常！");
            }
            logger.info("订单调整审批完审批拒绝结束", busActRel.getBusId());
        }
        logger.info("订单调整审批结束", busActRel.getBusId());
        return AgentResult.ok();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public AgentResult approvalTaskSettle(OrderAdj orderAdj) throws ProcessException {

        return orderAdjMapper.updateByPrimaryKeySelective(orderAdj)==1?AgentResult.ok():AgentResult.fail();
    }

    @Override
    @Transactional
    public Map<String, Object> freshRefundAmo(String adjId, String proAmount,String refundAmo,String refundType,String takeAmt) {
        Map<String, Object> resMap = new HashMap();
        OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(adjId);
        orderAdj.setProRefundAmount(new BigDecimal(proAmount));
        if (new BigDecimal(refundType).compareTo(OrderAdjRefundType.CDFQ_XXTK.code)==0){
            orderAdj.setRealRefundAmo(new BigDecimal(refundAmo).setScale(2, RoundingMode.HALF_UP));
            orderAdj.setSettleAmount(BigDecimal.ZERO);
            orderAdj.setRefundType(new BigDecimal(refundType));
        }else if (new BigDecimal(refundType).compareTo(OrderAdjRefundType.CDFQ_GZ.code)==0){
            orderAdj.setSettleAmount(new BigDecimal(refundAmo).setScale(2, RoundingMode.HALF_UP));
            orderAdj.setRealRefundAmo(BigDecimal.ZERO);
            orderAdj.setRefundType(new BigDecimal(refundType));
        }
//        orderAdjMapper.updateByPrimaryKeySelective(orderAdj);
        resMap.put("realRefundAmo",orderAdj.getRealRefundAmo());
        resMap.put("proRefundAmount",orderAdj.getProRefundAmount());
        resMap.put("settleAmount",orderAdj.getSettleAmount());
        return resMap;
    }

    @Override
    @Transactional
    public Map<String,Object> saveProAmo(String adjId,String proAmount){
        Map<String, Object> resMap = new HashMap();
        OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(adjId);
        orderAdj.setProRefundAmount(new BigDecimal(proAmount));
        orderAdjMapper.updateByPrimaryKeySelective(orderAdj);
        resMap.put("realRefundAmo",orderAdj.getRealRefundAmo()==null?"0.00":orderAdj.getRealRefundAmo());
        resMap.put("proRefundAmount",orderAdj.getProRefundAmount()==null?"0.00":orderAdj.getProRefundAmount());
        resMap.put("settleAmount",orderAdj.getSettleAmount()==null?"0.00":orderAdj.getSettleAmount());
        return resMap;
    }

    /**
     * 订单数量调整导出
     * @param map
     * @return
     */
    @Override
    public List<OrderAdjustVo> excelOrderAdjustAll(Map map) {
        if(null != map.get("userId")) {
            Long userId = (Long) map.get("userId");
            List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
            if (orgCodeRes == null && orgCodeRes.size() != 1) {
                return null;
            }
            Map<String, Object> stringObjectMap = orgCodeRes.get(0);
            String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
            map.put("organizationCode", organizationCode);
            List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
            map.put("platfromPerm", platfromPerm);
        }

        List<OrderAdjustVo> orderAdjVoList = orderAdjMapper.excelOrderAdjustAll(map);

        if (null!=orderAdjVoList && orderAdjVoList.size()>0) {
            for (OrderAdjustVo orderAdjustVo : orderAdjVoList) {
                if (StringUtils.isNotBlank(orderAdjustVo.getReviewsStat()) && !orderAdjustVo.getReviewsStat().equals("null")) {
                    String reviewsStatusByValue = AgStatus.getMsg(new BigDecimal(orderAdjustVo.getReviewsStat()));
                    if (null != reviewsStatusByValue) {
                        orderAdjustVo.setReviewsStat(reviewsStatusByValue);
                    }
                }
                if (StringUtils.isNotBlank(orderAdjustVo.getRefundStat()) && !orderAdjustVo.getRefundStat().equals("null")) {
                    Dict refundStatusByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.REFUND_STAT.name(), orderAdjustVo.getRefundStat());
                    if (null != refundStatusByValue) {
                        orderAdjustVo.setRefundStat(refundStatusByValue.getdItemname());
                    }
                }
            }
        }
        return orderAdjVoList;
    }

    /**
     * 查询全部订单调整明细
     * @param par
     * @param page
     * @return
     */
    @Override
    public PageInfo queryUpModelListAll(Map par, Page page) {
        PageInfo pageInfo = new PageInfo();
        if (par == null) return pageInfo;
        if(null!=par.get("userId")) {
            Long userId = (Long) par.get("userId");
            List<Map> platfromPerm = iResourceService.userHasPlatfromPerm(userId);
            par.put("platfromPerm", platfromPerm);
        }
        par.put("page", page);
        pageInfo.setTotal(orderAdjMapper.selectUpModelViewAllCount(par));
        pageInfo.setRows(orderAdjMapper.selectUpModelViewAll(par,page));
        return pageInfo;
    }

    /**
     * 执行订单调整计划
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public AgentResult adjustDoPayPlan(String adjId,OrderAdj orderAdj) throws Exception{
        logger.info("执行订单调整计划,id"+adjId);
        boolean isZero = false;
        boolean sendMsgToPlatm = false;
        Map<String,Object> resMap = new HashMap<>();
        OPaymentExample oPaymentExample = new OPaymentExample();
        oPaymentExample.or().andOrderIdEqualTo(orderAdj.getOrderId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OPayment> oPaymentList = oPaymentMapper.selectByExample(oPaymentExample);
        if (oPaymentList.size() != 1) {
            logger.info("订单调整审批完成:付款单明细未找到:{}", orderAdj.getId());
            throw new MessageException("付款单明细未找到！");
        }
        BigDecimal difAmount = orderAdjDetailMapper.sumDifAmount(orderAdj.getId());
        OPayment oPayment = oPaymentList.get(0);
        oPayment.setPayAmount(oPayment.getPayAmount().subtract(difAmount));//应付金额=原应付金额-差价金额
        BigDecimal re = oPayment.getOutstandingAmount().subtract(difAmount);
        logger.info("订单{}待付{},差价{}",orderAdj.getOrderId(),oPayment.getOutstandingAmount(),difAmount);
        if(re.compareTo(BigDecimal.ZERO)>0){
            //欠款金额=原欠款金额-差价   存在欠款
            oPayment.setOutstandingAmount(re);
        }else {
            //产生退款
            isZero = true;
            oPayment.setOutstandingAmount(BigDecimal.ZERO);//欠款金额=0
            oPayment.setRealAmount(oPayment.getRealAmount().subtract(orderAdj.getRefundAmount()));//已付金额=原已付金额-退款金额
        }
        if(1!=oPaymentMapper.updateByPrimaryKey(oPayment)){//更新原付款单为调整后的金额,
            logger.error("更新付款单失败!付款单id{}",oPayment.getId());
            throw new MessageException("更新付款单失败");
        }
        //调用补款数据入库接口
        if (orderAdj.getRefundAmount().compareTo(BigDecimal.ZERO)>0) {
            /******调用调账接口 开始*********/
            BigDecimal refundAmount = orderAdj.getRefundAmount().subtract(orderAdj.getProRefundAmount()).subtract(orderAdj.getOffsetAmount());
            if (refundAmount.compareTo(BigDecimal.ZERO)>=0){
                logger.info(orderAdj.getId()+"订单调整,退款金额大于0");
                if (orderAdj.getRefundType() != null && orderAdj.getRefundType().compareTo(OrderAdjRefundType.CDFQ_XXTK.code)==0){
                    orderAdj.setRealRefundAmo(refundAmount);
                    orderAdj.setSettleAmount(BigDecimal.ZERO);
                    orderAdj.setRefundStat(RefundStat.REFUNDING.key);
                }else {
                    orderAdj.setRefundType(OrderAdjRefundType.CDFQ_GZ.code);
                    orderAdj.setRealRefundAmo(BigDecimal.ZERO);
                    orderAdj.setSettleAmount(refundAmount);
                    orderAdj.setRefundStat(RefundStat.UNREFUND.key);
                }
                sendMsgToPlatm = true;
            }else {
                logger.info(orderAdj.getId()+"订单调整,退款金额小于0");
                orderAdj.setRefundType(OrderAdjRefundType.CDFQ_GZ.code);
                orderAdj.setRealRefundAmo(BigDecimal.ZERO);
                orderAdj.setSettleAmount(refundAmount);
                orderAdj.setRefundStat(RefundStat.UNREFUND.key);
            }
        }else {
            logger.info(orderAdj.getId()+"订单调整,退款金额小于0");
            orderAdj.setRefundType(OrderAdjRefundType.CDFQ_GZ.code);
            orderAdj.setRealRefundAmo(BigDecimal.ZERO);
            orderAdj.setSettleAmount(BigDecimal.ZERO);
            orderAdj.setRefundStat(RefundStat.UNREFUND.key);
        }

        Calendar orderdate = Calendar.getInstance();
        Calendar d = Calendar.getInstance();
        Calendar temp = Calendar.getInstance();
        String batchCode = d.getTime().getTime() + "";
        OrderAdjDetailExample orderAdjDetailExample = new OrderAdjDetailExample();
        orderAdjDetailExample.or().andAdjIdEqualTo(orderAdj.getId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OrderAdjDetail> orderAdjDetails = orderAdjDetailMapper.selectByExample(orderAdjDetailExample);
        BigDecimal forPayAmount = BigDecimal.ZERO;
        BigDecimal forRealPayAmount = BigDecimal.ZERO;
        for(OrderAdjDetail orderAdjDetail:orderAdjDetails){
            OSubOrder oSubOrder = oSubOrderMapper.selectByPrimaryKey(orderAdjDetail.getSubOrderId());
            oSubOrder.setProNum(oSubOrder.getProNum().subtract(orderAdjDetail.getAdjNum()));
            //计算订单金额
            forPayAmount = forPayAmount.add(oSubOrder.getProPrice().multiply(oSubOrder.getProNum()));
            //优惠后金额的差价
            forRealPayAmount = forRealPayAmount.add(oSubOrder.getProRelPrice().multiply(oSubOrder.getProNum()));
            if (1!=oSubOrderMapper.updateByPrimaryKeySelective(oSubOrder)){
                logger.error("更新子订单信息失败!子订单id{},订单id{}",oSubOrder.getId(),oSubOrder.getOrderId());
                throw new MessageException("更新子订单信息失败");
            };//更新子订单数量为调整后
        }
        //更新订单信息
        OOrder order = orderMapper.selectByPrimaryKey(orderAdj.getOrderId());
        order.setIncentiveAmo(forPayAmount.subtract(forRealPayAmount));
        order.setoAmo(forPayAmount);
        order.setPayAmo(forRealPayAmount);
        //更新新的订单金额到调整的信息表
        orderAdj.setoAmo(order.getoAmo());
        orderAdj.setIncentiveAmo(order.getIncentiveAmo());
        orderAdj.setPayAmo(order.getPayAmo());
        orderAdj.setReviewsStat(AgStatus.Approved.status);
        orderAdj.setReviewsDate(new Date());
        if (isZero) {
            if (StringUtils.isBlank(order.getRemark())){
                order.setRemark("有退款");
            }else if(!order.getRemark().contains("有退款")) {
                order.setRemark(order.getRemark() + "有退款");
            }
        }
//        order.setOrderStatus(OrderStatus.ENABLE.status);
        if( 1 != orderMapper.updateByPrimaryKeySelective(order)){
            logger.error("更新订单失败!订单表id{}",order.getId());
            throw new MessageException("更新订单失败");
        }
        List<BigDecimal> paymentStatus = Stream.of(PaymentStatus.DF.code,PaymentStatus.YQ.code,PaymentStatus.BF.code).collect(toList());
        OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
        oPaymentDetailExample.or().andOrderIdEqualTo(orderAdj.getOrderId())
                .andPaymentStatusIn(paymentStatus)
                .andStatusEqualTo(Status.STATUS_1.status)
                .andBatchCodeEqualTo(orderAdj.getOrgPaymentId()==null?"":orderAdj.getOrgPaymentId());
        List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
        Date beginDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(beginDate);
        c.add(Calendar.MONTH,-1);
        beginDate = c.getTime();
        for(OPaymentDetail oPaymentDetail:oPaymentDetails){
            if (oPaymentDetail.getPaymentStatus().compareTo(PaymentStatus.BF.code) == 0 ){
                oPaymentDetail.setPayAmount(oPaymentDetail.getRealPayAmount());
                oPaymentDetail.setPaymentStatus(PaymentStatus.JQ.code);
            }else {
                oPaymentDetail.setStatus(Status.STATUS_0.status);
            }
            if (1!=oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)){
                logger.error("更新还款计划失败!还款计划id{},订单id{}",oPaymentDetail.getId(),oPaymentDetail.getOrderId());
                throw new MessageException("更新订单失败");
            };
        }
        switch (order.getPaymentMethod()) {
            case "FKFQ":
                temp.setTime(oPayment.getDownPaymentDate());
                if(oPayment.getOutstandingAmount()==null || oPayment.getOutstandingAmount().compareTo(BigDecimal.ZERO)<0){
                    logger.info("代理商订单审批完成:待付金额不能为空:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    throw new MessageException("待付金额不能为空");
                }
                if (isZero){
                    //生成补款单
                    OSupplement oSupplement = new OSupplement();
                    oSupplement.setId(idService.genId(TabId.o_Supplement));
                    oSupplement.setAgentId(orderAdj.getAgentId());
                    oSupplement.setcTime(Calendar.getInstance().getTime());
                    oSupplement.setcUser(orderAdj.getAdjUserId());
                    oSupplement.setOrderId(orderAdj.getOrderId());
                    oSupplement.setPayAmount(re);
                    oSupplement.setRealPayAmount(re);//到账金额
                    oSupplement.setVersion(Status.STATUS_1.status);
                    oSupplement.setPkType(PkType.ORDER_REFUND_BK.code);
                    oSupplement.setSrcId(orderAdj.getId());
                    oSupplement.setReviewStatus(AgStatus.Approved.status);
                    oSupplement.setStatus(Status.STATUS_1.status);
                    oSupplement.setLogicalVersion(String.valueOf(Status.STATUS_1.status));
                    if (1 !=  oSupplementMapper.insert(oSupplement)) {
                        logger.info("订单调整审批完成，有退款:补款生成失败:订单ID:{},付款方式:{}，明细ID:{}",
                                order.getId(),
                                oPayment.getPayMethod(),
                                oSupplement.getId());
                        throw new MessageException("补款处理失败");
                    }
                    //分期数据,增加已结清,退款
                    List<Map> FKFQ_data = StageUtil.stageOrder(
                            re.abs(),
                            1,
                            oPayment.getDownPaymentDate(), temp.get(Calendar.DAY_OF_MONTH));
                    for (Map datum : FKFQ_data) {
                        OPaymentDetail record = new OPaymentDetail();
                        record.setId(idService.genId(TabId.o_payment_detail));
                        record.setBatchCode(batchCode);
                        record.setPaymentId(oPayment.getId());
                        record.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record.setOrderId(oPayment.getOrderId());
                        record.setPayAmount(re);
                        record.setRealPayAmount(re);
                        record.setPlanPayTime((Date) datum.get("date"));
                        record.setPlanNum((BigDecimal) datum.get("count"));
                        record.setAgentId(oPayment.getAgentId());
                        record.setPaymentStatus(PaymentStatus.JQ.code);
                        record.setcUser(oPayment.getUserId());
                        record.setcDate(d.getTime());
                        record.setSrcId(oSupplement.getId());
                        orderAdj.setStagesAmount(BigDecimal.ZERO);//更新为实际的分期金额
                        orderAdj.setNewPaymentId(batchCode);//新的还款计划批次号
                        if (OrderAdjRefundType.CDFQ_GZ.code.compareTo(orderAdj.getRefundType())==0) {
                            if (sendMsgToPlatm){
                                SettleAccounts settleAccounts = new SettleAccounts();
                                settleAccounts.setId(idService.genId(TabId.o_settle_accounts));
                                settleAccounts.setAgentId(orderAdj.getAgentId());//代理商id
                                settleAccounts.setsType(SettleType.ORDER_ADJUST.key);//挂账类型:订单调整
                                settleAccounts.setsTm(orderdate.getTime());
                                settleAccounts.setcTm(orderdate.getTime());
                                settleAccounts.setsStatus(Status.STATUS_0.status);
                                settleAccounts.setcUser(orderAdj.getAdjUserId());
                                settleAccounts.setsAmount(orderAdj.getSettleAmount());
                                settleAccounts.setSrcId(orderAdj.getId());//数据源id
                                settleAccounts.setStatus(Status.STATUS_1.status);
                                settleAccounts.setVersion(Status.STATUS_1.status);
                                if(1!=settleAccountsMapper.insertSelective(settleAccounts)){
                                    return AgentResult.fail("保存挂账记录失败!");
                                };
                                record.setSrcId(settleAccounts.getId());
                            }
                            record.setPayType(PaymentType.GZ.code);
                            record.setSrcType(PamentSrcType.ORDER_ADJ_SETTLE.code);
                        }else {
                            record.setPayType(PaymentType.TK.code);
                            record.setSrcType(PamentSrcType.ORDER_ADJ_REFUND.code);
                        }

                        record.setPayTime(new Date());
                        record.setStatus(Status.STATUS_1.status);
                        record.setVersion(Status.STATUS_1.status);

                        if (1 != oPaymentDetailMapper.insert(record)) {
                            logger.info("代理商订单审批完成:明细生成失败:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}",
                                    order.getId(),
                                    oPayment.getId(),
                                    oPayment.getPayMethod(),
                                    record.getId());
                            throw new MessageException("分期处理");
                        }

                        logger.info("代理商订单审批完成:明细生成:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}",
                                order.getId(),
                                oPayment.getId(),
                                oPayment.getPayMethod(),
                                record.getId());
                    }
                    break;

                }
                if (oPayment.getDownPaymentCount() == null || oPayment.getDownPaymentCount().compareTo(BigDecimal.ZERO) <= 0) {
                    logger.info("代理商订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    throw new MessageException("分期数有误");
                }
                if (oPayment.getDownPaymentDate() == null ) {
                    logger.info("代理商订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    throw new MessageException("分期日期错误");
                }
                logger.info("代理商订单审批完成:处理明细:{},{},{}",
                        order.getId(),
                        oPayment.getId(),
                        oPayment.getPayMethod());
                //如果是自定义分期
                if(StringUtils.isNotBlank(oPayment.getCustomStaging()) && Status.STATUS_1.status.toPlainString().equals(oPayment.getCustomStaging())){
                    //这里不处理首付值处理分期
                    //更新付款分期为审批通过，检查分期金额是否等于待付金额
                    OPaymentDetailExample customStaginDetailQeury = new OPaymentDetailExample();
                    customStaginDetailQeury.or()
                            .andStatusEqualTo(Status.STATUS_0.status)
                            .andPayTypeEqualTo(PaymentType.DKFQ.code)
                            .andOrderIdEqualTo(oPayment.getOrderId())
                            .andPaymentStatusEqualTo(PaymentStatus.DS.code)
                            .andPaymentIdEqualTo(oPayment.getId())
                            .andBatchCodeEqualTo(orderAdj.getNewPaymentId());
                    List<OPaymentDetail> oPaymentDetailsList = oPaymentDetailMapper.selectByExample(customStaginDetailQeury);
                    BigDecimal dkfqAmount = BigDecimal.ZERO;
                    for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                        dkfqAmount =dkfqAmount.add(oPaymentDetail.getPayAmount());
                    }
                    //欠款和代扣分期是否一致，不一致抛出异常
                    if(dkfqAmount.compareTo(oPayment.getOutstandingAmount())!=0){
                        throw new MessageException("待付款和分期欠款不匹配");
                    }
                    //审批通过更新自定义分期为待付款
                    for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                        oPaymentDetail.setRealPayAmount(BigDecimal.ZERO);
                        oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
                        oPaymentDetail.setStatus(Status.STATUS_1.status);
                        if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)){
                            throw new MessageException("付款明细处理失败，请重试！");
                        }
                    }
                    //非自定义分期
                }else{
                    //分期数据
                    List<Map> FKFQ_data = StageUtil.stageOrder(
                            oPayment.getOutstandingAmount(),
                            orderAdj.getOrgPlanNum().intValue(),
                            beginDate, temp.get(Calendar.DAY_OF_MONTH));
                    //明细处理
                    for (Map datum : FKFQ_data) {
                        OPaymentDetail record = new OPaymentDetail();
                        record.setId(idService.genId(TabId.o_payment_detail));
                        record.setBatchCode(batchCode);
                        record.setPaymentId(oPayment.getId());
                        record.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record.setOrderId(oPayment.getOrderId());
                        record.setPayType(PaymentType.DKFQ.code);
                        record.setPayAmount((BigDecimal) datum.get("item"));
                        record.setRealPayAmount(BigDecimal.ZERO);
                        record.setPlanPayTime((Date) datum.get("date"));
                        record.setPlanNum((BigDecimal) datum.get("count"));
                        record.setAgentId(oPayment.getAgentId());
                        record.setPaymentStatus(PaymentStatus.DF.code);
                        record.setcUser(oPayment.getUserId());
                        record.setcDate(d.getTime());
                        record.setStatus(Status.STATUS_1.status);
                        record.setVersion(Status.STATUS_1.status);
                        orderAdj.setStagesAmount(record.getPayAmount());
                        if (1 != oPaymentDetailMapper.insert(record)) {
                            logger.info("代理商订单审批完成:明细生成失败:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}",
                                    order.getId(),
                                    oPayment.getId(),
                                    oPayment.getPayMethod(),
                                    record.getId());
                            throw new MessageException("分期处理");
                        }

                        logger.info("代理商订单审批完成:明细生成:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}",
                                order.getId(),
                                oPayment.getId(),
                                oPayment.getPayMethod(),
                                record.getId());
                    }
                }
                break;

            case "FRFQ":
                temp.setTime(oPayment.getDownPaymentDate());
                if (oPayment.getOutstandingAmount() == null || oPayment.getOutstandingAmount().compareTo(BigDecimal.ZERO) < 0) {
                    logger.info("代理商订单审批完成:待付金额不能为空:{},{}", order.getId(), oPayment.getPayMethod());
                    throw new MessageException("待付金额不能为空");
                }
                if (isZero){
                    //生成补款单
                    OSupplement oSupplement = new OSupplement();
                    oSupplement.setId(idService.genId(TabId.o_Supplement));
                    oSupplement.setAgentId(orderAdj.getAgentId());
                    oSupplement.setcTime(Calendar.getInstance().getTime());
                    oSupplement.setcUser(orderAdj.getAdjUserId());
                    oSupplement.setOrderId(orderAdj.getOrderId());
                    oSupplement.setPayAmount(re);
                    oSupplement.setRealPayAmount(re);//到账金额
                    oSupplement.setVersion(Status.STATUS_1.status);
                    oSupplement.setPkType(PkType.ORDER_REFUND_BK.code);
                    oSupplement.setSrcId(orderAdj.getId());
                    oSupplement.setReviewStatus(AgStatus.Approved.status);
                    oSupplement.setStatus(Status.STATUS_1.status);
                    oSupplement.setLogicalVersion(String.valueOf(Status.STATUS_1.status));
                    if (1 !=  oSupplementMapper.insert(oSupplement)) {
                        logger.info("订单调整审批完成，有退款:补款生成失败:订单ID:{},付款方式:{}，明细ID:{}",
                                order.getId(),
                                oPayment.getPayMethod(),
                                oSupplement.getId());
                        throw new MessageException("补款处理失败");
                    }
                    //分期数据
                    List<Map> FRFQ_data = StageUtil.stageOrder(
                            re.abs(),
                            1,
                            oPayment.getDownPaymentDate(), temp.get(Calendar.DAY_OF_MONTH));
                    //明细处理
                    for (Map datum : FRFQ_data) {
                        OPaymentDetail record = new OPaymentDetail();
                        record.setId(idService.genId(TabId.o_payment_detail));
                        record.setBatchCode(batchCode);
                        record.setPaymentId(oPayment.getId());
                        record.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record.setOrderId(oPayment.getOrderId());
                        record.setPayAmount(re);
                        record.setRealPayAmount(re);
                        record.setPlanPayTime((Date) datum.get("date"));
                        record.setPlanNum((BigDecimal) datum.get("count"));
                        record.setAgentId(oPayment.getAgentId());
                        record.setPaymentStatus(PaymentStatus.JQ.code);
                        record.setcUser(oPayment.getUserId());
                        record.setcDate(d.getTime());
                        record.setSrcId(oSupplement.getId());
                        orderAdj.setStagesAmount(BigDecimal.ZERO);//更新为实际的分期金额
                        orderAdj.setNewPaymentId(batchCode);//新的还款计划批次号
                        if (OrderAdjRefundType.CDFQ_GZ.code.compareTo(orderAdj.getRefundType())==0) {
                            if (sendMsgToPlatm){
                                SettleAccounts settleAccounts = new SettleAccounts();
                                settleAccounts.setId(idService.genId(TabId.o_settle_accounts));
                                settleAccounts.setAgentId(orderAdj.getAgentId());//代理商id
                                settleAccounts.setsType(SettleType.ORDER_ADJUST.key);//挂账类型:订单调整
                                settleAccounts.setsTm(orderdate.getTime());
                                settleAccounts.setcTm(orderdate.getTime());
                                settleAccounts.setsStatus(Status.STATUS_0.status);
                                settleAccounts.setcUser(orderAdj.getAdjUserId());
                                settleAccounts.setsAmount(orderAdj.getSettleAmount());
                                settleAccounts.setSrcId(orderAdj.getId());//数据源id
                                settleAccounts.setStatus(Status.STATUS_1.status);
                                settleAccounts.setVersion(Status.STATUS_1.status);
                                if(1!=settleAccountsMapper.insertSelective(settleAccounts)){
                                    return AgentResult.fail("保存挂账记录失败!");
                                };
                                record.setSrcId(settleAccounts.getId());
                            }
                            record.setPayType(PaymentType.GZ.code);
                            record.setSrcType(PamentSrcType.ORDER_ADJ_SETTLE.code);
                        }else {
                            record.setPayType(PaymentType.TK.code);
                            record.setSrcType(PamentSrcType.ORDER_ADJ_REFUND.code);
                        }
                        record.setPayTime(new Date());
                        record.setStatus(Status.STATUS_1.status);
                        record.setVersion(Status.STATUS_1.status);
                        if (1 != oPaymentDetailMapper.insert(record)) {
                            logger.info("订单调整审批完成:明细生成失败:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}",
                                    order.getId(),
                                    oPayment.getId(),
                                    oPayment.getPayMethod(),
                                    record.getId());
                            throw new MessageException("分期处理");
                        }

                        logger.info("订单调整审批完成:明细生成:订单ID:{},付款方式:{}，明细ID:{}",
                                order.getId(),
                                oPayment.getPayMethod(),
                                record.getId());
                    }
                    break;
                }
                if (oPayment.getDownPaymentCount() == null || oPayment.getDownPaymentCount().compareTo(BigDecimal.ZERO) <= 0) {
                    logger.info("代理商订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    throw new MessageException("分期数有误");
                }
                if (oPayment.getDownPaymentDate() == null  ) {
                    logger.info("代理商订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    throw new MessageException("分期日期错误");
                }

                logger.info("代理商订单审批完成处理明细完成:{},{},{}",
                        order.getId(),
                        oPayment.getId(),
                        oPayment.getPayMethod());
                //如果是自定义分期
                if(StringUtils.isNotBlank(oPayment.getCustomStaging()) && Status.STATUS_1.status.toPlainString().equals(oPayment.getCustomStaging())){
                    //这里不处理首付值处理分期
                    //更新付款分期为审批通过，检查分期金额是否等于待付金额
                    OPaymentDetailExample customStaginDetailQeury = new OPaymentDetailExample();
                    customStaginDetailQeury.or()
                            .andStatusEqualTo(Status.STATUS_0.status)
                            .andPayTypeEqualTo(PaymentType.FRFQ.code)
                            .andOrderIdEqualTo(oPayment.getOrderId())
                            .andPaymentStatusEqualTo(PaymentStatus.DS.code)
                            .andPaymentIdEqualTo(oPayment.getId())
                            .andBatchCodeEqualTo(orderAdj.getNewPaymentId());
                    List<OPaymentDetail> oPaymentDetailsList = oPaymentDetailMapper.selectByExample(customStaginDetailQeury);
                    BigDecimal dkfqAmount = BigDecimal.ZERO;
                    for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                        dkfqAmount =dkfqAmount.add(oPaymentDetail.getPayAmount());
                    }
                    //欠款和代扣分期是否一致，不一致抛出异常
                    if(dkfqAmount.compareTo(oPayment.getOutstandingAmount())!=0){
                        throw new MessageException("待付款和分期欠款不匹配");
                    }
                    //审批通过更新自定义分期为待付款
                    for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                        oPaymentDetail.setRealPayAmount(BigDecimal.ZERO);
                        oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
                        oPaymentDetail.setStatus(Status.STATUS_1.status);
                        if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)){
                            throw new MessageException("付款明细处理失败，请重试！");
                        }
                    }
                    //非自定义分期
                }else{
                    //分期数据
                    List<Map> FRFQ_data = StageUtil.stageOrder(
                            oPayment.getOutstandingAmount(),
                            orderAdj.getOrgPlanNum().intValue(),
                            beginDate, temp.get(Calendar.DAY_OF_MONTH));

                    //明细处理
                    for (Map datum : FRFQ_data) {
                        OPaymentDetail record = new OPaymentDetail();
                        record.setId(idService.genId(TabId.o_payment_detail));
                        record.setBatchCode(batchCode);
                        record.setPaymentId(oPayment.getId());
                        record.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record.setOrderId(oPayment.getOrderId());
                        record.setPayType(PaymentType.FRFQ.code);
                        record.setPayAmount((BigDecimal) datum.get("item"));
                        record.setRealPayAmount(BigDecimal.ZERO);
                        record.setPlanPayTime((Date) datum.get("date"));
                        record.setPlanNum((BigDecimal) datum.get("count"));
                        record.setAgentId(oPayment.getAgentId());
                        record.setPaymentStatus(PaymentStatus.DF.code);
                        record.setcUser(oPayment.getUserId());
                        record.setcDate(d.getTime());
                        record.setStatus(Status.STATUS_1.status);
                        record.setVersion(Status.STATUS_1.status);
                        orderAdj.setStagesAmount(record.getPayAmount());//更新为实际的分期金额
                        orderAdj.setNewPaymentId(batchCode);//新的还款计划批次号
                        if (1 != oPaymentDetailMapper.insert(record)) {
                            logger.info("订单调整审批完成:明细生成失败:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}",
                                    order.getId(),
                                    oPayment.getId(),
                                    oPayment.getPayMethod(),
                                    record.getId());
                            throw new MessageException("分期处理");
                        }

                        logger.info("订单调整审批完成:明细生成:订单ID:{},付款方式:{}，明细ID:{}",
                                order.getId(),
                                oPayment.getPayMethod(),
                                record.getId());
                    }
                }
                break;
            case "XXDK":

                if (oPayment.getActualReceipt() == null || oPayment.getActualReceipt().compareTo(BigDecimal.ZERO) < 0) {
                    logger.info("订单审批完成:实际收款金额不能为空:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    throw new MessageException("实际收款金额不能为空");
                }
                if (isZero){
                    //生成补款单
                    OSupplement oSupplement = new OSupplement();
                    oSupplement.setId(idService.genId(TabId.o_Supplement));
                    oSupplement.setAgentId(orderAdj.getAgentId());
                    oSupplement.setcTime(Calendar.getInstance().getTime());
                    oSupplement.setcUser(orderAdj.getAdjUserId());
                    oSupplement.setOrderId(orderAdj.getOrderId());
                    oSupplement.setPayAmount(re);
                    oSupplement.setRealPayAmount(re);//到账金额
                    oSupplement.setVersion(Status.STATUS_1.status);
                    oSupplement.setPkType(PkType.ORDER_REFUND_BK.code);
                    oSupplement.setSrcId(orderAdj.getId());
                    oSupplement.setReviewStatus(AgStatus.Approved.status);
                    oSupplement.setStatus(Status.STATUS_1.status);
                    oSupplement.setLogicalVersion(String.valueOf(Status.STATUS_1.status));
                    if (1 !=  oSupplementMapper.insert(oSupplement)) {
                        logger.info("订单调整审批完成，有退款:补款生成失败:订单ID:{},付款方式:{}，明细ID:{}",
                                order.getId(),
                                oPayment.getPayMethod(),
                                oSupplement.getId());
                        throw new MessageException("补款处理失败");
                    }
                    //添加打款明细
                    OPaymentDetail record_XXDK = new OPaymentDetail();
                    record_XXDK.setId(idService.genId(TabId.o_payment_detail));
                    record_XXDK.setBatchCode(batchCode);
                    record_XXDK.setPaymentId(oPayment.getId());
                    record_XXDK.setPaymentType(PamentIdType.ORDER_FKD.code);
                    record_XXDK.setOrderId(oPayment.getOrderId());
                    record_XXDK.setPayAmount(re);
                    record_XXDK.setRealPayAmount(re);
                    record_XXDK.setPlanPayTime(d.getTime());
                    record_XXDK.setPlanNum(Status.STATUS_0.status);
                    record_XXDK.setAgentId(oPayment.getAgentId());
                    record_XXDK.setPaymentStatus(PaymentStatus.JQ.code);
                    record_XXDK.setcUser(oPayment.getUserId());
                    record_XXDK.setcDate(d.getTime());
                    record_XXDK.setSrcId(oSupplement.getId());
                    orderAdj.setStagesAmount(BigDecimal.ZERO);//更新为实际的分期金额
                    orderAdj.setNewPaymentId(batchCode);//新的还款计划批次号
                    if (OrderAdjRefundType.CDFQ_GZ.code.compareTo(orderAdj.getRefundType())==0) {
                        if (sendMsgToPlatm){
                            SettleAccounts settleAccounts = new SettleAccounts();
                            settleAccounts.setId(idService.genId(TabId.o_settle_accounts));
                            settleAccounts.setAgentId(orderAdj.getAgentId());//代理商id
                            settleAccounts.setsType(SettleType.ORDER_ADJUST.key);//挂账类型:订单调整
                            settleAccounts.setsTm(orderdate.getTime());
                            settleAccounts.setcTm(orderdate.getTime());
                            settleAccounts.setsStatus(Status.STATUS_0.status);
                            settleAccounts.setcUser(orderAdj.getAdjUserId());
                            settleAccounts.setsAmount(orderAdj.getSettleAmount());
                            settleAccounts.setSrcId(orderAdj.getId());//数据源id
                            settleAccounts.setStatus(Status.STATUS_1.status);
                            settleAccounts.setVersion(Status.STATUS_1.status);
                            if(1!=settleAccountsMapper.insertSelective(settleAccounts)){
                                return AgentResult.fail("保存挂账记录失败!");
                            };
                            record_XXDK.setSrcId(settleAccounts.getId());
                        }
                        record_XXDK.setPayType(PaymentType.GZ.code);
                        record_XXDK.setSrcType(PamentSrcType.ORDER_ADJ_SETTLE.code);
                    }else {
                        record_XXDK.setPayType(PaymentType.TK.code);
                        record_XXDK.setSrcType(PamentSrcType.ORDER_ADJ_REFUND.code);
                    }
                    record_XXDK.setPayTime(new Date());
                    record_XXDK.setStatus(Status.STATUS_1.status);
                    record_XXDK.setVersion(Status.STATUS_1.status);
                    if (1 != oPaymentDetailMapper.insert(record_XXDK)) {
                        throw new MessageException("打款明细错误");
                    }
                    logger.info("订单调整审批完成处理明细完成首付数据成功{}:{},{}",
                            order.getId(),
                            oPayment.getOutstandingAmount(),
                            oPayment.getPayMethod());
                    break;
                }

                //未付清生成待付明细
                if (oPayment.getOutstandingAmount().compareTo(BigDecimal.ZERO) > 0) {
                    //添加打款明细
                    OPaymentDetail record_XXDK = new OPaymentDetail();
                    record_XXDK.setId(idService.genId(TabId.o_payment_detail));
                    record_XXDK.setBatchCode(batchCode);
                    record_XXDK.setPaymentId(oPayment.getId());
                    record_XXDK.setPaymentType(PamentIdType.ORDER_FKD.code);
                    record_XXDK.setOrderId(oPayment.getOrderId());
                    record_XXDK.setPayType(PaymentType.DK.code);
                    record_XXDK.setPayAmount(oPayment.getOutstandingAmount());
                    record_XXDK.setRealPayAmount(BigDecimal.ZERO);
                    record_XXDK.setPlanPayTime(d.getTime());
                    record_XXDK.setPlanNum(Status.STATUS_0.status);
                    record_XXDK.setAgentId(oPayment.getAgentId());
                    record_XXDK.setPaymentStatus(PaymentStatus.DF.code);
                    record_XXDK.setcUser(oPayment.getUserId());
                    record_XXDK.setcDate(d.getTime());
                    record_XXDK.setStatus(Status.STATUS_1.status);
                    record_XXDK.setVersion(Status.STATUS_1.status);
                    orderAdj.setStagesAmount(record_XXDK.getPayAmount());//更新为实际的分期金额
                    orderAdj.setNewPaymentId(batchCode);//新的还款计划批次号
                    if (1 != oPaymentDetailMapper.insert(record_XXDK)) {
                        throw new MessageException("打款明细错误");
                    }
                    logger.info("代理商订单审批完成处理明细完成首付数据成功{}:{},{}",
                            order.getId(),
                            oPayment.getOutstandingAmount(),
                            oPayment.getPayMethod());
                    break;
                }
            case "SF1"://首付+分润分期
                temp.setTime(oPayment.getDownPaymentDate());
                if(oPayment.getDownPayment()==null || oPayment.getDownPayment().compareTo(BigDecimal.ZERO)<0){
                    logger.info("订单调整审批完成:首付金额为空:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    throw new MessageException("首付金额为空");
                }
                if (isZero){
                    //生成补款单
                    OSupplement oSupplement = new OSupplement();
                    oSupplement.setId(idService.genId(TabId.o_Supplement));
                    oSupplement.setAgentId(orderAdj.getAgentId());
                    oSupplement.setcTime(Calendar.getInstance().getTime());
                    oSupplement.setcUser(orderAdj.getAdjUserId());
                    oSupplement.setOrderId(orderAdj.getOrderId());
                    oSupplement.setPayAmount(re);
                    oSupplement.setRealPayAmount(re);//到账金额
                    oSupplement.setVersion(Status.STATUS_1.status);
                    oSupplement.setPkType(PkType.ORDER_REFUND_BK.code);
                    oSupplement.setSrcId(orderAdj.getId());
                    oSupplement.setReviewStatus(AgStatus.Approved.status);
                    oSupplement.setStatus(Status.STATUS_1.status);
                    oSupplement.setLogicalVersion(String.valueOf(Status.STATUS_1.status));
                    if (1 !=  oSupplementMapper.insert(oSupplement)) {
                        logger.info("订单调整审批完成，有退款:补款生成失败:订单ID:{},付款方式:{}，明细ID:{}",
                                order.getId(),
                                oPayment.getPayMethod(),
                                oSupplement.getId());
                        throw new MessageException("补款处理失败");
                    }
                    //分期数据
                    List<Map> SF1_data = StageUtil.stageOrder(
                            re.abs(),
                            1,
                            oPayment.getDownPaymentDate(), temp.get(Calendar.DAY_OF_MONTH));
                    //明细处理
                    for (Map datum : SF1_data) {
                        OPaymentDetail record = new OPaymentDetail();
                        record.setId(idService.genId(TabId.o_payment_detail));
                        record.setBatchCode(batchCode);
                        record.setPaymentId(oPayment.getId());
                        record.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record.setOrderId(oPayment.getOrderId());
                        record.setPayAmount(re);
                        record.setRealPayAmount(re);
                        record.setPlanPayTime((Date) datum.get("date"));
                        record.setPlanNum((BigDecimal) datum.get("count"));
                        record.setAgentId(oPayment.getAgentId());
                        record.setPaymentStatus(PaymentStatus.JQ.code);
                        record.setcUser(oPayment.getUserId());
                        record.setcDate(d.getTime());
                        record.setSrcId(oSupplement.getId());
                        orderAdj.setStagesAmount(BigDecimal.ZERO);//更新为实际的分期金额
                        orderAdj.setNewPaymentId(batchCode);//新的还款计划批次号
                        if (OrderAdjRefundType.CDFQ_GZ.code.compareTo(orderAdj.getRefundType())==0) {
                            if(sendMsgToPlatm){
                                SettleAccounts settleAccounts = new SettleAccounts();
                                settleAccounts.setId(idService.genId(TabId.o_settle_accounts));
                                settleAccounts.setAgentId(orderAdj.getAgentId());//代理商id
                                settleAccounts.setsType(SettleType.ORDER_ADJUST.key);//挂账类型:订单调整
                                settleAccounts.setsTm(orderdate.getTime());
                                settleAccounts.setcTm(orderdate.getTime());
                                settleAccounts.setsStatus(Status.STATUS_0.status);
                                settleAccounts.setcUser(orderAdj.getAdjUserId());
                                settleAccounts.setsAmount(orderAdj.getSettleAmount());
                                settleAccounts.setSrcId(orderAdj.getId());//数据源id
                                settleAccounts.setStatus(Status.STATUS_1.status);
                                settleAccounts.setVersion(Status.STATUS_1.status);
                                if(1!=settleAccountsMapper.insertSelective(settleAccounts)){
                                    return AgentResult.fail("保存挂账记录失败!");
                                };
                                record.setSrcId(settleAccounts.getId());
                            }
                            record.setPayType(PaymentType.GZ.code);
                            record.setSrcType(PamentSrcType.ORDER_ADJ_SETTLE.code);
                        }else {
                            record.setPayType(PaymentType.TK.code);
                            record.setSrcType(PamentSrcType.ORDER_ADJ_REFUND.code);
                        }
                        record.setPayTime(new Date());
                        record.setStatus(Status.STATUS_1.status);
                        record.setVersion(Status.STATUS_1.status);
                        if (1 != oPaymentDetailMapper.insert(record)) {
                            logger.info("订单调整审批完成:明细生成失败:订单ID:{},付款方式:{}，明细ID:{}",
                                    order.getId(),
                                    oPayment.getPayMethod(),
                                    record.getId());
                            throw new MessageException("分期处理");
                        }
                        logger.info("订单调整审批完成:明细生成:订单ID:{},付款方式:{}，明细ID:{}",
                                order.getId(),
                                oPayment.getPayMethod(),
                                record.getId());
                    }


                    break;
                }
                if(oPayment.getDownPaymentCount()==null || oPayment.getDownPaymentCount().compareTo(BigDecimal.ZERO)<=0){
                    logger.info("订单调整审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    throw new MessageException("分期数有误");
                }
                if(oPayment.getActualReceipt()==null || oPayment.getActualReceipt().compareTo(BigDecimal.ZERO)<=0){
                    logger.info("订单调整审批完成:实际收款金额不能为空:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    throw new MessageException("实际收款金额不能为空");
                }
                //如果是自定义分期
                if(StringUtils.isNotBlank(oPayment.getCustomStaging()) && Status.STATUS_1.status.toPlainString().equals(oPayment.getCustomStaging())){
                    //这里不处理首付值处理分期
                    //更新付款分期为审批通过，检查分期金额是否等于待付金额
                    OPaymentDetailExample customStaginDetailQeury = new OPaymentDetailExample();
                    customStaginDetailQeury.or()
                            .andStatusEqualTo(Status.STATUS_0.status)
                            .andPayTypeEqualTo(PaymentType.FRFQ.code)
                            .andOrderIdEqualTo(oPayment.getOrderId())
                            .andPaymentStatusEqualTo(PaymentStatus.DS.code)
                            .andPaymentIdEqualTo(oPayment.getId())
                            .andBatchCodeEqualTo(orderAdj.getNewPaymentId());
                    List<OPaymentDetail> oPaymentDetailsList = oPaymentDetailMapper.selectByExample(customStaginDetailQeury);
                    BigDecimal dkfqAmount = BigDecimal.ZERO;
                    for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                        dkfqAmount =dkfqAmount.add(oPaymentDetail.getPayAmount());
                    }
                    //欠款和代扣分期是否一致，不一致抛出异常
                    if(dkfqAmount.compareTo(oPayment.getOutstandingAmount())!=0){
                        throw new MessageException("待付款和分期欠款不匹配");
                    }
                    //审批通过更新自定义分期为待付款
                    for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                        oPaymentDetail.setRealPayAmount(BigDecimal.ZERO);
                        oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
                        oPaymentDetail.setStatus(Status.STATUS_1.status);
                        if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)){
                            throw new MessageException("付款明细处理失败，请重试！");
                        }
                    }
                    //非自定义分期
                }else{
                    //分期数据
                    List<Map> SF1_data = StageUtil.stageOrder(
                            oPayment.getOutstandingAmount(),
                            orderAdj.getOrgPlanNum().intValue(),
                            beginDate, temp.get(Calendar.DAY_OF_MONTH));

                    //明细处理
                    for (Map datum : SF1_data) {
                        OPaymentDetail record = new OPaymentDetail();
                        record.setId(idService.genId(TabId.o_payment_detail));
                        record.setBatchCode(batchCode);
                        record.setPaymentId(oPayment.getId());
                        record.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record.setOrderId(oPayment.getOrderId());
                        record.setPayType(PaymentType.FRFQ.code);
                        record.setPayAmount((BigDecimal) datum.get("item"));
                        record.setRealPayAmount(BigDecimal.ZERO);
                        record.setPlanPayTime((Date) datum.get("date"));
                        record.setPlanNum((BigDecimal) datum.get("count"));
                        record.setAgentId(oPayment.getAgentId());
                        record.setPaymentStatus(PaymentStatus.DF.code);
                        record.setcUser(oPayment.getUserId());
                        record.setcDate(d.getTime());
                        record.setStatus(Status.STATUS_1.status);
                        record.setVersion(Status.STATUS_1.status);
                        orderAdj.setStagesAmount(record.getPayAmount());//更新为实际的分期金额
                        orderAdj.setNewPaymentId(batchCode);//新的还款计划批次号
                        if (1 != oPaymentDetailMapper.insert(record)) {
                            logger.info("订单调整审批完成:明细生成失败:订单ID:{},付款方式:{}，明细ID:{}",
                                    order.getId(),
                                    oPayment.getPayMethod(),
                                    record.getId());
                            throw new MessageException("分期处理");
                        }
                        logger.info("订单调整审批完成:明细生成:订单ID:{},付款方式:{}，明细ID:{}",
                                order.getId(),
                                oPayment.getPayMethod(),
                                record.getId());
                    }
                }
                break;

            case "SF2"://打款分期
                temp.setTime(oPayment.getDownPaymentDate());
                if(oPayment.getDownPayment()==null || oPayment.getDownPayment().compareTo(BigDecimal.ZERO)<0){
                    logger.info("订单审批完成:首付金额为空:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    throw new MessageException("首付金额为空");
                }
                if (isZero){
                    //生成补款单
                    OSupplement oSupplement = new OSupplement();
                    oSupplement.setId(idService.genId(TabId.o_Supplement));
                    oSupplement.setAgentId(orderAdj.getAgentId());
                    oSupplement.setcTime(Calendar.getInstance().getTime());
                    oSupplement.setcUser(orderAdj.getAdjUserId());
                    oSupplement.setOrderId(orderAdj.getOrderId());
                    oSupplement.setPayAmount(re);
                    oSupplement.setRealPayAmount(re);//到账金额
                    oSupplement.setVersion(Status.STATUS_1.status);
                    oSupplement.setPkType(PkType.ORDER_REFUND_BK.code);
                    oSupplement.setSrcId(orderAdj.getId());
                    oSupplement.setReviewStatus(AgStatus.Approved.status);
                    oSupplement.setStatus(Status.STATUS_1.status);
                    oSupplement.setLogicalVersion(String.valueOf(Status.STATUS_1.status));
                    if (1 !=  oSupplementMapper.insert(oSupplement)) {
                        logger.info("订单调整审批完成，有退款:补款生成失败:订单ID:{},付款方式:{}，明细ID:{}",
                                order.getId(),
                                oPayment.getPayMethod(),
                                oSupplement.getId());
                        throw new MessageException("补款处理失败");
                    }
                    //分期数据
                    List<Map> SF2_data = StageUtil.stageOrder(
                            re,
                            1,
                            oPayment.getDownPaymentDate(), temp.get(Calendar.DAY_OF_MONTH));
                    //明细处理
                    for (Map datum : SF2_data) {
                        OPaymentDetail record = new OPaymentDetail();
                        record.setId(idService.genId(TabId.o_payment_detail));
                        record.setBatchCode(batchCode);
                        record.setPaymentId(oPayment.getId());
                        record.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record.setOrderId(oPayment.getOrderId());
                        record.setPayAmount(re);
                        record.setRealPayAmount(re);
                        record.setPlanPayTime((Date) datum.get("date"));
                        record.setPlanNum((BigDecimal) datum.get("count"));
                        record.setAgentId(oPayment.getAgentId());
                        record.setPaymentStatus(PaymentStatus.JQ.code);
                        record.setcUser(oPayment.getUserId());
                        record.setcDate(d.getTime());
                        record.setPayTime(new Date());
                        record.setStatus(Status.STATUS_1.status);
                        record.setVersion(Status.STATUS_1.status);
                        record.setSrcId(oSupplement.getId());
                        orderAdj.setStagesAmount(BigDecimal.ZERO);
                        orderAdj.setNewPaymentId(batchCode);//新的还款计划批次号
                        if (OrderAdjRefundType.CDFQ_GZ.code.compareTo(orderAdj.getRefundType())==0) {
                            if (sendMsgToPlatm){
                                SettleAccounts settleAccounts = new SettleAccounts();
                                settleAccounts.setId(idService.genId(TabId.o_settle_accounts));
                                settleAccounts.setAgentId(orderAdj.getAgentId());//代理商id
                                settleAccounts.setsType(SettleType.ORDER_ADJUST.key);//挂账类型:订单调整
                                settleAccounts.setsTm(orderdate.getTime());
                                settleAccounts.setcTm(orderdate.getTime());
                                settleAccounts.setsStatus(Status.STATUS_0.status);
                                settleAccounts.setcUser(orderAdj.getAdjUserId());
                                settleAccounts.setsAmount(orderAdj.getSettleAmount());
                                settleAccounts.setSrcId(orderAdj.getId());//数据源id
                                settleAccounts.setStatus(Status.STATUS_1.status);
                                settleAccounts.setVersion(Status.STATUS_1.status);
                                if(1!=settleAccountsMapper.insertSelective(settleAccounts)){
                                    return AgentResult.fail("保存挂账记录失败!");
                                };
                                record.setSrcId(settleAccounts.getId());
                            }
                            record.setPayType(PaymentType.GZ.code);
                            record.setSrcType(PamentSrcType.ORDER_ADJ_SETTLE.code);
                        }else {
                            record.setPayType(PaymentType.TK.code);
                            record.setSrcType(PamentSrcType.ORDER_ADJ_REFUND.code);
                        }
                        if (1 != oPaymentDetailMapper.insert(record)) {
                            logger.info("订单调整审批完成:明细生成失败:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}", order.getId(), oPayment.getId(), oPayment.getPayMethod(), record.getId());
                            throw new MessageException("分期处理");
                        }
                        logger.info("订单调整审批完成:明细生成:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}", order.getId(), oPayment.getId(), oPayment.getPayMethod(), record.getId());
                    }
                    logger.info("订单调整审批完成处理明细完成{}:{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    break;
                }
                if(oPayment.getDownPaymentCount()==null || oPayment.getDownPaymentCount().compareTo(BigDecimal.ZERO)<=0){
                    logger.info("订单审批完成:分期数据为错误:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    throw new MessageException("分期数有误");
                }
                if(oPayment.getActualReceipt()==null || oPayment.getActualReceipt().compareTo(BigDecimal.ZERO)<=0){
                    logger.info("订单审批完成:实际收款金额不能为空:{},{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                    throw new MessageException("实际收款金额不能为空");
                }
                //如果是自定义分期
                if(StringUtils.isNotBlank(oPayment.getCustomStaging()) && Status.STATUS_1.status.toPlainString().equals(oPayment.getCustomStaging())){
                    //这里不处理首付值处理分期
                    //更新付款分期为审批通过，检查分期金额是否等于待付金额
                    OPaymentDetailExample customStaginDetailQeury = new OPaymentDetailExample();
                    customStaginDetailQeury.or()
                            .andStatusEqualTo(Status.STATUS_0.status)
                            .andPayTypeEqualTo(PaymentType.DKFQ.code)
                            .andOrderIdEqualTo(oPayment.getOrderId())
                            .andPaymentStatusEqualTo(PaymentStatus.DS.code)
                            .andPaymentIdEqualTo(oPayment.getId())
                            .andBatchCodeEqualTo(orderAdj.getNewPaymentId());
                    List<OPaymentDetail> oPaymentDetailsList = oPaymentDetailMapper.selectByExample(customStaginDetailQeury);
                    BigDecimal dkfqAmount = BigDecimal.ZERO;
                    for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                        dkfqAmount =dkfqAmount.add(oPaymentDetail.getPayAmount());
                    }
                    //欠款和代扣分期是否一致，不一致抛出异常
                    if(dkfqAmount.compareTo(oPayment.getOutstandingAmount())!=0){
                        throw new MessageException("待付款和分期欠款不匹配");
                    }
                    //审批通过更新自定义分期为待付款
                    for (OPaymentDetail oPaymentDetail : oPaymentDetailsList) {
                        oPaymentDetail.setRealPayAmount(BigDecimal.ZERO);
                        oPaymentDetail.setPaymentStatus(PaymentStatus.DF.code);
                        oPaymentDetail.setStatus(Status.STATUS_1.status);
                        if(1!=oPaymentDetailMapper.updateByPrimaryKeySelective(oPaymentDetail)){
                            throw new MessageException("付款明细处理失败，请重试！");
                        }
                    }
                    //非自定义分期
                }else{
                    //分期数据
                    List<Map> SF2_data = StageUtil.stageOrder(
                            oPayment.getOutstandingAmount(),
                            orderAdj.getOrgPlanNum().intValue(),
                            beginDate, temp.get(Calendar.DAY_OF_MONTH));

                    //明细处理
                    for (Map datum : SF2_data) {
                        OPaymentDetail record = new OPaymentDetail();
                        record.setId(idService.genId(TabId.o_payment_detail));
                        record.setBatchCode(batchCode);
                        record.setPaymentId(oPayment.getId());
                        record.setPaymentType(PamentIdType.ORDER_FKD.code);
                        record.setOrderId(oPayment.getOrderId());
                        record.setPayType(PaymentType.DKFQ.code);
                        record.setPayAmount((BigDecimal) datum.get("item"));
                        record.setRealPayAmount(BigDecimal.ZERO);
                        record.setPlanPayTime((Date) datum.get("date"));
                        record.setPlanNum((BigDecimal) datum.get("count"));
                        record.setAgentId(oPayment.getAgentId());
                        record.setPaymentStatus(PaymentStatus.DF.code);
                        record.setcUser(oPayment.getUserId());
                        record.setcDate(d.getTime());
                        record.setStatus(Status.STATUS_1.status);
                        record.setVersion(Status.STATUS_1.status);
                        orderAdj.setStagesAmount(record.getPayAmount());
                        orderAdj.setNewPaymentId(batchCode);//新的还款计划批次号
                        if (1 != oPaymentDetailMapper.insert(record)) {
                            logger.info("订单调整审批完成:明细生成失败:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}", order.getId(), oPayment.getId(), oPayment.getPayMethod(), record.getId());
                            throw new MessageException("分期处理");
                        }
                        logger.info("订单调整审批完成:明细生成:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}", order.getId(), oPayment.getId(), oPayment.getPayMethod(), record.getId());

                    }
                }

                logger.info("订单调整审批完成处理明细完成{}:{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                break;
            case "QT":
                //产生退款
                if (isZero) {
                    //生成补款单
                    OSupplement oSupplement = new OSupplement();
                    oSupplement.setId(idService.genId(TabId.o_Supplement));
                    oSupplement.setAgentId(orderAdj.getAgentId());
                    oSupplement.setcTime(Calendar.getInstance().getTime());
                    oSupplement.setcUser(orderAdj.getAdjUserId());
                    oSupplement.setOrderId(orderAdj.getOrderId());
                    oSupplement.setPayAmount(re);
                    oSupplement.setRealPayAmount(re);//到账金额
                    oSupplement.setVersion(Status.STATUS_1.status);
                    oSupplement.setPkType(PkType.ORDER_REFUND_BK.code);
                    oSupplement.setSrcId(orderAdj.getId());
                    oSupplement.setReviewStatus(AgStatus.Approved.status);
                    oSupplement.setStatus(Status.STATUS_1.status);
                    oSupplement.setLogicalVersion(String.valueOf(Status.STATUS_1.status));
                    if (1 !=  oSupplementMapper.insert(oSupplement)) {
                        logger.info("订单调整审批完成，有退款:补款生成失败:订单ID:{},付款方式:{}，明细ID:{}",
                                order.getId(),
                                oPayment.getPayMethod(),
                                oSupplement.getId());
                        throw new MessageException("补款处理失败");
                    }
                    OPaymentDetail record_QT = new OPaymentDetail();
                    record_QT.setId(idService.genId(TabId.o_payment_detail));
                    record_QT.setBatchCode(batchCode);
                    record_QT.setPaymentId(oPayment.getId());
                    record_QT.setPaymentType(PamentIdType.ORDER_FKD.code);
                    record_QT.setOrderId(oPayment.getOrderId());
                    record_QT.setPayAmount(re);
                    record_QT.setRealPayAmount(re);
                    record_QT.setPlanPayTime(d.getTime());
                    record_QT.setPlanNum(Status.STATUS_0.status);
                    record_QT.setAgentId(oPayment.getAgentId());
                    record_QT.setPaymentStatus(PaymentStatus.JQ.code);
                    record_QT.setcUser(oPayment.getUserId());
                    record_QT.setcDate(d.getTime());
                    record_QT.setStatus(Status.STATUS_1.status);
                    record_QT.setVersion(Status.STATUS_1.status);
                    record_QT.setSrcId(oSupplement.getId());
                    orderAdj.setStagesAmount(BigDecimal.ZERO);
                    orderAdj.setNewPaymentId(batchCode);//新的还款计划批次号
                    if (OrderAdjRefundType.CDFQ_GZ.code.compareTo(orderAdj.getRefundType())==0) {
                        if (sendMsgToPlatm){
                            SettleAccounts settleAccounts = new SettleAccounts();
                            settleAccounts.setId(idService.genId(TabId.o_settle_accounts));
                            settleAccounts.setAgentId(orderAdj.getAgentId());//代理商id
                            settleAccounts.setsType(SettleType.ORDER_ADJUST.key);//挂账类型:订单调整
                            settleAccounts.setsTm(orderdate.getTime());
                            settleAccounts.setcTm(orderdate.getTime());
                            settleAccounts.setsStatus(Status.STATUS_0.status);
                            settleAccounts.setcUser(orderAdj.getAdjUserId());
                            settleAccounts.setsAmount(orderAdj.getSettleAmount());
                            settleAccounts.setSrcId(orderAdj.getId());//数据源id
                            settleAccounts.setStatus(Status.STATUS_1.status);
                            settleAccounts.setVersion(Status.STATUS_1.status);
                            if(1!=settleAccountsMapper.insertSelective(settleAccounts)){
                                return AgentResult.fail("保存挂账记录失败!");
                            };
                            record_QT.setSrcId(settleAccounts.getId());
                        }
                        record_QT.setPayType(PaymentType.GZ.code);
                        record_QT.setSrcType(PamentSrcType.ORDER_ADJ_SETTLE.code);
                    }else {
                        record_QT.setPayType(PaymentType.TK.code);
                        record_QT.setSrcType(PamentSrcType.ORDER_ADJ_REFUND.code);
                    }
                    if (1 != oPaymentDetailMapper.insert(record_QT)) {
                        logger.info("订单调整审批完成:明细生成失败:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}", order.getId(), oPayment.getId(), oPayment.getPayMethod(), record_QT.getId());
                        throw new MessageException("分期处理");
                    }
                    logger.info("订单调整审批完成:明细生成:订单ID:{},付款单ID:{},付款方式:{}，明细ID:{}", order.getId(), oPayment.getId(), oPayment.getPayMethod(), record_QT.getId());
                    break;
                }
                //抵扣金额必须等于待付金额
                logger.info("订单调整审批完成QT抵扣金额不等于订单待付金额{}:{},{},{}",
                        order.getId(),
                        oPayment.getPayMethod(),
                        oPayment.getOutstandingAmount(),
                        oPayment.getDeductionAmount());
                //添加抵扣后的余款为欠款
                OPaymentDetail record_QT = new OPaymentDetail();
                record_QT.setId(idService.genId(TabId.o_payment_detail));
                record_QT.setBatchCode(batchCode);
                record_QT.setPaymentId(oPayment.getId());
                record_QT.setPaymentType(PamentIdType.ORDER_FKD.code);
                record_QT.setOrderId(oPayment.getOrderId());
                record_QT.setPayType(PaymentType.SF.code);
                record_QT.setPayAmount(oPayment.getOutstandingAmount());
                record_QT.setRealPayAmount(BigDecimal.ZERO);
                record_QT.setPlanPayTime(d.getTime());
                record_QT.setPlanNum(Status.STATUS_0.status);
                record_QT.setAgentId(oPayment.getAgentId());
                record_QT.setPaymentStatus(PaymentStatus.DF.code);
                record_QT.setcUser(oPayment.getUserId());
                record_QT.setcDate(d.getTime());
                record_QT.setStatus(Status.STATUS_1.status);
                record_QT.setVersion(Status.STATUS_1.status);
                orderAdj.setStagesAmount(record_QT.getPayAmount());
                orderAdj.setNewPaymentId(batchCode);//新的还款计划批次号
                if (1 != oPaymentDetailMapper.insert(record_QT)) {
                    throw new MessageException("生成退款记录错误");
                }
                logger.info("订单调整审批完成处理明细完成首付数据成功{}:{},{}", order.getId(), oPayment.getId(), oPayment.getPayMethod());
                break;
        }

        //订单调整更新
        if(!approvalTaskSettle(orderAdj).isOK()){
            return AgentResult.fail("更新订单调整记录失败!");
        };
        //付款单数据更新
        if (1 != oPaymentMapper.updateByPrimaryKeySelective(oPayment)) {
            throw new MessageException("付款单数据更新异常！");
        }
        return AgentResult.okMap(resMap);
    }

    @Override
    public AgentResult adjustCheckAmo(OrderUpModelVo orderUpModelVo) throws Exception {

        OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(orderUpModelVo.getId());
        BigDecimal realRefundAmo = orderAdj.getRealRefundAmo();//实退金额
        String refundType = orderUpModelVo.getRefundType();
        if (refundType.equals(String.valueOf(RefundMehod.CDFQ_XXTK.code))){
            List<OrderAdjAccountVo> accounts = orderUpModelVo.getAccounts();
            BigDecimal refundAmo = BigDecimal.ZERO;
            for (OrderAdjAccountVo adjAccountVo:accounts){
                refundAmo = refundAmo.add(adjAccountVo.getRefundAmo());
            }
            if (realRefundAmo.compareTo(refundAmo) != 0){
                return AgentResult.fail("出款金额与应退金额不一致");
            }
        }else {
            String refundAmount = orderUpModelVo.getRefundAmount();//退款金额
            String settleAmount = orderUpModelVo.getSettleAmount();//挂账金额
            BigDecimal proRefundAmount = orderAdj.getProRefundAmount();//退货手续费
            BigDecimal offsetAmount = orderAdj.getOffsetAmount();//抵扣欠款金额
            if (realRefundAmo.compareTo(new BigDecimal(settleAmount)/*.add(offsetAmount).add(proRefundAmount)*/) != 0) {
                return AgentResult.fail("出款金额与应退金额不一致");
            }
        }
        return AgentResult.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public AgentResult saveAdjAccounts(OrderUpModelVo orderUpModelVo) throws Exception {
        List<OrderAdjAccountVo> accounts = orderUpModelVo.getAccounts();
        for (OrderAdjAccountVo adjAccountVo:accounts){
            OrderAdjAccountExample orderAdjAccountExample = new OrderAdjAccountExample();
            orderAdjAccountExample.or()
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andIdEqualTo(adjAccountVo.getId())
                    .andAdjIdEqualTo(orderUpModelVo.getId());
            List<OrderAdjAccount> orderAdjAccounts = orderAdjAccountMapper.selectByExample(orderAdjAccountExample);
            for (OrderAdjAccount account:orderAdjAccounts){
                account.setRefundCompany(adjAccountVo.getRefundCompany());
                account.setRefundAmo(adjAccountVo.getRefundAmo());
                account.setRefundStat(RefundStat.REFUNDING.key);
                if (1 !=orderAdjAccountMapper.updateByPrimaryKeySelective(account)){
                    return AgentResult.fail("账户信息不存在!");
                }
            }
        }
        return AgentResult.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    public AgentResult updateAdjAccountsByTk(OrderUpModelVo orderUpModelVo,String userId) throws Exception {
        List<OrderAdjAccountVo> accounts = orderUpModelVo.getAccounts();
        for (OrderAdjAccountVo adjAccountVo:accounts){

            if (null == adjAccountVo.getRefundTm() || null == adjAccountVo.getTkattachmentsFiles()){
                throw new MessageException("存在空的必输项!");
            }

            OrderAdjAccountExample orderAdjAccountExample = new OrderAdjAccountExample();
            orderAdjAccountExample.or()
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andIdEqualTo(adjAccountVo.getId())
                    .andAdjIdEqualTo(orderUpModelVo.getId());
            List<OrderAdjAccount> orderAdjAccounts = orderAdjAccountMapper.selectByExample(orderAdjAccountExample);
            for (OrderAdjAccount account:orderAdjAccounts){
                account.setRefundStat(RefundStat.REFUNED.key);
                account.setRefundTm(adjAccountVo.getRefundTm());
                account.setRefundUser(userId);
                if (1 !=orderAdjAccountMapper.updateByPrimaryKeySelective(account)){
                    return AgentResult.fail("账户信息不存在!");
                }
                for (String tkattachment : adjAccountVo.getTkattachmentsFiles()) {
                    AttachmentRel  record = new AttachmentRel();
                    record.setAttId(tkattachment);
                    record.setSrcId(account.getAdjId());
                    record.setcUser(userId);
                    record.setcTime(new Date());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.orderAdjust_refund.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    logger.info("添加订单调整退款附件关系,订单调整ID{},附件ID{}",account.getAdjId(),tkattachment);
                    if (1 != attachmentRelMapper.insertSelective(record)) {
                        logger.info("订单调整:{}", "添加订单调整附件关系失败");
                        throw new ProcessException("添加订单调整附件关系失败");
                    }
                }
            }


        }
        return AgentResult.ok();
    }

    @Override
    public AgentResult startOrderAdjustRefund(String id, String cuser) throws Exception {
        OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(id);
        if (StringUtils.isBlank(id)) {
            logger.info("订单调整退款提交审批，订单调整ID为空{}:{}", id, cuser);
            return AgentResult.fail("订单调整提交审批，订单调整ID为空!");
        }
        if (StringUtils.isBlank(cuser)) {
            logger.info("订单调整退款提交审批，操作用户为空{}:{}", id, cuser);
            return AgentResult.fail("订单调整审批中，操作用户为空！");
        }
        if (!orderAdj.getStatus().equals(Status.STATUS_1.status)) {
            logger.info("订单调整退款提交审批，订单调整信息已失效{}:{}", id, cuser);
            return AgentResult.fail("订单调整信息已失效！");
        }
        if (orderAdj.getReviewsStat().equals(AgStatus.Approving.name())) {
            logger.info("订单调整退款提交审批，禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("订单调整提交审批，禁止重复提交审批！");
        }
        if (orderAdj.getReviewsStat().equals(AgStatus.Approved.name())) {
            logger.info("订单调整退款提交审批，禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("订单调整提交审批，禁止重复提交审批！");
        }

        //流程中的部门参数
        Map startPar = agentEnterService.startPar(cuser);
        if (null == startPar) {
            logger.info("========用户{}{}启动部门参数为空", id, cuser);
            throw new MessageException("启动部门参数为空！");
        }
        startPar.put("settlementCardDs","1");
        startPar.put("settlementCardDg","1");
        //启动审批
        String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("orderAdjustRefund"), null, null, startPar);
        if (proce == null) {
            logger.info("订单调整提交审批，审批流启动失败{}:{}", id, cuser);
            throw new MessageException("审批流启动失败！");
        }

        Agent agent = agentMapper.selectByPrimaryKey(orderAdj.getAgentId());
        OOrder order = orderMapper.selectByPrimaryKey(orderAdj.getOrderId());
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
        //添加审批关系
        BusActRel record = new BusActRel();
        record.setBusId(orderAdj.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.orderAdjust.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(orderAdj.getAgentId());
        record.setAgentName(agent.getAgName());
        record.setNetInBusType("ACTIVITY_"+agentBusInfo.getBusPlatform());//数据权限
        record.setDataShiro(BusActRelBusType.orderAdjust.key);
        record.setAgDocPro(agentBusInfo.getAgDocPro());
        record.setAgDocDistrict(agentBusInfo.getAgDocDistrict());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("订单调整退款提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new MessageException("订单调整审批流启动失败：添加审批关系失败！");
        }
        return AgentResult.ok();
    }

    @Override
    public AgentResult approveFinishOrderAdjustRefund(String insid, String actname) throws Exception {
        logger.info("订单调整出纳审批完成:{},{}", insid, actname);
        //审批流关系
        BusActRel busActRel = busActRelService.findById(insid);
        OrderAdj orderAdj = null;
        if (actname.equals("finish_end")) { //审批完成
            logger.info("订单调整出纳审批完成,审批通过{}", busActRel.getBusId());
            busActRel.setActivStatus(AgStatus.Approved.name());
            if (1 != busActRelService.updateByPrimaryKey(busActRel)) {
                throw new MessageException("请重新提交！");
            }
            orderAdj = orderAdjMapper.selectByPrimaryKey(busActRel.getBusId());
            if(orderAdj==null ){
                throw new MessageException("没有此订单调整!"+ orderAdj.getId());
            }
            if (orderAdj.getReviewsStat().compareTo(AgStatus.Approved.status) != 0){
                throw new MessageException("该任务审批状态异常!");
            }
            orderAdj.setRefundStat(RefundStat.REFUNED.key);
            orderAdj.setRefundTm(new Date());
            // 订单调整更新
            if ( 1 != orderAdjMapper.updateByPrimaryKeySelective(orderAdj)) {
                throw new MessageException("订单调整数据更新异常！");
            }
        } else if(actname.equals("reject_end")) { //审批拒绝
            logger.info("订单调整出纳审批完审批拒绝{}", busActRel.getBusId());
            busActRel.setActivStatus(AgStatus.Refuse.name());
            if (1 != busActRelService.updateByPrimaryKey(busActRel)) {
                throw new MessageException("请重新提交！");
            }
            //订单调整数据
            orderAdj = orderAdjMapper.selectByPrimaryKey(busActRel.getBusId());
            if(orderAdj==null ){
                throw new MessageException("没有此订单调整!"+ orderAdj.getId());
            }
            if (orderAdj.getReviewsStat().compareTo(AgStatus.Approving.status) != 0){
                throw new MessageException("该任务审批状态异常!");
            }

            // 订单调整更新
            if ( 1 != orderAdjMapper.updateByPrimaryKeySelective(orderAdj)) {
                throw new MessageException("订单调整数据更新异常！");
            }
            logger.info("订单调整出纳审批完审批拒绝结束", busActRel.getBusId());
        }
        logger.info("订单调整出纳审批结束", busActRel.getBusId());
        return AgentResult.ok();
    }

    @Override
    public AgentResult approvalTaskOrderAdjustRefund(OrderUpModelVo orderUpModelVo, String userId) throws Exception {
        try {
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("rs", orderUpModelVo.getApprovalResult());
            reqMap.put("approvalOpinion", orderUpModelVo.getApprovalOpinion());
            reqMap.put("approvalPerson", userId);
            reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
            reqMap.put("taskId", orderUpModelVo.getTaskId());
            //下一个节点参数
            if (org.apache.commons.lang.StringUtils.isNotEmpty(orderUpModelVo.getOrderAdjAprDept()))
                reqMap.put("dept", orderUpModelVo.getOrderAdjAprDept());
            //传递部门信息
            Map startPar = agentEnterService.startPar(userId);
            if (null != startPar) {
                reqMap.put("party",startPar.get("party"));
            }
            OrderAdj orderAdj = orderAdjMapper.selectByPrimaryKey(orderUpModelVo.getId());
            List<OrderAdjAccountVo> OrderAdjAccountVoList = orderUpModelVo.getAccounts();
            for (OrderAdjAccountVo orderAdjAccountVo : OrderAdjAccountVoList) {// 日期和附件 以账户绑定
                if (null == orderAdjAccountVo.getRefundTm()){
                    throw new MessageException("请输入打款时间!");
                }
                // 保存打款日期
                String oaacId = orderAdjAccountVo.getId();
                OrderAdjAccount orderAdjAccount = orderAdjAccountMapper.selectByPrimaryKey(oaacId);
                orderAdjAccount.setRefundTm(orderAdjAccountVo.getRefundTm());
                if (1 !=orderAdjAccountMapper.updateByPrimaryKeySelective(orderAdjAccount)){
                    logger.info("订单调整保存打款时间失败:{}", orderAdjAccount.getId());
                    throw new ProcessException("订单调整保存打款时间失败");
                }
                // 保存打款附件
                List<String> attFiles = orderAdjAccountVo.getTkattachmentsFiles();
                AttachmentRel recordAtt = new AttachmentRel();
                if (attFiles.size()>0){
                    attFiles.forEach(attfile->{
                        recordAtt.setAttId(attfile);
                        recordAtt.setSrcId(oaacId);
                        recordAtt.setcUser(orderAdj.getAdjUserId());
                        recordAtt.setcTime(new Date());
                        recordAtt.setStatus(Status.STATUS_1.status);
                        recordAtt.setBusType(AttachmentRelType.orderAdjust_refund.name());
                        recordAtt.setId(idService.genId(TabId.a_attachment_rel));
                        logger.info("添加订单调整退款附件关系,订单调整ID{},附件ID{}",orderAdj.getId(),attfile);
                        if (1 != attachmentRelMapper.insertSelective(recordAtt)) {
                            logger.info("订单调整退款:{}", "添加订单调整退款附件关系失败");
                            throw new ProcessException("添加订单调整退款附件关系失败");
                        }
                    });
                }else {
                    throw new MessageException("请上传打款截图！");
                }
            }

            //完成任务
            Map resultMap = activityService.completeTask(orderUpModelVo.getTaskId(), reqMap);
            if (resultMap == null) {
                throw new MessageException("catch工作流处理任务异常！");
            }
            Boolean rs = (Boolean) resultMap.get("rs");
            String msg = String.valueOf(resultMap.get("msg"));
            if (!rs) {
                throw new MessageException("catch工作流处理任务异常！");
            }
            return AgentResult.ok(null);
        } catch (MessageException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }
}
