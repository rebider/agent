package com.ryx.credit.cms.controller.data;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.cms.shiro.permission.UrlPermission;
import com.ryx.credit.cms.util.ServiceFactory;
import com.ryx.credit.common.enumc.ApprovalType;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.NotifyType;
import com.ryx.credit.common.redis.RedisService;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.pojo.admin.CResource;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.service.IResourceService;
import com.ryx.credit.service.agent.AgentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Created by RYX on 2018/7/18.
 */
@RequestMapping("cache")
@Controller
public class CacheManageController extends BaseController{

    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private IResourceService resourceService;
    @Autowired
    private RedisService redisService;


    @RequestMapping("delNetInCache")
    @ResponseBody
    public String delNetInCache(){
        ServiceFactory.dictGroup = null;
        ServiceFactory.redisService.delete("DICT_GROUP");
        return "操作成功";
    }

    @RequestMapping("delOrderCache")
    @ResponseBody
    public String delOrderCache(){
        ServiceFactory.orderDictGroup=null;
        ServiceFactory.redisService.delete("ORDER_DICT_GROUP");
        return "操作成功";
    }

    @RequestMapping("querySms")
    public String querySms(HttpServletRequest request){
        Map<Object, Object> smsList = ServiceFactory.redisService.hGetall("sms");
        request.setAttribute("smsList",smsList);
        return "sms";
    }


    /**
     *  /cache/loadCache
     * @return
     */
    @RequestMapping("loadCache")
    @ResponseBody
    public String loadCache(HttpServletRequest request){

        delNetInCache();
        delOrderCache();
        optionsData(request);
        orderDictData(request);
        agentQueryService.loadCach();
        UrlPermission.loadUrl(resourceService,redisService);
        return "操作成功";
    }


}
