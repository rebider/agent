package com.ryx.credit.cms.controller.order;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.controller.data.AccessoryController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.OsupplementVo;
import com.ryx.credit.service.agent.AgentQueryService;
import com.ryx.credit.service.order.OCashReceivablesService;
import com.ryx.credit.service.order.OSupplementService;
import com.ryx.credit.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 订单补款
 */
@RequestMapping("supplement")
@Controller
public class SupplementController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AccessoryController.class);
    @Autowired
    private OSupplementService oSupplementService;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private OCashReceivablesService oCashReceivablesService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OSupplementService supplementService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("[开始]-订单补款页面");
        dropDown(model);
        return "order/supplementList";
    }

    /**
     * 查询补款列表
     *
     * @param request
     * @param oSupplement
     * @param time
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "queryList")
    public PageInfo queryList(HttpServletRequest request, OSupplement oSupplement, String time,String supplementShrio) {
        logger.info("[开始]-订单补款的数据展示");
        String userId = String.valueOf(getUserId());
        Page pageInfo = pageProcess(request);
        PageInfo info = oSupplementService.selectAll(pageInfo, oSupplement, time,userId,supplementShrio);
        return info;
    }

    /**
     * 查询补款详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "queryById")
    public String queryById(String id, Model model, HttpServletRequest request) {//id-->业务id
        logger.info("订单补款的详情");
        String flag = "false";
        String taskId = request.getParameter("taskId");
        String proIns = request.getParameter("proIns");
        String busType = request.getParameter("busType");
        String busId = request.getParameter("busId");
        if (null == id) {
            id = busId;
            flag = "true";
        }
        OSupplement oSupplement = oSupplementService.selectById(id);
        if (StringUtils.isBlank( oSupplement.getSrcId())) {
            return null;
        }
        OPaymentDetail oPaymentDetail = oSupplementService.selectByOPaymentId(oSupplement.getSrcId());

        List<Attachment> attachment = agentQueryService.accessoryQuery(id, AttachmentRelType.Order.name());
        request.setAttribute("id", id);
        request.setAttribute("reviewStatus", oSupplementService.selectById(id).getReviewStatus());
        request.setAttribute("busTypeImg", BusActRelBusType.PkType.name());
        List<Map<String, Object>> actRecordList = queryApprovalRecord(id, BusActRelBusType.PkType.name());
        request.setAttribute("actRecordList", actRecordList);
        //查询现款明细数据
        List<OCashReceivables> receivablesList = oCashReceivablesService.query(null, null, CashPayType.getContentEnum(CashPayType.SUPPLEMENT.code), id, Arrays.asList(AgStatus.Approving.status));
        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        model.addAttribute("recCompList", payComp_list);
        model.addAttribute("reviewStatus",oSupplement.getReviewStatus());
        model.addAttribute("oPaymentDetail", oPaymentDetail);
        model.addAttribute("taskId", taskId);
        model.addAttribute("busId", busId);
        model.addAttribute("attachment", attachment);
        model.addAttribute("id", id);
        model.addAttribute("oSupplement", oSupplement);
        model.addAttribute("realPayAmount", oSupplement.getRealPayAmount());
        request.setAttribute("proIns", proIns);
        request.setAttribute("paylist_approve", receivablesList);
        request.setAttribute("paylist", receivablesList);
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        request.setAttribute("payTypeSelect", PayType.getAllOption());
        optionsData(request);
        if (flag.equals("true")) {
            return "order/supplementApprove";
        }
        return "order/supplementViewWithAppr";
    }

    /**
     * 传递 srcid ,pktype,remark,agentId
     * supplement/supplementAddDialog
     *
     * @param oSupplement
     * @param model
     * @return
     */
    @RequestMapping(value = "supplementAddDialog")
    public String supplementAddDialog(OSupplement oSupplement, Model model,HttpServletRequest request) {
        logger.info("进入添加补款页面");
        dropDown(model);

        //srcid pktype 确定补款是付款单明细 查出（待付款 付款中 逾期)明细 （明细 应付款 pay_amount - 明细 实付款 real_pay_amount - 这条明细申请中的补款信息 ）
        BigDecimal payMount = oSupplementService.selectPayAmout(oSupplement.getSrcId(), oSupplement.getPkType());

        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        model.addAttribute("recCompList", payComp_list);
        model.addAttribute("oSupplement", oSupplement);
        model.addAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        model.addAttribute("orderId", oSupplement.getOrderId());
        request.setAttribute("payTypeSelect",PayType.getYHHKOption());
        request.setAttribute("payMount",payMount);
//        List<OCashReceivables> listoCashReceivables  = (List<OCashReceivables>)((FastMap)res.getData()).get("oCashReceivables");
        OPaymentDetail oPaymentDetail = supplementService.selectPaymentDetailById(oSupplement.getSrcId());
        OOrder order = orderService.getById(oPaymentDetail.getOrderId());
        if (null!=oPaymentDetail){
            OPayment oPayment = supplementService.selectOpayment(oPaymentDetail.getPaymentId());
            request.setAttribute("oPayment",oPayment);
        }
        request.setAttribute("oPaymentDetail",oPaymentDetail);
        request.setAttribute("order",order);

        return "order/supplementAddDialog";
    }

    /**
     * 添加补款---上传打款凭证
     *
     * @param request
     * @param osupplementVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "supplementSave")
    public ResultVO supplementSave(HttpServletRequest request, @RequestBody OsupplementVo osupplementVo) throws Exception {
        logger.info("添加补款");
        OSupplement supplement = osupplementVo.getSupplement();
        supplement.setcUser(getUserId() + "");
        ResultVO resultVO = oSupplementService.supplementSave(osupplementVo);
        return resultVO;
    }

    /**
     * 提交审批
     *
     * @param
     */
    @ResponseBody
    @RequestMapping("startAg")
    public ResultVO startAgentProcessing(HttpServletRequest request, HttpServletResponse response) {
        try {
            String agentId = request.getParameter("agentId");
            ResultVO rv = oSupplementService.startSuppActivity(agentId, getUserId() + "");
            return rv;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
    }


    @ResponseBody
    @RequestMapping(value = "selectBySrcId")
    public ResultVO selectBySrcId(HttpServletRequest request, @RequestBody OsupplementVo osupplementVo) {
        ResultVO resultVO = oSupplementService.selectBySrcId(osupplementVo);
        return resultVO;
    }

    @ResponseBody
    @RequestMapping(value = "supplementSaveAndaudit")
    public ResultVO supplementSaveAndaudit(HttpServletRequest request, @RequestBody OsupplementVo osupplementVo) {
        try {
            logger.info("添加补款并审核");
            ResultVO rv = new ResultVO();
            rv.setResInfo("失败");
            OSupplement supplement = osupplementVo.getSupplement();
            supplement.setcUser(getUserId() + "");
            rv = oSupplementService.supplementSave(osupplementVo);
            return rv;
        } catch (Exception e) {
            return ResultVO.fail(e.getMessage());
        }
    }

    /**
     * 处理任务
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("taskApproval")
    public ResultVO taskApproval(HttpServletRequest request, HttpServletResponse response, @RequestBody AgentVo agentVo) {
        AgentResult result = null;
        try {
            Long userId = getUserId();
            ResultVO resultVO = oSupplementService.updateAmount(agentVo,userId);
            if (null != resultVO) {
                if ("成功".equals(resultVO.getResInfo())) {
                    result = oSupplementService.approvalTask(agentVo, String.valueOf(getUserId()));
                }
            }
        }catch (MessageException |ProcessException e) {
            e.printStackTrace();
            if (e instanceof MessageException) {
                String msg = ((MessageException) e).getMsg();
                return ResultVO.fail(msg);
            }
            return ResultVO.fail(e.getLocalizedMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return ResultVO.fail(e.getMessage());
        }
        return ResultVO.success(null);
    }


    public void dropDown(Model model) {
        List<Dict> list = new ArrayList<Dict>();
        for (PkType pkType : PkType.values()) {//补款类型
            Dict dict = new Dict();
            dict.setdItemnremark(pkType.getValue());
            dict.setdItemname(pkType.getContent());
            list.add(dict);
        }
        List<Dict> payList = new ArrayList<Dict>();
        for (PayMethod payMethod : PayMethod.values()) {//付款方式
            Dict dict = new Dict();
            dict.setdItemnremark(payMethod.getValue());
            dict.setdItemname(payMethod.getContent());
            payList.add(dict);
        }
        List<Dict> agStatusList = new ArrayList<Dict>();
        for (AgStatus agStatus : AgStatus.values()) {//申请状态
            Dict dict = new Dict();
            dict.setdStatus(agStatus.getValue());
            dict.setdItemname(agStatus.getContent());
            agStatusList.add(dict);
        }
        List<Dict> schStatusList = new ArrayList<Dict>();
        for (SchStatus schStatus : SchStatus.values()) {//补款状态
            Dict dict = new Dict();
            dict.setdStatus(schStatus.getValue());
            dict.setdItemname(schStatus.getContent());
            schStatusList.add(dict);
        }

        model.addAttribute("PkTypeList", list);
        model.addAttribute("PayList", payList);
        model.addAttribute("agStatusList", agStatusList);
        model.addAttribute("schStatusList", schStatusList);
    }
}
