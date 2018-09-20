package com.ryx.credit.profit.service.impl;

import com.ryx.credit.profit.enums.DeductionStatus;
import com.ryx.credit.profit.enums.DeductionType;
import com.ryx.credit.profit.pojo.ProfitDeduction;
import com.ryx.credit.profit.pojo.ProfitStagingDetail;
import com.ryx.credit.profit.service.ProfitDeductionService;
import com.ryx.credit.profit.service.StagingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author yangmx
 * @desc
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 加载配置文件
@ContextConfiguration(locations = { "classpath:spring-context.xml", "classpath:spring-mybatis.xml" })
public class ImportExcellTest {

    @Autowired
    private ProfitDeductionService profitDeductionService;

    @Autowired
    private StagingService stagingServiceImpl;

    @Test
    public void testDeductionImport() throws Exception {
       File file = new File("D://excel//分润补扣款.xlsx");
       InputStream inputStream = new FileInputStream(file);
       List<List<Object>> deductionist = ExcelUtil.getListByExcel(inputStream, "D://excel//分润补扣款.xlsx");
        if (deductionist != null && deductionist.size() > 0) {
            deductionist.forEach(objectList->{
                ProfitDeduction deduction = new ProfitDeduction();
                deduction.setId((String)objectList.get(7));
                deduction.setAgentPid((String)objectList.get(0));
                deduction.setParentAgentPid((String)objectList.get(1));
                deduction.setAgentName((String)objectList.get(2));
                deduction.setSumDeductionAmt(objectList.get(4)==null?BigDecimal.ZERO:new BigDecimal((String)objectList.get(4)));
                deduction.setMustDeductionAmt(objectList.get(5)==null?BigDecimal.ZERO:new BigDecimal((String)objectList.get(5)));
                deduction.setUpperNotDeductionAmt(objectList.get(6)==null?BigDecimal.ZERO:new BigDecimal((String)objectList.get(6)));
                deduction.setRemark((String)objectList.get(3));
                deduction.setDeductionType(DeductionType.OTHER.getType());
                profitDeductionService.insert(deduction);
            });
        }
    }

    @Test
    public void testStagingDetailImport() throws Exception {
        File file = new File("D://excel//扣款分期.xlsx");
        InputStream inputStream = new FileInputStream(file);
        List<List<Object>> stagList = ExcelUtil.getListByExcel(inputStream, "D://excel//扣款分期.xlsx");
        if (stagList != null && stagList.size() > 0) {
            LocalDate date = LocalDate.now();
            stagList.forEach(objectList->{
                createStagingDetail(objectList, date);
            });
        }
    }

    private void createStagingDetail(List<Object> objects, LocalDate date) {
        BigDecimal stagCount = new BigDecimal((String)objects.get(2));
        BigDecimal current =  new BigDecimal((String)objects.get(4));

        for (int i=1; i<=stagCount.intValue(); i++) {
            ProfitStagingDetail profitStagingDetail = new ProfitStagingDetail();
            profitStagingDetail.setMustAmt(new BigDecimal((String) objects.get(3)));
            if (i<current.intValue()) {
                profitStagingDetail.setStatus("1");
                profitStagingDetail.setRealAmt(new BigDecimal((String) objects.get(3)));
            }else{
                profitStagingDetail.setStatus("0");
                profitStagingDetail.setRealAmt(BigDecimal.ZERO);
            }
            profitStagingDetail.setSourceId((String) objects.get(0));
            profitStagingDetail.setCurrentStagCount(i);
            profitStagingDetail.setDeductionDate(date.plusMonths(i-current.intValue()).format(DateTimeFormatter.ofPattern("yyyy-MM")));
            stagingServiceImpl.insetStagingDetail(profitStagingDetail);
        }
        ProfitDeduction deduction = new ProfitDeduction();
        deduction.setId((String) objects.get(0));
        deduction.setStagingStatus(DeductionStatus.PASS.getStatus());
        profitDeductionService.updateProfitDeduction(deduction);
    }

}