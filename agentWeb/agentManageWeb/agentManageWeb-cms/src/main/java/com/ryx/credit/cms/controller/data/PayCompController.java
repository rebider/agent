package com.ryx.credit.cms.controller.data;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.service.dict.PayCompService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.TreeMap;

/**
 * @author yangmx
 * @desc 打款公司字典表维护
 */
@Controller
@RequestMapping("/paycomp")
public class PayCompController extends BaseController {

    @Autowired
    private PayCompService payCompService;

    @GetMapping("/listPage")
    public String viewJsp(){
        return "data/payCompList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object getPageList(HttpServletRequest request, PayComp payComp){
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        pageInfo = payCompService.getPayCompList(page, payComp);
        return  pageInfo;
    }

    @GetMapping("/addPage")
    public String addViewJsp(){
        return "data/payCompAdd";
    }

    @RequestMapping("/add")
    @ResponseBody
    public Object addPayComp(PayComp payComp){
        payCompService.insertPayComp(payComp);
        return renderSuccess("添加成功！");
    }

    @GetMapping("/editPage")
    public String editViewJsp(HttpServletRequest request, @Param("id") String id){
        PayComp payComp = payCompService.getPayCompById(id);
        request.setAttribute("payComp", payComp);
        return "data/payCompEdit";
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Object editPayComp(PayComp payComp){
        payCompService.updatePayComp(payComp);
        return renderSuccess("修改成功！");
    }
}
