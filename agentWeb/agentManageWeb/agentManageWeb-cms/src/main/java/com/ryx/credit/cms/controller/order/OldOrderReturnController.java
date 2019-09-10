package com.ryx.credit.cms.controller.order;

import com.alibaba.fastjson.JSONArray;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ImportExcelUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.pojo.admin.order.OActivity;
import com.ryx.credit.pojo.admin.order.OProduct;
import com.ryx.credit.pojo.admin.order.OReturnOrder;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OldOrderReturnBusEditVo;
import com.ryx.credit.pojo.admin.vo.OldOrderReturnVo;
import com.ryx.credit.service.order.IAccountAdjustService;
import com.ryx.credit.service.order.OldOrderReturnService;
import com.ryx.credit.service.order.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2019/3/7
 * 描述：历史订单退货控制
 */
@Controller
@RequestMapping("oldorderreturn")
public class OldOrderReturnController  extends BaseController {

    private Logger logger = LoggerFactory.getLogger(OldOrderReturnController.class);

    @Resource(name = "productService")
    private ProductService productService;

    @Resource(name = "oldOrderReturnService")
    private OldOrderReturnService oldOrderReturnService;
    @Resource
    private IAccountAdjustService accountAdjustService;
    @Autowired
    protected RedisService redisService;

    /**
     * 历史订单退货申请界面
     * @param request
     * @param response
     * @return oldorderreturn/apply
     */
    @RequestMapping("apply")
    public String apply(HttpServletRequest request, HttpServletResponse response){
        List<OProduct> products = productService.allProductList(new OProduct());
        request.setAttribute("allProduct",products);
        request.setAttribute("orderType",request.getParameter("orderType"));
        String key = RedisCachKey.APP_SPLIT+":"+getStringUserId();
        List<Map<String, Object>> maps = redisService.popListMap(key);
        if(maps.size()!=0){
            request.setAttribute("rKey",key);
        }
        return "order/old_orderReturn";
    }


