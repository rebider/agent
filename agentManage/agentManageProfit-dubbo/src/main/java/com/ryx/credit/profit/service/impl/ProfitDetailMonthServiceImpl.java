package com.ryx.credit.profit.service.impl;/**
 * @Auther: zhaodw
 * @Date: 2018/8/6 09:08
 * @Description:
 */

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitDetailMonthExample;
import com.ryx.credit.profit.service.ProfitDetailMonthService;
import com.ryx.credit.service.dict.IdService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public int countByExample(ProfitDetailMonthExample example) {
        return profitDetailMonthMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(ProfitDetailMonthExample example) {
        return profitDetailMonthMapper.deleteByExample(example);
    }

    @Override
    public int insertSelective(ProfitDetailMonth record) {
        return profitDetailMonthMapper.insertSelective(record);
    }

    @Override
    public List<ProfitDetailMonth> selectByExample(ProfitDetailMonthExample example) {
        return profitDetailMonthMapper.selectByExample(example);
    }

    @Override
    public ProfitDetailMonth selectByPrimaryKey(String id) {
        return profitDetailMonthMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProfitDetailMonth selectByPIdAndMonth(ProfitDetailMonth record) {
        return profitDetailMonthMapper.selectByPIdAndMonth(record);
    }

    @Override
    public int updateByPrimaryKeySelective(ProfitDetailMonth record) {
        return profitDetailMonthMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ProfitDetailMonth record) {
        return profitDetailMonthMapper.updateByPrimaryKey(record);
    }


}