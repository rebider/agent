import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.service.order.OLogisticsService;
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

    @Test
    public void testIdSn(){
        try {
            List<String> data =  oLogisticsService.idList("1955AA9Mghj00a09","1955AA9Mghj00a01");
            logger.info(data.toString());
        } catch (MessageException e) {
            e.printStackTrace();
        }
    }
}
