package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.enumc.PDataAdjustType;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.TaxDeductionDetail;
import com.ryx.credit.profit.service.ITaxDeductionDetailService;
import com.ryx.credit.service.IUserService;
import com.ryx.credit.service.agent.AgentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author renshenghao
 * @Date 2018/12/20
 * @desc 扣税明细
 */
@Controller
@RequestMapping("/deductTaxDetail")
public class DeductTaxDetailController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(DeductTaxDetailController.class);

    @Autowired
    private ITaxDeductionDetailService taxDeductionDetailService;
    @Autowired
    private IUserService userService;

    private static final String FINANCE ="finance";
    private static final String AGENT = "agent";

    @RequestMapping(value = "/toPostRewardDetailList")
    public String deductTaxDetailList(){
        return "profit/deductTax/deductTaxDetailList";
    }

    /**
     * 获取直签平台数据
     * @param request
     * @param taxDeductionDetail
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/query")
    public Object posDeductTaxList(HttpServletRequest request, TaxDeductionDetail taxDeductionDetail){
        Object isQueryXIAJI=request.getParameter("isQuerySubordinate");
        String dateStart = request.getParameter("DATESTART");//获取查询开始日期
        String dateEnd = request.getParameter("DATEEND");//获取查询结束日期
        Page page=pageProcess(request);
        taxDeductionDetail.setBusPlatform("6000");
        /*if ((dateStart==null||dateStart.equals(""))){
            if(taxDeductionDetail.getAgentId()==null||taxDeductionDetail.getAgentId().equals("")){
                if(taxDeductionDetail.getAgentName()==null||taxDeductionDetail.getAgentName().equals("")){
                    if(getAgentId()==null){
                        Calendar c=Calendar.getInstance();
                        c.setTime(new Date());
                        c.add(Calendar.MONTH,-1);
                        dateStart = new SimpleDateFormat("yyyyMM").format(c.getTime());
                        dateEnd = new SimpleDateFormat("yyyyMM").format(c.getTime());
                    }
                }
            }
        }*/
        //代理商只能查看自己信息，财务不做限制，省区查看本省区下代理商信息
        List<Map<String, Object>> list = userService.orgCode(getUserId());
        Map<String,String> map = new HashMap<String,String>();
        if(list == null || list.isEmpty()){
            return new PageInfo();
        }
        Map<String, Object> mapList = list.get(0);
        if(mapList.get("ORGANIZATIONCODE").toString().contains("south") || mapList.get("ORGANIZATIONCODE").toString().contains("north")){
            map.put("orgId",mapList.get("ORGID").toString());  // 省区
        }else if(Objects.equals("south", mapList.get("ORGANIZATIONCODE").toString() )|| Objects.equals("north", mapList.get("ORGANIZATIONCODE").toString())){
            map.put("area",mapList.get("ORGID").toString());   // 大区
        }else if(Objects.equals(mapList.get("ORGANIZATIONCODE"), AGENT)){  // 代理商
            map = null;
            taxDeductionDetail.setAgentId(getAgentId());
        }else {  //其他账号
            map = null;
        }

        if (isQueryXIAJI!=null){
            if("".equals(dateEnd)){
                if(dateStart!=null&&!"".equals(dateStart)){
                    taxDeductionDetail.setProfitMonth(dateStart);
                }else{
                    Calendar c=Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.MONTH,-1);
                    taxDeductionDetail.setProfitMonth(new SimpleDateFormat("yyyyMM").format(c.getTime()));
                }
            }else{
                if(dateEnd.equals(dateStart)){
                    taxDeductionDetail.setProfitMonth(dateStart);
                }else{
                    taxDeductionDetail.setProfitMonth("error");
                }
            }
            return queryAndSubordinate(map,page,taxDeductionDetail);
        }
        PageInfo pageInfo=taxDeductionDetailService.posDeductTaxList(map,taxDeductionDetail,page,dateStart,dateEnd);
        return pageInfo;
    }

    /**
     * 直发平台
     */
    @ResponseBody
    @RequestMapping(value="/queryDirectly")
    public Object posDirectlyDeductTaxList(HttpServletRequest request, TaxDeductionDetail taxDeductionDetail){
        Page page=pageProcess(request);
        String dateStart = request.getParameter("DATESTART");//获取查询开始日期
        String dateEnd = request.getParameter("DATEEND");//获取查询结束日期
        taxDeductionDetail.setBusPlatform("6000");
        /*if ((dateStart==null||dateStart.equals(""))){
            if(taxDeductionDetail.getAgentId()==null||taxDeductionDetail.getAgentId().equals("")){
                if(taxDeductionDetail.getAgentName()==null||taxDeductionDetail.getAgentName().equals("")){
                    if(getAgentId()==null){
                        Calendar c=Calendar.getInstance();
                        c.setTime(new Date());
                        c.add(Calendar.MONTH,-1);
                        dateStart = new SimpleDateFormat("yyyyMM").format(c.getTime());
                        dateEnd = new SimpleDateFormat("yyyyMM").format(c.getTime());
                    }
                }
            }
        }*/
        PageInfo pageInfo=taxDeductionDetailService.posDirectlyDeductTaxList(taxDeductionDetail,page,dateStart,dateEnd);
        return pageInfo;
    }

    /**
     * 查询本级数据以及下级数据
     * @param page
     * @param taxDeductionDetail
     * @return
     */
    public Object queryAndSubordinate(Map<String,String> map,Page page,TaxDeductionDetail taxDeductionDetail){
        if(taxDeductionDetail==null){
            return null;
        }
        PageInfo pageInfo=taxDeductionDetailService.queryAndSubordinate(map,taxDeductionDetail,page);
        return pageInfo;
    }

    @RequestMapping(value = "/adjust")
    public ModelAndView adjust(String id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("id",id);
        modelAndView.setViewName("profit/deductTax/adjust");
        return modelAndView;
    }
    @RequestMapping(value = "/doAdjust")
    @ResponseBody
    public Object doAdjust(TaxDeductionDetail taxDeductionDetail){
        TaxDeductionDetail oldTDD=taxDeductionDetailService.selectById(taxDeductionDetail.getId());
        if(oldTDD==null){
            return renderError("ID获取失败，系统异常。");
        }
        nullTOZero(oldTDD);
        if(taxDeductionDetail.getAdjustAmt()==null){
            return renderError("请输入调整金额！");
        }else if(taxDeductionDetail.getAdjustReson()==null||taxDeductionDetail.getAdjustReson().equals("")){
            return renderError("请输入调整原因！");
        }else{
            //修改本月扣税基数
            if(oldTDD.getAdjustAmt()!=null){
                oldTDD.setTaxBase(oldTDD.getTaxBase().subtract(oldTDD.getAdjustAmt()).add(taxDeductionDetail.getAdjustAmt()));
            }else{
                oldTDD.setTaxBase(oldTDD.getTaxBase().add(taxDeductionDetail.getAdjustAmt()));
            }
            //修改本月调整金额
            oldTDD.setAdjustAmt(oldTDD.getAdjustAmt().add(taxDeductionDetail.getAdjustAmt()));
            //修改本月新增扣税
            oldTDD.setAddTaxAmt(oldTDD.getTaxBase().multiply(oldTDD.getTaxRate()));
            //修改本月应扣税额
            oldTDD.setSupposedTaxAmt(oldTDD.getPreNotDeductionAmt1().add(oldTDD.getAddTaxAmt().add(oldTDD.getAdjustAmt())));
            //修改本月实扣税额
            if(oldTDD.getBasicProfitAmt().compareTo(oldTDD.getSupposedTaxAmt())>=0){
                oldTDD.setRealTaxAmt(oldTDD.getSupposedTaxAmt());
                oldTDD.setNotDeductionTaxAmt(BigDecimal.ZERO);
            }else{
                oldTDD.setRealTaxAmt(oldTDD.getBasicProfitAmt());
                oldTDD.setNotDeductionTaxAmt(oldTDD.getSupposedTaxAmt().subtract(oldTDD.getBasicProfitAmt()));
            }
            //修改调整原因
            oldTDD.setAdjustReson(taxDeductionDetail.getAdjustReson());
            //修改调整时间
            oldTDD.setAdjustTime(new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));
            //设置调整人
            oldTDD.setAdjustAccount(getShiroUser().getName());
            //修改更新时间
            oldTDD.setUpdateTime(new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));
        }
        System.out.println(oldTDD.getAgentId());
        try {
            taxDeductionDetailService.updateAdjust(oldTDD,taxDeductionDetail);
            return renderSuccess("调整成功");
        }catch (Exception e){
            return renderError(e.getMessage());
        }

    }

    @RequestMapping("/examineAdjustDetail")
    public ModelAndView examineAdjustDetail(HttpServletRequest request){
        ModelAndView view=new ModelAndView();
        Map<String,Object> map=new HashMap<>();
        map.put("agentId",request.getParameter("agentId"));
        map.put("parentAgentId",request.getParameter("parentAgentId"));
        map.put("profitMonth",request.getParameter("profitMonth"));
        view.addObject("param",map);
        view.addObject("adjustType",PDataAdjustType.KS.adjustType);
        view.setViewName("common/adjustDetail");
        return view;
    }

    @RequestMapping("adjustDetailList")
    @ResponseBody
    public Object adjustDetailList(HttpServletRequest request){
        PageInfo pageInfo=new PageInfo();
        Map<String,Object>param=new HashMap<>();
        param.put("agentId",request.getParameter("agentId"));
        param.put("parentAgentId",request.getParameter("parentAgentId"));
        param.put("profitMonth",request.getParameter("profitMonth"));
        pageInfo=taxDeductionDetailService.adjustDetailList(param, PDataAdjustType.KS.adjustType,pageInfo);
        pageInfo.setTotal(pageInfo.getRows().size());
        return pageInfo;
    }

    @RequestMapping(value = "profitCount")
    public ModelAndView profitCount(HttpServletRequest request) {
        String agentName=request.getParameter("agentName");
        try {
            if (agentName.equals(new String(agentName.getBytes("iso8859-1"),"iso8859-1"))){
                agentName=new String(agentName.getBytes("iso8859-1"),"utf-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ModelAndView view = new ModelAndView();
        Map<String, Object> param ;
        param = getRequestParameter(request);
        param.put("agentId", request.getParameter("agentId"));
        param.put("agentName", agentName);
        String dateStart = request.getParameter("DATESTART");//获取查询开始日期
        String dateEnd = request.getParameter("DATEEND");//获取查询结束日期
        param.put("DATESTART",dateStart);
        param.put("DATEEND",dateEnd);
        logger.info(param.toString());
        if("".equals(dateStart)){
            if(request.getParameter("agentId")!=null&&request.getParameter("agentId").equals("")&&request.getParameter("agentName")!=null&&request.getParameter("agentName").equals("")){
                Calendar c=Calendar.getInstance();
                c.setTime(new Date());
                c.add(Calendar.MONTH,-1);
                param.put("DATESTART",new SimpleDateFormat("yyyyMM").format(c.getTime()));
            }
        }
        param.put("directly",request.getParameter("directly").equals("directly")?true:false);
        param.put("busPlatform", "6000");
        String isQuerySubordinate = request.getParameter("isQuerySubordinate");
        Map<String,Object> map=null;
        if(isQuerySubordinate!=null&&isQuerySubordinate.equals("isQuerySubordinate")){
            //统计下级
            if("".equals(dateEnd)){
                if(dateStart!=null&&!"".equals(dateStart)){
                    param.put("profitMonth",dateStart);
                }else{
                    Calendar c=Calendar.getInstance();
                    c.setTime(new Date());
                    c.add(Calendar.MONTH,-1);
                    param.put("profitMonth",new SimpleDateFormat("yyyyMM").format(c.getTime()));
                }
            }else{
                if(dateEnd.equals(dateStart)){
                    param.put("profitMonth",dateStart);
                }else{
                    param.put("profitMonth","error");
                }
            }
            map=taxDeductionDetailService.profitCount(param,true);
        }else {
            map=taxDeductionDetailService.profitCount(param,false);
        }
        view.addObject("totalNum",map.get("TOTALNUM"));
        view.addObject("totalMoney",map.get("TOTALMONEY")==null?0:map.get("TOTALMONEY"));
        view.setViewName("profit/profitCount");
        return view;
    }

    private void nullTOZero(TaxDeductionDetail taxDeductionDetail){
        if(taxDeductionDetail==null){
            return;
        }
        if(taxDeductionDetail.getTaxBase()==null){
            taxDeductionDetail.setTaxBase(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getBasicProfitAmt()==null){
            taxDeductionDetail.setBasicProfitAmt(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getAdjustAmt()==null){
            taxDeductionDetail.setAdjustAmt(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getSupposedTaxAmt()==null){
            taxDeductionDetail.setSupposedTaxAmt(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getAddTaxAmt()==null){
            taxDeductionDetail.setAddTaxAmt(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getPreNotDeductionAmt1()==null){
            taxDeductionDetail.setPreNotDeductionAmt1(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getTaxRate()==null){
            taxDeductionDetail.setTaxRate(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getAgentDfAmt()==null){
            taxDeductionDetail.setAgentDfAmt(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getBlAmt()==null){
            taxDeductionDetail.setBlAmt(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getDayBackAmt()==null){
            taxDeductionDetail.setDayBackAmt(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getDayProfitAmt()==null){
            taxDeductionDetail.setDayProfitAmt(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getMerchanOrderAmt()==null){
            taxDeductionDetail.setMerchanOrderAmt(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getNotDeductionTaxAmt()==null){
            taxDeductionDetail.setNotDeductionTaxAmt(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getPreLdAmt()==null){
            taxDeductionDetail.setPreLdAmt(BigDecimal.ZERO);
        }
        if(taxDeductionDetail.getRealTaxAmt()==null){
            taxDeductionDetail.setRealTaxAmt(BigDecimal.ZERO);
        }
    }
}
