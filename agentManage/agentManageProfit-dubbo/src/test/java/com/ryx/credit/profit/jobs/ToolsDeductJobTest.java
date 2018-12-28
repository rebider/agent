package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.pojo.admin.agent.AgentBusInfo;
import com.ryx.credit.profit.dao.PosRewardTemplateMapper;
import com.ryx.credit.profit.dao.ProfitDeductionMapper;
import com.ryx.credit.profit.dao.TransProfitDetailMapper;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.PosRewardSDetailService;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.profit.service.TransProfitDetailService;
import com.ryx.credit.profit.service.impl.PosProfitComputeServiceImpl;
import com.ryx.credit.profit.service.impl.ProfitToolsDeductServiceImpl;
import com.ryx.credit.service.agent.AgentBusinfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    @Autowired
    private AgentBusinfoService agentBusinfoService;
    @Autowired
    private TransProfitDetailService transProfitDetailService;
    @Autowired
    private TransProfitDetailMapper transProfitDetailMapper;
    @Autowired
    private ProfitDeductionMapper profitDeductionMapper;
    @Autowired
    private ProfitDeductionService profitDeductionService;
    @Test
    public void execut() throws Exception {
//        toolsDeductJob.execut();
//        System.out.println(LocalDate.now().plusMonths(-1).toString().substring(0,7));
//        profitToolsDeductService.otherOperate();

        Map<String, Object> map = new HashMap<>(10);
        ProfitDeduction profitDeduction = new ProfitDeduction();
        profitDeduction.setAgentId("AG20181121000000000015930");
        profitDeduction.setDeductionDate("2018-12");
        profitDeduction.setDeductionType(DeductionType.MACHINE.getType());
        List<ProfitDeduction> list = profitDeductionService.getProfitDeduction(profitDeduction);
        list.parallelStream().filter(profitDeduction1 -> Objects.equals("100003", profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getMustDeductionAmt).reduce(BigDecimal::add).ifPresent(bigDecimal-> map.put("PosDgMustDeductionAmt", bigDecimal));
        list.parallelStream().filter(profitDeduction1 -> Objects.equals("5000", profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getMustDeductionAmt).reduce(BigDecimal::add).ifPresent(bigDecimal-> map.put("RhbDgMustDeductionAmt", bigDecimal));
        list.parallelStream().filter(profitDeduction1 -> Objects.equals("100002", profitDeduction1.getDeductionDesc())).map(ProfitDeduction::getMustDeductionAmt).reduce(BigDecimal::add).ifPresent(bigDecimal-> map.put("ZposDgMustDeductionAmt", bigDecimal));
        System.out.println(map);
    }

    @Test
    public void computeToolsDeduct() throws Exception {

        Map<String, Object> map = new HashMap<>(10);
        map.put("agentPid", "AG20181121000000000015930"); //业务平台编号
        map.put("deductDate", "2018-12");   //扣款月份
        map.put("agentProfitAmt", "3200");     //代理商分润
        map.put("computType", "1");
        map.put("rotation", "1");
        Map<String, Object> sss = profitToolsDeductService.execut(map);

//        Map<String, Object> map = new HashMap<String, Object>(10);
//        map.put("agentPid", "AG20180809000000000005966"); //AG20180813000000000006020
//        map.put("paltformNo", "100003");        //平台编号
//        map.put("agentProfitAmt", "0");        //代理商分润
//        map.put("computType", "1");        //类型
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        Map<String, Object> newMap = new HashMap<String, Object>(10);
//        newMap.put("id","P_PROFIT_DETAIL_M20180920000000000128510");
//        newMap.put("basicAmt","16.66");
//        Map<String, Object> newMa1p = new HashMap<String, Object>(10);
//        newMa1p.put("id","P_PROFIT_DETAIL_M20180920000000000128572");
//        newMa1p.put("basicAmt", "80");
//        list.add(newMap);
//        list.add(newMa1p);
//        map.put("hbList", list);
//        map = profitToolsDeductService.execut(map);
//        System.out.println(map.toString());
    }

    @Test
    public void computePosreWard() throws Exception {
//        posProfitComputeServiceImpl.otherOperate();
//        Map<String, Object> map = new HashMap<String, Object>(10);
//        map.put("agentType", "2");//机构一代
//        map.put("agentId", "JS00000228");
//        map.put("busNum", "O00000000000165");
//        map.put("posTranAmt", "3622154621.84");
//        map.put("posJlTranAmt", "3355858697.04");
//        map.put("computType", "0");

        Map<String, Object> map = new HashMap<>(10);
        map.put("agentType", "3");
        map.put("agentId", "AG20180726000000000002659");
        map.put("currentDate", "201810");
        map.put("computType", "1");
        try {
            map = posProfitComputeServiceImpl.execut(map);
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
//                    LOG.error("获取pos奖励失败");
            throw new RuntimeException("获取pos奖励失败");
        }

//        List<String> busNum = new ArrayList<String>();
//        busNum.add("O00000000000221");
//        busNum.add("O00000000000718");
//        String profitDate = "201807";
//        String agentType = "2";
//        List<TransProfitDetail> transProfitDetails = profitDetailMonthServiceImpl.getChildTransProfitDetailList(busNum, profitDate, agentType);
//        List<Object> listadd = new ArrayList<Object>();
//        transProfitDetails.stream().forEach(transProfitDetail -> {
//            Map<String, Object> map = new HashMap<String, Object>(10);
//            map.put("agentType", transProfitDetail.getAgentType());//机构一代
//            map.put("agentId", transProfitDetail.getBusNum());
//            map.put("agentPid", transProfitDetail.getAgentId());
//            map.put("posTranAmt", transProfitDetail.getPosRewardAmt());
//            map.put("posJlTranAmt", transProfitDetail.getPosCreditAmt());
//            try {
//                map = posProfitComputeServiceImpl.execut(map);
//                System.out.println(map.toString());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            listadd.add(map);
//        });
//        System.out.println("+====="+ JSONObject.toJSONString(listadd));
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

    @Test
    public void test1() throws InterruptedException {
//                posProfitComputeServiceImpl.clearDetail();
        TransProfitDetail detaila = new TransProfitDetail();
        detaila.setProfitDate("201810");
        List<TransProfitDetail> list = transProfitDetailService.getTransProfitDetailList(detaila);
        list.forEach(transProfitDetail -> {

            TransProfitDetail detail = new TransProfitDetail();
            detail.setAgentId(transProfitDetail.getAgentId());
            detail.setBusCode("100003");
            List<TransProfitDetail> transProfitDetails = transProfitDetailService.getTransProfitDetailList(detail);
            if (transProfitDetails.size() > 0) {
                detail = transProfitDetails.get(0);
                Map<String, Object> map = new HashMap<>(10);
                map.put("agentType", detail.getAgentType());
                map.put("agentId", detail.getAgentId());
                map.put("currentDate", "201810");
                map.put("computType", "1");
                try {
                    map = posProfitComputeServiceImpl.execut(map);
                    BigDecimal sss=(BigDecimal) map.get("posRewardAmt");
                    System.out.println(sss);
                } catch (Exception e) {
                    e.printStackTrace();
//                    LOG.error("获取pos奖励失败");
                    throw new RuntimeException("获取pos奖励失败");
                }
            }else{
            }
        });

//        Map<String, Object> map = new HashMap<>(10);
//        map.put("agentType", "3");
//        map.put("agentId", "AG20180726000000000002659");
//        map.put("currentDate", "201810");
//        map.put("computType", "1");
//        try {
//            map = posProfitComputeServiceImpl.execut(map);
//            BigDecimal sss=(BigDecimal) map.get("posRewardAmt");
//            System.out.println(sss);
////                    transProfitDetail.setPosRewardDeductionAmt( (BigDecimal) map.get("posAssDeductAmt"));
//        } catch (Exception e) {
//            e.printStackTrace();
////                    LOG.error("获取pos奖励失败");
//            throw new RuntimeException("获取pos奖励失败");
//        }
    }

    @Test
    public void testCreidTranAmt() throws InterruptedException {
        TransProfitDetail detail = new TransProfitDetail();
        detail.setProfitDate("201810");
        List<TransProfitDetail> list = transProfitDetailService.getTransProfitDetailList(detail);
        list.forEach(transProfitDetail -> {
            int number=(int)((Math.random()*9+1)*100000);
            System.out.println(String.valueOf(number));
            transProfitDetail.setId(String.valueOf(number));
            transProfitDetail.setProfitDate("201710");
            transProfitDetail.setPosRewardAmt(BigDecimal.ZERO);
            transProfitDetail.setPosCreditAmt(BigDecimal.ZERO);
            transProfitDetail.setInTransAmt(BigDecimal.ZERO);
            transProfitDetail.setOutTransAmt(BigDecimal.ZERO);
            transProfitDetail.setInProfitAmt(BigDecimal.ZERO);
            transProfitDetail.setOutProfitAmt(BigDecimal.ZERO);
            transProfitDetailMapper.insert(transProfitDetail);
        });
    }

    @Test
    public void test() throws InterruptedException {

        TransProfitDetail details = new TransProfitDetail();
        details.setProfitDate("201809");
        List<TransProfitDetail> lista = transProfitDetailService.getTransProfitDetailList(details);
        lista.parallelStream().forEach(transProfitDetail1 -> {
            if (transProfitDetail1.getBusNum() != null) {
                AgentBusInfo busInfo = agentBusinfoService.getByBusidAndCode("100003", transProfitDetail1.getBusNum());
                if (busInfo != null) {
                    transProfitDetail1.setAgentId(busInfo.getAgentId());
                    transProfitDetail1.setAgentType(busInfo.getBusType());
                    TransProfitDetailExample sss = new TransProfitDetailExample();
                    sss.createCriteria().andIdEqualTo(transProfitDetail1.getId());
                    transProfitDetailMapper.deleteByExample(sss);
                    transProfitDetailMapper.insert(transProfitDetail1);
                }
            }
        });

        TransProfitDetail detail = new TransProfitDetail();
        detail.setProfitDate("201710");
        List<TransProfitDetail> list = transProfitDetailService.getTransProfitDetailList(detail);
        list.parallelStream().forEach(transProfitDetail -> {
            if(transProfitDetail.getBusNum() != null){
                AgentBusInfo busInfo = agentBusinfoService.getByBusidAndCode("100003", transProfitDetail.getBusNum());
                if(busInfo != null){
                    transProfitDetail.setAgentId(busInfo.getAgentId());
                    transProfitDetail.setAgentType(busInfo.getBusType());
                    TransProfitDetailExample sss = new TransProfitDetailExample() ;
                    sss.createCriteria().andIdEqualTo(transProfitDetail.getId());
                    transProfitDetailMapper.deleteByExample(sss);
                    transProfitDetailMapper.insert(transProfitDetail);
                }
            }


//            String superAgentId = posRewardSDetailService.getSuperAgentId(transProfitDetail.getAgentId());
//            if(transProfitDetail.getAgentId() == null ){
//                AgentBusInfo ss = agentBusinfoService.getByBusidAndCode(transProfitDetail.getBusCode(), transProfitDetail.getBusNum());
//                if(ss != null ){
//                    transProfitDetail.setAgentId(ss.getAgentId());
//                    transProfitDetail.setBusCode(ss.getBusPlatform());
//                    transProfitDetail.setAgentType(ss.getBusType());
//                    TransProfitDetailExample sss = new TransProfitDetailExample() ;
//                    sss.createCriteria().andIdEqualTo(transProfitDetail.getId());
//                    transProfitDetailMapper.deleteByExample(sss);
//                    transProfitDetailMapper.insert(transProfitDetail);
//                }
//            } else {
//                if(transProfitDetail.getAgentType() ==null || transProfitDetail.getAgentType() == ""){
//                    AgentBusInfo ss = agentBusinfoService.getByBusidAndCode(transProfitDetail.getBusCode(), transProfitDetail.getBusNum());
//                    if(ss != null){
//                        if(transProfitDetail.getAgentId() ==null){
//                            transProfitDetail.setAgentId(ss.getAgentId());
//                        }
//                        transProfitDetail.setAgentType(ss.getBusType());
//                        TransProfitDetailExample sss = new TransProfitDetailExample() ;
//                        sss.createCriteria().andIdEqualTo(transProfitDetail.getId());
//                        transProfitDetailMapper.deleteByExample(sss);
//                        transProfitDetailMapper.insert(transProfitDetail);
//                    }
//                }
//            }
        });
    }
    @Autowired
    private PosRewardSDetailService posRewardSDetailService;
    @Test
    public void tes1111t() throws InterruptedException {
//        profitToolsDeductService.otherOperate();
        posProfitComputeServiceImpl.otherOperate();
//        profitToolsDeductService.otherOperate();
//        posProfitComputeServiceImpl.clearDetail();

//        String currentDate = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0,6);
//        posRewardSDetailService.deleteCurrentDate(currentDate);

    }


    @Test
    public void testDeduction() throws InterruptedException {
//        ProfitDeductionExample Example = new ProfitDeductionExample();
//        ProfitDeductionExample.Criteria criteria = Example.createCriteria();
//        criteria.andDeductionTypeEqualTo("02");
//        criteria.andDeductionDateEqualTo("2018-10");
//        List<String> sss = new ArrayList<>();
//        sss.add("AG20181018000000000006622");
//        sss.add("AG20181121000000000015930");
//        sss.add("AG20180809000000000005966");
//        criteria.andAgentIdNotIn(sss);
//        List<ProfitDeduction> list = profitDeductionMapper.selectByExample(Example);
//        list.forEach(profitDeduction -> {
//            AgentBusInfo busInfo = agentBusinfoService.getByBusidAndCode(profitDeduction.getDeductionDesc(),profitDeduction.getAgentId());
//            if(busInfo != null){
//                ProfitDeduction update = new ProfitDeduction();
//                update.setId(profitDeduction.getId());
//                update.setAgentPid(busInfo.getAgentId());
//                profitDeductionMapper.updateByPrimaryKeySelective(update);
//            }
//        });


        ProfitDeductionExample Example = new ProfitDeductionExample();
        ProfitDeductionExample.Criteria criteria = Example.createCriteria();
        criteria.andDeductionTypeEqualTo("02");
        criteria.andDeductionDateEqualTo("2018-10");
        List<String> sss = new ArrayList<>();
        sss.add("AG20181018000000000006622");
        sss.add("AG20181121000000000015930");
        sss.add("AG20180809000000000005966");
        criteria.andAgentIdNotIn(sss);
        List<ProfitDeduction> list = profitDeductionMapper.selectByExample(Example);
        list.forEach(profitDeduction -> {
            if(profitDeduction.getParentAgentId() != null || profitDeduction.getParentAgentId() != ""){
                System.out.println(profitDeduction.getParentAgentId()+"=============");
                AgentBusInfo busInfo = agentBusinfoService.getByBusidAndCode(profitDeduction.getDeductionDesc(), profitDeduction.getParentAgentId());
                if(busInfo != null){
                    ProfitDeduction update = new ProfitDeduction();
                    update.setId(profitDeduction.getId());
                    update.setParentAgentPid(busInfo.getAgentId());
                    update.setParentAgentId(busInfo.getAgentId());
                    profitDeductionMapper.updateByPrimaryKeySelective(update);
                }
            }
        });


//        Map<String, Object> map = new HashMap<>(10);
//        map.put("agentPid", "AG20181018000000000006622"); //业务平台编号
//        map.put("deductDate", LocalDate.now().plusMonths(-1).toString().substring(0, 7));//扣款月份
//        map.put("agentProfitAmt", "33");//代理商分润
//        map.put("computType", "1");
//        map.put("rotation", "1");
//        try {
//            System.out.println(profitToolsDeductService.execut(map));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
//        Map<String, Object> newMap = new HashMap<String, Object>(10);
//        newMap.put("id","P_PROFIT_DETAIL_222222222222222222222222");
//        newMap.put("basicAmt","60.00");
//        Map<String, Object> newMa1p = new HashMap<String, Object>(10);
//        newMa1p.put("id","P_PROFIT_DETAIL_111111111111111111111111");
//        newMa1p.put("basicAmt", "60.00");
//        list.add(newMap);
//        list.add(newMa1p);
//        map.put("hbList", list);
//        map.put("rotation", "2");
//        try {
//            System.out.println(profitToolsDeductService.execut(map));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}