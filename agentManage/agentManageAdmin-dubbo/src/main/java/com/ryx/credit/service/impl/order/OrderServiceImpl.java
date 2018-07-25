package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.agentUtil.StageUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.AttachmentMapper;
import com.ryx.credit.dao.agent.AttachmentRelMapper;
import com.ryx.credit.dao.agent.BusActRelMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OReceiptOrderVo;
import com.ryx.credit.pojo.admin.vo.OrderFormVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.OrderService;
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
 * Created by RYX on 2018/7/13.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Autowired
    private OOrderMapper orderMapper;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private OSubOrderMapper oSubOrderMapper;
    @Autowired
    private OSubOrderActivityMapper oSubOrderActivityMapper;
    @Autowired
    private OReceiptProMapper  oReceiptProMapper;
    @Autowired
    private OPaymentMapper oPaymentMapper;
    @Autowired
    private OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private OProductMapper oProductMapper;
    @Autowired
    private OAddressMapper oAddressMapper;
    @Autowired
    private OReceiptOrderMapper  oReceiptOrderMapper;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private DictOptionsService dictOptionsService;


    @Override
    public PageInfo orderList(OOrder product, Page page) {

        OOrderExample example = new OOrderExample();
        OOrderExample.Criteria criteria = example.createCriteria();

        List<OOrder> oOrders = orderMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(oOrders);
        pageInfo.setTotal(orderMapper.countByExample(example));
        return pageInfo;
    }

    /**
     * 订单构建
     * @param orderFormVo
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult buildOrder(OrderFormVo orderFormVo,String userId) throws Exception{
        if(StringUtils.isBlank(orderFormVo.getAgentId())){
            return AgentResult.fail("请选择代理商");
        }
        if(StringUtils.isBlank(orderFormVo.getOrderPlatform())){
            return AgentResult.fail("请选择平台");
        }
        orderFormVo.setUserId(userId);
        //保存订单数据
        orderFormVo =  setOrderFormValue(orderFormVo,userId);
        //支付方式处理
        OPayment oPayment = orderFormVo.getoPayment();
        //分期处理
        AgentResult oPayment_res = paymentPlan(oPayment);
        return AgentResult.ok();
    }

    /**
     * 分期处理
     * @param oPayment
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult paymentPlan(OPayment oPayment) throws Exception{
        Date d = Calendar.getInstance().getTime();
        BigDecimal allPay = oPayment.getPayAmount();//总金额
        BigDecimal down = oPayment.getDownPayment();//首付
        BigDecimal paymentCount = oPayment.getDownPaymentCount();//分期
        Date downPaymentDate = oPayment.getDownPaymentDate();//起始日期
        switch (oPayment.getPayMethod()){
            case "SF1"://首付+分润分期
                if(down==null){
                    return AgentResult.fail("分期数据错误");
                }
                if(paymentCount==null){
                    return AgentResult.fail("分期数据错误");
                }
                if(downPaymentDate==null){
                    return AgentResult.fail("分期数据错误");
                }
                if(downPaymentDate.compareTo(new Date())<0){
                    return AgentResult.fail("分期日期错误");
                }
                List<Map> SF1_data =  StageUtil.stageOrder(allPay.subtract(down),paymentCount.intValue(),downPaymentDate,16);
                //明细处理
                for (Map datum : SF1_data) {
                    OPaymentDetail record =  new OPaymentDetail();
                    record.setId(idService.genId(TabId.o_payment_detail));
                    record.setBatchCode(d.getTime()+"");
                    record.setPaymentId(oPayment.getId());
                    record.setPaymentType(PamentIdType.ORDER_FKD.code);
                    record.setOrderId(oPayment.getOrderId());
                    record.setPayType(PaymentType.FRFQ.code);
                    record.setPayAmount((BigDecimal) datum.get("item"));
                    record.setRealPayAmount(new BigDecimal(0));
                    record.setPlanPayTime((Date)datum.get("date"));
                    record.setPlanNum((BigDecimal) datum.get("count"));
                    record.setAgentId(oPayment.getAgentId());
                    record.setPaymentStatus(PaymentStatus.DS.code);
                    record.setcUser(oPayment.getUserId());
                    record.setcDate(d);
                    record.setStatus(Status.STATUS_1.status);
                    record.setVersion(Status.STATUS_1.status);
                    if(1!=oPaymentDetailMapper.insert(record)){
                       throw new ProcessException("分期处理");
                    }
                }
                break;
            case "SF2": //首付+打款分期
                if(down==null){
                    return AgentResult.fail("分期数据错误");
                }
                if(paymentCount==null){
                    return AgentResult.fail("分期数据错误");
                }
                if(downPaymentDate==null){
                    return AgentResult.fail("分期数据错误");
                }
                if(downPaymentDate.compareTo(new Date())<0){
                    return AgentResult.fail("分期日期错误");
                }
                List<Map> SF2_data =  StageUtil.stageOrder(allPay.subtract(down),paymentCount.intValue(),downPaymentDate,16);
                //明细处理
                for (Map datum : SF2_data) {
                    OPaymentDetail record =  new OPaymentDetail();
                    record.setId(idService.genId(TabId.o_payment_detail));
                    record.setBatchCode(d.getTime()+"");
                    record.setPaymentId(oPayment.getId());
                    record.setPaymentType(PamentIdType.ORDER_FKD.code);
                    record.setOrderId(oPayment.getOrderId());
                    record.setPayType(PaymentType.DKFQ.code);
                    record.setPayAmount((BigDecimal) datum.get("item"));
                    record.setRealPayAmount(new BigDecimal(0));
                    record.setPlanNum((BigDecimal) datum.get("count"));
                    record.setAgentId(oPayment.getAgentId());
                    record.setPaymentStatus(PaymentStatus.DS.code);
                    record.setcUser(oPayment.getUserId());
                    record.setcDate(d);
                    record.setStatus(Status.STATUS_1.status);
                    record.setVersion(Status.STATUS_1.status);
                    if(1!=oPaymentDetailMapper.insert(record)){
                        throw new ProcessException("分期处理");
                    }
                }
                break;
            case "FKFQ"://付款分期
                if(down==null){
                    return AgentResult.fail("分期数据错误");
                }
                if(paymentCount==null){
                    return AgentResult.fail("分期数据错误");
                }
                if(downPaymentDate==null){
                    return AgentResult.fail("分期数据错误");
                }
                if(downPaymentDate.compareTo(new Date())<0){
                    return AgentResult.fail("分期日期错误");
                }
                List<Map> FKFQ_data =  StageUtil.stageOrder(allPay,paymentCount.intValue(),downPaymentDate,16);
                //明细处理
                for (Map datum : FKFQ_data) {
                    OPaymentDetail record =  new OPaymentDetail();
                    record.setId(idService.genId(TabId.o_payment_detail));
                    record.setBatchCode(d.getTime()+"");
                    record.setPaymentId(oPayment.getId());
                    record.setPaymentType(PamentIdType.ORDER_FKD.code);
                    record.setOrderId(oPayment.getOrderId());
                    record.setPayType(PaymentType.DKFQ.code);
                    record.setPayAmount((BigDecimal) datum.get("item"));
                    record.setRealPayAmount(new BigDecimal(0));
                    record.setPlanNum((BigDecimal) datum.get("count"));
                    record.setAgentId(oPayment.getAgentId());
                    record.setPaymentStatus(PaymentStatus.DS.code);
                    record.setcUser(oPayment.getUserId());
                    record.setcDate(d);
                    record.setStatus(Status.STATUS_1.status);
                    record.setVersion(Status.STATUS_1.status);
                    if(1!=oPaymentDetailMapper.insert(record)){
                        throw new ProcessException("分期处理");
                    }
                }
                break;
            case "FRFQ"://分润分期
                if(down==null){
                    return AgentResult.fail("分期数据错误");
                }
                if(paymentCount==null){
                    return AgentResult.fail("分期数据错误");
                }
                if(downPaymentDate==null){
                    return AgentResult.fail("分期数据错误");
                }
                if(downPaymentDate.compareTo(new Date())<0){
                    return AgentResult.fail("分期日期错误");
                }
                List<Map> FRFQ_data =  StageUtil.stageOrder(allPay,paymentCount.intValue(),downPaymentDate,16);
                //明细处理
                for (Map datum : FRFQ_data) {
                    OPaymentDetail record =  new OPaymentDetail();
                    record.setId(idService.genId(TabId.o_payment_detail));
                    record.setBatchCode(d.getTime()+"");
                    record.setPaymentId(oPayment.getId());
                    record.setPaymentType(PamentIdType.ORDER_FKD.code);
                    record.setOrderId(oPayment.getOrderId());
                    record.setPayType(PaymentType.FRFQ.code);
                    record.setPayAmount((BigDecimal) datum.get("item"));
                    record.setRealPayAmount(new BigDecimal(0));
                    record.setPlanNum((BigDecimal) datum.get("count"));
                    record.setAgentId(oPayment.getAgentId());
                    record.setPaymentStatus(PaymentStatus.DS.code);
                    record.setcUser(oPayment.getUserId());
                    record.setcDate(d);
                    record.setStatus(Status.STATUS_1.status);
                    record.setVersion(Status.STATUS_1.status);
                    if(1!=oPaymentDetailMapper.insert(record)){
                        throw new ProcessException("分期处理");
                    }
                }
                break;
            case "XXDK"://线下打款
                break;
            case "QT"://其他
                break;
        }
        return AgentResult.ok();

    }

    /**
     * 订单form表单填充并入库
     * @param orderFormVo
     * @param userId
     * @return
     */
    private OrderFormVo setOrderFormValue(OrderFormVo orderFormVo, String userId){

        //订单基础数据
        Date d = Calendar.getInstance().getTime();
        orderFormVo.setId(idService.genId(TabId.o_order));
        orderFormVo.setoNum(orderFormVo.getId());
        orderFormVo.setoApytime(orderFormVo.getcTime());
        orderFormVo.setUserId(userId);
        orderFormVo.setPayAmo(orderFormVo.getoAmo());
        orderFormVo.setReviewStatus(AgStatus.Create.status);
        orderFormVo.setOrderStatus(OrderStatus.CREATE.status);
        orderFormVo.setClearStatus(Status.STATUS_0.status);
        orderFormVo.setStatus(Status.STATUS_1.status);
        orderFormVo.setcTime(d);
        orderFormVo.setuUser(userId);
        orderFormVo.setuTime(d);
        orderFormVo.setVersion(Status.STATUS_0.status);

        //支付方式
        OPayment oPayment = orderFormVo.getoPayment();
        oPayment.setId(idService.genId(TabId.o_payment));
        oPayment.setUserId(userId);
        oPayment.setOrderId(orderFormVo.getId());
        oPayment.setAgentId(orderFormVo.getAgentId());
        oPayment.setcTime(d);
        oPayment.setRealAmount(Status.STATUS_0.status);//已付金额
        oPayment.setPayStatus(PayStatus.NON_PAYMENT.code);
        oPayment.setStatus(Status.STATUS_1.status);

        //订单总金额
        BigDecimal forPayAmount =  new BigDecimal(0);
        //订单应付金额
        BigDecimal forRealPayAmount =  new BigDecimal(0);

        //子订单接口 计算整个订单数据
        List<OSubOrder> OSubOrders = orderFormVo.getoSubOrder();
        for (OSubOrder oSubOrder : OSubOrders) {
            oSubOrder.setId(idService.genId(TabId.o_sub_order));
            OProduct product = oProductMapper.selectByPrimaryKey(oSubOrder.getProId());
            if(oSubOrder.getProPrice()==null || oSubOrder.getProPrice().compareTo(product.getProPrice())!=0){
                logger.info("下订单:{}","商品价格数据错误");
                throw new ProcessException("商品价格数据错误");
            }
            if(oSubOrder.getProRelPrice()==null){
                oSubOrder.setProRelPrice(oSubOrder.getProPrice());
            }
            if(oSubOrder.getProNum()==null || oSubOrder.getProNum().compareTo(BigDecimal.ZERO)<=0){
                logger.info("下订单:{}","商品数量错误");
                throw new ProcessException("商品数量错误");
            }
            oSubOrder.setOrderId(orderFormVo.getId());
            oSubOrder.setProCode(product.getProCode());
            oSubOrder.setProName(product.getProName());
            oSubOrder.setProType(product.getProType());
            oSubOrder.setProPrice(product.getProPrice());
            oSubOrder.setSendNum(Status.STATUS_0.status);
            oSubOrder.setIsDeposit(product.getIsDeposit());
            oSubOrder.setDeposit(product.getDeposit());
            oSubOrder.setModel(product.getProModel());
            oSubOrder.setcTime(d);
            oSubOrder.setuUser(userId);
            oSubOrder.setcUser(userId);
            oSubOrder.setuTime(d);
            oSubOrder.setStatus(Status.STATUS_1.status);
            oSubOrder.setVersion(Status.STATUS_0.status);
            //插入订单商品信息
            if(1!=oSubOrderMapper.insertSelective(oSubOrder)){
                logger.info("下订单:{}","oSubOrder添加失败");
                throw new ProcessException("oPayment添加失败");
            }
            forPayAmount = forPayAmount.add(oSubOrder.getProPrice().multiply(oSubOrder.getProNum()));
            forRealPayAmount = forRealPayAmount.add(oSubOrder.getProRelPrice().multiply(oSubOrder.getProNum()));
        }
        //收货地址
        List<OReceiptOrderVo> OReceiptOrderVos = orderFormVo.getoReceiptOrderList();
        for (OReceiptOrderVo oReceiptOrderVo : OReceiptOrderVos) {
            oReceiptOrderVo.setId(idService.genId(TabId.o_receipt_order));
            oReceiptOrderVo.setOrderId(orderFormVo.getId());
            oReceiptOrderVo.setReceiptNum(oReceiptOrderVo.getId());
            OAddress address = oAddressMapper.selectByPrimaryKey(oReceiptOrderVo.getAddressId());
            oReceiptOrderVo.setAddrRealname(address.getAddrRealname());
            oReceiptOrderVo.setAddrMobile(address.getAddrMobile());
            oReceiptOrderVo.setAddrProvince(address.getAddrProvince());
            oReceiptOrderVo.setAddrCity(address.getAddrCity());
            oReceiptOrderVo.setAddrDistrict(address.getAddrDistrict());
            oReceiptOrderVo.setAddrDetail(address.getAddrDetail());
            oReceiptOrderVo.setRemark(address.getRemark());
            oReceiptOrderVo.setZipCode(address.getZipCode());
            oReceiptOrderVo.setcTime(d);
            oReceiptOrderVo.setuTime(d);
            oReceiptOrderVo.setReceiptStatus(OReceiptStatus.TEMPORARY_STORAGE.code);
            oReceiptOrderVo.setuUser(userId);
            oReceiptOrderVo.setcUser(userId);
            oReceiptOrderVo.setStatus(Status.STATUS_1.status);
            oReceiptOrderVo.setVersion(Status.STATUS_0.status);
            oReceiptOrderVo.setAgentId(orderFormVo.getAgentId());
            BigDecimal b = new BigDecimal(0);
            List<OReceiptPro>  pros =  oReceiptOrderVo.getoReceiptPros();
            if(pros.size()==0){
                logger.info("下订单:{}","请为收货地址["+address.getRemark()+"]配置上商品明细");
                throw new ProcessException("请为收货地址["+address.getRemark()+"]配置上商品明细");
            }
            for (OReceiptPro pro : pros) {
                pro.setId(idService.genId(TabId.o_receipt_pro));
                pro.setcTime(d);
                pro.setOrderid(orderFormVo.getId());
                pro.setReceiptId(oReceiptOrderVo.getId());
                String proid = pro.getProId();
                OProduct product = oProductMapper.selectByPrimaryKey(proid);
                pro.setProCode(product.getProCode());
                pro.setProName(product.getProName());
                pro.setSendNum(new BigDecimal(0));
                pro.setcUser(userId);
                pro.setuTime(d);
                pro.setuUser(userId);
                pro.setStatus(Status.STATUS_1.status);
                pro.setVersion(Status.STATUS_0.status);
                pro.setReceiptProStatus(OReceiptStatus.TEMPORARY_STORAGE.code.toString());
                //插入收货地址明细
                if(1!=oReceiptProMapper.insertSelective(pro)){
                    logger.info("下订单:{}", "oReceiptPro添加失败");
                    throw new ProcessException("oPayment添加失败");
                }
                b =  b.add(pro.getProNum());
            }
            oReceiptOrderVo.setProNum(b);
            //插入收货地址
            if(1!=oReceiptOrderMapper.insertSelective(oReceiptOrderVo)){
                logger.info("下订单:{}", "oReceiptOrderVo添加失败");
                throw new ProcessException("oReceiptOrderVo添加失败");
            }
        }
        List<Attachment> attr =  orderFormVo.getAttachments();
        for (Attachment attachment : attr) {
                if (org.apache.commons.lang.StringUtils.isEmpty(attachment.getId())) continue;
                AttachmentRel record = new AttachmentRel();
                record.setAttId(attachment.getId());
                record.setSrcId(orderFormVo.getId());
                record.setcUser(userId);
                record.setcTime(d);
                record.setStatus(Status.STATUS_1.status);
                record.setBusType(AttachmentRelType.Order.name());
                record.setId(idService.genId(TabId.a_attachment_rel));
                if (1 != attachmentRelMapper.insertSelective(record)) {
                    logger.info("下订单:{}", "附件添加失败");
                    throw new ProcessException("下订单附件添加失败");
                }
        }
        
        //需要手动计算付款金额
        oPayment.setPayAmount(forRealPayAmount);//应付金额
        oPayment.setOutstandingAmount(forRealPayAmount);//待付金额
        oPayment.setRealAmount(new BigDecimal(0));//已付金额
        //需要手动计算付款金额
        orderFormVo.setIncentiveAmo(forPayAmount.subtract(forRealPayAmount));//订单优惠金额
        orderFormVo.setoAmo(forRealPayAmount);//订单总金额
        orderFormVo.setPayAmo(forRealPayAmount);//订单应付金额
        //插入订单
        if(1!=orderMapper.insertSelective(orderFormVo)){
            throw new ProcessException("订单添加失败");
        }
        //插入付款单
        if(1!=oPaymentMapper.insertSelective(oPayment)){
            throw new ProcessException("oPayment添加失败");
        }
        return orderFormVo;
    }


    /**
     * 加载订单数据
     * @param id
     * @return
     */
    @Override
    public AgentResult loadAgentInfo(String id)throws Exception {

         //订单
         OOrder order = orderMapper.selectByPrimaryKey(id);
         FastMap f = FastMap.fastMap("order",order);

         Agent agent = agentMapper.selectByPrimaryKey(order.getAgentId());
         f.putKeyV("agent",agent);

        //商品信息
        OSubOrderExample osubOrderExample = new OSubOrderExample();
        osubOrderExample.or().andOrderIdEqualTo(order.getId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(osubOrderExample);
        f.putKeyV("oSubOrders",oSubOrders);

        //商品活动信息
        if(oSubOrders.size()>0) {
            List<String> ids = new ArrayList<>();
            for (OSubOrder oSubOrder : oSubOrders) {
                ids.add(oSubOrder.getId());
            }
            if(ids.size()>0) {
                OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
                oSubOrderActivityExample.or().andSubOrderIdIn(ids);
                List<OSubOrderActivity> sSubOrderActivitys = oSubOrderActivityMapper.selectByExample(oSubOrderActivityExample);
                f.putKeyV("sSubOrderActivitys",sSubOrderActivitys);
            }
        }
        //配货信息
        OReceiptOrderExample oReceiptOrderExample = new OReceiptOrderExample();
        oReceiptOrderExample.or().andStatusEqualTo(Status.STATUS_1.status).andOrderIdEqualTo(order.getId());
        List<OReceiptOrder> oReceiptOrderList = oReceiptOrderMapper.selectByExample(oReceiptOrderExample);
        f.putKeyV("oReceiptOrders",oReceiptOrderList);

        //配货商品
        OReceiptProExample oReceiptProExample = new OReceiptProExample();
        oReceiptProExample.or().andOrderidEqualTo(order.getId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OReceiptPro>  oReceiptPros = oReceiptProMapper.selectByExample(oReceiptProExample);
        f.putKeyV("oReceiptPros",oReceiptPros);

        //支付信息
        OPaymentExample oPaymentExample = new OPaymentExample();
        oPaymentExample.or().andStatusEqualTo(Status.STATUS_1.status).andOrderIdEqualTo(order.getId());
        List<OPayment>  oPaymentList = oPaymentMapper.selectByExample(oPaymentExample);
        if(oPaymentList.size()!=1){
          return AgentResult.fail("支付信息错误");
        }
        OPayment oPayment = oPaymentList.get(0);
        f.putKeyV("oPayment",oPayment);

        OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
        oPaymentDetailExample.or().andStatusEqualTo(Status.STATUS_1.status).andPaymentIdEqualTo(oPayment.getId()).andOrderIdEqualTo(order.getId());
        List<OPaymentDetail> oPaymentDetails = oPaymentDetailMapper.selectByExample(oPaymentDetailExample);
        f.putKeyV("oPaymentDetails",oPaymentDetails);

        List<Attachment> attr = attachmentMapper.accessoryQuery(order.getId(),AttachmentRelType.Order.name());
        f.putKeyV("attrs",attr);
        return  AgentResult.ok(f);
    }

    /**
     * 启动订单审批流程
     * @param id
     * @param cuser
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public AgentResult startOrderActiviy(String id,String cuser) throws Exception {
        if (StringUtils.isBlank(id)) {
            logger.info("订单提交审批,订单ID为空{}:{}", id, cuser);
            return AgentResult.fail("订单提交审批，订单ID为空");
        }
        if (StringUtils.isBlank(cuser)) {
            logger.info("订单提交审批,操作用户为空{}:{}", id, cuser);
            return AgentResult.fail("代理商审批中，操作用户为空");
        }

        //检查是否有审批中的代理商新
        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(cuser).andActivStatusEqualTo(AgStatus.Approving.name()).andStatusEqualTo(Status.STATUS_1.status);
        if (busActRelMapper.selectByExample(example).size() > 0) {
            logger.info("订单提交审批,禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("订单提交审批，禁止重复提交审批");
        }
        OOrder order = orderMapper.selectByPrimaryKey(id);
        if (order.getReviewStatus().equals(AgStatus.Approving.name())) {
            logger.info("订单提交审批,禁止重复提交审批{}:{}", id, cuser);
            return AgentResult.fail("订单提交审批，禁止重复提交审批");
        }


        if (!order.getStatus().equals(Status.STATUS_1.status)) {
            logger.info("订单提交审批,代理商信息已失效{}:{}", id, cuser);
            return AgentResult.fail("订单信息已失效");
        }

        //更新代理商审批中
        order.setReviewStatus(AgStatus.Approving.status);
        order.setoApytime(new Date());
        if (1 != orderMapper.updateByPrimaryKeySelective(order)) {
            logger.info("订单提交审批，更新订单基本信息失败{}:{}", id, cuser);
            throw new ProcessException("订单提交审批，更新订单基本信息失败");
        }
        Map startPar = agentEnterService.startPar(cuser);
        if (null == startPar) {
            logger.info("========用户{}{}启动部门参数为空", id, cuser);
            throw new ProcessException("启动部门参数为空!");
        }

        //不同的业务类型找到不同的启动流程
        List<Dict> actlist = dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ACT_ORDER.name());
        String workId = null;
        for (Dict dict : actlist) {
                workId = dict.getdItemvalue();
        }
        //启动审批
        String proce = activityService.createDeloyFlow(null,workId, null, null, startPar);
        if (proce == null) {
            logger.info("订单提交审批，审批流启动失败{}:{}", id, cuser);
            throw new ProcessException("审批流启动失败!");
        }

        //代理商业务视频关系
        BusActRel record = new BusActRel();
        record.setBusId(order.getId());
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(cuser);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.ORDER.name());
        record.setActivStatus(AgStatus.Approving.name());
        if (1 != busActRelMapper.insertSelective(record)) {
            logger.info("订单提交审批，启动审批异常，添加审批关系失败{}:{}", id, proce);
            throw new ProcessException("审批流启动失败:添加审批关系失败");
        }
        return AgentResult.ok();
    }

    /**
     * 审批订单任务
     * @param agentVo
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws Exception {
        try {
            //处理审批数据
            logger.info("订单提交审批，完成任务{}:{}：{}", agentVo.getTaskId(), userId, JSONObject.toJSONString(agentVo));
            //完成任务
            AgentResult result = new AgentResult(500, "系统异常", "");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("rs", agentVo.getApprovalResult());
            reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
            reqMap.put("approvalPerson", userId);
            reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
            reqMap.put("taskId", agentVo.getTaskId());
            //下一个节点参数
            if(org.apache.commons.lang.StringUtils.isNotEmpty(agentVo.getOrderAprDept()))
            reqMap.put("dept", agentVo.getOrderAprDept());

            //传递部门信息
            Map startPar = agentEnterService.startPar(userId);
            if (null != startPar) {
                reqMap.put("party", startPar.get("party"));
            }

            //完成任务
            Map resultMap = activityService.completeTask(agentVo.getTaskId(), reqMap);
            Boolean rs = (Boolean) resultMap.get("rs");
            String msg = String.valueOf(resultMap.get("msg"));
            if (resultMap == null) {
                return result;
            }

            if (!rs) {
                result.setMsg(msg);
                return result;
            }

            return AgentResult.ok(resultMap);
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException("catch工作流处理任务异常!");
        }
    }
}
