package junit.profit;/**
 * @Auther: zhaodw
 * @Date: 2018/8/8 20:47
 * @Description:
 */

import com.ryx.credit.profit.jobs.ProfitAmtSumJob;
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
public class ProfitAmtSumJobTest {

    @Autowired
    private ProfitAmtSumJob profitAmtSumJob;

    @Test
    public void testSum() {
        profitAmtSumJob.deal();
    }
}
