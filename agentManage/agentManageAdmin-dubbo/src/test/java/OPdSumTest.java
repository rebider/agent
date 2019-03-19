import com.ryx.credit.task.OPdSumCreateTask;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * chenliang
 * 2019/3/18
 */

public class OPdSumTest extends BaseSpringTest {

    private Logger logger = LoggerFactory.getLogger(OPdSumTest.class);

    @Resource
    OPdSumCreateTask oPdSumCreateTask;
    @Test
    public void test() throws Exception {
        oPdSumCreateTask.CreateOPdSum();
    }



}
