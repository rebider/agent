package com.ryx.credit.profit.jobs;

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

    @Test
    public void execut() throws Exception {
        toolsDeductJob.execut();
    }

    @Test
    public void computeToolsDeduct() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(10);
        map.put("agentId", "AG20180803000000000005884");
        map.put("paltformNo", "6000");
        map.put("deductDate", "2018-08");
        map.put("agentProfitAmt", "500000");
        map = profitToolsDeductService.executDeduct(map);
        System.out.println("应扣："+map.get("mustDeductionAmtSum"));
        System.out.println("实扣："+map.get("actualDeductionAmtSum"));
    }
}