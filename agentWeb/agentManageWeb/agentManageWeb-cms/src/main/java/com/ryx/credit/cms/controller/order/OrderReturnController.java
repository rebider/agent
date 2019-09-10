package com.ryx.credit.cms.controller.order;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelExportSXXSSF;
import com.ryx.credit.cms.util.ImportExcelUtil;
import com.ryx.credit.cms.util.MyUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.result.Result;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.order.OInvoice;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OReturnOrder;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.ReturnOrderVo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.agent.TaskApprovalService;
import com.ryx.credit.service.order.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: Zhang Lei
 * @Description: 退货
 * @Date: 9:52 2018/7/25
 */
@RequestMapping("order/return")
@Controller
public class OrderReturnController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OrderReturnController.class);
    private static String EXPORT_Logistics_PATH = AppConfig.getProperty("export.path");

    @Resource
    private BusinessPlatformService businessPlatformService;
    @Resource
    private IOrderReturnService orderReturnService;
    @Resource
    private OLogisticsService oLogisticService;
    @Resource
    private IAccountAdjustService accountAdjustService;
    @Autowired
    private TaskApprovalService taskApprovalService;
    @Autowired
    private OLogisticsDetailService logisticsDetailService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    protected RedisService redisService;

    /**
     * @Author: Zhang Lei
     * @Description: 申请退货页面
     * @Date: 9:53 2018/7/25
     */
    @RequestMapping("/page/create")
    public String createPage(HttpServletRequest request) {
        request.setAttribute("orderType",request.getParameter("orderType"));
        String key = RedisCachKey.APP_SPLIT+":"+getStringUserId();
        List<Map<String, Object>> maps = redisService.popListMap(key);
        if(maps.size()!=0){
            request.setAttribute("rKey",key);
        }
        return "order/orderReturn";
    }

    /**
     * @Author: Zhang Lei
     * @Description: 退货列表页面
     * @Date: 9:53 2018/7/25
     */
    @RequestMapping("/page/list")
    public String listPage(HttpServletRequest request) {
        orderDictData(request);
        optionsData(request);
        List<PlatForm> platFormList = ServiceFactory.businessPlatformService.queryAblePlatForm();
        request.setAttribute("platFormList", platFormList);
        return "order/orderReturnList";
    }


    /**
     * @Author: Zhang Lei
     * @Description: 退货工作流审批界面
     * @Date: 9:53 2018/7/25
     */
    @RequestMapping("/page/auditView")
    public String auditView(HttpServletRequest request, Model model, String returnId) {
        Map<String, Object> map = orderReturnService.view(returnId);
        //退货单
        OReturnOrder returnOrder = (OReturnOrder) map.get("returnOrder");
        model.addAttribute("returnOrder", returnOrder);
        //退货单明细
        model.addAttribute("returnDetails", map.get("returnDetails"));
        //扣款明细
        model.addAttribute("deductCapitals", map.get("deductCapitals"));
        //已排单明细
        model.addAttribute("receiptPlans", map.get("receiptPlans"));

        //查询是否有调账记录
        Map<String, Object> oAccountAdjusts = accountAdjustService.getAccountAdjustDetail(returnId, AdjustType.TKTH.adjustType, String.valueOf(getUserId()), returnOrder.getAgentId());
        model.addAttribute("oAccountAdjusts", oAccountAdjusts);

        if (oAccountAdjusts == null || oAccountAdjusts.size() <= 0) {
            model.addAttribute("haveAdjusted", false);
            //没有做过调账时，才进行调账预算
            String sid = request.getParameter("sid");
            if (sid.equals("sid-4528CEA4-998C-4854-827B-1842BBA5DB4B")) {
                Map<String, Object> adjustmap = accountAdjustService.adjust(false, returnOrder.getReturnAmo(), AdjustType.TKTH.adjustType, 1, returnOrder.getAgentId(), String.valueOf(getUserId()), returnId, PamentSrcType.TUIKUAN_DIKOU.code);
                model.addAttribute("planNows_df", adjustmap.get("planNows_df"));
                model.addAttribute("planNows_complate", adjustmap.get("planNows_complate"));
                model.addAttribute("takeoutList", adjustmap.get("takeoutList"));
                model.addAttribute("planNews", adjustmap.get("planNews"));
                model.addAttribute("takeAmt", adjustmap.get("takeAmt"));
                model.addAttribute("refund", adjustmap.get("refund"));
                model.addAttribute("planNows", adjustmap.get("planNows"));
            }
        } else {
            model.addAttribute("haveAdjusted", true);
        }

        optionsData(request);

        //查询发票信息
        List<OInvoice> invoiceList = orderReturnService.findInvoiceById(returnOrder.getId());
        model.addAttribute("invoiceList", invoiceList);

        return "order/orderReturnAudit";
    }


    /**
     * @Author: Zhang Lei
     * @Description: 退货详情查看带流程图（代理商使用）
     * @Date: 9:53 2018/7/25
     */
    @RequestMapping("/page/viewAgentIndex")
    public String viewAgentIndex(HttpServletRequest request, Model model, String returnId) {
        request.setAttribute("rt", returnId);
        request.setAttribute("busTypeImg", BusActRelBusType.refund.name());
        List<Map<String, Object>> actRecordList = queryApprovalRecord(returnId, BusActRelBusType.refund.name());
        request.setAttribute("actRecordList", actRecordList);
        optionsData(request);
        return "order/orderReturnAuditAgentView";
    }

    /**
     * @Author: Zhang Lei
     * @Description: 退货修改（工作流退回修改订单信息页面）
     * @Date: 9:53 2018/7/25
     */
    @RequestMapping("/page/orderReturnEdit")
    public String orderReturnEdit(HttpServletRequest request, Model model, String returnId) {
        Map<String, Object> map = orderReturnService.view(returnId);
        //退货单
        OReturnOrder returnOrder = (OReturnOrder) map.get("returnOrder");
        model.addAttribute("returnOrder", returnOrder);
        //退货单明细
        model.addAttribute("returnDetails", map.get("returnDetails"));
        optionsData(request);

        //查询发票信息
        List<OInvoice> invoiceList = orderReturnService.findInvoiceById(returnOrder.getId());
        model.addAttribute("invoiceList", invoiceList);

        return "order/orderReturnEdit";
    }


    /**
     * @Author: Zhang Lei
     * @Description: 退货明细页面-代理商审批
     * @Date: 9:53 2018/7/25
     */
    @RequestMapping("/page/viewAgent")
    public String viewAgent(HttpServletRequest request, Model model, String rt) {
        Map<String, Object> map = orderReturnService.view(rt);
        //退货单
        OReturnOrder returnOrder = (OReturnOrder) map.get("returnOrder");
        model.addAttribute("returnOrder", returnOrder);
        //退货单明细
        model.addAttribute("returnDetails", map.get("returnDetails"));
        //扣款明细
        model.addAttribute("deductCapitals", map.get("deductCapitals"));
        //已排单明细
        model.addAttribute("receiptPlans", map.get("receiptPlans"));

        //查询是否有调账记录
        Map<String, Object> oAccountAdjusts = accountAdjustService.getAccountAdjustDetail(rt, AdjustType.TKTH.adjustType, String.valueOf(getUserId()), returnOrder.getAgentId());
        model.addAttribute("oAccountAdjusts", oAccountAdjusts);

        optionsData(request);

        //查询发票信息
        List<OInvoice> invoiceList = orderReturnService.findInvoiceById(returnOrder.getId());
        model.addAttribute("invoiceList", invoiceList);

        return "order/orderReturnAuditAgent";
    }


    /**
     * @Author: Zhang Lei
     * @Description: 排单查询页面
     * @Date: 9:53 2018/7/25
     */
    @RequestMapping("/page/planerList")
    public String planerList(HttpServletRequest request,String returnDetailsId,Model model) {
        model.addAttribute("returnDetailsId",returnDetailsId);
        return "order/orderReturnPlannerList";
    }


    /**
     * @Author: Zhang Lei
     * @Description: 退货列表查询
     * @Date: 14:14 2018/7/25
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(HttpServletRequest request, OReturnOrder returnOrder) {
        String agentId = getAgentId();
        returnOrder.setAgentId(agentId);
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        pageInfo = orderReturnService.orderList(returnOrder, pageInfo);
        return pageInfo;
    }


    /**
     * @Author: Zhang Lei
     * @Description: 解析SN上传文件
     * @Date: 19:39 2018/7/25
     */
    @RequestMapping(value = "/analysisFile")
    @ResponseBody
    public ResultVO analysisFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        String orderType = request.getParameter("orderType");
        String rKey = request.getParameter("rKey");

        String agentId = getAgentId();
        try {
            List<List<Object>> excelList = new ArrayList<>();
            if(StringUtils.isBlank(rKey)){
                if (file == null) {
                    return ResultVO.fail("请上传文件");
                }
                InputStream in = file.getInputStream();
                excelList = ImportExcelUtil.getListByExcel(in, file.getOriginalFilename());
            }else{
                List<Map<String, Object>> redisList = redisService.popListMap(rKey);
                for (Map<String, Object> map : redisList) {
                    if(String.valueOf(map.get("flag")).equals(orderType)){
                        List<Object> list = new ArrayList<>();
                        list.add(map.get("startSn"));
                        list.add(map.get("endSn"));
                        list.add(map.get("num"));
                        excelList.add(list);
                    }
                }
            }
            if (null == excelList || excelList.size() == 0) {
                return ResultVO.fail("文档记录为空");
            }

            //返回对象
            Map<String, Object> retMap = new HashMap<>();
            //根据 "订单编号_商品编号" 作为唯一ID，统计每行退货信息
            List<Map<String, Object>> retList = new ArrayList<>();
            //所有退货商品总价格
            BigDecimal totalAmt = BigDecimal.ZERO;

            try {
                for (List<Object> objects : excelList) {
                    String startSn = "";
                    String endSn = "";
                    String snCount = "";

                    startSn = String.valueOf(objects.get(0)).trim();
                    endSn = String.valueOf(objects.get(1)).trim();
                    snCount = String.valueOf(objects.get(2)).trim();

                    if(StringUtils.isBlank(startSn)){
                        return ResultVO.fail("sn开始不能为空！");
                    }
                    if(StringUtils.isBlank(endSn)){
                        return ResultVO.fail("sn结束不能为空！");
                    }
                    if(StringUtils.isBlank(snCount)){
                        return ResultVO.fail("sn数量不能为空");
                    }

                    //每行ID
                    String orderId_productId = "";
                    Map<String, Object> newLine_detail = null;

                    //解析此行SN明细列表
                    List<String> snList = logisticsDetailService.querySnLList(startSn, endSn);
                    int querySnCount = logisticsDetailService.querySnCount(startSn, endSn);
                    if(null==snList){
                        return ResultVO.fail("请检查sn号段！");
                    }
                    if(snList.size()==0){
                        return ResultVO.fail("请检查sn号段有效性！");
                    }
                    if(!String.valueOf(querySnCount).equals(snCount)){
                        return ResultVO.fail("sn数量与sn号段不匹配！");
                    }
                    HashSet<Object> set = new HashSet<>();
                    for (String sn : snList) {
                        //根据sn查询物流信息
                        Map<String, Object> map = oLogisticService.getLogisticsBySn(sn, agentId);
                        //每个sn的订单物流明细
                        String norderId = (String) map.get("ORDERID");//订单id
                        //用订单的id去验证他们是否都是在同一个平台上
                         if (StringUtils.isBlank(norderId)){
                             return ResultVO.fail("没有相关的订单");
                         }
                        OOrder order = orderService.getById(norderId);
                        set.add(order.getOrderPlatform());
                        if (set.size()>1){
                            return ResultVO.fail("所发SN码不属于同一个平台");
                        }
                        List<AgentBusInfo> agentBusInfos = agentBusinfoService.selectExistsById(order.getBusId());
                        if(agentBusInfos.size()==0){
                            throw new ProcessException("SN不在平台下");
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
                        String actId = (String) map.get("ACTID");
                        String actName = (String) map.get("ACTNAME");

                        totalAmt = totalAmt.add(proprice);

                        // 新一个 "订单_商品"
                        if (!orderId_productId.equals(norderId + "_" + proId + "_" + actId)) {
                            orderId_productId = norderId + "_" + proId + "_" + actId;
                            if (newLine_detail != null) {
                                retList.add(newLine_detail);
                            }

                            //生成一个订单中一个商品信息
                            newLine_detail = new HashMap<>();
                            newLine_detail.put("id", orderId_productId);
                            newLine_detail.put("startSn", sn);
                            newLine_detail.put("endSn", sn);
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
//                            newLine_detail.put("begins", begins);
//                            newLine_detail.put("finish", finish);
                            newLine_detail.put("ordernum", ordernum);
                            newLine_detail.put("actId", actId);
                            newLine_detail.put("actName", actName);
                        } else {
                            //还是同一个 "订单_商品",  累加一个订单中一个商品的数量，总价
                            newLine_detail.put("endSn", sn);
                            newLine_detail.put("count", (int) newLine_detail.get("count") + 1);
                            newLine_detail.put("totalPrice", ((BigDecimal) newLine_detail.get("totalPrice")).add(proprice));
                        }
                    }
                    retList.add(newLine_detail);
                }
            } catch (ProcessException e) {
                return ResultVO.fail(e.getMessage());
            }
//            catch (MessageException e) {
//                return ResultVO.fail(e.getMessage());
//            }

            //返回总金额和每行退货信息
            retMap.put("list", retList);
            retMap.put("totalAmt", totalAmt);
            return ResultVO.success(retMap);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultVO.fail("解析excel文件失败");
    }


    /**
     * @Author: Zhang Lei
     * @Description: 退货申请
     * @Date: 19:39 2018/7/25
     */
    @RequestMapping(value = "/apply")
    @ResponseBody
    public Object apply(String productsJson, HttpServletRequest request, OReturnOrder returnOrder,String invoiceList) {

        if (StringUtils.isEmpty(productsJson)) {
            return renderError("请先上传SN号");
        }
        String agentId = getAgentId();
        List<Map> list = null;
        try {
            list = JSONObject.parseArray(productsJson, Map.class);
            if(list==null || list.size()==0){
                return renderError("退货商品信息错误");
            }
            //fixme 初步解决后期需要优化 退货sn提交申请能同时提交多条
            for (Map<String, Object> map : list) {
                String startSn = (String) map.get("startSn");
                String endSn = (String) map.get("endSn");
                Integer begins = (Integer) map.get("begins");
                Integer finish = (Integer) map.get("finish");
                String v = redisService.getValue("return_sn_lock:"+startSn+"_"+endSn);
                if(StringUtils.isNotEmpty(v)){
                    return renderError("sn已上传在处理中");
                }
                redisService.setValue("return_sn_lock:"+startSn+"_"+endSn,startSn,6L);
            }
            try {
                returnOrder.setAgentId(agentId);
                returnOrder.setReturnAmo(returnOrder.getGoodsReturnAmo());
                orderReturnService.apply(agentId, returnOrder, productsJson, getUserId() + "",invoiceList);
            } catch (ProcessException e) {
                return renderError(e.getMessage());
            } catch (Exception e) {
                return renderError(Constants.FAIL_MSG);
            }
        } catch (Exception e) {
            throw new ProcessException("解析退货商品失败");
        }




        return renderSuccess("退货申请成功");
    }

    /**
     * @Author: Zhang Lei
     * @Description: 退货订单修改
     * @Date: 19:39 2018/7/25
     */
    @RequestMapping(value = "/applyEdit")
    @ResponseBody
    public Object applyEdit(String productsJson, HttpServletRequest request, OReturnOrder returnOrder,String invoiceList) {

        if (StringUtils.isEmpty(productsJson)) {
            return renderError("请先上传SN号");
        }

        try {
            String agentId = getAgentId();
            returnOrder.setAgentId(agentId);
            returnOrder.setReturnAmo(returnOrder.getGoodsReturnAmo());
            orderReturnService.applyEdit(agentId, returnOrder, productsJson, getStringUserId(),invoiceList);
        } catch (ProcessException e) {
            return renderError(e.getMessage());
        } catch (Exception e) {
            return renderError(Constants.FAIL_MSG);
        }

        return renderSuccess("退货信息修改成功，确认无误后，请进行提交操作重新进入审批流程");
    }


    /**
     * @Author: Zhang Lei
     * @Description: 退货详情查询
     * @Date: 19:39 2018/7/25
     */
    @RequestMapping(value = "/view")
    @ResponseBody
    public ResultVO view(String returnId, HttpServletRequest request, String agentId) {

        if (StringUtils.isEmpty(returnId)) {
            return ResultVO.fail("退货标识不能为空");
        }
        Map<String, Object> map = null;

        try {
            map = orderReturnService.view(returnId);
        } catch (ProcessException e) {
            return ResultVO.fail(e.getMessage());
        } catch (Exception e) {
            return ResultVO.fail(Constants.FAIL_MSG);
        }

        return ResultVO.success(map);
    }


    /**
     * @Author: Zhang Lei
     * @Description: 保存扣款款项
     * @Date: 14:14 2018/7/25
     */
    @RequestMapping(value = "/saveCut")
    @ResponseBody
    public ResultVO saveCut(HttpServletRequest request, String returnId, String amt, String ctype) {
        Map<String, Object> map = null;
        try {
            map = orderReturnService.saveCut(returnId, amt, ctype);
        } catch (Exception e) {
            return ResultVO.fail(Constants.FAIL_MSG);
        }
        return ResultVO.success(map);
    }

    /**
     * @Author: Zhang Lei
     * @Description: 删除扣款款项
     * @Date: 14:14 2018/7/25
     */
    @RequestMapping(value = "/delCut")
    @ResponseBody
    public ResultVO delCut(HttpServletRequest request, String returnId, String cutId) {
        Map<String, Object> map = null;
        try {
            map = orderReturnService.delCut(returnId, cutId, getUserId() + "");
        } catch (Exception e) {
            return ResultVO.fail(Constants.FAIL_MSG);
        }
        return ResultVO.success(map);
    }


    /**
     * @Author: Zhang Lei
     * @Description: 执行扣款计划
     * @Date: 19:39 2018/7/25
     */
    @RequestMapping(value = "/doPayPlan")
    @ResponseBody
    public Object doPayPlan(HttpServletRequest request, String returnId) {
        Map<String, Object> map = orderReturnService.view(returnId);
        //退货单
        OReturnOrder returnOrder = (OReturnOrder) map.get("returnOrder");
        //应该退款金额
        BigDecimal amount = returnOrder.getReturnAmo()==null?new BigDecimal(0):returnOrder.getReturnAmo();
        //机具已抵扣金额
        BigDecimal takeout_amount = returnOrder.getTakeOutAmo()==null?new BigDecimal(0):returnOrder.getTakeOutAmo();
        try {
            Map<String, Object> oAccountAdjusts =   accountAdjustService.adjust(true, amount.subtract(takeout_amount), AdjustType.TKTH.adjustType, 1, returnOrder.getAgentId(), String.valueOf(getUserId()), returnId, PamentSrcType.TUIKUAN_DIKOU.code);
            Result rs = (Result)renderSuccess("执行退货方案成功");
            rs.setObj(oAccountAdjusts);
            return rs;
        } catch (ProcessException e) {
            return renderError(e.getMessage());
        } catch (Exception e) {
            return renderError(Constants.FAIL_MSG);
        }


    }


    /**
     * @Author: Zhang Lei
     * @Description: 工作流审批查看界面
     * @Date: 19:39 2018/7/25
     */
    @RequestMapping(value = "/approvalView")
    public String approvalView(HttpServletRequest request, String taskId, String proIns, String busType, String busId, String sid) {

        //审批任务需要
        request.setAttribute("taskId", taskId);
        request.setAttribute("proIns", proIns);
        request.setAttribute("busType", busType);
        request.setAttribute("busId", busId);
        request.setAttribute("sid", sid);

        List<Map<String, Object>> actRecordList = queryApprovalRecord(busId, BusActRelBusType.refund.name());
        request.setAttribute("actRecordList", actRecordList);

        //通用参数
        optionsData(request);
        //审批参数
        List<Dict> orderReturnParam = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ORDER_RETURN.name());
        request.setAttribute("orderReturn_param", orderReturnParam);

        OReturnOrder oReturnOrder = orderReturnService.selectById(busId);
        if(StringUtils.isNotEmpty(oReturnOrder.getOreturntype()) && oReturnOrder.getOreturntype().equals(Oreturntype.OLD.code)){
            return "activity/old_orderReturnApproveView";
        }
        return "activity/OrderReturnApproval";
    }


    /**
     * 处理任务
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/taskApproval")
    public ResultVO taskApproval(HttpServletRequest request, HttpServletResponse response, @RequestBody AgentVo agentVo) {

        AgentResult result = null;
        String failmsg = "处理失败";
        try {
            result = orderReturnService.approvalTask(agentVo, String.valueOf(getUserId()));
            if (!result.isOK()){
                failmsg =  result.getMsg();
            }
        } catch (ProcessException e) {
            failmsg = e.getMessage();
        } catch (Exception e) {
            logger.info("taskApproval处理任务异常:" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (result == null) {
                return ResultVO.fail(failmsg);
            }
            if (result.isOK()) {
                return ResultVO.success("处理成功");
            } else {
                return ResultVO.fail(failmsg);
            }
        }
    }

    @RequestMapping("orderReturnList")
    @ResponseBody
    public Object orderReturnList(HttpServletRequest request){
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        TreeMap map = getRequestParameter(request);
        map.put("begin", page.getBegin());
        map.put("end", page.getEnd());
        if(StringUtils.isNotEmpty(getAgentId()))
        map.put("agentId", getAgentId());
        map.put("userId", getStringUserId());
        return orderReturnService.orderReturnList(map, pageInfo);
    }

    /**
     * 退货信息:
     * 导出退货信息
     */
    @RequestMapping("/exportOrderReturn")
    public void exportOLogistics(HttpServletRequest request, HttpServletResponse response){
        TreeMap map = getRequestParameter(request);
        PageInfo pageInfo = orderReturnService.orderReturnList(map, new PageInfo());
        Map<String, Object> param = new HashMap<String, Object>(6);

        String title = "退货日期,退货单日期,退货单编号,原订单编号,唯一码,代理商,平台,机具类型,厂家,机型,活动,数量,单价,金额,扣款,退货开始sn号,退货结束sn号";
        String column = "ROTIME,RETURNTIME,ID,ORDER_ID,AG_UNIQ_NUM,AG_NAME,PLATFORM_NAME,PRO_TYPE,VENDER,PRO_MODEL,ACTIVITY_NAME,RETURN_COUNT,ORDER_PRICE,RETURN_PRICE,CUT_AMO,BEGIN_SN,END_SN";

        param.put("path", EXPORT_Logistics_PATH);
        param.put("title", title);
        param.put("column", column);
        param.put("dataList", pageInfo.getRows());
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }

    /**
     * 退货信息：导出退货单数据
     * @param request
     * @param response
     */
    @RequestMapping("/exportTHD")
    public void exportTHD(HttpServletRequest request, HttpServletResponse response){
        TreeMap map = getRequestParameter(request);
        PageInfo pageInfo = orderReturnService.orderReturnList(map, new PageInfo());
        Map<String, Object> param = new HashMap<String, Object>(6);

        String title = "退货单日期,退货单编号,原订单编号,唯一码,代理商,平台,机具类型,厂家,机型,活动,数量,退款总金额,扣款,抵欠款金额,退打款金额,退打款账户,收款人";
        String column = "RETURNTIME,ID,ORDER_ID,AG_UNIQ_NUM,AG_NAME,PLATFORM_NAME,PRO_TYPE,VENDER,PRO_MODEL,ACTIVITY_NAME,RETURN_COUNT,RETURN_PRICE,CUT_AMO,TAKE_OUT_AMO,REL_RETURN_AMO,CLO_BANK_ACCOUNT,CLO_REALNAME";

        param.put("path", EXPORT_Logistics_PATH);
        param.put("title", title);
        param.put("column", column);
        param.put("dataList", pageInfo.getRows());
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }

    /**
     * 退货：导出退转发明细
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("exportRetForDetail")
    public void exportRetForDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        FastMap fastMap = FastMap.fastMap("agName", request.getParameter("agName"))
                .putKeyV("id", request.getParameter("id"))
                .putKeyV("activityName", request.getParameter("activityName"))
                .putKeyV("platform", request.getParameter("platform"))
                .putKeyV("proModel", request.getParameter("proModel"))
                .putKeyV("agUniqNum", request.getParameter("agUniqNum"))
                .putKeyV("proType", request.getParameter("proType"))
                .putKeyV("vender", request.getParameter("vender"))
                .putKeyV("payMethod", request.getParameter("payMethod"))
                .putKeyV("retSchedule", request.getParameter("retSchedule"))
                .putKeyV("beginTime", request.getParameter("beginTime"))
                .putKeyV("endTime", request.getParameter("endTime"));

        //查询当前用户的退转发明细数据
        fastMap.putKeyV("userId", getUserId());
        List<ReturnOrderVo> returnOrderVos = orderReturnService.exportRetForDetail(fastMap);
        String filePath = "C:/upload/";
        String filePrefix = "Return";
        int flushRows = 100;
        List<String> fieldNames = null;
        List<String> fieldCodes = null;
        //导出数据的title
        fieldNames = new ArrayList<String>();
        fieldCodes = new ArrayList<String>();
        fieldNames.add("退货代理商平台号");
        fieldCodes.add("busNum");
        fieldNames.add("退货代理商唯一码");
        fieldCodes.add("agUniqNum");
        fieldNames.add("厂家");
        fieldCodes.add("vender");
        fieldNames.add("型号");
        fieldCodes.add("proModel");
        fieldNames.add("退货数量");
        fieldCodes.add("returnCount");
        fieldNames.add("价格");
        fieldCodes.add("orderPrice");
        fieldNames.add("退货活动类型");
        fieldCodes.add("activityName");
        fieldNames.add("接收数量");
        fieldCodes.add("receiveSendNum");
        fieldNames.add("接收订单编号");
        fieldCodes.add("receiveOrderId");
        fieldNames.add("接收代理商唯一码");
        fieldCodes.add("receiveAgentId");
        fieldNames.add("接收代理商");
        fieldCodes.add("receiveAgentName");
        fieldNames.add("接收活动类型");
        fieldCodes.add("receiveActivityName");
        fieldNames.add("退货扣款总金额");
        fieldCodes.add("cutAmo");
        fieldNames.add("省");
        fieldCodes.add("addrProvince");
        fieldNames.add("市");
        fieldCodes.add("addrCity");
        fieldNames.add("区");
        fieldCodes.add("addrDistrict");
        fieldNames.add("地址");
        fieldCodes.add("addrDetail");
        fieldNames.add("物流单号");
        fieldCodes.add("wnumber");
        fieldNames.add("负责大区");
        fieldCodes.add("agDocDistrict");
        fieldNames.add("负责省区");
        fieldCodes.add("agDocPro");

        ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
        //执行导出
        excelExportSXXSSF.writeDatasByObject(returnOrderVos);
        String filename = filePrefix + "_" + MyUtil.getCurrentTimeStr() + ".xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        excelExportSXXSSF.getWb().write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

}
