package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.pojo.admin.agent.AgentColinfo;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.profit.service.BusiPlatService;
//import com.ryx.credit.service.agent.AgentNotifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * 业务平台接口服务实现类
 * @author wangy
 * @create 2018/10/15
 * @since 1.0.0
 */
@Service("busiPlatService")
public class BusiPlatServiceImpl implements BusiPlatService {

    private static Logger log = LoggerFactory.getLogger(BusiPlatServiceImpl.class);

//    @Autowired
//    AgentNotifyService agentNotifyService;

    @Override
    public boolean mPos_Frozen(List<String> agentIds) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("agencyBlack_type", "1");//1、冻结 0、解冻
        map.put("type", "1");//1-冻结本身以及下属，2-冻结自身（默认）；3-本身不冻结，冻结所有下级
        map.put("unfreeze", "0");//0-双冻结（默认）;1-分润冻结; 2-返现冻结
        map.put("flag", "4");//4冻结;0解冻
        map.put("batchIds", agentIds.toString());//AG码list
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("busiPlat.refuse"), params);
        log.debug("请求信息：" + res);
        if (!JSONObject.parseObject(res).get("respCode").equals("000000")) {
            log.error("请求失败！");
            AppConfig.sendEmails("代理商冻结失败" + res,"代理商冻结失败");
            return false;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        log.debug("data：" + data);
        log.debug("代理商冻结成功！");
        return true;
    }

    @Override
    public boolean mPos_unFrozen(List<String> agentIds) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("agencyBlack_type","0");//1、冻结 0、解冻
        map.put("type","1");//解冻时无视该类型
        map.put("unfreeze","0");//解冻时无视该类型
        map.put("flag","0");//4冻结;0解冻
        map.put("batchIds",agentIds.toString());//AG码list
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("busiPlat.refuse"),params);
        log.debug(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            log.error("请求失败！");
            AppConfig.sendEmails("代理商解冻失败："+res,"代理商解冻失败");
            return false;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        log.debug(data);
        log.debug("代理商解冻成功！");
        return true;
    }

    @Override
    public AgentResult mPos_updateAgName(String agentName, List<String> platId, AgentColinfo agentColinfo) {
        HashMap<String,String> map = new HashMap<String,String>();

        map.put("companyname", agentName);//代理商名称
        map.put("batchIds", platId.toString());//AG码
        map.put("colinfoMessage", agentColinfo.toString());//收款账户
        String params = JsonUtil.objectToJson(map);
        log.info("======mPos_updateAgName:{}",params);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("busiPlat.upAgName"),params);
        log.info("======mPos_updateAgName结果:{}",res);
        JSONObject resObj = JSONObject.parseObject(res);
        if(!resObj.get("respCode").equals("000000")){
            log.error("请求失败！");
            AppConfig.sendEmails("代理商更名失败:"+res,"代理商更名失败");
            return AgentResult.fail("代理商更名失败");
        }
        log.info("代理商更名成功！{}",resObj.get("respMsg"));
        return AgentResult.ok();
    }

    @Override
    public AgentResult pos_updateAgName(AgentNotifyVo agentNotifyVo) throws Exception {
        agentNotifyVo.setUseOrgan("886");//使用范围 886-代理商
//        return agentNotifyService.httpRequestForPos(agentNotifyVo);
        return null;
    }

    @Override
    public void mPos_quit() {

    }

    @Override
    public void pos_quit() {

    }

}
