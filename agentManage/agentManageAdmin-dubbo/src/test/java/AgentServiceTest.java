import com.ryx.credit.service.agent.AgentNotifyService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 作者：cx
 * 时间：2019/3/26
 * 描述：
 */
public class AgentServiceTest extends BaseSpringTest  {
    private Logger logger = LoggerFactory.getLogger(AgentServiceTest.class);
    @Autowired
    private AgentNotifyService agentNotifyService;

    @Test
    public void testNotify(){
        agentNotifyService.asynNotifyPlatform();
//        try {
//            Thread.currentThread().sleep(2000000000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
}
