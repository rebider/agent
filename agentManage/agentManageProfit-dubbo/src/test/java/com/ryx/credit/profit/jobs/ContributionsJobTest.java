package com.ryx.credit.profit.jobs;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 缴纳款（保证金）数据同步
 * @Author chenqiutian
 * @Create 2019/1/25
 */
@RunWith(SpringJUnit4ClassRunner.class)
//加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class ContributionsJobTest {

    private Logger logger = LoggerFactory.getLogger(ContributionsJobTest.class);

    @Autowired
    private ContributionsJob contributionsJob;

    @Test
    public void test(){
        contributionsJob.getEarnestMoney();
        logger.info("获取完毕");
    }

}