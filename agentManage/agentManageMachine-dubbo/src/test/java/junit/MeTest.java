package junit;

import com.alibaba.fastjson.JSONArray;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.MposTermBatchVo;
import com.ryx.credit.machine.vo.MposTermTypeVo;
import com.ryx.credit.machine.vo.TermMachineVo;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * 作者：cx
 * 时间：2018/10/10
 * 描述：
 */
public class MeTest extends BaseSpringTest {

    private Logger logger = LoggerFactory.getLogger(MeTest.class);

    @Resource(name = "termMachineService")
    private TermMachineService termMachineService;

    @Test
    public void testqueryTermMachine(){
        try {
            List<TermMachineVo> list =  termMachineService.queryTermMachine(PlatformType.MPOS);
            logger.debug(JSONArray.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testqueryMposTermBatch(){
        try {
            List<MposTermBatchVo> list =  termMachineService.queryMposTermBatch(PlatformType.MPOS);
            logger.debug(JSONArray.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testqueryMposTermType(){
        try {
            List<MposTermTypeVo> list =  termMachineService.queryMposTermType(PlatformType.MPOS);
            logger.debug(JSONArray.toJSONString(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
