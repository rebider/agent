package com.ryx.credit.profit.jobs;

import com.ryx.credit.profit.service.impl.PosProfitComputeServiceImpl;
import com.ryx.credit.profit.service.impl.ProfitToolsDeductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

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

    @Test
    public void execut() throws Exception {
        toolsDeductJob.execut();
    }

    @Test
    public void computeToolsDeduct() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(10);
        map.put("agentPid", "AG20180816000000000006084"); //AG20180813000000000006020
        map.put("paltformNo", "2000");        //平台编号
        map.put("deductDate", "2018-09");       //扣款月份
        map.put("agentProfitAmt", "777777");        //代理商分润
        map = profitToolsDeductService.execut(map);
        System.out.println("应扣："+map.get("mustDeductionAmtSum"));//返回应扣金额
        System.out.println("实扣："+map.get("actualDeductionAmtSum"));//实扣金额
    }

    @Test
    public void computePosreWard() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(10);
//        map.put("agentType", "3");//机构一代
//        map.put("agentId", "O00000000106311");
//        map.put("agentPid", "AG20180726000000000003327");
//        map.put("posTranAmt", "21100000");
//        map.put("posJlTranAmt", "21100000");

        map.put("agentType", "2");//机构
        map.put("agentId", "O00000000000607");
        map.put("agentPid", "AG20180726000000000003321");
        map.put("posTranAmt", "70000000");
        map.put("posJlTranAmt", "50000000");

//        map.put("agentType", "6");//标准一代
//        map.put("agentId", "O00000000138599");
//        map.put("agentPid", "AG20180726000000000002353");
//        map.put("posTranAmt", "60000000");
//        map.put("posJlTranAmt", "60000000");

        map = posProfitComputeServiceImpl.execut(map);
        System.out.println(map.toString());
    }
}