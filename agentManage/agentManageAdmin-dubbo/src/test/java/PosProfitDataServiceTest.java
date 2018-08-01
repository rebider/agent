import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.service.profit.IPosProfitDataService;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @Author: Zhang Lei
 * @Description: pos分潤数据获取service
 * @Date: 20:44 2018/7/31
 */
public class PosProfitDataServiceTest extends BaseSpringTest {

    @Resource
    IPosProfitDataService posProfitDataService;

    @Test
    public void getPosDateTest() throws Exception {
        AgentResult agentResult = posProfitDataService.getPosProfitDate("201807");
        System.out.println(JSONObject.toJSONString(agentResult));
    }

}
