import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.order.IPaymentDetailService;
import com.ryx.internet.pojo.OInternetRenewDetail;
import com.ryx.internet.service.OInternetRenewService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;

/**
 * 作者：cx
 * 时间：2018/12/21
 * 描述：
 */
public class InternetTest extends BaseSpringTest {

    @Autowired
    private OInternetRenewService internetRenewService;

    @Test
    public void test1() throws MessageException {
        Map<String,Object> reqMap = new HashMap<>();
        reqMap.put("month","201907");
        Set<String> agentIdList = new HashSet<>();
        agentIdList.add("AG19073576564");
        reqMap.put("agentIdList",agentIdList);
        AgentResult agentResult = internetRenewService.queryCardProfit(reqMap);
        System.out.println(agentResult.toString());
    }

}
