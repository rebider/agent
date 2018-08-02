import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.pojo.admin.agent.PayComp;
import com.ryx.credit.pojo.admin.order.OPaymentDetail;
import com.ryx.credit.service.dict.PayCompService;
import com.ryx.credit.service.order.IPaymentDetailService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangmx
 * @desc
 */
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
        List<OPaymentDetail> oPaymentList = new ArrayList<OPaymentDetail>();
        OPaymentDetail oPaymentDetail = new OPaymentDetail();
        //id,订单id，srcId,实际付款进行
        oPaymentDetail.setId("OPD2018073100000000000148");
        oPaymentDetail.setOrderId("OPA2018073100000000000076");
        oPaymentDetail.setSrcId("111");
        oPaymentDetail.setRealPayAmount(new BigDecimal(123));

        OPaymentDetail oPaymentDetail1 = new OPaymentDetail();
        //id,订单id，srcId,实际付款进行
        oPaymentDetail1.setId("OPD2018073100000000000149");
        oPaymentDetail1.setOrderId("OPA2018073100000000000076");
        oPaymentDetail1.setSrcId("222");
        oPaymentDetail1.setRealPayAmount(new BigDecimal(500));
        oPaymentList.add(oPaymentDetail1);
        oPaymentList.add(oPaymentDetail);
        ResultVO resultVO = paymentDetailService.uploadStatus(oPaymentList);
        System.out.println(resultVO);
    }

}