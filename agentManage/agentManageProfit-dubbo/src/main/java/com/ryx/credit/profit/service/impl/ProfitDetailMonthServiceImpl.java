package com.ryx.credit.profit.service.impl;/**
 * @Auther: zhaodw
 * @Date: 2018/8/6 09:08
 * @Description:
 */

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 月度分润明细业务接口实现
 * @author zhaodw
 * @create 2018/8/6
 * @since 1.0.0
 */
@Service("profitDetailMonthServiceImpl")
public class ProfitDetailMonthServiceImpl implements ProfitDetailMonthService {

    private static final Logger LOG = Logger.getLogger(ProfitDetailMonthServiceImpl.class);

    @Autowired
    private ProfitDetailMonthMapper profitDetailMonthMapper;

    @Autowired
    private IdService idService;

    @Override
    public void insert(ProfitDetailMonth profitDetailMonth) {
        if(profitDetailMonth.getId() == null) {
            profitDetailMonth.setId(idService.genId(TabId.P_PROFIT_DETAIL_M));
        }
        profitDetailMonthMapper.insert(profitDetailMonth);
    }

    @Override
    public void update(ProfitDetailMonth profitDetailMonth) {
        if(profitDetailMonth.getId() == null) {
            LOG.error("没有主键。");
            return;
        }
        profitDetailMonthMapper.updateByPrimaryKeySelective(profitDetailMonth);
    }
}
