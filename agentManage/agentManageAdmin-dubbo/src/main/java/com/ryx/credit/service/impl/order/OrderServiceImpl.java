package com.ryx.credit.service.impl.order;

import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.agentUtil.StageUtil;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.OReceiptOrderVo;
import com.ryx.credit.pojo.admin.vo.OrderFormVo;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/7/13.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);


    @Autowired
    private OOrderMapper orderMapper;
    @Autowired
    private OSubOrderMapper oSubOrderMapper;
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
    public AgentResult buildOrder(OrderFormVo orderFormVo,String userId) {
        orderFormVo.setUserId(userId);
        //保存订单数据
        orderFormVo =  setOrderFormValue(orderFormVo,userId);
        //支付方式处理
        OPayment oPayment = orderFormVo.getoPayment();
        //分期处理
        AgentResult oPayment_res =   paymentPlan(oPayment);

        return AgentResult.ok();
    }

    /**
     * 分期处理
     * @param oPayment
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    @Override
    public AgentResult paymentPlan(OPayment oPayment) {
        Date d = Calendar.getInstance().getTime();
        BigDecimal allPay = oPayment.getPayAmount();//总金额
        BigDecimal down = oPayment.getDownPayment();//首付
        BigDecimal paymentCount = oPayment.getDownPaymentCount();//分期
        Date downPaymentDate = oPayment.getDownPaymentDate();//起始日期

        switch (oPayment.getPayMethod()){
            case "SF1"://首付+分润分期

                if(downPaymentDate.compareTo(new Date())<0){
                    return AgentResult.fail("分期日期错误");
                }
                List<Map> SF1_data =  StageUtil.stageOrder(allPay.subtract(down),paymentCount.intValue(),downPaymentDate,16);
                //明细处理
                for (Map datum : SF1_data) {
                    OPaymentDetail record =  new OPaymentDetail();
                    record.setId(idService.genId(TabId.o_payment_detail));
                    record.setPaymentId(oPayment.getId());
                    record.setOrderId(oPayment.getOrderId());
                    record.setPayMethod(oPayment.getPayMethod());
                    record.setPayAmount((BigDecimal) datum.get("item"));
                    record.setRealPayAmount(new BigDecimal(0));
                    record.setPlanPayTime((Date)datum.get("date"));
                    record.setPlanNum((BigDecimal) datum.get("count"));
                    record.setAgentId(oPayment.getAgentId());
                    record.setCollectCompany(oPayment.getCollectCompany());
                    record.setPaymentStatus(PaymentStatus.DF.code);
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

                if(downPaymentDate.compareTo(new Date())<0){
                    return AgentResult.fail("分期日期错误");
                }
                List<Map> SF2_data =  StageUtil.stageOrder(allPay.subtract(down),paymentCount.intValue(),downPaymentDate,16);
                //明细处理
                for (Map datum : SF2_data) {
                    OPaymentDetail record =  new OPaymentDetail();
                    record.setId(idService.genId(TabId.o_payment_detail));
                    record.setPaymentId(oPayment.getId());
                    record.setOrderId(oPayment.getOrderId());
                    record.setPayMethod(oPayment.getPayMethod());
                    record.setPayAmount((BigDecimal) datum.get("item"));
                    record.setRealPayAmount(new BigDecimal(0));
                    record.setPlanNum((BigDecimal) datum.get("count"));
                    record.setAgentId(oPayment.getAgentId());
                    record.setCollectCompany(oPayment.getCollectCompany());
                    record.setPaymentStatus(PaymentStatus.DF.code);
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
                if(downPaymentDate.compareTo(new Date())<0){
                    return AgentResult.fail("分期日期错误");
                }
                List<Map> FKFQ_data =  StageUtil.stageOrder(allPay,paymentCount.intValue(),downPaymentDate,16);
                //明细处理
                for (Map datum : FKFQ_data) {
                    OPaymentDetail record =  new OPaymentDetail();
                    record.setId(idService.genId(TabId.o_payment_detail));
                    record.setPaymentId(oPayment.getId());
                    record.setOrderId(oPayment.getOrderId());
                    record.setPayMethod(oPayment.getPayMethod());
                    record.setPayAmount((BigDecimal) datum.get("item"));
                    record.setRealPayAmount(new BigDecimal(0));
                    record.setPlanNum((BigDecimal) datum.get("count"));
                    record.setAgentId(oPayment.getAgentId());
                    record.setCollectCompany(oPayment.getCollectCompany());
                    record.setPaymentStatus(PaymentStatus.DF.code);
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
                if(downPaymentDate.compareTo(new Date())<0){
                    return AgentResult.fail("分期日期错误");
                }
                List<Map> FRFQ_data =  StageUtil.stageOrder(allPay,paymentCount.intValue(),downPaymentDate,16);
                //明细处理
                for (Map datum : FRFQ_data) {
                    OPaymentDetail record =  new OPaymentDetail();
                    record.setId(idService.genId(TabId.o_payment_detail));
                    record.setPaymentId(oPayment.getId());
                    record.setOrderId(oPayment.getOrderId());
                    record.setPayMethod(oPayment.getPayMethod());
                    record.setPayAmount((BigDecimal) datum.get("item"));
                    record.setRealPayAmount(new BigDecimal(0));
                    record.setPlanNum((BigDecimal) datum.get("count"));
                    record.setAgentId(oPayment.getAgentId());
                    record.setCollectCompany(oPayment.getCollectCompany());
                    record.setPaymentStatus(PaymentStatus.DF.code);
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

    private OrderFormVo setOrderFormValue(OrderFormVo orderFormVo, String userId){
        //订单基础数据
        Date d = Calendar.getInstance().getTime();
        orderFormVo.setId(idService.genId(TabId.o_order));
        orderFormVo.setoNum(orderFormVo.getId());
        orderFormVo.setoApytime(orderFormVo.getcTime());
        orderFormVo.setUserId(userId);
        //orderFormVo.setIncentiveAmo(new BigDecimal(0));//优惠金额
        orderFormVo.setPayAmo(orderFormVo.getoAmo());
        orderFormVo.setReviewStatus(AgStatus.Create.status);
        orderFormVo.setOrderStatus(OrderStatus.CREATE.status);
        orderFormVo.setClearStatus(Status.STATUS_0.status);
        orderFormVo.setStatus(Status.STATUS_1.status);
        orderFormVo.setcTime(d);
        orderFormVo.setuUser(userId);
        orderFormVo.setuTime(d);
        orderFormVo.setVersion(Status.STATUS_0.status);
        if(1!=orderMapper.insertSelective(orderFormVo)){
            throw new ProcessException("订单添加失败");
        }

        //支付方式
        OPayment oPayment = orderFormVo.getoPayment();
        oPayment.setId(idService.genId(TabId.o_payment));
        oPayment.setUserId(userId);
        oPayment.setOrderId(orderFormVo.getId());
        oPayment.setAgentId(orderFormVo.getAgentId());
        oPayment.setcTime(d);
        //TODO 需要手动计算付款金额
       //        oPayment.setPayAmount();//应付金额
        oPayment.setOutstandingAmount(oPayment.getPayAmount());//代付基恩
        oPayment.setRealAmount(Status.STATUS_0.status);//已付金额
        oPayment.setPayStatus(PayStatus.NON_PAYMENT.code);
        oPayment.setStatus(Status.STATUS_1.status);
        if(1!=oPaymentMapper.insertSelective(oPayment)){
            throw new ProcessException("oPayment添加失败");
        }
        //子订单接口
        List<OSubOrder> OSubOrders = orderFormVo.getoSubOrder();
        for (OSubOrder oSubOrder : OSubOrders) {
            oSubOrder.setId(idService.genId(TabId.o_sub_order));
            OProduct product = oProductMapper.selectByPrimaryKey(oSubOrder.getProId());
            oSubOrder.setOrderId(orderFormVo.getId());
            oSubOrder.setProCode(product.getProCode());
            oSubOrder.setProName(product.getProName());
            oSubOrder.setProType(product.getProType());
            oSubOrder.setProPrice(product.getProPrice());
            oSubOrder.setIsDeposit(product.getIsDeposit());
            oSubOrder.setDeposit(product.getDeposit());
            oSubOrder.setModel(product.getProModel());
            oSubOrder.setcTime(d);
            oSubOrder.setuUser(userId);
            oSubOrder.setcUser(userId);
            oSubOrder.setuTime(d);
            oSubOrder.setStatus(Status.STATUS_1.status);
            oSubOrder.setVersion(Status.STATUS_0.status);
            if(1!=oSubOrderMapper.insertSelective(oSubOrder)){
                throw new ProcessException("oPayment添加失败");
            }
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
            for (OReceiptPro pro : pros) {
                pro.setId(idService.genId(TabId.o_receipt_pro));
                pro.setcTime(d);
                pro.setOrderid(orderFormVo.getId());
                pro.setReceiptId(oReceiptOrderVo.getId());
                String proid = pro.getProId();
                OProduct product = oProductMapper.selectByPrimaryKey(proid);
                pro.setProCode(product.getProCode());
                pro.setProName(product.getProName());
                pro.setcUser(userId);
                pro.setuTime(d);
                pro.setuUser(userId);
                pro.setStatus(Status.STATUS_1.status);
                pro.setVersion(Status.STATUS_0.status);
                if(1!=oReceiptProMapper.insertSelective(pro)){
                    throw new ProcessException("oPayment添加失败");
                }
                b =  b.add(pro.getProNum());
            }
            oReceiptOrderVo.setProNum(b);
            if(1!=oReceiptOrderMapper.insertSelective(oReceiptOrderVo)){
                throw new ProcessException("oPayment添加失败");
            }
        }
        return orderFormVo;
    }
}
