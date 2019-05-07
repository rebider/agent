import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.dao.order.OLogisticsDetailMapper;
import com.ryx.credit.dao.order.ReceiptPlanMapper;
import com.ryx.credit.service.agent.AgentNotifyService;
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
    private AgentNotifyService agentNotifyService;
    @Autowired
    private OLogisticsDetailMapper oLogisticsDetailMapper;
    @Autowired
    private OLogisticsService oLogisticsService;
    @Autowired
    private ReceiptPlanMapper receiptPlanMapper;

    @Test
    public void testNotify(){
//        agentNotifyService.asynNotifyPlatform();
//        try {
//            Thread.currentThread().sleep(2000000000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        int count  = oLogisticsDetailMapper.querySnCount(FastMap.fastMap("snBegin","ET0001")
//                .putKeyV("snEnd","ET5000")
//        .putKeyV("status",1)
//        .putKeyV("recordStatus","1"));
//        System.out.println("count");
//        oLogisticsService.isInSnSegment("SNM123","SNM124","SNM121","SNj125");
        List<Map<String,Object>> data = receiptPlanMapper.getReceipPlanList(FastMap.fastMap("PLAN_NUM","ORP20181126000000000000843"));
        System.out.println(data);
    }
}