    /**
     *  oldorderreturn/analysisFile
     * @param file
     * @param request
     * @return
     */
    @RequestMapping(value ="analysisFile")
    @ResponseBody
    public Object analysisFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {

        String orderType = request.getParameter("orderType");
        String rKey = request.getParameter("rKey");
        try {
            List<List<Object>> excelList = new ArrayList<>();
            if(StringUtils.isBlank(rKey)){
                if(file==null){
                    return renderError("请上传文件");
                }
                if(file.getSize()==0){
                    return renderError("请上传文件");
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
                        list.add(map.get("proModel"));
                        excelList.add(list);
                    }
                }
            }

            if(null==excelList || excelList.size()==0){
                return renderError("文档记录为空");
            }
            AgentResult resultList = oldOrderReturnService.parseExcel(excelList);
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return AgentResult.fail(e.getLocalizedMessage());
        }
    }



    /**
     * 退货申请单创建
     * @param request
     * @param response
     * @param oldOrderReturnVo
     * oldorderreturn/createApply
     * @return
     */
    @RequestMapping("createApply")
    @ResponseBody
    public AgentResult createApply(HttpServletRequest request,
                                   HttpServletResponse response,
                                   @RequestBody OldOrderReturnVo oldOrderReturnVo){
        oldOrderReturnVo.setAgentId(getAgentId());
        if(StringUtils.isBlank(oldOrderReturnVo.getAgentId())) {
//            oldOrderReturnVo.setAgentId("AG20190226000000000018201");
            return AgentResult.fail("未获取到代理商信息");
        }
        oldOrderReturnVo.setUserId(getUserId()+"");
        try {
           return oldOrderReturnService.saveOldReturnOrder(oldOrderReturnVo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("申请历史订单退货异常：",e);
            return AgentResult.fail(e.getLocalizedMessage());
        }
    }

    /**
     * 历史退货审批省区大区显示界面
     * oldorderreturn/approveDataEditView?returnId=
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("approveDataEditView")
    public String approveDataEditView(HttpServletRequest request, HttpServletResponse response){
        String returnId = request.getParameter("returnId");
        AgentResult agentResult =oldOrderReturnService.loadOldOrderApproveData(returnId);
        request.setAttribute("old_order_return_info_detail",agentResult.getMapData().get("details"));
        optionsData(request);
        return "order/old_orderReturn_approveView";
    }

    /**
     * 历史退货审批业务编辑及排单界面
     * oldorderreturn/approveDataBusEditView?returnId=
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("approveDataBusEditView")
    public String approveDataBusEditView(HttpServletRequest request, HttpServletResponse response){
        String returnId = request.getParameter("returnId");
        orderDictData(request);
        AgentResult agentResult =oldOrderReturnService.loadOldOrderApproveData(returnId);
        request.setAttribute("old_order_return_info_detail",agentResult.getMapData().get("details"));
        request.setAttribute("old_order_return_info",agentResult.getMapData().get("oReturnOrder"));
        //商品信息列表
        List<OProduct> products = productService.allProductList(new OProduct());
        request.setAttribute("products",products);
        return "order/old_orderReturn_approveView_bus_edit";
    }



    /**
     * 于华 大业务部
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("approveDataBusBosView")
    public String approveDataBusBosView(HttpServletRequest request, HttpServletResponse response){
        String returnId = request.getParameter("returnId");
        orderDictData(request);
        AgentResult agentResult =oldOrderReturnService.loadOldOrderApproveData(returnId);
        request.setAttribute("old_order_return_info_detail",agentResult.getMapData().get("details"));
        request.setAttribute("old_order_return_info",agentResult.getMapData().get("oReturnOrder"));
        //商品信息列表
        List<OProduct> products = productService.allProductList(new OProduct());
        request.setAttribute("products",products);
        return "order/old_orderReturn_approveView_bus_bos";
    }

    /**
     * 代理商上传物流
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("approveDataAgentLgView")
    public String approveDataAgentLgView(HttpServletRequest request, HttpServletResponse response){
        String returnId = request.getParameter("returnId");
        orderDictData(request);
        AgentResult agentResult =oldOrderReturnService.loadOldOrderApproveData(returnId);
        request.setAttribute("old_order_return_info_detail",agentResult.getMapData().get("details"));
        request.setAttribute("old_order_return_info",agentResult.getMapData().get("oReturnOrder"));
        //商品信息列表
        List<OProduct> products = productService.allProductList(new OProduct());
        request.setAttribute("products",products);
        return "order/old_orderReturn_approveView_agent_lg";
    }

    /**
     * 代理商物流后业务部门核查
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("approveDataBusCheckView")
    public String approveDataBusCheckView(HttpServletRequest request, HttpServletResponse response){
        String returnId = request.getParameter("returnId");
        orderDictData(request);
        AgentResult agentResult =oldOrderReturnService.loadOldOrderApproveData(returnId);
        request.setAttribute("old_order_return_info_detail",agentResult.getMapData().get("details"));
        request.setAttribute("old_order_return_info",agentResult.getMapData().get("oReturnOrder"));
        //商品信息列表
        List<OProduct> products = productService.allProductList(new OProduct());
        request.setAttribute("products",products);
        return "order/old_orderReturn_approveView_bus_check";
    }

    /**
     * 代理商上次物流后业务部门核查
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("approveDataFinCheckView")
    public String approveDataFinCheckView(HttpServletRequest request, HttpServletResponse response){
        String returnId = request.getParameter("returnId");
        orderDictData(request);
        AgentResult agentResult =oldOrderReturnService.loadOldOrderApproveData(returnId);
        request.setAttribute("old_order_return_info_detail",agentResult.getMapData().get("details"));
        request.setAttribute("old_order_return_info",agentResult.getMapData().get("oReturnOrder"));
        //商品信息列表
        List<OProduct> products = productService.allProductList(new OProduct());
        request.setAttribute("products",products);
        //财务退款抵扣信息
        OReturnOrder returnOrder = (OReturnOrder)agentResult.getMapData().get("oReturnOrder");
        //查询是否有调账记录
        Map<String, Object> oAccountAdjusts = accountAdjustService.getAccountAdjustDetail(returnId, AdjustType.TKTH.adjustType, String.valueOf(getUserId()), returnOrder.getAgentId());
        request.setAttribute("oAccountAdjusts", oAccountAdjusts);
        Map<String, Object> adjustmap = accountAdjustService.adjust(false, returnOrder.getReturnAmo(), AdjustType.TKTH.adjustType, 1, returnOrder.getAgentId(), String.valueOf(getUserId()), returnId, PamentSrcType.TUIKUAN_DIKOU.code);
        request.setAttribute("planNows_df", adjustmap.get("planNows_df"));//待付款计划
        request.setAttribute("planNows_complate", adjustmap.get("planNows_complate"));//已完成的付款计划
        request.setAttribute("takeoutList", adjustmap.get("takeoutList"));//抵扣信息
        request.setAttribute("planNews", adjustmap.get("planNews"));//新的付款计划
        request.setAttribute("takeAmt", adjustmap.get("takeAmt"));//可抵扣金额
        request.setAttribute("refund", adjustmap.get("refund"));
        request.setAttribute("planNows", adjustmap.get("planNows"));


        //结算方式
        List<Dict> settlement_type = ServiceFactory.dictOptionsService.dictList(DictGroup.ORDER.name(), DictGroup.SETTLEMENT_TYPE.name());
        request.setAttribute("settlementType", settlement_type);
        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        request.setAttribute("payTypeSelect", PayType.getYHHKOption());
        return "order/old_orderReturn_approveView_fin_check";
    }

    /**
     * 历史订单审批提交处理
     * oldorderreturn/approveOldOrderReturnTask
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping( value = "approveOldOrderReturnTask",method = RequestMethod.POST)
    public AgentResult approveOldOrderReturnTask(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 @RequestBody AgentVo agentVo){
        try {
            AgentResult agentResult = oldOrderReturnService.taskApprove(agentVo,getUserId()+"");
            return agentResult;
        } catch (MessageException e) {
            e.printStackTrace();
            logger.error("审批失败",e.getMsg());
            return AgentResult.fail(e.getMsg());
        }catch (Exception e){
            logger.error("审批失败",e.getMessage());
            e.printStackTrace();
            return AgentResult.fail(e.getMessage());
        }
    }

    /**
     * 根据订单号加载历史订单退货明细的商品信息活动信息等
     * /oldorderreturn/loadOldOrderReturnDetailInfo
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("loadOldOrderReturnDetailInfo")
    @ResponseBody
    public AgentResult loadOldOrderReturnDetailInfo(HttpServletRequest request,HttpServletResponse response){
        String orderId = request.getParameter("orderId");
        String proId = request.getParameter("proId");
        AgentResult agentResult  = oldOrderReturnService.loadOldOrderReturnDetailInfo(orderId,proId);
        return agentResult;
    }

    /**
     * 业务部们补全退货单信息
     * /oldorderreturn/completOldOrderReturnDetailInfo
     * @param request
     * @param response
     * @param oldOrderReturnBusEditVos
     * @return
     */
    @RequestMapping("completOldOrderReturnDetailInfo")
    @ResponseBody
    public AgentResult completOldOrderReturnDetailInfo(HttpServletRequest request,
                                                       HttpServletResponse response,
                                                       @RequestBody List<OldOrderReturnBusEditVo> oldOrderReturnBusEditVos ){
        try {
            return  oldOrderReturnService.completOldOrderReturnInfo(oldOrderReturnBusEditVos,getUserId()+"");
        } catch (MessageException e) {
            e.printStackTrace();
            logger.error("信息补全失败",e);
            return AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("信息补全失败",e);
            return AgentResult.fail("信息补全失败");
        }
    }

    /**
     * 排单选择列表
     * oldorderreturn/page/planerList?returnDetailsId=
     * @param request
     * @param returnDetailsId
     * @param model
     * @return
     */
    @RequestMapping("/page/planerList")
    public String planerList(HttpServletRequest request,String returnDetailsId,Model model) {
        model.addAttribute("returnDetailsId",returnDetailsId);
        //fixme 检查退货明细信息是否补全
        return "order/oldOrderReturnPlannerList";
    }

    /**
     * 历史订单代理商上传物流
     * @return
     */
    @RequestMapping("agentUploadLogicView")
    public String agentUploadLogicView(){
        return "order/old_oLogisticsImportReturn";
    }

    /**检查订单信息是否补全
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("checkReturnOrderOrderIdIsCompplet")
    @ResponseBody
    public AgentResult checkReturnOrderOrderIdIsCompplet(HttpServletRequest request,HttpServletResponse response){
        return oldOrderReturnService.checkReturnOrderOrderIdIsCompplet(request.getParameter("returnDetailsId"));
    }


}
