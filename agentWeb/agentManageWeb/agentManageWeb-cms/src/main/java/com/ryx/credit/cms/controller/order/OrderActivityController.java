package com.ryx.credit.cms.controller.order;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.AgStatus;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.PosType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.CUser;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.order.OActivity;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OProduct;
import com.ryx.credit.pojo.admin.vo.OActivityVo;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.order.OrderActivityService;
import com.ryx.credit.service.order.OrderService;
import com.ryx.credit.service.order.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 活动控制层
 *
 * @version V1.0
 * @Description:
 * @author: Liudh
 * @date: 2018/7/16 18:30
 */
@RequestMapping("activity")
@Controller
public class OrderActivityController extends BaseController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderActivityService orderActivityService;
    @Autowired
    private DictOptionsService dictOptionsService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private BusinessPlatformService businessPlatformService;


    @RequestMapping(value = "toActivityList")
    public String toActivityList(HttpServletRequest request, OProduct product) {
        List<PlatForm> platFormList = ServiceFactory.businessPlatformService.queryAblePlatForm();
        request.setAttribute("platFormList", platFormList);
        request.setAttribute("posTypeList", PosType.getContentMap());
        List<OProduct> productList = productService.allProductList(new OProduct());
        if (null != productList && productList.size() > 0) {
            for (OProduct oProduct : productList) {
                if(StringUtils.isNotBlank(oProduct.getProCom())) {
                    Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), oProduct.getProCom());
                    if (null != dictByValue)
                        oProduct.setName(dictByValue.getdItemname());
                }
                if(StringUtils.isNotBlank(oProduct.getProType())) {
                    Dict byValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(), oProduct.getProType());
                    if (null != byValue)
                        oProduct.setProTypeName(byValue.getdItemname());
                }
            }
        }
        request.setAttribute("productList", productList);
        return "order/activityList";
    }

    @RequestMapping(value = "activityList")
    @ResponseBody
    public Object activityList(HttpServletRequest request, OActivity activity) {
        Page pageInfo = pageProcess(request);
        PageInfo resultPageInfo = orderActivityService.activityList(activity, pageInfo);
        List<OActivity> rows = resultPageInfo.getRows();
        rows.forEach(row -> {
            Dict dict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(), row.getProType());
            if (dict != null)
                row.setProType(dict.getdItemname());
            if (StringUtils.isNotBlank(row.getcUser())) {
                CUser cUser = userService.selectById(row.getcUser());
                if(null!=cUser)
                row.setcUser(cUser.getName());
            }
            if (StringUtils.isNotBlank(row.getuUser())) {
                CUser uUser = userService.selectById(row.getuUser());
                if(null!=uUser)
                row.setuUser(uUser.getName());
            }
            OProduct product = productService.findById(row.getProductId());
            if (null!=product) {
                row.setProductId(product.getProCode()+"-"+product.getProName());
            }
            if (null != row.getActivityWay()) {
                Dict disDict = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.ACTIVITY_DIS_TYPE.name(), row.getActivityWay());
                if (disDict != null)
                    row.setActivityWay(disDict.getdItemname());
            }
            if (null != row.getVender()) {
                Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), row.getVender());
                if (null != dictByValue)
                    row.setVender(dictByValue.getdItemname());
            }
            if (null != row.getProModel()) {
                Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.PROMODE.name(), row.getProModel());
                if (null != dictByValue)
                    row.setProModel(dictByValue.getdItemname());
            }
            if (null != row.getActivityCondition()) {
                Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.ACTIVITY_CONDITION.name(), row.getActivityCondition());
                if (null != dictByValue)
                    row.setActivityCondition(dictByValue.getdItemname());
            }
            row.setPosType(PosType.getContentByValue(row.getPosType()));
        });
        return resultPageInfo;
    }


    @RequestMapping(value = "toActivityAdd")
    public String toActivityAdd(HttpServletRequest request, OActivity activity) {
        orderDictData(request);
        List<OProduct> productList = productService.allProductList(new OProduct());
        if (null != productList && productList.size() > 0) {
            for (OProduct oProduct : productList) {
                if(StringUtils.isNotBlank(oProduct.getProCom())) {
                    Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), oProduct.getProCom());
                    if (null != dictByValue)
                        oProduct.setName(dictByValue.getdItemname());
                }
                if(StringUtils.isNotBlank(oProduct.getProType())) {
                    Dict byValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(), oProduct.getProType());
                    if (null != byValue)
                        oProduct.setProTypeName(byValue.getdItemname());
                }
            }
        }
        request.setAttribute("productList", productList);
        request.setAttribute("posTypeList", PosType.getContentMap());
        return "order/activityAdd";
    }

    @RequestMapping(value = "activityAdd")
    @ResponseBody
    public ResultVO activityAdd(HttpServletRequest request, OActivityVo activityVo) throws MessageException {
       try {
           String userId = String.valueOf(getUserId());
           activityVo.setcUser(userId);
           activityVo.setuUser(userId);
           if (StringUtils.isNotBlank(activityVo.getBeginTimeStr())) {
               activityVo.setBeginTime(DateUtils.stringToDate(activityVo.getBeginTimeStr()));
           }
           if (StringUtils.isNotBlank(activityVo.getEndTimeStr())) {
               activityVo.setEndTime(DateUtils.stringToDate(activityVo.getEndTimeStr()));
           }
           if (null==activityVo.getOriginalPrice() || activityVo.getOriginalPrice().compareTo(BigDecimal.ZERO)<0) {
               return ResultVO.fail("原价不能为空");
           }
           if (null==activityVo.getPrice() || activityVo.getPrice().compareTo(BigDecimal.ZERO)<0) {
               return ResultVO.fail("价格不能为空");
           }
           ResultVO resultVO = orderActivityService.saveActivity(activityVo);
       }catch (Exception e) {
           e.printStackTrace();
           if(e instanceof MessageException){
               String msg = ((MessageException) e).getMsg();
               return ResultVO.fail(msg);
           }
           return ResultVO.fail(e.getMessage());
       }
        return ResultVO.success(null);
    }

    @RequestMapping(value = "toActivityEdit")
    public String toActivityEdit(HttpServletRequest request, OActivity activity) {
        orderDictData(request);
        List<OProduct> productList = productService.allProductList(new OProduct());
        if (null != productList && productList.size() > 0) {
            for (OProduct oProduct : productList) {
                if(StringUtils.isNotBlank(oProduct.getProCom())) {
                    Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), oProduct.getProCom());
                    if (null != dictByValue)
                        oProduct.setName(dictByValue.getdItemname());
                }
                if(StringUtils.isNotBlank(oProduct.getProType())) {
                    Dict byValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(), oProduct.getProType());
                    if (null != byValue)
                        oProduct.setProTypeName(byValue.getdItemname());
                }
            }
        }
        OActivity oActivity = orderActivityService.findById(activity.getId());
        if(StringUtils.isNotBlank(oActivity.getVender())) {
            Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), oActivity.getVender());
            if (null != dictByValue) {
                if (StringUtils.isNotBlank(dictByValue.getdItemname())) {
                    oActivity.setVenderName(dictByValue.getdItemname());
                }
            }
        }
        if(StringUtils.isNotBlank(oActivity.getProType())) {
            Dict byValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(), oActivity.getProType());
            if (StringUtils.isNotBlank(byValue.getdItemname()))
                oActivity.setProTypeName(byValue.getdItemname());
        }
        Map map = null;
        try {
            map = orderActivityService.selectTermMachine(oActivity.getPlatform());
        } catch (MessageException e) {
            e.printStackTrace();
        }
        request.setAttribute("productList", productList);
        request.setAttribute("oActivity", oActivity);
        request.setAttribute("termMachineList", map.get("termMachineList"));
        request.setAttribute("mposTermBatchList", map.get("mposTermBatchList"));
        request.setAttribute("mposTermTypeList", map.get("mposTermTypeList"));
        request.setAttribute("posTypeList", PosType.getContentMap());
        return "order/activityEdit";
    }

    @RequestMapping(value = "activityEdit")
    @ResponseBody
    public Object activityEdit(HttpServletRequest request, OActivityVo activityVo) {
        String userId = String.valueOf(getUserId());
        activityVo.setuUser(userId);
        if (StringUtils.isNotBlank(activityVo.getBeginTimeStr())) {
            activityVo.setBeginTime(DateUtils.stringToDate(activityVo.getBeginTimeStr()));
        }
        if (StringUtils.isNotBlank(activityVo.getEndTimeStr())) {
            activityVo.setEndTime(DateUtils.stringToDate(activityVo.getEndTimeStr()));
        }
        if (null==activityVo.getOriginalPrice() || activityVo.getOriginalPrice().compareTo(BigDecimal.ZERO)<0) {
            return ResultVO.fail("原价不能为空");
        }
        if (null==activityVo.getPrice() || activityVo.getPrice().compareTo(BigDecimal.ZERO)<0) {
            return ResultVO.fail("价格不能为空");
        }
        AgentResult agentResult = orderActivityService.updateActivity(activityVo);
        if (agentResult.isOK()) {
            return renderSuccess("修改成功！");
        }
        return renderSuccess("修改失败！");
    }

    @RequestMapping(value = "toActivityCopy")
    public String toActivityCopy(HttpServletRequest request, OActivity activity) {
        orderDictData(request);
        List<OProduct> productList = productService.allProductList(new OProduct());
        if (null != productList && productList.size() > 0) {
            for (OProduct oProduct : productList) {
                if(StringUtils.isNotBlank(oProduct.getProCom())) {
                    Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), oProduct.getProCom());
                    if (null != dictByValue)
                        oProduct.setName(dictByValue.getdItemname());
                }
                if(StringUtils.isNotBlank(oProduct.getProType())) {
                    Dict byValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(), oProduct.getProType());
                    if (null != byValue)
                        oProduct.setProTypeName(byValue.getdItemname());
                }
            }
        }
        OActivity oActivity = orderActivityService.findById(activity.getId());
        if(StringUtils.isNotBlank(oActivity.getVender())) {
            Dict dictByValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MANUFACTURER.name(), oActivity.getVender());
            if (null != dictByValue) {
                if (StringUtils.isNotBlank(dictByValue.getdItemname())) {
                    oActivity.setVenderName(dictByValue.getdItemname());
                }
            }
        }
        if(StringUtils.isNotBlank(oActivity.getProType())) {
            Dict byValue = dictOptionsService.findDictByValue(DictGroup.ORDER.name(), DictGroup.MODEL_TYPE.name(), oActivity.getProType());
            if (StringUtils.isNotBlank(byValue.getdItemname()))
                oActivity.setProTypeName(byValue.getdItemname());
        }
        Map map = null;
        try {
            map = orderActivityService.selectTermMachine(oActivity.getPlatform());
        } catch (MessageException e) {
            e.printStackTrace();
        }

        //获取重新生成的id
        OActivity toActivityCopy = orderActivityService.toActivityCopy(oActivity);
        oActivity.setId(toActivityCopy.getId());
        request.setAttribute("toActivityCopy", toActivityCopy);

        request.setAttribute("productList", productList);
        request.setAttribute("oActivity", oActivity);
        request.setAttribute("termMachineList", map.get("termMachineList"));
        request.setAttribute("mposTermBatchList", map.get("mposTermBatchList"));
        request.setAttribute("mposTermTypeList", map.get("mposTermTypeList"));
        request.setAttribute("posTypeList", PosType.getContentMap());
        return "order/activityCopy";
    }

    /**
     * 活动复制
     * activity/activityCopy
     * @param request
     * @param activityVo
     * @return
     */
    @RequestMapping(value = "activityCopy")
    @ResponseBody
    public Object activityCopy(HttpServletRequest request, OActivityVo activityVo) {
        String userId = String.valueOf(getUserId());
        activityVo.setcUser(userId);
        activityVo.setuUser(userId);
        if (StringUtils.isNotBlank(activityVo.getBeginTimeStr())) {
            activityVo.setBeginTime(DateUtils.stringToDate(activityVo.getBeginTimeStr()));
        }
        if (StringUtils.isNotBlank(activityVo.getEndTimeStr())) {
            activityVo.setEndTime(DateUtils.stringToDate(activityVo.getEndTimeStr()));
        }
        if (null==activityVo.getOriginalPrice() || activityVo.getOriginalPrice().compareTo(BigDecimal.ZERO)<0) {
            return ResultVO.fail("原价不能为空");
        }
        if (null==activityVo.getPrice() || activityVo.getPrice().compareTo(BigDecimal.ZERO)<0) {
            return ResultVO.fail("价格不能为空");
        }
        AgentResult agentResult = orderActivityService.activityCopy(activityVo);
        if (agentResult.isOK()) {
            return renderSuccess("复制成功！");
        } else {
            return renderError(agentResult.getMsg());
        }
    }


    /**
     * activity/queryAllActivity
     *
     * @return
     */
    @RequestMapping(value = "queryAllActivity")
    @ResponseBody
    public List<OActivity> queryAllActivity() {
        List<OActivity> allActivityList = orderActivityService.allActivity();
        if (null == allActivityList && allActivityList.size() == 0) {
            return Arrays.asList();
        }
        return allActivityList;
    }


    /**
     * 根据商品ID查询能够使用的活动信息
     * activity/queryProductCanActivity
     *
     * @param request
     * @param proId
     * @param agentId
     * @return
     */
    @RequestMapping(value = "queryProductCanActivity")
    @ResponseBody
    public List<OActivity> queryProductCanActivity(HttpServletRequest request,
                                                   @RequestParam("proId") String proId,
                                                   @RequestParam("agentId") String agentId,
                                                   @RequestParam(value = "orderId",required = false) String orderId,
                                                   @RequestParam(value = "orderAgentBusifo",required = false) String orderAgentBusifo,
                                                   @RequestParam(value = "oldActivityId",required = false) String oldActivityId) {
        if(StringUtils.isNotBlank(orderId)) {
            OOrder order =  orderService.getById(orderId);
            orderAgentBusifo = order.getBusId();
        }
        return orderActivityService.productActivity(proId, agentId,orderAgentBusifo,oldActivityId);
    }

    @RequestMapping(value = "queryTermMachine")
    @ResponseBody
    public Object queryTermMachine(String platformNum) {
        try {
            return orderActivityService.selectTermMachine(platformNum);
        } catch (MessageException e) {
            e.printStackTrace();
        }
       return null;
    }


    @RequestMapping(value = "toActivityVisible")
    public String toActivityVisible(HttpServletRequest request, OActivity activity) {
        OActivity newActivity = orderActivityService.findById(activity.getId());
        request.setAttribute("activity",newActivity);
        List<Map<String, String>> configuredList = orderActivityService.selectConfigured(newActivity.getActCode());
        request.setAttribute("configuredList",configuredList);
        return "order/activityVisible";
    }

    @RequestMapping(value = "activityVisible")
    @ResponseBody
    public AgentResult activityVisible(HttpServletRequest request) {
        String activityId = request.getParameter("activityId");
        String visible = request.getParameter("visible");
        String[] agentIds = request.getParameterValues("agentId");
        try {
            orderActivityService.saveActivityVisible(activityId,visible,agentIds,getStringUserId());
        } catch (MessageException e) {
            e.printStackTrace();
            return AgentResult.fail(e.getMsg());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AgentResult.ok();
    }

    @RequestMapping("verifyAgent")
    @ResponseBody
    public Object verifyAgent(String agUniqNum) {
        List<String> agStatusList = new ArrayList<>();
        agStatusList.add(AgStatus.Approving.name());
        agStatusList.add(AgStatus.Approved.name());
        AgentResult agentResult = businessPlatformService.verifyAgent(agUniqNum,agStatusList);
        if(agentResult.isOK()){
            return renderSuccess(JsonUtil.objectToJson(agentResult.getData()));
        }else{
            return renderError(agentResult.getMsg());
        }
    }

}
