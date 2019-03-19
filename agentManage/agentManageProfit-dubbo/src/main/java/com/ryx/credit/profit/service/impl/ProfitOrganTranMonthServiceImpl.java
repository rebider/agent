package com.ryx.credit.profit.service.impl;/**
 * @Auther: zhaodw
 * @Date: 2018/8/1 11:38
 * @Description:
 */

import com.ryx.credit.common.util.*;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitOrganTranMonthMapper;
import com.ryx.credit.profit.dao.TranPlatformMapper;
import com.ryx.credit.profit.jobs.NewProfitDataJob;
import com.ryx.credit.profit.jobs.TranDataJob;
import com.ryx.credit.profit.pojo.ProfitOrganTranMonth;
import com.ryx.credit.profit.pojo.ProfitOrganTranMonthExample;
import com.ryx.credit.profit.service.ProfitOrganTranMonthService;
import com.ryx.credit.profit.unitmain.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 月交易实现
 *
 * @author zhaodw
 * @create 2018/8/1
 * @since 1.0.0
 */
@Service("profitOrganTranMonthServiceImpl")
public class ProfitOrganTranMonthServiceImpl implements ProfitOrganTranMonthService {

    private static final Logger LOGGER = Logger.getLogger(ProfitOrganTranMonthServiceImpl.class);

    @Autowired
    private ProfitOrganTranMonthMapper profitOrganTranMonthMapper;
    @Autowired
    private TranPlatformMapper tranPlatformMapper;

    @Autowired
    private TranDataJob tranDataJob;
    @Resource
    NewProfitDataJob newProfitDataJob;
    @Autowired
    NewProfitMonthMposDataJob newProfitMonthMposDataJob;
    @Autowired
    DailyProfitMposDataJob dailyProfitMposDataJob;
    @Autowired
    ProfitZhiFaDataJob profitZhiFaDataJob;
    @Autowired
    ProfitMposDiffDataJob profitMposDiffDataJob;
    @Autowired
    ProfitSummaryDataJob profitSummaryDataJob;

    @Override
    public void insert(ProfitOrganTranMonth profitOrganTranMonth) {
        profitOrganTranMonthMapper.insert(profitOrganTranMonth);
    }

    @Override
    public void update(ProfitOrganTranMonth profitOrganTranMonth) {
        profitOrganTranMonthMapper.updateByPrimaryKeySelective(profitOrganTranMonth);
    }

    @Override
    public PageInfo getProfitOrganTranMonthList(ProfitOrganTranMonth profitOrganTranMonth, Page page) {
        ProfitOrganTranMonthExample example = new ProfitOrganTranMonthExample();
        example.setPage(page);
        ProfitOrganTranMonthExample.Criteria criteria = example.createCriteria();
        // 月份按开始到结束查询

        if (StringUtils.isNotBlank(profitOrganTranMonth.getProfitDateStart()) && StringUtils.isNotBlank(profitOrganTranMonth.getProfitDateEnd())) {
            criteria.andProfitDateBetween(profitOrganTranMonth.getProfitDateStart(), profitOrganTranMonth.getProfitDateEnd());
        } else if (StringUtils.isNotBlank(profitOrganTranMonth.getProfitDateStart())) {
            criteria.andProfitDateEqualTo(profitOrganTranMonth.getProfitDateStart());
        } else if (StringUtils.isNotBlank(profitOrganTranMonth.getProfitDateEnd())) {
            criteria.andProfitDateEqualTo(profitOrganTranMonth.getProfitDateEnd());
        }

        if (StringUtils.isNotBlank(profitOrganTranMonth.getProfitDate())) {
            criteria.andProfitDateEqualTo(profitOrganTranMonth.getProfitDate());
        }
        if (StringUtils.isNotBlank(profitOrganTranMonth.getProductType())) {
            criteria.andProductTypeEqualTo(profitOrganTranMonth.getProductType());
        }
        example.setOrderByClause(" PROFIT_DATE DESC ");
        List<ProfitOrganTranMonth> settleErrs = profitOrganTranMonthMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setRows(settleErrs);
        pageInfo.setTotal(profitOrganTranMonthMapper.countByExample(example));
        return pageInfo;
    }

    @Override
    public void delete(ProfitOrganTranMonth profitOrganTranMonth) {
        ProfitOrganTranMonthExample example = new ProfitOrganTranMonthExample();
        ProfitOrganTranMonthExample.Criteria criteria = example.createCriteria();
        // 月份按开始到结束查询
        if (StringUtils.isNotBlank(profitOrganTranMonth.getProfitDate())) {
            criteria.andProfitDateEqualTo(profitOrganTranMonth.getProfitDate());
            if (StringUtils.isNotBlank(profitOrganTranMonth.getProductType())) {
                criteria.andProductTypeEqualTo(profitOrganTranMonth.getProductType());
            }
        } else {
            LOGGER.error("删除失败");
            throw new RuntimeException("删除失败。。");
        }
        profitOrganTranMonthMapper.deleteByExample(example);
    }

    @Override
    public void importData(String type) {
        String month = LocalDate.now().plusMonths(-1).format(DateTimeFormatter.BASIC_ISO_DATE).substring(0, 6);
        try {
            if (type.equals("1")) {//交易量重新导入
                tranDataJob.deal(month);
            } else if ("2".equals(type)) {//pos分润重新同步
                newProfitDataJob.deal(month);
            } else if ("3".equals(type)) {//手刷月结分润重新同步
                newProfitMonthMposDataJob.excute(month);
            } else if ("4".equals(type)) {//手刷日结分润重新同步
                for (int i = 1; i <= 31; i++) {
                    String settleDay = month + (i < 10 ? "0" + i : i);
                    Thread thread = new Thread(new SyncMposDayProfit(settleDay));
                    thread.start();
                }
            } else if ("5".equals(type)) {//手刷直发平台重新同步
                profitZhiFaDataJob.excute(month);
            } else if ("6".equals(type)) {//手刷补差数据重新同步
                profitMposDiffDataJob.excute(month);
            } else if ("7".equals(type)) {//手刷月汇总重算
                profitSummaryDataJob.excute(month);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class SyncMposDayProfit implements Runnable {

        private String settleDay;

        public SyncMposDayProfit(String settleDay) {
            this.settleDay = settleDay;
        }

        @Override
        public void run() {
            dailyProfitMposDataJob.excute(settleDay);
        }
    }
    @Override
    public String doSettleTranAmount(Map<String,String> param){
        String params = JsonUtil.objectToJson(param);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("settle.tranAmount"), params);
        return res;
    }

    @Override
    public Map<String, Object> getAllTranPlatform() {
        Map<String,Object> result=new HashMap<String, Object>();
        List<String> allPlatformType = tranPlatformMapper.selectAllPlatformType();
        List<Map<String, String>> allTranPlatform = tranPlatformMapper.selectAllTranPlatform();
        for (String platformType:allPlatformType) {
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < allTranPlatform.size(); i++) {
                Map<String, String> map=allTranPlatform.get(i);
                if(map.get("PLATFORM_TYPE").equals(platformType)){
                    list.add(map.get("PLATFORM_NAME"));
                }
            }
            result.put(platformType,list);
        }
        return result;
    }

    @Override
    public String doProfitNewMonth(Map<String,String> param){
        String params = JsonUtil.objectToJson(param);
        String res = HttpClientUtil.doPostJson
                (AppConfig.getProperty("profit.newmonth"), params);
        return res;
    }

    @Override
    public Map<String, Object> getTranAmtByMonth(String tranMonth) {
        return tranPlatformMapper.getTranAmtByMonth(tranMonth);
    }
}
