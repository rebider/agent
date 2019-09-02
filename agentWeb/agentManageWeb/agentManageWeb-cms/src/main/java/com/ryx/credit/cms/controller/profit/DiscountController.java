package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ExcelExportSXXSSF;
import com.ryx.credit.cms.util.MyUtil;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.Platform;
import com.ryx.credit.common.enumc.RewardStatus;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.Agent;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.IPTaxAdjustService;
import com.ryx.credit.profit.service.IPosCheckService;
import com.ryx.credit.profit.service.IPosRewardService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentColinfoService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.BusinessPlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.String.valueOf;

/**
 * 优惠政策控制层
 * @version V1.0
 * @Description:
 * @author: WANGY
 * @date: 2018/7/23 15:30
 */
@RequestMapping("discount")
@Controller
public class DiscountController extends BaseController{

    private static Logger LOG = LoggerFactory.getLogger(DiscountController.class);
//    private static String POSCHECK_EXPORT_EXECL_PATH = AppConfig.getProperty("export.path");
//    String PosCheck_ExportTitle = ("代理商编码,代理商名称,考核起始日期,考核截止日期,交易总额（万）,机具订货量,分润比例,申请日期,考核状态");
//    String PosCheck_ExportColum = ("agentId,agentName,checkDateS,checkDateE,totalAmt,posOrders,profitScale,appDate,checkStatus");
    @Autowired
    private IPosRewardService rewardService;
    @Autowired
    private IPosCheckService checkService;
    @Autowired
    private IPTaxAdjustService taxAdjustService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentColinfoService agentColinfoService;
    @Autowired
    private BusinessPlatformService businessPlatformService;

    private static final String AGENT = "agent";
    private static final String FINANCE ="finance";

    /**
     * 校验代理商原税点
     * discount/queryPoint
     * @param agentId
     * @return
     */
    @RequestMapping("queryPoint")
    @ResponseBody
    public Object queryPoint(String agentId) {
        AgentColinfo verAgent = new AgentColinfo();
        verAgent.setAgentId(agentId);
        AgentColinfo colinfo = agentColinfoService.queryPoint(verAgent);//检索代理商信息，校验税点
        if (colinfo != null) {
            return renderSuccess(JsonUtil.objectToJson(colinfo));
        } else {
            return renderError("税点校验失败！");
        }
    }

    /**
     * 优惠政策申请------------------------税点调整
     * @param request
     * @param pTaxAdjust
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "posTaxList")
    public Object posTaxList(HttpServletRequest request, PTaxAdjust pTaxAdjust){
        Page page = pageProcess(request);
        List<Map<String, Object>>  list = iUserService.orgCode( getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String,Object> map = list.get(0);
        if(Objects.equals(map.get("ORGANIZATIONCODE"),FINANCE)){
            map = null;
        }else if(Objects.equals(map.get("ORGANIZATIONCODE"),AGENT)){
            map = null;
            pTaxAdjust.setAgentId(getAgentId());
        }
        PageInfo info = taxAdjustService.PTaxAdjustList(pTaxAdjust, page,map);
        return info;
    }

    @RequestMapping(value = {"posTax"})
    public String posTax(Model model){
        LOG.info("加载税点调整页面...");
        // 终审后不能做修改
        String finalStatus = ServiceFactory.redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            model.addAttribute("noEdit","0");
        }else{
            model.addAttribute("noEdit","1");
        }
        return "discount/posTax";
    }

    /**
     * 税点调整申请表单
     * agentEnter/agentForm
     * @param request
     * @return
     */
    @RequestMapping(value = {"posTaxForm", "form"})
    public String agentNetInFormView(HttpServletRequest request) {
        optionsData(request);
        //当前登录用户所属省区
        List<Map<String, Object>> userOrg = iUserService.orgCode(getUserId());
        if (userOrg.size() > 0) {
            request.setAttribute("userOrg", userOrg.get(0));
        }
        return "discount/posTaxInForm";
    }

