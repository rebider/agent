package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.pojo.PosRewardTemplate;
import com.ryx.credit.profit.service.PosRewardTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 〈pos奖励模板〉
 *
 * @author zhaodw
 * @create 2018/7/24
 * @since 1.0.0
 */
@Controller
@RequestMapping("/posRewardTemp")
public class PosTemplateController extends BaseController {

    Logger LOG = LoggerFactory.getLogger(PosTemplateController.class);

    @Autowired
    private PosRewardTemplateService posRewardTemplateService;

    @RequestMapping("/pageList")
    public String rewardTempPage() {
        return "profit/rewardTemp/rewardTempList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object getRewardTemplateList(HttpServletRequest request) {
        Page page = pageProcess(request);
        PageInfo pageInfo = posRewardTemplateService.getPosRewardTemplateList(page);
        return pageInfo;
    }

    @RequestMapping("/editPage")
    public ModelAndView getPosRewardTemplate(String id) {
        if (StringUtils.isNotBlank(id)) {
            PosRewardTemplate posRewardTemplate = posRewardTemplateService.getPosRewardTemplate(id);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("profit/rewardTemp/rewardTempEdit");
            modelAndView.addObject("posRewardTemplate", posRewardTemplate);
            String[] actId = posRewardTemplate.getActivityValid().split("~");
            modelAndView.addObject("activityValidStart", actId[0]);
            modelAndView.addObject("activityValidEnd", actId[1]);
            return modelAndView;
        }
        return null;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Object editPosRewardTemplate(PosRewardTemplate posRewardTemplate, HttpServletRequest request) {
        if (posRewardTemplate == null) {
            return renderError("系统异常，请联系维护人员！");
        }
        if(StringUtils.isBlank(posRewardTemplate.getCreditTranContrastMonth())
                || StringUtils.isBlank(posRewardTemplate.getTranContrastMonth())
                || StringUtils.isBlank(posRewardTemplate.getProportion().toString())
                || StringUtils.isBlank(posRewardTemplate.getTranTotalEnd().toString())
                || StringUtils.isBlank(posRewardTemplate.getTranTotalStart().toString())
                || StringUtils.isBlank(request.getParameter("activityValidStart"))
                || StringUtils.isBlank(request.getParameter("activityValidEnd"))
                ){
            return renderError("请填写完毕");
        }
        try{
            Date parse = new SimpleDateFormat("yyyy-MM").parse(posRewardTemplate.getTranContrastMonth());
            LOG.info("新交易对比月:"+parse);
            if (!new SimpleDateFormat("yyyy-MM").format(parse).equals(posRewardTemplate.getTranContrastMonth())){
                return renderError("交易对比月份格式填写错误！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return renderError("交易对比月份格式填写错误！");
        }
        try{
            Date parse = new SimpleDateFormat("yyyy-MM").parse(request.getParameter("activityValidStart"));
            LOG.info("新交易对比月:"+parse);
            if (!new SimpleDateFormat("yyyy-MM").format(parse).equals(request.getParameter("activityValidStart"))){
                return renderError("活动起始月份格式填写错误！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return renderError("活动起始月份格式填写错误！");
        }
        try{
            Date parse = new SimpleDateFormat("yyyy-MM").parse(request.getParameter("activityValidEnd"));
            LOG.info("新交易对比月:"+parse);
            if (!new SimpleDateFormat("yyyy-MM").format(parse).equals(request.getParameter("activityValidEnd"))){
                return renderError("活动结束月份格式填写错误！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return renderError("活动结束月份格式填写错误！");
        }
        if(Objects.equals(request.getParameter("activityValidStart"),request.getParameter("activityValidEnd"))){
            return renderError("开始时间与结束时间不能相等");
        }
        try {
            if (new SimpleDateFormat("yyyy-MM").parse(request.getParameter("activityValidEnd")).compareTo(new SimpleDateFormat("yyyy-MM").parse(request.getParameter("activityValidStart")))<0){
                return renderError("开始时间不能小于结束时间");
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        try {
            posRewardTemplate.setActivityValid(request.getParameter("activityValidStart")+"~"+request.getParameter("activityValidEnd"));
            posRewardTemplate.setUpdateTime(new Date());
            posRewardTemplate.setOperUser(getShiroUser().getLoginName());
            posRewardTemplateService.updatePosRewardTemplate(posRewardTemplate);
            return renderSuccess("Pos奖励模板修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }

    @RequestMapping("/addPage")
    public ModelAndView addPosRewardTemplatePage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("profit/rewardTemp/rewardTempAdd");
        return modelAndView;
    }

    @RequestMapping("/add")
    @ResponseBody
    public Object addPosRewardTemplate(PosRewardTemplate posRewardTemplate, HttpServletRequest request) {
        if (posRewardTemplate == null) {
            return renderError("系统异常，请联系维护人员！");
        }
        if(StringUtils.isBlank(posRewardTemplate.getCreditTranContrastMonth())
                || StringUtils.isBlank(posRewardTemplate.getTranContrastMonth())
                || StringUtils.isBlank(posRewardTemplate.getProportion().toString())
                || StringUtils.isBlank(posRewardTemplate.getTranTotalEnd().toString())
                || StringUtils.isBlank(posRewardTemplate.getTranTotalStart().toString())
                || StringUtils.isBlank(request.getParameter("activityValidStart"))
                || StringUtils.isBlank(request.getParameter("activityValidEnd"))){
            return renderError("请填写完毕");
        }
        try{
            Date parse = new SimpleDateFormat("yyyy-MM").parse(posRewardTemplate.getTranContrastMonth());
            LOG.info("新交易对比月:"+parse);
            if (!new SimpleDateFormat("yyyy-MM").format(parse).equals(posRewardTemplate.getTranContrastMonth())){
                return renderError("交易对比月份格式填写错误！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return renderError("交易对比月份格式填写错误！");
        }
        try{
            Date parse = new SimpleDateFormat("yyyy-MM").parse(request.getParameter("activityValidStart"));
            LOG.info("新交易对比月:"+parse);
            if (!new SimpleDateFormat("yyyy-MM").format(parse).equals(request.getParameter("activityValidStart"))){
                return renderError("活动起始月份格式填写错误！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return renderError("活动起始月份格式填写错误！");
        }
        try{
            Date parse = new SimpleDateFormat("yyyy-MM").parse(request.getParameter("activityValidEnd"));
            LOG.info("新交易对比月:"+parse);
            if (!new SimpleDateFormat("yyyy-MM").format(parse).equals(request.getParameter("activityValidEnd"))){
                return renderError("活动结束月份格式填写错误！");
            }
        }catch (Exception e){
            e.printStackTrace();
            return renderError("活动结束月份格式填写错误！");
        }
        if(Objects.equals(request.getParameter("activityValidStart"),request.getParameter("activityValidEnd"))){
            return renderError("开始时间与结束时间不能相等");
        }
        try {
            if (new SimpleDateFormat("yyyy-MM").parse(request.getParameter("activityValidEnd")).compareTo(new SimpleDateFormat("yyyy-MM").parse(request.getParameter("activityValidStart")))<0){
                return renderError("开始时间不能小于结束时间");
            }
        }catch (ParseException e){
            e.printStackTrace();
        }
        try {
            posRewardTemplate.setActivityValid(request.getParameter("activityValidStart")+"~"+request.getParameter("activityValidEnd"));
            posRewardTemplate.setCreateTime(new Date());
            posRewardTemplate.setUpdateTime(new Date());
            posRewardTemplate.setOperUser(getShiroUser().getLoginName());
            posRewardTemplateService.insertPosRewardTemplate(posRewardTemplate);
            return renderSuccess("Pos奖励模板修改成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return renderError("系统异常，请联系维护人员！");
    }
}
