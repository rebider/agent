package com.ryx.credit.profit.service.impl;

import com.ryx.credit.profit.service.PosRewardTemplateService;
import com.ryx.credit.profit.service.ProfitMonthService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

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

    @Test
    public void testCompute() throws Exception {
        profitMonthService.computeProfitAmt();
    }

}