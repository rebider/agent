package com.ryx.credit.cms.controller.profit;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.ImportDeductionDetail;
import com.ryx.credit.profit.service.ImportDeductionDetailService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 导入扣款明细页面逻辑处理
 * @author zhaodw
 * @create 2018/7/24
 * @since 1.0.0
 */
@Controller
@RequestMapping("/profit/other/deduction/")
public class ImportDeductionDetailController extends BaseController {

    private static  final  Logger LOGGER = Logger.getLogger(ImportDeductionDetailController.class);

    @Autowired
    private ImportDeductionDetailService importDeductionDetailServiceImpl;


    /**
     * 加载分期列表
     * @return 页面url
     */
    @RequestMapping("gotoDeductionDetailList")
    public String gotoDeductionDetailList(Model model, String sourceId) {
        LOGGER.info("加载分期明细页面。");
        model.addAttribute("sourceId", sourceId);
        return "profit/otherDeduction/deductionDetailList";
    }


    @RequestMapping(value = "getDeductionDetailList", method = RequestMethod.POST)
    @ResponseBody
    public PageInfo getDeductionDetailList(HttpServletRequest request, ImportDeductionDetail importDeductionDetail)
    {
        LOGGER.info("加载明细列表数据。");
        Page page = pageProcess(request);
        PageInfo resultPageInfo = importDeductionDetailServiceImpl.getImportDeductionDetailList(importDeductionDetail, page);
        return resultPageInfo;
    }

}
