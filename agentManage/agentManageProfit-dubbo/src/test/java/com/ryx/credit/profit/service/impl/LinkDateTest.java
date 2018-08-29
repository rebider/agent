package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.profit.dao.PAgentPidLinkMapper;
import com.ryx.credit.profit.pojo.PAgentPidLink;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitDirect;
import com.ryx.credit.profit.service.IProfitDService;
import com.ryx.credit.profit.service.IProfitDirectService;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.service.dict.IdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author yangmx
 * @desc
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class LinkDateTest {

    @Autowired
    IdService idService;
    @Autowired
    IProfitDService profitDService;
    @Autowired
    ProfitComputerServiceImpl computerService;
    @Autowired
    ProfitDetailMonthService profitDetailMonthService;
    @Autowired
    IProfitDirectService profitDirectService;
    @Autowired
    ProfitDeductionService profitDeductionService;
    @Autowired
    PAgentPidLinkMapper pidLinkMapper;
    private int index=1;

    @Test
    public void testX(){
        computerService.computerTax("201806");
    }

    public void synchroProfitLink(){
        HashMap<String,String> map = new HashMap<String,String>();
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.link"),params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        try {
            insertProfitLink(list);
        } catch (Exception e) {
            System.out.println("同步插入数据失败！");
            e.printStackTrace();
        }
    }

    public void insertProfitLink(List<JSONObject> profitDays){
        pidLinkMapper.deleteAll();
        System.out.println("先删除所有");
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