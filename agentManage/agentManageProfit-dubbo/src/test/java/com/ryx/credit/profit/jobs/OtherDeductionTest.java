package com.ryx.credit.profit.jobs;/**
 * @Auther: zhaodw
 * @Date: 2018/7/30 14:59
 * @Description:
 */

import com.ryx.credit.profit.service.ProfitDeductionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 退单任务测试
 * @author zhaodw
 * @create 2018/7/30
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class OtherDeductionTest {

    private Logger logger = LoggerFactory.getLogger(OtherDeductionTest.class);

    @Autowired
    private ProfitDeductionService profitDeductionService;

    /*** 
    * @Description: 测试其他扣款
    * @Param:  
    * @return:  
    * @Author: zhaodw 
    * @Date: 2018/7/30 
    */
    @Test
    public void testDeduction() {
        Map<String, Object> deductionMap = new HashMap<>(5);
        deductionMap.put("profitAmt", new BigDecimal("100"));
        deductionMap.put("bussType","02");
        deductionMap.put("agentPid", "JS00000116-Z00000201");
        deductionMap.put("parentAgentPid", "AG20180726000000000002759");
        profitDeductionService.settleErrDeduction(deductionMap);
//        profitDeductionService.settleErrDeduction(new BigDecimal("0.5"),"02","AG20180809000000000005966");
//        profitDeductionService.settleErrDeduction(new BigDecimal("4"),"02","AG201808090000000000059661");
//        profitDeductionService.settleErrDeduction(new BigDecimal("20"),"02","AG20180813000000000006020");
//        profitDeductionService.settleErrDeduction(new BigDecimal("25"),"02","AG20180809000000000005961");
//        profitDeductionService.settleErrDeduction(new BigDecimal("50"),"02","AG20180811000000000006000");
//        profitDeductionService.otherDeductionByType(new BigDecimal("100.22"),"AG20180803000000000005884",null );
//        profitDeductionService.otherDeductionByType(new BigDecimal("175.42"),"AG20180804000000000005901",null );
//        profitDeductionService.otherDeductionByType(new BigDecimal("300.33"),"AG20180809000000000005961",null );
////        profitDeductionService.otherDeductionByType(new BigDecimal("200.22"),"AG20180808000000000005960",null );
//        profitDeductionService.otherDeductionByType(new BigDecimal("100.33"),"AG20180813000000000006020",null );
//        profitDeductionService.otherDeductionByType(new BigDecimal("83.67"),"AG20180809000000000005962111",null );
    }
}
