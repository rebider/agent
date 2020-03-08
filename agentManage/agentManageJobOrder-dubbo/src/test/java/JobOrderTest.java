import com.ryx.credit.common.exception.MessageException;
import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.FastMap;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.service.agent.AgentBusinfoService;
import com.ryx.internet.service.OInternetRenewService;
import com.ryx.jobOrder.pojo.JoCustomKey;
import com.ryx.jobOrder.pojo.JoKeyManage;
import com.ryx.jobOrder.pojo.JoTask;
import com.ryx.jobOrder.service.JobOrderAuthService;
import com.ryx.jobOrder.service.JobOrderManageService;
import com.ryx.jobOrder.service.JobOrderStartService;
import com.ryx.jobOrder.service.JobOrderTaskService;
import com.ryx.jobOrder.vo.JoTaskVo;
import com.ryx.jobOrder.vo.JobKeyManageNodeVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLOutput;
import java.util.*;

/**
 * 作者：cx
 * 时间：2018/12/21
 * 描述：
 */
public class JobOrderTest extends BaseSpringTest {

    @Autowired
    private JobOrderTaskService jobOrderTaskService;
    @Autowired
    private JobOrderStartService jobOrderStartService;
    @Autowired
    private JobOrderManageService jobOrderManageService;
    @Autowired
    private JobOrderAuthService jobOrderAuthService;

    @Test
    public void test1() throws MessageException {
        Map<String,Object> reqMap = new HashMap<>();
        reqMap.put("month","201907");
        Set<String> agentIdList = new HashSet<>();
        agentIdList.add("AG19073576564");
        reqMap.put("agentIdList",agentIdList);
    }

    @Test
    public void test2() throws MessageException {
//        List list = jobOrderManageService.queryKeywordByJoStatus("0");// TODO 优化 字符串
//        System.out.println(list);
    }
    @Test
    public void test3() throws MessageException {
        List<Map> list3 = jobOrderManageService.selectLevel();
        List posFirList = new ArrayList();
        String firKey = "";
        int i = 0;
        for (Map map : list3){
            if(i == 0){
                firKey = (String)map.get("JOKEYONE");
                Map firMap = FastMap.fastMap("key", firKey)
                        .putKeyV("id",(String)map.get("joid"))
                        .putKeyV("value",(String)map.get("jokeynameone"));
            }

        }
    }

    @Test
    public void test4() throws MessageException {
        JoKeyManage jokeymanage = jobOrderManageService.queryKeywordDialog("POS_JY_CXWDZ");
//        agentBusinfoService.agentBusByDict("AG20181121000000000012756", "5593", "JOB","POS");
        System.out.println(jokeymanage);
    }

    @Test
    public void test6() throws MessageException {
        JoCustomKey joKeyManage = new JoCustomKey();
        joKeyManage.setJoSecondKeyNum("POS_JY_CXWDZ");
        List jokeymanage = jobOrderManageService.selectCustomListMapBySedType(joKeyManage);
//        agentBusinfoService.agentBusByDict("AG20181121000000000012756", "5593", "JOB","POS");
        System.out.println(jokeymanage);
    }

    @Test
    public void test5() throws MessageException {
        JoKeyManage toplevel = new JoKeyManage();
        toplevel.setJoKeyType("0");
        List<JoKeyManage> manage = jobOrderManageService.queryKeywordByJoStatus(toplevel);
        List all= new ArrayList();
        for(JoKeyManage joKeyManage: manage){
            // 获取下级所有
            JoKeyManage oneLevel = new JoKeyManage();
            oneLevel.setJoKeyType("1");
            oneLevel.setJoKeyBackNum(joKeyManage.getId());
            List topList = new ArrayList();
            List<JoKeyManage> firList = jobOrderManageService.queryKeywordByJoStatus(oneLevel);
            for(JoKeyManage firKeyMange: firList){
                // 获取下级所有
                JoKeyManage twoLevel = new JoKeyManage();
                twoLevel.setJoKeyType("2");
                twoLevel.setJoKeyBackNum(firKeyMange.getId());
                List firLists = new ArrayList();
                List<JoKeyManage> sedList = jobOrderManageService.queryKeywordByJoStatus(twoLevel);
                firLists.add(FastMap.fastMap("key",firKeyMange.getJoKey())
                        .putKeyV("id",firKeyMange.getId())
                        .putKeyV("value",firKeyMange.getJoKeyName())
                        .putKeyV("next",sedList));
                topList.add(FastMap.fastMap("key",joKeyManage.getJoKey())
                        .putKeyV("id",joKeyManage.getId())
                        .putKeyV("value",joKeyManage.getJoKeyName())
                        .putKeyV("next",firLists));

            }
            if(topList.size() != 0){
                all.add(topList);
            }
        }
        System.out.println(all);
    }
    @Test
    public void test7() throws MessageException {
        JoTaskVo joTaskVo = new JoTaskVo();
        joTaskVo.setId("JT20200226000000000000003");
//        joTaskVo.setJoTaskId("JT20200226000000000000003");
        Page page = new Page();
        PageInfo pageInfo = jobOrderTaskService.queryJobOrderTaskVo(joTaskVo, page);
        System.out.println("--------------------");
    }

    @Test
    public void test8() throws MessageException {
        JoTask joTask = new JoTask();
        joTask.setId("JT20200226000000000000003");
        List list = jobOrderTaskService.queryJobOrderTask(joTask);
        System.out.println();
    }

    @Test
    public void test9(){
        List list = jobOrderStartService.queryExpandKeyByJoid("JO20200227000000000000019");
        List<Map<String, Object>> allAcceptGroup = jobOrderAuthService.getAllAcceptGroup();
//        System.out.println(allAcceptGroup);
    }

    @Test
    public void test10(){
        List list = jobOrderAuthService.getAllAcceptGroup();
        System.out.println();
    }

    @Test
    public void  test11(){
        List<JobKeyManageNodeVo> viewJobKeyManageNodes = jobOrderAuthService.getViewJobKeyManageNodes("18233");
        for (JobKeyManageNodeVo node:viewJobKeyManageNodes){
            for (JobKeyManageNodeVo nodeSecond:node.getChildNodes()){
                for (JobKeyManageNodeVo nodeThird:nodeSecond.getChildNodes()){
                    System.out.print(node.getId()+"-");
                    System.out.print(node.getJoKeyType()+"-");
                    System.out.print(node.getJoKeyName()+"-");
                    System.out.print(nodeSecond.getId()+"-");
                    System.out.print(nodeSecond.getJoKeyType()+"-");
                    System.out.print(nodeSecond.getJoKeyName()+"-");
                    System.out.print(nodeThird.getId()+"-");
                    System.out.print(nodeThird.getJoKeyType()+"-");
                    System.out.print(nodeThird.getJoKeyName()+"-");
                    System.out.println("***********");
                }
            }
        }
    }
}
