package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.exceptions.StagingException;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.ProfitOrganTranMonthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 余额交易汇总核对
 * @author zhaodw
 * @create 2018/7/24
 * @since 1.0.0
 */
@Controller
@RequestMapping("/profit/tran/check/")
public class MonthTranSumController extends BaseController {

    private static  final  Logger LOGGER = Logger.getLogger(MonthTranSumController.class);

    @Autowired
    private ProfitOrganTranMonthService profitOrganTranMonthServiceImpl;

    /**
     * 加载列表
     * @return 页面url
     */
    @RequestMapping("gotoMonthTranSumList")
    public String gotoMonthTranSumList(Model model) {
        LOGGER.info("加载分期列表页面。");
        // 终审后不能做修改
        String finalStatus = ServiceFactory.redisService.getValue("commitFinal");
        if (StringUtils.isBlank(finalStatus)) {
            model.addAttribute("noEdit","0");
        }else{
            model.addAttribute("noEdit","1");
        }

        List<Map<String, String>> platFormTypes= profitOrganTranMonthServiceImpl.getAllPlatFormType();
        model.addAttribute("platFormTypes",platFormTypes);
        return "profit/tran/monthTranSumList";
    }

    @RequestMapping("getTranCheckData")
    @ResponseBody
    public Object getTranCheckData(String tranMonth,String platFormType){
        PageInfo pageInfo=new PageInfo();

        if (null==tranMonth&&null==platFormType){
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -1);
            tranMonth= new SimpleDateFormat("yyyyMM").format(calendar.getTime());
        }

        List<Map<String, String>> platFormTypes= profitOrganTranMonthServiceImpl.getAllPlatFormType();
        if ("00".equals(platFormType)){
            platFormType=null;
            if (null==tranMonth||"".equals(tranMonth)){
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, -1);
                tranMonth= new SimpleDateFormat("yyyyMM").format(calendar.getTime());
            }
        }
        for (Map<String,String> temp:platFormTypes){
            if (temp.get("num").equals(platFormType)){
                platFormType=temp.get("name");
                break;
            }
        }
        List<TranCheckData> list=profitOrganTranMonthServiceImpl.getAllCheckDataByPlatFormTypeAndProfitMonth(platFormType,tranMonth);

        pageInfo.setRows(list);
        return pageInfo;
    }

    /***
     * 加载列表数据
    * @Author: zhaodw
    * @Date: 2018/8/3
    */
    /*@RequestMapping(value = "getMonthTranSumList", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo getMonthTranSumList(HttpServletRequest request, ProfitOrganTranMonth profitOrganTranMonth)
    {
        LOGGER.info("加载交易列表数据。");
        Page page = pageProcess(request);
        PageInfo resultPageInfo = profitOrganTranMonthServiceImpl.getProfitOrganTranMonthList(profitOrganTranMonth, page);
        return resultPageInfo;
    }*/


    /***
    * @Description: 导入
    * @Author: zhaodw
    * @Date: 2018/8/3
    */
    @RequestMapping(value = "importData", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(HttpServletRequest request, ProfitStaging profitStaging)
    {
        try {
            String type = request.getParameter("type");
            profitOrganTranMonthServiceImpl.importData(type);
             return renderSuccess("操作成功！");
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return renderError("操作失败！");
    }

    /**
     * 同步数据
     * @return
     */
    @RequestMapping(value = "synchronizeTranCheckData", method = RequestMethod.POST)
    @ResponseBody
    public Object doSynchronizeTranCheckData(){
        try {
            Map<String,String> resultMap=profitOrganTranMonthServiceImpl.doSynchronizeTranCheckData();
            if("success".equals(resultMap.get("resultCode"))){
                return renderSuccess(resultMap.get("msg"));
            }else{
                return renderError(resultMap.get("msg"));
            }
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return renderError("操作失败！");
    }

}
