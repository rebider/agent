package com.ryx.credit.profit.jobs;

import com.alibaba.fastjson.JSONObject;
import com.ryx.credit.profit.dao.OrganTranMonthDetailMapper;
import com.ryx.credit.profit.pojo.OrganTranMonthDetail;
import com.ryx.credit.profit.pojo.OrganTranMonthDetailExample;
import com.ryx.credit.profit.service.impl.PosProfitComputeServiceImpl;
import com.ryx.credit.profit.service.impl.ProfitToolsDeductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private OrganTranMonthDetailMapper organTranMonthDetailMapper;

    @Test
    public void execut() throws Exception {
        toolsDeductJob.execut();
    }

    @Test
    public void computeToolsDeduct() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>(10);
        map.put("agentPid", "AG20180816000000000006084"); //AG20180813000000000006020
        map.put("paltformNo", "2000");        //平台编号
        map.put("deductDate", "2018-09");       //扣款月份
        map.put("agentProfitAmt", "777777");        //代理商分润
        map = profitToolsDeductService.execut(map);
        System.out.println("应扣："+map.get("mustDeductionAmtSum"));//返回应扣金额
        System.out.println("实扣："+map.get("actualDeductionAmtSum"));//实扣金额
    }

    @Test
    public void computePosreWard() throws Exception {
        OrganTranMonthDetailExample example = new OrganTranMonthDetailExample();
        OrganTranMonthDetailExample.Criteria criteria = example.createCriteria();
        criteria.andProfitDateEqualTo("201807");
        criteria.andAgentTypeEqualTo("3");
        criteria.andAllchildJlTranAmtEqualTo(new BigDecimal("11111111111111"));
        List<OrganTranMonthDetail> list = organTranMonthDetailMapper.selectByExample(example);
        List<Object> listadd = new ArrayList<Object>();
        list.stream().forEach(organTranMonthDetail1 -> {
            Map<String, Object> map = new HashMap<String, Object>(10);
            map.put("agentType", organTranMonthDetail1.getAgentType());//机构一代
            map.put("agentId", organTranMonthDetail1.getAgentId());
            map.put("agentPid", organTranMonthDetail1.getAgentPid());
            map.put("posTranAmt", organTranMonthDetail1.getPosTranAmt());
            map.put("posJlTranAmt", organTranMonthDetail1.getPosJlTranAmt());
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
        OrganTranMonthDetailExample example = new OrganTranMonthDetailExample();
        OrganTranMonthDetailExample.Criteria criteria = example.createCriteria();
        criteria.andProfitDateEqualTo("201807");
        criteria.andAgentTypeEqualTo("2");
        criteria.andAllchildJlTranAmtEqualTo(new BigDecimal("777777"));
        List<OrganTranMonthDetail> list = organTranMonthDetailMapper.selectByExample(example);
        List<Object> listadd = new ArrayList<Object>();
        list.stream().forEach(organTranMonthDetail1 -> {
            Map<String, Object> map = new HashMap<String, Object>(10);
            map.put("agentType", organTranMonthDetail1.getAgentType());//机构
            map.put("agentId", organTranMonthDetail1.getAgentId());
            map.put("agentPid", organTranMonthDetail1.getAgentPid());
            map.put("posTranAmt", organTranMonthDetail1.getPosTranAmt());
            map.put("posJlTranAmt", organTranMonthDetail1.getPosJlTranAmt());
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
}