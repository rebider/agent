package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.agentUtil.StageUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.CuserAgentMapper;
import com.ryx.credit.dao.agent.AgentBusInfoMapper;
import com.ryx.credit.dao.agent.AgentMapper;
import com.ryx.credit.dao.agent.ImportAgentMapper;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.pojo.admin.CuserAgent;
import com.ryx.credit.pojo.admin.CuserAgentExample;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.OrderFormVo;
import com.ryx.credit.pojo.admin.vo.OrderImportBaseInfo;
import com.ryx.credit.pojo.admin.vo.OrderImportGoodsInfo;
import com.ryx.credit.pojo.admin.vo.OrderLogicInfo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.ApaycompService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.impl.agent.AimportServiceImpl;
import com.ryx.credit.service.order.IPaymentDetailService;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.credit.service.order.OrderImportService;
import com.ryx.credit.service.order.PlannerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.text.Bidi;
import java.util.*;

/**
 * 作者：cx
 * 时间：2019/1/29
 * 描述：订单导入解析
 */
@Service("orderImportService")
public class OrderImportServiceImpl implements OrderImportService {

    private Logger logger = LoggerFactory.getLogger(OrderImportServiceImpl.class);

    @Value("#{config['order.import.address']}")
    private String addressCode;
    @Autowired
    private ImportAgentMapper importAgentMapper;
    @Autowired
    private IdService idService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private OOrderMapper oOrderMapper;
    @Autowired
    private OPaymentMapper oPaymentMapper;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderImportService orderImportService;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private CuserAgentMapper cuserAgentMapper;
    @Autowired
    private ApaycompService apaycompService;
    @Autowired
    OPaymentDetailMapper oPaymentDetailMapper;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private IPaymentDetailService iPaymentDetailService;
    @Autowired
    private OSubOrderMapper oSubOrderMapper;
    @Autowired
    private OActivityMapper oActivityMapper;
    @Autowired
    private OProductMapper oProductMapper;
    @Autowired
    private OAddressMapper oAddressMapper;
    @Autowired
    private OReceiptOrderMapper oReceiptOrderMapper;
    @Autowired
    private OReceiptProMapper oReceiptProMapper;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private OLogisticsMapper oLogisticsMapper;
    @Autowired
    private OLogisticsService oLogisticsService;

    private OLogisticsDetailMapper  oLogisticsDetailMapper;

    @Autowired
    private OSubOrderActivityMapper oSubOrderActivityMapper;


