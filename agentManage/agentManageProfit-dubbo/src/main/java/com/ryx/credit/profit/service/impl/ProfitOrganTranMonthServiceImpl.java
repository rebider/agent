package com.ryx.credit.profit.service.impl;/**
 * @Auther: zhaodw
 * @Date: 2018/8/1 11:38
 * @Description:
 */

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitOrganTranMonthMapper;
import com.ryx.credit.profit.pojo.ProfitOrganTranMonth;
import com.ryx.credit.profit.pojo.ProfitOrganTranMonthExample;
import com.ryx.credit.profit.service.ProfitOrganTranMonthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 月交易实现
 * @author zhaodw
 * @create 2018/8/1
 * @since 1.0.0
 */
@Service("profitOrganTranMonthServiceImpl")
public class ProfitOrganTranMonthServiceImpl implements ProfitOrganTranMonthService {

    private static final Logger LOGGER = Logger.getLogger(ProfitOrganTranMonthServiceImpl.class);

    @Autowired
    private ProfitOrganTranMonthMapper profitOrganTranMonthMapper;

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
        if (StringUtils.isNotBlank(profitOrganTranMonth.getProfitDate()))
        {
            criteria.andProfitDateEqualTo(profitOrganTranMonth.getProfitDate());
        }
        if (StringUtils.isNotBlank(profitOrganTranMonth.getProductType()))
        {
            criteria.andProductTypeEqualTo(profitOrganTranMonth.getProductType());
        }
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
        if (StringUtils.isNotBlank(profitOrganTranMonth.getProfitDate()))
        {
            criteria.andProfitDateEqualTo(profitOrganTranMonth.getProfitDate());
            if (StringUtils.isNotBlank(profitOrganTranMonth.getProductType()))
            {
                criteria.andProductTypeEqualTo(profitOrganTranMonth.getProductType());
            }
        } else {
            LOGGER.error("删除失败");
            throw new RuntimeException("删除失败。。");
        }
        profitOrganTranMonthMapper.deleteByExample(example);
    }
}
