import com.ryx.credit.common.util.DateUtils;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.service.ActivityService;
import com.ryx.credit.service.order.IPaymentDetailService;
import com.ryx.credit.service.order.ORemoveAccountService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者：cx
 * 时间：2018/12/21
 * 描述：
 */
public class ActivityServiceTest extends BaseSpringTest {

    @Autowired
    private ActivityService activityService;
    @Autowired
    private IPaymentDetailService paymentDetailService;
    @Autowired
    private ORemoveAccountService oRemoveAccountService;

    @Test
    public void a(){
        List<String> list = new ArrayList<>();
        list.add("ORA20191008000000000000142");

        oRemoveAccountService.processData(list);
    }

    @Test
    public void testInstance(){
        activityService.createDeloyFlow(null,"process_agent_relation",null,null, FastMap.fastMap("part","south"));
    }


    @Test
    public void testComplet(){

        activityService.completeTaskInNer("1700008",FastMap.fastMap("rs","pass")
        .putKeyV("approvalOpinion","approvalOpinion")
                .putKeyV("approvalPerson","1")
                .putKeyV("createTime", DateUtils.beforeDate(new Date())));
        activityService.completeTaskInNer("1697520",FastMap.fastMap("rs","reject")
                .putKeyV("approvalOpinion","approvalOpinion")
                .putKeyV("approvalPerson","1")
                .putKeyV("createTime", DateUtils.beforeDate(new Date())));
    }


    @Test
    public void test(){

        paymentDetailService.getSumDebt("AG20190129000000000016401");
    }


}
