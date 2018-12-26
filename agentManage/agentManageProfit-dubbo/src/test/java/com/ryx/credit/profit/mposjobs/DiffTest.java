package com.ryx.credit.profit.mposjobs;

import com.ryx.credit.profit.unitmain.DailyProfitMposDataJob;
import com.ryx.credit.profit.unitmain.ProfitMposDiffDataJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Author: Zhang Lei
 * @Description: 手刷补差同步测试
 * @Date: 16:12 2018/12/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = {"classpath:spring-context.xml", "classpath:spring-mybatis.xml"})
public class DiffTest {

    private Logger logger = LoggerFactory.getLogger(DiffTest.class);

    @Autowired
    private ProfitMposDiffDataJob profitMposDiffDataJob;

    @Test
    public void testDeal() {
        profitMposDiffDataJob.excute("201810");
    }
}
