package com.ryx.credit.cms.controller.order;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ImportExcelUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.pojo.admin.vo.ORefundPriceDiffVo;
import com.ryx.credit.service.order.CompensateService;
import com.ryx.credit.service.order.IAccountAdjustService;
import com.ryx.credit.service.order.OCashReceivablesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 补差价控制层
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/7/24 15:30
 */
@RequestMapping("compensate")
@Controller
public class CompensateController extends BaseController{

    private static Logger log = LoggerFactory.getLogger(CompensateController.class);
    @Autowired
    private CompensateService compensateService;
    @Autowired
    private IAccountAdjustService accountAdjustService;
    @Autowired
    private OCashReceivablesService oCashReceivablesService;
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "toRefundPriceDiffList")
    public String toRefundPriceDiffList(HttpServletRequest request,ORefundPriceDiff refundPriceDiff){
        optionsData(request);
        return "order/refundPriceDiffList";
    }

    @RequestMapping(value = "refundPriceDiffList")
    @ResponseBody
    public Object refundPriceDiffList(HttpServletRequest request,ORefundPriceDiffVo refundPriceDiff){
        Page pageInfo = pageProcess(request);
        String dataRole = request.getParameter("dataRole");
        refundPriceDiff.setcUser(String.valueOf(getUserId()));
        PageInfo resultPageInfo = compensateService.compensateList(refundPriceDiff,pageInfo,dataRole,getUserId());
        List<Map<String,Object>> rows = resultPageInfo.getRows();
        rows.forEach(row->{
            row.put("APPLY_COMP_NAME",PriceDiffType.getContentByValue(String.valueOf(row.get("APPLY_COMP_TYPE"))));
            row.put("REL_COMP_NAME",PriceDiffType.getContentByValue(String.valueOf(row.get("REL_COMP_TYPE"))));
        });
        return resultPageInfo;
    }

    @RequestMapping(value = "toCompensateAmtAddPage")
    public String toCompensateAmtAddPage(HttpServletRequest request){
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        request.setAttribute("payTypeSelect",PayType.getAllOption());
        request.setAttribute("orderType",request.getParameter("orderType"));
        String key = RedisCachKey.APP_SPLIT+":"+getStringUserId();
        List<Map<String, Object>> maps = redisService.popListMap(key);
        if(maps.size()!=0){
            request.setAttribute("rKey",key);
        }
        return "order/compensateAmtAdd";
    }

    @RequestMapping(value ="analysisFile")
    @ResponseBody
    public Object analysisFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        String orderType = request.getParameter("orderType");
        String rKey = request.getParameter("rKey");
        try {
            List<Map<String, Object>> resultList = new ArrayList<>();
            List<List<Object>> excelList = new ArrayList<>();
            if(StringUtils.isBlank(rKey)){
                if (file == null) {
                    return renderError("请上传文件");
                }
                if(file.getSize()==0){
                    return renderError("请上传文件");
                }
                InputStream in = file.getInputStream();
                excelList = ImportExcelUtil.getListByExcel(in, file.getOriginalFilename());
                if(null==excelList || excelList.size()==0){
                    return renderError("文档记录为空");
                }
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
            Set<String> agentIdSet = new HashSet<>();
            for (List<Object> objects : excelList) {
                try {
                    List<Map<String, Object>> compensateList = compensateService.getOrderMsgByExcel(objects,getUserId(),getAgentId());
                    for (Map<String, Object> stringObjectMap : compensateList) {
                        stringObjectMap.put("SUM_PROPRICE",new BigDecimal(stringObjectMap.get("SETTLEMENT_PRICE").toString()).multiply(new BigDecimal(stringObjectMap.get("PRO_NUM").toString())));
                        resultList.add(stringObjectMap);
                        agentIdSet.add(String.valueOf(stringObjectMap.get("AGENT_ID")));
                    }
                } catch (ProcessException e) {
                    e.printStackTrace();
                    return JsonUtil.objectToJson(renderError(e.getMessage()));
                }
            }
            if(agentIdSet.size()>1){
                log.info("退补差价代理商不唯一");
                throw new ProcessException("代理商不唯一");
            }
            String toJson = JsonUtil.objectToJson(resultList);
            return toJson;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.objectToJson(renderError(e.getMessage()));
        }
    }

    private Object priceDiff(AgentVo agentVo){
        List<ORefundPriceDiffDetail> refundPriceDiffDetailList = agentVo.getRefundPriceDiffDetailList();
        BigDecimal sumCalPrice = new BigDecimal(0);
        for (ORefundPriceDiffDetail row : refundPriceDiffDetailList) {
            if(StringUtils.isNotBlank(row.getActivityRealId())) {
                BigDecimal calPrice = compensateService.calculatePriceDiff(row.getBeginSn(),row.getEndSn(),row.getActivityFrontId(), row.getActivityRealId(), row.getChangeCount(), agentVo.getoRefundPriceDiff());
                sumCalPrice = sumCalPrice.add(calPrice);
            }
        }
        return sumCalPrice;
    }

    /**
     * 计算差价
     * @param agentVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"calculatePriceDiff"}, method = RequestMethod.POST)
    public Object calculatePriceDiff(@RequestBody AgentVo agentVo) {
        Object obj = priceDiff(agentVo);
        return obj;
    }

    @ResponseBody
    @RequestMapping(value = {"compensateAmtSave"}, method = RequestMethod.POST)
    public Object compensateAmtSave(@RequestBody AgentVo agentVo,HttpServletRequest request, HttpServletResponse response) {

        String sumCalPrice = String.valueOf(priceDiff(agentVo));
        ORefundPriceDiff oRefundPriceDiff = agentVo.getoRefundPriceDiff();
        oRefundPriceDiff.setApplyCompType(sumCalPrice.contains("-")?PriceDiffType.DETAIN_AMT.getValue():PriceDiffType.REPAIR_AMT.getValue());
        oRefundPriceDiff.setApplyCompAmt(sumCalPrice.contains("-")?new BigDecimal(sumCalPrice.substring(1)):new BigDecimal(sumCalPrice));
        AgentResult agentResult = compensateService.compensateAmtSave(oRefundPriceDiff, agentVo.getRefundPriceDiffDetailList(),agentVo.getRefundPriceDiffFile(), String.valueOf(getUserId()),agentVo.getoCashReceivablesVoList());
        if(!agentResult.isOK()){
            return AgentResult.fail(agentResult.getMsg());
        }
        if(agentVo.getFlag().equals("2")){
            return startCompensate(String.valueOf(agentResult.getData()));
        }
        if(agentResult.isOK()){
            return AgentResult.ok("处理成功！");
        }
        return AgentResult.fail(agentResult.getMsg());
    }


    /**
     * 启动审批
     * @param compId
     * @return
     */
    @ResponseBody
    @RequestMapping("startCompensate")
    public AgentResult startCompensate(String compId){
        AgentResult result = new AgentResult(500, "参数错误", "");
        try {
            result = compensateService.startCompensateActiviy(compId,String.valueOf(getUserId()));
        }catch (MessageException e) {
            return AgentResult.fail(e.getMsg());
        }catch (Exception e) {
            return AgentResult.fail(e.getMessage());
        }
        return result;
    }

    /**
     * 跳转审核页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("approvalCompensateView")
    public String approvalCompensateView(HttpServletRequest request, HttpServletResponse response,Model model) {
        ORefundPriceDiff oRefundPriceDiff = new ORefundPriceDiff();
        try {
            optionsData(request);
            String taskId = request.getParameter("taskId");
            String proIns = request.getParameter("proIns");
            String busType = request.getParameter("busType");
            String agentBusId = request.getParameter("busId");
            oRefundPriceDiff = compensateService.queryRefDiffDetail(agentBusId);
            request.setAttribute("oRefundPriceDiff",oRefundPriceDiff);
            request.setAttribute("taskId", taskId);
            request.setAttribute("proIns", proIns);
            List<Map<String, Object>> actRecordList = queryApprovalRecord(agentBusId, BusActRelBusType.COMPENSATE.name());
            request.setAttribute("actRecordList", actRecordList);
            request.setAttribute("agentBusId", agentBusId);
            Map<String, Object> adjust = accountAdjustService.adjust(false,oRefundPriceDiff.getRelCompAmt(), AdjustType.TCJ.name(),
                    1, oRefundPriceDiff.getRefundPriceDiffDetailList().get(0).getAgentId(), String.valueOf(getUserId()),
                    oRefundPriceDiff.getId(), PamentSrcType.TUICHAJIA_DIKOU.code);
            request.setAttribute("machineDebtAmt",adjust.get("takeAmt"));
            //线下打款信息
            List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null,CashPayType.REFUNDPRICEDIFF,agentBusId, null);
            request.setAttribute("paylist", listoCashReceivables);
            request.setAttribute("paylist_approve", listoCashReceivables);
            //收款公司
            List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
            request.setAttribute("recCompList", payComp_list);
            request.setAttribute("payTypeSelect",PayType.getAllOption());
            request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        } catch (ProcessException e) {
            e.printStackTrace();
            log.info("查看审批任务异常",e.getMessage());
        }  catch (Exception e) {
            e.printStackTrace();
        }
        if(oRefundPriceDiff.getOrderType().compareTo(OrderType.NEW.getValue())==0){
            return "activity/compensateApproval";
        }else{
            return "activity/oldCompensateApproval";
        }

    }

    /**
     * 处理任务
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("taskApproval")
    public ResultVO taskApproval(@RequestBody AgentVo agentVo,HttpServletRequest request, HttpServletResponse response){

        AgentResult result = null;
        try {
            result = compensateService.approvalTask(agentVo, String.valueOf(getUserId()));
        } catch (Exception e) {
            log.info("compensate.taskApproval处理任务异常:"+e.getMessage());
            e.printStackTrace();
        } finally {
            if(result==null){
                return ResultVO.fail("处理失败");
            }
            if(result.isOK()){
                return ResultVO.success("处理成功");
            }else{
                return ResultVO.fail("处理失败");
            }
        }
    }

    /**
     * 退补差价查看
     * @param id
     * @param reviewStatus
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = {"refundPriceDiffQuery"})
    public String compensateQuery(String id, String reviewStatus, Model model, HttpServletRequest request) {
        optionsData(request);
        ORefundPriceDiff oRefundPriceDiff = compensateService.queryRefDiffDetail(id);
        request.setAttribute("reviewStatus", oRefundPriceDiff.getReviewStatus());
        request.setAttribute("busIdImg", id);
        request.setAttribute("busTypeImg", BusActRelBusType.COMPENSATE.name());
        request.setAttribute("oRefundPriceDiff",oRefundPriceDiff);
        List<Map<String, Object>> actRecordList = queryApprovalRecord(id, BusActRelBusType.COMPENSATE.name());
        request.setAttribute("actRecordList", actRecordList);
        //线下打款信息
        List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null,CashPayType.REFUNDPRICEDIFF,id, null);
        request.setAttribute("paylist", listoCashReceivables);
        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("payTypeSelect",PayType.getAllOption());
        return "order/refundPriceDiffQuery";
    }

    /**
     * 抵扣
     * @param request
     * @param oRefundPriceDiff
     * @param isRealAdjust
     * @param adjustAmt
     * @return
     */
    @RequestMapping(value = {"adjust"})
    @ResponseBody
    public Object adjust(HttpServletRequest request,ORefundPriceDiff oRefundPriceDiff,Boolean isRealAdjust,BigDecimal adjustAmt){
        try {
            if(StringUtils.isBlank(oRefundPriceDiff.getId())){
                String agentBusId = request.getParameter("busId");
                if(StringUtils.isBlank(agentBusId)){
                    return renderError("业务id为空");
                }
                oRefundPriceDiff = compensateService.queryRefDiffDetail(agentBusId);
            }
            if(null==adjustAmt){
                return renderError("金额有误");
            }
            if(null==isRealAdjust){
                return renderError("参数有误");
            }
            log.info("扣除机具欠款请求参数：isRealAdjust：{},adjustAmt:{},agentId:{},UserId:{},priceDiffId:{}",isRealAdjust,adjustAmt,oRefundPriceDiff.getRefundPriceDiffDetailList().get(0).getAgentId(),String.valueOf(getUserId()),oRefundPriceDiff.getId());
            Map<String, Object> adjust = accountAdjustService.adjust(isRealAdjust,adjustAmt, AdjustType.TCJ.name(), 1,
                    oRefundPriceDiff.getRefundPriceDiffDetailList().get(0).getAgentId(),
                    String.valueOf(getUserId()), oRefundPriceDiff.getId(), PamentSrcType.TUICHAJIA_DIKOU.code);
            log.info("扣除机具欠款返回：{}",adjust);
        } catch (ProcessException e) {
            return renderError(e.getMessage());
        } catch (Exception e) {
            return renderError(Constants.FAIL_MSG);
        }
        return renderSuccess("执行扣款计划成功");
    }

    @RequestMapping(value = "toCompensateAmtEditPage")
    public String toCompensateAmtEditPage(HttpServletRequest request,String id){
        optionsData(request);
        ORefundPriceDiff oRefundPriceDiff = compensateService.queryRefDiffDetail(id);
        request.setAttribute("oRefundPriceDiff",oRefundPriceDiff);
        //线下打款信息
        List<OCashReceivables> listoCashReceivables  = oCashReceivablesService.query(null,null,CashPayType.REFUNDPRICEDIFF,id, null);
        //收款公司
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("paylist", listoCashReceivables);
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        request.setAttribute("payTypeSelect",PayType.getAllOption());
        if(oRefundPriceDiff.getOrderType().compareTo(OrderType.NEW.getValue())==0){
            return "order/compensateAmtEdit";
        }else{
            return "order/oldCompensateAmtEdit";
        }

    }


    @ResponseBody
    @RequestMapping(value = {"compensateAmtEdit"}, method = RequestMethod.POST)
    public AgentResult compensateAmtEdit(@RequestBody AgentVo agentVo,HttpServletRequest request, HttpServletResponse response) {

        List<ORefundPriceDiffDetail> refundPriceDiffDetailList = agentVo.getRefundPriceDiffDetailList();
        ORefundPriceDiff oRefundPriceDiff = agentVo.getoRefundPriceDiff();
        String sumCalPrice = String.valueOf(priceDiff(agentVo));
        oRefundPriceDiff.setApplyCompType(sumCalPrice.contains("-")?PriceDiffType.DETAIN_AMT.getValue():PriceDiffType.REPAIR_AMT.getValue());
        oRefundPriceDiff.setApplyCompAmt(sumCalPrice.contains("-")?new BigDecimal(sumCalPrice.substring(1)):new BigDecimal(sumCalPrice));
        oRefundPriceDiff.setRelCompType(oRefundPriceDiff.getApplyCompType());
        oRefundPriceDiff.setRelCompAmt(oRefundPriceDiff.getApplyCompAmt());
        AgentResult agentResult = compensateService.compensateAmtEdit(agentVo.getoRefundPriceDiff(), refundPriceDiffDetailList, agentVo.getRefundPriceDiffFile(), String.valueOf(getUserId()),agentVo.getoCashReceivablesVoList());
        return agentResult;
    }

    @RequestMapping(value = "compensateAmtDel")
    @ResponseBody
    public Object compensateAmtDel(HttpServletRequest request){
        try {
            String busId = request.getParameter("busId");
            AgentResult agentResult = compensateService.compensateAmtDel(busId, getStringUserId());
            if(agentResult.isOK()){
                return renderSuccess("删除成功！");
            }else{
                return renderError(agentResult.getMsg());
            }
        } catch (MessageException e) {
            e.printStackTrace();
            return renderError(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return renderError("删除失败！");
        }
    }

    @RequestMapping(value = "toRefundPriceDiffDetailList")
    public String toRefundPriceDiffDetailList(HttpServletRequest request,ORefundPriceDiff refundPriceDiff){
        optionsData(request);
        return "order/refundPriceDiffDetailList";
    }

    @RequestMapping(value = "refundPriceDiffDetailList")
    @ResponseBody
    public Object refundPriceDiffDetailList(HttpServletRequest request,ORefundPriceDiffDetail refundPriceDiffDetail){
        Page pageInfo = pageProcess(request);
        PageInfo resultPageInfo = compensateService.compensateDetailList(refundPriceDiffDetail,pageInfo,"",getUserId());
        return resultPageInfo;
    }

    @RequestMapping("manualDispose")
    @ResponseBody
    public AgentResult manualDispose(String busId){
        try {
            compensateService.manualDispose(busId);
        } catch (ProcessException e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMessage());
        }
        return AgentResult.ok();
    }

}
