import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.service.profit.IPosProfitDataService;
import com.ryx.credit.service.profit.PosOrganDataService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Author: Zhang Lei
 * @Description: pos分潤数据获取service
 * @Date: 20:44 2018/7/31
 */
public class PosOrgDataServiceTest extends BaseSpringTest {

    @Resource
    PosOrganDataService posOrganDataService;

    @Test
    public void getPosDateTest() throws Exception {
        AgentResult agentResult = posOrganDataService.getAllChild("O00000000220096");
        System.out.println(JSONObject.toJSONString(agentResult));
    }

}
