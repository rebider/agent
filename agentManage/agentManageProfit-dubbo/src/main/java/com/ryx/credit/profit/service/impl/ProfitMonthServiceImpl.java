package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.util.Page;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.dao.ProfitMonthMapper;
import com.ryx.credit.profit.pojo.ProfitDetailMonth;
import com.ryx.credit.profit.pojo.ProfitDetailMonthExample;
import com.ryx.credit.profit.pojo.ProfitMonth;
import com.ryx.credit.profit.pojo.ProfitMonthExample;
import com.ryx.credit.profit.service.ProfitMonthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangmx
 * @desc 月分润数据展示服务实现
 */
@Service("profitMonthService")
public class ProfitMonthServiceImpl implements ProfitMonthService {

    @Autowired
    private ProfitMonthMapper profitMonthMapper;
    @Autowired
    private ProfitDetailMonthMapper profitDetailMonthMapper;

    @Override
    public List<ProfitMonth> getProfitMonthList(Page page, ProfitMonth profitMonth) {
        ProfitMonthExample profitMonthExample= this.profitMonthEqualsTo(profitMonth);
        if(page != null){
            profitMonthExample.setPage(page);
        }
        return profitMonthMapper.selectByExample(profitMonthExample);
    }

    private ProfitMonthExample profitMonthEqualsTo(ProfitMonth profitMonth) {
        ProfitMonthExample profitMonthExample = new ProfitMonthExample();
        if(profitMonth == null ){
            return profitMonthExample;
        }
        ProfitMonthExample.Criteria criteria = profitMonthExample.createCriteria();
        if(StringUtils.isNotBlank(profitMonth.getAgentName())){
            criteria.andAgentNameEqualTo(profitMonth.getAgentName());
        }
        if(StringUtils.isNotBlank(profitMonth.getAgentId())){
            criteria.andAgentIdEqualTo(profitMonth.getAgentId());
        }
        if(StringUtils.isNotBlank(profitMonth.getProfitDate())){
            criteria.andProfitDateEqualTo(profitMonth.getProfitDate());
        }
        if(StringUtils.isNotBlank(profitMonth.getStatus())){
            criteria.andStatusEqualTo(profitMonth.getStatus());
        }
        return profitMonthExample;
    }

    @Override
    public int getProfitMonthCount(ProfitMonth profitMonth) {
        ProfitMonthExample profitMonthExample= this.profitMonthEqualsTo(profitMonth);
        return profitMonthMapper.countByExample(profitMonthExample);
    }

    @Override
    public List<ProfitDetailMonth> getProfitDetailMonthList(Page page, ProfitDetailMonth profitDetailMonth) {
        ProfitDetailMonthExample profitDetailMonthExample = profitDetailMonthEqualsTo(profitDetailMonth);
        if(page != null){
            profitDetailMonthExample.setPage(page);
        }
        return profitDetailMonthMapper.selectByExample(profitDetailMonthExample);
    }

    private ProfitDetailMonthExample profitDetailMonthEqualsTo(ProfitDetailMonth profitDetailMonth) {
        ProfitDetailMonthExample profitDetailMonthExample = new ProfitDetailMonthExample();
        if(profitDetailMonth == null){
            return profitDetailMonthExample;
        }
        ProfitDetailMonthExample.Criteria criteria = profitDetailMonthExample.createCriteria();
        if(StringUtils.isNotBlank(profitDetailMonth.getAgentId())){
            criteria.andAgentIdEqualTo(profitDetailMonth.getAgentId());
        }
        if(StringUtils.isNotBlank(profitDetailMonth.getProfitId())){
            criteria.andProfitIdEqualTo(profitDetailMonth.getProfitId());
        }
        return profitDetailMonthExample;
    }

    @Override
    public int getProfitDetailMonthCount(ProfitDetailMonth profitDetailMonth) {
        ProfitDetailMonthExample profitDetailMonthExample = profitDetailMonthEqualsTo(profitDetailMonth);
        return profitDetailMonthMapper.countByExample(profitDetailMonthExample);
    }
}