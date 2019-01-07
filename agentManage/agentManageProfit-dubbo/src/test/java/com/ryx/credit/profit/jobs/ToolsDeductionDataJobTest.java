package com.ryx.credit.profit.jobs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 机具扣款数据同步
 * @author zhaodw
 * @create 2018/7/30
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class ToolsDeductionDataJobTest {

    private Logger logger = LoggerFactory.getLogger(ToolsDeductionDataJobTest.class);

    @Autowired
    private ToolsDeductJob toolsDeductJob;

    @Test
    public void testDeal() {
        toolsDeductJob.execut();
    }
}
