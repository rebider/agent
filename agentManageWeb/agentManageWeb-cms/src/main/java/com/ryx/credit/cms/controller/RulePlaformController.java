package com.ryx.credit.cms.controller;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.*;
import com.ryx.credit.service.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * RulePlaformController
 * Created by IntelliJ IDEA.
 *
 * @Author Wang Qi
 * @Date 2017/6/19
 * @Time: 10:58
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/rulePlatform")
public class RulePlaformController extends BaseController{
    protected Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ICConditionService cConditionService;
    @Autowired
    private ICConditionValueService cConditionValueService;
    @Autowired
    private ICRuleConditionService cRuleConditionService;
    @Autowired
    private ICRulePlatformService cRulePlatformService;
    @Autowired
    private ICRuleService cRuleService;
    @Autowired
    private ICStrategyRuleService cStrategyRuleService;
    @Autowired
    private ICStrategyService cStrategyService;
    @Autowired
    private ICRulePlatformService rulePlatformService;

    @RequestMapping("/rulePlatformList")
    @ResponseBody
    public Object rulePlaformList(final HttpServletRequest request,
                           final HttpServletResponse response,
                           CRulePlatform cRulePlatform) {
        //返回对象
        TreeMap<String, String> treeMap = getRequestParameter(request);
        Page page = pageProcess(request);
        PageInfo pageInfo = new PageInfo(page.getCurrent(), page.getLength(), "seq", "desc");
        Map map = rulePlatformService.configExample(page, cRulePlatform);
        List<CRulePlatform> cRulePlatforms = (List<CRulePlatform>) map.get("list");
        page = (Page) map.get("page");
        pageInfo.setTotal(page.getCount());
        pageInfo.setRows(cRulePlatforms);
        pageInfo.setFrom(page.getBegin());
        return pageInfo;
    }

    @RequestMapping("/toList")
    public Object toList(final HttpServletRequest request,
                         final HttpServletResponse response
    ) {
        return "rulePlatform/rulePlatformList";
    }

    @RequestMapping("/toAdd")
    public Object toAdd(final HttpServletRequest request,
                        final HttpServletResponse response
    ) {
        return "rulePlatform/addRulePlatform";
    }

    @RequestMapping("/addRulePlatform")
    @ResponseBody
    public Object addRulePlatform(final HttpServletRequest request,
                          final HttpServletResponse response) {
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            CRulePlatform cRulePlatform = new CRulePlatform();
            cRulePlatform.setName(treeMap.get("name"));
            cRulePlatform.setCode(treeMap.get("code"));
            cRulePlatform.setDepartment(treeMap.get("department"));
            cRulePlatform.setMgroup(treeMap.get("mgroup"));
            Map map = cRulePlatformService.configExample(pageProcessAll(request, 2), cRulePlatform);
            List<CRulePlatform> list = (List<CRulePlatform>) map.get("list");
            if (list != null && list.size() > 0) {
                return renderSuccess("产品已经有该命名，请重新定义！");
            }
            cRulePlatform.setCreateTime(new Date());
            cRulePlatform.setDescription(treeMap.get("description"));
            cRulePlatform.setUpdateTime(new Date());
            cRulePlatformService.insert(cRulePlatform);
        } catch (Exception e) {
            logger.error("addRulePlatform error", e);
        }
        return renderSuccess("添加成功！");
    }

    @RequestMapping("/toUpdate")
    public Object toUpdate(final HttpServletRequest request,
                           final HttpServletResponse response,
                           BigDecimal id) {
        CRulePlatform cRulePlatform = cRulePlatformService.selectByPrimaryKey(id);
        request.setAttribute("cRulePlatform", cRulePlatform);
        return "rulePlatform/updateRulePlatform";
    }

    @RequestMapping("/updateRulePlatform")
    @ResponseBody
    public Object updateRulePlatform(final HttpServletRequest request,
                             final HttpServletResponse response) {
        TreeMap<String, String> treeMap = getRequestParameter(request);
        try {
            CRulePlatform cRulePlatform = new CRulePlatform();
            cRulePlatform.setName(treeMap.get("name"));
            cRulePlatform.setCode(treeMap.get("code"));
            cRulePlatform.setDepartment(treeMap.get("department"));
            cRulePlatform.setMgroup(treeMap.get("mgroup"));
            Map map = cRulePlatformService.configExample(pageProcessAll(request, 1), cRulePlatform);
            List<CRulePlatform> list = (List<CRulePlatform>) map.get("list");
            if (list != null && list.size() > 0) {
                for (CRulePlatform rule : list) {
                    if (!rule.getId().equals(new BigDecimal(treeMap.get("id")))) {        //修改时，判断非本身的数据是否重复
                        return renderSuccess("产品已经有该命名，请重新定义！");
                    }
                }
            }
            cRulePlatform.setId(new BigDecimal(treeMap.get("id")));
            cRulePlatform.setCreateTime(new Date());
            cRulePlatform.setDescription(treeMap.get("description"));
            cRulePlatform.setUpdateTime(new Date());
            cRulePlatformService.updateByPrimaryKeySelective(cRulePlatform);
        } catch (Exception e) {
            logger.error("updateRulePlatform error", e);
        }
        return renderSuccess("修改成功！");
    }
}
