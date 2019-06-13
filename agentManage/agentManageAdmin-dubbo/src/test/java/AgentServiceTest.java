import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.dao.order.OLogisticsDetailMapper;
import com.ryx.credit.dao.order.ReceiptPlanMapper;
import com.ryx.credit.service.agent.AgentNotifyService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.order.OLogisticsService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 作者：cx
 * 时间：2019/3/26
 * 描述：
 */
public class AgentServiceTest extends BaseSpringTest  {
    private Logger logger = LoggerFactory.getLogger(AgentServiceTest.class);
    @Autowired
    private OLogisticsDetailMapper oLogisticsDetailMapper;
    @Autowired
    private OLogisticsService oLogisticsService;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;
    @Autowired
    private AgentService agentService;

    @Test
    public void testNotify(){
        agentService.createAgentAccount();
    }
}
