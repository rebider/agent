package com.ryx.credit.service.impl.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.dao.agent.*;
import com.ryx.credit.dao.order.*;
import com.ryx.credit.machine.entity.ImsTermAdjustDetail;
import com.ryx.credit.machine.service.ImsTermAdjustDetailService;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.AdjustmentMachineVo;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.ReturnOrderVo;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentEnterService;
import com.ryx.credit.service.agent.BusActRelService;
import com.ryx.credit.service.dict.DepartmentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;
import com.ryx.credit.service.order.*;
import org.apache.commons.collections4.Put;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: Zhang Lei
 * @Description: 退货
 * @Date: 14:23 2018/7/25
 */
@Service("orderReturnService")
public class OrderReturnServiceImpl implements IOrderReturnService {

    public final static SimpleDateFormat sdfyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    public final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    private static Logger log = LoggerFactory.getLogger(OrderReturnServiceImpl.class);
    private static String refund_agent_modify_id = AppConfig.getProperty("refund_agent_modify_id");
    private static String refund_business1_id = AppConfig.getProperty("refund_business1_id");
    private static String refund_finc1_id = AppConfig.getProperty("refund_finc1_id");
    private static String refund_finc2_id = AppConfig.getProperty("refund_finc2_id");
    private static String refund_agent_upload_id = AppConfig.getProperty("refund_agent_upload_id");
    private static String refund_business2_id = AppConfig.getProperty("refund_business2_id");


    @Autowired
    private OReturnOrderMapper returnOrderMapper;
    @Resource
    private IdService idService;
    @Autowired
    private OReturnOrderDetailMapper returnOrderDetailMapper;
    @Autowired
    private OReturnOrderRelMapper returnOrderRelMapper;
    @Autowired
    private ODeductCapitalMapper deductCapitalMapper;
    @Resource
    private PlannerService plannerService;
    @Autowired
    private BusActRelMapper busActRelMapper;
    @Autowired
    private AgentEnterService agentEnterService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private AttachmentRelMapper attachmentRelMapper;
    @Resource
    private OLogisticsService oLogisticsService;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private OLogisticsMapper logisticsMapper;
    @Autowired
    private BusActRelService busActRelService;
    @Autowired
    private OLogisticsDetailMapper logisticsDetailMapper;
    @Autowired
    private OAccountAdjustMapper accountAdjustMapper;
    @Autowired
    private OReceiptProMapper receiptProMapper;
    @Autowired
    private OLogisticsDetailService logisticsDetailService;
    @Autowired
    private OSubOrderMapper oSubOrderMapper;
    @Autowired
    private OLogisticsMapper oLogisticsMapper;
    @Autowired
    private TermMachineService termMachineService;
    @Autowired
    private OOrderMapper oOrderMapper;
    @Autowired
    private PlatFormMapper platFormMapper;
    @Autowired
    private AgentBusInfoMapper agentBusInfoMapper;
    @Autowired
    private ImsTermAdjustDetailService imsTermAdjustDetailService;
    @Autowired
    private OSubOrderActivityMapper subOrderActivityMapper;
    @Autowired
    private OReceiptProMapper oReceiptProMapper;
    @Autowired
    private IOrderReturnService iOrderReturnService;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private OActivityMapper oActivityMapper;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private OPaymentMapper paymentMapper;
    @Autowired
    private OCashReceivablesMapper cashReceivablesMapper;
    @Autowired
    private OInvoiceMapper invoiceMapper;
    @Autowired
    private AttachmentMapper attachmentMapper;
    @Autowired
    private IResourceService iResourceService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private OActivityVisibleMapper activityVisibleMapper;
    @Autowired
    private OsnOperateService osnOperateService;
    @Autowired
    private OrderOffsetService orderOffsetService;
    @Autowired
    private OPayDetailMapper oPayDetailMapper;


    /**
     * @Author: Zhang Lei
     * @Description: 退货列表查询
     * @Date: 14:24 2018/7/25
     */
    @Override
    public PageInfo orderList(OReturnOrder returnOrder, PageInfo page) {
        OReturnOrderExample example = new OReturnOrderExample();
        OReturnOrderExample.Criteria c = example.or();
        if (returnOrder != null) {
            if (StringUtils.isNotEmpty(returnOrder.getId())) {
                c.andIdEqualTo(returnOrder.getId());
            }
            if (StringUtils.isNotEmpty(returnOrder.getAgentId())) {
                c.andAgentIdEqualTo(returnOrder.getAgentId());
            }
        }
        example.setPage(new Page(page.getFrom(), page.getPagesize()));
        long count = returnOrderMapper.countByExample(example);
        example.setOrderByClause(" u_time desc ");
        List<OReturnOrder> list = returnOrderMapper.selectByExample(example);
        page.setRows(list);
        page.setTotal(Integer.parseInt(count + ""));
        return page;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 查询退货详情
     * @Date: 15:27 2018/7/27
     */
    public Map<String, Object> view(String returnId) throws ProcessException {
        Map<String, Object> map = new HashMap<>();

        //查询退货单
        OReturnOrder returnOrder = returnOrderMapper.selectByPrimaryKey(returnId);
        if (returnOrder == null) {
            throw new ProcessException("退货单不存在");
        }

        //查询已排单列表
        Map<String, String> params = new HashMap<String, String>();
        params.put("returnId", returnId);
        List<Map<String, Object>>  receiptPlans = plannerService.queryOrderReceiptPlanInfo(params);

        //查询退货明细
        OReturnOrderDetailExample example = new OReturnOrderDetailExample();
        example.or().andReturnIdEqualTo(returnId);
        List<OReturnOrderDetail> returnDetails = returnOrderDetailMapper.selectByExample(example);
        for (OReturnOrderDetail returnDetail : returnDetails) {
            Dict proComDict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(),returnDetail.getProCom());
            if(null!=proComDict)
            returnDetail.setProCom(proComDict.getdItemname());
            Dict modelTypeDict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(),returnDetail.getProType());
            if(null!=modelTypeDict)
            returnDetail.setProType(modelTypeDict.getdItemname());
        }

        //查询扣款款项
        ODeductCapitalExample deductCapitalExample = new ODeductCapitalExample();
        deductCapitalExample.or().andSourceIdEqualTo(returnId);
        List<ODeductCapital> deductCapitals = deductCapitalMapper.selectByExample(deductCapitalExample);
        Map<String, Object> typeAmt = new HashMap<>();
        for (ODeductCapital oDeductCapital : deductCapitals) {
            typeAmt.put(oDeductCapital.getcType(), oDeductCapital.getcAmount());
        }

        map.put("returnOrder", returnOrder);
        map.put("returnDetails", returnDetails);
        map.put("deductCapitals", deductCapitals);
        map.put("receiptPlans", receiptPlans);
        map.put("typeAmt", typeAmt);

