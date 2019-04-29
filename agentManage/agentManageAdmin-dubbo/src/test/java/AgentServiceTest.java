import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.dao.order.OLogisticsDetailMapper;
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
    @Autowired
    private OLogisticsDetailMapper oLogisticsDetailMapper;

    @Test
    public void testNotify(){
//        agentNotifyService.asynNotifyPlatform();
//        try {
//            Thread.currentThread().sleep(2000000000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        int count  = oLogisticsDetailMapper.querySnCount(FastMap.fastMap("snBegin","ET0001")
                .putKeyV("snEnd","ET5000")
        .putKeyV("status",1)
        .putKeyV("recordStatus","1"));
        System.out.println("count");
    }
}
