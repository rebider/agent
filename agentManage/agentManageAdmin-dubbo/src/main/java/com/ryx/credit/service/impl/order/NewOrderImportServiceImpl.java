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
 * Created by lhl on 2019/3/5.
 * 无SN订单
 */
@Service("newOrderImportService")
public class NewOrderImportServiceImpl implements NewOrderImportService{

    private Logger logger = LoggerFactory.getLogger(NewOrderImportServiceImpl.class);

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
    private NewOrderImportService newOrderImportService;
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
    public List<String> newAddOrderInfoList(List<List<Object>> data, String dataType, String user, String batch) throws Exception {
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


    @Override
    public ResultVO newPareseOrderEnter(String user) {
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
                        logger.info("======处理订单解析{}", importAgent.getDataid());
                        importAgent.setDealstatus(Status.STATUS_1.status);
                        importAgent.setDealmsg("处理中");
                        importAgent.setDealTime(new Date());
                        importAgentMapper.updateByPrimaryKeySelective(importAgent);
                        if(newOrderImportService.newDeleteFailImportAgentOrder(importAgent, user).isOK()) {
                            AgentResult agentResult = newOrderImportService.newPareseOrder(importAgent, user);
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
                        logger.info("======处理订单解析{}完成{}", importAgent.getDataid(), e.getMsg());
                    }catch (Exception e) {
                        e.printStackTrace();
                        importAgent = importAgentMapper.selectByPrimaryKey(importAgent.getId());
                        importAgent.setDealstatus(Status.STATUS_3.status);
                        importAgent.setDealmsg(e.getLocalizedMessage().substring(0,60));
                        importAgent.setDealTime(new Date());
                        importAgentMapper.updateByPrimaryKeySelective(importAgent);
                        logger.info("======处理订单解析{}完成{}", importAgent.getDataid(), e.getLocalizedMessage());
                    }
                }
            }
        });
        return new ResultVO(ResultVO.SUCCESS,"任务处理中~");
    }


    @Override
    public ResultVO newPareseReturnOrderEnter(String user) {
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
                        importAgentMapper.updateByPrimaryKeySelective(importAgent);
                        if(newOrderImportService.newDeleteFailImportAgentReturn(importAgent, user).isOK()){
                            AgentResult agentResult = newOrderImportService.newPareseReturn(importAgent, user);
                            importAgent = importAgentMapper.selectByPrimaryKey(importAgent.getId());
                            importAgent.setDealstatus(Status.STATUS_2.status);
                            importAgent.setDealmsg(agentResult.getMsg());
                            importAgentMapper.updateByPrimaryKeySelective(importAgent);
                            logger.info("======处理订单解析{}完成{}", importAgent.getDataid(), agentResult.getMsg());
                        }
                    } catch (MessageException e) {
                        e.printStackTrace();
                        importAgent = importAgentMapper.selectByPrimaryKey(importAgent.getId());
                        importAgent.setDealstatus(Status.STATUS_3.status);
                        importAgent.setDealmsg(e.getMsg());
                        importAgentMapper.updateByPrimaryKeySelective(importAgent);
                        logger.info("======处理订单解析{}完成{}", importAgent.getDataid(), e.getMsg());
                    }catch (Exception e) {
                        e.printStackTrace();
                        importAgent = importAgentMapper.selectByPrimaryKey(importAgent.getId());
                        importAgent.setDealstatus(Status.STATUS_3.status);
                        importAgent.setDealmsg(e.getLocalizedMessage());
                        importAgentMapper.updateByPrimaryKeySelective(importAgent);
                        logger.info("======处理订单解析{}完成{}", importAgent.getDataid(), e.getLocalizedMessage());
                    }
                }
            }
        });
        return new ResultVO(ResultVO.SUCCESS,"任务处理中");
    }


    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    @Override
    public AgentResult newPareseOrder(ImportAgent importAgent,String User) throws MessageException {
        try {
            //解析订单信息
            String OBASE_dataContent = importAgent.getDatacontent();
            JSONArray OBASE_dataContent_jsonArray = JSONArray.parseArray(OBASE_dataContent);
            NewOrderImportBaseInfo newOrderImportBaseInfo = new NewOrderImportBaseInfo();
            newOrderImportBaseInfo.loadInfoFromJsonArray(OBASE_dataContent_jsonArray, importAgent.getId());
            //查询商品信息
            ImportAgentExample goods_exa = new ImportAgentExample();
            goods_exa.or().andDealstatusEqualTo(Status.STATUS_0.status)
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andDataidEqualTo(newOrderImportBaseInfo.getOrder_id())
                    .andDatatypeEqualTo(AgImportType.OGOODS.code);
            List<ImportAgent> goods_list = importAgentMapper.selectByExampleWithBLOBs(goods_exa);
            List<NewOrderImportGoodsInfo> newOrderImportGoodsInfos = new ArrayList<>();
            for (ImportAgent goodss : goods_list) {
                //解析订单商品信息
                String OGOODS_dataContent = goodss.getDatacontent();
                JSONArray OGOODS_dataContent_jsonArray = JSONArray.parseArray(OGOODS_dataContent);
                NewOrderImportGoodsInfo newOrderImportGoodsInfo = new NewOrderImportGoodsInfo();
                newOrderImportGoodsInfo.loadInfoFromJsonArray(OGOODS_dataContent_jsonArray,goodss.getId());
                //设置商品列表
                newOrderImportGoodsInfos.add(newOrderImportGoodsInfo);
            }
            if(newOrderImportGoodsInfos.size()==0){
                redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                        "商品信息记录为空[" + newOrderImportBaseInfo.getOrder_id() + "]");
                throw new MessageException("商品信息记录为空[" + newOrderImportBaseInfo.getOrder_id() + "]");
            }
            newOrderImportBaseInfo.setNewOrderImportGoodsInfos(newOrderImportGoodsInfos);

            AgentResult agentImport = AgentResult.ok();
            try {
                agentImport = newOrderImportService.newPareseOrderImportBaseInfo(newOrderImportBaseInfo,User);
            } catch (MessageException e) {
                e.printStackTrace();
                agentImport = AgentResult.fail(e.getMsg());
            }
            //订单解析失败 订单商品记录也更新为失败
            if (!agentImport.isOK()) {
                List<NewOrderImportGoodsInfo> newOrderImportGoodsInfoList = newOrderImportBaseInfo.getNewOrderImportGoodsInfos();
                for (NewOrderImportGoodsInfo newOrderImportGoodsInfo : newOrderImportGoodsInfoList) {
                    String importId = newOrderImportGoodsInfo.getImportId();
                    ImportAgent newOrderImportGoodsInfo_importAgent = importAgentMapper.selectByPrimaryKey(importId);
                    newOrderImportGoodsInfo_importAgent.setDealstatus(Status.STATUS_3.status);
                    newOrderImportGoodsInfo_importAgent.setDealmsg(agentImport.getMsg());
                    newOrderImportGoodsInfo_importAgent.setDealTime(new Date());
                    int updateAgentImport = importAgentMapper.updateByPrimaryKeySelective(newOrderImportGoodsInfo_importAgent);
                    if (updateAgentImport != 1) {
                        redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                                "更新newOrderImportBaseInfo记录失败" + newOrderImportBaseInfo.getOrder_id() + ":importId" + importId);
                        logger.info("更新newOrderImportBaseInfo记录失败" + newOrderImportBaseInfo.getOrder_id() + ":importId" + importId);
                        throw new MessageException("更新newOrderImportBaseInfo记录失败" + newOrderImportBaseInfo.getOrder_id() + ":importId" + importId);
                    }
                }
                logger.info("======处理订单解析{}失败{}", newOrderImportBaseInfo.getOrder_id(), agentImport.getMsg());
            }
            return agentImport;
        } catch (Exception e) {
            e.printStackTrace();
            redisService.rpushList(import_order_imOrderMsg_key+importAgent.getDataid(),e.getLocalizedMessage());
            logger.error("解析导入订单失败", e);
            throw new MessageException(e.getLocalizedMessage());
        } finally {
            redisService.delete(import_order_logic_redis_key+importAgent.getDataid());
        }
    }


    /**
     * 解析生成订单及付款单信息
     * @param newOrderImportBaseInfo
     * @param User
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public AgentResult newPareseOrderImportBaseInfo(NewOrderImportBaseInfo newOrderImportBaseInfo, String User) throws MessageException {
        //查询代理商信息 查询代理商业务平台信息
        AgentBusInfoExample example = new AgentBusInfoExample();
        example.or().andBusNumEqualTo(newOrderImportBaseInfo.getOrder_orgid()).andStatusEqualTo(Status.STATUS_1.status);
        List<AgentBusInfo> agentBusInfoList = agentBusInfoMapper.selectByExample(example);
        if(agentBusInfoList.size() != 1){
            redisService.rpushList(import_order_imOrderMsg_key+newOrderImportBaseInfo.getOrder_id(),
                    "代理商业务平台未找到" + newOrderImportBaseInfo.getOrder_orgid());
            logger.info("代理商业务平台未找到" + newOrderImportBaseInfo.getOrder_orgid());
            return AgentResult.fail("代理商业务平台未找到" + newOrderImportBaseInfo.getOrder_orgid());
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
        orderFormVo.setId(newOrderImportBaseInfo.getOrder_id());
        //自编订单号
        orderFormVo.setoNum(newOrderImportBaseInfo.getOrder_id());
        //订单申请时间
        orderFormVo.setoApytime(DateUtil.format(newOrderImportBaseInfo.getOrder_date(),"yyyy-MM-dd"));
        //订单生效时间
        orderFormVo.setoInuretime(DateUtil.format(newOrderImportBaseInfo.getOrder_date(),"yyyy-MM-dd"));
        //代理商登录用户ID
        orderFormVo.setUserId(cuserAgent!=null?cuserAgent.getUserid():"");
        //支付方式
        orderFormVo.setPaymentMethod(SettlementType.getByType(newOrderImportBaseInfo.getOrder_paymethod()).code);
        //订单金额
        orderFormVo.setoAmo(new BigDecimal(newOrderImportBaseInfo.getOrder_amt()));
        //订单应付金额
        orderFormVo.setPayAmo(new BigDecimal(newOrderImportBaseInfo.getOrder_amt()));
        //顶大优惠金额
        orderFormVo.setIncentiveAmo(BigDecimal.ZERO);
        //备注
        orderFormVo.setRemark(newOrderImportBaseInfo.getOrder_remark()+"（老订单）");
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
        if(newOrderImportBaseInfo.getOrder_amt().equals(newOrderImportBaseInfo.getOrder_have_amt())) {
            orderFormVo.setClearStatus(Status.STATUS_1.status);
        }else{
            orderFormVo.setClearStatus(Status.STATUS_0.status);
        }
        orderFormVo.setStatus(Status.STATUS_1.status);
        orderFormVo.setcTime(c.getTime());
        orderFormVo.setuUser(User);
        orderFormVo.setuTime(c.getTime());
        orderFormVo.setVersion(Status.STATUS_0.status);
        if(1 != oOrderMapper.insertSelective(orderFormVo)){
            redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                    "插入订单失败" + newOrderImportBaseInfo.getOrder_orgid());
            logger.info("插入订单失败{}", newOrderImportBaseInfo.getOrder_orgid());
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
        oPayment.setRealAmount(new BigDecimal(newOrderImportBaseInfo.getOrder_have_amt()));
        //待付金额
        oPayment.setOutstandingAmount(oPayment.getPayAmount().subtract(oPayment.getRealAmount()));
        oPayment.setPayCompletTime(c.getTime());
        //未付款
        if(BigDecimal.ZERO.compareTo(new BigDecimal(newOrderImportBaseInfo.getOrder_have_amt()))==0){
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
        if("是".equals(newOrderImportBaseInfo.getOrder_is_fp())) {
            oPayment.setIsCloInvoice(Status.STATUS_1.status);
        }else{
            oPayment.setIsCloInvoice(Status.STATUS_0.status);
        }
        for (Dict dict : CAPITAL_TYPE) {
            if(dict.getdItemname().equals(newOrderImportBaseInfo.getOrder_dk_type())){
                oPayment.setDeductionType(dict.getdItemvalue());
                break;
            }
        }
        //抵扣金额
        oPayment.setDeductionAmount(newOrderImportBaseInfo.getOrder_dk_amt()==null?new BigDecimal(newOrderImportBaseInfo.getOrder_dk_amt()):BigDecimal.ZERO);
        //首付金额
        if(StringUtils.isNotBlank(newOrderImportBaseInfo.getOrder_shoufu_amt())) {
            oPayment.setDownPayment(
                    StringUtils.isNotBlank(
                            newOrderImportBaseInfo.getOrder_shoufu_amt())?new BigDecimal(newOrderImportBaseInfo.getOrder_shoufu_amt()):BigDecimal.ZERO);
        }
        //分期期数
        oPayment.setDownPaymentCount(
                StringUtils.isNotBlank(
                        newOrderImportBaseInfo.getOrder_fenqi_count())?new BigDecimal(newOrderImportBaseInfo.getOrder_fenqi_count()):BigDecimal.ZERO);
        //分期日期
        oPayment.setDownPaymentDate(
                StringUtils.isNotBlank(
                        newOrderImportBaseInfo.getOrder_fenqi_date())?DateUtil.format(newOrderImportBaseInfo.getOrder_fenqi_date(),"yyyy-MM"):null
        );
        //打款用户
        oPayment.setDownPaymentUser(
                StringUtils.isNotBlank(
                        newOrderImportBaseInfo.getOrder_pay_user())?newOrderImportBaseInfo.getOrder_pay_user():"");

        //收款公司
        for (PayComp payComp : payComps) {
            if(payComp.getComName().equals(newOrderImportBaseInfo.getOrder_colcomp())){
                oPayment.setCollectCompany(payComp.getId());
            }
        }

        String batchcode = c.getTime().getTime()+"";
        //总额
        BigDecimal payAmount = oPayment.getPayAmount();
        //已付
        BigDecimal yifu = oPayment.getRealAmount();
        //待付生成分期 或者生成欠款
        BigDecimal daifu = oPayment.getOutstandingAmount();
        //首付金额 首付生成首付明细
        BigDecimal shoufu = oPayment.getDownPayment();
        //抵扣类型
        String dikou_type = oPayment.getDeductionType();
        //抵扣金额
        BigDecimal dikou = oPayment.getDeductionAmount()==null ?BigDecimal.ZERO:oPayment.getDeductionAmount();
        //首付实付金额
        BigDecimal acture = shoufu==null ?BigDecimal.ZERO:(shoufu.subtract(dikou));

        oPayment.setActualReceipt(acture);
        oPayment.setActualReceiptDate(c.getTime());
        oPayment.setRemark("(老订单)");
        oPayment.setcTime(c.getTime());
        oPayment.setStatus(Status.STATUS_1.status);
        oPayment.setVersion(Status.STATUS_0.status);
        if(1 != oPaymentMapper.insertSelective(oPayment)){
            redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                    "插入付款单失败" + newOrderImportBaseInfo.getOrder_orgid());
            logger.info("插入付款单失败{}", newOrderImportBaseInfo.getOrder_orgid());
            throw new MessageException("插入付款单失败");
        }

        //==========================================生成付款明细


        //首付金额 首付生成首付明细
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
                redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                        "分期处理失败" + newOrderImportBaseInfo.getOrder_orgid());
                logger.info("分期处理失败{}", newOrderImportBaseInfo.getOrder_orgid());
                throw new MessageException("分期处理");
            }
        }

        //抵扣 抵扣生成抵扣明细

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
                redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                        "分期处理失败" + newOrderImportBaseInfo.getOrder_orgid());
                logger.info("分期处理失败{}", newOrderImportBaseInfo.getOrder_orgid());
                throw new MessageException("分期处理");
            }
        }

        //已付款余处
        BigDecimal yifu_mingxi = yifu.subtract(shoufu);
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
                redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                        "分期处理失败" + newOrderImportBaseInfo.getOrder_orgid());
                logger.info("分期处理失败{}", newOrderImportBaseInfo.getOrder_orgid());
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
                FKFQ_data = FKFQ_data = StageUtil.stageOrder(daifu,oPayment.getDownPaymentCount().intValue(),oPayment.getDownPaymentDate(), c.get(Calendar.DAY_OF_MONTH));
            } else if (oPayment.getPayMethod().equals(SettlementType.FKFQ.code)) {
                paymentType = PaymentType.DKFQ;
                FKFQ_data = FKFQ_data = StageUtil.stageOrder(daifu,oPayment.getDownPaymentCount().intValue(),oPayment.getDownPaymentDate(), c.get(Calendar.DAY_OF_MONTH));
            } else if (oPayment.getPayMethod().equals(SettlementType.XXDK.code)) {
                paymentType = PaymentType.DKFQ;
                FKFQ_data = FKFQ_data = StageUtil.stageOrder(daifu,1,c.getTime(), c.get(Calendar.DAY_OF_MONTH));
            } else if (oPayment.getPayMethod().equals(SettlementType.FRFQ.code)) {
                paymentType = PaymentType.FRFQ;
                FKFQ_data = FKFQ_data = StageUtil.stageOrder(daifu,
                        oPayment.getDownPaymentCount().intValue(),
                        oPayment.getDownPaymentDate(), c.get(Calendar.DAY_OF_MONTH));
            }else{
                paymentType = PaymentType.FRFQ;
                FKFQ_data = FKFQ_data = StageUtil.stageOrder(daifu,oPayment.getDownPaymentCount().intValue(),oPayment.getDownPaymentDate(), c.get(Calendar.DAY_OF_MONTH));
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
                        redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                                "分期处理失败" + newOrderImportBaseInfo.getOrder_orgid());
                        logger.info("分期处理失败{}", newOrderImportBaseInfo.getOrder_orgid());
                        throw new MessageException("分期处理");
                    }
                }
            }
        }

        //解析订单商品信息
        AgentResult suborder = newOrderImportService.newPareseOrderImportSubOrderInfo(newOrderImportBaseInfo, orderFormVo, oPayment, User);
        if (!suborder.isOK()) {
            List<NewOrderImportGoodsInfo> newOrderImportGoodsInfos = newOrderImportBaseInfo.getNewOrderImportGoodsInfos();
            for (NewOrderImportGoodsInfo newOrderImportGoodsInfo : newOrderImportGoodsInfos) {
                String importId = newOrderImportGoodsInfo.getImportId();
                ImportAgent newOrderImportGoodsInfo_importAgent = importAgentMapper.selectByPrimaryKey(importId);
                newOrderImportGoodsInfo_importAgent.setDealstatus(Status.STATUS_3.status);
                newOrderImportGoodsInfo_importAgent.setDealmsg(suborder.getMsg());
                newOrderImportGoodsInfo_importAgent.setDealTime(new Date());
                int updateSuborder = importAgentMapper.updateByPrimaryKeySelective(newOrderImportGoodsInfo_importAgent);
                if(updateSuborder != 1){
                    redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                            "更新newOrderImportGoodsInfo记录失败" + newOrderImportBaseInfo.getOrder_id() + ":importId" + importId);
                    logger.info("更新newOrderImportGoodsInfo记录失败" + newOrderImportBaseInfo.getOrder_id() + ":importId" + importId);
                    throw new MessageException("更新newOrderImportGoodsInfo记录失败" + newOrderImportBaseInfo.getOrder_id() + ":importId" + importId);
                }
            }
            logger.info("======处理订单商品解析{}失败{}", newOrderImportBaseInfo.getOrder_id(), suborder.getMsg());
        }

        return AgentResult.ok();
    }


    /**
     * 解析生成订单子订单，子订单活动信息
     * @param newOrderImportBaseInfo
     * @param order
     * @param oPayment
     * @param User
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public AgentResult newPareseOrderImportSubOrderInfo(NewOrderImportBaseInfo newOrderImportBaseInfo, OOrder order, OPayment oPayment, String User) throws MessageException {
        List<NewOrderImportGoodsInfo> newOrderImportGoodsInfoList = newOrderImportBaseInfo.getNewOrderImportGoodsInfos();
        List<String> listIdes = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        //遍历商品查看有没有重复的商品
        for (NewOrderImportGoodsInfo newOrderImportGoodsInfo : newOrderImportGoodsInfoList) {
            if(listIdes.contains(newOrderImportGoodsInfo.getGoodsCode())){
                redisService.rpushList(import_order_imOrderMsg_key + newOrderImportGoodsInfo.getOrder_id(),
                        "订单" + newOrderImportGoodsInfo.getOrder_id() + "的商品" + newOrderImportGoodsInfo.getGoodsCode() + "重复，订单中不允许重复商品");
                logger.info("订单" + newOrderImportGoodsInfo.getOrder_id() + "的商品" + newOrderImportGoodsInfo.getGoodsCode() + "重复，订单中不允许重复商品");
                throw new MessageException("订单" + newOrderImportGoodsInfo.getOrder_id() + "的商品" + newOrderImportGoodsInfo.getGoodsCode() + "重复， ");
            }
        }
        for (NewOrderImportGoodsInfo newOrderImportGoodsInfo : newOrderImportGoodsInfoList) {
            //查询商品信息 查询商品活动
            OProductExample productExample = new OProductExample();
            productExample.or()
                    .andProCodeEqualTo(newOrderImportGoodsInfo.getGoodsCode())
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andProStatusEqualTo(Status.STATUS_1.status);
            List<OProduct> oProductList = oProductMapper.selectByExample(productExample);
            if(oProductList.size()!=1){
                redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                        "商品信息未找到" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
                logger.info("商品信息未找到" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
                throw new MessageException("商品信息未找到" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
            }
            OProduct product = oProductList.get(0);
            OActivity activity = oActivityMapper.selectByPrimaryKey(newOrderImportGoodsInfo.getActId());

            //查询采购单是否存在此商品
            OSubOrderExample oSubOrderExample = new OSubOrderExample();
            oSubOrderExample.createCriteria()
                    .andStatusEqualTo(Status.STATUS_1.status)
                    .andProIdEqualTo(product.getId())
                    .andOrderIdEqualTo(order.getId());
            List<OSubOrder> oSubOrderList = oSubOrderMapper.selectByExample(oSubOrderExample);
            if (oSubOrderList.size() != 0 && !oSubOrderList.isEmpty()) {
                for (OSubOrder oSubOrder : oSubOrderList) {
                    oSubOrder.setProId(product.getId());
                    oSubOrder.setOrderId(order.getId());
                    oSubOrder.setProCode(product.getProCode());
                    oSubOrder.setProName(product.getProName());
                    oSubOrder.setProType(activity.getProType());
                    oSubOrder.setProPrice(activity.getPrice());
                    oSubOrder.setSendNum(new BigDecimal(newOrderImportGoodsInfo.getSendOutNum()));
                    oSubOrder.setIsDeposit(product.getIsDeposit());
                    oSubOrder.setDeposit(product.getDeposit());
                    oSubOrder.setModel(activity.getProModel());
                    oSubOrder.setProNum(new BigDecimal(newOrderImportGoodsInfo.getGoodsNum()));
                    oSubOrder.setProRelPrice(activity.getPrice());
                    oSubOrder.setRemark("(老订单)");
                    oSubOrder.setcTime(c.getTime());
                    oSubOrder.setuUser(User);
                    oSubOrder.setcUser(User);
                    oSubOrder.setuTime(c.getTime());
                    oSubOrder.setStatus(Status.STATUS_1.status);
                    oSubOrder.setVersion(Status.STATUS_0.status);
                    oSubOrder.setAgentId(order.getAgentId());
                    oSubOrder.setSendOutNum(new BigDecimal(newOrderImportGoodsInfo.getSendOutNum()));
                    oSubOrder.setReturnsNum(new BigDecimal(newOrderImportGoodsInfo.getReturnsNum()));
                    if (1 != oSubOrderMapper.updateByPrimaryKeySelective(oSubOrder)) {
                        redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                                "采购单更新失败" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
                        logger.info("采购单更新失败" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
                        throw new MessageException("采购单更新失败" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
                    }

                    //查询采购活动
                    OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
                    oSubOrderActivityExample.createCriteria()
                            .andStatusEqualTo(Status.STATUS_1.status)
                            .andSubOrderIdEqualTo(oSubOrder.getId())
                            .andProIdEqualTo(oSubOrder.getProId());
                    List<OSubOrderActivity> oSubOrderActivityList = oSubOrderActivityMapper.selectByExample(oSubOrderActivityExample);
                    for (OSubOrderActivity oSubOrderActivity : oSubOrderActivityList) {
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
                        if (1 != oSubOrderActivityMapper.updateByPrimaryKeySelective(oSubOrderActivity)) {
                            redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                                    "采购单活动更新失败" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
                            logger.info("采购单活动更新失败" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
                            throw new MessageException("采购单活动更新失败！");
                        }
                    }
                }
            } else {
                //采购单表
                OSubOrder oSubOrder = new OSubOrder();
                oSubOrder.setId(idService.genId(TabId.o_sub_order));
                oSubOrder.setProId(product.getId());
                oSubOrder.setOrderId(order.getId());
                oSubOrder.setProCode(product.getProCode());
                oSubOrder.setProName(product.getProName());
                oSubOrder.setProType(activity.getProType());
                oSubOrder.setProPrice(activity.getPrice());
                oSubOrder.setSendNum(new BigDecimal(newOrderImportGoodsInfo.getSendOutNum()));
                oSubOrder.setIsDeposit(product.getIsDeposit());
                oSubOrder.setDeposit(product.getDeposit());
                oSubOrder.setModel(activity.getProModel());
                oSubOrder.setProNum(new BigDecimal(newOrderImportGoodsInfo.getGoodsNum()));
                oSubOrder.setProRelPrice(activity.getPrice());
                oSubOrder.setRemark("(老订单)");
                oSubOrder.setcTime(c.getTime());
                oSubOrder.setuUser(User);
                oSubOrder.setcUser(User);
                oSubOrder.setuTime(c.getTime());
                oSubOrder.setStatus(Status.STATUS_1.status);
                oSubOrder.setVersion(Status.STATUS_0.status);
                oSubOrder.setAgentId(order.getAgentId());
                oSubOrder.setSendOutNum(new BigDecimal(newOrderImportGoodsInfo.getSendOutNum()));
                oSubOrder.setReturnsNum(new BigDecimal(newOrderImportGoodsInfo.getReturnsNum()));
                if(1!=oSubOrderMapper.insertSelective(oSubOrder)){
                    redisService.rpushList(import_order_imOrderMsg_key+newOrderImportBaseInfo.getOrder_id(),
                            "采购单添加失败" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
                    logger.info("采购单添加失败" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
                    throw new MessageException("采购单添加失败" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
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
                    redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                            "采购单活动添加失败" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
                    logger.info("采购单活动添加失败" + newOrderImportBaseInfo.getOrder_id() + ":" + newOrderImportGoodsInfo.getGoodsCode());
                    throw new MessageException("采购单活动添加失败");
                }
            }

            String importid = newOrderImportGoodsInfo.getImportId();
            ImportAgent newOrderImportGoodsInfo_importAgent = importAgentMapper.selectByPrimaryKey(importid);
            newOrderImportGoodsInfo_importAgent.setDealstatus(Status.STATUS_2.status);
            newOrderImportGoodsInfo_importAgent.setDealmsg("成功");
            newOrderImportGoodsInfo_importAgent.setDealTime(new Date());
            if (importAgentMapper.updateByPrimaryKeySelective(newOrderImportGoodsInfo_importAgent) != 1) {
                redisService.rpushList(import_order_imOrderMsg_key + newOrderImportBaseInfo.getOrder_id(),
                        "更新orderImportGoodsInfo记录失败" + newOrderImportBaseInfo.getOrder_id() + ":importid" + importid);
                logger.info("更新orderImportGoodsInfo记录失败" + newOrderImportBaseInfo.getOrder_id() + ":importid" + importid);
                throw new MessageException("更新orderImportGoodsInfo记录失败" + newOrderImportBaseInfo.getOrder_id() + ":importid" + importid);
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
    public AgentResult newPareseOrderLogic(String value) throws MessageException {
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
    public AgentResult newPareseReturn(ImportAgent importAgent,String User) throws MessageException {
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
            if (importAgentMapper.updateByPrimaryKeySelective(importAgent1) != 1) {
                logger.info("物流信息更新处理中失败，importAgentsLogic.getId()[" + importAgent1.getId() + "]");
                throw new MessageException("物流信息更新处理中失败，importAgentsLogic.getId()[" + importAgent1.getId() + "]");
            }
        }
        return AgentResult.ok();
    }


    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRES_NEW)
    @Override
    public AgentResult newDeleteFailImportAgentReturn(ImportAgent importAgent,String user)throws Exception{
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
    public AgentResult newDeleteFailImportAgentOrder(ImportAgent importAgent,String user)throws Exception{
        OOrderExample query = new OOrderExample();
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
        List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(OSubOrderExample_query);
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
