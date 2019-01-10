import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.service.profit.PosOrganDataService;
import com.ryx.credit.task.CashSummaryMouthDataTask;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Author: Zhang Lei
 * @Description: pos分潤数据获取service
 * @Date: 20:44 2018/7/31
 */
public class CashSummaryMonthDataTaskTest extends BaseSpringTest {

    @Resource
    CashSummaryMouthDataTask cashSummaryMouthDataTask;

    @Test
    public void test() throws Exception {
        cashSummaryMouthDataTask.CashSummaryMouth();
    }

}
