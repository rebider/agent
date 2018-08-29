package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.profit.service.impl.PosProfitComputeServiceImpl;
import com.ryx.credit.profit.service.impl.ProfitToolsDeductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangmx
 * @desc
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class ToolsDeductJobTest {
    @Autowired
    ToolsDeductJob toolsDeductJob;
    @Autowired
    ProfitToolsDeductServiceImpl profitToolsDeductService;
    @Autowired
    PosProfitComputeServiceImpl posProfitComputeServiceImpl;
    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;

    @Test
    public void execut() throws Exception {
        toolsDeductJob.execut();
    }

    @Test
    public void computeToolsDeduct() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(10);
        map.put("agentPid", "AG20180817000000000006101"); //AG20180813000000000006020
        map.put("paltformNo", "100003");        //平台编号
        map.put("deductDate", "2018-07");       //扣款月份
        map.put("agentProfitAmt", "111111");        //代理商分润
        map = profitToolsDeductService.execut(map);
        System.out.println("应扣："+map.get("mustDeductionAmtSum"));//返回应扣金额
        System.out.println("实扣："+map.get("actualDeductionAmtSum"));//实扣金额
    }

    @Test
    public void computePosreWard() throws Exception {
        List<String> busNum = new ArrayList<String>();
        busNum.add("O00000000000221");
        busNum.add("O00000000000718");
        String profitDate = "201807";
        String agentType = "2";
        List<TransProfitDetail> transProfitDetails = profitDetailMonthServiceImpl.getChildTransProfitDetailList(busNum, profitDate, agentType);
        List<Object> listadd = new ArrayList<Object>();
        transProfitDetails.stream().forEach(transProfitDetail -> {
            Map<String, Object> map = new HashMap<String, Object>(10);
            map.put("agentType", transProfitDetail.getAgentType());//机构一代
            map.put("agentId", transProfitDetail.getBusNum());
            map.put("agentPid", transProfitDetail.getAgentId());
            map.put("posTranAmt", transProfitDetail.getPosRewardAmt());
            map.put("posJlTranAmt", transProfitDetail.getPosCreditAmt());
            try {
                map = posProfitComputeServiceImpl.execut(map);
                System.out.println(map.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            listadd.add(map);
        });
        System.out.println("+====="+ JSONObject.toJSONString(listadd));
    }

    @Test
    public void computeJGPosreWard() throws Exception {
        List<String> busNum = new ArrayList<String>();
//        busNum.add("O00000000110370");
//        busNum.add("O00000000145380");
//        busNum.add("O00000000218951");
//        busNum.add("O00000000166351");
//        busNum.add("O00000000216760");
//        busNum.add("O00000000112234");
//        busNum.add("O00000000213428");
//        busNum.add("O00000000148391");
//        busNum.add("O00000000118283");
        busNum.add("O00000000160468");
        String profitDate = "201807";
        String agentType = "3";
        List<TransProfitDetail> transProfitDetails = profitDetailMonthServiceImpl.getChildTransProfitDetailList(busNum, profitDate, agentType);
        List<Object> listadd = new ArrayList<Object>();
        transProfitDetails.stream().forEach(transProfitDetail -> {
            Map<String, Object> map = new HashMap<String, Object>(10);
            map.put("agentType", transProfitDetail.getAgentType());//机构一代
            map.put("agentId", transProfitDetail.getBusNum());
            map.put("agentPid", transProfitDetail.getAgentId());
            map.put("posTranAmt", transProfitDetail.getPosRewardAmt());
            map.put("posJlTranAmt", transProfitDetail.getPosCreditAmt());
            try {
                map = posProfitComputeServiceImpl.execut(map);
                System.out.println(map.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            listadd.add(map);
        });
        System.out.println("+====="+ JSONObject.toJSONString(listadd));
    }
}