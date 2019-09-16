package com.ryx.credit.cms.controller.order;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ImportExcelUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.PayType;
import com.ryx.credit.common.enumc.PriceDiffType;
import com.ryx.credit.common.enumc.RedisCachKey;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiff;
import com.ryx.credit.pojo.admin.order.ORefundPriceDiffDetail;
import com.ryx.credit.pojo.admin.order.OSubOrder;
import com.ryx.credit.pojo.admin.order.OSubOrderActivity;
import com.ryx.credit.pojo.admin.vo.AgentVo;
import com.ryx.credit.service.order.IAccountAdjustService;
import com.ryx.credit.service.order.OCashReceivablesService;
import com.ryx.credit.service.order.OldCompensateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 补差价控制层
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/7/24 15:30
 */
@RequestMapping("oldCompensate")
@Controller
public class OldCompensateController extends BaseController{

    private static Logger log = LoggerFactory.getLogger(OldCompensateController.class);
    @Autowired
    private OldCompensateService oldCompensateService;
    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "toCompensateAmtAddPage")
    public String toCompensateAmtAddPage(HttpServletRequest request){
        List<PayComp> payComp_list = ServiceFactory.apaycompService.recCompList();
        request.setAttribute("recCompList", payComp_list);
        request.setAttribute("paylist_model", Calendar.getInstance().getTime().getTime());
        request.setAttribute("payTypeSelect",PayType.getAllOption());
        String agentId = request.getParameter("agentId");
        if(StringUtils.isBlank(agentId)){
            agentId = getAgentId();
        }
        request.setAttribute("agentId",agentId);
        request.setAttribute("orderType",request.getParameter("orderType"));
        String key = RedisCachKey.APP_SPLIT+":"+getStringUserId();
        List<Map<String, Object>> maps = redisService.popListMap(key);
        if(maps.size()!=0){
            request.setAttribute("rKey",key);
        }
        return "order/oldCompensateAmtAdd";
    }


    @RequestMapping(value ="analysisFile")
    @ResponseBody
    public Object analysisFile(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
        try {
            String orderType = request.getParameter("orderType");
            String rKey = request.getParameter("rKey");
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
                        list.add(map.get("proModel"));
                        excelList.add(list);
                    }
                }
            }
            List<Map<String, Object>> resultList = oldCompensateService.getOrderMsgByExcel(excelList);
            String toJson = JsonUtil.objectToJson(resultList);
            return toJson;
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.objectToJson(renderError(e.getMessage()));
        }
    }

    @ResponseBody
    @RequestMapping(value = {"compensateAmtSave"}, method = RequestMethod.POST)
    public Object compensateAmtSave(@RequestBody AgentVo agentVo,HttpServletRequest request, HttpServletResponse response) {

        try {
            String sumCalPrice = String.valueOf(priceDiff(agentVo));
            ORefundPriceDiff oRefundPriceDiff = agentVo.getoRefundPriceDiff();
            oRefundPriceDiff.setApplyCompType(sumCalPrice.contains("-")?PriceDiffType.DETAIN_AMT.getValue():PriceDiffType.REPAIR_AMT.getValue());
            oRefundPriceDiff.setApplyCompAmt(sumCalPrice.contains("-")?new BigDecimal(sumCalPrice.substring(1)):new BigDecimal(sumCalPrice));
            AgentResult agentResult = oldCompensateService.compensateAmtSave(oRefundPriceDiff, agentVo.getRefundPriceDiffDetailList(),agentVo.getRefundPriceDiffFile(), String.valueOf(getUserId()),agentVo.getoCashReceivablesVoList());
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
        }catch (ProcessException e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return AgentResult.fail(e.getLocalizedMessage());
        }

    }

    @ResponseBody
    @RequestMapping("startCompensate")
    public AgentResult startCompensate(String compId){
        AgentResult result = new AgentResult(500, "参数错误", "");
        try {
            result = oldCompensateService.startCompensateActiviy(compId,String.valueOf(getUserId()));
        }catch (MessageException e) {
            return AgentResult.fail(e.getMsg());
        }catch (Exception e) {
            return AgentResult.fail(e.getMessage());
        }
        return result;
    }


    @RequestMapping(value = "queryOrder")
    @ResponseBody
    public Object queryOrder(HttpServletRequest request){

        try {
            String orderId = request.getParameter("orderId");
            List<OSubOrder> oSubOrders = oldCompensateService.queryOrder(orderId);
            return AgentResult.ok(oSubOrders);
        } catch (MessageException e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMessage());
        }
    }

    /**
     * 计算差价
     * @param agentVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"calculatePriceDiff"}, method = RequestMethod.POST)
    public Object calculatePriceDiff(@RequestBody AgentVo agentVo) {
        Object obj = null;
        try {
            obj = priceDiff(agentVo);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return obj;
    }


    private BigDecimal priceDiff(AgentVo agentVo)throws Exception{
        List<ORefundPriceDiffDetail> refundPriceDiffDetailList = agentVo.getRefundPriceDiffDetailList();
        BigDecimal sumCalPrice = new BigDecimal(0);
        for (ORefundPriceDiffDetail row : refundPriceDiffDetailList) {
            if(StringUtils.isNotBlank(row.getActivityRealId())) {
                BigDecimal calPrice = oldCompensateService.calculatePriceDiff(row.getActivityFrontId(), row.getActivityRealId(), row.getChangeCount());
                sumCalPrice = sumCalPrice.add(calPrice);
            }
        }
        return sumCalPrice;
    }

    @RequestMapping(value = "queryActivity")
    @ResponseBody
    public Object queryActivity(HttpServletRequest request){

        try {
            String orderId = request.getParameter("subOrderId");
            OSubOrderActivity subOrderActivity = oldCompensateService.queryActivity(orderId);
            return AgentResult.ok(subOrderActivity);
        } catch (MessageException e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = {"compensateAmtEdit"}, method = RequestMethod.POST)
    public AgentResult compensateAmtEdit(@RequestBody AgentVo agentVo,HttpServletRequest request, HttpServletResponse response) {

        try {
            List<ORefundPriceDiffDetail> refundPriceDiffDetailList = agentVo.getRefundPriceDiffDetailList();
            ORefundPriceDiff oRefundPriceDiff = agentVo.getoRefundPriceDiff();
            String sumCalPrice = String.valueOf(priceDiff(agentVo));
            oRefundPriceDiff.setApplyCompType(sumCalPrice.contains("-")?PriceDiffType.DETAIN_AMT.getValue():PriceDiffType.REPAIR_AMT.getValue());
            oRefundPriceDiff.setApplyCompAmt(sumCalPrice.contains("-")?new BigDecimal(sumCalPrice.substring(1)):new BigDecimal(sumCalPrice));
            oRefundPriceDiff.setRelCompType(oRefundPriceDiff.getApplyCompType());
            oRefundPriceDiff.setRelCompAmt(oRefundPriceDiff.getApplyCompAmt());
            AgentResult agentResult = oldCompensateService.compensateAmtEdit(agentVo.getoRefundPriceDiff(), refundPriceDiffDetailList, agentVo.getRefundPriceDiffFile(), String.valueOf(getUserId()),agentVo.getoCashReceivablesVoList());
            return agentResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AgentResult.fail();
    }

}
