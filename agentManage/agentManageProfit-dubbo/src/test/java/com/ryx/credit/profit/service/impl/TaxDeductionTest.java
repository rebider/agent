package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.dao.TransProfitDetailMapper;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.service.order.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class TaxDeductionTest {
    @Autowired
    TaxDeductionServiceImpl taxDeductionService;

    @Test
    public void test() throws Exception {
        Map<String,Object> map = new HashMap<>();
        taxDeductionService.taxDeductionComputer();
    }


}