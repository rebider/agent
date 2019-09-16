package com.ryx.credit.cms.controller;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ryx.credit.service.ISysLogService;

import javax.servlet.http.HttpServletRequest;

/**
 * @description：日志管理
 * @author：zhixuan.wang
 * @date：2015/10/30 18:06
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController extends BaseController{
    @Autowired
    private ISysLogService sysLogService;

    @GetMapping("/manager")
    public String manager() {
        return "admin/syslog";
    }

    @PostMapping("/dataGrid")
    @ResponseBody
    public PageInfo dataGrid(HttpServletRequest request) {
        Page page = pageProcess(request);
        PageInfo pageInfo = sysLogService.selectDataGrid(page);
        return pageInfo;
    }
}
