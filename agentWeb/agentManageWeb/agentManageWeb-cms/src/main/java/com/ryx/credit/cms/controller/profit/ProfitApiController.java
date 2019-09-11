package com.ryx.credit.cms.controller.profit;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.cms.controller.BaseController;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.pojo.ResultObject;
import com.ryx.credit.profit.service.IProfitDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


/**
 * 分潤数据接口控制层
 * @version V1.0
 * @Description:
 * @author: WANGY
 * @date: 2018/7/23 15:30
 */
@RequestMapping("profitapi")
@Controller
public class ProfitApiController extends BaseController{

    @Autowired
    private IProfitDService profitDService;


    @RequestMapping(value = "profitD",method = RequestMethod.POST)
    @ResponseBody
    public ResultObject profitDList(@RequestBody String params){
        ResultObject result = new ResultObject("0000","成功");
        //HashMap map = JSONObject.parseObject(params, HashMap.class);
        String res = HttpClientUtil.doPostJson("http://12.3.10.161:8003/qtfr/agentInterface/queryfrbyday.do",params);
        result.setResult(res);
        return result;
    }

}
