package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.exceptions.StagingException;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitStaging;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.StagingService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

/**
 * 退单管理页面逻辑处理
 * @author zhaodw
 * @create 2018/7/24
 * @since 1.0.0
 */
@Controller
@RequestMapping("/profit/staging/")
public class StagingController extends BaseController {

    private static  final  Logger LOGGER = Logger.getLogger(StagingController.class);

    @Autowired
    private ProfitDeductionService profitDeductionServiceImpl;

    @Autowired
    private StagingService stagingServiceImpl;

    /**
     * 加载分期列表
     * @return 页面url
     */
    @RequestMapping("gotoStagingList")
    public String gotoStagingList() {
        LOGGER.info("加载分期列表页面。");
        return "profit/stagingList";
    }

    /**
     * 加载分期列表
     * @return 页面url
     */
    @RequestMapping("gotoStagingDetailList")
    public String gotoStagingDetailList(Model model, String sourceId) {
        LOGGER.info("加载分期明细页面。");
        model.addAttribute("sourceId", sourceId);
        return "profit/staging/stagingDetailList";
    }

    /**
     * 加载分期申请页面
     * @return 申请页面url
     */
    @RequestMapping("gotoAddPage")
    public String gotoAddPage(Model model, String sourceId, String stagType) {
        LOGGER.info("加载新增分期页面。");
        ProfitDeduction profitDeduction = profitDeductionServiceImpl.getProfitDeductionById(sourceId);
        model.addAttribute("profitDeduction", profitDeduction);
        model.addAttribute("stagType", stagType);
        return "profit/staging/stagingAdd";
    }

    /***
    * @Author: zhaodw
    * @Date: 2018/8/3
    */
    @RequestMapping(value = "getStagingDetailList", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo getStagingDetailList(HttpServletRequest request, ProfitStagingDetail profitStagingDetail)
    {
        LOGGER.info("加载分期明细列表数据。");
        Page page = pageProcess(request);
        PageInfo resultPageInfo = stagingServiceImpl.getStagingDetailList(profitStagingDetail,null);
        return resultPageInfo;
    }


    /***
    * @Description:  编辑页面
    * @Author: zhaodw
    * @Date: 2018/8/3
    */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public Object edit(HttpServletRequest request, ProfitStaging profitStaging)
    {
        try {
             stagingServiceImpl.editStaging(profitStaging);
             return renderSuccess("编辑分期成功！");
        }catch (StagingException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return renderError(e.getMessage());
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return renderError("编辑失败！");
    }

    /***
    * @Description: 新增分期
    * @Param:
    * @return:
    * @Author: zhaodw
    * @Date: 2018/8/2
    */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public Object add(HttpServletRequest request, ProfitStaging profitStaging)
    {
        LOGGER.info("新增分期。");
        try {
//            getShiroUser().
            profitStaging.setUserId(getUserId().toString());
            profitStaging.setUserName(getShiroUser().getName());

            String workId = "refundByStages";
            String bustype = null;
            if ("2".equals(profitStaging.getStagType())) {
                Set<String> roles = getShiroUser().getRoles();
                if(roles !=null && roles.contains("代理商")) {
                    workId = "otherDeductAgent";
                    bustype ="1";
                }else {
                    workId = "otherDeductCity";
                    bustype ="1";
                }
            }
            if("3".equals(profitStaging.getStagType())){
                Set<String> roles = getShiroUser().getRoles();
                if(roles !=null && roles.contains("代理商")) {
                    workId = "otherDeductAgent";
                    bustype ="2";
                }else {
                    workId = "otherDeductCity";
                    bustype ="2";
                }
            }
            stagingServiceImpl.addStaging(profitStaging, workId,bustype);
            return renderSuccess("新增分期成功！");
        }catch (StagingException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return renderError(e.getMessage());
        }catch (Exception e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return renderError("新增分期失败！");
    }
}
