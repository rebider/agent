import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotations.TableId;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.CashSummaryMouthMapper;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.DateChangeRequest;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.order.CashSummaryMouth;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.credit.service.agent.AgentQueryService;
import com.ryx.credit.service.agent.AimportService;
import com.ryx.credit.service.agent.DateChangeReqService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;

import com.ryx.credit.service.order.OrderService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by cx on 2018/5/22.
 */
public class DictServiceTest extends BaseSpringTest {

    private Logger  logger = LoggerFactory.getLogger(DictServiceTest.class);

    @Autowired
    private IdService idService;
    @Autowired
    private DictOptionsService dictOptionsService;

    @Autowired
    private DateChangeReqService dateChangeReqService;

    @Autowired
    private AgentBusinfoService agentBusinfoService;

    @Autowired
    private AimportService aimportService;
    @Autowired
    private AgentQueryService agentQueryService;
    @Autowired
    private CashSummaryMouthMapper cashSummaryMouthMapper;
    @Autowired
    private OrderService orderService;
    
    @Test
    public void testId(){
        logger.info("=======testId=====");
        logger.info("======="+idService.genId(TabId.a_agent));
        logger.info("=======testId=====");
    }

    @Test
    public void testOptions(){
        logger.info("=======testOptions=====");
        List<Dict> list =  dictOptionsService.dictList("AGENT","CAPITAL_TYPE");
        logger.info("=======testOptions====="+ JSONObject.toJSONString(list));
    }
    


    @Test
    public void testDataChange(){
        logger.info("=======testDataChange=====");
        DateChangeRequest data = dateChangeReqService.getById("DC20180606000000000000022");
        logger.info("=======testDataChange====="+data);
    }


    @Test
    public void testImPortAgent(){
        logger.info("=======testImPortAgent=====");
        try {
          ResultVO data = aimportService.analysisRecode("1");
          logger.info(data.getResInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("=======testImPortAgent=====");
    }

    @Test
    public void testParent(){
        logger.info("=======testImPortAgent=====");
        try {
            List<AgentBusInfo> d= agentBusinfoService.queryParenFourLevel(new ArrayList<AgentBusInfo>(),"100007","AG20180615000000000000300");
            logger.info("======="+JSONObject.toJSONString(d)+"=====");
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("=======testImPortAgent=====");
    }

    @Test
    public void testChildLevel(){
        List<AgentBusInfo> list  = new ArrayList<>();
        list = agentBusinfoService.queryChildLevelByBusNum(list,"100003","O00000000151369");
        System.out.println("==================="+ JSONArray.toJSONString(list));
        System.out.println(list.size());
    }
    @Test
    public void loadCach(){
        agentQueryService.loadCach();
        try {
            Thread.currentThread().sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testSelectCashSummaryMouthData(){

      List<CashSummaryMouth> res =  cashSummaryMouthMapper.selectCashSummaryMouthData("201811","0");
        for (CashSummaryMouth re : res) {
            if(null==cashSummaryMouthMapper.selectByPrimaryKey(re)){
                cashSummaryMouthMapper.insertSelective(re);
            }
        }
      logger.info("======="+JSONObject.toJSONString(res));

    }

    @Test
    public void notifyBean() {
    }

    @Test
    public void testRepr(){
        orderService.testRepeatableRead();
    }

}
