package com.ryx.credit.profit.unitmain;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.profit.dao.PAgentPidLinkMapper;
import com.ryx.credit.profit.pojo.PAgentPidLink;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 手刷唯一码和业务平台编号关系同步
 */
@Service
public class ProfitMposLinkDataJob {
    Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private PAgentPidLinkMapper pidLinkMapper;
    @Autowired
    private IdService idService;
    private int index = 1;


    public static void main(String agrs[]){
        HashMap<String,String> map = new HashMap<String,String>();
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.link"),params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            //logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        System.out.println(data);
    }

    public void synchroProfitLink(){
        HashMap<String,String> map = new HashMap<String,String>();
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.link"),params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            logger.error("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        try {
            insertProfitLink(list);
        } catch (Exception e) {
            logger.error("同步插入数据失败！");
            e.printStackTrace();
            throw new RuntimeException("分润数据处理失败");
        }
    }

    public void insertProfitLink(List<JSONObject> profitDays){
        pidLinkMapper.deleteAll();
        for(JSONObject json:profitDays){
            String platFormNum = json.getString("platFormNum")==null?"":json.getString("platFormNum");
            if(!"0001".equals(platFormNum) && !"2000".equals(platFormNum) && !"5000".equals(platFormNum)
                    && !"1111".equals(platFormNum) && !"3000".equals(platFormNum) && !"6000".equals(platFormNum)
                    && !"4000".equals(platFormNum)){
                platFormNum = "1001";
            }
            PAgentPidLink link = new PAgentPidLink();
            link.setId(idService.genId(TabId.P_AGENT_PID_LINK));
            link.setDeptCode(platFormNum);
            link.setAgentId(json.getString("agencyId"));
            link.setAgentPid(json.getString("uniqueCode"));
            pidLinkMapper.insert(link);
        }
    }
}
