package com.ryx.credit.cms.controller.order;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.*;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.exception.ProcessException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.*;
import com.ryx.credit.pojo.admin.order.*;
import com.ryx.credit.pojo.admin.vo.*;
import com.ryx.credit.service.agent.AgentQueryService;
import com.ryx.credit.service.order.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @Auther: lrr
 * @Date: 2019/6/14 10:41
 * @Description:
 */
@RequestMapping("oorganization")
@Controller
public class OorganizationController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(OorganizationController.class);
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private AgentQueryService agentQueryService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        logger.info("[开始]-机构列表页面");
        request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
        return "order/organizationList";
    }

    /**
     * 查询机构列表
     *
     * @param request
     * @param Organization
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "organizationList")
    public PageInfo organizationList(HttpServletRequest request, Organization organization) {
        logger.info("[开始]-查询机构列表");
        String userId = String.valueOf(getUserId());
        Page pageInfo = pageProcess(request);
        PageInfo info = organizationService.organizationList(pageInfo, organization);
        return info;
    }

    @RequestMapping(value = {"organizationAddView", "form"})
    public String organizationAddView(HttpServletRequest request, HttpServletResponse response, Model model) {
        optionsData(request);
        request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
        //查詢所有的顶级
        model.addAttribute("organizationTop", organizationService.selectTop());
        return "order/organizationAdd";
    }

    /**
     * 添加机构
     * oorganization/organizationAdd
     */
    @ResponseBody
    @RequestMapping(value = {"organizationAdd"}, method = RequestMethod.POST)
    public ResultVO organizationAdd(HttpServletRequest request, @RequestBody AgentVo agentVo) {
        logger.info("添加机构");
        try {
            agentVo.setSid(getUserId() + "");
            ResultVO resultVO = organizationService.organizationAdd(agentVo);
            return resultVO;
        } catch (MessageException e) {
            return ResultVO.fail(e.getMsg());
        } catch (Exception e) {
            logger.info("机构添加失败");
            e.printStackTrace();
            return ResultVO.fail(e.getLocalizedMessage());
        }
    }

    /**
     * 删除机构
     *
     * @param request
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "organizationDelete")
    public AgentResult organizationDelete(HttpServletRequest request, @RequestParam("id") String id) {
        try {
            return organizationService.organizationDelete(id, getUserId() + "");
        } catch (Exception e) {
            e.printStackTrace();
            return AgentResult.fail("删除失败！");
        }

    }

    /**
     * 查询机构详情
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "queryOrganization")
    public String queryOrganization(String id, Model model, HttpServletRequest request) {
        logger.info("机构的详情");
        selectAll(id, model, request);
        return "order/organizationDialog";
    }

    /**
     * 进入修改页面
     */
    @RequestMapping(value = "organizationEditView")
    public String organizationEditView(String id, Model model, HttpServletRequest request) {
        logger.info("机构的详情");
        selectAll(id, model, request);
        return "order/organizationEdit";
    }


    /**
     * 修改机构
     * oorganization/organizationEdit
     * @param request
     * @param organizationVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "organizationEdit")
    public ResultVO organizationEdit(HttpServletRequest request, @RequestBody AgentVo agentVo) {
        try {
            logger.info("修改机构");
            ResultVO rv = new ResultVO();
            agentVo.setSid(getUserId() + "");
            rv = organizationService.organizationEdit(agentVo);
            return rv;
        } catch (ProcessException e) {
            return ResultVO.fail(e.getMsg());
        } catch (MessageException e) {
            return ResultVO.fail(e.getMsg());
        } catch (Exception e) {
            logger.info("机构修改错误{}{}", getUserId() + "",e.getMessage());
            e.printStackTrace();
            return ResultVO.fail(e.getLocalizedMessage());
        }
    }

    public void selectAll(String id, Model model, HttpServletRequest request) {
        optionsData(request);
        request.setAttribute("ablePlatForm", ServiceFactory.businessPlatformService.queryAblePlatForm());
        //查詢所有的顶级
//        model.addAttribute("organizationTop", organizationService.selectTop());
        List<Organization> organization = organizationService.selectOrganization(id);
//        List<OrganizationSerchVo> organization = organizationService.queryOrganization(id);

        List<Attachment> attachment = agentQueryService.accessoryQuery(id, AttachmentRelType.Organization.name());
        model.addAttribute("attachment", attachment);
        model.addAttribute("Organization", organization);
        model.addAttribute("platId", organization.get(0).getPlatId());
        model.addAttribute("businessNum", organization.get(0).getBusinessNum());

    }
}