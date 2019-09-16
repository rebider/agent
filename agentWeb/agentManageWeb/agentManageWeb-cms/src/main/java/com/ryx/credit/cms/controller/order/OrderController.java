package com.ryx.credit.cms.controller.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.*;
import com.ryx.credit.cms.util.ExcelExportSXXSSF;
import com.ryx.credit.cms.util.MyUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentoutVo;
import com.ryx.credit.pojo.admin.vo.OrderFormVo;
import com.ryx.credit.pojo.admin.vo.OrderoutVo;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.order.AddressService;
import com.ryx.credit.service.order.OrderActivityService;
import com.ryx.credit.service.order.OrderService;
import com.ryx.credit.service.order.ProductService;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.*;

/**
 * 订单控制层
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/7/16 15:30
 */
@RequestMapping("order")
@Controller
public class OrderController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderActivityService orderActivityService;
    @Autowired
    private BusinessPlatformService businessPlatformService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private ProductService productService;

    private static String EXPORT_Order_PATH = AppConfig.getProperty("export.path");


    @RequestMapping(value = "toOrderList")
    public String toOrderList(HttpServletRequest request,OProduct product){
        orderDictData(request);
        optionsData(request);
        List<PlatForm> platFormList = ServiceFactory.businessPlatformService.queryAblePlatForm();
        request.setAttribute("platFormList", platFormList);
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        request.setAttribute("agStatusList", agStatusList);
        return "order/orderList";
    }

    /**
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "orderList")
    @ResponseBody
    public Object orderList(HttpServletRequest request){

        Page pageInfo = pageProcess(request);

        FastMap par = FastMap.fastMap("oNum",request.getParameter("oNum"))
                .putKeyV("agentId",request.getParameter("agentId"))
                .putKeyV("agName",request.getParameter("agName"))
                .putKeyV("id",request.getParameter("id"))
                .putKeyV("platformNum",request.getParameter("platform"))
                .putKeyV("agUniqNum",request.getParameter("agUniqNum"))
                .putKeyV("busNum",request.getParameter("busNum"))//厂商
                .putKeyV("proType",request.getParameter("proType"))//机具类型
                .putKeyV("vender",request.getParameter("vender"))//厂商
                .putKeyV("model",request.getParameter("model"))//机型
                .putKeyV("payMethod",request.getParameter("payMethod"))//结算方式
                .putKeyV("agDocDistrict",request.getParameter("agDocDistrict"))//结算方式
                .putKeyV("agDocPro",request.getParameter("agDocPro"))//结算方式
                .putKeyV("activityName",request.getParameter("activityName"))//活动名称
                .putKeyV("reviewStatus",request.getParameter("reviewStatus"));//审批状态

        if(StringUtils.isNotBlank(request.getParameter("beginTime")) && StringUtils.isNotBlank(request.getParameter("endTime"))){
            par.putKeyV("beginTime", request.getParameter("beginTime"));
            par.putKeyV("endTime",request.getParameter("endTime"));
        }

        //查询当前用户的订单
        par.putKeyV("userId",getUserId());
        PageInfo resultPageInfo = orderService.orderList(par,pageInfo);
        return resultPageInfo;
    }


    /**
     * order/toALLOrderList
     * @param request
     * @param product
     * @return
     */
    @RequestMapping(value = "toALLOrderList")
    public String toALLOrderList(HttpServletRequest request,OProduct product){
        orderDictData(request);
        optionsData(request);
        List<PlatForm> platFormList = ServiceFactory.businessPlatformService.queryAblePlatForm();
        request.setAttribute("platFormList", platFormList);
        List<Dict> agStatusList = ServiceFactory.dictOptionsService.dictList(DictGroup.AGENT.name(), DictGroup.AG_STATUS_I.name());
        request.setAttribute("agStatusList", agStatusList);
        return "order/allOrderList";
    }

    @RequestMapping(value = "allOrderList")
    @ResponseBody
    public Object allOrderList(HttpServletRequest request){
        Page pageInfo = pageProcess(request);
        FastMap par = FastMap.fastMap("oNum",request.getParameter("oNum"))
                .putKeyV("agentId",request.getParameter("agentId"))
                .putKeyV("agName",request.getParameter("agName"))
                .putKeyV("id",request.getParameter("id"))
                .putKeyV("platformNum",request.getParameter("platform"))
                .putKeyV("agUniqNum",request.getParameter("agUniqNum"))
                .putKeyV("busNum",request.getParameter("busNum"))//厂商
                .putKeyV("proType",request.getParameter("proType"))//机具类型
                .putKeyV("vender",request.getParameter("vender"))//厂商
                .putKeyV("model",request.getParameter("model"))//机型
                .putKeyV("payMethod",request.getParameter("payMethod"))//结算方式
                .putKeyV("agDocDistrict",request.getParameter("agDocDistrict"))//结算方式
                .putKeyV("agDocPro",request.getParameter("agDocPro"))//结算方式
                .putKeyV("activityName",request.getParameter("activityName"))//活动名称
                .putKeyV("reviewStatus",request.getParameter("reviewStatus"))//审批状态
                .putKeyV("userId",getUserId())//登录用户
                .putKeyV("notLookTem",request.getParameter("notLookTem"));//不看暂存
        if(StringUtils.isNotBlank(request.getParameter("beginTime")) && StringUtils.isNotBlank(request.getParameter("endTime"))){
            par.putKeyV("beginTime", request.getParameter("beginTime"));
            par.putKeyV("endTime",request.getParameter("endTime"));
        }
        PageInfo resultPageInfo = orderService.allOderList(par,pageInfo);
        return resultPageInfo;
    }


    /**
     * /order/toAgentOrderList
     * @param request
     * @param product
     * @return
     */
    @RequestMapping(value = "toAgentOrderList")
    public String toAgentOrderList(HttpServletRequest request,OProduct product){
        orderDictData(request);
        optionsData(request);
        List<PlatForm> platFormList = ServiceFactory.businessPlatformService.queryAblePlatForm();
        request.setAttribute("platFormList", platFormList);
        return "order/agentOrderList";
    }

    @RequestMapping(value = "agentOrderList")
    @ResponseBody
    public Object agentOrderList(HttpServletRequest request){
        Page pageInfo = pageProcess(request);
        FastMap par = FastMap.fastMap("oNum",request.getParameter("oNum"))
                .putKeyV("agentId",getAgentId())
                .putKeyV("id",request.getParameter("id"))
                .putKeyV("userId",getUserId())//登录用户
                .putKeyV("agUniqNum",request.getParameter("agUniqNum"));
        if(StringUtils.isNotBlank(request.getParameter("beginTime")) && StringUtils.isNotBlank(request.getParameter("endTime"))){
            par.putKeyV("beginTime", request.getParameter("beginTime"));
            par.putKeyV("endTime",request.getParameter("endTime"));
        }
        PageInfo resultPageInfo = orderService.agentOderList(par,pageInfo);
        return resultPageInfo;
    }







    @RequestMapping("/importPage")
    public String importPage(){
        return "order/oLogisticsImportReturn";
    }

    /**
     * 订单管理:
     * 导出订单信息
     */
   /* @RequestMapping(value = "exportOrder")
    public void exportOrder(HttpServletRequest request, HttpServletResponse response){
        TreeMap map = getRequestParameter(request);
        PageInfo pageInfo = orderService.getOrderList(map, new PageInfo());
        Map<String, Object> param = new HashMap<String, Object>(6);
        
        String title = "订单日期,订单编号,代理商名称,唯一编号,产品,机型,数量,价格,总价,备注,联系人,电话,收货地址";
        String column = "O_APYTIME,O_NUM,AG_NAME,AG_UNIQ_NUM,PRO_NAME,MODEL,PRO_NUM,PRO_PRICE,O_AMO,EXPRESS_REMARK,ADDR_REALNAME,ADDR_MOBILE,ADDR_DETAIL";

        param.put("path", EXPORT_Order_PATH);
        param.put("title", title);
        param.put("column", column);
        param.put("dataList", pageInfo.getRows());
        param.put("response", response);
        ExcelUtil.downLoadExcel(param);
    }*/

    /**
     *  /order/updateOrderView
     * @param request
     * @param response
     * @param orderId
     * @param agentId
     * @return
     */
    @RequestMapping(value = "updateOrderView")
    public String updateOrderView(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestParam("orderId")String orderId,
                                  @RequestParam("agentId")String agentId){
        try {
            //业务平台参数
            List<PlatForm> platForms = businessPlatformService.queryAblePlatForm();
            request.setAttribute("platForms", platForms);
            //结算方式
            List<Dict> settlement_type = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.SETTLEMENT_TYPE.name());
            request.setAttribute("settlementType", settlement_type);
            //收款公司
            List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
            request.setAttribute("recCompList", payComp_list);
            List<OActivity> allActivityList = orderActivityService.allActivity();
            request.setAttribute("allActivityList", JSONArray.toJSONString(allActivityList));
            AgentResult res =  orderService.loadAgentInfo(orderId);
            if(res.isOK()) {
                request.setAttribute("data", res.getData());
                //线下打款信息
                List<OCashReceivables> listoCashReceivables  = (List<OCashReceivables>)((FastMap)res.getData()).get("oCashReceivables");
                request.setAttribute("paylist", listoCashReceivables);
            }
            AgentResult ar = agentService.isAgent(getUserId()+"");
            request.setAttribute("isagent", ar);

            List<Map> listPlateform = agentBusinfoService.agentBus(agentId,getUserId());
            request.setAttribute("listPlateform", listPlateform);
            request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
            request.setAttribute("payTypeSelect", PayType.getYHHKOption());
            optionsData(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "order/orderUpdate";
    }


    /**
     * /order/updateOrderAction
     * @param request
     * @param response
     * @param agentVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updateOrderAction")
    public AgentResult updateOrderAction(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @RequestBody OrderFormVo agentVo){
        try {
            logger.info("用户{}修改订单{}",JSONObject.toJSONString(agentVo));
            return  orderService.updateOrder(agentVo,getUserId()+"");
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof MessageException){
                return AgentResult.fail(((MessageException)e).getMsg());
            }
            return AgentResult.fail("失败");
        }

    }


    /**
     * 订单配货界面
     * /order/distributionView
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "distributionView")
    public String distributionView(HttpServletRequest request,
                                   HttpServletResponse response){
        request.setAttribute("orderId",request.getParameter("orderId"));
        request.setAttribute("agentId",request.getParameter("agentId"));
        return "order/orderDistribution";
    }


    /**
     * 订单配货数据
     * /order/distributionView
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "distributionViewData")
    public AgentResult distributionViewData(HttpServletRequest request,
                                            HttpServletResponse response){
        List<Map<String, Object>> sub =   orderService.querySubOrderInfoList(request.getParameter("agentId"),request.getParameter("orderId"));
        List<Map<String, Object>> peiHuo =   orderService.queryHavePeiHuoProduct(request.getParameter("agentId"),request.getParameter("orderId"));
        PageInfo page = new PageInfo(1,Integer.MAX_VALUE);
        OAddress oa = new OAddress();
        oa.setuId(getUserId()+"");
        PageInfo pageInfo = addressService.queryAddressList(page,oa);
        return AgentResult.ok(FastMap.fastMap("sub",sub).putKeyV("peiHuo",peiHuo).putKeyV("address",pageInfo.getRows()));
    }


    /**
     * 配货操作
     * /order/peihuoAction
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "peihuoAction")
    public AgentResult peihuoAction(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String orderId,
                                    String addressId,
                                    String agentId,
                                    String proId,
                                    Integer sendNum){
        if(StringUtils.isBlank(orderId)
                || StringUtils.isBlank(agentId)
                || StringUtils.isBlank(proId)
                || sendNum==null
                || sendNum<=0) {

            return AgentResult.fail("参数错误");
        }
        if(StringUtils.isBlank(addressId)) {
            return AgentResult.fail("请选择地址，或者前往地址管理里进行添加。");
        }

        if(!RegexUtil.checkInt(sendNum+"")){
            return AgentResult.fail("发送数量有误");
        }
        OProduct product = productService.findById(proId);
        if (null==product){
            return AgentResult.fail("无此商品信息");
        }
//        if (product.getProType().equals(PlatformType.POS.code)){
////                POS必须是10的倍数
//            if(sendNum%10!=0  && sendNum!=0){
//                logger.info("POS的数量必须是10的倍数");
//                return AgentResult.fail("POS的数量必须是10的倍数");
//            }
//
//        }else if(product.getProType().equals(PlatformType.MPOS.code)){
////                手刷必须是100的倍数
//            if(sendNum%100!=0 && sendNum!=0){
//                logger.info("手刷的数量必须是100的倍数");
//                return AgentResult.fail("手刷的数量必须是100的倍数");
//            }
//        }else if(null!=product.getProCode() && product.getProCode().equals("206")){
////                 流量卡必须100张起订
//            if(sendNum<100){
//                logger.info("流量卡的数量必须100张起订");
//                return AgentResult.fail("流量卡的数量必须100张起订");
//            }
//        }
        OReceiptOrder var1 = new OReceiptOrder();
        var1.setOrderId(orderId);
        var1.setAddressId(addressId);
        var1.setAgentId(agentId);
        var1.setuUser(getUserId()+"");
        OReceiptPro var2 = new OReceiptPro();
        var2.setProId(proId);
        var2.setcUser(getUserId()+"");
        var2.setuUser(getUserId()+"");
        int var3 = sendNum;
        try {
            return orderService.subOrderPeiHuo(var1,var2,var3);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof MessageException){
                return AgentResult.fail(((MessageException) e).getMsg());
            }
            return AgentResult.fail(e.getLocalizedMessage());
        }

    }


    /**
     * 更新发货商品
     * /order/updatepeihuoActionItem
     * @param request
     * @param response
     * @param id
     * @param proNum
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "updatepeihuoActionItem")
    public AgentResult updatepeihuoActionItem(HttpServletRequest request,
                                              HttpServletResponse response,
                                              String id,
                                              Integer proNum){
        if(StringUtils.isBlank(id)
                || proNum==null
                || proNum<0) {

            return AgentResult.fail("商品数量有误");
        }
        if(!RegexUtil.checkInt(proNum+"")){
            return AgentResult.fail("商品数量有误");
        }
        OReceiptPro var1 = new OReceiptPro();
        var1.setId(id);
        var1.setcUser(getUserId()+"");
        var1.setuUser(getUserId()+"");
        var1.setProNum(new BigDecimal(proNum));
        try {
            return orderService.subOrderPeiHuoUpdate(var1);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof MessageException){
                return AgentResult.fail(((MessageException) e).getMsg());
            }
            return AgentResult.fail(e.getLocalizedMessage());
        }

    }


    @ResponseBody
    @RequestMapping(value = "sureSendAction")
    public AgentResult sureSendAction(HttpServletRequest request,
                                              HttpServletResponse response,
                                              String id,Integer proNum){
        if(StringUtils.isBlank(id)) {

            return AgentResult.fail("参数错误");
        }
        if(StringUtils.isBlank(id)
                || proNum==null
                || proNum<0) {

            return AgentResult.fail("商品数量有误");
        }
        if(!RegexUtil.checkInt(proNum+"")){
            return AgentResult.fail("商品数量有误");
        }

        OReceiptPro var1 = new OReceiptPro();
        var1.setId(id);
        var1.setcUser(getUserId()+"");
        var1.setuUser(getUserId()+"");
        var1.setProNum(new BigDecimal(proNum));
        var1.setReceiptProStatus(OReceiptStatus.WAITING_LIST.code);
        try {
            return orderService.subOrderPeiHuoUpdate(var1);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof MessageException){
                return AgentResult.fail(((MessageException) e).getMsg());
            }
            return AgentResult.fail(e.getLocalizedMessage());
        }

    }


    @ResponseBody
    @RequestMapping(value = "sureSendActionAll")
    public AgentResult sureSendActionAll(HttpServletRequest request,
                                         HttpServletResponse response,
                                         String orderId,
                                         String agentId){
        if(StringUtils.isBlank(orderId)) {
            return AgentResult.fail("参数错误");
        }
        try {
            return orderService.subOrderPeiHuoUpdateStatus(orderId,agentId);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof MessageException){
                return AgentResult.fail(((MessageException) e).getMsg());
            }
            return AgentResult.fail(e.getLocalizedMessage());
        }

    }


    /**
     * order/settmentPriceSelect
     * 结算价填写界面，需要系统配置
     * @param req
     * @return
     */
    @RequestMapping(value = "settmentPriceSelect")
    public String settmentPriceSelect(HttpServletRequest req){
        List<Dict>  set_type = dictOptionsService.dictList(DictGroup.ORDER.name(),DictGroup.SETTLEMENT_PRICESTR.name());
        req.setAttribute(DictGroup.SETTLEMENT_PRICESTR.name(),set_type);
        return "order/settmentPriceSelect";
    }

    /**
     * 订单管理导出
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "exportOrder")
    public void exportUserHistory(HttpServletResponse response, HttpServletRequest request) throws Exception {
        FastMap par = FastMap.fastMap("oNum",request.getParameter("oNum"))
                .putKeyV("agentId",request.getParameter("agentId"))
                .putKeyV("agName",request.getParameter("agName"))
                .putKeyV("id",request.getParameter("id"))
                .putKeyV("platformNum",request.getParameter("platform"))
                .putKeyV("agUniqNum",request.getParameter("agUniqNum"))
                .putKeyV("proType",request.getParameter("proType"))//机具类型
                .putKeyV("vender",request.getParameter("vender"))//厂商
                .putKeyV("model",request.getParameter("model"))//机型
                .putKeyV("payMethod",request.getParameter("payMethod"))//结算方式
                .putKeyV("nuclearUser","1".equals(request.getParameter("nuclearUser"))?getUserId():null)
                .putKeyV("activityName",request.getParameter("activityName"))//活动名称
                .putKeyV("vender", request.getParameter("vender"))//厂商
                .putKeyV("reviewStatus", request.getParameter("reviewStatus"))//审批状态
                .putKeyV("agDocDistrict", request.getParameter("agDocDistrict"))//业务对接大区
                .putKeyV("agDocPro", request.getParameter("agDocPro"))//业务对接省区
                .putKeyV("busNum", request.getParameter("busNum"))//业务编号
                ;

        if(StringUtils.isNotBlank(request.getParameter("beginTime")) && StringUtils.isNotBlank(request.getParameter("endTime"))){
            par.putKeyV("beginTime", request.getParameter("beginTime"));
            par.putKeyV("endTime",request.getParameter("endTime"));
        }

        //查询当前用户的订单
        par.putKeyV("userId", getUserId());
        List<OrderoutVo> list = orderService.exportOrder(par);
        String filePath = "C:/upload/";
        String filePrefix = "Order";
        int flushRows = 100;
        List<String> fieldNames = null;
        List<String> fieldCodes = null;
        //指导导出数据的title
        fieldNames = new ArrayList<String>();
        fieldCodes = new ArrayList<String>();
        fieldNames.add("订单日期");
        fieldCodes.add("oinuretime");
        fieldNames.add("订单号");
        fieldCodes.add("num");
        fieldNames.add("代理商唯一码");
        fieldCodes.add("agUniqNum");
        fieldNames.add("业务平台码");
        fieldCodes.add("busNum");
        fieldNames.add("平台名称");
        fieldCodes.add("platformName");
        fieldNames.add("代理商名称");
        fieldCodes.add("agName");

        fieldNames.add("机具类型");
        fieldCodes.add("proType");
        fieldNames.add("活动");
        fieldCodes.add("activityName");
        fieldNames.add("机具型号");
        fieldCodes.add("model");

        fieldNames.add("单价");
        fieldCodes.add("proRelPrice");
        fieldNames.add("数量");
        fieldCodes.add("proNum");
        fieldNames.add("总金额");
        fieldCodes.add("payAmo");
        fieldNames.add("结算方式");
        fieldCodes.add("payMethod");

        fieldNames.add("收款地方");
        fieldCodes.add("comName");
        fieldNames.add("打款人");
        fieldCodes.add("payUser");
        fieldNames.add("实际到账日期");
        fieldCodes.add("realRecTime");
        fieldNames.add("核款人");
        fieldCodes.add("nuclearUser");
        fieldNames.add("核款时间");
        fieldCodes.add("checkDate");
        fieldNames.add("首付款金额");
        fieldCodes.add("xxAmount");
        fieldNames.add("保证金抵货款金额");
        fieldCodes.add("amount");
        fieldNames.add("存量分润");
        fieldCodes.add("profitMouth");
        fieldNames.add("分润形式");
        fieldCodes.add("profitForm");
        fieldNames.add("应扣分润金额");
        fieldCodes.add("ykfrAmt");
        fieldNames.add("分润扣款开始月份");
        fieldCodes.add("downPaymentDate");
        fieldNames.add("分期扣分润期数");
        fieldCodes.add("downPaymentCount");
        fieldNames.add("每期应扣分润金额");
        fieldCodes.add("mqykAmt");
        fieldNames.add("分期打款金额");
        fieldCodes.add("fqdkAmt");
        fieldNames.add("分期打款期数");
        fieldCodes.add("frdkCount");
        fieldNames.add("每期应打款金额");
        fieldCodes.add("mqydkAmt");
        fieldNames.add("实际还款金额");
        fieldCodes.add("sjdkAmt");
        fieldNames.add("剩余欠款");
        fieldCodes.add("syqkAmt");

        fieldNames.add("审批状态");
        fieldCodes.add("reviewStatus");
        fieldNames.add("审批时间");
        fieldCodes.add("oinuretime");
        fieldNames.add("对应省区");
        fieldCodes.add("agDocPpro");
        fieldNames.add("顶级机构");
        fieldCodes.add("orgName");
        fieldNames.add("备注");
        fieldCodes.add("remark");

        ExcelExportSXXSSF excelExportSXXSSF;
        excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
        //执行导出
        excelExportSXXSSF.writeDatasByObject(list);
        String filename = filePrefix + "_" + MyUtil.getCurrentTimeStr() + ".xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        excelExportSXXSSF.getWb().write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 所有订单导出
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "exportOrderAll")
    public void exportUserHistoryAll(HttpServletResponse response, HttpServletRequest request) throws Exception {
        FastMap par = FastMap.fastMap("oNum",request.getParameter("oNum"))
                .putKeyV("agentId",request.getParameter("agentId"))
                .putKeyV("agName",request.getParameter("agName"))
                .putKeyV("id",request.getParameter("id"))
                .putKeyV("platformNum",request.getParameter("platform"))
                .putKeyV("agUniqNum",request.getParameter("agUniqNum"))
                .putKeyV("proType",request.getParameter("proType"))//机具类型
                .putKeyV("vender",request.getParameter("vender"))//厂商
                .putKeyV("model",request.getParameter("model"))//机型
                .putKeyV("payMethod",request.getParameter("payMethod"))//结算方式
                .putKeyV("nuclearUser","1".equals(request.getParameter("nuclearUser"))?getUserId():null)
                .putKeyV("activityName",request.getParameter("activityName"))//活动名称
                .putKeyV("vender", request.getParameter("vender"))//厂商
                .putKeyV("reviewStatus", request.getParameter("reviewStatus"))//审批状态
                .putKeyV("agDocDistrict", request.getParameter("agDocDistrict"))//业务对接大区
                .putKeyV("agDocPro", request.getParameter("agDocPro"))//业务对接省区
                .putKeyV("busNum", request.getParameter("busNum"))//业务编号
                ;

        if(StringUtils.isNotBlank(request.getParameter("beginTime")) && StringUtils.isNotBlank(request.getParameter("endTime"))){
            par.putKeyV("beginTime", request.getParameter("beginTime"));
            par.putKeyV("endTime",request.getParameter("endTime"));
        }

        //查询当前用户的订单
        par.putKeyV("userId", getUserId());
        List<OrderoutVo> list = orderService.exportOrderAll(par);
        String filePath = "C:/upload/";
        String filePrefix = "Order";
        int flushRows = 100;
        List<String> fieldNames = null;
        List<String> fieldCodes = null;
        //指导导出数据的title
        fieldNames = new ArrayList<String>();
        fieldCodes = new ArrayList<String>();
        fieldNames.add("订单日期");
        fieldCodes.add("oinuretime");
        fieldNames.add("订单号");
        fieldCodes.add("num");
        fieldNames.add("代理商唯一码");
        fieldCodes.add("agUniqNum");
        fieldNames.add("业务平台码");
        fieldCodes.add("busNum");
        fieldNames.add("平台名称");
        fieldCodes.add("platformName");
        fieldNames.add("代理商名称");
        fieldCodes.add("agName");

        fieldNames.add("机具类型");
        fieldCodes.add("proType");
        fieldNames.add("活动");
        fieldCodes.add("activityName");
        fieldNames.add("机具型号");
        fieldCodes.add("model");

        fieldNames.add("单价");
        fieldCodes.add("proRelPrice");
        fieldNames.add("数量");
        fieldCodes.add("proNum");
        fieldNames.add("总金额");
        fieldCodes.add("payAmo");
        fieldNames.add("结算方式");
        fieldCodes.add("payMethod");

        fieldNames.add("收款地方");
        fieldCodes.add("comName");
        fieldNames.add("打款人");
        fieldCodes.add("payUser");
        fieldNames.add("实际到账日期");
        fieldCodes.add("realRecTime");
        fieldNames.add("核款人");
        fieldCodes.add("nuclearUser");
        fieldNames.add("核款时间");
        fieldCodes.add("checkDate");
        fieldNames.add("首付款金额");
        fieldCodes.add("xxAmount");
        fieldNames.add("保证金抵货款金额");
        fieldCodes.add("amount");
        fieldNames.add("存量分润");
        fieldCodes.add("profitMouth");
        fieldNames.add("分润形式");
        fieldCodes.add("profitForm");
        fieldNames.add("应扣分润金额");
        fieldCodes.add("ykfrAmt");
        fieldNames.add("分润扣款开始月份");
        fieldCodes.add("downPaymentDate");
        fieldNames.add("分期扣分润期数");
        fieldCodes.add("downPaymentCount");
        fieldNames.add("每期应扣分润金额");
        fieldCodes.add("mqykAmt");
        fieldNames.add("分期打款金额");
        fieldCodes.add("fqdkAmt");
        fieldNames.add("分期打款期数");
        fieldCodes.add("frdkCount");
        fieldNames.add("每期应打款金额");
        fieldCodes.add("mqydkAmt");
        fieldNames.add("实际还款金额");
        fieldCodes.add("sjdkAmt");
        fieldNames.add("剩余欠款");
        fieldCodes.add("syqkAmt");

        fieldNames.add("审批状态");
        fieldCodes.add("reviewStatus");
        fieldNames.add("审批时间");
        fieldCodes.add("oinuretime");
        fieldNames.add("对应省区");
        fieldCodes.add("agDocPpro");
        fieldNames.add("顶级机构");
        fieldCodes.add("orgName");
        fieldNames.add("备注");
        fieldCodes.add("remark");

        ExcelExportSXXSSF excelExportSXXSSF;
        excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
        //执行导出
        excelExportSXXSSF.writeDatasByObject(list);
        String filename = filePrefix + "_" + MyUtil.getCurrentTimeStr() + ".xlsx";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        excelExportSXXSSF.getWb().write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    @ResponseBody
    @RequestMapping(value = "deleteOrder")
    public AgentResult deleteOrder(HttpServletRequest request, @RequestParam("id") String id) {
        return orderService.updateStatus(id, getUserId() + "");
    }

    @ResponseBody
    @RequestMapping(value = "deletePeihuoAction")
    public AgentResult deletePeihuoAction(HttpServletRequest request,
                                          HttpServletResponse response,
                                          String id, Integer proNum){
        if(StringUtils.isBlank(id)
                || proNum==null
                || proNum<0) {

            return AgentResult.fail("商品数量有误");
        }
        if(!RegexUtil.checkInt(proNum+"")){
            return AgentResult.fail("商品数量有误");
        }
        OReceiptPro var1 = new OReceiptPro();
        var1.setId(id);
        var1.setcUser(getUserId()+"");
        var1.setuUser(getUserId()+"");
        var1.setProNum(new BigDecimal(proNum));
        try {
            return orderService.deletePeihuoAction(var1);
        } catch (Exception e) {
            e.printStackTrace();
            if(e instanceof MessageException){
                return AgentResult.fail(((MessageException) e).getMsg());
            }
            return AgentResult.fail(e.getLocalizedMessage());
        }

    }
}
