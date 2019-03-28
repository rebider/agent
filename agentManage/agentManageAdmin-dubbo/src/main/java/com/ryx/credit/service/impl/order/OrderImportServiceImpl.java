package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONArray;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.MapUtil;
import com.ryx.credit.common.util.ResultVO;
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
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.ApaycompService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 作者：cx
 * 时间：2019/1/29
 * 描述：订单导入解析（含有SN订单解析）
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
    @Autowired
    private OrderService orderService;
    @Autowired
    private OLogisticsDetailMapper  oLogisticsDetailMapper;
    @Autowired
    private OSubOrderActivityMapper oSubOrderActivityMapper;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private OReturnOrderDetailMapper oReturnOrderDetailMapper;
    @Autowired
    private OReturnOrderMapper  oReturnOrderMapper;
    @Autowired
    private OReturnOrderRelMapper oReturnOrderRelMapper;
    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

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

    /**
     * 解析SN订单数据（含有SN）
     * @param user
     * @return
     */
    @Override
    public ResultVO pareseOrderEnter(String user) {
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ImportAgentExample order_exa = new ImportAgentExample();
                order_exa.or().andDealstatusEqualTo(Status.STATUS_0.status)
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andDatatypeEqualTo(AgImportType.OBASE.code);
                List<ImportAgent> order_exa_list = importAgentMapper.selectByExampleWithBLOBs(order_exa);
                for (ImportAgent importAgent : order_exa_list) {
                    try {
                        logger.info("======处理订单解析{}",importAgent.getDataid());
                        importAgent.setDealstatus(Status.STATUS_1.status);
                        importAgent.setDealmsg("处理中");
                        importAgent.setDealTime(new Date());
                        importAgentMapper.updateByPrimaryKeySelective(importAgent);
                        if(orderImportService.deleteFailImportAgentOrder(importAgent,user).isOK()) {
                            AgentResult agentResult = orderImportService.pareseOrder(importAgent, user);
                            importAgent = importAgentMapper.selectByPrimaryKey(importAgent.getId());
                            importAgent.setDealstatus(Status.STATUS_2.status);
                            importAgent.setDealmsg(agentResult.getMsg());
                            importAgent.setDealTime(new Date());
                            importAgentMapper.updateByPrimaryKeySelective(importAgent);
                            logger.info("======处理订单解析{}完成{}", importAgent.getDataid(), agentResult.getMsg());
                        }
                    } catch (MessageException e) {
                        e.printStackTrace();
                        importAgent = importAgentMapper.selectByPrimaryKey(importAgent.getId());
                        importAgent.setDealstatus(Status.STATUS_3.status);
                        importAgent.setDealmsg(e.getMsg());
                        importAgent.setDealTime(new Date());
                        importAgentMapper.updateByPrimaryKeySelective(importAgent);
                        logger.info("======处理订单解析{}完成{}",importAgent.getDataid(),e.getMsg());
                    }catch (Exception e) {
                        e.printStackTrace();
                        importAgent = importAgentMapper.selectByPrimaryKey(importAgent.getId());
                        importAgent.setDealstatus(Status.STATUS_3.status);
                        importAgent.setDealmsg(e.getLocalizedMessage().substring(0,60));
                        importAgent.setDealTime(new Date());
                        importAgentMapper.updateByPrimaryKeySelective(importAgent);
                        logger.info("======处理订单解析{}完成{}",importAgent.getDataid(),e.getLocalizedMessage());
                    }
                }
            }
        });

        return new ResultVO(ResultVO.SUCCESS,"任务处理中");
    }


    @Override
    public ResultVO pareseReturnOrderEnter(String user) {
        threadPoolTaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                ImportAgentExample order_exa = new ImportAgentExample();
                order_exa.or().andDealstatusEqualTo(Status.STATUS_0.status)
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andDatatypeEqualTo(AgImportType.ORETURN.code);
                List<ImportAgent> order_exa_list = importAgentMapper.selectByExampleWithBLOBs(order_exa);
                for (ImportAgent importAgent : order_exa_list) {
                    try {
                        logger.info("======处理订单解析{}",importAgent.getDataid());
                        importAgent.setDealstatus(Status.STATUS_1.status);
                        importAgent.setDealmsg("处理中");
                        importAgent.setDealTime(new Date());
                        importAgentMapper.updateByPrimaryKeySelective(importAgent);
                        if(orderImportService.deleteFailImportAgentReturn(importAgent,user).isOK()){
                            AgentResult agentResult = orderImportService.pareseReturn(importAgent,user);
                            importAgent = importAgentMapper.selectByPrimaryKey(importAgent.getId());
                            importAgent.setDealstatus(Status.STATUS_2.status);
                            importAgent.setDealmsg(agentResult.getMsg());
                            importAgent.setDealTime(new Date());
                            importAgentMapper.updateByPrimaryKeySelective(importAgent);
                            logger.info("======处理订单解析{}完成{}",importAgent.getDataid(),agentResult.getMsg());
                        }
                    } catch (MessageException e) {
                        e.printStackTrace();
                        importAgent = importAgentMapper.selectByPrimaryKey(importAgent.getId());
                        importAgent.setDealstatus(Status.STATUS_3.status);
                        importAgent.setDealmsg(e.getMsg());
                        importAgent.setDealTime(new Date());
                        importAgentMapper.updateByPrimaryKeySelective(importAgent);
                        logger.info("======处理订单解析{}完成{}",importAgent.getDataid(),e.getMsg());
                    }catch (Exception e) {
                        e.printStackTrace();
                        importAgent = importAgentMapper.selectByPrimaryKey(importAgent.getId());
                        importAgent.setDealstatus(Status.STATUS_3.status);
                        importAgent.setDealmsg(e.getLocalizedMessage());
                        importAgent.setDealTime(new Date());
                        importAgentMapper.updateByPrimaryKeySelective(importAgent);
                        logger.info("======处理订单解析{}完成{}",importAgent.getDataid(),e.getLocalizedMessage());
                    }
                }
            }
        });
        return new ResultVO(ResultVO.SUCCESS,"任务处理中");
    }


    /**
     * 解析订单对象
     * @param importAgent
     * @param User
     * @return
     * @throws MessageException
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    @Override
    public AgentResult pareseOrder(ImportAgent importAgent,String User) throws MessageException {
        try {
            //解析订单信息
            String OBASE_dataContent = importAgent.getDatacontent();
            JSONArray OBASE_dataContent_jsonArray = JSONArray.parseArray(OBASE_dataContent);
            OrderImportBaseInfo orderImportBaseInfo  = new OrderImportBaseInfo();
            orderImportBaseInfo.loadInfoFromJsonArray(OBASE_dataContent_jsonArray,importAgent.getId());

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
                orderImportGoodsInfo.loadInfoFromJsonArray(OGOODS_dataContent_jsonArray,goodss.getId());
                //订单商品发货情况
                List<OrderLogicInfo> logicInfos = new ArrayList<>();
                //从redis里那物流信息
                ImportAgentExample goods_logic_exa = new ImportAgentExample();
                goods_logic_exa.or().andDealstatusEqualTo(Status.STATUS_0.status)
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andDataidEqualTo(orderImportBaseInfo.getOrder_id())
                        .andDatatypeEqualTo(AgImportType.OLOGISTICS.code);
                List<ImportAgent> goods_logic_exa_list = importAgentMapper.selectByExampleWithBLOBs(goods_logic_exa);
                for (ImportAgent listLogicItem : goods_logic_exa_list) {
                    JSONArray logic_dataContent_jsonArray = JSONArray.parseArray(listLogicItem.getDatacontent());
                    OrderLogicInfo orderLogicInfo = new OrderLogicInfo();
                    orderLogicInfo.loadInfoFromJsonArray(logic_dataContent_jsonArray,listLogicItem.getId());
                    logicInfos.add(orderLogicInfo);
                }
                if(logicInfos.size()==0){
                    redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"物流信息记录为空["+orderImportBaseInfo.getOrder_id()+"]");
                    logger.info("物流信息记录为空["+orderImportBaseInfo.getOrder_id()+"]");
                    throw new MessageException("物流信息记录为空["+orderImportBaseInfo.getOrder_id()+"]");
                }
                //设置商品物流信息
                orderImportGoodsInfo.setLogicInfos(logicInfos);
                //设置商品列表
                OrderImportGoodsInfos.add(orderImportGoodsInfo);
            }
            if(OrderImportGoodsInfos.size()==0){
                redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"商品信息记录为空["+orderImportBaseInfo.getOrder_id()+"]");
                throw new MessageException("商品信息记录为空["+orderImportBaseInfo.getOrder_id()+"]");
            }
            orderImportBaseInfo.setOrderImportGoodsInfos(OrderImportGoodsInfos);

            AgentResult agentImport = AgentResult.ok();
            try {
                agentImport = orderImportService.pareseOrderImportBaseInfo(orderImportBaseInfo,User);
            } catch (MessageException e) {
                e.printStackTrace();
                agentImport = AgentResult.fail(e.getMsg());
            }
            //订单解析失败 订单商品记录也更新为失败
            if (!agentImport.isOK()) {
                List<OrderImportGoodsInfo> orderImportGoodsInfoList = orderImportBaseInfo.getOrderImportGoodsInfos();
                for (OrderImportGoodsInfo orderImportGoodsInfo : orderImportGoodsInfoList) {
                    String importId = orderImportGoodsInfo.getImportId();
                    ImportAgent orderImportGoodsInfo_importAgent = importAgentMapper.selectByPrimaryKey(importId);
                    orderImportGoodsInfo_importAgent.setDealstatus(Status.STATUS_3.status);
                    orderImportGoodsInfo_importAgent.setDealmsg(agentImport.getMsg());
                    orderImportGoodsInfo_importAgent.setDealTime(new Date());
                    int updateAgentImport = importAgentMapper.updateByPrimaryKeySelective(orderImportGoodsInfo_importAgent);
                    if (updateAgentImport != 1) {
                        redisService.rpushList(import_order_imOrderMsg_key + orderImportBaseInfo.getOrder_id(),
                                "更新orderImportBaseInfo记录失败" + orderImportBaseInfo.getOrder_id() + ":importId" + importId);
                        logger.info("更新orderImportBaseInfo记录失败" + orderImportBaseInfo.getOrder_id() + ":importId" + importId);
                        throw new MessageException("更新orderImportBaseInfo记录失败" + orderImportBaseInfo.getOrder_id() + ":importId" + importId);
                    }
                }
                logger.info("======处理订单解析{}失败{}", orderImportBaseInfo.getOrder_id(), agentImport.getMsg());
            }
            return agentImport;
        } catch (Exception e) {
            e.printStackTrace();
            redisService.rpushList(import_order_imOrderMsg_key+importAgent.getDataid(),e.getLocalizedMessage());
            logger.error("解析导入订单失败",e);
            throw new MessageException(e.getLocalizedMessage());
        }finally {
            redisService.delete(import_order_logic_redis_key+importAgent.getDataid());
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
    public AgentResult pareseOrderImportBaseInfo(OrderImportBaseInfo orderImportBaseInfo, String User) throws MessageException {
        //查询代理商信息 查询代理商业务平台信息
        AgentBusInfoExample example = new AgentBusInfoExample();
        example.or().andBusNumEqualTo(orderImportBaseInfo.getOrder_orgid()).andStatusEqualTo(Status.STATUS_1.status);
        List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(example);
        if(agentBusInfoList.size()!=1){
            redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"代理商业务平台未找到"+orderImportBaseInfo.getOrder_orgid());
            logger.info("代理商业务平台未找到"+orderImportBaseInfo.getOrder_orgid());
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
        orderFormVo.setId(orderImportBaseInfo.getOrder_id());
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
        //新老订单
        orderFormVo.setOxOrder(Oreturntype.OLD.code);
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
            redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"插入订单失败"+orderImportBaseInfo.getOrder_orgid());
            logger.info("插入订单失败{}",orderImportBaseInfo.getOrder_orgid());
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

        //付款信息
        String batchcode = c.getTime().getTime()+"";
        //总额
        BigDecimal payAmount = oPayment.getPayAmount();
        //已付
        BigDecimal yifu = oPayment.getRealAmount();
        //待付生成分期 或者生成欠款
        BigDecimal daifu = oPayment.getOutstandingAmount();
        //首付金额 首付生成首付明细
        BigDecimal shoufu = oPayment.getDownPayment();
        //抵扣 抵扣生成抵扣明细
        String dikou_type = oPayment.getDeductionType();
        //抵扣金额
        BigDecimal dikou = oPayment.getDeductionAmount()==null ?BigDecimal.ZERO:oPayment.getDeductionAmount();
        //首付实付金额
        BigDecimal acture = (shoufu==null||shoufu.compareTo(BigDecimal.ZERO)==0) ?BigDecimal.ZERO:(shoufu.subtract(dikou));


        oPayment.setActualReceipt(acture);
        oPayment.setActualReceiptDate(c.getTime());
        oPayment.setRemark("(老订单)");
        oPayment.setcTime(c.getTime());
        oPayment.setStatus(Status.STATUS_1.status);
        oPayment.setVersion(Status.STATUS_0.status);
        if(1!=oPaymentMapper.insertSelective(oPayment)){
            redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"插入付款单失败"+orderImportBaseInfo.getOrder_orgid());
            logger.info("插入付款单失败{}",orderImportBaseInfo.getOrder_orgid());
            throw new MessageException("插入付款单失败");
        }

        //==========================================生成付款明细
        if(acture.compareTo(BigDecimal.ZERO)>0){
            OPaymentDetail record = new OPaymentDetail();
            record.setId(idService.genId(TabId.o_payment_detail));
            record.setBatchCode(batchcode);
            record.setPaymentId(oPayment.getId());
            record.setPaymentType(PamentIdType.ORDER_FKD.code);
            record.setOrderId(oPayment.getOrderId());
            record.setPayType(PaymentType.SF.code);
            record.setPayAmount(acture);
            record.setRealPayAmount(acture);
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
                redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"分期处理失败"+orderImportBaseInfo.getOrder_orgid());
                logger.info("分期处理失败{}",orderImportBaseInfo.getOrder_orgid());
                throw new MessageException("分期处理");
            }
        }

        if(dikou!=null && dikou.compareTo(BigDecimal.ZERO)>0){
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
                redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"分期处理失败"+orderImportBaseInfo.getOrder_orgid());
                logger.info("分期处理失败{}",orderImportBaseInfo.getOrder_orgid());
                throw new MessageException("分期处理");
            }
        }
        //已付款余处
        BigDecimal yifu_mingxi = (shoufu==null||shoufu.compareTo(BigDecimal.ZERO)<=0)?yifu.subtract(dikou):(yifu.subtract(shoufu));
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
                redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"分期处理失败"+orderImportBaseInfo.getOrder_orgid());
                logger.info("分期处理失败{}",orderImportBaseInfo.getOrder_orgid());
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
                FKFQ_data =  FKFQ_data = StageUtil.stageOrder(daifu,
                        oPayment.getDownPaymentCount().intValue(),
                        oPayment.getDownPaymentDate(), c.get(Calendar.DAY_OF_MONTH));
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
                        redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"分期处理失败"+orderImportBaseInfo.getOrder_orgid());
                        logger.info("分期处理失败{}",orderImportBaseInfo.getOrder_orgid());
                        throw new MessageException("分期处理");
                    }
                }
            }
        }
        //解析商品物流信息
        AgentResult suborder = orderImportService.pareseOrderImportSubOrderInfo(orderImportBaseInfo,orderFormVo,oPayment,User);
        if (!suborder.isOK()) {
            List<OrderImportGoodsInfo> orderImportGoodsInfos = orderImportBaseInfo.getOrderImportGoodsInfos();
            for (OrderImportGoodsInfo orderImportGoodsInfo : orderImportGoodsInfos) {
                String importId = orderImportGoodsInfo.getImportId();
                ImportAgent orderImportGoodsInfo_importAgent = importAgentMapper.selectByPrimaryKey(importId);
                orderImportGoodsInfo_importAgent.setDealstatus(Status.STATUS_3.status);
                orderImportGoodsInfo_importAgent.setDealmsg(suborder.getMsg());
                orderImportGoodsInfo_importAgent.setDealTime(new Date());
                int updateSuborder = importAgentMapper.updateByPrimaryKeySelective(orderImportGoodsInfo_importAgent);
                if(updateSuborder != 1){
                    redisService.rpushList(import_order_imOrderMsg_key + orderImportBaseInfo.getOrder_id(),
                            "更新orderImportGoodsInfo记录失败" + orderImportBaseInfo.getOrder_id() + ":importId" + importId);
                    logger.info("更新orderImportGoodsInfo记录失败" + orderImportBaseInfo.getOrder_id() + ":importId" + importId);
                    throw new MessageException("更新orderImportGoodsInfo记录失败" + orderImportBaseInfo.getOrder_id() + ":importId" + importId);
                }
            }
            logger.info("======处理订单商品解析{}失败{}", orderImportBaseInfo.getOrder_id(), suborder.getMsg());
        }

        return AgentResult.ok();
    }


    /**
     *解析生成订单子订单，子订单活动信息
     * @param orderImportBaseInfo
     * @param order
     * @param oPayment
     * @param User
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public AgentResult pareseOrderImportSubOrderInfo(OrderImportBaseInfo orderImportBaseInfo, OOrder order, OPayment oPayment, String User) throws MessageException {
        List<OrderImportGoodsInfo> OrderImportGoodsInfoList = orderImportBaseInfo.getOrderImportGoodsInfos();
        List<String> listIdes = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        //遍历商品查看有没有重复的商品
        for (OrderImportGoodsInfo orderImportGoodsInfo : OrderImportGoodsInfoList) {
            if(listIdes.contains(orderImportGoodsInfo.getGoodsCode())){
                redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"订单"+orderImportGoodsInfo.getOrder_id()+"的商品"+orderImportGoodsInfo.getGoodsCode()+"重复，订单中不允许重复商品");
                logger.info("订单"+orderImportGoodsInfo.getOrder_id()+"的商品"+orderImportGoodsInfo.getGoodsCode()+"重复，订单中不允许重复商品");
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
                redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"商品信息未找到"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                logger.info("商品信息未找到"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                throw new MessageException("商品信息未找到"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
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
                redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"发货商品大于订货商品"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                logger.info("发货商品大于订货商品"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                throw new MessageException("发货商品大于订货商品"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
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
                redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"采购单添加失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                logger.info("采购单添加失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                throw new MessageException("采购单添加失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
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
                redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"采购单活动添加失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                logger.info("采购单活动添加失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                throw new MessageException("采购单活动添加失败");
            }

            //可没有物流
            if(logicInfos.size()==0){
                continue;
//                redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"物流信息不能为空"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
//                logger.info("物流信息不能为空"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
//                throw new MessageException("物流信息不能为空"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
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
                    redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"收货单添加失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                    logger.info("收货单添加失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
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
                    redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"收货单商品添加失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                    logger.info("收货单商品添加失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                    throw new MessageException("收货单商品添加失败");
                }

                //收货单商品排单
                receiptPlan.setId(idService.genId(TabId.o_receipt_plan));
                receiptPlan.setPlanNum(receiptPlan.getId());
                receiptPlan.setOrderId(order.getId());
                receiptPlan.setReceiptId(receiptOrder.getId());
                receiptPlan.setUserId(User);
                receiptPlan.setProId(oReceiptPro.getId());
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
                    redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"排单信息添加失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                    logger.info("排单信息添加失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
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
                            redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"日期格式支持yyyyMMdd 或者yyyy-MM-dd 或者 yyyy/MM/dd"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                            logger.info("日期格式支持yyyyMMdd 或者yyyy-MM-dd 或者 yyyy/MM/dd"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
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
                    redisService.rpushList(import_order_imOrderMsg_key + orderImportBaseInfo.getOrder_id(),
                            "排单信息添加失败" + orderImportBaseInfo.getOrder_id() + ":" + orderImportGoodsInfo.getGoodsCode());
                    logger.info("排单信息添加失败" + orderImportBaseInfo.getOrder_id() + ":" + orderImportGoodsInfo.getGoodsCode());
                    throw new MessageException("排单信息添加失败");
                }
                //生成sn明细
                List<String> idListArr = oLogisticsService.idList(logicInfo.getSnStart(), logicInfo.getSnEnd(), Integer.valueOf(logicInfo.getSnEndNum()),Integer.valueOf( logicInfo.getSnEndNum()),logistics.getProCom());
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
                        detail.setBusId(order.getBusId());
                        detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                        detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                        detail.setSendStatus(Status.STATUS_1.status);

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
                            redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"添加sn明细失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                            logger.info("添加sn明细失败"+orderImportBaseInfo.getOrder_id()+":"+orderImportGoodsInfo.getGoodsCode());
                            throw new MessageException("添加失败");
                        }
                    }
                }

                String importid =  logicInfo.getImportId();
                ImportAgent logicInfo_importAgent = importAgentMapper.selectByPrimaryKey(importid);
                logicInfo_importAgent.setDealstatus(Status.STATUS_2.status);
                logicInfo_importAgent.setDealmsg("成功");
                if(importAgentMapper.updateByPrimaryKeySelective(logicInfo_importAgent)!=1){
                    redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"更新logicInfo_importAgent记录失败"+orderImportBaseInfo.getOrder_id()+":importid"+importid);
                    logger.info("更新logicInfo_importAgent记录失败"+orderImportBaseInfo.getOrder_id()+":importid"+importid);
                    throw new MessageException("更新logicInfo_importAgent记录失败"+orderImportBaseInfo.getOrder_id()+":importid"+importid);
                }
            }
            String importid = orderImportGoodsInfo.getImportId();
            ImportAgent orderImportGoodsInfo_importAgent = importAgentMapper.selectByPrimaryKey(importid);
            orderImportGoodsInfo_importAgent.setDealstatus(Status.STATUS_2.status);
            orderImportGoodsInfo_importAgent.setDealmsg("成功");
            orderImportGoodsInfo_importAgent.setDealTime(new Date());
            if(importAgentMapper.updateByPrimaryKeySelective(orderImportGoodsInfo_importAgent)!=1){
                redisService.rpushList(import_order_imOrderMsg_key+orderImportBaseInfo.getOrder_id(),"更新orderImportGoodsInfo记录失败"+orderImportBaseInfo.getOrder_id()+":importid"+importid);
                logger.info("更新orderImportGoodsInfo记录失败"+orderImportBaseInfo.getOrder_id()+":importid"+importid);
                throw new MessageException("更新orderImportGoodsInfo记录失败"+orderImportBaseInfo.getOrder_id()+":importid"+importid);
            }
        }

        return AgentResult.ok();
    }


    private String import_order_logic_redis_key="imOrderLogic:";
    private String import_order_imOrderMsg_key="imOrderMsg:";
    /**
     * 物流信息存储到redis
     * @return
     * @throws Exception
     */
    @Override
    public AgentResult pareseOrderLogic(String value) throws MessageException {
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
            orderLogicInfo.loadInfoFromJsonArray(logic_dataContent_jsonArray,logic.getId());
            try {
                //将发货物流放到redis里
                logger.info("======物流信存储到reids[{}][{}]",orderLogicInfo.getOrder_id(),orderLogicInfo.getGoodsCode());
                long count = redisService.rpushList(import_order_logic_redis_key+orderLogicInfo.getOrder_id(),logic_dataContent_jsonArray.toJSONString());
                logger.info("======物流信存储到reids[{}][{}][{}]条",orderLogicInfo.getOrder_id(),orderLogicInfo.getGoodsCode(),count);
                orderlogiccount++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return AgentResult.ok(orderlogiccount);
    }

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    @Override
    public AgentResult pareseReturn(ImportAgent importAgent,String User) throws MessageException {
        //退货单信息
        String returnOrderString = importAgent.getDatacontent();
        logger.info("解析退货单[{}]",returnOrderString);
        JSONArray returnOrderJsnoArray = JSONArray.parseArray(returnOrderString);
        OrderImportReturnInfo orderImportReturnInfo = new OrderImportReturnInfo();
        orderImportReturnInfo.loadInfoFromJsonArray(returnOrderJsnoArray,importAgent.getId());

        //代理商信息
        AgentExample agentExample = new AgentExample();
        agentExample.or().andAgUniqNumEqualTo(orderImportReturnInfo.getAgentUniqId()).andStatusEqualTo(Status.STATUS_1.status).andAgStatusEqualTo(AgStatus.Approved.name());
        List<Agent>  agentList =  agentMapper.selectByExample(agentExample);
        if(agentList.size()!=1){
            logger.info("解析退货单["+orderImportReturnInfo.getReturnOrderId()+"]代理商唯一编号查询代理商未找到["+orderImportReturnInfo.getAgentUniqId()+"]");
            throw new MessageException("代理商信息未找到");
        }
        Agent agent = agentList.get(0);

        //退货单物流信息 待处理
        ImportAgentExample example = new ImportAgentExample();
        example.or().andStatusEqualTo(Status.STATUS_1.status).andDealstatusEqualTo(Status.STATUS_0.status).andDataidEqualTo(orderImportReturnInfo.getReturnOrderId()).andDatatypeEqualTo(AgImportType.ORLOGI.code);
        List<ImportAgent> importAgentsLogics =  importAgentMapper.selectByExampleWithBLOBs(example);
        if(importAgentsLogics.size()==0){
            logger.info("退货单物流为空["+orderImportReturnInfo.getReturnOrderId()+"]");
            throw new MessageException("退货单物流为空["+orderImportReturnInfo.getReturnOrderId()+"]");
        }
        logger.info("退货单物流信息["+importAgentsLogics.size()+"]条");

        //每行ID
        String orderId_productId = "";
        Map<String, Object> newLine_detail = null;
        //所有退货商品总价格
        BigDecimal totalAmt = BigDecimal.ZERO;
        //订单退货单关系
        Set<String> relSet = new HashSet<>();
        //订单平台滤
        HashSet<Object> set = new HashSet<>();
        //根据 "订单编号_商品编号" 作为唯一ID，统计每行退货信息
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();


        //=============================================================退货单物流解析
        List<OrderImportReturnLogincInfo> orderImportReturnLogincInfos = new ArrayList<>();
        logger.info("退货单物流信息开始解析[{}][{}]条",orderImportReturnInfo.getReturnOrderId(),importAgentsLogics.size());
        for (ImportAgent importAgentsLogic : importAgentsLogics) {
            OrderImportReturnLogincInfo orderImportReturnLogincInfo = new OrderImportReturnLogincInfo();
            orderImportReturnLogincInfo.loadInfoFromJsonArray(JSONArray.parseArray(importAgentsLogic.getDatacontent()),importAgentsLogic.getId());
            orderImportReturnLogincInfos.add(orderImportReturnLogincInfo);
            importAgentsLogic.setDealstatus(Status.STATUS_1.status);
            importAgentsLogic.setDealTime(new Date());
            if(importAgentMapper.updateByPrimaryKeySelective(importAgentsLogic)!=1){
              logger.info("物流信息更新处理中失败，importAgentsLogic.getId()["+importAgentsLogic.getId()+"]");
              throw new MessageException("物流信息更新处理中失败，importAgentsLogic.getId()["+importAgentsLogic.getId()+"]");
            }
            //fixme 根据厂商判断sn生成方式
            List<String> idlist =  oLogisticsService.idList(
                    orderImportReturnLogincInfo.getSnStart(),
                    orderImportReturnLogincInfo.getSnEnd(),
                    Integer.valueOf(orderImportReturnLogincInfo.getSnStartNum()),
                    Integer.valueOf(orderImportReturnLogincInfo.getSnEndNum()),
                    orderImportReturnLogincInfo.getReturnCS());
            logger.info("退货单物流信息{}:{}:{}解析后数量{}",
                    orderImportReturnLogincInfo.getReturnOrderId(),
                    orderImportReturnLogincInfo.getSnStart(),
                    orderImportReturnLogincInfo.getSnEnd(),
                    idlist.size());
            if(idlist.size()==0){
                throw new MessageException(
                        orderImportReturnLogincInfo.getReturnOrderId()
                                +":"
                                +orderImportReturnLogincInfo.getSnStart()
                                +":"
                                +orderImportReturnLogincInfo.getSnEnd()+"解析后数量为"+idlist.size());
            }

            //fixme 退货导入是否加入退货数量进行校验
            logger.info("退货单物流信息{}:{}:{}解析后数量{}开始校验",
                    orderImportReturnLogincInfo.getReturnOrderId(),
                    orderImportReturnLogincInfo.getSnStart(),
                    orderImportReturnLogincInfo.getSnEnd(),
                    idlist.size());

            //校验sn的是否存在发货状态
            for (String s : idlist) {
                //退货单sn必须是发货有效的存在
                OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                oLogisticsDetailExample.or().andSnNumEqualTo(s)
                        .andAgentIdNotEqualTo(agent.getId())
                        .andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code)
                        .andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                List<OLogisticsDetail>  oLogisticsDetaillist = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                if(oLogisticsDetaillist.size()==0){
                    logger.info("退货单物流信息{}:{}:{}解析后数量{} 数据库中未找到代理商不为{}的sn{}",
                            orderImportReturnLogincInfo.getReturnOrderId(),
                            orderImportReturnLogincInfo.getSnStart(),
                            orderImportReturnLogincInfo.getSnEnd(),
                            idlist.size(),
                            agent.getId(),s);

                    throw new MessageException(
                            orderImportReturnLogincInfo.getReturnOrderId()+":"+orderImportReturnLogincInfo.getSnStart()+":"+orderImportReturnLogincInfo.getSnEnd()+"数据库中未找到代理商不为"+agent.getAgName()+"的sn码："+s);
                }
             }

            //解析sn并统计退货单明细
            for (String s : idlist) {
                Map<String, Object>  map = oLogisticsMapper.getOrderAndLogisticsBySn(s, agent.getId());
                if (map==null){
                    continue;
                }

                String norderId = (String) map.get("ORDERID");//订单id
                OOrder order = orderService.getById(norderId);
                set.add(order.getOrderPlatform());
                if (set.size()>1){
                    logger.info("退货单物流信息{}:{}:{}解析后数量{} 所发SN码不属于同一个平台",
                            orderImportReturnLogincInfo.getReturnOrderId(),
                            orderImportReturnLogincInfo.getSnStart(),
                            orderImportReturnLogincInfo.getSnEnd(),
                            idlist.size(),set.toString());
                    throw new MessageException("所发SN码不属于同一个平台");
                }
                List<AgentBusInfo> agentBusInfos = agentBusinfoService.selectExistsById(order.getBusId());
                if(agentBusInfos.size()==0){
                    logger.info("退货单物流信息{}:{}:{}解析后数量{} SN不在平台下",
                            orderImportReturnLogincInfo.getReturnOrderId(),
                            orderImportReturnLogincInfo.getSnStart(),
                            orderImportReturnLogincInfo.getSnEnd(),
                            idlist.size());
                    throw new MessageException("SN不在平台下");
                }
                String ordernum = (String) map.get("ORDERNUM");
                String proId = (String) map.get("PROID");
                String proName = (String) map.get("PRONAME");
                String protype = (String) map.get("PROTYPE");
                BigDecimal proprice = (BigDecimal) map.get("PROPRICE");
                String proCom = (String) map.get("PROCOM");
                String proModel = (String) map.get("PROMODEL");
                String planId = (String) map.get("PLANID");
                String receiptId = (String) map.get("RECEIPTID");
                totalAmt = totalAmt.add(proprice);
                // 新一个 "订单_商品"
                if (!orderId_productId.equals(norderId + "_" + proId)) {
                    orderId_productId = norderId + "_" + proId;
                    if (newLine_detail != null) {
                        retList.add(newLine_detail);
                    }
                    //生成一个订单中一个商品信息
                    newLine_detail = new HashMap<>();
                    newLine_detail.put("id", orderId_productId);
                    newLine_detail.put("startSn", s);
                    newLine_detail.put("endSn", s);
                    newLine_detail.put("orderId", norderId);
                    newLine_detail.put("proId", proId);
                    newLine_detail.put("proName", proName);
                    newLine_detail.put("proPrice", proprice);
                    newLine_detail.put("proType", protype);
                    newLine_detail.put("count", 1);
                    newLine_detail.put("totalPrice", proprice);
                    newLine_detail.put("proCom", proCom);
                    newLine_detail.put("proModel", proModel);
                    newLine_detail.put("planId", planId);
                    newLine_detail.put("receiptId", receiptId);
                    newLine_detail.put("ordernum", ordernum);
                    //sn放入redis中
                    redisService.rpushList("returnorder:orderprosnlist:"+orderId_productId,s);
                } else {
                    //还是同一个 "订单_商品",  累加一个订单中一个商品的数量，总价
                    newLine_detail.put("endSn", s);
                    newLine_detail.put("count", (int) newLine_detail.get("count") + 1);
                    newLine_detail.put("totalPrice", ((BigDecimal) newLine_detail.get("totalPrice")).add(proprice));
                    redisService.rpushList("returnorder:orderprosnlist:"+orderId_productId,s);
                }
                    //查询发货有效的自己的sn码更新为退货历史
                OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                oLogisticsDetailExample.or().andSnNumEqualTo(s)
                        .andAgentIdEqualTo(agent.getId())
                        .andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code)
                        .andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                List<OLogisticsDetail>  oLogisticsDetaillist = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                if(oLogisticsDetaillist.size()>0){
                    for (OLogisticsDetail detail : oLogisticsDetaillist) {
                        detail.setStatus(OLogisticsDetailStatus.STATUS_TH.code);
                        detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_HIS.code);
                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                    }
                }
            }
            //最后一个放到list中
            retList.add(newLine_detail);
        }



        Calendar c = Calendar.getInstance();
        //=============================================================退货单
        OReturnOrder oReturnOrder = new OReturnOrder();
        oReturnOrder.setId(orderImportReturnInfo.getReturnOrderId());
        oReturnOrder.setAgentId(agent.getId());
        oReturnOrder.setApplyRemark("(老订单)");
        oReturnOrder.setRetInvoice(Status.STATUS_0.status);
        oReturnOrder.setRetReceipt(Status.STATUS_0.status);
        oReturnOrder.setReturnAmo(new BigDecimal(orderImportReturnInfo.getReturnPayAmt()));
        oReturnOrder.setGoodsReturnAmo(new BigDecimal(orderImportReturnInfo.getReturnPayAmt()));
        oReturnOrder.setCutAmo(new BigDecimal(0));
        oReturnOrder.setRelReturnAmo(new BigDecimal(orderImportReturnInfo.getHaveReturnPayAmt()));
        oReturnOrder.setTakeOutAmo(new BigDecimal(0));
        oReturnOrder.setReturnAddress("(老订单)");
        oReturnOrder.setRetTime(c.getTime());
        oReturnOrder.setRemark("(老订单)");
        oReturnOrder.setBatchCode(c.getTime().getTime()+"");
        oReturnOrder.setcTime(c.getTime());
        oReturnOrder.setuTime(c.getTime());
        oReturnOrder.setuUser(User);
        oReturnOrder.setcUser(User);
        oReturnOrder.setStatus(Status.STATUS_1.status);
        oReturnOrder.setVersion(Status.STATUS_0.status);
        oReturnOrder.setRefundtime(c.getTime());
        oReturnOrder.setRefundpeople(User);
        if(oReturnOrderMapper.insertSelective(oReturnOrder)!=1){
            throw new MessageException("退货处理失败");
        }
        //=============================================================生成退货单退货明细，退货单订单关系表
        for (Map<String, Object> stringObjectMap : retList) {
            OReturnOrderDetail returnOrderDetail = new OReturnOrderDetail();
            returnOrderDetail.setId(idService.genId(TabId.o_return_order_detail));
            returnOrderDetail.setReturnId(oReturnOrder.getId());
            returnOrderDetail.setAgentId(agent.getId());
            returnOrderDetail.setOrderId(stringObjectMap.get("orderId")+"");
            returnOrderDetail.setSubOrderId( stringObjectMap.get("receiptId")+"");
            returnOrderDetail.setProId((String) stringObjectMap.get("proId")+"");
            returnOrderDetail.setProName((String) stringObjectMap.get("proName")+"");
            returnOrderDetail.setProType((String) stringObjectMap.get("proType")+"");
            returnOrderDetail.setProCom((String) stringObjectMap.get("proCom")+"");
            returnOrderDetail.setProPrice(MapUtil.getBigDecimal(stringObjectMap, "proPrice"));
            returnOrderDetail.setModel((String) stringObjectMap.get("proModel"));
            returnOrderDetail.setBeginSn(stringObjectMap.get("startSn")+"");
            returnOrderDetail.setEndSn(stringObjectMap.get("endSn")+"");
            returnOrderDetail.setOrderPrice(MapUtil.getBigDecimal(stringObjectMap, "proPrice"));
            returnOrderDetail.setReturnCount(MapUtil.getBigDecimal(stringObjectMap, "count"));
            returnOrderDetail.setReturnAmt(MapUtil.getBigDecimal(stringObjectMap, "totalPrice"));
            returnOrderDetail.setReturnTime(c.getTime());
            returnOrderDetail.setcTime(c.getTime());
            returnOrderDetail.setcUser(User);
            returnOrderDetail.setStatus(Status.STATUS_1.status);
            returnOrderDetail.setVersion(Status.STATUS_0.status);
            returnOrderDetail.setBatchNo(oReturnOrder.getBatchCode());
            if(oReturnOrderDetailMapper.insertSelective(returnOrderDetail)!=1){
                throw new MessageException("退货明细处理失败");
            }

            //需要更新退货明细的sn
            List<String> listforupdate_planreturnorderdetail = redisService.lrange("returnorder:orderprosnlist:"+stringObjectMap.get("orderId")+"_"+stringObjectMap.get("proId"),0,-1);
            for (String s : listforupdate_planreturnorderdetail) {
                OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                oLogisticsDetailExample.or().andSnNumEqualTo(s)
                        .andAgentIdNotEqualTo(agent.getId())
                        .andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code)
                        .andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                List<OLogisticsDetail>  oLogisticsDetaillist = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                if(oLogisticsDetaillist.size()!=0){
                    String logicid = oLogisticsDetaillist.get(0).getLogisticsId();
                    OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(logicid);
                    ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(logistics.getReceiptPlanId());
                    receiptPlan.setReturnOrderDetailId(returnOrderDetail.getId());
                    if(receiptPlanMapper.updateByPrimaryKeySelective(receiptPlan)!=1){
                        throw new MessageException("退货更新排单表退货子订单id失败setReturnOrderDetailId");
                    }
                }
            }
            redisService.delete("returnorder:orderprosnlist:"+stringObjectMap.get("orderId")+"_"+stringObjectMap.get("proId"));
            //订单退货单关系
            String setstr = returnOrderDetail.getOrderId() + "_" + returnOrderDetail.getSubOrderId() + "_" + returnOrderDetail.getReturnId();
            relSet.add(setstr);
        }
        //生成退货和订单关系
        for (String realId : relSet) {
            try {
                String[] ids = realId.split("_");
                OReturnOrderRel returnOrderRel = new OReturnOrderRel();
                returnOrderRel.setId(idService.genId(TabId.o_return_order_rel));
                returnOrderRel.setOrderId(ids[0]);
                returnOrderRel.setSubOrderId(ids[1]);
                returnOrderRel.setReturnOrderId(ids[2]);
                returnOrderRel.setcTime(new Date());
                returnOrderRel.setcUser(User);
                if(1!=oReturnOrderRelMapper.insertSelective(returnOrderRel)){
                    throw new MessageException("生成退货关系失败");
                }
            } catch (Exception e) {
                throw new MessageException("生成退货关系失败");
            }
        }

        //生成负数的分期欠款抵扣分润
        if(!orderImportReturnInfo.getReturnPayAmt().equals(orderImportReturnInfo.getHaveReturnPayAmt())){
            BigDecimal fqreturn = new BigDecimal(orderImportReturnInfo.getReturnPayAmt()).subtract(new BigDecimal(orderImportReturnInfo.getHaveReturnPayAmt()));
            BigDecimal fqcount = new BigDecimal(orderImportReturnInfo.getReturnFqCount());
            Date date = DateUtil.format(orderImportReturnInfo.getReturnFqDate(),"yyyy-MM");
            List<Map> FKFQ_data = null;
            switch (orderImportReturnInfo.getReturnPayMethod()){
                case "FRFQ" :
                   FKFQ_data = StageUtil.stageOrder(fqreturn,fqcount.intValue(),date, 1);
                    for (Map datum : FKFQ_data) {
                        OPaymentDetail record = new OPaymentDetail();
                        record.setId(idService.genId(TabId.o_payment_detail));
                        record.setBatchCode(oReturnOrder.getBatchCode());
                        record.setPaymentId(oReturnOrder.getId());//添加退货单ID
                        record.setPaymentType(PamentIdType.ORDER_IMR.code);//添加退货导入类型
                        record.setOrderId(oReturnOrder.getId());//添加退货单ID
                        record.setPayType(PaymentType.DKFQ.code);//分润分日期
                        record.setPayAmount(((BigDecimal) datum.get("item")).negate());//分润分期金额为负数
                        record.setRealPayAmount(BigDecimal.ZERO);
                        record.setPlanPayTime((Date) datum.get("date"));
                        record.setPlanNum((BigDecimal) datum.get("count"));
                        record.setAgentId(oReturnOrder.getAgentId());
                        record.setPaymentStatus(PaymentStatus.DF.code);
                        record.setcUser(oReturnOrder.getcUser());
                        record.setcDate(c.getTime());
                        record.setStatus(Status.STATUS_1.status);
                        record.setVersion(Status.STATUS_1.status);
                        if (1 != oPaymentDetailMapper.insert(record)) {
                            throw new MessageException("分期处理失败");
                        }
                    }
                break;
                case "分润分期" :
                    FKFQ_data = StageUtil.stageOrder(fqreturn,fqcount.intValue(),date, 1);
                    for (Map datum : FKFQ_data) {
                        OPaymentDetail record = new OPaymentDetail();
                        record.setId(idService.genId(TabId.o_payment_detail));
                        record.setBatchCode(oReturnOrder.getBatchCode());
                        record.setPaymentId(oReturnOrder.getId());//添加退货单ID
                        record.setPaymentType(PamentIdType.ORDER_IMR.code);//添加退货导入类型
                        record.setOrderId(oReturnOrder.getId());//添加退货单ID
                        record.setPayType(PaymentType.DKFQ.code);//分润分日期
                        record.setPayAmount(((BigDecimal) datum.get("item")).negate());//分润分期金额为负数
                        record.setRealPayAmount(BigDecimal.ZERO);
                        record.setPlanPayTime((Date) datum.get("date"));
                        record.setPlanNum((BigDecimal) datum.get("count"));
                        record.setAgentId(oReturnOrder.getAgentId());
                        record.setPaymentStatus(PaymentStatus.DF.code);
                        record.setcUser(oReturnOrder.getcUser());
                        record.setcDate(c.getTime());
                        record.setStatus(Status.STATUS_1.status);
                        record.setVersion(Status.STATUS_1.status);
                        if (1 != oPaymentDetailMapper.insert(record)) {
                            throw new MessageException("分期处理失败");
                        }
                    }
                    break;
                default:
                    FKFQ_data = StageUtil.stageOrder(fqreturn,fqcount.intValue(),date, 1);
                    for (Map datum : FKFQ_data) {
                        OPaymentDetail record = new OPaymentDetail();
                        record.setId(idService.genId(TabId.o_payment_detail));
                        record.setBatchCode(oReturnOrder.getBatchCode());
                        record.setPaymentId(oReturnOrder.getId());//添加退货单ID
                        record.setPaymentType(PamentIdType.ORDER_IMR.code);//添加退货导入类型
                        record.setOrderId(oReturnOrder.getId());//添加退货单ID
                        record.setPayType(PaymentType.DKFQ.code);//分润分日期
                        record.setPayAmount(((BigDecimal) datum.get("item")).negate());//分润分期金额为负数
                        record.setRealPayAmount(BigDecimal.ZERO);
                        record.setPlanPayTime((Date) datum.get("date"));
                        record.setPlanNum((BigDecimal) datum.get("count"));
                        record.setAgentId(oReturnOrder.getAgentId());
                        record.setPaymentStatus(PaymentStatus.DF.code);
                        record.setcUser(oReturnOrder.getcUser());
                        record.setcDate(c.getTime());
                        record.setStatus(Status.STATUS_1.status);
                        record.setVersion(Status.STATUS_1.status);
                        if (1 != oPaymentDetailMapper.insert(record)) {
                            throw new MessageException("分期处理失败");
                        }
                    }
                break;
            }
        }
        //更新为处理成功
        for (OrderImportReturnLogincInfo orderImportReturnLogincInfo: orderImportReturnLogincInfos) {
            ImportAgent importAgent1 = importAgentMapper.selectByPrimaryKey(orderImportReturnLogincInfo.getImportId());
            importAgent1.setDealstatus(Status.STATUS_2.status);
            importAgent1.setDealmsg("处理成功");
            importAgent1.setDealTime(new Date());
            if (importAgentMapper.updateByPrimaryKeySelective(importAgent1) != 1) {
                logger.info("物流信息更新处理中失败，importAgentsLogic.getId()[" + importAgent1.getId() + "]");
                throw new MessageException("物流信息更新处理中失败，importAgentsLogic.getId()[" + importAgent1.getId() + "]");
            }
        }

        return AgentResult.ok();
    }



    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    @Override
    public AgentResult deleteFailImportAgentReturn(ImportAgent importAgent,String user)throws Exception{
        String reutrnorderid =  importAgent.getDataid();
        OReturnOrder oReturnOrder = oReturnOrderMapper.selectByPrimaryKey(reutrnorderid);
        if(oReturnOrder==null)return AgentResult.ok();
        //删除退货单关系
        OReturnOrderRelExample oReturnOrderRelExample = new OReturnOrderRelExample();
        oReturnOrderRelExample.or().andReturnOrderIdEqualTo(reutrnorderid);
        oReturnOrderRelMapper.deleteByExample(oReturnOrderRelExample);

        //更新sn信息
        OReturnOrderDetailExample desn = new OReturnOrderDetailExample();
        desn.or().andReturnIdEqualTo(reutrnorderid);
        List<OReturnOrderDetail>  list = oReturnOrderDetailMapper.selectByExample(desn);
        for (OReturnOrderDetail oReturnOrderDetail : list) {
            OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
            oLogisticsDetailExample.or()
                    .andAgentIdEqualTo(oReturnOrderDetail.getAgentId())
                    .andSnNumLessThanOrEqualTo(oReturnOrderDetail.getBeginSn())
                    .andSnNumGreaterThanOrEqualTo(oReturnOrderDetail.getEndSn())
                    .andStatusEqualTo(OLogisticsDetailStatus.STATUS_TH.code)
                    .andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_HIS.code);
            List<OLogisticsDetail> details = oLogisticsDetailMapper.selectByExample(oLogisticsDetailExample);
            for (OLogisticsDetail detail : details) {
                detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                detail.setSendStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
            }
        }

        //删除退货单明细
        OReturnOrderDetailExample oReturnOrderDetailExample = new OReturnOrderDetailExample();
        oReturnOrderDetailExample.or().andReturnIdEqualTo(reutrnorderid);
        oReturnOrderDetailMapper.deleteByExample(oReturnOrderDetailExample);
        OPaymentDetailExample oPaymentDetailExample  = new OPaymentDetailExample();
        //删除退货单退款明细
        oPaymentDetailExample.or().andPaymentIdEqualTo(reutrnorderid).andPaymentTypeEqualTo(PamentIdType.ORDER_IMR.code);
        oPaymentDetailMapper.deleteByExample(oPaymentDetailExample);
        //删除退货单
        OReturnOrderExample oReturnOrderExample = new OReturnOrderExample();
        oReturnOrderExample.or().andIdEqualTo(reutrnorderid);
        oReturnOrderMapper.deleteByExample(oReturnOrderExample);

        return AgentResult.ok();

    }

    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    @Override
    public AgentResult deleteFailImportAgentOrder(ImportAgent importAgent,String user)throws Exception{
        OOrderExample query  = new OOrderExample();
        query.or().andONumEqualTo(importAgent.getDataid());
        List<OOrder> oOrders = oOrderMapper.selectByExample(query);
        if(oOrders.size()>1){
            return AgentResult.fail("订单查出有多条");
        }
        if(oOrders.size()==0){
            return AgentResult.ok();
        }
        OOrder order = oOrders.get(0);

        //删除活动
        OSubOrderExample OSubOrderExample_query = new OSubOrderExample();
        OSubOrderExample_query.or().andOrderIdEqualTo(order.getId());
        List<OSubOrder> oSubOrders =  oSubOrderMapper.selectByExample(OSubOrderExample_query);
        for (OSubOrder oSubOrder : oSubOrders) {
            OSubOrderActivityExample oOSubOrderActivityExample = new OSubOrderActivityExample();
            oOSubOrderActivityExample.or().andSubOrderIdEqualTo(oSubOrder.getId());
            oSubOrderActivityMapper.deleteByExample(oOSubOrderActivityExample);
        }

        //删除子订单
        OSubOrderExample oSubOrderExample = new OSubOrderExample();
        oSubOrderExample.or().andOrderIdEqualTo(order.getId());
        oSubOrderMapper.deleteByExample(oSubOrderExample);

        //付款单
        OPaymentExample oPaymentExample = new OPaymentExample();
        oPaymentExample.or().andOrderIdEqualTo(order.getId());
        oPaymentMapper.deleteByExample(oPaymentExample);
        //付款单明细
        OPaymentDetailExample oPaymentDetailExample = new OPaymentDetailExample();
        oPaymentDetailExample.or().andOrderIdEqualTo(order.getId());
        oPaymentDetailMapper.deleteByExample(oPaymentDetailExample);

        //删除收货单
        OReceiptOrderExample oReceiptOrderExample =new OReceiptOrderExample();
        oReceiptOrderExample.or().andOrderIdEqualTo(order.getId());
        oReceiptOrderMapper.deleteByExample(oReceiptOrderExample);

        //删除收货单商品
        OReceiptProExample oReceiptProExample = new OReceiptProExample();
        oReceiptProExample.or().andOrderidEqualTo(order.getId());
        oReceiptProMapper.deleteByExample(oReceiptProExample);

        //删除排单
        ReceiptPlanExample receiptPlanExample = new ReceiptPlanExample();
        receiptPlanExample.or().andOrderIdEqualTo(order.getId());
        receiptPlanMapper.deleteByExample(receiptPlanExample);

        //删除物流
        OLogisticsExample oLogisticsExample = new OLogisticsExample();
        oLogisticsExample.or().andOrderIdEqualTo(order.getId());
        oLogisticsMapper.deleteByExample(oLogisticsExample);

        //删除物流明细
        OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
        oLogisticsDetailExample.or().andOrderIdEqualTo(order.getId());
        oLogisticsDetailMapper.deleteByExample(oLogisticsDetailExample);

        //删除订单
        OOrderExample oOrderExample = new OOrderExample();
        oOrderExample.or().andIdEqualTo(order.getId());
        oOrderMapper.deleteByExample(oOrderExample);

        return AgentResult.ok();
    }

}
