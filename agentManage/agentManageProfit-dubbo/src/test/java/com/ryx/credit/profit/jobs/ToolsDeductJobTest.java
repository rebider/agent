package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.profit.dao.PosRewardTemplateMapper;
import com.ryx.credit.profit.pojo.PosRewardTemplate;
import com.ryx.credit.profit.pojo.TransProfitDetail;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.profit.service.impl.PosProfitComputeServiceImpl;
import com.ryx.credit.profit.service.impl.ProfitToolsDeductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author yangmx
 * @desc
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class ToolsDeductJobTest {
    @Autowired
    ToolsDeductJob toolsDeductJob;
    @Autowired
    ProfitToolsDeductServiceImpl profitToolsDeductService;
    @Autowired
    PosProfitComputeServiceImpl posProfitComputeServiceImpl;
    @Autowired
    private ProfitDetailMonthService profitDetailMonthServiceImpl;
    @Autowired
    protected RedisTemplate<String, String> redisTemplate;
    @Autowired
    private PosRewardTemplateMapper posRewardTemplateMapper;

    @Test
    public void execut() throws Exception {
        toolsDeductJob.execut();
    }

    @Test
    public void computeToolsDeduct() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(10);
        map.put("agentPid", "AG20180809000000000005966"); //AG20180813000000000006020
        map.put("paltformNo", "100003");        //平台编号
        map.put("agentProfitAmt", "0");        //代理商分润
        map.put("computType", "1");        //类型
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> newMap = new HashMap<String, Object>(10);
        newMap.put("id","P_PROFIT_DETAIL_M20180920000000000128510");
        newMap.put("basicAmt","16.66");
        Map<String, Object> newMa1p = new HashMap<String, Object>(10);
        newMa1p.put("id","P_PROFIT_DETAIL_M20180920000000000128572");
        newMa1p.put("basicAmt", "80");
        list.add(newMap);
        list.add(newMa1p);
        map.put("hbList", list);
        map = profitToolsDeductService.execut(map);
        System.out.println(map.toString());
    }

    @Test
    public void computePosreWard() throws Exception {
        List<String> busNum = new ArrayList<String>();
        busNum.add("O00000000000221");
        busNum.add("O00000000000718");
        String profitDate = "201807";
        String agentType = "2";
        List<TransProfitDetail> transProfitDetails = profitDetailMonthServiceImpl.getChildTransProfitDetailList(busNum, profitDate, agentType);
        List<Object> listadd = new ArrayList<Object>();
        transProfitDetails.stream().forEach(transProfitDetail -> {
            Map<String, Object> map = new HashMap<String, Object>(10);
            map.put("agentType", transProfitDetail.getAgentType());//机构一代
            map.put("agentId", transProfitDetail.getBusNum());
            map.put("agentPid", transProfitDetail.getAgentId());
            map.put("posTranAmt", transProfitDetail.getPosRewardAmt());
            map.put("posJlTranAmt", transProfitDetail.getPosCreditAmt());
            try {
                map = posProfitComputeServiceImpl.execut(map);
                System.out.println(map.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            listadd.add(map);
        });
        System.out.println("+====="+ JSONObject.toJSONString(listadd));
    }

    @Test
    public void computeJGPosreWard() throws Exception {
        Supplier<List<String>> list = ArrayList<String>::new;
        List<String> busNum = list.get();
//        busNum.add("O00000000110370");
//        busNum.add("O00000000145380");
//        busNum.add("O00000000218951");
//        busNum.add("O00000000166351");
//        busNum.add("O00000000216760");
//        busNum.add("O00000000112234");
//        busNum.add("O00000000213428");
//        busNum.add("O00000000148391");
//        busNum.add("O00000000118283");
        busNum.add("O00000000160468");
        String profitDate = "201807";
        String agentType = "3";
        List<TransProfitDetail> transProfitDetails = profitDetailMonthServiceImpl.getChildTransProfitDetailList(busNum, profitDate, agentType);
        List<Object> listadd = new ArrayList<Object>();
        transProfitDetails.stream().forEach(transProfitDetail -> {
            Map<String, Object> map = new HashMap<String, Object>(10);
            map.put("agentType", transProfitDetail.getAgentType());//机构一代
            map.put("agentId", transProfitDetail.getBusNum());
            map.put("agentPid", transProfitDetail.getAgentId());
            map.put("posTranAmt", transProfitDetail.getPosRewardAmt());
            map.put("posJlTranAmt", transProfitDetail.getPosCreditAmt());
            try {
                map = posProfitComputeServiceImpl.execut(map);
                System.out.println(map.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            listadd.add(map);
        });
        System.out.println("+====="+ JSONObject.toJSONString(listadd));
    }

    @Test
    public void redisTest(){
        List<PosRewardTemplate> posRewardTemplates = posRewardTemplateMapper.selectByExample(null);
        redisTemplate.opsForValue().set("POS_REWARD_TEMP", JSONObject.toJSONString(null));
        String list = redisTemplate.opsForValue().get("POS_REWARD_TEMP");
        System.out.println(list);
        List json = JSONObject.parseObject(list, List.class);
        if(json != null && !json.isEmpty()){
            json.forEach(lists -> {
                JSON sss=(JSON) JSONObject.parse(lists.toString());
                PosRewardTemplate posRewardTemplate = JSONObject.toJavaObject(sss, PosRewardTemplate.class);
                System.out.println(posRewardTemplate.getId());
            });
        }
    }
}