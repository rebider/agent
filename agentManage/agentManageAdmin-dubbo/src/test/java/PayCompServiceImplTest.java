import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.service.dict.PayCompService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.*;


public class PayCompServiceImplTest extends BaseSpringTest {
    @Autowired
    private PayCompService payCompService;
    @Autowired
    private IPaymentDetailService paymentDetailService;

    @Test
    public void insertPayComp() throws Exception {
        PayComp paycomp = new PayComp();
        paycomp.setId("9");
        paycomp.setComName("单元测试信息技术有限公司");
        paycomp.setcTime(new Date());
        paycomp.setcUtime(new Date());
        paycomp.setRemark("单元测试");
        paycomp.setStatus("1");
        payCompService.insertPayComp(paycomp);
    }

    @Test
    public void getPayCompList() throws Exception {
        PayComp paycomp = new PayComp();
        // paycomp.setComName("单元测试信息技术有限公司");
//        paycomp.setStatus("1");
//        List<PayComp> payComps = payCompService.getPayCompList(paycomp);
//        payComps.forEach(PayComp -> System.out.println(PayComp.getComName()));
    }

    @Test
    public void updatePayComp() throws Exception {
        PayComp paycomp = new PayComp();
        paycomp.setId("9");
        paycomp.setComName("单元测试信息技术有限公司");
        paycomp.setcTime(new Date());
        paycomp.setcUtime(new Date());
        paycomp.setRemark("单元测试信息技术有限公司");
        paycomp.setStatus("2");
        payCompService.updatePayComp(paycomp);
    }

    @Test
    public void update() {
        Map map = new HashMap<String, String>();
        map.put("detailId", "OPD2018073100000000000154");
        map.put("srcId", "OPA2018072500000000000055");
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(map);
        ResultVO resultVO = paymentDetailService.uploadStatus(list);
        System.out.println(resultVO);
    }

}
