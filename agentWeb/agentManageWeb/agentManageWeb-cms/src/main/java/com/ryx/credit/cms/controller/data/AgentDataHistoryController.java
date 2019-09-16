package com.ryx.credit.cms.controller.data;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.enumc.DataHistoryType;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.agent.DataHistory;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.service.agent.AgentDataHistoryService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 历史数据维护
 */
@RequestMapping("dataHistory")
@Controller
public class AgentDataHistoryController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(AgentDataHistoryController.class);


    @Autowired
    private AgentDataHistoryService agentDataHistoryService;

    @RequestMapping(value = "index")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<Dict> list = new ArrayList<Dict>();
        for (DataHistoryType historyType : DataHistoryType.values()) {
            Dict dict = new Dict();
            dict.setdItemnremark(historyType.getValue());
            dict.setdItemname(historyType.getContent());
            list.add(dict);
        }
        model.addAttribute("dataList",list);
        return "data/dataHistory";
    }

    @ResponseBody
    @RequestMapping(value = "selectAll")
    public PageInfo selectAll(HttpServletRequest request, HttpServletResponse response, DataHistory dataHistory, String time) {
        Page pageInfo = pageProcess(request);
        PageInfo info = agentDataHistoryService.selectAll(pageInfo, dataHistory, time);
        List<DataHistory> dataHistorys = info.getRows();
        for (DataHistory history : dataHistorys) {
            if(StringUtils.isNotBlank(history.getcUser())){
                String userName = getUserName(Long.valueOf(history.getcUser()));
                history.setcUserName(StringUtils.isNotBlank(userName)?userName:"");
            }
        }
        return info;
    }


    /**
     *  dataHistory/selectHistory?dataId=&dataType=
     * @param request
     * @param response
     * @param dataHistory
     * @return
     */
    @RequestMapping(value = "selectHistory")
    public String selectHistory(HttpServletRequest request, HttpServletResponse response, DataHistory dataHistory) {
        List<DataHistory> dataHistorys = agentDataHistoryService.selectHistory(dataHistory.getDataId(),dataHistory.getDataType());
        for (DataHistory history : dataHistorys) {
            if(StringUtils.isNotBlank(history.getcUser())){
                String userName = getUserName(Long.valueOf(history.getcUser()));
                history.setcUserName(StringUtils.isNotBlank(userName)?userName:"");
            }
            if(StringUtils.isNotBlank(history.getDataCotent())){
                history.setDataCotentObj(JSONObject.parseObject(history.getDataCotent()));
            }
        }
        request.setAttribute("history",dataHistorys);
        return "data/historyShow";
    }
}
