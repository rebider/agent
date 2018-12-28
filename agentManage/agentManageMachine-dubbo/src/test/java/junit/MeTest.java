package junit;

import com.alibaba.fastjson.JSONArray;
import com.ryx.credit.common.enumc.PlatformType;
import com.ryx.credit.machine.service.TermMachineService;
import com.ryx.credit.machine.vo.*;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.order.OOrder;
import com.ryx.credit.pojo.admin.order.OReturnOrderDetail;
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

    @Test
    public void testadjustmentMachine(){
        try {

            AdjustmentMachineVo vo = new AdjustmentMachineVo();
            vo.setOptUser("1");
            vo.setSnStart("8850003000000000");
            vo.setSnEnd("8850003000000019");
            //发货订单的业务编号
            vo.setNewBusNum("50000015");
            //退货订单的业务编号
            vo.setOldBusNum("50000001");
            vo.setPlatformNum("5000");
            vo.setSnNum("2");
            logger.debug(JSONArray.toJSONString(termMachineService.adjustmentMachine(vo)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testchangeActMachine(){
        try {

            ChangeActMachineVo changeActMachineVo = new ChangeActMachineVo();
            changeActMachineVo.setBusNum("50000001");
            changeActMachineVo.setSnStart("8850003000000000");
            changeActMachineVo.setSnEnd("8850003000000019");
            changeActMachineVo.setPlatformType("MPOS");
            changeActMachineVo.setOldAct("1005");
            changeActMachineVo.setNewAct("1004");
            changeActMachineVo.setSnNum("20");
            logger.debug(JSONArray.toJSONString(termMachineService.changeActMachine(changeActMachineVo)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
