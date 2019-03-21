package com.ryx.credit.profit.jobs;

import com.ryx.credit.common.enumc.PamentSrcType;
import com.ryx.credit.common.enumc.PaySign;
import com.ryx.credit.profit.service.DeductService;
import com.ryx.credit.service.order.IOPdSumService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机具完成扣款数据后返回给代理商测试
 * @author chenliang
 * @create 2018/3/21
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class ProfitOPdSumTest {

    private Logger logger = LoggerFactory.getLogger(ProfitOPdSumTest.class);


    @Autowired
    private IOPdSumService OPdSumService;

    @Test
    public void testDeal() {

        List<Map<String,Object>> listMap = new ArrayList<>();
        Map<String,Object> map1 = new HashMap<>();
        map1.put("deductTime","2019-03-21 15:31:48");
        map1.put("mustDeductionAmtSum", "3000");
        map1.put("actualDeductionAmtSum", "3000");
        map1.put("notDeductionAmt","0");
        map1.put("srcId", "P_DEDUCT20190321000000000026382");
        map1.put("detailId", "OPS2019032100000000000203");
        map1.put("srcType", PamentSrcType.FENRUN_DIKOU.code);
        listMap.add(map1);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("deductTime","2019-03-21 15:31:48");
        map2.put("mustDeductionAmtSum", "50000");
        map2.put("actualDeductionAmtSum", "50000");
        map2.put("notDeductionAmt","0");
        map2.put("srcId", "P_DEDUCT20190321000000000026383");
        map2.put("detailId", "OPS2019032100000000000201");
        map2.put("srcType", PamentSrcType.FENRUN_DIKOU.code);
        listMap.add(map2);
        Map<String,Object> map3 = new HashMap<>();
        map3.put("deductTime","2019-03-21 15:31:48");
        map3.put("mustDeductionAmtSum", "280");
        map3.put("actualDeductionAmtSum", "140");
        map3.put("notDeductionAmt","140");
        map3.put("srcId", "P_DEDUCT20190321000000000026384");
        map3.put("detailId", "OPS2019032100000000000200");
        map3.put("srcType", PamentSrcType.FENRUN_DIKOU.code);
        listMap.add(map3);

        Map<String,Object> map4 = new HashMap<>();
        map4.put("deductTime","2019-03-21 15:31:48");
        map4.put("mustDeductionAmtSum", "400");
        map4.put("actualDeductionAmtSum", "100");
        map4.put("notDeductionAmt","300");
        map4.put("srcId", "P_DEDUCT20190321000000000026385");
        map4.put("detailId", "OPS2019032100000000000198");
        map4.put("srcType", PamentSrcType.FENRUN_DIKOU.code);
        listMap.add(map4);

        Map<String,Object> map5 = new HashMap<>();
        map5.put("deductTime","2019-03-21 15:31:48");
        map5.put("mustDeductionAmtSum", "132666.67");
        map5.put("actualDeductionAmtSum", "88543.47");
        map5.put("notDeductionAmt","44123.2");
        map5.put("srcId", "P_DEDUCT20190321000000000026386");
        map5.put("detailId", "OPS2019032100000000000197");
        map5.put("srcType", PamentSrcType.FENRUN_DIKOU.code);

        listMap.add(map5);
        Map<String,Object> map6 = new HashMap<>();
        map6.put("deductTime","2019-03-21 15:31:48");
        map6.put("mustDeductionAmtSum", "4733.33");
        map6.put("actualDeductionAmtSum", "2047.67");
        map6.put("notDeductionAmt","2685.66");
        map6.put("srcId", "P_DEDUCT20190321000000000026387");
        map6.put("detailId", "OPS2019032100000000000202");
        map6.put("srcType", PamentSrcType.FENRUN_DIKOU.code);
        listMap.add(map6);
        Map<String,Object> map7 = new HashMap<>();
        map7.put("deductTime","2019-03-21 15:31:48");
        map7.put("mustDeductionAmtSum", "280");
        map7.put("actualDeductionAmtSum", "280");
        map7.put("notDeductionAmt","0");
        map7.put("srcId", "P_DEDUCT20190321000000000026388");
        map7.put("detailId", "OPS2019032100000000000196");
        map7.put("srcType", PamentSrcType.FENRUN_DIKOU.code);

        listMap.add(map7);
        Map<String,Object> map8 = new HashMap<>();
        map8.put("deductTime","2019-03-21 15:31:48");
        map8.put("mustDeductionAmtSum", "400");
        map8.put("actualDeductionAmtSum", "300");
        map8.put("notDeductionAmt","100");
        map8.put("srcId", "P_DEDUCT20190321000000000026389");
        map8.put("detailId", "OPS2019032100000000000199");
        map8.put("srcType", PamentSrcType.FENRUN_DIKOU.code);

        listMap.add(map8);
        OPdSumService.uploadStatus(listMap, PaySign.JQ.code);
    }
}
