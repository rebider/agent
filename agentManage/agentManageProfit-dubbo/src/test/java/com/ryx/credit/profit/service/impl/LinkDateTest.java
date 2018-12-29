package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.result.AgentResult;
import com.ryx.credit.common.util.DateUtil;
import com.ryx.credit.pojo.admin.order.OPayment;
import com.ryx.credit.profit.dao.*;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.service.order.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class LinkDateTest {
    @Autowired
    ProfitDetailMonthMapper detailMonthMapper;
    @Autowired
    TransProfitDetailMapper transProfitDetailMapper;
    @Autowired
    OrderService orderService;

    private int index = 1;
    private BigDecimal tranAmount = BigDecimal.ZERO;

    @Test
    public void test() throws Exception {
//        String profitDate = "201808";
//        new_computerTax(profitDate);
        new_computerTax();
    }

    /**
     * 每一个小时执行一次：@Scheduled(cron = "0 0 * * * ?")
     * 每月10号凌晨20点执行一次：@Scheduled(cron = "0 0 10 20 * ?")
     * @throws Exception
     */
    @Test
    @Scheduled(cron = "0 0 * * * ?")
    public void new_computerTax() throws Exception {
//        String profitDate = "201808";
        String profitDate = null;
        profitDate = profitDate==null?DateUtil.sdfDays.format(DateUtil.addMonth(new Date(),-1)).substring(0,6):profitDate;
        //三、抵税计算
        //1、获取cx提供的代理商线下打款信息列表
        //2、遍历打款信息，根据信息中的busnum和上级busnum关联出agid和上级agid，然后获取需要抵税的ProfitDetailMonth
        //3、抵扣税额，重新计算并更新税额、实际分润
        //PS:抵用金额税不能大于税额本身、抵用了多少金额需要交给cx更新
        List<TransProfitDetail> list = transProfitDetailMapper.selectListByDate(profitDate);
        for(TransProfitDetail tranDetail : list){
            AgentResult agentResult = orderService.queryPaymentXXDK(tranDetail.getBusNum());//查询线下打款数据信息
            if(null == agentResult){
                continue;
            }
            List<HashMap> maps = (List<HashMap>) agentResult.getData();
            if(maps.size() == 0){
                continue;
            }

            ProfitDetailMonth where = new ProfitDetailMonth();
            where.setAgentId(tranDetail.getAgentId());//代理商AG码
            where.setParentAgentId(tranDetail.getParentAgentId());//上级代理商AG码
            where.setProfitDate(profitDate);//月份
            ProfitDetailMonth profitDetail = detailMonthMapper.selectByIdAndParent(where);
            BigDecimal deductionTax = profitDetail.getDeductionTaxMonthAmt();//本月税额
            BigDecimal realAmt = profitDetail.getRealProfitAmt()==null?BigDecimal.ZERO:profitDetail.getRealProfitAmt();//实际分润
            BigDecimal tax = new BigDecimal(profitDetail.getTax());//税点(0.06)
            BigDecimal smalTax = profitDetail.getSmalTaxAmt()==null?BigDecimal.ZERO:profitDetail.getSmalTaxAmt();;//已抵税金额

            List<OPayment> paymentList = new ArrayList<>();
            for(HashMap map : maps){
                BigDecimal acAmt = (BigDecimal) map.get("ACTUAL_RECEIPT");//剩余可抵用打款金额
                BigDecimal taxAmt = acAmt.multiply(tax);//可抵税额

                //可抵金额若大于本月税额直接等于更新即可
                if(deductionTax.subtract(taxAmt).compareTo(BigDecimal.ZERO) < 0){
                    taxAmt = deductionTax;
                }
                //若已抵用税额跟本月税额持平则跳出不继续执行
                if(smalTax.subtract(deductionTax).compareTo(BigDecimal.ZERO) == 0){
                    break;
                }
                realAmt = realAmt.add(taxAmt);//实际分润+可抵税额
                smalTax = smalTax.add(taxAmt);//可抵税额+已抵税金额

                //打款
                OPayment oPayment = new OPayment();
                oPayment.setId((String) map.get("ID"));//主键ID
                oPayment.setProfitTaxAmt(taxAmt.divide(new BigDecimal("0.06")).setScale(2,4));//已用金额
                paymentList.add(oPayment);
            }
            //更新月分润明细
            profitDetail.setSmalTaxAmt(smalTax); //已抵用税额
            profitDetail.setRealProfitAmt(realAmt);//实际分润
            detailMonthMapper.updateByPrimaryKeySelective(profitDetail);

            //更新打款信息
            if(paymentList.size() > 0){
                orderService.updateProfitTaxAmt(paymentList);//批量更新分润税点抵扣金额
            }
        }
    }

}