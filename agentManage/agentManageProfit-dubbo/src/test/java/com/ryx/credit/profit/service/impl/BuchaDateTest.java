package com.ryx.credit.profit.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.AppConfig;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.common.util.HttpClientUtil;
import com.ryx.credit.common.util.JsonUtil;
import com.ryx.credit.profit.dao.PAgentPidLinkMapper;
import com.ryx.credit.profit.dao.ProfitSupplyDiffMapper;
import com.ryx.credit.profit.pojo.PAgentPidLink;
import com.ryx.credit.profit.pojo.ProfitSupplyDiff;
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
public class BuchaDateTest {

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
    @Autowired
    ProfitSupplyDiffMapper supplyDiffMapper;
    private int index=1;

    @Test
    public void testX(){
        synchroProfitDiff(null);
    }

    public void synchroProfitDiff(String month){
        HashMap<String,String> map = new HashMap<String,String>();
        month = month==null? DateUtil.sdfDays.format(DateUtil.addMonth(new Date() , -1)).substring(0,6):month;
        map.put("frMonth",month);
        map.put("pageNumber",index++ +"");
        map.put("pageSize","50");
        String params = JsonUtil.objectToJson(map);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.bucha"),params);
        System.out.println(res);
        if(!JSONObject.parseObject(res).get("respCode").equals("000000")){
            System.out.println("请求同步失败！");
            AppConfig.sendEmails("日分润同步失败","日分润同步失败");
            return;
        }
        String data = JSONObject.parseObject(res).get("data").toString();
        List<JSONObject> list = JSONObject.parseObject(data,List.class);
        try {
            if(list.size()>0){
                insertProfitDiff(list,month);
            }
        } catch (Exception e) {
            System.out.println("同步插入数据失败！");
            e.printStackTrace();
            throw new RuntimeException("分润数据处理失败");
        }
    }

    public void insertProfitDiff(List<JSONObject> profitDays,String date){
        for(JSONObject json:profitDays){
            ProfitSupplyDiff profitDiff = new ProfitSupplyDiff();
            profitDiff.setId(idService.genId(TabId.P_PROFIT_SUPPLU_DIFF));
            profitDiff.setAgentId(json.getString("AGENTID"));
            profitDiff.setAgentPid(json.getString("AGENTPID"));
            profitDiff.setDiffAmt(json.getBigDecimal("DIFFAMT"));
            profitDiff.setDiffDate(json.getString("DIFFDATE"));
            profitDiff.setDiffType(json.getString("DIFFTYPE"));
            profitDiff.setParentAgentid(json.getString("PARENTAGENTID"));
            profitDiff.setParentAgentpid(json.getString("PARENTAGENTPID"));
            profitDiff.setAgentName(json.getString("AGENTNAME"));
            profitDiff.setParentAgentname(json.getString("PARENTAGENTNAME"));
            supplyDiffMapper.insert(profitDiff);

        }
        synchroProfitDiff(date);
    }

}