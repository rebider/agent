package com.ryx.credit.profit.service.impl;

import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.service.ProfitMonthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yangmx
 * @desc
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class ProfitMonthServiceImplTest {

    @Autowired
    ProfitMonthService profitMonthService;
    @Autowired
    private ProfitDetailMonthMapper profitDetailMonthMapper;

    @Test
    public void testPayMoney() throws Exception {
        profitMonthService.testComputeProfitAmt();
    }

    /*@Test
    public void testPayMoney() throws Exception {
        profitMonthService.computeProfitAmt();
    }*/

//    @Test
//    public void testDept() throws Exception {
//        List<String> lis = profitDetailMonthMapper.getDistrictAgent("200");
//        lis.forEach(s -> System.out.println(s));
//    }
//
//    @Test
//    public void testPro() throws Exception {
//        List<String> lis = profitDetailMonthMapper.getProAgent("210");
//        lis.forEach(s -> System.out.println(s));
//    }

    /*@Test
    public void testDoKhDeductino() throws Exception {
        ProfitDetailMonth p = new ProfitDetailMonth();
        p.setProfitDate("201812");
        p.setAgentId("AG20181121000000000012621");
        p.setParentAgentId("AG20181121000000000011648");
        ProfitDetailMonth profitDetailMonth = profitDetailMonthMapper.selectByIdAndParent(p);
        profitMonthService.doKhDuction(profitDetailMonth,new BigDecimal("100000"),"2");
    }*/
}