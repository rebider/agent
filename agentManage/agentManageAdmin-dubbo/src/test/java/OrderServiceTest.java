import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.service.order.OLogisticsService;
import com.ryx.credit.service.order.OrderRepairService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 作者：cx
 * 时间：2019/5/6
 * 描述：
 */
public class OrderServiceTest  extends BaseSpringTest  {

    private Logger logger = LoggerFactory.getLogger(OrderServiceTest.class);



    @Autowired
    private OLogisticsService oLogisticsService;
    @Autowired
    private OrderRepairService orderRepairService;

    @Test
    public void testIdSn(){
        try {
            List<String> data =  oLogisticsService.idList("00000402G2100385700","00000402G2100385759");
            for (String datum : data) {
                logger.info("sn:{}",datum);
            }

        } catch (MessageException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void repairDumpOrderLogic(){
        try {
            orderRepairService.repairDumpOrderLogic(1,"OO20190215000000000002487");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
