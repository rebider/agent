package com.ryx.credit.profit.service.impl;/**
 * @Auther: zhaodw
 * @Date: 2018/8/1 11:38
 * @Description:
 */

import com.ryx.credit.common.util.Page;
import com.ryx.credit.common.util.PageInfo;
import com.ryx.credit.profit.dao.ProfitOrganTranMonthMapper;
import com.ryx.credit.profit.pojo.ProfitOrganTranMonth;
import com.ryx.credit.profit.service.ProfitOrganTranMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 月交易实现
 * @author zhaodw
 * @create 2018/8/1
 * @since 1.0.0
 */
@Service("profitOrganTranMonthServiceImpl")
public class ProfitOrganTranMonthServiceImpl implements ProfitOrganTranMonthService {

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
        return null;
    }
}
