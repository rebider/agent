package com.ryx.credit.cms.controller.data;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.TemplateAgreement;
import com.ryx.credit.service.dict.TemplateAgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.TreeMap;

/**
 * @author yangmx
 * @desc 模板协议维护
 */
@Controller
@RequestMapping("/tempAgreement")
public class TemplateAgreementController extends BaseController {

    @Autowired
    private TemplateAgreementService templateAgreementService;

    @GetMapping("/listPage")
    public String viewJsp(HttpServletRequest request ){
        return "data/templateAgreeList";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object getPageList(HttpServletRequest request, TemplateAgreement templateAgreement){
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), null, null);
        pageInfo = templateAgreementService.getTempAgreeList(page, templateAgreement);
        List<Dict> dict = ServiceFactory.dictOptionsService.dictList(DictGroup.TEMP_AGREE.name(), DictGroup.AGREE_TYPE.name());
        List<TemplateAgreement> list =  pageInfo.getRows();
        for (TemplateAgreement temp : list) {
            for (Dict dictList:dict) {
                if(dictList.getdItemvalue().equals(temp.getAgreType())){
                    temp.setAgreType(dictList.getdItemname());
                }
            }
        }
        return  pageInfo;
    }

    @GetMapping("/addPage")
    public String addViewJsp(HttpServletRequest request){
        List<Dict> dict = ServiceFactory.dictOptionsService.dictList(DictGroup.TEMP_AGREE.name(), DictGroup.AGREE_TYPE.name());
        request.setAttribute("dict" , dict);
        return "data/templateAgreeAdd";
    }

    @RequestMapping("/add")
    @ResponseBody
    public Object addTemplateAgreement(TemplateAgreement templateAgreement){
        templateAgreementService.insertTempAgreement(templateAgreement);
        return renderSuccess("添加成功！");
    }

    @GetMapping("/editPage")
    public String editViewJsp(HttpServletRequest request, @Param("id") String id){
        List<Dict> dict = ServiceFactory.dictOptionsService.dictList(DictGroup.TEMP_AGREE.name(), DictGroup.AGREE_TYPE.name());
        request.setAttribute("dict" , dict);
        TemplateAgreement templAgree = templateAgreementService.getTempAgreeById(id);
        request.setAttribute("templAgree" , templAgree);
        return "data/templateAgreeEdit";
    }

    @RequestMapping("/edit")
    @ResponseBody
    public Object editTemplateAgreement(TemplateAgreement templateAgreement){
        templateAgreementService.updateTempAgreement(templateAgreement);
        return renderSuccess("修改成功！");
    }
}