    /**
     * 税点调整申请
     * agentEnter/agentEnterIn
     * @return
     */
    @ResponseBody
    @RequestMapping(value = {"posTaxEnterIn"}, method = RequestMethod.POST)
    public ResultVO agentEnterIn(HttpServletRequest request, @RequestParam("agNam")String agNam, @RequestParam("agNum")String agNum,
                                 @RequestParam("old")String old, @RequestParam("ing")String ing, @RequestParam("month")String month) {
        List<Map<String, Object>>  list = iUserService.orgCode(getUserId());
        if(list.size() >0){
            if(list.get(0).get("ORGANIZATIONCODE").toString().contains("north") || list.get(0).get("ORGANIZATIONCODE").toString().contains("south") ){
                Map<String,String> map = new HashMap<String,String>();
                map.put("id",agNum);
                map.put("orgId",list.get(0).get("ORGID").toString());
                List<Agent> list1 = agentService.getListByORGAndId(map);
                if(list1.size()<=0){
                    return ResultVO.fail("请对本省区内代理商进行申请调整!");
                }
            }
        }

        PTaxAdjust pTaxAdjust = taxAdjustService.selectByAgentId(agNum);
        if(null != pTaxAdjust){
            return ResultVO.fail("请勿重复申请!");
        }
        pTaxAdjust = new PTaxAdjust();
        try {
            pTaxAdjust.setUserId(getUserId() + "");
            pTaxAdjust.setTaxStatus(RewardStatus.REVIEWING.getStatus());
            pTaxAdjust.setAgentId(agNum);
            pTaxAdjust.setAgentName(agNam);
            pTaxAdjust.setTaxOld(new BigDecimal(old));
//            double newIng = Double.parseDouble(ing.replace("%","")) * 0.01;//百分制转换
            pTaxAdjust.setTaxIng(new BigDecimal(ing));
            pTaxAdjust.setProfitMonth(month);//分润执行月
            ResultVO res = taxAdjustService.posTaxEnterIn(pTaxAdjust);
            return res;
        } catch (ProcessException e) {
            return ResultVO.fail(e.getMessage());
        }
    }

    @RequestMapping("/editPosTaxPage")
    public ModelAndView getPosTaxData(String id){
        if(StringUtils.isNotBlank(id)){
            PTaxAdjust pTaxAdjust = taxAdjustService.getPosTaxById(id);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("discount/posTaxInfo");
            modelAndView.addObject("taxAdjust" ,pTaxAdjust);
            return modelAndView;
        }
        return null;
    }

