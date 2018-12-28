package com.ryx.credit.profit.service.impl;

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
public class InvoiceTest {
    @Autowired
    OwnInvoiceServiceImpl ownInvoiceService;

    @Test
    public void test() throws Exception {
        Map<String,Object> map = new HashMap<>();
        ownInvoiceService.invoiceOwnComputer(map);
    }


}