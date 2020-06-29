import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotations.TableId;
import com.ryx.credit.common.enumc.DictGroup;
import com.ryx.credit.common.enumc.OrgType;
import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.ResultVO;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.dao.order.CashSummaryMouthMapper;
import com.ryx.credit.pojo.admin.COrganization;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.pojo.admin.agent.DateChangeRequest;
import com.ryx.credit.pojo.admin.agent.Dict;
import com.ryx.credit.pojo.admin.agent.PlatForm;
import com.ryx.credit.pojo.admin.order.CashSummaryMouth;
import com.ryx.credit.pojo.admin.vo.AgentNotifyVo;
import com.ryx.credit.service.agent.*;
import com.ryx.credit.service.dict.DepartmentService;
import com.ryx.credit.service.dict.DictOptionsService;
import com.ryx.credit.service.dict.IdService;

import com.ryx.credit.service.order.OrderService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


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
    @Autowired
    private PlatFormService platFormService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private AgentService agentService;


    @Test
    public void testId(){
        logger.info("=======testId=====");
        logger.info("======="+idService.genId(TabId.a_agent));
        logger.info("=======testId=====");
    }

    @Test
    public void testValueList(){
        List<String> actCodeList = dictOptionsService.dictValueList("AGENT", "ACTCODE");
        logger.info("=======testValueList====="+ actCodeList.contains("2004"));
        logger.info("=======testValueList=2===="+ actCodeList.toString());
    }

    @Test
    public void testOptions(){
        logger.info("=======testOptions=====");
        List<Dict> list =  dictOptionsService.dictList("AGENT","CAPITAL_TYPE");
        logger.info("=======testOptions====="+ JSONObject.toJSONString(list));
        try {
            List<Map<String, Object>> mapList = agentService.queryAgentColinfoInfo("AG20043764018");
            logger.info("=======testOptions====="+ JSONObject.toJSONString(mapList));
            AgentResult agentResult = agentService.queryAgentStatusInfo("AG20043764018");
            logger.info("=======testOptions====="+ JSONObject.toJSONString(agentResult.getMapData()));
        } catch (MessageException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAgent(){
        logger.info("=======testAgent=====");
        try {
            List<Map<String, Object>> mapList = agentService.queryAgentColinfoInfo("AG20043764018");
            logger.info("=======testAgent====="+ JSONObject.toJSONString(mapList));
            AgentResult agentResult = agentService.queryAgentStatusInfo("AG20043764018");
            logger.info("=======testAgent====="+ JSONObject.toJSONString(agentResult.getMapData()));
        } catch (MessageException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOptionsaaa(){
        // 查询代理商下的业务有几个省区,对应几个大区,多个省区多个审批,传多个省区、大区参数
        // 查询代理商下有哪些业务平台,只有瑞+,参数传"liuyanli";没有瑞+,参数"market1";包含瑞+和其他平台,参数传"liuyanli","market1"
        logger.info("=======testOptionsaaa=====");
        String agent_id = "AG20023754679";
        List<String> stringType = new ArrayList<String>();
        List<String> stringPro = new ArrayList<String>();
        List<String> stringDis = new ArrayList<String>();
        List<Dict> dictList_one = dictOptionsService.dictList(DictGroup.DATA_CHANGE.name(), DictGroup.MARKET_ONE.name());
        List<Dict> dictList_two = dictOptionsService.dictList(DictGroup.DATA_CHANGE.name(), DictGroup.MARKET_TWO.name());
        List<AgentBusInfo> agentBusInfoList = agentBusinfoService.agentBusInfoList(agent_id);
        for (AgentBusInfo agentBusInfo : agentBusInfoList) {
            PlatForm platForm = platFormService.selectByPlatformNum(agentBusInfo.getBusPlatform());
            if (platForm.getPlatformType().equals("RJPOS") || platForm.getPlatformType().equals("RJQZ")) {
                stringType.add(dictList_two.get(0).getdItemvalue());
            } else {
                stringType.add(dictList_one.get(0).getdItemvalue());
            }
            COrganization org_pro = departmentService.getById(agentBusInfo.getAgDocPro());
            stringPro.add(org_pro.getCode());
            COrganization org_dis = departmentService.getById(agentBusInfo.getAgDocDistrict());
            stringDis.add(org_dis.getCode());
        }
        ArrayList<String> arrayType = new ArrayList<>(new HashSet<>(stringType));
        if (arrayType.size() == 0) {
            logger.info("代理商启动审批异常，未获取到市场部审批参数{}", JSONObject.toJSON(agentBusInfoList));
        }
        ArrayList<String> arrayPro = new ArrayList<>(new HashSet<>(stringPro));
        if (arrayPro.size() == 0) {
            logger.info("代理商启动审批异常，未获取到省区审批参数{}", JSONObject.toJSON(agentBusInfoList));
        }
        ArrayList<String> arrayDis = new ArrayList<>(new HashSet<>(stringDis));
        if (arrayDis.size() == 0) {
            logger.info("代理商启动审批异常，未获取到大区审批参数{}", JSONObject.toJSON(agentBusInfoList));
        }
        logger.info("makUserList:{},proList:{},docList:{}", arrayType, arrayPro, arrayDis);

        FastMap.fastMap("makUserList", arrayType) // 市场部审批人参数
                .putKeyV("proList", arrayPro) // 省区审批人部门代码
                .putKeyV("docList", arrayDis) // 大区审批人部门代码
                .putKeyV("rejectCount","0"); // 拒绝数量，初始值0，有拒绝就置为1
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
