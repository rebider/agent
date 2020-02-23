import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.internet.service.OInternetRenewService;
import com.ryx.jobOrder.service.JobOrderTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 作者：cx
 * 时间：2018/12/21
 * 描述：
 */
public class JobOrderTest extends BaseSpringTest {

    @Autowired
    private JobOrderTaskService jobOrderTaskService;

    @Test
    public void test1() throws MessageException {
        Map<String,Object> reqMap = new HashMap<>();
        reqMap.put("month","201907");
        Set<String> agentIdList = new HashSet<>();
        agentIdList.add("AG19073576564");
        reqMap.put("agentIdList",agentIdList);
    }

}
