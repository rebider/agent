package junit.profit;/**
 * @Auther: zhaodw
 * @Date: 2018/7/30 14:59
 * @Description:
 */

import com.ryx.credit.profit.jobs.ProfitDataJob;
import com.ryx.credit.profit.jobs.RefundJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 退单任务测试
 * @author zhaodw
 * @create 2018/7/30
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class ProfitDataJobTest {

    private Logger logger = LoggerFactory.getLogger(ProfitDataJobTest.class);

    @Autowired
    private ProfitDataJob profitDataJob;

    /*** 
    * @Description: 测试任务处理
    * @Param:  
    * @return:  
    * @Author: zhaodw 
    * @Date: 2018/7/30 
    */
    @Test
    public void testDeal() {
        profitDataJob.deal();
    }
}
