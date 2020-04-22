import com.ryx.credit.common.enumc.NotifyType;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.dao.order.OLogisticsDetailMapper;
import com.ryx.credit.dao.order.ReceiptPlanMapper;
import com.ryx.credit.service.agent.AgentDebitCardFileService;
import com.ryx.credit.service.agent.AgentNotifyService;
import com.ryx.credit.service.agent.AgentService;
import com.ryx.credit.service.agent.DataChangeActivityService;
import com.ryx.credit.service.agent.netInPort.AgentNetInNotityService;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.credit.service.order.TerminalTransferService;
import com.ryx.credit.service.pay.LivenessDetectionService;
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
    @Autowired
    private AgentNetInNotityService agentNetInNotityService;
    @Autowired
    private TerminalTransferService terminalTransferService;
    @Autowired
    private DataChangeActivityService dataChangeActivityService;
    @Autowired
    private AgentDebitCardFileService agentDebitCardFileService;


    @Test
    public void export() throws Exception {
        agentDebitCardFileService.exportsForZHposOrPlus();
    }
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
    @Test
    public void posss(){
        try {
            agentNetInNotityService.asynNotifyPlatform("AB2019062v000000000020757", NotifyType.NetInEdit.getValue());
            Thread.currentThread().sleep(100000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private LivenessDetectionService livenessDetectionService;

    @Test
    public void testThree(){
        try {
            Map<String, Object> data =  terminalTransferService.disposeSN("000024026311890023316094","000024026311890023316094");
            System.out.println(data);
         } catch (MessageException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void adjustFinanceOrgByAccount(){
        try {
            dataChangeActivityService.adjustFinanceOrgByAccount("AG20013748164");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
