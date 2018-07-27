package com.ryx.credit.profit.service.impl;

import com.ryx.credit.common.enumc.TabId;
import com.ryx.credit.common.util.Page;
import com.ryx.credit.commons.utils.StringUtils;
import com.ryx.credit.profit.dao.ProfitDetailMonthMapper;
import com.ryx.credit.profit.dao.ProfitMonthMapper;
import com.ryx.credit.profit.dao.ProfitUnfreezeMapper;
import com.ryx.credit.profit.pojo.*;
import com.ryx.credit.profit.service.ProfitMonthService;
import com.ryx.credit.service.dict.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    @Autowired
    private ProfitUnfreezeMapper profitUnfreezeMapper;
    @Autowired
    private IdService idService;

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
        if(StringUtils.isNotBlank(profitMonth.getStatus())){
            criteria.andStatusEqualTo(profitMonth.getStatus());
        } else {
            criteria.andStatusNotEqualTo("0");
        }
        if(StringUtils.isNotBlank(profitMonth.getProfitDateStart())&& StringUtils.isNotBlank(profitMonth.getProfitDateEnd())){
            criteria.andProfitDateBetween(profitMonth.getProfitDateStart(),profitMonth.getProfitDateEnd());
        } else if(StringUtils.isNotBlank(profitMonth.getProfitDateStart())){
            criteria.andProfitDateEqualTo(profitMonth.getProfitDateStart());
        } else if(StringUtils.isNotBlank(profitMonth.getProfitDateEnd())){
            criteria.andProfitDateEqualTo(profitMonth.getProfitDateEnd());
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

    @Override
    public ProfitMonth getProfitMonth(String id) {
        if(StringUtils.isNotBlank(id)){
            return profitMonthMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    public void updateProfitMonth(ProfitMonth profitMonth) {
        if(profitMonth != null){
            profitMonthMapper.updateByPrimaryKeySelective(profitMonth);
        }
    }

    @Override
    public ProfitMonth selectByPrimaryKey(String id) {
        return profitMonthMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ProfitMonth record) {
        return profitMonthMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void insertProfitUnfreeze(ProfitUnfreeze profitUnfreeze) {
        if(profitUnfreeze != null){
            profitUnfreeze.setCreateTime(new Date());
            profitUnfreeze.setUpdateTime(new Date());
            profitUnfreeze.setId(idService.genId(TabId.p_profit_unfreeze));
            profitUnfreezeMapper.insertSelective(profitUnfreeze);
        }
    }
}
