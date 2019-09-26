package com.ryx.credit.cms.controller.order;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OrderFormVo;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.order.OrderActivityService;
import com.ryx.credit.service.order.OrderService;
import com.ryx.credit.service.order.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/7/16.
 */
@RequestMapping("orderbuild")
@Controller
public class OrderBuildController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(OrderBuildController.class);

    @Autowired
    private BusinessPlatformService businessPlatformService;
    @Autowired
    private OrderActivityService orderActivityService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private ProductService productService;

    /**
     * 下订单界面
     * /orderbuild/buildview
     *
     * @param request
     * @return
     */
    @RequestMapping("buildview")
    public String buildview(HttpServletRequest request) {
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

        AgentResult ar = agentService.isAgent(getUserId()+"");
        request.setAttribute("isagent", ar);

        if(ar.isOK()) {
            Agent ag = (Agent)ar.getData();
            List<Map> listPlateform = agentBusinfoService.agentBus(ag.getId(),getUserId());
            request.setAttribute("listPlateform", listPlateform);
        }
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        request.setAttribute("payTypeSelect", PayType.getYHHKOption());
        optionsData(request);

        return "order/orderBuild";
    }


    /**
     * 代理商平台
     *  /orderbuild/orderAgentPlatformBus
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("orderAgentPlatformBus")
    public List<Map> orderAgentPlatformBus(HttpServletRequest request) {
        logger.info("代理商平台orderAgentPlatformBus{}", getUserId());
        String agentId = request.getParameter("agentId");
        if(StringUtils.isBlank(agentId)){
            return new ArrayList<>();
        }
        return agentBusinfoService.agentBus(agentId,getUserId());
    }



    /**
     * 订单构建操作
     * /orderbuild/buildOrder
     *
     * @param request
     * @param response
     * @param agentVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"buildOrder"}, method = RequestMethod.POST)
    public AgentResult buildOrder(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @RequestBody OrderFormVo agentVo) {
        logger.info("====订单提交数据=={}=={}", getUserId(), JSONObject.toJSONString(agentVo));
        try {
            if(agentVo.getoPayment()==null)
                return AgentResult.fail("信息错误");
            if(agentVo.getoSubOrder()==null || agentVo.getoSubOrder().size()==0)
                return AgentResult.fail("请选择商品");
            if(StringUtils.isBlank(agentVo.getAgentId()))
                return AgentResult.fail("请指定代理商");
//            if(agentVo.getoAmo()==null || agentVo.getoAmo().compareTo(BigDecimal.ZERO)<=0)
//                return AgentResult.fail("订单金额有误");
            if(StringUtils.isBlank(agentVo.getOrderPlatform())) {
                return AgentResult.fail("订单平台信息有误");
            }
            //检查分期日
            if(null!=agentVo.getoPayment() && agentVo.getoPayment().getDownPaymentDate()!=null) {
                AgentResult checkDownPayMent = orderService.checkDownPaymentDate(agentVo.getoPayment().getDownPaymentDate());
                if(!checkDownPayMent.isOK()){
                    return checkDownPayMent;
                }
            }
//            if(null!=agentVo.getoPayment() && agentVo.getoPayment().getDownPaymentDate()!=null) {
//               Agent agent = agentService.getAgentById(agentVo.getAgentId());
//               if(!agent.getAgStatus().equals(AgStatus.Approved.name()))
//                   return AgentResult.fail("代理商未入网");
//            }
            //下订单
            AgentResult agentResult =  orderService.buildOrder(agentVo, getUserId() + "");
           return agentResult;
        } catch (MessageException e) {
            e.printStackTrace();
            logger.error(e.getMsg(),e);
            return AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(),e);
            return AgentResult.fail("添加订单失败:"+e.getLocalizedMessage());
        }

    }


    /**
     * 订单查看界面
     * /orderbuild/orderView
     *
     * @param request
     * @param response
     * @param orderId
     * @return
     */
    @RequestMapping(value = {"orderView"}, method = RequestMethod.GET)
    public String orderView(HttpServletRequest request,
                            HttpServletResponse response,
                            String orderId) {
        try {
            AgentResult res = orderService.loadAgentInfo(orderId);
            if (res.isOK()) {
                request.setAttribute("data", res.getData());
                List<Dict> orderStatus = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ORDER_STATUS.name());
                request.setAttribute("orderStatus", orderStatus);
                List<Dict> settlement_type = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.SETTLEMENT_TYPE.name());
                request.setAttribute("settlementType", settlement_type);
                List<Dict> paymentStatus = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.PAYMENTSTATUS.name());
                request.setAttribute("paymentStatus", paymentStatus);
                List<Dict> paymentType = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.PAYMENTTYPE.name());
                request.setAttribute("paymentType", paymentType);
                List<Map<String, Object>> actRecordList = queryApprovalRecord(orderId, BusActRelBusType.ORDER.name());
                request.setAttribute("actRecordList", actRecordList);

                OPayment oPayment = (OPayment)((FastMap)res.getData()).get("oPayment");
                if(StringUtils.isNotBlank(oPayment.getNuclearUser())){
                    getUserName(Long.valueOf(oPayment.getNuclearUser()));
                    request.setAttribute("NuclearUser", getUserName(Long.valueOf(oPayment.getNuclearUser())));
                }
                if(null!=oPayment.getNuclearTime()){
                    request.setAttribute("NuclearTime", DateUtil.format(oPayment.getNuclearTime(),"yyyy-MM-dd"));
                }
                //线下打款信息
                List<OCashReceivables> listoCashReceivables  = (List<OCashReceivables>)((FastMap)res.getData()).get("oCashReceivables");
                request.setAttribute("paylist", listoCashReceivables);
                request.setAttribute("payTypeSelect",PayType.getAllOption());

                //收款公司
                List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
                request.setAttribute("recCompList", payComp_list);
                request.setAttribute("orderId", orderId);

            }
            optionsData(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "order/orderView";
    }


    /**
     * 订单查看
     * /orderbuild/orderApprView
     * @param request
     * @param response
     * @param orderId
     * @return
     */
    @RequestMapping(value = {"orderApprView"}, method = RequestMethod.GET)
    public String orderApprView(HttpServletRequest request,
                            HttpServletResponse response,
                            String orderId) {
        try {
                request.setAttribute("orderId", orderId);
                request.setAttribute("reviewStatus", orderService.getById(orderId).getReviewStatus());
                request.setAttribute("busTypeImg", BusActRelBusType.ORDER.name());
                List<Map<String, Object>> actRecordList = queryApprovalRecord(orderId, BusActRelBusType.ORDER.name());
                request.setAttribute("actRecordList", actRecordList);
                optionsData(request);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "order/orderViewWithAppr";
    }


    /**
     * /orderbuild/approvalOrderView
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = {"approvalOrderView"}, method = RequestMethod.GET)
    public String approvalOrderView(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String taskId,
                                    String proIns,
                                    String busType,
                                    String busId,
                                    String sid) {
        try {

            //查找付款单的id
            OPayment oPayment = orderService.selectByOrderId(busId);
            if (null!=oPayment.getId()){
                request.setAttribute("oPayment",oPayment);
            }

            //代理商信息
            AgentResult res = orderService.loadAgentInfo(busId);
            if (res.isOK()) {
                request.setAttribute("data", res.getData());
                List<Dict> orderStatus = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ORDER_STATUS.name());
                request.setAttribute("orderStatus", orderStatus);
                List<Dict> settlement_type = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.SETTLEMENT_TYPE.name());
                request.setAttribute("settlementType", settlement_type);
                List<Dict> paymentStatus = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.PAYMENTSTATUS.name());
                request.setAttribute("paymentStatus", paymentStatus);
                List<Dict> paymentType = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.PAYMENTTYPE.name());
                request.setAttribute("paymentType", paymentType);
                List<Map<String, Object>> actRecordList = queryApprovalRecord(busId, BusActRelBusType.ORDER.name());
                request.setAttribute("actRecordList", actRecordList);
                //线下打款信息
                List<OCashReceivables> listoCashReceivables  = (List<OCashReceivables>)((FastMap)res.getData()).get("oCashReceivables");
                request.setAttribute("paylist", listoCashReceivables);
                request.setAttribute("paylist_approve", listoCashReceivables);
                request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
                request.setAttribute("payTypeSelect", PayType.getAllOption());

            } else {
                logger.info("订单审批代理商信息加载失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("订单审批代理商信息加载失败:" + e.getMessage(), e);
        }


        //审批参数
        List<Dict> ORDER_APR_BUSNISS = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ORDER_APR_BUSNISS.name());
        List<Dict> ORDER_APR_MARKET = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ORDER_APR_MARKET.name());
        request.setAttribute("order_apr_busniss", ORDER_APR_BUSNISS);
        request.setAttribute("order_apr_market", ORDER_APR_MARKET);

        //审批任务需要
        request.setAttribute("taskId", taskId);
        request.setAttribute("proIns", proIns);
        request.setAttribute("busType", busType);
        request.setAttribute("busId", busId);
        request.setAttribute("sid", sid);

        //通用参数
        optionsData(request);

        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);


        //用户部门
        List<Map<String, Object>>  userOrg =  iUserService.orgCode(getUserId());
        if(userOrg.size()>0){
            request.setAttribute("userOrgCode", userOrg.get(0));
        }
        return "activity/OrderAppyApprove";
    }

    /**
     * /orderbuild/startOrderReview
     *
     * @param request
     * @param response
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"startOrderReview"}, method = RequestMethod.GET)
    public AgentResult startOrderReview(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestParam("orderId") String orderId) {
        try {
            //启动代理商
            logger.info("====启动代理商审批=={}=={}", getUserId(), orderId);
            AgentResult res = orderService.startOrderActiviy(orderId, getUserId() + "");
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof ProcessException) {
                return AgentResult.fail("启动失败:" + e.getMessage());
            }
            logger.info("====启动代理商审批异常=={}=={}", getUserId(), e.getMessage());
            return AgentResult.fail("启动失败：" + e.getMessage());
        }
    }


    /**
     * /orderbuild/taskApproval
     *
     * @param request
     * @param response
     * @param agentVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "taskApproval",method = RequestMethod.POST)
    public AgentResult taskApproval(HttpServletRequest request,
                                    HttpServletResponse response,
                                    @RequestBody AgentVo agentVo) {
        try {
            AgentResult res = orderService.approvalTask(agentVo, getUserId() + "");
            return res;
        } catch (MessageException e) {
            e.printStackTrace();
            logger.error("审批失败",e);
            return AgentResult.fail(e.getMsg());
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("审批失败",e);
            return AgentResult.fail("审批失败");
        }
    }

    /**
     * /orderbuild/queryAgentCanCapital
     * @param request
     * @param response
     * @param agentId
     * @param type
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryAgentCanCapital",method = RequestMethod.GET)
    public AgentResult queryAgentCanCap(HttpServletRequest request,
                                        HttpServletResponse response,
                                        @RequestParam("agentId") String agentId,
                                        @RequestParam("type")    String type){
        return orderService.queryAgentCapital(agentId,type);
    }


    /**
     * 查询订单的待补款的付款明细
     * /orderbuild/queryOrderForOSupplementPaymentdetail
     *
     * @param request
     * @param response
     * @param orderId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryOrderForOSupplementPaymentdetail",method = RequestMethod.GET)
    public AgentResult queryOrderForOSupplementPaymentdetail(HttpServletRequest request,HttpServletResponse response,@RequestParam("orderId") String orderId,@RequestParam("agentId") String agentId){
        return orderService.queryOrderForOSupplementPaymentdetail(orderId,agentId);
    }


    @RequestMapping(value = "orderChangeView")
    public String orderChangeView(HttpServletRequest request, @RequestParam("orderId") String orderId) {
        try {
            AgentResult res = orderService.loadAgentInfo(orderId);
            if (res.isOK()) {
                request.setAttribute("data", res.getData());
                List<Dict> orderStatus = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.ORDER_STATUS.name());
                request.setAttribute("orderStatus", orderStatus);
                List<Dict> settlement_type = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.SETTLEMENT_TYPE.name());
                request.setAttribute("settlementType", settlement_type);
                List<Dict> paymentStatus = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.PAYMENTSTATUS.name());
                request.setAttribute("paymentStatus", paymentStatus);
                List<Dict> paymentType = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.PAYMENTTYPE.name());
                request.setAttribute("paymentType", paymentType);
                List<Map<String, Object>> actRecordList = queryApprovalRecord(orderId, BusActRelBusType.ORDER.name());
                request.setAttribute("actRecordList", actRecordList);
                OPayment oPayment = (OPayment) ((FastMap) res.getData()).get("oPayment");
                if (StringUtils.isNotBlank(oPayment.getNuclearUser())) {
                    getUserName(Long.valueOf(oPayment.getNuclearUser()));
                    request.setAttribute("NuclearUser", getUserName(Long.valueOf(oPayment.getNuclearUser())));
                }
                if (null != oPayment.getNuclearTime()) {
                    request.setAttribute("NuclearTime", DateUtil.format(oPayment.getNuclearTime(), "yyyy-MM-dd"));
                }
                //线下打款信息
                List<OCashReceivables> listoCashReceivables = (List<OCashReceivables>) ((FastMap) res.getData()).get("oCashReceivables");
                request.setAttribute("paylist", listoCashReceivables);
                request.setAttribute("payTypeSelect", PayType.getAllOption());
                //收款公司
                List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
                request.setAttribute("recCompList", payComp_list);
                request.setAttribute("orderId", orderId);
            }
            optionsData(request);
        } catch (Exception e) {
            logger.info("订单查询{}{}{}", getUserId()+"", orderId, e.getMessage());
            e.printStackTrace();
        }
        return "order/orderViewChangeActivity";
    }


    @ResponseBody
    @RequestMapping(value= "orderChangeActivity")
    public AgentResult orderChangeActivity(@RequestParam("oNum") String oNum,
                                           @RequestParam("subOrderId") String subOrderId,
                                           @RequestParam("activityId") String activityId) {
        logger.info("订单活动变更数据{}{}{}{}", getUserId(), oNum, subOrderId, activityId);
        try {
            if (activityId==null || activityId=="") {
                return AgentResult.fail("请选择变更活动");
            }
            AgentResult agentResult = orderService.orderChangeActivity(oNum, subOrderId, activityId, getUserId()+"");
            return agentResult;
        } catch(MessageException e) {
            e.printStackTrace();
            logger.error(e.getMsg(), e);
            return AgentResult.fail(e.getMsg());
        } catch(Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return AgentResult.fail("活动变更失败:" + e.getLocalizedMessage());
        }
    }

}

