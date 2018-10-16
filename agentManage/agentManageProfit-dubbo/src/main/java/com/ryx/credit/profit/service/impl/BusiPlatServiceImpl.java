package com.ryx.credit.profit.service.impl;/**
 * @Auther: zhaodw
 * @Date: 2018/7/31 20:27
 * @Description:
 */

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.common.util.agentUtil.AESUtil;
import com.ryx.credit.common.util.agentUtil.RSAUtil;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.profit.service.BusiPlatService;
import com.ryx.credit.service.agent.AgentNotifyService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.time.DateFormatUtils;
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

    @Autowired
    AgentNotifyService agentNotifyService;

    @Override
    public void mPos_Frozen() {

    }

    @Override
    public void mPos_unFrozen() {

    }


    @Override
    public void mPos_updateAgName(String agentName, List<String> platId) {
        HashMap<String,String> map = new HashMap<String,String>();
        map.put("companyname",agentName);
        map.put("batchIds",platId.toString());
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.bucha"),params);
        log.debug(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            log.error("请求失败！");
            AppConfig.sendEmails("代理商更名失败","代理商更名失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        log.debug(data);
        log.debug("代理商更名成功！");
    }

    @Override
    public void pos_updateAgName(AgentNotifyVo agentNotifyVo) throws Exception {
        agentNotifyVo.setUseOrgan("886");
        agentNotifyService.httpRequestForPos(agentNotifyVo);
    }

    @Override
    public void mPos_quit() {

    }

    @Override
    public void pos_quit() {

    }
}
