package com.ryx.credit.profit.service.impl;

import com.ryx.credit.profit.service.ProfitSupplyTaxService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class SupplyTaxTest {
    @Autowired
    ProfitSupplyTaxService profitSupplyTaxService;

    @Test
    public void test() {
        Map<String,Object> map = new HashMap<>();
        profitSupplyTaxService.taxSupplyComputer(map);
    }


}