    List<Dict> CAPITAL_TYPE = new ArrayList<>();
    List<PayComp> payComps = new ArrayList<>();
    @PostConstruct
    public void init(){
        CAPITAL_TYPE = dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.CAPITAL_TYPE.name());
        payComps = apaycompService.recCompList();
    }

    public int insertAgentImportData(ImportAgent importAgent) {
        importAgent.setStatus(Status.STATUS_1.status);
        importAgent.setDealstatus(Status.STATUS_0.status);//未处理
        importAgent.setcTime(Calendar.getInstance().getTime());
        importAgent.setId(idService.genId(TabId.a_import_agent));
        importAgent.setVersion(Status.STATUS_0.status);
        return importAgentMapper.insertSelective(importAgent);
    }
    @Override
    public List<String> addOrderInfoList(List<List<Object>> data, String dataType, String user, String batch) throws Exception {
        List<String> ids = new ArrayList<>();
        for (List<Object> datum : data) {
                if (datum == null || datum.size() == 0 || StringUtils.isBlank(datum.get(0) + "")) break;
                ImportAgent importAgent = new ImportAgent();
                importAgent.setBatchcode(batch);
                importAgent.setcUser(user);
                importAgent.setDatacontent(JSONArray.toJSONString(datum));
                importAgent.setDataid(datum.get(0) + "");
                importAgent.setDatatype(dataType);
                if (1 != insertAgentImportData(importAgent)) {
                    throw new ProcessException("插入失败");
                }
                ids.add(importAgent.getId());
        }
        return ids;
    }



    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    @Override
    public AgentResult pareseOrder(ImportAgent importAgent,String User) throws Exception {

        try {
            //解析订单信息
            String OBASE_dataContent = importAgent.getDatacontent();
            JSONArray OBASE_dataContent_jsonArray = JSONArray.parseArray(OBASE_dataContent);
            OrderImportBaseInfo orderImportBaseInfo  = new OrderImportBaseInfo();
            orderImportBaseInfo.loadInfoFromJsonArray(OBASE_dataContent_jsonArray);

            //查询商品信息
            ImportAgentExample goods_exa = new ImportAgentExample();
            goods_exa.or().andDealstatusEqualTo(Status.STATUS_0.status)
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andDataidEqualTo(orderImportBaseInfo.getOrder_id())
                    .andDatatypeEqualTo(AgImportType.OGOODS.code);
            List<ImportAgent> goods_list = importAgentMapper.selectByExampleWithBLOBs(goods_exa);
            List<OrderImportGoodsInfo> OrderImportGoodsInfos = new ArrayList<>();
            for (ImportAgent goodss : goods_list) {
                //解析订单商品信息
                String OGOODS_dataContent = goodss.getDatacontent();
                JSONArray OGOODS_dataContent_jsonArray = JSONArray.parseArray(OGOODS_dataContent);
                OrderImportGoodsInfo orderImportGoodsInfo  = new OrderImportGoodsInfo();
                orderImportGoodsInfo.loadInfoFromJsonArray(OGOODS_dataContent_jsonArray);

                //订单商品发货情况
                List<OrderLogicInfo> logicInfos = new ArrayList<>();
                //从redis里那物流信息
                List<String> listLogic = redisService.lrange(orderImportGoodsInfo.getOrder_id(),0,-1);
                for (String listLogicItem : listLogic) {
                    JSONArray logic_dataContent_jsonArray = JSONArray.parseArray(listLogicItem);
                    OrderLogicInfo orderLogicInfo = new OrderLogicInfo();
                    orderLogicInfo.loadInfoFromJsonArray(logic_dataContent_jsonArray);
                    logicInfos.add(orderLogicInfo);
                }
                //设置商品物流信息
                orderImportGoodsInfo.setLogicInfos(logicInfos);
                //设置商品列表
                OrderImportGoodsInfos.add(orderImportGoodsInfo);
            }
            orderImportBaseInfo.setOrderImportGoodsInfos(OrderImportGoodsInfos);
            AgentResult agentImport = orderImportService.pareseOrderImportBaseInfo(orderImportBaseInfo,User);
            return agentImport;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("解析导入订单失败",e);
            return AgentResult.fail(e.getLocalizedMessage());
        }

    }

    /**
     * 解析生成订单及付款单信息
     * @param orderImportBaseInfo
     * @param User
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public AgentResult pareseOrderImportBaseInfo(OrderImportBaseInfo orderImportBaseInfo, String User) throws Exception {

        //查询代理商信息 查询代理商业务平台信息
        AgentBusInfoExample example = new AgentBusInfoExample();
        example.or().andBusNumEqualTo(orderImportBaseInfo.getOrder_orgid()).andStatusEqualTo(Status.STATUS_1.status);
        List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(example);
        if(agentBusInfoList.size()!=1){
            return AgentResult.fail("代理商业务平台未找到"+orderImportBaseInfo.getOrder_orgid());
        }
        AgentBusInfo agentBusInfo = agentBusInfoList.get(0);
        Agent agent = agentMapper.selectByPrimaryKey(agentBusInfo.getAgentId());
        CuserAgentExample cuserAgentExample = new CuserAgentExample();
        cuserAgentExample.or().andAgentidEqualTo(agent.getId()).andStatusEqualTo(Status.STATUS_1.status);
        List<CuserAgent> cuserAgentList = cuserAgentMapper.selectByExample(cuserAgentExample);
        CuserAgent cuserAgent = null;
        if(cuserAgentList.size()==1){
             cuserAgent = cuserAgentList.get(0);
        }
        Calendar c = Calendar.getInstance();


        //==========================================生成订单信息
        OOrder orderFormVo = new OOrder();
        orderFormVo.setId(idService.genId(TabId.o_order));
        //自编订单号
        orderFormVo.setoNum(orderImportBaseInfo.getOrder_id());
        //订单申请时间
        orderFormVo.setoApytime(DateUtil.format(orderImportBaseInfo.getOrder_date(),"yyyy-MM-dd"));
        //订单生效时间
        orderFormVo.setoInuretime(DateUtil.format(orderImportBaseInfo.getOrder_date(),"yyyy-MM-dd"));
        //代理商登录用户ID
        orderFormVo.setUserId(cuserAgent!=null?cuserAgent.getUserid():"");
        //支付方式
        orderFormVo.setPaymentMethod(SettlementType.getByType(orderImportBaseInfo.getOrder_paymethod()).code);
        //订单金额
        orderFormVo.setoAmo(new BigDecimal(orderImportBaseInfo.getOrder_amt()));
        //订单应付金额
        orderFormVo.setPayAmo(new BigDecimal(orderImportBaseInfo.getOrder_amt()));
        //顶大优惠金额
        orderFormVo.setIncentiveAmo(BigDecimal.ZERO);
        //备注
        orderFormVo.setRemark(orderImportBaseInfo.getOrder_remark()+"（老订单）");
        //代理商ID
        orderFormVo.setAgentId(agent.getId());
        //平台代码
        orderFormVo.setOrderPlatform(agentBusInfo.getBusPlatform());
        //busid
        orderFormVo.setBusId(agentBusInfo.getId());
        orderFormVo.setReviewStatus(AgStatus.Approved.status);
        orderFormVo.setOrderStatus(OrderStatus.ENABLE.status);
        if(orderImportBaseInfo.getOrder_amt().equals(orderImportBaseInfo.getOrder_have_amt())) {
            orderFormVo.setClearStatus(Status.STATUS_1.status);
        }else{
            orderFormVo.setClearStatus(Status.STATUS_0.status);
        }
        orderFormVo.setStatus(Status.STATUS_1.status);
        orderFormVo.setcTime(c.getTime());
        orderFormVo.setuUser(User);
        orderFormVo.setuTime(c.getTime());
        orderFormVo.setVersion(Status.STATUS_0.status);
        if(1!=oOrderMapper.insertSelective(orderFormVo)){
            throw new MessageException("插入订单失败");
        }


        //==========================================生成付款单
        OPayment oPayment = new OPayment();
        oPayment.setId(idService.genId(TabId.o_payment));
        oPayment.setUserId(orderFormVo.getUserId());
        oPayment.setOrderId(orderFormVo.getId());
        oPayment.setAgentId(orderFormVo.getAgentId());
        oPayment.setPayMethod(orderFormVo.getPaymentMethod());
        //订单金额
        oPayment.setPayAmount(orderFormVo.getPayAmo());
        //已付金额
        oPayment.setRealAmount(new BigDecimal(orderImportBaseInfo.getOrder_have_amt()));
        //待付金额
        oPayment.setOutstandingAmount(oPayment.getPayAmount().subtract(oPayment.getRealAmount()));
        oPayment.setPayCompletTime(c.getTime());
        //未付款
        if(BigDecimal.ZERO.compareTo(new BigDecimal(orderImportBaseInfo.getOrder_have_amt()))==0){
            oPayment.setPayStatus(PayStatus.NON_PAYMENT.code);
            //已结清
        }else if(oPayment.getPayAmount().compareTo(oPayment.getRealAmount())==0){
            oPayment.setPayStatus(PayStatus.CLOSED.code);
            //部分付款
        }else if(oPayment.getPayAmount().compareTo(oPayment.getRealAmount())>0){
            oPayment.setPayStatus(PayStatus.PART_PAYMENT.code);
        }
        oPayment.setPlanSucTime(c.getTime());
        oPayment.setGuaranteeAgent(agentBusInfo.getBusRiskParent());
        if("是".equals(orderImportBaseInfo.getOrder_is_fp())) {
            oPayment.setIsCloInvoice(Status.STATUS_1.status);
        }else{
            oPayment.setIsCloInvoice(Status.STATUS_0.status);
        }
        for (Dict dict : CAPITAL_TYPE) {
            if(dict.getdItemname().equals(orderImportBaseInfo.getOrder_dk_type())){
                oPayment.setDeductionType(dict.getdItemvalue());
                break;
            }
        }
        //抵扣金额
        oPayment.setDeductionAmount(orderImportBaseInfo.getOrder_dk_amt()!=null?new BigDecimal(orderImportBaseInfo.getOrder_dk_amt()):BigDecimal.ZERO);

        //首付金额
        if(StringUtils.isNotBlank(orderImportBaseInfo.getOrder_shoufu_amt())) {
            oPayment.setDownPayment(
                    StringUtils.isNotBlank(orderImportBaseInfo.getOrder_shoufu_amt())?
                    new BigDecimal(orderImportBaseInfo.getOrder_shoufu_amt())
                    :BigDecimal.ZERO);
        }
        //分期期数
        oPayment.setDownPaymentCount(
                StringUtils.isNotBlank(orderImportBaseInfo.getOrder_fenqi_count())
                ?new BigDecimal(orderImportBaseInfo.getOrder_fenqi_count())
                :BigDecimal.ZERO);
        //分期日期
        oPayment.setDownPaymentDate(
                StringUtils.isNotBlank(orderImportBaseInfo.getOrder_fenqi_date())?
                DateUtil.format(orderImportBaseInfo.getOrder_fenqi_date(),"yyyy-MM")
                :null
        );
        //打款用户
        oPayment.setDownPaymentUser(
                StringUtils.isNotBlank(orderImportBaseInfo.getOrder_pay_user())
                ?orderImportBaseInfo.getOrder_pay_user()
                :"");

        //收款公司
        for (PayComp payComp : payComps) {
            if(payComp.getComName().equals(orderImportBaseInfo.getOrder_colcomp())){
                oPayment.setCollectCompany(payComp.getId());
            }
        }
        oPayment.setActualReceipt(oPayment.getDownPayment());
        oPayment.setActualReceiptDate(c.getTime());
        oPayment.setRemark("(老订单)");
        oPayment.setcTime(c.getTime());
        oPayment.setStatus(Status.STATUS_1.status);
        oPayment.setVersion(Status.STATUS_0.status);
        if(1!=oPaymentMapper.insertSelective(oPayment)){
            throw new MessageException("插入付款单失败");
        }


        //==========================================生成付款明细
        String batchcode = c.getTime().getTime()+"";
        //总额
        BigDecimal payAmount = oPayment.getPayAmount();
        //已付
        BigDecimal yifu = oPayment.getRealAmount();
        //待付生成分期 或者生成欠款
        BigDecimal daifu = oPayment.getOutstandingAmount();
        //首付金额 首付生成首付明细
        BigDecimal shoufu = oPayment.getDownPayment();
        if(shoufu.compareTo(BigDecimal.ZERO)>0){
            OPaymentDetail record = new OPaymentDetail();
            record.setId(idService.genId(TabId.o_payment_detail));
            record.setBatchCode(batchcode);
            record.setPaymentId(oPayment.getId());
            record.setPaymentType(PamentIdType.ORDER_FKD.code);
            record.setOrderId(oPayment.getOrderId());
            record.setPayType(PaymentType.SF.code);
            record.setPayAmount(shoufu);
            record.setRealPayAmount(shoufu);
            record.setPlanPayTime(c.getTime());
            record.setPlanNum(BigDecimal.ZERO);
            record.setAgentId(orderFormVo.getAgentId());
            record.setPaymentStatus(PaymentStatus.JQ.code);
            record.setRemark("(老订单)");
            record.setcUser(orderFormVo.getUserId());
            record.setcDate(c.getTime());
            record.setStatus(Status.STATUS_1.status);
            record.setVersion(Status.STATUS_1.status);
            if (1 != oPaymentDetailMapper.insert(record)) {
                throw new MessageException("分期处理");
            }
        }
        //抵扣 抵扣生成抵扣明细
        String dikou_type = oPayment.getDeductionType();
        BigDecimal dikou = oPayment.getDeductionAmount();
        if(dikou.compareTo(BigDecimal.ZERO)>0){
            OPaymentDetail record = new OPaymentDetail();
            record.setId(idService.genId(TabId.o_payment_detail));
            record.setBatchCode(batchcode);
            record.setPaymentId(oPayment.getId());
            record.setPaymentType(PamentIdType.ORDER_FKD.code);
            record.setOrderId(oPayment.getOrderId());
            record.setPayType(PaymentType.DK.code);
            record.setPayAmount(dikou);
            record.setRealPayAmount(dikou);
            record.setPlanPayTime(c.getTime());
            record.setPlanNum(BigDecimal.ZERO);
            record.setAgentId(orderFormVo.getAgentId());
            record.setPaymentStatus(PaymentStatus.JQ.code);
            record.setRemark(dikou_type+"(老订单)");
            record.setcUser(orderFormVo.getUserId());
            record.setcDate(c.getTime());
            record.setStatus(Status.STATUS_1.status);
            record.setVersion(Status.STATUS_1.status);
            if (1 != oPaymentDetailMapper.insert(record)) {
                throw new MessageException("分期处理");
            }
        }
        //已付款余处
        BigDecimal yifu_mingxi = yifu.subtract(shoufu).subtract(dikou);
        if(yifu_mingxi.compareTo(BigDecimal.ZERO)>0){
            //生成逾出明细
            OPaymentDetail record = new OPaymentDetail();
            record.setId(idService.genId(TabId.o_payment_detail));
            record.setBatchCode(batchcode);
            record.setPaymentId(oPayment.getId());
            record.setPaymentType(PamentIdType.ORDER_FKD.code);
            record.setOrderId(oPayment.getOrderId());
            record.setPayType(PaymentType.DK.code);
            record.setPayAmount(yifu_mingxi);
            record.setRealPayAmount(yifu_mingxi);
            record.setPlanPayTime(c.getTime());
            record.setPlanNum(BigDecimal.ZERO);
            record.setAgentId(orderFormVo.getAgentId());
            record.setPaymentStatus(PaymentStatus.JQ.code);
            record.setRemark("已付款余处(老订单)");
            record.setcUser(orderFormVo.getUserId());
            record.setcDate(c.getTime());
            record.setStatus(Status.STATUS_1.status);
            record.setVersion(Status.STATUS_1.status);
            if (1 != oPaymentDetailMapper.insert(record)) {
                throw new MessageException("分期处理");
            }
        }
        //如果有待付款 生成待付款明细
        if(daifu.compareTo(BigDecimal.ZERO)>0) {
            List<Map> FKFQ_data = null;
            PaymentType paymentType = null;

            //生成付款明细
            if (oPayment.getPayMethod().equals(SettlementType.SF1.code)) {
                paymentType = PaymentType.FRFQ;
                 FKFQ_data =  FKFQ_data = StageUtil.stageOrder(daifu,oPayment.getDownPaymentCount().intValue(),oPayment.getDownPaymentDate(), c.get(Calendar.DAY_OF_MONTH));
            } else if (oPayment.getPayMethod().equals(SettlementType.SF2.code)) {
                paymentType = PaymentType.DKFQ;
                 FKFQ_data =  FKFQ_data = StageUtil.stageOrder(daifu,oPayment.getDownPaymentCount().intValue(),oPayment.getDownPaymentDate(), c.get(Calendar.DAY_OF_MONTH));
            } else if (oPayment.getPayMethod().equals(SettlementType.FKFQ.code)) {
                paymentType = PaymentType.DKFQ;
                 FKFQ_data =  FKFQ_data = StageUtil.stageOrder(daifu,oPayment.getDownPaymentCount().intValue(),oPayment.getDownPaymentDate(), c.get(Calendar.DAY_OF_MONTH));
            } else if (oPayment.getPayMethod().equals(SettlementType.XXDK.code)) {
                paymentType = PaymentType.DKFQ;
                 FKFQ_data =  FKFQ_data = StageUtil.stageOrder(daifu,1,c.getTime(), c.get(Calendar.DAY_OF_MONTH));
            } else if (oPayment.getPayMethod().equals(SettlementType.FRFQ.code)) {
                paymentType = PaymentType.FRFQ;
                FKFQ_data =  FKFQ_data = StageUtil.stageOrder(daifu,oPayment.getDownPaymentCount().intValue(),oPayment.getDownPaymentDate(), c.get(Calendar.DAY_OF_MONTH));
            }else{
                paymentType = PaymentType.FRFQ;
                FKFQ_data =  FKFQ_data = StageUtil.stageOrder(daifu,oPayment.getDownPaymentCount().intValue(),oPayment.getDownPaymentDate(), c.get(Calendar.DAY_OF_MONTH));
            }
            if (FKFQ_data.size() > 0) {
                for (Map datum : FKFQ_data) {
                    OPaymentDetail record = new OPaymentDetail();
                    record.setId(idService.genId(TabId.o_payment_detail));
                    record.setBatchCode(batchcode);
                    record.setPaymentId(oPayment.getId());
                    record.setPaymentType(PamentIdType.ORDER_FKD.code);
                    record.setOrderId(oPayment.getOrderId());
                    record.setPayType(paymentType.code);
                    record.setPayAmount((BigDecimal) datum.get("item"));
                    record.setRealPayAmount(BigDecimal.ZERO);
                    record.setPlanPayTime((Date) datum.get("date"));
                    record.setPlanNum((BigDecimal) datum.get("count"));
                    record.setAgentId(oPayment.getAgentId());
                    record.setPaymentStatus(PaymentStatus.DF.code);
                    record.setcUser(oPayment.getUserId());
                    record.setcDate(c.getTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setVersion(Status.STATUS_1.status);
                    if (1 != oPaymentDetailMapper.insert(record)) {
                        throw new MessageException("分期处理");
                    }
                }
            }
        }
        //解析商品物流信息
        AgentResult suborder =  orderImportService.pareseOrderImportSubOrderInfo(orderImportBaseInfo,orderFormVo,oPayment,User);
        if(!suborder.isOK()){
            throw new MessageException(suborder.getMsg());
        }
        return AgentResult.ok();
    }


    /**
     *
     * @param orderImportBaseInfo
     * @param order
     * @param oPayment
     * @param User
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult pareseOrderImportSubOrderInfo(OrderImportBaseInfo orderImportBaseInfo, OOrder order, OPayment oPayment, String User) throws Exception {
        List<OrderImportGoodsInfo> OrderImportGoodsInfoList = orderImportBaseInfo.getOrderImportGoodsInfos();
        List<String> listIdes = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        //遍历商品查看有没有重复的商品
        for (OrderImportGoodsInfo orderImportGoodsInfo : OrderImportGoodsInfoList) {
            if(listIdes.contains(orderImportGoodsInfo.getGoodsCode())){
                throw new MessageException("订单"+orderImportGoodsInfo.getOrder_id()+"的商品"+orderImportGoodsInfo.getGoodsCode()+"重复，订单中不允许重复商品");
            }
        }
        for (OrderImportGoodsInfo orderImportGoodsInfo : OrderImportGoodsInfoList) {
            //查询商品信息 查询商品活动
            OProductExample productExample = new OProductExample();
            productExample.or()
                    .andProCodeEqualTo(orderImportGoodsInfo.getGoodsCode())
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andProStatusEqualTo(Status.STATUS_1.status);
            List<OProduct> oProductList = oProductMapper.selectByExample(productExample);
            if(oProductList.size()!=1){
                throw new MessageException("商品信息未找到");
            }
            OProduct product = oProductList.get(0);
            OActivity activity = oActivityMapper.selectByPrimaryKey(orderImportGoodsInfo.getActId());

            //物流发货数量检查
            List<OrderLogicInfo>  logicInfos = orderImportGoodsInfo.getLogicInfos();
            BigDecimal goodsSendNumAll = BigDecimal.ZERO;
            for (OrderLogicInfo logicInfo : logicInfos) {
                String  goodsSendNum =  logicInfo.getGoodsSendNum();
                goodsSendNumAll.add(new BigDecimal(goodsSendNum));
            }
            if(goodsSendNumAll.compareTo(new BigDecimal(orderImportGoodsInfo.getGoodsNum()))>0){
                throw new MessageException("发货商品大于订货商品");
            }

            //采购单表
            OSubOrder oSubOrder = new OSubOrder();
            oSubOrder.setId(idService.genId(TabId.o_sub_order));
            oSubOrder.setProId(product.getId());
            oSubOrder.setOrderId(order.getId());
            oSubOrder.setProCode(product.getProCode());
            oSubOrder.setProName(product.getProName());
            oSubOrder.setProType(activity.getProType());
            oSubOrder.setProPrice(activity.getPrice());
            oSubOrder.setSendNum(goodsSendNumAll);
            oSubOrder.setIsDeposit(product.getIsDeposit());
            oSubOrder.setDeposit(product.getDeposit());
            oSubOrder.setModel(activity.getProModel());
            oSubOrder.setProNum(new BigDecimal(orderImportGoodsInfo.getGoodsNum()));
            oSubOrder.setProRelPrice(activity.getPrice());
            oSubOrder.setRemark("(老订单)");
            oSubOrder.setcTime(c.getTime());
            oSubOrder.setuUser(User);
            oSubOrder.setcUser(User);
            oSubOrder.setuTime(c.getTime());
            oSubOrder.setStatus(Status.STATUS_1.status);
            oSubOrder.setVersion(Status.STATUS_0.status);
            oSubOrder.setAgentId(order.getAgentId());

            if(1!=oSubOrderMapper.insertSelective(oSubOrder)){
                throw new MessageException("采购单添加失败");
            }

            //商品采购单活动
            OSubOrderActivity oSubOrderActivity = new OSubOrderActivity();
            oSubOrderActivity.setId(idService.genId(TabId.o_sub_order_activity));
            oSubOrderActivity.setActivityId(activity.getId());
            oSubOrderActivity.setSubOrderId(oSubOrder.getId());
            oSubOrderActivity.setActivityName(activity.getActivityName());
            oSubOrderActivity.setRuleId(activity.getRuleId());
            oSubOrderActivity.setProId(oSubOrder.getProId());
            oSubOrderActivity.setProName(oSubOrder.getProName());
            oSubOrderActivity.setActivityRule(activity.getActivityRule());
            oSubOrderActivity.setActivityWay(activity.getActivityWay());
            oSubOrderActivity.setPrice(activity.getPrice());
            oSubOrderActivity.setOriginalPrice(activity.getOriginalPrice());
            oSubOrderActivity.setProModel(activity.getProModel());
            oSubOrderActivity.setVender(activity.getVender());
            oSubOrderActivity.setPlatform(activity.getPlatform());
            oSubOrderActivity.setgTime(activity.getgTime());
            oSubOrderActivity.setcTime(c.getTime());
            oSubOrderActivity.setuTime(c.getTime());
            oSubOrderActivity.setcUser(User);
            oSubOrderActivity.setuUser(User);
            oSubOrderActivity.setStatus(Status.STATUS_1.status);
            oSubOrderActivity.setVersion(Status.STATUS_0.status);
            oSubOrderActivity.setBusProCode(activity.getBusProCode());
            oSubOrderActivity.setBusProName(activity.getBusProName());
            oSubOrderActivity.setTermBatchcode(activity.getTermBatchcode());
            oSubOrderActivity.setTermBatchname(activity.getTermBatchname());
            oSubOrderActivity.setTermtype(activity.getTermtype());
            oSubOrderActivity.setTermtypename(activity.getTermtypename());
            oSubOrderActivity.setPosType(activity.getPosType());
            oSubOrderActivity.setPosSpePrice(activity.getPosSpePrice());
            oSubOrderActivity.setStandTime(activity.getStandTime());
            oSubOrderActivity.setStandAmt(activity.getStandAmt());
            oSubOrderActivity.setBackType(activity.getBackType());
            if(1!=oSubOrderActivityMapper.insertSelective(oSubOrderActivity)){
                throw new MessageException("采购单活动添加失败");
            }
            //发货单 排单 物流 生成
            for (OrderLogicInfo logicInfo : logicInfos) {

                OReceiptOrder receiptOrder = new OReceiptOrder();
                OReceiptPro oReceiptPro = new OReceiptPro();
                ReceiptPlan receiptPlan = new ReceiptPlan();
                OLogistics logistics = new OLogistics();

                //收货单
                receiptOrder.setId(idService.genId(TabId.o_receipt_order));
                receiptOrder.setOrderId(order.getId());
                receiptOrder.setReceiptNum(receiptOrder.getId());
                OAddress address = oAddressMapper.selectByPrimaryKey(addressCode);
                receiptOrder.setAddressId(address.getId());
                receiptOrder.setAddrRealname(address.getAddrRealname());
                receiptOrder.setAddrMobile(address.getAddrMobile());
                receiptOrder.setAddrProvince(address.getAddrProvince());
                receiptOrder.setAddrCity(address.getAddrCity());
                receiptOrder.setAddrDistrict(address.getAddrDistrict());
                receiptOrder.setAddrDetail(address.getAddrDetail()+logicInfo.getSendAddress());
                receiptOrder.setProNum(new BigDecimal(logicInfo.getGoodsSendNum()));
                receiptOrder.setRemark(address.getRemark()+"(老订单)");
                receiptOrder.setZipCode(address.getZipCode());
                receiptOrder.setcTime(c.getTime());
                receiptOrder.setuTime(c.getTime());
                receiptOrder.setReceiptStatus(OReceiptStatus.DISPATCHED_ORDER.code);
                receiptOrder.setuUser(User);
                receiptOrder.setcUser(User);
                receiptOrder.setStatus(Status.STATUS_1.status);
                receiptOrder.setVersion(Status.STATUS_0.status);
                receiptOrder.setAgentId(order.getAgentId());
                if(1!=oReceiptOrderMapper.insertSelective(receiptOrder)){
                    throw new MessageException("收货单添加失败");
                }

                //收货单商品
                oReceiptPro.setId(idService.genId(TabId.o_receipt_pro));
                oReceiptPro.setcTime(c.getTime());
                oReceiptPro.setOrderid(order.getId());
                oReceiptPro.setReceiptId(receiptOrder.getId());
                oReceiptPro.setProId(product.getId());
                oReceiptPro.setProCode(product.getProCode());
                oReceiptPro.setProName(product.getProName());
                oReceiptPro.setProNum(new BigDecimal(logicInfo.getGoodsSendNum()));
                oReceiptPro.setSendNum(new BigDecimal(logicInfo.getGoodsSendNum()));
                oReceiptPro.setcUser(User);
                oReceiptPro.setuTime(c.getTime());
                oReceiptPro.setuUser(User);
                oReceiptPro.setStatus(Status.STATUS_1.status);
                oReceiptPro.setVersion(Status.STATUS_0.status);
                oReceiptPro.setReceiptProStatus(OReceiptStatus.DISPATCHED_ORDER.code);
                if(1!=oReceiptProMapper.insertSelective(oReceiptPro)){
                    throw new MessageException("收货单商品添加失败");
                }


                //收货单商品排单
                receiptPlan.setId(idService.genId(TabId.o_receipt_plan));
                receiptPlan.setPlanNum(receiptPlan.getId());
                receiptPlan.setOrderId(order.getId());
                receiptPlan.setReceiptId(receiptOrder.getId());
                receiptPlan.setUserId(User);
                receiptPlan.setProId(product.getId());
                receiptPlan.setProType(oSubOrder.getProType());
                receiptPlan.setProCom(oSubOrderActivity.getVender());
                receiptPlan.setPlanProNum(oReceiptPro.getSendNum());
                receiptPlan.setSendProNum(oReceiptPro.getSendNum());
                receiptPlan.setModel(oSubOrderActivity.getProModel());
                receiptPlan.setSendDate(c.getTime());
                receiptPlan.setRealSendDate(receiptPlan.getSendDate());
                receiptPlan.setRemark("(老订单)");
                receiptPlan.setcUser(User);
                receiptPlan.setcDate(c.getTime());
                receiptPlan.setPlanOrderStatus(new BigDecimal(PlannerStatus.YesDeliver.getValue()));
                receiptPlan.setStatus(Status.STATUS_1.status);
                receiptPlan.setVersion(Status.STATUS_1.status);
                if(1!=receiptPlanMapper.insertSelective(receiptPlan)){
                    throw new MessageException("排单信息添加失败");
                }

                //收货单商品排单
                logistics.setId(idService.genId(TabId.o_logistics));
                logistics.setOrderId(order.getId());
                logistics.setReceiptPlanId(receiptPlan.getId());
                logistics.setProCom(receiptPlan.getProCom());
                logistics.setProId(receiptPlan.getProId());
                logistics.setProName(product.getProName());
                logistics.setProType(receiptPlan.getProType());
                logistics.setSendNum(receiptPlan.getSendProNum());
                logistics.setProPrice(oSubOrderActivity.getPrice());
                logistics.setProModel(receiptPlan.getModel());
                try {
                    logistics.setSendDate(DateUtil.format(logicInfo.getSendTime(),"yyyy-MM-dd"));
                }catch (Exception e){
                    try {
                        logistics.setSendDate(DateUtil.format(logicInfo.getSendTime(),"yyyyMMdd"));
                    }catch (Exception m){
                        try {
                            logistics.setSendDate(DateUtil.format(logicInfo.getSendTime(),"yyyy/MM/dd"));
                        }catch (Exception edate){
                            throw new MessageException("日期格式支持yyyyMMdd 或者yyyy-MM-dd 或者 yyyy/MM/dd");
                        }

                    }
                }
                logistics.setLogCom(logicInfo.getLogicComp());
                logistics.setwNumber(logicInfo.getLogicCode());
                logistics.setIsdeall(Status.STATUS_1.status);
                logistics.setSnBeginNum(logicInfo.getSnStart());
                logistics.setSnEndNum(logicInfo.getSnEnd());
                logistics.setLogType(LogType.Deliver.getValue());
                logistics.setcTime(c.getTime());
                logistics.setcUser(User);
                logistics.setStatus(Status.STATUS_1.status);
                logistics.setSendStatus(Status.STATUS_1.status);
                logistics.setSendMsg("(老订单)");
                if(1!=oLogisticsMapper.insertSelective(logistics)){
                    throw new MessageException("排单信息添加失败");
                }

                //生成sn明细
                List<String> idListArr = oLogisticsService.idList(logicInfo.getSnStart(), logicInfo.getSnEnd(), Integer.valueOf(logicInfo.getSnEndNum()),Integer.valueOf( logicInfo.getSnEndNum()));

                for (String snNum : idListArr) {
                    //检查sn是否存在物流状态和记录状态
                    OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                    OLogisticsDetailExample.Criteria oLogisticsDetailExample_criteria = oLogisticsDetailExample.createCriteria();
                    oLogisticsDetailExample_criteria.andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code);
                    oLogisticsDetailExample_criteria.andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                    oLogisticsDetailExample_criteria.andSnNumEqualTo(snNum);
                    List<OLogisticsDetail> oLogisticsDetails = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                    if (null != oLogisticsDetails && oLogisticsDetails.size() > 0) {
                        //说明已经存在数据
                        logger.info(snNum+"此物流已经存在,正在发货中!!!");
                        throw new MessageException(snNum+"此物流已经存在,正在发货中!!!");
                    }
                }
                if (null != idListArr && idListArr.size() > 0) {
                    for (String idSn : idListArr) {
                        OLogisticsDetail detail = new OLogisticsDetail();
                        //id，物流id，创建人，更新人，状态
                        detail.setId(idService.genId(TabId.o_logistics_detail));
                        detail.setOrderId(oSubOrder.getOrderId());
                        detail.setOrderNum(order.getoNum());
                        detail.setLogisticsId(logistics.getId());
                        detail.setProId(logistics.getProId());
                        detail.setProName(logistics.getProName());
                        detail.setSettlementPrice(oSubOrderActivity.getPrice());
                        detail.setSnNum(idSn);
                        detail.setAgentId(order.getAgentId());
                        detail.setcUser(User);
                        detail.setuUser(User);
                        detail.setcTime(Calendar.getInstance().getTime());
                        detail.setuTime(Calendar.getInstance().getTime());
                        detail.setOptType(OLogisticsDetailOptType.ORDER.code);
                        detail.setOptId(oSubOrder.getId());
                        OOrder oOrder = oOrderMapper.selectByPrimaryKey(oSubOrder.getOrderId());
                        detail.setBusId(oOrder.getBusId());
                        detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                        detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);

                        detail.setActivityId(oSubOrderActivity.getActivityId());
                        detail.setActivityName(oSubOrderActivity.getActivityName());
                        detail.setgTime(oSubOrderActivity.getgTime());
                        detail.setBusProCode(oSubOrderActivity.getBusProCode());
                        detail.setBusProName(oSubOrderActivity.getBusProName());
                        detail.setTermBatchcode(oSubOrderActivity.getTermBatchcode());
                        detail.setTermBatchname(oSubOrderActivity.getTermBatchname());
                        detail.setTermtype(oSubOrderActivity.getTermtype());
                        detail.setTermtypename(oSubOrderActivity.getTermtypename());
                        detail.setSettlementPrice(oSubOrderActivity.getPrice());
                        detail.setPosType(oSubOrderActivity.getPosType());
                        detail.setPosSpePrice(oSubOrderActivity.getPosSpePrice());
                        detail.setStandTime(oSubOrderActivity.getStandTime());

                        if (1 != oLogisticsDetailMapper.insertSelective(detail)) {
                            logger.info("添加失败");
                            throw new ProcessException("添加失败");
                        }
                    }
                }
            }

        }
        return null;
    }

    /**
     * 物流信息存储到redis
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult pareseOrderLogic(String value) throws Exception {
        logger.info("======{}物流信存储到reids",value);
        //查询订单信息
        ImportAgentExample example = new ImportAgentExample();
        example.or().andDealstatusEqualTo(Status.STATUS_0.status)
                .andStatusEqualTo(Status.STATUS_1.status)
                .andDatatypeEqualTo(AgImportType.OLOGISTICS.code);
        List<ImportAgent> logic_Order = importAgentMapper.selectByExampleWithBLOBs(example);
        int orderlogiccount = 0;
        for (ImportAgent logic : logic_Order) {
            //解析订单商品信息
            String logic_dataContent = logic.getDatacontent();
            JSONArray logic_dataContent_jsonArray = JSONArray.parseArray(logic_dataContent);
            OrderLogicInfo orderLogicInfo = new OrderLogicInfo();
            orderLogicInfo.loadInfoFromJsonArray(logic_dataContent_jsonArray);
            try {
                //将发货物流放到redis里
                redisService.delete(orderLogicInfo.getOrder_id());
                logger.info("======物流信存储到reids[{}][{}]",orderLogicInfo.getOrder_id(),orderLogicInfo.getGoodsCode());
                long count = redisService.rpushList(orderLogicInfo.getOrder_id(),logic_dataContent_jsonArray.toJSONString());
                logger.info("======物流信存储到reids[{}][{}][{}]条",orderLogicInfo.getOrder_id(),orderLogicInfo.getGoodsCode(),count);
                orderlogiccount++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return AgentResult.ok(orderlogiccount);
    }

    @Override
    public AgentResult pareseReturn() throws Exception {

        return null;
    }
}
