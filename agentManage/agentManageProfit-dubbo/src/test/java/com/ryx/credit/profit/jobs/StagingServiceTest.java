package com.ryx.credit.profit.jobs;

import com.ryx.credit.profit.pojo.ProfitStaging;
import com.ryx.credit.profit.service.StagingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class StagingServiceTest {

    private Logger  logger = LoggerFactory.getLogger(StagingServiceTest.class);

    @Autowired
    private StagingService stagingServiceImpl;


    @Test
    public void testAdd(){
        ProfitStaging staging = new ProfitStaging();
        staging.setSourceId("2");
        staging.setSumAmt(new BigDecimal("10000"));
        staging.setStagCount(new BigDecimal("2"));
        staging.setUserId("524");
        stagingServiceImpl.addStaging(staging,"",null);
    }
    


}
