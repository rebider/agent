package com.ryx.credit.cms.controller.data;

import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.enumc.AttachmentRelType;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.pojo.admin.agent.Attachment;
import com.ryx.credit.pojo.admin.agent.AttachmentRel;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.service.agent.AgentDataHistoryService;
import com.ryx.credit.service.data.AttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 附件维护
 */
@RequestMapping("accessory")
@Controller
public class AccessoryController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AccessoryController.class);
    @Autowired
    private AttachmentService attachmentService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Dict> list = new ArrayList<Dict>();
        for (AttachmentRelType attachment : AttachmentRelType.values()) {
            Dict dict = new Dict();
            dict.setdItemnremark(attachment.name());
            dict.setdItemname(attachment.msg);
            list.add(dict);
        }
        model.addAttribute("accessoryList",list);
        return "data/accessoryList";
    }

    @ResponseBody
    @RequestMapping(value = "selectList")
    public PageInfo selectList(HttpServletRequest request, HttpServletResponse response,Attachment attachment,AttachmentRel attachmentRel) {
        Page pageInfo = pageProcess(request);
        PageInfo info= attachmentService.selectAll(pageInfo,attachment,attachmentRel);
        return info;
    }

}
