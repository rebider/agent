package com.ryx.credit.profit.service.impl;

import com.ryx.credit.profit.service.PosRewardTemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * @author yangmx
 * @desc
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class PosRewardTemplateServiceImplTest {

    @Autowired
    PosRewardTemplateService posRewardTemplateService;

    @Test
    public void computePosReward() throws Exception {
        BigDecimal bigDecimal = new BigDecimal("50000000");
        BigDecimal pro = posRewardTemplateService.computePosReward(bigDecimal);
        System.out.println("=========================:"+pro);
    }

}