package com.ryx.credit.profit.jobs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author zhaodw
 * @create 2018/8/8
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class PosSumJobTest {

    @Autowired
    private NewProfitDataJob newProfitDataJob;

    @Test
    public void testSum() {
        newProfitDataJob.doSum("201811");
    }
}