        return map;
    }

    /**
     * @Author: Zhang Lei
     * @Description: 保存扣款款项
     * @Date: 20:25 2018/7/27
     */
    @Override
    @Transactional(propagation = Propagation.NESTED,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public Map<String, Object> saveCut(String returnId, String amt, String ctype) throws MessageException {

        Map<String, Object> map = new HashMap<>();
        OReturnOrder returnOrder = returnOrderMapper.selectByPrimaryKey(returnId);

        //查询，如果是修改，就更新
        List<ODeductCapital> deductCapitals = deductCapitalMapper.selectCountByMap(FastMap.fastMap("returnId", returnId).putKeyV("cType", ctype));
        if (deductCapitals.size() == 1) {
            ODeductCapital oDeductCapital = deductCapitals.get(0);
            BigDecimal amtBack = oDeductCapital.getcAmount();

            oDeductCapital.setcAmount(new BigDecimal(amt));
            deductCapitalMapper.updateByPrimaryKey(oDeductCapital);
            //退货单更新
            if (returnOrder.getReturnAmo().add(amtBack).subtract(new BigDecimal(amt)).compareTo(new BigDecimal(0)) < 0) {
                throw new MessageException("扣款金额不能大于机具金额！");
            }
            returnOrder.setCutAmo(returnOrder.getCutAmo().add(new BigDecimal(amt)).subtract(amtBack));
            returnOrder.setReturnAmo(returnOrder.getReturnAmo().add(amtBack).subtract(new BigDecimal(amt)));
            returnOrder.setuTime(new Date());
            returnOrderMapper.updateByPrimaryKeySelective(returnOrder);
            //返回参数封装
            map.put("cutId", oDeductCapital.getId());
            map.put("goodsReturnAmo", returnOrder.getGoodsReturnAmo());
            map.put("returnAmo", returnOrder.getReturnAmo());
            map.put("cutAmo", returnOrder.getCutAmo());
        } else {
            ODeductCapital deductCapital = new ODeductCapital();
            deductCapital.setId(idService.genId(TabId.o_deduct_capital));
            deductCapital.setcAmount(new BigDecimal(amt));
            deductCapital.setcType(ctype);
            deductCapital.setcAgentId(returnOrder.getAgentId());
            deductCapital.setSourceId(returnId);
            deductCapital.setcTime(new Date());
            deductCapitalMapper.insertSelective(deductCapital);
            //退货单更新
            if (returnOrder.getReturnAmo().subtract(new BigDecimal(amt)).compareTo(new BigDecimal(0)) < 0) {
                throw new MessageException("扣款金额不能大于机具金额！");
            }
            returnOrder.setCutAmo(returnOrder.getCutAmo().add(new BigDecimal(amt)));
            returnOrder.setReturnAmo(returnOrder.getReturnAmo().subtract(new BigDecimal(amt)));
            returnOrder.setuTime(new Date());
            returnOrderMapper.updateByPrimaryKeySelective(returnOrder);
            //返回参数封装
            map.put("cutId", deductCapital.getId());
            map.put("goodsReturnAmo", returnOrder.getGoodsReturnAmo());
            map.put("returnAmo", returnOrder.getReturnAmo());
            map.put("cutAmo", returnOrder.getCutAmo());
        }
        return map;
    }

    @Override
    public Map<String, Object> delCut(String returnId, String cutId, String userid) throws ProcessException {

        Map<String, Object> map = new HashMap<>();

        ODeductCapitalExample example = new ODeductCapitalExample();
        example.or().andIdEqualTo(cutId).andSourceIdEqualTo(returnId);
        List<ODeductCapital> oDeductCapitals = deductCapitalMapper.selectByExample(example);
        if (oDeductCapitals == null && oDeductCapitals.size() <= 0) {
            throw new ProcessException("未找到此扣款款项");
        }
        ODeductCapital oDeductCapital = oDeductCapitals.get(0);
        deductCapitalMapper.deleteByExample(example);

        OReturnOrder returnOrder = returnOrderMapper.selectByPrimaryKey(returnId);
        returnOrder.setCutAmo(returnOrder.getCutAmo().subtract(oDeductCapital.getcAmount()));
        returnOrder.setReturnAmo(returnOrder.getReturnAmo().add(oDeductCapital.getcAmount()));
        returnOrder.setuTime(new Date());
        returnOrder.setcUser(userid);
        returnOrderMapper.updateByPrimaryKeySelective(returnOrder);

        map.put("goodsReturnAmo", returnOrder.getGoodsReturnAmo());
        map.put("returnAmo", returnOrder.getReturnAmo());
        map.put("cutAmo", returnOrder.getCutAmo());

        return map;
    }





    /**
     * @Author: Zhang Lei
     * @Description: 退回申请修改
     * @Date: 17:09 2018/8/8
     */
    @Override
    @Transactional
    public Map<String, Object> applyEdit(String agentId, OReturnOrder returnOrder, String productsJson, String userid,String invoiceList) throws ProcessException {

        List<Map> list = null;
        try {
            list = JSONObject.parseArray(productsJson, Map.class);
        } catch (Exception e) {
            log.error("解析退货商品失败", e);
            throw new ProcessException("解析退货商品失败");
        }

        //检查SN是否允许退货
        try {
            checkSn(list, agentId);
        } catch (ProcessException e) {
            throw e;
        }

        List<OInvoice> oInvoices = null;
        try {
            oInvoices = JSONObject.parseArray(invoiceList,OInvoice.class);
        } catch (Exception e) {
            log.error("解析发票信息失败", e);
            throw new ProcessException("解析发票信息失败");
        }

        //删除旧退货明细
        String returnId = returnOrder.getId();
        OReturnOrderDetailExample example = new OReturnOrderDetailExample();
        example.or().andReturnIdEqualTo(returnId);
        returnOrderDetailMapper.deleteByExample(example);

        //删除旧的退货单和订单关系
        OReturnOrderRelExample relExample = new OReturnOrderRelExample();
        relExample.or().andReturnOrderIdEqualTo(returnId);
        returnOrderRelMapper.deleteByExample(relExample);


        //更新退货单
        OReturnOrder returnOrderDB = returnOrderMapper.selectByPrimaryKey(returnId);
        returnOrderDB.setRetSchedule(new BigDecimal(RetSchedule.SPZ.code));
        returnOrderDB.setGoodsReturnAmo(returnOrder.getReturnAmo());
        returnOrderDB.setReturnAmo(returnOrder.getReturnAmo());
        returnOrderDB.setCutAmo(BigDecimal.ZERO);
        returnOrderDB.setRelReturnAmo(BigDecimal.ZERO);
        returnOrderDB.setTakeOutAmo(BigDecimal.ZERO);
        returnOrderDB.setuTime(new Date());
        returnOrderDB.setuUser(userid);
        returnOrderDB.setRetReceipt(returnOrder.getRetReceipt());
        returnOrderDB.setRetInvoice(returnOrder.getRetInvoice());
        returnOrderDB.setApplyRemark(returnOrder.getApplyRemark());
        returnOrderMapper.updateByPrimaryKeySelective(returnOrderDB);


        //退货单和订单关系
        Set<String> relSet = new HashSet<>();

        //生成新的退货单明细
        BigDecimal orderTotalAmt = BigDecimal.ZERO;        //订单总金额
        BigDecimal totalAmt = BigDecimal.ZERO;             //退货总金额
        BigDecimal bjTotalAmt = BigDecimal.ZERO;             //北京收款总金额
        BigDecimal invoiceTotalAmt = BigDecimal.ZERO;      //发票总金额
        BigDecimal isCloInvoice = Status.STATUS_0.status;  //是否开具发票
        String collectCompany = "7";  //北京财务
        for (Map<String, Object> map : list) {

            String orderId = (String) map.get("orderId");
            String startSn = (String) map.get("startSn");
            String endSn = (String) map.get("endSn");

            //生成退货明细
            OReturnOrderDetail returnOrderDetail = null;
            try {
                returnOrderDetail = new OReturnOrderDetail();
                returnOrderDetail.setId(idService.genId(TabId.o_return_order_detail));
                returnOrderDetail.setReturnId(returnId);
                returnOrderDetail.setAgentId(agentId);
                returnOrderDetail.setOrderId(orderId);
                returnOrderDetail.setSubOrderId((String) map.get("receiptId"));
                returnOrderDetail.setProId((String) map.get("proId"));
                returnOrderDetail.setProName((String) map.get("proName"));
                returnOrderDetail.setProType((String) map.get("proType"));
                returnOrderDetail.setProCom((String) map.get("proCom"));
                returnOrderDetail.setProPrice(MapUtil.getBigDecimal(map, "proPrice"));
                returnOrderDetail.setModel((String) map.get("proModel"));
                returnOrderDetail.setBeginSn(startSn);
                returnOrderDetail.setEndSn(endSn);
                returnOrderDetail.setOrderPrice(MapUtil.getBigDecimal(map, "proPrice"));
                returnOrderDetail.setReturnCount(MapUtil.getBigDecimal(map, "count"));
                returnOrderDetail.setReturnAmt(MapUtil.getBigDecimal(map, "totalPrice"));
                returnOrderDetail.setReturnTime(new Date());
                returnOrderDetail.setcTime(new Date());
                returnOrderDetail.setcUser(agentId);
                returnOrderDetail.setStatus(Status.STATUS_1.status);
                returnOrderDetail.setVersion(Status.STATUS_1.status);
                returnOrderDetailMapper.insertSelective(returnOrderDetail);
            } catch (Exception e) {
                log.error("生成退货明细失败", e);
                throw new ProcessException("生成退货明细失败,SN[" + (String) map.get("startSn") + "--" + (String) map.get("endSn") + "]");
            }

            //更新物流明细表SN状态
            try {
                oLogisticsService.updateSnStatus(orderId, startSn, endSn, OLogisticsDetailStatus.STATUS_TH.code, OLogisticsDetailStatus.RECORD_STATUS_LOC.code, returnId);
            } catch (Exception e) {
                log.error("更新SN状态失败", e);
                throw new ProcessException("更新SN状态失败,SN[" + (String) map.get("startSn") + "--" + (String) map.get("endSn") + "]");
            }

            String setstr = returnOrderDetail.getOrderId() + "_" + returnOrderDetail.getSubOrderId() + "_" + returnOrderDetail.getReturnId();
            relSet.add(setstr);

            totalAmt = totalAmt.add(MapUtil.getBigDecimal(map, "proPrice").multiply(MapUtil.getBigDecimal(map, "count")));
            OPaymentExample oPaymentExample = new OPaymentExample();
            OPaymentExample.Criteria criteria = oPaymentExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andOrderIdEqualTo(orderId);
            List<OPayment>  paymentList = paymentMapper.selectByExample(oPaymentExample);
            OPayment oPayment = paymentList.get(0);
            BigDecimal cloInvoice = oPayment.getIsCloInvoice();
            if(cloInvoice.compareTo(Status.STATUS_1.status)==0){
                isCloInvoice = cloInvoice;
            }
            OCashReceivablesExample oCashReceivablesExample = new OCashReceivablesExample();
            OCashReceivablesExample.Criteria oCashCriteria = oCashReceivablesExample.createCriteria();
            oCashCriteria.andStatusEqualTo(Status.STATUS_1.status);
            oCashCriteria.andCashpayTypeEqualTo(CashPayType.PAYMENT.code);
            oCashCriteria.andReviewStatusEqualTo(AgStatus.Approved.getValue());
            oCashCriteria.andSrcIdEqualTo(oPayment.getId());
            List<OCashReceivables> oCashReceivables = cashReceivablesMapper.selectByExample(oCashReceivablesExample);
            for (OCashReceivables oCashReceivable : oCashReceivables) {
                String company = oCashReceivable.getCollectCompany();
                if(company.equals("7")){
                    collectCompany = company;
                    invoiceTotalAmt = invoiceTotalAmt.add(oCashReceivable.getRealAmount());
                }else{
                    bjTotalAmt = bjTotalAmt.add(oCashReceivable.getRealAmount());
                }
            }
            BigDecimal amt = iOrderReturnService.selectOrderDetails(orderId);
            orderTotalAmt = orderTotalAmt.add(oPayment.getPayAmount()).subtract(amt);
        }
        //订单下单时的发票开具状态为是 且 收款账号为深圳财务的
        if(isCloInvoice.compareTo(Status.STATUS_1.status)==0 && collectCompany.equals("7")){
//          1.订单总金额-退货金额小于等于发票金额 发票信息为必填
//          2.订单总金额-退货金额大于发票金额 发票信息可选择否
            if(orderTotalAmt.subtract(bjTotalAmt).subtract(totalAmt).compareTo(invoiceTotalAmt)<0){
                if(returnOrder.getRetInvoice().compareTo(Status.STATUS_0.status)==0){
                    throw new ProcessException("是否退发票必须选择是");
                }
                if(oInvoices==null){
                    throw new ProcessException("发票信息为必填");
                }
                if(oInvoices.size()==0){
                    throw new ProcessException("发票信息为必填");
                }
            }
        }
        //删除发票信息
        OInvoiceExample oInvoiceExample = new OInvoiceExample();
        OInvoiceExample.Criteria oInvoiceCriteria = oInvoiceExample.createCriteria();
        oInvoiceCriteria.andStatusEqualTo(Status.STATUS_1.status);
        oInvoiceCriteria.andSrcTypeEqualTo(OInvoiceSrcType.RETURNORDER.code);
        oInvoiceCriteria.andSrcIdEqualTo(returnId);
        List<OInvoice> updateInvoices = invoiceMapper.selectByExample(oInvoiceExample);
        updateInvoices.forEach(row->{
            row.setStatus(Status.STATUS_0.status);
            int i = invoiceMapper.updateByPrimaryKeySelective(row);
            if (1 != i) {
                log.info("删除发票信息失败");
                throw new ProcessException("删除发票信息失败");
            }
        });

        BigDecimal invoiceAmt = BigDecimal.ZERO;
        if(oInvoices!=null)
        for (OInvoice oInvoice : oInvoices) {
            if(StringUtils.isBlank(oInvoice.getInvoiceCompany())){
                throw new ProcessException("开票公司不能为空");
            }
            if(StringUtils.isBlank(oInvoice.getInvoiceProject())){
                throw new ProcessException("开票项目不能为空");
            }
            if(null==oInvoice.getInvoiceAmt()){
                throw new ProcessException("金额不能为空");
            }
            if(oInvoice.getInvoiceAmt().compareTo(BigDecimal.ZERO)==-1){
                throw new ProcessException("金额必须大于0！");
            }
            Boolean regMachinesDeptAmt = RegExpression.regAmount(oInvoice.getInvoiceAmt());
            if(!regMachinesDeptAmt){
                throw new ProcessException("金额不正确,保留小数点后两位！");
            }
            if(StringUtils.isBlank(oInvoice.getInvoiceNum())){
                throw new ProcessException("发票号不能为空");
            }
            if(StringUtils.isBlank(oInvoice.getInvoiceCode())){
                throw new ProcessException("发票代码不能为空");
            }
            if(StringUtils.isBlank(oInvoice.getExpressNum())){
                throw new ProcessException("快递单号不能为空");
            }
            if(StringUtils.isBlank(oInvoice.getExpressComp())){
                throw new ProcessException("快递公司不能为空");
            }
            if(null==oInvoice.getSendTime()){
                throw new ProcessException("寄出时间不能为空");
            }
            String oInvoiceId = idService.genId(TabId.O_INVOICE);
            oInvoice.setId(oInvoiceId);
            oInvoice.setSrcType(OInvoiceSrcType.RETURNORDER.code);
            oInvoice.setSrcId(returnId);
            oInvoice.setAgentId(returnOrder.getAgentId());
            oInvoice.setcTime(new Date());
            oInvoice.setStatus(Status.STATUS_1.status);
            oInvoice.setVersion(Status.STATUS_1.status);
            oInvoice.setcUser(userid);
            oInvoice.setuTime(new Date());
            oInvoice.setuUser(userid);
            invoiceMapper.insert(oInvoice);


            AttachmentRelExample attachmentRelExample = new AttachmentRelExample();
            AttachmentRelExample.Criteria criteria = attachmentRelExample.createCriteria();
            criteria.andSrcIdEqualTo(oInvoiceId);
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andBusTypeEqualTo(AttachmentRelType.returnOrderInvoice.name());
            List<AttachmentRel> attachmentRels = attachmentRelMapper.selectByExample(attachmentRelExample);
            attachmentRels.forEach(row->{
                row.setStatus(Status.STATUS_0.status);
                int i = attachmentRelMapper.updateByPrimaryKeySelective(row);
                if (1 != i) {
                    log.info("删除发票信息附件关系失败");
                    throw new ProcessException("删除附件失败");
                }
            });

            List<String> invoiceTableFiles = oInvoice.getInvoiceTableFile();
            //添加新的附件
            if (invoiceTableFiles != null && invoiceTableFiles.size()!=0) {
                for (String invoiceTableFile : invoiceTableFiles) {
                    AttachmentRel record = new AttachmentRel();
                    record.setAttId(invoiceTableFile);
                    record.setSrcId(oInvoiceId);
                    record.setcUser(userid);
                    record.setcTime(Calendar.getInstance().getTime());
                    record.setStatus(Status.STATUS_1.status);
                    record.setBusType(AttachmentRelType.returnOrderInvoice.name());
                    record.setId(idService.genId(TabId.a_attachment_rel));
                    int f = attachmentRelMapper.insertSelective(record);
                    if (1 != f) {
                        log.info("退货上传发票信息保存附件关系失败");
                        throw new ProcessException("保存附件失败");
                    }
                }
            }
            invoiceAmt = invoiceAmt.add(oInvoice.getInvoiceAmt());
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
                returnOrderRel.setcUser(agentId);
                returnOrderRelMapper.insertSelective(returnOrderRel);
            } catch (Exception e) {
                log.error("生成退货关系失败", e);
                throw new ProcessException("生成退货关系失败");
            }
        }

        return null;
    }


    public void delReceiptAndLogistis(String returnId) {
        OReturnOrderDetailExample example = new OReturnOrderDetailExample();
        example.or().andReturnIdEqualTo(returnId).andStatusEqualTo(Status.STATUS_1.status);
        List<OReturnOrderDetail>  order_return_details = returnOrderDetailMapper.selectByExample(example);

        List<String> order_return_details_id = new ArrayList<>();
        for (OReturnOrderDetail order_return_detail : order_return_details) {
            order_return_details_id.add(order_return_detail.getId());
        }
        //删除排单计划
        ReceiptPlanExample receiptPlanExample = new ReceiptPlanExample();
        receiptPlanExample.or().andReturnOrderDetailIdIn(order_return_details_id);
        List<ReceiptPlan> receiptPlans = receiptPlanMapper.selectByExample(receiptPlanExample);
        for (ReceiptPlan receiptPlan : receiptPlans) {
            String receiptPlanId = receiptPlan.getId();
            //更新收货单商品表已排单数量
            String receiptProId = receiptPlan.getProId();
            OReceiptProExample receiptProExample = new OReceiptProExample();
            receiptProExample.or().andIdEqualTo(receiptProId);
            List<OReceiptPro> receiptPros = receiptProMapper.selectByExample(receiptProExample);
            if (receiptPros != null && receiptPros.size() > 0) {
                OReceiptPro receiptPro = receiptPros.get(0);
                //收货单排单数量减去排单表排单数量
                receiptPro.setSendNum(receiptPro.getSendNum().subtract(receiptPlan.getPlanProNum()));
                receiptPro.setReceiptProStatus(OReceiptStatus.WAITING_LIST.code);
                int cts = receiptProMapper.updateByPrimaryKeySelective(receiptPro);
                if(cts<=0){
                    throw new ProcessException("退货退回时更新已排单数量失败，receiptProId={"+receiptProId+"}");
                }
            }
            //删除物流及物流明细
            OLogisticsExample logisticsExample = new OLogisticsExample();
            logisticsExample.or().andReceiptPlanIdEqualTo(receiptPlanId);
            List<OLogistics> oLogistics = logisticsMapper.selectByExample(logisticsExample);
            for (OLogistics logistics : oLogistics) {
                String logisticsId = logistics.getId();
                OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                oLogisticsDetailExample.or().andLogisticsIdEqualTo(logisticsId);
                logisticsDetailMapper.deleteByExample(oLogisticsDetailExample);
            }
            logisticsMapper.deleteByExample(logisticsExample);
        }
        receiptPlanMapper.deleteByExample(receiptPlanExample);
    }

    /**
     * @Author: Zhang Lei
     * @Description: 检查SN是否允许退货
     * @Date: 20:33 2018/8/10
     */
    public void checkSn(List<Map> list, String agentId) {
        List<String> orderIdList = new ArrayList();
        try {
            for (Map<String, Object> map : list) {
                String startSn = (String) map.get("startSn");
                String endSn = (String) map.get("endSn");
                Integer begins = (Integer) map.get("begins");
                Integer finish = (Integer) map.get("finish");

                //检查sn是否在划拨，换活动，退货中
                FastMap fastMap = osnOperateService.checkSNApproval(FastMap
                        .fastMap("beginSN", startSn)
                        .putKeyV("endSN", endSn));
                if (!FastMap.isSuc(fastMap)) throw new ProcessException(fastMap.get("msg").toString());

                List<String> sns = logisticsDetailService.querySnLList(startSn, endSn);
                for (String sn : sns) {
                    //根据sn查询物流信息
                    Map<String, Object> snmap = oLogisticsService.getLogisticsBySn(sn, agentId);
                }
                //查询SN转发的平台是否相同，不同不允许转发
                orderIdList.add((String) map.get("orderId"));
            }
            //查询平台是否相同
            if (!(orderIdList.size() > 0)) throw new ProcessException("上传sn订单异常，请联系管理员！！！");
            List<String> platformTypeList = platFormMapper.selectByOrderIdList(orderIdList);

            if (platformTypeList.size() != 1) {
                for (String platFormList : platformTypeList) {
                    if(!PlatformType.whetherPOS(platFormList)){
                        throw new ProcessException("退货只支持一个业务平台退货，本批次SN中存在多个业务，请分别提交!");
                    }
                }
            }
        }catch (ProcessException e) {
            throw e;
        }
    }

    /**
     * @Author: Zhang Lei
     * @Description: 退貨申請
     * @Date: 20:43 2018/7/26
     */
    @Override
    @Transactional
    public Map<String, Object> apply(String agentId, OReturnOrder returnOrder, String productsJson, String userid,String invoiceList) throws ProcessException {

        //冻结sn在业务平台状态
        List<Map<String, Object>> snList = new ArrayList<>();
        Map<String, Object> snMap = new HashMap<>();

        List<Map> list = null;
        try {
            list = JSONObject.parseArray(productsJson, Map.class);
        } catch (Exception e) {
            log.error("解析退货商品失败", e);
            throw new ProcessException("解析退货商品失败");
        }

        List<OInvoice> oInvoices = null;
        try {
            oInvoices = JSONObject.parseArray(invoiceList,OInvoice.class);
        } catch (Exception e) {
            log.error("解析发票信息失败", e);
            throw new ProcessException("解析发票信息失败");
        }

        //检查SN是否允许退货
        try {
            List<String> orderIdList = new ArrayList();
            for (Map<String, Object> map : list) {
                String startSn = (String) map.get("startSn");
                String endSn = (String) map.get("endSn");

                //检查sn是否在划拨，换活动，退货中
                FastMap fastMap = osnOperateService.checkSNApproval(FastMap
                        .fastMap("beginSN", startSn)
                        .putKeyV("endSN", endSn));
                if (!FastMap.isSuc(fastMap)) throw new ProcessException(fastMap.get("msg").toString());

                List<String> sns = logisticsDetailService.querySnLList(startSn, endSn);
                for (String sn : sns) {
                    //验证是否属于代理商的
                    Map<String, Object> checkMap = oLogisticsMapper.getOrderAndLogisticsBySn(sn, agentId);
                    if (checkMap == null || checkMap.size() <= 0) {
                        throw new ProcessException("sn号" + sn + "不是有效的发货状态，请核实是否属于您的订单或是否发起过退货");
                    }
                    //验证是否有效的平台码
                    List<Map<String, Object>> agentBusInfos = agentBusInfoMapper.queryAgentBusInfoByLogisticsDetailSn(FastMap.fastMap("snNum", sn));
                    if (null == agentBusInfos || agentBusInfos.size() != 1) {
                        throw new ProcessException("无效的业务平台，SN：" + sn);
                    }
                }
                //查询SN转发的平台是否相同，不同不允许转发
                orderIdList.add((String) map.get("orderId"));
            }

            //查询平台是否相同
            if (!(orderIdList.size() > 0)) throw new ProcessException("上传sn订单异常，请联系管理员！！！");
            List<String> platformTypeList = platFormMapper.selectByOrderIdList(orderIdList);
            if (platformTypeList.size() != 1) {
                for (String platFormList : platformTypeList) {
                    if(!PlatformType.whetherPOS(platFormList)){
                        throw new ProcessException("退货只支持一个业务平台退货，本批次SN中存在多个业务，请分批次提交!");
                    }
                }
            }
        } catch (ProcessException e) {
            log.error("查询SN是否允许退货失败", e);
            throw new ProcessException(e.getMessage());
        }

        //生成退货单
        String returnId = null;
        try {
            returnId = idService.genId(TabId.o_return_order);
            returnOrder.setId(returnId);
            returnOrder.setRetSchedule(new BigDecimal(RetSchedule.SPZ.code));
            returnOrder.setcTime(new Date());
            returnOrder.setcUser(userid);
            returnOrder.setAgentId(agentId);
            returnOrder.setCutAmo(BigDecimal.ZERO);
            returnOrder.setTakeOutAmo(BigDecimal.ZERO);
            returnOrder.setRelReturnAmo(BigDecimal.ZERO);
            returnOrder.setVersion(Status.STATUS_1.status);
            returnOrder.setLogicalVersion(String.valueOf(Status.STATUS_1.status));
            returnOrder.setStatus(Status.STATUS_1.status);
            returnOrderMapper.insertSelective(returnOrder);
        } catch (Exception e) {
            log.error("生成退货单失败", e);
            throw new ProcessException("生成退货单失败");
        }

        //退货单和订单关系
        Set<String> relSet = new HashSet<>();
        BigDecimal orderTotalAmt = BigDecimal.ZERO;        //订单总金额
        BigDecimal totalAmt = BigDecimal.ZERO;             //退货总金额
        BigDecimal bjTotalAmt = BigDecimal.ZERO;             //北京收款总金额
        BigDecimal invoiceTotalAmt = BigDecimal.ZERO;      //发票总金额
        BigDecimal isCloInvoice = Status.STATUS_0.status;  //是否开具发票
        String collectCompany = "7";  //北京财务
        Set<String> agDocDistrict = new HashSet<>();
        Set<String> agDocPro = new HashSet<>();
        Set<String> busPlatform = new HashSet<>();
        for (Map<String, Object> map : list) {
            String orderId = (String) map.get("orderId");
            String startSn = (String) map.get("startSn");
            String endSn = (String) map.get("endSn");
            //生成退货明细
            OReturnOrderDetail returnOrderDetail = null;
            try {
                returnOrderDetail = new OReturnOrderDetail();
                returnOrderDetail.setId(idService.genId(TabId.o_return_order_detail));
                returnOrderDetail.setReturnId(returnId);
                returnOrderDetail.setAgentId(agentId);
                returnOrderDetail.setOrderId(orderId);
                returnOrderDetail.setSubOrderId((String) map.get("receiptId"));
                returnOrderDetail.setProId((String) map.get("proId"));
                returnOrderDetail.setProName((String) map.get("proName"));
                returnOrderDetail.setProType((String) map.get("proType"));
                returnOrderDetail.setProCom((String) map.get("proCom"));
                returnOrderDetail.setProPrice(MapUtil.getBigDecimal(map, "proPrice"));
                returnOrderDetail.setModel((String) map.get("proModel"));
                returnOrderDetail.setBeginSn((String) map.get("startSn"));
                returnOrderDetail.setEndSn((String) map.get("endSn"));
                returnOrderDetail.setOrderPrice(MapUtil.getBigDecimal(map, "proPrice"));
                returnOrderDetail.setReturnPrice(MapUtil.getBigDecimal(map, "proPrice"));
                returnOrderDetail.setReturnCount(MapUtil.getBigDecimal(map, "count"));
                returnOrderDetail.setReturnAmt(MapUtil.getBigDecimal(map, "totalPrice"));
                returnOrderDetail.setReturnTime(new Date());
                returnOrderDetail.setIsDeposit(Status.STATUS_0.status);
                returnOrderDetail.setcTime(new Date());
                returnOrderDetail.setuTime(new Date());
                returnOrderDetail.setcUser(agentId);
                returnOrderDetail.setuUser(agentId);
                returnOrderDetail.setStatus(Status.STATUS_1.status);
                returnOrderDetail.setVersion(Status.STATUS_1.status);
                returnOrderDetail.setActid(MapUtil.getString(map, "actId"));
                returnOrderDetailMapper.insertSelective(returnOrderDetail);
            } catch (Exception e) {
                log.error("生成退货明细失败", e);
                throw new ProcessException("生成退货明细失败,SN[" + (String) map.get("startSn") + "--" + (String) map.get("endSn") + "]");
            }

            //更新物流明细表SN状态
            try {
                // fixme 检查sn状态是否有效 添加redissn锁
                oLogisticsService.updateSnStatus(orderId, startSn, endSn, OLogisticsDetailStatus.STATUS_TH.code, OLogisticsDetailStatus.RECORD_STATUS_LOC.code, returnId);
            } catch (Exception e) {
                log.error("更新SN状态失败", e);
                throw new ProcessException("更新SN状态失败,SN[" + (String) map.get("startSn") + "--" + (String) map.get("endSn") + "]");
            }


            String setstr = returnOrderDetail.getOrderId() + "_" + returnOrderDetail.getSubOrderId() + "_" + returnOrderDetail.getReturnId();
            relSet.add(setstr);
            totalAmt = totalAmt.add(MapUtil.getBigDecimal(map, "proPrice").multiply(MapUtil.getBigDecimal(map, "count")));

            OPaymentExample oPaymentExample = new OPaymentExample();
            OPaymentExample.Criteria criteria = oPaymentExample.createCriteria();
            criteria.andStatusEqualTo(Status.STATUS_1.status);
            criteria.andOrderIdEqualTo(orderId);
            List<OPayment>  paymentList = paymentMapper.selectByExample(oPaymentExample);
            OPayment oPayment = paymentList.get(0);
            BigDecimal cloInvoice = oPayment.getIsCloInvoice();
            if(cloInvoice.compareTo(Status.STATUS_1.status)==0){
                isCloInvoice = cloInvoice;
            }
            OCashReceivablesExample oCashReceivablesExample = new OCashReceivablesExample();
            OCashReceivablesExample.Criteria oCashCriteria = oCashReceivablesExample.createCriteria();
            oCashCriteria.andStatusEqualTo(Status.STATUS_1.status);
            oCashCriteria.andCashpayTypeEqualTo(CashPayType.PAYMENT.code);
            oCashCriteria.andReviewStatusEqualTo(AgStatus.Approved.getValue());
            oCashCriteria.andSrcIdEqualTo(oPayment.getId());
            List<OCashReceivables> oCashReceivables = cashReceivablesMapper.selectByExample(oCashReceivablesExample);
            for (OCashReceivables oCashReceivable : oCashReceivables) {
                String company = oCashReceivable.getCollectCompany();
                if(company.equals("7")){
                    invoiceTotalAmt = invoiceTotalAmt.add(oCashReceivable.getRealAmount());
                    collectCompany = company;
                }else{
                    bjTotalAmt = bjTotalAmt.add(oCashReceivable.getRealAmount());
                }
            }
            BigDecimal amt = iOrderReturnService.selectOrderDetails(orderId);
            orderTotalAmt = orderTotalAmt.add(oPayment.getPayAmount()).subtract(amt);

            //封装业务平台冻结sn
            snMap.put("endSn", returnOrderDetail.getEndSn());
            snMap.put("beginSn", returnOrderDetail.getBeginSn());
            snMap.put("taskId", returnOrderDetail.getId());
            snMap.put("orderId", returnOrderDetail.getOrderId());
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByOrderId(returnOrderDetail.getOrderId());
            agDocDistrict.add(agentBusInfo.getAgDocDistrict());
            agDocPro.add(agentBusInfo.getAgDocPro());
            busPlatform.add(agentBusInfo.getBusPlatform());
            snMap.put("agencyId", agentBusInfo.getBusNum());
            snList.add(snMap);
        }
        //订单下单时的发票开具状态为是 且 收款账号为深圳财务的
        if(isCloInvoice.compareTo(Status.STATUS_1.status)==0 && collectCompany.equals("7")){
//          1.订单总金额-退货金额小于等于发票金额 发票信息为必填
//          2.订单总金额-退货金额大于发票金额 发票信息可选择否
            if(orderTotalAmt.subtract(bjTotalAmt).subtract(totalAmt).compareTo(invoiceTotalAmt)<0){
                if(returnOrder.getRetInvoice().compareTo(Status.STATUS_0.status)==0){
                    throw new ProcessException("是否退发票必须选择是");
                }
                if(oInvoices==null){
                    throw new ProcessException("发票信息为必填");
                }
                if(oInvoices.size()==0){
                    throw new ProcessException("发票信息为必填");
                }
            }
        }

        BigDecimal invoiceAmt = BigDecimal.ZERO;
        if(Status.STATUS_1.status.compareTo(returnOrder.getRetInvoice())==0) {
            if (oInvoices != null && oInvoices.size() > 0) {
                for (OInvoice oInvoice : oInvoices) {
                    if (StringUtils.isBlank(oInvoice.getInvoiceCompany())) {
                        throw new ProcessException("开票公司不能为空");
                    }
                    if (StringUtils.isBlank(oInvoice.getInvoiceProject())) {
                        throw new ProcessException("开票项目不能为空");
                    }
                    if (null == oInvoice.getInvoiceAmt()) {
                        throw new ProcessException("金额不能为空");
                    }
                    if (StringUtils.isBlank(oInvoice.getInvoiceNum())) {
                        throw new ProcessException("发票号不能为空");
                    }
                    if (StringUtils.isBlank(oInvoice.getInvoiceCode())) {
                        throw new ProcessException("发票代码不能为空");
                    }
                    if (StringUtils.isBlank(oInvoice.getExpressNum())) {
                        throw new ProcessException("快递单号不能为空");
                    }
                    if (StringUtils.isBlank(oInvoice.getExpressComp())) {
                        throw new ProcessException("快递公司不能为空");
                    }
                    if (null == oInvoice.getSendTime()) {
                        throw new ProcessException("寄出时间不能为空");
                    }
                    String oInvoiceId = idService.genId(TabId.O_INVOICE);
                    oInvoice.setId(oInvoiceId);
                    oInvoice.setSrcType(OInvoiceSrcType.RETURNORDER.code);
                    oInvoice.setSrcId(returnId);
                    oInvoice.setAgentId(returnOrder.getAgentId());
                    oInvoice.setcTime(new Date());
                    oInvoice.setuTime(new Date());
                    oInvoice.setcUser(userid);
                    oInvoice.setuUser(userid);
                    oInvoice.setStatus(Status.STATUS_1.status);
                    oInvoice.setVersion(Status.STATUS_1.status);
                    invoiceMapper.insert(oInvoice);

                    List<String> invoiceTableFiles = oInvoice.getInvoiceTableFile();
                    //添加新的附件
                    if (invoiceTableFiles != null && invoiceTableFiles.size() != 0) {
                        for (String invoiceTableFile : invoiceTableFiles) {
                            AttachmentRel record = new AttachmentRel();
                            record.setAttId(invoiceTableFile);
                            record.setSrcId(oInvoiceId);
                            record.setcUser(userid);
                            record.setcTime(Calendar.getInstance().getTime());
                            record.setStatus(Status.STATUS_1.status);
                            record.setBusType(AttachmentRelType.returnOrderInvoice.name());
                            record.setId(idService.genId(TabId.a_attachment_rel));
                            int f = attachmentRelMapper.insertSelective(record);
                            if (1 != f) {
                                log.info("退货上传发票信息保存附件关系失败");
                                throw new ProcessException("保存附件失败");
                            }
                        }
                    }
                    invoiceAmt = invoiceAmt.add(oInvoice.getInvoiceAmt());
                }
                if (invoiceAmt.compareTo(totalAmt) == -1) {
                    throw new ProcessException("发票金额必须大于退货金额");
                }
            }else{
                throw new ProcessException("请填写发票信息");
            }
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
                returnOrderRel.setcUser(agentId);
                returnOrderRelMapper.insertSelective(returnOrderRel);
            } catch (Exception e) {
                log.error("生成退货关系失败", e);
                throw new ProcessException("生成退货关系失败");
            }
        }


        //启动退货审批流程==========================================================

        //检查是否有审批中的代理商新
        BusActRelExample example = new BusActRelExample();
        example.or().andBusIdEqualTo(returnId)
                .andBusTypeEqualTo(BusActRelBusType.refund.name())
                .andActivStatusEqualTo(AgStatus.Approving.name())
                .andStatusEqualTo(Status.STATUS_1.status);
        if (busActRelMapper.selectByExample(example).size() > 0) {
            log.info("退货提交审批失败,禁止重复提交审批{}:{}", returnId, agentId);
            throw new ProcessException("退货提交审批失败，禁止重复提交审批");
        }

        //更新退货单状态
        returnOrder.setRetSchedule(new BigDecimal(RetSchedule.SPZ.code));
        returnOrder.setuTime(new Date());
        if (1 != returnOrderMapper.updateByPrimaryKeySelective(returnOrder)) {
            log.info("退货提交审批失败,禁止重复提交审批{}:{}", returnId, agentId);
            throw new ProcessException("退货提交审批失败，更新退货单失败");
        }
        Map startPar = agentEnterService.startPar(userid);
        if (null == startPar) {
            log.info("========用户{}{}启动部门参数为空", returnId, agentId);
            throw new ProcessException("启动部门参数为空!");
        }

        //启动审批
        String proce = activityService.createDeloyFlow(null, dictOptionsService.getApproveVersion("refund"), null, null, startPar);
        if (proce == null) {
            log.info("退货提交审批，审批流启动失败{}:{}", returnId, agentId);
            throw new ProcessException("退货审批流启动失败!");
        }

        //添加审批关系
        BusActRel record = new BusActRel();
        record.setBusId(returnId);
        record.setActivId(proce);
        record.setcTime(Calendar.getInstance().getTime());
        record.setcUser(userid);
        record.setStatus(Status.STATUS_1.status);
        record.setBusType(BusActRelBusType.refund.name());
        record.setActivStatus(AgStatus.Approving.name());
        record.setAgentId(agentId);
        record.setDataShiro(BusActRelBusType.refund.key);
        Agent agent = agentMapper.selectByPrimaryKey(agentId);
        if(agent!=null) {
            record.setAgentName(agent.getAgName());
        }
        if (busPlatform.size() != 1 || agDocDistrict.size() != 1 || agDocPro.size() != 1) {
            throw new ProcessException("一次只能提交一种业务类型的机具！");
        }
        record.setNetInBusType("ACTIVITY_" + busPlatform.iterator().next());
        record.setAgDocDistrict(agDocDistrict.iterator().next());
        record.setAgDocPro(agDocPro.iterator().next());
        if (1 != busActRelMapper.insertSelective(record)) {
            log.info("退货提交审批，启动审批异常，添加审批关系失败{}:{}", returnId, proce);
            throw new ProcessException("退货审批流启动失败:添加审批关系失败");
        }

        //各个平台查询SN是否可用
        try {
            PlatForm platForm =platFormMapper.selectByOrderId((String) list.get(0).get("orderId"));
            if (null == platForm) throw new ProcessException("原订单信息不存在，请核实SN号码是否正确！");
            termMachineService.checkOrderReturnSN(snList, platForm.getPlatformType());
        } catch (Exception e) {
            throw new ProcessException(e.getLocalizedMessage());
        }

        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.DEFAULT,rollbackFor = Exception.class)
    public BigDecimal selectOrderDetails(String orderId){
        BigDecimal amt = BigDecimal.ZERO;
        OReturnOrderDetailExample oReturnOrderDetailExample = new OReturnOrderDetailExample();
        OReturnOrderDetailExample.Criteria criteria1 = oReturnOrderDetailExample.createCriteria();
        criteria1.andStatusEqualTo(Status.STATUS_1.status);
        criteria1.andOrderIdEqualTo(orderId);
        List<OReturnOrderDetail> oReturnOrderDetails = returnOrderDetailMapper.selectByExample(oReturnOrderDetailExample);
        for (OReturnOrderDetail oReturnOrderDetail : oReturnOrderDetails) {
            amt = amt.add(oReturnOrderDetail.getReturnAmt());
        }
        return amt;
    }

    /**
     * @Author: Zhang Lei
     * @Description: 更新某一退货单中原始订单SN状态
     * @Date: 16:18 2018/8/8
     */
    public void updateReturnOrderSnStatus(String returnId, BigDecimal snStatus, BigDecimal recordStatus) {
        try {
            OReturnOrderDetailExample example = new OReturnOrderDetailExample();
            example.or().andReturnIdEqualTo(returnId);
            List<OReturnOrderDetail> list = returnOrderDetailMapper.selectByExample(example);
            for (OReturnOrderDetail detail : list) {
                log.info("======更新退货单中原始订单SN状态{},{},{},{},{},{}",detail.getOrderId(), detail.getBeginSn(), detail.getEndSn(), snStatus, recordStatus, returnId);
                int res = oLogisticsService.updateSnStatus(detail.getOrderId(), detail.getBeginSn().trim(), detail.getEndSn().trim(), snStatus, recordStatus, returnId);
                log.info("======更新退货单中原始订单SN状态数量{}:{},{},{},{},{},{}",res,detail.getOrderId(), detail.getBeginSn(), detail.getEndSn(), snStatus, recordStatus, returnId);
                if(res==0)throw new MessageException("更新退货单中原始订单SN状态数量为0");
            }
        } catch (Exception e) {
            log.error("更新退货单中原始订单SN状态失败{},{}", returnId, snStatus, e);
            throw new ProcessException("更新退货单中原始订单SN状态失败{" + returnId + "}");
        }
    }

    /**
     * @Author: Zhang Lei
     * @Description: 更新退货单状态
     * @Date: 16:50 2018/8/8
     */
    public int updateOrderReturn(String returnId, BigDecimal status) {
        OReturnOrder returnOrder = returnOrderMapper.selectByPrimaryKey(returnId);
        returnOrder.setRetSchedule(status);
        returnOrder.setuTime(new Date());
        return returnOrderMapper.updateByPrimaryKeySelective(returnOrder);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTaskAjustPeople(OReturnOrder oReturnOrder) throws ProcessException {
        return returnOrderMapper.updateByPrimaryKeySelective(oReturnOrder)==1?AgentResult.ok():AgentResult.fail();
    }

    /**
     * 审批订单任务
     *
     * @param agentVo
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult approvalTask(AgentVo agentVo, String userId) throws ProcessException {
        try {

            //处理审批数据
            log.info("退货审批，完成任务{}:{}：{}", agentVo.getTaskId(), userId, JSONObject.toJSONString(agentVo));

            String returnId = agentVo.getReturnId();
            OReturnOrder returnOrder = returnOrderMapper.selectByPrimaryKey(returnId);
            //独立事物更新
            if(null!=agentVo.getoReturnOrder()) {
                if(null==agentVo.getoReturnOrder().getRefundtime()) {
                    throw new ProcessException("核款时间不能为空");
                }
                if(null==agentVo.getoReturnOrder().getRefundpeople()) {
                    throw new ProcessException("核款人不能为空");
                }
                returnOrder.setAuditor(userId);
                returnOrder.setRefundpeople(agentVo.getoReturnOrder().getRefundpeople());
                returnOrder.setRefundtime(agentVo.getoReturnOrder().getRefundtime());
                //独立事务更新
                if (!iOrderReturnService.approvalTaskAjustPeople(returnOrder).isOK()) {
                    throw new ProcessException("核款人更新失败");
                }

            }
            //再次查询
            returnOrder = returnOrderMapper.selectByPrimaryKey(returnId);
            //业务处理
            String sid = agentVo.getSid();

            String approveResult = agentVo.getApprovalResult();

            //如果是代理商修改订单时，修改SN状态
            if (approveResult.equals(ApprovalType.PASS.getValue()) && sid.equals(refund_agent_modify_id)) {
                try {
                    updateReturnOrderSnStatus(agentVo.getReturnId(), OLogisticsDetailStatus.STATUS_TH.code, OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
                } catch (ProcessException e) {
                    return AgentResult.fail(e.getMessage());
                }
            }

            //业务第一次审批，有排单就保存，没有就不保存
            if (approveResult.equals(ApprovalType.PASS.getValue()) && sid.equals(refund_business1_id)) {

                JSONArray jsonArray = JSONObject.parseArray(agentVo.getPlans());
                if (!jsonArray.isEmpty()) {
                    try {
                        AgentResult agentResult = savePlans(agentVo, userId);
                        if (!agentResult.isOK()) {
                            return AgentResult.fail(agentResult.getMsg());
                        }
                    } catch (MessageException me){
                        throw new ProcessException(me.getMsg());
                    }catch (Exception e){
                        throw new ProcessException(e.getLocalizedMessage());
                    }
                }
            }

            //业务第二次审批，校验排单信息，必须进行排单
            if (approveResult.equals(ApprovalType.PASS.getValue()) && sid.equals(refund_business2_id)) {
                //第一次未排单
                if (!(receiptPlanMapper.selectPlanNumReturnId(agentVo.getReturnId()) > 0)) {
                    JSONArray jsonArray = JSONObject.parseArray(agentVo.getPlans());
                    log.info("agentVo:{}",agentVo);
                    if ("null".equals(agentVo.getPlans()) || jsonArray.size() < 1 || null == agentVo.getPlans() || "[]".equals(agentVo.getPlans())) {
                        throw new ProcessException("排单信息不能为空");
                    } else {
                        try {
                            AgentResult agentResult = savePlans(agentVo, userId);
                            if (!agentResult.isOK()) {
                                return AgentResult.fail(agentResult.getMsg());
                            }
                        } catch (MessageException me){
                            throw new ProcessException(me.getMsg());
                        }catch (Exception e){
                            throw new ProcessException(e.getLocalizedMessage());
                        }
                    }
                }
            }

            //财务第一次审批时更新发货状态
            if (approveResult.equals(ApprovalType.PASS.getValue()) && sid.equals(refund_finc1_id)) {
                updateOrderReturn(agentVo.getReturnId(), new BigDecimal(RetSchedule.DFH.code));
            }

            //财务最后审批时上传打款凭证,并且是已经执行退款方案
            if (approveResult.equals(ApprovalType.PASS.getValue()) && sid.equals(refund_finc2_id)) {
                if (returnOrder.getLogicalVersion()!=null && returnOrder.getLogicalVersion().equals(String.valueOf(Status.STATUS_1.status))){
                    //根据新的逻辑版本号，判断是否执行了抵扣计划
                    OPayDetailExample oPayDetailExample = new OPayDetailExample();
                    oPayDetailExample.or().andSrcIdEqualTo(returnOrder.getId())
                            .andBusStatEqualTo(Status.STATUS_0.status)
                            .andStatusEqualTo(Status.STATUS_1.status);
                    List<OPayDetail> oPayDetails = oPayDetailMapper.selectByExample(oPayDetailExample);
                    if (oPayDetails == null || oPayDetails.size() <= 0) {
                        return AgentResult.fail("您还未执行退款方案");
                    }
                }else {
                    OAccountAdjustExample oAccountAdjustExample = new OAccountAdjustExample();
                    oAccountAdjustExample.or().andSrcIdEqualTo(agentVo.getReturnId()).andAdjustTypeEqualTo(AdjustType.TKTH.adjustType);
                    List<OAccountAdjust> oAccountAdjusts = accountAdjustMapper.selectByExample(oAccountAdjustExample);
                    if (oAccountAdjusts == null || oAccountAdjusts.size() <= 0) {
                        return AgentResult.fail("您还未执行退款方案");
                    }
                }

                if (returnOrder.getRelReturnAmo().compareTo(BigDecimal.ZERO) > 0 && agentVo.getAttachments().length <= 0) {
                    return AgentResult.fail("有线下退款金额时，必须上传打款凭证");
                }

                AgentResult agentResult = saveAttachments(agentVo, userId);
                if (!agentResult.isOK()) {
                    return AgentResult.fail(agentResult.getMsg());
                }
            }


            //代理商上传物流信息时判断是否上传物流信息
            if (approveResult.equals(ApprovalType.PASS.getValue()) && sid.equals(refund_agent_upload_id)) {
                //物流发货数量，必须等于排单数量
                int sendtotal = oLogisticsMapper.selectSendNumByReturnId(agentVo.getReturnId());
                int plantotal = receiptPlanMapper.selectPlanNumByReturnId(agentVo.getReturnId());
                if (plantotal != sendtotal) {
                    throw new ProcessException("物流发货数量必须等于排单数量！");
                }

                ReceiptPlanExample example = new ReceiptPlanExample();
                example.or().andReturnOrderDetailIdEqualTo(agentVo.getReturnId());
                List<ReceiptPlan> receiptPlans = receiptPlanMapper.selectByExample(example);
                for (ReceiptPlan receiptPlan : receiptPlans) {
                    String receiptPlanId = receiptPlan.getId();
                    OLogisticsExample example1 = new OLogisticsExample();
                    example1.or().andReceiptPlanIdEqualTo(receiptPlanId);
                    List<OLogistics> oLogistics = logisticsMapper.selectByExample(example1);
                    if (oLogistics == null || oLogistics.size() <= 0) {
                        throw new ProcessException("排单编号为" + receiptPlanId + "的排单未导入退货物流信息");
                    }
                }
                updateOrderReturn(agentVo.getReturnId(), new BigDecimal(RetSchedule.YFH.code));
            }

            //如果是退回修改订单信息时，修改SN状态
            if (agentVo.getApprovalResult().equals(ApprovalType.BACK.getValue())) {
                try {
//                    updateReturnOrderSnStatus(agentVo.getReturnId(), OLogisticsDetailStatus.STATUS_FH.code, OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
//                    updateOrderReturn(agentVo.getReturnId(), new BigDecimal(RetSchedule.TH.code));
//                    //删除排单和物流
//                    delReceiptAndLogistis(agentVo.getReturnId());
                } catch (ProcessException e) {
                    return AgentResult.fail(e.getMessage());
                }
            }

            //完成任务
            AgentResult result = new AgentResult(500, "系统异常", "");
            Map<String, Object> reqMap = new HashMap<>();
            reqMap.put("rs", agentVo.getApprovalResult());
            reqMap.put("approvalOpinion", agentVo.getApprovalOpinion());
            reqMap.put("approvalPerson", userId);
            reqMap.put("createTime", DateUtils.dateToStringss(new Date()));
            reqMap.put("taskId", agentVo.getTaskId());

            //下一个节点参数
            if (org.apache.commons.lang.StringUtils.isNotEmpty(agentVo.getOrderAprDept()))
                reqMap.put("dept", agentVo.getOrderAprDept());

            //传递部门信息
            Map startPar = agentEnterService.startPar(userId);
            if (null != startPar) {
                reqMap.put("party", startPar.get("party"));
            }
            //完成任务
            Map resultMap = activityService.completeTask(agentVo.getTaskId(), reqMap);
            if (resultMap == null) {
                throw new ProcessException("catch工作流处理任务异常!");
            }
            Boolean rs = (Boolean) resultMap.get("rs");
            String msg = String.valueOf(resultMap.get("msg"));
            if (!rs) {
                throw new ProcessException("catch工作流处理任务异常!");
            }
            return AgentResult.ok(null);
        } catch (ProcessException e) {
            e.printStackTrace();
            throw new ProcessException(e.getMessage());
        }
    }


    /**
     * @Author: Zhang Lei
     * @Description: 审批拒绝
     * @Date: 16:38 2018/8/8
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public void approvalReject(String processInstanceId, String activityName) throws Exception{
        try {
            log.info("退货审批拒绝回调:{},{}", processInstanceId, activityName);
            //审批流关系
            BusActRel rel = busActRelService.findById(processInstanceId);
            rel.setActivStatus(AgStatus.Refuse.name());
            if(1!=busActRelService.updateByPrimaryKey(rel)){
                throw new MessageException("更新流程失败");
            }
            //退货编号
            String returnId = rel.getBusId();
            //取消抵扣
            OReturnOrder oReturnOrder = returnOrderMapper.selectByPrimaryKey(returnId);
            //取消抵扣
            AgentResult agentResult = orderOffsetService.OffsetArrearsCancle(oReturnOrder.getTakeOutAmo(), OffsetPaytype.THTK.code, returnId);
            if (!agentResult.isOK()){
                log.error("抵扣欠款取消失败");
                throw new MessageException("抵扣欠款取消失败！");
            }
            //更新退货单
            updateOrderReturn(returnId, new BigDecimal(RetSchedule.JJ.code));
            //更新原始订单SN
            updateReturnOrderSnStatus(returnId, OLogisticsDetailStatus.STATUS_FH.code, OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
            //删除排单和物流
            delReceiptAndLogistis(returnId);
        } catch (Exception e) {
            log.error("退货审批拒绝回调错误", e);
            throw e;
        }
    }

    /**
     * @Author: Zhang Lei
     * @Description: 审批完成
     * @Date: 16:38 2018/8/8
     */
    @Transactional(rollbackFor = Exception.class,isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public void approvalFinish(String processInstanceId, String activityName) throws Exception{
        try {
            log.info("退货审批完成回调:{},{}", processInstanceId, activityName);
            //审批流关系
            BusActRel rel = busActRelService.findById(processInstanceId);
            rel.setActivStatus(AgStatus.Approved.name());
            if(1!=busActRelService.updateByPrimaryKey(rel)){
                throw new MessageException("更新流程失败");
            }
            //退货编号
            String returnId = rel.getBusId();
            OReturnOrder oReturnOrder = returnOrderMapper.selectByPrimaryKey(returnId);
            //提交抵扣
            AgentResult agentResult = orderOffsetService.OffsetArrearsCommit(oReturnOrder.getTakeOutAmo(), OffsetPaytype.THTK.code, returnId);
            if (!agentResult.isOK()){
                log.error("抵扣欠款提交失败");
                throw new MessageException(agentResult.getMsg());
            }
            //更新退货单
            if(updateOrderReturn(returnId, new BigDecimal(RetSchedule.WC.code))!=1){
                log.info("退货审批完成回调:{},{},更新退货单失败", processInstanceId, activityName);
                throw new MessageException("更新退货单失败");
            }
            //更新原始订单SN
            updateReturnOrderSnStatus(returnId, OLogisticsDetailStatus.STATUS_TH.code, OLogisticsDetailStatus.RECORD_STATUS_HIS.code);
            //更新新订单SN状态
            updateNewOrderSnStatus(returnId);
        } catch (Exception e) {
            log.error("退货审批完成回调异常", e);
            throw e;
        }
    }

    /**
     * @Author: Zhang Lei
     * @Description: 更新新订单物流明细SN状态
     * @Date: 20:03 2018/8/10
     */
    public void updateNewOrderSnStatus(String returnId)throws Exception {
        try {
            log.info("======更新新订单物流明细SN状态:{}",returnId);
            //根据退货单查询明细
            OReturnOrderDetailExample oReturnOrderDetailExample = new OReturnOrderDetailExample();
            oReturnOrderDetailExample.or().andStatusEqualTo(Status.STATUS_1.status).andReturnIdEqualTo(returnId);
            List<OReturnOrderDetail>  list_return_order_detail =  returnOrderDetailMapper.selectByExample(oReturnOrderDetailExample);
            List<String> list_return_order_detail_id = new ArrayList<>();
            for (OReturnOrderDetail detail : list_return_order_detail) {
                list_return_order_detail_id.add(detail.getId());
            }
            if(list_return_order_detail_id.size()==0){
                log.info("======更新新订单物流明细SN状态:未找到对应的退货单明细");
                throw new MessageException("未找到对应的退货单明细");
            }
            //根据退货单查询明细查询排单信息 更具排单信息 查询订单和sn
            ReceiptPlanExample example = new ReceiptPlanExample();
            example.or().andReturnOrderDetailIdIn(list_return_order_detail_id);
            List<ReceiptPlan> receiptPlans = receiptPlanMapper.selectByExample(example);
            for (ReceiptPlan receiptPlan : receiptPlans) {
                //查询发货物流
                String receiptPlanId = receiptPlan.getId();
                OLogisticsExample example2 = new OLogisticsExample();
                example2.or().andReceiptPlanIdEqualTo(receiptPlanId);
                List<OLogistics> logistics = logisticsMapper.selectByExample(example2);
                for (OLogistics logi : logistics) {
                    //更新物流明细表中新订单SN状态
                    String orderId = logi.getOrderId();
                    String startSn = logi.getSnBeginNum();
                    String endSn = logi.getSnEndNum();
                    log.info("======更新新订单物流明细SN状态:{}",JSONObject.toJSONString(logi));
                    int res = oLogisticsService.updateSnStatus(orderId, startSn.trim(), endSn.trim(), OLogisticsDetailStatus.STATUS_FH.code, OLogisticsDetailStatus.RECORD_STATUS_VAL.code, null);
                    log.info("======更新新订单物流明细SN状态:{},{}",JSONObject.toJSONString(logi),res);
                    if(res==0){
                        throw new MessageException("更新新订单物流明细SN状态：修改记录为空");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    /**
     * @Author: Zhang Lei
     * @Description: 保存排单
     * @Date: 21:31 2018/8/2
     */
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public AgentResult savePlans(AgentVo agentVo, String userid)throws Exception {
        try {

            JSONArray jsonArray = JSONObject.parseArray(agentVo.getPlans());

            List<String> details = new ArrayList<>();
            List<ReceiptPlan> receiptPlanDetails = new ArrayList<ReceiptPlan>();
            int planCount = 0;

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                ReceiptPlan receiptPlan = new ReceiptPlan();
                receiptPlan.setProId(jsonObject.getString("receiptProId"));
                receiptPlan.setcUser(userid);
                receiptPlan.setUserId(userid);
                receiptPlan.setOrderId(jsonObject.getString("orderId"));
                receiptPlan.setReceiptId(jsonObject.getString("receiptId"));
                //根据退货单进行设置机型和厂家
                // receiptPlan.setProCom(jsonObject.getString("proCom"));
                // receiptPlan.setModel(jsonObject.getString("model"));
                receiptPlan.setPlanProNum(jsonObject.getBigDecimal("planProNum"));
                receiptPlan.setReturnOrderDetailId(jsonObject.getString("O_RETURN_ORDER_DETAIL_ID"));
                String receiptProId = jsonObject.getString("receiptProId");
                if(receiptPlan.getPlanProNum().intValue()==0){
                    throw new MessageException("排单数量不能为0");
                }

                planCount=planCount+receiptPlan.getPlanProNum().intValue();
                //id集合
                details.add(receiptPlan.getReturnOrderDetailId());
                //对象集合
                receiptPlanDetails.add(receiptPlan);
            }

            if(planCount==0){
                throw new MessageException("排单数量不能为0");
            }

            //退货排单数量检查
            OReturnOrderDetailExample example = new OReturnOrderDetailExample();
            example.or().andIdIn(details).andStatusEqualTo(Status.STATUS_1.status);
            List<OReturnOrderDetail> detailsList = returnOrderDetailMapper.selectByExample(example);
            int returnCount = 0;
            for (OReturnOrderDetail return_detail : detailsList) {
                returnCount = returnCount+return_detail.getReturnCount().intValue();
                for (ReceiptPlan receiptPlanDetail : receiptPlanDetails) {
                    if(receiptPlanDetail.getReturnOrderDetailId().equals(return_detail.getId())
                            && receiptPlanDetail.getPlanProNum().compareTo(return_detail.getReturnCount()) > 0){
                        throw new MessageException("排单数量["+receiptPlanDetail.getPlanProNum()+"]大于退货["+return_detail.getReturnCount()+"]的数量");
                    }
                }
            }

            if(returnCount!=planCount){
                throw new MessageException("退货数量必须和排单数量一致");
            }

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;
                ReceiptPlan receiptPlan = new ReceiptPlan();
                receiptPlan.setProId(jsonObject.getString("receiptProId"));
                receiptPlan.setcUser(userid);
                receiptPlan.setUserId(userid);
                receiptPlan.setOrderId(jsonObject.getString("orderId"));
                receiptPlan.setReceiptId(jsonObject.getString("receiptId"));
                receiptPlan.setPlanProNum(jsonObject.getBigDecimal("planProNum"));
                receiptPlan.setReturnOrderDetailId(jsonObject.getString("O_RETURN_ORDER_DETAIL_ID"));
                String receiptProId = jsonObject.getString("receiptProId");

                //根据退货单的商品活动补充极具类型
                String O_RETURN_ORDER_DETAIL_ID = jsonObject.getString("O_RETURN_ORDER_DETAIL_ID");
                OReturnOrderDetail oReturnOrderDetail =  returnOrderDetailMapper.selectByPrimaryKey(O_RETURN_ORDER_DETAIL_ID);

                //机具型号要和退货的机具型号和厂家要一样
                receiptPlan.setProCom(oReturnOrderDetail.getProCom());
                receiptPlan.setModel(oReturnOrderDetail.getModel());

                //查询新订单的订单
                OReceiptPro oReceiptPro = receiptProMapper.selectByPrimaryKey(receiptProId);
                OSubOrderExample new_oSubOrders = new OSubOrderExample();
                new_oSubOrders.or().andOrderIdEqualTo(oReceiptPro.getOrderid())
                        .andProIdEqualTo(oReceiptPro.getProId())
                        .andStatusEqualTo(Status.STATUS_1.status);
                List<OSubOrder>  oSubOrders_new = oSubOrderMapper.selectByExample(new_oSubOrders);

                if(oSubOrders_new.size()!=1){
                    throw new MessageException("订购商品未找到!");
                }
                OOrder order = oOrderMapper.selectByPrimaryKey(receiptPlan.getOrderId());
                //查询新订单的子订单活动
                OSubOrder subOrder = oSubOrders_new.get(0);

                OSubOrderActivityExample oSubOrderActivityExample_new = new OSubOrderActivityExample();
                oSubOrderActivityExample_new.or()
                        .andSubOrderIdEqualTo(subOrder.getId())
                        .andStatusEqualTo(Status.STATUS_1.status);

                List<OSubOrderActivity>  new_order_activitys = subOrderActivityMapper.selectByExample(oSubOrderActivityExample_new);
                if(new_order_activitys.size()==0){
                    throw new MessageException("排单订单活动未找到!");
                }
                OSubOrderActivity new_order_subactivity = new_order_activitys.get(0);
                OActivity new_order_oActivity = oActivityMapper.selectByPrimaryKey(new_order_subactivity.getActivityId());
                //找到新订单活动信息
                OActivityExample find_new_order_activity = new OActivityExample();
                find_new_order_activity.or()
                        .andVenderEqualTo(receiptPlan.getProCom())
                        .andProModelEqualTo(receiptPlan.getModel())
                        .andActCodeEqualTo(new_order_oActivity.getActCode())
                        .andStatusEqualTo(Status.STATUS_1.status);
                List<OActivity> activities = oActivityMapper.selectByExample(find_new_order_activity);
                if(activities.size()==0){
                    throw new MessageException("排单订单活动无法确定! 厂商："+receiptPlan.getProCom()+",型号:"+receiptPlan.getModel()+",活动代码:"+new_order_oActivity.getActCode());
                }
                OActivity new_act = null;
                //只有一个活动
                if(activities.size()==1){
                     new_act = activities.get(0);
                }else{
                    throw new MessageException("新订单活动不能确定");
                }
                AgentResult result = plannerService.savePlanner(receiptPlan, receiptProId,new_act.getId());
                log.info("退货排单信息保存:{}{}",receiptPlan.getReturnOrderDetailId(),receiptPlan.getProId(),result.getMsg());
            }

        } catch (Exception e) {
            log.error("保存退货排单失败", e);
          throw new MessageException("保存退货排单失败:"+e.getLocalizedMessage());
        }

        return AgentResult.ok();
    }


    /**
     * @Author: Zhang Lei
     * @Description: 保存打款截图
     * @Date: 21:31 2018/8/2
     */
    @Override
    public AgentResult saveAttachments(AgentVo agentVo, String userid) {
        try {
            String[] attachments = agentVo.getAttachments();
            String returnId = agentVo.getReturnId();
            for (String attach : attachments) {
                AttachmentRel attachmentRel = new AttachmentRel();
                attachmentRel.setId(idService.genId(TabId.a_attachment_rel));
                attachmentRel.setSrcId(returnId);
                attachmentRel.setAttId(attach);
                attachmentRel.setBusType(AttachmentRelType.Return.name());
                attachmentRel.setcTime(new Date());
                attachmentRel.setcUser(userid);
                attachmentRel.setStatus(Status.STATUS_1.status);
                attachmentRelMapper.insertSelective(attachmentRel);
            }
        } catch (Exception e) {
            log.error("保存退货打款凭证失败", e);
            return AgentResult.fail("保存退货打款凭证失败");
        }

        return AgentResult.ok();
    }


    /**
     * @Author: Zhang Lei
     * @Description: 执行扣款计划后更新退货单和退货明细
     * @Date: 19:13 2018/8/3
     */
    public void doPlan(String returnId, BigDecimal takeAmt, String userid)throws Exception{

        try {
            OReturnOrder oReturnOrder = returnOrderMapper.selectByPrimaryKey(returnId);

            if(oReturnOrder.getRetSchedule()!=null && (oReturnOrder.getRetSchedule().equals(RetSchedule.TKZ.code) || oReturnOrder.getRetSchedule().equals(RetSchedule.WC.code)) ){
                throw new MessageException("禁止重复调账");
            }

            BigDecimal returnAmo = oReturnOrder.getReturnAmo();
            oReturnOrder.setTakeOutAmo(takeAmt);
            oReturnOrder.setRelReturnAmo(returnAmo.subtract(takeAmt));

            if (oReturnOrder.getRelReturnAmo().compareTo(BigDecimal.ZERO) > 0) {
                oReturnOrder.setRetSchedule(new BigDecimal(RetSchedule.TKZ.code));
            } else {
                oReturnOrder.setRetSchedule(new BigDecimal(RetSchedule.WC.code));
            }

            oReturnOrder.setRetTime(new Date());
            oReturnOrder.setuUser(userid);

            if(1!=returnOrderMapper.updateByPrimaryKeySelective(oReturnOrder)){
                throw new MessageException("退款单更更新失败");
            }
        } catch (MessageException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * 查询退货所有数据&查询代理商退货数据
     * @param param
     * @param pageInfo
     * @return
     */
    @Override
    public PageInfo orderReturnList(Map<String, Object> param, PageInfo pageInfo) {
        if(StringUtils.isBlank(String.valueOf(param.get("agentId")))){
            if(!String.valueOf(param.get("orderReturn")).equals("all")){
                List<Map<String, Object>> orgCodeRes = iUserService.orgCode(Long.valueOf(param.get("userId").toString()));
                if(orgCodeRes==null && orgCodeRes.size()!=1){
                    return null;
                }
                Map<String, Object> objectMap = orgCodeRes.get(0);
                String orgId = String.valueOf(objectMap.get("ORGID"));
                param.put("orgId",orgId);
            }
        }
        Long count = returnOrderMapper.getOrderReturnCount(param);
        List<Map<String, Object>> list = returnOrderMapper.getOrderReturnList(param);
        for (Map<String, Object> stringObjectMap : list) {
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(),String.valueOf(stringObjectMap.get("VENDER")));
            if(dict!=null){
                stringObjectMap.put("VENDER",dict.getdItemname());
            }
            Dict modelType = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(),String.valueOf(stringObjectMap.get("PRO_TYPE")));
            if (null!=modelType){
                stringObjectMap.put("PRO_TYPE",modelType.getdItemname());
            }
        }
        pageInfo.setTotal(count.intValue());
        pageInfo.setRows(list);
        return pageInfo;
    }

    /**
     * 查询省区退货数据
     * @param page
     * @param map
     * @param userId
     * @return
     */
    @Override
    public PageInfo queryOrderReturnList(Page page, Map map, Long userId) {
        List<Map<String, Object>> orgCodeRes = iUserService.orgCode(userId);
        if (orgCodeRes == null && orgCodeRes.size() != 1) {
            return null;
        }
        Map<String, Object> stringObjectMap = orgCodeRes.get(0);
        String orgId = String.valueOf(stringObjectMap.get("ORGID"));
        String organizationCode = String.valueOf(stringObjectMap.get("ORGANIZATIONCODE"));
        map.put("orgId", orgId);
        map.put("userId", userId);
        map.put("organizationCode", organizationCode);
        if (null != map) {
            String time = String.valueOf(map.get("time"));
            if (StringUtils.isNotBlank(time)&&!time.equals("null")) {
                String reltime = time.substring(0, 10);
                map.put("time", reltime);
            }
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(returnOrderMapper.queryOrderReturnProvinceList(map, page));
        pageInfo.setTotal(returnOrderMapper.queryOrderReturnProvinceCount(map));
        return pageInfo;
    }

    /**
     * 退货导入物流信息
     * @param data
     * @param user
     * @return
     * @throws Exception
     */

    @Override
    public List<String> addList(List<List<Object>> data, String user) throws Exception {
        List<String> list = new ArrayList<>();
        for (List<Object> objectList : data) {
            try {
                AgentResult result =   iOrderReturnService.addListItem(objectList,user);
                log.info("导入物流{}成功",objectList.toString());
                list.add("物流["+objectList.toString()+"]"+result.getMsg());
            }catch (MessageException e) {
                e.printStackTrace();
                log.info("导入物流{}抛出异常{}",objectList.toString(),e.getMsg());
                list.add("物流["+objectList.toString()+"]"+e.getMsg());
            }
            catch (Exception e) {
                e.printStackTrace();
                log.info("导入物流{}抛出异常",objectList.toString());
                list.add("物流["+objectList.toString()+"]导入异常"+e.getMessage());
            }
        }
        return list;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    @Override
    public AgentResult addListItem(List<Object> objectList, String user) throws Exception {

        String planNum = "";
        String orderId = "";
        String proCode = "";
        String proId = "";
        String proName = "";
        String sendDate = "";
        String sendProNum = "";
        String beginSn = "";
        String endSn = "";
        String beginSnCount = "";
        String endSnCount = "";
        String logCom = "";
        String wNumber = "";
        String proType="";

            List col = Arrays.asList(ReceiptPlanReturnExportColum.ReceiptPlanExportColum_column.col);
            planNum = String.valueOf(objectList.get(col.indexOf("PLAN_NUM")));
            orderId = String.valueOf(objectList.get(col.indexOf("ORDER_ID")));
            proCode = String.valueOf(objectList.get(col.indexOf("PRO_CODE")));
            proId = String.valueOf(objectList.get(col.indexOf("PRO_ID")));
            proName = String.valueOf(objectList.get(col.indexOf("PRO_NAME")));
            String proCom = String.valueOf(objectList.get(col.indexOf("PRO_COM_STRING")));

            sendDate = String.valueOf(objectList.get(col.indexOf("h")));
            sendProNum = String.valueOf(objectList.get(col.indexOf("g")));
            logCom = String.valueOf(objectList.get(col.indexOf("a")));
            wNumber = String.valueOf(objectList.get(col.indexOf("b")));
            beginSn = String.valueOf(objectList.get(col.indexOf("c")));
            endSn = String.valueOf(objectList.get(col.indexOf("d")));
            beginSnCount = String.valueOf(objectList.get(col.indexOf("e")));
            endSnCount = String.valueOf(objectList.get(col.indexOf("f")));
            proType = String.valueOf(objectList.get(col.indexOf("PRO_TYPE")));

            if (com.ryx.credit.commons.utils.StringUtils.isBlank(sendDate)) {
                log.info("发货日期不能为空");
                throw new MessageException("发货日期不能为空");
            }
            if (com.ryx.credit.commons.utils.StringUtils.isBlank(planNum)) {
                log.info("排单编号为空");
                throw new MessageException("排单编号为空");
            }
            if (com.ryx.credit.commons.utils.StringUtils.isBlank(orderId)){
                log.info("订单编号为空");
                throw new MessageException("订单编号为空");
            }
            if (com.ryx.credit.commons.utils.StringUtils.isBlank(proCode)) {
                log.info("商品编号为空");
                throw new MessageException("商品编号为空");
            }
            if (com.ryx.credit.commons.utils.StringUtils.isBlank(proId)) {
                log.info("商品ID为空");
                throw new MessageException("商品ID为空");
            }
            if (com.ryx.credit.commons.utils.StringUtils.isBlank(sendProNum)) {
                log.info("请填写发货数量");
                throw new MessageException("请填写发货数量");
            }
            if (com.ryx.credit.commons.utils.StringUtils.isBlank(beginSn)) {
                log.info("请填写起始SN序列号");
                throw new MessageException("请填写起始SN序列号");
            }
            if (com.ryx.credit.commons.utils.StringUtils.isBlank(endSn)){
                log.info("请填写结束SN序列号");
                throw new MessageException("请填写结束SN序列号");
            }

        if (com.ryx.credit.commons.utils.StringUtils.isBlank(logCom)) {
                log.info("请填写物流公司");
                throw new MessageException("请填写物流公司");
            }
            if (com.ryx.credit.commons.utils.StringUtils.isBlank(wNumber)) {
                log.info("请填写物流单号");
                throw new MessageException("请填写物流单号");
            }
            if (com.ryx.credit.commons.utils.StringUtils.isBlank(proType)) {
                log.info("商品类型不能为空");
                throw new MessageException("商品类型不能为空");
            }

            //校验文档不能更改
            List<Map<String,Object>> listItem = receiptPlanMapper.getReceipPlanList(FastMap.fastMap("PLAN_NUM",planNum));
            if(listItem.size()>0){
                //检查列是否有更改
                AgentResult agentResult = checkRecordPlan(objectList,listItem.get(0));
                if(!agentResult.isOK()){
                    log.info("校验Excel文档失败：[],[]",planNum,agentResult.getMsg());
                    throw new MessageException(agentResult.getMsg());
                }
                //校验发货sn是否在退货号码段内
                Object return_detail = listItem.get(0).get("RETURN_ORDER_DETAIL_ID");
                if(return_detail!=null) {
                    OReturnOrderDetail returnOrderDetail = returnOrderDetailMapper.selectByPrimaryKey(return_detail+"");
                    AgentResult res = oLogisticsService.isInSnSegment(returnOrderDetail.getBeginSn(), returnOrderDetail.getEndSn(), beginSn, endSn);
                    if(!res.isOK()){
                        throw new MessageException(res.getMsg());
                    }
                }
            }else{
                throw new MessageException("排单信息未找到");
            }
            if (com.ryx.credit.commons.utils.StringUtils.isBlank(beginSnCount)|| com.ryx.credit.commons.utils.StringUtils.isBlank(endSnCount)){
                beginSnCount="0";
                endSnCount="0";
            }
            //IDlist检查
            List<String> stringList = new OLogisticServiceImpl().idList(beginSn, endSn,Integer.parseInt(beginSnCount),Integer.parseInt(endSnCount),proCom);
            if (Integer.valueOf(sendProNum) != stringList.size()) {
                log.info("请仔细核对发货数量");
                throw new MessageException("请仔细核对发货数量");
            }

            //子订单查询
            OSubOrderExample example = new OSubOrderExample();
            example.or().andStatusEqualTo(Status.STATUS_1.status).andProIdEqualTo(proId).andOrderIdEqualTo(orderId);
            List<OSubOrder>  subOrders = oSubOrderMapper.selectByExample(example);
            if(subOrders.size()!=1){
                log.info("请填写物流单号");
                throw new MessageException("订单["+orderId+"]的商品["+proId+"]数量大于1");
            }
            OSubOrder subOrderItem = subOrders.get(0);
            //商品活动
            OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
            OSubOrderActivityExample.Criteria oSubOrderActivityExample_criteria = oSubOrderActivityExample.createCriteria();
            oSubOrderActivityExample_criteria.andSubOrderIdEqualTo(subOrderItem.getId()).andStatusEqualTo(Status.STATUS_1.status);
            List<OSubOrderActivity> oSubOrderActivities = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);
            if(null==oSubOrderActivities){
                log.info("查询活动数据错误1");
                throw new MessageException("查询活动数据错误");
            }
            if(0==oSubOrderActivities.size()){
                log.info("查询活动数据错误2");
                throw new MessageException("查询活动数据错误");
            }
            //商品活动临时表
            OSubOrderActivity oSubOrderActivity = oSubOrderActivities.get(0);
            OActivity oActivity = oActivityMapper.selectByPrimaryKey(oSubOrderActivity.getActivityId());

            //物流信息
            OLogistics oLogistics = new OLogistics();
            oLogistics.setId(idService.genId(TabId.o_logistics));           // 物流ID序列号
            oLogistics.setcUser(user);                                      // 创建人
            oLogistics.setStatus(Status.STATUS_1.status);                   // 默认记录状态为1
            oLogistics.setLogType(LogType.Refund.getValue());              // 默认物流类型为1
            try {
                oLogistics.setSendDate(sdfyyyyMMdd.parse(sendDate));
            }catch (Exception e){
                try {
                    oLogistics.setSendDate(sdf.parse(sendDate));// 物流日期
                }catch (Exception m){
                    throw new MessageException("日期格式支持yyyyMMdd 或者yyyy-MM-dd");
                }
            }
            oLogistics.setcTime(Calendar.getInstance().getTime());          // 创建时间
            oLogistics.setIsdeall(Status.STATUS_1.status);

            //ID信息
            oLogistics.setReceiptPlanId(planNum); // 排单编号
            oLogistics.setOrderId(orderId);       // 订单编号
            oLogistics.setProId(proId);         // 商品ID
            oLogistics.setProName(proName);       // 商品名称
            oLogistics.setProPrice(subOrderItem.getProRelPrice());//商品单价
            //排单信息
            ReceiptPlan planVo = receiptPlanMapper.selectByPrimaryKey(oLogistics.getReceiptPlanId());
            if(planVo==null)throw new MessageException("排单信息未找到");
            if (null==planVo.getReturnOrderDetailId())throw new MessageException("退货明细未找到");

            OReturnOrderDetail returnOrderDetail = returnOrderDetailMapper.selectByPrimaryKey(planVo.getReturnOrderDetailId());
            String firstSn = returnOrderDetail.getBeginSn();
            String lastSn = returnOrderDetail.getEndSn();
            if (beginSn.compareTo(firstSn)<0 || beginSn.compareTo(lastSn)>0 || endSn.compareTo(firstSn)<0 || endSn.compareTo(lastSn)>0 || endSn.compareTo(beginSn)<0){
                log.info("与退货的sn不符合");
                throw new MessageException("与退货的sn不符合");
            }

            //排单的活动 下发到业务系统使用此活动
            OActivity oActivity_plan = oActivityMapper.selectByPrimaryKey(planVo.getActivityId());

            //商品信息从排单表里查
            oLogistics.setProCom(planVo.getProCom());// 厂家
            oLogistics.setProType(planVo.getProType());//排单添加商品类型
            oLogistics.setProModel(planVo.getModel());//型号

            oLogistics.setSendNum(new BigDecimal(sendProNum));  // 发货数量
            oLogistics.setLogCom(logCom);       // 物流公司
            oLogistics.setwNumber(wNumber);      // 物流单号
            oLogistics.setSnBeginNum(beginSn);   // 起始SN序列号
            oLogistics.setSnEndNum(endSn);     // 结束SN序列号
            oLogistics.setSendStatus(LogisticsSendStatus.none_send.code);
            log.info("导入物流数据{}" , JSONObject.toJSON(oLogistics));
            if (1 != oLogisticsService.insertImportData(oLogistics)) {
                throw new MessageException("排单编号为:"+planNum+"处理，插入物流信息失败");
            }

            //调用明细接口之前需要先去数据库进行查询是否已有数据
            if (null != stringList && stringList.size() > 0) {
                for (String snNum : stringList) {
                    //检查sn是否存在物流状态和记录状态
                    OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                    OLogisticsDetailExample.Criteria criteria = oLogisticsDetailExample.createCriteria();
                    criteria.andStatusEqualTo(OLogisticsDetailStatus.STATUS_FH.code);
                    criteria.andRecordStatusEqualTo(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                    criteria.andSnNumEqualTo(snNum);
                    List<OLogisticsDetail> oLogisticsDetails = logisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                    if (null != oLogisticsDetails && oLogisticsDetails.size() > 0) {
                        //说明已经存在数据
                        log.info(snNum+"此物流已经存在,正在发货中!!!");
                        throw new MessageException(snNum+"此物流已经存在,正在发货中!!!");
                    }
                }
            }
            // 调用明细接口 插入物流明细
            /*ResultVO resultVO = insertLogisticsDetail(oLogistics.getSnBeginNum(), oLogistics.getSnEndNum(),Integer.parseInt(beginSnCount),Integer.parseInt(endSnCount), oLogistics.getId(), user, planVo.getId());

            //插入成功更新排单信息
            if (resultVO.isSuccess()) {
                String id =  oLogistics.getReceiptPlanId();   // 排单编号
                if (null == id) {
                    throw new MessageException("排单ID查询失败！");
                } else {

                    ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(id);
                    OReturnOrderDetail returnOrderDetail1Info = returnOrderDetailMapper.selectByPrimaryKey(receiptPlan.getReturnOrderDetailId());

                    if (receiptPlan != null) {
                        if(receiptPlan.getSendProNum()==null || receiptPlan.getSendProNum().compareTo(BigDecimal.ZERO)==0) {// 发货数量
                            receiptPlan.setSendProNum(new BigDecimal(sendProNum));
                        }else{
                            receiptPlan.setSendProNum(receiptPlan.getSendProNum().add(new BigDecimal(sendProNum)));
                        }
                        receiptPlan.setRealSendDate(Calendar.getInstance().getTime());                          // 实际发货时间
                        receiptPlan.setPlanOrderStatus(new BigDecimal(PlannerStatus.YesDeliver.getValue()));    // 排单状态为已发货
                        if (receiptPlanMapper.updateByPrimaryKeySelective(receiptPlan)!= 1) {
                            throw new MessageException("更新排单数据失败！");
                        }
                        System.out.println("更新排单数据============================================" + JSONObject.toJSON(receiptPlan));
                    }

                    //流量卡不进行下发操作
                    if(oActivity!=null && com.ryx.credit.commons.utils.StringUtils.isNotBlank(oActivity_plan.getActCode()) && ("2204".equals(oActivity_plan.getActCode()) || "2004".equals(oActivity_plan.getActCode()) )  ){
                        log.info("导入物流数据,流量卡不进行下发操作，活动代码{}={}==========================================={}" ,oActivity_plan.getActCode(),oLogistics.getId(), JSONObject.toJSON(oLogistics));
                        return AgentResult.ok("流量卡不进行下发操作");
                    }
                    OOrder oOrder = oOrderMapper.selectByPrimaryKey(orderId);
                    PlatForm platForm =platFormMapper.selectByPlatFormNum(oOrder.getOrderPlatform());
                    //===============================================================================
                    //进行机具调整操作
                    if (PlatformType.whetherPOS(platForm.getPlatformType())){
                        log.info("======pos发货 更新库存记录:{}:{}",proType,stringList);
                        List<OLogisticsDetail> snList = (List<OLogisticsDetail>)resultVO.getObj();
                        if(null==oOrder){
                            throw new MessageException("查询订单数据失败！");
                        }
                        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
                        if(null==agentBusInfo){
                            throw new MessageException("查询订单业务数据失败！");
                        }
                        ImsTermAdjustDetail imsTermAdjustDetail = new ImsTermAdjustDetail();
                        imsTermAdjustDetail.setnOrgId(agentBusInfo.getBusNum());
                        imsTermAdjustDetail.setMachineId(oActivity_plan.getBusProCode());
                        OLogistics logistics =  oLogisticsMapper.selectByPrimaryKey(oLogistics.getId());
                        log.info("退货上传物流下发到POS系统:{}:{}:{}",user,logistics.getId(),snList.toString());
                        try {
                            AgentResult ar =  imsTermAdjustDetailService.insertImsTermAdjustDetail(snList,imsTermAdjustDetail);
                            if(ar.isOK()){
                                logistics.setSendStatus(Status.STATUS_1.status);
                                logistics.setSendMsg(ar.getMsg());
                                oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                            }else{
                                logistics.setSendStatus(Status.STATUS_2.status);
                                logistics.setSendMsg(ar.getMsg());
                                oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                            }
                        } catch (MessageException e) {
                            e.printStackTrace();
                            log.error("机具退货调整POS接口调用异常"+logistics.getId(),e);
                            logistics.setSendStatus(Status.STATUS_2.status);
                            logistics.setSendMsg(e.getMsg());
                            oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                        }catch (Exception e) {
                            e.printStackTrace();
                            log.error("机具退货调整POS接口调用异常"+logistics.getId(),e);
                            logistics.setSendStatus(Status.STATUS_2.status);
                            logistics.setSendMsg(e.getLocalizedMessage());
                            oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                        }
                    }else if (PlatformType.MPOS.code.equals(platForm.getPlatformType())){
                        log.info("======首刷发货 更新库存记录:{}:{}",proType,stringList);
                        //起始sn
                        OLogisticsDetailExample exampleOLogisticsDetailExamplestart = new OLogisticsDetailExample();
                        exampleOLogisticsDetailExamplestart.or().andSnNumEqualTo(oLogistics.getSnBeginNum()).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
                        List<OLogisticsDetail> logisticsDetailsstart = logisticsDetailMapper.selectByExample(exampleOLogisticsDetailExamplestart);
                        OLogisticsDetail detailstart = logisticsDetailsstart.get(0);

                        //结束sn
                        OLogisticsDetailExample exampleOLogisticsDetailExampleend = new OLogisticsDetailExample();
                        exampleOLogisticsDetailExampleend.or().andSnNumEqualTo(oLogistics.getSnEndNum()).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
                        List<OLogisticsDetail> logisticsDetailsend = logisticsDetailMapper.selectByExample(exampleOLogisticsDetailExampleend);
                        OLogisticsDetail detailend = logisticsDetailsend.get(0);

                        AdjustmentMachineVo vo = new AdjustmentMachineVo();
                        vo.setOptUser(user);
                        vo.setSnStart(detailstart.getSnNum()+detailstart.getTerminalidCheck());
                        vo.setSnEnd(detailend.getSnNum()+detailend.getTerminalidCheck());
                        vo.setSnNum(oLogistics.getSendNum().toString());

                        //发货订单的业务编号
                        OOrder order =  oOrderMapper.selectByPrimaryKey(oLogistics.getOrderId());
                        AgentBusInfo busInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
                        vo.setNewBusNum(busInfo.getBusNum());
                        vo.setPlatformType(platForm.getPlatformType());

                        //退货订单的业务编号
                        OReturnOrderDetail oReturnOrderDetail = returnOrderDetailMapper.selectByPrimaryKey(receiptPlan.getReturnOrderDetailId());

                        OOrder orderreturn =  oOrderMapper.selectByPrimaryKey(oReturnOrderDetail.getOrderId());
                        AgentBusInfo returnbusInfo = agentBusInfoMapper.selectByPrimaryKey(orderreturn.getBusId());
                        vo.setOldBusNum(returnbusInfo.getBusNum());
                        vo.setPlatformNum(returnbusInfo.getBusPlatform());
                        //新活动
                        vo.setNewAct(oActivity_plan.getBusProCode());

                        //老活动查询 老活动采用退货明细中的活动编号,如果没有活动编号从退货明细中查询订单里的活动，此处补差价后会出现问题，已添加actid进行修复，此处为兼容老的数据
                        if(oReturnOrderDetail.getActid()==null || StringUtils.isEmpty(oReturnOrderDetail.getActid())) {
                            OSubOrderExample old_OSubOrder = new OSubOrderExample();
                            old_OSubOrder.or()
                                    .andOrderIdEqualTo(oReturnOrderDetail.getOrderId())
                                    .andProIdEqualTo(oReturnOrderDetail.getProId())
                                    .andStatusEqualTo(Status.STATUS_1.status);
                            List<OSubOrder> list_osub_old = oSubOrderMapper.selectByExample(old_OSubOrder);

                            if (list_osub_old.size() == 0) {
                                throw new MessageException("退货机具活动信息未找到");
                            }
                            OSubOrder old_suborder = list_osub_old.get(0);
                            OSubOrderActivityExample example_old_activity = new OSubOrderActivityExample();
                            example_old_activity.or().andSubOrderIdEqualTo(old_suborder.getId()).andStatusEqualTo(Status.STATUS_1.status);
                            List<OSubOrderActivity> list_old_act = subOrderActivityMapper.selectByExample(example_old_activity);
                            if (list_old_act.size() == 0) {
                                throw new MessageException("退货机具活动信息未找到");
                            }
                            OSubOrderActivity old_act = list_old_act.get(0);
                            vo.setOldAct(old_act.getBusProCode());
                        }else {
                            OActivity return_sn_activity = oActivityMapper.selectByPrimaryKey(oReturnOrderDetail.getActid());
                            vo.setOldAct(return_sn_activity.getBusProCode());
                        }

                        //cxinfo 机具退货调整首刷接口调用
                        OLogistics logistics =  oLogisticsMapper.selectByPrimaryKey(oLogistics.getId());
                        //同平台下发，不同平台不下发
                        if(busInfo.getBusPlatform().equals(returnbusInfo.getBusPlatform())) {
                            try {
                                log.info("退货上传物流下发到首刷系统:{}:{}:{}:{}",user,logistics.getId(),vo.getSnStart(),vo.getSnEnd());
                                AgentResult mposXF = termMachineService.adjustmentMachine(vo);
                                log.info("机具退货调整首刷接口调用:{}:{}:{}:{}:{}",user,logistics.getId(),vo.getSnStart(),vo.getSnEnd(),mposXF.getMsg());
                                if (!mposXF.isOK()) {
                                    logistics.setSendStatus(Status.STATUS_2.status);
                                    logistics.setSendMsg(mposXF.getMsg());
                                    if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                        log.info("机具退货调整首刷接口调用STATUS_2更新数据库失败:{}:{}:{}:{}:{}",user,logistics.getId(),vo.getSnStart(),vo.getSnEnd(),mposXF.getMsg());
                                    }
                                }else{
                                    logistics.setSendStatus(Status.STATUS_1.status);
                                    logistics.setSendMsg(mposXF.getMsg());
                                    if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                        log.info("机具退货调整首刷接口调用STATUS_1更新数据库失败:{}:{}:{}:{}:{}",user,logistics.getId(),vo.getSnStart(),vo.getSnEnd(),mposXF.getMsg());
                                    }
                                }
                            }catch (MessageException e) {
                                e.printStackTrace();
                                logistics.setSendStatus(Status.STATUS_2.status);
                                logistics.setSendMsg(e.getMsg());
                                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                    log.info("机具退货调整首刷接口调用Exception更新数据库失败:{}",JSONObject.toJSONString(logistics));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                logistics.setSendStatus(Status.STATUS_2.status);
                                logistics.setSendMsg(e.getLocalizedMessage());
                                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                    log.info("机具退货调整首刷接口调用Exception更新数据库失败:{}",JSONObject.toJSONString(logistics));
                                }
                            }
                        }else{
                            logistics.setSendStatus(LogisticsSendStatus.dt_send.code);
                            logistics.setSendMsg("不同平台不下发，手动调整");
                            if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                                log.info("机具退货调整首刷接口调用Exception更新数据库失败:{}",JSONObject.toJSONString(logistics));
                            }
                        }
                    }else  if (PlatformType.SSPOS.code.equals(platForm.getPlatformType())){
                        log.info("======pos发货 更新库存记录:{}:{}",proType,stringList);
                        List<OLogisticsDetail> snList = (List<OLogisticsDetail>)resultVO.getObj();
                        if(null==oOrder){
                            throw new MessageException("查询订单数据失败！");
                        }
                        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
                        if(null==agentBusInfo){
                            throw new MessageException("查询订单业务数据失败！");
                        }

                        AdjustmentMachineVo vo = new AdjustmentMachineVo();
                        vo.setPlatformType(platForm.getPlatformType());
                            ImsTermAdjustDetail imsTermAdjustDetail = new ImsTermAdjustDetail();
                            imsTermAdjustDetail.setnOrgId(agentBusInfo.getBusNum());
                            imsTermAdjustDetail.setMachineId(oActivity_plan.getBusProCode());
                        OLogistics logistics =  oLogisticsMapper.selectByPrimaryKey(oLogistics.getId());
                        log.info("退货上传物流下发到POS系统:{}:{}:{}",user,logistics.getId(),snList.toString());
                        try {
                            vo.setImsTermAdjustDetail(imsTermAdjustDetail);
                            vo.setLogisticsDetailList(snList);
                            AgentResult ar = termMachineService.adjustmentMachine(vo);
                            if(ar.isOK()){
                                logistics.setSendStatus(Status.STATUS_1.status);
                                logistics.setSendMsg(ar.getMsg());
                                oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                            }else{
                                logistics.setSendStatus(Status.STATUS_2.status);
                                logistics.setSendMsg(ar.getMsg());
                                oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                            }
                        } catch (MessageException e) {
                            e.printStackTrace();
                            log.error("机具退货调整POS接口调用异常"+logistics.getId(),e);
                            logistics.setSendStatus(Status.STATUS_2.status);
                            logistics.setSendMsg(e.getMsg());
                            oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                        }catch (Exception e) {
                            e.printStackTrace();
                            log.error("机具退货调整POS接口调用异常"+logistics.getId(),e);
                            logistics.setSendStatus(Status.STATUS_2.status);
                            logistics.setSendMsg(e.getLocalizedMessage());
                            oLogisticsMapper.updateByPrimaryKeySelective(logistics);
                        }
                    }else  if (PlatformType.RDBPOS.code.equals(platForm.getPlatformType())){
                        //瑞大宝退货转发平台
                        // terminalNoStart  String    终端编号起始
                        // terminalNoEnd    String    终端编号结束
                        // agencyId         String    代理商A码
                        // isFreeze         String    是否执行冻结("1" 执行)
                        // id               String    id
                        // status           Integer   能否转发("1" 能转发 "2"已执行冻结)
                        // unableNum        Integer   不能转发的终端数量
                        //============================================================================================================================================================

                        Map<String, Object> reqMap = new HashMap<String, Object>();
                        reqMap.put("terminalNoStart",oLogistics.getSnBeginNum());   //终端编号起始
                        reqMap.put("terminalNoEnd",oLogistics.getSnEndNum());   //终端编号结束
                        reqMap.put("id",oLogistics.getwNumber());   //id
                        reqMap.put("agencyId",oLogistics.getSnEndNum());    //代理商A码
                        reqMap.put("isFreeze",oLogistics.getSnEndNum());    //是否执行冻结("1" 执行)
                        reqMap.put("status",oLogistics.getSnEndNum());      //能否转发("1" 能转发 "2"已执行冻结)
                        reqMap.put("unableNum",oLogistics.getSnEndNum());   //不能转发的终端数量

                        *//*try {
                            String json = JsonUtil.objectToJson(reqMap);
                            log.info("------------------------------------------>>>RDB退货下发查询参数:" + json);
                            String respResult = HttpClientUtil.doPostJsonWithException(AppConfig.getProperty("rdbpos_return_of_goods"), json);

                            if (!StringUtils.isNotBlank(respResult)) {
                                throw new Exception("瑞大宝退货下发查询接口返回值为空，请联系管理员！");
                            }

                            JSONObject respJson = JSONObject.parseObject(respResult);
                            if (!(null != respJson.getString("code") && null != respJson.getString("success") && respJson.getString("code").equals("0000") && respJson.getBoolean("success"))) {
                                log.info("------------------------------------------>>>RDB退货下发返回异常:" + respResult);
                                throw new Exception(null != respJson.getString("msg") ? respJson.getString("msg") : "瑞大宝，查询下发接口，返回值异常，请联系管理员!");
                            }

                            //查询成功，进行退货下发
                            reqMap.clear();
                            reqMap.put("taskId", logistics.getwNumber());
                            try {
                                String retJson = JsonUtil.objectToJson(reqMap);
                                String retString = HttpClientUtil.doPostJsonWithException(AppConfig.getProperty("rdbpos.checkTermResult"), retJson);
                                if (!StringUtils.isNotBlank(retString)) {
                                    throw new Exception("瑞大宝,查询,下发接口,返回值为空，请联系管理员！");
                                }

                                JSONObject resJson = JSONObject.parseObject(retString);
                                log.info("------------------------------------------>>>RDB退货下发查询接口返回值:" + retString);
                                log.info("------------------------------------------>>>退货要修改的明细具体信息:" + JsonUtil.objectToJson(listOLogisticsDetailSn));
                                if (null != resJson.getString("code") && resJson.getString("code").equals("0000") && null != resJson.getBoolean("success") && resJson.getBoolean("success")) {
                                    //机具,下发成功，更新物流明细为下发成功
                                    log.info("下发物流接口调用成功：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), resJson.getString("msg"));
                                    listOLogisticsDetailSn.forEach(detail -> {
                                        detail.setSendStatus(LogisticsDetailSendStatus.send_success.code);
                                        detail.setSbusMsg(resJson.getString("msg"));
                                        detail.setuTime(date);
                                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                                    });
                                    retMap.put("result", true);
                                    return retMap;
                                } else if (null != resJson.getString("code") && resJson.getString("code").equals("9999") && null != resJson.getBoolean("success") && !resJson.getBoolean("success")) {
                                    //机具,下发失败，更新物流明细为下发失败，更新物流信息未下发失败，禁止再次发送，人工介入
                                    List<Dict> dicts = dictOptionsService.dictList(DictGroup.EMAIL.name(), DictGroup.LOGISTICS_FAIL_EMAIL.name());
                                    String[] emailArr = new String[dicts.size()];
                                    for (int i = 0; i < dicts.size(); i++) {
                                        emailArr[i] = String.valueOf(dicts.get(i).getdItemvalue());
                                    }
                                    AppConfig.sendEmail(emailArr, "SN开始：" + logistics.getSnBeginNum() + ",SN结束：" + logistics.getSnEndNum(), "任务生成物流明细错误报警OsnOperateServiceImpl");
                                    //AppConfig.sendEmails("SN开始：" + logistics.getSnBeginNum() + ",SN结束：" + logistics.getSnEndNum(), "任务生成物流明细错误报警OsnOperateServiceImpl");
                                    log.info("下发物流接口调用失败：物流编号:{},批次编号:{},时间:{},信息:{}", logcId, batch, DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss"), resJson.getString("msg"));
                                    listOLogisticsDetailSn.forEach(detail -> {
                                        detail.setSendStatus(LogisticsDetailSendStatus.send_fail.code);
                                        detail.setSbusMsg(resJson.getString("msg"));
                                        detail.setuTime(date);
                                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                                    });
                                    retMap.put("result", false);
                                    return retMap;
                                } else if (null != resJson.getString("code") && resJson.getString("code").equals("2001") && null != resJson.getBoolean("success") && !resJson.getBoolean("success")) {
                                    //订单处理中，返回到明细处理，明细继续循环处理，一直到业务系统处理完成
                                    listOLogisticsDetailSn.forEach(detail -> {
                                        detail.setSendStatus(LogisticsDetailSendStatus.none_send.code);
                                        detail.setSbusMsg(resJson.getString("msg"));
                                        detail.setuTime(date);
                                        oLogisticsDetailMapper.updateByPrimaryKeySelective(detail);
                                    });
                                    retMap.put("code", "RDBPOS");
                                    retMap.put("result", false);
                                    return retMap;
                                } else {
                                    throw new Exception("瑞大宝，查询下发接口，返回值不符合要求，请联系管理员！");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                throw e;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            //发送异常邮件
                            List<Dict> dicts = dictOptionsService.dictList(DictGroup.EMAIL.name(), DictGroup.LOGISTICS_FAIL_EMAIL.name());
                            String[] emailArr = new String[dicts.size()];
                            for (int i = 0; i < dicts.size(); i++) {
                                emailArr[i] = String.valueOf(dicts.get(i).getdItemvalue());
                            }
                            AppConfig.sendEmail(emailArr, "瑞大宝，查询下发接口，发送请求失败：" + MailUtil.printStackTrace(e), "瑞大宝接口异常");
                            throw e;
                        }*//*
                        //============================================================================================================================================================
                    }else{
                        OLogistics logistics_send =oLogisticsMapper.selectByPrimaryKey(oLogistics.getId());
                        logistics_send.setSendStatus(LogisticsSendStatus.dt_send.code);
                        logistics_send.setSendMsg("未实现的业务平台物流");
                        if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics_send)){
                            log.info("手刷下发物流更新记录Exception失败{}",JSONObject.toJSONString(oLogistics));
                        }
                        AppConfig.sendEmails("beginSn:"+beginSn+",endSn:"+endSn+",历史退货物流未调用业务系统，平台类型与编号:"+platForm.getPlatformType()+","+platForm.getPlatformNum(), "历史退货物流未调用业务系统"+platForm.getPlatformType()+","+platForm.getPlatformNum());
                        log.info("beginSn:"+beginSn+",endSn:"+endSn+",历史退货物流未调用业务系统，平台类型与编号:"+platForm.getPlatformType()+","+platForm.getPlatformNum());
                    }
                }
            }else{
                    return AgentResult.fail(resultVO.getResInfo());
            }*/
        return AgentResult.ok();
    }

    /**
     * 检查上传物流excel列是否修改过
     * @param excel
     * @param db
     * @return
     */
    @Override
    public AgentResult checkRecordPlan(List<Object> excel,Map<String,Object> db){
        Object PLAN_NUM = db.get("PLAN_NUM");
        String [] col= ReceiptPlanReturnExportColum.ReceiptPlanExportColum_column.code.split(",");
        String [] title= ReceiptPlanReturnExportColum.ReceiptPlanExportColum_title.code.split(",");
        for (int i=0;i<18;i++){
            if(null==db.get(col[i]) || db.get(col[i]).toString().length()==0){
                continue;
            }
            if(excel.get(i)==null || com.ryx.credit.commons.utils.StringUtils.isBlank(excel.get(i).toString())){
                return AgentResult.fail(PLAN_NUM+title[i]+"有改动");
            }
            if(!(excel.get(i)+"").equals((db.get(col[i])+""))){
                return AgentResult.fail(PLAN_NUM+title[i]+"有改动");
            }
        }
        return AgentResult.ok();
    }

    /**
     * 根据SN查询业务信息
     * @param oLogisticsDetail
     * @return
     */
    @Override
    public AgentBusInfo queryBusInfoByLogDetail(OLogisticsDetail oLogisticsDetail){

        OOrder oOrder = oOrderMapper.selectByPrimaryKey(oLogisticsDetail.getOrderId());
        if(null==oOrder){
            return null;
        }
        AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
        return agentBusInfo;
    }

    @Override
    public Map selectByReturnDeId(String returnDetailsId) {
        Map map = returnOrderMapper.selectByReturnDeId(returnDetailsId);
        if (null==map){
            ResultVO.fail("无对应数据");
        }
        return map;
    }

    /**
     * 插入物流明细
     */
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class)
    public ResultVO insertLogisticsDetail(String startSn, String endSn, Integer begins, Integer finish, String logisticsId, String cUser, String planId) throws MessageException {
        ReceiptPlan planVo = receiptPlanMapper.selectByPrimaryKey(planId);
        String orderId = planVo.getOrderId();//订单ID
        String proId = planVo.getProId();//收货单商品id
        OReceiptPro oReceiptPro  = oReceiptProMapper.selectByPrimaryKey(proId);
        OSubOrderExample example = new OSubOrderExample();
        example.or().andOrderIdEqualTo(orderId).andProIdEqualTo(oReceiptPro.getProId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrder> oSubOrders = oSubOrderMapper.selectByExample(example);
        if(oSubOrders.size()==0){
            throw new MessageException("商品价格未能锁定");
        }
        OSubOrder oSubOrder = oSubOrders.get(0);
        OSubOrderActivityExample oSubOrderActivityExample = new OSubOrderActivityExample();
        oSubOrderActivityExample.or().andSubOrderIdEqualTo(oSubOrder.getId()).andProIdEqualTo(oSubOrder.getProId()).andStatusEqualTo(Status.STATUS_1.status);
        List<OSubOrderActivity>  OSubOrderActivitylist = subOrderActivityMapper.selectByExample(oSubOrderActivityExample);

        //排单的活动 下发到业务系统使用此活动
        OActivity oActivity_plan = oActivityMapper.selectByPrimaryKey(planVo.getActivityId());

        OOrder order = oOrderMapper.selectByPrimaryKey(oSubOrder.getOrderId());
        //1.起始SN序列号  2.结束SN序列号  3.开始截取的位数   4.结束截取的位数
        if (StringUtils.isBlank(startSn)) {
            log.info("起始SN序列号为空{}:", startSn);
            throw new ProcessException("起始SN序列号为空");
        }
        if (StringUtils.isBlank(endSn)) {
            log.info("结束SN序列号为空{}:", endSn);
            throw new ProcessException("结束SN序列号为空");
        }
        if (com.ryx.credit.commons.utils.StringUtils.isBlank(logisticsId)){
            log.info("物流id为空{}:", logisticsId);
            throw new ProcessException("物流id为空");
        }
        OLogisticsExample oLogisticsExample = new OLogisticsExample();
        OLogisticsExample.Criteria criteria = oLogisticsExample.createCriteria().andStatusEqualTo(Status.STATUS_1.status).andIdEqualTo(logisticsId);
        List<OLogistics> oLogistics = oLogisticsMapper.selectByExample(oLogisticsExample);
        if (null==oLogistics || oLogistics.size()==0){
            log.info("无此物流信息{}:", logisticsId);
            throw new ProcessException("无此物流信息");
        }
        OLogistics ol = oLogistics.get(0);
        if (!ol.getProCom().equals("联迪")) {
            if (null == begins) {
                throw new ProcessException("开始截取的位数为空");
            }
            if (null == finish) {
                throw new ProcessException("结束截取的位数为空");
            }
        }


        List<String> idList = oLogisticsService.idList(startSn, endSn, begins, finish,ol.getProCom());
        List<OLogisticsDetail> detailList = new ArrayList<>();
        if (null != idList && idList.size() > 0) {
            for (String idSn : idList) {

                //查询发货锁定
                OLogisticsDetailExample oLogisticsDetailExample = new OLogisticsDetailExample();
                oLogisticsDetailExample.or().andSnNumEqualTo(idSn)
                        .andStatusEqualTo(Status.STATUS_1.status)
                        .andRecordStatusEqualTo(Status.STATUS_2.status);
                oLogisticsDetailExample.setOrderByClause(" c_time desc ");

                List<OLogisticsDetail>  OLogisticsDetaillist_fahuo =  logisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                if(OLogisticsDetaillist_fahuo.size()>0) {
                   throw new MessageException(OLogisticsDetaillist_fahuo.get(0).getSnNum()+"已处于发货锁定状态");
                }

                //查询退货锁定
                oLogisticsDetailExample = new OLogisticsDetailExample();
                oLogisticsDetailExample.or().andSnNumEqualTo(idSn)
                        .andStatusEqualTo(Status.STATUS_2.status)
                        .andRecordStatusEqualTo(Status.STATUS_2.status);
                oLogisticsDetailExample.setOrderByClause(" c_time desc ");
                List<OLogisticsDetail>  OLogisticsDetaillist_tuihuo =  logisticsDetailMapper.selectByExample(oLogisticsDetailExample);
                OLogisticsDetail detail = new OLogisticsDetail();
                if(OLogisticsDetaillist_tuihuo.size()>0) {
                     detail = OLogisticsDetaillist_tuihuo.get(0);
                }

                //id，物流id，创建人，更新人，状态
                detail.setId(idService.genId(TabId.o_logistics_detail));
                detail.setOrderId(oSubOrder.getOrderId());
                detail.setOrderNum(order.getoNum());
                detail.setLogisticsId(logisticsId);
                detail.setProId(oSubOrder.getProId());
                detail.setProName(oSubOrder.getProName());
                detail.setSettlementPrice(oSubOrder.getProRelPrice());
                if(oActivity_plan!=null){
                    detail.setActivityId(oActivity_plan.getId());
                    detail.setActivityName(oActivity_plan.getActivityName());
                    detail.setgTime(oActivity_plan.getgTime());
                    detail.setBusProCode(oActivity_plan.getBusProCode());
                    detail.setBusProName(oActivity_plan.getBusProName());
                    detail.setTermBatchcode(oActivity_plan.getTermBatchcode());
                    detail.setTermBatchname(oActivity_plan.getTermBatchname());
                    detail.setTermtype(oActivity_plan.getTermtype());
                    detail.setTermtypename(oActivity_plan.getTermtypename());
                    detail.setSettlementPrice(oActivity_plan.getPrice());
                    detail.setPosType(oActivity_plan.getPosType());
                    detail.setPosSpePrice(oActivity_plan.getPosSpePrice());
                    detail.setStandTime(oActivity_plan.getStandTime());
                }
                detail.setSnNum(idSn);
                detail.setAgentId(order.getAgentId());
                detail.setcUser(cUser);
                detail.setuUser(cUser);
                detail.setcTime(Calendar.getInstance().getTime());
                detail.setuTime(Calendar.getInstance().getTime());
                detail.setOptType(OLogisticsDetailOptType.ORDER.code);
                detail.setOptId(orderId);
                OOrder oOrder = oOrderMapper.selectByPrimaryKey(orderId);
                detail.setBusId(oOrder.getBusId());
                if(StringUtils.isNotBlank(planVo.getReturnOrderDetailId())) {
                    OReturnOrderDetail detail1 = returnOrderDetailMapper.selectByPrimaryKey(planVo.getReturnOrderDetailId());
                    detail.setReturnOrderId(detail1.getReturnId());
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_LOC.code);
                }else{
                    detail.setStatus(OLogisticsDetailStatus.STATUS_FH.code);
                    detail.setRecordStatus(OLogisticsDetailStatus.RECORD_STATUS_VAL.code);
                }
                detail.setVersion(Status.STATUS_1.status);
                if (1 != logisticsDetailMapper.insertSelective(detail)) {
                    log.info("添加失败");
                    throw new ProcessException("添加失败");
                }
                detailList.add(detail);
            }
        }
        return ResultVO.success(detailList);
    }


    @Override
    public AgentResult sendReturnLgcInfoToBusSystem(String lgcId, String userId) throws Exception {
        OLogistics logistics = oLogisticsMapper.selectByPrimaryKey(lgcId);
        if(logistics.getSendStatus()!=null){
            if(logistics.getSendStatus().compareTo(Status.STATUS_2.status)!=0){
                return AgentResult.fail("只发送联动失败的物流");
            }
        }
        OLogisticsDetailExample example = new OLogisticsDetailExample();
        example.or().andLogisticsIdEqualTo(logistics.getId()).andRecordStatusEqualTo(Status.STATUS_1.status).andStatusEqualTo(Status.STATUS_1.status);
        List<OLogisticsDetail>  listDetails = logisticsDetailMapper.selectByExample(example);
        if(listDetails.size()<=0){
            return AgentResult.fail("物流明细为空");
        }
        if(listDetails.size()!=logistics.getSendNum().intValue()){
            log.info("物流下发发货数量不匹配");
            return AgentResult.fail("物流下发发货数量不匹配");
        }
        List<String> ids = new ArrayList<>();
        for (OLogisticsDetail listDetail : listDetails) {
            ids.add(listDetail.getSnNum());
        }
        //排单信息
        ReceiptPlan receiptPlan = receiptPlanMapper.selectByPrimaryKey(logistics.getReceiptPlanId());
        OLogisticsDetail detail = listDetails.get(0);
        if(StringUtils.isEmpty(receiptPlan.getReturnOrderDetailId())){
            return AgentResult.fail("没有退货子订单编号");
        }
        //进行机具调整操作
        OOrder oOrder = oOrderMapper.selectByPrimaryKey(logistics.getOrderId());
        if (null==oOrder) throw new MessageException("查询订单数据失败！");
        PlatForm platForm = platFormMapper.selectByPlatFormNum(oOrder.getOrderPlatform());

        log.info("PlatformType:{}",platForm.getPlatformType());
        //重新下发具体操作
        if (platForm.getPlatformType().equals(PlatformType.RDBPOS.code) || platForm.getPlatformType().equals(PlatformType.RJPOS.code)) {
            //瑞大宝平台重新下发，瑞+物流重新下发
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
            if (null==agentBusInfo) throw new MessageException("查询业务数据失败！");

            try {
                //更新物流明细
                OLogisticsDetail oLogisticsDetail = new OLogisticsDetail();
                oLogisticsDetail.setSendStatus(LogisticsDetailSendStatus.none_send.code);
                oLogisticsDetail.setStatus(Status.STATUS_1.status);
                oLogisticsDetail.setLogisticsId(logistics.getId());
                oLogisticsDetail.setSbusMsg("");
                int deleteInt = logistics.getSendNum().compareTo(BigDecimal.valueOf(logisticsDetailMapper.updateByLogisticsId(oLogisticsDetail)));
                if (deleteInt != 0) {
                    log.info("更新物流异常，物流明细和物流发送数量不同。");
                    throw new Exception("更新物流异常，物流明细和物流发送数量不同。");
                }
                //更新物流
                OLogistics updateLogistics = new OLogistics();
                updateLogistics.setId(logistics.getId());
                updateLogistics.setSendStatus(LogisticsSendStatus.gen_detail_sucess.code);
                updateLogistics.setSendMsg("");
                updateLogistics.setVersion(logistics.getVersion());
                if (1 != oLogisticsMapper.updateByPrimaryKeySelective(updateLogistics)) {
                    log.info("发货物流，重新发送，更新数据库失败:{},{},{}", logistics.getId(), logistics.getSnBeginNum(), logistics.getSnEndNum());
                    throw new Exception("更新物流状态发生异常！！！");
                }
            }catch (Exception e){
                e.printStackTrace();
                logistics.setSendMsg("下发异常");
                logistics.setSendStatus(Status.STATUS_2.status);
                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                    log.info("RDB下发物流更新失败Exception{}",JSONObject.toJSONString(logistics));
                }
                return AgentResult.fail(e.getLocalizedMessage());
            }
            return AgentResult.ok();
        //POS逻辑
        } else if (PlatformType.whetherPOS(platForm.getPlatformType())){
            log.info("======pos发货 更新库存记录:{}:{}",logistics.getProType(),ids);
            AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByPrimaryKey(oOrder.getBusId());
            if(null==agentBusInfo){
                throw new MessageException("查询订单业务数据失败！");
            }
            ImsTermAdjustDetail imsTermAdjustDetail = new ImsTermAdjustDetail();
            imsTermAdjustDetail.setnOrgId(agentBusInfo.getBusNum());
            imsTermAdjustDetail.setMachineId(detail.getBusProCode());
            log.info("退货上传物流下发到首刷系统:{}:{}:{}",userId,logistics.getId(),ids.toString());
            try {
                AgentResult ar =  imsTermAdjustDetailService.insertImsTermAdjustDetail(logistics,listDetails,imsTermAdjustDetail);
                if(ar.isOK()){
                    logistics.setSendStatus(Status.STATUS_1.status);
                    logistics.setSendMsg(ar.getMsg());
                    if(1==oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                        log.info("退货上传物流下发到首刷系统,更新物流失败:{}:{}:{}",userId,logistics.getId(),ids.toString());
                    }
                    return AgentResult.ok();
                }else{
                    logistics.setSendStatus(Status.STATUS_2.status);
                    logistics.setSendMsg(ar.getMsg());
                    if(1==oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                        log.info("退货上传物流下发到首刷系统,更新物流失败:{}:{}:{}",userId,logistics.getId(),ids.toString());
                    }
                    return AgentResult.fail(ar.getMsg());
                }
            } catch (MessageException e) {
                e.printStackTrace();
                log.error("机具退货调整POS接口调用异常"+logistics.getId(),e);
                logistics.setSendStatus(Status.STATUS_2.status);
                logistics.setSendMsg("机具退货调整POS接口调用异常");
                if(1==oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                    log.error("机具退货调整POS接口调用异常,更新物流失败"+logistics.getId(),e);
                }
                return AgentResult.fail(e.getMsg());
            }
        //手刷逻辑   机具退货调整首刷接口调用
        }else  if (PlatformType.MPOS.code.equals(platForm.getPlatformType())){
            log.info("======首刷发货 更新库存记录:{}:{}",logistics.getProType(),ids);
            //起始sn
            OLogisticsDetailExample exampleOLogisticsDetailExamplestart = new OLogisticsDetailExample();
            exampleOLogisticsDetailExamplestart.or().andSnNumEqualTo(logistics.getSnBeginNum()).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
            List<OLogisticsDetail> logisticsDetailsstart = logisticsDetailMapper.selectByExample(exampleOLogisticsDetailExamplestart);
            OLogisticsDetail detailstart = logisticsDetailsstart.get(0);

            //结束sn
            OLogisticsDetailExample exampleOLogisticsDetailExampleend = new OLogisticsDetailExample();
            exampleOLogisticsDetailExampleend.or().andSnNumEqualTo(logistics.getSnEndNum()).andTerminalidTypeEqualTo(PlatformType.MPOS.code);
            List<OLogisticsDetail> logisticsDetailsend = logisticsDetailMapper.selectByExample(exampleOLogisticsDetailExampleend);
            OLogisticsDetail detailend = logisticsDetailsend.get(0);

            AdjustmentMachineVo vo = new AdjustmentMachineVo();
            vo.setOptUser(userId);
            vo.setSnStart(detailstart.getSnNum()+(detailstart.getTerminalidCheck()==null?"":detailstart.getTerminalidCheck()));
            vo.setSnEnd(detailend.getSnNum()+(detailend.getTerminalidCheck()==null?"":detailend.getTerminalidCheck()));
            vo.setSnNum(logistics.getSendNum().toString());

            //发货订单的业务编号
            OOrder order =  oOrderMapper.selectByPrimaryKey(logistics.getOrderId());
            AgentBusInfo busInfo = agentBusInfoMapper.selectByPrimaryKey(order.getBusId());
            vo.setNewBusNum(busInfo.getBusNum());


            //退货订单的业务编号
            OReturnOrderDetail oReturnOrderDetail = returnOrderDetailMapper.selectByPrimaryKey(receiptPlan.getReturnOrderDetailId());
            OOrder orderreturn =  oOrderMapper.selectByPrimaryKey(oReturnOrderDetail.getOrderId());
            AgentBusInfo returnbusInfo = agentBusInfoMapper.selectByPrimaryKey(orderreturn.getBusId());
            vo.setOldBusNum(returnbusInfo.getBusNum());
            vo.setPlatformNum(returnbusInfo.getBusPlatform());

            //新活动
            vo.setNewAct(detail.getBusProCode());
            //老活动查询
            OSubOrderExample old_OSubOrder = new OSubOrderExample();
            old_OSubOrder.or()
                    .andOrderIdEqualTo(oReturnOrderDetail.getOrderId())
                    .andProIdEqualTo(oReturnOrderDetail.getProId())
                    .andStatusEqualTo(Status.STATUS_1.status);
            List<OSubOrder> list_osub_old = oSubOrderMapper.selectByExample(old_OSubOrder);

            if(list_osub_old.size()==0){
                throw new MessageException("退货机具活动信息未找到");
            }
            OSubOrder old_suborder  = list_osub_old.get(0);
            OSubOrderActivityExample example_old_activity = new OSubOrderActivityExample();
            example_old_activity.or().andSubOrderIdEqualTo(old_suborder.getId()).andStatusEqualTo(Status.STATUS_1.status);
            List<OSubOrderActivity>  list_old_act = subOrderActivityMapper.selectByExample(example_old_activity);
            if(list_old_act.size()==0){
                throw new MessageException("退货机具活动信息未找到");
            }
            OSubOrderActivity old_act = list_old_act.get(0);
            vo.setOldAct(old_act.getBusProCode());
            //同平台下发，不同平台不下发
            if(busInfo.getBusPlatform().equals(returnbusInfo.getBusPlatform())) {
                try {
                    log.info("退货上传物流下发到首刷系统:{}:{}:{}:{}",userId,logistics.getId(),vo.getSnStart(),vo.getSnEnd());
                    AgentResult mposXF = termMachineService.adjustmentMachine(vo);
                    log.info("机具退货调整首刷接口调用:{}:{}:{}:{}:{}",userId,logistics.getId(),vo.getSnStart(),vo.getSnEnd(),mposXF.getMsg());
                    if (!mposXF.isOK()) {
                        logistics.setSendStatus(Status.STATUS_2.status);
                        logistics.setSendMsg(mposXF.getMsg());
                        if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                            log.info("机具退货调整首刷接口调用STATUS_2更新数据库失败:{}:{}:{}:{}:{}",userId,logistics.getId(),vo.getSnStart(),vo.getSnEnd(),mposXF.getMsg());
                        }
                        return AgentResult.fail(mposXF.getMsg());
                    }else{
                        logistics.setSendStatus(Status.STATUS_1.status);
                        logistics.setSendMsg(mposXF.getMsg());
                        if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                            log.info("机具退货调整首刷接口调用STATUS_1更新数据库失败:{}:{}:{}:{}:{}",userId,logistics.getId(),vo.getSnStart(),vo.getSnEnd(),mposXF.getMsg());
                        }
                        return AgentResult.ok();
                    }
                }catch (MessageException e) {
                    e.printStackTrace();
                    logistics.setSendStatus(Status.STATUS_2.status);
                    logistics.setSendMsg(e.getMsg());
                    if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                        log.info("机具退货调整首刷接口调用Exception更新数据库失败:{}",JSONObject.toJSONString(logistics));
                    }
                    return AgentResult.fail(e.getLocalizedMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                    logistics.setSendStatus(Status.STATUS_2.status);
                    logistics.setSendMsg("退货物流业务系统联动调整异常");
                    if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                        log.info("机具退货调整首刷接口调用Exception更新数据库失败:{}",JSONObject.toJSONString(logistics));
                    }
                    return AgentResult.fail(e.getLocalizedMessage());
                }
            }else{
                logistics.setSendStatus(Status.STATUS_0.status);
                logistics.setSendMsg("不同平台不下发，手动调整");
                if(1!=oLogisticsMapper.updateByPrimaryKeySelective(logistics)){
                    log.info("机具退货调整首刷接口调用Exception更新数据库失败:{}",JSONObject.toJSONString(logistics));
                }
                return AgentResult.ok("不同平台不下发，手动调整");
            }
        }else{
            return AgentResult.ok("未实现的业务平台");
        }
    }

    /**
     * 根据退货单id查询发票信息
     * @param id
     * @return
     */
    @Override
    public List<OInvoice> findInvoiceById(String id){
        OInvoiceExample oInvoiceExample = new OInvoiceExample();
        OInvoiceExample.Criteria criteria = oInvoiceExample.createCriteria();
        criteria.andStatusEqualTo(Status.STATUS_1.status);
        criteria.andSrcIdEqualTo(id);
        criteria.andSrcTypeEqualTo(OInvoiceSrcType.RETURNORDER.code);
        List<OInvoice> oInvoices = invoiceMapper.selectByExample(oInvoiceExample);
        for (OInvoice oInvoice : oInvoices) {
            List<Attachment> attachments = attachmentMapper.accessoryQuery(oInvoice.getId(), AttachmentRelType.returnOrderInvoice.name());
            oInvoice.setAttachments(attachments);
        }
        return oInvoices;
    }

    @Override
    public OReturnOrder selectById(String id) {
        return returnOrderMapper.selectByPrimaryKey(id);
    }

    /**
     * 导出-退转发明细
     * @param map
     * @return
     */
    @Override
    public List<ReturnOrderVo> exportRetForDetail(Map map) {
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
        if (null!=map.get("agName") && StringUtils.isNotBlank(map.get("agName")+"")) {
            map.put("agName", "%"+map.get("agName")+"%");
        }
        if (null!=map.get("id") && StringUtils.isNotBlank(map.get("id")+"")) {
            map.put("id", map.get("id"));
        }
        if (null!=map.get("activityName") && StringUtils.isNotBlank(map.get("activityName")+"")) {
            map.put("activityName", map.get("activityName"));
        }
        if (null!=map.get("platform") && StringUtils.isNotBlank(map.get("platform")+"")) {
            map.put("platform", map.get("platform"));
        }
        if (null!=map.get("proModel") && StringUtils.isNotBlank(map.get("proModel")+"")) {
            map.put("proModel", map.get("proModel"));
        }
        if (null!=map.get("agUniqNum") && StringUtils.isNotBlank(map.get("agUniqNum")+"")) {
            map.put("agUniqNum", map.get("agUniqNum"));
        }
        if (null!=map.get("proType") && StringUtils.isNotBlank(map.get("proType")+"")) {
            map.put("proType", map.get("proType"));
        }
        if (null!=map.get("vender") && StringUtils.isNotBlank(map.get("vender")+"")) {
            map.put("vender", map.get("vender"));
        }
        if (null!=map.get("payMethod") && StringUtils.isNotBlank(map.get("payMethod")+"")) {
            map.put("payMethod", map.get("payMethod"));
        }
        if (null!=map.get("retSchedule") && StringUtils.isNotBlank(map.get("retSchedule")+"")) {
            map.put("retSchedule", map.get("retSchedule"));
        }
        if (null!=map.get("beginTime") && StringUtils.isNotBlank(map.get("beginTime")+"")) {
            map.put("beginTime", map.get("beginTime"));
        }
        if (null!=map.get("endTime") && StringUtils.isNotBlank(map.get("endTime")+"")) {
            map.put("endTime", map.get("endTime"));
        }

        List<ReturnOrderVo> receiptOrderVoList = returnOrderMapper.exportRetForDetail(map);
        for (ReturnOrderVo returnOrderVo : receiptOrderVoList) {
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), returnOrderVo.getVender());
            if (dict != null) {
                returnOrderVo.setVender(dict.getdItemname());
            }
            String return_order_id = returnOrderVo.getReturnOrderId();//退货单编号
            String receive_ag_doc_district = returnOrderVo.getAgDocDistrict();//接收方所属大区
            String receive_ag_doc_pro = returnOrderVo.getAgDocPro();//接收方所属省区
            if(receive_ag_doc_district==null || receive_ag_doc_district=="" || receive_ag_doc_district=="null"){
                returnOrderVo.setAgDocDistrict(receive_ag_doc_district==null?"":receive_ag_doc_district);
            }else{
                returnOrderVo.setAgDocDistrict(departmentService.getById(receive_ag_doc_district).getName()==null?"":departmentService.getById(receive_ag_doc_district).getName());
            }
            if(receive_ag_doc_pro==null || receive_ag_doc_pro=="" || receive_ag_doc_pro=="null"){
                returnOrderVo.setAgDocPro(receive_ag_doc_pro==null?"":receive_ag_doc_pro);
            }else{
                returnOrderVo.setAgDocPro(departmentService.getById(receive_ag_doc_pro).getName()==null?"":departmentService.getById(receive_ag_doc_pro).getName());
            }

            Map<String, Object> params_plan = new HashMap<String, Object>();
            params_plan.put("returnId", return_order_id);
            List<Map<String, Object>> receiptPlans = receiptPlanMapper.queryReveiveAgentData(params_plan);
            if (receiptPlans.size()!=0 && receiptPlans!=null) {
                for (Map<String, Object> receiptPlan : receiptPlans) {
                    String receive_activity_name = String.valueOf(receiptPlan.get("ACTIVITY_NAME"));//接收方活动类型
                    returnOrderVo.setReceiveActivityName(receive_activity_name==null?"":receive_activity_name);
                }
            }
        }
        log.info("导出退转发明细数据：", receiptOrderVoList);
        return receiptOrderVoList;
    }

    @Override
    public AgentResult updateReturnOrder(OReturnOrder oReturnOrder) {
        if (returnOrderMapper.updateByPrimaryKeySelective(oReturnOrder)!=1){
            return AgentResult.fail();
        }
        return AgentResult.ok();
    }

    /**
     * 冻结其他平台SN号码，防止代理商误操作
     * @param list
     */
    private void checkSnForOtherPlatform(List<Map> list) {
        try {
            //查询机具平台
            PlatForm platForm =platFormMapper.selectByOrderId((String) list.get(0).get("orderId"));
            if (null == platForm) throw new ProcessException("原订单信息不存在，请核实SN号码是否正确！");

            //RDB业务平台查询sn是否可以下发（后期维护扩展到其他系统校验）
            if(PlatformType.RDBPOS.code.equals(platForm.getPlatformType())) {
                Map<String, Object> reqMap = new HashMap<String, Object>();
                List<Map<String, Object>> reqList = new ArrayList<Map<String, Object>>();
                for (Map<String, Object> map : list) {
                    //瑞大宝查询、冻结、所需参数
                    AgentBusInfo agentBusInfo = agentBusInfoMapper.selectByOrderId((String) list.get(0).get("orderId"));
                    reqList.add(FastMap.fastMap("terminalNoStart", map.get("startSn")).
                            putKeyV("terminalNoEnd", map.get("endSn")).
                            putKeyV("id", agentBusInfo.getId()).
                            putKeyV("agencyId", agentBusInfo.getBusNum()) //代理商A码（必须15位）
                    );
                }
                reqMap.put("terminalNos", reqList);
                reqMap.put("isFreeze", "1"); //"1"执行冻结
                //冻结Sn、不允许代理商操作机具。
                log.info("请求RBD参数：{}" ,reqMap);
                try {
                    String json = JSONObject.toJSONString(reqMap);
                    log.info("RDB退货查询冻结参数:" + json);
                    String respResult = HttpClientUtil.doPostJsonWithException(AppConfig.getProperty("rdbpos_return_of_goods_freeze"), json);

                    if (!StringUtils.isNotBlank(respResult)) {
                        throw new Exception("瑞大宝退货下发查询接口返回值为空，请联系管理员！");
                    }

                    JSONObject respJson = JSONObject.parseObject(respResult);
                    if (!(null != respJson.getString("code") && null != respJson.getString("success") && respJson.getString("code").equals("0000") && respJson.getBoolean("success"))) {
                        log.info("RDB冻结退货SN返回异常:" + respResult);
                        throw new Exception(null != respJson.getString("msg") ? respJson.getString("msg") : "RDB业务平台冻结SN接口，返回值异常，请联系管理员！！！");
                    }
                } catch (Exception e) {
                    throw new ProcessException("冻结瑞大宝SN失败，瑞大宝接口异常，请联系管理员");
                }
            }
        }catch (ProcessException e) {
            throw e;
        }
    }
    /**
     * 查询扣款信息
     */
    public Map<String, Object> deductDetail(String returnId) throws ProcessException {
        Map<String, Object> map = new HashMap<>();
        //查询扣款款项
        ODeductCapitalExample deductCapitalExample = new ODeductCapitalExample();
        deductCapitalExample.or().andSourceIdEqualTo(returnId);
        List<ODeductCapital> deductCapitals = deductCapitalMapper.selectByExample(deductCapitalExample);

        map.put("deductCapitals", deductCapitals);
        return map;
    }
}
