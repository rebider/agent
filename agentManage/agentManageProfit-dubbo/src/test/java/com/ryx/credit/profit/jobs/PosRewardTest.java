package com.ryx.credit.profit.jobs;

import com.ryx.credit.profit.service.DeductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author zl
 * @create 2018/8/8
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = {"classpath:spring-context.xml", "classpath:spring-mybatis.xml"})
public class PosRewardTest {

    @Autowired
    @Qualifier("posProfitComputeServiceImpl")
    private DeductService posProfitComputeServiceImpl;

    @Test
    public void test1() {

        try {
            posProfitComputeServiceImpl.otherOperate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