    /**
     * 驳回修改--税点调整
     * 审批修改申请信息
     */
    @RequestMapping("/editPosTaxRegect")
    @ResponseBody
    public Object editPosTaxRegect(PTaxAdjust pTaxAdjust){
        if(pTaxAdjust == null ){
            return renderError("系统异常，请联系维护人员！");
        }
        try {
            taxAdjustService.editPosTaxRegect(pTaxAdjust);
            return renderSuccess("税点调整申请信息修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }


    /**
     * 优惠政策申请------------------------POS特殊奖励
     * @param request
     * @return
     */
    @RequestMapping(value = {"posReward"})
    public String posReward(HttpServletRequest request){
        LOG.info("加载POS特殊奖励页面...");
        List<Platform> platformList=new ArrayList<>();
        platformList.add(Platform.POS);
        platformList.add(Platform.ZPOS);
        platformList.add(Platform.REF);
        platformList.add(Platform.RYF);
        platformList.add(Platform.RXS);
        platformList.add(Platform.RF);
        platformList.add(Platform.RYT);
        platformList.add(Platform.RLS);
        platformList.add(Platform.RYS);
        platformList.add(Platform.RET);
        request.setAttribute("platformList",platformList);
        return "discount/posReward";
    }

    @ResponseBody
    @RequestMapping(value = "posRewardList")
    public Object posRewardList(HttpServletRequest request, PosReward posReward){
        Page page = pageProcess(request);
        if (getAgentId()!=null){
            posReward.setAgentId(getAgentId());
        }
        PageInfo pageInfo = rewardService.posRewardList(posReward, page);
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "posHuddleRewardList")
    public Object posHuddleRewardList(HttpServletRequest request, PosReward posReward){
        Page page = pageProcess(request);
        posReward.setAgentId(getAgentId());
        PosHuddleRewardDetail posHuddleRewardDetail= new PosHuddleRewardDetail();
        if (posReward.getAgentName()!=null&&!"".equals(posReward.getAgentName())){
            posHuddleRewardDetail.setPosAgentName(posReward.getAgentName());
        }
        if (posReward.getAgentId()!=null&&!"".equals(posReward.getAgentId())){
            posHuddleRewardDetail.setPosAgentId(posReward.getAgentId());
        }

        PageInfo pageInfo = rewardService.posHuddleRewardList(posHuddleRewardDetail, page);
        return pageInfo;
    }

    /**
     * 优惠政策申请：
     * 1、POS奖励考核申请------申请审批
     * @return
     */
    @RequestMapping("/addPage")
    public ModelAndView getPosReward(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        AgentResult result = agentService.isAgent(getUserId()+"");
        request.setAttribute("isagent", result);
        modelAndView.setViewName("discount/posRewardAdd");
        return modelAndView;
    }

    @RequestMapping("/addHuddleDetail")
    public ModelAndView addHuddleDetail(String huddleCode,HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        request.setAttribute("huddleCode", huddleCode);
        modelAndView.setViewName("discount/posHuddleDetail");
        return modelAndView;
    }

    @RequestMapping("/posHuddleDetailList")
    @ResponseBody
    public List<PosHuddleRewardDetail> posHuddleDetailList(String huddleCode){
        List<PosHuddleRewardDetail> posHuddleRewardDetails =  rewardService.selectByHuddleCode(huddleCode);

        return posHuddleRewardDetails;

    }


    /**
     * 优惠政策申请：
     * 1、POS奖励抱团考核申请------申请审批
     * @return
     */
    @RequestMapping("/addHuddlePage")
    public ModelAndView getHuddlePosReward(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        AgentResult result = agentService.isAgent(getUserId()+"");
        request.setAttribute("isagent", result);
        modelAndView.setViewName("discount/posHuddleRewardAdd");
        return modelAndView;
    }

    /**
     * 创建审批流--POS奖励考核
     * @return
     */
    @RequestMapping("/addReward")
    @ResponseBody
    public Object addPosReward(PosReward posReward,HttpServletRequest request) {
        posReward.setAppraisalCycle(posReward.getTotalConsMonth()+"～"+posReward.getCreditConsMonth());
        posReward.setAgentPid(posReward.getAgentId());
        String type=request.getParameter("type");
        String totalConsMonth=request.getParameter("totalConsMonth");
        String creditConsMonth=request.getParameter("creditConsMonth");
        String[] assess_month=request.getParameterValues("assess_month");
        String no_assess=request.getParameter("no_assess");
        if ("huddle".equals(type)) {
            TreeMap treeMap = getRequestParameter(request);
            List listIs = new ArrayList();
            String huddleCode = UUID.randomUUID().toString().replace("-", "").toLowerCase();

            List<PosHuddleRewardDetail> list = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                String AGname = (String) treeMap.get("agentName" + i);
                String AGID = (String) treeMap.get("agentId" + i);
                if ("".equals(AGname) || AGname == null) {
                    break;
                }
                PosHuddleRewardDetail posHuddleRewardDetail = new PosHuddleRewardDetail();
                posHuddleRewardDetail.setHuddleCode(huddleCode);
                posHuddleRewardDetail.setPosAgentId(AGID);
                posHuddleRewardDetail.setPosAgentName(AGname);
                list.add(posHuddleRewardDetail);
                /* rewardService.insertSelective(posHuddleRewardDetail);*/
                AGname = AGname.substring(0, 4);
                listIs.add(AGname);
            }
            Set set = new HashSet(listIs);
            if(set.size()!=listIs.size()){
                return renderError("代理商重复申请");
            }
            if (set.size() < 2) {
                return renderError("请最少选择两家代理商");
            }

            PosReward posRewardHuddle = new PosReward();
            List<PosReward> rewar = null;
            for (PosHuddleRewardDetail pos : list) {
                posReward.setAgentId(pos.getPosAgentId());
                posReward.setAgentName(pos.getPosAgentName());
                /*rewar = rewardService.selectRewardByMonth(posReward);
                if (null != rewar && !rewar.isEmpty()) {
                    return renderError(pos.getPosAgentName()+"此预发周期pos单个奖励申请中，不能重复申请!");
                }*/
                if(!rewardService.isRepetition(posReward)){
                    return renderError("此预发周期申请中，不能重复申请!");
                }

            }
            Map<String,Object> mapHuddle = new HashMap<>();
            for (PosHuddleRewardDetail pos : list) {
                mapHuddle.put("POS_AGENT_ID",pos.getPosAgentId());
                mapHuddle.put("POS_AGENT_NAME",pos.getPosAgentName());
                List<String> list1=  DateKit.getMonthBetween(totalConsMonth,creditConsMonth);
                List<Map<String, Object>>  list2 = rewardService.huddlePos(mapHuddle);
                for (Map<String, Object> map:list2) {
                    List<String> list3 = DateKit.getMonthBetween((String) map.get("TOTAL_CONS_MONTH"), (String) map.get("CREDIT_CONS_MONTH"));
                    for (String month1:list1) {
                        for (String month2:list3) {
                            if(month1 .equals(month2) ){
                                return renderError(map.get("POS_AGENT_NAME")+"此预发周期pos奖励抱团申请奖励中，不能重复申请!");
                            }
                        }
                    }
                }

            }
                PPosHuddleReward pPosHuddleReward = new PPosHuddleReward();
                pPosHuddleReward.setAgentName(set.toString());

                pPosHuddleReward.setCreateTm(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                //设置默认申请状态为‘申请中’
                pPosHuddleReward.setApplyStatus("0");
                pPosHuddleReward.setHuddleCode(huddleCode);
                //设置审核月
                String temp = "";
                if (assess_month != null) {
                    for (int i = 0; i < assess_month.length; i++) {
                        temp += assess_month[i];
                        if (i < assess_month.length - 1) {
                            temp += ",";
                        }
                    }
                    pPosHuddleReward.setTotalEndMonth(temp);
                } else if (no_assess == null) {
                    return renderError("请选择考核月份！");
                } else {
                    pPosHuddleReward.setTotalEndMonth(null);
                }
                //判断起始与结束月份是否是相同
                if (totalConsMonth.equals(creditConsMonth)) {
                    return renderError("结束月份不能和起始月份一致！");
                }

            try {
                Set<String> roles = getShiroUser().getRoles();
                String workId = null;
                if (roles != null && roles.contains("代理商")) {
                    workId = "posRewardAgent";
                } else {
                    workId = "posRewardCity";
                }
                pPosHuddleReward.setTotalConsMonth(totalConsMonth);//预发起始月
                pPosHuddleReward.setCreditConsMonth(creditConsMonth);//预发结束月
                pPosHuddleReward.setGrowAmt((String) treeMap.get("growAmt"));
                pPosHuddleReward.setMachineryNum((String) treeMap.get("machineryNum"));
                pPosHuddleReward.setRewardScale(new BigDecimal(treeMap.get("rewardScale").toString()) );
                rewardService.applyHuddlePosReward(pPosHuddleReward, valueOf(getUserId()), workId);
                for (PosHuddleRewardDetail pos : list) {
                    rewardService.insertHuddleDetail(pos);
                }
                return renderSuccess("POS抱团奖励考核申请成功！");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return renderError("系统异常，请联系维护人员！");
        }
            //单个的POS奖励从这里开始
            //设置创建时间
            posReward.setCreateTm(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            //设置默认申请状态为‘申请中’
            posReward.setApplyStatus("0");

            if(!rewardService.isRepetition(posReward)){
                return renderError("此预发周期申请中，不能重复申请!");
            }
            Map<String,Object> mapHuddle = new HashMap<>();
           mapHuddle.put("POS_AGENT_ID",posReward.getAgentId());
            mapHuddle.put("POS_AGENT_NAME",posReward.getAgentName());
            List<String> list1=  DateKit.getMonthBetween(totalConsMonth,creditConsMonth);
            List<Map<String, Object>>  list2 = rewardService.huddlePos(mapHuddle);
            for (Map<String, Object> map:list2) {
                List<String> list3 = DateKit.getMonthBetween((String) map.get("TOTAL_CONS_MONTH"), (String) map.get("CREDIT_CONS_MONTH"));
                for (String month1:list1) {
                    for (String month2:list3) {
                        if(month1.equals(month2)){
                            return renderError(map.get("POS_AGENT_NAME")+"此预发周期pos奖励抱团申请奖励中，不能重复申请!");
                        }
                    }
                }
        }
        try {
            Set<String> roles = getShiroUser().getRoles();
            String workId = null;
            if (roles != null && roles.contains("代理商")) {
                workId = "posRewardAgent";
            } else {
                workId = "posRewardCity";
            }
            rewardService.applyPosReward(posReward, valueOf(getUserId()), workId);
            return renderSuccess("奖励考核申请成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }
    @RequestMapping("/editPage")
    public ModelAndView getData(String id){
        if(StringUtils.isNotBlank(id)){
            PosReward posReward = rewardService.getPosRewardById(id);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("discount/posRewardInfo");
            modelAndView.addObject("posReward" ,posReward);
            return modelAndView;
        }
        return null;
    }

    /**
     * 驳回修改--POS奖励考核
     * 审批修改申请信息
     */
    @RequestMapping("/editRewardRegect")
    @ResponseBody
    public Object editRewardRegect(PosReward posReward, HttpServletRequest request){
        String totalCons = request.getParameter("totalConsMonth");
        String creditCons = request.getParameter("creditConsMonth");
        String totalEnd = request.getParameter("assess_month");
        //判断起始与结束月份是否是相同
        if (totalCons.equals(creditCons)) {
            return renderError("结束月份不能和起始月份一致！");
        }
        //判断考核月份是否在区间内
        long startMonth = DateUtil.format(totalCons, "yyyy-MM").getTime();
        long endMonth = DateUtil.format(creditCons, "yyyy-MM").getTime();
        long month = DateUtil.format(totalEnd, "yyyy-MM").getTime();
        if (month >= startMonth && month <= endMonth) {
            System.out.println("考核月份在区间内！");
        } else {
            return renderError("考核月份不在所选的预发周期内！");
        }
        try {
            posReward.setTotalConsMonth(totalCons);//预发起始月
            posReward.setCreditConsMonth(creditCons);//预发结束月
            posReward.setTotalEndMonth(totalEnd);//考核月份
            rewardService.editRewardRegect(posReward);
            return renderSuccess("POS奖励信息修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }

    /**
     * 驳回修改--POS抱团奖励考核
     * 审批修改申请信息
     */
    @RequestMapping("/editHuddleRewardRegect")
    @ResponseBody
    public Object editHuddleRewardRegect(PPosHuddleReward posReward, HttpServletRequest request){
        String totalCons = request.getParameter("totalConsMonth");
        String creditCons = request.getParameter("creditConsMonth");
        String totalEnd = request.getParameter("assess_month");
        //判断起始与结束月份是否是相同
        if (totalCons.equals(creditCons)) {
            return renderError("结束月份不能和起始月份一致！");
        }
        //判断考核月份是否在区间内
        long startMonth = DateUtil.format(totalCons, "yyyy-MM").getTime();
        long endMonth = DateUtil.format(creditCons, "yyyy-MM").getTime();
        long month = DateUtil.format(totalEnd, "yyyy-MM").getTime();
        if (month >= startMonth && month <= endMonth) {
            System.out.println("考核月份在区间内！");
        } else {
            return renderError("考核月份不在所选的预发周期内！");
        }
        try {
            posReward.setTotalConsMonth(totalCons);//预发起始月
            posReward.setCreditConsMonth(creditCons);//预发结束月
            posReward.setTotalEndMonth(totalEnd);//考核月份
            rewardService.editHuddleRewardRegect(posReward);
            return renderSuccess("POS抱团奖励信息修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }
    /**
     * 优惠政策申请------------------------分润比例考核
     * @param request
     * @return
     */
    @RequestMapping(value = {"posCheck"})
    public String posCheck(HttpServletRequest request){
        LOG.info("加载分润比例考核页面...");
        return "discount/posCheck";
    }

    /**
     *   分润比例考核数据查询
     */
    @ResponseBody
    @RequestMapping(value = "posCheckList")
    public Object posCheckList(HttpServletRequest request, PosCheck posCheck){
        Page page = pageProcess(request);
        List<Map<String, Object>>  list = iUserService.orgCode( getUserId());
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String,Object> map = list.get(0);
        if(Objects.equals(map.get("ORGANIZATIONCODE"),AGENT)){
            map = null;
            posCheck.setAgentId(getAgentId());
        }else if(Objects.equals(map.get("ORGANIZATIONCODE"),FINANCE)){
            map = null;
        }
        return checkService.posCheckList(map, posCheck, page);
    }

    /**
     * 分润比例考核申请------申请页面
     * @return
     */
    @RequestMapping("/addCheckPage")
    public ModelAndView getPosCheck(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        AgentResult result = agentService.isAgent(getUserId()+"");
        request.setAttribute("isagent", result);
        modelAndView.setViewName("discount/posCheck/posCheckAdd");
        return modelAndView;
    }


    /**
     * 校验 代理商名称，AG，，业务平台，业务平台编码 获取代理商的 平台信息
     */
    @RequestMapping("queryAgentInfo")
    @ResponseBody
    public ResultVO queryAgentName(HttpServletRequest request) {
        Map<String,String> map = getRequestParameter(request);
        List<Map<String,Object>> resultList = checkService.queryAgentBusPlayformInfo(map);
        if(resultList == null || resultList.size() == 0){
            return ResultVO.fail("未查询到数据信息");
        }else{
            return ResultVO.success(resultList);
        }
    }

    /**
     * 创建审批流--分润比例考核
     */
    @RequestMapping("/addCheck")
    @ResponseBody
    public Object addPosCheck(PosCheck posCheck,HttpServletRequest request){
        //判断申请的代理商是否属于该省区
        List<Map<String, Object>>  list = iUserService.orgCode(getUserId());
        if(list.size() >0){
            if(list.get(0).get("ORGANIZATIONCODE").toString().contains("north") || list.get(0).get("ORGANIZATIONCODE").toString().contains("south") ){
                Map<String,String> map = new HashMap<String,String>();
                map.put("id",posCheck.getAgentId());
                map.put("orgId",list.get(0).get("ORGID").toString());
                List<Agent> list1 = agentService.getListByORGAndId(map);
                if(list1.size()<=0){
                    return renderError("所申请代理商不属于该省区");
                }
            }
            if(Objects.equals(list.get(0).get("ORGANIZATIONCODE"), AGENT)){
                if(!getAgentId().equals(posCheck.getAgentId())){
                    return renderError("代理商只能申请自己的分润比例!");
                }
            }
        }
        Map<String,String> map = getRequestParameter(request);
        String checkDate = request.getParameter("checkboxDate");
        if("1".equals(checkDate)){ //判断是否是长期
            posCheck.setCheckDateE("1");
            posCheck.setCheckDateS(DateUtil.sdf_Days.format(new Date()).substring(0,7));  //  开始日期为自然月
        //    posCheck.setCheckDateS(DateUtil.sdf_Days.format(DateUtil.addMonth(new Date(), -1)).substring(0, 7)); //开始日期为分润月
        }else if (posCheck.getCheckDateS().equals(posCheck.getCheckDateE())) {//判断起始与结束日期是否相同
            return renderError("结束日期不能和起始日期一致！");
        }
        //判断分润比例格式
        BigDecimal scale = new BigDecimal(String.valueOf(posCheck.getProfitScale()));
        if(scale.compareTo(new BigDecimal(0.1)) < 0 || scale.compareTo(new BigDecimal(1)) > 0){
            return renderError("分润比例不能小于0.1且不能大于1！");
        }
        try {
            Set<String> roles = getShiroUser().getRoles();
            String workId = null;
            if(roles !=null && roles.contains("代理商")) {
                workId = "posCheck_Agent";
            }else {
                workId = "posCheck_City";
            }
            checkService.applyPosCheck(posCheck, valueOf(getUserId()), workId);
            return renderSuccess("分润比例考核申请成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }

    @RequestMapping("/editCheckPage")
    public ModelAndView getCheckData(String id){
        if(StringUtils.isNotBlank(id)){
            PosCheck posCheck = checkService.getPosCheckById(id);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("discount/posCheck/posCheckInfo");
            modelAndView.addObject("posCheck", posCheck);
            return modelAndView;
        }
        return null;
    }

    /**
     * 驳回修改--分润比例考核
     * 审批修改申请信息
     */
    @RequestMapping("/editCheckRegect")
    @ResponseBody
    public Object editCheckRegect(PosCheck posCheck, HttpServletRequest request){
        String start = request.getParameter("checkDateS");
        String end = request.getParameter("checkDateE");
        String profitScale = request.getParameter("profitScale");
        //判断起始与结束日期是否相同
        if (start.equals(end)) {
            return renderError("结束日期不能和起始日期一致！");
        }
        //判断分润比例格式
        BigDecimal scale = new BigDecimal(String.valueOf(profitScale));
        if(scale.compareTo(new BigDecimal(0.1)) < 0 || scale.compareTo(new BigDecimal(1)) > 0){
            return renderError("分润比例不能小于0.1且不能大于1！");
        }
        try {
            posCheck.setCheckDateS(start); //考核起始日期
            posCheck.setCheckDateE(end);  //考核截止日期
            checkService.editCheckRegect(posCheck);
            return renderSuccess("分润比例信息修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }

    /**
     * 分润比例考核：导出数据
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "exportPosCheck")
    public void exportProfitD(PosCheck posCheck, HttpServletResponse response, HttpServletRequest request) throws Exception {
        TreeMap map = getRequestParameter(request);
        Page page = pageProcess(request);
        posCheck.setAgentId(getAgentId());
        List<PosCheck> list = checkService.exportPosCheck(posCheck);
        list.stream().forEach(aaa -> {aaa.setCheckStatus(aaa.getCheckStatus().equals("0")?"申请中":(aaa.getCheckStatus().equals("1")?"生效":(aaa.getCheckStatus().equals("2")?"无效":(aaa.getCheckStatus().equals("9")?"新建":"null"))));});
        String filePath = "C:/upload/";
        String filePrefix = "PC";
        int flushRows = 100;

        List<String> fieldNames = new ArrayList<String>();
        List<String> fieldCodes = new ArrayList<String>();
        fieldNames.add("代理商编码");
        fieldCodes.add("agentId");
        fieldNames.add("代理商名称");
        fieldCodes.add("agentName");
        fieldNames.add("考核起始日期");
        fieldCodes.add("checkDateS");
        fieldNames.add("考核截止日期");
        fieldCodes.add("checkDateE");
        fieldNames.add("交易总额（万）");
        fieldCodes.add("totalAmt");
        fieldNames.add("机具订货量");
        fieldCodes.add("posOrders");
        fieldNames.add("分润比例");
        fieldCodes.add("profitScale");
        fieldNames.add("申请日期");
        fieldCodes.add("appDate");
        fieldNames.add("考核状态");
        fieldCodes.add("checkStatus");

        ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
        excelExportSXXSSF.writeDatasByObject(list);
        String filename = filePrefix + "_" + MyUtil.getCurrentTimeStr() + ".xls";
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        excelExportSXXSSF.getWb().write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

}
